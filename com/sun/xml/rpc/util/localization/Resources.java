/*    */ package com.sun.xml.rpc.util.localization;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.MissingResourceException;
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
/*    */ public class Resources
/*    */ {
/*    */   private ResourceBundle _bundle;
/*    */   
/*    */   public Resources(String bundleName) throws MissingResourceException {
/* 41 */     this._bundle = ResourceBundle.getBundle(bundleName);
/*    */   }
/*    */   
/*    */   public String getString(String key) {
/* 45 */     return getText(key, null);
/*    */   }
/*    */   
/*    */   public String getString(String key, String arg) {
/* 49 */     return getText(key, new String[] { arg });
/*    */   }
/*    */   
/*    */   public String getString(String key, String[] args) {
/* 53 */     return getText(key, args);
/*    */   }
/*    */   
/*    */   private String getText(String key, String[] args) {
/* 57 */     if (this._bundle == null) {
/* 58 */       return "";
/*    */     }
/*    */     try {
/* 61 */       return MessageFormat.format(this._bundle.getString(key), (Object[])args);
/* 62 */     } catch (MissingResourceException e) {
/* 63 */       String msg = "Missing resource: key={0}";
/* 64 */       return MessageFormat.format(msg, (Object[])new String[] { key });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\Resources.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */