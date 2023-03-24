/*    */ package com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.impl.concurrent.ConcurrentQueueBlockingImpl;
/*    */ import com.sun.xml.ws.transport.tcp.connectioncache.impl.concurrent.ConcurrentQueueImpl;
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
/*    */ 
/*    */ public final class ConcurrentQueueFactory
/*    */ {
/*    */   public static <V> ConcurrentQueue<V> makeBlockingConcurrentQueue() {
/* 62 */     return (ConcurrentQueue<V>)new ConcurrentQueueBlockingImpl();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> ConcurrentQueue<V> makeConcurrentQueue() {
/* 69 */     return (ConcurrentQueue<V>)new ConcurrentQueueImpl();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\spi\concurrent\ConcurrentQueueFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */