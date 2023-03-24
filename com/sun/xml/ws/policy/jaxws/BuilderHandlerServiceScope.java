/*    */ package com.sun.xml.ws.policy.jaxws;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*    */ import com.sun.xml.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.ws.policy.PolicySubject;
/*    */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
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
/*    */ final class BuilderHandlerServiceScope
/*    */   extends BuilderHandler
/*    */ {
/*    */   private final QName service;
/*    */   
/*    */   BuilderHandlerServiceScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, QName service) {
/* 67 */     super(policyURIs, policyStore, policySubject);
/* 68 */     this.service = service;
/*    */   }
/*    */   
/*    */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/* 72 */     PolicyMapKey mapKey = PolicyMap.createWsdlServiceScopeKey(this.service);
/* 73 */     for (PolicySubject subject : getPolicySubjects()) {
/* 74 */       policyMapExtender.putServiceSubject(mapKey, subject);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 80 */     return this.service.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\BuilderHandlerServiceScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */