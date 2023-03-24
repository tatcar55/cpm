/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.xml.ws.api.server.HttpEndpoint;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapterList;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.Executor;
/*     */ import javax.xml.ws.spi.http.HttpContext;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class HttpEndpoint
/*     */   extends HttpEndpoint
/*     */ {
/*     */   private String address;
/*     */   private HttpContext httpContext;
/*     */   private final HttpAdapter adapter;
/*     */   private final Executor executor;
/*     */   
/*     */   public HttpEndpoint(Executor executor, HttpAdapter adapter) {
/*  73 */     this.executor = executor;
/*  74 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public void publish(String address) {
/*  78 */     this.address = address;
/*  79 */     this.httpContext = ServerMgr.getInstance().createContext(address);
/*  80 */     publish(this.httpContext);
/*     */   }
/*     */   
/*     */   public void publish(Object serverContext) {
/*  84 */     if (serverContext instanceof HttpContext) {
/*  85 */       setHandler((HttpContext)serverContext);
/*     */       return;
/*     */     } 
/*  88 */     if (serverContext instanceof HttpContext) {
/*  89 */       this.httpContext = (HttpContext)serverContext;
/*  90 */       setHandler(this.httpContext);
/*     */       return;
/*     */     } 
/*  93 */     throw new ServerRtException(ServerMessages.NOT_KNOW_HTTP_CONTEXT_TYPE(serverContext.getClass(), HttpContext.class, HttpContext.class), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   HttpAdapterList getAdapterOwner() {
/*  99 */     return this.adapter.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getEPRAddress() {
/* 107 */     if (this.address == null)
/* 108 */       return this.httpContext.getServer().getAddress().toString(); 
/*     */     try {
/* 110 */       URL u = new URL(this.address);
/* 111 */       if (u.getPort() == 0) {
/* 112 */         return (new URL(u.getProtocol(), u.getHost(), this.httpContext.getServer().getAddress().getPort(), u.getFile())).toString();
/*     */       }
/*     */     }
/* 115 */     catch (MalformedURLException murl) {}
/* 116 */     return this.address;
/*     */   }
/*     */   
/*     */   public void stop() {
/* 120 */     if (this.httpContext != null) {
/* 121 */       if (this.address == null) {
/*     */ 
/*     */         
/* 124 */         this.httpContext.getServer().removeContext(this.httpContext);
/*     */       } else {
/*     */         
/* 127 */         ServerMgr.getInstance().removeContext(this.httpContext);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 132 */     this.adapter.getEndpoint().dispose();
/*     */   }
/*     */   
/*     */   private void setHandler(HttpContext context) {
/* 136 */     context.setHandler(new WSHttpHandler(this.adapter, this.executor));
/*     */   }
/*     */   
/*     */   private void setHandler(HttpContext context) {
/* 140 */     context.setHandler(new PortableHttpHandler(this.adapter, this.executor));
/*     */   }
/*     */   
/*     */   public <T extends javax.xml.ws.EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 144 */     String eprAddress = getEPRAddress();
/* 145 */     return clazz.cast(this.adapter.getEndpoint().getEndpointReference(clazz, eprAddress, eprAddress + "?wsdl", referenceParameters));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\HttpEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */