/*     */ package com.sun.xml.ws.rx.rm.policy.wsrm200702;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.ComplexAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
/*     */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeature;
/*     */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeatureBuilder;
/*     */ import com.sun.xml.ws.rx.rm.api.RmAssertionNamespace;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.policy.RmConfigurator;
/*     */ import java.util.Collection;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Rm11Assertion
/*     */   extends ComplexAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  86 */   private static final Logger LOGGER = Logger.getLogger(Rm11Assertion.class);
/*     */   
/*  88 */   public static final QName NAME = RmProtocolVersion.WSRM200702.rmAssertionName;
/*  89 */   private static final QName SEQUENCE_STR_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("SequenceSTR");
/*  90 */   private static final QName SEQUENCE_TRANSPORT_SECURITY_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("SequenceTransportSecurity");
/*  91 */   private static AssertionInstantiator instantiator = new AssertionInstantiator()
/*     */     {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  94 */         return (PolicyAssertion)new Rm11Assertion(data, assertionParameters, nestedAlternative);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  99 */     return instantiator;
/*     */   }
/*     */   private final ReliableMessagingFeature.SecurityBinding securityBinding;
/*     */   private final ReliableMessagingFeature.DeliveryAssurance deliveryAssurance;
/*     */   private final boolean isOrderedDelivery;
/*     */   
/*     */   private Rm11Assertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/* 106 */     super(data, assertionParameters, nestedAlternative);
/*     */     
/* 108 */     ReliableMessagingFeature.SecurityBinding _securityBinding = ReliableMessagingFeature.SecurityBinding.NONE;
/* 109 */     DeliveryAssuranceAssertion deliveryAssuranceAssertion = null;
/*     */     
/* 111 */     if (nestedAlternative != null) {
/* 112 */       for (PolicyAssertion nestedAssertion : nestedAlternative) {
/* 113 */         if (SEQUENCE_STR_QNAME.equals(nestedAssertion.getName())) {
/* 114 */           _securityBinding = evaluateDeliveryAssurance((_securityBinding == ReliableMessagingFeature.SecurityBinding.NONE), ReliableMessagingFeature.SecurityBinding.STR, data); continue;
/* 115 */         }  if (SEQUENCE_TRANSPORT_SECURITY_QNAME.equals(nestedAssertion.getName())) {
/* 116 */           _securityBinding = evaluateDeliveryAssurance((_securityBinding == ReliableMessagingFeature.SecurityBinding.NONE), ReliableMessagingFeature.SecurityBinding.TRANSPORT, data); continue;
/* 117 */         }  if (DeliveryAssuranceAssertion.NAME.equals(nestedAssertion.getName())) {
/* 118 */           deliveryAssuranceAssertion = (DeliveryAssuranceAssertion)nestedAssertion;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 123 */     if (deliveryAssuranceAssertion == null) {
/* 124 */       this.deliveryAssurance = ReliableMessagingFeature.DeliveryAssurance.getDefault();
/* 125 */       this.isOrderedDelivery = false;
/*     */     } else {
/* 127 */       this.deliveryAssurance = deliveryAssuranceAssertion.getDeliveryAssurance();
/* 128 */       this.isOrderedDelivery = deliveryAssuranceAssertion.isOrderedDelivery();
/*     */     } 
/*     */     
/* 131 */     this.securityBinding = _securityBinding;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeature.DeliveryAssurance getDeliveryAssurance() {
/* 135 */     return this.deliveryAssurance;
/*     */   }
/*     */   
/*     */   public boolean isOrderedDelivery() {
/* 139 */     return this.isOrderedDelivery;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeature.SecurityBinding getSecurityBinding() {
/* 143 */     return this.securityBinding;
/*     */   }
/*     */   
/*     */   private ReliableMessagingFeature.SecurityBinding evaluateDeliveryAssurance(boolean successCondition, ReliableMessagingFeature.SecurityBinding bindingOnSuccess, AssertionData data) throws AssertionCreationException {
/* 147 */     if (successCondition) {
/* 148 */       return bindingOnSuccess;
/*     */     }
/* 150 */     throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, LocalizationMessages.WSRM_1005_MULTIPLE_SECURITY_BINDINGS_IN_POLICY()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 155 */     if (builder.getProtocolVersion() != RmProtocolVersion.WSRM200702) {
/* 156 */       throw new WebServiceException(LocalizationMessages.WSRM_1002_MULTIPLE_WSRM_VERSIONS_IN_POLICY());
/*     */     }
/*     */     
/* 159 */     if (this.isOrderedDelivery) {
/* 160 */       builder = builder.enableOrderedDelivery();
/*     */     }
/*     */     
/* 163 */     return builder.deliveryAssurance(this.deliveryAssurance).securityBinding(this.securityBinding);
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 167 */     return (RmProtocolVersion.WSRM200702 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\wsrm200702\Rm11Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */