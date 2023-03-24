/*     */ package com.sun.xml.wss.impl.callback;
/*     */ 
/*     */ import java.security.PrivateKey;
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
/*     */ public class SignatureKeyCallback
/*     */   extends XWSSCallback
/*     */   implements Callback
/*     */ {
/*     */   private Request request;
/*     */   
/*     */   public SignatureKeyCallback(Request request) {
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
/*     */   public static abstract class PrivKeyCertRequest
/*     */     implements Request
/*     */   {
/*     */     PrivateKey privateKey;
/*     */ 
/*     */     
/*     */     X509Certificate certificate;
/*     */ 
/*     */ 
/*     */     
/*     */     public void setPrivateKey(PrivateKey privateKey) {
/*  93 */       this.privateKey = privateKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PrivateKey getPrivateKey() {
/* 103 */       return this.privateKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setX509Certificate(X509Certificate certificate) {
/* 114 */       this.certificate = certificate;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public X509Certificate getX509Certificate() {
/* 124 */       return this.certificate;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DefaultPrivKeyCertRequest
/*     */     extends PrivKeyCertRequest {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AliasPrivKeyCertRequest
/*     */     extends PrivKeyCertRequest
/*     */   {
/*     */     private String alias;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AliasPrivKeyCertRequest(String alias) {
/* 151 */       this.alias = alias;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getAlias() {
/* 161 */       return this.alias;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PublicKeyBasedPrivKeyCertRequest
/*     */     extends PrivKeyCertRequest
/*     */   {
/*     */     private PublicKey pk;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKeyBasedPrivKeyCertRequest(PublicKey publicKey) {
/* 179 */       this.pk = publicKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PublicKey getPublicKey() {
/* 188 */       return this.pk;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\SignatureKeyCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */