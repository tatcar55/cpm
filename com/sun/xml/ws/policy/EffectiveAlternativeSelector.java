/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectiveAlternativeSelector
/*     */ {
/*     */   private enum AlternativeFitness
/*     */   {
/*  63 */     UNEVALUATED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  65 */         switch (assertionFitness) {
/*     */           case INVALID:
/*  67 */             return UNKNOWN;
/*     */           case UNKNOWN:
/*  69 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/*  71 */             return SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/*  73 */             return INVALID;
/*     */         } 
/*  75 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/*  79 */     INVALID {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  81 */         return INVALID;
/*     */       }
/*     */     },
/*  84 */     UNKNOWN {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/*  86 */         switch (assertionFitness) {
/*     */           case INVALID:
/*  88 */             return UNKNOWN;
/*     */           case UNKNOWN:
/*  90 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/*  92 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/*  94 */             return INVALID;
/*     */         } 
/*  96 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/* 100 */     UNSUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 102 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/* 105 */             return UNSUPPORTED;
/*     */           case UNSUPPORTED:
/* 107 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/* 109 */             return INVALID;
/*     */         } 
/* 111 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/* 115 */     PARTIALLY_SUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 117 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/*     */           case UNSUPPORTED:
/* 121 */             return PARTIALLY_SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/* 123 */             return INVALID;
/*     */         } 
/* 125 */         return UNEVALUATED;
/*     */       }
/*     */     },
/*     */     
/* 129 */     SUPPORTED_EMPTY
/*     */     {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 132 */         throw new UnsupportedOperationException("Combine operation was called unexpectedly on 'SUPPORTED_EMPTY' alternative fitness enumeration state.");
/*     */       }
/*     */     },
/* 135 */     SUPPORTED {
/*     */       AlternativeFitness combine(PolicyAssertionValidator.Fitness assertionFitness) {
/* 137 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/* 140 */             return PARTIALLY_SUPPORTED;
/*     */           case UNSUPPORTED:
/* 142 */             return SUPPORTED;
/*     */           case PARTIALLY_SUPPORTED:
/* 144 */             return INVALID;
/*     */         } 
/* 146 */         return UNEVALUATED;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     abstract AlternativeFitness combine(PolicyAssertionValidator.Fitness param1Fitness);
/*     */   }
/*     */   
/* 154 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(EffectiveAlternativeSelector.class);
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
/*     */   public static void doSelection(EffectivePolicyModifier modifier) throws PolicyException {
/* 167 */     AssertionValidationProcessor validationProcessor = AssertionValidationProcessor.getInstance();
/* 168 */     selectAlternatives(modifier, validationProcessor);
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
/*     */   protected static void selectAlternatives(EffectivePolicyModifier modifier, AssertionValidationProcessor validationProcessor) throws PolicyException {
/* 182 */     PolicyMap map = modifier.getMap();
/* 183 */     for (PolicyMapKey mapKey : map.getAllServiceScopeKeys()) {
/* 184 */       Policy oldPolicy = map.getServiceEffectivePolicy(mapKey);
/* 185 */       modifier.setNewEffectivePolicyForServiceScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 187 */     for (PolicyMapKey mapKey : map.getAllEndpointScopeKeys()) {
/* 188 */       Policy oldPolicy = map.getEndpointEffectivePolicy(mapKey);
/* 189 */       modifier.setNewEffectivePolicyForEndpointScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 191 */     for (PolicyMapKey mapKey : map.getAllOperationScopeKeys()) {
/* 192 */       Policy oldPolicy = map.getOperationEffectivePolicy(mapKey);
/* 193 */       modifier.setNewEffectivePolicyForOperationScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 195 */     for (PolicyMapKey mapKey : map.getAllInputMessageScopeKeys()) {
/* 196 */       Policy oldPolicy = map.getInputMessageEffectivePolicy(mapKey);
/* 197 */       modifier.setNewEffectivePolicyForInputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 199 */     for (PolicyMapKey mapKey : map.getAllOutputMessageScopeKeys()) {
/* 200 */       Policy oldPolicy = map.getOutputMessageEffectivePolicy(mapKey);
/* 201 */       modifier.setNewEffectivePolicyForOutputMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/* 203 */     for (PolicyMapKey mapKey : map.getAllFaultMessageScopeKeys()) {
/* 204 */       Policy oldPolicy = map.getFaultMessageEffectivePolicy(mapKey);
/* 205 */       modifier.setNewEffectivePolicyForFaultMessageScope(mapKey, selectBestAlternative(oldPolicy, validationProcessor));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Policy selectBestAlternative(Policy policy, AssertionValidationProcessor validationProcessor) throws PolicyException {
/* 210 */     AssertionSet bestAlternative = null;
/* 211 */     AlternativeFitness bestAlternativeFitness = AlternativeFitness.UNEVALUATED;
/* 212 */     for (AssertionSet alternative : policy) {
/* 213 */       AlternativeFitness alternativeFitness = alternative.isEmpty() ? AlternativeFitness.SUPPORTED_EMPTY : AlternativeFitness.UNEVALUATED;
/* 214 */       for (PolicyAssertion assertion : alternative) {
/*     */         
/* 216 */         PolicyAssertionValidator.Fitness assertionFitness = validationProcessor.validateClientSide(assertion);
/* 217 */         switch (assertionFitness) {
/*     */           case INVALID:
/*     */           case UNKNOWN:
/*     */           case PARTIALLY_SUPPORTED:
/* 221 */             LOGGER.warning(LocalizationMessages.WSP_0075_PROBLEMATIC_ASSERTION_STATE(assertion.getName(), assertionFitness));
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 227 */         alternativeFitness = alternativeFitness.combine(assertionFitness);
/*     */       } 
/*     */       
/* 230 */       if (bestAlternativeFitness.compareTo(alternativeFitness) < 0) {
/*     */         
/* 232 */         bestAlternative = alternative;
/* 233 */         bestAlternativeFitness = alternativeFitness;
/*     */       } 
/*     */       
/* 236 */       if (bestAlternativeFitness == AlternativeFitness.SUPPORTED) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 242 */     switch (bestAlternativeFitness) {
/*     */       case INVALID:
/* 244 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0053_INVALID_CLIENT_SIDE_ALTERNATIVE()));
/*     */       case UNKNOWN:
/*     */       case UNSUPPORTED:
/*     */       case PARTIALLY_SUPPORTED:
/* 248 */         LOGGER.warning(LocalizationMessages.WSP_0019_SUBOPTIMAL_ALTERNATIVE_SELECTED(bestAlternativeFitness));
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 254 */     Collection<AssertionSet> alternativeSet = null;
/* 255 */     if (bestAlternative != null) {
/*     */       
/* 257 */       alternativeSet = new LinkedList<AssertionSet>();
/* 258 */       alternativeSet.add(bestAlternative);
/*     */     } 
/* 260 */     return Policy.createPolicy(policy.getNamespaceVersion(), policy.getName(), policy.getId(), alternativeSet);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\EffectiveAlternativeSelector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */