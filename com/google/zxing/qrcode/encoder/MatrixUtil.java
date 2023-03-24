/*     */ package com.google.zxing.qrcode.encoder;
/*     */ 
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/*     */ import com.google.zxing.qrcode.decoder.Version;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MatrixUtil
/*     */ {
/*  34 */   private static final int[][] POSITION_DETECTION_PATTERN = new int[][] { { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final int[][] POSITION_ADJUSTMENT_PATTERN = new int[][] { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 1, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][] { { -1, -1, -1, -1, -1, -1, -1 }, { 6, 18, -1, -1, -1, -1, -1 }, { 6, 22, -1, -1, -1, -1, -1 }, { 6, 26, -1, -1, -1, -1, -1 }, { 6, 30, -1, -1, -1, -1, -1 }, { 6, 34, -1, -1, -1, -1, -1 }, { 6, 22, 38, -1, -1, -1, -1 }, { 6, 24, 42, -1, -1, -1, -1 }, { 6, 26, 46, -1, -1, -1, -1 }, { 6, 28, 50, -1, -1, -1, -1 }, { 6, 30, 54, -1, -1, -1, -1 }, { 6, 32, 58, -1, -1, -1, -1 }, { 6, 34, 62, -1, -1, -1, -1 }, { 6, 26, 46, 66, -1, -1, -1 }, { 6, 26, 48, 70, -1, -1, -1 }, { 6, 26, 50, 74, -1, -1, -1 }, { 6, 30, 54, 78, -1, -1, -1 }, { 6, 30, 56, 82, -1, -1, -1 }, { 6, 30, 58, 86, -1, -1, -1 }, { 6, 34, 62, 90, -1, -1, -1 }, { 6, 28, 50, 72, 94, -1, -1 }, { 6, 26, 50, 74, 98, -1, -1 }, { 6, 30, 54, 78, 102, -1, -1 }, { 6, 28, 54, 80, 106, -1, -1 }, { 6, 32, 58, 84, 110, -1, -1 }, { 6, 30, 58, 86, 114, -1, -1 }, { 6, 34, 62, 90, 118, -1, -1 }, { 6, 26, 50, 74, 98, 122, -1 }, { 6, 30, 54, 78, 102, 126, -1 }, { 6, 26, 52, 78, 104, 130, -1 }, { 6, 30, 56, 82, 108, 134, -1 }, { 6, 34, 60, 86, 112, 138, -1 }, { 6, 30, 58, 86, 114, 142, -1 }, { 6, 34, 62, 90, 118, 146, -1 }, { 6, 30, 54, 78, 102, 126, 150 }, { 6, 24, 50, 76, 102, 128, 154 }, { 6, 28, 54, 80, 106, 132, 158 }, { 6, 32, 58, 84, 110, 136, 162 }, { 6, 26, 54, 82, 110, 138, 166 }, { 6, 30, 58, 86, 114, 142, 170 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   private static final int[][] TYPE_INFO_COORDINATES = new int[][] { { 8, 0 }, { 8, 1 }, { 8, 2 }, { 8, 3 }, { 8, 4 }, { 8, 5 }, { 8, 7 }, { 8, 8 }, { 7, 8 }, { 5, 8 }, { 4, 8 }, { 3, 8 }, { 2, 8 }, { 1, 8 }, { 0, 8 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int VERSION_INFO_POLY = 7973;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TYPE_INFO_POLY = 1335;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int TYPE_INFO_MASK_PATTERN = 21522;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void clearMatrix(ByteMatrix matrix) {
/* 127 */     matrix.clear((byte)-1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void buildMatrix(BitArray dataBits, ErrorCorrectionLevel ecLevel, Version version, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 137 */     clearMatrix(matrix);
/* 138 */     embedBasicPatterns(version, matrix);
/*     */     
/* 140 */     embedTypeInfo(ecLevel, maskPattern, matrix);
/*     */     
/* 142 */     maybeEmbedVersionInfo(version, matrix);
/*     */     
/* 144 */     embedDataBits(dataBits, maskPattern, matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void embedBasicPatterns(Version version, ByteMatrix matrix) throws WriterException {
/* 155 */     embedPositionDetectionPatternsAndSeparators(matrix);
/*     */     
/* 157 */     embedDarkDotAtLeftBottomCorner(matrix);
/*     */ 
/*     */     
/* 160 */     maybeEmbedPositionAdjustmentPatterns(version, matrix);
/*     */     
/* 162 */     embedTimingPatterns(matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void embedTypeInfo(ErrorCorrectionLevel ecLevel, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 168 */     BitArray typeInfoBits = new BitArray();
/* 169 */     makeTypeInfoBits(ecLevel, maskPattern, typeInfoBits);
/*     */     
/* 171 */     for (int i = 0; i < typeInfoBits.getSize(); i++) {
/*     */ 
/*     */       
/* 174 */       boolean bit = typeInfoBits.get(typeInfoBits.getSize() - 1 - i);
/*     */ 
/*     */       
/* 177 */       int x1 = TYPE_INFO_COORDINATES[i][0];
/* 178 */       int y1 = TYPE_INFO_COORDINATES[i][1];
/* 179 */       matrix.set(x1, y1, bit);
/*     */       
/* 181 */       if (i < 8) {
/*     */         
/* 183 */         int x2 = matrix.getWidth() - i - 1;
/* 184 */         int y2 = 8;
/* 185 */         matrix.set(x2, y2, bit);
/*     */       } else {
/*     */         
/* 188 */         int x2 = 8;
/* 189 */         int y2 = matrix.getHeight() - 7 + i - 8;
/* 190 */         matrix.set(x2, y2, bit);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void maybeEmbedVersionInfo(Version version, ByteMatrix matrix) throws WriterException {
/* 198 */     if (version.getVersionNumber() < 7) {
/*     */       return;
/*     */     }
/* 201 */     BitArray versionInfoBits = new BitArray();
/* 202 */     makeVersionInfoBits(version, versionInfoBits);
/*     */     
/* 204 */     int bitIndex = 17;
/* 205 */     for (int i = 0; i < 6; i++) {
/* 206 */       for (int j = 0; j < 3; j++) {
/*     */         
/* 208 */         boolean bit = versionInfoBits.get(bitIndex);
/* 209 */         bitIndex--;
/*     */         
/* 211 */         matrix.set(i, matrix.getHeight() - 11 + j, bit);
/*     */         
/* 213 */         matrix.set(matrix.getHeight() - 11 + j, i, bit);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void embedDataBits(BitArray dataBits, int maskPattern, ByteMatrix matrix) throws WriterException {
/* 223 */     int bitIndex = 0;
/* 224 */     int direction = -1;
/*     */     
/* 226 */     int x = matrix.getWidth() - 1;
/* 227 */     int y = matrix.getHeight() - 1;
/* 228 */     while (x > 0) {
/*     */       
/* 230 */       if (x == 6) {
/* 231 */         x--;
/*     */       }
/* 233 */       while (y >= 0 && y < matrix.getHeight()) {
/* 234 */         for (int i = 0; i < 2; i++) {
/* 235 */           int xx = x - i;
/*     */           
/* 237 */           if (isEmpty(matrix.get(xx, y))) {
/*     */             boolean bit;
/*     */ 
/*     */             
/* 241 */             if (bitIndex < dataBits.getSize()) {
/* 242 */               bit = dataBits.get(bitIndex);
/* 243 */               bitIndex++;
/*     */             }
/*     */             else {
/*     */               
/* 247 */               bit = false;
/*     */             } 
/*     */ 
/*     */             
/* 251 */             if (maskPattern != -1 && MaskUtil.getDataMaskBit(maskPattern, xx, y)) {
/* 252 */               bit = !bit;
/*     */             }
/* 254 */             matrix.set(xx, y, bit);
/*     */           } 
/* 256 */         }  y += direction;
/*     */       } 
/* 258 */       direction = -direction;
/* 259 */       y += direction;
/* 260 */       x -= 2;
/*     */     } 
/*     */     
/* 263 */     if (bitIndex != dataBits.getSize()) {
/* 264 */       throw new WriterException("Not all bits consumed: " + bitIndex + '/' + dataBits.getSize());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int findMSBSet(int value) {
/* 274 */     int numDigits = 0;
/* 275 */     while (value != 0) {
/* 276 */       value >>>= 1;
/* 277 */       numDigits++;
/*     */     } 
/* 279 */     return numDigits;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int calculateBCHCode(int value, int poly) {
/* 308 */     if (poly == 0) {
/* 309 */       throw new IllegalArgumentException("0 polynomial");
/*     */     }
/*     */ 
/*     */     
/* 313 */     int msbSetInPoly = findMSBSet(poly);
/* 314 */     value <<= msbSetInPoly - 1;
/*     */     
/* 316 */     while (findMSBSet(value) >= msbSetInPoly) {
/* 317 */       value ^= poly << findMSBSet(value) - msbSetInPoly;
/*     */     }
/*     */     
/* 320 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void makeTypeInfoBits(ErrorCorrectionLevel ecLevel, int maskPattern, BitArray bits) throws WriterException {
/* 328 */     if (!QRCode.isValidMaskPattern(maskPattern)) {
/* 329 */       throw new WriterException("Invalid mask pattern");
/*     */     }
/* 331 */     int typeInfo = ecLevel.getBits() << 3 | maskPattern;
/* 332 */     bits.appendBits(typeInfo, 5);
/*     */     
/* 334 */     int bchCode = calculateBCHCode(typeInfo, 1335);
/* 335 */     bits.appendBits(bchCode, 10);
/*     */     
/* 337 */     BitArray maskBits = new BitArray();
/* 338 */     maskBits.appendBits(21522, 15);
/* 339 */     bits.xor(maskBits);
/*     */     
/* 341 */     if (bits.getSize() != 15) {
/* 342 */       throw new WriterException("should not happen but we got: " + bits.getSize());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void makeVersionInfoBits(Version version, BitArray bits) throws WriterException {
/* 349 */     bits.appendBits(version.getVersionNumber(), 6);
/* 350 */     int bchCode = calculateBCHCode(version.getVersionNumber(), 7973);
/* 351 */     bits.appendBits(bchCode, 12);
/*     */     
/* 353 */     if (bits.getSize() != 18) {
/* 354 */       throw new WriterException("should not happen but we got: " + bits.getSize());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isEmpty(int value) {
/* 360 */     return (value == -1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedTimingPatterns(ByteMatrix matrix) {
/* 366 */     for (int i = 8; i < matrix.getWidth() - 8; i++) {
/* 367 */       int bit = (i + 1) % 2;
/*     */       
/* 369 */       if (isEmpty(matrix.get(i, 6))) {
/* 370 */         matrix.set(i, 6, bit);
/*     */       }
/*     */       
/* 373 */       if (isEmpty(matrix.get(6, i))) {
/* 374 */         matrix.set(6, i, bit);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void embedDarkDotAtLeftBottomCorner(ByteMatrix matrix) throws WriterException {
/* 381 */     if (matrix.get(8, matrix.getHeight() - 8) == 0) {
/* 382 */       throw new WriterException();
/*     */     }
/* 384 */     matrix.set(8, matrix.getHeight() - 8, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedHorizontalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 390 */     for (int x = 0; x < 8; x++) {
/* 391 */       if (!isEmpty(matrix.get(xStart + x, yStart))) {
/* 392 */         throw new WriterException();
/*     */       }
/* 394 */       matrix.set(xStart + x, yStart, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedVerticalSeparationPattern(int xStart, int yStart, ByteMatrix matrix) throws WriterException {
/* 401 */     for (int y = 0; y < 7; y++) {
/* 402 */       if (!isEmpty(matrix.get(xStart, yStart + y))) {
/* 403 */         throw new WriterException();
/*     */       }
/* 405 */       matrix.set(xStart, yStart + y, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedPositionAdjustmentPattern(int xStart, int yStart, ByteMatrix matrix) {
/* 413 */     for (int y = 0; y < 5; y++) {
/* 414 */       for (int x = 0; x < 5; x++) {
/* 415 */         matrix.set(xStart + x, yStart + y, POSITION_ADJUSTMENT_PATTERN[y][x]);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void embedPositionDetectionPattern(int xStart, int yStart, ByteMatrix matrix) {
/* 421 */     for (int y = 0; y < 7; y++) {
/* 422 */       for (int x = 0; x < 7; x++) {
/* 423 */         matrix.set(xStart + x, yStart + y, POSITION_DETECTION_PATTERN[y][x]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix matrix) throws WriterException {
/* 431 */     int pdpWidth = (POSITION_DETECTION_PATTERN[0]).length;
/*     */     
/* 433 */     embedPositionDetectionPattern(0, 0, matrix);
/*     */     
/* 435 */     embedPositionDetectionPattern(matrix.getWidth() - pdpWidth, 0, matrix);
/*     */     
/* 437 */     embedPositionDetectionPattern(0, matrix.getWidth() - pdpWidth, matrix);
/*     */ 
/*     */     
/* 440 */     int hspWidth = 8;
/*     */     
/* 442 */     embedHorizontalSeparationPattern(0, hspWidth - 1, matrix);
/*     */     
/* 444 */     embedHorizontalSeparationPattern(matrix.getWidth() - hspWidth, hspWidth - 1, matrix);
/*     */ 
/*     */     
/* 447 */     embedHorizontalSeparationPattern(0, matrix.getWidth() - hspWidth, matrix);
/*     */ 
/*     */     
/* 450 */     int vspSize = 7;
/*     */     
/* 452 */     embedVerticalSeparationPattern(vspSize, 0, matrix);
/*     */     
/* 454 */     embedVerticalSeparationPattern(matrix.getHeight() - vspSize - 1, 0, matrix);
/*     */     
/* 456 */     embedVerticalSeparationPattern(vspSize, matrix.getHeight() - vspSize, matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void maybeEmbedPositionAdjustmentPatterns(Version version, ByteMatrix matrix) {
/* 462 */     if (version.getVersionNumber() < 2) {
/*     */       return;
/*     */     }
/* 465 */     int index = version.getVersionNumber() - 1;
/* 466 */     int[] coordinates = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[index];
/* 467 */     int numCoordinates = (POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE[index]).length;
/* 468 */     for (int i = 0; i < numCoordinates; i++) {
/* 469 */       for (int j = 0; j < numCoordinates; j++) {
/* 470 */         int y = coordinates[i];
/* 471 */         int x = coordinates[j];
/* 472 */         if (x != -1 && y != -1)
/*     */         {
/*     */ 
/*     */           
/* 476 */           if (isEmpty(matrix.get(x, y)))
/*     */           {
/*     */             
/* 479 */             embedPositionAdjustmentPattern(x - 2, y - 2, matrix);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\encoder\MatrixUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */