/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.TrustStore;
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
/*    */ public class TrustStore
/*    */   extends KeyStore
/*    */   implements TrustStore
/*    */ {
/* 55 */   private static QName peerAlias = new QName("peeralias");
/* 56 */   private static QName stsAlias = new QName("stsalias");
/* 57 */   private static QName serviceAlias = new QName("servicealias");
/* 58 */   private static QName certSelector = new QName("certSelector");
/* 59 */   private static QName callbackHandler = new QName("callbackHandler");
/*    */ 
/*    */   
/*    */   public TrustStore() {}
/*    */   
/*    */   public TrustStore(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 65 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   public String getPeerAlias() {
/* 68 */     return getAttributeValue(peerAlias);
/*    */   }
/*    */   
/*    */   public String getSTSAlias() {
/* 72 */     return getAttributeValue(stsAlias);
/*    */   }
/*    */   
/*    */   public String getServiceAlias() {
/* 76 */     return getAttributeValue(serviceAlias);
/*    */   }
/*    */   
/*    */   public String getCertSelectorClassName() {
/* 80 */     return getAttributeValue(certSelector);
/*    */   }
/*    */   
/*    */   public String getTrustStoreCallbackHandler() {
/* 84 */     return getAttributeValue(callbackHandler);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\TrustStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */