/*    */ package com.google.zxing.common.detector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MathUtils
/*    */ {
/*    */   public static int round(float d) {
/* 34 */     return (int)(d + ((d < 0.0F) ? -0.5F : 0.5F));
/*    */   }
/*    */   
/*    */   public static float distance(float aX, float aY, float bX, float bY) {
/* 38 */     float xDiff = aX - bX;
/* 39 */     float yDiff = aY - bY;
/* 40 */     return (float)Math.sqrt((xDiff * xDiff + yDiff * yDiff));
/*    */   }
/*    */   
/*    */   public static float distance(int aX, int aY, int bX, int bY) {
/* 44 */     int xDiff = aX - bX;
/* 45 */     int yDiff = aY - bY;
/* 46 */     return (float)Math.sqrt((xDiff * xDiff + yDiff * yDiff));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\detector\MathUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */