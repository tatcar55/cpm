/*     */ package com.sun.xml.ws.rx.rm.policy.net200502;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.ComplexAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmFlowControlAssertion
/*     */   extends ComplexAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  70 */   public static final QName NAME = RmAssertionNamespace.MICROSOFT_200502.getQName("RmFlowControl");
/*     */   
/*  72 */   private static final Logger LOGGER = Logger.getLogger(RmFlowControlAssertion.class);
/*  73 */   private static final QName BUFFER_SIZE_ASSERTION_QNAME = RmAssertionNamespace.MICROSOFT_200502.getQName("MaxReceiveBufferSize");
/*     */   private static final long DEFAULT_DESTINATION_BUFFER_QUOTA = 32L;
/*     */   
/*  76 */   private static AssertionInstantiator instantiator = new AssertionInstantiator()
/*     */     {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  79 */         return (PolicyAssertion)new RmFlowControlAssertion(data, assertionParameters, nestedAlternative);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  84 */     return instantiator;
/*     */   }
/*     */ 
/*     */   
/*     */   private RmFlowControlAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  89 */     super(data, assertionParameters, nestedAlternative);
/*     */     
/*  91 */     long _maxBufferSize = 32L;
/*  92 */     boolean bufferSizeSet = false;
/*  93 */     if (assertionParameters != null) {
/*  94 */       for (PolicyAssertion assertion : assertionParameters) {
/*  95 */         if (BUFFER_SIZE_ASSERTION_QNAME.equals(assertion.getName())) {
/*  96 */           if (bufferSizeSet) {
/*  97 */             throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, LocalizationMessages.WSRM_1006_MULTIPLE_BUFFER_SIZES_IN_POLICY()));
/*     */           }
/*  99 */           _maxBufferSize = Long.parseLong(assertion.getValue());
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 104 */     this.maxBufferSize = _maxBufferSize;
/*     */   }
/*     */   private final long maxBufferSize;
/*     */   public long getMaximumBufferSize() {
/* 108 */     return this.maxBufferSize;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 112 */     return builder.destinationBufferQuota(this.maxBufferSize);
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 116 */     return (RmProtocolVersion.WSRM200502 == version || RmProtocolVersion.WSRM200702 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\net200502\RmFlowControlAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */