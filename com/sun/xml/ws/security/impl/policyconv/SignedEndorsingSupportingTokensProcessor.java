/*    */ package com.sun.xml.ws.security.impl.policyconv;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*    */ import com.sun.xml.ws.security.policy.Binding;
/*    */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SignedEndorsingSupportingTokensProcessor
/*    */   extends EndorsingSupportingTokensProcessor
/*    */ {
/*    */   public SignedEndorsingSupportingTokensProcessor(SupportingTokens st, TokenProcessor tokenProcessor, Binding binding, XWSSPolicyContainer container, SignaturePolicy sp, EncryptionPolicy ep, PolicyID pid) {
/* 65 */     super(st, tokenProcessor, binding, container, sp, ep, pid);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addToPrimarySignature(WSSPolicy policy, Token token) throws PolicyException {
/* 70 */     String includeToken = token.getIncludeToken();
/* 71 */     SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion((PolicyAssertion)token);
/* 72 */     SignatureTarget target = null;
/* 73 */     if (includeToken.endsWith("Never") && PolicyUtil.isX509Token((PolicyAssertion)token, spVersion)) {
/* 74 */       String uid = this.pid.generateID();
/* 75 */       ((AuthenticationTokenPolicy.X509CertificateBinding)policy).setSTRID(uid);
/* 76 */       target = this.stc.newURISignatureTargetForSSToken(uid);
/*    */       
/* 78 */       target.isITNever(true);
/*    */     } else {
/* 80 */       target = this.stc.newURISignatureTargetForSSToken(policy.getUUID());
/*    */     } 
/* 82 */     SecurityPolicyUtil.setName((Target)target, policy);
/*    */     
/* 84 */     if ((!PolicyUtil.isUsernameToken((PolicyAssertion)token, spVersion) && !spVersion.includeTokenAlways.equals(includeToken) && !spVersion.includeTokenAlwaysToRecipient.equals(includeToken)) || PolicyUtil.isSamlToken((PolicyAssertion)token, spVersion) || PolicyUtil.isIssuedToken((PolicyAssertion)token, spVersion)) {
/*    */ 
/*    */ 
/*    */       
/* 88 */       this.stc.addSTRTransform(target);
/* 89 */       target.setPolicyQName(getQName(policy));
/*    */     } else {
/* 91 */       this.stc.addTransform(target);
/*    */     } 
/* 93 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.signaturePolicy.getFeatureBinding();
/* 94 */     spFB.addTargetBinding(target);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SignedEndorsingSupportingTokensProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */