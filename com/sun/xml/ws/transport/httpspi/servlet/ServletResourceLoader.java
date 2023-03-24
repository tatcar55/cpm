/*    */ package com.sun.xml.ws.transport.httpspi.servlet;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ public final class ServletResourceLoader
/*    */   implements ResourceLoader
/*    */ {
/*    */   private final ServletContext context;
/*    */   
/*    */   public ServletResourceLoader(ServletContext context) {
/* 57 */     this.context = context;
/*    */   }
/*    */   
/*    */   public URL getResource(String path) throws MalformedURLException {
/* 61 */     return this.context.getResource(path);
/*    */   }
/*    */   
/*    */   public URL getCatalogFile() throws MalformedURLException {
/* 65 */     return getResource("/WEB-INF/jax-ws-catalog.xml");
/*    */   }
/*    */   
/*    */   public Set<String> getResourcePaths(String path) {
/* 69 */     return this.context.getResourcePaths(path);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\ServletResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */