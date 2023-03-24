/*     */ package com.sun.xml.ws.security.trust.impl.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.client.SecondaryIssuedTokenParameters;
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
/*     */ public class SecondaryIssuedTokenParametersImpl
/*     */   implements SecondaryIssuedTokenParameters
/*     */ {
/*  52 */   private String tokenType = null;
/*     */   
/*  54 */   private String keyType = null;
/*     */   
/*  56 */   private long keySize = -1L;
/*     */   
/*  58 */   private String signatureAlg = null;
/*     */   
/*  60 */   private String encAlg = null;
/*     */   
/*  62 */   private String canAlg = null;
/*     */   
/*  64 */   private String keyWrapAlg = null;
/*     */   
/*  66 */   private String signWith = null;
/*     */   
/*  68 */   private String encryptWith = null;
/*     */   
/*  70 */   private Claims claims = null;
/*     */   
/*     */   public void setTokenType(String tokenType) {
/*  73 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public void setKeyType(String keyType) {
/*  77 */     this.keyType = keyType;
/*     */   }
/*     */   
/*     */   public void setKeySize(long keySize) {
/*  81 */     this.keySize = keySize;
/*     */   }
/*     */   
/*     */   public void setSignWith(String signWithAlg) {
/*  85 */     this.signWith = signWithAlg;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(String encWithAlg) {
/*  89 */     this.encryptWith = encWithAlg;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String sigAlg) {
/*  93 */     this.signatureAlg = sigAlg;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(String encAlg) {
/*  97 */     this.encAlg = encAlg;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(String canAlg) {
/* 101 */     this.canAlg = canAlg;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(String keyWrapAlg) {
/* 105 */     this.keyWrapAlg = keyWrapAlg;
/*     */   }
/*     */   
/*     */   public void setClaims(Claims claims) {
/* 109 */     this.claims = claims;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 113 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public String getKeyType() {
/* 117 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 121 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public String getSignatureAlgorithm() {
/* 125 */     return this.signatureAlg;
/*     */   }
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 129 */     return this.encAlg;
/*     */   }
/*     */   
/*     */   public String getCanonicalizationAlgorithm() {
/* 133 */     return this.canAlg;
/*     */   }
/*     */   
/*     */   public String getKeyWrapAlgorithm() {
/* 137 */     return this.keyWrapAlg;
/*     */   }
/*     */   
/*     */   public String getSignWith() {
/* 141 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public String getEncryptWith() {
/* 145 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 149 */     return this.claims;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\client\SecondaryIssuedTokenParametersImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */