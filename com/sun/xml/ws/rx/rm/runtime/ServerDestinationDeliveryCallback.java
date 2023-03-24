/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.Postman;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.util.AbstractResponseHandler;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ServerDestinationDeliveryCallback
/*     */   implements Postman.Callback
/*     */ {
/*     */   private static class ResponseCallbackHandler
/*     */     extends AbstractResponseHandler
/*     */     implements Fiber.CompletionCallback
/*     */   {
/*     */     private static final String RM_ACK_PROPERTY_KEY = "RM_ACK";
/*     */     private final JaxwsApplicationMessage request;
/*     */     private final RuntimeContext rc;
/*     */     
/*     */     public ResponseCallbackHandler(JaxwsApplicationMessage request, RuntimeContext rc) {
/*  79 */       super(rc.suspendedFiberStorage, request.getCorrelationId());
/*  80 */       this.request = request;
/*  81 */       this.rc = rc;
/*     */     }
/*     */     
/*     */     public void onCompletion(Packet response) {
/*     */       try {
/*  86 */         HaContext.initFrom(response);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  93 */         String rmAckPropertyValue = String.class.cast(response.invocationProperties.remove("RM_ACK"));
/*  94 */         if (rmAckPropertyValue == null || Boolean.parseBoolean(rmAckPropertyValue)) {
/*  95 */           this.rc.destinationMessageHandler.acknowledgeApplicationLayerDelivery(this.request);
/*     */         } else {
/*  97 */           ServerDestinationDeliveryCallback.LOGGER.finer(String.format("Value of the '%s' property is '%s'. The request has not been acknowledged.", new Object[] { "RM_ACK", rmAckPropertyValue }));
/*  98 */           RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(this.request.getNextResendCount(), this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.destinationMessageHandler);
/*     */ 
/*     */ 
/*     */           
/*     */           return;
/*     */         } 
/*     */ 
/*     */         
/* 106 */         if (response.getMessage() == null) {
/* 107 */           AcknowledgementData ackData = this.rc.destinationMessageHandler.getAcknowledgementData(this.request.getSequenceId());
/* 108 */           if (ackData.getAckReqestedSequenceId() != null || ackData.containsSequenceAcknowledgementData()) {
/*     */             
/* 110 */             response = this.rc.communicator.setEmptyResponseMessage(response, this.request.getPacket(), this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction);
/* 111 */             this.rc.protocolHandler.appendAcknowledgementHeaders(response, ackData);
/*     */           } 
/*     */           
/* 114 */           resumeParentFiber(response);
/*     */         } else {
/* 116 */           JaxwsApplicationMessage message = new JaxwsApplicationMessage(response, getCorrelationId());
/* 117 */           this.rc.sourceMessageHandler.registerMessage(message, this.rc.getBoundSequenceId(this.request.getSequenceId()));
/* 118 */           this.rc.sourceMessageHandler.putToDeliveryQueue(message);
/*     */         }
/*     */       
/*     */       }
/* 122 */       catch (DuplicateMessageRegistrationException ex) {
/* 123 */         onCompletion((Throwable)ex);
/*     */       } finally {
/* 125 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onCompletion(Throwable error) {
/* 130 */       if (Utilities.isResendPossible(error)) {
/*     */         try {
/* 132 */           HaContext.initFrom(this.request.getPacket());
/*     */           
/* 134 */           RedeliveryTaskExecutor.INSTANCE.register(this.request, this.rc.configuration.getRmFeature().getRetransmissionBackoffAlgorithm().getDelayInMillis(this.request.getNextResendCount(), this.rc.configuration.getRmFeature().getMessageRetransmissionInterval()), TimeUnit.MILLISECONDS, this.rc.destinationMessageHandler);
/*     */ 
/*     */         
/*     */         }
/*     */         finally {
/*     */ 
/*     */           
/* 141 */           HaContext.clear();
/*     */         } 
/*     */       } else {
/* 144 */         resumeParentFiber(error);
/*     */       } 
/*     */     }
/*     */   }
/* 148 */   private static final Logger LOGGER = Logger.getLogger(ServerDestinationDeliveryCallback.class);
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   public ServerDestinationDeliveryCallback(RuntimeContext rc) {
/* 152 */     this.rc = rc;
/*     */   }
/*     */   
/*     */   public void deliver(ApplicationMessage message) {
/* 156 */     if (message instanceof JaxwsApplicationMessage) {
/* 157 */       deliver(JaxwsApplicationMessage.class.cast(message));
/*     */     } else {
/* 159 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1141_UNEXPECTED_MESSAGE_CLASS(message.getClass().getName(), JaxwsApplicationMessage.class.getName())));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void deliver(JaxwsApplicationMessage message) {
/* 166 */     Fiber.CompletionCallback responseCallback = new ResponseCallbackHandler(message, this.rc);
/*     */     
/* 168 */     this.rc.communicator.sendAsync(message.getPacket().copy(true), responseCallback);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ServerDestinationDeliveryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */