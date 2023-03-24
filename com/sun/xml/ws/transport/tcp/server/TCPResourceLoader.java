/*    */ package com.sun.xml.ws.transport.tcp.server;
/*    */ 
/*    */ import com.sun.xml.ws.transport.http.ResourceLoader;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.Set;
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
/*    */ public final class TCPResourceLoader
/*    */   implements ResourceLoader
/*    */ {
/*    */   private final TCPContext context;
/*    */   
/*    */   public TCPResourceLoader(TCPContext context) {
/* 55 */     this.context = context;
/*    */   }
/*    */   
/*    */   public URL getResource(String path) throws MalformedURLException {
/* 59 */     return this.context.getResource(path);
/*    */   }
/*    */   
/*    */   public URL getCatalogFile() throws MalformedURLException {
/* 63 */     return getResource("/WEB-INF/jax-ws-catalog.xml");
/*    */   }
/*    */   
/*    */   public Set<String> getResourcePaths(String path) {
/* 67 */     return this.context.getResourcePaths(path);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\TCPResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */