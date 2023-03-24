/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.SequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
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
/*     */ class SourceMessageHandler
/*     */   implements MessageHandler
/*     */ {
/*  62 */   private static final Logger LOGGER = Logger.getLogger(SourceMessageHandler.class);
/*     */   
/*     */   private volatile SequenceManager sequenceManager;
/*     */   
/*     */   SourceMessageHandler(@Nullable SequenceManager sequenceManager) {
/*  67 */     this.sequenceManager = sequenceManager;
/*     */   }
/*     */   
/*     */   void setSequenceManager(SequenceManager sequenceManager) {
/*  71 */     this.sequenceManager = sequenceManager;
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
/*     */   public void registerMessage(@NotNull ApplicationMessage outMessage, @NotNull String outboundSequenceId) throws DuplicateMessageRegistrationException, UnknownSequenceException {
/*  83 */     assert this.sequenceManager != null;
/*  84 */     assert outMessage != null;
/*  85 */     assert outboundSequenceId != null;
/*     */     
/*  87 */     Sequence outboundSequence = this.sequenceManager.getOutboundSequence(outboundSequenceId);
/*  88 */     outboundSequence.registerMessage(outMessage, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attachAcknowledgementInfo(@NotNull ApplicationMessage outMessage) throws UnknownSequenceException {
/*  99 */     assert this.sequenceManager != null;
/* 100 */     assert outMessage != null;
/* 101 */     assert outMessage.getSequenceId() != null;
/*     */ 
/*     */     
/* 104 */     outMessage.setAcknowledgementData(getAcknowledgementData(outMessage.getSequenceId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AcknowledgementData getAcknowledgementData(String outboundSequenceId) throws UnknownSequenceException {
/* 115 */     assert this.sequenceManager != null;
/*     */     
/* 117 */     AcknowledgementData.Builder ackDataBuilder = AcknowledgementData.getBuilder();
/* 118 */     Sequence inboundSequence = this.sequenceManager.getBoundSequence(outboundSequenceId);
/* 119 */     if (inboundSequence != null) {
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
/* 143 */       ackDataBuilder.acknowledgements(inboundSequence.getId(), inboundSequence.getAcknowledgedMessageNumbers(), inboundSequence.isClosed());
/* 144 */       inboundSequence.clearAckRequestedFlag();
/*     */     } 
/*     */     
/* 147 */     Sequence outboundSequence = this.sequenceManager.getOutboundSequence(outboundSequenceId);
/* 148 */     if (outboundSequence.hasUnacknowledgedMessages()) {
/* 149 */       ackDataBuilder.ackReqestedSequenceId(outboundSequenceId);
/* 150 */       outboundSequence.updateLastAcknowledgementRequestTime();
/*     */     } 
/* 152 */     AcknowledgementData acknowledgementData = ackDataBuilder.build();
/* 153 */     return acknowledgementData;
/*     */   }
/*     */   
/*     */   public void putToDeliveryQueue(ApplicationMessage message) throws RxRuntimeException {
/* 157 */     assert this.sequenceManager != null;
/*     */     
/* 159 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 160 */       LOGGER.finer(String.format("Putting a message with number [ %d ] to the delivery queue of a sequence [ %s ]", new Object[] { Long.valueOf(message.getMessageNumber()), message.getSequenceId() }));
/*     */     }
/* 162 */     this.sequenceManager.getOutboundSequence(message.getSequenceId()).getDeliveryQueue().put(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\SourceMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */