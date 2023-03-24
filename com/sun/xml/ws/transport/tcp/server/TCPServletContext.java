/*    */ package com.sun.xml.ws.transport.tcp.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
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
/*    */ public final class TCPServletContext
/*    */   implements TCPContext
/*    */ {
/*    */   private final ServletContext servletContext;
/* 59 */   private final Map<String, Object> attributes = new HashMap<String, Object>();
/*    */   
/*    */   public TCPServletContext(ServletContext servletContext) {
/* 62 */     this.servletContext = servletContext;
/*    */   }
/*    */   
/*    */   public InputStream getResourceAsStream(String resource) throws IOException {
/* 66 */     return this.servletContext.getResourceAsStream(resource);
/*    */   }
/*    */   
/*    */   public Set<String> getResourcePaths(String path) {
/* 70 */     return this.servletContext.getResourcePaths(path);
/*    */   }
/*    */   
/*    */   public URL getResource(String resource) throws MalformedURLException {
/* 74 */     return this.servletContext.getResource(resource);
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getAttribute(String name) {
/* 79 */     return this.attributes.get(name);
/*    */   }
/*    */   
/*    */   public void setAttribute(String name, Object value) {
/* 83 */     this.attributes.put(name, value);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPServletContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */