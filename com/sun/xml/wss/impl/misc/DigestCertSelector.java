/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertSelector;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigestCertSelector
/*     */   implements CertSelector
/*     */ {
/*     */   private final byte[] keyId;
/*     */   private final String algorithm;
/*  76 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestCertSelector(byte[] keyIdValue, String algo) {
/*  83 */     this.keyId = keyIdValue;
/*  84 */     this.algorithm = algo;
/*     */   }
/*     */   
/*     */   public boolean match(Certificate cert) {
/*  88 */     if (cert instanceof java.security.cert.X509Certificate) {
/*  89 */       byte[] thumbPrintIdentifier = null;
/*     */       
/*     */       try {
/*  92 */         thumbPrintIdentifier = MessageDigest.getInstance(this.algorithm).digest(cert.getEncoded());
/*  93 */       } catch (NoSuchAlgorithmException ex) {
/*  94 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0708_NO_DIGEST_ALGORITHM(), ex);
/*  95 */         throw new RuntimeException("Digest algorithm SHA-1 not found");
/*  96 */       } catch (CertificateEncodingException ex) {
/*  97 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0709_ERROR_GETTING_RAW_CONTENT(), ex);
/*  98 */         throw new RuntimeException("Error while getting certificate's raw content");
/*     */       } 
/*     */       
/* 101 */       if (Arrays.equals(thumbPrintIdentifier, this.keyId)) {
/* 102 */         return true;
/*     */       }
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 109 */     return new DigestCertSelector(this.keyId, this.algorithm);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\DigestCertSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */