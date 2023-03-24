/*     */ package com.sun.xml.ws.metro.api.config.management;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.server.EndpointComponent;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.config.management.ManagementMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.HttpMetadataPublisher;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ManagedHttpMetadataPublisher
/*     */   extends HttpMetadataPublisher
/*     */   implements EndpointComponent
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(ManagedHttpMetadataPublisher.class);
/*     */   
/*     */   public <T> T getSPI(Class<T> spiType) {
/*  67 */     if (spiType.isAssignableFrom(getClass())) {
/*  68 */       return spiType.cast(this);
/*     */     }
/*     */     
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handleMetadataRequest(HttpAdapter adapter, WSHTTPConnection connection) throws IOException {
/*  78 */     String query = connection.getQueryString();
/*  79 */     if (isWSDLQuery(query)) {
/*  80 */       publishWSDL(connection, adapter);
/*  81 */       return true;
/*     */     } 
/*  83 */     if (isInitQuery(query)) {
/*  84 */       LOGGER.info(ManagementMessages.WSM_5100_INIT_RECEIVED());
/*  85 */       return true;
/*     */     } 
/*     */     
/*  88 */     return false;
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
/*     */   private boolean isWSDLQuery(String query) {
/* 102 */     return (query != null && (query.equals("WSDL") || query.startsWith("wsdl")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInitQuery(String query) {
/* 112 */     return (query != null && query.toLowerCase().equals("init-cm"));
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
/*     */   private void publishWSDL(@NotNull WSHTTPConnection connection, @NotNull HttpAdapter adapter) throws IOException {
/* 129 */     ServiceDefinition currentServiceDefinition = adapter.getEndpoint().getServiceDefinition();
/* 130 */     if (adapter.getServiceDefinition() != currentServiceDefinition) {
/* 131 */       adapter.initWSDLMap(currentServiceDefinition);
/*     */     }
/* 133 */     adapter.publishWSDL(connection);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\metro\api\config\management\ManagedHttpMetadataPublisher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */