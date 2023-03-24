/*    */ package com.sun.xml.ws.rx.rm.protocol;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TerminateSequenceData
/*    */ {
/*    */   @NotNull
/*    */   private final String sequenceId;
/*    */   private final long lastMessageId;
/*    */   @Nullable
/*    */   private final AcknowledgementData acknowledgementData;
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     @NotNull
/*    */     private final String sequenceId;
/*    */     private final long lastMessageId;
/*    */     @Nullable
/*    */     private AcknowledgementData acknowledgementData;
/*    */     
/*    */     private Builder(String sequenceId, long lastMessageId) {
/* 57 */       this.sequenceId = sequenceId;
/* 58 */       this.lastMessageId = lastMessageId;
/*    */     }
/*    */     
/*    */     public Builder acknowledgementData(@Nullable AcknowledgementData acknowledgementData) {
/* 62 */       this.acknowledgementData = acknowledgementData;
/*    */       
/* 64 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public TerminateSequenceData build() {
/* 69 */       return new TerminateSequenceData(this.sequenceId, this.lastMessageId, this.acknowledgementData);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static Builder getBuilder(@NotNull String sequenceId, long lastMessageId) {
/* 75 */     return new Builder(sequenceId, lastMessageId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private TerminateSequenceData(@NotNull String sequenceId, long lastMessageId, @Nullable AcknowledgementData acknowledgementData) {
/* 83 */     assert sequenceId != null;
/*    */     
/* 85 */     this.sequenceId = sequenceId;
/* 86 */     this.lastMessageId = lastMessageId;
/* 87 */     this.acknowledgementData = acknowledgementData;
/*    */   }
/*    */   @NotNull
/*    */   public String getSequenceId() {
/* 91 */     return this.sequenceId;
/*    */   }
/*    */   
/*    */   public long getLastMessageId() {
/* 95 */     return this.lastMessageId;
/*    */   }
/*    */   @Nullable
/*    */   public AcknowledgementData getAcknowledgementData() {
/* 99 */     return this.acknowledgementData;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\TerminateSequenceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */