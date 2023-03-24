/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueue;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSequence
/*     */   implements Sequence
/*     */ {
/*     */   protected final SequenceData data;
/*     */   private final DeliveryQueue deliveryQueue;
/*     */   private final TimeSynchronizer timeSynchronizer;
/*     */   
/*     */   AbstractSequence(@NotNull SequenceData data, @NotNull DeliveryQueueBuilder deliveryQueueBuilder, @NotNull TimeSynchronizer timeSynchronizer) {
/*  76 */     assert data != null;
/*  77 */     assert deliveryQueueBuilder != null;
/*  78 */     assert timeSynchronizer != null;
/*     */ 
/*     */     
/*  81 */     this.data = data;
/*  82 */     this.timeSynchronizer = timeSynchronizer;
/*     */     
/*  84 */     deliveryQueueBuilder.sequence(this);
/*  85 */     this.deliveryQueue = deliveryQueueBuilder.build();
/*     */   }
/*     */   
/*     */   public String getId() {
/*  89 */     return this.data.getSequenceId();
/*     */   }
/*     */   
/*     */   public String getBoundSecurityTokenReferenceId() {
/*  93 */     return this.data.getBoundSecurityTokenReferenceId();
/*     */   }
/*     */   
/*     */   public long getLastMessageNumber() {
/*  97 */     return this.data.getLastMessageNumber();
/*     */   }
/*     */   
/*     */   public List<Sequence.AckRange> getAcknowledgedMessageNumbers() {
/* 101 */     List<Long> values = this.data.getLastMessageNumberWithUnackedMessageNumbers();
/*     */     
/* 103 */     long lastMessageNumber = ((Long)values.remove(0)).longValue();
/* 104 */     List<Long> unackedMessageNumbers = values;
/*     */     
/* 106 */     if (lastMessageNumber == 0L)
/*     */     {
/* 108 */       return Collections.emptyList(); } 
/* 109 */     if (unackedMessageNumbers.isEmpty())
/*     */     {
/* 111 */       return Arrays.asList(new Sequence.AckRange[] { new Sequence.AckRange(1L, lastMessageNumber) });
/*     */     }
/*     */     
/* 114 */     List<Sequence.AckRange> result = new LinkedList<Sequence.AckRange>();
/*     */     
/* 116 */     long lastBottomAckRange = 1L;
/* 117 */     for (Iterator<Long> i$ = unackedMessageNumbers.iterator(); i$.hasNext(); ) { long lastUnacked = ((Long)i$.next()).longValue();
/* 118 */       if (lastBottomAckRange < lastUnacked) {
/* 119 */         result.add(new Sequence.AckRange(lastBottomAckRange, lastUnacked - 1L));
/*     */       }
/* 121 */       lastBottomAckRange = lastUnacked + 1L; }
/*     */     
/* 123 */     if (lastBottomAckRange <= lastMessageNumber) {
/* 124 */       result.add(new Sequence.AckRange(lastBottomAckRange, lastMessageNumber));
/*     */     }
/*     */     
/* 127 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUnacknowledgedMessages() {
/* 132 */     return !this.data.getUnackedMessageNumbers().isEmpty();
/*     */   }
/*     */   
/*     */   public Sequence.State getState() {
/* 136 */     return this.data.getState();
/*     */   }
/*     */   
/*     */   public void setAckRequestedFlag() {
/* 140 */     this.data.setAckRequestedFlag(true);
/*     */   }
/*     */   
/*     */   public void clearAckRequestedFlag() {
/* 144 */     this.data.setAckRequestedFlag(false);
/*     */   }
/*     */   
/*     */   public boolean isAckRequested() {
/* 148 */     return this.data.getAckRequestedFlag();
/*     */   }
/*     */   
/*     */   public boolean isFailedOver(long messageNumber) {
/* 152 */     return this.data.isFailedOver(messageNumber);
/*     */   }
/*     */   
/*     */   public void updateLastAcknowledgementRequestTime() {
/* 156 */     this.data.setLastAcknowledgementRequestTime(this.timeSynchronizer.currentTimeInMillis());
/*     */   }
/*     */   
/*     */   public long getLastActivityTime() {
/* 160 */     return this.data.getLastActivityTime();
/*     */   }
/*     */   
/*     */   public boolean isStandaloneAcknowledgementRequestSchedulable(long delayPeriod) {
/* 164 */     return (this.timeSynchronizer.currentTimeInMillis() - this.data.getLastAcknowledgementRequestTime() > delayPeriod && hasUnacknowledgedMessages());
/*     */   }
/*     */   
/*     */   public void close() {
/* 168 */     this.data.setState(Sequence.State.CLOSED);
/* 169 */     this.deliveryQueue.close();
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 173 */     Sequence.State currentStatus = this.data.getState();
/* 174 */     return (currentStatus == Sequence.State.CLOSING || currentStatus == Sequence.State.CLOSED || currentStatus == Sequence.State.TERMINATING);
/*     */   }
/*     */   
/*     */   public boolean isExpired() {
/* 178 */     return (this.data.getExpirationTime() == -1L) ? false : ((this.timeSynchronizer.currentTimeInMillis() > this.data.getExpirationTime()));
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/* 182 */     this.data.setState(Sequence.State.TERMINATING);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SequenceData getData() {
/* 188 */     return this.data;
/*     */   }
/*     */   
/*     */   public ApplicationMessage retrieveMessage(String correlationId) {
/* 192 */     return this.data.retrieveMessage(correlationId);
/*     */   }
/*     */   
/*     */   public DeliveryQueue getDeliveryQueue() {
/* 196 */     return this.deliveryQueue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 201 */     if (obj == null) {
/* 202 */       return false;
/*     */     }
/* 204 */     if (this == obj) {
/* 205 */       return true;
/*     */     }
/* 207 */     if (getClass() != obj.getClass()) {
/* 208 */       return false;
/*     */     }
/* 210 */     AbstractSequence other = (AbstractSequence)obj;
/* 211 */     if ((this.data.getSequenceId() == null) ? (other.data.getSequenceId() != null) : !this.data.getSequenceId().equals(other.data.getSequenceId())) {
/* 212 */       return false;
/*     */     }
/* 214 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 219 */     int hash = 7;
/* 220 */     hash = 41 * hash + ((this.data.getSequenceId() != null) ? this.data.getSequenceId().hashCode() : 0);
/* 221 */     return hash;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\AbstractSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */