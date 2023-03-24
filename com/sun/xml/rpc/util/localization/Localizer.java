/*     */ package com.sun.xml.rpc.util.localization;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
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
/*     */ public class Localizer
/*     */ {
/*     */   protected Locale _locale;
/*     */   protected HashMap _resourceBundles;
/*     */   
/*     */   public Localizer() {
/*  46 */     this(Locale.getDefault());
/*     */   }
/*     */   
/*     */   public Localizer(Locale l) {
/*  50 */     this._locale = l;
/*  51 */     this._resourceBundles = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  55 */     return this._locale;
/*     */   }
/*     */   
/*     */   public String localize(Localizable l) {
/*  59 */     String bundlename = l.getResourceBundleName();
/*     */     
/*     */     try {
/*  62 */       ResourceBundle bundle = (ResourceBundle)this._resourceBundles.get(bundlename);
/*     */ 
/*     */       
/*  65 */       if (bundle == null) {
/*     */         try {
/*  67 */           bundle = ResourceBundle.getBundle(bundlename, this._locale);
/*  68 */         } catch (MissingResourceException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  76 */           int i = bundlename.lastIndexOf('.');
/*  77 */           if (i != -1) {
/*  78 */             String alternateBundleName = bundlename.substring(i + 1);
/*     */             
/*     */             try {
/*  81 */               bundle = ResourceBundle.getBundle(alternateBundleName, this._locale);
/*     */ 
/*     */             
/*     */             }
/*  85 */             catch (MissingResourceException e2) {
/*     */               
/*  87 */               return getDefaultMessage(l);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/*  92 */         this._resourceBundles.put(bundlename, bundle);
/*     */       } 
/*     */       
/*  95 */       if (bundle == null) {
/*  96 */         return getDefaultMessage(l);
/*     */       }
/*     */       
/*  99 */       String key = l.getKey();
/* 100 */       if (key == null) {
/* 101 */         key = "undefined";
/*     */       }
/* 103 */       String msg = null;
/*     */       try {
/* 105 */         msg = bundle.getString(key);
/* 106 */       } catch (MissingResourceException e) {
/*     */         
/* 108 */         msg = bundle.getString("undefined");
/*     */       } 
/*     */ 
/*     */       
/* 112 */       Object[] args = l.getArguments();
/* 113 */       if (args != null) {
/* 114 */         for (int i = 0; i < args.length; i++) {
/* 115 */           if (args[i] instanceof Localizable) {
/* 116 */             args[i] = localize((Localizable)args[i]);
/*     */           }
/*     */         } 
/*     */       }
/* 120 */       String message = MessageFormat.format(msg, args);
/* 121 */       return message;
/*     */     }
/* 123 */     catch (MissingResourceException e) {
/* 124 */       return getDefaultMessage(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultMessage(Localizable l) {
/* 130 */     String key = l.getKey();
/* 131 */     Object[] args = l.getArguments();
/* 132 */     StringBuffer sb = new StringBuffer();
/* 133 */     if (!(l instanceof com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter))
/*     */     {
/* 135 */       sb.append("[failed to localize] ");
/*     */     }
/* 137 */     sb.append(String.valueOf(key));
/* 138 */     if (args != null) {
/* 139 */       sb.append('(');
/* 140 */       for (int i = 0; i < args.length; i++) {
/* 141 */         if (i != 0)
/* 142 */           sb.append(", "); 
/* 143 */         sb.append(String.valueOf(args[i]));
/*     */       } 
/* 145 */       sb.append(')');
/*     */     } 
/* 147 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\Localizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */