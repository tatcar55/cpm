/*    */ package com.sun.xml.ws.rx.rm.policy.net200702;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.SimpleAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
/*    */ import com.sun.xml.ws.rx.rm.api.ReliableMessagingFeatureBuilder;
/*    */ import com.sun.xml.ws.rx.rm.api.RmAssertionNamespace;
/*    */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*    */ import com.sun.xml.ws.rx.rm.policy.RmConfigurator;
/*    */ import java.util.Collection;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AcknowledgementIntervalAssertion
/*    */   extends SimpleAssertion
/*    */   implements RmConfigurator
/*    */ {
/* 65 */   public static final QName NAME = RmAssertionNamespace.MICROSOFT_200702.getQName("AcknowledgementInterval");
/* 66 */   private static final QName MILISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*    */   
/* 68 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 70 */         return (PolicyAssertion)new AcknowledgementIntervalAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 75 */     return instantiator;
/*    */   }
/*    */   
/*    */   private final long timeout;
/*    */   
/*    */   public AcknowledgementIntervalAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 81 */     super(data, assertionParameters);
/*    */     
/* 83 */     this.timeout = Long.parseLong(data.getAttributeValue(MILISECONDS_ATTRIBUTE_QNAME));
/*    */   }
/*    */   
/*    */   public long getTimeout() {
/* 87 */     return this.timeout;
/*    */   }
/*    */   
/*    */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 91 */     return builder.acknowledgementTransmissionInterval(this.timeout);
/*    */   }
/*    */   
/*    */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 95 */     return (RmProtocolVersion.WSRM200702 == version);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\net200702\AcknowledgementIntervalAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */