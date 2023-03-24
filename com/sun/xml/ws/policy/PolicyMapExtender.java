/*    */ package com.sun.xml.ws.policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class PolicyMapExtender
/*    */   extends PolicyMapMutator
/*    */ {
/*    */   public static PolicyMapExtender createPolicyMapExtender() {
/* 58 */     return new PolicyMapExtender();
/*    */   }
/*    */   
/*    */   public void putServiceSubject(PolicyMapKey key, PolicySubject subject) {
/* 62 */     getMap().putSubject(PolicyMap.ScopeType.SERVICE, key, subject);
/*    */   }
/*    */   
/*    */   public void putEndpointSubject(PolicyMapKey key, PolicySubject subject) {
/* 66 */     getMap().putSubject(PolicyMap.ScopeType.ENDPOINT, key, subject);
/*    */   }
/*    */   
/*    */   public void putOperationSubject(PolicyMapKey key, PolicySubject subject) {
/* 70 */     getMap().putSubject(PolicyMap.ScopeType.OPERATION, key, subject);
/*    */   }
/*    */   
/*    */   public void putInputMessageSubject(PolicyMapKey key, PolicySubject subject) {
/* 74 */     getMap().putSubject(PolicyMap.ScopeType.INPUT_MESSAGE, key, subject);
/*    */   }
/*    */   
/*    */   public void putOutputMessageSubject(PolicyMapKey key, PolicySubject subject) {
/* 78 */     getMap().putSubject(PolicyMap.ScopeType.OUTPUT_MESSAGE, key, subject);
/*    */   }
/*    */   
/*    */   public void putFaultMessageSubject(PolicyMapKey key, PolicySubject subject) {
/* 82 */     getMap().putSubject(PolicyMap.ScopeType.FAULT_MESSAGE, key, subject);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMapExtender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */