/*    */ package com.sun.xml.ws.api.policy;
/*    */ 
/*    */ import com.sun.xml.ws.addressing.policy.AddressingPolicyValidator;
/*    */ import com.sun.xml.ws.config.management.policy.ManagementPolicyValidator;
/*    */ import com.sun.xml.ws.encoding.policy.EncodingPolicyValidator;
/*    */ import com.sun.xml.ws.policy.AssertionValidationProcessor;
/*    */ import com.sun.xml.ws.policy.PolicyException;
/*    */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationProcessor
/*    */   extends AssertionValidationProcessor
/*    */ {
/* 59 */   private static final PolicyAssertionValidator[] JAXWS_ASSERTION_VALIDATORS = new PolicyAssertionValidator[] { (PolicyAssertionValidator)new AddressingPolicyValidator(), (PolicyAssertionValidator)new EncodingPolicyValidator(), (PolicyAssertionValidator)new ManagementPolicyValidator() };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ValidationProcessor() throws PolicyException {
/* 73 */     super(Arrays.asList(JAXWS_ASSERTION_VALIDATORS));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ValidationProcessor getInstance() throws PolicyException {
/* 83 */     return new ValidationProcessor();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\policy\ValidationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */