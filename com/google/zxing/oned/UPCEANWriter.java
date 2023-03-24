/*    */ package com.google.zxing.oned;
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
/*    */ public abstract class UPCEANWriter
/*    */   extends OneDimensionalCodeWriter
/*    */ {
/*    */   public int getDefaultMargin() {
/* 31 */     return UPCEANReader.START_END_PATTERN.length;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\UPCEANWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */