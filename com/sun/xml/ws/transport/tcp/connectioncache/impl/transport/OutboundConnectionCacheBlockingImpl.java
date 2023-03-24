/*     */ package com.sun.xml.ws.transport.tcp.connectioncache.impl.transport;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.concurrent.ConcurrentQueue;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.Connection;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ConnectionFinder;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.ContactInfo;
/*     */ import com.sun.xml.ws.transport.tcp.connectioncache.spi.transport.OutboundConnectionCache;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
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
/*     */ public final class OutboundConnectionCacheBlockingImpl<C extends Connection>
/*     */   extends ConnectionCacheBlockingBase<C>
/*     */   implements OutboundConnectionCache<C>
/*     */ {
/*     */   private final int maxParallelConnections;
/*     */   private Map<ContactInfo<C>, CacheEntry<C>> entryMap;
/*     */   private Map<C, ConnectionState<C>> connectionMap;
/*     */   
/*     */   public int maxParallelConnections() {
/*  74 */     return this.maxParallelConnections;
/*     */   }
/*     */   
/*     */   protected String thisClassName() {
/*  78 */     return "OutboundConnectionCacheBlockingImpl";
/*     */   }
/*     */ 
/*     */   
/*     */   private enum ConnectionStateValue
/*     */   {
/*  84 */     NEW, BUSY, IDLE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ConnectionState<C extends Connection>
/*     */   {
/*     */     OutboundConnectionCacheBlockingImpl.ConnectionStateValue csv;
/*     */ 
/*     */     
/*     */     final ContactInfo<C> cinfo;
/*     */ 
/*     */     
/*     */     final C connection;
/*     */ 
/*     */     
/*     */     final OutboundConnectionCacheBlockingImpl.CacheEntry<C> entry;
/*     */ 
/*     */     
/*     */     int busyCount;
/*     */ 
/*     */     
/*     */     int expectedResponseCount;
/*     */ 
/*     */     
/*     */     ConcurrentQueue.Handle<C> reclaimableHandle;
/*     */ 
/*     */ 
/*     */     
/*     */     ConnectionState(ContactInfo<C> cinfo, OutboundConnectionCacheBlockingImpl.CacheEntry<C> entry, C conn) {
/* 115 */       this.csv = OutboundConnectionCacheBlockingImpl.ConnectionStateValue.NEW;
/* 116 */       this.cinfo = cinfo;
/* 117 */       this.connection = conn;
/* 118 */       this.entry = entry;
/*     */       
/* 120 */       this.busyCount = 0;
/* 121 */       this.expectedResponseCount = 0;
/* 122 */       this.reclaimableHandle = null;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 126 */       return "ConnectionState[cinfo=" + this.cinfo + " connection=" + this.connection + " busyCount=" + this.busyCount + " expectedResponseCount=" + this.expectedResponseCount + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class CacheEntry<C extends Connection>
/*     */   {
/* 139 */     final Queue<C> idleConnections = new LinkedBlockingQueue<C>();
/* 140 */     final Collection<C> idleConnectionsView = Collections.unmodifiableCollection(this.idleConnections);
/*     */ 
/*     */     
/* 143 */     final Queue<C> busyConnections = new LinkedBlockingQueue<C>();
/* 144 */     final Collection<C> busyConnectionsView = Collections.unmodifiableCollection(this.busyConnections);
/*     */ 
/*     */     
/*     */     public int totalConnections() {
/* 148 */       return this.idleConnections.size() + this.busyConnections.size();
/*     */     }
/*     */ 
/*     */     
/*     */     private CacheEntry() {}
/*     */   }
/*     */   
/*     */   public OutboundConnectionCacheBlockingImpl(String cacheType, int highWaterMark, int numberToReclaim, int maxParallelConnections, Logger logger) {
/* 156 */     super(cacheType, highWaterMark, numberToReclaim, logger);
/*     */     
/* 158 */     if (maxParallelConnections < 1) {
/* 159 */       throw new IllegalArgumentException("maxParallelConnections must be > 0");
/*     */     }
/*     */     
/* 162 */     this.maxParallelConnections = maxParallelConnections;
/*     */     
/* 164 */     this.entryMap = new HashMap<ContactInfo<C>, CacheEntry<C>>();
/* 165 */     this.connectionMap = new HashMap<C, ConnectionState<C>>();
/*     */     
/* 167 */     if (debug()) {
/* 168 */       dprint(".constructor completed: " + cacheType);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canCreateNewConnection(ContactInfo<C> cinfo) {
/* 173 */     CacheEntry<C> entry = this.entryMap.get(cinfo);
/* 174 */     if (entry == null) {
/* 175 */       return true;
/*     */     }
/* 177 */     return internalCanCreateNewConnection(entry);
/*     */   }
/*     */   
/*     */   private boolean internalCanCreateNewConnection(CacheEntry<C> entry) {
/* 181 */     int totalConnectionsInEntry = entry.totalConnections();
/*     */     
/* 183 */     boolean createNewConnection = (totalConnectionsInEntry == 0 || (numberOfConnections() < highWaterMark() && totalConnectionsInEntry < this.maxParallelConnections));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     return createNewConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private CacheEntry<C> getEntry(ContactInfo<C> cinfo) throws IOException {
/* 194 */     if (debug()) {
/* 195 */       dprint("->getEntry: " + cinfo);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 200 */       CacheEntry<C> result = this.entryMap.get(cinfo);
/* 201 */       if (result == null) {
/* 202 */         if (debug()) {
/* 203 */           dprint(".getEntry: " + cinfo + " creating new CacheEntry");
/*     */         }
/*     */ 
/*     */         
/* 207 */         result = new CacheEntry<C>();
/* 208 */         this.entryMap.put(cinfo, result);
/*     */       }
/* 210 */       else if (debug()) {
/* 211 */         dprint(".getEntry: " + cinfo + " re-using existing CacheEntry");
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 216 */       return result;
/*     */     } finally {
/* 218 */       if (debug()) {
/* 219 */         dprint("<-getEntry: " + cinfo);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private C tryNewConnection(CacheEntry<C> entry, ContactInfo<C> cinfo) throws IOException {
/* 229 */     if (debug())
/* 230 */       dprint("->tryNewConnection: " + cinfo); 
/*     */     try {
/*     */       Connection connection;
/* 233 */       C conn = null;
/*     */       
/* 235 */       if (internalCanCreateNewConnection(entry)) {
/*     */ 
/*     */ 
/*     */         
/* 239 */         connection = cinfo.createConnection();
/*     */         
/* 241 */         if (debug()) {
/* 242 */           dprint(".tryNewConnection: " + cinfo + " created connection " + connection);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 247 */       return (C)connection;
/*     */     } finally {
/* 249 */       if (debug())
/* 250 */         dprint("<-tryNewConnection: " + cinfo); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decrementTotalIdle() {
/* 255 */     if (debug()) {
/* 256 */       dprint("->decrementTotalIdle: totalIdle = " + this.totalIdle);
/*     */     }
/*     */     
/*     */     try {
/* 260 */       if (this.totalIdle > 0) {
/* 261 */         this.totalIdle--;
/*     */       }
/* 263 */       else if (debug()) {
/* 264 */         dprint(".decrementTotalIdle: incorrect idle count: was already 0");
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 269 */       if (debug()) {
/* 270 */         dprint("<-decrementTotalIdle: totalIdle = " + this.totalIdle);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void decrementTotalBusy() {
/* 277 */     if (debug()) {
/* 278 */       dprint("->decrementTotalBusy: totalBusy = " + this.totalBusy);
/*     */     }
/*     */     
/*     */     try {
/* 282 */       if (this.totalBusy > 0) {
/* 283 */         this.totalBusy--;
/*     */       }
/* 285 */       else if (debug()) {
/* 286 */         dprint(".decrementTotalBusy: incorrect idle count: was already 0");
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 291 */       if (debug()) {
/* 292 */         dprint("<-decrementTotalBusy: totalBusy = " + this.totalBusy);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makeResultBusy(C result, ConnectionState<C> cs, CacheEntry<C> entry) {
/* 302 */     if (debug()) {
/* 303 */       dprint("->makeResultBusy: " + result + " was previously " + cs.csv);
/*     */     }
/*     */     try {
/*     */       ConcurrentQueue.Handle<C> handle;
/* 307 */       switch (cs.csv) {
/*     */         case NEW:
/* 309 */           this.totalBusy++;
/*     */           break;
/*     */         
/*     */         case IDLE:
/* 313 */           this.totalBusy++;
/* 314 */           decrementTotalIdle();
/*     */           
/* 316 */           handle = cs.reclaimableHandle;
/*     */ 
/*     */           
/* 319 */           if (handle != null) {
/* 320 */             if (!handle.remove() && 
/* 321 */               debug()) {
/* 322 */               dprint(".makeResultBusy: " + cs.cinfo + " result was not on reclaimable Q");
/*     */             }
/*     */ 
/*     */             
/* 326 */             cs.reclaimableHandle = null;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 335 */       entry.busyConnections.offer(result);
/* 336 */       cs.csv = ConnectionStateValue.BUSY;
/* 337 */       cs.busyCount++;
/*     */     } finally {
/* 339 */       if (debug())
/* 340 */         dprint("<-makeResultBusy: " + result); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private C tryIdleConnections(CacheEntry<C> entry) {
/* 345 */     if (debug()) {
/* 346 */       dprint("->tryIdleConnections");
/*     */     }
/*     */     
/*     */     try {
/* 350 */       return entry.idleConnections.poll();
/*     */     } finally {
/* 352 */       if (debug()) {
/* 353 */         dprint("<-tryIdleConnections");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private C tryBusyConnections(CacheEntry<C> entry) {
/* 362 */     if (debug()) {
/* 363 */       dprint("->tryBusyConnections");
/*     */     }
/*     */     
/*     */     try {
/* 367 */       Connection connection = (Connection)entry.busyConnections.poll();
/*     */       
/* 369 */       if (connection == null) {
/* 370 */         throw new RuntimeException("INTERNAL ERROR: no busy connection available");
/*     */       }
/*     */ 
/*     */       
/* 374 */       return (C)connection;
/*     */     } finally {
/* 376 */       if (debug()) {
/* 377 */         dprint("<-tryBusyConnections");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized C get(ContactInfo<C> cinfo) throws IOException {
/* 385 */     return get(cinfo, (ConnectionFinder<C>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ConnectionState<C> getConnectionState(ContactInfo<C> cinfo, CacheEntry<C> entry, C conn) {
/* 391 */     if (debug()) {
/* 392 */       dprint("->getConnectionState: " + conn);
/*     */     }
/*     */     try {
/* 395 */       ConnectionState<C> cs = this.connectionMap.get(conn);
/* 396 */       if (cs == null) {
/* 397 */         if (debug()) {
/* 398 */           dprint(".getConnectionState: " + conn + " creating new ConnectionState" + cs);
/*     */         }
/*     */         
/* 401 */         cs = new ConnectionState<C>(cinfo, entry, conn);
/* 402 */         this.connectionMap.put(conn, cs);
/*     */       }
/* 404 */       else if (debug()) {
/* 405 */         dprint(".getConnectionState: " + conn + " found ConnectionState" + cs);
/*     */       } 
/*     */ 
/*     */       
/* 409 */       return cs;
/*     */     } finally {
/* 411 */       if (debug()) {
/* 412 */         dprint("<-getConnectionState: " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized C get(ContactInfo<C> cinfo, ConnectionFinder<C> finder) throws IOException {
/* 419 */     if (debug()) {
/* 420 */       dprint("->get: " + cinfo);
/*     */     }
/*     */     
/* 423 */     ConnectionState<C> cs = null;
/*     */     try {
/*     */       Connection connection;
/* 426 */       CacheEntry<C> entry = getEntry(cinfo);
/* 427 */       C result = null;
/*     */       
/* 429 */       if (numberOfConnections() >= highWaterMark())
/*     */       {
/*     */ 
/*     */         
/* 433 */         reclaim();
/*     */       }
/*     */       
/* 436 */       if (finder != null) {
/*     */         
/* 438 */         if (debug()) {
/* 439 */           dprint(".get: " + cinfo + " Calling the finder to get a connection");
/*     */         }
/*     */ 
/*     */         
/* 443 */         connection = finder.find(cinfo, entry.idleConnectionsView, entry.busyConnectionsView);
/*     */ 
/*     */         
/* 446 */         if (connection != null) {
/* 447 */           cs = getConnectionState(cinfo, entry, (C)connection);
/*     */ 
/*     */           
/* 450 */           if (cs.csv == ConnectionStateValue.BUSY) {
/* 451 */             entry.busyConnections.remove(connection);
/* 452 */           } else if (cs.csv == ConnectionStateValue.IDLE) {
/* 453 */             entry.idleConnections.remove(connection);
/*     */           } 
/*     */         } 
/*     */       } 
/* 457 */       if (connection == null) {
/* 458 */         connection = (Connection)tryIdleConnections(entry);
/*     */       }
/*     */       
/* 461 */       if (connection == null) {
/* 462 */         connection = (Connection)tryNewConnection(entry, cinfo);
/*     */       }
/*     */       
/* 465 */       if (connection == null) {
/* 466 */         connection = (Connection)tryBusyConnections(entry);
/*     */       }
/*     */       
/* 469 */       if (cs == null) {
/* 470 */         cs = getConnectionState(cinfo, entry, (C)connection);
/*     */       }
/* 472 */       makeResultBusy((C)connection, cs, entry);
/* 473 */       return (C)connection;
/*     */     } finally {
/* 475 */       if (debug()) {
/* 476 */         dprint(".get " + cinfo + " totalIdle=" + this.totalIdle + " totalBusy=" + this.totalBusy);
/*     */ 
/*     */ 
/*     */         
/* 480 */         dprint("<-get " + cinfo + " ConnectionState=" + cs);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean reclaimOrClose(ConnectionState<C> cs, C conn) {
/* 488 */     if (debug()) {
/* 489 */       dprint("->reclaimOrClose: " + conn);
/*     */     }
/*     */     try {
/* 492 */       boolean isOverflow = (numberOfConnections() > highWaterMark());
/*     */ 
/*     */       
/* 495 */       if (isOverflow) {
/* 496 */         if (debug()) {
/* 497 */           dprint(".reclaimOrClose: closing overflow connection " + conn);
/*     */         }
/*     */ 
/*     */         
/* 501 */         close(conn);
/*     */       } else {
/* 503 */         if (debug()) {
/* 504 */           dprint(".reclaimOrClose: queuing reclaimable connection " + conn);
/*     */         }
/*     */ 
/*     */         
/* 508 */         cs.reclaimableHandle = this.reclaimableConnections.offer(conn);
/*     */       } 
/*     */ 
/*     */       
/* 512 */       return isOverflow;
/*     */     } finally {
/* 514 */       if (debug()) {
/* 515 */         dprint("<-reclaimOrClose: " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void release(C conn, int numResponsesExpected) {
/* 522 */     if (debug()) {
/* 523 */       dprint("->release: " + conn + " expecting " + numResponsesExpected + " responses");
/*     */     }
/*     */ 
/*     */     
/* 527 */     ConnectionState<C> cs = this.connectionMap.get(conn);
/*     */     
/*     */     try {
/* 530 */       if (cs == null) {
/* 531 */         if (debug()) {
/* 532 */           dprint(".release: " + conn + " was closed");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 537 */       cs.expectedResponseCount += numResponsesExpected;
/* 538 */       int numResp = cs.expectedResponseCount;
/* 539 */       int numBusy = --cs.busyCount;
/* 540 */       if (numBusy < 0) {
/* 541 */         if (debug()) {
/* 542 */           dprint(".release: " + conn + " numBusy=" + numBusy + " is < 0: error");
/*     */         }
/*     */ 
/*     */         
/* 546 */         cs.busyCount = 0;
/*     */         
/*     */         return;
/*     */       } 
/* 550 */       if (debug()) {
/* 551 */         dprint(".release: " + numResp + " responses expected");
/* 552 */         dprint(".release: " + numBusy + " busy count");
/*     */       } 
/*     */       
/* 555 */       if (numBusy == 0) {
/* 556 */         CacheEntry<C> entry = cs.entry;
/* 557 */         boolean wasOnBusy = entry.busyConnections.remove(conn);
/* 558 */         if (!wasOnBusy && 
/* 559 */           debug()) {
/* 560 */           dprint(".release: " + conn + " was NOT on busy queue, " + "but should have been");
/*     */         }
/*     */ 
/*     */         
/* 564 */         boolean connectionClosed = false;
/* 565 */         if (numResp == 0) {
/* 566 */           connectionClosed = reclaimOrClose(cs, conn);
/*     */         }
/*     */         
/* 569 */         decrementTotalBusy();
/*     */         
/* 571 */         if (!connectionClosed) {
/* 572 */           if (debug()) {
/* 573 */             dprint(".release: queuing idle connection " + conn);
/*     */           }
/*     */ 
/*     */           
/* 577 */           this.totalIdle++;
/* 578 */           entry.idleConnections.offer(conn);
/* 579 */           cs.csv = ConnectionStateValue.IDLE;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 584 */       if (debug()) {
/* 585 */         dprint(".release " + conn + " cs=" + cs + " totalIdle=" + this.totalIdle + " totalBusy=" + this.totalBusy);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 590 */         dprint("<-release" + conn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void responseReceived(C conn) {
/* 599 */     if (debug()) {
/* 600 */       dprint("->responseReceived: " + conn);
/*     */     }
/*     */     
/*     */     try {
/* 604 */       ConnectionState<C> cs = this.connectionMap.get(conn);
/* 605 */       if (cs == null) {
/* 606 */         if (debug()) {
/* 607 */           dprint(".responseReceived: received response on closed connection " + conn);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 616 */       int waitCount = --cs.expectedResponseCount;
/*     */       
/* 618 */       if (debug()) {
/* 619 */         dprint(".responseReceived: " + conn + " waitCount=" + waitCount);
/*     */       }
/*     */ 
/*     */       
/* 623 */       if (waitCount < 0) {
/* 624 */         if (debug()) {
/* 625 */           dprint(".responseReceived: " + conn + " incorrect call: error");
/*     */         }
/*     */         
/* 628 */         cs.expectedResponseCount = 0;
/*     */         
/*     */         return;
/*     */       } 
/* 632 */       if (waitCount == 0 && cs.busyCount == 0) {
/* 633 */         reclaimOrClose(cs, conn);
/*     */       }
/*     */     } finally {
/* 636 */       if (debug()) {
/* 637 */         dprint("<-responseReceived: " + conn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close(C conn) {
/* 646 */     if (debug()) {
/* 647 */       dprint("->close: " + conn);
/*     */     }
/*     */     
/*     */     try {
/* 651 */       ConnectionState<C> cs = this.connectionMap.remove(conn);
/* 652 */       if (cs == null) {
/* 653 */         if (debug()) {
/* 654 */           dprint(".close: " + conn + " was already closed");
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 660 */       if (debug()) {
/* 661 */         dprint(".close: " + conn + "Connection state=" + cs);
/*     */       }
/*     */ 
/*     */       
/* 665 */       ConcurrentQueue.Handle<C> rh = cs.reclaimableHandle;
/* 666 */       if (rh != null) {
/* 667 */         boolean result = rh.remove();
/* 668 */         if (debug()) {
/* 669 */           dprint(".close: " + conn + "reclaimableHandle .remove = " + result);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 674 */       if (cs.entry.busyConnections.remove(conn)) {
/* 675 */         if (debug()) {
/* 676 */           dprint(".close: " + conn + " removed from busyConnections");
/*     */         }
/*     */ 
/*     */         
/* 680 */         decrementTotalBusy();
/*     */       } 
/*     */       
/* 683 */       if (cs.entry.idleConnections.remove(conn)) {
/* 684 */         if (debug()) {
/* 685 */           dprint(".close: " + conn + " removed from idleConnections");
/*     */         }
/*     */ 
/*     */         
/* 689 */         decrementTotalIdle();
/*     */       } 
/*     */       
/*     */       try {
/* 693 */         conn.close();
/* 694 */       } catch (IOException exc) {
/* 695 */         if (debug()) {
/* 696 */           dprint(".close: " + conn + ": Caught IOException on close:" + exc);
/*     */         }
/*     */       } 
/*     */     } finally {
/* 700 */       if (debug()) {
/* 701 */         dprintStatistics();
/* 702 */         dprint("<-close: " + conn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\connectioncache\impl\transport\OutboundConnectionCacheBlockingImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */