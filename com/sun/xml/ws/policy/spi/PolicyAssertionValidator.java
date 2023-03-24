/*    */ package com.sun.xml.ws.policy.spi;
/*    */ 
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PolicyAssertionValidator
/*    */ {
/*    */   Fitness validateClientSide(PolicyAssertion paramPolicyAssertion);
/*    */   
/*    */   Fitness validateServerSide(PolicyAssertion paramPolicyAssertion);
/*    */   
/*    */   String[] declareSupportedDomains();
/*    */   
/*    */   public enum Fitness
/*    */   {
/* 53 */     UNKNOWN,
/* 54 */     INVALID,
/* 55 */     UNSUPPORTED,
/* 56 */     SUPPORTED;
/*    */     
/*    */     public Fitness combine(Fitness other) {
/* 59 */       if (compareTo(other) < 0) {
/* 60 */         return other;
/*    */       }
/* 62 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\spi\PolicyAssertionValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */