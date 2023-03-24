/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.faults.WsrmRequiredException;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DestinationMessageHandler
/*     */   implements MessageHandler
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(DestinationMessageHandler.class);
/*     */   
/*     */   private volatile SequenceManager sequenceManager;
/*     */   
/*     */   DestinationMessageHandler(@Nullable SequenceManager sequenceManager) {
/*  71 */     this.sequenceManager = sequenceManager;
/*     */   }
/*     */   
/*     */   void setSequenceManager(SequenceManager sequenceManager) {
/*  75 */     this.sequenceManager = sequenceManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerMessage(@NotNull ApplicationMessage inMessage) throws DuplicateMessageRegistrationException, UnknownSequenceException, WsrmRequiredException {
/*  86 */     assert this.sequenceManager != null;
/*  87 */     assert inMessage != null;
/*     */     
/*  89 */     String inboundSequenceId = inMessage.getSequenceId();
/*  90 */     if (inboundSequenceId == null) {
/*  91 */       throw new WsrmRequiredException();
/*     */     }
/*  93 */     Sequence inboundSequence = this.sequenceManager.getInboundSequence(inboundSequenceId);
/*     */ 
/*     */ 
/*     */     
/*  97 */     inboundSequence.registerMessage(inMessage, true);
/*     */     
/*  99 */     inboundSequence.setAckRequestedFlag();
/*     */   }
/*     */   
/*     */   public void processAcknowledgements(@Nullable AcknowledgementData acknowledgementData) throws UnknownSequenceException {
/* 103 */     assert this.sequenceManager != null;
/*     */     
/* 105 */     if (acknowledgementData == null) {
/*     */       return;
/*     */     }
/*     */     
/* 109 */     if (acknowledgementData.getAcknowledgedSequenceId() != null) {
/* 110 */       List<Sequence.AckRange> acknowledgedRanges = acknowledgementData.getAcknowledgedRanges();
/* 111 */       if (!acknowledgedRanges.isEmpty()) {
/* 112 */         Sequence outboundSequence = this.sequenceManager.getOutboundSequence(acknowledgementData.getAcknowledgedSequenceId());
/* 113 */         if (!outboundSequence.isClosed()) {
/* 114 */           outboundSequence.acknowledgeMessageNumbers(acknowledgedRanges);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     if (acknowledgementData.getAckReqestedSequenceId() != null) {
/* 120 */       Sequence inboundSequence = this.sequenceManager.getInboundSequence(acknowledgementData.getAckReqestedSequenceId());
/* 121 */       inboundSequence.setAckRequestedFlag();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AcknowledgementData getAcknowledgementData(String inboundSequenceId) throws UnknownSequenceException {
/* 133 */     assert this.sequenceManager != null;
/*     */     
/* 135 */     AcknowledgementData.Builder ackDataBuilder = AcknowledgementData.getBuilder();
/* 136 */     Sequence inboundSequence = this.sequenceManager.getInboundSequence(inboundSequenceId);
/* 137 */     if (inboundSequence.isAckRequested() || inboundSequence.isClosed()) {
/* 138 */       ackDataBuilder.acknowledgements(inboundSequence.getId(), inboundSequence.getAcknowledgedMessageNumbers(), inboundSequence.isClosed());
/* 139 */       inboundSequence.clearAckRequestedFlag();
/*     */     } 
/*     */ 
/*     */     
/* 143 */     Sequence outboundSequence = this.sequenceManager.getBoundSequence(inboundSequenceId);
/* 144 */     if (outboundSequence != null && outboundSequence.hasUnacknowledgedMessages()) {
/* 145 */       ackDataBuilder.ackReqestedSequenceId(outboundSequence.getId());
/* 146 */       outboundSequence.updateLastAcknowledgementRequestTime();
/*     */     } 
/*     */     
/* 149 */     return ackDataBuilder.build();
/*     */   }
/*     */   
/*     */   public void acknowledgeApplicationLayerDelivery(ApplicationMessage inMessage) throws UnknownSequenceException {
/* 153 */     assert this.sequenceManager != null;
/*     */     
/* 155 */     this.sequenceManager.getInboundSequence(inMessage.getSequenceId()).acknowledgeMessageNumber(inMessage.getMessageNumber());
/*     */   }
/*     */   
/*     */   public void putToDeliveryQueue(ApplicationMessage message) throws RxRuntimeException, UnknownSequenceException {
/* 159 */     assert this.sequenceManager != null;
/*     */     
/* 161 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 162 */       LOGGER.finer(String.format("Putting a message with number [ %d ] to the delivery queue of a sequence [ %s ]", new Object[] { Long.valueOf(message.getMessageNumber()), message.getSequenceId() }));
/*     */     }
/*     */     
/* 165 */     this.sequenceManager.getInboundSequence(message.getSequenceId()).getDeliveryQueue().put(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\DestinationMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */