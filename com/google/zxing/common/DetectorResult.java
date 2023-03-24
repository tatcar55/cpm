/*    */ package com.google.zxing.common;
/*    */ 
/*    */ import com.google.zxing.ResultPoint;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DetectorResult
/*    */ {
/*    */   private final BitMatrix bits;
/*    */   private final ResultPoint[] points;
/*    */   
/*    */   public DetectorResult(BitMatrix bits, ResultPoint[] points) {
/* 34 */     this.bits = bits;
/* 35 */     this.points = points;
/*    */   }
/*    */   
/*    */   public final BitMatrix getBits() {
/* 39 */     return this.bits;
/*    */   }
/*    */   
/*    */   public final ResultPoint[] getPoints() {
/* 43 */     return this.points;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\DetectorResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */