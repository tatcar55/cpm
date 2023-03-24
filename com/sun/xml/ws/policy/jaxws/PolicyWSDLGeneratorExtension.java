/*     */ package com.sun.xml.ws.policy.jaxws;
/*     */ 
/*     */ import com.sun.xml.txw2.TypedXmlWriter;
/*     */ import com.sun.xml.ws.addressing.policy.AddressingPolicyMapConfigurator;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.model.CheckedException;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*     */ import com.sun.xml.ws.api.policy.ModelGenerator;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import com.sun.xml.ws.encoding.policy.MtomPolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapExtender;
/*     */ import com.sun.xml.ws.policy.PolicyMapMutator;
/*     */ import com.sun.xml.ws.policy.PolicyMapUtil;
/*     */ import com.sun.xml.ws.policy.PolicyMerger;
/*     */ import com.sun.xml.ws.policy.PolicySubject;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicyModelMarshaller;
/*     */ import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.wspolicy.XmlToken;
/*     */ import com.sun.xml.ws.policy.subject.WsdlBindingSubject;
/*     */ import com.sun.xml.ws.resources.PolicyMessages;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class PolicyWSDLGeneratorExtension
/*     */   extends WSDLGeneratorExtension
/*     */ {
/*     */   enum ScopeType
/*     */   {
/* 104 */     SERVICE,
/* 105 */     ENDPOINT,
/* 106 */     OPERATION,
/* 107 */     INPUT_MESSAGE,
/* 108 */     OUTPUT_MESSAGE,
/* 109 */     FAULT_MESSAGE;
/*     */   }
/* 111 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyWSDLGeneratorExtension.class);
/*     */   private PolicyMap policyMap;
/*     */   private SEIModel seiModel;
/* 114 */   private final Collection<PolicySubject> subjects = new LinkedList<PolicySubject>();
/* 115 */   private final PolicyModelMarshaller marshaller = PolicyModelMarshaller.getXmlMarshaller(true);
/* 116 */   private final PolicyMerger merger = PolicyMerger.getMerger();
/*     */ 
/*     */   
/*     */   public void start(WSDLGenExtnContext context) {
/* 120 */     LOGGER.entering();
/*     */     try {
/* 122 */       this.seiModel = context.getModel();
/*     */       
/* 124 */       PolicyMapConfigurator[] policyMapConfigurators = loadConfigurators();
/* 125 */       PolicyMapExtender[] extenders = new PolicyMapExtender[policyMapConfigurators.length];
/* 126 */       for (int i = 0; i < policyMapConfigurators.length; i++) {
/* 127 */         extenders[i] = PolicyMapExtender.createPolicyMapExtender();
/*     */       }
/*     */       
/* 130 */       this.policyMap = PolicyResolverFactory.create().resolve(new PolicyResolver.ServerContext(this.policyMap, context.getContainer(), context.getEndpointClass(), false, (PolicyMapMutator[])extenders));
/*     */ 
/*     */       
/* 133 */       if (this.policyMap == null) {
/* 134 */         LOGGER.fine(PolicyMessages.WSP_1019_CREATE_EMPTY_POLICY_MAP());
/* 135 */         this.policyMap = PolicyMap.createPolicyMap(Arrays.asList(extenders));
/*     */       } 
/*     */       
/* 138 */       WSBinding binding = context.getBinding();
/*     */       try {
/* 140 */         Collection<PolicySubject> policySubjects = new LinkedList<PolicySubject>();
/* 141 */         for (int j = 0; j < policyMapConfigurators.length; j++) {
/* 142 */           policySubjects.addAll(policyMapConfigurators[j].update(this.policyMap, this.seiModel, binding));
/* 143 */           extenders[j].disconnect();
/*     */         } 
/* 145 */         PolicyMapUtil.insertPolicies(this.policyMap, policySubjects, this.seiModel.getServiceQName(), this.seiModel.getPortName());
/* 146 */       } catch (PolicyException e) {
/* 147 */         throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1017_MAP_UPDATE_FAILED(), e));
/*     */       } 
/* 149 */       TypedXmlWriter root = context.getRoot();
/* 150 */       root._namespace(NamespaceVersion.v1_2.toString(), NamespaceVersion.v1_2.getDefaultNamespacePrefix());
/* 151 */       root._namespace(NamespaceVersion.v1_5.toString(), NamespaceVersion.v1_5.getDefaultNamespacePrefix());
/* 152 */       root._namespace("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu");
/*     */     } finally {
/*     */       
/* 155 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDefinitionsExtension(TypedXmlWriter definitions) {
/*     */     try {
/* 162 */       LOGGER.entering();
/* 163 */       if (this.policyMap == null) {
/* 164 */         LOGGER.fine(PolicyMessages.WSP_1009_NOT_MARSHALLING_ANY_POLICIES_POLICY_MAP_IS_NULL());
/*     */       } else {
/* 166 */         this.subjects.addAll(this.policyMap.getPolicySubjects());
/* 167 */         PolicyModelGenerator generator = ModelGenerator.getGenerator();
/* 168 */         Set<String> policyIDsOrNamesWritten = new HashSet<String>();
/* 169 */         for (PolicySubject subject : this.subjects) {
/* 170 */           Policy policy; if (subject.getSubject() == null) {
/* 171 */             LOGGER.fine(PolicyMessages.WSP_1008_NOT_MARSHALLING_WSDL_SUBJ_NULL(subject));
/*     */             continue;
/*     */           } 
/*     */           try {
/* 175 */             policy = subject.getEffectivePolicy(this.merger);
/* 176 */           } catch (PolicyException e) {
/* 177 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1011_FAILED_TO_RETRIEVE_EFFECTIVE_POLICY_FOR_SUBJECT(subject.toString()), e));
/*     */           } 
/* 179 */           if (null == policy.getIdOrName() || policyIDsOrNamesWritten.contains(policy.getIdOrName())) {
/* 180 */             LOGGER.fine(PolicyMessages.WSP_1016_POLICY_ID_NULL_OR_DUPLICATE(policy)); continue;
/*     */           } 
/*     */           try {
/* 183 */             PolicySourceModel policyInfoset = generator.translate(policy);
/* 184 */             this.marshaller.marshal(policyInfoset, definitions);
/* 185 */           } catch (PolicyException e) {
/* 186 */             throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1018_FAILED_TO_MARSHALL_POLICY(policy.getIdOrName()), e));
/*     */           } 
/* 188 */           policyIDsOrNamesWritten.add(policy.getIdOrName());
/*     */         }
/*     */       
/*     */       } 
/*     */     } finally {
/*     */       
/* 194 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServiceExtension(TypedXmlWriter service) {
/* 200 */     LOGGER.entering();
/* 201 */     String serviceName = (null == this.seiModel) ? null : this.seiModel.getServiceQName().getLocalPart();
/* 202 */     selectAndProcessSubject(service, WSDLService.class, ScopeType.SERVICE, serviceName);
/* 203 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPortExtension(TypedXmlWriter port) {
/* 208 */     LOGGER.entering();
/* 209 */     String portName = (null == this.seiModel) ? null : this.seiModel.getPortName().getLocalPart();
/* 210 */     selectAndProcessSubject(port, WSDLPort.class, ScopeType.ENDPOINT, portName);
/* 211 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPortTypeExtension(TypedXmlWriter portType) {
/* 216 */     LOGGER.entering();
/* 217 */     String portTypeName = (null == this.seiModel) ? null : this.seiModel.getPortTypeName().getLocalPart();
/* 218 */     selectAndProcessSubject(portType, WSDLPortType.class, ScopeType.ENDPOINT, portTypeName);
/* 219 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingExtension(TypedXmlWriter binding) {
/* 224 */     LOGGER.entering();
/* 225 */     QName bindingName = (null == this.seiModel) ? null : this.seiModel.getBoundPortTypeName();
/* 226 */     selectAndProcessBindingSubject(binding, WSDLBoundPortType.class, ScopeType.ENDPOINT, bindingName);
/* 227 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationExtension(TypedXmlWriter operation, JavaMethod method) {
/* 232 */     LOGGER.entering();
/* 233 */     selectAndProcessSubject(operation, WSDLOperation.class, ScopeType.OPERATION, (String)null);
/* 234 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingOperationExtension(TypedXmlWriter operation, JavaMethod method) {
/* 239 */     LOGGER.entering();
/* 240 */     QName operationName = (method == null) ? null : new QName(method.getOwner().getTargetNamespace(), method.getOperationName());
/* 241 */     selectAndProcessBindingSubject(operation, WSDLBoundOperation.class, ScopeType.OPERATION, operationName);
/* 242 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addInputMessageExtension(TypedXmlWriter message, JavaMethod method) {
/* 247 */     LOGGER.entering();
/* 248 */     String messageName = (null == method) ? null : method.getRequestMessageName();
/* 249 */     selectAndProcessSubject(message, WSDLMessage.class, ScopeType.INPUT_MESSAGE, messageName);
/* 250 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOutputMessageExtension(TypedXmlWriter message, JavaMethod method) {
/* 255 */     LOGGER.entering();
/* 256 */     String messageName = (null == method) ? null : method.getResponseMessageName();
/* 257 */     selectAndProcessSubject(message, WSDLMessage.class, ScopeType.OUTPUT_MESSAGE, messageName);
/* 258 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFaultMessageExtension(TypedXmlWriter message, JavaMethod method, CheckedException exception) {
/* 263 */     LOGGER.entering();
/* 264 */     String messageName = (null == exception) ? null : exception.getMessageName();
/* 265 */     selectAndProcessSubject(message, WSDLMessage.class, ScopeType.FAULT_MESSAGE, messageName);
/* 266 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/* 271 */     LOGGER.entering();
/* 272 */     String messageName = (null == method) ? null : method.getRequestMessageName();
/* 273 */     selectAndProcessSubject(input, WSDLInput.class, ScopeType.INPUT_MESSAGE, messageName);
/* 274 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 279 */     LOGGER.entering();
/* 280 */     String messageName = (null == method) ? null : method.getResponseMessageName();
/* 281 */     selectAndProcessSubject(output, WSDLOutput.class, ScopeType.OUTPUT_MESSAGE, messageName);
/* 282 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addOperationFaultExtension(TypedXmlWriter fault, JavaMethod method, CheckedException exception) {
/* 287 */     LOGGER.entering();
/* 288 */     String messageName = (null == exception) ? null : exception.getMessageName();
/* 289 */     selectAndProcessSubject(fault, WSDLFault.class, ScopeType.FAULT_MESSAGE, messageName);
/* 290 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingOperationInputExtension(TypedXmlWriter input, JavaMethod method) {
/* 295 */     LOGGER.entering();
/* 296 */     QName operationName = new QName(method.getOwner().getTargetNamespace(), method.getOperationName());
/* 297 */     selectAndProcessBindingSubject(input, WSDLBoundOperation.class, ScopeType.INPUT_MESSAGE, operationName);
/* 298 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingOperationOutputExtension(TypedXmlWriter output, JavaMethod method) {
/* 303 */     LOGGER.entering();
/* 304 */     QName operationName = new QName(method.getOwner().getTargetNamespace(), method.getOperationName());
/* 305 */     selectAndProcessBindingSubject(output, WSDLBoundOperation.class, ScopeType.OUTPUT_MESSAGE, operationName);
/* 306 */     LOGGER.exiting();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBindingOperationFaultExtension(TypedXmlWriter writer, JavaMethod method, CheckedException exception) {
/* 311 */     LOGGER.entering(new Object[] { writer, method, exception });
/* 312 */     if (this.subjects != null) {
/* 313 */       for (PolicySubject subject : this.subjects) {
/* 314 */         if (this.policyMap.isFaultMessageSubject(subject)) {
/* 315 */           Object concreteSubject = subject.getSubject();
/* 316 */           if (concreteSubject != null) {
/* 317 */             String exceptionName = (exception == null) ? null : exception.getMessageName();
/* 318 */             if (exceptionName == null) {
/* 319 */               writePolicyOrReferenceIt(subject, writer);
/*     */             }
/* 321 */             if (WSDLBoundFaultContainer.class.isInstance(concreteSubject)) {
/* 322 */               WSDLBoundFaultContainer faultContainer = (WSDLBoundFaultContainer)concreteSubject;
/* 323 */               WSDLBoundFault fault = faultContainer.getBoundFault();
/* 324 */               WSDLBoundOperation operation = faultContainer.getBoundOperation();
/* 325 */               if (exceptionName.equals(fault.getName()) && operation.getName().getLocalPart().equals(method.getOperationName()))
/*     */               {
/* 327 */                 writePolicyOrReferenceIt(subject, writer); } 
/*     */               continue;
/*     */             } 
/* 330 */             if (WsdlBindingSubject.class.isInstance(concreteSubject)) {
/* 331 */               WsdlBindingSubject wsdlSubject = (WsdlBindingSubject)concreteSubject;
/* 332 */               if (wsdlSubject.getMessageType() == WsdlBindingSubject.WsdlMessageType.FAULT && exception.getOwner().getTargetNamespace().equals(wsdlSubject.getName().getNamespaceURI()) && exceptionName.equals(wsdlSubject.getName().getLocalPart()))
/*     */               {
/*     */                 
/* 335 */                 writePolicyOrReferenceIt(subject, writer);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 342 */     LOGGER.exiting();
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
/*     */   private void selectAndProcessSubject(TypedXmlWriter xmlWriter, Class clazz, ScopeType scopeType, QName bindingName) {
/* 355 */     LOGGER.entering(new Object[] { xmlWriter, clazz, scopeType, bindingName });
/* 356 */     if (bindingName == null) {
/* 357 */       selectAndProcessSubject(xmlWriter, clazz, scopeType, (String)null);
/*     */     } else {
/* 359 */       if (this.subjects != null) {
/* 360 */         for (PolicySubject subject : this.subjects) {
/* 361 */           if (bindingName.equals(subject.getSubject())) {
/* 362 */             writePolicyOrReferenceIt(subject, xmlWriter);
/*     */           }
/*     */         } 
/*     */       }
/* 366 */       selectAndProcessSubject(xmlWriter, clazz, scopeType, bindingName.getLocalPart());
/*     */     } 
/* 368 */     LOGGER.exiting();
/*     */   }
/*     */   
/*     */   private void selectAndProcessBindingSubject(TypedXmlWriter xmlWriter, Class clazz, ScopeType scopeType, QName bindingName) {
/* 372 */     LOGGER.entering(new Object[] { xmlWriter, clazz, scopeType, bindingName });
/* 373 */     if (this.subjects != null && bindingName != null) {
/* 374 */       for (PolicySubject subject : this.subjects) {
/* 375 */         if (subject.getSubject() instanceof WsdlBindingSubject) {
/* 376 */           WsdlBindingSubject wsdlSubject = (WsdlBindingSubject)subject.getSubject();
/* 377 */           if (bindingName.equals(wsdlSubject.getName())) {
/* 378 */             writePolicyOrReferenceIt(subject, xmlWriter);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 383 */     selectAndProcessSubject(xmlWriter, clazz, scopeType, bindingName);
/* 384 */     LOGGER.exiting();
/*     */   }
/*     */   
/*     */   private void selectAndProcessSubject(TypedXmlWriter xmlWriter, Class clazz, ScopeType scopeType, String wsdlName) {
/* 388 */     LOGGER.entering(new Object[] { xmlWriter, clazz, scopeType, wsdlName });
/* 389 */     if (this.subjects != null) {
/* 390 */       for (PolicySubject subject : this.subjects) {
/* 391 */         if (isCorrectType(this.policyMap, subject, scopeType)) {
/* 392 */           Object concreteSubject = subject.getSubject();
/* 393 */           if (concreteSubject != null && clazz.isInstance(concreteSubject)) {
/* 394 */             if (null == wsdlName) {
/* 395 */               writePolicyOrReferenceIt(subject, xmlWriter); continue;
/*     */             } 
/*     */             try {
/* 398 */               Method getNameMethod = clazz.getDeclaredMethod("getName", new Class[0]);
/* 399 */               if (stringEqualsToStringOrQName(wsdlName, getNameMethod.invoke(concreteSubject, new Object[0]))) {
/* 400 */                 writePolicyOrReferenceIt(subject, xmlWriter);
/*     */               }
/* 402 */             } catch (NoSuchMethodException e) {
/* 403 */               throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1003_UNABLE_TO_CHECK_ELEMENT_NAME(clazz.getName(), wsdlName), e));
/* 404 */             } catch (IllegalAccessException e) {
/* 405 */               throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1003_UNABLE_TO_CHECK_ELEMENT_NAME(clazz.getName(), wsdlName), e));
/* 406 */             } catch (InvocationTargetException e) {
/* 407 */               throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1003_UNABLE_TO_CHECK_ELEMENT_NAME(clazz.getName(), wsdlName), e));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 414 */     LOGGER.exiting();
/*     */   }
/*     */   
/*     */   private static boolean isCorrectType(PolicyMap map, PolicySubject subject, ScopeType type) {
/* 418 */     switch (type) {
/*     */       case OPERATION:
/* 420 */         return (!map.isInputMessageSubject(subject) && !map.isOutputMessageSubject(subject) && !map.isFaultMessageSubject(subject));
/*     */       case INPUT_MESSAGE:
/* 422 */         return map.isInputMessageSubject(subject);
/*     */       case OUTPUT_MESSAGE:
/* 424 */         return map.isOutputMessageSubject(subject);
/*     */       case FAULT_MESSAGE:
/* 426 */         return map.isFaultMessageSubject(subject);
/*     */     } 
/* 428 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean stringEqualsToStringOrQName(String first, Object second) {
/* 433 */     return (second instanceof QName) ? first.equals(((QName)second).getLocalPart()) : first.equals(second);
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
/*     */   private void writePolicyOrReferenceIt(PolicySubject subject, TypedXmlWriter writer) {
/*     */     Policy policy;
/*     */     try {
/* 448 */       policy = subject.getEffectivePolicy(this.merger);
/* 449 */     } catch (PolicyException e) {
/* 450 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1011_FAILED_TO_RETRIEVE_EFFECTIVE_POLICY_FOR_SUBJECT(subject.toString()), e));
/*     */     } 
/* 452 */     if (policy != null) {
/* 453 */       if (null == policy.getIdOrName()) {
/* 454 */         PolicyModelGenerator generator = ModelGenerator.getGenerator();
/*     */         try {
/* 456 */           PolicySourceModel policyInfoset = generator.translate(policy);
/* 457 */           this.marshaller.marshal(policyInfoset, writer);
/* 458 */         } catch (PolicyException pe) {
/* 459 */           throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(PolicyMessages.WSP_1002_UNABLE_TO_MARSHALL_POLICY_OR_POLICY_REFERENCE(), pe));
/*     */         } 
/*     */       } else {
/* 462 */         TypedXmlWriter policyReference = writer._element(policy.getNamespaceVersion().asQName(XmlToken.PolicyReference), TypedXmlWriter.class);
/* 463 */         policyReference._attribute(XmlToken.Uri.toString(), '#' + policy.getIdOrName());
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private PolicyMapConfigurator[] loadConfigurators() {
/* 469 */     Collection<PolicyMapConfigurator> configurators = new LinkedList<PolicyMapConfigurator>();
/*     */ 
/*     */     
/* 472 */     configurators.add(new AddressingPolicyMapConfigurator());
/* 473 */     configurators.add(new MtomPolicyMapConfigurator());
/*     */ 
/*     */     
/* 476 */     PolicyUtil.addServiceProviders(configurators, PolicyMapConfigurator.class);
/*     */     
/* 478 */     return configurators.<PolicyMapConfigurator>toArray(new PolicyMapConfigurator[configurators.size()]);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\jaxws\PolicyWSDLGeneratorExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */