/*    */ package com.sun.xml.ws.security.impl.policyconv;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*    */ import com.sun.xml.ws.security.policy.Binding;
/*    */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*    */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*    */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*    */ import com.sun.xml.ws.security.policy.Token;
/*    */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*    */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*    */ import com.sun.xml.wss.impl.policy.mls.Target;
/*    */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
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
/*    */ public class SignedSupportingTokensProcessor
/*    */   extends SupportingTokensProcessor
/*    */ {
/*    */   public SignedSupportingTokensProcessor(SignedSupportingTokens st, TokenProcessor tokenProcessor, Binding binding, XWSSPolicyContainer container, SignaturePolicy sp, EncryptionPolicy ep, PolicyID pid) {
/* 63 */     super((SupportingTokens)st, tokenProcessor, binding, container, sp, ep, pid);
/*    */   }
/*    */   
/*    */   protected void addToPrimarySignature(WSSPolicy policy, Token token) throws PolicyException {
/* 67 */     String includeToken = token.getIncludeToken();
/* 68 */     SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion((PolicyAssertion)token);
/* 69 */     SignatureTarget target = null;
/* 70 */     if (includeToken.endsWith("Never") && PolicyUtil.isX509Token((PolicyAssertion)token, spVersion)) {
/* 71 */       String uid = this.pid.generateID();
/* 72 */       ((AuthenticationTokenPolicy.X509CertificateBinding)policy).setSTRID(uid);
/* 73 */       target = this.stc.newURISignatureTargetForSSToken(uid);
/*    */       
/* 75 */       target.isITNever(true);
/*    */     } else {
/* 77 */       target = this.stc.newURISignatureTargetForSSToken(policy.getUUID());
/*    */     } 
/* 79 */     SecurityPolicyUtil.setName((Target)target, policy);
/*    */     
/* 81 */     if ((!PolicyUtil.isUsernameToken((PolicyAssertion)token, spVersion) && !spVersion.includeTokenAlways.equals(includeToken) && !spVersion.includeTokenAlwaysToRecipient.equals(includeToken)) || PolicyUtil.isSamlToken((PolicyAssertion)token, spVersion) || PolicyUtil.isIssuedToken((PolicyAssertion)token, spVersion)) {
/*    */ 
/*    */ 
/*    */       
/* 85 */       this.stc.addSTRTransform(target);
/* 86 */       target.setPolicyQName(getQName(policy));
/*    */     } else {
/* 88 */       this.stc.addTransform(target);
/*    */     } 
/* 90 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.signaturePolicy.getFeatureBinding();
/* 91 */     spFB.addTargetBinding(target);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SignedSupportingTokensProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */