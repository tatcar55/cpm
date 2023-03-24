/*     */ package com.google.zxing.qrcode.detector;
/*     */ 
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.ResultPointCallback;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DetectorResult;
/*     */ import com.google.zxing.common.GridSampler;
/*     */ import com.google.zxing.common.PerspectiveTransform;
/*     */ import com.google.zxing.common.detector.MathUtils;
/*     */ import com.google.zxing.qrcode.decoder.Version;
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
/*     */ public class Detector
/*     */ {
/*     */   private final BitMatrix image;
/*     */   private ResultPointCallback resultPointCallback;
/*     */   
/*     */   public Detector(BitMatrix image) {
/*  45 */     this.image = image;
/*     */   }
/*     */   
/*     */   protected final BitMatrix getImage() {
/*  49 */     return this.image;
/*     */   }
/*     */   
/*     */   protected final ResultPointCallback getResultPointCallback() {
/*  53 */     return this.resultPointCallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DetectorResult detect() throws NotFoundException, FormatException {
/*  64 */     return detect(null);
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
/*     */   public final DetectorResult detect(Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
/*  77 */     this
/*  78 */       .resultPointCallback = (hints == null) ? null : (ResultPointCallback)hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/*     */     
/*  80 */     FinderPatternFinder finder = new FinderPatternFinder(this.image, this.resultPointCallback);
/*  81 */     FinderPatternInfo info = finder.find(hints);
/*     */     
/*  83 */     return processFinderPatternInfo(info);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final DetectorResult processFinderPatternInfo(FinderPatternInfo info) throws NotFoundException, FormatException {
/*     */     ResultPoint[] points;
/*  89 */     FinderPattern topLeft = info.getTopLeft();
/*  90 */     FinderPattern topRight = info.getTopRight();
/*  91 */     FinderPattern bottomLeft = info.getBottomLeft();
/*     */     
/*  93 */     float moduleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
/*  94 */     if (moduleSize < 1.0F) {
/*  95 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*  97 */     int dimension = computeDimension(topLeft, topRight, bottomLeft, moduleSize);
/*  98 */     Version provisionalVersion = Version.getProvisionalVersionForDimension(dimension);
/*  99 */     int modulesBetweenFPCenters = provisionalVersion.getDimensionForVersion() - 7;
/*     */     
/* 101 */     AlignmentPattern alignmentPattern = null;
/*     */     
/* 103 */     if ((provisionalVersion.getAlignmentPatternCenters()).length > 0) {
/*     */ 
/*     */       
/* 106 */       float bottomRightX = topRight.getX() - topLeft.getX() + bottomLeft.getX();
/* 107 */       float bottomRightY = topRight.getY() - topLeft.getY() + bottomLeft.getY();
/*     */ 
/*     */ 
/*     */       
/* 111 */       float correctionToTopLeft = 1.0F - 3.0F / modulesBetweenFPCenters;
/* 112 */       int estAlignmentX = (int)(topLeft.getX() + correctionToTopLeft * (bottomRightX - topLeft.getX()));
/* 113 */       int estAlignmentY = (int)(topLeft.getY() + correctionToTopLeft * (bottomRightY - topLeft.getY()));
/*     */       
/*     */       int i;
/* 116 */       for (i = 4; i <= 16; i <<= 1) {
/*     */         try {
/* 118 */           alignmentPattern = findAlignmentInRegion(moduleSize, estAlignmentX, estAlignmentY, i);
/*     */ 
/*     */ 
/*     */           
/*     */           break;
/* 123 */         } catch (NotFoundException notFoundException) {}
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     PerspectiveTransform transform = createTransform(topLeft, topRight, bottomLeft, alignmentPattern, dimension);
/*     */     
/* 133 */     BitMatrix bits = sampleGrid(this.image, transform, dimension);
/*     */ 
/*     */     
/* 136 */     if (alignmentPattern == null) {
/* 137 */       points = new ResultPoint[] { bottomLeft, topLeft, topRight };
/*     */     } else {
/* 139 */       points = new ResultPoint[] { bottomLeft, topLeft, topRight, alignmentPattern };
/*     */     } 
/* 141 */     return new DetectorResult(bits, points);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PerspectiveTransform createTransform(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, ResultPoint alignmentPattern, int dimension) {
/* 149 */     float bottomRightX, bottomRightY, sourceBottomRightX, sourceBottomRightY, dimMinusThree = dimension - 3.5F;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (alignmentPattern != null) {
/* 155 */       bottomRightX = alignmentPattern.getX();
/* 156 */       bottomRightY = alignmentPattern.getY();
/* 157 */       sourceBottomRightX = dimMinusThree - 3.0F;
/* 158 */       sourceBottomRightY = sourceBottomRightX;
/*     */     } else {
/*     */       
/* 161 */       bottomRightX = topRight.getX() - topLeft.getX() + bottomLeft.getX();
/* 162 */       bottomRightY = topRight.getY() - topLeft.getY() + bottomLeft.getY();
/* 163 */       sourceBottomRightX = dimMinusThree;
/* 164 */       sourceBottomRightY = dimMinusThree;
/*     */     } 
/*     */     
/* 167 */     return PerspectiveTransform.quadrilateralToQuadrilateral(3.5F, 3.5F, dimMinusThree, 3.5F, sourceBottomRightX, sourceBottomRightY, 3.5F, dimMinusThree, topLeft
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 176 */         .getX(), topLeft
/* 177 */         .getY(), topRight
/* 178 */         .getX(), topRight
/* 179 */         .getY(), bottomRightX, bottomRightY, bottomLeft
/*     */ 
/*     */         
/* 182 */         .getX(), bottomLeft
/* 183 */         .getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix sampleGrid(BitMatrix image, PerspectiveTransform transform, int dimension) throws NotFoundException {
/* 190 */     GridSampler sampler = GridSampler.getInstance();
/* 191 */     return sampler.sampleGrid(image, dimension, dimension, transform);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeDimension(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft, float moduleSize) throws NotFoundException {
/* 202 */     int tltrCentersDimension = MathUtils.round(ResultPoint.distance(topLeft, topRight) / moduleSize);
/* 203 */     int tlblCentersDimension = MathUtils.round(ResultPoint.distance(topLeft, bottomLeft) / moduleSize);
/* 204 */     int dimension = (tltrCentersDimension + tlblCentersDimension) / 2 + 7;
/* 205 */     switch (dimension & 0x3) {
/*     */       case 0:
/* 207 */         dimension++;
/*     */         break;
/*     */       
/*     */       case 2:
/* 211 */         dimension--;
/*     */         break;
/*     */       case 3:
/* 214 */         throw NotFoundException.getNotFoundInstance();
/*     */     } 
/* 216 */     return dimension;
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
/*     */   protected final float calculateModuleSize(ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomLeft) {
/* 233 */     return (calculateModuleSizeOneWay(topLeft, topRight) + calculateModuleSizeOneWay(topLeft, bottomLeft)) / 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float calculateModuleSizeOneWay(ResultPoint pattern, ResultPoint otherPattern) {
/* 242 */     float moduleSizeEst1 = sizeOfBlackWhiteBlackRunBothWays((int)pattern.getX(), 
/* 243 */         (int)pattern.getY(), 
/* 244 */         (int)otherPattern.getX(), 
/* 245 */         (int)otherPattern.getY());
/* 246 */     float moduleSizeEst2 = sizeOfBlackWhiteBlackRunBothWays((int)otherPattern.getX(), 
/* 247 */         (int)otherPattern.getY(), 
/* 248 */         (int)pattern.getX(), 
/* 249 */         (int)pattern.getY());
/* 250 */     if (Float.isNaN(moduleSizeEst1)) {
/* 251 */       return moduleSizeEst2 / 7.0F;
/*     */     }
/* 253 */     if (Float.isNaN(moduleSizeEst2)) {
/* 254 */       return moduleSizeEst1 / 7.0F;
/*     */     }
/*     */ 
/*     */     
/* 258 */     return (moduleSizeEst1 + moduleSizeEst2) / 14.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float sizeOfBlackWhiteBlackRunBothWays(int fromX, int fromY, int toX, int toY) {
/* 268 */     float result = sizeOfBlackWhiteBlackRun(fromX, fromY, toX, toY);
/*     */ 
/*     */     
/* 271 */     float scale = 1.0F;
/* 272 */     int otherToX = fromX - toX - fromX;
/* 273 */     if (otherToX < 0) {
/* 274 */       scale = fromX / (fromX - otherToX);
/* 275 */       otherToX = 0;
/* 276 */     } else if (otherToX >= this.image.getWidth()) {
/* 277 */       scale = (this.image.getWidth() - 1 - fromX) / (otherToX - fromX);
/* 278 */       otherToX = this.image.getWidth() - 1;
/*     */     } 
/* 280 */     int otherToY = (int)(fromY - (toY - fromY) * scale);
/*     */     
/* 282 */     scale = 1.0F;
/* 283 */     if (otherToY < 0) {
/* 284 */       scale = fromY / (fromY - otherToY);
/* 285 */       otherToY = 0;
/* 286 */     } else if (otherToY >= this.image.getHeight()) {
/* 287 */       scale = (this.image.getHeight() - 1 - fromY) / (otherToY - fromY);
/* 288 */       otherToY = this.image.getHeight() - 1;
/*     */     } 
/* 290 */     otherToX = (int)(fromX + (otherToX - fromX) * scale);
/*     */     
/* 292 */     result += sizeOfBlackWhiteBlackRun(fromX, fromY, otherToX, otherToY);
/*     */ 
/*     */     
/* 295 */     return result - 1.0F;
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
/*     */   private float sizeOfBlackWhiteBlackRun(int fromX, int fromY, int toX, int toY) {
/* 309 */     boolean steep = (Math.abs(toY - fromY) > Math.abs(toX - fromX));
/* 310 */     if (steep) {
/* 311 */       int temp = fromX;
/* 312 */       fromX = fromY;
/* 313 */       fromY = temp;
/* 314 */       temp = toX;
/* 315 */       toX = toY;
/* 316 */       toY = temp;
/*     */     } 
/*     */     
/* 319 */     int dx = Math.abs(toX - fromX);
/* 320 */     int dy = Math.abs(toY - fromY);
/* 321 */     int error = -dx / 2;
/* 322 */     int xstep = (fromX < toX) ? 1 : -1;
/* 323 */     int ystep = (fromY < toY) ? 1 : -1;
/*     */ 
/*     */     
/* 326 */     int state = 0;
/*     */     
/* 328 */     int xLimit = toX + xstep; int x, y;
/* 329 */     for (x = fromX, y = fromY; x != xLimit; x += xstep) {
/* 330 */       int realX = steep ? y : x;
/* 331 */       int realY = steep ? x : y;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       if (((state == 1)) == this.image.get(realX, realY)) {
/* 337 */         if (state == 2) {
/* 338 */           return MathUtils.distance(x, y, fromX, fromY);
/*     */         }
/* 340 */         state++;
/*     */       } 
/*     */       
/* 343 */       error += dy;
/* 344 */       if (error > 0) {
/* 345 */         if (y == toY) {
/*     */           break;
/*     */         }
/* 348 */         y += ystep;
/* 349 */         error -= dx;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 355 */     if (state == 2) {
/* 356 */       return MathUtils.distance(toX + xstep, toY, fromX, fromY);
/*     */     }
/*     */     
/* 359 */     return Float.NaN;
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
/*     */   protected final AlignmentPattern findAlignmentInRegion(float overallEstModuleSize, int estAlignmentX, int estAlignmentY, float allowanceFactor) throws NotFoundException {
/* 380 */     int allowance = (int)(allowanceFactor * overallEstModuleSize);
/* 381 */     int alignmentAreaLeftX = Math.max(0, estAlignmentX - allowance);
/* 382 */     int alignmentAreaRightX = Math.min(this.image.getWidth() - 1, estAlignmentX + allowance);
/* 383 */     if ((alignmentAreaRightX - alignmentAreaLeftX) < overallEstModuleSize * 3.0F) {
/* 384 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 387 */     int alignmentAreaTopY = Math.max(0, estAlignmentY - allowance);
/* 388 */     int alignmentAreaBottomY = Math.min(this.image.getHeight() - 1, estAlignmentY + allowance);
/* 389 */     if ((alignmentAreaBottomY - alignmentAreaTopY) < overallEstModuleSize * 3.0F) {
/* 390 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 393 */     AlignmentPatternFinder alignmentFinder = new AlignmentPatternFinder(this.image, alignmentAreaLeftX, alignmentAreaTopY, alignmentAreaRightX - alignmentAreaLeftX, alignmentAreaBottomY - alignmentAreaTopY, overallEstModuleSize, this.resultPointCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     return alignmentFinder.find();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\detector\Detector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */