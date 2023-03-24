/*     */ package com.sun.xml.fastinfoset;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
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
/*     */ public abstract class AbstractResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   public static final String LOCALE = "com.sun.xml.fastinfoset.locale";
/*     */   
/*     */   public String getString(String key, Object[] args) {
/*  51 */     String pattern = getBundle().getString(key);
/*  52 */     return MessageFormat.format(pattern, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale parseLocale(String localeString) {
/*  63 */     Locale locale = null;
/*  64 */     if (localeString == null) {
/*  65 */       locale = Locale.getDefault();
/*     */     } else {
/*     */       try {
/*  68 */         String[] args = localeString.split("_");
/*  69 */         if (args.length == 1) {
/*  70 */           locale = new Locale(args[0]);
/*  71 */         } else if (args.length == 2) {
/*  72 */           locale = new Locale(args[0], args[1]);
/*  73 */         } else if (args.length == 3) {
/*  74 */           locale = new Locale(args[0], args[1], args[2]);
/*     */         } 
/*  76 */       } catch (Throwable t) {
/*  77 */         locale = Locale.getDefault();
/*     */       } 
/*     */     } 
/*  80 */     return locale;
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
/*     */   public abstract ResourceBundle getBundle();
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
/*     */   protected Object handleGetObject(String key) {
/* 107 */     return getBundle().getObject(key);
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
/*     */   public final Enumeration getKeys() {
/* 120 */     return getBundle().getKeys();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\AbstractResourceBundle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */