/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultMetadataType;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.ResultPointCallback;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import java.util.Arrays;
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
/*     */ public abstract class UPCEANReader
/*     */   extends OneDReader
/*     */ {
/*     */   private static final float MAX_AVG_VARIANCE = 0.48F;
/*     */   private static final float MAX_INDIVIDUAL_VARIANCE = 0.7F;
/*  53 */   static final int[] START_END_PATTERN = new int[] { 1, 1, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   static final int[] MIDDLE_PATTERN = new int[] { 1, 1, 1, 1, 1 };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   static final int[][] L_PATTERNS = new int[][] { { 3, 2, 1, 1 }, { 2, 2, 2, 1 }, { 2, 1, 2, 2 }, { 1, 4, 1, 1 }, { 1, 1, 3, 2 }, { 1, 2, 3, 1 }, { 1, 1, 1, 4 }, { 1, 3, 1, 2 }, { 1, 2, 1, 3 }, { 3, 1, 1, 2 } };
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
/*  82 */   static final int[][] L_AND_G_PATTERNS = new int[20][]; static {
/*  83 */     System.arraycopy(L_PATTERNS, 0, L_AND_G_PATTERNS, 0, 10);
/*  84 */     for (int i = 10; i < 20; i++) {
/*  85 */       int[] widths = L_PATTERNS[i - 10];
/*  86 */       int[] reversedWidths = new int[widths.length];
/*  87 */       for (int j = 0; j < widths.length; j++) {
/*  88 */         reversedWidths[j] = widths[widths.length - j - 1];
/*     */       }
/*  90 */       L_AND_G_PATTERNS[i] = reversedWidths;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
/* 100 */   private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();
/* 101 */   private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
/*     */ 
/*     */   
/*     */   static int[] findStartGuardPattern(BitArray row) throws NotFoundException {
/* 105 */     boolean foundStart = false;
/* 106 */     int[] startRange = null;
/* 107 */     int nextStart = 0;
/* 108 */     int[] counters = new int[START_END_PATTERN.length];
/* 109 */     while (!foundStart) {
/* 110 */       Arrays.fill(counters, 0, START_END_PATTERN.length, 0);
/* 111 */       startRange = findGuardPattern(row, nextStart, false, START_END_PATTERN, counters);
/* 112 */       int start = startRange[0];
/* 113 */       nextStart = startRange[1];
/*     */ 
/*     */ 
/*     */       
/* 117 */       int quietStart = start - nextStart - start;
/* 118 */       if (quietStart >= 0) {
/* 119 */         foundStart = row.isRange(quietStart, start, false);
/*     */       }
/*     */     } 
/* 122 */     return startRange;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
/* 128 */     return decodeRow(rowNumber, row, findStartGuardPattern(row), hints);
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
/*     */   public Result decodeRow(int rowNumber, BitArray row, int[] startGuardRange, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
/* 152 */     ResultPointCallback resultPointCallback = (hints == null) ? null : (ResultPointCallback)hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/*     */     
/* 154 */     if (resultPointCallback != null) {
/* 155 */       resultPointCallback.foundPossibleResultPoint(new ResultPoint((startGuardRange[0] + startGuardRange[1]) / 2.0F, rowNumber));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 160 */     StringBuilder result = this.decodeRowStringBuffer;
/* 161 */     result.setLength(0);
/* 162 */     int endStart = decodeMiddle(row, startGuardRange, result);
/*     */     
/* 164 */     if (resultPointCallback != null) {
/* 165 */       resultPointCallback.foundPossibleResultPoint(new ResultPoint(endStart, rowNumber));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 170 */     int[] endRange = decodeEnd(row, endStart);
/*     */     
/* 172 */     if (resultPointCallback != null) {
/* 173 */       resultPointCallback.foundPossibleResultPoint(new ResultPoint((endRange[0] + endRange[1]) / 2.0F, rowNumber));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 181 */     int end = endRange[1];
/* 182 */     int quietEnd = end + end - endRange[0];
/* 183 */     if (quietEnd >= row.getSize() || !row.isRange(end, quietEnd, false)) {
/* 184 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 187 */     String resultString = result.toString();
/*     */     
/* 189 */     if (resultString.length() < 8) {
/* 190 */       throw FormatException.getFormatInstance();
/*     */     }
/* 192 */     if (!checkChecksum(resultString)) {
/* 193 */       throw ChecksumException.getChecksumInstance();
/*     */     }
/*     */     
/* 196 */     float left = (startGuardRange[1] + startGuardRange[0]) / 2.0F;
/* 197 */     float right = (endRange[1] + endRange[0]) / 2.0F;
/* 198 */     BarcodeFormat format = getBarcodeFormat();
/* 199 */     Result decodeResult = new Result(resultString, null, new ResultPoint[] { new ResultPoint(left, rowNumber), new ResultPoint(right, rowNumber) }format);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 206 */     int extensionLength = 0;
/*     */     
/*     */     try {
/* 209 */       Result extensionResult = this.extensionReader.decodeRow(rowNumber, row, endRange[1]);
/* 210 */       decodeResult.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, extensionResult.getText());
/* 211 */       decodeResult.putAllMetadata(extensionResult.getResultMetadata());
/* 212 */       decodeResult.addResultPoints(extensionResult.getResultPoints());
/* 213 */       extensionLength = extensionResult.getText().length();
/* 214 */     } catch (ReaderException readerException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     int[] allowedExtensions = (hints == null) ? null : (int[])hints.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
/* 220 */     if (allowedExtensions != null) {
/* 221 */       boolean valid = false;
/* 222 */       for (int length : allowedExtensions) {
/* 223 */         if (extensionLength == length) {
/* 224 */           valid = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 228 */       if (!valid) {
/* 229 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */     } 
/*     */     
/* 233 */     if (format == BarcodeFormat.EAN_13 || format == BarcodeFormat.UPC_A) {
/* 234 */       String countryID = this.eanManSupport.lookupCountryIdentifier(resultString);
/* 235 */       if (countryID != null) {
/* 236 */         decodeResult.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, countryID);
/*     */       }
/*     */     } 
/*     */     
/* 240 */     return decodeResult;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean checkChecksum(String s) throws FormatException {
/* 249 */     return checkStandardUPCEANChecksum(s);
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
/*     */   static boolean checkStandardUPCEANChecksum(CharSequence s) throws FormatException {
/* 261 */     int length = s.length();
/* 262 */     if (length == 0) {
/* 263 */       return false;
/*     */     }
/*     */     
/* 266 */     int sum = 0; int i;
/* 267 */     for (i = length - 2; i >= 0; i -= 2) {
/* 268 */       int digit = s.charAt(i) - 48;
/* 269 */       if (digit < 0 || digit > 9) {
/* 270 */         throw FormatException.getFormatInstance();
/*     */       }
/* 272 */       sum += digit;
/*     */     } 
/* 274 */     sum *= 3;
/* 275 */     for (i = length - 1; i >= 0; i -= 2) {
/* 276 */       int digit = s.charAt(i) - 48;
/* 277 */       if (digit < 0 || digit > 9) {
/* 278 */         throw FormatException.getFormatInstance();
/*     */       }
/* 280 */       sum += digit;
/*     */     } 
/* 282 */     return (sum % 10 == 0);
/*     */   }
/*     */   
/*     */   int[] decodeEnd(BitArray row, int endStart) throws NotFoundException {
/* 286 */     return findGuardPattern(row, endStart, false, START_END_PATTERN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int[] findGuardPattern(BitArray row, int rowOffset, boolean whiteFirst, int[] pattern) throws NotFoundException {
/* 293 */     return findGuardPattern(row, rowOffset, whiteFirst, pattern, new int[pattern.length]);
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
/*     */   private static int[] findGuardPattern(BitArray row, int rowOffset, boolean whiteFirst, int[] pattern, int[] counters) throws NotFoundException {
/* 312 */     int patternLength = pattern.length;
/* 313 */     int width = row.getSize();
/* 314 */     boolean isWhite = whiteFirst;
/* 315 */     rowOffset = whiteFirst ? row.getNextUnset(rowOffset) : row.getNextSet(rowOffset);
/* 316 */     int counterPosition = 0;
/* 317 */     int patternStart = rowOffset;
/* 318 */     for (int x = rowOffset; x < width; x++) {
/* 319 */       if (row.get(x) ^ isWhite) {
/* 320 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 322 */         if (counterPosition == patternLength - 1) {
/* 323 */           if (patternMatchVariance(counters, pattern, 0.7F) < 0.48F) {
/* 324 */             return new int[] { patternStart, x };
/*     */           }
/* 326 */           patternStart += counters[0] + counters[1];
/* 327 */           System.arraycopy(counters, 2, counters, 0, patternLength - 2);
/* 328 */           counters[patternLength - 2] = 0;
/* 329 */           counters[patternLength - 1] = 0;
/* 330 */           counterPosition--;
/*     */         } else {
/* 332 */           counterPosition++;
/*     */         } 
/* 334 */         counters[counterPosition] = 1;
/* 335 */         isWhite = !isWhite;
/*     */       } 
/*     */     } 
/* 338 */     throw NotFoundException.getNotFoundInstance();
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
/*     */   static int decodeDigit(BitArray row, int[] counters, int rowOffset, int[][] patterns) throws NotFoundException {
/* 355 */     recordPattern(row, rowOffset, counters);
/* 356 */     float bestVariance = 0.48F;
/* 357 */     int bestMatch = -1;
/* 358 */     int max = patterns.length;
/* 359 */     for (int i = 0; i < max; i++) {
/* 360 */       int[] pattern = patterns[i];
/* 361 */       float variance = patternMatchVariance(counters, pattern, 0.7F);
/* 362 */       if (variance < bestVariance) {
/* 363 */         bestVariance = variance;
/* 364 */         bestMatch = i;
/*     */       } 
/*     */     } 
/* 367 */     if (bestMatch >= 0) {
/* 368 */       return bestMatch;
/*     */     }
/* 370 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */   
/*     */   abstract BarcodeFormat getBarcodeFormat();
/*     */   
/*     */   protected abstract int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfint, StringBuilder paramStringBuilder) throws NotFoundException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\UPCEANReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */