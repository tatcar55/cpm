/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.rx.RxException;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.Postman;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.util.AbstractResponseHandler;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClientSourceDeliveryCallback
/*     */   implements Postman.Callback
/*     */ {
/*  57 */   private static final Logger LOGGER = Logger.getLogger(ClientSourceDeliveryCallback.class);
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   private static class OneWayMepCallbackHandler extends AbstractResponseHandler implements Fiber.CompletionCallback {
/*     */     private final RuntimeContext rc;
/*     */     private final JaxwsApplicationMessage request;
/*     */     
/*     */     public OneWayMepCallbackHandler(JaxwsApplicationMessage request, RuntimeContext rc) {
/*  65 */       super(rc.suspendedFiberStorage, request.getCorrelationId());
/*  66 */       this.request = request;
/*  67 */       this.rc = rc;
/*     */     }
/*     */     
/*     */     public void onCompletion(Packet response) {
/*     */       try {
/*  72 */         HaContext.initFrom(response);
/*     */         
/*  74 */         if (response.getMessage() != null) {
/*  75 */           JaxwsApplicationMessage message = new JaxwsApplicationMessage(response, getCorrelationId());
/*  76 */           this.rc.protocolHandler.loadSequenceHeaderData(message, message.getJaxwsMessage());
/*  77 */           this.rc.protocolHandler.loadAcknowledgementData(message, message.getJaxwsMessage());
/*     */           
/*  79 */           this.rc.destinationMessageHandler.processAcknowledgements(message.getAcknowledgementData());
/*     */           
/*  81 */           if ((this.rc.configuration.getRuntimeVersion()).protocolVersion.isFault(message.getWsaAction()));
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*  86 */         resumeParentFiber(response);
/*  87 */       } catch (RxRuntimeException ex) {
/*  88 */         onCompletion((Throwable)ex);
/*     */       } finally {
/*  90 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onCompletion(Throwable error) {
/*  95 */       if (!Utilities.isResendPossible(error)) {
/*  96 */         resumeParentFiber(error);
/*     */         
/*     */         return;
/*     */       } 
/* 100 */       int nextResendCount = this.request.getNextResendCount();
/* 101 */       if (!this.rc.configuration.getRmFeature().canRetransmitMessage(nextResendCount)) {
/* 102 */         resumeParentFiber(error);
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/* 107 */         HaContext.initFrom(this.request.getPacket());
/*     */         
/* 109 */         RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(nextResendCount, this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.sourceMessageHandler);
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 115 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ReqRespMepCallbackHandler
/*     */     extends AbstractResponseHandler implements Fiber.CompletionCallback {
/*     */     private final JaxwsApplicationMessage request;
/*     */     private final RuntimeContext rc;
/*     */     
/*     */     public ReqRespMepCallbackHandler(JaxwsApplicationMessage request, RuntimeContext rc) {
/* 126 */       super(rc.suspendedFiberStorage, request.getCorrelationId());
/* 127 */       this.request = request;
/* 128 */       this.rc = rc;
/*     */     }
/*     */     
/*     */     public void onCompletion(Packet response) {
/*     */       try {
/* 133 */         HaContext.initFrom(response);
/*     */         
/* 135 */         if (response.getMessage() != null) {
/*     */           
/* 137 */           JaxwsApplicationMessage message = new JaxwsApplicationMessage(response, getCorrelationId());
/* 138 */           this.rc.protocolHandler.loadSequenceHeaderData(message, message.getJaxwsMessage());
/* 139 */           this.rc.protocolHandler.loadAcknowledgementData(message, message.getJaxwsMessage());
/*     */           
/* 141 */           this.rc.destinationMessageHandler.processAcknowledgements(message.getAcknowledgementData());
/*     */           
/* 143 */           if ((this.rc.configuration.getRuntimeVersion()).protocolVersion.isFault(message.getWsaAction()));
/*     */ 
/*     */ 
/*     */           
/* 147 */           if (message.getSequenceId() != null) {
/* 148 */             this.rc.destinationMessageHandler.registerMessage(message);
/* 149 */             this.rc.destinationMessageHandler.putToDeliveryQueue(message);
/*     */           } else {
/*     */             
/* 152 */             resumeParentFiber(response);
/*     */           } 
/*     */         } else {
/*     */           
/* 156 */           int nextResendCount = this.request.getNextResendCount();
/* 157 */           if (!this.rc.configuration.getRmFeature().canRetransmitMessage(nextResendCount)) {
/* 158 */             resumeParentFiber((Throwable)new RxRuntimeException(LocalizationMessages.WSRM_1159_MAX_MESSAGE_RESEND_ATTEMPTS_REACHED()));
/*     */             
/*     */             return;
/*     */           } 
/* 162 */           RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(nextResendCount, this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.sourceMessageHandler);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 168 */       catch (RxRuntimeException ex) {
/* 169 */         onCompletion((Throwable)ex);
/* 170 */       } catch (RxException ex) {
/* 171 */         onCompletion((Throwable)ex);
/*     */       } finally {
/* 173 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onCompletion(Throwable error) {
/* 178 */       if (Utilities.isResendPossible(error)) {
/* 179 */         int nextResendCount = this.request.getNextResendCount();
/* 180 */         if (!this.rc.configuration.getRmFeature().canRetransmitMessage(nextResendCount)) {
/* 181 */           resumeParentFiber((Throwable)new RxRuntimeException(LocalizationMessages.WSRM_1159_MAX_MESSAGE_RESEND_ATTEMPTS_REACHED()));
/*     */           
/*     */           return;
/*     */         } 
/*     */         try {
/* 186 */           HaContext.initFrom(this.request.getPacket());
/*     */           
/* 188 */           RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(nextResendCount, this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.sourceMessageHandler);
/*     */ 
/*     */         
/*     */         }
/*     */         finally {
/*     */ 
/*     */           
/* 195 */           HaContext.clear();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 200 */         resumeParentFiber(error);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class AmbiguousMepCallbackHandler
/*     */     extends AbstractResponseHandler implements Fiber.CompletionCallback {
/*     */     private final JaxwsApplicationMessage request;
/*     */     private final RuntimeContext rc;
/*     */     
/*     */     public AmbiguousMepCallbackHandler(JaxwsApplicationMessage request, RuntimeContext rc) {
/* 211 */       super(rc.suspendedFiberStorage, request.getCorrelationId());
/* 212 */       this.request = request;
/* 213 */       this.rc = rc;
/*     */     }
/*     */     
/*     */     public void onCompletion(Packet response) {
/*     */       try {
/* 218 */         HaContext.initFrom(response);
/*     */         
/* 220 */         if (response.getMessage() != null) {
/*     */           
/* 222 */           JaxwsApplicationMessage message = new JaxwsApplicationMessage(response, getCorrelationId());
/* 223 */           this.rc.protocolHandler.loadSequenceHeaderData(message, message.getJaxwsMessage());
/* 224 */           this.rc.protocolHandler.loadAcknowledgementData(message, message.getJaxwsMessage());
/*     */           
/* 226 */           this.rc.destinationMessageHandler.processAcknowledgements(message.getAcknowledgementData());
/*     */           
/* 228 */           if (message.getSequenceId() != null) {
/*     */             try {
/* 230 */               this.rc.destinationMessageHandler.registerMessage(message);
/* 231 */               this.rc.destinationMessageHandler.putToDeliveryQueue(message);
/*     */               return;
/* 233 */             } catch (DuplicateMessageRegistrationException ex) {
/* 234 */               onCompletion((Throwable)ex);
/*     */               
/*     */               return;
/*     */             } 
/*     */           }
/*     */         } 
/* 240 */         resumeParentFiber(response);
/*     */       }
/* 242 */       catch (RxRuntimeException ex) {
/* 243 */         onCompletion((Throwable)ex);
/*     */       } finally {
/* 245 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onCompletion(Throwable error) {
/* 250 */       if (Utilities.isResendPossible(error)) {
/* 251 */         RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(this.request.getNextResendCount(), this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.sourceMessageHandler);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 257 */         resumeParentFiber(error);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientSourceDeliveryCallback(RuntimeContext rc) {
/* 265 */     this.rc = rc;
/*     */   }
/*     */   
/*     */   public void deliver(ApplicationMessage message) {
/* 269 */     if (message instanceof JaxwsApplicationMessage) {
/* 270 */       deliver(JaxwsApplicationMessage.class.cast(message));
/*     */     } else {
/* 272 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1141_UNEXPECTED_MESSAGE_CLASS(message.getClass().getName(), JaxwsApplicationMessage.class.getName())));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void deliver(JaxwsApplicationMessage message) {
/* 279 */     LOGGER.entering(new Object[] { message }); try {
/*     */       Fiber.CompletionCallback responseCallback;
/* 281 */       this.rc.sourceMessageHandler.attachAcknowledgementInfo(message);
/*     */       
/* 283 */       Packet outboundPacketCopy = message.getPacket().copy(true);
/*     */       
/* 285 */       this.rc.protocolHandler.appendSequenceHeader(outboundPacketCopy.getMessage(), message);
/* 286 */       this.rc.protocolHandler.appendAcknowledgementHeaders(outboundPacketCopy, message.getAcknowledgementData());
/*     */ 
/*     */       
/* 289 */       if (outboundPacketCopy.expectReply == null) {
/* 290 */         responseCallback = new AmbiguousMepCallbackHandler(message, this.rc);
/* 291 */       } else if (outboundPacketCopy.expectReply.booleanValue()) {
/* 292 */         responseCallback = new ReqRespMepCallbackHandler(message, this.rc);
/*     */       } else {
/* 294 */         responseCallback = new OneWayMepCallbackHandler(message, this.rc);
/*     */       } 
/*     */       
/* 297 */       if (LOGGER.isLoggable(Level.FINEST)) {
/* 298 */         LOGGER.finer("Selected Response callback class: " + responseCallback.getClass().getName());
/*     */       }
/*     */       
/* 301 */       this.rc.communicator.sendAsync(outboundPacketCopy, responseCallback);
/*     */     } finally {
/* 303 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ClientSourceDeliveryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */