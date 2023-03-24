/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.MetroClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.commons.MaintenanceTaskExecutor;
/*     */ import com.sun.xml.ws.commons.VolatileReference;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.dev.ProtocolMessageHandler;
/*     */ import com.sun.xml.ws.rx.mc.dev.WsmcRuntimeProvider;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.PostmanPool;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateSequenceException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManagerFactory;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ClientTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  87 */   private static final Logger LOGGER = Logger.getLogger(ClientTube.class);
/*  88 */   private static final Lock INIT_LOCK = new ReentrantLock();
/*     */   
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   private final WSEndpointReference rmSourceReference;
/*     */   private volatile VolatileReference<String> outboundSequenceId;
/*     */   
/*     */   ClientTube(ClientTube original, TubeCloner cloner) {
/*  96 */     super(original, cloner);
/*  97 */     this.rc = original.rc;
/*     */     
/*  99 */     this.rmSourceReference = original.rmSourceReference;
/* 100 */     this.outboundSequenceId = original.outboundSequenceId;
/*     */   }
/*     */   
/*     */   ClientTube(RmConfiguration configuration, ClientTubelineAssemblyContext context) throws RxRuntimeException {
/* 104 */     super(context.getTubelineHead());
/*     */     
/* 106 */     this.outboundSequenceId = new VolatileReference(null);
/*     */     
/* 108 */     SecureConversationInitiator scInitiator = (SecureConversationInitiator)context.getImplementation(SecureConversationInitiator.class);
/* 109 */     if (scInitiator == null)
/*     */     {
/* 111 */       scInitiator = ((MetroClientTubelineAssemblyContext)context).getScInitiator();
/*     */     }
/*     */     
/* 114 */     this.rc = RuntimeContext.builder(configuration, Communicator.builder("rm-client-tube-communicator").tubelineHead(this.next).secureConversationInitiator(scInitiator).addressingVersion(configuration.getAddressingVersion()).soapVersion(configuration.getSoapVersion()).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     DeliveryQueueBuilder outboundQueueBuilder = DeliveryQueueBuilder.getBuilder(this.rc.configuration, PostmanPool.INSTANCE.getPostman(), new ClientSourceDeliveryCallback(this.rc));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     DeliveryQueueBuilder inboundQueueBuilder = null;
/* 130 */     if (this.rc.configuration.requestResponseOperationsDetected()) {
/* 131 */       inboundQueueBuilder = DeliveryQueueBuilder.getBuilder(this.rc.configuration, PostmanPool.INSTANCE.getPostman(), new ClientDestinationDeliveryCallback(this.rc));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     SequenceManager sequenceManager = SequenceManagerFactory.INSTANCE.createSequenceManager(false, context.getAddress().getURI().toString(), inboundQueueBuilder, outboundQueueBuilder, this.rc.configuration);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     this.rc.setSequenceManager(sequenceManager);
/*     */ 
/*     */     
/* 146 */     WsmcRuntimeProvider wsmcRuntimeProvider = (WsmcRuntimeProvider)context.getImplementation(WsmcRuntimeProvider.class);
/* 147 */     if (configuration.isMakeConnectionSupportEnabled()) {
/* 148 */       assert wsmcRuntimeProvider != null;
/*     */       
/* 150 */       this.rmSourceReference = wsmcRuntimeProvider.getWsmcAnonymousEndpointReference();
/* 151 */       wsmcRuntimeProvider.registerProtocolMessageHandler(createRmProtocolMessageHandler(this.rc));
/*     */     } else {
/* 153 */       this.rmSourceReference = (configuration.getAddressingVersion()).anonymousEpr;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientTube copy(TubeCloner cloner) {
/* 159 */     LOGGER.entering();
/*     */     try {
/* 161 */       return new ClientTube(this, cloner);
/*     */     } finally {
/* 163 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 169 */     LOGGER.entering();
/*     */     try {
/* 171 */       HaContext.initFrom(request);
/* 172 */       if (HaContext.failoverDetected()) {
/* 173 */         this.rc.sequenceManager().invalidateCache();
/*     */       }
/*     */       
/*     */       try {
/* 177 */         INIT_LOCK.lock();
/* 178 */         if (this.outboundSequenceId.value == null) {
/* 179 */           openRmSession(request);
/*     */         }
/*     */       } finally {
/* 182 */         INIT_LOCK.unlock();
/*     */       } 
/* 184 */       assert this.outboundSequenceId != null;
/*     */       
/* 186 */       JaxwsApplicationMessage message = new JaxwsApplicationMessage(request, request.getMessage().getID(this.rc.addressingVersion, this.rc.soapVersion));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 200 */     catch (DuplicateMessageRegistrationException ex) {
/*     */       
/* 202 */       LOGGER.logSevereException((Throwable)ex);
/* 203 */       return doThrow((Throwable)ex);
/* 204 */     } catch (RxRuntimeException ex) {
/* 205 */       LOGGER.logSevereException((Throwable)ex);
/* 206 */       return doThrow((Throwable)ex);
/*     */     } finally {
/* 208 */       HaContext.clear();
/* 209 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet repsonse) {
/* 215 */     LOGGER.entering();
/*     */     try {
/* 217 */       return super.processResponse(repsonse);
/*     */     } finally {
/* 219 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable throwable) {
/* 225 */     LOGGER.entering();
/*     */     try {
/* 227 */       if (throwable instanceof RxRuntimeException)
/*     */       {
/* 229 */         closeRmSession();
/*     */       }
/*     */       
/* 232 */       return super.processException(throwable);
/*     */     } finally {
/* 234 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 240 */     LOGGER.entering();
/*     */     try {
/* 242 */       closeRmSession();
/*     */     } finally {
/*     */       try {
/* 245 */         this.rc.close();
/*     */       } finally {
/* 247 */         super.preDestroy();
/* 248 */         LOGGER.exiting();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static ProtocolMessageHandler createRmProtocolMessageHandler(final RuntimeContext rc) {
/* 255 */     final RmProtocolVersion rmVersion = (rc.configuration.getRuntimeVersion()).protocolVersion;
/*     */     
/* 257 */     return new ProtocolMessageHandler()
/*     */       {
/* 259 */         Collection<String> SUPPORTED_WSA_ACTIONS = Collections.unmodifiableCollection(Arrays.asList(new String[] { this.val$rmVersion.ackRequestedAction, this.val$rmVersion.closeSequenceAction, this.val$rmVersion.sequenceAcknowledgementAction, this.val$rmVersion.terminateSequenceAction }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public Collection<String> getSuportedWsaActions() {
/* 272 */           return this.SUPPORTED_WSA_ACTIONS;
/*     */         }
/*     */         
/*     */         public void processProtocolMessage(Packet protocolMessagePacket) {
/* 276 */           if (rc.protocolHandler.containsProtocolMessage(protocolMessagePacket)) {
/* 277 */             ClientTube.LOGGER.finer("Processing RM protocol response message.");
/*     */             
/* 279 */             String wsaAction = rc.communicator.getWsaAction(protocolMessagePacket);
/* 280 */             if (rmVersion.ackRequestedAction.equals(wsaAction) || rmVersion.sequenceAcknowledgementAction.equals(wsaAction)) {
/* 281 */               AcknowledgementData ackData = rc.protocolHandler.getAcknowledgementData(protocolMessagePacket.getMessage());
/* 282 */               rc.destinationMessageHandler.processAcknowledgements(ackData);
/* 283 */             } else if (rmVersion.closeSequenceAction.equals(wsaAction)) {
/* 284 */               handleCloseSequenceAction(protocolMessagePacket);
/* 285 */             } else if (rmVersion.terminateSequenceAction.equals(wsaAction)) {
/* 286 */               handleTerminateSequenceAction(protocolMessagePacket);
/*     */             } else {
/* 288 */               throw (RxRuntimeException)ClientTube.LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1134_UNSUPPORTED_PROTOCOL_MESSAGE(wsaAction)));
/*     */             } 
/*     */           } else {
/* 291 */             ClientTube.LOGGER.severe(LocalizationMessages.WSRM_1120_RESPONSE_NOT_IDENTIFIED_AS_PROTOCOL_MESSAGE());
/*     */           } 
/*     */         }
/*     */         
/*     */         private void handleCloseSequenceAction(Packet protocolMessagePacket) {
/* 296 */           CloseSequenceData requestData = rc.protocolHandler.toCloseSequenceData(protocolMessagePacket);
/* 297 */           rc.destinationMessageHandler.processAcknowledgements(requestData.getAcknowledgementData());
/*     */ 
/*     */           
/*     */           try {
/* 301 */             Sequence closedSequence = rc.sequenceManager().closeSequence(requestData.getSequenceId());
/*     */             
/* 303 */             CloseSequenceResponseData.Builder responseBuilder = CloseSequenceResponseData.getBuilder(closedSequence.getId());
/* 304 */             responseBuilder.acknowledgementData(rc.destinationMessageHandler.getAcknowledgementData(closedSequence.getId()));
/* 305 */             closedSequence.clearAckRequestedFlag();
/*     */             
/* 307 */             CloseSequenceResponseData responseData = responseBuilder.build();
/* 308 */             Packet responsePacket = rc.protocolHandler.toPacket(responseData, protocolMessagePacket, true);
/* 309 */             rc.communicator.sendAsync(responsePacket, null);
/* 310 */           } catch (UnknownSequenceException ex) {
/* 311 */             ClientTube.LOGGER.warning(LocalizationMessages.WSRM_1124_NO_SUCH_SEQUENCE_ID_REGISTERED(requestData.getSequenceId()), (Throwable)ex);
/* 312 */             rc.communicator.sendAsync(ex.toRequest(rc), null);
/*     */           } 
/*     */         }
/*     */         
/*     */         private void handleTerminateSequenceAction(Packet protocolMessagePacket) {
/* 317 */           TerminateSequenceData requestData = rc.protocolHandler.toTerminateSequenceData(protocolMessagePacket);
/* 318 */           rc.destinationMessageHandler.processAcknowledgements(requestData.getAcknowledgementData());
/*     */ 
/*     */           
/*     */           try {
/* 322 */             rc.sequenceManager().terminateSequence(requestData.getSequenceId());
/*     */             
/* 324 */             TerminateSequenceResponseData tsrData = TerminateSequenceResponseData.getBuilder(requestData.getSequenceId()).build();
/* 325 */             Packet tsrPacket = rc.protocolHandler.toPacket(tsrData, protocolMessagePacket, true);
/* 326 */             rc.communicator.sendAsync(tsrPacket, null);
/* 327 */           } catch (UnknownSequenceException ex) {
/* 328 */             ClientTube.LOGGER.warning(LocalizationMessages.WSRM_1124_NO_SUCH_SEQUENCE_ID_REGISTERED(requestData.getSequenceId()), (Throwable)ex);
/* 329 */             rc.communicator.sendAsync(ex.toRequest(rc), null);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private void openRmSession(Packet request) {
/* 336 */     this.rc.communicator.setDestinationAddressFrom(request);
/*     */     
/* 338 */     createSequences(request);
/*     */     
/* 340 */     ClientAckRequesterTask cart = new ClientAckRequesterTask(this.rc, (String)this.outboundSequenceId.value);
/* 341 */     MaintenanceTaskExecutor.INSTANCE.register(cart, cart.getExecutionDelay(), cart.getExecutionDelayTimeUnit());
/*     */   }
/*     */   
/*     */   private void closeRmSession() {
/* 345 */     if (this.outboundSequenceId.value == null || !this.rc.sequenceManager().isValid((String)this.outboundSequenceId.value)) {
/*     */       return;
/*     */     }
/*     */     
/* 349 */     String inboundSequenceId = this.rc.getBoundSequenceId((String)this.outboundSequenceId.value);
/*     */     try {
/* 351 */       if (inboundSequenceId != null) {
/* 352 */         waitForMissingAcknowledgements(inboundSequenceId, this.rc.configuration.getRmFeature().getCloseSequenceOperationTimeout());
/*     */       }
/* 354 */     } catch (RuntimeException ex) {
/* 355 */       LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */     } 
/*     */     
/*     */     try {
/* 359 */       sendCloseSequenceRequest();
/* 360 */     } catch (RuntimeException ex) {
/* 361 */       LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */     } finally {
/* 363 */       closeSequences();
/*     */     } 
/*     */     
/*     */     try {
/* 367 */       waitForMissingAcknowledgements((String)this.outboundSequenceId.value, this.rc.configuration.getRmFeature().getCloseSequenceOperationTimeout());
/* 368 */     } catch (RuntimeException ex) {
/* 369 */       LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */     } 
/*     */     
/*     */     try {
/* 373 */       terminateOutboundSequence();
/* 374 */     } catch (RuntimeException ex) {
/* 375 */       LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */     } finally {
/*     */       
/* 378 */       this.rc.sequenceManager().terminateSequence((String)this.outboundSequenceId.value);
/*     */     } 
/*     */     
/* 381 */     if (inboundSequenceId != null) {
/*     */       try {
/* 383 */         waitForInboundSequenceStateChange(inboundSequenceId, this.rc.configuration.getRmFeature().getCloseSequenceOperationTimeout(), Sequence.State.TERMINATING);
/* 384 */       } catch (RuntimeException ex) {
/* 385 */         LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */       } finally {
/* 387 */         if (this.rc.sequenceManager().isValid(inboundSequenceId)) {
/*     */           try {
/* 389 */             this.rc.sequenceManager().terminateSequence(inboundSequenceId);
/* 390 */           } catch (UnknownSequenceException ignored) {}
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void createSequences(Packet appRequest) throws RxRuntimeException, DuplicateSequenceException {
/* 397 */     CreateSequenceData.Builder csBuilder = CreateSequenceData.getBuilder(this.rmSourceReference.toSpec());
/*     */     
/*     */     try {
/* 400 */       csBuilder.strType(this.rc.communicator.tryStartSecureConversation(appRequest));
/* 401 */     } catch (WSTrustException ex) {
/* 402 */       LOGGER.severe(LocalizationMessages.WSRM_1121_SECURE_CONVERSATION_INIT_FAILED(), (Throwable)ex);
/*     */     } 
/*     */     
/* 405 */     if (this.rc.configuration.requestResponseOperationsDetected() && !this.rc.configuration.getRmFeature().isOfferElementGenerationDisabled()) {
/* 406 */       csBuilder.offeredInboundSequenceId(this.rc.sequenceManager().generateSequenceUID());
/*     */     }
/*     */ 
/*     */     
/* 410 */     String messageName = "CreateSequence";
/*     */     
/* 412 */     CreateSequenceData requestData = csBuilder.build();
/* 413 */     Packet request = this.rc.protocolHandler.toPacket(requestData, (Packet)null);
/*     */     
/* 415 */     Packet response = sendSessionControlMessage("CreateSequence", request);
/* 416 */     CreateSequenceResponseData responseData = this.rc.protocolHandler.toCreateSequenceResponseData(verifyResponse(response, "CreateSequence", Level.SEVERE));
/*     */     
/* 418 */     if (requestData.getOfferedSequenceId() != null && responseData.getAcceptedSequenceAcksTo() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       csBuilder.offeredInboundSequenceId(this.rc.sequenceManager().generateSequenceUID());
/*     */ 
/*     */       
/* 427 */       requestData = csBuilder.build();
/* 428 */       request = this.rc.protocolHandler.toPacket(requestData, (Packet)null);
/*     */       
/* 430 */       response = sendSessionControlMessage("CreateSequence", request);
/* 431 */       responseData = this.rc.protocolHandler.toCreateSequenceResponseData(verifyResponse(response, "CreateSequence", Level.SEVERE));
/*     */     } 
/*     */     
/* 434 */     if (responseData.getAcceptedSequenceAcksTo() != null && 
/* 435 */       !this.rc.communicator.getDestinationAddress().getURI().toString().equals((new WSEndpointReference(responseData.getAcceptedSequenceAcksTo())).getAddress())) {
/* 436 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1116_ACKS_TO_NOT_EQUAL_TO_ENDPOINT_DESTINATION(responseData.getAcceptedSequenceAcksTo().toString(), this.rc.communicator.getDestinationAddress()));
/*     */     }
/*     */ 
/*     */     
/* 440 */     this.outboundSequenceId.value = this.rc.sequenceManager().createOutboundSequence(responseData.getSequenceId(), (requestData.getStrType() != null) ? requestData.getStrType().getId() : null, (responseData.getDuration() == -1L) ? -1L : (responseData.getDuration() + this.rc.sequenceManager().currentTimeInMillis())).getId();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (requestData.getOfferedSequenceId() != null) {
/* 446 */       Sequence inboundSequence = this.rc.sequenceManager().createInboundSequence(requestData.getOfferedSequenceId(), (requestData.getStrType() != null) ? requestData.getStrType().getId() : null, (responseData.getDuration() == -1L) ? -1L : (responseData.getDuration() + this.rc.sequenceManager().currentTimeInMillis()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 451 */       this.rc.sequenceManager().bindSequences((String)this.outboundSequenceId.value, inboundSequence.getId());
/* 452 */       this.rc.sequenceManager().bindSequences(inboundSequence.getId(), (String)this.outboundSequenceId.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean sendCloseSequenceRequest() {
/* 457 */     CloseSequenceData.Builder dataBuilder = CloseSequenceData.getBuilder((String)this.outboundSequenceId.value, this.rc.sequenceManager().getOutboundSequence((String)this.outboundSequenceId.value).getLastMessageNumber());
/*     */ 
/*     */     
/* 460 */     dataBuilder.acknowledgementData(this.rc.sourceMessageHandler.getAcknowledgementData((String)this.outboundSequenceId.value));
/*     */     
/* 462 */     Packet request = this.rc.protocolHandler.toPacket(dataBuilder.build(), (Packet)null);
/*     */     
/* 464 */     String messageName = "CloseSequence";
/* 465 */     Packet response = verifyResponse(sendSessionControlMessage("CloseSequence", request), "CloseSequence", Level.WARNING);
/*     */     
/* 467 */     String responseAction = this.rc.communicator.getWsaAction(response);
/* 468 */     if (this.rc.rmVersion.protocolVersion.closeSequenceResponseAction.equals(responseAction)) {
/* 469 */       CloseSequenceResponseData responseData = this.rc.protocolHandler.toCloseSequenceResponseData(response);
/* 470 */       this.rc.destinationMessageHandler.processAcknowledgements(responseData.getAcknowledgementData());
/* 471 */       if (!((String)this.outboundSequenceId.value).equals(responseData.getSequenceId())) {
/* 472 */         LOGGER.warning(LocalizationMessages.WSRM_1119_UNEXPECTED_SEQUENCE_ID_IN_CLOSE_SR(responseData.getSequenceId(), this.outboundSequenceId));
/*     */       }
/*     */       
/* 475 */       return true;
/*     */     } 
/*     */     
/* 478 */     return false;
/*     */   }
/*     */   
/*     */   private void closeSequences() {
/* 482 */     String boundSequenceId = this.rc.getBoundSequenceId((String)this.outboundSequenceId.value);
/*     */     try {
/* 484 */       this.rc.sequenceManager().closeSequence((String)this.outboundSequenceId.value);
/*     */     } finally {
/* 486 */       if (boundSequenceId != null) {
/*     */         try {
/* 488 */           waitForInboundSequenceStateChange(boundSequenceId, this.rc.configuration.getRmFeature().getCloseSequenceOperationTimeout(), Sequence.State.CLOSED);
/* 489 */         } catch (RuntimeException ex) {
/* 490 */           LOGGER.warning(LocalizationMessages.WSRM_1103_RM_SEQUENCE_NOT_TERMINATED_NORMALLY(), ex);
/*     */         } finally {
/* 492 */           if (this.rc.sequenceManager().isValid(boundSequenceId)) {
/*     */             try {
/* 494 */               this.rc.sequenceManager().closeSequence(boundSequenceId);
/* 495 */             } catch (UnknownSequenceException ignored) {}
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void terminateOutboundSequence() {
/* 503 */     TerminateSequenceData.Builder dataBuilder = TerminateSequenceData.getBuilder((String)this.outboundSequenceId.value, this.rc.sequenceManager().getOutboundSequence((String)this.outboundSequenceId.value).getLastMessageNumber());
/*     */ 
/*     */     
/* 506 */     dataBuilder.acknowledgementData(this.rc.sourceMessageHandler.getAcknowledgementData((String)this.outboundSequenceId.value));
/*     */     
/* 508 */     Packet request = this.rc.protocolHandler.toPacket(dataBuilder.build(), (Packet)null);
/*     */     
/* 510 */     String messageName = "TerminateSequence";
/* 511 */     Packet response = verifyResponse(sendSessionControlMessage("TerminateSequence", request), "TerminateSequence", Level.FINE);
/*     */     
/* 513 */     if (response.getMessage() != null) {
/* 514 */       String responseAction = this.rc.communicator.getWsaAction(response);
/*     */       
/* 516 */       if (this.rc.rmVersion.protocolVersion.terminateSequenceResponseAction.equals(responseAction)) {
/* 517 */         TerminateSequenceResponseData responseData = this.rc.protocolHandler.toTerminateSequenceResponseData(response);
/*     */         
/* 519 */         this.rc.destinationMessageHandler.processAcknowledgements(responseData.getAcknowledgementData());
/*     */         
/* 521 */         if (!((String)this.outboundSequenceId.value).equals(responseData.getSequenceId())) {
/* 522 */           LOGGER.warning(LocalizationMessages.WSRM_1117_UNEXPECTED_SEQUENCE_ID_IN_TERMINATE_SR(responseData.getSequenceId(), this.outboundSequenceId.value));
/*     */         }
/*     */       }
/* 525 */       else if (this.rc.rmVersion.protocolVersion.terminateSequenceAction.equals(responseAction)) {
/* 526 */         TerminateSequenceData responseData = this.rc.protocolHandler.toTerminateSequenceData(response);
/*     */         
/* 528 */         this.rc.destinationMessageHandler.processAcknowledgements(responseData.getAcknowledgementData());
/*     */         
/* 530 */         if (responseData.getSequenceId() != null) {
/* 531 */           String expectedInboundSequenceId = this.rc.getBoundSequenceId((String)this.outboundSequenceId.value);
/* 532 */           if (!areEqual(expectedInboundSequenceId, responseData.getSequenceId())) {
/* 533 */             LOGGER.warning(LocalizationMessages.WSRM_1117_UNEXPECTED_SEQUENCE_ID_IN_TERMINATE_SR(responseData.getSequenceId(), expectedInboundSequenceId));
/*     */           }
/*     */           
/*     */           try {
/* 537 */             this.rc.sequenceManager().terminateSequence(responseData.getSequenceId());
/* 538 */           } catch (UnknownSequenceException ex) {
/* 539 */             LOGGER.warning(LocalizationMessages.WSRM_1124_NO_SUCH_SEQUENCE_ID_REGISTERED(responseData.getSequenceId()), (Throwable)ex);
/* 540 */             this.rc.communicator.sendAsync(ex.toRequest(this.rc), null);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet sendSessionControlMessage(String messageName, Packet request) throws RxRuntimeException {
/* 548 */     int attempt = 0;
/* 549 */     Packet response = null;
/*     */     while (true) {
/* 551 */       if (attempt > this.rc.configuration.getRmFeature().getMaxRmSessionControlMessageResendAttempts()) {
/* 552 */         throw new RxRuntimeException(LocalizationMessages.WSRM_1128_MAX_RM_SESSION_CONTROL_MESSAGE_RESEND_ATTEMPTS_REACHED(messageName));
/*     */       }
/*     */       try {
/* 555 */         response = this.rc.communicator.send(request.copy(true));
/*     */         break;
/* 557 */       } catch (RuntimeException ex) {
/* 558 */         if (!Utilities.isResendPossible(ex)) {
/* 559 */           throw new RxRuntimeException(LocalizationMessages.WSRM_1106_SENDING_RM_SESSION_CONTROL_MESSAGE_FAILED(messageName), ex);
/*     */         }
/* 561 */         LOGGER.warning(LocalizationMessages.WSRM_1106_SENDING_RM_SESSION_CONTROL_MESSAGE_FAILED(messageName), ex);
/*     */ 
/*     */         
/* 564 */         attempt++;
/*     */       } 
/* 566 */     }  return response;
/*     */   }
/*     */   
/*     */   private static boolean areEqual(String s1, String s2) {
/* 570 */     if (s1 == null) {
/* 571 */       return (s2 == null);
/*     */     }
/* 573 */     return s1.equals(s2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void waitForMissingAcknowledgements(final String sequenceId, long timeoutInMillis) {
/* 578 */     final CountDownLatch doneSignal = new CountDownLatch(1);
/* 579 */     ScheduledFuture<?> taskHandle = this.rc.scheduledTaskManager.startTask(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 583 */               if (!ClientTube.this.rc.sequenceManager().getSequence(sequenceId).hasUnacknowledgedMessages()) {
/* 584 */                 doneSignal.countDown();
/*     */               }
/* 586 */             } catch (UnknownSequenceException ex) {
/* 587 */               ClientTube.LOGGER.severe(LocalizationMessages.WSRM_1111_WAITING_FOR_SEQ_ACKS_UNEXPECTED_EXCEPTION(sequenceId), (Throwable)ex);
/* 588 */               doneSignal.countDown();
/*     */             } 
/*     */           }
/*     */         }10L, 10L);
/*     */     
/*     */     try {
/* 594 */       if (timeoutInMillis > 0L) {
/* 595 */         boolean waitResult = doneSignal.await(timeoutInMillis, TimeUnit.MILLISECONDS);
/* 596 */         if (!waitResult) {
/* 597 */           LOGGER.info(LocalizationMessages.WSRM_1112_WAITING_FOR_SEQ_ACKS_TIMED_OUT(sequenceId, Long.valueOf(timeoutInMillis)));
/*     */         }
/*     */       } else {
/* 600 */         doneSignal.await();
/*     */       } 
/* 602 */     } catch (InterruptedException ex) {
/* 603 */       LOGGER.fine(LocalizationMessages.WSRM_1113_WAITING_FOR_SEQ_ACKS_INTERRUPTED(sequenceId), ex);
/*     */     } finally {
/* 605 */       taskHandle.cancel(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void waitForInboundSequenceStateChange(final String sequenceId, long timeoutInMillis, final Sequence.State waitForState) {
/* 610 */     final CountDownLatch stateChangedSignal = new CountDownLatch(1);
/* 611 */     ScheduledFuture<?> taskHandle = this.rc.scheduledTaskManager.startTask(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 615 */               if (ClientTube.this.rc.sequenceManager().getSequence(sequenceId).getState() == waitForState) {
/* 616 */                 stateChangedSignal.countDown();
/*     */               }
/* 618 */             } catch (UnknownSequenceException ex) {
/* 619 */               ClientTube.LOGGER.fine(LocalizationMessages.WSRM_1124_NO_SUCH_SEQUENCE_ID_REGISTERED(sequenceId), (Throwable)ex);
/* 620 */               stateChangedSignal.countDown();
/*     */             } 
/*     */           }
/*     */         }10L, 10L);
/*     */     
/*     */     try {
/* 626 */       if (timeoutInMillis > 0L) {
/* 627 */         boolean waitResult = stateChangedSignal.await(timeoutInMillis, TimeUnit.MILLISECONDS);
/* 628 */         if (!waitResult) {
/* 629 */           LOGGER.info(LocalizationMessages.WSRM_1157_WAITING_FOR_SEQ_STATE_CHANGE_TIMED_OUT(sequenceId, waitForState, Long.valueOf(timeoutInMillis)));
/*     */         }
/*     */       } else {
/* 632 */         stateChangedSignal.await();
/*     */       } 
/* 634 */     } catch (InterruptedException ex) {
/* 635 */       LOGGER.fine(LocalizationMessages.WSRM_1158_WAITING_FOR_SEQ_STATE_CHANGE_INTERRUPTED(sequenceId, waitForState), ex);
/*     */     } finally {
/* 637 */       taskHandle.cancel(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet verifyResponse(Packet response, String requestId, Level logLevel) throws RxRuntimeException {
/* 642 */     String logMessage = null;
/* 643 */     if (response == null || response.getMessage() == null) {
/* 644 */       logMessage = LocalizationMessages.WSRM_1114_NULL_RESPONSE_ON_PROTOCOL_MESSAGE_REQUEST(requestId);
/*     */     } else {
/* 646 */       String responseAction = this.rc.communicator.getWsaAction(response);
/* 647 */       if (response.getMessage().isFault() || this.rc.rmVersion.protocolVersion.isFault(responseAction)) {
/* 648 */         logMessage = LocalizationMessages.WSRM_1115_PROTOCOL_MESSAGE_REQUEST_REFUSED(requestId);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 653 */     if (logMessage != null) {
/* 654 */       if (logLevel == Level.SEVERE) {
/* 655 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(logMessage));
/*     */       }
/* 657 */       LOGGER.log(logLevel, logMessage);
/*     */     } 
/*     */ 
/*     */     
/* 661 */     return response;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */