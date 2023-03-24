/*    */ package com.google.zxing.aztec.encoder;
/*    */ 
/*    */ import com.google.zxing.common.BitArray;
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
/*    */ final class SimpleToken
/*    */   extends Token
/*    */ {
/*    */   private final short value;
/*    */   private final short bitCount;
/*    */   
/*    */   SimpleToken(Token previous, int value, int bitCount) {
/* 28 */     super(previous);
/* 29 */     this.value = (short)value;
/* 30 */     this.bitCount = (short)bitCount;
/*    */   }
/*    */ 
/*    */   
/*    */   void appendTo(BitArray bitArray, byte[] text) {
/* 35 */     bitArray.appendBits(this.value, this.bitCount);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     int value = this.value & (1 << this.bitCount) - 1;
/* 41 */     value |= 1 << this.bitCount;
/* 42 */     return '<' + Integer.toBinaryString(value | 1 << this.bitCount).substring(1) + '>';
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\encoder\SimpleToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */