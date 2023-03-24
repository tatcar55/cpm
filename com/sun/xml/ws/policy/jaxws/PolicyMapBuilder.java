/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.ws.policy.PolicyMapMutator;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PolicyMapBuilder
/*     */ {
/*  65 */   private List<BuilderHandler> policyBuilders = new LinkedList<BuilderHandler>();
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
/*     */   void registerHandler(BuilderHandler builder) {
/*  80 */     if (null != builder) {
/*  81 */       this.policyBuilders.add(builder);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PolicyMap getPolicyMap(PolicyMapMutator... externalMutators) throws PolicyException {
/*  91 */     return getNewPolicyMap(externalMutators);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyMap getNewPolicyMap(PolicyMapMutator... externalMutators) throws PolicyException {
/* 101 */     HashSet<PolicyMapMutator> mutators = new HashSet<PolicyMapMutator>();
/* 102 */     PolicyMapExtender myExtender = PolicyMapExtender.createPolicyMapExtender();
/* 103 */     mutators.add(myExtender);
/* 104 */     if (null != externalMutators) {
/* 105 */       mutators.addAll(Arrays.asList(externalMutators));
/*     */     }
/* 107 */     PolicyMap policyMap = PolicyMap.createPolicyMap(mutators);
/* 108 */     for (BuilderHandler builder : this.policyBuilders) {
/* 109 */       builder.populate(myExtender);
/*     */     }
/* 111 */     return policyMap;
/*     */   }
/*     */   
/*     */   void unregisterAll() {
/* 115 */     this.policyBuilders = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\PolicyMapBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */