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
/*    */ 
/*    */ 
/*    */ public class CloseSequenceResponseData
/*    */ {
/*    */   @NotNull
/*    */   private final String sequenceId;
/*    */   @Nullable
/*    */   private final AcknowledgementData acknowledgementData;
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     @NotNull
/*    */     private final String sequenceId;
/*    */     @Nullable
/*    */     private AcknowledgementData acknowledgementData;
/*    */     
/*    */     public Builder(@NotNull String sequenceId) {
/* 57 */       this.sequenceId = sequenceId;
/*    */     }
/*    */     
/*    */     public void acknowledgementData(@Nullable AcknowledgementData acknowledgementData) {
/* 61 */       this.acknowledgementData = acknowledgementData;
/*    */     }
/*    */     
/*    */     public CloseSequenceResponseData build() {
/* 65 */       return new CloseSequenceResponseData(this.sequenceId, this.acknowledgementData);
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder getBuilder(String sequenceId) {
/* 70 */     return new Builder(sequenceId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private CloseSequenceResponseData(@NotNull String sequenceId, @Nullable AcknowledgementData acknowledgementData) {
/* 77 */     this.sequenceId = sequenceId;
/* 78 */     this.acknowledgementData = acknowledgementData;
/*    */   }
/*    */   @NotNull
/*    */   public String getSequenceId() {
/* 82 */     return this.sequenceId;
/*    */   }
/*    */   @Nullable
/*    */   public AcknowledgementData getAcknowledgementData() {
/* 86 */     return this.acknowledgementData;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\protocol\CloseSequenceResponseData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */