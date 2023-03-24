/*     */ package com.sun.xml.ws.rx.rm.policy.metro200603;
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
/*     */ 
/*     */ 
/*     */ public class AllowDuplicatesAssertion
/*     */   extends SimpleAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  80 */   public static final QName NAME = RmAssertionNamespace.METRO_200603.getQName("AllowDuplicates");
/*     */   
/*  82 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/*  84 */         return (PolicyAssertion)new AllowDuplicatesAssertion(data, assertionParameters);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  89 */     return instantiator;
/*     */   }
/*     */   
/*     */   public AllowDuplicatesAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/*  93 */     super(data, assertionParameters);
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/*  97 */     if (builder.getProtocolVersion() != RmProtocolVersion.WSRM200502) {
/*  98 */       throw new WebServiceException(LocalizationMessages.WSRM_1001_ASSERTION_NOT_COMPATIBLE_WITH_RM_VERSION(NAME, builder.getProtocolVersion()));
/*     */     }
/*     */     
/* 101 */     return builder.deliveryAssurance(ReliableMessagingFeature.DeliveryAssurance.AT_LEAST_ONCE);
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 105 */     return (RmProtocolVersion.WSRM200502 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200603\AllowDuplicatesAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */