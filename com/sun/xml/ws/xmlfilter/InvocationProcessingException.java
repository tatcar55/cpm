/*    */ package com.sun.xml.ws.xmlfilter;
/*    */ 
/*    */ import com.sun.xml.ws.xmlfilter.localization.LocalizationMessages;
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
/*    */ public class InvocationProcessingException
/*    */   extends RuntimeException
/*    */ {
/*    */   public InvocationProcessingException(String message) {
/* 52 */     super(message);
/*    */   }
/*    */   
/*    */   public InvocationProcessingException(String message, Throwable cause) {
/* 56 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InvocationProcessingException(Throwable cause) {
/* 60 */     super(cause.getMessage(), cause);
/*    */   }
/*    */   
/*    */   public InvocationProcessingException(Invocation invocation) {
/* 64 */     super(assemblyExceptionMessage(invocation));
/*    */   }
/*    */   
/*    */   public InvocationProcessingException(Invocation invocation, Throwable cause) {
/* 68 */     super(assemblyExceptionMessage(invocation), cause);
/*    */   }
/*    */   
/*    */   private static String assemblyExceptionMessage(Invocation invocation) {
/* 72 */     return LocalizationMessages.XMLF_5005_INVOCATION_ERROR(invocation.getMethodName(), invocation.argsToString());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\InvocationProcessingException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */