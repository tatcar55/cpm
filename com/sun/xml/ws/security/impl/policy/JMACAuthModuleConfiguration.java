/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.security.policy.JMACAuthModuleConfiguration;
/*    */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JMACAuthModuleConfiguration
/*    */   extends PolicyAssertion
/*    */   implements JMACAuthModuleConfiguration, SecurityAssertionValidator
/*    */ {
/*    */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 58 */     return null;
/*    */   }
/*    */   
/*    */   public Iterator<? extends PolicyAssertion> getAuthModules() {
/* 62 */     return null;
/*    */   }
/*    */   
/*    */   public String getOverrideDefaultTokenValidation() {
/* 66 */     return null;
/*    */   }
/*    */   
/*    */   public String getOverrideDefaultAuthModules() {
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\JMACAuthModuleConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */