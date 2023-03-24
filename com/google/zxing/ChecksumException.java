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
/*    */ public final class ChecksumException
/*    */   extends ReaderException
/*    */ {
/* 27 */   private static final ChecksumException INSTANCE = new ChecksumException();
/*    */   static {
/* 29 */     INSTANCE.setStackTrace(NO_TRACE);
/*    */   }
/*    */ 
/*    */   
/*    */   private ChecksumException() {}
/*    */ 
/*    */   
/*    */   private ChecksumException(Throwable cause) {
/* 37 */     super(cause);
/*    */   }
/*    */   
/*    */   public static ChecksumException getChecksumInstance() {
/* 41 */     return isStackTrace ? new ChecksumException() : INSTANCE;
/*    */   }
/*    */   
/*    */   public static ChecksumException getChecksumInstance(Throwable cause) {
/* 45 */     return isStackTrace ? new ChecksumException(cause) : INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\ChecksumException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */