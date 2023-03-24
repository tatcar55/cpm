/*     */ package com.google.zxing;
/*     */ 
/*     */ import com.google.zxing.common.detector.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResultPoint
/*     */ {
/*     */   private final float x;
/*     */   private final float y;
/*     */   
/*     */   public ResultPoint(float x, float y) {
/*  33 */     this.x = x;
/*  34 */     this.y = y;
/*     */   }
/*     */   
/*     */   public final float getX() {
/*  38 */     return this.x;
/*     */   }
/*     */   
/*     */   public final float getY() {
/*  42 */     return this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object other) {
/*  47 */     if (other instanceof ResultPoint) {
/*  48 */       ResultPoint otherPoint = (ResultPoint)other;
/*  49 */       return (this.x == otherPoint.x && this.y == otherPoint.y);
/*     */     } 
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/*  56 */     return 31 * Float.floatToIntBits(this.x) + Float.floatToIntBits(this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/*  61 */     StringBuilder result = new StringBuilder(25);
/*  62 */     result.append('(');
/*  63 */     result.append(this.x);
/*  64 */     result.append(',');
/*  65 */     result.append(this.y);
/*  66 */     result.append(')');
/*  67 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void orderBestPatterns(ResultPoint[] patterns) {
/*     */     ResultPoint pointA, pointB, pointC;
/*  79 */     float zeroOneDistance = distance(patterns[0], patterns[1]);
/*  80 */     float oneTwoDistance = distance(patterns[1], patterns[2]);
/*  81 */     float zeroTwoDistance = distance(patterns[0], patterns[2]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (oneTwoDistance >= zeroOneDistance && oneTwoDistance >= zeroTwoDistance) {
/*  88 */       pointB = patterns[0];
/*  89 */       pointA = patterns[1];
/*  90 */       pointC = patterns[2];
/*  91 */     } else if (zeroTwoDistance >= oneTwoDistance && zeroTwoDistance >= zeroOneDistance) {
/*  92 */       pointB = patterns[1];
/*  93 */       pointA = patterns[0];
/*  94 */       pointC = patterns[2];
/*     */     } else {
/*  96 */       pointB = patterns[2];
/*  97 */       pointA = patterns[0];
/*  98 */       pointC = patterns[1];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 105 */     if (crossProductZ(pointA, pointB, pointC) < 0.0F) {
/* 106 */       ResultPoint temp = pointA;
/* 107 */       pointA = pointC;
/* 108 */       pointC = temp;
/*     */     } 
/*     */     
/* 111 */     patterns[0] = pointA;
/* 112 */     patterns[1] = pointB;
/* 113 */     patterns[2] = pointC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float distance(ResultPoint pattern1, ResultPoint pattern2) {
/* 123 */     return MathUtils.distance(pattern1.x, pattern1.y, pattern2.x, pattern2.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float crossProductZ(ResultPoint pointA, ResultPoint pointB, ResultPoint pointC) {
/* 132 */     float bX = pointB.x;
/* 133 */     float bY = pointB.y;
/* 134 */     return (pointC.x - bX) * (pointA.y - bY) - (pointC.y - bY) * (pointA.x - bX);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\ResultPoint.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */