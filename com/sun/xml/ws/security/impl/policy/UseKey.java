/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*    */ import com.sun.xml.ws.security.policy.UseKey;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.Collection;
/*    */ import java.util.logging.Level;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UseKey
/*    */   extends PolicyAssertion
/*    */   implements UseKey, SecurityAssertionValidator
/*    */ {
/* 69 */   private static QName sig = new QName("sig");
/*    */   private URI signatureID;
/*    */   private boolean populated = false;
/* 72 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*    */ 
/*    */ 
/*    */   
/*    */   public UseKey(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 77 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 81 */     return populate(isServer);
/*    */   }
/*    */ 
/*    */   
/*    */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 86 */     if (!this.populated) {
/*    */       try {
/* 88 */         this.signatureID = new URI(getAttributeValue(sig));
/* 89 */       } catch (URISyntaxException ex) {
/* 90 */         Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0102_INVALID_URI_VALUE(getAttributeValue(sig)), ex);
/* 91 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*    */       } 
/* 93 */       this.populated = true;
/*    */     } 
/* 95 */     return this.fitness;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\UseKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */