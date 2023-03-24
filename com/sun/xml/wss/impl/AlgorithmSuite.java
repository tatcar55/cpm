/*     */ package com.sun.xml.wss.impl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlgorithmSuite
/*     */ {
/*     */   private String digestAlgo;
/*     */   private String encryptionAlgo;
/*     */   private String symKeyAlgo;
/*     */   private String asymKeyAlgo;
/*     */   private String signatureAlgo;
/*     */   
/*     */   public AlgorithmSuite(String digAlgo, String encAlgo, String symkAlgo, String asymkAlgo) {
/*  69 */     this.digestAlgo = digAlgo;
/*  70 */     this.encryptionAlgo = encAlgo;
/*  71 */     this.symKeyAlgo = symkAlgo;
/*  72 */     this.asymKeyAlgo = asymkAlgo;
/*     */   }
/*     */   
/*     */   public String getDigestAlgorithm() {
/*  76 */     return this.digestAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/*  81 */     return this.encryptionAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSymmetricKeyAlgorithm() {
/*  86 */     return this.symKeyAlgo;
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeyAlgorithm() {
/*  90 */     return this.asymKeyAlgo;
/*     */   }
/*     */   
/*     */   public String getSignatureKDAlogrithm() {
/*  94 */     throw new UnsupportedOperationException("getSignatureKDAlogrithm not supported");
/*     */   }
/*     */   
/*     */   public String getEncryptionKDAlogrithm() {
/*  98 */     throw new UnsupportedOperationException("getEncryptionKDAlogrithm not supported");
/*     */   }
/*     */   
/*     */   public int getMinSKLAlgorithm() {
/* 102 */     throw new UnsupportedOperationException("getMinSKLAlgorithm not supported");
/*     */   }
/*     */   
/*     */   public String getSymmetricKeySignatureAlgorithm() {
/* 106 */     throw new UnsupportedOperationException("getSymmetricKeySignatureAlgorithm not supported");
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeySignatureAlgorithm() {
/* 110 */     throw new UnsupportedOperationException(" getAsymmetricKeySignatureAlgorithm not supported");
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String sigAlgo) {
/* 114 */     this.signatureAlgo = sigAlgo;
/*     */   }
/*     */   
/*     */   public String getSignatureAlgorithm() {
/* 118 */     return this.signatureAlgo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\AlgorithmSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */