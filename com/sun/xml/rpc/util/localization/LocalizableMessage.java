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
/*    */ public class LocalizableMessage
/*    */   implements Localizable
/*    */ {
/*    */   protected String _bundlename;
/*    */   protected String _key;
/*    */   protected Object[] _args;
/*    */   
/*    */   public LocalizableMessage(String bundlename, String key) {
/* 39 */     this(bundlename, key, (Object[])null);
/*    */   }
/*    */   
/*    */   public LocalizableMessage(String bundlename, String key, String arg) {
/* 43 */     this(bundlename, key, new Object[] { arg });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected LocalizableMessage(String bundlename, String key, Object[] args) {
/* 50 */     this._bundlename = bundlename;
/* 51 */     this._key = key;
/* 52 */     this._args = args;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 56 */     return this._key;
/*    */   }
/*    */   
/*    */   public Object[] getArguments() {
/* 60 */     return this._args;
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 64 */     return this._bundlename;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\LocalizableMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */