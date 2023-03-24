/*     */ package com.sun.xml.ws.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
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
/*     */ public class AddressingPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  72 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingPolicyMapConfigurator.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class AddressingAssertion
/*     */     extends PolicyAssertion
/*     */   {
/*     */     AddressingAssertion(AssertionData assertionData, AssertionSet nestedAlternative) {
/*  82 */       super(assertionData, null, nestedAlternative);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     AddressingAssertion(AssertionData assertionData) {
/*  91 */       super(assertionData, null, null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/* 101 */     LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */     
/* 103 */     Collection<PolicySubject> subjects = new ArrayList<PolicySubject>();
/* 104 */     if (policyMap != null) {
/* 105 */       AddressingFeature addressingFeature = (AddressingFeature)wsBinding.getFeature(AddressingFeature.class);
/* 106 */       if (LOGGER.isLoggable(Level.FINEST)) {
/* 107 */         LOGGER.finest("addressingFeature = " + addressingFeature);
/*     */       }
/* 109 */       if (addressingFeature != null && addressingFeature.isEnabled())
/*     */       {
/* 111 */         addWsamAddressing(subjects, policyMap, model, addressingFeature);
/*     */       }
/*     */     } 
/* 114 */     LOGGER.exiting(subjects);
/* 115 */     return subjects;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addWsamAddressing(Collection<PolicySubject> subjects, PolicyMap policyMap, SEIModel model, AddressingFeature addressingFeature) throws PolicyException {
/* 120 */     QName bindingName = model.getBoundPortTypeName();
/* 121 */     WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(bindingName);
/* 122 */     Policy addressingPolicy = createWsamAddressingPolicy(bindingName, addressingFeature);
/* 123 */     PolicySubject addressingPolicySubject = new PolicySubject(wsdlSubject, addressingPolicy);
/* 124 */     subjects.add(addressingPolicySubject);
/* 125 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 126 */       LOGGER.fine("Added addressing policy with ID \"" + addressingPolicy.getIdOrName() + "\" to binding element \"" + bindingName + "\"");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policy createWsamAddressingPolicy(QName bindingName, AddressingFeature af) {
/* 134 */     ArrayList<AssertionSet> assertionSets = new ArrayList<AssertionSet>(1);
/* 135 */     ArrayList<PolicyAssertion> assertions = new ArrayList<PolicyAssertion>(1);
/* 136 */     AssertionData addressingData = AssertionData.createAssertionData(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION);
/*     */     
/* 138 */     if (!af.isRequired()) {
/* 139 */       addressingData.setOptionalAttribute(true);
/*     */     }
/*     */     try {
/* 142 */       AddressingFeature.Responses responses = af.getResponses();
/* 143 */       if (responses == AddressingFeature.Responses.ANONYMOUS) {
/* 144 */         AssertionData nestedAsserData = AssertionData.createAssertionData(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION);
/* 145 */         PolicyAssertion nestedAsser = new AddressingAssertion(nestedAsserData, null);
/* 146 */         assertions.add(new AddressingAssertion(addressingData, AssertionSet.createAssertionSet(Collections.singleton(nestedAsser))));
/* 147 */       } else if (responses == AddressingFeature.Responses.NON_ANONYMOUS) {
/* 148 */         AssertionData nestedAsserData = AssertionData.createAssertionData(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION);
/* 149 */         PolicyAssertion nestedAsser = new AddressingAssertion(nestedAsserData, null);
/* 150 */         assertions.add(new AddressingAssertion(addressingData, AssertionSet.createAssertionSet(Collections.singleton(nestedAsser))));
/*     */       } else {
/* 152 */         assertions.add(new AddressingAssertion(addressingData, AssertionSet.createAssertionSet(null)));
/*     */       } 
/* 154 */     } catch (NoSuchMethodError e) {
/*     */ 
/*     */       
/* 157 */       assertions.add(new AddressingAssertion(addressingData, AssertionSet.createAssertionSet(null)));
/*     */     } 
/* 159 */     assertionSets.add(AssertionSet.createAssertionSet(assertions));
/* 160 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_WSAM_Addressing_Policy", assertionSets);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\policy\AddressingPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */