/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.commons.DelayedTaskManager;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
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
/*     */ public class ClientAckRequesterTask
/*     */   implements DelayedTaskManager.DelayedTask
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(ClientAckRequesterTask.class);
/*     */   
/*     */   private final RuntimeContext rc;
/*     */   private final String outboundSequenceId;
/*     */   private final long acknowledgementRequestInterval;
/*     */   
/*     */   public ClientAckRequesterTask(RuntimeContext rc, String outboundSequenceId) {
/*  65 */     this.rc = rc;
/*  66 */     this.acknowledgementRequestInterval = rc.configuration.getRmFeature().getAckRequestTransmissionInterval();
/*  67 */     this.outboundSequenceId = outboundSequenceId;
/*     */   }
/*     */   
/*     */   public void run(DelayedTaskManager manager) {
/*  71 */     LOGGER.entering(new Object[] { this.outboundSequenceId });
/*     */     try {
/*  73 */       if (this.rc.communicator.isClosed()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  78 */       if (this.rc.sequenceManager().isValid(this.outboundSequenceId)) {
/*  79 */         Sequence sequence = this.rc.sequenceManager().getOutboundSequence(this.outboundSequenceId);
/*  80 */         if (!sequence.isClosed() && !sequence.isExpired()) {
/*     */           try {
/*  82 */             if (sequence.isStandaloneAcknowledgementRequestSchedulable(this.acknowledgementRequestInterval)) {
/*  83 */               requestAcknowledgement();
/*  84 */               sequence.updateLastAcknowledgementRequestTime();
/*     */             } 
/*     */           } finally {
/*  87 */             LOGGER.finer(String.format("Scheduling next run for an outbound sequence with id [ %s ]", new Object[] { this.outboundSequenceId }));
/*  88 */             manager.register(this, getExecutionDelay(), getExecutionDelayTimeUnit());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } finally {
/*     */       
/*  94 */       LOGGER.exiting(this.outboundSequenceId);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void requestAcknowledgement() {
/*  99 */     Packet request = this.rc.communicator.createEmptyRequestPacket(this.rc.rmVersion.protocolVersion.ackRequestedAction, true);
/* 100 */     JaxwsApplicationMessage requestMessage = new JaxwsApplicationMessage(request, request.getMessage().getID(this.rc.addressingVersion, this.rc.soapVersion));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     requestMessage.setSequenceData(this.outboundSequenceId, 0L);
/*     */     
/* 107 */     this.rc.sourceMessageHandler.attachAcknowledgementInfo(requestMessage);
/* 108 */     this.rc.protocolHandler.appendAcknowledgementHeaders(requestMessage.getPacket(), requestMessage.getAcknowledgementData());
/*     */     
/* 110 */     this.rc.communicator.sendAsync(request, new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(Packet response) {
/* 113 */             if (response == null || response.getMessage() == null) {
/* 114 */               ClientAckRequesterTask.LOGGER.warning(LocalizationMessages.WSRM_1108_NULL_RESPONSE_FOR_ACK_REQUEST());
/*     */               
/*     */               return;
/*     */             } 
/*     */             try {
/* 119 */               if (ClientAckRequesterTask.this.rc.protocolHandler.containsProtocolMessage(response)) {
/* 120 */                 ClientAckRequesterTask.LOGGER.finer("Processing RM protocol response message.");
/* 121 */                 JaxwsApplicationMessage message = new JaxwsApplicationMessage(response, "");
/* 122 */                 ClientAckRequesterTask.this.rc.protocolHandler.loadAcknowledgementData(message, message.getJaxwsMessage());
/*     */                 
/* 124 */                 ClientAckRequesterTask.this.rc.destinationMessageHandler.processAcknowledgements(message.getAcknowledgementData());
/*     */               } else {
/* 126 */                 ClientAckRequesterTask.LOGGER.severe(LocalizationMessages.WSRM_1120_RESPONSE_NOT_IDENTIFIED_AS_PROTOCOL_MESSAGE());
/*     */               } 
/*     */               
/* 129 */               if (response.getMessage().isFault()) {
/* 130 */                 ClientAckRequesterTask.LOGGER.warning(LocalizationMessages.WSRM_1109_SOAP_FAULT_RESPONSE_FOR_ACK_REQUEST());
/*     */               }
/*     */             } finally {
/* 133 */               response.getMessage().consume();
/*     */             } 
/*     */           }
/*     */           
/*     */           public void onCompletion(Throwable error) {
/* 138 */             ClientAckRequesterTask.LOGGER.warning(LocalizationMessages.WSRM_1127_UNEXPECTED_EXCEPTION_WHEN_SENDING_ACK_REQUEST(), error);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public long getExecutionDelay() {
/* 144 */     return this.acknowledgementRequestInterval;
/*     */   }
/*     */   
/*     */   public TimeUnit getExecutionDelayTimeUnit() {
/* 148 */     return TimeUnit.MILLISECONDS;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 152 */     return "client acknowledgement requester task";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ClientAckRequesterTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */