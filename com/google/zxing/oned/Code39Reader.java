/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultPoint;
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
/*     */ public final class Code39Reader
/*     */   extends OneDReader
/*     */ {
/*     */   static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
/*  40 */   private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   static final int[] CHARACTER_ENCODINGS = new int[] { 52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final int ASTERISK_ENCODING = CHARACTER_ENCODINGS[39];
/*     */   
/*     */   private final boolean usingCheckDigit;
/*     */   
/*     */   private final boolean extendedMode;
/*     */   
/*     */   private final StringBuilder decodeRowResult;
/*     */   
/*     */   private final int[] counters;
/*     */ 
/*     */   
/*     */   public Code39Reader() {
/*  67 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Code39Reader(boolean usingCheckDigit) {
/*  78 */     this(usingCheckDigit, false);
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
/*     */   public Code39Reader(boolean usingCheckDigit, boolean extendedMode) {
/*  92 */     this.usingCheckDigit = usingCheckDigit;
/*  93 */     this.extendedMode = extendedMode;
/*  94 */     this.decodeRowResult = new StringBuilder(20);
/*  95 */     this.counters = new int[9];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
/* 102 */     int[] theCounters = this.counters;
/* 103 */     Arrays.fill(theCounters, 0);
/* 104 */     StringBuilder result = this.decodeRowResult;
/* 105 */     result.setLength(0);
/*     */     
/* 107 */     int[] start = findAsteriskPattern(row, theCounters);
/*     */     
/* 109 */     int nextStart = row.getNextSet(start[1]);
/* 110 */     int end = row.getSize();
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 115 */       recordPattern(row, nextStart, theCounters);
/* 116 */       int pattern = toNarrowWidePattern(theCounters);
/* 117 */       if (pattern < 0) {
/* 118 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 120 */       char decodedChar = patternToChar(pattern);
/* 121 */       result.append(decodedChar);
/* 122 */       int lastStart = nextStart;
/* 123 */       for (int counter : theCounters) {
/* 124 */         nextStart += counter;
/*     */       }
/*     */       
/* 127 */       nextStart = row.getNextSet(nextStart);
/* 128 */       if (decodedChar == '*') {
/* 129 */         String resultString; result.setLength(result.length() - 1);
/*     */ 
/*     */         
/* 132 */         int lastPatternSize = 0;
/* 133 */         for (int counter : theCounters) {
/* 134 */           lastPatternSize += counter;
/*     */         }
/* 136 */         int whiteSpaceAfterEnd = nextStart - lastStart - lastPatternSize;
/*     */ 
/*     */         
/* 139 */         if (nextStart != end && whiteSpaceAfterEnd * 2 < lastPatternSize) {
/* 140 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/*     */         
/* 143 */         if (this.usingCheckDigit) {
/* 144 */           int max = result.length() - 1;
/* 145 */           int total = 0;
/* 146 */           for (int i = 0; i < max; i++) {
/* 147 */             total += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(this.decodeRowResult.charAt(i));
/*     */           }
/* 149 */           if (result.charAt(max) != ALPHABET[total % 43]) {
/* 150 */             throw ChecksumException.getChecksumInstance();
/*     */           }
/* 152 */           result.setLength(max);
/*     */         } 
/*     */         
/* 155 */         if (result.length() == 0)
/*     */         {
/* 157 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/*     */ 
/*     */         
/* 161 */         if (this.extendedMode) {
/* 162 */           resultString = decodeExtended(result);
/*     */         } else {
/* 164 */           resultString = result.toString();
/*     */         } 
/*     */         
/* 167 */         float left = (start[1] + start[0]) / 2.0F;
/* 168 */         float right = lastStart + lastPatternSize / 2.0F;
/* 169 */         return new Result(resultString, null, new ResultPoint[] { new ResultPoint(left, rowNumber), new ResultPoint(right, rowNumber) }BarcodeFormat.CODE_39);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] findAsteriskPattern(BitArray row, int[] counters) throws NotFoundException {
/* 180 */     int width = row.getSize();
/* 181 */     int rowOffset = row.getNextSet(0);
/*     */     
/* 183 */     int counterPosition = 0;
/* 184 */     int patternStart = rowOffset;
/* 185 */     boolean isWhite = false;
/* 186 */     int patternLength = counters.length;
/*     */     
/* 188 */     for (int i = rowOffset; i < width; i++) {
/* 189 */       if (row.get(i) ^ isWhite) {
/* 190 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 192 */         if (counterPosition == patternLength - 1) {
/*     */           
/* 194 */           if (toNarrowWidePattern(counters) == ASTERISK_ENCODING && row
/* 195 */             .isRange(Math.max(0, patternStart - (i - patternStart) / 2), patternStart, false)) {
/* 196 */             return new int[] { patternStart, i };
/*     */           }
/* 198 */           patternStart += counters[0] + counters[1];
/* 199 */           System.arraycopy(counters, 2, counters, 0, patternLength - 2);
/* 200 */           counters[patternLength - 2] = 0;
/* 201 */           counters[patternLength - 1] = 0;
/* 202 */           counterPosition--;
/*     */         } else {
/* 204 */           counterPosition++;
/*     */         } 
/* 206 */         counters[counterPosition] = 1;
/* 207 */         isWhite = !isWhite;
/*     */       } 
/*     */     } 
/* 210 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int toNarrowWidePattern(int[] counters) {
/* 216 */     int numCounters = counters.length;
/* 217 */     int maxNarrowCounter = 0;
/*     */     
/*     */     while (true) {
/* 220 */       int minCounter = Integer.MAX_VALUE;
/* 221 */       for (int counter : counters) {
/* 222 */         if (counter < minCounter && counter > maxNarrowCounter) {
/* 223 */           minCounter = counter;
/*     */         }
/*     */       } 
/* 226 */       maxNarrowCounter = minCounter;
/* 227 */       int wideCounters = 0;
/* 228 */       int totalWideCountersWidth = 0;
/* 229 */       int pattern = 0; int i;
/* 230 */       for (i = 0; i < numCounters; i++) {
/* 231 */         int counter = counters[i];
/* 232 */         if (counter > maxNarrowCounter) {
/* 233 */           pattern |= 1 << numCounters - 1 - i;
/* 234 */           wideCounters++;
/* 235 */           totalWideCountersWidth += counter;
/*     */         } 
/*     */       } 
/* 238 */       if (wideCounters == 3) {
/*     */ 
/*     */ 
/*     */         
/* 242 */         for (i = 0; i < numCounters && wideCounters > 0; i++) {
/* 243 */           int counter = counters[i];
/* 244 */           if (counter > maxNarrowCounter) {
/* 245 */             wideCounters--;
/*     */             
/* 247 */             if (counter * 2 >= totalWideCountersWidth) {
/* 248 */               return -1;
/*     */             }
/*     */           } 
/*     */         } 
/* 252 */         return pattern;
/*     */       } 
/* 254 */       if (wideCounters <= 3)
/* 255 */         return -1; 
/*     */     } 
/*     */   }
/*     */   private static char patternToChar(int pattern) throws NotFoundException {
/* 259 */     for (int i = 0; i < CHARACTER_ENCODINGS.length; i++) {
/* 260 */       if (CHARACTER_ENCODINGS[i] == pattern) {
/* 261 */         return ALPHABET[i];
/*     */       }
/*     */     } 
/* 264 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */   
/*     */   private static String decodeExtended(CharSequence encoded) throws FormatException {
/* 268 */     int length = encoded.length();
/* 269 */     StringBuilder decoded = new StringBuilder(length);
/* 270 */     for (int i = 0; i < length; i++) {
/* 271 */       char c = encoded.charAt(i);
/* 272 */       if (c == '+' || c == '$' || c == '%' || c == '/') {
/* 273 */         char next = encoded.charAt(i + 1);
/* 274 */         char decodedChar = Character.MIN_VALUE;
/* 275 */         switch (c) {
/*     */           
/*     */           case '+':
/* 278 */             if (next >= 'A' && next <= 'Z') {
/* 279 */               decodedChar = (char)(next + 32); break;
/*     */             } 
/* 281 */             throw FormatException.getFormatInstance();
/*     */ 
/*     */ 
/*     */           
/*     */           case '$':
/* 286 */             if (next >= 'A' && next <= 'Z') {
/* 287 */               decodedChar = (char)(next - 64); break;
/*     */             } 
/* 289 */             throw FormatException.getFormatInstance();
/*     */ 
/*     */ 
/*     */           
/*     */           case '%':
/* 294 */             if (next >= 'A' && next <= 'E') {
/* 295 */               decodedChar = (char)(next - 38); break;
/* 296 */             }  if (next >= 'F' && next <= 'W') {
/* 297 */               decodedChar = (char)(next - 11); break;
/*     */             } 
/* 299 */             throw FormatException.getFormatInstance();
/*     */ 
/*     */ 
/*     */           
/*     */           case '/':
/* 304 */             if (next >= 'A' && next <= 'O') {
/* 305 */               decodedChar = (char)(next - 32); break;
/* 306 */             }  if (next == 'Z') {
/* 307 */               decodedChar = ':'; break;
/*     */             } 
/* 309 */             throw FormatException.getFormatInstance();
/*     */         } 
/*     */ 
/*     */         
/* 313 */         decoded.append(decodedChar);
/*     */         
/* 315 */         i++;
/*     */       } else {
/* 317 */         decoded.append(c);
/*     */       } 
/*     */     } 
/* 320 */     return decoded.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\Code39Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */