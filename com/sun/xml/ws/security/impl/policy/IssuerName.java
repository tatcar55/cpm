/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.IssuerName;
/*    */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
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
/*    */ public class IssuerName
/*    */   extends PolicyAssertion
/*    */   implements IssuerName, SecurityAssertionValidator
/*    */ {
/* 55 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*    */   private boolean populated = false;
/* 57 */   String issuerName = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public IssuerName() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public IssuerName(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 66 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public String getIssuerName() {
/* 70 */     populate();
/* 71 */     return this.issuerName;
/*    */   }
/*    */   
/*    */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 75 */     return populate(isServer);
/*    */   }
/*    */   
/*    */   private void populate() {
/* 79 */     populate(false);
/*    */   }
/*    */   
/*    */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 83 */     if (!this.populated) {
/* 84 */       this.issuerName = getValue();
/* 85 */       this.populated = true;
/*    */     } 
/* 87 */     return this.fitness;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\IssuerName.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */