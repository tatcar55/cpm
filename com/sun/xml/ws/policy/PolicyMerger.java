/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolicyMerger
/*     */ {
/*  55 */   private static final PolicyMerger merger = new PolicyMerger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyMerger getMerger() {
/*  70 */     return merger;
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
/*     */   
/*     */   public Policy merge(Collection<Policy> policies) {
/*  86 */     if (policies == null || policies.isEmpty())
/*  87 */       return null; 
/*  88 */     if (policies.size() == 1) {
/*  89 */       return policies.iterator().next();
/*     */     }
/*     */     
/*  92 */     Collection<Collection<AssertionSet>> alternativeSets = new LinkedList<Collection<AssertionSet>>();
/*  93 */     StringBuilder id = new StringBuilder();
/*  94 */     NamespaceVersion mergedVersion = ((Policy)policies.iterator().next()).getNamespaceVersion();
/*  95 */     for (Policy policy : policies) {
/*  96 */       alternativeSets.add(policy.getContent());
/*  97 */       if (mergedVersion.compareTo((Enum)policy.getNamespaceVersion()) < 0) {
/*  98 */         mergedVersion = policy.getNamespaceVersion();
/*     */       }
/* 100 */       String policyId = policy.getId();
/* 101 */       if (policyId != null) {
/* 102 */         if (id.length() > 0) {
/* 103 */           id.append('-');
/*     */         }
/* 105 */         id.append(policyId);
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     Collection<Collection<AssertionSet>> combinedAlternatives = PolicyUtils.Collections.combine(null, alternativeSets, false);
/*     */     
/* 111 */     if (combinedAlternatives == null || combinedAlternatives.isEmpty()) {
/* 112 */       return Policy.createNullPolicy(mergedVersion, null, (id.length() == 0) ? null : id.toString());
/*     */     }
/* 114 */     Collection<AssertionSet> mergedSetList = new ArrayList<AssertionSet>(combinedAlternatives.size());
/* 115 */     for (Collection<AssertionSet> toBeMerged : combinedAlternatives) {
/* 116 */       mergedSetList.add(AssertionSet.createMergedAssertionSet(toBeMerged));
/*     */     }
/* 118 */     return Policy.createPolicy(mergedVersion, null, (id.length() == 0) ? null : id.toString(), mergedSetList);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMerger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */