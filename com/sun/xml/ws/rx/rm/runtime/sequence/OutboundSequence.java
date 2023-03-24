/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OutboundSequence
/*     */   extends AbstractSequence
/*     */ {
/*     */   public static final long INITIAL_LAST_MESSAGE_ID = 0L;
/*  63 */   private static final Logger LOGGER = Logger.getLogger(OutboundSequence.class);
/*     */   
/*     */   public OutboundSequence(SequenceData data, DeliveryQueueBuilder deliveryQueueBuilder, TimeSynchronizer timeSynchronizer) {
/*  66 */     super(data, deliveryQueueBuilder, timeSynchronizer);
/*     */   }
/*     */   
/*     */   public void registerMessage(ApplicationMessage message, boolean storeMessageFlag) throws DuplicateMessageRegistrationException, AbstractSoapFaultException {
/*  70 */     getState().verifyAcceptingMessageRegistration(getId(), AbstractSoapFaultException.Code.Sender);
/*     */     
/*  72 */     if (message.getSequenceId() != null) {
/*  73 */       throw new IllegalArgumentException(String.format("Cannot register message: Application message has been already registered on a sequence [ %s ].", new Object[] { message.getSequenceId() }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     message.setSequenceData(getId(), generateNextMessageId());
/*  79 */     if (storeMessageFlag) {
/*  80 */       this.data.attachMessageToUnackedMessageNumber(message);
/*     */     }
/*     */   }
/*     */   
/*     */   private long generateNextMessageId() throws MessageNumberRolloverException, IllegalStateException, DuplicateMessageRegistrationException {
/*  85 */     long nextId = this.data.incrementAndGetLastMessageNumber(true);
/*     */     
/*  87 */     if (nextId > Long.MAX_VALUE) {
/*  88 */       throw (MessageNumberRolloverException)LOGGER.logSevereException(new MessageNumberRolloverException(getId(), nextId));
/*     */     }
/*     */     
/*  91 */     return nextId;
/*     */   }
/*     */   
/*     */   public void acknowledgeMessageNumber(long messageId) {
/*  95 */     throw new UnsupportedOperationException(LocalizationMessages.WSRM_1101_UNSUPPORTED_OPERATION(getClass().getName()));
/*     */   }
/*     */   
/*     */   public void acknowledgeMessageNumbers(List<Sequence.AckRange> ranges) throws InvalidAcknowledgementException, AbstractSoapFaultException {
/*  99 */     getState().verifyAcceptingAcknowledgement(getId(), AbstractSoapFaultException.Code.Sender);
/*     */     
/* 101 */     if (ranges == null || ranges.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 105 */     Sequence.AckRange.sort(ranges);
/*     */ 
/*     */     
/* 108 */     Sequence.AckRange lastAckRange = ranges.get(ranges.size() - 1);
/* 109 */     if (this.data.getLastMessageNumber() < lastAckRange.upper) {
/* 110 */       throw new InvalidAcknowledgementException(getId(), lastAckRange.upper, ranges);
/*     */     }
/*     */     
/* 113 */     Collection<Long> unackedMessageNumbers = this.data.getUnackedMessageNumbers();
/* 114 */     if (unackedMessageNumbers.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 120 */     Iterator<Sequence.AckRange> rangeIterator = ranges.iterator();
/* 121 */     Sequence.AckRange currentRange = rangeIterator.next();
/*     */     
/* 123 */     for (Iterator<Long> i$ = unackedMessageNumbers.iterator(); i$.hasNext(); ) { long unackedMessageNumber = ((Long)i$.next()).longValue();
/* 124 */       if (unackedMessageNumber >= currentRange.lower && unackedMessageNumber <= currentRange.upper) {
/* 125 */         this.data.markAsAcknowledged(unackedMessageNumber); continue;
/* 126 */       }  if (rangeIterator.hasNext()) {
/* 127 */         currentRange = rangeIterator.next();
/*     */       } }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     getDeliveryQueue().onSequenceAcknowledgement();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\OutboundSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */