/*    */ package org.apache.commons.codec.digest;
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
/*    */ public enum HmacAlgorithms
/*    */ {
/* 41 */   HMAC_MD5("HmacMD5"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   HMAC_SHA_1("HmacSHA1"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 57 */   HMAC_SHA_256("HmacSHA256"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   HMAC_SHA_384("HmacSHA384"),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 73 */   HMAC_SHA_512("HmacSHA512");
/*    */   
/*    */   private final String algorithm;
/*    */   
/*    */   HmacAlgorithms(String algorithm) {
/* 78 */     this.algorithm = algorithm;
/*    */   }
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
/*    */   public String toString() {
/* 91 */     return this.algorithm;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\apache\commons\codec\digest\HmacAlgorithms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */