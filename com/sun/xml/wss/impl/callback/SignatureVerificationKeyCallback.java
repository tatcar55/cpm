/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
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
/*     */ public class SignatureVerificationKeyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   
/*     */   public SignatureVerificationKeyCallback(Request request) {
/*  67 */     this.request = request;
/*     */   }
/*     */   
/*     */   public Request getRequest() {
/*  71 */     return this.request;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface Request {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class X509CertificateRequest
/*     */     implements Request
/*     */   {
/*     */     X509Certificate certificate;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setX509Certificate(X509Certificate certificate) {
/*  91 */       this.certificate = certificate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509Certificate getX509Certificate() {
/* 100 */       return this.certificate;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class ThumbprintBasedRequest
/*     */     extends X509CertificateRequest
/*     */   {
/*     */     private byte[] x509Thumbprint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ThumbprintBasedRequest(byte[] x509Thumbprint) {
/* 120 */       this.x509Thumbprint = x509Thumbprint;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getThumbprintIdentifier() {
/* 128 */       return this.x509Thumbprint;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class X509SubjectKeyIdentifierBasedRequest
/*     */     extends X509CertificateRequest
/*     */   {
/*     */     private byte[] x509SubjectKeyIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509SubjectKeyIdentifierBasedRequest(byte[] x509SubjectKeyIdentifier) {
/* 149 */       this.x509SubjectKeyIdentifier = x509SubjectKeyIdentifier;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getSubjectKeyIdentifier() {
/* 158 */       return this.x509SubjectKeyIdentifier;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class X509IssuerSerialBasedRequest
/*     */     extends X509CertificateRequest
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
/* 182 */       this.issuerName = issuerName;
/* 183 */       this.serialNumber = serialNumber;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getIssuerName() {
/* 192 */       return this.issuerName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BigInteger getSerialNumber() {
/* 201 */       return this.serialNumber;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PublicKeyBasedRequest
/*     */     extends X509CertificateRequest
/*     */   {
/* 218 */     PublicKey pubKey = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKeyBasedRequest(PublicKey pk) {
/* 227 */       this.pubKey = pk;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKey getPublicKey() {
/* 237 */       return this.pubKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\SignatureVerificationKeyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */