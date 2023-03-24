/*    */ package com.sun.xml.ws.policy.subject;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyMap;
/*    */ import com.sun.xml.ws.policy.PolicyMapKey;
/*    */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
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
/*    */ public class PolicyMapKeyConverter
/*    */ {
/* 56 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMapKeyConverter.class);
/*    */   
/*    */   private final QName serviceName;
/*    */   private final QName portName;
/*    */   
/*    */   public PolicyMapKeyConverter(QName serviceName, QName portName) {
/* 62 */     this.serviceName = serviceName;
/* 63 */     this.portName = portName;
/*    */   }
/*    */   
/*    */   public PolicyMapKey getPolicyMapKey(WsdlBindingSubject subject) {
/* 67 */     LOGGER.entering(new Object[] { subject });
/*    */     
/* 69 */     PolicyMapKey key = null;
/* 70 */     if (subject.isBindingSubject()) {
/* 71 */       key = PolicyMap.createWsdlEndpointScopeKey(this.serviceName, this.portName);
/*    */     }
/* 73 */     else if (subject.isBindingOperationSubject()) {
/* 74 */       key = PolicyMap.createWsdlOperationScopeKey(this.serviceName, this.portName, subject.getName());
/*    */     }
/* 76 */     else if (subject.isBindingMessageSubject()) {
/* 77 */       if (subject.getMessageType() == WsdlBindingSubject.WsdlMessageType.FAULT) {
/* 78 */         key = PolicyMap.createWsdlFaultMessageScopeKey(this.serviceName, this.portName, subject.getParent().getName(), subject.getName());
/*    */       }
/*    */       else {
/*    */         
/* 82 */         key = PolicyMap.createWsdlMessageScopeKey(this.serviceName, this.portName, subject.getParent().getName());
/*    */       } 
/*    */     } 
/*    */     
/* 86 */     LOGGER.exiting(key);
/* 87 */     return key;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\subject\PolicyMapKeyConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */