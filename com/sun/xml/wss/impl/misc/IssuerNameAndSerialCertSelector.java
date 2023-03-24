/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.x500.X500Principal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IssuerNameAndSerialCertSelector
/*     */   implements CertSelector
/*     */ {
/*     */   private final BigInteger serialNumber;
/*     */   private final String issuerName;
/*  77 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuerNameAndSerialCertSelector(BigInteger serialNum, String issuer) {
/*  82 */     this.serialNumber = serialNum;
/*  83 */     this.issuerName = issuer;
/*     */   }
/*     */   
/*     */   public boolean match(Certificate cert) {
/*  87 */     if (cert instanceof X509Certificate && 
/*  88 */       matchesIssuerSerialAndName(this.serialNumber, this.issuerName, (X509Certificate)cert)) {
/*  89 */       return true;
/*     */     }
/*     */     
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  96 */     return new IssuerNameAndSerialCertSelector(this.serialNumber, this.issuerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchesIssuerSerialAndName(BigInteger serialNumberMatch, String issuerNameMatch, X509Certificate x509Cert) {
/* 105 */     X500Principal thisIssuerPrincipal = x509Cert.getIssuerX500Principal();
/* 106 */     X500Principal issuerPrincipal = new X500Principal(this.issuerName);
/*     */     
/* 108 */     BigInteger thisSerialNumber = x509Cert.getSerialNumber();
/*     */ 
/*     */     
/* 111 */     if (this.serialNumber.equals(serialNumberMatch) && issuerPrincipal.equals(thisIssuerPrincipal))
/*     */     {
/* 113 */       return true;
/*     */     }
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\IssuerNameAndSerialCertSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */