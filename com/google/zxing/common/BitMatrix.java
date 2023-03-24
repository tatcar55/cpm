/*     */ package com.google.zxing.common;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BitMatrix
/*     */   implements Cloneable
/*     */ {
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final int rowSize;
/*     */   private final int[] bits;
/*     */   
/*     */   public BitMatrix(int dimension) {
/*  45 */     this(dimension, dimension);
/*     */   }
/*     */   
/*     */   public BitMatrix(int width, int height) {
/*  49 */     if (width < 1 || height < 1) {
/*  50 */       throw new IllegalArgumentException("Both dimensions must be greater than 0");
/*     */     }
/*  52 */     this.width = width;
/*  53 */     this.height = height;
/*  54 */     this.rowSize = (width + 31) / 32;
/*  55 */     this.bits = new int[this.rowSize * height];
/*     */   }
/*     */   
/*     */   private BitMatrix(int width, int height, int rowSize, int[] bits) {
/*  59 */     this.width = width;
/*  60 */     this.height = height;
/*  61 */     this.rowSize = rowSize;
/*  62 */     this.bits = bits;
/*     */   }
/*     */   
/*     */   public static BitMatrix parse(String stringRepresentation, String setString, String unsetString) {
/*  66 */     if (stringRepresentation == null) {
/*  67 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  70 */     boolean[] bits = new boolean[stringRepresentation.length()];
/*  71 */     int bitsPos = 0;
/*  72 */     int rowStartPos = 0;
/*  73 */     int rowLength = -1;
/*  74 */     int nRows = 0;
/*  75 */     int pos = 0;
/*  76 */     while (pos < stringRepresentation.length()) {
/*  77 */       if (stringRepresentation.charAt(pos) == '\n' || stringRepresentation
/*  78 */         .charAt(pos) == '\r') {
/*  79 */         if (bitsPos > rowStartPos) {
/*  80 */           if (rowLength == -1) {
/*  81 */             rowLength = bitsPos - rowStartPos;
/*  82 */           } else if (bitsPos - rowStartPos != rowLength) {
/*  83 */             throw new IllegalArgumentException("row lengths do not match");
/*     */           } 
/*  85 */           rowStartPos = bitsPos;
/*  86 */           nRows++;
/*     */         } 
/*  88 */         pos++; continue;
/*  89 */       }  if (stringRepresentation.substring(pos, pos + setString.length()).equals(setString)) {
/*  90 */         pos += setString.length();
/*  91 */         bits[bitsPos] = true;
/*  92 */         bitsPos++; continue;
/*  93 */       }  if (stringRepresentation.substring(pos, pos + unsetString.length()).equals(unsetString)) {
/*  94 */         pos += unsetString.length();
/*  95 */         bits[bitsPos] = false;
/*  96 */         bitsPos++; continue;
/*     */       } 
/*  98 */       throw new IllegalArgumentException("illegal character encountered: " + stringRepresentation
/*  99 */           .substring(pos));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 104 */     if (bitsPos > rowStartPos) {
/* 105 */       if (rowLength == -1) {
/* 106 */         rowLength = bitsPos - rowStartPos;
/* 107 */       } else if (bitsPos - rowStartPos != rowLength) {
/* 108 */         throw new IllegalArgumentException("row lengths do not match");
/*     */       } 
/* 110 */       nRows++;
/*     */     } 
/*     */     
/* 113 */     BitMatrix matrix = new BitMatrix(rowLength, nRows);
/* 114 */     for (int i = 0; i < bitsPos; i++) {
/* 115 */       if (bits[i]) {
/* 116 */         matrix.set(i % rowLength, i / rowLength);
/*     */       }
/*     */     } 
/* 119 */     return matrix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean get(int x, int y) {
/* 130 */     int offset = y * this.rowSize + x / 32;
/* 131 */     return ((this.bits[offset] >>> (x & 0x1F) & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int x, int y) {
/* 141 */     int offset = y * this.rowSize + x / 32;
/* 142 */     this.bits[offset] = this.bits[offset] | 1 << (x & 0x1F);
/*     */   }
/*     */   
/*     */   public void unset(int x, int y) {
/* 146 */     int offset = y * this.rowSize + x / 32;
/* 147 */     this.bits[offset] = this.bits[offset] & (1 << (x & 0x1F) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(int x, int y) {
/* 157 */     int offset = y * this.rowSize + x / 32;
/* 158 */     this.bits[offset] = this.bits[offset] ^ 1 << (x & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void xor(BitMatrix mask) {
/* 168 */     if (this.width != mask.getWidth() || this.height != mask.getHeight() || this.rowSize != mask
/* 169 */       .getRowSize()) {
/* 170 */       throw new IllegalArgumentException("input matrix dimensions do not match");
/*     */     }
/* 172 */     BitArray rowArray = new BitArray(this.width / 32 + 1);
/* 173 */     for (int y = 0; y < this.height; y++) {
/* 174 */       int offset = y * this.rowSize;
/* 175 */       int[] row = mask.getRow(y, rowArray).getBitArray();
/* 176 */       for (int x = 0; x < this.rowSize; x++) {
/* 177 */         this.bits[offset + x] = this.bits[offset + x] ^ row[x];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 186 */     int max = this.bits.length;
/* 187 */     for (int i = 0; i < max; i++) {
/* 188 */       this.bits[i] = 0;
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
/*     */   public void setRegion(int left, int top, int width, int height) {
/* 201 */     if (top < 0 || left < 0) {
/* 202 */       throw new IllegalArgumentException("Left and top must be nonnegative");
/*     */     }
/* 204 */     if (height < 1 || width < 1) {
/* 205 */       throw new IllegalArgumentException("Height and width must be at least 1");
/*     */     }
/* 207 */     int right = left + width;
/* 208 */     int bottom = top + height;
/* 209 */     if (bottom > this.height || right > this.width) {
/* 210 */       throw new IllegalArgumentException("The region must fit inside the matrix");
/*     */     }
/* 212 */     for (int y = top; y < bottom; y++) {
/* 213 */       int offset = y * this.rowSize;
/* 214 */       for (int x = left; x < right; x++) {
/* 215 */         this.bits[offset + x / 32] = this.bits[offset + x / 32] | 1 << (x & 0x1F);
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
/*     */   public BitArray getRow(int y, BitArray row) {
/* 229 */     if (row == null || row.getSize() < this.width) {
/* 230 */       row = new BitArray(this.width);
/*     */     } else {
/* 232 */       row.clear();
/*     */     } 
/* 234 */     int offset = y * this.rowSize;
/* 235 */     for (int x = 0; x < this.rowSize; x++) {
/* 236 */       row.setBulk(x * 32, this.bits[offset + x]);
/*     */     }
/* 238 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRow(int y, BitArray row) {
/* 246 */     System.arraycopy(row.getBitArray(), 0, this.bits, y * this.rowSize, this.rowSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rotate180() {
/* 253 */     int width = getWidth();
/* 254 */     int height = getHeight();
/* 255 */     BitArray topRow = new BitArray(width);
/* 256 */     BitArray bottomRow = new BitArray(width);
/* 257 */     for (int i = 0; i < (height + 1) / 2; i++) {
/* 258 */       topRow = getRow(i, topRow);
/* 259 */       bottomRow = getRow(height - 1 - i, bottomRow);
/* 260 */       topRow.reverse();
/* 261 */       bottomRow.reverse();
/* 262 */       setRow(i, bottomRow);
/* 263 */       setRow(height - 1 - i, topRow);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getEnclosingRectangle() {
/* 273 */     int left = this.width;
/* 274 */     int top = this.height;
/* 275 */     int right = -1;
/* 276 */     int bottom = -1;
/*     */     
/* 278 */     for (int y = 0; y < this.height; y++) {
/* 279 */       for (int x32 = 0; x32 < this.rowSize; x32++) {
/* 280 */         int theBits = this.bits[y * this.rowSize + x32];
/* 281 */         if (theBits != 0) {
/* 282 */           if (y < top) {
/* 283 */             top = y;
/*     */           }
/* 285 */           if (y > bottom) {
/* 286 */             bottom = y;
/*     */           }
/* 288 */           if (x32 * 32 < left) {
/* 289 */             int bit = 0;
/* 290 */             while (theBits << 31 - bit == 0) {
/* 291 */               bit++;
/*     */             }
/* 293 */             if (x32 * 32 + bit < left) {
/* 294 */               left = x32 * 32 + bit;
/*     */             }
/*     */           } 
/* 297 */           if (x32 * 32 + 31 > right) {
/* 298 */             int bit = 31;
/* 299 */             while (theBits >>> bit == 0) {
/* 300 */               bit--;
/*     */             }
/* 302 */             if (x32 * 32 + bit > right) {
/* 303 */               right = x32 * 32 + bit;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     int width = right - left;
/* 311 */     int height = bottom - top;
/*     */     
/* 313 */     if (width < 0 || height < 0) {
/* 314 */       return null;
/*     */     }
/*     */     
/* 317 */     return new int[] { left, top, width, height };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getTopLeftOnBit() {
/* 326 */     int bitsOffset = 0;
/* 327 */     while (bitsOffset < this.bits.length && this.bits[bitsOffset] == 0) {
/* 328 */       bitsOffset++;
/*     */     }
/* 330 */     if (bitsOffset == this.bits.length) {
/* 331 */       return null;
/*     */     }
/* 333 */     int y = bitsOffset / this.rowSize;
/* 334 */     int x = bitsOffset % this.rowSize * 32;
/*     */     
/* 336 */     int theBits = this.bits[bitsOffset];
/* 337 */     int bit = 0;
/* 338 */     while (theBits << 31 - bit == 0) {
/* 339 */       bit++;
/*     */     }
/* 341 */     x += bit;
/* 342 */     return new int[] { x, y };
/*     */   }
/*     */   
/*     */   public int[] getBottomRightOnBit() {
/* 346 */     int bitsOffset = this.bits.length - 1;
/* 347 */     while (bitsOffset >= 0 && this.bits[bitsOffset] == 0) {
/* 348 */       bitsOffset--;
/*     */     }
/* 350 */     if (bitsOffset < 0) {
/* 351 */       return null;
/*     */     }
/*     */     
/* 354 */     int y = bitsOffset / this.rowSize;
/* 355 */     int x = bitsOffset % this.rowSize * 32;
/*     */     
/* 357 */     int theBits = this.bits[bitsOffset];
/* 358 */     int bit = 31;
/* 359 */     while (theBits >>> bit == 0) {
/* 360 */       bit--;
/*     */     }
/* 362 */     x += bit;
/*     */     
/* 364 */     return new int[] { x, y };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 371 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 378 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowSize() {
/* 385 */     return this.rowSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 390 */     if (!(o instanceof BitMatrix)) {
/* 391 */       return false;
/*     */     }
/* 393 */     BitMatrix other = (BitMatrix)o;
/* 394 */     return (this.width == other.width && this.height == other.height && this.rowSize == other.rowSize && 
/* 395 */       Arrays.equals(this.bits, other.bits));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 400 */     int hash = this.width;
/* 401 */     hash = 31 * hash + this.width;
/* 402 */     hash = 31 * hash + this.height;
/* 403 */     hash = 31 * hash + this.rowSize;
/* 404 */     hash = 31 * hash + Arrays.hashCode(this.bits);
/* 405 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 413 */     return toString("X ", "  ");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString(String setString, String unsetString) {
/* 422 */     return toString(setString, unsetString, "\n");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String toString(String setString, String unsetString, String lineSeparator) {
/* 434 */     StringBuilder result = new StringBuilder(this.height * (this.width + 1));
/* 435 */     for (int y = 0; y < this.height; y++) {
/* 436 */       for (int x = 0; x < this.width; x++) {
/* 437 */         result.append(get(x, y) ? setString : unsetString);
/*     */       }
/* 439 */       result.append(lineSeparator);
/*     */     } 
/* 441 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public BitMatrix clone() {
/* 446 */     return new BitMatrix(this.width, this.height, this.rowSize, (int[])this.bits.clone());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\BitMatrix.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */