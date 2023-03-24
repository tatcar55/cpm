/*     */ package com.sun.istack.localization;
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
/*     */   private final Locale _locale;
/*     */   private final HashMap _resourceBundles;
/*     */   
/*     */   public Localizer() {
/*  61 */     this(Locale.getDefault());
/*     */   }
/*     */   
/*     */   public Localizer(Locale l) {
/*  65 */     this._locale = l;
/*  66 */     this._resourceBundles = new HashMap<Object, Object>();
/*     */   }
/*     */   
/*     */   public Locale getLocale() {
/*  70 */     return this._locale;
/*     */   }
/*     */   
/*     */   public String localize(Localizable l) {
/*  74 */     String key = l.getKey();
/*  75 */     if (key == "\000")
/*     */     {
/*  77 */       return (String)l.getArguments()[0];
/*     */     }
/*  79 */     String bundlename = l.getResourceBundleName();
/*     */     try {
/*     */       String str1;
/*  82 */       ResourceBundle bundle = (ResourceBundle)this._resourceBundles.get(bundlename);
/*     */ 
/*     */       
/*  85 */       if (bundle == null) {
/*     */         try {
/*  87 */           bundle = ResourceBundle.getBundle(bundlename, this._locale);
/*  88 */         } catch (MissingResourceException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  96 */           int j = bundlename.lastIndexOf('.');
/*  97 */           if (j != -1) {
/*  98 */             String alternateBundleName = bundlename.substring(j + 1);
/*     */             
/*     */             try {
/* 101 */               bundle = ResourceBundle.getBundle(alternateBundleName, this._locale);
/*     */ 
/*     */             
/*     */             }
/* 105 */             catch (MissingResourceException e2) {
/*     */               
/*     */               try {
/* 108 */                 bundle = ResourceBundle.getBundle(bundlename, this._locale, Thread.currentThread().getContextClassLoader());
/* 109 */               } catch (MissingResourceException e3) {
/*     */                 
/* 111 */                 return getDefaultMessage(l);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 118 */         this._resourceBundles.put(bundlename, bundle);
/*     */       } 
/*     */       
/* 121 */       if (bundle == null) {
/* 122 */         return getDefaultMessage(l);
/*     */       }
/*     */       
/* 125 */       if (key == null) {
/* 126 */         key = "undefined";
/*     */       }
/*     */       
/*     */       try {
/* 130 */         str1 = bundle.getString(key);
/* 131 */       } catch (MissingResourceException e) {
/*     */         
/* 133 */         str1 = bundle.getString("undefined");
/*     */       } 
/*     */ 
/*     */       
/* 137 */       Object[] args = l.getArguments();
/* 138 */       for (int i = 0; i < args.length; i++) {
/* 139 */         if (args[i] instanceof Localizable) {
/* 140 */           args[i] = localize((Localizable)args[i]);
/*     */         }
/*     */       } 
/* 143 */       String message = MessageFormat.format(str1, args);
/* 144 */       return message;
/*     */     }
/* 146 */     catch (MissingResourceException e) {
/* 147 */       return getDefaultMessage(l);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDefaultMessage(Localizable l) {
/* 153 */     String key = l.getKey();
/* 154 */     Object[] args = l.getArguments();
/* 155 */     StringBuilder sb = new StringBuilder();
/* 156 */     sb.append("[failed to localize] ");
/* 157 */     sb.append(key);
/* 158 */     if (args != null) {
/* 159 */       sb.append('(');
/* 160 */       for (int i = 0; i < args.length; i++) {
/* 161 */         if (i != 0)
/* 162 */           sb.append(", "); 
/* 163 */         sb.append(String.valueOf(args[i]));
/*     */       } 
/* 165 */       sb.append(')');
/*     */     } 
/* 167 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\localization\Localizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */