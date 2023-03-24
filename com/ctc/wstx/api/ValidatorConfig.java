/*    */ package com.ctc.wstx.api;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class ValidatorConfig
/*    */   extends CommonConfig
/*    */ {
/* 10 */   static final ValidatorConfig sInstance = new ValidatorConfig();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ValidatorConfig createDefaults() {
/* 21 */     return sInstance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int findPropertyId(String propName) {
/* 26 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Object getProperty(int id) {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean setProperty(String propName, int id, Object value) {
/* 36 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\api\ValidatorConfig.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */