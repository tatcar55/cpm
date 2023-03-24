/*     */ package com.google.zxing.oned.rss.expanded;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import com.google.zxing.oned.rss.AbstractRSSReader;
/*     */ import com.google.zxing.oned.rss.DataCharacter;
/*     */ import com.google.zxing.oned.rss.FinderPattern;
/*     */ import com.google.zxing.oned.rss.RSSUtils;
/*     */ import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
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
/*     */ public final class RSSExpandedReader
/*     */   extends AbstractRSSReader
/*     */ {
/*  54 */   private static final int[] SYMBOL_WIDEST = new int[] { 7, 5, 4, 3, 1 };
/*  55 */   private static final int[] EVEN_TOTAL_SUBSET = new int[] { 4, 20, 52, 104, 204 };
/*  56 */   private static final int[] GSUM = new int[] { 0, 348, 1388, 2948, 3988 };
/*     */   
/*  58 */   private static final int[][] FINDER_PATTERNS = new int[][] { { 1, 8, 4, 1 }, { 3, 6, 4, 1 }, { 3, 4, 6, 1 }, { 3, 2, 8, 1 }, { 2, 6, 5, 1 }, { 2, 2, 9, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final int[][] WEIGHTS = new int[][] { { 1, 3, 9, 27, 81, 32, 96, 77 }, { 20, 60, 180, 118, 143, 7, 21, 63 }, { 189, 145, 13, 39, 117, 140, 209, 205 }, { 193, 157, 49, 147, 19, 57, 171, 91 }, { 62, 186, 136, 197, 169, 85, 44, 132 }, { 185, 133, 188, 142, 4, 12, 36, 108 }, { 113, 128, 173, 97, 80, 29, 87, 50 }, { 150, 28, 84, 41, 123, 158, 52, 156 }, { 46, 138, 203, 187, 139, 206, 196, 166 }, { 76, 17, 51, 153, 37, 111, 122, 155 }, { 43, 129, 176, 106, 107, 110, 119, 146 }, { 16, 48, 144, 10, 30, 90, 59, 177 }, { 109, 116, 137, 200, 178, 112, 125, 164 }, { 70, 210, 208, 202, 184, 130, 179, 115 }, { 134, 191, 151, 31, 93, 68, 204, 190 }, { 148, 22, 66, 198, 172, 94, 71, 2 }, { 6, 18, 54, 162, 64, 192, 154, 40 }, { 120, 149, 25, 75, 14, 42, 126, 167 }, { 79, 26, 78, 23, 69, 207, 199, 175 }, { 103, 98, 83, 38, 114, 131, 182, 124 }, { 161, 61, 183, 127, 170, 88, 53, 159 }, { 55, 165, 73, 8, 24, 72, 5, 15 }, { 45, 135, 194, 160, 58, 174, 100, 89 } };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_A = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_B = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_C = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_D = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_E = 4;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int FINDER_PAT_F = 5;
/*     */ 
/*     */ 
/*     */   
/* 100 */   private static final int[][] FINDER_PATTERN_SEQUENCES = new int[][] { { 0, 0 }, { 0, 1, 1 }, { 0, 2, 1, 3 }, { 0, 4, 1, 3, 2 }, { 0, 4, 1, 3, 3, 5 }, { 0, 4, 1, 3, 4, 5, 5 }, { 0, 0, 1, 1, 2, 2, 3, 3 }, { 0, 0, 1, 1, 2, 2, 3, 4, 4 }, { 0, 0, 1, 1, 2, 2, 3, 4, 5, 5 }, { 0, 0, 1, 1, 2, 3, 3, 4, 4, 5, 5 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int MAX_PAIRS = 11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   private final List<ExpandedPair> pairs = new ArrayList<>(11);
/* 116 */   private final List<ExpandedRow> rows = new ArrayList<>();
/* 117 */   private final int[] startEnd = new int[2];
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean startFromEven;
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decodeRow(int rowNumber, BitArray row, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
/* 126 */     this.pairs.clear();
/* 127 */     this.startFromEven = false;
/*     */     try {
/* 129 */       List<ExpandedPair> pairs = decodeRow2pairs(rowNumber, row);
/* 130 */       return constructResult(pairs);
/* 131 */     } catch (NotFoundException notFoundException) {
/*     */ 
/*     */ 
/*     */       
/* 135 */       this.pairs.clear();
/* 136 */       this.startFromEven = true;
/* 137 */       List<ExpandedPair> pairs = decodeRow2pairs(rowNumber, row);
/* 138 */       return constructResult(pairs);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reset() {
/* 143 */     this.pairs.clear();
/* 144 */     this.rows.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   List<ExpandedPair> decodeRow2pairs(int rowNumber, BitArray row) throws NotFoundException {
/*     */     try {
/*     */       while (true) {
/* 151 */         ExpandedPair nextPair = retrieveNextPair(row, this.pairs, rowNumber);
/* 152 */         this.pairs.add(nextPair);
/*     */       }
/*     */     
/* 155 */     } catch (NotFoundException nfe) {
/* 156 */       if (this.pairs.isEmpty()) {
/* 157 */         throw nfe;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 162 */       if (checkChecksum()) {
/* 163 */         return this.pairs;
/*     */       }
/*     */       
/* 166 */       boolean tryStackedDecode = !this.rows.isEmpty();
/* 167 */       boolean wasReversed = false;
/* 168 */       storeRow(rowNumber, wasReversed);
/* 169 */       if (tryStackedDecode) {
/*     */ 
/*     */         
/* 172 */         List<ExpandedPair> ps = checkRows(false);
/* 173 */         if (ps != null) {
/* 174 */           return ps;
/*     */         }
/* 176 */         ps = checkRows(true);
/* 177 */         if (ps != null) {
/* 178 */           return ps;
/*     */         }
/*     */       } 
/*     */       
/* 182 */       throw NotFoundException.getNotFoundInstance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ExpandedPair> checkRows(boolean reverse) {
/* 189 */     if (this.rows.size() > 25) {
/* 190 */       this.rows.clear();
/* 191 */       return null;
/*     */     } 
/*     */     
/* 194 */     this.pairs.clear();
/* 195 */     if (reverse) {
/* 196 */       Collections.reverse(this.rows);
/*     */     }
/*     */     
/* 199 */     List<ExpandedPair> ps = null;
/*     */     try {
/* 201 */       ps = checkRows(new ArrayList<>(), 0);
/* 202 */     } catch (NotFoundException notFoundException) {}
/*     */ 
/*     */ 
/*     */     
/* 206 */     if (reverse) {
/* 207 */       Collections.reverse(this.rows);
/*     */     }
/*     */     
/* 210 */     return ps;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<ExpandedPair> checkRows(List<ExpandedRow> collectedRows, int currentRow) throws NotFoundException {
/* 216 */     for (int i = currentRow; i < this.rows.size(); i++) {
/* 217 */       ExpandedRow row = this.rows.get(i);
/* 218 */       this.pairs.clear();
/* 219 */       int size = collectedRows.size();
/* 220 */       for (int j = 0; j < size; j++) {
/* 221 */         this.pairs.addAll(((ExpandedRow)collectedRows.get(j)).getPairs());
/*     */       }
/* 223 */       this.pairs.addAll(row.getPairs());
/*     */       
/* 225 */       if (isValidSequence(this.pairs)) {
/*     */ 
/*     */ 
/*     */         
/* 229 */         if (checkChecksum()) {
/* 230 */           return this.pairs;
/*     */         }
/*     */         
/* 233 */         List<ExpandedRow> rs = new ArrayList<>();
/* 234 */         rs.addAll(collectedRows);
/* 235 */         rs.add(row);
/*     */         
/*     */         try {
/* 238 */           return checkRows(rs, i + 1);
/* 239 */         } catch (NotFoundException notFoundException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 244 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isValidSequence(List<ExpandedPair> pairs) {
/* 250 */     for (int[] sequence : FINDER_PATTERN_SEQUENCES) {
/* 251 */       if (pairs.size() <= sequence.length) {
/*     */ 
/*     */ 
/*     */         
/* 255 */         boolean stop = true;
/* 256 */         for (int j = 0; j < pairs.size(); j++) {
/* 257 */           if (((ExpandedPair)pairs.get(j)).getFinderPattern().getValue() != sequence[j]) {
/* 258 */             stop = false;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 263 */         if (stop) {
/* 264 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeRow(int rowNumber, boolean wasReversed) {
/* 273 */     int insertPos = 0;
/* 274 */     boolean prevIsSame = false;
/* 275 */     boolean nextIsSame = false;
/* 276 */     while (insertPos < this.rows.size()) {
/* 277 */       ExpandedRow erow = this.rows.get(insertPos);
/* 278 */       if (erow.getRowNumber() > rowNumber) {
/* 279 */         nextIsSame = erow.isEquivalent(this.pairs);
/*     */         break;
/*     */       } 
/* 282 */       prevIsSame = erow.isEquivalent(this.pairs);
/* 283 */       insertPos++;
/*     */     } 
/* 285 */     if (nextIsSame || prevIsSame) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 294 */     if (isPartialRow(this.pairs, this.rows)) {
/*     */       return;
/*     */     }
/*     */     
/* 298 */     this.rows.add(insertPos, new ExpandedRow(this.pairs, rowNumber, wasReversed));
/*     */     
/* 300 */     removePartialRows(this.pairs, this.rows);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void removePartialRows(List<ExpandedPair> pairs, List<ExpandedRow> rows) {
/* 305 */     for (Iterator<ExpandedRow> iterator = rows.iterator(); iterator.hasNext(); ) {
/* 306 */       ExpandedRow r = iterator.next();
/* 307 */       if (r.getPairs().size() == pairs.size()) {
/*     */         continue;
/*     */       }
/* 310 */       boolean allFound = true;
/* 311 */       for (ExpandedPair p : r.getPairs()) {
/* 312 */         boolean found = false;
/* 313 */         for (ExpandedPair pp : pairs) {
/* 314 */           if (p.equals(pp)) {
/* 315 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 319 */         if (!found) {
/* 320 */           allFound = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 324 */       if (allFound)
/*     */       {
/* 326 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isPartialRow(Iterable<ExpandedPair> pairs, Iterable<ExpandedRow> rows) {
/* 333 */     for (ExpandedRow r : rows) {
/* 334 */       boolean allFound = true;
/* 335 */       for (ExpandedPair p : pairs) {
/* 336 */         boolean found = false;
/* 337 */         for (ExpandedPair pp : r.getPairs()) {
/* 338 */           if (p.equals(pp)) {
/* 339 */             found = true;
/*     */             break;
/*     */           } 
/*     */         } 
/* 343 */         if (!found) {
/* 344 */           allFound = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 348 */       if (allFound)
/*     */       {
/* 350 */         return true;
/*     */       }
/*     */     } 
/* 353 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   List<ExpandedRow> getRows() {
/* 358 */     return this.rows;
/*     */   }
/*     */ 
/*     */   
/*     */   static Result constructResult(List<ExpandedPair> pairs) throws NotFoundException, FormatException {
/* 363 */     BitArray binary = BitArrayBuilder.buildBitArray(pairs);
/*     */     
/* 365 */     AbstractExpandedDecoder decoder = AbstractExpandedDecoder.createDecoder(binary);
/* 366 */     String resultingString = decoder.parseInformation();
/*     */     
/* 368 */     ResultPoint[] firstPoints = ((ExpandedPair)pairs.get(0)).getFinderPattern().getResultPoints();
/* 369 */     ResultPoint[] lastPoints = ((ExpandedPair)pairs.get(pairs.size() - 1)).getFinderPattern().getResultPoints();
/*     */     
/* 371 */     return new Result(resultingString, null, new ResultPoint[] { firstPoints[0], firstPoints[1], lastPoints[0], lastPoints[1] }, BarcodeFormat.RSS_EXPANDED);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean checkChecksum() {
/* 380 */     ExpandedPair firstPair = this.pairs.get(0);
/* 381 */     DataCharacter checkCharacter = firstPair.getLeftChar();
/* 382 */     DataCharacter firstCharacter = firstPair.getRightChar();
/*     */     
/* 384 */     if (firstCharacter == null) {
/* 385 */       return false;
/*     */     }
/*     */     
/* 388 */     int checksum = firstCharacter.getChecksumPortion();
/* 389 */     int s = 2;
/*     */     
/* 391 */     for (int i = 1; i < this.pairs.size(); i++) {
/* 392 */       ExpandedPair currentPair = this.pairs.get(i);
/* 393 */       checksum += currentPair.getLeftChar().getChecksumPortion();
/* 394 */       s++;
/* 395 */       DataCharacter currentRightChar = currentPair.getRightChar();
/* 396 */       if (currentRightChar != null) {
/* 397 */         checksum += currentRightChar.getChecksumPortion();
/* 398 */         s++;
/*     */       } 
/*     */     } 
/*     */     
/* 402 */     checksum %= 211;
/*     */     
/* 404 */     int checkCharacterValue = 211 * (s - 4) + checksum;
/*     */     
/* 406 */     return (checkCharacterValue == checkCharacter.getValue());
/*     */   }
/*     */   
/*     */   private static int getNextSecondBar(BitArray row, int initialPos) {
/*     */     int currentPos;
/* 411 */     if (row.get(initialPos)) {
/* 412 */       currentPos = row.getNextUnset(initialPos);
/* 413 */       currentPos = row.getNextSet(currentPos);
/*     */     } else {
/* 415 */       currentPos = row.getNextSet(initialPos);
/* 416 */       currentPos = row.getNextUnset(currentPos);
/*     */     } 
/* 418 */     return currentPos;
/*     */   }
/*     */   
/*     */   ExpandedPair retrieveNextPair(BitArray row, List<ExpandedPair> previousPairs, int rowNumber) throws NotFoundException {
/*     */     FinderPattern pattern;
/*     */     DataCharacter rightChar;
/* 424 */     boolean isOddPattern = (previousPairs.size() % 2 == 0);
/* 425 */     if (this.startFromEven) {
/* 426 */       isOddPattern = !isOddPattern;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 431 */     boolean keepFinding = true;
/* 432 */     int forcedOffset = -1;
/*     */     do {
/* 434 */       findNextPair(row, previousPairs, forcedOffset);
/* 435 */       pattern = parseFoundFinderPattern(row, rowNumber, isOddPattern);
/* 436 */       if (pattern == null) {
/* 437 */         forcedOffset = getNextSecondBar(row, this.startEnd[0]);
/*     */       } else {
/* 439 */         keepFinding = false;
/*     */       } 
/* 441 */     } while (keepFinding);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     DataCharacter leftChar = decodeDataCharacter(row, pattern, isOddPattern, true);
/*     */     
/* 448 */     if (!previousPairs.isEmpty() && ((ExpandedPair)previousPairs.get(previousPairs.size() - 1)).mustBeLast()) {
/* 449 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 454 */       rightChar = decodeDataCharacter(row, pattern, isOddPattern, false);
/* 455 */     } catch (NotFoundException ignored) {
/* 456 */       rightChar = null;
/*     */     } 
/* 458 */     boolean mayBeLast = true;
/* 459 */     return new ExpandedPair(leftChar, rightChar, pattern, mayBeLast);
/*     */   }
/*     */ 
/*     */   
/*     */   private void findNextPair(BitArray row, List<ExpandedPair> previousPairs, int forcedOffset) throws NotFoundException {
/* 464 */     int rowOffset, counters[] = getDecodeFinderCounters();
/* 465 */     counters[0] = 0;
/* 466 */     counters[1] = 0;
/* 467 */     counters[2] = 0;
/* 468 */     counters[3] = 0;
/*     */     
/* 470 */     int width = row.getSize();
/*     */ 
/*     */     
/* 473 */     if (forcedOffset >= 0) {
/* 474 */       rowOffset = forcedOffset;
/* 475 */     } else if (previousPairs.isEmpty()) {
/* 476 */       rowOffset = 0;
/*     */     } else {
/* 478 */       ExpandedPair lastPair = previousPairs.get(previousPairs.size() - 1);
/* 479 */       rowOffset = lastPair.getFinderPattern().getStartEnd()[1];
/*     */     } 
/* 481 */     boolean searchingEvenPair = (previousPairs.size() % 2 != 0);
/* 482 */     if (this.startFromEven) {
/* 483 */       searchingEvenPair = !searchingEvenPair;
/*     */     }
/*     */     
/* 486 */     boolean isWhite = false;
/* 487 */     while (rowOffset < width) {
/* 488 */       isWhite = !row.get(rowOffset);
/* 489 */       if (!isWhite) {
/*     */         break;
/*     */       }
/* 492 */       rowOffset++;
/*     */     } 
/*     */     
/* 495 */     int counterPosition = 0;
/* 496 */     int patternStart = rowOffset;
/* 497 */     for (int x = rowOffset; x < width; x++) {
/* 498 */       if (row.get(x) ^ isWhite) {
/* 499 */         counters[counterPosition] = counters[counterPosition] + 1;
/*     */       } else {
/* 501 */         if (counterPosition == 3) {
/* 502 */           if (searchingEvenPair) {
/* 503 */             reverseCounters(counters);
/*     */           }
/*     */           
/* 506 */           if (isFinderPattern(counters)) {
/* 507 */             this.startEnd[0] = patternStart;
/* 508 */             this.startEnd[1] = x;
/*     */             
/*     */             return;
/*     */           } 
/* 512 */           if (searchingEvenPair) {
/* 513 */             reverseCounters(counters);
/*     */           }
/*     */           
/* 516 */           patternStart += counters[0] + counters[1];
/* 517 */           counters[0] = counters[2];
/* 518 */           counters[1] = counters[3];
/* 519 */           counters[2] = 0;
/* 520 */           counters[3] = 0;
/* 521 */           counterPosition--;
/*     */         } else {
/* 523 */           counterPosition++;
/*     */         } 
/* 525 */         counters[counterPosition] = 1;
/* 526 */         isWhite = !isWhite;
/*     */       } 
/*     */     } 
/* 529 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */   
/*     */   private static void reverseCounters(int[] counters) {
/* 533 */     int length = counters.length;
/* 534 */     for (int i = 0; i < length / 2; i++) {
/* 535 */       int tmp = counters[i];
/* 536 */       counters[i] = counters[length - i - 1];
/* 537 */       counters[length - i - 1] = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FinderPattern parseFoundFinderPattern(BitArray row, int rowNumber, boolean oddPattern) {
/*     */     int firstCounter, start, end, value;
/* 547 */     if (oddPattern) {
/*     */ 
/*     */       
/* 550 */       int firstElementStart = this.startEnd[0] - 1;
/*     */       
/* 552 */       while (firstElementStart >= 0 && !row.get(firstElementStart)) {
/* 553 */         firstElementStart--;
/*     */       }
/*     */       
/* 556 */       firstElementStart++;
/* 557 */       firstCounter = this.startEnd[0] - firstElementStart;
/* 558 */       start = firstElementStart;
/* 559 */       end = this.startEnd[1];
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 564 */       start = this.startEnd[0];
/*     */       
/* 566 */       end = row.getNextUnset(this.startEnd[1] + 1);
/* 567 */       firstCounter = end - this.startEnd[1];
/*     */     } 
/*     */ 
/*     */     
/* 571 */     int[] counters = getDecodeFinderCounters();
/* 572 */     System.arraycopy(counters, 0, counters, 1, counters.length - 1);
/*     */     
/* 574 */     counters[0] = firstCounter;
/*     */     
/*     */     try {
/* 577 */       value = parseFinderValue(counters, FINDER_PATTERNS);
/* 578 */     } catch (NotFoundException ignored) {
/* 579 */       return null;
/*     */     } 
/* 581 */     return new FinderPattern(value, new int[] { start, end }, start, end, rowNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DataCharacter decodeDataCharacter(BitArray row, FinderPattern pattern, boolean isOddPattern, boolean leftChar) throws NotFoundException {
/* 588 */     int[] counters = getDataCharacterCounters();
/* 589 */     counters[0] = 0;
/* 590 */     counters[1] = 0;
/* 591 */     counters[2] = 0;
/* 592 */     counters[3] = 0;
/* 593 */     counters[4] = 0;
/* 594 */     counters[5] = 0;
/* 595 */     counters[6] = 0;
/* 596 */     counters[7] = 0;
/*     */     
/* 598 */     if (leftChar) {
/* 599 */       recordPatternInReverse(row, pattern.getStartEnd()[0], counters);
/*     */     } else {
/* 601 */       recordPattern(row, pattern.getStartEnd()[1], counters);
/*     */       
/* 603 */       for (int m = 0, n = counters.length - 1; m < n; m++, n--) {
/* 604 */         int temp = counters[m];
/* 605 */         counters[m] = counters[n];
/* 606 */         counters[n] = temp;
/*     */       } 
/*     */     } 
/*     */     
/* 610 */     int numModules = 17;
/* 611 */     float elementWidth = count(counters) / numModules;
/*     */ 
/*     */     
/* 614 */     float expectedElementWidth = (pattern.getStartEnd()[1] - pattern.getStartEnd()[0]) / 15.0F;
/* 615 */     if (Math.abs(elementWidth - expectedElementWidth) / expectedElementWidth > 0.3F) {
/* 616 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 619 */     int[] oddCounts = getOddCounts();
/* 620 */     int[] evenCounts = getEvenCounts();
/* 621 */     float[] oddRoundingErrors = getOddRoundingErrors();
/* 622 */     float[] evenRoundingErrors = getEvenRoundingErrors();
/*     */     
/* 624 */     for (int i = 0; i < counters.length; i++) {
/* 625 */       float f = 1.0F * counters[i] / elementWidth;
/* 626 */       int count = (int)(f + 0.5F);
/* 627 */       if (count < 1) {
/* 628 */         if (f < 0.3F) {
/* 629 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 631 */         count = 1;
/* 632 */       } else if (count > 8) {
/* 633 */         if (f > 8.7F) {
/* 634 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 636 */         count = 8;
/*     */       } 
/* 638 */       int offset = i / 2;
/* 639 */       if ((i & 0x1) == 0) {
/* 640 */         oddCounts[offset] = count;
/* 641 */         oddRoundingErrors[offset] = f - count;
/*     */       } else {
/* 643 */         evenCounts[offset] = count;
/* 644 */         evenRoundingErrors[offset] = f - count;
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     adjustOddEvenCounts(numModules);
/*     */     
/* 650 */     int weightRowNumber = 4 * pattern.getValue() + (isOddPattern ? 0 : 2) + (leftChar ? 0 : 1) - 1;
/*     */     
/* 652 */     int oddSum = 0;
/* 653 */     int oddChecksumPortion = 0;
/* 654 */     for (int j = oddCounts.length - 1; j >= 0; j--) {
/* 655 */       if (isNotA1left(pattern, isOddPattern, leftChar)) {
/* 656 */         int weight = WEIGHTS[weightRowNumber][2 * j];
/* 657 */         oddChecksumPortion += oddCounts[j] * weight;
/*     */       } 
/* 659 */       oddSum += oddCounts[j];
/*     */     } 
/* 661 */     int evenChecksumPortion = 0;
/*     */     
/* 663 */     for (int k = evenCounts.length - 1; k >= 0; k--) {
/* 664 */       if (isNotA1left(pattern, isOddPattern, leftChar)) {
/* 665 */         int weight = WEIGHTS[weightRowNumber][2 * k + 1];
/* 666 */         evenChecksumPortion += evenCounts[k] * weight;
/*     */       } 
/*     */     } 
/*     */     
/* 670 */     int checksumPortion = oddChecksumPortion + evenChecksumPortion;
/*     */     
/* 672 */     if ((oddSum & 0x1) != 0 || oddSum > 13 || oddSum < 4) {
/* 673 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 676 */     int group = (13 - oddSum) / 2;
/* 677 */     int oddWidest = SYMBOL_WIDEST[group];
/* 678 */     int evenWidest = 9 - oddWidest;
/* 679 */     int vOdd = RSSUtils.getRSSvalue(oddCounts, oddWidest, true);
/* 680 */     int vEven = RSSUtils.getRSSvalue(evenCounts, evenWidest, false);
/* 681 */     int tEven = EVEN_TOTAL_SUBSET[group];
/* 682 */     int gSum = GSUM[group];
/* 683 */     int value = vOdd * tEven + vEven + gSum;
/*     */     
/* 685 */     return new DataCharacter(value, checksumPortion);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isNotA1left(FinderPattern pattern, boolean isOddPattern, boolean leftChar) {
/* 690 */     return (pattern.getValue() != 0 || !isOddPattern || !leftChar);
/*     */   }
/*     */ 
/*     */   
/*     */   private void adjustOddEvenCounts(int numModules) throws NotFoundException {
/* 695 */     int oddSum = count(getOddCounts());
/* 696 */     int evenSum = count(getEvenCounts());
/* 697 */     int mismatch = oddSum + evenSum - numModules;
/* 698 */     boolean oddParityBad = ((oddSum & 0x1) == 1);
/* 699 */     boolean evenParityBad = ((evenSum & 0x1) == 0);
/*     */     
/* 701 */     boolean incrementOdd = false;
/* 702 */     boolean decrementOdd = false;
/*     */     
/* 704 */     if (oddSum > 13) {
/* 705 */       decrementOdd = true;
/* 706 */     } else if (oddSum < 4) {
/* 707 */       incrementOdd = true;
/*     */     } 
/* 709 */     boolean incrementEven = false;
/* 710 */     boolean decrementEven = false;
/* 711 */     if (evenSum > 13) {
/* 712 */       decrementEven = true;
/* 713 */     } else if (evenSum < 4) {
/* 714 */       incrementEven = true;
/*     */     } 
/*     */     
/* 717 */     if (mismatch == 1) {
/* 718 */       if (oddParityBad) {
/* 719 */         if (evenParityBad) {
/* 720 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 722 */         decrementOdd = true;
/*     */       } else {
/* 724 */         if (!evenParityBad) {
/* 725 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 727 */         decrementEven = true;
/*     */       } 
/* 729 */     } else if (mismatch == -1) {
/* 730 */       if (oddParityBad) {
/* 731 */         if (evenParityBad) {
/* 732 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 734 */         incrementOdd = true;
/*     */       } else {
/* 736 */         if (!evenParityBad) {
/* 737 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/* 739 */         incrementEven = true;
/*     */       } 
/* 741 */     } else if (mismatch == 0) {
/* 742 */       if (oddParityBad) {
/* 743 */         if (!evenParityBad) {
/* 744 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/*     */         
/* 747 */         if (oddSum < evenSum) {
/* 748 */           incrementOdd = true;
/* 749 */           decrementEven = true;
/*     */         } else {
/* 751 */           decrementOdd = true;
/* 752 */           incrementEven = true;
/*     */         }
/*     */       
/* 755 */       } else if (evenParityBad) {
/* 756 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 761 */       throw NotFoundException.getNotFoundInstance();
/*     */     } 
/*     */     
/* 764 */     if (incrementOdd) {
/* 765 */       if (decrementOdd) {
/* 766 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 768 */       increment(getOddCounts(), getOddRoundingErrors());
/*     */     } 
/* 770 */     if (decrementOdd) {
/* 771 */       decrement(getOddCounts(), getOddRoundingErrors());
/*     */     }
/* 773 */     if (incrementEven) {
/* 774 */       if (decrementEven) {
/* 775 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 777 */       increment(getEvenCounts(), getOddRoundingErrors());
/*     */     } 
/* 779 */     if (decrementEven)
/* 780 */       decrement(getEvenCounts(), getEvenRoundingErrors()); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\RSSExpandedReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */