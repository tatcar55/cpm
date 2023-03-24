/*     */ package com.sun.xml.ws.encoding.policy;
/*     */ 
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
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.soap.MTOMFeature;
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
/*     */ public class MtomPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  71 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(MtomPolicyMapConfigurator.class);
/*     */ 
/*     */   
/*     */   static class MtomAssertion
/*     */     extends PolicyAssertion
/*     */   {
/*  77 */     private static final AssertionData mtomData = AssertionData.createAssertionData(EncodingConstants.OPTIMIZED_MIME_SERIALIZATION_ASSERTION);
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  82 */       mtomData.setOptionalAttribute(true);
/*     */     }
/*     */     
/*     */     MtomAssertion() {
/*  86 */       super(mtomData, null, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/* 105 */     LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */     
/* 107 */     Collection<PolicySubject> subjects = new ArrayList<PolicySubject>();
/* 108 */     if (policyMap != null) {
/* 109 */       MTOMFeature mtomFeature = (MTOMFeature)wsBinding.getFeature(MTOMFeature.class);
/* 110 */       if (LOGGER.isLoggable(Level.FINEST)) {
/* 111 */         LOGGER.finest("mtomFeature = " + mtomFeature);
/*     */       }
/* 113 */       if (mtomFeature != null && mtomFeature.isEnabled()) {
/* 114 */         QName bindingName = model.getBoundPortTypeName();
/* 115 */         WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(bindingName);
/* 116 */         Policy mtomPolicy = createMtomPolicy(bindingName);
/* 117 */         PolicySubject mtomPolicySubject = new PolicySubject(wsdlSubject, mtomPolicy);
/* 118 */         subjects.add(mtomPolicySubject);
/* 119 */         if (LOGGER.isLoggable(Level.FINEST)) {
/* 120 */           LOGGER.fine("Added MTOM policy with ID \"" + mtomPolicy.getIdOrName() + "\" to binding element \"" + bindingName + "\"");
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 125 */     LOGGER.exiting(subjects);
/* 126 */     return subjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policy createMtomPolicy(QName bindingName) {
/* 137 */     ArrayList<AssertionSet> assertionSets = new ArrayList<AssertionSet>(1);
/* 138 */     ArrayList<PolicyAssertion> assertions = new ArrayList<PolicyAssertion>(1);
/* 139 */     assertions.add(new MtomAssertion());
/* 140 */     assertionSets.add(AssertionSet.createAssertionSet(assertions));
/* 141 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_MTOM_Policy", assertionSets);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\policy\MtomPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */