/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.databinding.WSDLGenInfo;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*     */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*     */ import com.sun.xml.ws.api.server.AsyncProvider;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.api.server.InstanceResolver;
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
/*     */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.binding.SOAPBindingImpl;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.db.DatabindingImpl;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.model.ReflectAnnotationReader;
/*     */ import com.sun.xml.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLModelImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLServiceImpl;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.jaxws.PolicyUtil;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.server.provider.ProviderInvokerTube;
/*     */ import com.sun.xml.ws.server.sei.SEIInvokerTube;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationProcessor;
/*     */ import com.sun.xml.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Logger;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.WebServiceProvider;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointFactory
/*     */ {
/* 120 */   private static final EndpointFactory instance = new EndpointFactory();
/*     */   
/*     */   public static EndpointFactory getInstance() {
/* 123 */     return instance;
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
/*     */   public static <T> WSEndpoint<T> createEndpoint(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous) {
/* 143 */     return createEndpoint(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> WSEndpoint<T> createEndpoint(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous, boolean isStandard) {
/* 154 */     EndpointFactory factory = (container != null) ? (EndpointFactory)container.getSPI(EndpointFactory.class) : null;
/* 155 */     if (factory == null) {
/* 156 */       factory = getInstance();
/*     */     }
/* 158 */     return factory.create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, isStandard);
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
/*     */   
/*     */   public <T> WSEndpoint<T> create(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous) {
/* 179 */     return create(implType, processHandlerAnnotation, invoker, serviceName, portName, container, binding, primaryWsdl, metadata, resolver, isTransportSynchronous, true);
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
/*     */   public <T> WSEndpoint<T> create(Class<T> implType, boolean processHandlerAnnotation, @Nullable Invoker invoker, @Nullable QName serviceName, @Nullable QName portName, @Nullable Container container, @Nullable WSBinding binding, @Nullable SDDocumentSource primaryWsdl, @Nullable Collection<? extends SDDocumentSource> metadata, EntityResolver resolver, boolean isTransportSynchronous, boolean isStandard) {
/*     */     BindingImpl bindingImpl;
/*     */     EndpointAwareTube terminal;
/* 193 */     if (implType == null) {
/* 194 */       throw new IllegalArgumentException();
/*     */     }
/* 196 */     MetadataReader metadataReader = getExternalMetadatReader(implType, binding);
/*     */     
/* 198 */     if (isStandard) {
/* 199 */       verifyImplementorClass(implType, metadataReader);
/*     */     }
/*     */     
/* 202 */     if (invoker == null) {
/* 203 */       invoker = InstanceResolver.createDefault(implType).createInvoker();
/*     */     }
/*     */     
/* 206 */     List<SDDocumentSource> md = new ArrayList<SDDocumentSource>();
/* 207 */     if (metadata != null) {
/* 208 */       md.addAll(metadata);
/*     */     }
/* 210 */     if (primaryWsdl != null && !md.contains(primaryWsdl)) {
/* 211 */       md.add(primaryWsdl);
/*     */     }
/* 213 */     if (container == null) {
/* 214 */       container = ContainerResolver.getInstance().getContainer();
/*     */     }
/* 216 */     if (serviceName == null) {
/* 217 */       serviceName = getDefaultServiceName(implType, metadataReader);
/*     */     }
/* 219 */     if (portName == null) {
/* 220 */       portName = getDefaultPortName(serviceName, implType, metadataReader);
/*     */     }
/*     */     
/* 223 */     String serviceNS = serviceName.getNamespaceURI();
/* 224 */     String portNS = portName.getNamespaceURI();
/* 225 */     if (!serviceNS.equals(portNS)) {
/* 226 */       throw new ServerRtException("wrong.tns.for.port", new Object[] { portNS, serviceNS });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 231 */     if (binding == null) {
/* 232 */       bindingImpl = BindingImpl.create(BindingID.parse(implType));
/*     */     }
/* 234 */     if (isStandard && primaryWsdl != null) {
/* 235 */       verifyPrimaryWSDL(primaryWsdl, serviceName);
/*     */     }
/*     */     
/* 238 */     QName portTypeName = null;
/* 239 */     if (isStandard && implType.getAnnotation(WebServiceProvider.class) == null) {
/* 240 */       portTypeName = RuntimeModeler.getPortTypeName(implType, metadataReader);
/*     */     }
/*     */ 
/*     */     
/* 244 */     List<SDDocumentImpl> docList = categoriseMetadata(md, serviceName, portTypeName);
/*     */ 
/*     */     
/* 247 */     SDDocumentImpl primaryDoc = (primaryWsdl != null) ? SDDocumentImpl.create(primaryWsdl, serviceName, portTypeName) : findPrimary(docList);
/*     */ 
/*     */     
/* 250 */     WSDLPortImpl wsdlPort = null;
/* 251 */     AbstractSEIModelImpl seiModel = null;
/*     */     
/* 253 */     if (primaryDoc != null) {
/* 254 */       wsdlPort = getWSDLPort(primaryDoc, (List)docList, serviceName, portName, container, resolver);
/*     */     }
/*     */     
/* 257 */     WebServiceFeatureList features = bindingImpl.getFeatures();
/* 258 */     if (isStandard) {
/* 259 */       features.parseAnnotations(implType);
/*     */     }
/* 261 */     PolicyMap policyMap = null;
/*     */     
/* 263 */     if (isUseProviderTube(implType, isStandard)) {
/*     */       Iterable<WebServiceFeature> configFtrs;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       if (wsdlPort != null) {
/* 270 */         policyMap = wsdlPort.getOwner().getParent().getPolicyMap();
/*     */         
/* 272 */         WebServiceFeatureList webServiceFeatureList = wsdlPort.getFeatures();
/*     */       } else {
/*     */         
/* 275 */         policyMap = PolicyResolverFactory.create().resolve(new PolicyResolver.ServerContext(null, container, implType, false, new com.sun.xml.ws.policy.PolicyMapMutator[0]));
/*     */         
/* 277 */         configFtrs = PolicyUtil.getPortScopedFeatures(policyMap, serviceName, portName);
/*     */       } 
/* 279 */       features.mergeFeatures(configFtrs, true);
/* 280 */       terminal = createProviderInvokerTube(implType, (WSBinding)bindingImpl, invoker, container);
/*     */     } else {
/*     */       
/* 283 */       seiModel = createSEIModel((WSDLPort)wsdlPort, implType, serviceName, portName, (WSBinding)bindingImpl);
/* 284 */       if (bindingImpl instanceof SOAPBindingImpl)
/*     */       {
/* 286 */         ((SOAPBindingImpl)bindingImpl).setPortKnownHeaders(((SOAPSEIModel)seiModel).getKnownHeaders());
/*     */       }
/*     */ 
/*     */       
/* 290 */       if (primaryDoc == null) {
/* 291 */         primaryDoc = generateWSDL((WSBinding)bindingImpl, seiModel, docList, container, implType);
/*     */         
/* 293 */         wsdlPort = getWSDLPort(primaryDoc, (List)docList, serviceName, portName, container, resolver);
/* 294 */         seiModel.freeze((WSDLPort)wsdlPort);
/*     */       } 
/* 296 */       policyMap = wsdlPort.getOwner().getParent().getPolicyMap();
/*     */ 
/*     */ 
/*     */       
/* 300 */       features.mergeFeatures((Iterable)wsdlPort.getFeatures(), true);
/* 301 */       terminal = createSEIInvokerTube(seiModel, invoker, (WSBinding)bindingImpl);
/*     */     } 
/*     */ 
/*     */     
/* 305 */     if (processHandlerAnnotation) {
/* 306 */       processHandlerAnnotation((WSBinding)bindingImpl, implType, serviceName, portName);
/*     */     }
/*     */     
/* 309 */     if (primaryDoc != null) {
/* 310 */       docList = findMetadataClosure(primaryDoc, docList, resolver);
/*     */     }
/*     */     
/* 313 */     ServiceDefinitionImpl serviceDefiniton = (primaryDoc != null) ? new ServiceDefinitionImpl(docList, primaryDoc) : null;
/*     */     
/* 315 */     return create(serviceName, portName, (WSBinding)bindingImpl, container, (SEIModel)seiModel, (WSDLPort)wsdlPort, implType, serviceDefiniton, terminal, isTransportSynchronous, policyMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> WSEndpoint<T> create(QName serviceName, QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort wsdlPort, Class<T> implType, ServiceDefinitionImpl serviceDefinition, EndpointAwareTube terminal, boolean isTransportSynchronous, PolicyMap policyMap) {
/* 320 */     return new WSEndpointImpl<T>(serviceName, portName, binding, container, seiModel, wsdlPort, implType, serviceDefinition, terminal, isTransportSynchronous, policyMap);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isUseProviderTube(Class<?> implType, boolean isStandard) {
/* 325 */     return (!isStandard || implType.getAnnotation(WebServiceProvider.class) != null);
/*     */   }
/*     */   
/*     */   protected EndpointAwareTube createSEIInvokerTube(AbstractSEIModelImpl seiModel, Invoker invoker, WSBinding binding) {
/* 329 */     return (EndpointAwareTube)new SEIInvokerTube(seiModel, invoker, binding);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> EndpointAwareTube createProviderInvokerTube(Class<T> implType, WSBinding binding, Invoker invoker, Container container) {
/* 334 */     return (EndpointAwareTube)ProviderInvokerTube.create(implType, binding, invoker, container);
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
/*     */   private static List<SDDocumentImpl> findMetadataClosure(SDDocumentImpl primaryDoc, List<SDDocumentImpl> docList, EntityResolver resolver) {
/* 347 */     Map<String, SDDocumentImpl> oldMap = new HashMap<String, SDDocumentImpl>();
/* 348 */     for (SDDocumentImpl doc : docList) {
/* 349 */       oldMap.put(doc.getSystemId().toString(), doc);
/*     */     }
/*     */     
/* 352 */     Map<String, SDDocumentImpl> newMap = new HashMap<String, SDDocumentImpl>();
/* 353 */     newMap.put(primaryDoc.getSystemId().toString(), primaryDoc);
/*     */     
/* 355 */     List<String> remaining = new ArrayList<String>();
/* 356 */     remaining.addAll(primaryDoc.getImports());
/* 357 */     while (!remaining.isEmpty()) {
/* 358 */       String url = remaining.remove(0);
/* 359 */       SDDocumentImpl doc = oldMap.get(url);
/* 360 */       if (doc == null)
/*     */       {
/* 362 */         if (resolver != null) {
/*     */           try {
/* 364 */             InputSource source = resolver.resolveEntity(null, url);
/* 365 */             if (source != null) {
/* 366 */               MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 367 */               XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(source.getByteStream());
/* 368 */               xsb.createFromXMLStreamReader(reader);
/*     */               
/* 370 */               SDDocumentSource sdocSource = SDDocumentImpl.create(new URL(url), (XMLStreamBuffer)xsb);
/* 371 */               doc = SDDocumentImpl.create(sdocSource, (QName)null, (QName)null);
/*     */             } 
/* 373 */           } catch (Exception ex) {
/* 374 */             ex.printStackTrace();
/*     */           } 
/*     */         }
/*     */       }
/*     */       
/* 379 */       if (doc != null && !newMap.containsKey(url)) {
/* 380 */         newMap.put(url, doc);
/* 381 */         remaining.addAll(doc.getImports());
/*     */       } 
/*     */     } 
/* 384 */     List<SDDocumentImpl> newMetadata = new ArrayList<SDDocumentImpl>();
/* 385 */     newMetadata.addAll(newMap.values());
/* 386 */     return newMetadata;
/*     */   }
/*     */   
/*     */   private static <T> void processHandlerAnnotation(WSBinding binding, Class<T> implType, QName serviceName, QName portName) {
/* 390 */     HandlerAnnotationInfo chainInfo = HandlerAnnotationProcessor.buildHandlerInfo(implType, serviceName, portName, binding);
/*     */ 
/*     */     
/* 393 */     if (chainInfo != null) {
/* 394 */       binding.setHandlerChain(chainInfo.getHandlers());
/* 395 */       if (binding instanceof SOAPBinding) {
/* 396 */         ((SOAPBinding)binding).setRoles(chainInfo.getRoles());
/*     */       }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean verifyImplementorClass(Class<?> clz) {
/* 414 */     return verifyImplementorClass(clz, null);
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
/*     */   public static boolean verifyImplementorClass(Class<?> clz, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 431 */     if (metadataReader == null) {
/* 432 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 435 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, clz);
/* 436 */     WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, clz);
/* 437 */     if (wsProvider == null && ws == null) {
/* 438 */       throw new IllegalArgumentException(clz + " has neither @WebService nor @WebServiceProvider annotation");
/*     */     }
/* 440 */     if (wsProvider != null && ws != null) {
/* 441 */       throw new IllegalArgumentException(clz + " has both @WebService and @WebServiceProvider annotations");
/*     */     }
/* 443 */     if (wsProvider != null) {
/* 444 */       if (Provider.class.isAssignableFrom(clz) || AsyncProvider.class.isAssignableFrom(clz)) {
/* 445 */         return true;
/*     */       }
/* 447 */       throw new IllegalArgumentException(clz + " doesn't implement Provider or AsyncProvider interface");
/*     */     } 
/* 449 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AbstractSEIModelImpl createSEIModel(WSDLPort wsdlPort, Class<?> implType, @NotNull QName serviceName, @NotNull QName portName, WSBinding binding) {
/* 474 */     DatabindingFactory fac = DatabindingFactory.newInstance();
/* 475 */     DatabindingConfig config = new DatabindingConfig();
/* 476 */     config.setEndpointClass(implType);
/* 477 */     config.getMappingInfo().setServiceName(serviceName);
/* 478 */     config.setWsdlPort(wsdlPort);
/* 479 */     config.setWSBinding(binding);
/*     */ 
/*     */     
/* 482 */     config.setClassLoader(implType.getClassLoader());
/* 483 */     config.getMappingInfo().setPortName(portName);
/*     */     
/* 485 */     config.setMetadataReader(getExternalMetadatReader(implType, binding));
/*     */     
/* 487 */     DatabindingImpl rt = (DatabindingImpl)fac.createRuntime(config);
/* 488 */     return (AbstractSEIModelImpl)rt.getModel();
/*     */   }
/*     */   
/*     */   public static MetadataReader getExternalMetadatReader(Class<?> implType, WSBinding binding) {
/* 492 */     ExternalMetadataFeature ef = (ExternalMetadataFeature)binding.getFeature(ExternalMetadataFeature.class);
/*     */     
/* 494 */     if (ef != null)
/* 495 */       return ef.getMetadataReader(implType.getClassLoader()); 
/* 496 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType) {
/* 523 */     return getDefaultServiceName(implType, (MetadataReader)null);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType, MetadataReader metadataReader) {
/* 527 */     return getDefaultServiceName(implType, true, metadataReader);
/*     */   }
/*     */   
/*     */   @NotNull
/* 531 */   public static QName getDefaultServiceName(Class<?> implType, boolean isStandard) { return getDefaultServiceName(implType, isStandard, null); } @NotNull
/*     */   public static QName getDefaultServiceName(Class<?> implType, boolean isStandard, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/*     */     QName serviceName;
/* 535 */     if (metadataReader == null) {
/* 536 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 539 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, implType);
/* 540 */     if (wsProvider != null) {
/* 541 */       String tns = wsProvider.targetNamespace();
/* 542 */       String local = wsProvider.serviceName();
/* 543 */       serviceName = new QName(tns, local);
/*     */     } else {
/* 545 */       serviceName = RuntimeModeler.getServiceName(implType, (MetadataReader)reflectAnnotationReader, isStandard);
/*     */     } 
/* 547 */     assert serviceName != null;
/* 548 */     return serviceName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType) {
/* 558 */     return getDefaultPortName(serviceName, implType, (MetadataReader)null);
/*     */   }
/*     */   @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, MetadataReader metadataReader) {
/* 562 */     return getDefaultPortName(serviceName, implType, true, metadataReader);
/*     */   }
/*     */   
/*     */   @NotNull
/* 566 */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, boolean isStandard) { return getDefaultPortName(serviceName, implType, isStandard, null); } @NotNull
/*     */   public static QName getDefaultPortName(QName serviceName, Class<?> implType, boolean isStandard, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/*     */     QName portName;
/* 570 */     if (metadataReader == null) {
/* 571 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 574 */     WebServiceProvider wsProvider = (WebServiceProvider)reflectAnnotationReader.getAnnotation(WebServiceProvider.class, implType);
/* 575 */     if (wsProvider != null) {
/* 576 */       String tns = wsProvider.targetNamespace();
/* 577 */       String local = wsProvider.portName();
/* 578 */       portName = new QName(tns, local);
/*     */     } else {
/* 580 */       portName = RuntimeModeler.getPortName(implType, (MetadataReader)reflectAnnotationReader, serviceName.getNamespaceURI(), isStandard);
/*     */     } 
/* 582 */     assert portName != null;
/* 583 */     return portName;
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
/*     */   @Nullable
/*     */   public static String getWsdlLocation(Class<?> implType) {
/* 596 */     return getWsdlLocation(implType, (MetadataReader)new ReflectAnnotationReader());
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
/*     */   @Nullable
/*     */   public static String getWsdlLocation(Class<?> implType, MetadataReader metadataReader) {
/*     */     ReflectAnnotationReader reflectAnnotationReader;
/* 610 */     if (metadataReader == null) {
/* 611 */       reflectAnnotationReader = new ReflectAnnotationReader();
/*     */     }
/*     */     
/* 614 */     WebService ws = (WebService)reflectAnnotationReader.getAnnotation(WebService.class, implType);
/* 615 */     if (ws != null) {
/* 616 */       return nullIfEmpty(ws.wsdlLocation());
/*     */     }
/* 618 */     WebServiceProvider wsProvider = implType.<WebServiceProvider>getAnnotation(WebServiceProvider.class);
/* 619 */     assert wsProvider != null;
/* 620 */     return nullIfEmpty(wsProvider.wsdlLocation());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String nullIfEmpty(String string) {
/* 625 */     if (string.length() < 1) {
/* 626 */       string = null;
/*     */     }
/* 628 */     return string;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static SDDocumentImpl generateWSDL(WSBinding binding, AbstractSEIModelImpl seiModel, List<SDDocumentImpl> docs, Container container, Class implType) {
/* 637 */     BindingID bindingId = binding.getBindingId();
/* 638 */     if (!bindingId.canGenerateWSDL()) {
/* 639 */       throw new ServerRtException("can.not.generate.wsdl", new Object[] { bindingId });
/*     */     }
/*     */     
/* 642 */     if (bindingId.toString().equals("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")) {
/* 643 */       String msg = ServerMessages.GENERATE_NON_STANDARD_WSDL();
/* 644 */       logger.warning(msg);
/*     */     } 
/*     */ 
/*     */     
/* 648 */     WSDLGenResolver wsdlResolver = new WSDLGenResolver(docs, seiModel.getServiceQName(), seiModel.getPortTypeName());
/* 649 */     WSDLGenInfo wsdlGenInfo = new WSDLGenInfo();
/* 650 */     wsdlGenInfo.setWsdlResolver(wsdlResolver);
/* 651 */     wsdlGenInfo.setContainer(container);
/* 652 */     wsdlGenInfo.setExtensions((WSDLGeneratorExtension[])ServiceFinder.find(WSDLGeneratorExtension.class).toArray());
/* 653 */     wsdlGenInfo.setInlineSchemas(false);
/* 654 */     seiModel.getDatabinding().generateWSDL(wsdlGenInfo);
/*     */ 
/*     */ 
/*     */     
/* 658 */     return wsdlResolver.updateDocs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<SDDocumentImpl> categoriseMetadata(List<SDDocumentSource> src, QName serviceName, QName portTypeName) {
/* 667 */     List<SDDocumentImpl> r = new ArrayList<SDDocumentImpl>(src.size());
/* 668 */     for (SDDocumentSource doc : src) {
/* 669 */       r.add(SDDocumentImpl.create(doc, serviceName, portTypeName));
/*     */     }
/* 671 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void verifyPrimaryWSDL(@NotNull SDDocumentSource primaryWsdl, @NotNull QName serviceName) {
/* 679 */     SDDocumentImpl primaryDoc = SDDocumentImpl.create(primaryWsdl, serviceName, (QName)null);
/* 680 */     if (!(primaryDoc instanceof SDDocument.WSDL)) {
/* 681 */       throw new WebServiceException(primaryWsdl.getSystemId() + " is not a WSDL. But it is passed as a primary WSDL");
/*     */     }
/*     */     
/* 684 */     SDDocument.WSDL wsdlDoc = (SDDocument.WSDL)primaryDoc;
/* 685 */     if (!wsdlDoc.hasService()) {
/* 686 */       if (wsdlDoc.getAllServices().isEmpty()) {
/* 687 */         throw new WebServiceException("Not a primary WSDL=" + primaryWsdl.getSystemId() + " since it doesn't have Service " + serviceName);
/*     */       }
/*     */       
/* 690 */       throw new WebServiceException("WSDL " + primaryDoc.getSystemId() + " has the following services " + wsdlDoc.getAllServices() + " but not " + serviceName + ". Maybe you forgot to specify a serviceName and/or targetNamespace in @WebService/@WebServiceProvider?");
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
/*     */   
/*     */   @Nullable
/*     */   private static SDDocumentImpl findPrimary(@NotNull List<SDDocumentImpl> docList) {
/* 706 */     SDDocumentImpl primaryDoc = null;
/* 707 */     boolean foundConcrete = false;
/* 708 */     boolean foundAbstract = false;
/* 709 */     for (SDDocumentImpl doc : docList) {
/* 710 */       if (doc instanceof SDDocument.WSDL) {
/* 711 */         SDDocument.WSDL wsdlDoc = (SDDocument.WSDL)doc;
/* 712 */         if (wsdlDoc.hasService()) {
/* 713 */           primaryDoc = doc;
/* 714 */           if (foundConcrete) {
/* 715 */             throw new ServerRtException("duplicate.primary.wsdl", new Object[] { doc.getSystemId() });
/*     */           }
/* 717 */           foundConcrete = true;
/*     */         } 
/* 719 */         if (wsdlDoc.hasPortType()) {
/* 720 */           if (foundAbstract) {
/* 721 */             throw new ServerRtException("duplicate.abstract.wsdl", new Object[] { doc.getSystemId() });
/*     */           }
/* 723 */           foundAbstract = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 727 */     return primaryDoc;
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
/*     */   @NotNull
/*     */   private static WSDLPortImpl getWSDLPort(SDDocumentSource primaryWsdl, List<? extends SDDocumentSource> metadata, @NotNull QName serviceName, @NotNull QName portName, Container container, EntityResolver resolver) {
/* 743 */     URL wsdlUrl = primaryWsdl.getSystemId();
/*     */     
/*     */     try {
/* 746 */       WSDLModelImpl wsdlDoc = RuntimeWSDLParser.parse(new XMLEntityResolver.Parser(primaryWsdl), new EntityResolverImpl(metadata, resolver), false, container, (WSDLParserExtension[])ServiceFinder.find(WSDLParserExtension.class).toArray());
/*     */ 
/*     */       
/* 749 */       if (wsdlDoc.getServices().size() == 0) {
/* 750 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_NOSERVICE_IN_WSDLMODEL(wsdlUrl));
/*     */       }
/* 752 */       WSDLServiceImpl wsdlService = wsdlDoc.getService(serviceName);
/* 753 */       if (wsdlService == null) {
/* 754 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICE(serviceName, wsdlUrl));
/*     */       }
/* 756 */       WSDLPortImpl wsdlPort = wsdlService.get(portName);
/* 757 */       if (wsdlPort == null) {
/* 758 */         throw new ServerRtException(ServerMessages.localizableRUNTIME_PARSER_WSDL_INCORRECTSERVICEPORT(serviceName, portName, wsdlUrl));
/*     */       }
/* 760 */       return wsdlPort;
/* 761 */     } catch (IOException e) {
/* 762 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/* 763 */     } catch (XMLStreamException e) {
/* 764 */       throw new ServerRtException("runtime.saxparser.exception", new Object[] { e.getMessage(), e.getLocation(), e });
/* 765 */     } catch (SAXException e) {
/* 766 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/* 767 */     } catch (ServiceConfigurationError e) {
/* 768 */       throw new ServerRtException("runtime.parser.wsdl", new Object[] { wsdlUrl, e });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class EntityResolverImpl
/*     */     implements XMLEntityResolver
/*     */   {
/* 776 */     private Map<String, SDDocumentSource> metadata = new HashMap<String, SDDocumentSource>();
/*     */     private EntityResolver resolver;
/*     */     
/*     */     public EntityResolverImpl(List<? extends SDDocumentSource> metadata, EntityResolver resolver) {
/* 780 */       for (SDDocumentSource doc : metadata) {
/* 781 */         this.metadata.put(doc.getSystemId().toExternalForm(), doc);
/*     */       }
/* 783 */       this.resolver = resolver;
/*     */     }
/*     */     
/*     */     public XMLEntityResolver.Parser resolveEntity(String publicId, String systemId) throws IOException, XMLStreamException {
/* 787 */       if (systemId != null) {
/* 788 */         SDDocumentSource doc = this.metadata.get(systemId);
/* 789 */         if (doc != null)
/* 790 */           return new XMLEntityResolver.Parser(doc); 
/*     */       } 
/* 792 */       if (this.resolver != null) {
/*     */         try {
/* 794 */           InputSource source = this.resolver.resolveEntity(publicId, systemId);
/* 795 */           if (source != null) {
/* 796 */             XMLEntityResolver.Parser p = new XMLEntityResolver.Parser(null, XMLStreamReaderFactory.create(source, true));
/* 797 */             return p;
/*     */           } 
/* 799 */         } catch (SAXException e) {
/* 800 */           throw new XMLStreamException(e);
/*     */         } 
/*     */       }
/* 803 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 808 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.endpoint");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\EndpointFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */