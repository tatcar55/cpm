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
/*    */ 
/*    */ 
/*    */ public class ResendIntervalClientAssertion
/*    */   extends SimpleAssertion
/*    */   implements RmConfigurator
/*    */ {
/* 68 */   public static final QName NAME = RmAssertionNamespace.METRO_CLIENT_200603.getQName("ResendInterval");
/* 69 */   private static final QName MILLISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*    */   
/* 71 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 73 */         return (PolicyAssertion)new ResendIntervalClientAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 78 */     return instantiator;
/*    */   }
/*    */   
/*    */   private final long interval;
/*    */   
/*    */   public ResendIntervalClientAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 84 */     super(data, assertionParameters);
/*    */     
/* 86 */     this.interval = Long.parseLong(getAttributeValue(MILLISECONDS_ATTRIBUTE_QNAME));
/*    */   }
/*    */   
/*    */   public long getInterval() {
/* 90 */     return this.interval;
/*    */   }
/*    */   
/*    */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 94 */     return builder.messageRetransmissionInterval(this.interval);
/*    */   }
/*    */   
/*    */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 98 */     return (RmProtocolVersion.WSRM200502 == version);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200603\ResendIntervalClientAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */