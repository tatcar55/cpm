/*    */ package com.sun.xml.ws.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleAssertion
/*    */   extends PolicyAssertion
/*    */ {
/*    */   protected SimpleAssertion() {}
/*    */   
/*    */   protected SimpleAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
/* 58 */     super(data, assertionParameters);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasNestedPolicy() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public final NestedPolicy getNestedPolicy() {
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\SimpleAssertion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */