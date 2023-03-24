/*    */ package com.sun.xml.ws.security.addressing.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*    */ import java.util.ArrayList;
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
/*    */ public class WsawAddressingPolicyValidator
/*    */   implements PolicyAssertionValidator
/*    */ {
/* 59 */   private static final ArrayList<QName> supportedAssertions = new ArrayList<QName>();
/*    */   
/*    */   static {
/* 62 */     supportedAssertions.add(new QName(AddressingVersion.W3C.policyNsUri, "UsingAddressing"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 72 */     return supportedAssertions.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 76 */     return supportedAssertions.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public String[] declareSupportedDomains() {
/* 80 */     return new String[] { AddressingVersion.W3C.policyNsUri };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\policy\WsawAddressingPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */