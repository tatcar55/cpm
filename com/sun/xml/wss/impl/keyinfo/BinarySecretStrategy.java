/*     */ package com.sun.xml.wss.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class BinarySecretStrategy
/*     */   extends KeyInfoStrategy
/*     */ {
/*  66 */   private byte[] secret = null;
/*     */   
/*  68 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecretStrategy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecretStrategy(byte[] secret) {
/*  80 */     this.secret = secret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId) throws XWSSecurityException {}
/*     */ 
/*     */   
/*     */   public void insertKey(SecurityTokenReference tokenRef, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*  89 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0703_UNSUPPORTED_OPERATION());
/*     */     
/*  91 */     throw new UnsupportedOperationException("A ds:BinarySecret can't be put under a wsse:SecurityTokenReference");
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/*  96 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0705_UNSUPPORTED_OPERATION());
/*     */     
/*  98 */     throw new UnsupportedOperationException("Setting a certificate is not a supported operation for ds:BinarySecret strategy");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAlias() {
/* 103 */     return Base64.encode(this.secret);
/*     */   }
/*     */   
/*     */   public void setSecret(byte[] secret) {
/* 107 */     this.secret = secret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\BinarySecretStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */