/*    */ package com.sun.xml.wss.impl.config;
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
/*    */ public enum SecurityConfigProvider
/*    */ {
/* 57 */   INSTANCE; private long maxNonceAge;
/*    */   public static final long DEFAULT_MAX_NONCE_AGE = 900000L;
/*    */   
/*    */   SecurityConfigProvider() {
/* 61 */     this.maxNonceAge = 900000L;
/*    */   } public void init(long maxNonceAge) {
/* 63 */     this.maxNonceAge = maxNonceAge;
/*    */   } public long getMaxNonceAge() {
/* 65 */     return this.maxNonceAge;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\config\SecurityConfigProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */