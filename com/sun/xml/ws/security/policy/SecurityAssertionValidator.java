/*    */ package com.sun.xml.ws.security.policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface SecurityAssertionValidator
/*    */ {
/*    */   AssertionFitness validate(boolean paramBoolean);
/*    */   
/*    */   public enum AssertionFitness
/*    */   {
/* 53 */     HAS_UNKNOWN_ASSERTION,
/* 54 */     IS_VALID,
/* 55 */     HAS_UNSUPPORTED_ASSERTION,
/* 56 */     HAS_INVALID_VALUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\SecurityAssertionValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */