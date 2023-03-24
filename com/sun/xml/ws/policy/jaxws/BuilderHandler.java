/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.api.policy.ModelTranslator;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.resources.PolicyMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ abstract class BuilderHandler
/*     */ {
/*  62 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(BuilderHandler.class);
/*     */   
/*     */   Map<String, PolicySourceModel> policyStore;
/*     */   
/*     */   Collection<String> policyURIs;
/*     */   
/*     */   Object policySubject;
/*     */ 
/*     */   
/*     */   BuilderHandler(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject) {
/*  72 */     this.policyStore = policyStore;
/*  73 */     this.policyURIs = policyURIs;
/*  74 */     this.policySubject = policySubject;
/*     */   }
/*     */   
/*     */   final void populate(PolicyMapExtender policyMapExtender) throws PolicyException {
/*  78 */     if (null == policyMapExtender) {
/*  79 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1006_POLICY_MAP_EXTENDER_CAN_NOT_BE_NULL()));
/*     */     }
/*     */     
/*  82 */     doPopulate(policyMapExtender);
/*     */   }
/*     */   
/*     */   protected abstract void doPopulate(PolicyMapExtender paramPolicyMapExtender) throws PolicyException;
/*     */   
/*     */   final Collection<Policy> getPolicies() throws PolicyException {
/*  88 */     if (null == this.policyURIs) {
/*  89 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1004_POLICY_URIS_CAN_NOT_BE_NULL()));
/*     */     }
/*  91 */     if (null == this.policyStore) {
/*  92 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1010_NO_POLICIES_DEFINED()));
/*     */     }
/*     */     
/*  95 */     Collection<Policy> result = new ArrayList<Policy>(this.policyURIs.size());
/*     */     
/*  97 */     for (String policyURI : this.policyURIs) {
/*  98 */       PolicySourceModel sourceModel = this.policyStore.get(policyURI);
/*  99 */       if (sourceModel == null) {
/* 100 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(PolicyMessages.WSP_1005_POLICY_REFERENCE_DOES_NOT_EXIST(policyURI)));
/*     */       }
/* 102 */       result.add(ModelTranslator.getTranslator().translate(sourceModel));
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return result;
/*     */   }
/*     */   
/*     */   final Collection<PolicySubject> getPolicySubjects() throws PolicyException {
/* 110 */     Collection<Policy> policies = getPolicies();
/* 111 */     Collection<PolicySubject> result = new ArrayList<PolicySubject>(policies.size());
/* 112 */     for (Policy policy : policies) {
/* 113 */       result.add(new PolicySubject(this.policySubject, policy));
/*     */     }
/* 115 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\BuilderHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */