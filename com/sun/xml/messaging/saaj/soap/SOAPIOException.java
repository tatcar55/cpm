/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
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
/*     */ public class SOAPIOException
/*     */   extends IOException
/*     */ {
/*     */   SOAPExceptionImpl soapException;
/*     */   
/*     */   public SOAPIOException() {
/*  60 */     this.soapException = new SOAPExceptionImpl();
/*  61 */     this.soapException.fillInStackTrace();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPIOException(String s) {
/*  66 */     this.soapException = new SOAPExceptionImpl(s);
/*  67 */     this.soapException.fillInStackTrace();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPIOException(String reason, Throwable cause) {
/*  72 */     this.soapException = new SOAPExceptionImpl(reason, cause);
/*  73 */     this.soapException.fillInStackTrace();
/*     */   }
/*     */   
/*     */   public SOAPIOException(Throwable cause) {
/*  77 */     super(cause.toString());
/*  78 */     this.soapException = new SOAPExceptionImpl(cause);
/*  79 */     this.soapException.fillInStackTrace();
/*     */   }
/*     */   
/*     */   public Throwable fillInStackTrace() {
/*  83 */     if (this.soapException != null) {
/*  84 */       this.soapException.fillInStackTrace();
/*     */     }
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public String getLocalizedMessage() {
/*  90 */     return this.soapException.getLocalizedMessage();
/*     */   }
/*     */   
/*     */   public String getMessage() {
/*  94 */     return this.soapException.getMessage();
/*     */   }
/*     */   
/*     */   public void printStackTrace() {
/*  98 */     this.soapException.printStackTrace();
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 102 */     this.soapException.printStackTrace(s);
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintWriter s) {
/* 106 */     this.soapException.printStackTrace(s);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 110 */     return this.soapException.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\SOAPIOException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */