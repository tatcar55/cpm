/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*     */ 
/*     */ import com.sun.enterprise.webservice.EjbRuntimeEndpointInfo;
/*     */ import com.sun.enterprise.webservice.JAXWSAdapterRegistry;
/*     */ import com.sun.enterprise.webservice.WebServiceEjbEndpointRegistry;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.server.Adapter;
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPAdapterRegistry;
/*     */ import com.sun.xml.ws.transport.tcp.util.WSTCPURI;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ public final class WSTCPAdapterRegistryImpl
/*     */   implements WSTCPAdapterRegistry
/*     */ {
/*  61 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   final Map<String, RegistryRecord> registry = new ConcurrentHashMap<String, RegistryRecord>();
/*  68 */   private static final WSTCPAdapterRegistryImpl instance = new WSTCPAdapterRegistryImpl();
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static WSTCPAdapterRegistryImpl getInstance() {
/*  74 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPAdapter getTarget(@NotNull WSTCPURI requestURI) {
/*     */     RegistryRecord record;
/*  82 */     if (requestURI.path.length() > 0 && !requestURI.path.equals("/")) {
/*  83 */       record = this.registry.get(requestURI.path);
/*     */     } else {
/*  85 */       record = this.registry.get("/");
/*     */     } 
/*     */     
/*  88 */     if (record != null) {
/*  89 */       if (record.adapter == null) {
/*     */         try {
/*  91 */           record.adapter = createWSAdapter(requestURI.path, record.wsEndpointDescriptor);
/*     */           
/*  93 */           logger.log(Level.FINE, "WSTCPAdapterRegistryImpl. Register adapter. Path: {0}", requestURI.path);
/*  94 */         } catch (Exception e) {
/*     */           
/*  96 */           logger.log(Level.SEVERE, "WSTCPAdapterRegistryImpl. " + MessagesMessages.WSTCP_0008_ERROR_TCP_ADAPTER_CREATE(record.wsEndpointDescriptor.getWSServiceName()), e);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 101 */       return record.adapter;
/*     */     } 
/*     */     
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerEndpoint(@NotNull String path, @NotNull WSEndpointDescriptor wsEndpointDescriptor) {
/* 110 */     this.registry.put(path, new RegistryRecord(wsEndpointDescriptor));
/*     */   }
/*     */   
/*     */   public void deregisterEndpoint(@NotNull String path) {
/* 114 */     logger.log(Level.FINE, "WSTCPAdapterRegistryImpl. DeRegister adapter for {0}", path);
/* 115 */     this.registry.remove(path);
/*     */   }
/*     */   
/*     */   public WSEndpointDescriptor lookupEndpoint(@NotNull String path) {
/* 119 */     RegistryRecord record = this.registry.get(path);
/* 120 */     return (record != null) ? record.wsEndpointDescriptor : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private TCPAdapter createWSAdapter(@NotNull String wsPath, @NotNull WSEndpointDescriptor wsEndpointDescriptor) throws Exception {
/*     */     Adapter adapter;
/* 126 */     if (wsEndpointDescriptor.isEJB()) {
/* 127 */       EjbRuntimeEndpointInfo ejbEndPtInfo = WebServiceEjbEndpointRegistry.getRegistry().getEjbWebServiceEndpoint(wsEndpointDescriptor.getURI(), "POST", null);
/*     */       
/* 129 */       adapter = (Adapter)ejbEndPtInfo.prepareInvocation(true);
/*     */     } else {
/* 131 */       String uri = wsEndpointDescriptor.getURI();
/* 132 */       adapter = JAXWSAdapterRegistry.getInstance().getAdapter(wsEndpointDescriptor.getContextRoot(), uri, uri);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 137 */     TCPAdapter tcpAdapter = new TCP109Adapter(wsEndpointDescriptor.getWSServiceName().toString(), wsPath, adapter.getEndpoint(), new ServletFakeArtifactSet(wsEndpointDescriptor.getRequestURL(), wsEndpointDescriptor.getUrlPattern()), wsEndpointDescriptor.isEJB());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     return tcpAdapter;
/*     */   }
/*     */   
/*     */   protected static class RegistryRecord {
/*     */     public TCPAdapter adapter;
/*     */     public WSEndpointDescriptor wsEndpointDescriptor;
/*     */     
/*     */     public RegistryRecord(WSEndpointDescriptor wsEndpointDescriptor) {
/* 151 */       this.wsEndpointDescriptor = wsEndpointDescriptor;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\WSTCPAdapterRegistryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */