/*     */ package com.google.zxing.pdf417.decoder;
/*     */ 
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.pdf417.PDF417Common;
/*     */ import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PDF417ScanningDecoder
/*     */ {
/*     */   private static final int CODEWORD_SKEW_SIZE = 2;
/*     */   private static final int MAX_ERRORS = 3;
/*     */   private static final int MAX_EC_CODEWORDS = 512;
/*  42 */   private static final ErrorCorrection errorCorrection = new ErrorCorrection();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DecoderResult decode(BitMatrix image, ResultPoint imageTopLeft, ResultPoint imageBottomLeft, ResultPoint imageTopRight, ResultPoint imageBottomRight, int minCodewordWidth, int maxCodewordWidth) throws NotFoundException, FormatException, ChecksumException {
/*  58 */     BoundingBox boundingBox = new BoundingBox(image, imageTopLeft, imageBottomLeft, imageTopRight, imageBottomRight);
/*  59 */     DetectionResultRowIndicatorColumn leftRowIndicatorColumn = null;
/*  60 */     DetectionResultRowIndicatorColumn rightRowIndicatorColumn = null;
/*  61 */     DetectionResult detectionResult = null;
/*  62 */     for (int i = 0; i < 2; i++) {
/*  63 */       if (imageTopLeft != null) {
/*  64 */         leftRowIndicatorColumn = getRowIndicatorColumn(image, boundingBox, imageTopLeft, true, minCodewordWidth, maxCodewordWidth);
/*     */       }
/*     */       
/*  67 */       if (imageTopRight != null) {
/*  68 */         rightRowIndicatorColumn = getRowIndicatorColumn(image, boundingBox, imageTopRight, false, minCodewordWidth, maxCodewordWidth);
/*     */       }
/*     */       
/*  71 */       detectionResult = merge(leftRowIndicatorColumn, rightRowIndicatorColumn);
/*  72 */       if (detectionResult == null) {
/*  73 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*  75 */       if (i == 0 && detectionResult.getBoundingBox() != null && (detectionResult
/*  76 */         .getBoundingBox().getMinY() < boundingBox.getMinY() || detectionResult.getBoundingBox()
/*  77 */         .getMaxY() > boundingBox.getMaxY())) {
/*  78 */         boundingBox = detectionResult.getBoundingBox();
/*     */       } else {
/*  80 */         detectionResult.setBoundingBox(boundingBox);
/*     */         break;
/*     */       } 
/*     */     } 
/*  84 */     int maxBarcodeColumn = detectionResult.getBarcodeColumnCount() + 1;
/*  85 */     detectionResult.setDetectionResultColumn(0, leftRowIndicatorColumn);
/*  86 */     detectionResult.setDetectionResultColumn(maxBarcodeColumn, rightRowIndicatorColumn);
/*     */     
/*  88 */     boolean leftToRight = (leftRowIndicatorColumn != null);
/*  89 */     for (int barcodeColumnCount = 1; barcodeColumnCount <= maxBarcodeColumn; barcodeColumnCount++) {
/*  90 */       int barcodeColumn = leftToRight ? barcodeColumnCount : (maxBarcodeColumn - barcodeColumnCount);
/*  91 */       if (detectionResult.getDetectionResultColumn(barcodeColumn) == null) {
/*     */         DetectionResultColumn detectionResultColumn;
/*     */ 
/*     */ 
/*     */         
/*  96 */         if (barcodeColumn == 0 || barcodeColumn == maxBarcodeColumn) {
/*  97 */           detectionResultColumn = new DetectionResultRowIndicatorColumn(boundingBox, (barcodeColumn == 0));
/*     */         } else {
/*  99 */           detectionResultColumn = new DetectionResultColumn(boundingBox);
/*     */         } 
/* 101 */         detectionResult.setDetectionResultColumn(barcodeColumn, detectionResultColumn);
/* 102 */         int startColumn = -1;
/* 103 */         int previousStartColumn = startColumn;
/*     */         
/* 105 */         for (int imageRow = boundingBox.getMinY(); imageRow <= boundingBox.getMaxY(); imageRow++) {
/* 106 */           startColumn = getStartColumn(detectionResult, barcodeColumn, imageRow, leftToRight);
/* 107 */           if (startColumn < 0 || startColumn > boundingBox.getMaxX()) {
/* 108 */             if (previousStartColumn == -1) {
/*     */               continue;
/*     */             }
/* 111 */             startColumn = previousStartColumn;
/*     */           } 
/* 113 */           Codeword codeword = detectCodeword(image, boundingBox.getMinX(), boundingBox.getMaxX(), leftToRight, startColumn, imageRow, minCodewordWidth, maxCodewordWidth);
/*     */           
/* 115 */           if (codeword != null) {
/* 116 */             detectionResultColumn.setCodeword(imageRow, codeword);
/* 117 */             previousStartColumn = startColumn;
/* 118 */             minCodewordWidth = Math.min(minCodewordWidth, codeword.getWidth());
/* 119 */             maxCodewordWidth = Math.max(maxCodewordWidth, codeword.getWidth());
/*     */           }  continue;
/*     */         } 
/*     */       } 
/* 123 */     }  return createDecoderResult(detectionResult);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static DetectionResult merge(DetectionResultRowIndicatorColumn leftRowIndicatorColumn, DetectionResultRowIndicatorColumn rightRowIndicatorColumn) throws NotFoundException, FormatException {
/* 129 */     if (leftRowIndicatorColumn == null && rightRowIndicatorColumn == null) {
/* 130 */       return null;
/*     */     }
/* 132 */     BarcodeMetadata barcodeMetadata = getBarcodeMetadata(leftRowIndicatorColumn, rightRowIndicatorColumn);
/* 133 */     if (barcodeMetadata == null) {
/* 134 */       return null;
/*     */     }
/* 136 */     BoundingBox boundingBox = BoundingBox.merge(adjustBoundingBox(leftRowIndicatorColumn), 
/* 137 */         adjustBoundingBox(rightRowIndicatorColumn));
/* 138 */     return new DetectionResult(barcodeMetadata, boundingBox);
/*     */   }
/*     */ 
/*     */   
/*     */   private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn rowIndicatorColumn) throws NotFoundException, FormatException {
/* 143 */     if (rowIndicatorColumn == null) {
/* 144 */       return null;
/*     */     }
/* 146 */     int[] rowHeights = rowIndicatorColumn.getRowHeights();
/* 147 */     if (rowHeights == null) {
/* 148 */       return null;
/*     */     }
/* 150 */     int maxRowHeight = getMax(rowHeights);
/* 151 */     int missingStartRows = 0;
/* 152 */     for (int rowHeight : rowHeights) {
/* 153 */       missingStartRows += maxRowHeight - rowHeight;
/* 154 */       if (rowHeight > 0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 158 */     Codeword[] codewords = rowIndicatorColumn.getCodewords();
/* 159 */     for (int row = 0; missingStartRows > 0 && codewords[row] == null; row++) {
/* 160 */       missingStartRows--;
/*     */     }
/* 162 */     int missingEndRows = 0; int i;
/* 163 */     for (i = rowHeights.length - 1; i >= 0; i--) {
/* 164 */       missingEndRows += maxRowHeight - rowHeights[i];
/* 165 */       if (rowHeights[i] > 0) {
/*     */         break;
/*     */       }
/*     */     } 
/* 169 */     for (i = codewords.length - 1; missingEndRows > 0 && codewords[i] == null; i--) {
/* 170 */       missingEndRows--;
/*     */     }
/* 172 */     return rowIndicatorColumn.getBoundingBox().addMissingRows(missingStartRows, missingEndRows, rowIndicatorColumn
/* 173 */         .isLeft());
/*     */   }
/*     */   
/*     */   private static int getMax(int[] values) {
/* 177 */     int maxValue = -1;
/* 178 */     for (int value : values) {
/* 179 */       maxValue = Math.max(maxValue, value);
/*     */     }
/* 181 */     return maxValue;
/*     */   }
/*     */ 
/*     */   
/*     */   private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn leftRowIndicatorColumn, DetectionResultRowIndicatorColumn rightRowIndicatorColumn) {
/*     */     BarcodeMetadata leftBarcodeMetadata;
/* 187 */     if (leftRowIndicatorColumn == null || (
/* 188 */       leftBarcodeMetadata = leftRowIndicatorColumn.getBarcodeMetadata()) == null) {
/* 189 */       return (rightRowIndicatorColumn == null) ? null : rightRowIndicatorColumn.getBarcodeMetadata();
/*     */     }
/*     */     BarcodeMetadata rightBarcodeMetadata;
/* 192 */     if (rightRowIndicatorColumn == null || (
/* 193 */       rightBarcodeMetadata = rightRowIndicatorColumn.getBarcodeMetadata()) == null) {
/* 194 */       return leftBarcodeMetadata;
/*     */     }
/*     */     
/* 197 */     if (leftBarcodeMetadata.getColumnCount() != rightBarcodeMetadata.getColumnCount() && leftBarcodeMetadata
/* 198 */       .getErrorCorrectionLevel() != rightBarcodeMetadata.getErrorCorrectionLevel() && leftBarcodeMetadata
/* 199 */       .getRowCount() != rightBarcodeMetadata.getRowCount()) {
/* 200 */       return null;
/*     */     }
/* 202 */     return leftBarcodeMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix image, BoundingBox boundingBox, ResultPoint startPoint, boolean leftToRight, int minCodewordWidth, int maxCodewordWidth) {
/* 211 */     DetectionResultRowIndicatorColumn rowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, leftToRight);
/*     */     
/* 213 */     for (int i = 0; i < 2; i++) {
/* 214 */       int increment = (i == 0) ? 1 : -1;
/* 215 */       int startColumn = (int)startPoint.getX(); int imageRow;
/* 216 */       for (imageRow = (int)startPoint.getY(); imageRow <= boundingBox.getMaxY() && imageRow >= boundingBox
/* 217 */         .getMinY(); imageRow += increment) {
/* 218 */         Codeword codeword = detectCodeword(image, 0, image.getWidth(), leftToRight, startColumn, imageRow, minCodewordWidth, maxCodewordWidth);
/*     */         
/* 220 */         if (codeword != null) {
/* 221 */           rowIndicatorColumn.setCodeword(imageRow, codeword);
/* 222 */           if (leftToRight) {
/* 223 */             startColumn = codeword.getStartX();
/*     */           } else {
/* 225 */             startColumn = codeword.getEndX();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 230 */     return rowIndicatorColumn;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] barcodeMatrix) throws NotFoundException {
/* 235 */     int[] numberOfCodewords = barcodeMatrix[0][1].getValue();
/*     */ 
/*     */     
/* 238 */     int calculatedNumberOfCodewords = detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount() - getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
/* 239 */     if (numberOfCodewords.length == 0) {
/* 240 */       if (calculatedNumberOfCodewords < 1 || calculatedNumberOfCodewords > 928) {
/* 241 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 243 */       barcodeMatrix[0][1].setValue(calculatedNumberOfCodewords);
/* 244 */     } else if (numberOfCodewords[0] != calculatedNumberOfCodewords) {
/*     */       
/* 246 */       barcodeMatrix[0][1].setValue(calculatedNumberOfCodewords);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws FormatException, ChecksumException, NotFoundException {
/* 252 */     BarcodeValue[][] barcodeMatrix = createBarcodeMatrix(detectionResult);
/* 253 */     adjustCodewordCount(detectionResult, barcodeMatrix);
/* 254 */     Collection<Integer> erasures = new ArrayList<>();
/* 255 */     int[] codewords = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
/* 256 */     List<int[]> ambiguousIndexValuesList = (List)new ArrayList<>();
/* 257 */     List<Integer> ambiguousIndexesList = new ArrayList<>();
/* 258 */     for (int row = 0; row < detectionResult.getBarcodeRowCount(); row++) {
/* 259 */       for (int column = 0; column < detectionResult.getBarcodeColumnCount(); column++) {
/* 260 */         int[] values = barcodeMatrix[row][column + 1].getValue();
/* 261 */         int codewordIndex = row * detectionResult.getBarcodeColumnCount() + column;
/* 262 */         if (values.length == 0) {
/* 263 */           erasures.add(Integer.valueOf(codewordIndex));
/* 264 */         } else if (values.length == 1) {
/* 265 */           codewords[codewordIndex] = values[0];
/*     */         } else {
/* 267 */           ambiguousIndexesList.add(Integer.valueOf(codewordIndex));
/* 268 */           ambiguousIndexValuesList.add(values);
/*     */         } 
/*     */       } 
/*     */     } 
/* 272 */     int[][] ambiguousIndexValues = new int[ambiguousIndexValuesList.size()][];
/* 273 */     for (int i = 0; i < ambiguousIndexValues.length; i++) {
/* 274 */       ambiguousIndexValues[i] = ambiguousIndexValuesList.get(i);
/*     */     }
/* 276 */     return createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), codewords, 
/* 277 */         PDF417Common.toIntArray(erasures), PDF417Common.toIntArray(ambiguousIndexesList), ambiguousIndexValues);
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
/*     */   private static DecoderResult createDecoderResultFromAmbiguousValues(int ecLevel, int[] codewords, int[] erasureArray, int[] ambiguousIndexes, int[][] ambiguousIndexValues) throws FormatException, ChecksumException {
/* 299 */     int[] ambiguousIndexCount = new int[ambiguousIndexes.length];
/*     */     
/* 301 */     int tries = 100;
/* 302 */     while (tries-- > 0) {
/* 303 */       for (int i = 0; i < ambiguousIndexCount.length; i++) {
/* 304 */         codewords[ambiguousIndexes[i]] = ambiguousIndexValues[i][ambiguousIndexCount[i]];
/*     */       }
/*     */       try {
/* 307 */         return decodeCodewords(codewords, ecLevel, erasureArray);
/* 308 */       } catch (ChecksumException checksumException) {
/*     */ 
/*     */         
/* 311 */         if (ambiguousIndexCount.length == 0) {
/* 312 */           throw ChecksumException.getChecksumInstance();
/*     */         }
/* 314 */         for (int j = 0; j < ambiguousIndexCount.length; j++) {
/* 315 */           if (ambiguousIndexCount[j] < (ambiguousIndexValues[j]).length - 1) {
/* 316 */             ambiguousIndexCount[j] = ambiguousIndexCount[j] + 1;
/*     */             break;
/*     */           } 
/* 319 */           ambiguousIndexCount[j] = 0;
/* 320 */           if (j == ambiguousIndexCount.length - 1) {
/* 321 */             throw ChecksumException.getChecksumInstance();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 326 */     throw ChecksumException.getChecksumInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   private static BarcodeValue[][] createBarcodeMatrix(DetectionResult detectionResult) throws FormatException {
/* 331 */     BarcodeValue[][] barcodeMatrix = new BarcodeValue[detectionResult.getBarcodeRowCount()][detectionResult.getBarcodeColumnCount() + 2];
/* 332 */     for (int row = 0; row < barcodeMatrix.length; row++) {
/* 333 */       for (int i = 0; i < (barcodeMatrix[row]).length; i++) {
/* 334 */         barcodeMatrix[row][i] = new BarcodeValue();
/*     */       }
/*     */     } 
/*     */     
/* 338 */     int column = 0;
/* 339 */     for (DetectionResultColumn detectionResultColumn : detectionResult.getDetectionResultColumns()) {
/* 340 */       if (detectionResultColumn != null) {
/* 341 */         for (Codeword codeword : detectionResultColumn.getCodewords()) {
/* 342 */           if (codeword != null) {
/* 343 */             int rowNumber = codeword.getRowNumber();
/* 344 */             if (rowNumber >= 0) {
/* 345 */               if (rowNumber >= barcodeMatrix.length) {
/* 346 */                 throw FormatException.getFormatInstance();
/*     */               }
/* 348 */               barcodeMatrix[rowNumber][column].setValue(codeword.getValue());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/* 353 */       column++;
/*     */     } 
/* 355 */     return barcodeMatrix;
/*     */   }
/*     */   
/*     */   private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int barcodeColumn) {
/* 359 */     return (barcodeColumn >= 0 && barcodeColumn <= detectionResult.getBarcodeColumnCount() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getStartColumn(DetectionResult detectionResult, int barcodeColumn, int imageRow, boolean leftToRight) {
/* 366 */     int offset = leftToRight ? 1 : -1;
/* 367 */     Codeword codeword = null;
/* 368 */     if (isValidBarcodeColumn(detectionResult, barcodeColumn - offset)) {
/* 369 */       codeword = detectionResult.getDetectionResultColumn(barcodeColumn - offset).getCodeword(imageRow);
/*     */     }
/* 371 */     if (codeword != null) {
/* 372 */       return leftToRight ? codeword.getEndX() : codeword.getStartX();
/*     */     }
/* 374 */     codeword = detectionResult.getDetectionResultColumn(barcodeColumn).getCodewordNearby(imageRow);
/* 375 */     if (codeword != null) {
/* 376 */       return leftToRight ? codeword.getStartX() : codeword.getEndX();
/*     */     }
/* 378 */     if (isValidBarcodeColumn(detectionResult, barcodeColumn - offset)) {
/* 379 */       codeword = detectionResult.getDetectionResultColumn(barcodeColumn - offset).getCodewordNearby(imageRow);
/*     */     }
/* 381 */     if (codeword != null) {
/* 382 */       return leftToRight ? codeword.getEndX() : codeword.getStartX();
/*     */     }
/* 384 */     int skippedColumns = 0;
/*     */     
/* 386 */     while (isValidBarcodeColumn(detectionResult, barcodeColumn - offset)) {
/* 387 */       barcodeColumn -= offset;
/* 388 */       for (Codeword previousRowCodeword : detectionResult.getDetectionResultColumn(barcodeColumn).getCodewords()) {
/* 389 */         if (previousRowCodeword != null)
/*     */         {
/*     */ 
/*     */           
/* 393 */           return (leftToRight ? previousRowCodeword.getEndX() : previousRowCodeword.getStartX()) + offset * skippedColumns * (previousRowCodeword.getEndX() - previousRowCodeword.getStartX());
/*     */         }
/*     */       } 
/* 396 */       skippedColumns++;
/*     */     } 
/* 398 */     return leftToRight ? detectionResult.getBoundingBox().getMinX() : detectionResult.getBoundingBox().getMaxX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Codeword detectCodeword(BitMatrix image, int minColumn, int maxColumn, boolean leftToRight, int startColumn, int imageRow, int minCodewordWidth, int maxCodewordWidth) {
/*     */     int endColumn;
/* 409 */     startColumn = adjustCodewordStartColumn(image, minColumn, maxColumn, leftToRight, startColumn, imageRow);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 414 */     int[] moduleBitCount = getModuleBitCount(image, minColumn, maxColumn, leftToRight, startColumn, imageRow);
/* 415 */     if (moduleBitCount == null) {
/* 416 */       return null;
/*     */     }
/*     */     
/* 419 */     int codewordBitCount = PDF417Common.getBitCountSum(moduleBitCount);
/* 420 */     if (leftToRight) {
/* 421 */       endColumn = startColumn + codewordBitCount;
/*     */     } else {
/* 423 */       for (int i = 0; i < moduleBitCount.length / 2; i++) {
/* 424 */         int tmpCount = moduleBitCount[i];
/* 425 */         moduleBitCount[i] = moduleBitCount[moduleBitCount.length - 1 - i];
/* 426 */         moduleBitCount[moduleBitCount.length - 1 - i] = tmpCount;
/*     */       } 
/* 428 */       endColumn = startColumn;
/* 429 */       startColumn = endColumn - codewordBitCount;
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
/*     */     
/* 445 */     if (!checkCodewordSkew(codewordBitCount, minCodewordWidth, maxCodewordWidth))
/*     */     {
/*     */       
/* 448 */       return null;
/*     */     }
/*     */     
/* 451 */     int decodedValue = PDF417CodewordDecoder.getDecodedValue(moduleBitCount);
/* 452 */     int codeword = PDF417Common.getCodeword(decodedValue);
/* 453 */     if (codeword == -1) {
/* 454 */       return null;
/*     */     }
/* 456 */     return new Codeword(startColumn, endColumn, getCodewordBucketNumber(decodedValue), codeword);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] getModuleBitCount(BitMatrix image, int minColumn, int maxColumn, boolean leftToRight, int startColumn, int imageRow) {
/* 465 */     int imageColumn = startColumn;
/* 466 */     int[] moduleBitCount = new int[8];
/* 467 */     int moduleNumber = 0;
/* 468 */     int increment = leftToRight ? 1 : -1;
/* 469 */     boolean previousPixelValue = leftToRight;
/* 470 */     while (((leftToRight && imageColumn < maxColumn) || (!leftToRight && imageColumn >= minColumn)) && moduleNumber < moduleBitCount.length) {
/*     */       
/* 472 */       if (image.get(imageColumn, imageRow) == previousPixelValue) {
/* 473 */         moduleBitCount[moduleNumber] = moduleBitCount[moduleNumber] + 1;
/* 474 */         imageColumn += increment; continue;
/*     */       } 
/* 476 */       moduleNumber++;
/* 477 */       previousPixelValue = !previousPixelValue;
/*     */     } 
/*     */     
/* 480 */     if (moduleNumber == moduleBitCount.length || (((leftToRight && imageColumn == maxColumn) || (!leftToRight && imageColumn == minColumn)) && moduleNumber == moduleBitCount.length - 1))
/*     */     {
/* 482 */       return moduleBitCount;
/*     */     }
/* 484 */     return null;
/*     */   }
/*     */   
/*     */   private static int getNumberOfECCodeWords(int barcodeECLevel) {
/* 488 */     return 2 << barcodeECLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int adjustCodewordStartColumn(BitMatrix image, int minColumn, int maxColumn, boolean leftToRight, int codewordStartColumn, int imageRow) {
/* 497 */     int correctedStartColumn = codewordStartColumn;
/* 498 */     int increment = leftToRight ? -1 : 1;
/*     */     
/* 500 */     for (int i = 0; i < 2; i++) {
/* 501 */       while (((leftToRight && correctedStartColumn >= minColumn) || (!leftToRight && correctedStartColumn < maxColumn)) && leftToRight == image
/* 502 */         .get(correctedStartColumn, imageRow)) {
/* 503 */         if (Math.abs(codewordStartColumn - correctedStartColumn) > 2) {
/* 504 */           return codewordStartColumn;
/*     */         }
/* 506 */         correctedStartColumn += increment;
/*     */       } 
/* 508 */       increment = -increment;
/* 509 */       leftToRight = !leftToRight;
/*     */     } 
/* 511 */     return correctedStartColumn;
/*     */   }
/*     */   
/*     */   private static boolean checkCodewordSkew(int codewordSize, int minCodewordWidth, int maxCodewordWidth) {
/* 515 */     return (minCodewordWidth - 2 <= codewordSize && codewordSize <= maxCodewordWidth + 2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static DecoderResult decodeCodewords(int[] codewords, int ecLevel, int[] erasures) throws FormatException, ChecksumException {
/* 521 */     if (codewords.length == 0) {
/* 522 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */     
/* 525 */     int numECCodewords = 1 << ecLevel + 1;
/* 526 */     int correctedErrorsCount = correctErrors(codewords, erasures, numECCodewords);
/* 527 */     verifyCodewordCount(codewords, numECCodewords);
/*     */ 
/*     */     
/* 530 */     DecoderResult decoderResult = DecodedBitStreamParser.decode(codewords, String.valueOf(ecLevel));
/* 531 */     decoderResult.setErrorsCorrected(Integer.valueOf(correctedErrorsCount));
/* 532 */     decoderResult.setErasures(Integer.valueOf(erasures.length));
/* 533 */     return decoderResult;
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
/*     */   private static int correctErrors(int[] codewords, int[] erasures, int numECCodewords) throws ChecksumException {
/* 546 */     if ((erasures != null && erasures.length > numECCodewords / 2 + 3) || numECCodewords < 0 || numECCodewords > 512)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 551 */       throw ChecksumException.getChecksumInstance();
/*     */     }
/* 553 */     return errorCorrection.decode(codewords, numECCodewords, erasures);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyCodewordCount(int[] codewords, int numECCodewords) throws FormatException {
/* 560 */     if (codewords.length < 4)
/*     */     {
/*     */       
/* 563 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 568 */     int numberOfCodewords = codewords[0];
/* 569 */     if (numberOfCodewords > codewords.length) {
/* 570 */       throw FormatException.getFormatInstance();
/*     */     }
/* 572 */     if (numberOfCodewords == 0)
/*     */     {
/* 574 */       if (numECCodewords < codewords.length) {
/* 575 */         codewords[0] = codewords.length - numECCodewords;
/*     */       } else {
/* 577 */         throw FormatException.getFormatInstance();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static int[] getBitCountForCodeword(int codeword) {
/* 583 */     int[] result = new int[8];
/* 584 */     int previousValue = 0;
/* 585 */     int i = result.length - 1;
/*     */     while (true) {
/* 587 */       if ((codeword & 0x1) != previousValue) {
/* 588 */         previousValue = codeword & 0x1;
/* 589 */         i--;
/* 590 */         if (i < 0) {
/*     */           break;
/*     */         }
/*     */       } 
/* 594 */       result[i] = result[i] + 1;
/* 595 */       codeword >>= 1;
/*     */     } 
/* 597 */     return result;
/*     */   }
/*     */   
/*     */   private static int getCodewordBucketNumber(int codeword) {
/* 601 */     return getCodewordBucketNumber(getBitCountForCodeword(codeword));
/*     */   }
/*     */   
/*     */   private static int getCodewordBucketNumber(int[] moduleBitCount) {
/* 605 */     return (moduleBitCount[0] - moduleBitCount[2] + moduleBitCount[4] - moduleBitCount[6] + 9) % 9;
/*     */   }
/*     */   
/*     */   public static String toString(BarcodeValue[][] barcodeMatrix) {
/* 609 */     Formatter formatter = new Formatter();
/* 610 */     for (int row = 0; row < barcodeMatrix.length; row++) {
/* 611 */       formatter.format("Row %2d: ", new Object[] { Integer.valueOf(row) });
/* 612 */       for (int column = 0; column < (barcodeMatrix[row]).length; column++) {
/* 613 */         BarcodeValue barcodeValue = barcodeMatrix[row][column];
/* 614 */         if ((barcodeValue.getValue()).length == 0) {
/* 615 */           formatter.format("        ", (Object[])null);
/*     */         } else {
/* 617 */           formatter.format("%4d(%2d)", new Object[] { Integer.valueOf(barcodeValue.getValue()[0]), barcodeValue
/* 618 */                 .getConfidence(barcodeValue.getValue()[0]) });
/*     */         } 
/*     */       } 
/* 621 */       formatter.format("%n", new Object[0]);
/*     */     } 
/* 623 */     String result = formatter.toString();
/* 624 */     formatter.close();
/* 625 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\PDF417ScanningDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */