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
/*    */ public class CloseTimeoutClientAssertion
/*    */   extends SimpleAssertion
/*    */   implements RmConfigurator
/*    */ {
/* 64 */   public static final QName NAME = RmAssertionNamespace.METRO_CLIENT_200603.getQName("CloseTimeout");
/* 65 */   private static final QName MILLISECONDS_ATTRIBUTE_QNAME = new QName("", "Milliseconds");
/*    */   
/* 67 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 69 */         return (PolicyAssertion)new CloseTimeoutClientAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 74 */     return instantiator;
/*    */   }
/*    */   
/*    */   private final long timeout;
/*    */   
/*    */   public CloseTimeoutClientAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 80 */     super(data, assertionParameters);
/*    */     
/* 82 */     this.timeout = Long.parseLong(data.getAttributeValue(MILLISECONDS_ATTRIBUTE_QNAME));
/*    */   }
/*    */   
/*    */   public long getTimeout() {
/* 86 */     return this.timeout;
/*    */   }
/*    */   
/*    */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 90 */     return builder.closeSequenceOperationTimeout(this.timeout);
/*    */   }
/*    */   
/*    */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 94 */     return (RmProtocolVersion.WSRM200502 == version);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200603\CloseTimeoutClientAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */