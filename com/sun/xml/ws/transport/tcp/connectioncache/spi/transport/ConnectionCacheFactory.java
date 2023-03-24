/*    */ package com.sun.xml.ws.transport.tcp.connectioncache.spi.transport;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.impl.transport.InboundConnectionCacheBlockingImpl;
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.impl.transport.OutboundConnectionCacheBlockingImpl;
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
/*    */ public final class ConnectionCacheFactory
/*    */ {
/*    */   public static <C extends Connection> OutboundConnectionCache<C> makeBlockingOutboundConnectionCache(String cacheType, int highWaterMark, int numberToReclaim, int maxParallelConnections, Logger logger) {
/* 62 */     return (OutboundConnectionCache<C>)new OutboundConnectionCacheBlockingImpl(cacheType, highWaterMark, numberToReclaim, maxParallelConnections, logger);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <C extends Connection> InboundConnectionCache<C> makeBlockingInboundConnectionCache(String cacheType, int highWaterMark, int numberToReclaim, Logger logger) {
/* 69 */     return (InboundConnectionCache<C>)new InboundConnectionCacheBlockingImpl(cacheType, highWaterMark, numberToReclaim, logger);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\transport\ConnectionCacheFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */