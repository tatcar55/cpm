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
/*    */ 
/*    */ public abstract class ComplexAssertion
/*    */   extends PolicyAssertion
/*    */ {
/*    */   private final NestedPolicy nestedPolicy;
/*    */   
/*    */   protected ComplexAssertion() {
/* 59 */     this.nestedPolicy = NestedPolicy.createNestedPolicy(AssertionSet.emptyAssertionSet());
/*    */   }
/*    */   
/*    */   protected ComplexAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) {
/* 63 */     super(data, assertionParameters);
/*    */     
/* 65 */     AssertionSet nestedSet = (nestedAlternative != null) ? nestedAlternative : AssertionSet.emptyAssertionSet();
/* 66 */     this.nestedPolicy = NestedPolicy.createNestedPolicy(nestedSet);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean hasNestedPolicy() {
/* 71 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public final NestedPolicy getNestedPolicy() {
/* 76 */     return this.nestedPolicy;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\ComplexAssertion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */