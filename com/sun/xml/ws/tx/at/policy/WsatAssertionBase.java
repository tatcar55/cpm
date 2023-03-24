/*    */ package com.sun.xml.ws.tx.at.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.SimpleAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
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
/*    */ abstract class WsatAssertionBase
/*    */   extends SimpleAssertion
/*    */ {
/* 58 */   private static final QName WSP2002_OPTIONAL = new QName("http://schemas.xmlsoap.org/ws/2002/12/policy", "Optional");
/*    */   
/*    */   private static AssertionData createAssertionData(QName assertionQName, boolean isOptional) {
/* 61 */     AssertionData result = AssertionData.createAssertionData(assertionQName);
/* 62 */     result.setOptionalAttribute(isOptional);
/* 63 */     if (isOptional)
/*    */     {
/* 65 */       result.setAttribute(WSP2002_OPTIONAL, "true");
/*    */     }
/* 67 */     return result;
/*    */   }
/*    */   
/*    */   WsatAssertionBase(QName wsatPolicyAssertionName, boolean isOptional) {
/* 71 */     super(createAssertionData(wsatPolicyAssertionName, isOptional), null);
/*    */   }
/*    */   
/*    */   public WsatAssertionBase(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 75 */     super(data, assertionParameters);
/* 76 */     if (data.isOptionalAttributeSet())
/*    */     {
/* 78 */       data.setAttribute(WSP2002_OPTIONAL, "true");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\WsatAssertionBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */