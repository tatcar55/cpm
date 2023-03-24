/*     */ package com.sun.xml.ws.policy.parser;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.policy.AlternativeSelector;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.ws.api.policy.ValidationProcessor;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.EffectivePolicyModifier;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.PolicyMapMutator;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*     */ import java.net.URL;
/*     */ import java.util.Collection;
/*     */ import javax.xml.stream.FactoryConfigurationError;
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
/*     */ 
/*     */ public class WsitPolicyResolver
/*     */   implements PolicyResolver
/*     */ {
/*  76 */   private static final Logger LOGGER = Logger.getLogger(WsitPolicyResolver.class);
/*     */   
/*     */   public PolicyMap resolve(PolicyResolver.ServerContext context) throws WebServiceException {
/*  79 */     Class endpointClass = context.getEndpointClass();
/*  80 */     String configId = (endpointClass == null) ? null : endpointClass.getName();
/*  81 */     if (!context.hasWsdl()) {
/*     */       
/*  83 */       PolicyMap map = null;
/*     */       try {
/*  85 */         Collection<PolicyMapMutator> mutators = context.getMutators();
/*     */         
/*  87 */         map = PolicyConfigParser.parse(configId, context.getContainer(), mutators.<PolicyMapMutator>toArray(new PolicyMapMutator[mutators.size()]));
/*     */       }
/*  89 */       catch (PolicyException e) {
/*  90 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSP_5006_FAILED_TO_READ_WSIT_CONFIG_FOR_ID(configId), e));
/*     */       } 
/*     */       
/*  93 */       if (map == null) {
/*  94 */         LOGGER.config(LocalizationMessages.WSP_5008_CREATE_POLICY_MAP_FOR_CONFIG(configId));
/*     */       } else {
/*     */         
/*  97 */         validateServerPolicyMap(map);
/*     */       } 
/*  99 */       return map;
/*     */     } 
/*     */     
/*     */     try {
/* 103 */       if (configId != null) {
/*     */         
/* 105 */         URL wsitConfigFile = PolicyConfigParser.findConfigFile(configId, context.getContainer());
/* 106 */         if (wsitConfigFile != null) {
/* 107 */           LOGGER.warning(LocalizationMessages.WSP_5024_WSIT_CONFIG_AND_WSDL(wsitConfigFile));
/*     */         }
/*     */       } 
/* 110 */       return PolicyResolverFactory.DEFAULT_POLICY_RESOLVER.resolve(context);
/* 111 */     } catch (PolicyException e) {
/* 112 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSP_5023_FIND_WSIT_CONFIG_FAILED(), e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicyMap resolve(PolicyResolver.ClientContext context) {
/*     */     PolicyMap effectivePolicyMap;
/*     */     try {
/* 120 */       PolicyMap clientConfigPolicyMap = PolicyConfigParser.parse("client", context.getContainer(), new PolicyMapMutator[0]);
/*     */       
/* 122 */       if (clientConfigPolicyMap == null) {
/* 123 */         LOGGER.config(LocalizationMessages.WSP_5014_CLIENT_CONFIG_PROCESSING_SKIPPED());
/* 124 */         effectivePolicyMap = context.getPolicyMap();
/*     */       } else {
/*     */         
/* 127 */         effectivePolicyMap = mergePolicyMap(context.getPolicyMap(), clientConfigPolicyMap);
/*     */       } 
/* 129 */     } catch (PolicyException e) {
/* 130 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(LocalizationMessages.WSP_5004_ERROR_WHILE_PROCESSING_CLIENT_CONFIG(), e));
/*     */     } 
/*     */ 
/*     */     
/* 134 */     if (effectivePolicyMap != null) {
/* 135 */       return doAlternativeSelection(effectivePolicyMap);
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateServerPolicyMap(PolicyMap policyMap) {
/*     */     try {
/* 148 */       ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
/*     */       
/* 150 */       for (Policy policy : policyMap) {
/*     */ 
/*     */ 
/*     */         
/* 154 */         for (AssertionSet assertionSet : policy) {
/* 155 */           for (PolicyAssertion assertion : assertionSet) {
/* 156 */             PolicyAssertionValidator.Fitness validationResult = validationProcessor.validateServerSide(assertion);
/* 157 */             if (validationResult != PolicyAssertionValidator.Fitness.SUPPORTED) {
/* 158 */               throw new PolicyException(LocalizationMessages.WSP_5017_SERVER_SIDE_ASSERTION_VALIDATION_FAILED(assertion.getName(), validationResult));
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */       
/*     */       } 
/* 165 */     } catch (PolicyException e) {
/* 166 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PolicyMap doAlternativeSelection(PolicyMap policyMap) {
/* 177 */     EffectivePolicyModifier modifier = EffectivePolicyModifier.createEffectivePolicyModifier();
/* 178 */     modifier.connect(policyMap);
/*     */     try {
/* 180 */       AlternativeSelector.doSelection(modifier);
/* 181 */     } catch (PolicyException e) {
/* 182 */       throw new WebServiceException(e);
/*     */     } finally {
/* 184 */       modifier.disconnect();
/*     */     } 
/* 186 */     return policyMap;
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
/*     */   private static PolicyMap mergePolicyMap(PolicyMap policyMap, PolicyMap clientPolicyMap) throws PolicyException {
/* 198 */     PolicyMapExtender mapExtender = PolicyMapExtender.createPolicyMapExtender();
/* 199 */     String clientWsitConfigId = "client";
/* 200 */     if (policyMap != null) {
/* 201 */       mapExtender.connect(policyMap);
/*     */       try {
/* 203 */         for (PolicyMapKey key : clientPolicyMap.getAllServiceScopeKeys()) {
/* 204 */           Policy policy = clientPolicyMap.getServiceEffectivePolicy(key);
/*     */           
/* 206 */           mapExtender.putServiceSubject(key, new PolicySubject("client", policy));
/*     */         } 
/*     */         
/* 209 */         for (PolicyMapKey key : clientPolicyMap.getAllEndpointScopeKeys()) {
/* 210 */           Policy policy = clientPolicyMap.getEndpointEffectivePolicy(key);
/*     */           
/* 212 */           mapExtender.putEndpointSubject(key, new PolicySubject("client", policy));
/*     */         } 
/*     */         
/* 215 */         for (PolicyMapKey key : clientPolicyMap.getAllOperationScopeKeys()) {
/* 216 */           Policy policy = clientPolicyMap.getOperationEffectivePolicy(key);
/*     */           
/* 218 */           mapExtender.putOperationSubject(key, new PolicySubject("client", policy));
/*     */         } 
/*     */         
/* 221 */         for (PolicyMapKey key : clientPolicyMap.getAllInputMessageScopeKeys()) {
/* 222 */           Policy policy = clientPolicyMap.getInputMessageEffectivePolicy(key);
/*     */           
/* 224 */           mapExtender.putInputMessageSubject(key, new PolicySubject("client", policy));
/*     */         } 
/*     */         
/* 227 */         for (PolicyMapKey key : clientPolicyMap.getAllOutputMessageScopeKeys()) {
/* 228 */           Policy policy = clientPolicyMap.getOutputMessageEffectivePolicy(key);
/*     */           
/* 230 */           mapExtender.putOutputMessageSubject(key, new PolicySubject("client", policy));
/*     */         } 
/*     */         
/* 233 */         for (PolicyMapKey key : clientPolicyMap.getAllFaultMessageScopeKeys()) {
/* 234 */           Policy policy = clientPolicyMap.getFaultMessageEffectivePolicy(key);
/*     */           
/* 236 */           mapExtender.putFaultMessageSubject(key, new PolicySubject("client", policy));
/*     */         } 
/* 238 */         LOGGER.fine(LocalizationMessages.WSP_5015_CLIENT_CFG_POLICIES_TRANSFERED_INTO_FINAL_POLICY_MAP(policyMap));
/* 239 */       } catch (FactoryConfigurationError ex) {
/* 240 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(ex));
/*     */       } 
/* 242 */       return policyMap;
/*     */     } 
/*     */     
/* 245 */     return clientPolicyMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\parser\WsitPolicyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */