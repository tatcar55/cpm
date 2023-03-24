/*    */ package com.google.zxing.qrcode.detector;
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
/*    */ public final class FinderPatternInfo
/*    */ {
/*    */   private final FinderPattern bottomLeft;
/*    */   private final FinderPattern topLeft;
/*    */   private final FinderPattern topRight;
/*    */   
/*    */   public FinderPatternInfo(FinderPattern[] patternCenters) {
/* 32 */     this.bottomLeft = patternCenters[0];
/* 33 */     this.topLeft = patternCenters[1];
/* 34 */     this.topRight = patternCenters[2];
/*    */   }
/*    */   
/*    */   public FinderPattern getBottomLeft() {
/* 38 */     return this.bottomLeft;
/*    */   }
/*    */   
/*    */   public FinderPattern getTopLeft() {
/* 42 */     return this.topLeft;
/*    */   }
/*    */   
/*    */   public FinderPattern getTopRight() {
/* 46 */     return this.topRight;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\detector\FinderPatternInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */