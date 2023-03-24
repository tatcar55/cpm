/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.FiberContextSwitchInterceptor;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.wsdl.OperationDispatcher;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import org.glassfish.gmbal.AMXClient;
/*     */ import org.glassfish.gmbal.GmbalMBean;
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
/*     */ public class WSEndpointMOMProxy
/*     */   extends WSEndpoint
/*     */   implements ManagedObjectManager
/*     */ {
/*     */   @NotNull
/*     */   private final WSEndpointImpl wsEndpoint;
/*     */   private ManagedObjectManager managedObjectManager;
/*     */   
/*     */   WSEndpointMOMProxy(@NotNull WSEndpointImpl wsEndpoint) {
/*  88 */     this.wsEndpoint = wsEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ManagedObjectManager getManagedObjectManager() {
/*  98 */     if (this.managedObjectManager == null) {
/*  99 */       this.managedObjectManager = this.wsEndpoint.obtainManagedObjectManager();
/*     */     }
/* 101 */     return this.managedObjectManager;
/*     */   }
/*     */   
/*     */   void setManagedObjectManager(ManagedObjectManager managedObjectManager) {
/* 105 */     this.managedObjectManager = managedObjectManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 115 */     return (this.managedObjectManager != null);
/*     */   }
/*     */   
/*     */   public WSEndpointImpl getWsEndpoint() {
/* 119 */     return this.wsEndpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void suspendJMXRegistration() {
/* 124 */     getManagedObjectManager().suspendJMXRegistration();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resumeJMXRegistration() {
/* 129 */     getManagedObjectManager().resumeJMXRegistration();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isManagedObject(Object obj) {
/* 134 */     return getManagedObjectManager().isManagedObject(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot() {
/* 139 */     return getManagedObjectManager().createRoot();
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot(Object root) {
/* 144 */     return getManagedObjectManager().createRoot(root);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean createRoot(Object root, String name) {
/* 149 */     return getManagedObjectManager().createRoot(root, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getRoot() {
/* 154 */     return getManagedObjectManager().getRoot();
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj, String name) {
/* 159 */     return getManagedObjectManager().register(parent, obj, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean register(Object parent, Object obj) {
/* 164 */     return getManagedObjectManager().register(parent, obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj, String name) {
/* 169 */     return getManagedObjectManager().registerAtRoot(obj, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public GmbalMBean registerAtRoot(Object obj) {
/* 174 */     return getManagedObjectManager().registerAtRoot(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregister(Object obj) {
/* 179 */     getManagedObjectManager().unregister(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public ObjectName getObjectName(Object obj) {
/* 184 */     return getManagedObjectManager().getObjectName(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public AMXClient getAMXClient(Object obj) {
/* 189 */     return getManagedObjectManager().getAMXClient(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getObject(ObjectName oname) {
/* 194 */     return getManagedObjectManager().getObject(oname);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stripPrefix(String... str) {
/* 199 */     getManagedObjectManager().stripPrefix(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stripPackagePrefix() {
/* 204 */     getManagedObjectManager().stripPackagePrefix();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDomain() {
/* 209 */     return getManagedObjectManager().getDomain();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMBeanServer(MBeanServer server) {
/* 214 */     getManagedObjectManager().setMBeanServer(server);
/*     */   }
/*     */ 
/*     */   
/*     */   public MBeanServer getMBeanServer() {
/* 219 */     return getManagedObjectManager().getMBeanServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setResourceBundle(ResourceBundle rb) {
/* 224 */     getManagedObjectManager().setResourceBundle(rb);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceBundle getResourceBundle() {
/* 229 */     return getManagedObjectManager().getResourceBundle();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAnnotation(AnnotatedElement element, Annotation annotation) {
/* 234 */     getManagedObjectManager().addAnnotation(element, annotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel level) {
/* 239 */     getManagedObjectManager().setRegistrationDebug(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRuntimeDebug(boolean flag) {
/* 244 */     getManagedObjectManager().setRuntimeDebug(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTypelibDebug(int level) {
/* 249 */     getManagedObjectManager().setTypelibDebug(level);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJMXRegistrationDebug(boolean flag) {
/* 254 */     getManagedObjectManager().setJMXRegistrationDebug(flag);
/*     */   }
/*     */ 
/*     */   
/*     */   public String dumpSkeleton(Object obj) {
/* 259 */     return getManagedObjectManager().dumpSkeleton(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public void suppressDuplicateRootReport(boolean suppressReport) {
/* 264 */     getManagedObjectManager().suppressDuplicateRootReport(suppressReport);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 269 */     getManagedObjectManager().close();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equalsProxiedInstance(WSEndpoint endpoint) {
/* 274 */     if (this.wsEndpoint == null) {
/* 275 */       return (endpoint == null);
/*     */     }
/* 277 */     return this.wsEndpoint.equals(endpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public Codec createCodec() {
/* 282 */     return this.wsEndpoint.createCodec();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getServiceName() {
/* 287 */     return this.wsEndpoint.getServiceName();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getPortName() {
/* 292 */     return this.wsEndpoint.getPortName();
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getImplementationClass() {
/* 297 */     return this.wsEndpoint.getImplementationClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSBinding getBinding() {
/* 302 */     return this.wsEndpoint.getBinding();
/*     */   }
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 307 */     return this.wsEndpoint.getContainer();
/*     */   }
/*     */ 
/*     */   
/*     */   public WSDLPort getPort() {
/* 312 */     return this.wsEndpoint.getPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExecutor(Executor exec) {
/* 317 */     this.wsEndpoint.setExecutor(exec);
/*     */   }
/*     */ 
/*     */   
/*     */   public void schedule(Packet request, WSEndpoint.CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
/* 322 */     this.wsEndpoint.schedule(request, callback, interceptor);
/*     */   }
/*     */ 
/*     */   
/*     */   public WSEndpoint.PipeHead createPipeHead() {
/* 327 */     return this.wsEndpoint.createPipeHead();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 332 */     if (this.wsEndpoint != null) {
/* 333 */       this.wsEndpoint.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ServiceDefinition getServiceDefinition() {
/* 339 */     return this.wsEndpoint.getServiceDefinition();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getComponentRegistry() {
/* 344 */     return this.wsEndpoint.getComponentRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public SEIModel getSEIModel() {
/* 349 */     return this.wsEndpoint.getSEIModel();
/*     */   }
/*     */ 
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/* 354 */     return this.wsEndpoint.getPolicyMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeManagedObjectManager() {
/* 359 */     this.wsEndpoint.closeManagedObjectManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerTubeAssemblerContext getAssemblerContext() {
/* 364 */     return this.wsEndpoint.getAssemblerContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Class<EndpointReference> clazz, String address, String wsdlAddress, Element... referenceParameters) {
/* 369 */     return this.wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public EndpointReference getEndpointReference(Class<EndpointReference> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
/* 374 */     return this.wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, metadata, referenceParameters);
/*     */   }
/*     */ 
/*     */   
/*     */   public OperationDispatcher getOperationDispatcher() {
/* 379 */     return this.wsEndpoint.getOperationDispatcher();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createServiceResponseForException(ThrowableContainerPropertySet tc, Packet responsePacket, SOAPVersion soapVersion, WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
/* 390 */     return this.wsEndpoint.createServiceResponseForException(tc, responsePacket, soapVersion, wsdlPort, seiModel, binding);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\WSEndpointMOMProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */