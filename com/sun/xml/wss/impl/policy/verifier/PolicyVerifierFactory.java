/*    */ package com.sun.xml.wss.impl.policy.verifier;
/*    */ 
/*    */ import com.sun.xml.ws.security.opt.impl.incoming.TargetResolverImpl;
/*    */ import com.sun.xml.wss.ProcessingContext;
/*    */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*    */ import com.sun.xml.wss.impl.policy.spi.PolicyVerifier;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolicyVerifierFactory
/*    */ {
/*    */   public static PolicyVerifier createVerifier(SecurityPolicy servicePolicy, ProcessingContext ctx) {
/* 62 */     TargetResolverImpl targetResolverImpl = new TargetResolverImpl(ctx);
/* 63 */     if (servicePolicy instanceof com.sun.xml.wss.impl.policy.mls.MessagePolicy)
/* 64 */       return new MessagePolicyVerifier(ctx, (TargetResolver)targetResolverImpl); 
/* 65 */     if (servicePolicy instanceof com.sun.xml.wss.impl.policy.PolicyAlternatives) {
/* 66 */       return new PolicyAlternativesVerifier(ctx, (TargetResolver)targetResolverImpl);
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\PolicyVerifierFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */