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
/*     */ final class PolicyScope
/*     */ {
/*  57 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyScope.class);
/*     */   
/*  59 */   private final List<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*     */   
/*     */   PolicyScope(List<PolicySubject> initialSubjects) {
/*  62 */     if (initialSubjects != null && !initialSubjects.isEmpty()) {
/*  63 */       this.subjects.addAll(initialSubjects);
/*     */     }
/*     */   }
/*     */   
/*     */   void attach(PolicySubject subject) {
/*  68 */     if (subject == null) {
/*  69 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0020_SUBJECT_PARAM_MUST_NOT_BE_NULL()));
/*     */     }
/*     */     
/*  72 */     this.subjects.add(subject);
/*     */   }
/*     */   
/*     */   void dettachAllSubjects() {
/*  76 */     this.subjects.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Policy getEffectivePolicy(PolicyMerger merger) throws PolicyException {
/*  85 */     LinkedList<Policy> policies = new LinkedList<Policy>();
/*  86 */     for (PolicySubject subject : this.subjects) {
/*  87 */       policies.add(subject.getEffectivePolicy(merger));
/*     */     }
/*  89 */     return merger.merge(policies);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Collection<PolicySubject> getPolicySubjects() {
/*  98 */     return this.subjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return toString(0, new StringBuffer()).toString();
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
/* 118 */     String indent = PolicyUtils.Text.createIndent(indentLevel);
/*     */     
/* 120 */     buffer.append(indent).append("policy scope {").append(PolicyUtils.Text.NEW_LINE);
/* 121 */     for (PolicySubject policySubject : this.subjects) {
/* 122 */       policySubject.toString(indentLevel + 1, buffer).append(PolicyUtils.Text.NEW_LINE);
/*     */     }
/* 124 */     buffer.append(indent).append('}');
/*     */     
/* 126 */     return buffer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyScope.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */