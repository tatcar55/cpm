/*     */ package com.sun.xml.ws.tx.at.policy;
/*     */ 
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AtPolicyCreator
/*     */ {
/*     */   private static void registerCombination(WsatNamespace version, Transactional.TransactionFlowType flowType, EjbTransactionType ejbTat, WsatAssertionBase... assertions) {
/*  66 */     if (assertions == null) {
/*  67 */       assertions = new WsatAssertionBase[0];
/*     */     }
/*  69 */     ((Map)((Map)SUPPORTED_COMBINATIONS.get(version)).get(flowType)).put(ejbTat, Arrays.asList(assertions));
/*     */   }
/*     */ 
/*     */   
/*  73 */   private static final Map<WsatNamespace, Map<Transactional.TransactionFlowType, Map<EjbTransactionType, Collection<WsatAssertionBase>>>> SUPPORTED_COMBINATIONS = new EnumMap<WsatNamespace, Map<Transactional.TransactionFlowType, Map<EjbTransactionType, Collection<WsatAssertionBase>>>>(WsatNamespace.class); static {
/*  74 */     for (WsatNamespace ns : WsatNamespace.values()) {
/*  75 */       Map<Transactional.TransactionFlowType, Map<EjbTransactionType, Collection<WsatAssertionBase>>> nsMap = new EnumMap<Transactional.TransactionFlowType, Map<EjbTransactionType, Collection<WsatAssertionBase>>>(Transactional.TransactionFlowType.class);
/*  76 */       for (Transactional.TransactionFlowType flowType : Transactional.TransactionFlowType.values()) {
/*  77 */         nsMap.put(flowType, new EnumMap<EjbTransactionType, Collection<WsatAssertionBase>>(EjbTransactionType.class));
/*     */       }
/*  79 */       SUPPORTED_COMBINATIONS.put(ns, nsMap);
/*     */     } 
/*     */ 
/*     */     
/*  83 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, false) });
/*  84 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.MANDATORY, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, false) });
/*  85 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.REQUIRED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, false) });
/*     */     
/*  87 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, true) });
/*  88 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.SUPPORTS, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, true) });
/*  89 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.REQUIRED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200410, true), new AtAlwaysCapability(false) });
/*     */     
/*  91 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.NEVER, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[0]);
/*  92 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.NEVER, EjbTransactionType.NEVER, new WsatAssertionBase[0]);
/*  93 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.NEVER, EjbTransactionType.REQUIRES_NEW, new WsatAssertionBase[] { new AtAlwaysCapability(false) });
/*  94 */     registerCombination(WsatNamespace.WSAT200410, Transactional.TransactionFlowType.NEVER, EjbTransactionType.REQUIRED, new WsatAssertionBase[] { new AtAlwaysCapability(false) });
/*     */ 
/*     */     
/*  97 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, false) });
/*  98 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.MANDATORY, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, false) });
/*  99 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.MANDATORY, EjbTransactionType.REQUIRED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, false) });
/*     */     
/* 101 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, true) });
/* 102 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.SUPPORTS, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, true) });
/* 103 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.SUPPORTS, EjbTransactionType.REQUIRED, new WsatAssertionBase[] { new AtAssertion(WsatNamespace.WSAT200606, true) });
/*     */     
/* 105 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.NEVER, EjbTransactionType.NOT_DEFINED, new WsatAssertionBase[0]);
/* 106 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.NEVER, EjbTransactionType.NEVER, new WsatAssertionBase[0]);
/* 107 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.NEVER, EjbTransactionType.REQUIRES_NEW, new WsatAssertionBase[0]);
/* 108 */     registerCombination(WsatNamespace.WSAT200606, Transactional.TransactionFlowType.NEVER, EjbTransactionType.NOT_SUPPORTED, new WsatAssertionBase[0]);
/*     */   }
/*     */   
/*     */   public static Policy createPolicy(String policyId, WsatNamespace version, Transactional.TransactionFlowType wsatFlowType, EjbTransactionType ejbTat) {
/* 112 */     if (wsatFlowType == null || ejbTat == null) {
/* 113 */       return null;
/*     */     }
/*     */     
/* 116 */     Collection<WsatAssertionBase> assertions = (Collection<WsatAssertionBase>)((Map)((Map)SUPPORTED_COMBINATIONS.get(version)).get(wsatFlowType)).get(ejbTat);
/* 117 */     if (assertions == null) {
/* 118 */       throw new IllegalArgumentException(String.format("Unsupported combinantion: WS-AT namespace: [ %s ], WS-AT flow type: [ %s ], EJB transaction attribute: [ %s ]", new Object[] { version, wsatFlowType, ejbTat }));
/*     */     }
/* 120 */     if (assertions.isEmpty()) {
/* 121 */       return null;
/*     */     }
/*     */     
/* 124 */     List<AssertionSet> assertionSets = new ArrayList<AssertionSet>(1);
/* 125 */     assertionSets.add(AssertionSet.createAssertionSet(assertions));
/*     */     
/* 127 */     return Policy.createPolicy("", policyId, assertionSets);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\AtPolicyCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */