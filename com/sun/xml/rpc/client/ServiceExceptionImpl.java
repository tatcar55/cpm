/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.NestableExceptionSupport;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableSupport;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.xml.rpc.ServiceException;
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
/*     */ public class ServiceExceptionImpl
/*     */   extends ServiceException
/*     */   implements Localizable
/*     */ {
/*     */   protected LocalizableSupport localizablePart;
/*  49 */   protected NestableExceptionSupport nestablePart = new NestableExceptionSupport();
/*     */ 
/*     */   
/*     */   public ServiceExceptionImpl(String key) {
/*  53 */     this();
/*  54 */     this.localizablePart = new LocalizableSupport(key);
/*     */   }
/*     */   public ServiceExceptionImpl() {}
/*     */   public ServiceExceptionImpl(String key, String arg) {
/*  58 */     this();
/*  59 */     this.localizablePart = new LocalizableSupport(key, arg);
/*     */   }
/*     */   
/*     */   public ServiceExceptionImpl(String key, Localizable localizable) {
/*  63 */     this(key, new Object[] { localizable });
/*     */   }
/*     */   
/*     */   public ServiceExceptionImpl(String key, Object[] args) {
/*  67 */     this();
/*  68 */     this.localizablePart = new LocalizableSupport(key, args);
/*  69 */     if (args != null && this.nestablePart.getCause() == null) {
/*  70 */       for (int i = 0; i < args.length; i++) {
/*  71 */         if (args[i] instanceof Throwable) {
/*  72 */           this.nestablePart.setCause((Throwable)args[i]);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public ServiceExceptionImpl(Localizable arg) {
/*  80 */     this("service.exception.nested", arg);
/*     */   }
/*     */   
/*     */   public String getResourceBundleName() {
/*  84 */     return "com.sun.xml.rpc.resources.dii";
/*     */   }
/*     */   
/*     */   public String getKey() {
/*  88 */     return this.localizablePart.getKey();
/*     */   }
/*     */   
/*     */   public Object[] getArguments() {
/*  92 */     return this.localizablePart.getArguments();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  98 */     return getMessage();
/*     */   }
/*     */   
/*     */   public String getMessage() {
/* 102 */     Localizer localizer = new Localizer();
/* 103 */     return localizer.localize(this);
/*     */   }
/*     */   
/*     */   public Throwable getLinkedException() {
/* 107 */     return this.nestablePart.getCause();
/*     */   }
/*     */   
/*     */   public void printStackTrace() {
/* 111 */     super.printStackTrace();
/* 112 */     this.nestablePart.printStackTrace();
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintStream s) {
/* 116 */     super.printStackTrace(s);
/* 117 */     this.nestablePart.printStackTrace(s);
/*     */   }
/*     */   
/*     */   public void printStackTrace(PrintWriter s) {
/* 121 */     super.printStackTrace(s);
/* 122 */     this.nestablePart.printStackTrace(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\ServiceExceptionImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */