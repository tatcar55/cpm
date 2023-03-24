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
/*    */ 
/*    */ 
/*    */ public abstract class ReaderException
/*    */   extends Exception
/*    */ {
/* 30 */   protected static final boolean isStackTrace = (System.getProperty("surefire.test.class.path") != null);
/* 31 */   protected static final StackTraceElement[] NO_TRACE = new StackTraceElement[0];
/*    */ 
/*    */   
/*    */   ReaderException() {}
/*    */ 
/*    */   
/*    */   ReaderException(Throwable cause) {
/* 38 */     super(cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Throwable fillInStackTrace() {
/* 46 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\ReaderException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */