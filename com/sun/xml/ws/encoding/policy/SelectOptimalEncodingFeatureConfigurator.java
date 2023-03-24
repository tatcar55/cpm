/*    */ package com.sun.xml.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.client.SelectOptimalEncodingFeature;
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
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ public class SelectOptimalEncodingFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/* 67 */   public static final QName enabled = new QName("enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 77 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/* 78 */     if (key != null && policyMap != null) {
/* 79 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 80 */       if (null != policy && policy.contains(EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION)) {
/* 81 */         Iterator<AssertionSet> assertions = policy.iterator();
/* 82 */         while (assertions.hasNext()) {
/* 83 */           AssertionSet assertionSet = assertions.next();
/* 84 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 85 */           while (policyAssertion.hasNext()) {
/* 86 */             PolicyAssertion assertion = policyAssertion.next();
/* 87 */             if (EncodingConstants.SELECT_OPTIMAL_ENCODING_ASSERTION.equals(assertion.getName())) {
/* 88 */               String value = assertion.getAttributeValue(enabled);
/* 89 */               boolean isSelectOptimalEncodingEnabled = (value == null || Boolean.valueOf(value.trim()).booleanValue());
/* 90 */               features.add(new SelectOptimalEncodingFeature(isSelectOptimalEncodingEnabled));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 96 */     return features;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\policy\SelectOptimalEncodingFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */