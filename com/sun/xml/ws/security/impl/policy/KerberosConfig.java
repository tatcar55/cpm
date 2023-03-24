/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.KerberosConfig;
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
/*    */ public class KerberosConfig
/*    */   extends PolicyAssertion
/*    */   implements KerberosConfig
/*    */ {
/* 55 */   private static QName loginModule = new QName("loginModule");
/* 56 */   private static QName servicePrincipal = new QName("servicePrincipal");
/* 57 */   private static QName credentialDelegation = new QName("credentialDelegation");
/*    */ 
/*    */   
/*    */   public KerberosConfig() {}
/*    */ 
/*    */   
/*    */   public KerberosConfig(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 64 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public String getLoginModule() {
/* 68 */     return getAttributeValue(loginModule);
/*    */   }
/*    */   
/*    */   public String getServicePrincipal() {
/* 72 */     return getAttributeValue(servicePrincipal);
/*    */   }
/*    */   
/*    */   public String getCredentialDelegation() {
/* 76 */     return getAttributeValue(credentialDelegation);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\KerberosConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */