/*     */ package com.sun.xml.rpc.util.exception;
/*     */ 
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableSupport;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.xml.rpc.JAXRPCException;
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
/*     */ public abstract class JAXRPCExceptionBase
/*     */   extends JAXRPCException
/*     */   implements Localizable
/*     */ {
/*     */   protected LocalizableSupport localizablePart;
/*  50 */   protected NestableExceptionSupport nestablePart = new NestableExceptionSupport();
/*     */ 
/*     */   
/*     */   public JAXRPCExceptionBase(String key) {
/*  54 */     this();
/*  55 */     this.localizablePart = new LocalizableSupport(key);
/*     */   }
/*     */   public JAXRPCExceptionBase() {}
/*     */   public JAXRPCExceptionBase(String key, String arg) {
/*  59 */     this();
/*  60 */     this.localizablePart = new LocalizableSupport(key, arg);
/*     */   }
/*     */   
/*     */   public JAXRPCExceptionBase(String key, Localizable localizable) {
/*  64 */     this(key, new Object[] { localizable });
/*     */   }
/*     */   
/*     */   protected JAXRPCExceptionBase(String key, Object[] args) {
/*  68 */     this();
/*  69 */     this.localizablePart = new LocalizableSupport(key, args);
/*  70 */     if (args != null && this.nestablePart.getCause() == null) {
/*  71 */       for (int i = 0; i < args.length; i++) {
/*  72 */         if (args[i] instanceof Throwable) {
/*  73 */           this.nestablePart.setCause((Throwable)args[i]);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  81 */     return this.localizablePart.getKey();
/*     */   }
/*     */   
/*     */   public Object[] getArguments() {
/*  85 */     return this.localizablePart.getArguments();
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getResourceBundleName();
/*     */ 
/*     */   
/*     */   public String toString() {
/*  93 */     return getMessage();
/*     */   }
/*     */   
/*     */   public String getMessage() {
/*  97 */     Localizer localizer = new Localizer();
/*  98 */     return localizer.localize(this);
/*     */   }
/*     */   
/*     */   public Throwable getLinkedException() {
/* 102 */     return this.nestablePart.getCause();
/*     */   }
/*     */   
/*     */   public void printStackTrace() {
/* 106 */     super.printStackTrace();
/* 107 */     this.nestablePart.printStackTrace();
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 111 */     super.printStackTrace(s);
/* 112 */     this.nestablePart.printStackTrace(s);
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintWriter s) {
/* 116 */     super.printStackTrace(s);
/* 117 */     this.nestablePart.printStackTrace(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\exception\JAXRPCExceptionBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */