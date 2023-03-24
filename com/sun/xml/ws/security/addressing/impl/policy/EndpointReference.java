/*    */ package com.sun.xml.ws.security.addressing.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EndpointReference
/*    */   extends PolicyAssertion
/*    */ {
/*    */   private Address address;
/*    */   private boolean populated = false;
/*    */   
/*    */   public EndpointReference(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 73 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public Address getAddress() {
/* 77 */     populate();
/* 78 */     return this.address;
/*    */   }
/*    */ 
/*    */   
/*    */   private void populate() {
/* 83 */     if (this.populated) {
/*    */       return;
/*    */     }
/* 86 */     synchronized (getClass()) {
/* 87 */       if (!this.populated) {
/* 88 */         if (hasNestedAssertions()) {
/* 89 */           Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 90 */           while (it.hasNext()) {
/* 91 */             PolicyAssertion assertion = it.next();
/* 92 */             if (PolicyUtil.isAddress(assertion)) {
/* 93 */               this.address = (Address)assertion;
/*    */             }
/*    */           } 
/*    */         } 
/* 97 */         this.populated = true;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\addressing\impl\policy\EndpointReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */