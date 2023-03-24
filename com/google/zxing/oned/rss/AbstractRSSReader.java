/*     */ package com.google.zxing.oned.rss;
/*     */ 
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.oned.OneDReader;
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
/*     */ public abstract class AbstractRSSReader
/*     */   extends OneDReader
/*     */ {
/*     */   private static final float MAX_AVG_VARIANCE = 0.2F;
/*     */   private static final float MAX_INDIVIDUAL_VARIANCE = 0.45F;
/*     */   private static final float MIN_FINDER_PATTERN_RATIO = 0.7916667F;
/*     */   private static final float MAX_FINDER_PATTERN_RATIO = 0.89285713F;
/*  38 */   private final int[] decodeFinderCounters = new int[4];
/*  39 */   private final int[] dataCharacterCounters = new int[8];
/*  40 */   private final float[] oddRoundingErrors = new float[4];
/*  41 */   private final float[] evenRoundingErrors = new float[4];
/*  42 */   private final int[] oddCounts = new int[this.dataCharacterCounters.length / 2];
/*  43 */   private final int[] evenCounts = new int[this.dataCharacterCounters.length / 2];
/*     */ 
/*     */   
/*     */   protected final int[] getDecodeFinderCounters() {
/*  47 */     return this.decodeFinderCounters;
/*     */   }
/*     */   
/*     */   protected final int[] getDataCharacterCounters() {
/*  51 */     return this.dataCharacterCounters;
/*     */   }
/*     */   
/*     */   protected final float[] getOddRoundingErrors() {
/*  55 */     return this.oddRoundingErrors;
/*     */   }
/*     */   
/*     */   protected final float[] getEvenRoundingErrors() {
/*  59 */     return this.evenRoundingErrors;
/*     */   }
/*     */   
/*     */   protected final int[] getOddCounts() {
/*  63 */     return this.oddCounts;
/*     */   }
/*     */   
/*     */   protected final int[] getEvenCounts() {
/*  67 */     return this.evenCounts;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int parseFinderValue(int[] counters, int[][] finderPatterns) throws NotFoundException {
/*  72 */     for (int value = 0; value < finderPatterns.length; value++) {
/*  73 */       if (patternMatchVariance(counters, finderPatterns[value], 0.45F) < 0.2F)
/*     */       {
/*  75 */         return value;
/*     */       }
/*     */     } 
/*  78 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */   
/*     */   protected static int count(int[] array) {
/*  82 */     int count = 0;
/*  83 */     for (int a : array) {
/*  84 */       count += a;
/*     */     }
/*  86 */     return count;
/*     */   }
/*     */   
/*     */   protected static void increment(int[] array, float[] errors) {
/*  90 */     int index = 0;
/*  91 */     float biggestError = errors[0];
/*  92 */     for (int i = 1; i < array.length; i++) {
/*  93 */       if (errors[i] > biggestError) {
/*  94 */         biggestError = errors[i];
/*  95 */         index = i;
/*     */       } 
/*     */     } 
/*  98 */     array[index] = array[index] + 1;
/*     */   }
/*     */   
/*     */   protected static void decrement(int[] array, float[] errors) {
/* 102 */     int index = 0;
/* 103 */     float biggestError = errors[0];
/* 104 */     for (int i = 1; i < array.length; i++) {
/* 105 */       if (errors[i] < biggestError) {
/* 106 */         biggestError = errors[i];
/* 107 */         index = i;
/*     */       } 
/*     */     } 
/* 110 */     array[index] = array[index] - 1;
/*     */   }
/*     */   
/*     */   protected static boolean isFinderPattern(int[] counters) {
/* 114 */     int firstTwoSum = counters[0] + counters[1];
/* 115 */     int sum = firstTwoSum + counters[2] + counters[3];
/* 116 */     float ratio = firstTwoSum / sum;
/* 117 */     if (ratio >= 0.7916667F && ratio <= 0.89285713F) {
/*     */       
/* 119 */       int minCounter = Integer.MAX_VALUE;
/* 120 */       int maxCounter = Integer.MIN_VALUE;
/* 121 */       for (int counter : counters) {
/* 122 */         if (counter > maxCounter) {
/* 123 */           maxCounter = counter;
/*     */         }
/* 125 */         if (counter < minCounter) {
/* 126 */           minCounter = counter;
/*     */         }
/*     */       } 
/* 129 */       return (maxCounter < 10 * minCounter);
/*     */     } 
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\AbstractRSSReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */