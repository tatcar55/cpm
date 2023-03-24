/*     */ package com.sun.xml.ws.transport.tcp.policy;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.transport.tcp.TcpTransportFeature;
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
/*     */ public class TCPTransportPolicyMapConfigurator
/*     */   implements PolicyMapConfigurator
/*     */ {
/*  72 */   private static final Logger LOGGER = Logger.getLogger(TCPTransportPolicyMapConfigurator.class);
/*     */   
/*     */   public Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException {
/*  75 */     Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/*     */     
/*     */     try {
/*  78 */       LOGGER.entering(new Object[] { policyMap, model, wsBinding });
/*     */       
/*  80 */       updateTCPTransportSettings(subjects, wsBinding, model, policyMap);
/*     */       
/*  82 */       return subjects;
/*     */     }
/*     */     finally {
/*     */       
/*  86 */       LOGGER.exiting(subjects);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateTCPTransportSettings(Collection<PolicySubject> subjects, WSBinding wsBinding, SEIModel model, PolicyMap policyMap) throws PolicyException, IllegalArgumentException {
/*  91 */     TcpTransportFeature tcpTransportFeature = (TcpTransportFeature)wsBinding.getFeature(TcpTransportFeature.class);
/*  92 */     if (tcpTransportFeature == null || !tcpTransportFeature.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     if (LOGGER.isLoggable(Level.FINEST))
/*     */     {
/*  98 */       LOGGER.finest(String.format("Make TCP transport feature enabled on service '%s', port '%s'", new Object[] { model.getServiceQName(), model.getPortName() }));
/*     */     }
/*     */     
/* 101 */     PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(model.getServiceQName(), model.getPortName());
/* 102 */     Policy existingPolicy = (policyMap != null) ? policyMap.getEndpointEffectivePolicy(endpointKey) : null;
/* 103 */     if (existingPolicy == null || !existingPolicy.contains(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION)) {
/* 104 */       Policy tcpTransportPolicy = createTCPTransportPolicy(model.getBoundPortTypeName());
/* 105 */       WsdlBindingSubject wsdlSubject = WsdlBindingSubject.createBindingSubject(model.getBoundPortTypeName());
/* 106 */       PolicySubject subject = new PolicySubject(wsdlSubject, tcpTransportPolicy);
/* 107 */       subjects.add(subject);
/* 108 */       if (LOGGER.isLoggable(Level.FINE))
/*     */       {
/* 110 */         LOGGER.fine(String.format("Added TCP transport policy with ID '%s' to binding element '%s'", new Object[] { tcpTransportPolicy.getIdOrName(), model.getBoundPortTypeName() }));
/*     */       }
/* 112 */     } else if (LOGGER.isLoggable(Level.FINE)) {
/*     */       
/* 114 */       LOGGER.fine("Make TCP transport assertion is already present in the endpoint policy");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Policy createTCPTransportPolicy(QName bindingName) {
/* 125 */     return Policy.createPolicy(null, bindingName.getLocalPart() + "_TCPTransport_Policy", Arrays.asList(new AssertionSet[] { AssertionSet.createAssertionSet(Arrays.asList(new PolicyAssertion[] { (PolicyAssertion)new TCPTransportAssertion() })) }));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class TCPTransportAssertion
/*     */     extends SimpleAssertion
/*     */   {
/*     */     public TCPTransportAssertion() {
/* 133 */       this(AssertionData.createAssertionData(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION), null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public TCPTransportAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 139 */       super(data, assertionParameters);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\policy\TCPTransportPolicyMapConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */