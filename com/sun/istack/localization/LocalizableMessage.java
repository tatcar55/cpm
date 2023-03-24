/*    */ package com.sun.istack.localization;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public final class LocalizableMessage
/*    */   implements Localizable
/*    */ {
/*    */   private final String _bundlename;
/*    */   private final String _key;
/*    */   private final Object[] _args;
/*    */   
/*    */   public LocalizableMessage(String bundlename, String key, Object... args) {
/* 55 */     this._bundlename = bundlename;
/* 56 */     this._key = key;
/* 57 */     if (args == null)
/* 58 */       args = new Object[0]; 
/* 59 */     this._args = args;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 63 */     return this._key;
/*    */   }
/*    */   
/*    */   public Object[] getArguments() {
/* 67 */     return Arrays.copyOf(this._args, this._args.length);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 71 */     return this._bundlename;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\localization\LocalizableMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */