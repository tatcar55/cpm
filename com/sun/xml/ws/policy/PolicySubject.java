/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ public final class PolicySubject
/*     */ {
/*  57 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicySubject.class);
/*     */   
/*  59 */   private final List<Policy> policies = new LinkedList<Policy>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Object subject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicySubject(Object subject, Policy policy) throws IllegalArgumentException {
/*  71 */     if (subject == null || policy == null) {
/*  72 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0021_SUBJECT_AND_POLICY_PARAM_MUST_NOT_BE_NULL(subject, policy)));
/*     */     }
/*     */     
/*  75 */     this.subject = subject;
/*  76 */     attach(policy);
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
/*     */   public PolicySubject(Object subject, Collection<Policy> policies) throws IllegalArgumentException {
/*  89 */     if (subject == null || policies == null) {
/*  90 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0062_INPUT_PARAMS_MUST_NOT_BE_NULL()));
/*     */     }
/*     */     
/*  93 */     if (policies.isEmpty()) {
/*  94 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0064_INITIAL_POLICY_COLLECTION_MUST_NOT_BE_EMPTY()));
/*     */     }
/*     */     
/*  97 */     this.subject = subject;
/*  98 */     this.policies.addAll(policies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attach(Policy policy) {
/* 109 */     if (policy == null) {
/* 110 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0038_POLICY_TO_ATTACH_MUST_NOT_BE_NULL()));
/*     */     }
/* 112 */     this.policies.add(policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Policy getEffectivePolicy(PolicyMerger merger) throws PolicyException {
/* 122 */     return merger.merge(this.policies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getSubject() {
/* 133 */     return this.subject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 141 */     return toString(0, new StringBuffer()).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   StringBuffer toString(int indentLevel, StringBuffer buffer) {
/* 152 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/* 153 */     String innerIndent = PolicyUtils.Text.createIndent(indentLevel + 1);
/*     */     
/* 155 */     buffer.append(indent).append("policy subject {").append(PolicyUtils.Text.NEW_LINE);
/* 156 */     buffer.append(innerIndent).append("subject = '").append(this.subject).append('\'').append(PolicyUtils.Text.NEW_LINE);
/* 157 */     for (Policy policy : this.policies) {
/* 158 */       policy.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */     }
/* 160 */     buffer.append(indent).append('}');
/*     */     
/* 162 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicySubject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */