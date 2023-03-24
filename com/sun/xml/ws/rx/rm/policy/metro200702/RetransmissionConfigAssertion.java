/*     */ package com.sun.xml.ws.rx.rm.policy.metro200702;
/*     */ 
/*     */ import com.sun.istack.NotNull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RetransmissionConfigAssertion
/*     */   extends ComplexAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  67 */   public static final QName NAME = RmAssertionNamespace.METRO_200702.getQName("RetransmissionConfig");
/*     */   
/*  69 */   private static final Logger LOGGER = Logger.getLogger(RetransmissionConfigAssertion.class);
/*     */   
/*  71 */   private static final QName INTERVAL_PARAMETER_QNAME = RmAssertionNamespace.METRO_200702.getQName("Interval");
/*  72 */   private static final QName ALGORITHM_PARAMETER_QNAME = RmAssertionNamespace.METRO_200702.getQName("Algorithm");
/*  73 */   private static final QName MAX_RETRIES_PARAMETER_QNAME = RmAssertionNamespace.METRO_200702.getQName("MaxRetries");
/*     */   
/*  75 */   private static final QName MILLISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*     */   
/*  77 */   private static AssertionInstantiator instantiator = new AssertionInstantiator()
/*     */     {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  80 */         return (PolicyAssertion)new RetransmissionConfigAssertion(data, assertionParameters, nestedAlternative);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  85 */     return instantiator;
/*     */   }
/*     */ 
/*     */   
/*     */   private final long interval;
/*     */   
/*     */   private RetransmissionConfigAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  92 */     super(data, assertionParameters, nestedAlternative);
/*     */     
/*  94 */     if (assertionParameters == null || assertionParameters.isEmpty())
/*     */     {
/*  96 */       throw new AssertionCreationException(data, "No assertion parameters found.");
/*     */     }
/*  98 */     PolicyAssertion _interval = getParameter(INTERVAL_PARAMETER_QNAME, data, assertionParameters);
/*  99 */     this.interval = (_interval == null) ? 2000L : Long.parseLong(_interval.getAttributeValue(MILLISECONDS_ATTRIBUTE_QNAME));
/*     */     
/* 101 */     PolicyAssertion _maxRetries = getParameter(MAX_RETRIES_PARAMETER_QNAME, data, assertionParameters);
/* 102 */     this.maxRetries = (_maxRetries == null) ? -1L : Long.parseLong(_maxRetries.getValue());
/*     */     
/* 104 */     PolicyAssertion algorithmParameter = getParameter(ALGORITHM_PARAMETER_QNAME, data, assertionParameters);
/* 105 */     ReliableMessagingFeature.BackoffAlgorithm _algorithm = (algorithmParameter == null) ? null : ReliableMessagingFeature.BackoffAlgorithm.parse(algorithmParameter.getValue());
/* 106 */     this.algorithm = (_algorithm == null) ? ReliableMessagingFeature.BackoffAlgorithm.getDefault() : _algorithm;
/*     */   }
/*     */   private final long maxRetries; private final ReliableMessagingFeature.BackoffAlgorithm algorithm;
/*     */   private static PolicyAssertion getParameter(@NotNull QName parameterName, AssertionData data, @NotNull Collection<? extends PolicyAssertion> assertionParameters) throws AssertionCreationException {
/* 110 */     assert parameterName != null;
/* 111 */     assert assertionParameters != null;
/*     */     
/* 113 */     PolicyAssertion parameter = null;
/* 114 */     boolean parameterSet = false;
/*     */     
/* 116 */     for (PolicyAssertion assertion : assertionParameters) {
/* 117 */       if (parameterName.equals(assertion.getName())) {
/* 118 */         if (parameterSet) {
/* 119 */           throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, LocalizationMessages.WSRM_1007_MULTIPLE_OCCURENCES_OF_ASSERTION_PARAMETER(parameterName, NAME)));
/*     */         }
/*     */ 
/*     */         
/* 123 */         parameter = assertion;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 128 */     return parameter;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeature.BackoffAlgorithm getAlgorithm() {
/* 132 */     return this.algorithm;
/*     */   }
/*     */   
/*     */   public long getInterval() {
/* 136 */     return this.interval;
/*     */   }
/*     */   
/*     */   public long getMaxRetries() {
/* 140 */     return this.maxRetries;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 144 */     return builder.messageRetransmissionInterval(this.interval).retransmissionBackoffAlgorithm(this.algorithm).maxMessageRetransmissionCount(this.maxRetries);
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 148 */     return (RmProtocolVersion.WSRM200702 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200702\RetransmissionConfigAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */