/*     */ package com.sun.xml.ws.rx.rm.protocol;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TerminateSequenceResponseData
/*     */ {
/*     */   @NotNull
/*     */   private final String sequenceId;
/*     */   @Nullable
/*     */   private final String boundSequenceId;
/*     */   private final long boundSequenceLastMessageId;
/*     */   @Nullable
/*     */   private final AcknowledgementData acknowledgementData;
/*     */   
/*     */   public static class Builder
/*     */   {
/*     */     @NotNull
/*     */     private final String sequenceId;
/*     */     @Nullable
/*     */     private String boundSequenceId;
/*     */     private long boundSequenceLastMessageId;
/*     */     @Nullable
/*     */     private AcknowledgementData acknowledgementData;
/*     */     
/*     */     public Builder(@NotNull String sequenceId) {
/*  59 */       this.sequenceId = sequenceId;
/*     */     }
/*     */     
/*     */     public Builder acknowledgementData(@Nullable AcknowledgementData acknowledgementData) {
/*  63 */       this.acknowledgementData = acknowledgementData;
/*     */       
/*  65 */       return this;
/*     */     }
/*     */     
/*     */     public Builder boundSequenceData(String sequenceId, long lastMessageId) {
/*  69 */       this.boundSequenceId = sequenceId;
/*  70 */       this.boundSequenceLastMessageId = lastMessageId;
/*     */       
/*  72 */       return this;
/*     */     }
/*     */     
/*     */     public TerminateSequenceResponseData build() {
/*  76 */       return new TerminateSequenceResponseData(this.sequenceId, this.boundSequenceId, this.boundSequenceLastMessageId, this.acknowledgementData);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder getBuilder(String sequenceId) {
/*  81 */     return new Builder(sequenceId);
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
/*     */   
/*     */   private TerminateSequenceResponseData(@NotNull String sequenceId, @Nullable String boundSequenceId, long boundSequenceLastMessageId, @Nullable AcknowledgementData acknowledgementData) {
/*  94 */     this.sequenceId = sequenceId;
/*  95 */     this.boundSequenceId = boundSequenceId;
/*  96 */     this.boundSequenceLastMessageId = boundSequenceLastMessageId;
/*  97 */     this.acknowledgementData = acknowledgementData;
/*     */   }
/*     */   @NotNull
/*     */   public String getSequenceId() {
/* 101 */     return this.sequenceId;
/*     */   }
/*     */   @Nullable
/*     */   public AcknowledgementData getAcknowledgementData() {
/* 105 */     return this.acknowledgementData;
/*     */   }
/*     */   @Nullable
/*     */   public String getBoundSequenceId() {
/* 109 */     return this.boundSequenceId;
/*     */   }
/*     */   
/*     */   public long getBoundSequenceLastMessageId() {
/* 113 */     return this.boundSequenceLastMessageId;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\TerminateSequenceResponseData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */