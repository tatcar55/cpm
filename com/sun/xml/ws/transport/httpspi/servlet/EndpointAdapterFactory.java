/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Endpoint;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.spi.Provider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EndpointAdapterFactory
/*     */   implements DeploymentDescriptorParser.AdapterFactory<EndpointAdapter>
/*     */ {
/*  60 */   private static final Logger LOGGER = Logger.getLogger(EndpointAdapterFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private final EndpointContextImpl appContext = new EndpointContextImpl();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointAdapter createAdapter(String name, String urlPattern, Class<?> implType, QName serviceName, QName portName, String bindingId, List<Source> metadata, WebServiceFeature... features) {
/*  73 */     LOGGER.info("Creating Endpoint using JAX-WS 2.2 HTTP SPI");
/*  74 */     InvokerImpl endpointInvoker = new InvokerImpl(implType);
/*  75 */     Endpoint endpoint = Provider.provider().createEndpoint(bindingId, implType, endpointInvoker, features);
/*  76 */     this.appContext.add(endpoint);
/*  77 */     endpoint.setEndpointContext(this.appContext);
/*     */ 
/*     */     
/*  80 */     if (portName != null || serviceName != null) {
/*  81 */       Map<String, Object> props = new HashMap<String, Object>();
/*  82 */       if (portName != null) {
/*  83 */         props.put("javax.xml.ws.wsdl.port", portName);
/*     */       }
/*  85 */       if (serviceName != null) {
/*  86 */         props.put("javax.xml.ws.wsdl.service", serviceName);
/*     */       }
/*  88 */       if (LOGGER.isLoggable(Level.INFO)) {
/*  89 */         LOGGER.log(Level.INFO, "Setting Endpoint Properties={0}", props);
/*     */       }
/*  91 */       endpoint.setProperties(props);
/*     */     } 
/*     */ 
/*     */     
/*  95 */     if (metadata != null) {
/*  96 */       endpoint.setMetadata(metadata);
/*  97 */       List<String> docId = new ArrayList<String>();
/*  98 */       for (Source source : metadata) {
/*  99 */         docId.add(source.getSystemId());
/*     */       }
/* 101 */       if (LOGGER.isLoggable(Level.INFO)) {
/* 102 */         LOGGER.log(Level.INFO, "Setting metadata={0}", docId);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     return new EndpointAdapter(endpoint, urlPattern);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\EndpointAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */