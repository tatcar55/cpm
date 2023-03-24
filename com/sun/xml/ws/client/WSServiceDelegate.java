/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.Closeable;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.ComponentFeature;
/*     */ import com.sun.xml.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.ServiceInterceptor;
/*     */ import com.sun.xml.ws.api.client.ServiceInterceptorFactory;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Stubs;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.client.sei.SEIStub;
/*     */ import com.sun.xml.ws.db.DatabindingImpl;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ import com.sun.xml.ws.developer.UsesJAXBContextFeature;
/*     */ import com.sun.xml.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.ws.model.RuntimeModeler;
/*     */ import com.sun.xml.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLModelImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLServiceImpl;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.resources.DispatchMessages;
/*     */ import com.sun.xml.ws.resources.ProviderApiMessages;
/*     */ import com.sun.xml.ws.util.JAXWSUtils;
/*     */ import com.sun.xml.ws.util.ServiceConfigurationError;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import javax.jws.HandlerChain;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.Dispatch;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceClient;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.handler.HandlerResolver;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ import org.xml.sax.EntityResolver;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSServiceDelegate
/*     */   extends WSService
/*     */ {
/* 162 */   private final Map<QName, PortInfo> ports = new HashMap<QName, PortInfo>();
/*     */   protected Map<QName, PortInfo> getQNameToPortInfoMap() {
/* 164 */     return this.ports;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/* 169 */   private HandlerConfigurator handlerConfigurator = new HandlerConfigurator.HandlerResolverImpl(null);
/*     */ 
/*     */ 
/*     */   
/*     */   private final Class<? extends Service> serviceClass;
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebServiceFeatureList features;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final QName serviceName;
/*     */ 
/*     */   
/* 184 */   private final Map<QName, SEIPortInfo> seiContext = new HashMap<QName, SEIPortInfo>();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Executor executor;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private WSDLServiceImpl wsdlService;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Container container;
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   final ServiceInterceptor serviceInterceptor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 208 */     this(wsdlDocumentLocation, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   protected WSServiceDelegate(URL wsdlDocumentLocation, QName serviceName, Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 212 */     this((wsdlDocumentLocation == null) ? null : new StreamSource(wsdlDocumentLocation.toExternalForm()), serviceName, serviceClass, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 222 */     this(wsdl, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSServiceDelegate(@Nullable Source wsdl, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 230 */     this(wsdl, (WSDLServiceImpl)null, serviceName, serviceClass, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @Nullable WSDLServiceImpl service, @NotNull QName serviceName, @NotNull Class<? extends Service> serviceClass, WebServiceFeature... features) {
/* 238 */     this(wsdl, service, serviceName, serviceClass, new WebServiceFeatureList(features));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSServiceDelegate(@Nullable Source wsdl, @Nullable WSDLServiceImpl service, @NotNull QName serviceName, @NotNull final Class<? extends Service> serviceClass, WebServiceFeatureList features) {
/* 247 */     if (serviceName == null) {
/* 248 */       throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME_NULL(null));
/*     */     }
/*     */     
/* 251 */     this.features = features;
/*     */     
/* 253 */     WSService.InitParams initParams = INIT_PARAMS.get();
/* 254 */     INIT_PARAMS.set(null);
/* 255 */     if (initParams == null) {
/* 256 */       initParams = EMPTY_PARAMS;
/*     */     }
/*     */     
/* 259 */     this.serviceName = serviceName;
/* 260 */     this.serviceClass = serviceClass;
/* 261 */     Container tContainer = (initParams.getContainer() != null) ? initParams.getContainer() : ContainerResolver.getInstance().getContainer();
/* 262 */     if (tContainer == Container.NONE) {
/* 263 */       tContainer = new ClientContainer();
/*     */     }
/* 265 */     this.container = tContainer;
/*     */     
/* 267 */     ComponentFeature cf = (ComponentFeature)this.features.get(ComponentFeature.class);
/* 268 */     if (cf != null) {
/* 269 */       switch (cf.getTarget()) {
/*     */         case SERVICE:
/* 271 */           getComponents().add(cf.getComponent());
/*     */           break;
/*     */         case CONTAINER:
/* 274 */           this.container.getComponents().add(cf.getComponent());
/*     */           break;
/*     */         default:
/* 277 */           throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/* 280 */     ComponentsFeature csf = (ComponentsFeature)this.features.get(ComponentsFeature.class);
/* 281 */     if (csf != null) {
/* 282 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 283 */         switch (cfi.getTarget()) {
/*     */           case SERVICE:
/* 285 */             getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */           case CONTAINER:
/* 288 */             this.container.getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */         } 
/* 291 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 297 */     ServiceInterceptor interceptor = ServiceInterceptorFactory.load(this, Thread.currentThread().getContextClassLoader());
/* 298 */     ServiceInterceptor si = (ServiceInterceptor)this.container.getSPI(ServiceInterceptor.class);
/* 299 */     if (si != null) {
/* 300 */       interceptor = ServiceInterceptor.aggregate(new ServiceInterceptor[] { interceptor, si });
/*     */     }
/* 302 */     this.serviceInterceptor = interceptor;
/*     */     
/* 304 */     if (service == null) {
/*     */       
/* 306 */       if (wsdl == null && 
/* 307 */         serviceClass != Service.class) {
/* 308 */         WebServiceClient wsClient = AccessController.<WebServiceClient>doPrivileged(new PrivilegedAction<WebServiceClient>() {
/*     */               public WebServiceClient run() {
/* 310 */                 return (WebServiceClient)serviceClass.getAnnotation(WebServiceClient.class);
/*     */               }
/*     */             });
/* 313 */         String wsdlLocation = wsClient.wsdlLocation();
/* 314 */         wsdlLocation = JAXWSUtils.absolutize(JAXWSUtils.getFileOrURLName(wsdlLocation));
/* 315 */         wsdl = new StreamSource(wsdlLocation);
/*     */       } 
/*     */       
/* 318 */       if (wsdl != null) {
/*     */         try {
/* 320 */           URL url = (wsdl.getSystemId() == null) ? null : JAXWSUtils.getEncodedURL(wsdl.getSystemId());
/* 321 */           WSDLModelImpl model = parseWSDL(url, wsdl, serviceClass);
/* 322 */           service = model.getService(this.serviceName);
/* 323 */           if (service == null) {
/* 324 */             throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME(this.serviceName, buildNameList(model.getServices().keySet())));
/*     */           }
/*     */ 
/*     */           
/* 328 */           for (WSDLPortImpl wSDLPortImpl : service.getPorts())
/* 329 */             this.ports.put(wSDLPortImpl.getName(), new PortInfo(this, (WSDLPort)wSDLPortImpl)); 
/* 330 */         } catch (MalformedURLException e) {
/* 331 */           throw new WebServiceException(ClientMessages.INVALID_WSDL_URL(wsdl.getSystemId()));
/*     */         } 
/*     */       }
/*     */     } 
/* 335 */     this.wsdlService = service;
/*     */     
/* 337 */     if (serviceClass != Service.class) {
/*     */       
/* 339 */       HandlerChain handlerChain = AccessController.<HandlerChain>doPrivileged(new PrivilegedAction<HandlerChain>()
/*     */           {
/*     */             public HandlerChain run() {
/* 342 */               return (HandlerChain)serviceClass.getAnnotation(HandlerChain.class);
/*     */             }
/*     */           });
/* 345 */       if (handlerChain != null) {
/* 346 */         this.handlerConfigurator = new HandlerConfigurator.AnnotationConfigurator(this);
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
/*     */   private WSDLModelImpl parseWSDL(URL wsdlDocumentLocation, Source wsdlSource, Class serviceClass) {
/*     */     try {
/* 359 */       return RuntimeWSDLParser.parse(wsdlDocumentLocation, wsdlSource, createCatalogResolver(), true, getContainer(), serviceClass, (WSDLParserExtension[])ServiceFinder.find(WSDLParserExtension.class).toArray());
/*     */     }
/* 361 */     catch (IOException e) {
/* 362 */       throw new WebServiceException(e);
/* 363 */     } catch (XMLStreamException e) {
/* 364 */       throw new WebServiceException(e);
/* 365 */     } catch (SAXException e) {
/* 366 */       throw new WebServiceException(e);
/* 367 */     } catch (ServiceConfigurationError e) {
/* 368 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected EntityResolver createCatalogResolver() {
/* 373 */     return XmlUtil.createDefaultCatalogResolver();
/*     */   }
/*     */   
/*     */   public Executor getExecutor() {
/* 377 */     return this.executor;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor executor) {
/* 381 */     this.executor = executor;
/*     */   }
/*     */   
/*     */   public HandlerResolver getHandlerResolver() {
/* 385 */     return this.handlerConfigurator.getResolver();
/*     */   }
/*     */   
/*     */   final HandlerConfigurator getHandlerConfigurator() {
/* 389 */     return this.handlerConfigurator;
/*     */   }
/*     */   
/*     */   public void setHandlerResolver(HandlerResolver resolver) {
/* 393 */     this.handlerConfigurator = new HandlerConfigurator.HandlerResolverImpl(resolver);
/*     */   }
/*     */   
/*     */   public <T> T getPort(QName portName, Class<T> portInterface) throws WebServiceException {
/* 397 */     return getPort(portName, portInterface, EMPTY_FEATURES);
/*     */   }
/*     */   
/*     */   public <T> T getPort(QName portName, Class<T> portInterface, WebServiceFeature... features) {
/* 401 */     if (portName == null || portInterface == null)
/* 402 */       throw new IllegalArgumentException(); 
/* 403 */     WSDLServiceImpl tWsdlService = this.wsdlService;
/* 404 */     if (tWsdlService == null) {
/*     */ 
/*     */       
/* 407 */       tWsdlService = getWSDLModelfromSEI(portInterface);
/*     */       
/* 409 */       if (tWsdlService == null) {
/* 410 */         throw new WebServiceException(ProviderApiMessages.NO_WSDL_NO_PORT(portInterface.getName()));
/*     */       }
/*     */     } 
/*     */     
/* 414 */     WSDLPortImpl portModel = getPortModel(tWsdlService, portName);
/* 415 */     return getPort(portModel.getEPR(), portName, portInterface, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> T getPort(EndpointReference epr, Class<T> portInterface, WebServiceFeature... features) {
/* 419 */     return getPort(WSEndpointReference.create(epr), portInterface, features);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getPort(WSEndpointReference wsepr, Class<T> portInterface, WebServiceFeature... features) {
/* 424 */     WebServiceFeatureList featureList = new WebServiceFeatureList(features);
/* 425 */     QName portTypeName = RuntimeModeler.getPortTypeName(portInterface, getMetadadaReader(featureList, portInterface.getClassLoader()));
/*     */     
/* 427 */     QName portName = getPortNameFromEPR(wsepr, portTypeName);
/* 428 */     return getPort(wsepr, portName, portInterface, featureList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> T getPort(WSEndpointReference wsepr, QName portName, Class<T> portInterface, WebServiceFeatureList features) {
/* 433 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 434 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 435 */       throw new IllegalArgumentException();
/*     */     }
/* 437 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 438 */     if (csf != null)
/* 439 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 440 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 441 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 444 */     features.addAll((Iterable)this.features);
/*     */     
/* 446 */     SEIPortInfo spi = addSEI(portName, portInterface, features);
/* 447 */     return createEndpointIFBaseProxy(wsepr, portName, portInterface, features, spi);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getPort(Class<T> portInterface, WebServiceFeature... features) {
/* 453 */     QName portTypeName = RuntimeModeler.getPortTypeName(portInterface, getMetadadaReader(new WebServiceFeatureList(features), portInterface.getClassLoader()));
/* 454 */     WSDLServiceImpl tmpWsdlService = this.wsdlService;
/* 455 */     if (tmpWsdlService == null) {
/*     */ 
/*     */       
/* 458 */       tmpWsdlService = getWSDLModelfromSEI(portInterface);
/*     */       
/* 460 */       if (tmpWsdlService == null) {
/* 461 */         throw new WebServiceException(ProviderApiMessages.NO_WSDL_NO_PORT(portInterface.getName()));
/*     */       }
/*     */     } 
/*     */     
/* 465 */     WSDLPortImpl port = tmpWsdlService.getMatchingPort(portTypeName);
/* 466 */     if (port == null) {
/* 467 */       throw new WebServiceException(ClientMessages.UNDEFINED_PORT_TYPE(portTypeName));
/*     */     }
/* 469 */     QName portName = port.getName();
/* 470 */     return getPort(portName, portInterface, features);
/*     */   }
/*     */   
/*     */   public <T> T getPort(Class<T> portInterface) throws WebServiceException {
/* 474 */     return getPort(portInterface, EMPTY_FEATURES);
/*     */   }
/*     */   
/*     */   public void addPort(QName portName, String bindingId, String endpointAddress) throws WebServiceException {
/* 478 */     if (!this.ports.containsKey(portName)) {
/* 479 */       BindingID bid = (bindingId == null) ? (BindingID)BindingID.SOAP11_HTTP : BindingID.parse(bindingId);
/* 480 */       this.ports.put(portName, new PortInfo(this, (endpointAddress == null) ? null : EndpointAddress.create(endpointAddress), portName, bid));
/*     */     }
/*     */     else {
/*     */       
/* 484 */       throw new WebServiceException(DispatchMessages.DUPLICATE_PORT(portName.toString()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode) throws WebServiceException {
/* 489 */     return createDispatch(portName, aClass, mode, EMPTY_FEATURES);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, WSEndpointReference wsepr, Class<T> aClass, Service.Mode mode, WebServiceFeature... features) {
/* 494 */     return createDispatch(portName, wsepr, aClass, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, WSEndpointReference wsepr, Class<T> aClass, Service.Mode mode, WebServiceFeatureList features) {
/* 498 */     PortInfo port = safeGetPort(portName);
/*     */     
/* 500 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 501 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 502 */       throw new IllegalArgumentException();
/*     */     }
/* 504 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 505 */     if (csf != null)
/* 506 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 507 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 508 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 511 */     features.addAll((Iterable)this.features);
/*     */     
/* 513 */     BindingImpl binding = port.createBinding(features, null, null);
/* 514 */     binding.setMode(mode);
/* 515 */     Dispatch<T> dispatch = Stubs.createDispatch(port, this, (WSBinding)binding, aClass, mode, wsepr);
/* 516 */     this.serviceInterceptor.postCreateDispatch((WSBindingProvider)dispatch);
/* 517 */     return dispatch;
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode, WebServiceFeature... features) {
/* 521 */     return createDispatch(portName, aClass, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(QName portName, Class<T> aClass, Service.Mode mode, WebServiceFeatureList features) {
/* 525 */     WSEndpointReference wsepr = null;
/* 526 */     boolean isAddressingEnabled = false;
/* 527 */     AddressingFeature af = (AddressingFeature)features.get(AddressingFeature.class);
/* 528 */     if (af == null) {
/* 529 */       af = (AddressingFeature)this.features.get(AddressingFeature.class);
/*     */     }
/* 531 */     if (af != null && af.isEnabled())
/* 532 */       isAddressingEnabled = true; 
/* 533 */     MemberSubmissionAddressingFeature msa = (MemberSubmissionAddressingFeature)features.get(MemberSubmissionAddressingFeature.class);
/* 534 */     if (msa == null) {
/* 535 */       msa = (MemberSubmissionAddressingFeature)this.features.get(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 537 */     if (msa != null && msa.isEnabled())
/* 538 */       isAddressingEnabled = true; 
/* 539 */     if (isAddressingEnabled && this.wsdlService != null && this.wsdlService.get(portName) != null) {
/* 540 */       wsepr = this.wsdlService.get(portName).getEPR();
/*     */     }
/* 542 */     return createDispatch(portName, wsepr, aClass, mode, features);
/*     */   }
/*     */   
/*     */   public <T> Dispatch<T> createDispatch(EndpointReference endpointReference, Class<T> type, Service.Mode mode, WebServiceFeature... features) {
/* 546 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 547 */     QName portName = addPortEpr(wsepr);
/* 548 */     return createDispatch(portName, wsepr, type, mode, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public PortInfo safeGetPort(QName portName) {
/* 557 */     PortInfo port = this.ports.get(portName);
/* 558 */     if (port == null) {
/* 559 */       throw new WebServiceException(ClientMessages.INVALID_PORT_NAME(portName, buildNameList(this.ports.keySet())));
/*     */     }
/* 561 */     return port;
/*     */   }
/*     */   
/*     */   private StringBuilder buildNameList(Collection<QName> names) {
/* 565 */     StringBuilder sb = new StringBuilder();
/* 566 */     for (QName qn : names) {
/* 567 */       if (sb.length() > 0) sb.append(','); 
/* 568 */       sb.append(qn);
/*     */     } 
/* 570 */     return sb;
/*     */   }
/*     */   
/*     */   public EndpointAddress getEndpointAddress(QName qName) {
/* 574 */     PortInfo p = this.ports.get(qName);
/* 575 */     return (p != null) ? p.targetEndpoint : null;
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode) throws WebServiceException {
/* 579 */     return createDispatch(portName, jaxbContext, mode, EMPTY_FEATURES);
/*     */   }
/*     */ 
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, WSEndpointReference wsepr, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeature... features) {
/* 584 */     return createDispatch(portName, wsepr, jaxbContext, mode, new WebServiceFeatureList(features));
/*     */   }
/*     */   
/*     */   protected Dispatch<Object> createDispatch(QName portName, WSEndpointReference wsepr, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeatureList features) {
/* 588 */     PortInfo port = safeGetPort(portName);
/*     */     
/* 590 */     ComponentFeature cf = (ComponentFeature)features.get(ComponentFeature.class);
/* 591 */     if (cf != null && !ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 592 */       throw new IllegalArgumentException();
/*     */     }
/* 594 */     ComponentsFeature csf = (ComponentsFeature)features.get(ComponentsFeature.class);
/* 595 */     if (csf != null)
/* 596 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 597 */         if (!ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 598 */           throw new IllegalArgumentException();
/*     */         }
/*     */       }  
/* 601 */     features.addAll((Iterable)this.features);
/*     */     
/* 603 */     BindingImpl binding = port.createBinding(features, null, null);
/* 604 */     binding.setMode(mode);
/* 605 */     Dispatch<Object> dispatch = Stubs.createJAXBDispatch(port, (WSBinding)binding, jaxbContext, mode, wsepr);
/*     */     
/* 607 */     this.serviceInterceptor.postCreateDispatch((WSBindingProvider)dispatch);
/* 608 */     return dispatch;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Container getContainer() {
/* 613 */     return this.container;
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeature... webServiceFeatures) {
/* 617 */     return createDispatch(portName, jaxbContext, mode, new WebServiceFeatureList(webServiceFeatures));
/*     */   }
/*     */   
/*     */   protected Dispatch<Object> createDispatch(QName portName, JAXBContext jaxbContext, Service.Mode mode, WebServiceFeatureList features) {
/* 621 */     WSEndpointReference wsepr = null;
/* 622 */     boolean isAddressingEnabled = false;
/* 623 */     AddressingFeature af = (AddressingFeature)features.get(AddressingFeature.class);
/* 624 */     if (af == null) {
/* 625 */       af = (AddressingFeature)this.features.get(AddressingFeature.class);
/*     */     }
/* 627 */     if (af != null && af.isEnabled())
/* 628 */       isAddressingEnabled = true; 
/* 629 */     MemberSubmissionAddressingFeature msa = (MemberSubmissionAddressingFeature)features.get(MemberSubmissionAddressingFeature.class);
/* 630 */     if (msa == null) {
/* 631 */       msa = (MemberSubmissionAddressingFeature)this.features.get(MemberSubmissionAddressingFeature.class);
/*     */     }
/* 633 */     if (msa != null && msa.isEnabled())
/* 634 */       isAddressingEnabled = true; 
/* 635 */     if (isAddressingEnabled && this.wsdlService != null && this.wsdlService.get(portName) != null) {
/* 636 */       wsepr = this.wsdlService.get(portName).getEPR();
/*     */     }
/* 638 */     return createDispatch(portName, wsepr, jaxbContext, mode, features);
/*     */   }
/*     */   
/*     */   public Dispatch<Object> createDispatch(EndpointReference endpointReference, JAXBContext context, Service.Mode mode, WebServiceFeature... features) {
/* 642 */     WSEndpointReference wsepr = new WSEndpointReference(endpointReference);
/* 643 */     QName portName = addPortEpr(wsepr);
/* 644 */     return createDispatch(portName, wsepr, context, mode, features);
/*     */   }
/*     */   
/*     */   private QName addPortEpr(WSEndpointReference wsepr) {
/* 648 */     if (wsepr == null)
/* 649 */       throw new WebServiceException(ProviderApiMessages.NULL_EPR()); 
/* 650 */     QName eprPortName = getPortNameFromEPR(wsepr, (QName)null);
/*     */ 
/*     */ 
/*     */     
/* 654 */     PortInfo portInfo = new PortInfo(this, (wsepr.getAddress() == null) ? null : EndpointAddress.create(wsepr.getAddress()), eprPortName, getPortModel(this.wsdlService, eprPortName).getBinding().getBindingId());
/*     */     
/* 656 */     if (!this.ports.containsKey(eprPortName)) {
/* 657 */       this.ports.put(eprPortName, portInfo);
/*     */     }
/*     */     
/* 660 */     return eprPortName;
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
/*     */   private QName getPortNameFromEPR(@NotNull WSEndpointReference wsepr, @Nullable QName portTypeName) {
/* 677 */     WSEndpointReference.Metadata metadata = wsepr.getMetaData();
/* 678 */     QName eprServiceName = metadata.getServiceName();
/* 679 */     QName eprPortName = metadata.getPortName();
/* 680 */     if (eprServiceName != null && !eprServiceName.equals(this.serviceName)) {
/* 681 */       throw new WebServiceException("EndpointReference WSDL ServiceName differs from Service Instance WSDL Service QName.\n The two Service QNames must match");
/*     */     }
/*     */     
/* 684 */     if (this.wsdlService == null) {
/* 685 */       Source eprWsdlSource = metadata.getWsdlSource();
/* 686 */       if (eprWsdlSource == null) {
/* 687 */         throw new WebServiceException(ProviderApiMessages.NULL_WSDL());
/*     */       }
/*     */       try {
/* 690 */         WSDLModelImpl eprWsdlMdl = parseWSDL(new URL(wsepr.getAddress()), eprWsdlSource, (Class)null);
/* 691 */         this.wsdlService = eprWsdlMdl.getService(this.serviceName);
/* 692 */         if (this.wsdlService == null) {
/* 693 */           throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME(this.serviceName, buildNameList(eprWsdlMdl.getServices().keySet())));
/*     */         }
/* 695 */       } catch (MalformedURLException e) {
/* 696 */         throw new WebServiceException(ClientMessages.INVALID_ADDRESS(wsepr.getAddress()));
/*     */       } 
/*     */     } 
/* 699 */     QName portName = eprPortName;
/*     */     
/* 701 */     if (portName == null && portTypeName != null) {
/*     */       
/* 703 */       WSDLPortImpl port = this.wsdlService.getMatchingPort(portTypeName);
/* 704 */       if (port == null)
/* 705 */         throw new WebServiceException(ClientMessages.UNDEFINED_PORT_TYPE(portTypeName)); 
/* 706 */       portName = port.getName();
/*     */     } 
/* 708 */     if (portName == null)
/* 709 */       throw new WebServiceException(ProviderApiMessages.NULL_PORTNAME()); 
/* 710 */     if (this.wsdlService.get(portName) == null) {
/* 711 */       throw new WebServiceException(ClientMessages.INVALID_EPR_PORT_NAME(portName, buildWsdlPortNames()));
/*     */     }
/* 713 */     return portName;
/*     */   }
/*     */ 
/*     */   
/*     */   private WSDLServiceImpl getWSDLModelfromSEI(final Class sei) {
/* 718 */     WebService ws = AccessController.<WebService>doPrivileged(new PrivilegedAction<WebService>() {
/*     */           public WebService run() {
/* 720 */             return (WebService)sei.getAnnotation(WebService.class);
/*     */           }
/*     */         });
/* 723 */     if (ws == null || ws.wsdlLocation().equals(""))
/* 724 */       return null; 
/* 725 */     String wsdlLocation = ws.wsdlLocation();
/* 726 */     wsdlLocation = JAXWSUtils.absolutize(JAXWSUtils.getFileOrURLName(wsdlLocation));
/* 727 */     Source wsdl = new StreamSource(wsdlLocation);
/* 728 */     WSDLServiceImpl service = null;
/*     */     
/*     */     try {
/* 731 */       URL url = (wsdl.getSystemId() == null) ? null : new URL(wsdl.getSystemId());
/* 732 */       WSDLModelImpl model = parseWSDL(url, wsdl, sei);
/* 733 */       service = model.getService(this.serviceName);
/* 734 */       if (service == null) {
/* 735 */         throw new WebServiceException(ClientMessages.INVALID_SERVICE_NAME(this.serviceName, buildNameList(model.getServices().keySet())));
/*     */       }
/*     */     }
/* 738 */     catch (MalformedURLException e) {
/* 739 */       throw new WebServiceException(ClientMessages.INVALID_WSDL_URL(wsdl.getSystemId()));
/*     */     } 
/* 741 */     return service;
/*     */   }
/*     */   
/*     */   public QName getServiceName() {
/* 745 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   public Class getServiceClass() {
/* 749 */     return this.serviceClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<QName> getPorts() throws WebServiceException {
/* 755 */     return this.ports.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public URL getWSDLDocumentLocation() {
/* 760 */     if (this.wsdlService == null) return null; 
/*     */     try {
/* 762 */       return new URL(this.wsdlService.getParent().getLocation().getSystemId());
/* 763 */     } catch (MalformedURLException e) {
/* 764 */       throw new AssertionError(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> T createEndpointIFBaseProxy(@Nullable WSEndpointReference epr, QName portName, Class<T> portInterface, WebServiceFeatureList webServiceFeatures, SEIPortInfo eif) {
/* 771 */     if (this.wsdlService == null) {
/* 772 */       throw new WebServiceException(ClientMessages.INVALID_SERVICE_NO_WSDL(this.serviceName));
/*     */     }
/*     */     
/* 775 */     if (this.wsdlService.get(portName) == null) {
/* 776 */       throw new WebServiceException(ClientMessages.INVALID_PORT_NAME(portName, buildWsdlPortNames()));
/*     */     }
/*     */ 
/*     */     
/* 780 */     BindingImpl binding = eif.createBinding(webServiceFeatures, portInterface);
/* 781 */     InvocationHandler pis = getStubHandler(binding, eif, epr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 790 */     ClassLoader loader = getDelegatingLoader(portInterface.getClassLoader(), WSServiceDelegate.class.getClassLoader());
/*     */ 
/*     */     
/* 793 */     T proxy = portInterface.cast(Proxy.newProxyInstance(loader, new Class[] { portInterface, WSBindingProvider.class, Closeable.class }, pis));
/*     */     
/* 795 */     if (this.serviceInterceptor != null) {
/* 796 */       this.serviceInterceptor.postCreateProxy((WSBindingProvider)proxy, portInterface);
/*     */     }
/* 798 */     return proxy;
/*     */   }
/*     */   
/*     */   protected InvocationHandler getStubHandler(BindingImpl binding, SEIPortInfo eif, @Nullable WSEndpointReference epr) {
/* 802 */     return (InvocationHandler)new SEIStub(eif, binding, eif.model, epr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private StringBuilder buildWsdlPortNames() {
/* 809 */     Set<QName> wsdlPortNames = new HashSet<QName>();
/* 810 */     for (WSDLPortImpl port : this.wsdlService.getPorts()) {
/* 811 */       wsdlPortNames.add(port.getName());
/*     */     }
/* 813 */     return buildNameList(wsdlPortNames);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSDLPortImpl getPortModel(WSDLServiceImpl wsdlService, QName portName) {
/* 822 */     WSDLPortImpl port = wsdlService.get(portName);
/* 823 */     if (port == null) {
/* 824 */       throw new WebServiceException(ClientMessages.INVALID_PORT_NAME(portName, buildWsdlPortNames()));
/*     */     }
/* 826 */     return port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SEIPortInfo addSEI(QName portName, Class portInterface, WebServiceFeatureList features) throws WebServiceException {
/* 835 */     boolean ownModel = useOwnSEIModel(features);
/* 836 */     if (ownModel)
/*     */     {
/* 838 */       return createSEIPortInfo(portName, portInterface, features);
/*     */     }
/*     */     
/* 841 */     SEIPortInfo spi = this.seiContext.get(portName);
/* 842 */     if (spi == null) {
/* 843 */       spi = createSEIPortInfo(portName, portInterface, features);
/* 844 */       this.seiContext.put(spi.portName, spi);
/* 845 */       this.ports.put(spi.portName, spi);
/*     */     } 
/* 847 */     return spi;
/*     */   }
/*     */   
/*     */   public SEIModel buildRuntimeModel(QName serviceName, QName portName, Class portInterface, WSDLPort wsdlPort, WebServiceFeatureList features) {
/* 851 */     DatabindingFactory fac = DatabindingFactory.newInstance();
/* 852 */     DatabindingConfig config = new DatabindingConfig();
/* 853 */     config.setContractClass(portInterface);
/* 854 */     config.getMappingInfo().setServiceName(serviceName);
/* 855 */     config.setWsdlPort(wsdlPort);
/* 856 */     config.setFeatures((Iterable)features);
/* 857 */     config.setClassLoader(portInterface.getClassLoader());
/* 858 */     config.getMappingInfo().setPortName(portName);
/*     */ 
/*     */     
/* 861 */     config.setMetadataReader(getMetadadaReader(features, portInterface.getClassLoader()));
/*     */     
/* 863 */     DatabindingImpl rt = (DatabindingImpl)fac.createRuntime(config);
/*     */     
/* 865 */     return rt.getModel();
/*     */   }
/*     */   
/*     */   private MetadataReader getMetadadaReader(WebServiceFeatureList features, ClassLoader classLoader) {
/* 869 */     if (features == null) return null; 
/* 870 */     ExternalMetadataFeature ef = (ExternalMetadataFeature)features.get(ExternalMetadataFeature.class);
/*     */     
/* 872 */     if (ef != null)
/* 873 */       return ef.getMetadataReader(classLoader); 
/* 874 */     return null;
/*     */   }
/*     */   
/*     */   private SEIPortInfo createSEIPortInfo(QName portName, Class portInterface, WebServiceFeatureList features) {
/* 878 */     WSDLPortImpl wsdlPort = getPortModel(this.wsdlService, portName);
/* 879 */     SEIModel model = buildRuntimeModel(this.serviceName, portName, portInterface, (WSDLPort)wsdlPort, features);
/*     */     
/* 881 */     return new SEIPortInfo(this, portInterface, (SOAPSEIModel)model, (WSDLPort)wsdlPort);
/*     */   }
/*     */   
/*     */   private boolean useOwnSEIModel(WebServiceFeatureList features) {
/* 885 */     return features.contains(UsesJAXBContextFeature.class);
/*     */   }
/*     */   
/*     */   public WSDLServiceImpl getWsdlService() {
/* 889 */     return this.wsdlService;
/*     */   }
/*     */   
/*     */   static class DaemonThreadFactory
/*     */     implements ThreadFactory {
/*     */     public Thread newThread(Runnable r) {
/* 895 */       Thread daemonThread = new Thread(r);
/* 896 */       daemonThread.setDaemon(Boolean.TRUE.booleanValue());
/* 897 */       return daemonThread;
/*     */     }
/*     */   }
/*     */   
/* 901 */   protected static final WebServiceFeature[] EMPTY_FEATURES = new WebServiceFeature[0];
/*     */   
/*     */   private static ClassLoader getDelegatingLoader(ClassLoader loader1, ClassLoader loader2) {
/* 904 */     if (loader1 == null) return loader2; 
/* 905 */     if (loader2 == null) return loader1; 
/* 906 */     return new DelegatingLoader(loader1, loader2);
/*     */   }
/*     */   
/*     */   private static final class DelegatingLoader
/*     */     extends ClassLoader {
/*     */     private final ClassLoader loader;
/*     */     
/*     */     public int hashCode() {
/* 914 */       int prime = 31;
/* 915 */       int result = 1;
/* 916 */       result = 31 * result + ((this.loader == null) ? 0 : this.loader.hashCode());
/*     */       
/* 918 */       result = 31 * result + ((getParent() == null) ? 0 : getParent().hashCode());
/*     */       
/* 920 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 925 */       if (this == obj)
/* 926 */         return true; 
/* 927 */       if (obj == null)
/* 928 */         return false; 
/* 929 */       if (getClass() != obj.getClass())
/* 930 */         return false; 
/* 931 */       DelegatingLoader other = (DelegatingLoader)obj;
/* 932 */       if (this.loader == null) {
/* 933 */         if (other.loader != null)
/* 934 */           return false; 
/* 935 */       } else if (!this.loader.equals(other.loader)) {
/* 936 */         return false;
/* 937 */       }  if (getParent() == null) {
/* 938 */         if (other.getParent() != null)
/* 939 */           return false; 
/* 940 */       } else if (!getParent().equals(other.getParent())) {
/* 941 */         return false;
/* 942 */       }  return true;
/*     */     }
/*     */     
/*     */     DelegatingLoader(ClassLoader loader1, ClassLoader loader2) {
/* 946 */       super(loader2);
/* 947 */       this.loader = loader1;
/*     */     }
/*     */     
/*     */     protected Class findClass(String name) throws ClassNotFoundException {
/* 951 */       return this.loader.loadClass(name);
/*     */     }
/*     */     
/*     */     protected URL findResource(String name) {
/* 955 */       return this.loader.getResource(name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\WSServiceDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */