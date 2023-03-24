/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import java.util.Timer;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.gmbal.AMXMetadata;
/*     */ import org.glassfish.gmbal.Description;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedObject
/*     */ @Description("per-endpoint NonceManager")
/*     */ @AMXMetadata(type = "WSNonceManager")
/*     */ public class DefaultNonceManager
/*     */   extends NonceManager
/*     */ {
/*     */   private static final boolean USE_DAEMON_THREAD = true;
/*  59 */   private static final Timer nonceCleanupTimer = new Timer(true);
/*     */ 
/*     */   
/*  62 */   private NonceCache nonceCache = null;
/*     */ 
/*     */   
/*  65 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ManagedAttribute
/*     */   private NonceCache getNonceCache() {
/*  73 */     return this.nonceCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validateNonce(String nonce, String created) throws NonceManager.NonceException {
/*  78 */     if (this.nonceCache == null || (this.nonceCache != null && this.nonceCache.wasCanceled())) {
/*  79 */       initNonceCache(getMaxNonceAge());
/*     */     }
/*     */     
/*  82 */     if (!this.nonceCache.isScheduled()) {
/*  83 */       if (log.isLoggable(Level.FINE)) {
/*  84 */         log.log(Level.FINE, "About to Store a new Nonce, but Reclaimer not Scheduled, so scheduling one" + this.nonceCache);
/*     */       }
/*     */       
/*  87 */       setNonceCacheCleanup();
/*     */     } 
/*  89 */     return this.nonceCache.validateAndCacheNonce(nonce, created);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void setNonceCacheCleanup() {
/*  94 */     if (!this.nonceCache.isScheduled()) {
/*  95 */       if (log.isLoggable(Level.FINE)) {
/*  96 */         log.log(Level.FINE, "Scheduling Nonce Reclaimer task...... for " + this + ":" + this.nonceCache);
/*     */       }
/*  98 */       nonceCleanupTimer.schedule(this.nonceCache, this.nonceCache.getMaxNonceAge(), this.nonceCache.getMaxNonceAge());
/*     */ 
/*     */ 
/*     */       
/* 102 */       this.nonceCache.scheduled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void initNonceCache(long maxNonceAge) {
/* 108 */     if (this.nonceCache == null) {
/* 109 */       this.nonceCache = new NonceCache(maxNonceAge);
/* 110 */       if (log.isLoggable(Level.FINE)) {
/* 111 */         log.log(Level.FINE, "Creating NonceCache for first time....." + this.nonceCache);
/*     */       }
/* 113 */     } else if (this.nonceCache.wasCanceled()) {
/* 114 */       this.nonceCache = new NonceCache(maxNonceAge);
/* 115 */       if (log.isLoggable(Level.FINE))
/* 116 */         log.log(Level.FINE, "Re-creating NonceCache because it was canceled....." + this.nonceCache); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\DefaultNonceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */