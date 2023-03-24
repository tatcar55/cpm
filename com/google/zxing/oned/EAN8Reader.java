/*    */ package com.google.zxing.oned;
/*    */ 
/*    */ import com.google.zxing.BarcodeFormat;
/*    */ import com.google.zxing.NotFoundException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class EAN8Reader
/*    */   extends UPCEANReader
/*    */ {
/* 33 */   private final int[] decodeMiddleCounters = new int[4];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int decodeMiddle(BitArray row, int[] startRange, StringBuilder result) throws NotFoundException {
/* 40 */     int[] counters = this.decodeMiddleCounters;
/* 41 */     counters[0] = 0;
/* 42 */     counters[1] = 0;
/* 43 */     counters[2] = 0;
/* 44 */     counters[3] = 0;
/* 45 */     int end = row.getSize();
/* 46 */     int rowOffset = startRange[1];
/*    */     
/* 48 */     for (int x = 0; x < 4 && rowOffset < end; x++) {
/* 49 */       int bestMatch = decodeDigit(row, counters, rowOffset, L_PATTERNS);
/* 50 */       result.append((char)(48 + bestMatch));
/* 51 */       for (int counter : counters) {
/* 52 */         rowOffset += counter;
/*    */       }
/*    */     } 
/*    */     
/* 56 */     int[] middleRange = findGuardPattern(row, rowOffset, true, MIDDLE_PATTERN);
/* 57 */     rowOffset = middleRange[1];
/*    */     
/* 59 */     for (int i = 0; i < 4 && rowOffset < end; i++) {
/* 60 */       int bestMatch = decodeDigit(row, counters, rowOffset, L_PATTERNS);
/* 61 */       result.append((char)(48 + bestMatch));
/* 62 */       for (int counter : counters) {
/* 63 */         rowOffset += counter;
/*    */       }
/*    */     } 
/*    */     
/* 67 */     return rowOffset;
/*    */   }
/*    */ 
/*    */   
/*    */   BarcodeFormat getBarcodeFormat() {
/* 72 */     return BarcodeFormat.EAN_8;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\EAN8Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */