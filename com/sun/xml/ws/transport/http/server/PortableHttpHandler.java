/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.resources.HttpserverMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.spi.http.HttpExchange;
/*     */ import javax.xml.ws.spi.http.HttpHandler;
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
/*     */ final class PortableHttpHandler
/*     */   extends HttpHandler
/*     */ {
/*     */   private static final String GET_METHOD = "GET";
/*     */   private static final String POST_METHOD = "POST";
/*     */   private static final String HEAD_METHOD = "HEAD";
/*     */   private static final String PUT_METHOD = "PUT";
/*     */   private static final String DELETE_METHOD = "DELETE";
/*  69 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.http");
/*     */   
/*     */   private final HttpAdapter adapter;
/*     */   
/*     */   private final Executor executor;
/*     */ 
/*     */   
/*     */   public PortableHttpHandler(@NotNull HttpAdapter adapter, @Nullable Executor executor) {
/*  77 */     assert adapter != null;
/*  78 */     this.adapter = adapter;
/*  79 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(HttpExchange msg) {
/*     */     try {
/*  88 */       if (logger.isLoggable(Level.FINE)) {
/*  89 */         logger.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  91 */       if (this.executor != null) {
/*     */ 
/*     */         
/*  94 */         this.executor.execute(new HttpHandlerRunnable(msg));
/*     */       } else {
/*  96 */         handleExchange(msg);
/*     */       } 
/*  98 */     } catch (Throwable e) {
/*     */       
/* 100 */       logger.log(Level.SEVERE, (String)null, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleExchange(HttpExchange msg) throws IOException {
/* 105 */     WSHTTPConnection con = new PortableConnectionImpl(this.adapter, msg);
/*     */     try {
/* 107 */       if (logger.isLoggable(Level.FINE)) {
/* 108 */         logger.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/* 110 */       String method = msg.getRequestMethod();
/* 111 */       if (method.equals("GET") || method.equals("POST") || method.equals("HEAD") || method.equals("PUT") || method.equals("DELETE")) {
/*     */         
/* 113 */         this.adapter.handle(con);
/*     */       } else {
/* 115 */         logger.warning(HttpserverMessages.UNEXPECTED_HTTP_METHOD(method));
/*     */       } 
/*     */     } finally {
/* 118 */       msg.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   class HttpHandlerRunnable
/*     */     implements Runnable
/*     */   {
/*     */     final HttpExchange msg;
/*     */ 
/*     */     
/*     */     HttpHandlerRunnable(HttpExchange msg) {
/* 130 */       this.msg = msg;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 137 */         PortableHttpHandler.this.handleExchange(this.msg);
/* 138 */       } catch (Throwable e) {
/*     */         
/* 140 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\PortableHttpHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */