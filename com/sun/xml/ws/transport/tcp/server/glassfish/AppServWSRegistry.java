/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*     */ 
/*     */ import com.sun.enterprise.deployment.WebServiceEndpoint;
/*     */ import com.sun.enterprise.webservice.EjbRuntimeEndpointInfo;
/*     */ import com.sun.enterprise.webservice.WebServiceEjbEndpointRegistry;
/*     */ import com.sun.enterprise.webservice.monitoring.Endpoint;
/*     */ import com.sun.enterprise.webservice.monitoring.WebServiceEngine;
/*     */ import com.sun.enterprise.webservice.monitoring.WebServiceEngineFactory;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AppServWSRegistry
/*     */ {
/*  60 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*  63 */   private static final AppServWSRegistry instance = new AppServWSRegistry();
/*     */   
/*     */   public static AppServWSRegistry getInstance() {
/*  66 */     return instance;
/*     */   }
/*     */   
/*     */   private AppServWSRegistry() {
/*  70 */     WSEndpointLifeCycleListener lifecycleListener = new WSEndpointLifeCycleListener();
/*     */     
/*  72 */     WebServiceEngine engine = WebServiceEngineFactory.getInstance().getEngine();
/*  73 */     engine.addLifecycleListener(lifecycleListener);
/*     */     
/*  75 */     populateEndpoints(engine);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEndpoints(@NotNull WebServiceEngine engine) {
/*  82 */     Iterator<Endpoint> endpoints = engine.getEndpoints();
/*  83 */     while (endpoints.hasNext()) {
/*  84 */       registerEndpoint(endpoints.next());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public EjbRuntimeEndpointInfo getEjbRuntimeEndpointInfo(@NotNull String wsPath) {
/*  93 */     WSEndpointDescriptor wsEndpointDescriptor = WSTCPAdapterRegistryImpl.getInstance().lookupEndpoint(wsPath);
/*     */     
/*  95 */     EjbRuntimeEndpointInfo endpointInfo = null;
/*     */     
/*  97 */     if (wsEndpointDescriptor.isEJB()) {
/*  98 */       endpointInfo = WebServiceEjbEndpointRegistry.getRegistry().getEjbWebServiceEndpoint(wsEndpointDescriptor.getURI(), "POST", null);
/*     */     }
/*     */ 
/*     */     
/* 102 */     return endpointInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerEndpoint(@NotNull Endpoint endpoint) {
/* 109 */     WebServiceEndpoint wsServiceDescriptor = endpoint.getDescriptor();
/*     */     
/* 111 */     if (wsServiceDescriptor != null && isTCPEnabled(wsServiceDescriptor)) {
/* 112 */       String contextRoot = getEndpointContextRoot(wsServiceDescriptor);
/* 113 */       String urlPattern = getEndpointUrlPattern(wsServiceDescriptor);
/* 114 */       String wsPath = getWebServiceEndpointPath(wsServiceDescriptor);
/*     */       
/* 116 */       if (logger.isLoggable(Level.FINE)) {
/* 117 */         logger.log(Level.FINE, MessagesMessages.WSTCP_1110_APP_SERV_REG_REGISTER_ENDPOINT(wsServiceDescriptor.getServiceName(), wsPath, Boolean.valueOf(wsServiceDescriptor.implementedByEjbComponent())));
/*     */       }
/*     */       
/* 120 */       WSEndpointDescriptor descriptor = new WSEndpointDescriptor(wsServiceDescriptor, contextRoot, urlPattern, endpoint.getEndpointSelector());
/*     */ 
/*     */ 
/*     */       
/* 124 */       WSTCPAdapterRegistryImpl.getInstance().registerEndpoint(wsPath, descriptor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void deregisterEndpoint(@NotNull Endpoint endpoint) {
/* 132 */     WebServiceEndpoint wsServiceDescriptor = endpoint.getDescriptor();
/* 133 */     String wsPath = getWebServiceEndpointPath(wsServiceDescriptor);
/*     */     
/* 135 */     if (logger.isLoggable(Level.FINE)) {
/* 136 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1111_APP_SERV_REG_DEREGISTER_ENDPOINT(wsServiceDescriptor.getWebService().getName(), wsPath, Boolean.valueOf(wsServiceDescriptor.implementedByEjbComponent())));
/*     */     }
/*     */ 
/*     */     
/* 140 */     WSTCPAdapterRegistryImpl.getInstance().deregisterEndpoint(wsPath);
/*     */   }
/*     */   @NotNull
/*     */   private String getWebServiceEndpointPath(@NotNull WebServiceEndpoint wsServiceDescriptor) {
/*     */     String wsPath;
/* 145 */     if (!wsServiceDescriptor.implementedByEjbComponent()) {
/* 146 */       String contextRoot = wsServiceDescriptor.getWebComponentImpl().getWebBundleDescriptor().getContextRoot();
/*     */       
/* 148 */       String urlPattern = wsServiceDescriptor.getEndpointAddressUri();
/* 149 */       wsPath = contextRoot + ensureSlash(urlPattern);
/* 150 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1116_APP_SERV_REG_GET_WS_ENDP_PATH_NON_EJB(wsPath));
/*     */     } else {
/* 152 */       wsPath = wsServiceDescriptor.getEndpointAddressUri();
/* 153 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1117_APP_SERV_REG_GET_WS_ENDP_PATH_EJB(wsPath));
/*     */     } 
/*     */     
/* 156 */     return ensureSlash(wsPath);
/*     */   }
/*     */   @NotNull
/*     */   private String getEndpointContextRoot(@NotNull WebServiceEndpoint wsServiceDescriptor) {
/*     */     String contextRoot;
/* 161 */     if (!wsServiceDescriptor.implementedByEjbComponent()) {
/* 162 */       contextRoot = wsServiceDescriptor.getWebComponentImpl().getWebBundleDescriptor().getContextRoot();
/*     */       
/* 164 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1112_APP_SERV_REG_GET_ENDP_CR_NON_EJB(contextRoot));
/*     */     } else {
/* 166 */       String[] path = wsServiceDescriptor.getEndpointAddressUri().split("/");
/* 167 */       contextRoot = "/" + path[1];
/* 168 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1113_APP_SERV_REG_GET_ENDP_CR_EJB(contextRoot));
/*     */     } 
/*     */     
/* 171 */     return contextRoot;
/*     */   }
/*     */   @NotNull
/*     */   private String getEndpointUrlPattern(@NotNull WebServiceEndpoint wsServiceDescriptor) {
/*     */     String urlPattern;
/* 176 */     if (!wsServiceDescriptor.implementedByEjbComponent()) {
/* 177 */       urlPattern = wsServiceDescriptor.getEndpointAddressUri();
/* 178 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1114_APP_SERV_REG_GET_ENDP_URL_PATTERN_NON_EJB(urlPattern));
/*     */     } else {
/* 180 */       String[] path = wsServiceDescriptor.getEndpointAddressUri().split("/");
/* 181 */       if (path.length < 3) {
/* 182 */         return "";
/*     */       }
/*     */       
/* 185 */       urlPattern = "/" + path[2];
/* 186 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1115_APP_SERV_REG_GET_ENDP_URL_PATTERN_EJB(urlPattern));
/*     */     } 
/*     */     
/* 189 */     return urlPattern;
/*     */   }
/*     */   @Nullable
/*     */   private String ensureSlash(@Nullable String s) {
/* 193 */     if (s != null && s.length() > 0 && s.charAt(0) != '/') {
/* 194 */       return "/" + s;
/*     */     }
/*     */     
/* 197 */     return s;
/*     */   }
/*     */   
/*     */   private boolean isTCPEnabled(WebServiceEndpoint webServiceDesc) {
/* 201 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\AppServWSRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */