/*    */ package com.sun.xml.ws.security.impl.policy;
/*    */ 
/*    */ import com.sun.xml.ws.policy.AssertionSet;
/*    */ import com.sun.xml.ws.policy.PolicyAssertion;
/*    */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*    */ import com.sun.xml.ws.security.policy.Validator;
/*    */ import java.util.Collection;
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
/*    */ public class Validator
/*    */   extends PolicyAssertion
/*    */   implements Validator
/*    */ {
/* 53 */   private static QName name = new QName("name");
/* 54 */   private static QName className = new QName("classname");
/*    */ 
/*    */   
/*    */   public Validator() {}
/*    */   
/*    */   public Validator(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/* 60 */     super(name, nestedAssertions, nestedAlternative);
/*    */   }
/*    */   
/*    */   public String getValidatorName() {
/* 64 */     return getAttributeValue(name);
/*    */   }
/*    */   
/*    */   public String getValidator() {
/* 68 */     return getAttributeValue(className);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Validator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */