/*     */ package com.google.zxing.pdf417.decoder;
/*     */ 
/*     */ import com.google.zxing.pdf417.PDF417Common;
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
/*     */ final class PDF417CodewordDecoder
/*     */ {
/*  27 */   private static final float[][] RATIOS_TABLE = new float[PDF417Common.SYMBOL_TABLE.length][8];
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  32 */     for (int i = 0; i < PDF417Common.SYMBOL_TABLE.length; i++) {
/*  33 */       int currentSymbol = PDF417Common.SYMBOL_TABLE[i];
/*  34 */       int currentBit = currentSymbol & 0x1;
/*  35 */       for (int j = 0; j < 8; j++) {
/*  36 */         float size = 0.0F;
/*  37 */         while ((currentSymbol & 0x1) == currentBit) {
/*  38 */           size++;
/*  39 */           currentSymbol >>= 1;
/*     */         } 
/*  41 */         currentBit = currentSymbol & 0x1;
/*  42 */         RATIOS_TABLE[i][8 - j - 1] = size / 17.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDecodedValue(int[] moduleBitCount) {
/*  51 */     int decodedValue = getDecodedCodewordValue(sampleBitCounts(moduleBitCount));
/*  52 */     if (decodedValue != -1) {
/*  53 */       return decodedValue;
/*     */     }
/*  55 */     return getClosestDecodedValue(moduleBitCount);
/*     */   }
/*     */   
/*     */   private static int[] sampleBitCounts(int[] moduleBitCount) {
/*  59 */     float bitCountSum = PDF417Common.getBitCountSum(moduleBitCount);
/*  60 */     int[] result = new int[8];
/*  61 */     int bitCountIndex = 0;
/*  62 */     int sumPreviousBits = 0;
/*  63 */     for (int i = 0; i < 17; i++) {
/*  64 */       float sampleIndex = bitCountSum / 34.0F + i * bitCountSum / 17.0F;
/*     */ 
/*     */       
/*  67 */       if ((sumPreviousBits + moduleBitCount[bitCountIndex]) <= sampleIndex) {
/*  68 */         sumPreviousBits += moduleBitCount[bitCountIndex];
/*  69 */         bitCountIndex++;
/*     */       } 
/*  71 */       result[bitCountIndex] = result[bitCountIndex] + 1;
/*     */     } 
/*  73 */     return result;
/*     */   }
/*     */   
/*     */   private static int getDecodedCodewordValue(int[] moduleBitCount) {
/*  77 */     int decodedValue = getBitValue(moduleBitCount);
/*  78 */     return (PDF417Common.getCodeword(decodedValue) == -1) ? -1 : decodedValue;
/*     */   }
/*     */   
/*     */   private static int getBitValue(int[] moduleBitCount) {
/*  82 */     long result = 0L;
/*  83 */     for (int i = 0; i < moduleBitCount.length; i++) {
/*  84 */       for (int bit = 0; bit < moduleBitCount[i]; bit++) {
/*  85 */         result = result << 1L | ((i % 2 == 0) ? 1L : 0L);
/*     */       }
/*     */     } 
/*  88 */     return (int)result;
/*     */   }
/*     */   
/*     */   private static int getClosestDecodedValue(int[] moduleBitCount) {
/*  92 */     int bitCountSum = PDF417Common.getBitCountSum(moduleBitCount);
/*  93 */     float[] bitCountRatios = new float[8];
/*  94 */     for (int i = 0; i < bitCountRatios.length; i++) {
/*  95 */       bitCountRatios[i] = moduleBitCount[i] / bitCountSum;
/*     */     }
/*  97 */     float bestMatchError = Float.MAX_VALUE;
/*  98 */     int bestMatch = -1;
/*  99 */     for (int j = 0; j < RATIOS_TABLE.length; j++) {
/* 100 */       float error = 0.0F;
/* 101 */       float[] ratioTableRow = RATIOS_TABLE[j];
/* 102 */       for (int k = 0; k < 8; k++) {
/* 103 */         float diff = ratioTableRow[k] - bitCountRatios[k];
/* 104 */         error += diff * diff;
/* 105 */         if (error >= bestMatchError) {
/*     */           break;
/*     */         }
/*     */       } 
/* 109 */       if (error < bestMatchError) {
/* 110 */         bestMatchError = error;
/* 111 */         bestMatch = PDF417Common.SYMBOL_TABLE[j];
/*     */       } 
/*     */     } 
/* 114 */     return bestMatch;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\PDF417CodewordDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */