/*    */ package com.google.zxing;
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
/*    */ public final class FormatException
/*    */   extends ReaderException
/*    */ {
/* 28 */   private static final FormatException INSTANCE = new FormatException();
/*    */   static {
/* 30 */     INSTANCE.setStackTrace(NO_TRACE);
/*    */   }
/*    */ 
/*    */   
/*    */   private FormatException() {}
/*    */   
/*    */   private FormatException(Throwable cause) {
/* 37 */     super(cause);
/*    */   }
/*    */   
/*    */   public static FormatException getFormatInstance() {
/* 41 */     return isStackTrace ? new FormatException() : INSTANCE;
/*    */   }
/*    */   
/*    */   public static FormatException getFormatInstance(Throwable cause) {
/* 45 */     return isStackTrace ? new FormatException(cause) : INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\FormatException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */