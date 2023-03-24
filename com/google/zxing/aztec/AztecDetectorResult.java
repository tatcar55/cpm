/*    */ package com.google.zxing.aztec;
/*    */ 
/*    */ import com.google.zxing.ResultPoint;
/*    */ import com.google.zxing.common.BitMatrix;
/*    */ import com.google.zxing.common.DetectorResult;
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
/*    */ public final class AztecDetectorResult
/*    */   extends DetectorResult
/*    */ {
/*    */   private final boolean compact;
/*    */   private final int nbDatablocks;
/*    */   private final int nbLayers;
/*    */   
/*    */   public AztecDetectorResult(BitMatrix bits, ResultPoint[] points, boolean compact, int nbDatablocks, int nbLayers) {
/* 34 */     super(bits, points);
/* 35 */     this.compact = compact;
/* 36 */     this.nbDatablocks = nbDatablocks;
/* 37 */     this.nbLayers = nbLayers;
/*    */   }
/*    */   
/*    */   public int getNbLayers() {
/* 41 */     return this.nbLayers;
/*    */   }
/*    */   
/*    */   public int getNbDatablocks() {
/* 45 */     return this.nbDatablocks;
/*    */   }
/*    */   
/*    */   public boolean isCompact() {
/* 49 */     return this.compact;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\AztecDetectorResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */