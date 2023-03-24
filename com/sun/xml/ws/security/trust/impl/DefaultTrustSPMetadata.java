/*    */ package com.sun.xml.ws.security.trust.impl;
/*    */ 
/*    */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class DefaultTrustSPMetadata
/*    */   implements TrustSPMetadata
/*    */ {
/*    */   private String tokenType;
/*    */   private String keyType;
/*    */   private String certAlias;
/* 58 */   private Map<String, Object> otherOptions = new HashMap<String, Object>();
/*    */ 
/*    */ 
/*    */   
/*    */   public DefaultTrustSPMetadata(String endpoint) {}
/*    */ 
/*    */   
/*    */   public void setCertAlias(String certAlias) {
/* 66 */     this.certAlias = certAlias;
/*    */   }
/*    */   
/*    */   public String getCertAlias() {
/* 70 */     return this.certAlias;
/*    */   }
/*    */   
/*    */   public void setTokenType(String tokenType) {
/* 74 */     this.tokenType = tokenType;
/*    */   }
/*    */   
/*    */   public String getTokenType() {
/* 78 */     return this.tokenType;
/*    */   }
/*    */   
/*    */   public void setKeyType(String keyType) {
/* 82 */     this.keyType = keyType;
/*    */   }
/*    */   
/*    */   public String getKeyType() {
/* 86 */     return this.keyType;
/*    */   }
/*    */   
/*    */   public Map<String, Object> getOtherOptions() {
/* 90 */     return this.otherOptions;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\DefaultTrustSPMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */