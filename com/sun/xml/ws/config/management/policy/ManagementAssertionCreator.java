/*    */ package com.sun.xml.ws.config.management.policy;
/*    */ 
/*    */ import com.sun.xml.ws.api.config.management.policy.ManagedClientAssertion;
/*    */ import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
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
/*    */ public class ManagementAssertionCreator
/*    */   implements PolicyAssertionCreator
/*    */ {
/*    */   public String[] getSupportedDomainNamespaceURIs() {
/* 63 */     return new String[] { "http://java.sun.com/xml/ns/metro/management" };
/*    */   }
/*    */ 
/*    */   
/*    */   public PolicyAssertion createAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative, PolicyAssertionCreator defaultCreator) throws AssertionCreationException {
/* 68 */     QName name = data.getName();
/* 69 */     if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(name)) {
/* 70 */       return (PolicyAssertion)new ManagedServiceAssertion(data, assertionParameters);
/*    */     }
/* 72 */     if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(name)) {
/* 73 */       return (PolicyAssertion)new ManagedClientAssertion(data, assertionParameters);
/*    */     }
/*    */     
/* 76 */     return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\policy\ManagementAssertionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */