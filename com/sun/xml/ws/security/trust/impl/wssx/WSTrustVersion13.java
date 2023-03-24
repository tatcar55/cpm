/*     */ package com.sun.xml.ws.security.trust.impl.wssx;
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
/*     */ public class WSTrustVersion13
/*     */   extends WSTrustVersion
/*     */ {
/*  53 */   private String nsURI = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
/*     */   
/*     */   public String getNamespaceURI() {
/*  56 */     return this.nsURI;
/*     */   }
/*     */   
/*     */   public String getIssueRequestTypeURI() {
/*  60 */     return this.nsURI + "/Issue";
/*     */   }
/*     */   
/*     */   public String getRenewRequestTypeURI() {
/*  64 */     return this.nsURI + "/Renew";
/*     */   }
/*     */   
/*     */   public String getCancelRequestTypeURI() {
/*  68 */     return this.nsURI + "/Cancel";
/*     */   }
/*     */   
/*     */   public String getValidateRequestTypeURI() {
/*  72 */     return this.nsURI + "/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateStatuesTokenType() {
/*  76 */     return this.nsURI + "/RSTR/Status";
/*     */   }
/*     */   
/*     */   public String getKeyExchangeRequestTypeURI() {
/*  80 */     return this.nsURI + "/KET";
/*     */   }
/*     */   
/*     */   public String getPublicKeyTypeURI() {
/*  84 */     return this.nsURI + "/PublicKey";
/*     */   }
/*     */   
/*     */   public String getSymmetricKeyTypeURI() {
/*  88 */     return this.nsURI + "/SymmetricKey";
/*     */   }
/*     */   
/*     */   public String getBearerKeyTypeURI() {
/*  92 */     return this.nsURI + "/Bearer";
/*     */   }
/*     */   
/*     */   public String getIssueRequestAction() {
/*  96 */     return this.nsURI + "/RST/Issue";
/*     */   }
/*     */   
/*     */   public String getIssueResponseAction() {
/* 100 */     return this.nsURI + "/RSTR/Issue";
/*     */   }
/*     */   
/*     */   public String getIssueFinalResoponseAction() {
/* 104 */     return this.nsURI + "/RSTRC/IssueFinal";
/*     */   }
/*     */   
/*     */   public String getRenewRequestAction() {
/* 108 */     return this.nsURI + "/RST/Renew";
/*     */   }
/*     */   
/*     */   public String getRenewResponseAction() {
/* 112 */     return this.nsURI + "/RSTR/Renew";
/*     */   }
/*     */   
/*     */   public String getRenewFinalResoponseAction() {
/* 116 */     return this.nsURI + "/RSTRC/RenewFinal";
/*     */   }
/*     */   public String getCancelRequestAction() {
/* 119 */     return this.nsURI + "/RST/Cancel";
/*     */   }
/*     */   
/*     */   public String getCancelResponseAction() {
/* 123 */     return this.nsURI + "/RSTR/Cancel";
/*     */   }
/*     */   
/*     */   public String getCancelFinalResoponseAction() {
/* 127 */     return this.nsURI + "/RSTRC/CancelFinal";
/*     */   }
/*     */   
/*     */   public String getValidateRequestAction() {
/* 131 */     return this.nsURI + "/RST/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateResponseAction() {
/* 135 */     return this.nsURI + "/RSTR/Validate";
/*     */   }
/*     */   
/*     */   public String getValidateFinalResoponseAction() {
/* 139 */     return this.nsURI + "/RSTR/ValidateFinal";
/*     */   }
/*     */   
/*     */   public String getCKPSHA1algorithmURI() {
/* 143 */     return this.nsURI + "/CK/PSHA1";
/*     */   }
/*     */   
/*     */   public String getCKHASHalgorithmURI() {
/* 147 */     return this.nsURI + "/CK/HASH";
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeyBinarySecretTypeURI() {
/* 151 */     return this.nsURI + "/AsymmetricKey";
/*     */   }
/*     */   
/*     */   public String getNonceBinarySecretTypeURI() {
/* 155 */     return this.nsURI + "/Nonce";
/*     */   }
/*     */   
/*     */   public String getValidStatusCodeURI() {
/* 159 */     return this.nsURI + "/status/valid";
/*     */   }
/*     */   
/*     */   public String getInvalidStatusCodeURI() {
/* 163 */     return this.nsURI + "/status/invalid";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\WSTrustVersion13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */