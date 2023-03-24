/*     */ package com.sun.xml.ws.rx.rm.policy.wsrm200502;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.SimpleAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
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
/*     */ public final class Rm10Assertion
/*     */   extends SimpleAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  78 */   public static final QName NAME = RmProtocolVersion.WSRM200502.rmAssertionName;
/*  79 */   private static final QName INACTIVITY_TIMEOUT_QNAME = RmAssertionNamespace.WSRMP_200502.getQName("InactivityTimeout");
/*  80 */   private static final QName RETRANSMITTION_INTERVAL_QNAME = RmAssertionNamespace.WSRMP_200502.getQName("BaseRetransmissionInterval");
/*  81 */   private static final QName EXPONENTIAL_BACKOFF_QNAME = RmAssertionNamespace.WSRMP_200502.getQName("ExponentialBackoff");
/*  82 */   private static final QName MILISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*  83 */   private static AssertionInstantiator instantiator = new AssertionInstantiator()
/*     */     {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/*  86 */         return (PolicyAssertion)new Rm10Assertion(data, assertionParameters);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  91 */     return instantiator;
/*     */   }
/*     */   private final long inactivityTimeout;
/*     */   private final long retransmittionInterval;
/*     */   private final boolean useExponentialBackoffAlgorithm;
/*     */   
/*     */   private Rm10Assertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/*  98 */     super(data, assertionParameters);
/*     */     
/* 100 */     long _inactivityTimeout = 600000L;
/* 101 */     long _retransmittionInterval = 2000L;
/* 102 */     boolean _useExponentialBackoffAlgorithm = false;
/*     */     
/* 104 */     if (assertionParameters != null) {
/* 105 */       for (PolicyAssertion parameter : assertionParameters) {
/* 106 */         if (INACTIVITY_TIMEOUT_QNAME.equals(parameter.getName())) {
/* 107 */           _inactivityTimeout = Long.parseLong(parameter.getAttributeValue(MILISECONDS_ATTRIBUTE_QNAME)); continue;
/* 108 */         }  if (RETRANSMITTION_INTERVAL_QNAME.equals(parameter.getName())) {
/* 109 */           _retransmittionInterval = Long.parseLong(parameter.getAttributeValue(MILISECONDS_ATTRIBUTE_QNAME)); continue;
/* 110 */         }  if (EXPONENTIAL_BACKOFF_QNAME.equals(parameter.getName())) {
/* 111 */           _useExponentialBackoffAlgorithm = true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 116 */     this.inactivityTimeout = _inactivityTimeout;
/* 117 */     this.retransmittionInterval = _retransmittionInterval;
/* 118 */     this.useExponentialBackoffAlgorithm = _useExponentialBackoffAlgorithm;
/*     */   }
/*     */   
/*     */   public long getInactivityTimeout() {
/* 122 */     return this.inactivityTimeout;
/*     */   }
/*     */   
/*     */   public long getBaseRetransmittionInterval() {
/* 126 */     return this.retransmittionInterval;
/*     */   }
/*     */   
/*     */   public boolean useExponentialBackoffAlgorithm() {
/* 130 */     return this.useExponentialBackoffAlgorithm;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 134 */     if (builder.getProtocolVersion() != RmProtocolVersion.WSRM200502) {
/* 135 */       throw new WebServiceException(LocalizationMessages.WSRM_1002_MULTIPLE_WSRM_VERSIONS_IN_POLICY());
/*     */     }
/*     */     
/* 138 */     if (this.inactivityTimeout != 600000L) {
/* 139 */       builder.sequenceInactivityTimeout(this.inactivityTimeout);
/*     */     }
/* 141 */     if (this.inactivityTimeout != 2000L) {
/* 142 */       builder.messageRetransmissionInterval(this.retransmittionInterval);
/*     */     }
/*     */     
/* 145 */     if (this.useExponentialBackoffAlgorithm) {
/* 146 */       builder.retransmissionBackoffAlgorithm(ReliableMessagingFeature.BackoffAlgorithm.EXPONENTIAL);
/*     */     }
/*     */     
/* 149 */     return builder;
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 153 */     return (RmProtocolVersion.WSRM200502 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\wsrm200502\Rm10Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */