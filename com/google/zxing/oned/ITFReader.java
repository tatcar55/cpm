/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public final class ITFReader
/*     */   extends OneDReader
/*     */ {
/*     */   private static final float MAX_AVG_VARIANCE = 0.38F;
/*     */   private static final float MAX_INDIVIDUAL_VARIANCE = 0.78F;
/*     */   private static final int W = 3;
/*     */   private static final int N = 1;
/*  54 */   private static final int[] DEFAULT_ALLOWED_LENGTHS = new int[] { 6, 8, 10, 12, 14 };
/*     */ 
/*     */   
/*  57 */   private int narrowLineWidth = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final int[] START_PATTERN = new int[] { 1, 1, 1, 1 };
/*  66 */   private static final int[] END_PATTERN_REVERSED = new int[] { 1, 1, 3 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   static final int[][] PATTERNS = new int[][] { { 1, 1, 3, 3, 1 }, { 3, 1, 1, 1, 3 }, { 1, 3, 1, 1, 3 }, { 3, 3, 1, 1, 1 }, { 1, 1, 3, 1, 3 }, { 3, 1, 3, 1, 1 }, { 1, 3, 3, 1, 1 }, { 1, 1, 1, 3, 3 }, { 3, 1, 1, 3, 1 }, { 1, 3, 1, 3, 1 } };
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
/*     */   public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) throws FormatException, NotFoundException {
/*  89 */     int[] startRange = decodeStart(row);
/*  90 */     int[] endRange = decodeEnd(row);
/*     */     
/*  92 */     StringBuilder result = new StringBuilder(20);
/*  93 */     decodeMiddle(row, startRange[1], endRange[0], result);
/*  94 */     String resultString = result.toString();
/*     */     
/*  96 */     int[] allowedLengths = null;
/*  97 */     if (hints != null) {
/*  98 */       allowedLengths = (int[])hints.get(DecodeHintType.ALLOWED_LENGTHS);
/*     */     }
/*     */     
/* 101 */     if (allowedLengths == null) {
/* 102 */       allowedLengths = DEFAULT_ALLOWED_LENGTHS;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 107 */     int length = resultString.length();
/* 108 */     boolean lengthOK = false;
/* 109 */     int maxAllowedLength = 0;
/* 110 */     for (int allowedLength : allowedLengths) {
/* 111 */       if (length == allowedLength) {
/* 112 */         lengthOK = true;
/*     */         break;
/*     */       } 
/* 115 */       if (allowedLength > maxAllowedLength) {
/* 116 */         maxAllowedLength = allowedLength;
/*     */       }
/*     */     } 
/* 119 */     if (!lengthOK && length > maxAllowedLength) {
/* 120 */       lengthOK = true;
/*     */     }
/* 122 */     if (!lengthOK) {
/* 123 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */     
/* 126 */     return new Result(resultString, null, new ResultPoint[] { new ResultPoint(startRange[1], rowNumber), new ResultPoint(endRange[0], rowNumber) }BarcodeFormat.ITF);
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
/*     */   private static void decodeMiddle(BitArray row, int payloadStart, int payloadEnd, StringBuilder resultString) throws NotFoundException {
/* 150 */     int[] counterDigitPair = new int[10];
/* 151 */     int[] counterBlack = new int[5];
/* 152 */     int[] counterWhite = new int[5];
/*     */     
/* 154 */     while (payloadStart < payloadEnd) {
/*     */ 
/*     */       
/* 157 */       recordPattern(row, payloadStart, counterDigitPair);
/*     */       
/* 159 */       for (int k = 0; k < 5; k++) {
/* 160 */         int twoK = 2 * k;
/* 161 */         counterBlack[k] = counterDigitPair[twoK];
/* 162 */         counterWhite[k] = counterDigitPair[twoK + 1];
/*     */       } 
/*     */       
/* 165 */       int bestMatch = decodeDigit(counterBlack);
/* 166 */       resultString.append((char)(48 + bestMatch));
/* 167 */       bestMatch = decodeDigit(counterWhite);
/* 168 */       resultString.append((char)(48 + bestMatch));
/*     */       
/* 170 */       for (int counterDigit : counterDigitPair) {
/* 171 */         payloadStart += counterDigit;
/*     */       }
/*     */     } 
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
/*     */   int[] decodeStart(BitArray row) throws NotFoundException {
/* 185 */     int endStart = skipWhiteSpace(row);
/* 186 */     int[] startPattern = findGuardPattern(row, endStart, START_PATTERN);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     this.narrowLineWidth = (startPattern[1] - startPattern[0]) / 4;
/*     */     
/* 193 */     validateQuietZone(row, startPattern[0]);
/*     */     
/* 195 */     return startPattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateQuietZone(BitArray row, int startPattern) throws NotFoundException {
/* 215 */     int quietCount = this.narrowLineWidth * 10;
/*     */ 
/*     */     
/* 218 */     quietCount = (quietCount < startPattern) ? quietCount : startPattern;
/*     */     
/* 220 */     for (int i = startPattern - 1; quietCount > 0 && i >= 0 && 
/* 221 */       !row.get(i); i--)
/*     */     {
/*     */       
/* 224 */       quietCount--;
/*     */     }
/* 226 */     if (quietCount != 0)
/*     */     {
/* 228 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int skipWhiteSpace(BitArray row) throws NotFoundException {
/* 240 */     int width = row.getSize();
/* 241 */     int endStart = row.getNextSet(0);
/* 242 */     if (endStart == width) {
/* 243 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 246 */     return endStart;
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
/*     */ 
/*     */   
/*     */   int[] decodeEnd(BitArray row) throws NotFoundException {
/* 261 */     row.reverse();
/*     */     try {
/* 263 */       int endStart = skipWhiteSpace(row);
/* 264 */       int[] endPattern = findGuardPattern(row, endStart, END_PATTERN_REVERSED);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       validateQuietZone(row, endPattern[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       int temp = endPattern[0];
/* 275 */       endPattern[0] = row.getSize() - endPattern[1];
/* 276 */       endPattern[1] = row.getSize() - temp;
/*     */       
/* 278 */       return endPattern;
/*     */     } finally {
/*     */       
/* 281 */       row.reverse();
/*     */     } 
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
/*     */ 
/*     */   
/*     */   private static int[] findGuardPattern(BitArray row, int rowOffset, int[] pattern) throws NotFoundException {
/* 297 */     int patternLength = pattern.length;
/* 298 */     int[] counters = new int[patternLength];
/* 299 */     int width = row.getSize();
/* 300 */     boolean isWhite = false;
/*     */     
/* 302 */     int counterPosition = 0;
/* 303 */     int patternStart = rowOffset;
/* 304 */     for (int x = rowOffset; x < width; x++) {
/* 305 */       if (row.get(x) ^ isWhite) {
/* 306 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 308 */         if (counterPosition == patternLength - 1) {
/* 309 */           if (patternMatchVariance(counters, pattern, 0.78F) < 0.38F) {
/* 310 */             return new int[] { patternStart, x };
/*     */           }
/* 312 */           patternStart += counters[0] + counters[1];
/* 313 */           System.arraycopy(counters, 2, counters, 0, patternLength - 2);
/* 314 */           counters[patternLength - 2] = 0;
/* 315 */           counters[patternLength - 1] = 0;
/* 316 */           counterPosition--;
/*     */         } else {
/* 318 */           counterPosition++;
/*     */         } 
/* 320 */         counters[counterPosition] = 1;
/* 321 */         isWhite = !isWhite;
/*     */       } 
/*     */     } 
/* 324 */     throw NotFoundException.getNotFoundInstance();
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
/*     */   private static int decodeDigit(int[] counters) throws NotFoundException {
/* 336 */     float bestVariance = 0.38F;
/* 337 */     int bestMatch = -1;
/* 338 */     int max = PATTERNS.length;
/* 339 */     for (int i = 0; i < max; i++) {
/* 340 */       int[] pattern = PATTERNS[i];
/* 341 */       float variance = patternMatchVariance(counters, pattern, 0.78F);
/* 342 */       if (variance < bestVariance) {
/* 343 */         bestVariance = variance;
/* 344 */         bestMatch = i;
/*     */       } 
/*     */     } 
/* 347 */     if (bestMatch >= 0) {
/* 348 */       return bestMatch;
/*     */     }
/* 350 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\ITFReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */