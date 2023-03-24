/*    */ package com.sun.xml.ws.security.impl.policyconv;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*    */ import com.sun.xml.ws.security.policy.Binding;
/*    */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*    */ import com.sun.xml.ws.security.policy.SignedEncryptedSupportingTokens;
/*    */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*    */ import com.sun.xml.ws.security.policy.Token;
/*    */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*    */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignedEncryptedSupportingTokensProcessor
/*    */   extends SignedSupportingTokensProcessor
/*    */ {
/*    */   private boolean isIssuedTokenAsEncryptedSupportingToken = false;
/*    */   
/*    */   public SignedEncryptedSupportingTokensProcessor(SignedEncryptedSupportingTokens st, TokenProcessor tokenProcessor, Binding binding, XWSSPolicyContainer container, SignaturePolicy sp, EncryptionPolicy ep, PolicyID pid) {
/* 65 */     super((SignedSupportingTokens)st, tokenProcessor, binding, container, sp, ep, pid);
/*    */   }
/*    */   
/*    */   protected void encryptToken(Token token, SecurityPolicyVersion spVersion) throws PolicyException {
/* 69 */     if (token.getTokenId() != null) {
/* 70 */       EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)this.encryptionPolicy.getFeatureBinding();
/* 71 */       EncryptionTarget et = this.etc.newURIEncryptionTarget(token.getTokenId());
/* 72 */       fb.addTargetBinding(et);
/*    */       
/* 74 */       if (PolicyUtil.isIssuedToken((PolicyAssertion)token, spVersion)) {
/* 75 */         this.isIssuedTokenAsEncryptedSupportingToken = true;
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected boolean isIssuedTokenAsEncryptedSupportingToken() {
/* 81 */     return this.isIssuedTokenAsEncryptedSupportingToken;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SignedEncryptedSupportingTokensProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */