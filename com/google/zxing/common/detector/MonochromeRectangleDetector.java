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
/*     */ public final class MonochromeRectangleDetector
/*     */ {
/*     */   private static final int MAX_MODULES = 32;
/*     */   private final BitMatrix image;
/*     */   
/*     */   public MonochromeRectangleDetector(BitMatrix image) {
/*  37 */     this.image = image;
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
/*     */   public ResultPoint[] detect() throws NotFoundException {
/*  51 */     int height = this.image.getHeight();
/*  52 */     int width = this.image.getWidth();
/*  53 */     int halfHeight = height / 2;
/*  54 */     int halfWidth = width / 2;
/*  55 */     int deltaY = Math.max(1, height / 256);
/*  56 */     int deltaX = Math.max(1, width / 256);
/*     */     
/*  58 */     int top = 0;
/*  59 */     int bottom = height;
/*  60 */     int left = 0;
/*  61 */     int right = width;
/*  62 */     ResultPoint pointA = findCornerFromCenter(halfWidth, 0, left, right, halfHeight, -deltaY, top, bottom, halfWidth / 2);
/*     */     
/*  64 */     top = (int)pointA.getY() - 1;
/*  65 */     ResultPoint pointB = findCornerFromCenter(halfWidth, -deltaX, left, right, halfHeight, 0, top, bottom, halfHeight / 2);
/*     */     
/*  67 */     left = (int)pointB.getX() - 1;
/*  68 */     ResultPoint pointC = findCornerFromCenter(halfWidth, deltaX, left, right, halfHeight, 0, top, bottom, halfHeight / 2);
/*     */     
/*  70 */     right = (int)pointC.getX() + 1;
/*  71 */     ResultPoint pointD = findCornerFromCenter(halfWidth, 0, left, right, halfHeight, deltaY, top, bottom, halfWidth / 2);
/*     */     
/*  73 */     bottom = (int)pointD.getY() + 1;
/*     */ 
/*     */     
/*  76 */     pointA = findCornerFromCenter(halfWidth, 0, left, right, halfHeight, -deltaY, top, bottom, halfWidth / 4);
/*     */ 
/*     */     
/*  79 */     return new ResultPoint[] { pointA, pointB, pointC, pointD };
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
/*     */   
/*     */   private ResultPoint findCornerFromCenter(int centerX, int deltaX, int left, int right, int centerY, int deltaY, int top, int bottom, int maxWhiteRun) throws NotFoundException {
/* 109 */     int[] lastRange = null;
/* 110 */     int y = centerY, x = centerX;
/* 111 */     for (; y < bottom && y >= top && x < right && x >= left; 
/* 112 */       y += deltaY, x += deltaX) {
/*     */       int[] range;
/* 114 */       if (deltaX == 0) {
/*     */         
/* 116 */         range = blackWhiteRange(y, maxWhiteRun, left, right, true);
/*     */       } else {
/*     */         
/* 119 */         range = blackWhiteRange(x, maxWhiteRun, top, bottom, false);
/*     */       } 
/* 121 */       if (range == null) {
/* 122 */         if (lastRange == null) {
/* 123 */           throw NotFoundException.getNotFoundInstance();
/*     */         }
/*     */         
/* 126 */         if (deltaX == 0) {
/* 127 */           int lastY = y - deltaY;
/* 128 */           if (lastRange[0] < centerX) {
/* 129 */             if (lastRange[1] > centerX)
/*     */             {
/* 131 */               return new ResultPoint((deltaY > 0) ? lastRange[0] : lastRange[1], lastY);
/*     */             }
/* 133 */             return new ResultPoint(lastRange[0], lastY);
/*     */           } 
/* 135 */           return new ResultPoint(lastRange[1], lastY);
/*     */         } 
/*     */         
/* 138 */         int lastX = x - deltaX;
/* 139 */         if (lastRange[0] < centerY) {
/* 140 */           if (lastRange[1] > centerY) {
/* 141 */             return new ResultPoint(lastX, (deltaX < 0) ? lastRange[0] : lastRange[1]);
/*     */           }
/* 143 */           return new ResultPoint(lastX, lastRange[0]);
/*     */         } 
/* 145 */         return new ResultPoint(lastX, lastRange[1]);
/*     */       } 
/*     */ 
/*     */       
/* 149 */       lastRange = range;
/*     */     } 
/* 151 */     throw NotFoundException.getNotFoundInstance();
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
/*     */   private int[] blackWhiteRange(int fixedDimension, int maxWhiteRun, int minDim, int maxDim, boolean horizontal) {
/* 170 */     int center = (minDim + maxDim) / 2;
/*     */ 
/*     */     
/* 173 */     int start = center;
/* 174 */     while (start >= minDim) {
/* 175 */       if (horizontal ? this.image.get(start, fixedDimension) : this.image.get(fixedDimension, start)) {
/* 176 */         start--; continue;
/*     */       } 
/* 178 */       int whiteRunStart = start;
/*     */       do {
/* 180 */         start--;
/* 181 */       } while (start >= minDim && (horizontal ? this.image.get(start, fixedDimension) : this.image
/* 182 */         .get(fixedDimension, start)));
/* 183 */       int whiteRunSize = whiteRunStart - start;
/* 184 */       if (start < minDim || whiteRunSize > maxWhiteRun) {
/* 185 */         start = whiteRunStart;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 190 */     start++;
/*     */ 
/*     */     
/* 193 */     int end = center;
/* 194 */     while (end < maxDim) {
/* 195 */       if (horizontal ? this.image.get(end, fixedDimension) : this.image.get(fixedDimension, end)) {
/* 196 */         end++; continue;
/*     */       } 
/* 198 */       int whiteRunStart = end;
/*     */       do {
/* 200 */         end++;
/* 201 */       } while (end < maxDim && (horizontal ? this.image.get(end, fixedDimension) : this.image
/* 202 */         .get(fixedDimension, end)));
/* 203 */       int whiteRunSize = end - whiteRunStart;
/* 204 */       if (end >= maxDim || whiteRunSize > maxWhiteRun) {
/* 205 */         end = whiteRunStart;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 210 */     end--;
/*     */     
/* 212 */     (new int[2])[0] = start; (new int[2])[1] = end; return (end > start) ? new int[2] : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\detector\MonochromeRectangleDetector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */