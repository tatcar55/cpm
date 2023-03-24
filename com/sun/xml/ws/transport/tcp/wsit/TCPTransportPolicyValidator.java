/*    */ package com.sun.xml.ws.transport.tcp.wsit;
/*    */ 
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
/*    */ public final class TCPTransportPolicyValidator
/*    */   implements PolicyAssertionValidator
/*    */ {
/* 56 */   private static final ArrayList<QName> clientSupportedAssertions = new ArrayList<QName>(2);
/* 57 */   private static final ArrayList<QName> commonSupportedAssertions = new ArrayList<QName>(2);
/*    */   
/*    */   static {
/* 60 */     clientSupportedAssertions.add(TCPConstants.SELECT_OPTIMAL_TRANSPORT_ASSERTION);
/* 61 */     commonSupportedAssertions.add(TCPConstants.TCPTRANSPORT_POLICY_ASSERTION);
/* 62 */     commonSupportedAssertions.add(TCPConstants.TCPTRANSPORT_CONNECTION_MANAGEMENT_ASSERTION);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 70 */     return (clientSupportedAssertions.contains(assertion.getName()) || commonSupportedAssertions.contains(assertion.getName())) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 75 */     return commonSupportedAssertions.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*    */   }
/*    */   
/*    */   public String[] declareSupportedDomains() {
/* 79 */     return new String[] { "http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp/service", "http://java.sun.com/xml/ns/wsit/2006/09/policy/transport/client", "http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp" };
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\TCPTransportPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */