/*    */ package com.sun.xml.fastinfoset;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
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
/*    */ public class CommonResourceBundle
/*    */   extends AbstractResourceBundle
/*    */ {
/*    */   public static final String BASE_NAME = "com.sun.xml.fastinfoset.resources.ResourceBundle";
/* 28 */   private static CommonResourceBundle instance = null;
/* 29 */   private static Locale locale = null;
/* 30 */   private ResourceBundle bundle = null;
/*    */ 
/*    */   
/*    */   protected CommonResourceBundle() {
/* 34 */     this.bundle = ResourceBundle.getBundle("com.sun.xml.fastinfoset.resources.ResourceBundle");
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommonResourceBundle(Locale locale) {
/* 39 */     this.bundle = ResourceBundle.getBundle("com.sun.xml.fastinfoset.resources.ResourceBundle", locale);
/*    */   }
/*    */   
/*    */   public static CommonResourceBundle getInstance() {
/* 43 */     if (instance == null) {
/* 44 */       synchronized (CommonResourceBundle.class) {
/* 45 */         instance = new CommonResourceBundle();
/*    */ 
/*    */ 
/*    */         
/* 49 */         String localeString = null;
/* 50 */         locale = parseLocale(localeString);
/*    */       } 
/*    */     }
/*    */     
/* 54 */     return instance;
/*    */   }
/*    */   
/*    */   public static CommonResourceBundle getInstance(Locale locale) {
/* 58 */     if (instance == null) {
/* 59 */       synchronized (CommonResourceBundle.class) {
/* 60 */         instance = new CommonResourceBundle(locale);
/*    */       } 
/*    */     } else {
/* 63 */       synchronized (CommonResourceBundle.class) {
/* 64 */         if (CommonResourceBundle.locale != locale) {
/* 65 */           instance = new CommonResourceBundle(locale);
/*    */         }
/*    */       } 
/*    */     } 
/* 69 */     return instance;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceBundle getBundle() {
/* 74 */     return this.bundle;
/*    */   }
/*    */   public ResourceBundle getBundle(Locale locale) {
/* 77 */     return ResourceBundle.getBundle("com.sun.xml.fastinfoset.resources.ResourceBundle", locale);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\CommonResourceBundle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */