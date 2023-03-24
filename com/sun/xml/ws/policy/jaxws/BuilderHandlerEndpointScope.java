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
/*    */ final class BuilderHandlerEndpointScope
/*    */   extends BuilderHandler
/*    */ {
/*    */   private final QName service;
/*    */   private final QName port;
/*    */   
/*    */   BuilderHandlerEndpointScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, QName service, QName port) {
/* 65 */     super(policyURIs, policyStore, policySubject);
/* 66 */     this.service = service;
/* 67 */     this.port = port;
/*    */   }
/*    */   
/*    */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/* 71 */     PolicyMapKey mapKey = PolicyMap.createWsdlEndpointScopeKey(this.service, this.port);
/* 72 */     for (PolicySubject subject : getPolicySubjects()) {
/* 73 */       policyMapExtender.putEndpointSubject(mapKey, subject);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 79 */     return this.service.toString() + ":" + this.port.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\BuilderHandlerEndpointScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */