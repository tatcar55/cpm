/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.rx.message.RxMessageBase;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ApplicationMessageBase
/*     */   extends RxMessageBase
/*     */   implements ApplicationMessage
/*     */ {
/*     */   private String sequenceId;
/*     */   private long messageNumber;
/*     */   private AcknowledgementData acknowledgementData;
/*     */   private final AtomicInteger resendCount;
/*     */   
/*     */   protected ApplicationMessageBase(@NotNull String correlationId) {
/*  61 */     this(1, correlationId, null, 0L, null);
/*     */   }
/*     */   
/*     */   protected ApplicationMessageBase(@NotNull String correlationId, String sequenceId, long messageNumber, AcknowledgementData acknowledgementData) {
/*  65 */     this(1, correlationId, sequenceId, messageNumber, acknowledgementData);
/*     */   }
/*     */   
/*     */   protected ApplicationMessageBase(int initialResendCounterValue, @NotNull String correlationId, String sequenceId, long messageNumber, AcknowledgementData acknowledgementData) {
/*  69 */     super(correlationId);
/*     */     
/*  71 */     this.resendCount = new AtomicInteger(initialResendCounterValue);
/*     */     
/*  73 */     this.sequenceId = sequenceId;
/*  74 */     this.messageNumber = messageNumber;
/*  75 */     this.acknowledgementData = acknowledgementData;
/*     */   }
/*     */   
/*     */   public AcknowledgementData getAcknowledgementData() {
/*  79 */     return this.acknowledgementData;
/*     */   }
/*     */   
/*     */   public long getMessageNumber() {
/*  83 */     return this.messageNumber;
/*     */   }
/*     */   
/*     */   public String getSequenceId() {
/*  87 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public void setAcknowledgementData(AcknowledgementData data) {
/*  91 */     this.acknowledgementData = data;
/*     */   }
/*     */   
/*     */   public void setSequenceData(String sequenceId, long messageNumber) {
/*  95 */     assert sequenceId != null;
/*  96 */     this.sequenceId = sequenceId;
/*  97 */     this.messageNumber = messageNumber;
/*     */   }
/*     */   
/*     */   public int getNextResendCount() {
/* 101 */     return this.resendCount.getAndIncrement();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ApplicationMessageBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */