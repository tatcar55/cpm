/*     */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*     */ 
/*     */ import com.sun.appserv.server.LifecycleEvent;
/*     */ import com.sun.appserv.server.LifecycleListener;
/*     */ import com.sun.appserv.server.ServerLifecycleException;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.transport.tcp.grizzly.GrizzlyTCPConnector;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPAdapter;
/*     */ import com.sun.xml.ws.transport.tcp.server.TCPMessageListener;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPConnector;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPDelegate;
/*     */ import com.sun.xml.ws.transport.tcp.server.WSTCPModule;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
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
/*     */ public final class WSTCPLifeCycleModule
/*     */   extends WSTCPModule
/*     */   implements LifecycleListener
/*     */ {
/*     */   private WSTCPConnector connector;
/*     */   private WSTCPDelegate delegate;
/*     */   private Properties properties;
/*     */   
/*     */   public void handleEvent(@NotNull LifecycleEvent lifecycleEvent) throws ServerLifecycleException {
/*  65 */     int eventType = lifecycleEvent.getEventType();
/*  66 */     if (eventType == 0) {
/*  67 */       WSTCPModule.setInstance(this);
/*  68 */       logger.log(Level.FINE, "WSTCPLifeCycleModule.INIT_EVENT");
/*  69 */       this.properties = (Properties)lifecycleEvent.getData();
/*  70 */     } else if (eventType == 1) {
/*  71 */       logger.log(Level.FINE, "WSTCPLifeCycleModule.STARTUP_EVENT");
/*  72 */       this.delegate = new WSTCPDelegate();
/*  73 */     } else if (eventType == 2) {
/*  74 */       logger.log(Level.FINE, "WSTCPLifeCycleModule.READY_EVENT");
/*     */       try {
/*  76 */         AppServWSRegistry.getInstance();
/*  77 */         this.delegate.setCustomWSRegistry(WSTCPAdapterRegistryImpl.getInstance());
/*  78 */         this.connector = (WSTCPConnector)new GrizzlyTCPConnector((TCPMessageListener)this.delegate, this.properties);
/*  79 */         this.connector.listen();
/*     */       
/*     */       }
/*  82 */       catch (Exception e) {
/*  83 */         logger.log(Level.SEVERE, e.getMessage(), e);
/*     */       } 
/*  85 */     } else if (eventType == 3) {
/*  86 */       logger.log(Level.FINE, "WSTCPLifeCycleModule.SHUTDOWN_EVENT");
/*  87 */       WSTCPModule.setInstance(null);
/*     */       
/*  89 */       if (this.delegate != null) {
/*  90 */         this.delegate.destroy();
/*     */       }
/*     */       
/*  93 */       if (this.connector != null) {
/*  94 */         this.connector.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(@NotNull String contextPath, @NotNull List<TCPAdapter> adapters) {
/* 102 */     this.delegate.registerAdapters(contextPath, adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public void free(@NotNull String contextPath, @NotNull List<TCPAdapter> adapters) {
/* 107 */     this.delegate.freeAdapters(contextPath, adapters);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 112 */     if (this.connector != null) {
/* 113 */       return this.connector.getPort();
/*     */     }
/*     */     
/* 116 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\WSTCPLifeCycleModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */