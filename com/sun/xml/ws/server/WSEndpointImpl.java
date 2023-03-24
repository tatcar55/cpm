/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.addressing.EPRSDDocumentFilter;
/*     */ import com.sun.xml.ws.addressing.WSEPRExtension;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.ComponentFeature;
/*     */ import com.sun.xml.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.Engine;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.ws.api.pipe.ServerPipeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.SyncStartForAsyncFeature;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.api.server.EndpointAwareCodec;
/*     */ import com.sun.xml.ws.api.server.EndpointComponent;
/*     */ import com.sun.xml.ws.api.server.EndpointReferenceExtensionContributor;
/*     */ import com.sun.xml.ws.api.server.LazyMOMProvider;
/*     */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.api.server.TransportBackChannel;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLDirectProperties;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortProperties;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLProperties;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.resources.HandlerMessages;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PreDestroy;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSEndpointImpl<T>
/*     */   extends WSEndpoint<T>
/*     */   implements LazyMOMProvider.WSEndpointScopeChangeListener
/*     */ {
/*  98 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.endpoint");
/*     */   
/*     */   @NotNull
/*     */   private final QName serviceName;
/*     */   
/*     */   @NotNull
/*     */   private final QName portName;
/*     */   
/*     */   protected final WSBinding binding;
/*     */   
/*     */   private final SEIModel seiModel;
/*     */   
/*     */   @NotNull
/*     */   private final Container container;
/*     */   
/*     */   private final WSDLPort port;
/*     */   
/*     */   protected final Tube masterTubeline;
/*     */   private final ServiceDefinitionImpl serviceDef;
/* 117 */   private final Object managedObjectManagerLock = new Object(); private final SOAPVersion soapVersion; private final Engine engine; @NotNull private final Codec masterCodec; @NotNull private final PolicyMap endpointPolicy; private final Pool<Tube> tubePool; private final OperationDispatcher operationDispatcher; @NotNull
/* 118 */   private ManagedObjectManager managedObjectManager; private boolean managedObjectManagerClosed = false; private LazyMOMProvider.Scope lazyMOMProviderScope = LazyMOMProvider.Scope.STANDALONE;
/*     */   @NotNull
/*     */   private final ServerTubeAssemblerContext context;
/* 121 */   private Map<QName, WSEndpointReference.EPRExtension> endpointReferenceExtensions = new HashMap<QName, WSEndpointReference.EPRExtension>();
/*     */ 
/*     */   
/*     */   private boolean disposed;
/*     */ 
/*     */   
/*     */   private final Class<T> implementationClass;
/*     */   
/*     */   @NotNull
/*     */   private final WSDLProperties wsdlProperties;
/*     */   
/* 132 */   private final Set<Component> componentRegistry = new CopyOnWriteArraySet<Component>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSEndpointImpl(@NotNull QName serviceName, @NotNull QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort port, Class<T> implementationClass, @Nullable ServiceDefinitionImpl serviceDef, EndpointAwareTube terminalTube, boolean isSynchronous, PolicyMap endpointPolicy) {
/* 140 */     this.serviceName = serviceName;
/* 141 */     this.portName = portName;
/* 142 */     this.binding = binding;
/* 143 */     this.soapVersion = binding.getSOAPVersion();
/* 144 */     this.container = container;
/* 145 */     this.port = port;
/* 146 */     this.implementationClass = implementationClass;
/* 147 */     this.serviceDef = serviceDef;
/* 148 */     this.seiModel = seiModel;
/* 149 */     this.endpointPolicy = endpointPolicy;
/*     */     
/* 151 */     LazyMOMProvider.INSTANCE.registerEndpoint(this);
/* 152 */     initManagedObjectManager();
/*     */     
/* 154 */     if (serviceDef != null) {
/* 155 */       serviceDef.setOwner(this);
/*     */     }
/*     */     
/* 158 */     ComponentFeature cf = (ComponentFeature)binding.getFeature(ComponentFeature.class);
/* 159 */     if (cf != null) {
/* 160 */       switch (cf.getTarget()) {
/*     */         case GLASSFISH_NO_JMX:
/* 162 */           this.componentRegistry.add(cf.getComponent());
/*     */           break;
/*     */         case null:
/* 165 */           container.getComponents().add(cf.getComponent());
/*     */           break;
/*     */         default:
/* 168 */           throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/* 171 */     ComponentsFeature csf = (ComponentsFeature)binding.getFeature(ComponentsFeature.class);
/* 172 */     if (csf != null) {
/* 173 */       for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 174 */         switch (cfi.getTarget()) {
/*     */           case GLASSFISH_NO_JMX:
/* 176 */             this.componentRegistry.add(cfi.getComponent());
/*     */             continue;
/*     */           case null:
/* 179 */             container.getComponents().add(cfi.getComponent());
/*     */             continue;
/*     */         } 
/* 182 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 187 */     TubelineAssembler assembler = TubelineAssemblerFactory.create(Thread.currentThread().getContextClassLoader(), binding.getBindingId(), container);
/*     */     
/* 189 */     assert assembler != null;
/*     */     
/* 191 */     this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, binding, seiModel);
/*     */     
/* 193 */     this.context = createServerTubeAssemblerContext(terminalTube, isSynchronous);
/* 194 */     this.masterTubeline = assembler.createServer(this.context);
/*     */     
/* 196 */     Codec c = this.context.getCodec();
/* 197 */     if (c instanceof EndpointAwareCodec) {
/*     */       
/* 199 */       c = c.copy();
/* 200 */       ((EndpointAwareCodec)c).setEndpoint(this);
/*     */     } 
/* 202 */     this.masterCodec = c;
/*     */     
/* 204 */     this.tubePool = (Pool<Tube>)new Pool.TubePool(this.masterTubeline);
/* 205 */     terminalTube.setEndpoint(this);
/* 206 */     this.engine = new Engine(toString(), container);
/* 207 */     this.wsdlProperties = (port == null) ? (WSDLProperties)new WSDLDirectProperties(serviceName, portName, seiModel) : (WSDLProperties)new WSDLPortProperties(port, seiModel);
/*     */     
/* 209 */     Map<QName, WSEndpointReference.EPRExtension> eprExtensions = new HashMap<QName, WSEndpointReference.EPRExtension>();
/*     */     try {
/* 211 */       if (port != null) {
/*     */         
/* 213 */         WSEndpointReference wsdlEpr = ((WSDLPortImpl)port).getEPR();
/* 214 */         if (wsdlEpr != null) {
/* 215 */           for (WSEndpointReference.EPRExtension extnEl : wsdlEpr.getEPRExtensions()) {
/* 216 */             eprExtensions.put(extnEl.getQName(), extnEl);
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/* 221 */       EndpointReferenceExtensionContributor[] eprExtnContributors = (EndpointReferenceExtensionContributor[])ServiceFinder.find(EndpointReferenceExtensionContributor.class).toArray();
/* 222 */       for (EndpointReferenceExtensionContributor eprExtnContributor : eprExtnContributors) {
/* 223 */         WSEndpointReference.EPRExtension wsdlEPRExtn = eprExtensions.remove(eprExtnContributor.getQName());
/* 224 */         WSEndpointReference.EPRExtension endpointEprExtn = eprExtnContributor.getEPRExtension(this, wsdlEPRExtn);
/* 225 */         if (endpointEprExtn != null) {
/* 226 */           eprExtensions.put(endpointEprExtn.getQName(), endpointEprExtn);
/*     */         }
/*     */       } 
/* 229 */       for (WSEndpointReference.EPRExtension extn : eprExtensions.values()) {
/* 230 */         this.endpointReferenceExtensions.put(extn.getQName(), new WSEPRExtension(XMLStreamBuffer.createNewBufferFromXMLStreamReader(extn.readAsXMLStreamReader()), extn.getQName()));
/*     */       }
/*     */     }
/* 233 */     catch (XMLStreamException ex) {
/* 234 */       throw new WebServiceException(ex);
/*     */     } 
/* 236 */     if (!eprExtensions.isEmpty()) {
/* 237 */       serviceDef.addFilter((SDDocumentFilter)new EPRSDDocumentFilter(this));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerTubeAssemblerContext createServerTubeAssemblerContext(EndpointAwareTube terminalTube, boolean isSynchronous) {
/* 243 */     return (ServerTubeAssemblerContext)new ServerPipeAssemblerContext(this.seiModel, this.port, this, terminalTube, isSynchronous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSEndpointImpl(@NotNull QName serviceName, @NotNull QName portName, WSBinding binding, Container container, SEIModel seiModel, WSDLPort port, Tube masterTubeline) {
/* 251 */     this.serviceName = serviceName;
/* 252 */     this.portName = portName;
/* 253 */     this.binding = binding;
/* 254 */     this.soapVersion = binding.getSOAPVersion();
/* 255 */     this.container = container;
/* 256 */     this.endpointPolicy = null;
/* 257 */     this.port = port;
/* 258 */     this.seiModel = seiModel;
/* 259 */     this.serviceDef = null;
/* 260 */     this.implementationClass = null;
/* 261 */     this.masterTubeline = masterTubeline;
/* 262 */     this.masterCodec = ((BindingImpl)this.binding).createCodec();
/*     */     
/* 264 */     LazyMOMProvider.INSTANCE.registerEndpoint(this);
/* 265 */     initManagedObjectManager();
/*     */     
/* 267 */     this.operationDispatcher = (port == null) ? null : new OperationDispatcher(port, binding, seiModel);
/* 268 */     this.context = (ServerTubeAssemblerContext)new ServerPipeAssemblerContext(seiModel, port, this, null, false);
/*     */ 
/*     */     
/* 271 */     this.tubePool = (Pool<Tube>)new Pool.TubePool(masterTubeline);
/* 272 */     this.engine = new Engine(toString(), container);
/* 273 */     this.wsdlProperties = (port == null) ? (WSDLProperties)new WSDLDirectProperties(serviceName, portName, seiModel) : (WSDLProperties)new WSDLPortProperties(port, seiModel);
/*     */   }
/*     */   
/*     */   public Collection<WSEndpointReference.EPRExtension> getEndpointReferenceExtensions() {
/* 277 */     return this.endpointReferenceExtensions.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 285 */     return this.operationDispatcher;
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 289 */     return this.endpointPolicy;
/*     */   }
/*     */   @NotNull
/*     */   public Class<T> getImplementationClass() {
/* 293 */     return this.implementationClass;
/*     */   }
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 297 */     return this.binding;
/*     */   }
/*     */   @NotNull
/*     */   public Container getContainer() {
/* 301 */     return this.container;
/*     */   }
/*     */   
/*     */   public WSDLPort getPort() {
/* 305 */     return this.port;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 310 */     return this.seiModel;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor exec) {
/* 314 */     this.engine.setExecutor(exec);
/*     */   }
/*     */ 
/*     */   
/*     */   public Engine getEngine() {
/* 319 */     return this.engine;
/*     */   }
/*     */   
/*     */   public void schedule(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 323 */     processAsync(request, callback, interceptor, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAsync(final Packet request, final WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor, boolean schedule) {
/* 329 */     Container old = ContainerResolver.getDefault().enterContainer(this.container);
/*     */     try {
/* 331 */       request.endpoint = this;
/* 332 */       request.addSatellite((PropertySet)this.wsdlProperties);
/*     */       
/* 334 */       Fiber fiber = this.engine.createFiber();
/* 335 */       fiber.setDeliverThrowableInPacket(true);
/* 336 */       if (interceptor != null) {
/* 337 */         fiber.addInterceptor(interceptor);
/*     */       }
/* 339 */       final Tube tube = (Tube)this.tubePool.take();
/* 340 */       Fiber.CompletionCallback cbak = new Fiber.CompletionCallback() {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 342 */             ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 343 */             if (tc == null)
/*     */             {
/*     */               
/* 346 */               WSEndpointImpl.this.tubePool.recycle(tube);
/*     */             }
/*     */             
/* 349 */             if (callback != null) {
/* 350 */               if (tc != null) {
/* 351 */                 response = WSEndpointImpl.this.createServiceResponseForException(tc, response, WSEndpointImpl.this.soapVersion, request.endpoint.getPort(), null, request.endpoint.getBinding());
/*     */               }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 358 */               callback.onCompletion(response);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 365 */             throw new IllegalStateException();
/*     */           }
/*     */         };
/*     */       
/* 369 */       fiber.start(tube, request, cbak, (this.binding.isFeatureEnabled(SyncStartForAsyncFeature.class) || !schedule));
/*     */     }
/*     */     finally {
/*     */       
/* 373 */       ContainerResolver.getDefault().exitContainer(old);
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
/*     */   public Packet createServiceResponseForException(ThrowableContainerPropertySet tc, Packet responsePacket, SOAPVersion soapVersion, WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 386 */     if (tc.isFaultCreated()) return responsePacket;
/*     */     
/* 388 */     Message faultMessage = SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, tc.getThrowable());
/* 389 */     Packet result = responsePacket.createServerResponse(faultMessage, wsdlPort, seiModel, binding);
/*     */     
/* 391 */     tc.setFaultMessage(faultMessage);
/* 392 */     tc.setResponsePacket(responsePacket);
/* 393 */     tc.setFaultCreated(true);
/* 394 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 399 */     processAsync(request, callback, interceptor, false);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public WSEndpoint.PipeHead createPipeHead() {
/* 404 */     return new WSEndpoint.PipeHead() {
/* 405 */         private final Tube tube = TubeCloner.clone(WSEndpointImpl.this.masterTubeline);
/*     */ 
/*     */         
/*     */         @NotNull
/*     */         public Packet process(Packet request, WebServiceContextDelegate wscd, TransportBackChannel tbc) {
/* 410 */           Container old = ContainerResolver.getDefault().enterContainer(WSEndpointImpl.this.container); try {
/*     */             Packet packet;
/* 412 */             request.webServiceContextDelegate = wscd;
/* 413 */             request.transportBackChannel = tbc;
/* 414 */             request.endpoint = WSEndpointImpl.this;
/* 415 */             request.addSatellite((PropertySet)WSEndpointImpl.this.wsdlProperties);
/*     */             
/* 417 */             Fiber fiber = WSEndpointImpl.this.engine.createFiber();
/*     */             
/*     */             try {
/* 420 */               packet = fiber.runSync(this.tube, request);
/* 421 */             } catch (RuntimeException re) {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 426 */               Message faultMsg = SOAPFaultBuilder.createSOAPFaultMessage(WSEndpointImpl.this.soapVersion, null, re);
/*     */               
/* 428 */               packet = request.createServerResponse(faultMsg, request.endpoint.getPort(), null, request.endpoint.getBinding());
/*     */             } 
/*     */ 
/*     */             
/* 432 */             return packet;
/*     */           } finally {
/* 434 */             ContainerResolver.getDefault().exitContainer(old);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public synchronized void dispose() {
/* 441 */     if (this.disposed) {
/*     */       return;
/*     */     }
/* 444 */     this.disposed = true;
/*     */     
/* 446 */     this.masterTubeline.preDestroy();
/*     */     
/* 448 */     for (Handler handler : this.binding.getHandlerChain()) {
/* 449 */       Method[] arr$; int len$; int i$; for (arr$ = handler.getClass().getMethods(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Method method = arr$[i$];
/* 450 */         if (method.getAnnotation(PreDestroy.class) == null) {
/*     */           i$++; continue;
/*     */         } 
/*     */         try {
/* 454 */           method.invoke(handler, new Object[0]);
/* 455 */         } catch (Exception e) {
/* 456 */           logger.log(Level.WARNING, HandlerMessages.HANDLER_PREDESTROY_IGNORE(e.getMessage()), e);
/*     */         }  }
/*     */     
/*     */     } 
/*     */     
/* 461 */     closeManagedObjectManager();
/* 462 */     LazyMOMProvider.INSTANCE.unregisterEndpoint(this);
/*     */   }
/*     */   
/*     */   public ServiceDefinitionImpl getServiceDefinition() {
/* 466 */     return this.serviceDef;
/*     */   }
/*     */   
/*     */   public Set<EndpointComponent> getComponentRegistry() {
/* 470 */     Set<EndpointComponent> sec = new EndpointComponentSet();
/* 471 */     for (Component c : this.componentRegistry) {
/* 472 */       sec.add((c instanceof EndpointComponentWrapper) ? ((EndpointComponentWrapper)c).component : new ComponentWrapper(c));
/*     */     }
/*     */ 
/*     */     
/* 476 */     return sec;
/*     */   }
/*     */   
/*     */   private class EndpointComponentSet extends HashSet<EndpointComponent> {
/*     */     private EndpointComponentSet() {}
/*     */     
/*     */     public Iterator<EndpointComponent> iterator() {
/* 483 */       final Iterator<EndpointComponent> it = super.iterator();
/* 484 */       return new Iterator<EndpointComponent>() {
/* 485 */           private EndpointComponent last = null;
/*     */           
/*     */           public boolean hasNext() {
/* 488 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public EndpointComponent next() {
/* 492 */             this.last = it.next();
/* 493 */             return this.last;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 497 */             it.remove();
/* 498 */             if (this.last != null) {
/* 499 */               WSEndpointImpl.this.componentRegistry.remove((this.last instanceof WSEndpointImpl.ComponentWrapper) ? ((WSEndpointImpl.ComponentWrapper)this.last).component : new WSEndpointImpl.EndpointComponentWrapper(this.last));
/*     */             }
/*     */ 
/*     */             
/* 503 */             this.last = null;
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean add(EndpointComponent e) {
/* 510 */       boolean result = super.add(e);
/* 511 */       if (result) {
/* 512 */         WSEndpointImpl.this.componentRegistry.add(new WSEndpointImpl.EndpointComponentWrapper(e));
/*     */       }
/* 514 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 519 */       boolean result = super.remove(o);
/* 520 */       if (result) {
/* 521 */         WSEndpointImpl.this.componentRegistry.remove((o instanceof WSEndpointImpl.ComponentWrapper) ? ((WSEndpointImpl.ComponentWrapper)o).component : new WSEndpointImpl.EndpointComponentWrapper((EndpointComponent)o));
/*     */       }
/*     */ 
/*     */       
/* 525 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ComponentWrapper
/*     */     implements EndpointComponent {
/*     */     private final Component component;
/*     */     
/*     */     public ComponentWrapper(Component component) {
/* 534 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 538 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 543 */       return this.component.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 548 */       return this.component.equals(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EndpointComponentWrapper implements Component {
/*     */     private final EndpointComponent component;
/*     */     
/*     */     public EndpointComponentWrapper(EndpointComponent component) {
/* 556 */       this.component = component;
/*     */     }
/*     */     
/*     */     public <S> S getSPI(Class<S> spiType) {
/* 560 */       return (S)this.component.getSPI(spiType);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 565 */       return this.component.hashCode();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/* 570 */       return this.component.equals(obj);
/*     */     }
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 576 */     return this.componentRegistry;
/*     */   }
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, Element... referenceParameters) {
/* 580 */     List<Element> refParams = null;
/* 581 */     if (referenceParameters != null) {
/* 582 */       refParams = Arrays.asList(referenceParameters);
/*     */     }
/* 584 */     return getEndpointReference(clazz, address, wsdlAddress, null, refParams);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
/* 590 */     QName portType = null;
/* 591 */     if (this.port != null) {
/* 592 */       portType = this.port.getBinding().getPortTypeName();
/*     */     }
/*     */     
/* 595 */     AddressingVersion av = AddressingVersion.fromSpecClass(clazz);
/* 596 */     return (T)(new WSEndpointReference(av, address, this.serviceName, this.portName, portType, metadata, wsdlAddress, referenceParameters, this.endpointReferenceExtensions.values(), null)).toSpec(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public QName getPortName() {
/* 602 */     return this.portName;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Codec createCodec() {
/* 607 */     return this.masterCodec.copy();
/*     */   }
/*     */   @NotNull
/*     */   public QName getServiceName() {
/* 611 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   private void initManagedObjectManager() {
/* 615 */     synchronized (this.managedObjectManagerLock) {
/* 616 */       if (this.managedObjectManager == null)
/* 617 */         switch (this.lazyMOMProviderScope) {
/*     */           case GLASSFISH_NO_JMX:
/* 619 */             this.managedObjectManager = new WSEndpointMOMProxy(this);
/*     */             break;
/*     */           default:
/* 622 */             this.managedObjectManager = obtainManagedObjectManager();
/*     */             break;
/*     */         }  
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 629 */     return this.managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   ManagedObjectManager obtainManagedObjectManager() {
/* 640 */     MonitorRootService monitorRootService = new MonitorRootService(this);
/* 641 */     ManagedObjectManager mOM = monitorRootService.createManagedObjectManager(this);
/*     */ 
/*     */     
/* 644 */     mOM.resumeJMXRegistration();
/*     */     
/* 646 */     return mOM;
/*     */   }
/*     */   
/*     */   public void scopeChanged(LazyMOMProvider.Scope scope) {
/* 650 */     synchronized (this.managedObjectManagerLock) {
/* 651 */       if (this.managedObjectManagerClosed) {
/*     */         return;
/*     */       }
/*     */       
/* 655 */       this.lazyMOMProviderScope = scope;
/*     */ 
/*     */       
/* 658 */       if (this.managedObjectManager == null) {
/* 659 */         if (scope != LazyMOMProvider.Scope.GLASSFISH_NO_JMX) {
/* 660 */           this.managedObjectManager = obtainManagedObjectManager();
/*     */         } else {
/* 662 */           this.managedObjectManager = new WSEndpointMOMProxy(this);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 667 */       else if (this.managedObjectManager instanceof WSEndpointMOMProxy && !((WSEndpointMOMProxy)this.managedObjectManager).isInitialized()) {
/*     */         
/* 669 */         ((WSEndpointMOMProxy)this.managedObjectManager).setManagedObjectManager(obtainManagedObjectManager());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 675 */   private static final Logger monitoringLogger = Logger.getLogger("com.sun.xml.ws.monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeManagedObjectManager() {
/* 681 */     synchronized (this.managedObjectManagerLock) {
/* 682 */       if (this.managedObjectManagerClosed == true) {
/*     */         return;
/*     */       }
/* 685 */       if (this.managedObjectManager != null) {
/* 686 */         boolean close = true;
/*     */ 
/*     */         
/* 689 */         if (this.managedObjectManager instanceof WSEndpointMOMProxy && !((WSEndpointMOMProxy)this.managedObjectManager).isInitialized())
/*     */         {
/* 691 */           close = false;
/*     */         }
/*     */         
/* 694 */         if (close) {
/*     */           try {
/* 696 */             ObjectName name = this.managedObjectManager.getObjectName(this.managedObjectManager.getRoot());
/*     */             
/* 698 */             if (name != null) {
/* 699 */               monitoringLogger.log(Level.INFO, "Closing Metro monitoring root: {0}", name);
/*     */             }
/* 701 */             this.managedObjectManager.close();
/* 702 */           } catch (IOException e) {
/* 703 */             monitoringLogger.log(Level.WARNING, "Ignoring error when closing Managed Object Manager", e);
/*     */           } 
/*     */         }
/*     */       } 
/* 707 */       this.managedObjectManagerClosed = true;
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public ServerTubeAssemblerContext getAssemblerContext() {
/* 712 */     return this.context;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\WSEndpointImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */