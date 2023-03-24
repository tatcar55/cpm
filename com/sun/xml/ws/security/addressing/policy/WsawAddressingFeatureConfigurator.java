/*     */ package com.sun.xml.ws.security.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WsawAddressingFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  72 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(WsawAddressingFeatureConfigurator.class);
/*     */   
/*  74 */   private static final QName WSAW_ADDRESSING_ASSERTION = new QName(AddressingVersion.W3C.policyNsUri, "UsingAddressing");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*  93 */     LOGGER.entering(new Object[] { key, policyMap });
/*  94 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/*  95 */     if (key != null && policyMap != null) {
/*  96 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  97 */       if (null != policy && policy.contains(WSAW_ADDRESSING_ASSERTION)) {
/*  98 */         Iterator<AssertionSet> assertions = policy.iterator();
/*  99 */         while (assertions.hasNext()) {
/* 100 */           AssertionSet assertionSet = assertions.next();
/* 101 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 102 */           while (policyAssertion.hasNext()) {
/* 103 */             PolicyAssertion assertion = policyAssertion.next();
/* 104 */             if (assertion.getName().equals(WSAW_ADDRESSING_ASSERTION)) {
/* 105 */               WebServiceFeature feature = new AddressingFeature(true, !assertion.isOptional());
/* 106 */               features.add(feature);
/* 107 */               if (LOGGER.isLoggable(Level.FINE)) {
/* 108 */                 LOGGER.fine("Added addressing feature \"" + feature + "\" to element \"" + key + "\"");
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 115 */     LOGGER.exiting(features);
/* 116 */     return features;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\policy\WsawAddressingFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */