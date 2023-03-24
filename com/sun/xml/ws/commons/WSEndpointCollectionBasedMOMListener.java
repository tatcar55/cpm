/*     */ package com.sun.xml.ws.commons;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.LazyMOMProvider;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import java.util.Map;
/*     */ import org.glassfish.gmbal.ManagedObjectManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSEndpointCollectionBasedMOMListener<T extends MOMRegistrationAware>
/*     */   implements LazyMOMProvider.DefaultScopeChangeListener
/*     */ {
/*     */   private final Object lock;
/*     */   private final Map<WSEndpoint, T> registrationAwareMap;
/*     */   private final String registrationName;
/*  20 */   private LazyMOMProvider.Scope lazyMOMProviderScope = LazyMOMProvider.Scope.STANDALONE;
/*     */   
/*     */   public WSEndpointCollectionBasedMOMListener(String registrationName, Map<WSEndpoint, T> registrationAwareMap) {
/*  23 */     this(new Object(), registrationName, registrationAwareMap);
/*     */   }
/*     */   
/*     */   public WSEndpointCollectionBasedMOMListener(Object lock, String registrationName, Map<WSEndpoint, T> registrationAwareMap) {
/*  27 */     this.lock = lock;
/*  28 */     this.registrationName = registrationName;
/*  29 */     this.registrationAwareMap = registrationAwareMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize() {
/*  37 */     LazyMOMProvider.INSTANCE.registerListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRegisterAtMOM() {
/*  46 */     return (this.lazyMOMProviderScope != LazyMOMProvider.Scope.GLASSFISH_NO_JMX);
/*     */   }
/*     */   
/*     */   private void registerObjectsAtMOM() {
/*  50 */     synchronized (this.lock) {
/*  51 */       for (Map.Entry<WSEndpoint, T> entry : this.registrationAwareMap.entrySet()) {
/*  52 */         registerAtMOM((MOMRegistrationAware)entry.getValue(), entry.getKey());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerAtMOM(MOMRegistrationAware momRegistrationAware, WSEndpoint wsEndpoint) {
/*  58 */     registerAtMOM(momRegistrationAware, wsEndpoint.getManagedObjectManager());
/*     */   }
/*     */   
/*     */   public void registerAtMOM(MOMRegistrationAware momRegistrationAware, ManagedObjectManager managedObjectManager) {
/*  62 */     if (!momRegistrationAware.isRegisteredAtMOM()) {
/*  63 */       managedObjectManager.registerAtRoot(momRegistrationAware, this.registrationName);
/*  64 */       momRegistrationAware.setRegisteredAtMOM(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void scopeChanged(LazyMOMProvider.Scope scope) {
/*  69 */     synchronized (this.lock) {
/*  70 */       if (this.lazyMOMProviderScope == scope) {
/*     */         return;
/*     */       }
/*     */       
/*  74 */       this.lazyMOMProviderScope = scope;
/*     */     } 
/*     */     
/*  77 */     switch (scope) {
/*     */       case GLASSFISH_JMX:
/*  79 */         registerObjectsAtMOM();
/*     */         break;
/*     */       case GLASSFISH_NO_JMX:
/*  82 */         unregisterObjectsFromMOM();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unregisterObjectsFromMOM() {
/*  90 */     synchronized (this.lock) {
/*  91 */       for (Map.Entry<WSEndpoint, T> entry : this.registrationAwareMap.entrySet()) {
/*  92 */         if (((MOMRegistrationAware)entry.getValue()).isRegisteredAtMOM()) {
/*  93 */           unregisterFromMOM((MOMRegistrationAware)entry.getValue(), entry.getKey());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregisterFromMOM(MOMRegistrationAware momRegistrationAware, ManagedObjectManager managedObjectManager) {
/* 100 */     managedObjectManager.unregister(momRegistrationAware);
/* 101 */     momRegistrationAware.setRegisteredAtMOM(false);
/*     */   }
/*     */   
/*     */   public void unregisterFromMOM(MOMRegistrationAware momRegistrationAware, WSEndpoint wsEndpoint) {
/* 105 */     registerAtMOM(momRegistrationAware, wsEndpoint.getManagedObjectManager());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\WSEndpointCollectionBasedMOMListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */