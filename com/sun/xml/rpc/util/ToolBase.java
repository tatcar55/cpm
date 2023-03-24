/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
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
/*     */ public abstract class ToolBase
/*     */ {
/*     */   protected OutputStream out;
/*     */   protected String program;
/*     */   protected Localizer localizer;
/*     */   protected LocalizableMessageFactory messageFactory;
/*     */   protected static final String TRUE = "true";
/*     */   protected static final String FALSE = "false";
/*     */   
/*     */   public ToolBase(OutputStream out, String program) {
/*  44 */     this.out = out;
/*  45 */     this.program = program;
/*  46 */     initialize();
/*     */   }
/*     */   
/*     */   protected void initialize() {
/*  50 */     this.messageFactory = new LocalizableMessageFactory(getResourceBundleName());
/*  51 */     this.localizer = new Localizer();
/*     */   }
/*     */   
/*     */   public boolean run(String[] args) {
/*  55 */     if (!parseArguments(args)) {
/*  56 */       return false;
/*     */     }
/*     */     
/*     */     try {
/*  60 */       run();
/*  61 */       return wasSuccessful();
/*  62 */     } catch (Exception e) {
/*  63 */       if (e instanceof Localizable) {
/*  64 */         report((Localizable)e);
/*     */       } else {
/*  66 */         report(getMessage(getGenericErrorMessage(), e.toString()));
/*     */       } 
/*  68 */       printStackTrace(e);
/*  69 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean wasSuccessful() {
/*  74 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean parseArguments(String[] paramArrayOfString);
/*     */   
/*     */   protected abstract void run() throws Exception;
/*     */   
/*     */   public void printStackTrace(Throwable t) {
/*  83 */     PrintStream outstream = (this.out instanceof PrintStream) ? (PrintStream)this.out : new PrintStream(this.out, true);
/*     */ 
/*     */ 
/*     */     
/*  87 */     t.printStackTrace(outstream);
/*  88 */     outstream.flush();
/*     */   } protected abstract String getGenericErrorMessage();
/*     */   protected abstract String getResourceBundleName();
/*     */   protected void report(String msg) {
/*  92 */     PrintStream outstream = (this.out instanceof PrintStream) ? (PrintStream)this.out : new PrintStream(this.out, true);
/*     */ 
/*     */ 
/*     */     
/*  96 */     outstream.println(msg);
/*  97 */     outstream.flush();
/*     */   }
/*     */   
/*     */   protected void report(Localizable msg) {
/* 101 */     report(this.localizer.localize(msg));
/*     */   }
/*     */   
/*     */   public Localizable getMessage(String key) {
/* 105 */     return getMessage(key, (Object[])null);
/*     */   }
/*     */   
/*     */   public Localizable getMessage(String key, String arg) {
/* 109 */     return this.messageFactory.getMessage(key, new Object[] { arg });
/*     */   }
/*     */   
/*     */   public Localizable getMessage(String key, String arg1, String arg2) {
/* 113 */     return this.messageFactory.getMessage(key, new Object[] { arg1, arg2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Localizable getMessage(String key, String arg1, String arg2, String arg3) {
/* 121 */     return this.messageFactory.getMessage(key, new Object[] { arg1, arg2, arg3 });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Localizable getMessage(String key, Localizable localizable) {
/* 127 */     return this.messageFactory.getMessage(key, new Object[] { localizable });
/*     */   }
/*     */   
/*     */   public Localizable getMessage(String key, Object[] args) {
/* 131 */     return this.messageFactory.getMessage(key, args);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\ToolBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */