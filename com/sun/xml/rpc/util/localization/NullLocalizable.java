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
/*    */ 
/*    */ public class NullLocalizable
/*    */   implements Localizable
/*    */ {
/* 36 */   protected static NullLocalizable instance = null;
/*    */   
/*    */   public NullLocalizable(String key) {
/* 39 */     this._key = key;
/*    */   }
/*    */   private String _key;
/*    */   public String getKey() {
/* 43 */     return this._key;
/*    */   }
/*    */   public Object[] getArguments() {
/* 46 */     return null;
/*    */   }
/*    */   public String getResourceBundleName() {
/* 49 */     return "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static NullLocalizable instance() {
/* 55 */     if (instance == null) {
/* 56 */       instance = new NullLocalizable(null);
/*    */     }
/* 58 */     return instance;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\NullLocalizable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */