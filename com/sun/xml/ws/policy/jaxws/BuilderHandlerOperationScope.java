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
/*    */ 
/*    */ final class BuilderHandlerOperationScope
/*    */   extends BuilderHandler
/*    */ {
/*    */   private final QName service;
/*    */   private final QName port;
/*    */   private final QName operation;
/*    */   
/*    */   BuilderHandlerOperationScope(Collection<String> policyURIs, Map<String, PolicySourceModel> policyStore, Object policySubject, QName service, QName port, QName operation) {
/* 70 */     super(policyURIs, policyStore, policySubject);
/* 71 */     this.service = service;
/* 72 */     this.port = port;
/* 73 */     this.operation = operation;
/*    */   }
/*    */   
/*    */   protected void doPopulate(PolicyMapExtender policyMapExtender) throws PolicyException {
/* 77 */     PolicyMapKey mapKey = PolicyMap.createWsdlOperationScopeKey(this.service, this.port, this.operation);
/* 78 */     for (PolicySubject subject : getPolicySubjects())
/* 79 */       policyMapExtender.putOperationSubject(mapKey, subject); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\BuilderHandlerOperationScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */