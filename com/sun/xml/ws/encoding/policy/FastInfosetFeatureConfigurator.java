/*    */ package com.sun.xml.ws.encoding.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.fastinfoset.FastInfosetFeature;
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
/*    */ 
/*    */ public class FastInfosetFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/* 68 */   public static final QName enabled = new QName("enabled");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 78 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/* 79 */     if (key != null && policyMap != null) {
/* 80 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 81 */       if (null != policy && policy.contains(EncodingConstants.OPTIMIZED_FI_SERIALIZATION_ASSERTION)) {
/* 82 */         Iterator<AssertionSet> assertions = policy.iterator();
/* 83 */         while (assertions.hasNext()) {
/* 84 */           AssertionSet assertionSet = assertions.next();
/* 85 */           Iterator<PolicyAssertion> policyAssertion = assertionSet.iterator();
/* 86 */           while (policyAssertion.hasNext()) {
/* 87 */             PolicyAssertion assertion = policyAssertion.next();
/* 88 */             if (EncodingConstants.OPTIMIZED_FI_SERIALIZATION_ASSERTION.equals(assertion.getName())) {
/* 89 */               String value = assertion.getAttributeValue(enabled);
/* 90 */               boolean isFastInfosetEnabled = Boolean.valueOf(value.trim()).booleanValue();
/* 91 */               features.add(new FastInfosetFeature(isFastInfosetEnabled));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/* 97 */     return features;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\policy\FastInfosetFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */