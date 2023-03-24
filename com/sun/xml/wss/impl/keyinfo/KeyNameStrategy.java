/*     */ package com.sun.xml.wss.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
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
/*     */ public class KeyNameStrategy
/*     */   extends KeyInfoStrategy
/*     */ {
/*  62 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*     */   String keyName;
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyNameStrategy() {
/*  70 */     this.keyName = null;
/*     */   }
/*     */   
/*     */   public KeyNameStrategy(String alias, boolean forSigning) {
/*  74 */     this.keyName = alias;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(SecurityTokenReference tokenRef, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*  82 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0703_UNSUPPORTED_OPERATION());
/*     */     
/*  84 */     throw new UnsupportedOperationException("A ds:KeyName can't be put under a wsse:SecurityTokenReference");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId) throws XWSSecurityException {
/*  95 */     keyInfo.addKeyName(this.keyName);
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/*  99 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0705_UNSUPPORTED_OPERATION());
/*     */     
/* 101 */     throw new UnsupportedOperationException("Setting a certificate is not a supported operation for ds:KeyName strategy");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyName() {
/* 106 */     return this.keyName;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/* 110 */     return this.keyName;
/*     */   }
/*     */   
/*     */   public void setKeyName(String name) {
/* 114 */     this.keyName = name;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\KeyNameStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */