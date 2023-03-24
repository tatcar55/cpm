/*    */ package com.sun.xml.ws.rx.rm.policy.metro200702;
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
/*    */ public class PersistentAssertion
/*    */   extends SimpleAssertion
/*    */   implements RmConfigurator
/*    */ {
/* 60 */   public static final QName NAME = RmAssertionNamespace.METRO_200702.getQName("Persistent");
/*    */   
/* 62 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 64 */         return (PolicyAssertion)new PersistentAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 69 */     return instantiator;
/*    */   }
/*    */   
/*    */   public PersistentAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 73 */     super(data, assertionParameters);
/*    */   }
/*    */   
/*    */   public ReliableMessagingFeatureBuilder update(ReliableMessagingFeatureBuilder builder) {
/* 77 */     return builder.enablePersistence();
/*    */   }
/*    */   
/*    */   public boolean isCompatibleWith(RmProtocolVersion version) {
/* 81 */     return (RmProtocolVersion.WSRM200702 == version);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\policy\metro200702\PersistentAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */