/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.ws.api.policy.AlternativeSelector;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.ws.api.policy.ValidationProcessor;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.EffectivePolicyModifier;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*     */ import com.sun.xml.ws.resources.PolicyMessages;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ public class DefaultPolicyResolver
/*     */   implements PolicyResolver
/*     */ {
/*     */   public PolicyMap resolve(PolicyResolver.ServerContext context) {
/*  67 */     PolicyMap map = context.getPolicyMap();
/*  68 */     if (map != null)
/*  69 */       validateServerPolicyMap(map); 
/*  70 */     return map;
/*     */   }
/*     */   
/*     */   public PolicyMap resolve(PolicyResolver.ClientContext context) {
/*  74 */     PolicyMap map = context.getPolicyMap();
/*  75 */     if (map != null)
/*  76 */       map = doAlternativeSelection(map); 
/*  77 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void validateServerPolicyMap(PolicyMap policyMap) {
/*     */     try {
/*  88 */       ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
/*     */       
/*  90 */       for (Policy policy : policyMap) {
/*     */ 
/*     */ 
/*     */         
/*  94 */         for (AssertionSet assertionSet : policy) {
/*  95 */           for (PolicyAssertion assertion : assertionSet) {
/*  96 */             PolicyAssertionValidator.Fitness validationResult = validationProcessor.validateServerSide(assertion);
/*  97 */             if (validationResult != PolicyAssertionValidator.Fitness.SUPPORTED) {
/*  98 */               throw new PolicyException(PolicyMessages.WSP_1015_SERVER_SIDE_ASSERTION_VALIDATION_FAILED(assertion.getName(), validationResult));
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 105 */     } catch (PolicyException e) {
/* 106 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PolicyMap doAlternativeSelection(PolicyMap policyMap) {
/* 117 */     EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
/* 118 */     modifier.connect(policyMap);
/*     */     try {
/* 120 */       AlternativeSelector.doSelection(modifier);
/* 121 */     } catch (PolicyException e) {
/* 122 */       throw new WebServiceException(e);
/*     */     } 
/* 124 */     return policyMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\DefaultPolicyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */