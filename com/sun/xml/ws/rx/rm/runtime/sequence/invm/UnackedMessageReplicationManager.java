/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence.invm;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ha.HaInfo;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.commons.ha.StickyKey;
/*     */ import com.sun.xml.ws.rx.ha.ReplicationManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.JaxwsApplicationMessage;
/*     */ import java.io.Serializable;
/*     */ import java.util.logging.Level;
/*     */ import org.glassfish.ha.store.api.BackingStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UnackedMessageReplicationManager
/*     */   implements ReplicationManager<String, ApplicationMessage>
/*     */ {
/*  56 */   private static final Logger LOGGER = Logger.getLogger(UnackedMessageReplicationManager.class);
/*     */   private BackingStore<StickyKey, JaxwsApplicationMessage.JaxwsApplicationMessageState> unackedMesagesBs;
/*     */   private final String loggerProlog;
/*     */   
/*     */   public UnackedMessageReplicationManager(String uniqueEndpointId) {
/*  61 */     this.loggerProlog = "[" + uniqueEndpointId + "_UNACKED_MESSAGES_MANAGER]: ";
/*  62 */     this.unackedMesagesBs = HighAvailabilityProvider.INSTANCE.createBackingStore(HighAvailabilityProvider.INSTANCE.getBackingStoreFactory(HighAvailabilityProvider.StoreType.IN_MEMORY), uniqueEndpointId + "_UNACKED_MESSAGES_BS", StickyKey.class, JaxwsApplicationMessage.JaxwsApplicationMessageState.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ApplicationMessage load(String key) {
/*  70 */     JaxwsApplicationMessage.JaxwsApplicationMessageState state = (JaxwsApplicationMessage.JaxwsApplicationMessageState)HighAvailabilityProvider.loadFrom(this.unackedMesagesBs, (Serializable)new StickyKey(key), null);
/*  71 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  72 */       LOGGER.finer(this.loggerProlog + "Message state loaded from unacked message backing store for key [" + key + "]: " + ((state == null) ? null : state.toString()));
/*     */     }
/*     */     
/*  75 */     JaxwsApplicationMessage message = null;
/*  76 */     if (state != null) {
/*  77 */       message = state.toMessage();
/*     */     }
/*     */     
/*  80 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  81 */       LOGGER.finer(this.loggerProlog + "Message state converted to a unacked message: " + ((message == null) ? null : message.toString()));
/*     */     }
/*  83 */     return (ApplicationMessage)message;
/*     */   }
/*     */   
/*     */   public void save(String key, ApplicationMessage _value, boolean isNew) {
/*  87 */     if (!(_value instanceof JaxwsApplicationMessage)) {
/*  88 */       throw new IllegalArgumentException("Unsupported application message type: " + _value.getClass().getName());
/*     */     }
/*  90 */     JaxwsApplicationMessage.JaxwsApplicationMessageState value = ((JaxwsApplicationMessage)_value).getState();
/*     */     
/*  92 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  93 */       LOGGER.finer(this.loggerProlog + "Sending for replication unacked message with a key [" + key + "]: " + value.toString() + ", isNew=" + isNew);
/*     */     }
/*     */     
/*  96 */     HaInfo haInfo = HaContext.currentHaInfo();
/*  97 */     if (haInfo != null) {
/*  98 */       if (LOGGER.isLoggable(Level.FINER)) {
/*  99 */         LOGGER.finer(this.loggerProlog + "Existing HaInfo found, using it for unacked message state replication: " + HaContext.asString(haInfo));
/*     */       }
/*     */       
/* 102 */       HaContext.udpateReplicaInstance(HighAvailabilityProvider.saveTo(this.unackedMesagesBs, (Serializable)new StickyKey(key, haInfo.getKey()), (Serializable)value, isNew));
/*     */     } else {
/* 104 */       StickyKey stickyKey = new StickyKey(key);
/* 105 */       String replicaId = HighAvailabilityProvider.saveTo(this.unackedMesagesBs, (Serializable)stickyKey, (Serializable)value, isNew);
/*     */       
/* 107 */       haInfo = new HaInfo(stickyKey.getHashKey(), replicaId, false);
/* 108 */       HaContext.updateHaInfo(haInfo);
/* 109 */       if (LOGGER.isLoggable(Level.FINER)) {
/* 110 */         LOGGER.finer(this.loggerProlog + "No HaInfo found, created new after unacked message state replication: " + HaContext.asString(haInfo));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(String key) {
/* 116 */     HighAvailabilityProvider.removeFrom(this.unackedMesagesBs, (Serializable)new StickyKey(key));
/* 117 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 118 */       LOGGER.finer(this.loggerProlog + "Removed unacked message from the backing store for key [" + key + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public void close() {
/* 123 */     HighAvailabilityProvider.close(this.unackedMesagesBs);
/* 124 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 125 */       LOGGER.finer(this.loggerProlog + "Closed unacked message backing store");
/*     */     }
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 130 */     HighAvailabilityProvider.destroy(this.unackedMesagesBs);
/* 131 */     if (LOGGER.isLoggable(Level.FINER))
/* 132 */       LOGGER.finer(this.loggerProlog + "Destroyed unacked message backing store"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\invm\UnackedMessageReplicationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */