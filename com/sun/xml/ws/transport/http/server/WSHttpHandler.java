/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.net.httpserver.HttpExchange;
/*     */ import com.sun.net.httpserver.HttpHandler;
/*     */ import com.sun.xml.ws.resources.HttpserverMessages;
/*     */ import com.sun.xml.ws.transport.http.HttpAdapter;
/*     */ import com.sun.xml.ws.transport.http.WSHTTPConnection;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ final class WSHttpHandler
/*     */   implements HttpHandler
/*     */ {
/*     */   private static final String GET_METHOD = "GET";
/*     */   private static final String POST_METHOD = "POST";
/*     */   private static final String HEAD_METHOD = "HEAD";
/*     */   private static final String PUT_METHOD = "PUT";
/*     */   private static final String DELETE_METHOD = "DELETE";
/*  70 */   private static final Logger LOGGER = Logger.getLogger("com.sun.xml.ws.server.http");
/*     */ 
/*     */   
/*  73 */   private static final boolean fineTraceEnabled = LOGGER.isLoggable(Level.FINE);
/*     */   
/*     */   private final HttpAdapter adapter;
/*     */   private final Executor executor;
/*     */   
/*     */   public WSHttpHandler(@NotNull HttpAdapter adapter, @Nullable Executor executor) {
/*  79 */     assert adapter != null;
/*  80 */     this.adapter = adapter;
/*  81 */     this.executor = executor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handle(HttpExchange msg) {
/*     */     try {
/*  89 */       if (fineTraceEnabled) {
/*  90 */         LOGGER.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/*  92 */       if (this.executor != null) {
/*     */ 
/*     */         
/*  95 */         this.executor.execute(new HttpHandlerRunnable(msg));
/*     */       } else {
/*  97 */         handleExchange(msg);
/*     */       } 
/*  99 */     } catch (Throwable e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleExchange(HttpExchange msg) throws IOException {
/* 105 */     WSHTTPConnection con = new ServerConnectionImpl(this.adapter, msg);
/*     */     try {
/* 107 */       if (fineTraceEnabled) {
/* 108 */         LOGGER.log(Level.FINE, "Received HTTP request:{0}", msg.getRequestURI());
/*     */       }
/* 110 */       String method = msg.getRequestMethod();
/* 111 */       if (method.equals("GET") || method.equals("POST") || method.equals("HEAD") || method.equals("PUT") || method.equals("DELETE")) {
/*     */         
/* 113 */         this.adapter.handle(con);
/*     */       }
/* 115 */       else if (LOGGER.isLoggable(Level.WARNING)) {
/* 116 */         LOGGER.warning(HttpserverMessages.UNEXPECTED_HTTP_METHOD(method));
/*     */       } 
/*     */     } finally {
/*     */       
/* 120 */       msg.close();
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
/* 132 */       this.msg = msg;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 137 */         WSHttpHandler.this.handleExchange(this.msg);
/* 138 */       } catch (Throwable e) {
/*     */         
/* 140 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\WSHttpHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */