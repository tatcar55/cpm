/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSTrustVersion10
/*     */   extends WSTrustVersion
/*     */ {
/*  54 */   private String nsURI = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*     */   
/*     */   public String getNamespaceURI() {
/*  57 */     return this.nsURI;
/*     */   }
/*     */   
/*     */   public String getIssueRequestTypeURI() {
/*  61 */     return this.nsURI + "/Issue";
/*     */   }
/*     */   
/*     */   public String getRenewRequestTypeURI() {
/*  65 */     return this.nsURI + "/Renew";
/*     */   }
/*     */   
/*     */   public String getCancelRequestTypeURI() {
/*  69 */     return this.nsURI + "/Cancel";
/*     */   }
/*     */   
/*     */   public String getValidateRequestTypeURI() {
/*  73 */     return this.nsURI + "/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateStatuesTokenType() {
/*  77 */     return this.nsURI + "/RSTR/Status";
/*     */   }
/*     */   
/*     */   public String getKeyExchangeRequestTypeURI() {
/*  81 */     return this.nsURI + "/KET";
/*     */   }
/*     */   
/*     */   public String getPublicKeyTypeURI() {
/*  85 */     return this.nsURI + "/PublicKey";
/*     */   }
/*     */   
/*     */   public String getSymmetricKeyTypeURI() {
/*  89 */     return this.nsURI + "/SymmetricKey";
/*     */   }
/*     */   
/*     */   public String getBearerKeyTypeURI() {
/*  93 */     return "http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey";
/*     */   }
/*     */   
/*     */   public String getIssueRequestAction() {
/*  97 */     return this.nsURI + "/RST/Issue";
/*     */   }
/*     */   
/*     */   public String getIssueResponseAction() {
/* 101 */     return this.nsURI + "/RSTR/Issue";
/*     */   }
/*     */   
/*     */   public String getIssueFinalResoponseAction() {
/* 105 */     return this.nsURI + "/RSTR/Issue";
/*     */   }
/*     */   
/*     */   public String getRenewRequestAction() {
/* 109 */     return this.nsURI + "/RST/Renew";
/*     */   }
/*     */   
/*     */   public String getRenewResponseAction() {
/* 113 */     return this.nsURI + "/RSTR/Renew";
/*     */   }
/*     */   
/*     */   public String getRenewFinalResoponseAction() {
/* 117 */     return this.nsURI + "/RSTR/Renew";
/*     */   }
/*     */   public String getCancelRequestAction() {
/* 120 */     return this.nsURI + "/RST/Cancel";
/*     */   }
/*     */   
/*     */   public String getCancelResponseAction() {
/* 124 */     return this.nsURI + "/RSTR/Cancel";
/*     */   }
/*     */   
/*     */   public String getCancelFinalResoponseAction() {
/* 128 */     return this.nsURI + "/RSTR/Cancel";
/*     */   }
/*     */   
/*     */   public String getValidateRequestAction() {
/* 132 */     return this.nsURI + "/RST/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateResponseAction() {
/* 136 */     return this.nsURI + "/RSTR/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateFinalResoponseAction() {
/* 140 */     return this.nsURI + "/RSTR/Validate";
/*     */   }
/*     */   
/*     */   public String getCKPSHA1algorithmURI() {
/* 144 */     return this.nsURI + "/CK/PSHA1";
/*     */   }
/*     */   
/*     */   public String getCKHASHalgorithmURI() {
/* 148 */     return this.nsURI + "/CK/HASH";
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeyBinarySecretTypeURI() {
/* 152 */     return this.nsURI + "/AsymmetricKey";
/*     */   }
/*     */   
/*     */   public String getNonceBinarySecretTypeURI() {
/* 156 */     return this.nsURI + "/Nonce";
/*     */   }
/*     */   
/*     */   public String getValidStatusCodeURI() {
/* 160 */     return this.nsURI + "/status/valid";
/*     */   }
/*     */   
/*     */   public String getInvalidStatusCodeURI() {
/* 164 */     return this.nsURI + "/status/invalid";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\WSTrustVersion10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */