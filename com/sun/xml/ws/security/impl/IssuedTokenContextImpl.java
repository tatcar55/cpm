/*     */ package com.sun.xml.ws.security.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.net.URI;
/*     */ import java.security.Key;
/*     */ import java.security.KeyPair;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.security.auth.Subject;
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
/*     */ public class IssuedTokenContextImpl
/*     */   implements IssuedTokenContext
/*     */ {
/*  69 */   X509Certificate x509Certificate = null;
/*  70 */   Token securityToken = null;
/*  71 */   Token associatedProofToken = null;
/*  72 */   Token secTokenReference = null;
/*  73 */   Token unAttachedSecTokenReference = null;
/*  74 */   ArrayList<Object> securityPolicies = new ArrayList();
/*  75 */   Object otherPartyEntropy = null;
/*  76 */   Object selfEntropy = null;
/*     */   URI computedKeyAlgorithm;
/*     */   String sigAlgorithm;
/*     */   String encAlgorithm;
/*     */   String canonicalizationAlgorithm;
/*     */   String signWith;
/*     */   String encryptWith;
/*  83 */   byte[] proofKey = null;
/*  84 */   SecurityContextTokenInfo sctInfo = null;
/*  85 */   Date creationTime = null;
/*  86 */   Date expiryTime = null;
/*  87 */   String username = null;
/*  88 */   String endPointAddress = null;
/*     */   Subject subject;
/*     */   KeyPair proofKeyPair;
/*  91 */   String authType = null;
/*  92 */   String tokenType = null;
/*  93 */   String keyType = null;
/*  94 */   String tokenIssuer = null;
/*  95 */   Token target = null;
/*     */   
/*  97 */   Map<String, Object> otherProps = new HashMap<String, Object>();
/*     */   
/*     */   public X509Certificate getRequestorCertificate() {
/* 100 */     return this.x509Certificate;
/*     */   }
/*     */   
/*     */   public void setRequestorCertificate(X509Certificate cert) {
/* 104 */     this.x509Certificate = cert;
/*     */   }
/*     */   
/*     */   public Subject getRequestorSubject() {
/* 108 */     return this.subject;
/*     */   }
/*     */   
/*     */   public void setRequestorSubject(Subject subject) {
/* 112 */     this.subject = subject;
/*     */   }
/*     */   
/*     */   public String getRequestorUsername() {
/* 116 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setRequestorUsername(String username) {
/* 120 */     this.username = username;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSecurityToken(Token securityToken) {
/* 125 */     this.securityToken = securityToken;
/*     */   }
/*     */   
/*     */   public Token getSecurityToken() {
/* 129 */     return this.securityToken;
/*     */   }
/*     */   
/*     */   public void setAssociatedProofToken(Token associatedProofToken) {
/* 133 */     this.associatedProofToken = associatedProofToken;
/*     */   }
/*     */   
/*     */   public Token getAssociatedProofToken() {
/* 137 */     return this.associatedProofToken;
/*     */   }
/*     */   
/*     */   public Token getAttachedSecurityTokenReference() {
/* 141 */     return this.secTokenReference;
/*     */   }
/*     */   
/*     */   public void setAttachedSecurityTokenReference(Token secTokenReference) {
/* 145 */     this.secTokenReference = secTokenReference;
/*     */   }
/*     */   
/*     */   public Token getUnAttachedSecurityTokenReference() {
/* 149 */     return this.unAttachedSecTokenReference;
/*     */   }
/*     */   
/*     */   public void setUnAttachedSecurityTokenReference(Token secTokenReference) {
/* 153 */     this.unAttachedSecTokenReference = secTokenReference;
/*     */   }
/*     */   
/*     */   public ArrayList<Object> getSecurityPolicy() {
/* 157 */     return this.securityPolicies;
/*     */   }
/*     */   
/*     */   public void setOtherPartyEntropy(Object otherPartyEntropy) {
/* 161 */     this.otherPartyEntropy = otherPartyEntropy;
/*     */   }
/*     */   
/*     */   public Object getOtherPartyEntropy() {
/* 165 */     return this.otherPartyEntropy;
/*     */   }
/*     */   
/*     */   public Key getDecipheredOtherPartyEntropy(Key privKey) throws XWSSecurityException {
/*     */     try {
/* 170 */       return getDecipheredOtherPartyEntropy(getOtherPartyEntropy(), privKey);
/* 171 */     } catch (XMLEncryptionException xee) {
/* 172 */       throw new XWSSecurityException(xee);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Key getDecipheredOtherPartyEntropy(Object encryptedKey, Key privKey) throws XMLEncryptionException {
/* 179 */     if (encryptedKey instanceof EncryptedKey) {
/* 180 */       EncryptedKey encKey = (EncryptedKey)encryptedKey;
/* 181 */       XMLCipher cipher = XMLCipher.getInstance();
/* 182 */       cipher.setKEK(privKey);
/* 183 */       cipher.decryptKey(encKey);
/* 184 */       return null;
/*     */     } 
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelfEntropy(Object selfEntropy) {
/* 191 */     this.selfEntropy = selfEntropy;
/*     */   }
/*     */   
/*     */   public Object getSelfEntropy() {
/* 195 */     return this.selfEntropy;
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getComputedKeyAlgorithmFromProofToken() {
/* 200 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public void setComputedKeyAlgorithmFromProofToken(URI computedKeyAlgorithm) {
/* 204 */     this.computedKeyAlgorithm = computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public void setProofKey(byte[] key) {
/* 208 */     this.proofKey = key;
/*     */   }
/*     */   
/*     */   public byte[] getProofKey() {
/* 212 */     return this.proofKey;
/*     */   }
/*     */   
/*     */   public void setProofKeyPair(KeyPair keys) {
/* 216 */     this.proofKeyPair = keys;
/*     */   }
/*     */   
/*     */   public KeyPair getProofKeyPair() {
/* 220 */     return this.proofKeyPair;
/*     */   }
/*     */   
/*     */   public void setAuthnContextClass(String authType) {
/* 224 */     this.authType = authType;
/*     */   }
/*     */   
/*     */   public String getAuthnContextClass() {
/* 228 */     return this.authType;
/*     */   }
/*     */   
/*     */   public Date getCreationTime() {
/* 232 */     return this.creationTime;
/*     */   }
/*     */   
/*     */   public Date getExpirationTime() {
/* 236 */     return this.expiryTime;
/*     */   }
/*     */   
/*     */   public void setCreationTime(Date date) {
/* 240 */     this.creationTime = date;
/*     */   }
/*     */   
/*     */   public void setExpirationTime(Date date) {
/* 244 */     this.expiryTime = date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEndpointAddress(String endPointAddress) {
/* 251 */     this.endPointAddress = endPointAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEndpointAddress() {
/* 258 */     return this.endPointAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {}
/*     */ 
/*     */   
/*     */   public SecurityContextTokenInfo getSecurityContextTokenInfo() {
/* 266 */     return this.sctInfo;
/*     */   }
/*     */   
/*     */   public void setSecurityContextTokenInfo(SecurityContextTokenInfo sctInfo) {
/* 270 */     this.sctInfo = sctInfo;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getOtherProperties() {
/* 274 */     return this.otherProps;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 278 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 282 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setKeyType(String keyType) {
/* 286 */     this.keyType = keyType;
/*     */   }
/*     */   
/*     */   public String getKeyType() {
/* 290 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public void setAppliesTo(String appliesTo) {
/* 294 */     this.endPointAddress = appliesTo;
/*     */   }
/*     */   
/*     */   public String getAppliesTo() {
/* 298 */     return this.endPointAddress;
/*     */   }
/*     */   
/*     */   public void setTokenIssuer(String issuer) {
/* 302 */     this.tokenIssuer = issuer;
/*     */   }
/*     */   
/*     */   public String getTokenIssuer() {
/* 306 */     return this.tokenIssuer;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String sigAlg) {
/* 310 */     this.sigAlgorithm = sigAlg;
/*     */   }
/*     */   
/*     */   public String getSignatureAlgorithm() {
/* 314 */     return this.sigAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(String encAlg) {
/* 318 */     this.encAlgorithm = encAlg;
/*     */   }
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 322 */     return this.encAlgorithm;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(String canonAlg) {
/* 326 */     this.canonicalizationAlgorithm = canonAlg;
/*     */   }
/*     */   
/*     */   public String getCanonicalizationAlgorithm() {
/* 330 */     return this.canonicalizationAlgorithm;
/*     */   }
/*     */   
/*     */   public void setSignWith(String signWithAlgo) {
/* 334 */     this.signWith = signWithAlgo;
/*     */   }
/*     */   
/*     */   public String getSignWith() {
/* 338 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(String encryptWithAlgo) {
/* 342 */     this.encryptWith = encryptWithAlgo;
/*     */   }
/*     */   
/*     */   public String getEncryptWith() {
/* 346 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setTarget(Token target) {
/* 350 */     this.target = target;
/*     */   }
/*     */   
/*     */   public Token getTarget() {
/* 354 */     return this.target;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\IssuedTokenContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */