/*    */ package com.sun.xml.ws.security.impl.policyconv;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyException;
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
/*    */ public class EndorsingSupportingTokensProcessor
/*    */   extends SupportingTokensProcessor
/*    */ {
/* 59 */   protected SignaturePolicy primarySP = null;
/*    */ 
/*    */   
/*    */   public EndorsingSupportingTokensProcessor(SupportingTokens st, TokenProcessor tokenProcessor, Binding binding, XWSSPolicyContainer container, SignaturePolicy sp, EncryptionPolicy ep, PolicyID pid) {
/* 63 */     super(st, tokenProcessor, binding, container, sp, ep, pid);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addToPrimarySignature(WSSPolicy policy, Token token) throws PolicyException {}
/*    */ 
/*    */   
/*    */   protected void collectSignaturePolicies(Token token) throws PolicyException {
/* 71 */     createSupportingSignature(token);
/*    */   }
/*    */   
/*    */   protected void endorseSignature(SignaturePolicy sp) {
/* 75 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/* 76 */     SignatureTarget sigTarget = this.stc.newURISignatureTarget(this.signaturePolicy.getUUID());
/* 77 */     this.stc.addTransform(sigTarget);
/* 78 */     SecurityPolicyUtil.setName((Target)sigTarget, (WSSPolicy)this.signaturePolicy);
/* 79 */     spFB.addTargetBinding(sigTarget);
/* 80 */     spFB.isEndorsingSignature(true);
/*    */   }
/*    */   
/*    */   protected void correctSAMLBinding(WSSPolicy policy) {
/* 84 */     ((AuthenticationTokenPolicy.SAMLAssertionBinding)policy).setAssertionType("HOK");
/*    */   }
/*    */   
/*    */   protected void encryptToken(Token token, SecurityPolicyVersion spVersion) throws PolicyException {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\EndorsingSupportingTokensProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */