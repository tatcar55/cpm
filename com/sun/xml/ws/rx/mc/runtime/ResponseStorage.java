/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.ws.rx.ha.HighlyAvailableMap;
/*     */ import com.sun.xml.ws.rx.ha.ReplicationManager;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.message.jaxws.JaxwsMessage;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import org.glassfish.ha.store.api.BackingStore;
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
/*     */ final class ResponseStorage
/*     */ {
/*  61 */   private static final Logger LOGGER = Logger.getLogger(ResponseStorage.class);
/*     */   
/*     */   private static final class PendingMessageDataReplicationManager
/*     */     implements ReplicationManager<String, JaxwsMessage> {
/*     */     private final BackingStore<StickyKey, JaxwsMessage.JaxwsMessageState> messageStateStore;
/*     */     private final String loggerProlog;
/*     */     
/*     */     public PendingMessageDataReplicationManager(String endpointUid) {
/*  69 */       this.messageStateStore = HighAvailabilityProvider.INSTANCE.createBackingStore(HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY), endpointUid + "_MC_PENDING_MESSAGE_DATA_STORE", StickyKey.class, JaxwsMessage.JaxwsMessageState.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       this.loggerProlog = "[MC message data manager endpointUid: " + endpointUid + "]: ";
/*     */       
/*  77 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/*  78 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Created pending message backing store");
/*     */       }
/*     */     }
/*     */     
/*     */     public JaxwsMessage load(String key) {
/*  83 */       JaxwsMessage.JaxwsMessageState state = (JaxwsMessage.JaxwsMessageState)HighAvailabilityProvider.loadFrom(this.messageStateStore, (Serializable)new StickyKey(key), null);
/*     */       
/*  85 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/*  86 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Message state loaded from pending message backing store for key [" + key + "]: " + ((state == null) ? null : state.toString()));
/*     */       }
/*     */       
/*  89 */       JaxwsMessage message = state.toMessage();
/*     */       
/*  91 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/*  92 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Message state converted to a pending message: " + ((message == null) ? null : message.toString()));
/*     */       }
/*  94 */       return message;
/*     */     }
/*     */     
/*     */     public void save(String key, JaxwsMessage value, boolean isNew) {
/*  98 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/*  99 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Sending for replication pending message with a key [" + key + "]: " + value.toString() + ", isNew=" + isNew);
/*     */       }
/*     */       
/* 102 */       JaxwsMessage.JaxwsMessageState state = value.getState();
/* 103 */       HaInfo haInfo = HaContext.currentHaInfo();
/* 104 */       if (haInfo != null) {
/* 105 */         if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/* 106 */           ResponseStorage.LOGGER.finer(this.loggerProlog + "Existing HaInfo found, using it for pending message state replication: " + HaContext.asString(haInfo));
/*     */         }
/*     */         
/* 109 */         HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.messageStateStore, (Serializable)new StickyKey(key, haInfo.getKey()), (Serializable)state, isNew));
/*     */       } else {
/* 111 */         StickyKey stickyKey = new StickyKey(key);
/* 112 */         String replicaId = HighAvailabilityProvider.saveTo(this.messageStateStore, (Serializable)stickyKey, (Serializable)state, isNew);
/*     */         
/* 114 */         haInfo = new HaInfo(stickyKey.getHashKey(), replicaId, false);
/* 115 */         HaContext.updateHaInfo(haInfo);
/* 116 */         if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/* 117 */           ResponseStorage.LOGGER.finer(this.loggerProlog + "No HaInfo found, created new after pending message state replication: " + HaContext.asString(haInfo));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove(String key) {
/* 123 */       HighAvailabilityProvider.removeFrom(this.messageStateStore, (Serializable)new StickyKey(key));
/* 124 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/* 125 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Removed pending message from the backing store for key [" + key + "]");
/*     */       }
/*     */     }
/*     */     
/*     */     public void close() {
/* 130 */       HighAvailabilityProvider.close(this.messageStateStore);
/* 131 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/* 132 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Closed pending message backing store");
/*     */       }
/*     */     }
/*     */     
/*     */     public void destroy() {
/* 137 */       HighAvailabilityProvider.destroy(this.messageStateStore);
/* 138 */       if (ResponseStorage.LOGGER.isLoggable(Level.FINER)) {
/* 139 */         ResponseStorage.LOGGER.finer(this.loggerProlog + "Destroyed pending message backing store");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 144 */   private final ReentrantReadWriteLock storageLock = new ReentrantReadWriteLock();
/*     */   
/*     */   private final HighlyAvailableMap<String, JaxwsMessage> pendingResponses;
/*     */   
/*     */   private final HighlyAvailableMap<String, PendingResponseIdentifiers> pendingResponseIdentifiers;
/*     */   private final String endpointUid;
/*     */   
/*     */   public ResponseStorage(String endpointUid) {
/* 152 */     HighlyAvailableMap.StickyReplicationManager<String, PendingResponseIdentifiers> responseIdentifiersManager = null;
/* 153 */     PendingMessageDataReplicationManager responseManager = null;
/* 154 */     if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured()) {
/* 155 */       BackingStoreFactory bsf = HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY);
/*     */       
/* 157 */       responseIdentifiersManager = new HighlyAvailableMap.StickyReplicationManager(endpointUid + "_MC_PENDING_MESSAGE_IDENTIFIERS_MAP_MANAGER", HighAvailabilityProvider.INSTANCE.createBackingStore(bsf, endpointUid + "_MC_PENDING_MESSAGE_IDENTIFIERS_STORE", StickyKey.class, PendingResponseIdentifiers.class));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 165 */       responseManager = new PendingMessageDataReplicationManager(endpointUid);
/*     */     } 
/* 167 */     this.pendingResponseIdentifiers = HighlyAvailableMap.create(endpointUid + "_MC_PENDING_MESSAGE_IDENTIFIERS_MAP", (ReplicationManager)responseIdentifiersManager);
/* 168 */     this.pendingResponses = HighlyAvailableMap.create(endpointUid + "_MC_PENDING_MESSAGE_DATA_MAP", responseManager);
/* 169 */     this.endpointUid = endpointUid;
/*     */     
/* 171 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 172 */       LOGGER.finer("[WSMC-HA] endpoint UID [" + endpointUid + "]: Response storage initialized");
/*     */     }
/*     */   }
/*     */   
/*     */   void store(@NotNull JaxwsMessage response, @NotNull String clientUID) {
/*     */     try {
/* 178 */       this.storageLock.writeLock().lock();
/*     */       
/* 180 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 181 */         LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Storing new response for client UID: " + clientUID);
/*     */       }
/*     */       
/* 184 */       this.pendingResponses.put(response.getCorrelationId(), response);
/*     */       
/* 186 */       PendingResponseIdentifiers clientResponses = (PendingResponseIdentifiers)this.pendingResponseIdentifiers.get(clientUID);
/* 187 */       if (clientResponses == null) {
/* 188 */         clientResponses = new PendingResponseIdentifiers();
/*     */       }
/* 190 */       if (!clientResponses.offer(response.getCorrelationId())) {
/* 191 */         LOGGER.severe(LocalizationMessages.WSMC_0104_ERROR_STORING_RESPONSE(clientUID));
/*     */       }
/* 193 */       this.pendingResponseIdentifiers.put(clientUID, clientResponses);
/*     */     } finally {
/* 195 */       this.storageLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public JaxwsMessage getPendingResponse(@NotNull String clientUID) {
/*     */     try {
/* 201 */       this.storageLock.writeLock().lock();
/* 202 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 203 */         LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Retrieving stored pending response for client UID: " + clientUID);
/*     */       }
/*     */       
/* 206 */       PendingResponseIdentifiers clientResponses = (PendingResponseIdentifiers)this.pendingResponseIdentifiers.get(clientUID);
/* 207 */       if (clientResponses != null && !clientResponses.isEmpty()) {
/* 208 */         String messageId = clientResponses.poll();
/* 209 */         if (LOGGER.isLoggable(Level.FINER)) {
/* 210 */           LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Found registered pending response with message id [" + messageId + "] for client UID: " + clientUID);
/*     */         }
/* 212 */         this.pendingResponseIdentifiers.put(clientUID, clientResponses);
/*     */         
/* 214 */         JaxwsMessage response = (JaxwsMessage)this.pendingResponses.remove(messageId);
/* 215 */         if (LOGGER.isLoggable(Level.FINER)) {
/* 216 */           LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Retrieved and removed pending response message data for message id [" + messageId + "]: " + ((response == null) ? null : response.toString()));
/*     */         }
/* 218 */         if (response == null) {
/* 219 */           LOGGER.warning("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: No penidng response message data found for message id [" + messageId + "]");
/*     */         }
/*     */         
/* 222 */         return response;
/*     */       } 
/* 224 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 225 */         LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: No pedning responses found for client UID: " + clientUID);
/*     */       }
/* 227 */       return null;
/*     */     } finally {
/* 229 */       this.storageLock.writeLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasPendingResponse(@NotNull String clientUID) {
/*     */     try {
/* 235 */       this.storageLock.readLock().lock();
/* 236 */       PendingResponseIdentifiers clientResponses = (PendingResponseIdentifiers)this.pendingResponseIdentifiers.get(clientUID);
/* 237 */       boolean result = (clientResponses != null && !clientResponses.isEmpty());
/*     */       
/* 239 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 240 */         LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Pending responses avaliable for client UID [" + clientUID + "]: " + result);
/*     */       }
/*     */       
/* 243 */       return result;
/*     */     } finally {
/* 245 */       this.storageLock.readLock().unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   void invalidateLocalCache() {
/* 250 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 251 */       LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Invalidation local caches for the response storage");
/*     */     }
/* 253 */     this.pendingResponseIdentifiers.invalidateCache();
/* 254 */     this.pendingResponses.invalidateCache();
/*     */   }
/*     */   
/*     */   void dispose() {
/* 258 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 259 */       LOGGER.finer("[WSMC-HA] endpoint UID [" + this.endpointUid + "]: Disposing the response storage");
/*     */     }
/*     */     
/* 262 */     this.pendingResponseIdentifiers.close();
/* 263 */     this.pendingResponseIdentifiers.destroy();
/*     */     
/* 265 */     this.pendingResponses.close();
/* 266 */     this.pendingResponses.destroy();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\ResponseStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */