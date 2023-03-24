/*     */ package com.sun.xml.ws.rx.rm.policy.spi_impl;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
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
/*     */ import com.sun.xml.ws.rx.rm.policy.wsrm200702.Rm11Assertion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class RmAssertionValidator
/*     */   implements PolicyAssertionValidator
/*     */ {
/*  72 */   private static final ArrayList<QName> SERVER_SIDE_ASSERTIONS = new ArrayList<QName>(13);
/*  73 */   private static final ArrayList<QName> CLIENT_SIDE_ASSERTIONS = new ArrayList<QName>(16);
/*     */   
/*  75 */   private static final List<String> SUPPORTED_DOMAINS = Collections.unmodifiableList(RmAssertionNamespace.namespacesList());
/*     */ 
/*     */   
/*     */   static {
/*  79 */     SERVER_SIDE_ASSERTIONS.add(Rm10Assertion.NAME);
/*     */     
/*  81 */     SERVER_SIDE_ASSERTIONS.add(Rm11Assertion.NAME);
/*     */     
/*  83 */     SERVER_SIDE_ASSERTIONS.add(AllowDuplicatesAssertion.NAME);
/*  84 */     SERVER_SIDE_ASSERTIONS.add(OrderedDeliveryAssertion.NAME);
/*     */     
/*  86 */     SERVER_SIDE_ASSERTIONS.add(AckRequestIntervalAssertion.NAME);
/*  87 */     SERVER_SIDE_ASSERTIONS.add(CloseSequenceTimeoutAssertion.NAME);
/*  88 */     SERVER_SIDE_ASSERTIONS.add(MaintenanceTaskPeriodAssertion.NAME);
/*  89 */     SERVER_SIDE_ASSERTIONS.add(MaxConcurrentSessionsAssertion.NAME);
/*  90 */     SERVER_SIDE_ASSERTIONS.add(PersistentAssertion.NAME);
/*  91 */     SERVER_SIDE_ASSERTIONS.add(RetransmissionConfigAssertion.NAME);
/*     */     
/*  93 */     SERVER_SIDE_ASSERTIONS.add(RmFlowControlAssertion.NAME);
/*     */     
/*  95 */     SERVER_SIDE_ASSERTIONS.add(AcknowledgementIntervalAssertion.NAME);
/*  96 */     SERVER_SIDE_ASSERTIONS.add(InactivityTimeoutAssertion.NAME);
/*     */ 
/*     */     
/*  99 */     CLIENT_SIDE_ASSERTIONS.add(AckRequestIntervalClientAssertion.NAME);
/* 100 */     CLIENT_SIDE_ASSERTIONS.add(CloseTimeoutClientAssertion.NAME);
/* 101 */     CLIENT_SIDE_ASSERTIONS.add(ResendIntervalClientAssertion.NAME);
/*     */     
/* 103 */     CLIENT_SIDE_ASSERTIONS.addAll(SERVER_SIDE_ASSERTIONS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 110 */     return CLIENT_SIDE_ASSERTIONS.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 114 */     QName assertionName = assertion.getName();
/* 115 */     if (SERVER_SIDE_ASSERTIONS.contains(assertionName))
/* 116 */       return PolicyAssertionValidator.Fitness.SUPPORTED; 
/* 117 */     if (CLIENT_SIDE_ASSERTIONS.contains(assertionName)) {
/* 118 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */     }
/* 120 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] declareSupportedDomains() {
/* 125 */     return SUPPORTED_DOMAINS.<String>toArray(new String[SUPPORTED_DOMAINS.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\spi_impl\RmAssertionValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */