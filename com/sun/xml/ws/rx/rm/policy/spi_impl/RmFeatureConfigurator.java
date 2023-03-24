/*     */ package com.sun.xml.ws.rx.rm.policy.spi_impl;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*     */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeatureBuilder;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.policy.RmConfigurator;
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
/*     */ public class RmFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  69 */   private static final Logger LOGGER = Logger.getLogger(RmFeatureConfigurator.class);
/*     */ 
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
/*  81 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/*  82 */     if (key != null && policyMap != null) {
/*  83 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  84 */       if (policy != null) {
/*  85 */         for (AssertionSet alternative : policy) {
/*     */           
/*  87 */           ReliableMessagingFeature reliableMessagingFeature = getRmFeature(alternative);
/*  88 */           if (reliableMessagingFeature != null) {
/*  89 */             features.add(reliableMessagingFeature);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*  94 */     return features;
/*     */   }
/*     */ 
/*     */   
/*     */   private ReliableMessagingFeature getRmFeature(AssertionSet alternative) throws PolicyException {
/*  99 */     ReliableMessagingFeatureBuilder rmFeatureBuilder = null;
/* 100 */     for (RmProtocolVersion rmv : RmProtocolVersion.values()) {
/* 101 */       if (isPresentAndMandatory(alternative, rmv.rmAssertionName)) {
/* 102 */         rmFeatureBuilder = new ReliableMessagingFeatureBuilder(rmv);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 107 */     if (rmFeatureBuilder == null) {
/* 108 */       return null;
/*     */     }
/*     */     
/* 111 */     for (PolicyAssertion assertion : alternative) {
/* 112 */       if (assertion instanceof RmConfigurator) {
/* 113 */         RmConfigurator rmAssertion = RmConfigurator.class.cast(assertion);
/* 114 */         if (!rmAssertion.isCompatibleWith(rmFeatureBuilder.getProtocolVersion())) {
/* 115 */           LOGGER.warning(LocalizationMessages.WSRM_1009_INCONSISTENCIES_IN_POLICY(rmAssertion.getName(), rmFeatureBuilder.getProtocolVersion()));
/*     */         }
/*     */ 
/*     */         
/* 119 */         rmFeatureBuilder = rmAssertion.update(rmFeatureBuilder);
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return rmFeatureBuilder.build();
/*     */   }
/*     */   
/*     */   private Collection<PolicyAssertion> getAssertionsWithName(AssertionSet alternative, QName name) throws PolicyException {
/* 127 */     Collection<PolicyAssertion> assertions = alternative.get(name);
/* 128 */     if (assertions.size() > 1) {
/* 129 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSRM_1008_DUPLICATE_ASSERTION_IN_POLICY(Integer.valueOf(assertions.size()), name)));
/*     */     }
/*     */     
/* 132 */     return assertions;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPresentAndMandatory(AssertionSet alternative, QName assertionName) throws PolicyException {
/* 138 */     Collection<PolicyAssertion> assertions = getAssertionsWithName(alternative, assertionName);
/* 139 */     for (PolicyAssertion assertion : assertions) {
/* 140 */       if (!assertion.isOptional()) {
/* 141 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\spi_impl\RmFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */