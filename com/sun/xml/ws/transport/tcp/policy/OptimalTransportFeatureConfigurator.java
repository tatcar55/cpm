/*     */ package com.sun.xml.ws.transport.tcp.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.transport.tcp.SelectOptimalTransportFeature;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.transport.tcp.wsit.TCPConstants;
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
/*     */ public class OptimalTransportFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*  66 */   private static final QName ENABLED = new QName("enabled");
/*  67 */   private static final Logger LOGGER = Logger.getLogger(OptimalTransportFeatureConfigurator.class);
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
/*  84 */           for (PolicyAssertion assertion : alternative) {
/*  85 */             if (assertion.getName().equals(TCPConstants.SELECT_OPTIMAL_TRANSPORT_ASSERTION)) {
/*  86 */               boolean isEnabled = true;
/*  87 */               String value = assertion.getAttributeValue(ENABLED);
/*  88 */               if (value != null) {
/*  89 */                 value = value.trim();
/*  90 */                 isEnabled = (Boolean.valueOf(value).booleanValue() || value.equalsIgnoreCase("yes"));
/*     */               } 
/*     */               
/*  93 */               features.add(new SelectOptimalTransportFeature(isEnabled));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 100 */     return features;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\policy\OptimalTransportFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */