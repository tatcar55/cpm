/*    */ package com.sun.xml.rpc.util.exception;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
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
/*    */ public class NestableExceptionSupport
/*    */ {
/* 39 */   protected Throwable cause = null;
/*    */ 
/*    */   
/*    */   public NestableExceptionSupport() {}
/*    */   
/*    */   public NestableExceptionSupport(Throwable cause) {
/* 45 */     this.cause = cause;
/*    */   }
/*    */ 
/*    */   
/*    */   public void printStackTrace() {
/* 50 */     if (this.cause != null) {
/* 51 */       System.err.println("\nCAUSE:\n");
/* 52 */       this.cause.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void printStackTrace(PrintStream s) {
/* 58 */     if (this.cause != null) {
/* 59 */       s.println("\nCAUSE:\n");
/* 60 */       this.cause.printStackTrace(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void printStackTrace(PrintWriter s) {
/* 66 */     if (this.cause != null) {
/* 67 */       s.println("\nCAUSE:\n");
/* 68 */       this.cause.printStackTrace(s);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Throwable getCause() {
/* 73 */     return this.cause;
/*    */   }
/*    */   
/*    */   public void setCause(Throwable cause) {
/* 77 */     this.cause = cause;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\exception\NestableExceptionSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */