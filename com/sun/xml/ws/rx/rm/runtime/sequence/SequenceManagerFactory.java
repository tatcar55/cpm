/*     */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.commons.WSEndpointCollectionBasedMOMListener;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*     */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.invm.InVmSequenceManager;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.persistent.PersistentSequenceManager;
/*     */ import com.sun.xml.ws.server.WSEndpointImpl;
/*     */ import com.sun.xml.ws.server.WSEndpointMOMProxy;
/*     */ import java.util.WeakHashMap;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
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
/*     */ public enum SequenceManagerFactory
/*     */ {
/*  62 */   INSTANCE;
/*     */ 
/*     */   
/*  65 */   private final WeakHashMap<WSEndpoint, SequenceManager> sequenceManagersForDeferredRegistration = new WeakHashMap<WSEndpoint, SequenceManager>();
/*     */   
/*     */   private final WSEndpointCollectionBasedMOMListener listener;
/*     */   
/*     */   SequenceManagerFactory() {
/*  70 */     this.listener = new WSEndpointCollectionBasedMOMListener(this, "RMSequenceManager", this.sequenceManagersForDeferredRegistration);
/*  71 */     this.listener.initialize();
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
/*     */   public SequenceManager createSequenceManager(boolean persistent, String uniqueEndpointId, DeliveryQueueBuilder inboundQueueBuilder, DeliveryQueueBuilder outboundQueueBuilder, RmConfiguration configuration) {
/*  86 */     synchronized (INSTANCE) {
/*     */       InVmSequenceManager inVmSequenceManager;
/*  88 */       if (persistent) {
/*  89 */         PersistentSequenceManager persistentSequenceManager = new PersistentSequenceManager(uniqueEndpointId, inboundQueueBuilder, outboundQueueBuilder, configuration);
/*     */       } else {
/*  91 */         inVmSequenceManager = new InVmSequenceManager(uniqueEndpointId, inboundQueueBuilder, outboundQueueBuilder, configuration);
/*     */       } 
/*     */       
/*  94 */       ManagedObjectManager mom = configuration.getManagedObjectManager();
/*  95 */       handleMOMRegistration((SequenceManager)inVmSequenceManager, mom, true);
/*     */       
/*  97 */       return (SequenceManager)inVmSequenceManager;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose(SequenceManager manager, RmConfiguration configuration) {
/* 107 */     synchronized (INSTANCE) {
/* 108 */       manager.dispose();
/*     */       
/* 110 */       ManagedObjectManager mom = configuration.getManagedObjectManager();
/* 111 */       handleMOMRegistration(manager, mom, false);
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
/*     */   private void handleMOMRegistration(SequenceManager manager, ManagedObjectManager managedObjectManager, boolean register) {
/* 123 */     if (manager == null || managedObjectManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     if (!this.listener.canRegisterAtMOM() && managedObjectManager instanceof WSEndpointMOMProxy) {
/*     */ 
/*     */       
/* 130 */       WSEndpointMOMProxy endpointMOMProxy = (WSEndpointMOMProxy)managedObjectManager;
/* 131 */       WSEndpointImpl wsEndpoint = endpointMOMProxy.getWsEndpoint();
/*     */       
/* 133 */       if (register) {
/* 134 */         this.sequenceManagersForDeferredRegistration.put(wsEndpoint, manager);
/*     */       } else {
/* 136 */         this.sequenceManagersForDeferredRegistration.remove(wsEndpoint);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 143 */     else if (register) {
/* 144 */       this.listener.registerAtMOM(manager, managedObjectManager);
/*     */     } else {
/* 146 */       this.listener.unregisterFromMOM(manager, managedObjectManager);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */