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
/*     */ 
/*     */ class CompactModelGenerator
/*     */   extends PolicyModelGenerator
/*     */ {
/*  59 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(CompactModelGenerator.class);
/*     */   
/*     */   private final PolicyModelGenerator.PolicySourceModelCreator sourceModelCreator;
/*     */ 
/*     */   
/*     */   CompactModelGenerator(PolicyModelGenerator.PolicySourceModelCreator sourceModelCreator) {
/*  65 */     this.sourceModelCreator = sourceModelCreator;
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicySourceModel translate(Policy policy) throws PolicyException {
/*  70 */     LOGGER.entering(new Object[] { policy });
/*     */     
/*  72 */     PolicySourceModel model = null;
/*     */     
/*  74 */     if (policy == null) {
/*  75 */       LOGGER.fine(LocalizationMessages.WSP_0047_POLICY_IS_NULL_RETURNING());
/*     */     } else {
/*  77 */       model = this.sourceModelCreator.create(policy);
/*  78 */       ModelNode rootNode = model.getRootNode();
/*  79 */       int numberOfAssertionSets = policy.getNumberOfAssertionSets();
/*  80 */       if (numberOfAssertionSets > 1) {
/*  81 */         rootNode = rootNode.createChildExactlyOneNode();
/*     */       }
/*  83 */       ModelNode alternativeNode = rootNode;
/*  84 */       for (AssertionSet set : policy) {
/*  85 */         if (numberOfAssertionSets > 1) {
/*  86 */           alternativeNode = rootNode.createChildAllNode();
/*     */         }
/*  88 */         for (PolicyAssertion assertion : set) {
/*  89 */           AssertionData data = AssertionData.createAssertionData(assertion.getName(), assertion.getValue(), assertion.getAttributes(), assertion.isOptional(), assertion.isIgnorable());
/*  90 */           ModelNode assertionNode = alternativeNode.createChildAssertionNode(data);
/*  91 */           if (assertion.hasNestedPolicy()) {
/*  92 */             translate(assertionNode, assertion.getNestedPolicy());
/*     */           }
/*  94 */           if (assertion.hasParameters()) {
/*  95 */             translate(assertionNode, assertion.getParametersIterator());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     LOGGER.exiting(model);
/* 102 */     return model;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ModelNode translate(ModelNode parentAssertion, NestedPolicy policy) {
/* 107 */     ModelNode nestedPolicyRoot = parentAssertion.createChildPolicyNode();
/* 108 */     AssertionSet set = policy.getAssertionSet();
/* 109 */     translate(nestedPolicyRoot, set);
/* 110 */     return nestedPolicyRoot;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\CompactModelGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */