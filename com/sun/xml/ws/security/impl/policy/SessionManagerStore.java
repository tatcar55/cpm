/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.SessionManagerStore;
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
/*    */ public class SessionManagerStore
/*    */   extends PolicyAssertion
/*    */   implements SessionManagerStore
/*    */ {
/* 55 */   private static QName sessionTimeout = new QName("sessionTimeout");
/* 56 */   private static QName sessionThreshold = new QName("sessionThreshold");
/*    */   
/*    */   public SessionManagerStore(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 59 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */ 
/*    */   
/*    */   public SessionManagerStore() {}
/*    */   
/*    */   public String getSessionTimeOut() {
/* 66 */     return getAttributeValue(sessionTimeout);
/*    */   }
/*    */   public String getSessionThreshold() {
/* 69 */     return getAttributeValue(sessionThreshold);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SessionManagerStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */