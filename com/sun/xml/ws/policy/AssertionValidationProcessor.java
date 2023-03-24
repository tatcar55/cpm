/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssertionValidationProcessor
/*     */ {
/*  58 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AssertionValidationProcessor.class);
/*     */   
/*  60 */   private final Collection<PolicyAssertionValidator> validators = new LinkedList<PolicyAssertionValidator>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AssertionValidationProcessor() throws PolicyException {
/*  70 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AssertionValidationProcessor(Collection<PolicyAssertionValidator> policyValidators) throws PolicyException {
/*  85 */     for (PolicyAssertionValidator validator : (PolicyAssertionValidator[])PolicyUtils.ServiceProvider.load(PolicyAssertionValidator.class)) {
/*  86 */       this.validators.add(validator);
/*     */     }
/*  88 */     if (policyValidators != null) {
/*  89 */       for (PolicyAssertionValidator validator : policyValidators) {
/*  90 */         this.validators.add(validator);
/*     */       }
/*     */     }
/*  93 */     if (this.validators.size() == 0) {
/*  94 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0076_NO_SERVICE_PROVIDERS_FOUND(PolicyAssertionValidator.class.getName())));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AssertionValidationProcessor getInstance() throws PolicyException {
/* 108 */     return new AssertionValidationProcessor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) throws PolicyException {
/* 120 */     PolicyAssertionValidator.Fitness assertionFitness = PolicyAssertionValidator.Fitness.UNKNOWN;
/* 121 */     for (PolicyAssertionValidator validator : this.validators) {
/* 122 */       assertionFitness = assertionFitness.combine(validator.validateClientSide(assertion));
/* 123 */       if (assertionFitness == PolicyAssertionValidator.Fitness.SUPPORTED) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return assertionFitness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) throws PolicyException {
/* 140 */     PolicyAssertionValidator.Fitness assertionFitness = PolicyAssertionValidator.Fitness.UNKNOWN;
/* 141 */     for (PolicyAssertionValidator validator : this.validators) {
/* 142 */       assertionFitness = assertionFitness.combine(validator.validateServerSide(assertion));
/* 143 */       if (assertionFitness == PolicyAssertionValidator.Fitness.SUPPORTED) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 148 */     return assertionFitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\AssertionValidationProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */