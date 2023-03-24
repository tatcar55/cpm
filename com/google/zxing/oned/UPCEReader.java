/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.common.BitArray;
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
/*     */ public final class UPCEReader
/*     */   extends UPCEANReader
/*     */ {
/*  37 */   private static final int[] MIDDLE_END_PATTERN = new int[] { 1, 1, 1, 1, 1, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final int[][] NUMSYS_AND_CHECK_DIGIT_PATTERNS = new int[][] { { 56, 52, 50, 49, 44, 38, 35, 42, 41, 37 }, { 7, 11, 13, 14, 19, 25, 28, 21, 22, 26 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   private final int[] decodeMiddleCounters = new int[4];
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int decodeMiddle(BitArray row, int[] startRange, StringBuilder result) throws NotFoundException {
/*  58 */     int[] counters = this.decodeMiddleCounters;
/*  59 */     counters[0] = 0;
/*  60 */     counters[1] = 0;
/*  61 */     counters[2] = 0;
/*  62 */     counters[3] = 0;
/*  63 */     int end = row.getSize();
/*  64 */     int rowOffset = startRange[1];
/*     */     
/*  66 */     int lgPatternFound = 0;
/*     */     
/*  68 */     for (int x = 0; x < 6 && rowOffset < end; x++) {
/*  69 */       int bestMatch = decodeDigit(row, counters, rowOffset, L_AND_G_PATTERNS);
/*  70 */       result.append((char)(48 + bestMatch % 10));
/*  71 */       for (int counter : counters) {
/*  72 */         rowOffset += counter;
/*     */       }
/*  74 */       if (bestMatch >= 10) {
/*  75 */         lgPatternFound |= 1 << 5 - x;
/*     */       }
/*     */     } 
/*     */     
/*  79 */     determineNumSysAndCheckDigit(result, lgPatternFound);
/*     */     
/*  81 */     return rowOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int[] decodeEnd(BitArray row, int endStart) throws NotFoundException {
/*  86 */     return findGuardPattern(row, endStart, true, MIDDLE_END_PATTERN);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkChecksum(String s) throws FormatException {
/*  91 */     return super.checkChecksum(convertUPCEtoUPCA(s));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void determineNumSysAndCheckDigit(StringBuilder resultString, int lgPatternFound) throws NotFoundException {
/*  97 */     for (int numSys = 0; numSys <= 1; numSys++) {
/*  98 */       for (int d = 0; d < 10; d++) {
/*  99 */         if (lgPatternFound == NUMSYS_AND_CHECK_DIGIT_PATTERNS[numSys][d]) {
/* 100 */           resultString.insert(0, (char)(48 + numSys));
/* 101 */           resultString.append((char)(48 + d));
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 106 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   BarcodeFormat getBarcodeFormat() {
/* 111 */     return BarcodeFormat.UPC_E;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertUPCEtoUPCA(String upce) {
/* 121 */     char[] upceChars = new char[6];
/* 122 */     upce.getChars(1, 7, upceChars, 0);
/* 123 */     StringBuilder result = new StringBuilder(12);
/* 124 */     result.append(upce.charAt(0));
/* 125 */     char lastChar = upceChars[5];
/* 126 */     switch (lastChar)
/*     */     { case '0':
/*     */       case '1':
/*     */       case '2':
/* 130 */         result.append(upceChars, 0, 2);
/* 131 */         result.append(lastChar);
/* 132 */         result.append("0000");
/* 133 */         result.append(upceChars, 2, 3);
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
/* 151 */         result.append(upce.charAt(7));
/* 152 */         return result.toString();case '3': result.append(upceChars, 0, 3); result.append("00000"); result.append(upceChars, 3, 2); result.append(upce.charAt(7)); return result.toString();case '4': result.append(upceChars, 0, 4); result.append("00000"); result.append(upceChars[4]); result.append(upce.charAt(7)); return result.toString(); }  result.append(upceChars, 0, 5); result.append("0000"); result.append(lastChar); result.append(upce.charAt(7)); return result.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\UPCEReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */