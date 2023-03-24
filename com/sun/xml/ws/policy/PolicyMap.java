/*     */ package com.sun.xml.ws.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.privateutil.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public final class PolicyMap
/*     */   implements Iterable<Policy>
/*     */ {
/*  68 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyMap.class);
/*     */   
/*  70 */   private static final PolicyMapKeyHandler serviceKeyHandler = new PolicyMapKeyHandler() {
/*     */       public boolean areEqual(PolicyMapKey key1, PolicyMapKey key2) {
/*  72 */         return key1.getService().equals(key2.getService());
/*     */       }
/*     */       
/*     */       public int generateHashCode(PolicyMapKey key) {
/*  76 */         int result = 17;
/*     */         
/*  78 */         result = 37 * result + key.getService().hashCode();
/*     */         
/*  80 */         return result;
/*     */       }
/*     */     };
/*     */   
/*  84 */   private static final PolicyMapKeyHandler endpointKeyHandler = new PolicyMapKeyHandler() {
/*     */       public boolean areEqual(PolicyMapKey key1, PolicyMapKey key2) {
/*  86 */         boolean retVal = true;
/*     */         
/*  88 */         retVal = (retVal && key1.getService().equals(key2.getService()));
/*  89 */         retVal = (retVal && ((key1.getPort() == null) ? (key2.getPort() == null) : key1.getPort().equals(key2.getPort())));
/*     */         
/*  91 */         return retVal;
/*     */       }
/*     */       
/*     */       public int generateHashCode(PolicyMapKey key) {
/*  95 */         int result = 17;
/*     */         
/*  97 */         result = 37 * result + key.getService().hashCode();
/*  98 */         result = 37 * result + ((key.getPort() == null) ? 0 : key.getPort().hashCode());
/*     */         
/* 100 */         return result;
/*     */       }
/*     */     };
/*     */   
/* 104 */   private static final PolicyMapKeyHandler operationAndInputOutputMessageKeyHandler = new PolicyMapKeyHandler()
/*     */     {
/*     */       public boolean areEqual(PolicyMapKey key1, PolicyMapKey key2)
/*     */       {
/* 108 */         boolean retVal = true;
/*     */         
/* 110 */         retVal = (retVal && key1.getService().equals(key2.getService()));
/* 111 */         retVal = (retVal && ((key1.getPort() == null) ? (key2.getPort() == null) : key1.getPort().equals(key2.getPort())));
/* 112 */         retVal = (retVal && ((key1.getOperation() == null) ? (key2.getOperation() == null) : key1.getOperation().equals(key2.getOperation())));
/*     */         
/* 114 */         return retVal;
/*     */       }
/*     */       
/*     */       public int generateHashCode(PolicyMapKey key) {
/* 118 */         int result = 17;
/*     */         
/* 120 */         result = 37 * result + key.getService().hashCode();
/* 121 */         result = 37 * result + ((key.getPort() == null) ? 0 : key.getPort().hashCode());
/* 122 */         result = 37 * result + ((key.getOperation() == null) ? 0 : key.getOperation().hashCode());
/*     */         
/* 124 */         return result;
/*     */       }
/*     */     };
/*     */   
/* 128 */   private static final PolicyMapKeyHandler faultMessageHandler = new PolicyMapKeyHandler() {
/*     */       public boolean areEqual(PolicyMapKey key1, PolicyMapKey key2) {
/* 130 */         boolean retVal = true;
/*     */         
/* 132 */         retVal = (retVal && key1.getService().equals(key2.getService()));
/* 133 */         retVal = (retVal && ((key1.getPort() == null) ? (key2.getPort() == null) : key1.getPort().equals(key2.getPort())));
/* 134 */         retVal = (retVal && ((key1.getOperation() == null) ? (key2.getOperation() == null) : key1.getOperation().equals(key2.getOperation())));
/* 135 */         retVal = (retVal && ((key1.getFaultMessage() == null) ? (key2.getFaultMessage() == null) : key1.getFaultMessage().equals(key2.getFaultMessage())));
/*     */         
/* 137 */         return retVal;
/*     */       }
/*     */       
/*     */       public int generateHashCode(PolicyMapKey key) {
/* 141 */         int result = 17;
/*     */         
/* 143 */         result = 37 * result + key.getService().hashCode();
/* 144 */         result = 37 * result + ((key.getPort() == null) ? 0 : key.getPort().hashCode());
/* 145 */         result = 37 * result + ((key.getOperation() == null) ? 0 : key.getOperation().hashCode());
/* 146 */         result = 37 * result + ((key.getFaultMessage() == null) ? 0 : key.getFaultMessage().hashCode());
/*     */         
/* 148 */         return result;
/*     */       }
/*     */     };
/*     */   
/*     */   enum ScopeType
/*     */   {
/* 154 */     SERVICE,
/* 155 */     ENDPOINT,
/* 156 */     OPERATION,
/* 157 */     INPUT_MESSAGE,
/* 158 */     OUTPUT_MESSAGE,
/* 159 */     FAULT_MESSAGE;
/*     */   }
/*     */   
/*     */   private static final class ScopeMap implements Iterable<Policy> {
/* 163 */     private final Map<PolicyMapKey, PolicyScope> internalMap = new HashMap<PolicyMapKey, PolicyScope>();
/*     */     private final PolicyMapKeyHandler scopeKeyHandler;
/*     */     private final PolicyMerger merger;
/*     */     
/*     */     ScopeMap(PolicyMerger merger, PolicyMapKeyHandler scopeKeyHandler) {
/* 168 */       this.merger = merger;
/* 169 */       this.scopeKeyHandler = scopeKeyHandler;
/*     */     }
/*     */     
/*     */     Policy getEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 173 */       PolicyScope scope = this.internalMap.get(createLocalCopy(key));
/* 174 */       return (scope == null) ? null : scope.getEffectivePolicy(this.merger);
/*     */     }
/*     */     
/*     */     void putSubject(PolicyMapKey key, PolicySubject subject) {
/* 178 */       PolicyMapKey localKey = createLocalCopy(key);
/* 179 */       PolicyScope scope = this.internalMap.get(localKey);
/* 180 */       if (scope == null) {
/* 181 */         List<PolicySubject> list = new LinkedList<PolicySubject>();
/* 182 */         list.add(subject);
/* 183 */         this.internalMap.put(localKey, new PolicyScope(list));
/*     */       } else {
/* 185 */         scope.attach(subject);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void setNewEffectivePolicy(PolicyMapKey key, Policy newEffectivePolicy) {
/* 192 */       PolicySubject subject = new PolicySubject(key, newEffectivePolicy);
/*     */       
/* 194 */       PolicyMapKey localKey = createLocalCopy(key);
/* 195 */       PolicyScope scope = this.internalMap.get(localKey);
/* 196 */       if (scope == null) {
/* 197 */         List<PolicySubject> list = new LinkedList<PolicySubject>();
/* 198 */         list.add(subject);
/* 199 */         this.internalMap.put(localKey, new PolicyScope(list));
/*     */       } else {
/* 201 */         scope.dettachAllSubjects();
/* 202 */         scope.attach(subject);
/*     */       } 
/*     */     }
/*     */     
/*     */     Collection<PolicyScope> getStoredScopes() {
/* 207 */       return this.internalMap.values();
/*     */     }
/*     */     
/*     */     Set<PolicyMapKey> getAllKeys() {
/* 211 */       return this.internalMap.keySet();
/*     */     }
/*     */     
/*     */     private PolicyMapKey createLocalCopy(PolicyMapKey key) {
/* 215 */       if (key == null) {
/* 216 */         throw (IllegalArgumentException)PolicyMap.LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0045_POLICY_MAP_KEY_MUST_NOT_BE_NULL()));
/*     */       }
/*     */       
/* 219 */       PolicyMapKey localKeyCopy = new PolicyMapKey(key);
/* 220 */       localKeyCopy.setHandler(this.scopeKeyHandler);
/*     */       
/* 222 */       return localKeyCopy;
/*     */     }
/*     */     
/*     */     public Iterator<Policy> iterator() {
/* 226 */       return new Iterator<Policy>() {
/* 227 */           private final Iterator<PolicyMapKey> keysIterator = PolicyMap.ScopeMap.this.internalMap.keySet().iterator();
/*     */           
/*     */           public boolean hasNext() {
/* 230 */             return this.keysIterator.hasNext();
/*     */           }
/*     */           
/*     */           public Policy next() {
/* 234 */             PolicyMapKey key = this.keysIterator.next();
/*     */             try {
/* 236 */               return PolicyMap.ScopeMap.this.getEffectivePolicy(key);
/* 237 */             } catch (PolicyException e) {
/* 238 */               throw (IllegalStateException)PolicyMap.LOGGER.logSevereException(new IllegalStateException(LocalizationMessages.WSP_0069_EXCEPTION_WHILE_RETRIEVING_EFFECTIVE_POLICY_FOR_KEY(key), e));
/*     */             } 
/*     */           }
/*     */           
/*     */           public void remove() {
/* 243 */             throw (UnsupportedOperationException)PolicyMap.LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0034_REMOVE_OPERATION_NOT_SUPPORTED()));
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 249 */       return this.internalMap.isEmpty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 254 */       return this.internalMap.toString();
/*     */     }
/*     */   }
/*     */   
/* 258 */   private static final PolicyMerger merger = PolicyMerger.getMerger();
/*     */   
/* 260 */   private final ScopeMap serviceMap = new ScopeMap(merger, serviceKeyHandler);
/* 261 */   private final ScopeMap endpointMap = new ScopeMap(merger, endpointKeyHandler);
/* 262 */   private final ScopeMap operationMap = new ScopeMap(merger, operationAndInputOutputMessageKeyHandler);
/* 263 */   private final ScopeMap inputMessageMap = new ScopeMap(merger, operationAndInputOutputMessageKeyHandler);
/* 264 */   private final ScopeMap outputMessageMap = new ScopeMap(merger, operationAndInputOutputMessageKeyHandler);
/* 265 */   private final ScopeMap faultMessageMap = new ScopeMap(merger, faultMessageHandler);
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
/*     */   public static PolicyMap createPolicyMap(Collection<? extends PolicyMapMutator> mutators) {
/* 281 */     PolicyMap result = new PolicyMap();
/*     */     
/* 283 */     if (mutators != null && !mutators.isEmpty()) {
/* 284 */       for (PolicyMapMutator mutator : mutators) {
/* 285 */         mutator.connect(result);
/*     */       }
/*     */     }
/*     */     
/* 289 */     return result;
/*     */   }
/*     */   
/*     */   public Policy getServiceEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 293 */     return this.serviceMap.getEffectivePolicy(key);
/*     */   }
/*     */   
/*     */   public Policy getEndpointEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 297 */     return this.endpointMap.getEffectivePolicy(key);
/*     */   }
/*     */   
/*     */   public Policy getOperationEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 301 */     return this.operationMap.getEffectivePolicy(key);
/*     */   }
/*     */   
/*     */   public Policy getInputMessageEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 305 */     return this.inputMessageMap.getEffectivePolicy(key);
/*     */   }
/*     */   
/*     */   public Policy getOutputMessageEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 309 */     return this.outputMessageMap.getEffectivePolicy(key);
/*     */   }
/*     */   
/*     */   public Policy getFaultMessageEffectivePolicy(PolicyMapKey key) throws PolicyException {
/* 313 */     return this.faultMessageMap.getEffectivePolicy(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllServiceScopeKeys() {
/* 322 */     return this.serviceMap.getAllKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllEndpointScopeKeys() {
/* 331 */     return this.endpointMap.getAllKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllOperationScopeKeys() {
/* 340 */     return this.operationMap.getAllKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllInputMessageScopeKeys() {
/* 349 */     return this.inputMessageMap.getAllKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllOutputMessageScopeKeys() {
/* 358 */     return this.outputMessageMap.getAllKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicyMapKey> getAllFaultMessageScopeKeys() {
/* 367 */     return this.faultMessageMap.getAllKeys();
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
/*     */   void putSubject(ScopeType scopeType, PolicyMapKey key, PolicySubject subject) {
/* 380 */     switch (scopeType) {
/*     */       case SERVICE:
/* 382 */         this.serviceMap.putSubject(key, subject);
/*     */         return;
/*     */       case ENDPOINT:
/* 385 */         this.endpointMap.putSubject(key, subject);
/*     */         return;
/*     */       case OPERATION:
/* 388 */         this.operationMap.putSubject(key, subject);
/*     */         return;
/*     */       case INPUT_MESSAGE:
/* 391 */         this.inputMessageMap.putSubject(key, subject);
/*     */         return;
/*     */       case OUTPUT_MESSAGE:
/* 394 */         this.outputMessageMap.putSubject(key, subject);
/*     */         return;
/*     */       case FAULT_MESSAGE:
/* 397 */         this.faultMessageMap.putSubject(key, subject);
/*     */         return;
/*     */     } 
/* 400 */     throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0002_UNRECOGNIZED_SCOPE_TYPE(scopeType)));
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
/*     */ 
/*     */   
/*     */   void setNewEffectivePolicyForScope(ScopeType scopeType, PolicyMapKey key, Policy newEffectivePolicy) throws IllegalArgumentException {
/* 417 */     if (scopeType == null || key == null || newEffectivePolicy == null) {
/* 418 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0062_INPUT_PARAMS_MUST_NOT_BE_NULL()));
/*     */     }
/*     */     
/* 421 */     switch (scopeType) {
/*     */       case SERVICE:
/* 423 */         this.serviceMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */       case ENDPOINT:
/* 426 */         this.endpointMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */       case OPERATION:
/* 429 */         this.operationMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */       case INPUT_MESSAGE:
/* 432 */         this.inputMessageMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */       case OUTPUT_MESSAGE:
/* 435 */         this.outputMessageMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */       case FAULT_MESSAGE:
/* 438 */         this.faultMessageMap.setNewEffectivePolicy(key, newEffectivePolicy);
/*     */         return;
/*     */     } 
/* 441 */     throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0002_UNRECOGNIZED_SCOPE_TYPE(scopeType)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<PolicySubject> getPolicySubjects() {
/* 451 */     List<PolicySubject> subjects = new LinkedList<PolicySubject>();
/* 452 */     addSubjects(subjects, this.serviceMap);
/* 453 */     addSubjects(subjects, this.endpointMap);
/* 454 */     addSubjects(subjects, this.operationMap);
/* 455 */     addSubjects(subjects, this.inputMessageMap);
/* 456 */     addSubjects(subjects, this.outputMessageMap);
/* 457 */     addSubjects(subjects, this.faultMessageMap);
/* 458 */     return subjects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInputMessageSubject(PolicySubject subject) {
/* 465 */     for (PolicyScope scope : this.inputMessageMap.getStoredScopes()) {
/* 466 */       if (scope.getPolicySubjects().contains(subject)) {
/* 467 */         return true;
/*     */       }
/*     */     } 
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOutputMessageSubject(PolicySubject subject) {
/* 477 */     for (PolicyScope scope : this.outputMessageMap.getStoredScopes()) {
/* 478 */       if (scope.getPolicySubjects().contains(subject)) {
/* 479 */         return true;
/*     */       }
/*     */     } 
/* 482 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFaultMessageSubject(PolicySubject subject) {
/* 490 */     for (PolicyScope scope : this.faultMessageMap.getStoredScopes()) {
/* 491 */       if (scope.getPolicySubjects().contains(subject)) {
/* 492 */         return true;
/*     */       }
/*     */     } 
/* 495 */     return false;
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
/*     */   public boolean isEmpty() {
/* 507 */     return (this.serviceMap.isEmpty() && this.endpointMap.isEmpty() && this.operationMap.isEmpty() && this.inputMessageMap.isEmpty() && this.outputMessageMap.isEmpty() && this.faultMessageMap.isEmpty());
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
/*     */   private void addSubjects(Collection<PolicySubject> subjects, ScopeMap scopeMap) {
/* 520 */     for (PolicyScope scope : scopeMap.getStoredScopes()) {
/* 521 */       Collection<PolicySubject> scopedSubjects = scope.getPolicySubjects();
/* 522 */       subjects.addAll(scopedSubjects);
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
/*     */   public static PolicyMapKey createWsdlServiceScopeKey(QName service) throws IllegalArgumentException {
/* 534 */     if (service == null) {
/* 535 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0031_SERVICE_PARAM_MUST_NOT_BE_NULL()));
/*     */     }
/* 537 */     return new PolicyMapKey(service, null, null, serviceKeyHandler);
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
/*     */   public static PolicyMapKey createWsdlEndpointScopeKey(QName service, QName port) throws IllegalArgumentException {
/* 549 */     if (service == null || port == null) {
/* 550 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0033_SERVICE_AND_PORT_PARAM_MUST_NOT_BE_NULL(service, port)));
/*     */     }
/* 552 */     return new PolicyMapKey(service, port, null, endpointKeyHandler);
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
/*     */   public static PolicyMapKey createWsdlOperationScopeKey(QName service, QName port, QName operation) throws IllegalArgumentException {
/* 565 */     return createOperationOrInputOutputMessageKey(service, port, operation);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyMapKey createWsdlMessageScopeKey(QName service, QName port, QName operation) throws IllegalArgumentException {
/* 583 */     return createOperationOrInputOutputMessageKey(service, port, operation);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyMapKey createWsdlFaultMessageScopeKey(QName service, QName port, QName operation, QName fault) throws IllegalArgumentException {
/* 603 */     if (service == null || port == null || operation == null || fault == null) {
/* 604 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0030_SERVICE_PORT_OPERATION_FAULT_MSG_PARAM_MUST_NOT_BE_NULL(service, port, operation, fault)));
/*     */     }
/*     */     
/* 607 */     return new PolicyMapKey(service, port, operation, fault, faultMessageHandler);
/*     */   }
/*     */   
/*     */   private static PolicyMapKey createOperationOrInputOutputMessageKey(QName service, QName port, QName operation) {
/* 611 */     if (service == null || port == null || operation == null) {
/* 612 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_0029_SERVICE_PORT_OPERATION_PARAM_MUST_NOT_BE_NULL(service, port, operation)));
/*     */     }
/*     */     
/* 615 */     return new PolicyMapKey(service, port, operation, operationAndInputOutputMessageKeyHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 621 */     StringBuffer result = new StringBuffer();
/* 622 */     if (null != this.serviceMap) {
/* 623 */       result.append("\nServiceMap=").append(this.serviceMap);
/*     */     }
/* 625 */     if (null != this.endpointMap) {
/* 626 */       result.append("\nEndpointMap=").append(this.endpointMap);
/*     */     }
/* 628 */     if (null != this.operationMap) {
/* 629 */       result.append("\nOperationMap=").append(this.operationMap);
/*     */     }
/* 631 */     if (null != this.inputMessageMap) {
/* 632 */       result.append("\nInputMessageMap=").append(this.inputMessageMap);
/*     */     }
/* 634 */     if (null != this.outputMessageMap) {
/* 635 */       result.append("\nOutputMessageMap=").append(this.outputMessageMap);
/*     */     }
/* 637 */     if (null != this.faultMessageMap) {
/* 638 */       result.append("\nFaultMessageMap=").append(this.faultMessageMap);
/*     */     }
/* 640 */     return result.toString();
/*     */   }
/*     */   
/*     */   public Iterator<Policy> iterator() {
/* 644 */     return new Iterator<Policy>()
/*     */       {
/*     */         private final Iterator<Iterator<Policy>> mainIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         private Iterator<Policy> currentScopeIterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 662 */           while (!this.currentScopeIterator.hasNext()) {
/* 663 */             if (this.mainIterator.hasNext()) {
/* 664 */               this.currentScopeIterator = this.mainIterator.next(); continue;
/*     */             } 
/* 666 */             return false;
/*     */           } 
/*     */ 
/*     */           
/* 670 */           return true;
/*     */         }
/*     */         
/*     */         public Policy next() {
/* 674 */           if (hasNext()) {
/* 675 */             return this.currentScopeIterator.next();
/*     */           }
/* 677 */           throw (NoSuchElementException)PolicyMap.LOGGER.logSevereException(new NoSuchElementException(LocalizationMessages.WSP_0054_NO_MORE_ELEMS_IN_POLICY_MAP()));
/*     */         }
/*     */         
/*     */         public void remove() {
/* 681 */           throw (UnsupportedOperationException)PolicyMap.LOGGER.logSevereException(new UnsupportedOperationException(LocalizationMessages.WSP_0034_REMOVE_OPERATION_NOT_SUPPORTED()));
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\PolicyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */