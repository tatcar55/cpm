/*     */ package com.sun.xml.messaging.saaj;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.xml.soap.SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPExceptionImpl
/*     */   extends SOAPException
/*     */ {
/*     */   private Throwable cause;
/*     */   
/*     */   public SOAPExceptionImpl() {
/*  72 */     this.cause = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPExceptionImpl(String reason) {
/*  82 */     super(reason);
/*  83 */     this.cause = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPExceptionImpl(String reason, Throwable cause) {
/*  97 */     super(reason);
/*  98 */     initCause(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPExceptionImpl(Throwable cause) {
/* 106 */     super(cause.toString());
/* 107 */     initCause(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 125 */     String message = super.getMessage();
/* 126 */     if (message == null && this.cause != null) {
/* 127 */       return this.cause.getMessage();
/*     */     }
/* 129 */     return message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCause() {
/* 143 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Throwable initCause(Throwable cause) {
/* 177 */     if (this.cause != null) {
/* 178 */       throw new IllegalStateException("Can't override cause");
/*     */     }
/* 180 */     if (cause == this) {
/* 181 */       throw new IllegalArgumentException("Self-causation not permitted");
/*     */     }
/* 183 */     this.cause = cause;
/*     */     
/* 185 */     return this;
/*     */   }
/*     */   
/*     */   public void printStackTrace() {
/* 189 */     super.printStackTrace();
/* 190 */     if (this.cause != null) {
/* 191 */       System.err.println("\nCAUSE:\n");
/* 192 */       this.cause.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 197 */     super.printStackTrace(s);
/* 198 */     if (this.cause != null) {
/* 199 */       s.println("\nCAUSE:\n");
/* 200 */       this.cause.printStackTrace(s);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintWriter s) {
/* 205 */     super.printStackTrace(s);
/* 206 */     if (this.cause != null) {
/* 207 */       s.println("\nCAUSE:\n");
/* 208 */       this.cause.printStackTrace(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\SOAPExceptionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */