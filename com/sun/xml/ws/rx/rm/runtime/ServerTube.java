/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.runtime.dev.Session;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.rx.RxException;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.RmSecurityException;
/*     */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*     */ import com.sun.xml.ws.rx.rm.faults.CreateSequenceRefusedFault;
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
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManagerFactory;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.ws.EndpointReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  89 */   private static final Logger LOGGER = Logger.getLogger(ServerTube.class);
/*  90 */   private static final Level PROTOCOL_FAULT_LOGGING_LEVEL = Level.WARNING;
/*     */ 
/*     */   
/*     */   private static final String SEQUENCE_PROPERTY = "com.sun.xml.ws.sequence";
/*     */ 
/*     */   
/*     */   private static final String MESSAGE_NUMBER_PROPERTY = "com.sun.xml.ws.messagenumber";
/*     */   
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   private final WSEndpoint endpoint;
/*     */ 
/*     */   
/*     */   public ServerTube(ServerTube original, TubeCloner cloner) {
/* 104 */     super(original, cloner);
/*     */     
/* 106 */     this.rc = original.rc;
/* 107 */     this.endpoint = original.endpoint;
/*     */   }
/*     */   
/*     */   public ServerTube(RmConfiguration configuration, ServerTubelineAssemblyContext context) {
/* 111 */     super(context.getTubelineHead());
/*     */     
/* 113 */     this.endpoint = context.getEndpoint();
/*     */ 
/*     */     
/* 116 */     if (configuration.getAddressingVersion() == null) {
/* 117 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1140_NO_ADDRESSING_VERSION_ON_ENDPOINT());
/*     */     }
/*     */     
/* 120 */     RuntimeContext.Builder rcBuilder = RuntimeContext.builder(configuration, Communicator.builder("rm-server-tube-communicator").tubelineHead(this.next).addressingVersion(configuration.getAddressingVersion()).soapVersion(configuration.getSoapVersion()).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build());
/*     */ 
/*     */     
/* 123 */     this.rc = rcBuilder.build();
/*     */     
/* 125 */     DeliveryQueueBuilder inboundQueueBuilder = DeliveryQueueBuilder.getBuilder(configuration, PostmanPool.INSTANCE.getPostman(), new ServerDestinationDeliveryCallback(this.rc));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     DeliveryQueueBuilder outboundQueueBuilder = null;
/* 131 */     if (configuration.requestResponseOperationsDetected()) {
/* 132 */       outboundQueueBuilder = DeliveryQueueBuilder.getBuilder(configuration, PostmanPool.INSTANCE.getPostman(), new ServerSourceDeliveryCallback(this.rc));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     SequenceManager sequenceManager = SequenceManagerFactory.INSTANCE.createSequenceManager(configuration.getRmFeature().isPersistenceEnabled(), context.getEndpoint().getServiceName() + "::" + context.getEndpoint().getPortName(), inboundQueueBuilder, outboundQueueBuilder, configuration);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     this.rc.setSequenceManager(sequenceManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerTube copy(TubeCloner cloner) {
/* 150 */     LOGGER.entering();
/*     */     try {
/* 152 */       return new ServerTube(this, cloner);
/*     */     } finally {
/* 154 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 160 */     LOGGER.entering();
/*     */     try {
/* 162 */       HaContext.initFrom(request);
/* 163 */       if (HaContext.failoverDetected()) {
/* 164 */         this.rc.sequenceManager().invalidateCache();
/*     */       }
/*     */       
/* 167 */       String wsaAction = this.rc.communicator.getWsaAction(request);
/* 168 */       if (this.rc.rmVersion.protocolVersion.isProtocolAction(wsaAction)) {
/* 169 */         return doReturnWith(processProtocolMessage(request, wsaAction));
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 174 */       request.keepTransportBackChannelOpen();
/*     */       
/* 176 */       JaxwsApplicationMessage message = new JaxwsApplicationMessage(request, request.getMessage().getID(this.rc.addressingVersion, this.rc.soapVersion));
/* 177 */       this.rc.protocolHandler.loadSequenceHeaderData(message, message.getJaxwsMessage());
/* 178 */       this.rc.protocolHandler.loadAcknowledgementData(message, message.getJaxwsMessage());
/*     */       
/* 180 */       validateSecurityContextTokenId(this.rc.sequenceManager().getInboundSequence(message.getSequenceId()).getBoundSecurityTokenReferenceId(), message.getPacket());
/* 181 */       if (!hasSession(request)) {
/* 182 */         setSession(message.getSequenceId(), request);
/*     */       }
/* 184 */       exposeSequenceDataToUser(message);
/*     */       
/* 186 */       this.rc.destinationMessageHandler.processAcknowledgements(message.getAcknowledgementData());
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
/*     */     }
/* 206 */     catch (AbstractSoapFaultException ex) {
/* 207 */       LOGGER.logException((Throwable)ex, PROTOCOL_FAULT_LOGGING_LEVEL);
/* 208 */       return doReturnWith(ex.toResponse(this.rc, request));
/* 209 */     } catch (RxRuntimeException ex) {
/* 210 */       LOGGER.logSevereException((Throwable)ex);
/* 211 */       return doThrow((Throwable)ex);
/*     */     } finally {
/* 213 */       HaContext.clear();
/* 214 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 220 */     LOGGER.entering();
/*     */     try {
/* 222 */       return super.processResponse(response);
/*     */     } finally {
/* 224 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable throwable) {
/* 230 */     LOGGER.entering();
/*     */     try {
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
/* 242 */       this.rc.close();
/*     */       
/* 244 */       SessionManager.removeSessionManager(this.endpoint);
/*     */     } finally {
/* 246 */       super.preDestroy();
/* 247 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private NextAction handleDuplicateMessageException(JaxwsApplicationMessage message, Packet request) throws UnknownSequenceException, RxRuntimeException {
/* 253 */     Sequence inboundSequence = this.rc.sequenceManager().getInboundSequence(message.getSequenceId());
/* 254 */     if (inboundSequence.isFailedOver(message.getMessageNumber())) {
/* 255 */       synchronized (message.getCorrelationId()) {
/*     */ 
/*     */         
/* 258 */         this.rc.suspendedFiberStorage.register(message.getCorrelationId(), Fiber.current());
/* 259 */         this.rc.destinationMessageHandler.putToDeliveryQueue(message);
/*     */         
/* 261 */         return doSuspend();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 266 */     Sequence outboundSequence = this.rc.sequenceManager().getBoundSequence(message.getSequenceId());
/* 267 */     if (outboundSequence != null) {
/* 268 */       ApplicationMessage _responseMessage = outboundSequence.retrieveMessage(message.getCorrelationId());
/* 269 */       if (_responseMessage == null) {
/* 270 */         return doReturnWith(this.rc.protocolHandler.createEmptyAcknowledgementResponse(this.rc.destinationMessageHandler.getAcknowledgementData(message.getSequenceId()), request));
/*     */       }
/*     */ 
/*     */       
/* 274 */       if ((this.rc.configuration.getRmFeature().isPersistenceEnabled() || HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) && 
/* 275 */         _responseMessage instanceof JaxwsApplicationMessage) {
/* 276 */         JaxwsApplicationMessage jaxwsAppMsg = (JaxwsApplicationMessage)_responseMessage;
/* 277 */         if (jaxwsAppMsg.getPacket() == null)
/*     */         {
/*     */           
/* 280 */           jaxwsAppMsg.setPacket(this.rc.communicator.createEmptyResponsePacket(request, jaxwsAppMsg.getWsaAction()));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 285 */       Fiber oldRegisteredFiber = this.rc.suspendedFiberStorage.register(_responseMessage.getCorrelationId(), Fiber.current());
/* 286 */       if (oldRegisteredFiber != null) {
/* 287 */         oldRegisteredFiber.resume(this.rc.protocolHandler.createEmptyAcknowledgementResponse(this.rc.destinationMessageHandler.getAcknowledgementData(message.getSequenceId()), request));
/*     */       }
/*     */ 
/*     */       
/* 291 */       this.rc.sourceMessageHandler.putToDeliveryQueue(_responseMessage);
/* 292 */       return doSuspend();
/*     */     } 
/* 294 */     return doReturnWith(this.rc.protocolHandler.createEmptyAcknowledgementResponse(this.rc.destinationMessageHandler.getAcknowledgementData(message.getSequenceId()), request));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processProtocolMessage(Packet request, String wsaAction) throws AbstractSoapFaultException {
/* 301 */     if (this.rc.rmVersion.protocolVersion.createSequenceAction.equals(wsaAction))
/* 302 */       return handleCreateSequenceAction(request); 
/* 303 */     if (this.rc.rmVersion.protocolVersion.closeSequenceAction.equals(wsaAction))
/* 304 */       return handleCloseSequenceAction(request); 
/* 305 */     if (this.rc.rmVersion.protocolVersion.terminateSequenceAction.equals(wsaAction))
/* 306 */       return handleTerminateSequenceAction(request); 
/* 307 */     if (this.rc.rmVersion.protocolVersion.ackRequestedAction.equals(wsaAction))
/* 308 */       return handleAckRequestedAction(request); 
/* 309 */     if (this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction.equals(wsaAction))
/* 310 */       return handleSequenceAcknowledgementAction(request); 
/* 311 */     if (this.rc.rmVersion.protocolVersion.terminateSequenceResponseAction.equals(wsaAction)) {
/* 312 */       return handleTerminateSequenceResponseAction(request);
/*     */     }
/* 314 */     throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1134_UNSUPPORTED_PROTOCOL_MESSAGE(wsaAction)));
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet handleCreateSequenceAction(Packet request) throws CreateSequenceRefusedFault {
/* 319 */     CreateSequenceData requestData = this.rc.protocolHandler.toCreateSequenceData(request);
/*     */     
/* 321 */     EndpointReference requestDestination = null;
/* 322 */     if (requestData.getOfferedSequenceId() != null && this.rc.configuration.requestResponseOperationsDetected()) {
/*     */ 
/*     */       
/* 325 */       if (this.rc.sequenceManager().isValid(requestData.getOfferedSequenceId()))
/*     */       {
/* 327 */         throw new CreateSequenceRefusedFault(LocalizationMessages.WSRM_1137_OFFERED_ID_ALREADY_IN_USE(requestData.getOfferedSequenceId()), AbstractSoapFaultException.Code.Sender);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 332 */       String wsaTo = this.rc.communicator.getWsaTo(request);
/*     */       try {
/* 334 */         requestDestination = (new WSEndpointReference(new URI(wsaTo), this.rc.addressingVersion)).toSpec();
/* 335 */       } catch (URISyntaxException e) {
/* 336 */         throw new CreateSequenceRefusedFault(LocalizationMessages.WSRM_1129_INVALID_VALUE_OF_MESSAGE_HEADER("To", "CreateSequence", wsaTo), AbstractSoapFaultException.Code.Sender, e);
/*     */ 
/*     */       
/*     */       }
/* 340 */       catch (NullPointerException e) {
/* 341 */         throw new CreateSequenceRefusedFault(LocalizationMessages.WSRM_1130_MISSING_MESSAGE_HEADER("To", "CreateSequence", wsaTo), AbstractSoapFaultException.Code.Sender, e);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     String receivedSctId = null;
/* 349 */     if (requestData.getStrType() != null) {
/*     */ 
/*     */ 
/*     */       
/* 353 */       String activeSctId = getSecurityContextTokenId(request);
/* 354 */       if (activeSctId == null) {
/* 355 */         throw new CreateSequenceRefusedFault(LocalizationMessages.WSRM_1133_NO_SECURITY_TOKEN_IN_REQUEST_PACKET(), AbstractSoapFaultException.Code.Sender);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 360 */         receivedSctId = Utilities.extractSecurityContextTokenId(requestData.getStrType());
/* 361 */       } catch (RxException ex) {
/* 362 */         throw new CreateSequenceRefusedFault(ex.getMessage(), AbstractSoapFaultException.Code.Sender);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 367 */       if (!activeSctId.equals(receivedSctId)) {
/* 368 */         throw new CreateSequenceRefusedFault(LocalizationMessages.WSRM_1131_SECURITY_TOKEN_AUTHORIZATION_ERROR(receivedSctId, activeSctId), AbstractSoapFaultException.Code.Sender);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     Sequence inboundSequence = this.rc.sequenceManager().createInboundSequence(this.rc.sequenceManager().generateSequenceUID(), receivedSctId, calculateSequenceExpirationTime(requestData.getDuration()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     CreateSequenceResponseData.Builder responseBuilder = CreateSequenceResponseData.getBuilder(inboundSequence.getId());
/*     */ 
/*     */     
/* 383 */     if (requestData.getOfferedSequenceId() != null && this.rc.configuration.requestResponseOperationsDetected()) {
/*     */ 
/*     */       
/* 386 */       Sequence outboundSequence = this.rc.sequenceManager().createOutboundSequence(requestData.getOfferedSequenceId(), receivedSctId, calculateSequenceExpirationTime(requestData.getOfferedSequenceExpiry()));
/*     */ 
/*     */ 
/*     */       
/* 390 */       this.rc.sequenceManager().bindSequences(inboundSequence.getId(), outboundSequence.getId());
/* 391 */       this.rc.sequenceManager().bindSequences(outboundSequence.getId(), inboundSequence.getId());
/*     */       
/* 393 */       responseBuilder.acceptedSequenceAcksTo(requestDestination);
/*     */     } 
/*     */     
/* 396 */     if (!hasSession(request)) {
/* 397 */       Utilities.startSession(request.endpoint, inboundSequence.getId());
/*     */     }
/*     */     
/* 400 */     return this.rc.protocolHandler.toPacket(responseBuilder.build(), request, false);
/*     */   }
/*     */   
/*     */   private Packet handleCloseSequenceAction(Packet request) {
/* 404 */     CloseSequenceData requestData = this.rc.protocolHandler.toCloseSequenceData(request);
/*     */     
/* 406 */     this.rc.destinationMessageHandler.processAcknowledgements(requestData.getAcknowledgementData());
/*     */     
/* 408 */     Sequence inboundSequence = this.rc.sequenceManager().getInboundSequence(requestData.getSequenceId());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 413 */     String boundSequenceId = this.rc.getBoundSequenceId(inboundSequence.getId());
/*     */     
/*     */     try {
/* 416 */       this.rc.sequenceManager().closeSequence(inboundSequence.getId());
/*     */       
/* 418 */       CloseSequenceResponseData.Builder responseBuilder = CloseSequenceResponseData.getBuilder(inboundSequence.getId());
/*     */       
/* 420 */       AcknowledgementData.Builder ackDataBuilder = AcknowledgementData.getBuilder(this.rc.destinationMessageHandler.getAcknowledgementData(inboundSequence.getId()));
/*     */       
/* 422 */       ackDataBuilder.acknowledgements(inboundSequence.getId(), inboundSequence.getAcknowledgedMessageNumbers(), true);
/* 423 */       inboundSequence.clearAckRequestedFlag();
/*     */       
/* 425 */       responseBuilder.acknowledgementData(ackDataBuilder.build());
/*     */       
/* 427 */       return this.rc.protocolHandler.toPacket(responseBuilder.build(), request, false);
/*     */     } finally {
/* 429 */       if (boundSequenceId != null) {
/* 430 */         this.rc.sequenceManager().closeSequence(boundSequenceId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet handleTerminateSequenceAction(Packet request) {
/* 436 */     TerminateSequenceData requestData = this.rc.protocolHandler.toTerminateSequenceData(request);
/*     */     
/* 438 */     this.rc.destinationMessageHandler.processAcknowledgements(requestData.getAcknowledgementData());
/*     */     
/* 440 */     Sequence inboundSequence = this.rc.sequenceManager().getInboundSequence(requestData.getSequenceId());
/* 441 */     Sequence outboundSeqence = this.rc.sequenceManager().getBoundSequence(requestData.getSequenceId());
/*     */     try {
/* 443 */       TerminateSequenceResponseData.Builder responseBuilder = TerminateSequenceResponseData.getBuilder(inboundSequence.getId());
/* 444 */       responseBuilder.acknowledgementData(this.rc.destinationMessageHandler.getAcknowledgementData(inboundSequence.getId()));
/*     */       
/* 446 */       if (outboundSeqence != null) {
/* 447 */         responseBuilder.boundSequenceData(outboundSeqence.getId(), outboundSeqence.getLastMessageNumber());
/*     */       }
/*     */       
/* 450 */       return this.rc.protocolHandler.toPacket(responseBuilder.build(), request, false);
/*     */     } finally {
/* 452 */       Utilities.endSessionIfExists(request.endpoint, inboundSequence.getId());
/*     */       try {
/* 454 */         this.rc.sequenceManager().terminateSequence(inboundSequence.getId());
/*     */       } finally {
/* 456 */         if (outboundSeqence != null) {
/* 457 */           this.rc.sequenceManager().terminateSequence(outboundSeqence.getId());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Packet handleTerminateSequenceResponseAction(Packet request) {
/* 464 */     TerminateSequenceResponseData data = this.rc.protocolHandler.toTerminateSequenceResponseData(request);
/*     */     
/* 466 */     this.rc.destinationMessageHandler.processAcknowledgements(data.getAcknowledgementData());
/*     */ 
/*     */ 
/*     */     
/* 470 */     request.transportBackChannel.close();
/* 471 */     return this.rc.communicator.createNullResponsePacket(request);
/*     */   }
/*     */   
/*     */   private Packet handleSequenceAcknowledgementAction(Packet request) {
/* 475 */     AcknowledgementData ackData = this.rc.protocolHandler.getAcknowledgementData(request.getMessage());
/* 476 */     this.rc.destinationMessageHandler.processAcknowledgements(ackData);
/*     */     
/* 478 */     request.transportBackChannel.close();
/* 479 */     return this.rc.communicator.createNullResponsePacket(request);
/*     */   }
/*     */   
/*     */   private Packet handleAckRequestedAction(Packet request) {
/* 483 */     AcknowledgementData ackData = this.rc.protocolHandler.getAcknowledgementData(request.getMessage());
/* 484 */     this.rc.destinationMessageHandler.processAcknowledgements(ackData);
/*     */     
/* 486 */     return this.rc.protocolHandler.createEmptyAcknowledgementResponse(this.rc.destinationMessageHandler.getAcknowledgementData(ackData.getAckReqestedSequenceId()), request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Session getSession(Packet packet) {
/* 493 */     String sessionId = (String)packet.invocationProperties.get("com.sun.xml.ws.sessionid");
/* 494 */     if (sessionId == null) {
/* 495 */       return null;
/*     */     }
/*     */     
/* 498 */     return SessionManager.getSessionManager(packet.endpoint, null).getSession(sessionId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasSession(Packet packet) {
/* 505 */     return (getSession(packet) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSession(String sessionId, Packet packet) {
/* 512 */     packet.invocationProperties.put("com.sun.xml.ws.sessionid", sessionId);
/*     */     
/* 514 */     Session session = SessionManager.getSessionManager(packet.endpoint, null).getSession(sessionId);
/*     */     
/* 516 */     if (session == null) {
/* 517 */       session = Utilities.startSession(packet.endpoint, sessionId);
/*     */     }
/*     */     
/* 520 */     packet.invocationProperties.put("com.sun.xml.ws.session", session.getUserData());
/*     */   }
/*     */   
/*     */   private void exposeSequenceDataToUser(JaxwsApplicationMessage message) {
/* 524 */     (message.getPacket()).invocationProperties.put("com.sun.xml.ws.sequence", message.getSequenceId());
/* 525 */     (message.getPacket()).invocationProperties.put("com.sun.xml.ws.messagenumber", Long.valueOf(message.getMessageNumber()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSecurityContextTokenId(Packet packet) {
/* 532 */     Session session = getSession(packet);
/* 533 */     return (session != null) ? session.getSecurityInfo().getIdentifier() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateSecurityContextTokenId(String expectedSctId, Packet packet) throws RmSecurityException {
/* 545 */     String actualSctId = getSecurityContextTokenId(packet);
/* 546 */     boolean isValid = (expectedSctId != null) ? expectedSctId.equals(actualSctId) : ((actualSctId == null));
/*     */     
/* 548 */     if (!isValid) {
/* 549 */       throw new RmSecurityException(LocalizationMessages.WSRM_1131_SECURITY_TOKEN_AUTHORIZATION_ERROR(actualSctId, expectedSctId));
/*     */     }
/*     */   }
/*     */   
/*     */   private long calculateSequenceExpirationTime(long expiryDuration) {
/* 554 */     if (expiryDuration == -1L) {
/* 555 */       return -1L;
/*     */     }
/* 557 */     return expiryDuration + this.rc.sequenceManager().currentTimeInMillis();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */