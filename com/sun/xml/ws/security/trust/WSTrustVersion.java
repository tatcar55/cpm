/*     */ package com.sun.xml.ws.security.trust;
/*     */ 
/*     */ import com.sun.xml.ws.security.trust.impl.WSTrustVersion10;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.WSTrustVersion13;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSTrustVersion
/*     */ {
/*  52 */   public static final WSTrustVersion WS_TRUST_10 = (WSTrustVersion)new WSTrustVersion10();
/*     */   
/*  54 */   public static final WSTrustVersion WS_TRUST_13 = (WSTrustVersion)new WSTrustVersion13();
/*     */   public static final String WS_TRUST_10_NS_URI = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*     */   public static final String WS_TRUST_13_NS_URI = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
/*     */   
/*     */   public static WSTrustVersion getInstance(String nsURI) {
/*  59 */     if (nsURI.equals(WS_TRUST_13.getNamespaceURI())) {
/*  60 */       return WS_TRUST_13;
/*     */     }
/*     */     
/*  63 */     return WS_TRUST_10;
/*     */   }
/*     */   
/*     */   public abstract String getNamespaceURI();
/*     */   
/*     */   public abstract String getIssueRequestTypeURI();
/*     */   
/*     */   public abstract String getRenewRequestTypeURI();
/*     */   
/*     */   public abstract String getCancelRequestTypeURI();
/*     */   
/*     */   public abstract String getValidateRequestTypeURI();
/*     */   
/*     */   public abstract String getValidateStatuesTokenType();
/*     */   
/*     */   public abstract String getKeyExchangeRequestTypeURI();
/*     */   
/*     */   public abstract String getPublicKeyTypeURI();
/*     */   
/*     */   public abstract String getSymmetricKeyTypeURI();
/*     */   
/*     */   public abstract String getBearerKeyTypeURI();
/*     */   
/*     */   public abstract String getIssueRequestAction();
/*     */   
/*     */   public abstract String getIssueResponseAction();
/*     */   
/*     */   public abstract String getIssueFinalResoponseAction();
/*     */   
/*     */   public abstract String getRenewRequestAction();
/*     */   
/*     */   public abstract String getRenewResponseAction();
/*     */   
/*     */   public abstract String getRenewFinalResoponseAction();
/*     */   
/*     */   public abstract String getCancelRequestAction();
/*     */   
/*     */   public abstract String getCancelResponseAction();
/*     */   
/*     */   public abstract String getCancelFinalResoponseAction();
/*     */   
/*     */   public abstract String getValidateRequestAction();
/*     */   
/*     */   public abstract String getValidateResponseAction();
/*     */   
/*     */   public abstract String getValidateFinalResoponseAction();
/*     */   
/*     */   public String getFinalResponseAction(String reqAction) {
/* 111 */     if (reqAction.equals(getIssueRequestAction())) {
/* 112 */       return getIssueFinalResoponseAction();
/*     */     }
/*     */     
/* 115 */     if (reqAction.equals(getRenewRequestAction())) {
/* 116 */       return getRenewFinalResoponseAction();
/*     */     }
/*     */     
/* 119 */     if (reqAction.equals(getCancelRequestAction())) {
/* 120 */       return getCancelFinalResoponseAction();
/*     */     }
/*     */     
/* 123 */     if (reqAction.equals(getValidateRequestAction())) {
/* 124 */       return getValidateFinalResoponseAction();
/*     */     }
/*     */     
/* 127 */     return null;
/*     */   }
/*     */   
/*     */   public abstract String getCKPSHA1algorithmURI();
/*     */   
/*     */   public abstract String getCKHASHalgorithmURI();
/*     */   
/*     */   public abstract String getAsymmetricKeyBinarySecretTypeURI();
/*     */   
/*     */   public abstract String getNonceBinarySecretTypeURI();
/*     */   
/*     */   public abstract String getValidStatusCodeURI();
/*     */   
/*     */   public abstract String getInvalidStatusCodeURI();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */