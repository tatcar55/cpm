/*     */ package com.sun.xml.ws.rx.mc.policy.spi_impl;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.mc.policy.wsmc200702.MakeConnectionSupportedAssertion;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class McFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  67 */   private static final Logger LOGGER = Logger.getLogger(McFeatureConfigurator.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/*  79 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/*  80 */     if (key != null && policyMap != null) {
/*  81 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  82 */       if (policy != null) {
/*  83 */         for (AssertionSet alternative : policy) {
/*  84 */           MakeConnectionSupportedFeature feature = translateIntoMakeConnectionFeature(alternative);
/*  85 */           if (feature != null) {
/*  86 */             features.add(feature);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*  91 */     return features;
/*     */   }
/*     */ 
/*     */   
/*     */   private MakeConnectionSupportedFeature translateIntoMakeConnectionFeature(AssertionSet alternative) throws PolicyException {
/*  96 */     if (isPresentAndMandatory(alternative, MakeConnectionSupportedAssertion.NAME)) {
/*  97 */       return new MakeConnectionSupportedFeature();
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   private Collection<PolicyAssertion> getAssertionsWithName(AssertionSet alternative, QName name) throws PolicyException {
/* 103 */     Collection<PolicyAssertion> assertions = alternative.get(name);
/* 104 */     if (assertions.size() > 1) {
/* 105 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSMC_0122_DUPLICATE_ASSERTION_IN_POLICY(Integer.valueOf(assertions.size()), name)));
/*     */     }
/*     */     
/* 108 */     return assertions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPresentAndMandatory(AssertionSet alternative, QName assertionName) throws PolicyException {
/* 114 */     Collection<PolicyAssertion> assertions = getAssertionsWithName(alternative, assertionName);
/* 115 */     for (PolicyAssertion assertion : assertions) {
/* 116 */       if (!assertion.isOptional()) {
/* 117 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 121 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\spi_impl\McFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */