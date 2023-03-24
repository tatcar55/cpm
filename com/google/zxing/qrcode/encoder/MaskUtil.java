/*     */ package com.google.zxing.qrcode.encoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MaskUtil
/*     */ {
/*     */   private static final int N1 = 3;
/*     */   private static final int N2 = 3;
/*     */   private static final int N3 = 40;
/*     */   private static final int N4 = 10;
/*     */   
/*     */   static int applyMaskPenaltyRule1(ByteMatrix matrix) {
/*  41 */     return applyMaskPenaltyRule1Internal(matrix, true) + applyMaskPenaltyRule1Internal(matrix, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int applyMaskPenaltyRule2(ByteMatrix matrix) {
/*  50 */     int penalty = 0;
/*  51 */     byte[][] array = matrix.getArray();
/*  52 */     int width = matrix.getWidth();
/*  53 */     int height = matrix.getHeight();
/*  54 */     for (int y = 0; y < height - 1; y++) {
/*  55 */       for (int x = 0; x < width - 1; x++) {
/*  56 */         int value = array[y][x];
/*  57 */         if (value == array[y][x + 1] && value == array[y + 1][x] && value == array[y + 1][x + 1]) {
/*  58 */           penalty++;
/*     */         }
/*     */       } 
/*     */     } 
/*  62 */     return 3 * penalty;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int applyMaskPenaltyRule3(ByteMatrix matrix) {
/*  71 */     int numPenalties = 0;
/*  72 */     byte[][] array = matrix.getArray();
/*  73 */     int width = matrix.getWidth();
/*  74 */     int height = matrix.getHeight();
/*  75 */     for (int y = 0; y < height; y++) {
/*  76 */       for (int x = 0; x < width; x++) {
/*  77 */         byte[] arrayY = array[y];
/*  78 */         if (x + 6 < width && arrayY[x] == 1 && arrayY[x + 1] == 0 && arrayY[x + 2] == 1 && arrayY[x + 3] == 1 && arrayY[x + 4] == 1 && arrayY[x + 5] == 0 && arrayY[x + 6] == 1 && (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  86 */           isWhiteHorizontal(arrayY, x - 4, x) || isWhiteHorizontal(arrayY, x + 7, x + 11))) {
/*  87 */           numPenalties++;
/*     */         }
/*  89 */         if (y + 6 < height && array[y][x] == 1 && array[y + 1][x] == 0 && array[y + 2][x] == 1 && array[y + 3][x] == 1 && array[y + 4][x] == 1 && array[y + 5][x] == 0 && array[y + 6][x] == 1 && (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  97 */           isWhiteVertical(array, x, y - 4, y) || isWhiteVertical(array, x, y + 7, y + 11))) {
/*  98 */           numPenalties++;
/*     */         }
/*     */       } 
/*     */     } 
/* 102 */     return numPenalties * 40;
/*     */   }
/*     */   
/*     */   private static boolean isWhiteHorizontal(byte[] rowArray, int from, int to) {
/* 106 */     for (int i = from; i < to; i++) {
/* 107 */       if (i >= 0 && i < rowArray.length && rowArray[i] == 1) {
/* 108 */         return false;
/*     */       }
/*     */     } 
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isWhiteVertical(byte[][] array, int col, int from, int to) {
/* 115 */     for (int i = from; i < to; i++) {
/* 116 */       if (i >= 0 && i < array.length && array[i][col] == 1) {
/* 117 */         return false;
/*     */       }
/*     */     } 
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int applyMaskPenaltyRule4(ByteMatrix matrix) {
/* 128 */     int numDarkCells = 0;
/* 129 */     byte[][] array = matrix.getArray();
/* 130 */     int width = matrix.getWidth();
/* 131 */     int height = matrix.getHeight();
/* 132 */     for (int y = 0; y < height; y++) {
/* 133 */       byte[] arrayY = array[y];
/* 134 */       for (int x = 0; x < width; x++) {
/* 135 */         if (arrayY[x] == 1) {
/* 136 */           numDarkCells++;
/*     */         }
/*     */       } 
/*     */     } 
/* 140 */     int numTotalCells = matrix.getHeight() * matrix.getWidth();
/* 141 */     int fivePercentVariances = Math.abs(numDarkCells * 2 - numTotalCells) * 10 / numTotalCells;
/* 142 */     return fivePercentVariances * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean getDataMaskBit(int maskPattern, int x, int y) {
/*     */     int intermediate;
/*     */     int temp;
/* 152 */     switch (maskPattern) {
/*     */       case 0:
/* 154 */         intermediate = y + x & 0x1;
/*     */         break;
/*     */       case 1:
/* 157 */         intermediate = y & 0x1;
/*     */         break;
/*     */       case 2:
/* 160 */         intermediate = x % 3;
/*     */         break;
/*     */       case 3:
/* 163 */         intermediate = (y + x) % 3;
/*     */         break;
/*     */       case 4:
/* 166 */         intermediate = y / 2 + x / 3 & 0x1;
/*     */         break;
/*     */       case 5:
/* 169 */         temp = y * x;
/* 170 */         intermediate = (temp & 0x1) + temp % 3;
/*     */         break;
/*     */       case 6:
/* 173 */         temp = y * x;
/* 174 */         intermediate = (temp & 0x1) + temp % 3 & 0x1;
/*     */         break;
/*     */       case 7:
/* 177 */         temp = y * x;
/* 178 */         intermediate = temp % 3 + (y + x & 0x1) & 0x1;
/*     */         break;
/*     */       default:
/* 181 */         throw new IllegalArgumentException("Invalid mask pattern: " + maskPattern);
/*     */     } 
/* 183 */     return (intermediate == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int applyMaskPenaltyRule1Internal(ByteMatrix matrix, boolean isHorizontal) {
/* 191 */     int penalty = 0;
/* 192 */     int iLimit = isHorizontal ? matrix.getHeight() : matrix.getWidth();
/* 193 */     int jLimit = isHorizontal ? matrix.getWidth() : matrix.getHeight();
/* 194 */     byte[][] array = matrix.getArray();
/* 195 */     for (int i = 0; i < iLimit; i++) {
/* 196 */       int numSameBitCells = 0;
/* 197 */       int prevBit = -1;
/* 198 */       for (int j = 0; j < jLimit; j++) {
/* 199 */         int bit = isHorizontal ? array[i][j] : array[j][i];
/* 200 */         if (bit == prevBit) {
/* 201 */           numSameBitCells++;
/*     */         } else {
/* 203 */           if (numSameBitCells >= 5) {
/* 204 */             penalty += 3 + numSameBitCells - 5;
/*     */           }
/* 206 */           numSameBitCells = 1;
/* 207 */           prevBit = bit;
/*     */         } 
/*     */       } 
/* 210 */       if (numSameBitCells >= 5) {
/* 211 */         penalty += 3 + numSameBitCells - 5;
/*     */       }
/*     */     } 
/* 214 */     return penalty;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\encoder\MaskUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */