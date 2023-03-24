/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.CertStoreConfig;
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
/*    */ public class CertStoreConfig
/*    */   extends PolicyAssertion
/*    */   implements CertStoreConfig
/*    */ {
/* 64 */   private static QName cbh = new QName("callbackHandler");
/* 65 */   private static QName certSelector = new QName("certSelector");
/* 66 */   private static QName crlSelector = new QName("crlSelector");
/*    */ 
/*    */   
/*    */   public CertStoreConfig(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 70 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */ 
/*    */   
/*    */   public CertStoreConfig() {}
/*    */   
/*    */   public String getCallbackHandlerClassName() {
/* 77 */     return getAttributeValue(cbh);
/*    */   }
/*    */   
/*    */   public String getCertSelectorClassName() {
/* 81 */     return getAttributeValue(certSelector);
/*    */   }
/*    */   
/*    */   public String getCRLSelectorClassName() {
/* 85 */     return getAttributeValue(crlSelector);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\CertStoreConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */