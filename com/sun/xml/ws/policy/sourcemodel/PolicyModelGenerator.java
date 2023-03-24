/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PolicyModelGenerator
/*     */ {
/*  64 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyModelGenerator.class);
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
/*     */   public static PolicyModelGenerator getGenerator() {
/*  79 */     return getNormalizedGenerator(new PolicySourceModelCreator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static PolicyModelGenerator getCompactGenerator(PolicySourceModelCreator creator) {
/*  90 */     return new CompactModelGenerator(creator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static PolicyModelGenerator getNormalizedGenerator(PolicySourceModelCreator creator) {
/* 101 */     return new NormalizedModelGenerator(creator);
/*     */   }
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
/*     */   public abstract PolicySourceModel translate(Policy paramPolicy) throws PolicyException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ModelNode translate(ModelNode paramModelNode, NestedPolicy paramNestedPolicy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void translate(ModelNode node, AssertionSet assertions) {
/* 134 */     for (PolicyAssertion assertion : assertions) {
/* 135 */       AssertionData data = AssertionData.createAssertionData(assertion.getName(), assertion.getValue(), assertion.getAttributes(), assertion.isOptional(), assertion.isIgnorable());
/* 136 */       ModelNode assertionNode = node.createChildAssertionNode(data);
/* 137 */       if (assertion.hasNestedPolicy()) {
/* 138 */         translate(assertionNode, assertion.getNestedPolicy());
/*     */       }
/* 140 */       if (assertion.hasParameters()) {
/* 141 */         translate(assertionNode, assertion.getParametersIterator());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void translate(ModelNode assertionNode, Iterator<PolicyAssertion> assertionParametersIterator) {
/* 153 */     while (assertionParametersIterator.hasNext()) {
/* 154 */       PolicyAssertion assertionParameter = assertionParametersIterator.next();
/* 155 */       AssertionData data = AssertionData.createAssertionParameterData(assertionParameter.getName(), assertionParameter.getValue(), assertionParameter.getAttributes());
/* 156 */       ModelNode assertionParameterNode = assertionNode.createChildAssertionParameterNode(data);
/* 157 */       if (assertionParameter.hasNestedPolicy()) {
/* 158 */         throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0005_UNEXPECTED_POLICY_ELEMENT_FOUND_IN_ASSERTION_PARAM(assertionParameter)));
/*     */       }
/* 160 */       if (assertionParameter.hasNestedAssertions()) {
/* 161 */         translate(assertionParameterNode, assertionParameter.getNestedAssertionsIterator());
/*     */       }
/*     */     } 
/*     */   }
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
/*     */   protected static class PolicySourceModelCreator
/*     */   {
/*     */     protected PolicySourceModel create(Policy policy) {
/* 180 */       return PolicySourceModel.createPolicySourceModel(policy.getNamespaceVersion(), policy.getId(), policy.getName());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\PolicyModelGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */