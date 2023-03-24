/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.Module;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebModule;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServerAdapter
/*     */   extends HttpAdapter
/*     */   implements BoundEndpoint
/*     */ {
/*     */   final String name;
/*     */   
/*     */   protected ServerAdapter(String name, String urlPattern, WSEndpoint endpoint, ServerAdapterList owner) {
/*  74 */     super(endpoint, owner, urlPattern);
/*  75 */     this.name = name;
/*     */     
/*  77 */     Module module = (Module)endpoint.getContainer().getSPI(Module.class);
/*  78 */     if (module == null) {
/*  79 */       LOGGER.log(Level.WARNING, "Container {0} doesn''t support {1}", new Object[] { endpoint.getContainer(), Module.class });
/*     */     } else {
/*     */       
/*  82 */       module.getBoundEndpoints().add(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  91 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress() {
/*  97 */     WebModule webModule = (WebModule)this.endpoint.getContainer().getSPI(WebModule.class);
/*  98 */     if (webModule == null)
/*     */     {
/* 100 */       throw new WebServiceException("Container " + this.endpoint.getContainer() + " doesn't support " + WebModule.class);
/*     */     }
/* 102 */     return getAddress(webModule.getContextPath());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public URI getAddress(String baseAddress) {
/* 107 */     String adrs = baseAddress + getValidPath();
/*     */     try {
/* 109 */       return new URI(adrs);
/* 110 */     } catch (URISyntaxException e) {
/*     */       
/* 112 */       throw new WebServiceException("Unable to compute address for " + this.endpoint, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/* 117 */     this.endpoint.dispose();
/*     */   }
/*     */   
/*     */   public String getUrlPattern() {
/* 121 */     return this.urlPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 126 */     return super.toString() + "[name=" + this.name + ']';
/*     */   }
/*     */   
/* 129 */   private static final Logger LOGGER = Logger.getLogger(ServerAdapter.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\ServerAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */