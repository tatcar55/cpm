/*    */ package com.sun.xml.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.Policy;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.soap.MTOMFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MtomFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/*    */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 80 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/* 81 */     if (key != null && policyMap != null) {
/* 82 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 83 */       if (null != policy && policy.contains(EncodingConstants.OPTIMIZED_MIME_SERIALIZATION_ASSERTION)) {
/* 84 */         Iterator<AssertionSet> assertions = policy.iterator();
/* 85 */         while (assertions.hasNext()) {
/* 86 */           AssertionSet assertionSet = assertions.next();
/* 87 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 88 */           while (policyAssertion.hasNext()) {
/* 89 */             PolicyAssertion assertion = policyAssertion.next();
/* 90 */             if (EncodingConstants.OPTIMIZED_MIME_SERIALIZATION_ASSERTION.equals(assertion.getName())) {
/* 91 */               features.add(new MTOMFeature(true));
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 97 */     return features;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\policy\MtomFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */