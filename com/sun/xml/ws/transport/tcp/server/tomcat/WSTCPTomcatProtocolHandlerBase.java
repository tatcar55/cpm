/*     */ package com.sun.xml.ws.transport.tcp.server.tomcat;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.coyote.Adapter;
/*     */ import org.apache.coyote.ProtocolHandler;
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
/*     */ public abstract class WSTCPTomcatProtocolHandlerBase
/*     */   implements ProtocolHandler, Runnable
/*     */ {
/*  57 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp.server");
/*     */ 
/*     */   
/*  60 */   private Map<String, Object> atts = new HashMap<String, Object>();
/*     */   
/*     */   private Adapter adapter;
/*     */   
/*     */   protected int port;
/*  65 */   protected int redirectHttpPort = 8080;
/*     */   protected int readThreadsCount;
/*  67 */   protected int maxWorkerThreadsCount = -1;
/*  68 */   protected int minWorkerThreadsCount = -1;
/*     */   
/*     */   public void setAttribute(String string, Object object) {
/*  71 */     this.atts.put(string, object);
/*     */   }
/*     */   
/*     */   public Object getAttribute(String string) {
/*  75 */     return this.atts.get(string);
/*     */   }
/*     */   
/*     */   public Iterator getAttributeNames() {
/*  79 */     return this.atts.keySet().iterator();
/*     */   }
/*     */   
/*     */   public void setAdapter(Adapter adapter) {
/*  83 */     this.adapter = adapter;
/*     */   }
/*     */   
/*     */   public Adapter getAdapter() {
/*  87 */     return this.adapter;
/*     */   }
/*     */   
/*     */   public void init() throws Exception {
/*  91 */     if (logger.isLoggable(Level.INFO)) {
/*  92 */       logger.log(Level.INFO, MessagesMessages.WSTCP_1170_INIT_SOAPTCP(Integer.valueOf(this.port)));
/*     */     }
/*     */     
/*  95 */     WSTCPTomcatRegistry.setInstance(new WSTCPTomcatRegistry(this.port));
/*     */   }
/*     */   
/*     */   public void start() throws Exception {
/*  99 */     if (logger.isLoggable(Level.FINE)) {
/* 100 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1171_START_SOAPTCP_LISTENER());
/*     */     }
/* 102 */     (new Thread(this)).start();
/*     */   }
/*     */   
/*     */   public void resume() throws Exception {
/* 106 */     if (logger.isLoggable(Level.FINE)) {
/* 107 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1173_RESUME_SOAPTCP_LISTENER());
/*     */     }
/* 109 */     start();
/*     */   }
/*     */   
/*     */   public void pause() throws Exception {
/* 113 */     if (logger.isLoggable(Level.FINE)) {
/* 114 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1172_PAUSE_SOAPTCP_LISTENER());
/*     */     }
/* 116 */     WSTCPTomcatRegistry.setInstance(new WSTCPTomcatRegistry(-1));
/* 117 */     destroy();
/*     */   }
/*     */   
/*     */   public void setPort(int port) {
/* 121 */     this.port = port;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 125 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setReadThreadsCount(int readThreadsCount) {
/* 129 */     this.readThreadsCount = readThreadsCount;
/*     */   }
/*     */   
/*     */   public int getReadThreadsCount() {
/* 133 */     return this.readThreadsCount;
/*     */   }
/*     */   
/*     */   public void setMaxWorkerThreadsCount(int maxWorkerThreadsCount) {
/* 137 */     this.maxWorkerThreadsCount = maxWorkerThreadsCount;
/*     */   }
/*     */   
/*     */   public int getMaxWorkerThreadsCount() {
/* 141 */     return this.maxWorkerThreadsCount;
/*     */   }
/*     */   
/*     */   public void setMinWorkerThreadsCount(int minWorkerThreadsCount) {
/* 145 */     this.minWorkerThreadsCount = minWorkerThreadsCount;
/*     */   }
/*     */   
/*     */   public int getMinWorkerThreadsCount() {
/* 149 */     return this.minWorkerThreadsCount;
/*     */   }
/*     */   
/*     */   public void setRedirectHttpPort(int redirectHttpPort) {
/* 153 */     this.redirectHttpPort = redirectHttpPort;
/*     */   }
/*     */   
/*     */   public int getRedirectHttpPort() {
/* 157 */     return this.redirectHttpPort;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return MessagesMessages.WSTCP_1174_TOMCAT_SOAPTCP_LISTENER(Integer.valueOf(this.port));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\tomcat\WSTCPTomcatProtocolHandlerBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */