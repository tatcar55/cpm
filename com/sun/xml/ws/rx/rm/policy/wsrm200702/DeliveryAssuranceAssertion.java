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
/*     */ import com.sun.xml.ws.rx.rm.api.RmAssertionNamespace;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeliveryAssuranceAssertion
/*     */   extends ComplexAssertion
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(DeliveryAssuranceAssertion.class);
/*  74 */   private static final QName EXACTLY_ONCE_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("ExactlyOnce");
/*  75 */   private static final QName AT_LEAST_ONCE_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("AtLeastOnce");
/*  76 */   private static final QName AT_MOST_ONCE_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("AtMostOnce");
/*  77 */   private static final QName IN_ORDER_QNAME = RmAssertionNamespace.WSRMP_200702.getQName("InOrder");
/*  78 */   public static final QName NAME = RmAssertionNamespace.WSRMP_200702.getQName("DeliveryAssurance");
/*  79 */   private static AssertionInstantiator instantiator = new AssertionInstantiator()
/*     */     {
/*     */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  82 */         return (PolicyAssertion)new DeliveryAssuranceAssertion(data, assertionParameters, nestedAlternative);
/*     */       }
/*     */     };
/*     */   
/*     */   public static AssertionInstantiator getInstantiator() {
/*  87 */     return instantiator;
/*     */   }
/*     */   private final ReliableMessagingFeature.DeliveryAssurance deliveryAssurance;
/*     */   private final boolean orderedDelivery;
/*     */   
/*     */   private DeliveryAssuranceAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/*  93 */     super(data, assertionParameters, nestedAlternative);
/*     */     
/*  95 */     ReliableMessagingFeature.DeliveryAssurance _deliveryAssurance = null;
/*  96 */     boolean _orderedDelivery = false;
/*     */     
/*  98 */     if (nestedAlternative != null) {
/*  99 */       for (PolicyAssertion nestedAssertion : nestedAlternative) {
/*     */         
/* 101 */         if (EXACTLY_ONCE_QNAME.equals(nestedAssertion.getName())) {
/* 102 */           _deliveryAssurance = evaluateDeliveryAssurance((_deliveryAssurance == null), ReliableMessagingFeature.DeliveryAssurance.EXACTLY_ONCE, data); continue;
/* 103 */         }  if (AT_LEAST_ONCE_QNAME.equals(nestedAssertion.getName())) {
/* 104 */           _deliveryAssurance = evaluateDeliveryAssurance((_deliveryAssurance == null), ReliableMessagingFeature.DeliveryAssurance.AT_LEAST_ONCE, data); continue;
/* 105 */         }  if (AT_MOST_ONCE_QNAME.equals(nestedAssertion.getName())) {
/* 106 */           _deliveryAssurance = evaluateDeliveryAssurance((_deliveryAssurance == null), ReliableMessagingFeature.DeliveryAssurance.AT_MOST_ONCE, data); continue;
/* 107 */         }  if (IN_ORDER_QNAME.equals(nestedAssertion.getName())) {
/* 108 */           _orderedDelivery = true;
/*     */         }
/*     */       } 
/*     */     }
/* 112 */     this.deliveryAssurance = (_deliveryAssurance == null) ? ReliableMessagingFeature.DeliveryAssurance.getDefault() : _deliveryAssurance;
/* 113 */     this.orderedDelivery = _orderedDelivery;
/*     */   }
/*     */   
/*     */   public ReliableMessagingFeature.DeliveryAssurance getDeliveryAssurance() {
/* 117 */     return this.deliveryAssurance;
/*     */   }
/*     */   
/*     */   public boolean isOrderedDelivery() {
/* 121 */     return this.orderedDelivery;
/*     */   }
/*     */   
/*     */   private ReliableMessagingFeature.DeliveryAssurance evaluateDeliveryAssurance(boolean successCondition, ReliableMessagingFeature.DeliveryAssurance daOnSuccess, AssertionData data) throws AssertionCreationException {
/* 125 */     if (successCondition) {
/* 126 */       return daOnSuccess;
/*     */     }
/* 128 */     throw (AssertionCreationException)LOGGER.logSevereException(new AssertionCreationException(data, LocalizationMessages.WSRM_1003_MUTLIPLE_DA_TYPES_IN_POLICY()));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\wsrm200702\DeliveryAssuranceAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */