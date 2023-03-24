/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultSTSConfiguration
/*     */   implements STSConfiguration
/*     */ {
/*  62 */   private Map<String, TrustSPMetadata> spMap = new HashMap<String, TrustSPMetadata>();
/*     */   
/*     */   private String type;
/*     */   
/*     */   private String issuer;
/*     */   private boolean encryptIssuedToken = false;
/*     */   private boolean encryptIssuedKey = true;
/*     */   private long issuedTokenTimeout;
/*     */   private CallbackHandler callbackHandler;
/*  71 */   private Map<String, Object> otherOptions = new HashMap<String, Object>();
/*     */ 
/*     */   
/*     */   public void addTrustSPMetadata(TrustSPMetadata data, String spEndpoint) {
/*  75 */     this.spMap.put(spEndpoint, data);
/*     */   }
/*     */   
/*     */   public TrustSPMetadata getTrustSPMetadata(String spEndpoint) {
/*  79 */     return this.spMap.get(spEndpoint);
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  83 */     this.type = type;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  87 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setIssuer(String issuer) {
/*  91 */     this.issuer = issuer;
/*     */   }
/*     */   
/*     */   public String getIssuer() {
/*  95 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public void setEncryptIssuedToken(boolean encryptIssuedToken) {
/*  99 */     this.encryptIssuedToken = encryptIssuedToken;
/*     */   }
/*     */   
/*     */   public boolean getEncryptIssuedToken() {
/* 103 */     return this.encryptIssuedToken;
/*     */   }
/*     */   
/*     */   public void setEncryptIssuedKey(boolean encryptIssuedKey) {
/* 107 */     this.encryptIssuedKey = encryptIssuedKey;
/*     */   }
/*     */   
/*     */   public boolean getEncryptIssuedKey() {
/* 111 */     return this.encryptIssuedKey;
/*     */   }
/*     */   
/*     */   public void setIssuedTokenTimeout(long issuedTokenTimeout) {
/* 115 */     this.issuedTokenTimeout = issuedTokenTimeout;
/*     */   }
/*     */   
/*     */   public long getIssuedTokenTimeout() {
/* 119 */     return this.issuedTokenTimeout;
/*     */   }
/*     */   
/*     */   public void setCallbackHandler(CallbackHandler callbackHandler) {
/* 123 */     this.callbackHandler = callbackHandler;
/*     */   }
/*     */   
/*     */   public CallbackHandler getCallbackHandler() {
/* 127 */     return this.callbackHandler;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getOtherOptions() {
/* 131 */     return this.otherOptions;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\DefaultSTSConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */