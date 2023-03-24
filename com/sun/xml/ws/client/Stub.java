/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.addressing.WSEPRExtension;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.Cancelable;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.ComponentFeature;
/*     */ import com.sun.xml.ws.api.ComponentRegistry;
/*     */ import com.sun.xml.ws.api.ComponentsFeature;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Engine;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.FiberContextSwitchInterceptorFactory;
/*     */ import com.sun.xml.ws.api.pipe.SyncStartForAsyncFeature;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.ws.model.SOAPSEIModel;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLDirectProperties;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLPortProperties;
/*     */ import com.sun.xml.ws.model.wsdl.WSDLProperties;
/*     */ import com.sun.xml.ws.resources.ClientMessages;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import com.sun.xml.ws.util.RuntimeVersion;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.Binding;
/*     */ import javax.xml.ws.BindingProvider;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.RespectBindingFeature;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Stub
/*     */   implements WSBindingProvider, ResponseContextReceiver, ComponentRegistry
/*     */ {
/*     */   public static final String PREVENT_SYNC_START_FOR_ASYNC_INVOKE = "com.sun.xml.ws.client.StubRequestSyncStartForAsyncInvoke";
/*     */   private Pool<Tube> tubes;
/*     */   private final Engine engine;
/*     */   protected final WSServiceDelegate owner;
/*     */   @Nullable
/*     */   protected WSEndpointReference endpointReference;
/*     */   protected final BindingImpl binding;
/*     */   protected final WSPortInfo portInfo;
/*     */   protected AddressingVersion addrVersion;
/* 167 */   public RequestContext requestContext = new RequestContext();
/*     */ 
/*     */   
/*     */   private final RequestContext cleanRequestContext;
/*     */ 
/*     */   
/*     */   private ResponseContext responseContext;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected final WSDLPort wsdlPort;
/*     */ 
/*     */   
/*     */   protected QName portname;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private volatile Header[] userOutboundHeaders;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private final WSDLProperties wsdlProperties;
/*     */   
/* 190 */   protected OperationDispatcher operationDispatcher = null;
/*     */   
/*     */   @NotNull
/*     */   private final ManagedObjectManager managedObjectManager;
/*     */   
/*     */   private boolean managedObjectManagerClosed = false;
/* 196 */   private final Set<Component> components = new CopyOnWriteArraySet<Component>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected Stub(WSServiceDelegate owner, Tube master, BindingImpl binding, WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 211 */     this(owner, master, null, null, binding, wsdlPort, defaultEndPointAddress, epr);
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
/*     */   @Deprecated
/*     */   protected Stub(QName portname, WSServiceDelegate owner, Tube master, BindingImpl binding, WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 228 */     this(owner, master, null, portname, binding, wsdlPort, defaultEndPointAddress, epr);
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
/*     */   protected Stub(WSPortInfo portInfo, BindingImpl binding, Tube master, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 244 */     this((WSServiceDelegate)portInfo.getOwner(), master, portInfo, null, binding, portInfo.getPort(), defaultEndPointAddress, epr);
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
/*     */   protected Stub(WSPortInfo portInfo, BindingImpl binding, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 259 */     this(portInfo, binding, null, defaultEndPointAddress, epr);
/*     */   }
/*     */ 
/*     */   
/*     */   private Stub(WSServiceDelegate owner, @Nullable Tube master, @Nullable WSPortInfo portInfo, QName portname, BindingImpl binding, @Nullable WSDLPort wsdlPort, EndpointAddress defaultEndPointAddress, @Nullable WSEndpointReference epr) {
/* 264 */     Container old = ContainerResolver.getDefault().enterContainer(owner.getContainer());
/*     */     try {
/* 266 */       this.owner = owner;
/* 267 */       this.portInfo = portInfo;
/* 268 */       this.wsdlPort = (wsdlPort != null) ? wsdlPort : ((portInfo != null) ? portInfo.getPort() : null);
/* 269 */       this.portname = portname;
/* 270 */       if (portname == null) {
/* 271 */         if (portInfo != null) {
/* 272 */           this.portname = portInfo.getPortName();
/* 273 */         } else if (wsdlPort != null) {
/* 274 */           this.portname = wsdlPort.getName();
/*     */         } 
/*     */       }
/* 277 */       this.binding = binding;
/*     */       
/* 279 */       ComponentFeature cf = (ComponentFeature)binding.getFeature(ComponentFeature.class);
/* 280 */       if (cf != null && ComponentFeature.Target.STUB.equals(cf.getTarget())) {
/* 281 */         this.components.add(cf.getComponent());
/*     */       }
/* 283 */       ComponentsFeature csf = (ComponentsFeature)binding.getFeature(ComponentsFeature.class);
/* 284 */       if (csf != null) {
/* 285 */         for (ComponentFeature cfi : csf.getComponentFeatures()) {
/* 286 */           if (ComponentFeature.Target.STUB.equals(cfi.getTarget())) {
/* 287 */             this.components.add(cfi.getComponent());
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 292 */       if (epr != null) {
/* 293 */         this.requestContext.setEndPointAddressString(epr.getAddress());
/*     */       } else {
/* 295 */         this.requestContext.setEndpointAddress(defaultEndPointAddress);
/*     */       } 
/* 297 */       this.engine = new Engine(getStringId(), owner.getContainer(), owner.getExecutor());
/* 298 */       this.endpointReference = epr;
/* 299 */       this.wsdlProperties = (wsdlPort == null) ? (WSDLProperties)new WSDLDirectProperties(owner.getServiceName(), portname) : (WSDLProperties)new WSDLPortProperties(wsdlPort);
/*     */       
/* 301 */       this.cleanRequestContext = this.requestContext.copy();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 306 */       this.managedObjectManager = (new MonitorRootClient(this)).createManagedObjectManager(this);
/*     */       
/* 308 */       if (master != null) {
/* 309 */         this.tubes = (Pool<Tube>)new Pool.TubePool(master);
/*     */       } else {
/* 311 */         this.tubes = (Pool<Tube>)new Pool.TubePool(createPipeline(portInfo, (WSBinding)binding));
/*     */       } 
/*     */       
/* 314 */       this.addrVersion = binding.getAddressingVersion();
/*     */ 
/*     */ 
/*     */       
/* 318 */       this.managedObjectManager.resumeJMXRegistration();
/*     */     } finally {
/* 320 */       ContainerResolver.getDefault().exitContainer(old);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Tube createPipeline(WSPortInfo portInfo, WSBinding binding) {
/*     */     SOAPSEIModel sOAPSEIModel;
/* 329 */     checkAllWSDLExtensionsUnderstood(portInfo, binding);
/* 330 */     SEIModel seiModel = null;
/* 331 */     Class sei = null;
/* 332 */     if (portInfo instanceof SEIPortInfo) {
/* 333 */       SEIPortInfo sp = (SEIPortInfo)portInfo;
/* 334 */       sOAPSEIModel = sp.model;
/* 335 */       sei = sp.sei;
/*     */     } 
/* 337 */     BindingID bindingId = portInfo.getBindingId();
/*     */     
/* 339 */     TubelineAssembler assembler = TubelineAssemblerFactory.create(Thread.currentThread().getContextClassLoader(), bindingId, this.owner.getContainer());
/*     */     
/* 341 */     if (assembler == null) {
/* 342 */       throw new WebServiceException("Unable to process bindingID=" + bindingId);
/*     */     }
/* 344 */     return assembler.createClient(new ClientTubeAssemblerContext(portInfo.getEndpointAddress(), portInfo.getPort(), this, binding, this.owner.getContainer(), ((BindingImpl)binding).createCodec(), (SEIModel)sOAPSEIModel, sei));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPort getWSDLPort() {
/* 352 */     return this.wsdlPort;
/*     */   }
/*     */   
/*     */   public WSService getService() {
/* 356 */     return this.owner;
/*     */   }
/*     */   
/*     */   public Pool<Tube> getTubes() {
/* 360 */     return this.tubes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkAllWSDLExtensionsUnderstood(WSPortInfo port, WSBinding binding) {
/* 371 */     if (port.getPort() != null && binding.isFeatureEnabled(RespectBindingFeature.class)) {
/* 372 */       ((WSDLPortImpl)port.getPort()).areRequiredExtensionsUnderstood();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WSPortInfo getPortInfo() {
/* 378 */     return this.portInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 388 */     if (this.operationDispatcher == null && this.wsdlPort != null) {
/* 389 */       this.operationDispatcher = new OperationDispatcher(this.wsdlPort, (WSBinding)this.binding, null);
/*     */     }
/* 391 */     return this.operationDispatcher;
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
/*     */   @NotNull
/*     */   protected final QName getServiceName() {
/* 416 */     return this.owner.getServiceName();
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
/*     */   public final Executor getExecutor() {
/* 428 */     return this.owner.getExecutor();
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
/*     */   protected final Packet process(Packet packet, RequestContext requestContext, ResponseContextReceiver receiver) {
/* 449 */     packet.isSynchronousMEP = Boolean.valueOf(true);
/* 450 */     packet.component = (Component)this;
/* 451 */     configureRequestPacket(packet, requestContext);
/* 452 */     Pool<Tube> pool = this.tubes;
/* 453 */     if (pool == null) {
/* 454 */       throw new WebServiceException("close method has already been invoked");
/*     */     }
/*     */     
/* 457 */     Fiber fiber = this.engine.createFiber();
/* 458 */     configureFiber(fiber);
/*     */ 
/*     */     
/* 461 */     Tube tube = (Tube)pool.take();
/*     */     
/*     */     try {
/* 464 */       return fiber.runSync(tube, packet);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/* 472 */       Packet reply = (fiber.getPacket() == null) ? packet : fiber.getPacket();
/* 473 */       receiver.setResponseContext(new ResponseContext(reply));
/*     */       
/* 475 */       pool.recycle(tube);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void configureRequestPacket(Packet packet, RequestContext requestContext) {
/* 481 */     packet.proxy = (BindingProvider)this;
/* 482 */     packet.handlerConfig = this.binding.getHandlerConfig();
/*     */ 
/*     */     
/* 485 */     Header[] hl = this.userOutboundHeaders;
/* 486 */     if (hl != null) {
/* 487 */       MessageHeaders mh = packet.getMessage().getMessageHeaders();
/* 488 */       for (Header h : hl) {
/* 489 */         mh.add(h);
/*     */       }
/*     */     } 
/*     */     
/* 493 */     requestContext.fill(packet, (this.binding.getAddressingVersion() != null));
/* 494 */     packet.addSatellite((PropertySet)this.wsdlProperties);
/*     */     
/* 496 */     if (this.addrVersion != null) {
/*     */       
/* 498 */       MessageHeaders headerList = packet.getMessage().getMessageHeaders();
/* 499 */       AddressingUtils.fillRequestAddressingHeaders(headerList, this.wsdlPort, (WSBinding)this.binding, packet);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 505 */       if (this.endpointReference != null) {
/* 506 */         this.endpointReference.addReferenceParametersToList(packet.getMessage().getHeaders());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void processAsync(AsyncResponseImpl<?> receiver, Packet request, RequestContext requestContext, final Fiber.CompletionCallback completionCallback) {
/* 531 */     request.component = (Component)this;
/* 532 */     configureRequestPacket(request, requestContext);
/*     */     
/* 534 */     final Pool<Tube> pool = this.tubes;
/* 535 */     if (pool == null) {
/* 536 */       throw new WebServiceException("close method has already been invoked");
/*     */     }
/*     */     
/* 539 */     Fiber fiber = this.engine.createFiber();
/* 540 */     configureFiber(fiber);
/*     */     
/* 542 */     receiver.setCancelable((Cancelable)fiber);
/*     */ 
/*     */     
/* 545 */     if (receiver.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 549 */     FiberContextSwitchInterceptorFactory fcsif = (FiberContextSwitchInterceptorFactory)this.owner.getSPI(FiberContextSwitchInterceptorFactory.class);
/* 550 */     if (fcsif != null) {
/* 551 */       fiber.addInterceptor(fcsif.create());
/*     */     }
/*     */ 
/*     */     
/* 555 */     final Tube tube = (Tube)pool.take();
/*     */     
/* 557 */     Fiber.CompletionCallback fiberCallback = new Fiber.CompletionCallback()
/*     */       {
/*     */         public void onCompletion(@NotNull Packet response) {
/* 560 */           pool.recycle(tube);
/* 561 */           completionCallback.onCompletion(response);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void onCompletion(@NotNull Throwable error) {
/* 568 */           completionCallback.onCompletion(error);
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 574 */     fiber.start(tube, request, fiberCallback, (getBinding().isFeatureEnabled(SyncStartForAsyncFeature.class) && !requestContext.containsKey("com.sun.xml.ws.client.StubRequestSyncStartForAsyncInvoke")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void configureFiber(Fiber fiber) {}
/*     */ 
/*     */ 
/*     */   
/* 584 */   private static final Logger monitoringLogger = Logger.getLogger("com.sun.xml.ws.monitoring");
/*     */ 
/*     */   
/*     */   public void close() {
/* 588 */     Pool.TubePool tp = (Pool.TubePool)this.tubes;
/* 589 */     if (tp != null) {
/*     */ 
/*     */ 
/*     */       
/* 593 */       Tube p = tp.takeMaster();
/* 594 */       p.preDestroy();
/* 595 */       this.tubes = null;
/*     */     } 
/* 597 */     if (!this.managedObjectManagerClosed) {
/*     */       try {
/* 599 */         ObjectName name = this.managedObjectManager.getObjectName(this.managedObjectManager.getRoot());
/*     */         
/* 601 */         if (name != null) {
/* 602 */           monitoringLogger.log(Level.INFO, "Closing Metro monitoring root: {0}", name);
/*     */         }
/* 604 */         this.managedObjectManager.close();
/* 605 */       } catch (IOException e) {
/* 606 */         monitoringLogger.log(Level.WARNING, "Ignoring error when closing Managed Object Manager", e);
/*     */       } 
/* 608 */       this.managedObjectManagerClosed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final WSBinding getBinding() {
/* 614 */     return (WSBinding)this.binding;
/*     */   }
/*     */ 
/*     */   
/*     */   public final Map<String, Object> getRequestContext() {
/* 619 */     return this.requestContext.asMap();
/*     */   }
/*     */   
/*     */   public void resetRequestContext() {
/* 623 */     this.requestContext = this.cleanRequestContext.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public final ResponseContext getResponseContext() {
/* 628 */     return this.responseContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResponseContext(ResponseContext rc) {
/* 633 */     this.responseContext = rc;
/*     */   }
/*     */   
/*     */   private String getStringId() {
/* 637 */     return RuntimeVersion.VERSION + ": Stub for " + getRequestContext().get("javax.xml.ws.service.endpoint.address");
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 642 */     return getStringId();
/*     */   }
/*     */ 
/*     */   
/*     */   public final WSEndpointReference getWSEndpointReference() {
/* 647 */     if (this.binding.getBindingID().equals("http://www.w3.org/2004/08/wsdl/http")) {
/* 648 */       throw new UnsupportedOperationException(ClientMessages.UNSUPPORTED_OPERATION("BindingProvider.getEndpointReference(Class<T> class)", "XML/HTTP Binding", "SOAP11 or SOAP12 Binding"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 653 */     if (this.endpointReference != null) {
/* 654 */       return this.endpointReference;
/*     */     }
/*     */     
/* 657 */     String eprAddress = this.requestContext.getEndpointAddress().toString();
/* 658 */     QName portTypeName = null;
/* 659 */     String wsdlAddress = null;
/* 660 */     List<WSEndpointReference.EPRExtension> wsdlEPRExtensions = new ArrayList<WSEndpointReference.EPRExtension>();
/* 661 */     if (this.wsdlPort != null) {
/* 662 */       portTypeName = this.wsdlPort.getBinding().getPortTypeName();
/* 663 */       wsdlAddress = eprAddress + "?wsdl";
/*     */ 
/*     */       
/*     */       try {
/* 667 */         WSEndpointReference wsdlEpr = ((WSDLPortImpl)this.wsdlPort).getEPR();
/* 668 */         if (wsdlEpr != null) {
/* 669 */           for (WSEndpointReference.EPRExtension extnEl : wsdlEpr.getEPRExtensions()) {
/* 670 */             wsdlEPRExtensions.add(new WSEPRExtension(XMLStreamBuffer.createNewBufferFromXMLStreamReader(extnEl.readAsXMLStreamReader()), extnEl.getQName()));
/*     */           
/*     */           }
/*     */         }
/*     */       }
/* 675 */       catch (XMLStreamException ex) {
/* 676 */         throw new WebServiceException(ex);
/*     */       } 
/*     */     } 
/* 679 */     AddressingVersion av = AddressingVersion.W3C;
/* 680 */     this.endpointReference = new WSEndpointReference(av, eprAddress, getServiceName(), getPortName(), portTypeName, null, wsdlAddress, null, wsdlEPRExtensions, null);
/*     */ 
/*     */     
/* 683 */     return this.endpointReference;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final W3CEndpointReference getEndpointReference() {
/* 689 */     if (this.binding.getBindingID().equals("http://www.w3.org/2004/08/wsdl/http")) {
/* 690 */       throw new UnsupportedOperationException(ClientMessages.UNSUPPORTED_OPERATION("BindingProvider.getEndpointReference()", "XML/HTTP Binding", "SOAP11 or SOAP12 Binding"));
/*     */     }
/*     */     
/* 693 */     return getEndpointReference(W3CEndpointReference.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public final <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
/* 698 */     return (T)getWSEndpointReference().toSpec(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 705 */     return this.managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setOutboundHeaders(List<Header> headers) {
/* 715 */     if (headers == null) {
/* 716 */       this.userOutboundHeaders = null;
/*     */     } else {
/* 718 */       for (Header h : headers) {
/* 719 */         if (h == null) {
/* 720 */           throw new IllegalArgumentException();
/*     */         }
/*     */       } 
/* 723 */       this.userOutboundHeaders = headers.<Header>toArray(new Header[headers.size()]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setOutboundHeaders(Header... headers) {
/* 729 */     if (headers == null) {
/* 730 */       this.userOutboundHeaders = null;
/*     */     } else {
/* 732 */       for (Header h : headers) {
/* 733 */         if (h == null) {
/* 734 */           throw new IllegalArgumentException();
/*     */         }
/*     */       } 
/* 737 */       Header[] hl = new Header[headers.length];
/* 738 */       System.arraycopy(headers, 0, hl, 0, headers.length);
/* 739 */       this.userOutboundHeaders = hl;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final List<Header> getInboundHeaders() {
/* 745 */     return Collections.unmodifiableList((List<? extends Header>)this.responseContext.get("com.sun.xml.ws.api.message.HeaderList"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setAddress(String address) {
/* 751 */     this.requestContext.put("javax.xml.ws.service.endpoint.address", address);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> S getSPI(Class<S> spiType) {
/* 756 */     for (Component c : this.components) {
/* 757 */       S s = (S)c.getSPI(spiType);
/* 758 */       if (s != null) {
/* 759 */         return s;
/*     */       }
/*     */     } 
/* 762 */     return (S)this.owner.getSPI(spiType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Component> getComponents() {
/* 767 */     return this.components;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   protected abstract QName getPortName();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\Stub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */