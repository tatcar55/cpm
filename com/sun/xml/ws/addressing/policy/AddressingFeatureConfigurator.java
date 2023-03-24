/*     */ package com.sun.xml.ws.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.bind.util.Which;
/*     */ import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.resources.ModelerMessages;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class AddressingFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  75 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingFeatureConfigurator.class);
/*     */   
/*  77 */   private static final QName[] ADDRESSING_ASSERTIONS = new QName[] { new QName(AddressingVersion.MEMBER.policyNsUri, "UsingAddressing") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/*  87 */     LOGGER.entering(new Object[] { key, policyMap });
/*  88 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/*  89 */     if (key != null && policyMap != null) {
/*  90 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/*  91 */       for (QName addressingAssertionQName : ADDRESSING_ASSERTIONS) {
/*  92 */         if (policy != null && policy.contains(addressingAssertionQName)) {
/*  93 */           Iterator<AssertionSet> assertions = policy.iterator();
/*  94 */           while (assertions.hasNext()) {
/*  95 */             AssertionSet assertionSet = assertions.next();
/*  96 */             Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/*  97 */             while (policyAssertion.hasNext()) {
/*  98 */               PolicyAssertion assertion = policyAssertion.next();
/*  99 */               if (assertion.getName().equals(addressingAssertionQName)) {
/* 100 */                 WebServiceFeature feature = AddressingVersion.getFeature(addressingAssertionQName.getNamespaceURI(), true, !assertion.isOptional());
/* 101 */                 if (LOGGER.isLoggable(Level.FINE)) {
/* 102 */                   LOGGER.fine("Added addressing feature \"" + feature + "\" for element \"" + key + "\"");
/*     */                 }
/* 104 */                 features.add(feature);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 112 */       if (policy != null && policy.contains(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
/* 113 */         for (AssertionSet assertions : policy) {
/* 114 */           for (PolicyAssertion assertion : assertions) {
/* 115 */             if (assertion.getName().equals(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
/* 116 */               WebServiceFeature feature; NestedPolicy nestedPolicy = assertion.getNestedPolicy();
/* 117 */               boolean requiresAnonymousResponses = false;
/* 118 */               boolean requiresNonAnonymousResponses = false;
/* 119 */               if (nestedPolicy != null) {
/* 120 */                 requiresAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION);
/* 121 */                 requiresNonAnonymousResponses = nestedPolicy.contains(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION);
/*     */               } 
/* 123 */               if (requiresAnonymousResponses && requiresNonAnonymousResponses) {
/* 124 */                 throw new WebServiceException("Only one among AnonymousResponses and NonAnonymousResponses can be nested in an Addressing assertion");
/*     */               }
/*     */ 
/*     */               
/*     */               try {
/* 129 */                 if (requiresAnonymousResponses) {
/* 130 */                   feature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.ANONYMOUS);
/* 131 */                 } else if (requiresNonAnonymousResponses) {
/* 132 */                   feature = new AddressingFeature(true, !assertion.isOptional(), AddressingFeature.Responses.NON_ANONYMOUS);
/*     */                 } else {
/* 134 */                   feature = new AddressingFeature(true, !assertion.isOptional());
/*     */                 } 
/* 136 */               } catch (NoSuchMethodError e) {
/* 137 */                 throw (PolicyException)LOGGER.logSevereException(new PolicyException(ModelerMessages.RUNTIME_MODELER_ADDRESSING_RESPONSES_NOSUCHMETHOD(toJar(Which.which(AddressingFeature.class))), e));
/*     */               } 
/* 139 */               if (LOGGER.isLoggable(Level.FINE)) {
/* 140 */                 LOGGER.fine("Added addressing feature \"" + feature + "\" for element \"" + key + "\"");
/*     */               }
/* 142 */               features.add(feature);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 148 */     LOGGER.exiting(features);
/* 149 */     return features;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toJar(String url) {
/* 156 */     if (!url.startsWith("jar:"))
/* 157 */       return url; 
/* 158 */     url = url.substring(4);
/* 159 */     return url.substring(0, url.lastIndexOf('!'));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\policy\AddressingFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */