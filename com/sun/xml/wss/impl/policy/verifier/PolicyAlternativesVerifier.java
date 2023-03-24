/*     */ package com.sun.xml.wss.impl.policy.verifier;
/*     */ 
/*     */ import com.sun.xml.ws.security.spi.AlternativeSelector;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.impl.PolicyViolationException;
/*     */ import com.sun.xml.wss.impl.policy.PolicyAlternatives;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.spi.PolicyVerifier;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.logging.Logger;
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
/*     */ public class PolicyAlternativesVerifier
/*     */   implements PolicyVerifier
/*     */ {
/*  61 */   private ProcessingContext ctx = null;
/*     */   
/*  63 */   private static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAlternativesVerifier(ProcessingContext ctx, TargetResolver targetResolver) {
/*  69 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void verifyPolicy(SecurityPolicy recvdPolicy, SecurityPolicy configPolicy) throws PolicyViolationException {
/*  74 */     PolicyAlternatives confPolicies = (PolicyAlternatives)configPolicy;
/*     */     
/*  76 */     List<MessagePolicy> mps = confPolicies.getSecurityPolicy();
/*  77 */     if (mps.size() == 1) {
/*  78 */       PolicyVerifier verifier = PolicyVerifierFactory.createVerifier((SecurityPolicy)mps.get(0), this.ctx);
/*  79 */       verifier.verifyPolicy(recvdPolicy, (SecurityPolicy)mps.get(0));
/*  80 */       if (((MessagePolicy)mps.get(0)).getPolicyAlternativeId() != null) {
/*  81 */         this.ctx.getExtraneousProperties().put("policy-alternative-id", ((MessagePolicy)mps.get(0)).getPolicyAlternativeId());
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  88 */     AlternativeSelector selector = findAlternativesSelector(mps);
/*  89 */     MessagePolicy toVerify = selector.selectAlternative(this.ctx, mps, recvdPolicy);
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (toVerify != null) {
/*  94 */       PolicyVerifier verifier = PolicyVerifierFactory.createVerifier((SecurityPolicy)toVerify, this.ctx);
/*  95 */       verifier.verifyPolicy(recvdPolicy, (SecurityPolicy)toVerify);
/*  96 */       if (toVerify.getPolicyAlternativeId() != null) {
/*  97 */         this.ctx.getExtraneousProperties().put("policy-alternative-id", toVerify.getPolicyAlternativeId());
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 102 */     throw new UnsupportedOperationException("Cannot verify the request against the configured PolicyAlternatives in the WebService");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AlternativeSelector findAlternativesSelector(List<MessagePolicy> alternatives) {
/* 111 */     ServiceLoader<AlternativeSelector> alternativeSelectorLoader = ServiceLoader.load(AlternativeSelector.class);
/*     */ 
/*     */     
/* 114 */     if (alternativeSelectorLoader == null) {
/* 115 */       if (alternatives.size() == 2) {
/* 116 */         return new UsernameOrSAMLAlternativeSelector();
/*     */       }
/* 118 */       throw new UnsupportedOperationException("No AlternativeSelector accepts the policy alternatives combination.");
/*     */     } 
/*     */     
/* 121 */     Iterator<AlternativeSelector> alternativeSelectorIterator = alternativeSelectorLoader.iterator();
/*     */     
/* 123 */     while (alternativeSelectorIterator.hasNext()) {
/* 124 */       AlternativeSelector selector = alternativeSelectorIterator.next();
/* 125 */       if (selector.supportsAlternatives(alternatives)) {
/* 126 */         return selector;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     throw new UnsupportedOperationException("No AlternativeSelector accepts the policy alternatives combination.");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\verifier\PolicyAlternativesVerifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */