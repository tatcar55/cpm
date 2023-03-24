/*     */ package com.sun.xml.ws.security.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class WsawAddressingPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  74 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(WsawAddressingPolicyMapConfigurator.class);
/*     */   
/*     */   private static final class AddressingAssertion
/*     */     extends PolicyAssertion {
/*     */     AddressingAssertion(AssertionData assertionData) {
/*  79 */       super(assertionData, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/*  89 */     LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */     
/*  91 */     Collection<PolicySubject> subjects = new ArrayList<PolicySubject>();
/*  92 */     if (policyMap != null) {
/*  93 */       AddressingFeature addressingFeature = (AddressingFeature)wsBinding.getFeature(AddressingFeature.class);
/*  94 */       if (LOGGER.isLoggable(Level.FINEST)) {
/*  95 */         LOGGER.finest("addressingFeature = " + addressingFeature);
/*     */       }
/*  97 */       if (addressingFeature != null && addressingFeature.isEnabled())
/*     */       {
/*  99 */         addWsawUsingAddressingForCompatibility(subjects, policyMap, model, addressingFeature);
/*     */       }
/*     */     } 
/*     */     
/* 103 */     LOGGER.exiting(subjects);
/* 104 */     return subjects;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addWsawUsingAddressingForCompatibility(Collection<PolicySubject> subjects, PolicyMap policyMap, SEIModel model, AddressingFeature addressingFeature) throws PolicyException {
/* 109 */     AddressingVersion addressingVersion = AddressingVersion.fromFeature(addressingFeature);
/* 110 */     QName usingAddressing = new QName(addressingVersion.policyNsUri, "UsingAddressing");
/* 111 */     PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(model.getServiceQName(), model.getPortName());
/* 112 */     Policy existingPolicy = policyMap.getEndpointEffectivePolicy(endpointKey);
/* 113 */     if (existingPolicy == null || !existingPolicy.contains(usingAddressing)) {
/* 114 */       QName bindingName = model.getBoundPortTypeName();
/* 115 */       WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(bindingName);
/* 116 */       Policy addressingPolicy = createWsawAddressingPolicy(bindingName, usingAddressing, addressingFeature.isRequired());
/* 117 */       PolicySubject addressingPolicySubject = new PolicySubject(wsdlSubject, addressingPolicy);
/* 118 */       subjects.add(addressingPolicySubject);
/* 119 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 120 */         LOGGER.fine("Added addressing policy with ID \"" + addressingPolicy.getIdOrName() + "\" to binding element \"" + bindingName + "\"");
/*     */       }
/*     */     }
/* 123 */     else if (LOGGER.isLoggable(Level.FINE)) {
/* 124 */       LOGGER.fine("Addressing policy exists already, doing nothing");
/*     */     } 
/*     */   }
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
/*     */   private Policy createWsawAddressingPolicy(QName bindingName, QName assertionName, boolean isRequired) {
/* 138 */     ArrayList<AssertionSet> assertionSets = new ArrayList<AssertionSet>(1);
/* 139 */     ArrayList<PolicyAssertion> assertions = new ArrayList<PolicyAssertion>(1);
/* 140 */     AssertionData addressingData = AssertionData.createAssertionData(assertionName);
/*     */     
/* 142 */     if (!isRequired) {
/* 143 */       addressingData.setOptionalAttribute(true);
/*     */     }
/* 145 */     assertions.add(new AddressingAssertion(addressingData));
/* 146 */     assertionSets.add(AssertionSet.createAssertionSet(assertions));
/* 147 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_Wsaw_Addressing_Policy", assertionSets);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\policy\WsawAddressingPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */