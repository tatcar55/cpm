/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
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
/*     */ class NormalizedModelGenerator
/*     */   extends PolicyModelGenerator
/*     */ {
/*  58 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(NormalizedModelGenerator.class);
/*     */   
/*     */   private final PolicyModelGenerator.PolicySourceModelCreator sourceModelCreator;
/*     */ 
/*     */   
/*     */   NormalizedModelGenerator(PolicyModelGenerator.PolicySourceModelCreator sourceModelCreator) {
/*  64 */     this.sourceModelCreator = sourceModelCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicySourceModel translate(Policy policy) throws PolicyException {
/*  69 */     LOGGER.entering(new Object[] { policy });
/*     */     
/*  71 */     PolicySourceModel model = null;
/*     */     
/*  73 */     if (policy == null) {
/*  74 */       LOGGER.fine(LocalizationMessages.WSP_0047_POLICY_IS_NULL_RETURNING());
/*     */     } else {
/*  76 */       model = this.sourceModelCreator.create(policy);
/*  77 */       ModelNode rootNode = model.getRootNode();
/*  78 */       ModelNode exactlyOneNode = rootNode.createChildExactlyOneNode();
/*  79 */       for (AssertionSet set : policy) {
/*  80 */         ModelNode alternativeNode = exactlyOneNode.createChildAllNode();
/*  81 */         for (PolicyAssertion assertion : set) {
/*  82 */           AssertionData data = AssertionData.createAssertionData(assertion.getName(), assertion.getValue(), assertion.getAttributes(), assertion.isOptional(), assertion.isIgnorable());
/*  83 */           ModelNode assertionNode = alternativeNode.createChildAssertionNode(data);
/*  84 */           if (assertion.hasNestedPolicy()) {
/*  85 */             translate(assertionNode, assertion.getNestedPolicy());
/*     */           }
/*  87 */           if (assertion.hasParameters()) {
/*  88 */             translate(assertionNode, assertion.getParametersIterator());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     LOGGER.exiting(model);
/*  95 */     return model;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelNode translate(ModelNode parentAssertion, NestedPolicy policy) {
/* 100 */     ModelNode nestedPolicyRoot = parentAssertion.createChildPolicyNode();
/* 101 */     ModelNode exactlyOneNode = nestedPolicyRoot.createChildExactlyOneNode();
/* 102 */     AssertionSet set = policy.getAssertionSet();
/* 103 */     ModelNode alternativeNode = exactlyOneNode.createChildAllNode();
/* 104 */     translate(alternativeNode, set);
/* 105 */     return nestedPolicyRoot;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\NormalizedModelGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */