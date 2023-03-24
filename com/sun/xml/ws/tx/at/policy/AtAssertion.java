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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AtAssertion
/*    */   extends WsatAssertionBase
/*    */ {
/*    */   private final WsatNamespace namespace;
/*    */   
/*    */   public static QName nameForNamespace(WsatNamespace ns) {
/* 60 */     return ns.createFqn("ATAssertion");
/*    */   }
/*    */   
/*    */   public AtAssertion(WsatNamespace ns, boolean isOptional) {
/* 64 */     super(nameForNamespace(ns), isOptional);
/*    */     
/* 66 */     this.namespace = ns;
/*    */   }
/*    */   
/*    */   public AtAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 70 */     super(data, assertionParameters);
/*    */     
/* 72 */     this.namespace = WsatNamespace.forNamespaceUri(data.getName().getNamespaceURI());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\policy\AtAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */