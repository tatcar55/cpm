/*     */ package com.sun.xml.ws.rx.rm.policy.metro200603;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.SimpleAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
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
/*     */ public class OrderedDeliveryAssertion
/*     */   extends SimpleAssertion
/*     */   implements RmConfigurator
/*     */ {
/*  81 */   public static final QName NAME = RmAssertionNamespace.METRO_200603.getQName("Ordered");
/*     */   
/*  83 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/*  85 */         return (PolicyAssertion)new OrderedDeliveryAssertion(data, assertionParameters);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  90 */     return instantiator;
/*     */   }
/*     */   
/*     */   public OrderedDeliveryAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/*  94 */     super(data, assertionParameters);
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/*  98 */     if (builder.getProtocolVersion() != RmProtocolVersion.WSRM200502) {
/*  99 */       throw new WebServiceException(LocalizationMessages.WSRM_1001_ASSERTION_NOT_COMPATIBLE_WITH_RM_VERSION(NAME, builder.getProtocolVersion()));
/*     */     }
/*     */     
/* 102 */     return builder.enableOrderedDelivery();
/*     */   }
/*     */   
/*     */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 106 */     return (RmProtocolVersion.WSRM200502 == version);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200603\OrderedDeliveryAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */