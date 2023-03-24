/*     */ package com.sun.xml.wss.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509IssuerSerialStrategy
/*     */   extends KeyInfoStrategy
/*     */ {
/*  66 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   X509Certificate cert = null;
/*     */   
/*  73 */   String alias = null;
/*     */ 
/*     */   
/*     */   public X509IssuerSerialStrategy() {}
/*     */ 
/*     */   
/*     */   public X509IssuerSerialStrategy(String alias, boolean forSigning) {
/*  80 */     this.alias = alias;
/*     */     
/*  82 */     this.cert = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(SecurityTokenReference tokenRef, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*  88 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(secureMsg.getSOAPPart(), this.cert);
/*     */     
/*  90 */     tokenRef.setReference((ReferenceElement)x509IssuerSerial);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId) throws XWSSecurityException {
/*  99 */     Document ownerDoc = keyInfo.getOwnerDocument();
/* 100 */     SecurityTokenReference tokenRef = new SecurityTokenReference(ownerDoc);
/*     */     
/* 102 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial(ownerDoc, this.cert);
/*     */     
/* 104 */     tokenRef.setReference((ReferenceElement)x509IssuerSerial);
/* 105 */     keyInfo.addSecurityTokenReference(tokenRef);
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 109 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/* 113 */     return this.alias;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\X509IssuerSerialStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */