/*    */ package com.sun.xml.ws.transport.tcp.policy;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.api.transport.tcp.TcpTransportFeature;
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.Policy;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*    */ import com.sun.xml.ws.transport.tcp.wsit.TCPConstants;
/*    */ import java.util.Collection;
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
/*    */ public class TCPTransportFeatureConfigurator
/*    */   implements PolicyFeatureConfigurator
/*    */ {
/* 66 */   private static final QName ENABLED = new QName("enabled");
/* 67 */   private static final Logger LOGGER = Logger.getLogger(TCPTransportFeatureConfigurator.class);
/*    */ 
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
/* 81 */       if (policy != null) {
/* 82 */         for (AssertionSet alternative : policy) {
/* 83 */           for (PolicyAssertion assertion : alternative) {
/* 84 */             if (assertion.getName().equals(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION)) {
/* 85 */               boolean isEnabled = true;
/* 86 */               String value = assertion.getAttributeValue(ENABLED);
/* 87 */               if (value != null) {
/* 88 */                 value = value.trim();
/* 89 */                 isEnabled = (Boolean.valueOf(value).booleanValue() || value.equalsIgnoreCase("yes"));
/*    */               } 
/*    */               
/* 92 */               features.add(new TcpTransportFeature(isEnabled));
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 99 */     return features;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\policy\TCPTransportFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */