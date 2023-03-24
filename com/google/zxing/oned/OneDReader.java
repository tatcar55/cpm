/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Reader;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultMetadataType;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumMap;
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
/*     */ public abstract class OneDReader
/*     */   implements Reader
/*     */ {
/*     */   public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
/*  46 */     return decode(image, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
/*     */     try {
/*  54 */       return doDecode(image, hints);
/*  55 */     } catch (NotFoundException nfe) {
/*  56 */       boolean tryHarder = (hints != null && hints.containsKey(DecodeHintType.TRY_HARDER));
/*  57 */       if (tryHarder && image.isRotateSupported()) {
/*  58 */         BinaryBitmap rotatedImage = image.rotateCounterClockwise();
/*  59 */         Result result = doDecode(rotatedImage, hints);
/*     */         
/*  61 */         Map<ResultMetadataType, ?> metadata = result.getResultMetadata();
/*  62 */         int orientation = 270;
/*  63 */         if (metadata != null && metadata.containsKey(ResultMetadataType.ORIENTATION))
/*     */         {
/*     */           
/*  66 */           orientation = (orientation + ((Integer)metadata.get(ResultMetadataType.ORIENTATION)).intValue()) % 360;
/*     */         }
/*  68 */         result.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(orientation));
/*     */         
/*  70 */         ResultPoint[] points = result.getResultPoints();
/*  71 */         if (points != null) {
/*  72 */           int height = rotatedImage.getHeight();
/*  73 */           for (int i = 0; i < points.length; i++) {
/*  74 */             points[i] = new ResultPoint(height - points[i].getY() - 1.0F, points[i].getX());
/*     */           }
/*     */         } 
/*  77 */         return result;
/*     */       } 
/*  79 */       throw nfe;
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
/*     */   public void reset() {}
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
/*     */   private Result doDecode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
/* 105 */     int maxLines, width = image.getWidth();
/* 106 */     int height = image.getHeight();
/* 107 */     BitArray row = new BitArray(width);
/*     */     
/* 109 */     int middle = height >> 1;
/* 110 */     boolean tryHarder = (hints != null && hints.containsKey(DecodeHintType.TRY_HARDER));
/* 111 */     int rowStep = Math.max(1, height >> (tryHarder ? 8 : 5));
/*     */     
/* 113 */     if (tryHarder) {
/* 114 */       maxLines = height;
/*     */     } else {
/* 116 */       maxLines = 15;
/*     */     } 
/*     */     
/* 119 */     for (int x = 0; x < maxLines; x++) {
/*     */ 
/*     */       
/* 122 */       int rowStepsAboveOrBelow = (x + 1) / 2;
/* 123 */       boolean isAbove = ((x & 0x1) == 0);
/* 124 */       int rowNumber = middle + rowStep * (isAbove ? rowStepsAboveOrBelow : -rowStepsAboveOrBelow);
/* 125 */       if (rowNumber < 0 || rowNumber >= height) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 132 */         row = image.getBlackRow(rowNumber, row);
/* 133 */       } catch (NotFoundException ignored) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       for (int attempt = 0; attempt < 2; attempt++) {
/* 140 */         if (attempt == 1) {
/* 141 */           row.reverse();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 146 */           if (hints != null && hints.containsKey(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
/* 147 */             Map<DecodeHintType, Object> newHints = new EnumMap<>(DecodeHintType.class);
/* 148 */             newHints.putAll(hints);
/* 149 */             newHints.remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/* 150 */             hints = newHints;
/*     */           } 
/*     */         } 
/*     */         
/*     */         try {
/* 155 */           Result result = decodeRow(rowNumber, row, hints);
/*     */           
/* 157 */           if (attempt == 1) {
/*     */             
/* 159 */             result.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(180));
/*     */             
/* 161 */             ResultPoint[] points = result.getResultPoints();
/* 162 */             if (points != null) {
/* 163 */               points[0] = new ResultPoint(width - points[0].getX() - 1.0F, points[0].getY());
/* 164 */               points[1] = new ResultPoint(width - points[1].getX() - 1.0F, points[1].getY());
/*     */             } 
/*     */           } 
/* 167 */           return result;
/* 168 */         } catch (ReaderException readerException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 174 */     throw NotFoundException.getNotFoundInstance();
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
/*     */   protected static void recordPattern(BitArray row, int start, int[] counters) throws NotFoundException {
/* 193 */     int numCounters = counters.length;
/* 194 */     Arrays.fill(counters, 0, numCounters, 0);
/* 195 */     int end = row.getSize();
/* 196 */     if (start >= end) {
/* 197 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 199 */     boolean isWhite = !row.get(start);
/* 200 */     int counterPosition = 0;
/* 201 */     int i = start;
/* 202 */     while (i < end) {
/* 203 */       if (row.get(i) ^ isWhite) {
/* 204 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 206 */         counterPosition++;
/* 207 */         if (counterPosition == numCounters) {
/*     */           break;
/*     */         }
/* 210 */         counters[counterPosition] = 1;
/* 211 */         isWhite = !isWhite;
/*     */       } 
/*     */       
/* 214 */       i++;
/*     */     } 
/*     */ 
/*     */     
/* 218 */     if (counterPosition != numCounters && (counterPosition != numCounters - 1 || i != end)) {
/* 219 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void recordPatternInReverse(BitArray row, int start, int[] counters) throws NotFoundException {
/* 226 */     int numTransitionsLeft = counters.length;
/* 227 */     boolean last = row.get(start);
/* 228 */     while (start > 0 && numTransitionsLeft >= 0) {
/* 229 */       if (row.get(--start) != last) {
/* 230 */         numTransitionsLeft--;
/* 231 */         last = !last;
/*     */       } 
/*     */     } 
/* 234 */     if (numTransitionsLeft >= 0) {
/* 235 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 237 */     recordPattern(row, start + 1, counters);
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
/*     */   protected static float patternMatchVariance(int[] counters, int[] pattern, float maxIndividualVariance) {
/* 253 */     int numCounters = counters.length;
/* 254 */     int total = 0;
/* 255 */     int patternLength = 0;
/* 256 */     for (int i = 0; i < numCounters; i++) {
/* 257 */       total += counters[i];
/* 258 */       patternLength += pattern[i];
/*     */     } 
/* 260 */     if (total < patternLength)
/*     */     {
/*     */       
/* 263 */       return Float.POSITIVE_INFINITY;
/*     */     }
/*     */     
/* 266 */     float unitBarWidth = total / patternLength;
/* 267 */     maxIndividualVariance *= unitBarWidth;
/*     */     
/* 269 */     float totalVariance = 0.0F;
/* 270 */     for (int x = 0; x < numCounters; x++) {
/* 271 */       int counter = counters[x];
/* 272 */       float scaledPattern = pattern[x] * unitBarWidth;
/* 273 */       float variance = (counter > scaledPattern) ? (counter - scaledPattern) : (scaledPattern - counter);
/* 274 */       if (variance > maxIndividualVariance) {
/* 275 */         return Float.POSITIVE_INFINITY;
/*     */       }
/* 277 */       totalVariance += variance;
/*     */     } 
/* 279 */     return totalVariance / total;
/*     */   }
/*     */   
/*     */   public abstract Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap) throws NotFoundException, ChecksumException, FormatException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\OneDReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */