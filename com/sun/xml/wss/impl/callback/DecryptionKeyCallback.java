/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.security.auth.callback.Callback;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DecryptionKeyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   
/*     */   public DecryptionKeyCallback(Request request) {
/*  71 */     this.request = request;
/*     */   }
/*     */   
/*     */   public Request getRequest() {
/*  75 */     return this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Request {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class PrivateKeyRequest
/*     */     implements Request
/*     */   {
/*     */     PrivateKey privateKey;
/*     */ 
/*     */     
/*     */     public void setPrivateKey(PrivateKey privateKey) {
/*  91 */       this.privateKey = privateKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrivateKey getPrivateKey() {
/*  99 */       return this.privateKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class X509SubjectKeyIdentifierBasedRequest
/*     */     extends PrivateKeyRequest
/*     */   {
/*     */     private byte[] x509SubjectKeyIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509SubjectKeyIdentifierBasedRequest(byte[] x509SubjectKeyIdentifier) {
/* 118 */       this.x509SubjectKeyIdentifier = x509SubjectKeyIdentifier;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getSubjectKeyIdentifier() {
/* 126 */       return this.x509SubjectKeyIdentifier;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ThumbprintBasedRequest
/*     */     extends PrivateKeyRequest
/*     */   {
/*     */     private byte[] x509Thumbprint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ThumbprintBasedRequest(byte[] x509Thumbprint) {
/* 146 */       this.x509Thumbprint = x509Thumbprint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getThumbprintIdentifier() {
/* 154 */       return this.x509Thumbprint;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class X509IssuerSerialBasedRequest
/*     */     extends PrivateKeyRequest
/*     */   {
/*     */     private String issuerName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private BigInteger serialNumber;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509IssuerSerialBasedRequest(String issuerName, BigInteger serialNumber) {
/* 178 */       this.issuerName = issuerName;
/* 179 */       this.serialNumber = serialNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getIssuerName() {
/* 188 */       return this.issuerName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BigInteger getSerialNumber() {
/* 199 */       return this.serialNumber;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class X509CertificateBasedRequest
/*     */     extends PrivateKeyRequest
/*     */   {
/*     */     private X509Certificate certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509CertificateBasedRequest(X509Certificate certificate) {
/* 218 */       this.certificate = certificate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509Certificate getX509Certificate() {
/* 227 */       return this.certificate;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class SymmetricKeyRequest
/*     */     implements Request
/*     */   {
/*     */     SecretKey symmetricKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setSymmetricKey(SecretKey symmetricKey) {
/* 247 */       this.symmetricKey = symmetricKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecretKey getSymmetricKey() {
/* 256 */       return this.symmetricKey;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AliasSymmetricKeyRequest
/*     */     extends SymmetricKeyRequest
/*     */   {
/*     */     private String alias;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AliasSymmetricKeyRequest(String alias) {
/* 275 */       this.alias = alias;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAlias() {
/* 283 */       return this.alias;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PublicKeyBasedPrivKeyRequest
/*     */     extends PrivateKeyRequest
/*     */   {
/*     */     private PublicKey pk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKeyBasedPrivKeyRequest(PublicKey publicKey) {
/* 302 */       this.pk = publicKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKey getPublicKey() {
/* 311 */       return this.pk;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\DecryptionKeyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */