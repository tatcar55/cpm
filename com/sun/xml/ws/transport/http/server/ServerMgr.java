/*     */ package com.sun.xml.ws.transport.http.server;
/*     */ 
/*     */ import com.sun.net.httpserver.HttpContext;
/*     */ import com.sun.net.httpserver.HttpServer;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
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
/*     */ final class ServerMgr
/*     */ {
/*  64 */   private static final ServerMgr serverMgr = new ServerMgr();
/*  65 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.http");
/*     */ 
/*     */   
/*  68 */   private final Map<InetSocketAddress, ServerState> servers = new HashMap<InetSocketAddress, ServerState>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ServerMgr getInstance() {
/*  77 */     return serverMgr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   HttpContext createContext(String address) {
/*     */     try {
/*     */       ServerState state;
/*  89 */       URL url = new URL(address);
/*  90 */       int port = url.getPort();
/*  91 */       if (port == -1) {
/*  92 */         port = url.getDefaultPort();
/*     */       }
/*  94 */       InetSocketAddress inetAddress = new InetSocketAddress(url.getHost(), port);
/*     */       
/*  96 */       synchronized (this.servers) {
/*  97 */         state = this.servers.get(inetAddress);
/*  98 */         if (state == null) {
/*  99 */           logger.fine("Creating new HTTP Server at " + inetAddress);
/*     */           
/* 101 */           HttpServer httpServer = HttpServer.create(inetAddress, 0);
/* 102 */           httpServer.setExecutor(Executors.newCachedThreadPool());
/* 103 */           String path = url.toURI().getPath();
/* 104 */           logger.fine("Creating HTTP Context at = " + path);
/* 105 */           HttpContext httpContext = httpServer.createContext(path);
/* 106 */           httpServer.start();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 111 */           inetAddress = httpServer.getAddress();
/*     */           
/* 113 */           logger.fine("HTTP server started = " + inetAddress);
/* 114 */           state = new ServerState(httpServer, path);
/* 115 */           this.servers.put(inetAddress, state);
/* 116 */           return httpContext;
/*     */         } 
/*     */       } 
/* 119 */       HttpServer server = state.getServer();
/*     */       
/* 121 */       if (state.getPaths().contains(url.getPath())) {
/* 122 */         String err = "Context with URL path " + url.getPath() + " already exists on the server " + server.getAddress();
/* 123 */         logger.fine(err);
/* 124 */         throw new IllegalArgumentException(err);
/*     */       } 
/*     */       
/* 127 */       logger.fine("Creating HTTP Context at = " + url.getPath());
/* 128 */       HttpContext context = server.createContext(url.getPath());
/* 129 */       state.oneMoreContext(url.getPath());
/* 130 */       return context;
/* 131 */     } catch (Exception e) {
/* 132 */       throw new ServerRtException("server.rt.err", new Object[] { e });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeContext(HttpContext context) {
/* 141 */     InetSocketAddress inetAddress = context.getServer().getAddress();
/* 142 */     synchronized (this.servers) {
/* 143 */       ServerState state = this.servers.get(inetAddress);
/* 144 */       int instances = state.noOfContexts();
/* 145 */       if (instances < 2) {
/* 146 */         ((ExecutorService)state.getServer().getExecutor()).shutdown();
/* 147 */         state.getServer().stop(0);
/* 148 */         this.servers.remove(inetAddress);
/*     */       } else {
/* 150 */         state.getServer().removeContext(context);
/* 151 */         state.oneLessContext(context.getPath());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static final class ServerState {
/*     */     private final HttpServer server;
/*     */     private int instances;
/* 159 */     private Set<String> paths = new HashSet<String>();
/*     */     
/*     */     ServerState(HttpServer server, String path) {
/* 162 */       this.server = server;
/* 163 */       this.instances = 1;
/* 164 */       this.paths.add(path);
/*     */     }
/*     */     
/*     */     public HttpServer getServer() {
/* 168 */       return this.server;
/*     */     }
/*     */     
/*     */     public void oneMoreContext(String path) {
/* 172 */       this.instances++;
/* 173 */       this.paths.add(path);
/*     */     }
/*     */     
/*     */     public void oneLessContext(String path) {
/* 177 */       this.instances--;
/* 178 */       this.paths.remove(path);
/*     */     }
/*     */     
/*     */     public int noOfContexts() {
/* 182 */       return this.instances;
/*     */     }
/*     */     
/*     */     public Set<String> getPaths() {
/* 186 */       return this.paths;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\server\ServerMgr.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */