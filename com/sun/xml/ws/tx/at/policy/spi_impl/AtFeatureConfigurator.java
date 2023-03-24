/*     */ package com.sun.xml.ws.tx.at.policy.spi_impl;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.api.tx.at.TransactionalFeature;
/*     */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AtFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  70 */   private static final Logger LOGGER = Logger.getLogger(AtFeatureConfigurator.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey endpointKey, PolicyMap policyMap) throws PolicyException {
/*  82 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/*  83 */     if (endpointKey == null || policyMap == null) {
/*  84 */       return features;
/*     */     }
/*     */     
/*  87 */     TransactionalFeature endpointFeature = getAtFeature(policyMap.getEndpointEffectivePolicy(endpointKey), false);
/*  88 */     if (endpointFeature != null) {
/*  89 */       features.add(endpointFeature);
/*     */     }
/*     */     
/*  92 */     for (PolicyMapKey key : policyMap.getAllOperationScopeKeys()) {
/*  93 */       if (!endpointKey.equals(key)) {
/*     */         continue;
/*     */       }
/*     */       
/*  97 */       TransactionalFeature feature = getAtFeature(policyMap.getOperationEffectivePolicy(key), true);
/*  98 */       if (feature == null || !feature.isEnabled()) {
/*     */         continue;
/*     */       }
/*     */       
/* 102 */       if (endpointFeature == null) {
/* 103 */         endpointFeature = feature;
/* 104 */         features.add(endpointFeature);
/* 105 */       } else if (endpointFeature.getVersion() != feature.getVersion()) {
/* 106 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSAT_1004_ENDPOINT_AND_OPERATION_POLICIES_DONT_MATCH(endpointKey, key)));
/*     */       } 
/*     */       
/* 109 */       endpointFeature.setExplicitMode(true);
/* 110 */       String opName = key.getOperation().getLocalPart();
/* 111 */       feature.setFlowType(opName, feature.getFlowType());
/* 112 */       feature.setEnabled(opName, true);
/*     */     } 
/*     */     
/* 115 */     return features;
/*     */   }
/*     */   
/*     */   private TransactionalFeature getAtFeature(Policy policy, boolean setExplictMode) throws WebServiceException, PolicyException {
/* 119 */     if (policy == null) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     TransactionalFeature resultFeature = null;
/* 124 */     for (AssertionSet alternative : policy) {
/* 125 */       TransactionalFeature feature = getAtFeature(alternative, setExplictMode);
/* 126 */       if (feature == null) {
/*     */         continue;
/*     */       }
/* 129 */       if (resultFeature == null) {
/* 130 */         resultFeature = feature; continue;
/* 131 */       }  if (!areCompatible(resultFeature, feature)) {
/* 132 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSAT_1003_INCOMPATIBLE_FEATURES_DETECTED(policy.toString())));
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return resultFeature;
/*     */   }
/*     */   
/*     */   private TransactionalFeature getAtFeature(AssertionSet alternative, boolean setExplicitMode) throws PolicyException {
/* 140 */     TransactionalFeature feature = null;
/* 141 */     for (PolicyAssertion assertion : alternative) {
/* 142 */       if (assertion instanceof com.sun.xml.ws.tx.at.policy.AtAssertion) {
/* 143 */         if (feature != null) {
/* 144 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSAT_1001_DUPLICATE_ASSERTION_IN_POLICY(alternative.toString())));
/*     */         }
/*     */         
/* 147 */         feature = new TransactionalFeature(true);
/* 148 */         feature.setExplicitMode(setExplicitMode);
/* 149 */         WsatNamespace version = WsatNamespace.forNamespaceUri(assertion.getName().getNamespaceURI());
/*     */         
/* 151 */         feature.setVersion(Transactional.Version.forNamespaceVersion(version));
/* 152 */         feature.setFlowType(assertion.isOptional() ? Transactional.TransactionFlowType.SUPPORTS : Transactional.TransactionFlowType.MANDATORY);
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     return feature;
/*     */   }
/*     */   
/*     */   private static boolean areCompatible(TransactionalFeature featureA, TransactionalFeature featureB) {
/* 160 */     boolean result = true;
/*     */     
/* 162 */     result = (result && featureA.isEnabled() == featureB.isEnabled());
/* 163 */     result = (result && featureA.getVersion() == featureB.getVersion());
/* 164 */     result = (result && featureA.getFlowType() == featureB.getFlowType());
/*     */     
/* 166 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\spi_impl\AtFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */