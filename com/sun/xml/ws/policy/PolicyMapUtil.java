/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.subject.PolicyMapKeyConverter;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolicyMapUtil
/*     */ {
/*  61 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapUtil.class);
/*     */   
/*  63 */   private static final PolicyMerger MERGER = PolicyMerger.getMerger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void rejectAlternatives(PolicyMap map) throws PolicyException {
/*  83 */     for (Policy policy : map) {
/*  84 */       if (policy.getNumberOfAssertionSets() > 1) {
/*  85 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0035_RECONFIGURE_ALTERNATIVES(policy.getIdOrName())));
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
/*     */   
/*     */   public static void insertPolicies(PolicyMap policyMap, Collection<PolicySubject> policySubjects, QName serviceName, QName portName) throws PolicyException {
/* 103 */     LOGGER.entering(new Object[] { policyMap, policySubjects, serviceName, portName });
/*     */     
/* 105 */     HashMap<WsdlBindingSubject, Collection<Policy>> subjectToPolicies = new HashMap<WsdlBindingSubject, Collection<Policy>>();
/* 106 */     for (PolicySubject subject : policySubjects) {
/* 107 */       Object actualSubject = subject.getSubject();
/* 108 */       if (actualSubject instanceof WsdlBindingSubject) {
/* 109 */         WsdlBindingSubject wsdlSubject = (WsdlBindingSubject)actualSubject;
/* 110 */         Collection<Policy> subjectPolicies = new LinkedList<Policy>();
/* 111 */         subjectPolicies.add(subject.getEffectivePolicy(MERGER));
/* 112 */         Collection<Policy> existingPolicies = subjectToPolicies.put(wsdlSubject, subjectPolicies);
/* 113 */         if (existingPolicies != null) {
/* 114 */           subjectPolicies.addAll(existingPolicies);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     PolicyMapKeyConverter converter = new PolicyMapKeyConverter(serviceName, portName);
/* 120 */     for (WsdlBindingSubject wsdlSubject : subjectToPolicies.keySet()) {
/* 121 */       PolicySubject newSubject = new PolicySubject(wsdlSubject, subjectToPolicies.get(wsdlSubject));
/* 122 */       PolicyMapKey mapKey = converter.getPolicyMapKey(wsdlSubject);
/*     */       
/* 124 */       if (wsdlSubject.isBindingSubject()) {
/* 125 */         policyMap.putSubject(PolicyMap.ScopeType.ENDPOINT, mapKey, newSubject); continue;
/*     */       } 
/* 127 */       if (wsdlSubject.isBindingOperationSubject()) {
/* 128 */         policyMap.putSubject(PolicyMap.ScopeType.OPERATION, mapKey, newSubject); continue;
/*     */       } 
/* 130 */       if (wsdlSubject.isBindingMessageSubject()) {
/* 131 */         switch (wsdlSubject.getMessageType()) {
/*     */           case INPUT:
/* 133 */             policyMap.putSubject(PolicyMap.ScopeType.INPUT_MESSAGE, mapKey, newSubject);
/*     */           
/*     */           case OUTPUT:
/* 136 */             policyMap.putSubject(PolicyMap.ScopeType.OUTPUT_MESSAGE, mapKey, newSubject);
/*     */           
/*     */           case FAULT:
/* 139 */             policyMap.putSubject(PolicyMap.ScopeType.FAULT_MESSAGE, mapKey, newSubject);
/*     */         } 
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/* 145 */     LOGGER.exiting();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMapUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */