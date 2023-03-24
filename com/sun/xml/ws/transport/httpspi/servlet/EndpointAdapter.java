/*    */ package com.sun.xml.ws.transport.httpspi.servlet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import javax.xml.ws.Endpoint;
/*    */ import javax.xml.ws.spi.http.HttpContext;
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
/*    */ public final class EndpointAdapter
/*    */ {
/*    */   private final Endpoint endpoint;
/*    */   private final String urlPattern;
/*    */   private final EndpointHttpContext httpContext;
/*    */   
/*    */   public EndpointAdapter(Endpoint endpoint, String urlPattern) {
/* 59 */     this.endpoint = endpoint;
/* 60 */     this.urlPattern = urlPattern;
/* 61 */     this.httpContext = new EndpointHttpContext(urlPattern);
/*    */   }
/*    */   
/*    */   public Endpoint getEndpoint() {
/* 65 */     return this.endpoint;
/*    */   }
/*    */   
/*    */   public HttpContext getContext() {
/* 69 */     return this.httpContext;
/*    */   }
/*    */   
/*    */   public void publish() {
/* 73 */     this.endpoint.publish(this.httpContext);
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 77 */     this.endpoint.stop();
/*    */   }
/*    */   
/*    */   public String getUrlPattern() {
/* 81 */     return this.urlPattern;
/*    */   }
/*    */   
/*    */   public void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
/* 85 */     this.httpContext.handle(context, request, response);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValidPath() {
/* 93 */     if (this.urlPattern.endsWith("/*")) {
/* 94 */       return this.urlPattern.substring(0, this.urlPattern.length() - 2);
/*    */     }
/* 96 */     return this.urlPattern;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\EndpointAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */