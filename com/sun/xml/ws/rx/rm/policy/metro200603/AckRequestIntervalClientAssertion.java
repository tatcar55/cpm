/*    */ package com.sun.xml.ws.rx.rm.policy.metro200603;
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
/*    */ 
/*    */ public class AckRequestIntervalClientAssertion
/*    */   extends SimpleAssertion
/*    */   implements RmConfigurator
/*    */ {
/* 66 */   public static final QName NAME = RmAssertionNamespace.METRO_CLIENT_200603.getQName("AckRequestInterval");
/* 67 */   private static final QName MILLISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*    */   
/* 69 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 71 */         return (PolicyAssertion)new AckRequestIntervalClientAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 76 */     return instantiator;
/*    */   }
/*    */   
/*    */   private final long interval;
/*    */   
/*    */   private AckRequestIntervalClientAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 82 */     super(data, assertionParameters);
/*    */     
/* 84 */     this.interval = Long.parseLong(getAttributeValue(MILLISECONDS_ATTRIBUTE_QNAME));
/*    */   }
/*    */   
/*    */   public long getInterval() {
/* 88 */     return this.interval;
/*    */   }
/*    */   
/*    */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 92 */     return builder.ackRequestTransmissionInterval(this.interval);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 97 */     return (RmProtocolVersion.WSRM200502 == version);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200603\AckRequestIntervalClientAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */