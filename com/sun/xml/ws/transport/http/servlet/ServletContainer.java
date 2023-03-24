/*    */ package com.sun.xml.ws.transport.http.servlet;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.xml.ws.api.ResourceLoader;
/*    */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*    */ import com.sun.xml.ws.api.server.Container;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.xml.ws.WebServiceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ServletContainer
/*    */   extends Container
/*    */ {
/*    */   private final ServletContext servletContext;
/* 62 */   private final ServletModule module = new ServletModule() {
/* 63 */       private final List<BoundEndpoint> endpoints = new ArrayList<BoundEndpoint>();
/*    */       @NotNull
/*    */       public List<BoundEndpoint> getBoundEndpoints() {
/* 66 */         return this.endpoints;
/*    */       }
/*    */       
/*    */       @NotNull
/*    */       public String getContextPath() {
/* 71 */         throw new WebServiceException("Container " + ServletContainer.class.getName() + " doesn't support getContextPath()");
/*    */       }
/*    */     };
/*    */   
/* 75 */   private final ResourceLoader loader = new ResourceLoader() {
/*    */       public URL getResource(String resource) throws MalformedURLException {
/* 77 */         return ServletContainer.this.servletContext.getResource("/WEB-INF/" + resource);
/*    */       }
/*    */     };
/*    */   
/*    */   ServletContainer(ServletContext servletContext) {
/* 82 */     this.servletContext = servletContext;
/*    */   }
/*    */   
/*    */   public <T> T getSPI(Class<T> spiType) {
/* 86 */     if (spiType == ServletContext.class) {
/* 87 */       return spiType.cast(this.servletContext);
/*    */     }
/* 89 */     if (spiType.isAssignableFrom(ServletModule.class)) {
/* 90 */       return spiType.cast(this.module);
/*    */     }
/* 92 */     if (spiType == ResourceLoader.class) {
/* 93 */       return spiType.cast(this.loader);
/*    */     }
/* 95 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\ServletContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */