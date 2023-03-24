/*     */ package com.google.zxing.oned.rss;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.ResultPointCallback;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ public final class RSS14Reader
/*     */   extends AbstractRSSReader
/*     */ {
/*  37 */   private static final int[] OUTSIDE_EVEN_TOTAL_SUBSET = new int[] { 1, 10, 34, 70, 126 };
/*  38 */   private static final int[] INSIDE_ODD_TOTAL_SUBSET = new int[] { 4, 20, 48, 81 };
/*  39 */   private static final int[] OUTSIDE_GSUM = new int[] { 0, 161, 961, 2015, 2715 };
/*  40 */   private static final int[] INSIDE_GSUM = new int[] { 0, 336, 1036, 1516 };
/*  41 */   private static final int[] OUTSIDE_ODD_WIDEST = new int[] { 8, 6, 4, 3, 1 };
/*  42 */   private static final int[] INSIDE_ODD_WIDEST = new int[] { 2, 4, 6, 8 };
/*     */   
/*  44 */   private static final int[][] FINDER_PATTERNS = new int[][] { { 3, 8, 2, 1 }, { 3, 5, 5, 1 }, { 3, 3, 7, 1 }, { 3, 1, 9, 1 }, { 2, 7, 4, 1 }, { 2, 5, 6, 1 }, { 2, 3, 8, 1 }, { 1, 5, 7, 1 }, { 1, 3, 9, 1 } };
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
/*  60 */   private final List<Pair> possibleLeftPairs = new ArrayList<>();
/*  61 */   private final List<Pair> possibleRightPairs = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) throws NotFoundException {
/*  68 */     Pair leftPair = decodePair(row, false, rowNumber, hints);
/*  69 */     addOrTally(this.possibleLeftPairs, leftPair);
/*  70 */     row.reverse();
/*  71 */     Pair rightPair = decodePair(row, true, rowNumber, hints);
/*  72 */     addOrTally(this.possibleRightPairs, rightPair);
/*  73 */     row.reverse();
/*  74 */     int lefSize = this.possibleLeftPairs.size();
/*  75 */     for (int i = 0; i < lefSize; i++) {
/*  76 */       Pair left = this.possibleLeftPairs.get(i);
/*  77 */       if (left.getCount() > 1) {
/*  78 */         int rightSize = this.possibleRightPairs.size();
/*  79 */         for (int j = 0; j < rightSize; j++) {
/*  80 */           Pair right = this.possibleRightPairs.get(j);
/*  81 */           if (right.getCount() > 1 && 
/*  82 */             checkChecksum(left, right)) {
/*  83 */             return constructResult(left, right);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */   
/*     */   private static void addOrTally(Collection<Pair> possiblePairs, Pair pair) {
/*  93 */     if (pair == null) {
/*     */       return;
/*     */     }
/*  96 */     boolean found = false;
/*  97 */     for (Pair other : possiblePairs) {
/*  98 */       if (other.getValue() == pair.getValue()) {
/*  99 */         other.incrementCount();
/* 100 */         found = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 104 */     if (!found) {
/* 105 */       possiblePairs.add(pair);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 111 */     this.possibleLeftPairs.clear();
/* 112 */     this.possibleRightPairs.clear();
/*     */   }
/*     */   
/*     */   private static Result constructResult(Pair leftPair, Pair rightPair) {
/* 116 */     long symbolValue = 4537077L * leftPair.getValue() + rightPair.getValue();
/* 117 */     String text = String.valueOf(symbolValue);
/*     */     
/* 119 */     StringBuilder buffer = new StringBuilder(14);
/* 120 */     for (int i = 13 - text.length(); i > 0; i--) {
/* 121 */       buffer.append('0');
/*     */     }
/* 123 */     buffer.append(text);
/*     */     
/* 125 */     int checkDigit = 0;
/* 126 */     for (int j = 0; j < 13; j++) {
/* 127 */       int digit = buffer.charAt(j) - 48;
/* 128 */       checkDigit += ((j & 0x1) == 0) ? (3 * digit) : digit;
/*     */     } 
/* 130 */     checkDigit = 10 - checkDigit % 10;
/* 131 */     if (checkDigit == 10) {
/* 132 */       checkDigit = 0;
/*     */     }
/* 134 */     buffer.append(checkDigit);
/*     */     
/* 136 */     ResultPoint[] leftPoints = leftPair.getFinderPattern().getResultPoints();
/* 137 */     ResultPoint[] rightPoints = rightPair.getFinderPattern().getResultPoints();
/* 138 */     return new Result(
/* 139 */         String.valueOf(buffer.toString()), null, new ResultPoint[] { leftPoints[0], leftPoints[1], rightPoints[0], rightPoints[1] }, BarcodeFormat.RSS_14);
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
/*     */   private static boolean checkChecksum(Pair leftPair, Pair rightPair) {
/* 151 */     int checkValue = (leftPair.getChecksumPortion() + 16 * rightPair.getChecksumPortion()) % 79;
/*     */     
/* 153 */     int targetCheckValue = 9 * leftPair.getFinderPattern().getValue() + rightPair.getFinderPattern().getValue();
/* 154 */     if (targetCheckValue > 72) {
/* 155 */       targetCheckValue--;
/*     */     }
/* 157 */     if (targetCheckValue > 8) {
/* 158 */       targetCheckValue--;
/*     */     }
/* 160 */     return (checkValue == targetCheckValue);
/*     */   }
/*     */   
/*     */   private Pair decodePair(BitArray row, boolean right, int rowNumber, Map<DecodeHintType, ?> hints) {
/*     */     try {
/* 165 */       int[] startEnd = findFinderPattern(row, 0, right);
/* 166 */       FinderPattern pattern = parseFoundFinderPattern(row, rowNumber, right, startEnd);
/*     */ 
/*     */       
/* 169 */       ResultPointCallback resultPointCallback = (hints == null) ? null : (ResultPointCallback)hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/*     */       
/* 171 */       if (resultPointCallback != null) {
/* 172 */         float center = (startEnd[0] + startEnd[1]) / 2.0F;
/* 173 */         if (right)
/*     */         {
/* 175 */           center = (row.getSize() - 1) - center;
/*     */         }
/* 177 */         resultPointCallback.foundPossibleResultPoint(new ResultPoint(center, rowNumber));
/*     */       } 
/*     */       
/* 180 */       DataCharacter outside = decodeDataCharacter(row, pattern, true);
/* 181 */       DataCharacter inside = decodeDataCharacter(row, pattern, false);
/* 182 */       return new Pair(1597 * outside.getValue() + inside.getValue(), outside
/* 183 */           .getChecksumPortion() + 4 * inside.getChecksumPortion(), pattern);
/*     */     }
/* 185 */     catch (NotFoundException ignored) {
/* 186 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DataCharacter decodeDataCharacter(BitArray row, FinderPattern pattern, boolean outsideChar) throws NotFoundException {
/* 193 */     int[] counters = getDataCharacterCounters();
/* 194 */     counters[0] = 0;
/* 195 */     counters[1] = 0;
/* 196 */     counters[2] = 0;
/* 197 */     counters[3] = 0;
/* 198 */     counters[4] = 0;
/* 199 */     counters[5] = 0;
/* 200 */     counters[6] = 0;
/* 201 */     counters[7] = 0;
/*     */     
/* 203 */     if (outsideChar) {
/* 204 */       recordPatternInReverse(row, pattern.getStartEnd()[0], counters);
/*     */     } else {
/* 206 */       recordPattern(row, pattern.getStartEnd()[1] + 1, counters);
/*     */       
/* 208 */       for (int m = 0, n = counters.length - 1; m < n; m++, n--) {
/* 209 */         int temp = counters[m];
/* 210 */         counters[m] = counters[n];
/* 211 */         counters[n] = temp;
/*     */       } 
/*     */     } 
/*     */     
/* 215 */     int numModules = outsideChar ? 16 : 15;
/* 216 */     float elementWidth = count(counters) / numModules;
/*     */     
/* 218 */     int[] oddCounts = getOddCounts();
/* 219 */     int[] evenCounts = getEvenCounts();
/* 220 */     float[] oddRoundingErrors = getOddRoundingErrors();
/* 221 */     float[] evenRoundingErrors = getEvenRoundingErrors();
/*     */     
/* 223 */     for (int i = 0; i < counters.length; i++) {
/* 224 */       float value = counters[i] / elementWidth;
/* 225 */       int count = (int)(value + 0.5F);
/* 226 */       if (count < 1) {
/* 227 */         count = 1;
/* 228 */       } else if (count > 8) {
/* 229 */         count = 8;
/*     */       } 
/* 231 */       int offset = i / 2;
/* 232 */       if ((i & 0x1) == 0) {
/* 233 */         oddCounts[offset] = count;
/* 234 */         oddRoundingErrors[offset] = value - count;
/*     */       } else {
/* 236 */         evenCounts[offset] = count;
/* 237 */         evenRoundingErrors[offset] = value - count;
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     adjustOddEvenCounts(outsideChar, numModules);
/*     */     
/* 243 */     int oddSum = 0;
/* 244 */     int oddChecksumPortion = 0;
/* 245 */     for (int j = oddCounts.length - 1; j >= 0; j--) {
/* 246 */       oddChecksumPortion *= 9;
/* 247 */       oddChecksumPortion += oddCounts[j];
/* 248 */       oddSum += oddCounts[j];
/*     */     } 
/* 250 */     int evenChecksumPortion = 0;
/* 251 */     int evenSum = 0;
/* 252 */     for (int k = evenCounts.length - 1; k >= 0; k--) {
/* 253 */       evenChecksumPortion *= 9;
/* 254 */       evenChecksumPortion += evenCounts[k];
/* 255 */       evenSum += evenCounts[k];
/*     */     } 
/* 257 */     int checksumPortion = oddChecksumPortion + 3 * evenChecksumPortion;
/*     */     
/* 259 */     if (outsideChar) {
/* 260 */       if ((oddSum & 0x1) != 0 || oddSum > 12 || oddSum < 4) {
/* 261 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 263 */       int m = (12 - oddSum) / 2;
/* 264 */       int n = OUTSIDE_ODD_WIDEST[m];
/* 265 */       int i1 = 9 - n;
/* 266 */       int i2 = RSSUtils.getRSSvalue(oddCounts, n, false);
/* 267 */       int i3 = RSSUtils.getRSSvalue(evenCounts, i1, true);
/* 268 */       int tEven = OUTSIDE_EVEN_TOTAL_SUBSET[m];
/* 269 */       int i4 = OUTSIDE_GSUM[m];
/* 270 */       return new DataCharacter(i2 * tEven + i3 + i4, checksumPortion);
/*     */     } 
/* 272 */     if ((evenSum & 0x1) != 0 || evenSum > 10 || evenSum < 4) {
/* 273 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 275 */     int group = (10 - evenSum) / 2;
/* 276 */     int oddWidest = INSIDE_ODD_WIDEST[group];
/* 277 */     int evenWidest = 9 - oddWidest;
/* 278 */     int vOdd = RSSUtils.getRSSvalue(oddCounts, oddWidest, true);
/* 279 */     int vEven = RSSUtils.getRSSvalue(evenCounts, evenWidest, false);
/* 280 */     int tOdd = INSIDE_ODD_TOTAL_SUBSET[group];
/* 281 */     int gSum = INSIDE_GSUM[group];
/* 282 */     return new DataCharacter(vEven * tOdd + vOdd + gSum, checksumPortion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] findFinderPattern(BitArray row, int rowOffset, boolean rightFinderPattern) throws NotFoundException {
/* 290 */     int[] counters = getDecodeFinderCounters();
/* 291 */     counters[0] = 0;
/* 292 */     counters[1] = 0;
/* 293 */     counters[2] = 0;
/* 294 */     counters[3] = 0;
/*     */     
/* 296 */     int width = row.getSize();
/* 297 */     boolean isWhite = false;
/* 298 */     while (rowOffset < width) {
/* 299 */       isWhite = !row.get(rowOffset);
/* 300 */       if (rightFinderPattern == isWhite) {
/*     */         break;
/*     */       }
/*     */       
/* 304 */       rowOffset++;
/*     */     } 
/*     */     
/* 307 */     int counterPosition = 0;
/* 308 */     int patternStart = rowOffset;
/* 309 */     for (int x = rowOffset; x < width; x++) {
/* 310 */       if (row.get(x) ^ isWhite) {
/* 311 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 313 */         if (counterPosition == 3) {
/* 314 */           if (isFinderPattern(counters)) {
/* 315 */             return new int[] { patternStart, x };
/*     */           }
/* 317 */           patternStart += counters[0] + counters[1];
/* 318 */           counters[0] = counters[2];
/* 319 */           counters[1] = counters[3];
/* 320 */           counters[2] = 0;
/* 321 */           counters[3] = 0;
/* 322 */           counterPosition--;
/*     */         } else {
/* 324 */           counterPosition++;
/*     */         } 
/* 326 */         counters[counterPosition] = 1;
/* 327 */         isWhite = !isWhite;
/*     */       } 
/*     */     } 
/* 330 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FinderPattern parseFoundFinderPattern(BitArray row, int rowNumber, boolean right, int[] startEnd) throws NotFoundException {
/* 337 */     boolean firstIsBlack = row.get(startEnd[0]);
/* 338 */     int firstElementStart = startEnd[0] - 1;
/*     */     
/* 340 */     while (firstElementStart >= 0 && firstIsBlack ^ row.get(firstElementStart)) {
/* 341 */       firstElementStart--;
/*     */     }
/* 343 */     firstElementStart++;
/* 344 */     int firstCounter = startEnd[0] - firstElementStart;
/*     */     
/* 346 */     int[] counters = getDecodeFinderCounters();
/* 347 */     System.arraycopy(counters, 0, counters, 1, counters.length - 1);
/* 348 */     counters[0] = firstCounter;
/* 349 */     int value = parseFinderValue(counters, FINDER_PATTERNS);
/* 350 */     int start = firstElementStart;
/* 351 */     int end = startEnd[1];
/* 352 */     if (right) {
/*     */       
/* 354 */       start = row.getSize() - 1 - start;
/* 355 */       end = row.getSize() - 1 - end;
/*     */     } 
/* 357 */     return new FinderPattern(value, new int[] { firstElementStart, startEnd[1] }, start, end, rowNumber);
/*     */   }
/*     */ 
/*     */   
/*     */   private void adjustOddEvenCounts(boolean outsideChar, int numModules) throws NotFoundException {
/* 362 */     int oddSum = count(getOddCounts());
/* 363 */     int evenSum = count(getEvenCounts());
/* 364 */     int mismatch = oddSum + evenSum - numModules;
/* 365 */     boolean oddParityBad = ((oddSum & 0x1) == (outsideChar ? 1 : 0));
/* 366 */     boolean evenParityBad = ((evenSum & 0x1) == 1);
/*     */     
/* 368 */     boolean incrementOdd = false;
/* 369 */     boolean decrementOdd = false;
/* 370 */     boolean incrementEven = false;
/* 371 */     boolean decrementEven = false;
/*     */     
/* 373 */     if (outsideChar) {
/* 374 */       if (oddSum > 12) {
/* 375 */         decrementOdd = true;
/* 376 */       } else if (oddSum < 4) {
/* 377 */         incrementOdd = true;
/*     */       } 
/* 379 */       if (evenSum > 12) {
/* 380 */         decrementEven = true;
/* 381 */       } else if (evenSum < 4) {
/* 382 */         incrementEven = true;
/*     */       } 
/*     */     } else {
/* 385 */       if (oddSum > 11) {
/* 386 */         decrementOdd = true;
/* 387 */       } else if (oddSum < 5) {
/* 388 */         incrementOdd = true;
/*     */       } 
/* 390 */       if (evenSum > 10) {
/* 391 */         decrementEven = true;
/* 392 */       } else if (evenSum < 4) {
/* 393 */         incrementEven = true;
/*     */       } 
/*     */     } 
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
/* 409 */     if (mismatch == 1) {
/* 410 */       if (oddParityBad) {
/* 411 */         if (evenParityBad) {
/* 412 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 414 */         decrementOdd = true;
/*     */       } else {
/* 416 */         if (!evenParityBad) {
/* 417 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 419 */         decrementEven = true;
/*     */       } 
/* 421 */     } else if (mismatch == -1) {
/* 422 */       if (oddParityBad) {
/* 423 */         if (evenParityBad) {
/* 424 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 426 */         incrementOdd = true;
/*     */       } else {
/* 428 */         if (!evenParityBad) {
/* 429 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 431 */         incrementEven = true;
/*     */       } 
/* 433 */     } else if (mismatch == 0) {
/* 434 */       if (oddParityBad) {
/* 435 */         if (!evenParityBad) {
/* 436 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/*     */         
/* 439 */         if (oddSum < evenSum) {
/* 440 */           incrementOdd = true;
/* 441 */           decrementEven = true;
/*     */         } else {
/* 443 */           decrementOdd = true;
/* 444 */           incrementEven = true;
/*     */         }
/*     */       
/* 447 */       } else if (evenParityBad) {
/* 448 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 453 */       throw NotFoundException.getNotFoundInstance();
/*     */     } 
/*     */     
/* 456 */     if (incrementOdd) {
/* 457 */       if (decrementOdd) {
/* 458 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 460 */       increment(getOddCounts(), getOddRoundingErrors());
/*     */     } 
/* 462 */     if (decrementOdd) {
/* 463 */       decrement(getOddCounts(), getOddRoundingErrors());
/*     */     }
/* 465 */     if (incrementEven) {
/* 466 */       if (decrementEven) {
/* 467 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 469 */       increment(getEvenCounts(), getOddRoundingErrors());
/*     */     } 
/* 471 */     if (decrementEven)
/* 472 */       decrement(getEvenCounts(), getEvenRoundingErrors()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\RSS14Reader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */