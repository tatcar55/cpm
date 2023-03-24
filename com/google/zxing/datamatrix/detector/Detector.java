/*     */ package com.google.zxing.datamatrix.detector;
/*     */ 
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DetectorResult;
/*     */ import com.google.zxing.common.GridSampler;
/*     */ import com.google.zxing.common.detector.MathUtils;
/*     */ import com.google.zxing.common.detector.WhiteRectangleDetector;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
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
/*     */ public final class Detector
/*     */ {
/*     */   private final BitMatrix image;
/*     */   private final WhiteRectangleDetector rectangleDetector;
/*     */   
/*     */   public Detector(BitMatrix image) throws NotFoundException {
/*  47 */     this.image = image;
/*  48 */     this.rectangleDetector = new WhiteRectangleDetector(image);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DetectorResult detect() throws NotFoundException {
/*     */     ResultPoint topRight;
/*     */     BitMatrix bits;
/*  59 */     ResultPoint correctedTopRight, cornerPoints[] = this.rectangleDetector.detect();
/*  60 */     ResultPoint pointA = cornerPoints[0];
/*  61 */     ResultPoint pointB = cornerPoints[1];
/*  62 */     ResultPoint pointC = cornerPoints[2];
/*  63 */     ResultPoint pointD = cornerPoints[3];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     List<ResultPointsAndTransitions> transitions = new ArrayList<>(4);
/*  69 */     transitions.add(transitionsBetween(pointA, pointB));
/*  70 */     transitions.add(transitionsBetween(pointA, pointC));
/*  71 */     transitions.add(transitionsBetween(pointB, pointD));
/*  72 */     transitions.add(transitionsBetween(pointC, pointD));
/*  73 */     Collections.sort(transitions, new ResultPointsAndTransitionsComparator());
/*     */ 
/*     */ 
/*     */     
/*  77 */     ResultPointsAndTransitions lSideOne = transitions.get(0);
/*  78 */     ResultPointsAndTransitions lSideTwo = transitions.get(1);
/*     */ 
/*     */ 
/*     */     
/*  82 */     Map<ResultPoint, Integer> pointCount = new HashMap<>();
/*  83 */     increment(pointCount, lSideOne.getFrom());
/*  84 */     increment(pointCount, lSideOne.getTo());
/*  85 */     increment(pointCount, lSideTwo.getFrom());
/*  86 */     increment(pointCount, lSideTwo.getTo());
/*     */     
/*  88 */     ResultPoint maybeTopLeft = null;
/*  89 */     ResultPoint bottomLeft = null;
/*  90 */     ResultPoint maybeBottomRight = null;
/*  91 */     for (Map.Entry<ResultPoint, Integer> entry : pointCount.entrySet()) {
/*  92 */       ResultPoint point = entry.getKey();
/*  93 */       Integer value = entry.getValue();
/*  94 */       if (value.intValue() == 2) {
/*  95 */         bottomLeft = point;
/*     */         continue;
/*     */       } 
/*  98 */       if (maybeTopLeft == null) {
/*  99 */         maybeTopLeft = point; continue;
/*     */       } 
/* 101 */       maybeBottomRight = point;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     if (maybeTopLeft == null || bottomLeft == null || maybeBottomRight == null) {
/* 107 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */ 
/*     */     
/* 111 */     ResultPoint[] corners = { maybeTopLeft, bottomLeft, maybeBottomRight };
/*     */     
/* 113 */     ResultPoint.orderBestPatterns(corners);
/*     */ 
/*     */     
/* 116 */     ResultPoint bottomRight = corners[0];
/* 117 */     bottomLeft = corners[1];
/* 118 */     ResultPoint topLeft = corners[2];
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (!pointCount.containsKey(pointA)) {
/* 123 */       topRight = pointA;
/* 124 */     } else if (!pointCount.containsKey(pointB)) {
/* 125 */       topRight = pointB;
/* 126 */     } else if (!pointCount.containsKey(pointC)) {
/* 127 */       topRight = pointC;
/*     */     } else {
/* 129 */       topRight = pointD;
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
/* 141 */     int dimensionTop = transitionsBetween(topLeft, topRight).getTransitions();
/* 142 */     int dimensionRight = transitionsBetween(bottomRight, topRight).getTransitions();
/*     */     
/* 144 */     if ((dimensionTop & 0x1) == 1)
/*     */     {
/* 146 */       dimensionTop++;
/*     */     }
/* 148 */     dimensionTop += 2;
/*     */     
/* 150 */     if ((dimensionRight & 0x1) == 1)
/*     */     {
/* 152 */       dimensionRight++;
/*     */     }
/* 154 */     dimensionRight += 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     if (4 * dimensionTop >= 7 * dimensionRight || 4 * dimensionRight >= 7 * dimensionTop) {
/*     */ 
/*     */ 
/*     */       
/* 166 */       correctedTopRight = correctTopRightRectangular(bottomLeft, bottomRight, topLeft, topRight, dimensionTop, dimensionRight);
/* 167 */       if (correctedTopRight == null) {
/* 168 */         correctedTopRight = topRight;
/*     */       }
/*     */       
/* 171 */       dimensionTop = transitionsBetween(topLeft, correctedTopRight).getTransitions();
/* 172 */       dimensionRight = transitionsBetween(bottomRight, correctedTopRight).getTransitions();
/*     */       
/* 174 */       if ((dimensionTop & 0x1) == 1)
/*     */       {
/* 176 */         dimensionTop++;
/*     */       }
/*     */       
/* 179 */       if ((dimensionRight & 0x1) == 1)
/*     */       {
/* 181 */         dimensionRight++;
/*     */       }
/*     */       
/* 184 */       bits = sampleGrid(this.image, topLeft, bottomLeft, bottomRight, correctedTopRight, dimensionTop, dimensionRight);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 189 */       int dimension = Math.min(dimensionRight, dimensionTop);
/*     */       
/* 191 */       correctedTopRight = correctTopRight(bottomLeft, bottomRight, topLeft, topRight, dimension);
/* 192 */       if (correctedTopRight == null) {
/* 193 */         correctedTopRight = topRight;
/*     */       }
/*     */ 
/*     */       
/* 197 */       int dimensionCorrected = Math.max(transitionsBetween(topLeft, correctedTopRight).getTransitions(), 
/* 198 */           transitionsBetween(bottomRight, correctedTopRight).getTransitions());
/* 199 */       dimensionCorrected++;
/* 200 */       if ((dimensionCorrected & 0x1) == 1) {
/* 201 */         dimensionCorrected++;
/*     */       }
/*     */       
/* 204 */       bits = sampleGrid(this.image, topLeft, bottomLeft, bottomRight, correctedTopRight, dimensionCorrected, dimensionCorrected);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 213 */     return new DetectorResult(bits, new ResultPoint[] { topLeft, bottomLeft, bottomRight, correctedTopRight });
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
/*     */   private ResultPoint correctTopRightRectangular(ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topLeft, ResultPoint topRight, int dimensionTop, int dimensionRight) {
/* 227 */     float corr = distance(bottomLeft, bottomRight) / dimensionTop;
/* 228 */     int norm = distance(topLeft, topRight);
/* 229 */     float cos = (topRight.getX() - topLeft.getX()) / norm;
/* 230 */     float sin = (topRight.getY() - topLeft.getY()) / norm;
/*     */     
/* 232 */     ResultPoint c1 = new ResultPoint(topRight.getX() + corr * cos, topRight.getY() + corr * sin);
/*     */     
/* 234 */     corr = distance(bottomLeft, topLeft) / dimensionRight;
/* 235 */     norm = distance(bottomRight, topRight);
/* 236 */     cos = (topRight.getX() - bottomRight.getX()) / norm;
/* 237 */     sin = (topRight.getY() - bottomRight.getY()) / norm;
/*     */     
/* 239 */     ResultPoint c2 = new ResultPoint(topRight.getX() + corr * cos, topRight.getY() + corr * sin);
/*     */     
/* 241 */     if (!isValid(c1)) {
/* 242 */       if (isValid(c2)) {
/* 243 */         return c2;
/*     */       }
/* 245 */       return null;
/*     */     } 
/* 247 */     if (!isValid(c2)) {
/* 248 */       return c1;
/*     */     }
/*     */ 
/*     */     
/* 252 */     int l1 = Math.abs(dimensionTop - transitionsBetween(topLeft, c1).getTransitions()) + Math.abs(dimensionRight - transitionsBetween(bottomRight, c1).getTransitions());
/*     */     
/* 254 */     int l2 = Math.abs(dimensionTop - transitionsBetween(topLeft, c2).getTransitions()) + Math.abs(dimensionRight - transitionsBetween(bottomRight, c2).getTransitions());
/*     */     
/* 256 */     if (l1 <= l2) {
/* 257 */       return c1;
/*     */     }
/*     */     
/* 260 */     return c2;
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
/*     */   private ResultPoint correctTopRight(ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topLeft, ResultPoint topRight, int dimension) {
/* 273 */     float corr = distance(bottomLeft, bottomRight) / dimension;
/* 274 */     int norm = distance(topLeft, topRight);
/* 275 */     float cos = (topRight.getX() - topLeft.getX()) / norm;
/* 276 */     float sin = (topRight.getY() - topLeft.getY()) / norm;
/*     */     
/* 278 */     ResultPoint c1 = new ResultPoint(topRight.getX() + corr * cos, topRight.getY() + corr * sin);
/*     */     
/* 280 */     corr = distance(bottomLeft, topLeft) / dimension;
/* 281 */     norm = distance(bottomRight, topRight);
/* 282 */     cos = (topRight.getX() - bottomRight.getX()) / norm;
/* 283 */     sin = (topRight.getY() - bottomRight.getY()) / norm;
/*     */     
/* 285 */     ResultPoint c2 = new ResultPoint(topRight.getX() + corr * cos, topRight.getY() + corr * sin);
/*     */     
/* 287 */     if (!isValid(c1)) {
/* 288 */       if (isValid(c2)) {
/* 289 */         return c2;
/*     */       }
/* 291 */       return null;
/*     */     } 
/* 293 */     if (!isValid(c2)) {
/* 294 */       return c1;
/*     */     }
/*     */     
/* 297 */     int l1 = Math.abs(transitionsBetween(topLeft, c1).getTransitions() - 
/* 298 */         transitionsBetween(bottomRight, c1).getTransitions());
/* 299 */     int l2 = Math.abs(transitionsBetween(topLeft, c2).getTransitions() - 
/* 300 */         transitionsBetween(bottomRight, c2).getTransitions());
/*     */     
/* 302 */     return (l1 <= l2) ? c1 : c2;
/*     */   }
/*     */   
/*     */   private boolean isValid(ResultPoint p) {
/* 306 */     return (p.getX() >= 0.0F && p.getX() < this.image.getWidth() && p.getY() > 0.0F && p.getY() < this.image.getHeight());
/*     */   }
/*     */   
/*     */   private static int distance(ResultPoint a, ResultPoint b) {
/* 310 */     return MathUtils.round(ResultPoint.distance(a, b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void increment(Map<ResultPoint, Integer> table, ResultPoint key) {
/* 317 */     Integer value = table.get(key);
/* 318 */     table.put(key, Integer.valueOf((value == null) ? 1 : (value.intValue() + 1)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix sampleGrid(BitMatrix image, ResultPoint topLeft, ResultPoint bottomLeft, ResultPoint bottomRight, ResultPoint topRight, int dimensionX, int dimensionY) throws NotFoundException {
/* 329 */     GridSampler sampler = GridSampler.getInstance();
/*     */     
/* 331 */     return sampler.sampleGrid(image, dimensionX, dimensionY, 0.5F, 0.5F, dimensionX - 0.5F, 0.5F, dimensionX - 0.5F, dimensionY - 0.5F, 0.5F, dimensionY - 0.5F, topLeft
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 342 */         .getX(), topLeft
/* 343 */         .getY(), topRight
/* 344 */         .getX(), topRight
/* 345 */         .getY(), bottomRight
/* 346 */         .getX(), bottomRight
/* 347 */         .getY(), bottomLeft
/* 348 */         .getX(), bottomLeft
/* 349 */         .getY());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResultPointsAndTransitions transitionsBetween(ResultPoint from, ResultPoint to) {
/* 357 */     int fromX = (int)from.getX();
/* 358 */     int fromY = (int)from.getY();
/* 359 */     int toX = (int)to.getX();
/* 360 */     int toY = (int)to.getY();
/* 361 */     boolean steep = (Math.abs(toY - fromY) > Math.abs(toX - fromX));
/* 362 */     if (steep) {
/* 363 */       int temp = fromX;
/* 364 */       fromX = fromY;
/* 365 */       fromY = temp;
/* 366 */       temp = toX;
/* 367 */       toX = toY;
/* 368 */       toY = temp;
/*     */     } 
/*     */     
/* 371 */     int dx = Math.abs(toX - fromX);
/* 372 */     int dy = Math.abs(toY - fromY);
/* 373 */     int error = -dx / 2;
/* 374 */     int ystep = (fromY < toY) ? 1 : -1;
/* 375 */     int xstep = (fromX < toX) ? 1 : -1;
/* 376 */     int transitions = 0;
/* 377 */     boolean inBlack = this.image.get(steep ? fromY : fromX, steep ? fromX : fromY); int x, y;
/* 378 */     for (x = fromX, y = fromY; x != toX; x += xstep) {
/* 379 */       boolean isBlack = this.image.get(steep ? y : x, steep ? x : y);
/* 380 */       if (isBlack != inBlack) {
/* 381 */         transitions++;
/* 382 */         inBlack = isBlack;
/*     */       } 
/* 384 */       error += dy;
/* 385 */       if (error > 0) {
/* 386 */         if (y == toY) {
/*     */           break;
/*     */         }
/* 389 */         y += ystep;
/* 390 */         error -= dx;
/*     */       } 
/*     */     } 
/* 393 */     return new ResultPointsAndTransitions(from, to, transitions);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ResultPointsAndTransitions
/*     */   {
/*     */     private final ResultPoint from;
/*     */     
/*     */     private final ResultPoint to;
/*     */     
/*     */     private final int transitions;
/*     */     
/*     */     private ResultPointsAndTransitions(ResultPoint from, ResultPoint to, int transitions) {
/* 406 */       this.from = from;
/* 407 */       this.to = to;
/* 408 */       this.transitions = transitions;
/*     */     }
/*     */     
/*     */     ResultPoint getFrom() {
/* 412 */       return this.from;
/*     */     }
/*     */     
/*     */     ResultPoint getTo() {
/* 416 */       return this.to;
/*     */     }
/*     */     
/*     */     public int getTransitions() {
/* 420 */       return this.transitions;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 425 */       return this.from + "/" + this.to + '/' + this.transitions;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class ResultPointsAndTransitionsComparator
/*     */     implements Comparator<ResultPointsAndTransitions>, Serializable
/*     */   {
/*     */     private ResultPointsAndTransitionsComparator() {}
/*     */     
/*     */     public int compare(Detector.ResultPointsAndTransitions o1, Detector.ResultPointsAndTransitions o2) {
/* 436 */       return o1.getTransitions() - o2.getTransitions();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\detector\Detector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */