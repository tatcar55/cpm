/*    */ package com.sun.xml.ws.config.management.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.config.management.policy.ManagedClientAssertion;
/*    */ import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
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
/*    */ public class ManagementPolicyValidator
/*    */   implements PolicyAssertionValidator
/*    */ {
/*    */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 60 */     QName assertionName = assertion.getName();
/* 61 */     if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(assertionName)) {
/* 62 */       return PolicyAssertionValidator.Fitness.SUPPORTED;
/*    */     }
/* 64 */     if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(assertionName)) {
/* 65 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*    */     }
/*    */     
/* 68 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 73 */     QName assertionName = assertion.getName();
/* 74 */     if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(assertionName)) {
/* 75 */       return PolicyAssertionValidator.Fitness.SUPPORTED;
/*    */     }
/* 77 */     if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(assertionName)) {
/* 78 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*    */     }
/*    */     
/* 81 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] declareSupportedDomains() {
/* 86 */     return new String[] { "http://java.sun.com/xml/ns/metro/management" };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\policy\ManagementPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */