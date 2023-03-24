/*     */ package com.google.zxing.common.detector;
/*     */ 
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WhiteRectangleDetector
/*     */ {
/*     */   private static final int INIT_SIZE = 10;
/*     */   private static final int CORR = 1;
/*     */   private final BitMatrix image;
/*     */   private final int height;
/*     */   private final int width;
/*     */   private final int leftInit;
/*     */   private final int rightInit;
/*     */   private final int downInit;
/*     */   private final int upInit;
/*     */   
/*     */   public WhiteRectangleDetector(BitMatrix image) throws NotFoundException {
/*  47 */     this(image, 10, image.getWidth() / 2, image.getHeight() / 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WhiteRectangleDetector(BitMatrix image, int initSize, int x, int y) throws NotFoundException {
/*  58 */     this.image = image;
/*  59 */     this.height = image.getHeight();
/*  60 */     this.width = image.getWidth();
/*  61 */     int halfsize = initSize / 2;
/*  62 */     this.leftInit = x - halfsize;
/*  63 */     this.rightInit = x + halfsize;
/*  64 */     this.upInit = y - halfsize;
/*  65 */     this.downInit = y + halfsize;
/*  66 */     if (this.upInit < 0 || this.leftInit < 0 || this.downInit >= this.height || this.rightInit >= this.width) {
/*  67 */       throw NotFoundException.getNotFoundInstance();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultPoint[] detect() throws NotFoundException {
/*  87 */     int left = this.leftInit;
/*  88 */     int right = this.rightInit;
/*  89 */     int up = this.upInit;
/*  90 */     int down = this.downInit;
/*  91 */     boolean sizeExceeded = false;
/*  92 */     boolean aBlackPointFoundOnBorder = true;
/*  93 */     boolean atLeastOneBlackPointFoundOnBorder = false;
/*     */     
/*  95 */     boolean atLeastOneBlackPointFoundOnRight = false;
/*  96 */     boolean atLeastOneBlackPointFoundOnBottom = false;
/*  97 */     boolean atLeastOneBlackPointFoundOnLeft = false;
/*  98 */     boolean atLeastOneBlackPointFoundOnTop = false;
/*     */     
/* 100 */     while (aBlackPointFoundOnBorder) {
/*     */       
/* 102 */       aBlackPointFoundOnBorder = false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       boolean rightBorderNotWhite = true;
/* 108 */       while ((rightBorderNotWhite || !atLeastOneBlackPointFoundOnRight) && right < this.width) {
/* 109 */         rightBorderNotWhite = containsBlackPoint(up, down, right, false);
/* 110 */         if (rightBorderNotWhite) {
/* 111 */           right++;
/* 112 */           aBlackPointFoundOnBorder = true;
/* 113 */           atLeastOneBlackPointFoundOnRight = true; continue;
/* 114 */         }  if (!atLeastOneBlackPointFoundOnRight) {
/* 115 */           right++;
/*     */         }
/*     */       } 
/*     */       
/* 119 */       if (right >= this.width) {
/* 120 */         sizeExceeded = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 127 */       boolean bottomBorderNotWhite = true;
/* 128 */       while ((bottomBorderNotWhite || !atLeastOneBlackPointFoundOnBottom) && down < this.height) {
/* 129 */         bottomBorderNotWhite = containsBlackPoint(left, right, down, true);
/* 130 */         if (bottomBorderNotWhite) {
/* 131 */           down++;
/* 132 */           aBlackPointFoundOnBorder = true;
/* 133 */           atLeastOneBlackPointFoundOnBottom = true; continue;
/* 134 */         }  if (!atLeastOneBlackPointFoundOnBottom) {
/* 135 */           down++;
/*     */         }
/*     */       } 
/*     */       
/* 139 */       if (down >= this.height) {
/* 140 */         sizeExceeded = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 147 */       boolean leftBorderNotWhite = true;
/* 148 */       while ((leftBorderNotWhite || !atLeastOneBlackPointFoundOnLeft) && left >= 0) {
/* 149 */         leftBorderNotWhite = containsBlackPoint(up, down, left, false);
/* 150 */         if (leftBorderNotWhite) {
/* 151 */           left--;
/* 152 */           aBlackPointFoundOnBorder = true;
/* 153 */           atLeastOneBlackPointFoundOnLeft = true; continue;
/* 154 */         }  if (!atLeastOneBlackPointFoundOnLeft) {
/* 155 */           left--;
/*     */         }
/*     */       } 
/*     */       
/* 159 */       if (left < 0) {
/* 160 */         sizeExceeded = true;
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 167 */       boolean topBorderNotWhite = true;
/* 168 */       while ((topBorderNotWhite || !atLeastOneBlackPointFoundOnTop) && up >= 0) {
/* 169 */         topBorderNotWhite = containsBlackPoint(left, right, up, true);
/* 170 */         if (topBorderNotWhite) {
/* 171 */           up--;
/* 172 */           aBlackPointFoundOnBorder = true;
/* 173 */           atLeastOneBlackPointFoundOnTop = true; continue;
/* 174 */         }  if (!atLeastOneBlackPointFoundOnTop) {
/* 175 */           up--;
/*     */         }
/*     */       } 
/*     */       
/* 179 */       if (up < 0) {
/* 180 */         sizeExceeded = true;
/*     */         
/*     */         break;
/*     */       } 
/* 184 */       if (aBlackPointFoundOnBorder) {
/* 185 */         atLeastOneBlackPointFoundOnBorder = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (!sizeExceeded && atLeastOneBlackPointFoundOnBorder) {
/*     */       
/* 192 */       int maxSize = right - left;
/*     */       
/* 194 */       ResultPoint z = null;
/* 195 */       for (int i = 1; i < maxSize; i++) {
/* 196 */         z = getBlackPointOnSegment(left, (down - i), (left + i), down);
/* 197 */         if (z != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 202 */       if (z == null) {
/* 203 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */       
/* 206 */       ResultPoint t = null;
/*     */       
/* 208 */       for (int j = 1; j < maxSize; j++) {
/* 209 */         t = getBlackPointOnSegment(left, (up + j), (left + j), up);
/* 210 */         if (t != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 215 */       if (t == null) {
/* 216 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */       
/* 219 */       ResultPoint x = null;
/*     */       
/* 221 */       for (int k = 1; k < maxSize; k++) {
/* 222 */         x = getBlackPointOnSegment(right, (up + k), (right - k), up);
/* 223 */         if (x != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 228 */       if (x == null) {
/* 229 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */       
/* 232 */       ResultPoint y = null;
/*     */       
/* 234 */       for (int m = 1; m < maxSize; m++) {
/* 235 */         y = getBlackPointOnSegment(right, (down - m), (right - m), down);
/* 236 */         if (y != null) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 241 */       if (y == null) {
/* 242 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/*     */       
/* 245 */       return centerEdges(y, z, x, t);
/*     */     } 
/*     */     
/* 248 */     throw NotFoundException.getNotFoundInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   private ResultPoint getBlackPointOnSegment(float aX, float aY, float bX, float bY) {
/* 253 */     int dist = MathUtils.round(MathUtils.distance(aX, aY, bX, bY));
/* 254 */     float xStep = (bX - aX) / dist;
/* 255 */     float yStep = (bY - aY) / dist;
/*     */     
/* 257 */     for (int i = 0; i < dist; i++) {
/* 258 */       int x = MathUtils.round(aX + i * xStep);
/* 259 */       int y = MathUtils.round(aY + i * yStep);
/* 260 */       if (this.image.get(x, y)) {
/* 261 */         return new ResultPoint(x, y);
/*     */       }
/*     */     } 
/* 264 */     return null;
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
/*     */   private ResultPoint[] centerEdges(ResultPoint y, ResultPoint z, ResultPoint x, ResultPoint t) {
/* 290 */     float yi = y.getX();
/* 291 */     float yj = y.getY();
/* 292 */     float zi = z.getX();
/* 293 */     float zj = z.getY();
/* 294 */     float xi = x.getX();
/* 295 */     float xj = x.getY();
/* 296 */     float ti = t.getX();
/* 297 */     float tj = t.getY();
/*     */     
/* 299 */     if (yi < this.width / 2.0F) {
/* 300 */       return new ResultPoint[] { new ResultPoint(ti - 1.0F, tj + 1.0F), new ResultPoint(zi + 1.0F, zj + 1.0F), new ResultPoint(xi - 1.0F, xj - 1.0F), new ResultPoint(yi + 1.0F, yj - 1.0F) };
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 306 */     return new ResultPoint[] { new ResultPoint(ti + 1.0F, tj + 1.0F), new ResultPoint(zi + 1.0F, zj - 1.0F), new ResultPoint(xi - 1.0F, xj + 1.0F), new ResultPoint(yi - 1.0F, yj - 1.0F) };
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
/*     */   private boolean containsBlackPoint(int a, int b, int fixed, boolean horizontal) {
/* 325 */     if (horizontal) {
/* 326 */       for (int x = a; x <= b; x++) {
/* 327 */         if (this.image.get(x, fixed)) {
/* 328 */           return true;
/*     */         }
/*     */       } 
/*     */     } else {
/* 332 */       for (int y = a; y <= b; y++) {
/* 333 */         if (this.image.get(fixed, y)) {
/* 334 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 339 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\detector\WhiteRectangleDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */