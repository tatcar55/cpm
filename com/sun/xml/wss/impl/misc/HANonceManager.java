/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ import org.glassfish.ha.store.api.BackingStoreConfiguration;
/*     */ import org.glassfish.ha.store.api.BackingStoreException;
/*     */ import org.glassfish.ha.store.api.BackingStoreFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HANonceManager
/*     */   extends NonceManager
/*     */ {
/*     */   private Long maxNonceAge;
/*  70 */   private BackingStore<StickyKey, HAPojo> backingStore = null;
/*     */   private NonceCache localCache;
/*  72 */   private final ScheduledExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
/*     */   
/*     */   public HANonceManager(long maxNonceAge) {
/*  75 */     this.maxNonceAge = Long.valueOf(maxNonceAge);
/*     */     
/*     */     try {
/*  78 */       BackingStoreConfiguration<StickyKey, HAPojo> bsConfig = HighAvailabilityProvider.INSTANCE.initBackingStoreConfiguration("HANonceManagerStore", StickyKey.class, HAPojo.class);
/*     */ 
/*     */       
/*  81 */       bsConfig.getVendorSpecificSettings().put("max.idle.timeout.in.seconds", Long.valueOf(maxNonceAge / 1000L));
/*     */ 
/*     */ 
/*     */       
/*  85 */       bsConfig.getVendorSpecificSettings().put("start.gms", Boolean.valueOf(true));
/*  86 */       BackingStoreFactory bsFactory = HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY);
/*  87 */       this.backingStore = bsFactory.createBackingStore(bsConfig);
/*  88 */       this.localCache = new NonceCache(maxNonceAge);
/*  89 */       this.singleThreadScheduledExecutor.scheduleAtFixedRate(new nonceCleanupTask(), this.maxNonceAge.longValue(), this.maxNonceAge.longValue(), TimeUnit.MILLISECONDS);
/*  90 */     } catch (BackingStoreException ex) {
/*  91 */       LOGGER.log(Level.SEVERE, LogStringsMessages.WSS_0826_ERROR_INITIALIZE_BACKINGSTORE(), (Throwable)ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HANonceManager(BackingStore<StickyKey, HAPojo> backingStore, long maxNonceAge) {
/*  96 */     this.backingStore = backingStore;
/*  97 */     this.maxNonceAge = Long.valueOf(maxNonceAge);
/*  98 */     this.singleThreadScheduledExecutor.scheduleAtFixedRate(new nonceCleanupTask(), this.maxNonceAge.longValue(), this.maxNonceAge.longValue(), TimeUnit.MILLISECONDS);
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
/*     */   public boolean validateNonce(String nonce, String created) throws NonceManager.NonceException {
/* 113 */     boolean isnewNonce = this.localCache.validateAndCacheNonce(nonce, created);
/* 114 */     byte[] data = created.getBytes();
/* 115 */     HAPojo pojo = new HAPojo();
/* 116 */     pojo.setData(data);
/*     */     try {
/* 118 */       HAPojo value = null;
/*     */       try {
/* 120 */         value = (HAPojo)HighAvailabilityProvider.loadFrom(this.backingStore, (Serializable)new StickyKey(nonce), null);
/* 121 */       } catch (Exception ex) {
/* 122 */         LOGGER.log(Level.WARNING, " exception during load command ", ex);
/*     */       } 
/* 124 */       if (value != null) {
/* 125 */         String message = "Nonce Repeated : Nonce Cache already contains the nonce value :" + nonce;
/* 126 */         LOGGER.log(Level.WARNING, LogStringsMessages.WSS_0815_NONCE_REPEATED_ERROR(nonce));
/* 127 */         throw new NonceManager.NonceException(message);
/*     */       } 
/* 129 */       HaInfo haInfo = HaContext.currentHaInfo();
/* 130 */       if (haInfo != null) {
/* 131 */         HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)new StickyKey(nonce, haInfo.getKey()), pojo, true));
/*     */       } else {
/* 133 */         StickyKey stickyKey = new StickyKey(nonce);
/* 134 */         String replicaId = HighAvailabilityProvider.saveTo(this.backingStore, (Serializable)stickyKey, pojo, true);
/* 135 */         HaContext.updateHaInfo(new HaInfo(stickyKey.getHashKey(), replicaId, false));
/*     */       } 
/*     */       
/* 138 */       LOGGER.log(Level.INFO, " nonce {0} saved ", nonce);
/*     */     }
/* 140 */     catch (Exception ex) {
/* 141 */       LOGGER.log(Level.SEVERE, LogStringsMessages.WSS_0825_ERROR_VALIDATE_NONCE(), ex);
/* 142 */       return false;
/*     */     } 
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public class nonceCleanupTask
/*     */     implements Runnable
/*     */   {
/*     */     public void run() {
/*     */       try {
/* 152 */         HANonceManager.this.localCache.removeExpired();
/* 153 */         if (HANonceManager.this.backingStore.size() <= 0) {
/*     */           return;
/*     */         }
/* 156 */         int removed = HANonceManager.this.backingStore.removeExpired(HANonceManager.this.maxNonceAge.longValue());
/* 157 */         HANonceManager.LOGGER.log(Level.INFO, " removed {0} expired entries from backing store ", Integer.valueOf(removed));
/* 158 */       } catch (BackingStoreException ex) {
/* 159 */         HANonceManager.LOGGER.log(Level.SEVERE, LogStringsMessages.WSS_0827_ERROR_REMOVING_EXPIRED_ENTRIES(), (Throwable)ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(String key) throws BackingStoreException {
/* 165 */     this.backingStore.remove((Serializable)new StickyKey(key));
/*     */   }
/*     */   
/*     */   public static class HAPojo
/*     */     implements Serializable {
/*     */     byte[] data;
/*     */     
/*     */     public void setData(byte[] data) {
/* 173 */       this.data = data;
/*     */     }
/*     */     
/*     */     public byte[] getData() {
/* 177 */       return this.data;
/*     */     }
/*     */     public String toString() {
/* 180 */       if (this.data == null) {
/* 181 */         return "";
/*     */       }
/* 183 */       return new String(this.data);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\HANonceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */