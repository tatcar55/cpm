/*     */ package com.sun.xml.ws.metro.api.config.management;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.Component;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.config.management.EndpointCreationAttributes;
/*     */ import com.sun.xml.ws.api.config.management.Reconfigurable;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.EndpointComponent;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.config.management.ManagementMessages;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagedEndpoint<T>
/*     */   extends WSEndpoint<T>
/*     */ {
/*  96 */   private static final Logger LOGGER = Logger.getLogger(ManagedEndpoint.class);
/*     */   
/*     */   private WSEndpoint<T> endpointDelegate;
/*  99 */   private final Collection<ReconfigNotifier> reconfigNotifiers = new LinkedList<ReconfigNotifier>();
/*     */   
/*     */   private static final long ENDPOINT_DISPOSE_DELAY_DEFAULT = 120000L;
/*     */   
/* 103 */   private long endpointDisposeDelay = 120000L;
/* 104 */   private final ScheduledExecutorService disposeThreadPool = Executors.newScheduledThreadPool(1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final EndpointCreationAttributes creationAttributes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManagedEndpoint(WSEndpoint<T> endpoint, EndpointCreationAttributes attributes) {
/* 117 */     this.creationAttributes = attributes;
/* 118 */     this.endpointDelegate = endpoint;
/*     */     
/* 120 */     for (ReconfigNotifier notifier : this.reconfigNotifiers) {
/* 121 */       notifier.sendNotification();
/*     */     }
/*     */     
/* 124 */     if (LOGGER.isLoggable(Level.CONFIG)) {
/* 125 */       LOGGER.config(ManagementMessages.WSM_5066_STARTING_ENDPOINT());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addNotifier(ReconfigNotifier notifier) {
/* 134 */     this.reconfigNotifiers.add(notifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointCreationAttributes getCreationAttributes() {
/* 142 */     return this.creationAttributes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void swapEndpointDelegate(WSEndpoint<T> endpoint) {
/* 153 */     Set<EndpointComponent> endpointComponents = endpoint.getComponentRegistry();
/*     */     
/* 155 */     WSEndpoint<T> oldEndpoint = this.endpointDelegate;
/* 156 */     this.endpointDelegate = endpoint;
/* 157 */     for (EndpointComponent component : endpointComponents) {
/* 158 */       Reconfigurable reconfigurable = (Reconfigurable)component.getSPI(Reconfigurable.class);
/* 159 */       if (reconfigurable != null) {
/* 160 */         reconfigurable.reconfigure();
/*     */       }
/*     */     } 
/* 163 */     disposeDelegate(oldEndpoint);
/* 164 */     LOGGER.info(ManagementMessages.WSM_5000_RECONFIGURED_ENDPOINT(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void dispose() {
/* 169 */     this.disposeThreadPool.shutdown();
/* 170 */     if (this.endpointDelegate != null) {
/* 171 */       this.endpointDelegate.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Codec createCodec() {
/* 177 */     return this.endpointDelegate.createCodec();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getServiceName() {
/* 182 */     return this.endpointDelegate.getServiceName();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getPortName() {
/* 187 */     return this.endpointDelegate.getPortName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<T> getImplementationClass() {
/* 192 */     return this.endpointDelegate.getImplementationClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSBinding getBinding() {
/* 197 */     return this.endpointDelegate.getBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 202 */     return this.endpointDelegate.getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSDLPort getPort() {
/* 207 */     return this.endpointDelegate.getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExecutor(Executor exec) {
/* 212 */     this.endpointDelegate.setExecutor(exec);
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 217 */     this.endpointDelegate.schedule(request, callback, interceptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 222 */     this.endpointDelegate.process(request, callback, interceptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public WSEndpoint.PipeHead createPipeHead() {
/* 227 */     return this.endpointDelegate.createPipeHead();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceDefinition getServiceDefinition() {
/* 232 */     return this.endpointDelegate.getServiceDefinition();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<EndpointComponent> getComponentRegistry() {
/* 237 */     return this.endpointDelegate.getComponentRegistry();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Set<Component> getComponents() {
/* 242 */     return this.endpointDelegate.getComponents();
/*     */   }
/*     */ 
/*     */   
/*     */   public SEIModel getSEIModel() {
/* 247 */     return this.endpointDelegate.getSEIModel();
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 252 */     return this.endpointDelegate.getPolicyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public ManagedObjectManager getManagedObjectManager() {
/* 257 */     return this.endpointDelegate.getManagedObjectManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeManagedObjectManager() {
/* 262 */     this.endpointDelegate.closeManagedObjectManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerTubeAssemblerContext getAssemblerContext() {
/* 267 */     return this.endpointDelegate.getAssemblerContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void disposeDelegate(final WSEndpoint<T> endpoint) {
/* 277 */     Runnable dispose = new Runnable() {
/* 278 */         final WSEndpoint<T> disposableEndpoint = endpoint;
/*     */         
/*     */         public void run() {
/*     */           try {
/* 282 */             this.disposableEndpoint.dispose();
/* 283 */           } catch (WebServiceException e) {
/* 284 */             ManagedEndpoint.LOGGER.severe(ManagementMessages.WSM_5101_DISPOSE_FAILED(), e);
/*     */           } 
/*     */         }
/*     */       };
/* 288 */     this.disposeThreadPool.schedule(dispose, this.endpointDisposeDelay, TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsProxiedInstance(WSEndpoint endpoint) {
/* 293 */     if (this.endpointDelegate == null) {
/* 294 */       return (endpoint == null);
/*     */     }
/* 296 */     if (endpoint instanceof ManagedEndpoint) {
/* 297 */       return false;
/*     */     }
/* 299 */     return this.endpointDelegate.equals(endpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, Element... referenceParameters) {
/* 304 */     return (T)this.endpointDelegate.getEndpointReference(clazz, address, wsdlAddress, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
/* 309 */     return (T)this.endpointDelegate.getEndpointReference(clazz, address, wsdlAddress, metadata, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 314 */     return this.endpointDelegate.getOperationDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet createServiceResponseForException(ThrowableContainerPropertySet tcps, Packet packet, SOAPVersion soapv, WSDLPort wsdlp, SEIModel seim, WSBinding wsb) {
/* 319 */     return this.endpointDelegate.createServiceResponseForException(tcps, packet, soapv, wsdlp, seim, wsb);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\metro\api\config\management\ManagedEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */