/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TimerTask;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.gmbal.ManagedAttribute;
/*     */ import org.glassfish.gmbal.ManagedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ManagedData
/*     */ public class NonceCache
/*     */   extends TimerTask
/*     */ {
/*  69 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private Map<String, String> nonceCache = Collections.synchronizedMap(new HashMap<String, String>());
/*  75 */   private Map<String, String> oldNonceCache = Collections.synchronizedMap(new HashMap<String, String>());
/*     */   
/*     */   @ManagedAttribute
/*     */   private Map<String, String> getNonceCache() {
/*  79 */     return this.nonceCache;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   private Map<String, String> getOldNonceCache() {
/*  84 */     return this.oldNonceCache;
/*     */   }
/*     */   
/*  87 */   private long MAX_NONCE_AGE = 900000L;
/*     */   
/*     */   private boolean scheduledFlag = false;
/*     */   
/*     */   private boolean canceledFlag = false;
/*     */   
/*     */   public NonceCache() {}
/*     */   
/*     */   public NonceCache(long maxNonceAge) {
/*  96 */     this.MAX_NONCE_AGE = maxNonceAge;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean validateAndCacheNonce(String nonce, String created) throws NonceManager.NonceException {
/* 101 */     if (this.nonceCache.containsKey(nonce) || this.oldNonceCache.containsKey(nonce)) {
/* 102 */       log.log(Level.WARNING, LogStringsMessages.WSS_0815_NONCE_REPEATED_ERROR(nonce));
/* 103 */       throw new NonceManager.NonceException(LogStringsMessages.WSS_0815_NONCE_REPEATED_ERROR(nonce));
/*     */     } 
/*     */     
/* 106 */     if (log.isLoggable(Level.FINE)) {
/* 107 */       log.log(Level.FINE, "Storing Nonce Value {0} into {1}", new Object[] { nonce, this });
/*     */     }
/*     */     
/* 110 */     this.nonceCache.put(nonce, created);
/* 111 */     return true;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean isScheduled() {
/* 116 */     return this.scheduledFlag;
/*     */   }
/*     */   
/*     */   public void scheduled(boolean flag) {
/* 120 */     this.scheduledFlag = flag;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public boolean wasCanceled() {
/* 125 */     return this.canceledFlag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/* 130 */     if (this.nonceCache.isEmpty()) {
/* 131 */       cancel();
/* 132 */       if (log.isLoggable(Level.FINE)) {
/* 133 */         log.log(Level.FINE, "Canceled Timer Task due to inactivity ...for {0}", this);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 138 */     removeExpired();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cancel() {
/* 143 */     boolean ret = super.cancel();
/* 144 */     this.canceledFlag = true;
/* 145 */     this.oldNonceCache.clear();
/* 146 */     this.nonceCache.clear();
/*     */     
/* 148 */     return ret;
/*     */   }
/*     */   
/*     */   @ManagedAttribute
/*     */   public long getMaxNonceAge() {
/* 153 */     return this.MAX_NONCE_AGE;
/*     */   }
/*     */   
/*     */   public void removeExpired() {
/* 157 */     if (log.isLoggable(Level.FINE)) {
/* 158 */       log.log(Level.FINE, "Clearing old Nonce values...for {0}", this);
/*     */     }
/*     */     
/* 161 */     this.oldNonceCache.clear();
/* 162 */     Map<String, String> temp = this.nonceCache;
/* 163 */     this.nonceCache = this.oldNonceCache;
/* 164 */     this.oldNonceCache = temp;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\NonceCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */