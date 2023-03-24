/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolicyIntersector
/*     */ {
/*     */   enum CompatibilityMode
/*     */   {
/*  59 */     STRICT,
/*  60 */     LAX;
/*     */   }
/*     */   
/*  63 */   private static final PolicyIntersector STRICT_INTERSECTOR = new PolicyIntersector(CompatibilityMode.STRICT);
/*  64 */   private static final PolicyIntersector LAX_INTERSECTOR = new PolicyIntersector(CompatibilityMode.LAX);
/*  65 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyIntersector.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private CompatibilityMode mode;
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyIntersector(CompatibilityMode intersectionMode) {
/*  74 */     this.mode = intersectionMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyIntersector createStrictPolicyIntersector() {
/*  83 */     return STRICT_INTERSECTOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyIntersector createLaxPolicyIntersector() {
/*  92 */     return LAX_INTERSECTOR;
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
/*     */   public Policy intersect(Policy... policies) {
/* 106 */     if (policies == null || policies.length == 0)
/* 107 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0056_NEITHER_NULL_NOR_EMPTY_POLICY_COLLECTION_EXPECTED())); 
/* 108 */     if (policies.length == 1) {
/* 109 */       return policies[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 114 */     boolean found = false;
/* 115 */     boolean allPoliciesEmpty = true;
/* 116 */     NamespaceVersion latestVersion = null;
/* 117 */     for (Policy tested : policies) {
/* 118 */       if (tested.isEmpty()) {
/* 119 */         found = true;
/*     */       } else {
/* 121 */         if (tested.isNull()) {
/* 122 */           found = true;
/*     */         }
/* 124 */         allPoliciesEmpty = false;
/*     */       } 
/* 126 */       if (latestVersion == null) {
/* 127 */         latestVersion = tested.getNamespaceVersion();
/* 128 */       } else if (latestVersion.compareTo((Enum)tested.getNamespaceVersion()) < 0) {
/* 129 */         latestVersion = tested.getNamespaceVersion();
/*     */       } 
/*     */       
/* 132 */       if (found && !allPoliciesEmpty) {
/* 133 */         return Policy.createNullPolicy(latestVersion, null, null);
/*     */       }
/*     */     } 
/* 136 */     latestVersion = (latestVersion != null) ? latestVersion : NamespaceVersion.getLatestVersion();
/* 137 */     if (allPoliciesEmpty) {
/* 138 */       return Policy.createEmptyPolicy(latestVersion, null, null);
/*     */     }
/*     */ 
/*     */     
/* 142 */     List<AssertionSet> finalAlternatives = new LinkedList<AssertionSet>(policies[0].getContent());
/* 143 */     Queue<AssertionSet> testedAlternatives = new LinkedList<AssertionSet>();
/* 144 */     List<AssertionSet> alternativesToMerge = new ArrayList<AssertionSet>(2);
/* 145 */     for (int i = 1; i < policies.length; i++) {
/* 146 */       Collection<AssertionSet> currentAlternatives = policies[i].getContent();
/*     */       
/* 148 */       testedAlternatives.clear();
/* 149 */       testedAlternatives.addAll(finalAlternatives);
/* 150 */       finalAlternatives.clear();
/*     */       
/*     */       AssertionSet testedAlternative;
/* 153 */       while ((testedAlternative = testedAlternatives.poll()) != null) {
/* 154 */         for (AssertionSet currentAlternative : currentAlternatives) {
/* 155 */           if (testedAlternative.isCompatibleWith(currentAlternative, this.mode)) {
/* 156 */             alternativesToMerge.add(testedAlternative);
/* 157 */             alternativesToMerge.add(currentAlternative);
/* 158 */             finalAlternatives.add(AssertionSet.createMergedAssertionSet(alternativesToMerge));
/* 159 */             alternativesToMerge.clear();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 165 */     return Policy.createPolicy(latestVersion, null, null, finalAlternatives);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyIntersector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */