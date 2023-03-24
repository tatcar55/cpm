/*    */ package com.sun.xml.ws.tx.at.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.WsatNamespace;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
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
/*    */ public class AtAlwaysCapability
/*    */   extends WsatAssertionBase
/*    */ {
/* 54 */   public static final QName NAME = WsatNamespace.WSAT200410.createFqn("ATAlwaysCapability");
/*    */   
/*    */   public AtAlwaysCapability(boolean isOptional) {
/* 57 */     super(NAME, isOptional);
/*    */   }
/*    */   
/*    */   public AtAlwaysCapability(AssertionData data, Collection<PolicyAssertion> assertionParameters) {
/* 61 */     super(data, assertionParameters);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\AtAlwaysCapability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */