/*    */ package com.sun.xml.ws.rx.mc.policy.wsmc200702;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.SimpleAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.rx.mc.policy.McAssertionNamespace;
/*    */ import com.sun.xml.ws.rx.policy.AssertionInstantiator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MakeConnectionSupportedAssertion
/*    */   extends SimpleAssertion
/*    */ {
/* 68 */   public static final QName NAME = McAssertionNamespace.WSMC_200702.getQName("MCSupported");
/*    */   
/* 70 */   private static AssertionInstantiator instantiator = new AssertionInstantiator() {
/*    */       public PolicyAssertion newInstance(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 72 */         return (PolicyAssertion)new MakeConnectionSupportedAssertion(data, assertionParameters);
/*    */       }
/*    */     };
/*    */   
/*    */   public static AssertionInstantiator getInstantiator() {
/* 77 */     return instantiator;
/*    */   }
/*    */   
/*    */   public MakeConnectionSupportedAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 81 */     super(data, assertionParameters);
/*    */   }
/*    */   
/*    */   public MakeConnectionSupportedAssertion() {
/* 85 */     super(AssertionData.createAssertionData(NAME), null);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\policy\wsmc200702\MakeConnectionSupportedAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */