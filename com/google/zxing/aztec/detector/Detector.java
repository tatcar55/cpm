/*     */ package com.google.zxing.aztec.detector;
/*     */ 
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.aztec.AztecDetectorResult;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.GridSampler;
/*     */ import com.google.zxing.common.detector.MathUtils;
/*     */ import com.google.zxing.common.detector.WhiteRectangleDetector;
/*     */ import com.google.zxing.common.reedsolomon.GenericGF;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private boolean compact;
/*     */   private int nbLayers;
/*     */   private int nbDataBlocks;
/*     */   private int nbCenterLayers;
/*     */   private int shift;
/*     */   
/*     */   public Detector(BitMatrix image) {
/*  48 */     this.image = image;
/*     */   }
/*     */   
/*     */   public AztecDetectorResult detect() throws NotFoundException {
/*  52 */     return detect(false);
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
/*     */   public AztecDetectorResult detect(boolean isMirror) throws NotFoundException {
/*  65 */     Point pCenter = getMatrixCenter();
/*     */ 
/*     */ 
/*     */     
/*  69 */     ResultPoint[] bullsEyeCorners = getBullsEyeCorners(pCenter);
/*     */     
/*  71 */     if (isMirror) {
/*  72 */       ResultPoint temp = bullsEyeCorners[0];
/*  73 */       bullsEyeCorners[0] = bullsEyeCorners[2];
/*  74 */       bullsEyeCorners[2] = temp;
/*     */     } 
/*     */ 
/*     */     
/*  78 */     extractParameters(bullsEyeCorners);
/*     */ 
/*     */     
/*  81 */     BitMatrix bits = sampleGrid(this.image, bullsEyeCorners[this.shift % 4], bullsEyeCorners[(this.shift + 1) % 4], bullsEyeCorners[(this.shift + 2) % 4], bullsEyeCorners[(this.shift + 3) % 4]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     ResultPoint[] corners = getMatrixCornerPoints(bullsEyeCorners);
/*     */     
/*  90 */     return new AztecDetectorResult(bits, corners, this.compact, this.nbDataBlocks, this.nbLayers);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void extractParameters(ResultPoint[] bullsEyeCorners) throws NotFoundException {
/* 100 */     if (!isValid(bullsEyeCorners[0]) || !isValid(bullsEyeCorners[1]) || 
/* 101 */       !isValid(bullsEyeCorners[2]) || !isValid(bullsEyeCorners[3])) {
/* 102 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 104 */     int length = 2 * this.nbCenterLayers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     int[] sides = { sampleLine(bullsEyeCorners[0], bullsEyeCorners[1], length), sampleLine(bullsEyeCorners[1], bullsEyeCorners[2], length), sampleLine(bullsEyeCorners[2], bullsEyeCorners[3], length), sampleLine(bullsEyeCorners[3], bullsEyeCorners[0], length) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     this.shift = getRotation(sides, length);
/*     */ 
/*     */     
/* 120 */     long parameterData = 0L;
/* 121 */     for (int i = 0; i < 4; i++) {
/* 122 */       int side = sides[(this.shift + i) % 4];
/* 123 */       if (this.compact) {
/*     */         
/* 125 */         parameterData <<= 7L;
/* 126 */         parameterData += (side >> 1 & 0x7F);
/*     */       } else {
/*     */         
/* 129 */         parameterData <<= 10L;
/* 130 */         parameterData += ((side >> 2 & 0x3E0) + (side >> 1 & 0x1F));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int correctedData = getCorrectedParameterData(parameterData, this.compact);
/*     */     
/* 138 */     if (this.compact) {
/*     */       
/* 140 */       this.nbLayers = (correctedData >> 6) + 1;
/* 141 */       this.nbDataBlocks = (correctedData & 0x3F) + 1;
/*     */     } else {
/*     */       
/* 144 */       this.nbLayers = (correctedData >> 11) + 1;
/* 145 */       this.nbDataBlocks = (correctedData & 0x7FF) + 1;
/*     */     } 
/*     */   }
/*     */   
/* 149 */   private static final int[] EXPECTED_CORNER_BITS = new int[] { 3808, 476, 2107, 1799 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getRotation(int[] sides, int length) throws NotFoundException {
/* 166 */     int cornerBits = 0;
/* 167 */     for (int side : sides) {
/*     */       
/* 169 */       int t = (side >> length - 2 << 1) + (side & 0x1);
/* 170 */       cornerBits = (cornerBits << 3) + t;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 175 */     cornerBits = ((cornerBits & 0x1) << 11) + (cornerBits >> 1);
/*     */ 
/*     */ 
/*     */     
/* 179 */     for (int shift = 0; shift < 4; shift++) {
/* 180 */       if (Integer.bitCount(cornerBits ^ EXPECTED_CORNER_BITS[shift]) <= 2) {
/* 181 */         return shift;
/*     */       }
/*     */     } 
/* 184 */     throw NotFoundException.getNotFoundInstance();
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
/*     */   private static int getCorrectedParameterData(long parameterData, boolean compact) throws NotFoundException {
/*     */     int numCodewords, numDataCodewords;
/* 198 */     if (compact) {
/* 199 */       numCodewords = 7;
/* 200 */       numDataCodewords = 2;
/*     */     } else {
/* 202 */       numCodewords = 10;
/* 203 */       numDataCodewords = 4;
/*     */     } 
/*     */     
/* 206 */     int numECCodewords = numCodewords - numDataCodewords;
/* 207 */     int[] parameterWords = new int[numCodewords];
/* 208 */     for (int i = numCodewords - 1; i >= 0; i--) {
/* 209 */       parameterWords[i] = (int)parameterData & 0xF;
/* 210 */       parameterData >>= 4L;
/*     */     } 
/*     */     try {
/* 213 */       ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.AZTEC_PARAM);
/* 214 */       rsDecoder.decode(parameterWords, numECCodewords);
/* 215 */     } catch (ReedSolomonException ignored) {
/* 216 */       throw NotFoundException.getNotFoundInstance();
/*     */     } 
/*     */     
/* 219 */     int result = 0;
/* 220 */     for (int j = 0; j < numDataCodewords; j++) {
/* 221 */       result = (result << 4) + parameterWords[j];
/*     */     }
/* 223 */     return result;
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
/*     */   private ResultPoint[] getBullsEyeCorners(Point pCenter) throws NotFoundException {
/* 237 */     Point pina = pCenter;
/* 238 */     Point pinb = pCenter;
/* 239 */     Point pinc = pCenter;
/* 240 */     Point pind = pCenter;
/*     */     
/* 242 */     boolean color = true;
/*     */     
/* 244 */     for (this.nbCenterLayers = 1; this.nbCenterLayers < 9; this.nbCenterLayers++) {
/* 245 */       Point pouta = getFirstDifferent(pina, color, 1, -1);
/* 246 */       Point poutb = getFirstDifferent(pinb, color, 1, 1);
/* 247 */       Point poutc = getFirstDifferent(pinc, color, -1, 1);
/* 248 */       Point poutd = getFirstDifferent(pind, color, -1, -1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       if (this.nbCenterLayers > 2) {
/* 255 */         float q = distance(poutd, pouta) * this.nbCenterLayers / distance(pind, pina) * (this.nbCenterLayers + 2);
/* 256 */         if (q < 0.75D || q > 1.25D || !isWhiteOrBlackRectangle(pouta, poutb, poutc, poutd)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 261 */       pina = pouta;
/* 262 */       pinb = poutb;
/* 263 */       pinc = poutc;
/* 264 */       pind = poutd;
/*     */       
/* 266 */       color = !color;
/*     */     } 
/*     */     
/* 269 */     if (this.nbCenterLayers != 5 && this.nbCenterLayers != 7) {
/* 270 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 273 */     this.compact = (this.nbCenterLayers == 5);
/*     */ 
/*     */ 
/*     */     
/* 277 */     ResultPoint pinax = new ResultPoint(pina.getX() + 0.5F, pina.getY() - 0.5F);
/* 278 */     ResultPoint pinbx = new ResultPoint(pinb.getX() + 0.5F, pinb.getY() + 0.5F);
/* 279 */     ResultPoint pincx = new ResultPoint(pinc.getX() - 0.5F, pinc.getY() + 0.5F);
/* 280 */     ResultPoint pindx = new ResultPoint(pind.getX() - 0.5F, pind.getY() - 0.5F);
/*     */ 
/*     */ 
/*     */     
/* 284 */     return expandSquare(new ResultPoint[] { pinax, pinbx, pincx, pindx }, (2 * this.nbCenterLayers - 3), (2 * this.nbCenterLayers));
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
/*     */   private Point getMatrixCenter() {
/*     */     ResultPoint pointA, pointB, pointC, pointD;
/*     */     try {
/* 304 */       ResultPoint[] cornerPoints = (new WhiteRectangleDetector(this.image)).detect();
/* 305 */       pointA = cornerPoints[0];
/* 306 */       pointB = cornerPoints[1];
/* 307 */       pointC = cornerPoints[2];
/* 308 */       pointD = cornerPoints[3];
/*     */     }
/* 310 */     catch (NotFoundException e) {
/*     */ 
/*     */ 
/*     */       
/* 314 */       int i = this.image.getWidth() / 2;
/* 315 */       int j = this.image.getHeight() / 2;
/* 316 */       pointA = getFirstDifferent(new Point(i + 7, j - 7), false, 1, -1).toResultPoint();
/* 317 */       pointB = getFirstDifferent(new Point(i + 7, j + 7), false, 1, 1).toResultPoint();
/* 318 */       pointC = getFirstDifferent(new Point(i - 7, j + 7), false, -1, 1).toResultPoint();
/* 319 */       pointD = getFirstDifferent(new Point(i - 7, j - 7), false, -1, -1).toResultPoint();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 324 */     int cx = MathUtils.round((pointA.getX() + pointD.getX() + pointB.getX() + pointC.getX()) / 4.0F);
/* 325 */     int cy = MathUtils.round((pointA.getY() + pointD.getY() + pointB.getY() + pointC.getY()) / 4.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 331 */       ResultPoint[] cornerPoints = (new WhiteRectangleDetector(this.image, 15, cx, cy)).detect();
/* 332 */       pointA = cornerPoints[0];
/* 333 */       pointB = cornerPoints[1];
/* 334 */       pointC = cornerPoints[2];
/* 335 */       pointD = cornerPoints[3];
/* 336 */     } catch (NotFoundException e) {
/*     */ 
/*     */       
/* 339 */       pointA = getFirstDifferent(new Point(cx + 7, cy - 7), false, 1, -1).toResultPoint();
/* 340 */       pointB = getFirstDifferent(new Point(cx + 7, cy + 7), false, 1, 1).toResultPoint();
/* 341 */       pointC = getFirstDifferent(new Point(cx - 7, cy + 7), false, -1, 1).toResultPoint();
/* 342 */       pointD = getFirstDifferent(new Point(cx - 7, cy - 7), false, -1, -1).toResultPoint();
/*     */     } 
/*     */ 
/*     */     
/* 346 */     cx = MathUtils.round((pointA.getX() + pointD.getX() + pointB.getX() + pointC.getX()) / 4.0F);
/* 347 */     cy = MathUtils.round((pointA.getY() + pointD.getY() + pointB.getY() + pointC.getY()) / 4.0F);
/*     */     
/* 349 */     return new Point(cx, cy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ResultPoint[] getMatrixCornerPoints(ResultPoint[] bullsEyeCorners) {
/* 359 */     return expandSquare(bullsEyeCorners, (2 * this.nbCenterLayers), getDimension());
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
/*     */   private BitMatrix sampleGrid(BitMatrix image, ResultPoint topLeft, ResultPoint topRight, ResultPoint bottomRight, ResultPoint bottomLeft) throws NotFoundException {
/* 373 */     GridSampler sampler = GridSampler.getInstance();
/* 374 */     int dimension = getDimension();
/*     */     
/* 376 */     float low = dimension / 2.0F - this.nbCenterLayers;
/* 377 */     float high = dimension / 2.0F + this.nbCenterLayers;
/*     */     
/* 379 */     return sampler.sampleGrid(image, dimension, dimension, low, low, high, low, high, high, low, high, topLeft
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 386 */         .getX(), topLeft.getY(), topRight
/* 387 */         .getX(), topRight.getY(), bottomRight
/* 388 */         .getX(), bottomRight.getY(), bottomLeft
/* 389 */         .getX(), bottomLeft.getY());
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
/*     */   private int sampleLine(ResultPoint p1, ResultPoint p2, int size) {
/* 401 */     int result = 0;
/*     */     
/* 403 */     float d = distance(p1, p2);
/* 404 */     float moduleSize = d / size;
/* 405 */     float px = p1.getX();
/* 406 */     float py = p1.getY();
/* 407 */     float dx = moduleSize * (p2.getX() - p1.getX()) / d;
/* 408 */     float dy = moduleSize * (p2.getY() - p1.getY()) / d;
/* 409 */     for (int i = 0; i < size; i++) {
/* 410 */       if (this.image.get(MathUtils.round(px + i * dx), MathUtils.round(py + i * dy))) {
/* 411 */         result |= 1 << size - i - 1;
/*     */       }
/*     */     } 
/* 414 */     return result;
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
/*     */   private boolean isWhiteOrBlackRectangle(Point p1, Point p2, Point p3, Point p4) {
/* 426 */     int corr = 3;
/*     */     
/* 428 */     p1 = new Point(p1.getX() - corr, p1.getY() + corr);
/* 429 */     p2 = new Point(p2.getX() - corr, p2.getY() - corr);
/* 430 */     p3 = new Point(p3.getX() + corr, p3.getY() - corr);
/* 431 */     p4 = new Point(p4.getX() + corr, p4.getY() + corr);
/*     */     
/* 433 */     int cInit = getColor(p4, p1);
/*     */     
/* 435 */     if (cInit == 0) {
/* 436 */       return false;
/*     */     }
/*     */     
/* 439 */     int c = getColor(p1, p2);
/*     */     
/* 441 */     if (c != cInit) {
/* 442 */       return false;
/*     */     }
/*     */     
/* 445 */     c = getColor(p2, p3);
/*     */     
/* 447 */     if (c != cInit) {
/* 448 */       return false;
/*     */     }
/*     */     
/* 451 */     c = getColor(p3, p4);
/*     */     
/* 453 */     return (c == cInit);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getColor(Point p1, Point p2) {
/* 463 */     float d = distance(p1, p2);
/* 464 */     float dx = (p2.getX() - p1.getX()) / d;
/* 465 */     float dy = (p2.getY() - p1.getY()) / d;
/* 466 */     int error = 0;
/*     */     
/* 468 */     float px = p1.getX();
/* 469 */     float py = p1.getY();
/*     */     
/* 471 */     boolean colorModel = this.image.get(p1.getX(), p1.getY());
/*     */     
/* 473 */     for (int i = 0; i < d; i++) {
/* 474 */       px += dx;
/* 475 */       py += dy;
/* 476 */       if (this.image.get(MathUtils.round(px), MathUtils.round(py)) != colorModel) {
/* 477 */         error++;
/*     */       }
/*     */     } 
/*     */     
/* 481 */     float errRatio = error / d;
/*     */     
/* 483 */     if (errRatio > 0.1F && errRatio < 0.9F) {
/* 484 */       return 0;
/*     */     }
/*     */     
/* 487 */     return (((errRatio <= 0.1F)) == colorModel) ? 1 : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Point getFirstDifferent(Point init, boolean color, int dx, int dy) {
/* 494 */     int x = init.getX() + dx;
/* 495 */     int y = init.getY() + dy;
/*     */     
/* 497 */     while (isValid(x, y) && this.image.get(x, y) == color) {
/* 498 */       x += dx;
/* 499 */       y += dy;
/*     */     } 
/*     */     
/* 502 */     x -= dx;
/* 503 */     y -= dy;
/*     */     
/* 505 */     while (isValid(x, y) && this.image.get(x, y) == color) {
/* 506 */       x += dx;
/*     */     }
/* 508 */     x -= dx;
/*     */     
/* 510 */     while (isValid(x, y) && this.image.get(x, y) == color) {
/* 511 */       y += dy;
/*     */     }
/* 513 */     y -= dy;
/*     */     
/* 515 */     return new Point(x, y);
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
/*     */   private static ResultPoint[] expandSquare(ResultPoint[] cornerPoints, float oldSide, float newSide) {
/* 527 */     float ratio = newSide / 2.0F * oldSide;
/* 528 */     float dx = cornerPoints[0].getX() - cornerPoints[2].getX();
/* 529 */     float dy = cornerPoints[0].getY() - cornerPoints[2].getY();
/* 530 */     float centerx = (cornerPoints[0].getX() + cornerPoints[2].getX()) / 2.0F;
/* 531 */     float centery = (cornerPoints[0].getY() + cornerPoints[2].getY()) / 2.0F;
/*     */     
/* 533 */     ResultPoint result0 = new ResultPoint(centerx + ratio * dx, centery + ratio * dy);
/* 534 */     ResultPoint result2 = new ResultPoint(centerx - ratio * dx, centery - ratio * dy);
/*     */     
/* 536 */     dx = cornerPoints[1].getX() - cornerPoints[3].getX();
/* 537 */     dy = cornerPoints[1].getY() - cornerPoints[3].getY();
/* 538 */     centerx = (cornerPoints[1].getX() + cornerPoints[3].getX()) / 2.0F;
/* 539 */     centery = (cornerPoints[1].getY() + cornerPoints[3].getY()) / 2.0F;
/* 540 */     ResultPoint result1 = new ResultPoint(centerx + ratio * dx, centery + ratio * dy);
/* 541 */     ResultPoint result3 = new ResultPoint(centerx - ratio * dx, centery - ratio * dy);
/*     */     
/* 543 */     return new ResultPoint[] { result0, result1, result2, result3 };
/*     */   }
/*     */   
/*     */   private boolean isValid(int x, int y) {
/* 547 */     return (x >= 0 && x < this.image.getWidth() && y > 0 && y < this.image.getHeight());
/*     */   }
/*     */   
/*     */   private boolean isValid(ResultPoint point) {
/* 551 */     int x = MathUtils.round(point.getX());
/* 552 */     int y = MathUtils.round(point.getY());
/* 553 */     return isValid(x, y);
/*     */   }
/*     */   
/*     */   private static float distance(Point a, Point b) {
/* 557 */     return MathUtils.distance(a.getX(), a.getY(), b.getX(), b.getY());
/*     */   }
/*     */   
/*     */   private static float distance(ResultPoint a, ResultPoint b) {
/* 561 */     return MathUtils.distance(a.getX(), a.getY(), b.getX(), b.getY());
/*     */   }
/*     */   
/*     */   private int getDimension() {
/* 565 */     if (this.compact) {
/* 566 */       return 4 * this.nbLayers + 11;
/*     */     }
/* 568 */     if (this.nbLayers <= 4) {
/* 569 */       return 4 * this.nbLayers + 15;
/*     */     }
/* 571 */     return 4 * this.nbLayers + 2 * ((this.nbLayers - 4) / 8 + 1) + 15;
/*     */   }
/*     */   
/*     */   static final class Point {
/*     */     private final int x;
/*     */     private final int y;
/*     */     
/*     */     ResultPoint toResultPoint() {
/* 579 */       return new ResultPoint(getX(), getY());
/*     */     }
/*     */     
/*     */     Point(int x, int y) {
/* 583 */       this.x = x;
/* 584 */       this.y = y;
/*     */     }
/*     */     
/*     */     int getX() {
/* 588 */       return this.x;
/*     */     }
/*     */     
/*     */     int getY() {
/* 592 */       return this.y;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 597 */       return "<" + this.x + ' ' + this.y + '>';
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\detector\Detector.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */