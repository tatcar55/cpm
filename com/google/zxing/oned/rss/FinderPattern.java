/*    */ package com.google.zxing.oned.rss;
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
/*    */ public final class FinderPattern
/*    */ {
/*    */   private final int value;
/*    */   private final int[] startEnd;
/*    */   private final ResultPoint[] resultPoints;
/*    */   
/*    */   public FinderPattern(int value, int[] startEnd, int start, int end, int rowNumber) {
/* 28 */     this.value = value;
/* 29 */     this.startEnd = startEnd;
/* 30 */     this.resultPoints = new ResultPoint[] { new ResultPoint(start, rowNumber), new ResultPoint(end, rowNumber) };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 37 */     return this.value;
/*    */   }
/*    */   
/*    */   public int[] getStartEnd() {
/* 41 */     return this.startEnd;
/*    */   }
/*    */   
/*    */   public ResultPoint[] getResultPoints() {
/* 45 */     return this.resultPoints;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     if (!(o instanceof FinderPattern)) {
/* 51 */       return false;
/*    */     }
/* 53 */     FinderPattern that = (FinderPattern)o;
/* 54 */     return (this.value == that.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 59 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\FinderPattern.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */