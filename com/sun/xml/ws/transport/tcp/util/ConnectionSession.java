/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.io.Connection;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConnectionSession
/*     */   implements Connection
/*     */ {
/*     */   ChannelZeroContext channelZeroContext;
/*     */   private Connection connection;
/*     */   private boolean isClosed;
/*     */   private final SessionCloseListener sessionCloseListener;
/*     */   
/*     */   public abstract void registerChannel(@NotNull ChannelContext paramChannelContext);
/*     */   
/*     */   public abstract void deregisterChannel(@NotNull ChannelContext paramChannelContext);
/*     */   
/*     */   public abstract int getChannelsAmount();
/*     */   
/*     */   public ConnectionSession(Connection connection, SessionCloseListener sessionCloseListener) {
/*  68 */     this.connection = connection;
/*  69 */     this.sessionCloseListener = sessionCloseListener;
/*     */   }
/*     */   
/*     */   protected void init() {
/*  73 */     this.channelZeroContext = new ChannelZeroContext(this);
/*  74 */     registerChannel(this.channelZeroContext);
/*     */   }
/*     */   @Nullable
/*     */   public Object getAttribute(@NotNull String name) {
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public void setAttribute(@NotNull String name, @Nullable Object value) {}
/*     */   
/*     */   public void onReadCompleted() {}
/*     */   
/*     */   @Nullable
/*  86 */   public ChannelContext findWSServiceContextByURI(@NotNull WSTCPURI wsTCPURI) { return null; } @Nullable
/*     */   public ChannelContext findWSServiceContextByChannelId(int channelId) {
/*  88 */     return null;
/*     */   } @NotNull
/*     */   public ChannelContext getServiceChannelContext() {
/*  91 */     return this.channelZeroContext;
/*     */   }
/*     */   
/*     */   public void close() {
/*  95 */     if (this.sessionCloseListener != null) {
/*  96 */       this.sessionCloseListener.notifySessionClose(this);
/*     */     }
/*     */     
/*  99 */     synchronized (this) {
/* 100 */       if (this.isClosed)
/* 101 */         return;  this.isClosed = true;
/*     */     } 
/*     */     
/*     */     try {
/* 105 */       this.connection.close();
/* 106 */     } catch (IOException ex) {}
/*     */ 
/*     */     
/* 109 */     this.connection = null;
/*     */   }
/*     */   
/*     */   public Connection getConnection() {
/* 113 */     return this.connection;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ConnectionSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */