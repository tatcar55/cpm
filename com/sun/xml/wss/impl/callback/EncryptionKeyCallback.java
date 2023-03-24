/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
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
/*     */ public class EncryptionKeyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   
/*     */   public EncryptionKeyCallback(Request request) {
/*  68 */     this.request = request;
/*     */   }
/*     */   
/*     */   public Request getRequest() {
/*  72 */     return this.request;
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface Request {}
/*     */   
/*     */   public static abstract class X509CertificateRequest
/*     */     implements Request
/*     */   {
/*     */     X509Certificate certificate;
/*     */     
/*     */     public void setX509Certificate(X509Certificate certificate) {
/*  84 */       this.certificate = certificate;
/*     */     }
/*     */     
/*     */     public X509Certificate getX509Certificate() {
/*  88 */       return this.certificate;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DefaultX509CertificateRequest
/*     */     extends X509CertificateRequest {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AliasX509CertificateRequest
/*     */     extends X509CertificateRequest
/*     */   {
/*     */     private String alias;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AliasX509CertificateRequest(String alias) {
/* 116 */       this.alias = alias;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAlias() {
/* 125 */       return this.alias;
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
/*     */     public void setSymmetricKey(SecretKey symmetricKey) {
/* 144 */       this.symmetricKey = symmetricKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecretKey getSymmetricKey() {
/* 154 */       return this.symmetricKey;
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
/*     */     
/*     */     public AliasSymmetricKeyRequest(String alias) {
/* 174 */       this.alias = alias;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAlias() {
/* 183 */       return this.alias;
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
/*     */   public static class PublicKeyBasedRequest
/*     */     extends X509CertificateRequest
/*     */   {
/* 197 */     PublicKey pubKey = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKeyBasedRequest(PublicKey pk) {
/* 206 */       this.pubKey = pk;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKey getPublicKey() {
/* 216 */       return this.pubKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\EncryptionKeyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */