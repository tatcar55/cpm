/*    */ package com.sun.xml.ws.transport.tcp.connectioncache.impl.transport;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueueFactory;
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ abstract class ConnectionCacheBlockingBase<C extends Connection>
/*    */   extends ConnectionCacheBase<C>
/*    */ {
/*    */   protected int totalBusy;
/*    */   protected int totalIdle;
/*    */   
/*    */   ConnectionCacheBlockingBase(String cacheType, int highWaterMark, int numberToReclaim, Logger logger) {
/* 56 */     super(cacheType, highWaterMark, numberToReclaim, logger);
/*    */     
/* 58 */     this.totalBusy = 0;
/* 59 */     this.totalIdle = 0;
/*    */     
/* 61 */     this.reclaimableConnections = ConcurrentQueueFactory.makeConcurrentQueue();
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized long numberOfConnections() {
/* 66 */     return (this.totalIdle + this.totalBusy);
/*    */   }
/*    */   
/*    */   public synchronized long numberOfIdleConnections() {
/* 70 */     return this.totalIdle;
/*    */   }
/*    */   
/*    */   public synchronized long numberOfBusyConnections() {
/* 74 */     return this.totalBusy;
/*    */   }
/*    */   
/*    */   public synchronized long numberOfReclaimableConnections() {
/* 78 */     return this.reclaimableConnections.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\transport\ConnectionCacheBlockingBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */