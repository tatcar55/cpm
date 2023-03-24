/*    */ package com.sun.xml.ws.transport.http.servlet;
/*    */ 
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*    */ import com.sun.xml.ws.transport.http.HttpAdapterList;
/*    */ import javax.servlet.ServletContext;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServletAdapterList
/*    */   extends HttpAdapterList<ServletAdapter>
/*    */ {
/*    */   private final ServletContext context;
/*    */   
/*    */   @Deprecated
/*    */   public ServletAdapterList() {
/* 64 */     this.context = null;
/*    */   }
/*    */   
/*    */   public ServletAdapterList(ServletContext ctxt) {
/* 68 */     this.context = ctxt;
/*    */   }
/*    */   
/*    */   ServletContext getServletContext() {
/* 72 */     return this.context;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ServletAdapter createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
/* 77 */     return new ServletAdapter(name, urlPattern, endpoint, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\servlet\ServletAdapterList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */