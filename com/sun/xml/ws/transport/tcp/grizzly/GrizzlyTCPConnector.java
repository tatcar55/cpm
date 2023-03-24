/*     */ package com.sun.xml.ws.transport.tcp.grizzly;
/*     */ 
/*     */ import com.sun.enterprise.web.connector.grizzly.SelectorThread;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.transport.tcp.server.IncomeMessageProcessor;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPMessageListener;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPConnector;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.util.Properties;
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
/*     */ public class GrizzlyTCPConnector
/*     */   implements WSTCPConnector
/*     */ {
/*     */   private SelectorThread selectorThread;
/*     */   private String host;
/*     */   private int port;
/*     */   private TCPMessageListener listener;
/*     */   private final Properties properties;
/*     */   private final boolean isPortUnificationMode;
/*     */   
/*     */   public GrizzlyTCPConnector(@NotNull String host, int port, @NotNull TCPMessageListener listener) {
/*  68 */     this.host = host;
/*  69 */     this.port = port;
/*  70 */     this.listener = listener;
/*  71 */     this.isPortUnificationMode = false;
/*  72 */     this.properties = new Properties();
/*     */   }
/*     */   
/*     */   public GrizzlyTCPConnector(@NotNull TCPMessageListener listener, @NotNull Properties properties) {
/*  76 */     this.listener = listener;
/*  77 */     this.isPortUnificationMode = true;
/*  78 */     this.properties = properties;
/*  79 */     this.port = -1;
/*     */   }
/*     */   
/*     */   public void listen() throws IOException {
/*  83 */     if (this.isPortUnificationMode) {
/*  84 */       listenOnUnifiedPort();
/*     */     } else {
/*  86 */       listenOnNewPort();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void listenOnNewPort() throws IOException {
/*     */     try {
/*  92 */       IncomeMessageProcessor.registerListener(this.port, this.listener, this.properties);
/*     */       
/*  94 */       this.selectorThread = new SelectorThread();
/*  95 */       this.selectorThread.setClassLoader(WSTCPStreamAlgorithm.class.getClassLoader());
/*  96 */       this.selectorThread.setAlgorithmClassName(WSTCPStreamAlgorithm.class.getName());
/*  97 */       this.selectorThread.setAddress(InetAddress.getByName(this.host));
/*  98 */       this.selectorThread.setPort(this.port);
/*  99 */       this.selectorThread.setBufferSize(4096);
/* 100 */       this.selectorThread.setMaxKeepAliveRequests(-1);
/* 101 */       this.selectorThread.initEndpoint();
/* 102 */       this.selectorThread.start();
/* 103 */     } catch (IOException e) {
/* 104 */       close();
/* 105 */       throw e;
/* 106 */     } catch (InstantiationException e) {
/* 107 */       close();
/* 108 */       throw new IOException(e.getClass().getName() + ": " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void listenOnUnifiedPort() {
/* 113 */     WSTCPProtocolHandler.setIncomingMessageProcessor(IncomeMessageProcessor.registerListener(0, this.listener, this.properties));
/*     */   }
/*     */   
/*     */   public void close() {
/* 117 */     if (this.selectorThread != null) {
/* 118 */       this.selectorThread.stopEndpoint();
/* 119 */       IncomeMessageProcessor.releaseListener(this.selectorThread.getPort());
/* 120 */       this.selectorThread = null;
/*     */     } 
/*     */   }
/*     */   public String getHost() {
/* 124 */     return this.host;
/*     */   }
/*     */   
/*     */   public void setHost(String host) {
/* 128 */     this.host = host;
/*     */   }
/*     */   
/*     */   public int getPort() {
/* 132 */     return this.port;
/*     */   }
/*     */   
/*     */   public void setPort(int port) {
/* 136 */     this.port = port;
/*     */   }
/*     */   
/*     */   public TCPMessageListener getListener() {
/* 140 */     return this.listener;
/*     */   }
/*     */   
/*     */   public void setListener(TCPMessageListener listener) {
/* 144 */     this.listener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFrameSize(int frameSize) {
/* 149 */     this.selectorThread.setBufferSize(frameSize);
/*     */   }
/*     */   
/*     */   public int getFrameSize() {
/* 153 */     return this.selectorThread.getBufferSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\grizzly\GrizzlyTCPConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */