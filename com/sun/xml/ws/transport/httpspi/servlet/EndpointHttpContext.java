/*    */ package com.sun.xml.ws.transport.httpspi.servlet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Set;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
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
/*    */ public final class EndpointHttpContext
/*    */   extends HttpContext
/*    */ {
/*    */   private final String urlPattern;
/*    */   
/*    */   public EndpointHttpContext(String urlPattern) {
/* 57 */     this.urlPattern = urlPattern;
/*    */   }
/*    */   
/*    */   void handle(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws IOException {
/* 61 */     EndpointHttpExchange exchange = new EndpointHttpExchange(request, response, context, this);
/* 62 */     this.handler.handle(exchange);
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 66 */     return this.urlPattern;
/*    */   }
/*    */   
/*    */   public Object getAttribute(String name) {
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   public Set<String> getAttributeNames() {
/* 74 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\EndpointHttpContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */