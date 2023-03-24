/*    */ package com.google.zxing.qrcode.encoder;
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
/*    */ final class BlockPair
/*    */ {
/*    */   private final byte[] dataBytes;
/*    */   private final byte[] errorCorrectionBytes;
/*    */   
/*    */   BlockPair(byte[] data, byte[] errorCorrection) {
/* 25 */     this.dataBytes = data;
/* 26 */     this.errorCorrectionBytes = errorCorrection;
/*    */   }
/*    */   
/*    */   public byte[] getDataBytes() {
/* 30 */     return this.dataBytes;
/*    */   }
/*    */   
/*    */   public byte[] getErrorCorrectionBytes() {
/* 34 */     return this.errorCorrectionBytes;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\encoder\BlockPair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */