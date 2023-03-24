/*     */ package com.sun.xml.ws.transport.tcp.connectioncache.impl.transport;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueue;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ConnectionCache;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConnectionCacheBase<C extends Connection>
/*     */   implements ConnectionCache<C>
/*     */ {
/*     */   private final String cacheType;
/*     */   protected final Logger logger;
/*     */   private final int highWaterMark;
/*     */   private final int numberToReclaim;
/*  68 */   protected ConcurrentQueue<C> reclaimableConnections = null;
/*     */   
/*     */   protected boolean debug() {
/*  71 */     return this.logger.isLoggable(Level.FINER);
/*     */   }
/*     */   
/*     */   public final String getCacheType() {
/*  75 */     return this.cacheType;
/*     */   }
/*     */   
/*     */   public final int numberToReclaim() {
/*  79 */     return this.numberToReclaim;
/*     */   }
/*     */   
/*     */   public final int highWaterMark() {
/*  83 */     return this.highWaterMark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String thisClassName();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ConnectionCacheBase(String cacheType, int highWaterMark, int numberToReclaim, Logger logger) {
/*  95 */     if (cacheType == null) {
/*  96 */       throw new IllegalArgumentException("cacheType must not be null");
/*     */     }
/*  98 */     if (highWaterMark < 0) {
/*  99 */       throw new IllegalArgumentException("highWaterMark must be non-negative");
/*     */     }
/* 101 */     if (numberToReclaim < 1) {
/* 102 */       throw new IllegalArgumentException("numberToReclaim must be at least 1");
/*     */     }
/* 104 */     if (logger == null) {
/* 105 */       throw new IllegalArgumentException("logger must not be null");
/*     */     }
/* 107 */     this.cacheType = cacheType;
/* 108 */     this.logger = logger;
/* 109 */     this.highWaterMark = highWaterMark;
/* 110 */     this.numberToReclaim = numberToReclaim;
/*     */   }
/*     */   
/*     */   protected final void dprint(String msg) {
/* 114 */     this.logger.finer(thisClassName() + msg);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 118 */     return thisClassName() + "[" + getCacheType() + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public void dprintStatistics() {
/* 123 */     dprint(".stats: idle=" + numberOfIdleConnections() + " reclaimable=" + numberOfReclaimableConnections() + " busy=" + numberOfBusyConnections() + " total=" + numberOfConnections() + " (" + highWaterMark() + "/" + numberToReclaim() + ")");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean reclaim() {
/* 138 */     if (debug()) {
/* 139 */       dprint(".reclaim: starting");
/*     */     }
/* 141 */     int ctr = 0;
/* 142 */     while (ctr < numberToReclaim()) {
/* 143 */       Connection connection = (Connection)this.reclaimableConnections.poll();
/* 144 */       if (connection == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 149 */       if (debug()) {
/* 150 */         dprint(".reclaim: closing connection " + connection);
/*     */       }
/*     */       try {
/* 153 */         close(connection);
/* 154 */       } catch (RuntimeException exc) {
/* 155 */         if (debug())
/* 156 */           dprint(".reclaim: caught exception on close: " + exc); 
/* 157 */         throw exc;
/*     */       } 
/*     */       
/* 160 */       ctr++;
/*     */     } 
/*     */     
/* 163 */     if (debug()) {
/* 164 */       dprint(".reclaim: reclaimed " + ctr + " connection(s)");
/*     */     }
/* 166 */     return (ctr > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\transport\ConnectionCacheBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */