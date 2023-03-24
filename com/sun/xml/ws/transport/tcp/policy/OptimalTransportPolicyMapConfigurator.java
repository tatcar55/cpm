/*     */ package com.sun.xml.ws.transport.tcp.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.transport.tcp.SelectOptimalTransportFeature;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.SimpleAssertion;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import com.sun.xml.ws.transport.tcp.wsit.TCPConstants;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class OptimalTransportPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  72 */   private static final Logger LOGGER = Logger.getLogger(OptimalTransportPolicyMapConfigurator.class);
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/*  75 */     Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*     */     
/*     */     try {
/*  78 */       LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */       
/*  80 */       updateOptimalTransportSettings(subjects, wsBinding, model, policyMap);
/*     */       
/*  82 */       return subjects;
/*     */     }
/*     */     finally {
/*     */       
/*  86 */       LOGGER.exiting(subjects);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateOptimalTransportSettings(Collection<PolicySubject> subjects, WSBinding wsBinding, SEIModel model, PolicyMap policyMap) throws PolicyException, IllegalArgumentException {
/*  91 */     SelectOptimalTransportFeature optimalTransportFeature = (SelectOptimalTransportFeature)wsBinding.getFeature(SelectOptimalTransportFeature.class);
/*  92 */     if (optimalTransportFeature == null || !optimalTransportFeature.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (LOGGER.isLoggable(Level.FINEST))
/*     */     {
/*  98 */       LOGGER.finest(String.format("Make Optimal transport feature enabled on service '%s', port '%s'", new Object[] { model.getServiceQName(), model.getPortName() }));
/*     */     }
/*     */     
/* 101 */     PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(model.getServiceQName(), model.getPortName());
/* 102 */     Policy existingPolicy = (policyMap != null) ? policyMap.getEndpointEffectivePolicy(endpointKey) : null;
/* 103 */     if (existingPolicy == null || !existingPolicy.contains(TCPConstants.SELECT_OPTIMAL_TRANSPORT_ASSERTION)) {
/* 104 */       Policy otPolicy = createOptimalTransportPolicy(model.getBoundPortTypeName());
/* 105 */       WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(model.getBoundPortTypeName());
/* 106 */       PolicySubject subject = new PolicySubject(wsdlSubject, otPolicy);
/* 107 */       subjects.add(subject);
/* 108 */       if (LOGGER.isLoggable(Level.FINE))
/*     */       {
/* 110 */         LOGGER.fine(String.format("Added Optimal transport policy with ID '%s' to binding element '%s'", new Object[] { otPolicy.getIdOrName(), model.getBoundPortTypeName() }));
/*     */       }
/* 112 */     } else if (LOGGER.isLoggable(Level.FINE)) {
/*     */       
/* 114 */       LOGGER.fine("Make Optimal transport assertion is already present in the endpoint policy");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policy createOptimalTransportPolicy(QName bindingName) {
/* 125 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_OptimalTransport_Policy", Arrays.asList(new AssertionSet[] { AssertionSet.createAssertionSet(Arrays.asList(new PolicyAssertion[] { (PolicyAssertion)new OptimalTransportAssertion() })) }));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class OptimalTransportAssertion
/*     */     extends SimpleAssertion
/*     */   {
/*     */     public OptimalTransportAssertion() {
/* 133 */       this(AssertionData.createAssertionData(TCPConstants.SELECT_OPTIMAL_TRANSPORT_ASSERTION), null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public OptimalTransportAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 139 */       super(data, assertionParameters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\policy\OptimalTransportPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */