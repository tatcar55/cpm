/*     */ package com.sun.xml.ws.rx.rm.protocol;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
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
/*     */ public class CreateSequenceResponseData
/*     */ {
/*     */   @NotNull
/*     */   private final String sequenceId;
/*     */   private final long duration;
/*     */   @Nullable
/*     */   private final EndpointReference acceptedSequenceAcksTo;
/*     */   private final Sequence.IncompleteSequenceBehavior incompleteSequenceBehavior;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @NotNull
/*     */     private final String sequenceId;
/*     */     private long duration;
/*     */     @Nullable
/*     */     private EndpointReference acceptedSequenceAcksTo;
/*     */     private Sequence.IncompleteSequenceBehavior incompleteSequenceBehavior;
/*     */     
/*     */     private Builder(String sequenceId) {
/*  61 */       this.sequenceId = sequenceId;
/*  62 */       this.duration = -1L;
/*  63 */       this.incompleteSequenceBehavior = Sequence.IncompleteSequenceBehavior.getDefault();
/*     */     }
/*     */     
/*     */     public Builder acceptedSequenceAcksTo(EndpointReference acceptedSequenceAcksTo) {
/*  67 */       this.acceptedSequenceAcksTo = acceptedSequenceAcksTo;
/*  68 */       return this;
/*     */     }
/*     */     
/*     */     public Builder duration(long duration) {
/*  72 */       this.duration = duration;
/*  73 */       return this;
/*     */     }
/*     */     
/*     */     public Builder incompleteSequenceBehavior(Sequence.IncompleteSequenceBehavior value) {
/*  77 */       this.incompleteSequenceBehavior = value;
/*  78 */       return this;
/*     */     }
/*     */     
/*     */     public CreateSequenceResponseData build() {
/*  82 */       return new CreateSequenceResponseData(this.sequenceId, this.duration, this.acceptedSequenceAcksTo, this.incompleteSequenceBehavior);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder getBuilder(String sequenceId) {
/*  87 */     return new Builder(sequenceId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CreateSequenceResponseData(@NotNull String sequenceId, long expirationTime, @Nullable EndpointReference acceptedSequenceAcksTo, Sequence.IncompleteSequenceBehavior incompleteSequenceBehavior) {
/*  97 */     this.sequenceId = sequenceId;
/*  98 */     this.duration = expirationTime;
/*  99 */     this.acceptedSequenceAcksTo = acceptedSequenceAcksTo;
/* 100 */     this.incompleteSequenceBehavior = incompleteSequenceBehavior;
/*     */   }
/*     */   @Nullable
/*     */   public EndpointReference getAcceptedSequenceAcksTo() {
/* 104 */     return this.acceptedSequenceAcksTo;
/*     */   }
/*     */   
/*     */   public long getDuration() {
/* 108 */     return this.duration;
/*     */   }
/*     */   
/*     */   public boolean doesNotExpire() {
/* 112 */     return (this.duration == -1L);
/*     */   }
/*     */   @NotNull
/*     */   public String getSequenceId() {
/* 116 */     return this.sequenceId;
/*     */   }
/*     */   
/*     */   public Sequence.IncompleteSequenceBehavior getIncompleteSequenceBehavior() {
/* 120 */     return this.incompleteSequenceBehavior;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\CreateSequenceResponseData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */