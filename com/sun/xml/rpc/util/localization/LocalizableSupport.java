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
/*    */ public class LocalizableSupport
/*    */ {
/*    */   protected String key;
/*    */   protected Object[] arguments;
/*    */   
/*    */   public LocalizableSupport(String key) {
/* 37 */     this(key, (Object[])null);
/*    */   }
/*    */   
/*    */   public LocalizableSupport(String key, String argument) {
/* 41 */     this(key, new Object[] { argument });
/*    */   }
/*    */   
/*    */   public LocalizableSupport(String key, Localizable localizable) {
/* 45 */     this(key, new Object[] { localizable });
/*    */   }
/*    */   
/*    */   public LocalizableSupport(String key, Object[] arguments) {
/* 49 */     this.key = key;
/* 50 */     this.arguments = arguments;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 54 */     return this.key;
/*    */   }
/*    */   public Object[] getArguments() {
/* 57 */     return this.arguments;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\localization\LocalizableSupport.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */