/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.EndorsingEncryptedSupportingTokens;
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
/*    */ public class EndorsingEncryptedSupportingTokens
/*    */   extends EndorsingSupportingTokens
/*    */   implements EndorsingEncryptedSupportingTokens
/*    */ {
/*    */   public EndorsingEncryptedSupportingTokens() {}
/*    */   
/*    */   public EndorsingEncryptedSupportingTokens(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 59 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\EndorsingEncryptedSupportingTokens.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */