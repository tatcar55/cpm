/*     */ package com.sun.xml.ws.policy.sourcemodel;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import com.sun.xml.ws.policy.spi.AssertionCreationException;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
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
/*     */ public class PolicyModelTranslator
/*     */ {
/*     */   private static final class ContentDecomposition
/*     */   {
/*  73 */     final List<Collection<ModelNode>> exactlyOneContents = new LinkedList<Collection<ModelNode>>();
/*  74 */     final List<ModelNode> assertions = new LinkedList<ModelNode>();
/*     */     
/*     */     void reset() {
/*  77 */       this.exactlyOneContents.clear();
/*  78 */       this.assertions.clear();
/*     */     }
/*     */     
/*     */     private ContentDecomposition() {} }
/*     */   
/*     */   private static final class RawAssertion {
/*  84 */     Collection<PolicyModelTranslator.RawAlternative> nestedAlternatives = null; ModelNode originalNode;
/*     */     final Collection<ModelNode> parameters;
/*     */     
/*     */     RawAssertion(ModelNode originalNode, Collection<ModelNode> parameters) {
/*  88 */       this.parameters = parameters;
/*  89 */       this.originalNode = originalNode;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class RawAlternative {
/*  94 */     private static final PolicyLogger LOGGER = PolicyLogger.getLogger(RawAlternative.class);
/*     */     
/*  96 */     final List<PolicyModelTranslator.RawPolicy> allNestedPolicies = new LinkedList<PolicyModelTranslator.RawPolicy>();
/*     */     final Collection<PolicyModelTranslator.RawAssertion> nestedAssertions;
/*     */     
/*     */     RawAlternative(Collection<ModelNode> assertionNodes) throws PolicyException {
/* 100 */       this.nestedAssertions = new LinkedList<PolicyModelTranslator.RawAssertion>();
/* 101 */       for (ModelNode node : assertionNodes) {
/* 102 */         PolicyModelTranslator.RawAssertion assertion = new PolicyModelTranslator.RawAssertion(node, new LinkedList<ModelNode>());
/* 103 */         this.nestedAssertions.add(assertion);
/*     */         
/* 105 */         for (ModelNode assertionNodeChild : assertion.originalNode.getChildren()) {
/* 106 */           switch (assertionNodeChild.getType()) {
/*     */             case ASSERTION_PARAMETER_NODE:
/* 108 */               assertion.parameters.add(assertionNodeChild);
/*     */               continue;
/*     */             case POLICY:
/*     */             case POLICY_REFERENCE:
/* 112 */               if (assertion.nestedAlternatives == null) {
/* 113 */                 PolicyModelTranslator.RawPolicy nestedPolicy; assertion.nestedAlternatives = new LinkedList<RawAlternative>();
/*     */                 
/* 115 */                 if (assertionNodeChild.getType() == ModelNode.Type.POLICY) {
/* 116 */                   nestedPolicy = new PolicyModelTranslator.RawPolicy(assertionNodeChild, assertion.nestedAlternatives);
/*     */                 } else {
/* 118 */                   nestedPolicy = new PolicyModelTranslator.RawPolicy(PolicyModelTranslator.getReferencedModelRootNode(assertionNodeChild), assertion.nestedAlternatives);
/*     */                 } 
/* 120 */                 this.allNestedPolicies.add(nestedPolicy); continue;
/*     */               } 
/* 122 */               throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0006_UNEXPECTED_MULTIPLE_POLICY_NODES()));
/*     */           } 
/*     */ 
/*     */           
/* 126 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0008_UNEXPECTED_CHILD_MODEL_TYPE(assertionNodeChild.getType())));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class RawPolicy
/*     */   {
/*     */     final Collection<ModelNode> originalContent;
/*     */     final Collection<PolicyModelTranslator.RawAlternative> alternatives;
/*     */     
/*     */     RawPolicy(ModelNode policyNode, Collection<PolicyModelTranslator.RawAlternative> alternatives) {
/* 139 */       this.originalContent = policyNode.getChildren();
/* 140 */       this.alternatives = alternatives;
/*     */     }
/*     */   }
/*     */   
/* 144 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyModelTranslator.class);
/*     */   
/* 146 */   private static final PolicyAssertionCreator defaultCreator = new DefaultPolicyAssertionCreator();
/*     */   
/*     */   private final Map<String, PolicyAssertionCreator> assertionCreators;
/*     */ 
/*     */   
/*     */   private PolicyModelTranslator() throws PolicyException {
/* 152 */     this(null);
/*     */   }
/*     */   
/*     */   protected PolicyModelTranslator(Collection<PolicyAssertionCreator> creators) throws PolicyException {
/* 156 */     LOGGER.entering(new Object[] { creators });
/*     */     
/* 158 */     Collection<PolicyAssertionCreator> allCreators = new LinkedList<PolicyAssertionCreator>();
/* 159 */     PolicyAssertionCreator[] discoveredCreators = (PolicyAssertionCreator[])PolicyUtils.ServiceProvider.load(PolicyAssertionCreator.class);
/* 160 */     for (PolicyAssertionCreator creator : discoveredCreators) {
/* 161 */       allCreators.add(creator);
/*     */     }
/* 163 */     if (creators != null) {
/* 164 */       for (PolicyAssertionCreator creator : creators) {
/* 165 */         allCreators.add(creator);
/*     */       }
/*     */     }
/*     */     
/* 169 */     Map<String, PolicyAssertionCreator> pacMap = new HashMap<String, PolicyAssertionCreator>();
/* 170 */     for (PolicyAssertionCreator creator : allCreators) {
/* 171 */       String[] supportedURIs = creator.getSupportedDomainNamespaceURIs();
/* 172 */       String creatorClassName = creator.getClass().getName();
/*     */       
/* 174 */       if (supportedURIs == null || supportedURIs.length == 0) {
/* 175 */         LOGGER.warning(LocalizationMessages.WSP_0077_ASSERTION_CREATOR_DOES_NOT_SUPPORT_ANY_URI(creatorClassName));
/*     */         
/*     */         continue;
/*     */       } 
/* 179 */       for (String supportedURI : supportedURIs) {
/* 180 */         LOGGER.config(LocalizationMessages.WSP_0078_ASSERTION_CREATOR_DISCOVERED(creatorClassName, supportedURI));
/* 181 */         if (supportedURI == null || supportedURI.length() == 0) {
/* 182 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0070_ERROR_REGISTERING_ASSERTION_CREATOR(creatorClassName)));
/*     */         }
/*     */ 
/*     */         
/* 186 */         PolicyAssertionCreator oldCreator = pacMap.put(supportedURI, creator);
/* 187 */         if (oldCreator != null) {
/* 188 */           throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0071_ERROR_MULTIPLE_ASSERTION_CREATORS_FOR_NAMESPACE(supportedURI, oldCreator.getClass().getName(), creator.getClass().getName())));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 195 */     this.assertionCreators = Collections.unmodifiableMap(pacMap);
/* 196 */     LOGGER.exiting();
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
/*     */   public static PolicyModelTranslator getTranslator() throws PolicyException {
/* 209 */     return new PolicyModelTranslator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Policy translate(PolicySourceModel model) throws PolicyException {
/*     */     PolicySourceModel localPolicyModelCopy;
/* 221 */     LOGGER.entering(new Object[] { model });
/*     */     
/* 223 */     if (model == null) {
/* 224 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0043_POLICY_MODEL_TRANSLATION_ERROR_INPUT_PARAM_NULL()));
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 229 */       localPolicyModelCopy = model.clone();
/* 230 */     } catch (CloneNotSupportedException e) {
/* 231 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0016_UNABLE_TO_CLONE_POLICY_SOURCE_MODEL(), e));
/*     */     } 
/*     */     
/* 234 */     String policyId = localPolicyModelCopy.getPolicyId();
/* 235 */     String policyName = localPolicyModelCopy.getPolicyName();
/*     */     
/* 237 */     Collection<AssertionSet> alternatives = createPolicyAlternatives(localPolicyModelCopy);
/* 238 */     LOGGER.finest(LocalizationMessages.WSP_0052_NUMBER_OF_ALTERNATIVE_COMBINATIONS_CREATED(Integer.valueOf(alternatives.size())));
/*     */     
/* 240 */     Policy policy = null;
/* 241 */     if (alternatives.size() == 0) {
/* 242 */       policy = Policy.createNullPolicy(model.getNamespaceVersion(), policyName, policyId);
/* 243 */       LOGGER.finest(LocalizationMessages.WSP_0055_NO_ALTERNATIVE_COMBINATIONS_CREATED());
/* 244 */     } else if (alternatives.size() == 1 && ((AssertionSet)alternatives.iterator().next()).isEmpty()) {
/* 245 */       policy = Policy.createEmptyPolicy(model.getNamespaceVersion(), policyName, policyId);
/* 246 */       LOGGER.finest(LocalizationMessages.WSP_0026_SINGLE_EMPTY_ALTERNATIVE_COMBINATION_CREATED());
/*     */     } else {
/* 248 */       policy = Policy.createPolicy(model.getNamespaceVersion(), policyName, policyId, alternatives);
/* 249 */       LOGGER.finest(LocalizationMessages.WSP_0057_N_ALTERNATIVE_COMBINATIONS_M_POLICY_ALTERNATIVES_CREATED(Integer.valueOf(alternatives.size()), Integer.valueOf(policy.getNumberOfAssertionSets())));
/*     */     } 
/*     */     
/* 252 */     LOGGER.exiting(policy);
/* 253 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<AssertionSet> createPolicyAlternatives(PolicySourceModel model) throws PolicyException {
/* 263 */     ContentDecomposition decomposition = new ContentDecomposition();
/*     */ 
/*     */     
/* 266 */     Queue<RawPolicy> policyQueue = new LinkedList<RawPolicy>();
/* 267 */     Queue<Collection<ModelNode>> contentQueue = new LinkedList<Collection<ModelNode>>();
/*     */     
/* 269 */     RawPolicy rootPolicy = new RawPolicy(model.getRootNode(), new LinkedList<RawAlternative>());
/* 270 */     RawPolicy processedPolicy = rootPolicy;
/*     */     while (true) {
/* 272 */       Collection<ModelNode> processedContent = processedPolicy.originalContent;
/*     */       do {
/* 274 */         decompose(processedContent, decomposition);
/* 275 */         if (decomposition.exactlyOneContents.isEmpty()) {
/* 276 */           RawAlternative alternative = new RawAlternative(decomposition.assertions);
/* 277 */           processedPolicy.alternatives.add(alternative);
/* 278 */           if (!alternative.allNestedPolicies.isEmpty()) {
/* 279 */             policyQueue.addAll(alternative.allNestedPolicies);
/*     */           }
/*     */         } else {
/* 282 */           Collection<Collection<ModelNode>> combinations = PolicyUtils.Collections.combine(decomposition.assertions, decomposition.exactlyOneContents, false);
/* 283 */           if (combinations != null && !combinations.isEmpty())
/*     */           {
/* 285 */             contentQueue.addAll(combinations);
/*     */           }
/*     */         } 
/* 288 */       } while ((processedContent = contentQueue.poll()) != null || (
/* 289 */         processedPolicy = policyQueue.poll()) != null);
/*     */     } 
/*     */     
/* 292 */     Collection<AssertionSet> assertionSets = new LinkedList<AssertionSet>();
/* 293 */     for (RawAlternative rootAlternative : rootPolicy.alternatives) {
/* 294 */       Collection<AssertionSet> normalizedAlternatives = normalizeRawAlternative(rootAlternative);
/* 295 */       assertionSets.addAll(normalizedAlternatives);
/*     */     } 
/*     */     
/* 298 */     return assertionSets;
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
/*     */   private void decompose(Collection<ModelNode> content, ContentDecomposition decomposition) throws PolicyException {
/* 310 */     decomposition.reset();
/*     */     
/* 312 */     Queue<ModelNode> allContentQueue = new LinkedList<ModelNode>(content);
/*     */     ModelNode node;
/* 314 */     while ((node = allContentQueue.poll()) != null) {
/*     */       
/* 316 */       switch (node.getType()) {
/*     */         case POLICY:
/*     */         case ALL:
/* 319 */           allContentQueue.addAll(node.getChildren());
/*     */           continue;
/*     */         case POLICY_REFERENCE:
/* 322 */           allContentQueue.addAll(getReferencedModelRootNode(node).getChildren());
/*     */           continue;
/*     */         case EXACTLY_ONE:
/* 325 */           decomposition.exactlyOneContents.add(expandsExactlyOneContent(node.getChildren()));
/*     */           continue;
/*     */         case ASSERTION:
/* 328 */           decomposition.assertions.add(node);
/*     */           continue;
/*     */       } 
/* 331 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0007_UNEXPECTED_MODEL_NODE_TYPE_FOUND(node.getType())));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ModelNode getReferencedModelRootNode(ModelNode policyReferenceNode) throws PolicyException {
/* 337 */     PolicySourceModel referencedModel = policyReferenceNode.getReferencedModel();
/* 338 */     if (referencedModel == null) {
/* 339 */       PolicyReferenceData refData = policyReferenceNode.getPolicyReferenceData();
/* 340 */       if (refData == null) {
/* 341 */         throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0041_POLICY_REFERENCE_NODE_FOUND_WITH_NO_POLICY_REFERENCE_IN_IT()));
/*     */       }
/* 343 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0010_UNEXPANDED_POLICY_REFERENCE_NODE_FOUND_REFERENCING(refData.getReferencedModelUri())));
/*     */     } 
/*     */     
/* 346 */     return referencedModel.getRootNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Collection<ModelNode> expandsExactlyOneContent(Collection<ModelNode> content) throws PolicyException {
/* 354 */     Collection<ModelNode> result = new LinkedList<ModelNode>();
/*     */     
/* 356 */     Queue<ModelNode> eoContentQueue = new LinkedList<ModelNode>(content);
/*     */     ModelNode node;
/* 358 */     while ((node = eoContentQueue.poll()) != null) {
/*     */       
/* 360 */       switch (node.getType()) {
/*     */         case POLICY:
/*     */         case ALL:
/*     */         case ASSERTION:
/* 364 */           result.add(node);
/*     */           continue;
/*     */         case POLICY_REFERENCE:
/* 367 */           result.add(getReferencedModelRootNode(node));
/*     */           continue;
/*     */         case EXACTLY_ONE:
/* 370 */           eoContentQueue.addAll(node.getChildren());
/*     */           continue;
/*     */       } 
/* 373 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0001_UNSUPPORTED_MODEL_NODE_TYPE(node.getType())));
/*     */     } 
/*     */ 
/*     */     
/* 377 */     return result;
/*     */   }
/*     */   
/*     */   private List<AssertionSet> normalizeRawAlternative(RawAlternative alternative) throws AssertionCreationException, PolicyException {
/* 381 */     List<PolicyAssertion> normalizedContentBase = new LinkedList<PolicyAssertion>();
/* 382 */     Collection<List<PolicyAssertion>> normalizedContentOptions = new LinkedList<List<PolicyAssertion>>();
/* 383 */     if (!alternative.nestedAssertions.isEmpty()) {
/* 384 */       Queue<RawAssertion> nestedAssertionsQueue = new LinkedList<RawAssertion>(alternative.nestedAssertions);
/*     */       RawAssertion rawAssertion;
/* 386 */       while ((rawAssertion = nestedAssertionsQueue.poll()) != null) {
/* 387 */         List<PolicyAssertion> normalized = normalizeRawAssertion(rawAssertion);
/*     */ 
/*     */         
/* 390 */         if (normalized.size() == 1) {
/* 391 */           normalizedContentBase.addAll(normalized); continue;
/*     */         } 
/* 393 */         normalizedContentOptions.add(normalized);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 398 */     List<AssertionSet> options = new LinkedList<AssertionSet>();
/* 399 */     if (normalizedContentOptions.isEmpty()) {
/*     */       
/* 401 */       options.add(AssertionSet.createAssertionSet(normalizedContentBase));
/*     */     } else {
/*     */       
/* 404 */       Collection<Collection<PolicyAssertion>> contentCombinations = PolicyUtils.Collections.combine(normalizedContentBase, normalizedContentOptions, true);
/* 405 */       for (Collection<PolicyAssertion> contentOption : contentCombinations) {
/* 406 */         options.add(AssertionSet.createAssertionSet(contentOption));
/*     */       }
/*     */     } 
/* 409 */     return options;
/*     */   }
/*     */   
/*     */   private List<PolicyAssertion> normalizeRawAssertion(RawAssertion assertion) throws AssertionCreationException, PolicyException {
/*     */     List<PolicyAssertion> parameters;
/* 414 */     if (assertion.parameters.isEmpty()) {
/* 415 */       parameters = null;
/*     */     } else {
/* 417 */       parameters = new ArrayList<PolicyAssertion>(assertion.parameters.size());
/* 418 */       for (ModelNode parameterNode : assertion.parameters) {
/* 419 */         parameters.add(createPolicyAssertionParameter(parameterNode));
/*     */       }
/*     */     } 
/*     */     
/* 423 */     List<AssertionSet> nestedAlternatives = new LinkedList<AssertionSet>();
/* 424 */     if (assertion.nestedAlternatives != null && !assertion.nestedAlternatives.isEmpty()) {
/* 425 */       Queue<RawAlternative> nestedAlternativeQueue = new LinkedList<RawAlternative>(assertion.nestedAlternatives);
/*     */       RawAlternative rawAlternative;
/* 427 */       while ((rawAlternative = nestedAlternativeQueue.poll()) != null) {
/* 428 */         nestedAlternatives.addAll(normalizeRawAlternative(rawAlternative));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 434 */     List<PolicyAssertion> assertionOptions = new LinkedList<PolicyAssertion>();
/* 435 */     boolean nestedAlternativesAvailable = !nestedAlternatives.isEmpty();
/* 436 */     if (nestedAlternativesAvailable) {
/* 437 */       for (AssertionSet nestedAlternative : nestedAlternatives) {
/* 438 */         assertionOptions.add(createPolicyAssertion(assertion.originalNode.getNodeData(), parameters, nestedAlternative));
/*     */       }
/*     */     } else {
/* 441 */       assertionOptions.add(createPolicyAssertion(assertion.originalNode.getNodeData(), parameters, null));
/*     */     } 
/* 443 */     return assertionOptions;
/*     */   }
/*     */   
/*     */   private PolicyAssertion createPolicyAssertionParameter(ModelNode parameterNode) throws AssertionCreationException, PolicyException {
/* 447 */     if (parameterNode.getType() != ModelNode.Type.ASSERTION_PARAMETER_NODE) {
/* 448 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_0065_INCONSISTENCY_IN_POLICY_SOURCE_MODEL(parameterNode.getType())));
/*     */     }
/*     */     
/* 451 */     List<PolicyAssertion> childParameters = null;
/* 452 */     if (parameterNode.hasChildren()) {
/* 453 */       childParameters = new ArrayList<PolicyAssertion>(parameterNode.childrenSize());
/* 454 */       for (ModelNode childParameterNode : parameterNode) {
/* 455 */         childParameters.add(createPolicyAssertionParameter(childParameterNode));
/*     */       }
/*     */     } 
/*     */     
/* 459 */     return createPolicyAssertion(parameterNode.getNodeData(), childParameters, null);
/*     */   }
/*     */   
/*     */   private PolicyAssertion createPolicyAssertion(AssertionData data, Collection<PolicyAssertion> assertionParameters, AssertionSet nestedAlternative) throws AssertionCreationException {
/* 463 */     String assertionNamespace = data.getName().getNamespaceURI();
/* 464 */     PolicyAssertionCreator domainSpecificPAC = this.assertionCreators.get(assertionNamespace);
/*     */ 
/*     */     
/* 467 */     if (domainSpecificPAC == null) {
/* 468 */       return defaultCreator.createAssertion(data, assertionParameters, nestedAlternative, null);
/*     */     }
/* 470 */     return domainSpecificPAC.createAssertion(data, assertionParameters, nestedAlternative, defaultCreator);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\sourcemodel\PolicyModelTranslator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */