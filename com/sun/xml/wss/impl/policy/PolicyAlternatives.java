/*    */ package com.sun.xml.wss.impl.policy;
/*    */ 
/*    */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PolicyAlternatives
/*    */   implements SecurityPolicy
/*    */ {
/*    */   private final List<MessagePolicy> policyAlternatives;
/*    */   
/*    */   public PolicyAlternatives(List<MessagePolicy> policies) {
/* 58 */     this.policyAlternatives = policies;
/*    */   }
/*    */   public String getType() {
/* 61 */     return "SecurityPolicyAlternatives";
/*    */   }
/*    */   
/*    */   public final List<MessagePolicy> getSecurityPolicy() {
/* 65 */     return this.policyAlternatives;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 72 */     if (this.policyAlternatives == null) {
/* 73 */       return true;
/*    */     }
/*    */     
/* 76 */     for (MessagePolicy m : this.policyAlternatives) {
/* 77 */       if (!m.isEmpty()) {
/* 78 */         return false;
/*    */       }
/*    */     } 
/* 81 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\PolicyAlternatives.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */