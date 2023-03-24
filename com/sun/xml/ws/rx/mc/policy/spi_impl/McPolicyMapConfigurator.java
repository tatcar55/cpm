/*     */ package com.sun.xml.ws.rx.mc.policy.spi_impl;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import com.sun.xml.ws.rx.mc.api.MakeConnectionSupportedFeature;
/*     */ import com.sun.xml.ws.rx.mc.policy.wsmc200702.MakeConnectionSupportedAssertion;
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
/*     */ public class McPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  69 */   private static final Logger LOGGER = Logger.getLogger(McPolicyMapConfigurator.class);
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/*  72 */     Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*     */     
/*     */     try {
/*  75 */       LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */       
/*  77 */       updateMakeConnectionSettings(subjects, wsBinding, model, policyMap);
/*     */       
/*  79 */       return subjects;
/*     */     } finally {
/*  81 */       LOGGER.exiting(subjects);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policy createMakeConnectionPolicy(QName bindingName) {
/*  92 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_MCSupported_Policy", Arrays.asList(new AssertionSet[] { AssertionSet.createAssertionSet(Arrays.asList(new PolicyAssertion[] { (PolicyAssertion)new MakeConnectionSupportedAssertion() })) }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMakeConnectionSettings(Collection<PolicySubject> subjects, WSBinding wsBinding, SEIModel model, PolicyMap policyMap) throws PolicyException, IllegalArgumentException {
/*  98 */     MakeConnectionSupportedFeature mcFeature = (MakeConnectionSupportedFeature)wsBinding.getFeature(MakeConnectionSupportedFeature.class);
/*  99 */     if (mcFeature == null || !mcFeature.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     if (LOGGER.isLoggable(Level.FINEST)) {
/* 104 */       LOGGER.finest(String.format("Make connection feature enabled on service '%s', port '%s'", new Object[] { model.getServiceQName(), model.getPortName() }));
/*     */     }
/*     */     
/* 107 */     PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(model.getServiceQName(), model.getPortName());
/* 108 */     Policy existingPolicy = (policyMap != null) ? policyMap.getEndpointEffectivePolicy(endpointKey) : null;
/* 109 */     if (existingPolicy == null || !existingPolicy.contains(MakeConnectionSupportedAssertion.NAME)) {
/* 110 */       Policy mcPolicy = createMakeConnectionPolicy(model.getBoundPortTypeName());
/* 111 */       WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(model.getBoundPortTypeName());
/* 112 */       PolicySubject subject = new PolicySubject(wsdlSubject, mcPolicy);
/* 113 */       subjects.add(subject);
/* 114 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 115 */         LOGGER.fine(String.format("Added make connection policy with ID '%s' to binding element '%s'", new Object[] { mcPolicy.getIdOrName(), model.getBoundPortTypeName() }));
/*     */       }
/* 117 */     } else if (LOGGER.isLoggable(Level.FINE)) {
/* 118 */       LOGGER.fine("Make connection assertion is already present in the endpoint policy");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\spi_impl\McPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */