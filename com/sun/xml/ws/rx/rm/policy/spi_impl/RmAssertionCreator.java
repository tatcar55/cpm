/*     */ package com.sun.xml.ws.rx.rm.policy.spi_impl;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*     */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
/*     */ import com.sun.xml.ws.rx.rm.api.RmAssertionNamespace;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200603.AckRequestIntervalClientAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200603.AllowDuplicatesAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200603.CloseTimeoutClientAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200603.OrderedDeliveryAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200603.ResendIntervalClientAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.AckRequestIntervalAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.CloseSequenceTimeoutAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.MaintenanceTaskPeriodAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.MaxConcurrentSessionsAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.PersistentAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.metro200702.RetransmissionConfigAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.net200502.RmFlowControlAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.net200702.AcknowledgementIntervalAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.net200702.InactivityTimeoutAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.wsrm200502.Rm10Assertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.wsrm200702.DeliveryAssuranceAssertion;
/*     */ import com.sun.xml.ws.rx.rm.policy.wsrm200702.Rm11Assertion;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public final class RmAssertionCreator
/*     */   implements PolicyAssertionCreator
/*     */ {
/*  80 */   private static final Map<QName, AssertionInstantiator> instantiationMap = new HashMap<QName, AssertionInstantiator>();
/*     */   
/*     */   static {
/*  83 */     instantiationMap.put(AckRequestIntervalClientAssertion.NAME, AckRequestIntervalClientAssertion.getInstantiator());
/*  84 */     instantiationMap.put(AllowDuplicatesAssertion.NAME, AllowDuplicatesAssertion.getInstantiator());
/*  85 */     instantiationMap.put(CloseTimeoutClientAssertion.NAME, CloseTimeoutClientAssertion.getInstantiator());
/*  86 */     instantiationMap.put(OrderedDeliveryAssertion.NAME, OrderedDeliveryAssertion.getInstantiator());
/*  87 */     instantiationMap.put(ResendIntervalClientAssertion.NAME, ResendIntervalClientAssertion.getInstantiator());
/*     */ 
/*     */     
/*  90 */     instantiationMap.put(AckRequestIntervalAssertion.NAME, AckRequestIntervalAssertion.getInstantiator());
/*  91 */     instantiationMap.put(CloseSequenceTimeoutAssertion.NAME, CloseSequenceTimeoutAssertion.getInstantiator());
/*  92 */     instantiationMap.put(MaintenanceTaskPeriodAssertion.NAME, MaintenanceTaskPeriodAssertion.getInstantiator());
/*  93 */     instantiationMap.put(MaxConcurrentSessionsAssertion.NAME, MaxConcurrentSessionsAssertion.getInstantiator());
/*  94 */     instantiationMap.put(PersistentAssertion.NAME, PersistentAssertion.getInstantiator());
/*  95 */     instantiationMap.put(RetransmissionConfigAssertion.NAME, RetransmissionConfigAssertion.getInstantiator());
/*     */ 
/*     */     
/*  98 */     instantiationMap.put(RmFlowControlAssertion.NAME, RmFlowControlAssertion.getInstantiator());
/*     */ 
/*     */     
/* 101 */     instantiationMap.put(AcknowledgementIntervalAssertion.NAME, AcknowledgementIntervalAssertion.getInstantiator());
/* 102 */     instantiationMap.put(InactivityTimeoutAssertion.NAME, InactivityTimeoutAssertion.getInstantiator());
/*     */ 
/*     */     
/* 105 */     instantiationMap.put(Rm10Assertion.NAME, Rm10Assertion.getInstantiator());
/*     */ 
/*     */     
/* 108 */     instantiationMap.put(DeliveryAssuranceAssertion.NAME, DeliveryAssuranceAssertion.getInstantiator());
/* 109 */     instantiationMap.put(Rm11Assertion.NAME, Rm11Assertion.getInstantiator());
/*     */   }
/*     */ 
/*     */   
/* 113 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(RmAssertionNamespace.namespacesList());
/*     */   
/*     */   public String[] getSupportedDomainNamespaceURIs() {
/* 116 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*     */   }
/*     */   
/*     */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 120 */     AssertionInstantiator instantiator = instantiationMap.get(data.getName());
/* 121 */     if (instantiator != null) {
/* 122 */       return instantiator.newInstance(data, assertionParameters, nestedAlternative);
/*     */     }
/* 124 */     return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\spi_impl\RmAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */