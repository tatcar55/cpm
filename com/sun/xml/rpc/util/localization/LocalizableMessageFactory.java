/*    */ package com.sun.xml.rpc.util.localization;
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
/*    */ public class LocalizableMessageFactory
/*    */ {
/*    */   protected String _bundlename;
/*    */   
/*    */   public LocalizableMessageFactory(String bundlename) {
/* 37 */     this._bundlename = bundlename;
/*    */   }
/*    */   
/*    */   public Localizable getMessage(String key) {
/* 41 */     return getMessage(key, (Object[])null);
/*    */   }
/*    */   
/*    */   public Localizable getMessage(String key, String arg) {
/* 45 */     return getMessage(key, new Object[] { arg });
/*    */   }
/*    */   
/*    */   public Localizable getMessage(String key, Localizable localizable) {
/* 49 */     return getMessage(key, new Object[] { localizable });
/*    */   }
/*    */   
/*    */   public Localizable getMessage(String key, Object[] args) {
/* 53 */     return new LocalizableMessage(this._bundlename, key, args);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\LocalizableMessageFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */