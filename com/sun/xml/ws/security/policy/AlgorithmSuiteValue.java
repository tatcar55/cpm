/*     */ package com.sun.xml.ws.security.policy;
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
/*     */ public enum AlgorithmSuiteValue
/*     */ {
/*  57 */   Basic256("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   Basic192("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   Basic128("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   TripleDes("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   Basic256Rsa15("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   Basic192Rsa15("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   Basic128Rsa15("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   TripleDesRsa15("http://www.w3.org/2000/09/xmldsig#sha1", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   Basic256Sha256("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 129 */   Basic192Sha256("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 137 */   Basic128Sha256("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   TripleDesSha256("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   Basic256Sha256Rsa15("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes256-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes256", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 256),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   Basic192Sha256Rsa15("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes192-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes192", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 169 */   Basic128Sha256Rsa15("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#aes128-cbc", "http://www.w3.org/2001/04/xmlenc#kw-aes128", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 128),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   TripleDesSha256Rsa15("http://www.w3.org/2001/04/xmlenc#sha256", "http://www.w3.org/2001/04/xmlenc#tripledes-cbc", "http://www.w3.org/2001/04/xmlenc#kw-tripledes", "http://www.w3.org/2001/04/xmlenc#rsa-1_5", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1", 192);
/*     */ 
/*     */ 
/*     */   
/*     */   private final String dsigAlgorithm;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String encAlgorithm;
/*     */ 
/*     */   
/*     */   private final String symKWAlgorithm;
/*     */ 
/*     */   
/*     */   private final String asymKWAlgorithm;
/*     */ 
/*     */   
/*     */   private final String encKDAlgorithm;
/*     */ 
/*     */   
/*     */   private final String sigKDAlgorithm;
/*     */ 
/*     */   
/*     */   private final int minSKLAlgorithm;
/*     */ 
/*     */ 
/*     */   
/*     */   AlgorithmSuiteValue(String dsigAlgorithm, String encAlgorithm, String symKWAlgorithm, String asymKWAlgorithm, String encKDAlgorithm, String sigKDAlgorithm, int minSKLAlgorithm) {
/* 205 */     this.dsigAlgorithm = dsigAlgorithm;
/* 206 */     this.encAlgorithm = encAlgorithm;
/* 207 */     this.symKWAlgorithm = symKWAlgorithm;
/* 208 */     this.asymKWAlgorithm = asymKWAlgorithm;
/* 209 */     this.encKDAlgorithm = encKDAlgorithm;
/* 210 */     this.sigKDAlgorithm = sigKDAlgorithm;
/* 211 */     this.minSKLAlgorithm = minSKLAlgorithm;
/*     */   }
/*     */   
/*     */   public String getDigAlgorithm() {
/* 215 */     return this.dsigAlgorithm;
/*     */   }
/*     */   
/*     */   public String getEncAlgorithm() {
/* 219 */     return this.encAlgorithm;
/*     */   }
/*     */   
/*     */   public String getSymKWAlgorithm() {
/* 223 */     return this.symKWAlgorithm;
/*     */   }
/*     */   
/*     */   public String getAsymKWAlgorithm() {
/* 227 */     return this.asymKWAlgorithm;
/*     */   }
/*     */   
/*     */   public String getEncKDAlgorithm() {
/* 231 */     return this.encKDAlgorithm;
/*     */   }
/*     */   
/*     */   public String getSigKDAlgorithm() {
/* 235 */     return this.sigKDAlgorithm;
/*     */   }
/*     */   
/*     */   public int getMinSKLAlgorithm() {
/* 239 */     return this.minSKLAlgorithm;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\AlgorithmSuiteValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */