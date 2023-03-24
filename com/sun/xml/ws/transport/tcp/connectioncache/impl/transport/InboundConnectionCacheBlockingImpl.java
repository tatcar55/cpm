/*     */ package com.sun.xml.ws.transport.tcp.connectioncache.impl.transport;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueue;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.InboundConnectionCache;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public final class InboundConnectionCacheBlockingImpl<C extends Connection>
/*     */   extends ConnectionCacheBlockingBase<C>
/*     */   implements InboundConnectionCache<C>
/*     */ {
/*     */   private final Map<C, ConnectionState<C>> connectionMap;
/*     */   
/*     */   protected String thisClassName() {
/*  64 */     return "InboundConnectionCacheBlockingImpl";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ConnectionState<C extends Connection>
/*     */   {
/*     */     final C connection;
/*     */     
/*     */     int busyCount;
/*     */     
/*     */     int expectedResponseCount;
/*     */     
/*     */     ConcurrentQueue.Handle<C> reclaimableHandle;
/*     */ 
/*     */     
/*     */     ConnectionState(C conn) {
/*  81 */       this.connection = conn;
/*     */       
/*  83 */       this.busyCount = 0;
/*  84 */       this.expectedResponseCount = 0;
/*  85 */       this.reclaimableHandle = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InboundConnectionCacheBlockingImpl(String cacheType, int highWaterMark, int numberToReclaim, Logger logger) {
/*  93 */     super(cacheType, highWaterMark, numberToReclaim, logger);
/*     */     
/*  95 */     this.connectionMap = new HashMap<C, ConnectionState<C>>();
/*     */     
/*  97 */     if (debug()) {
/*  98 */       dprint(".constructor completed: " + getCacheType());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void requestReceived(C conn) {
/* 105 */     if (debug()) {
/* 106 */       dprint("->requestReceived: connection " + conn);
/*     */     }
/*     */     try {
/* 109 */       ConnectionState<C> cs = getConnectionState(conn);
/*     */       
/* 111 */       int totalConnections = this.totalBusy + this.totalIdle;
/* 112 */       if (totalConnections > highWaterMark()) {
/* 113 */         reclaim();
/*     */       }
/* 115 */       ConcurrentQueue.Handle<C> reclaimHandle = cs.reclaimableHandle;
/* 116 */       if (reclaimHandle != null) {
/* 117 */         if (debug()) {
/* 118 */           dprint(".requestReceived: " + conn + " removed from reclaimableQueue");
/*     */         }
/* 120 */         reclaimHandle.remove();
/*     */       } 
/*     */       
/* 123 */       int count = cs.busyCount++;
/* 124 */       if (count == 0) {
/* 125 */         if (debug()) {
/* 126 */           dprint(".requestReceived: " + conn + " transition from idle to busy");
/*     */         }
/*     */         
/* 129 */         this.totalIdle--;
/* 130 */         this.totalBusy++;
/*     */       } 
/*     */     } finally {
/* 133 */       if (debug()) {
/* 134 */         dprint("<-requestReceived: connection " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void requestProcessed(C conn, int numResponsesExpected) {
/* 141 */     if (debug()) {
/* 142 */       dprint("->requestProcessed: connection " + conn + " expecting " + numResponsesExpected + " responses");
/*     */     }
/*     */     
/*     */     try {
/* 146 */       ConnectionState<C> cs = this.connectionMap.get(conn);
/*     */       
/* 148 */       if (cs == null) {
/* 149 */         if (debug()) {
/* 150 */           dprint(".release: connection " + conn + " was closed");
/*     */         }
/*     */         return;
/*     */       } 
/* 154 */       cs.expectedResponseCount += numResponsesExpected;
/* 155 */       int numResp = cs.expectedResponseCount;
/* 156 */       int numBusy = --cs.busyCount;
/*     */       
/* 158 */       if (debug()) {
/* 159 */         dprint(".release: " + numResp + " responses expected");
/* 160 */         dprint(".release: " + numBusy + " busy count for connection");
/*     */       } 
/*     */ 
/*     */       
/* 164 */       if (numBusy == 0) {
/* 165 */         this.totalBusy--;
/* 166 */         this.totalIdle++;
/*     */         
/* 168 */         if (numResp == 0) {
/* 169 */           if (debug()) {
/* 170 */             dprint(".release: queuing reclaimable connection " + conn);
/*     */           }
/*     */ 
/*     */           
/* 174 */           if (this.totalBusy + this.totalIdle > highWaterMark()) {
/* 175 */             close(conn);
/*     */           } else {
/* 177 */             cs.reclaimableHandle = this.reclaimableConnections.offer(conn);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 184 */       if (debug()) {
/* 185 */         dprint("<-requestProcessed");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void responseSent(C conn) {
/* 193 */     if (debug()) {
/* 194 */       dprint("->responseSent: " + conn);
/*     */     }
/*     */     try {
/* 197 */       ConnectionState<C> cs = this.connectionMap.get(conn);
/* 198 */       int waitCount = --cs.expectedResponseCount;
/* 199 */       if (waitCount == 0) {
/* 200 */         if (debug()) {
/* 201 */           dprint(".responseSent: " + conn + " is now reclaimable");
/*     */         }
/* 203 */         if (this.totalBusy + this.totalIdle > highWaterMark()) {
/* 204 */           if (debug()) {
/* 205 */             dprint(".responseSent: " + conn + " closing connection");
/*     */           }
/*     */ 
/*     */           
/* 209 */           close(conn);
/*     */         } else {
/* 211 */           cs.reclaimableHandle = this.reclaimableConnections.offer(conn);
/*     */ 
/*     */           
/* 214 */           if (debug()) {
/* 215 */             dprint(".responseSent: " + conn + " is now reclaimable");
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 220 */       else if (debug()) {
/* 221 */         dprint(".responseSent: " + conn + " waitCount=" + waitCount);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 226 */       if (debug()) {
/* 227 */         dprint("<-responseSent: " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close(C conn) {
/* 236 */     if (debug()) {
/* 237 */       dprint("->close: " + conn);
/*     */     }
/*     */     try {
/* 240 */       ConnectionState<C> cs = this.connectionMap.remove(conn);
/*     */       
/* 242 */       if (cs == null)
/*     */         return; 
/* 244 */       int count = cs.busyCount;
/* 245 */       if (debug()) {
/* 246 */         dprint(".close: " + conn + " count = " + count);
/*     */       }
/* 248 */       if (count == 0) {
/* 249 */         this.totalIdle--;
/*     */       } else {
/* 251 */         this.totalBusy--;
/*     */       } 
/* 253 */       ConcurrentQueue.Handle<C> rh = cs.reclaimableHandle;
/* 254 */       if (rh != null) {
/* 255 */         if (debug()) {
/* 256 */           dprint(".close: " + conn + " connection was reclaimable");
/*     */         }
/* 258 */         rh.remove();
/*     */       } 
/*     */       
/*     */       try {
/* 262 */         conn.close();
/* 263 */       } catch (IOException exc) {
/* 264 */         if (debug())
/* 265 */           dprint(".close: " + conn + " close threw " + exc); 
/*     */       } 
/*     */     } finally {
/* 268 */       if (debug()) {
/* 269 */         dprint("<-close: " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ConnectionState<C> getConnectionState(C conn) {
/* 277 */     if (debug()) {
/* 278 */       dprint("->getConnectionState: " + conn);
/*     */     }
/*     */     try {
/* 281 */       ConnectionState<C> result = this.connectionMap.get(conn);
/* 282 */       if (result == null) {
/* 283 */         if (debug()) {
/* 284 */           dprint(".getConnectionState: " + conn + " creating new ConnectionState instance");
/*     */         }
/* 286 */         result = new ConnectionState<C>(conn);
/* 287 */         this.connectionMap.put(conn, result);
/* 288 */         this.totalIdle++;
/*     */       } 
/*     */       
/* 291 */       return result;
/*     */     } finally {
/* 293 */       if (debug())
/* 294 */         dprint("<-getConnectionState: " + conn); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\transport\InboundConnectionCacheBlockingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */