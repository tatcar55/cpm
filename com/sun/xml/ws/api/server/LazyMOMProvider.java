/*     */ package com.sun.xml.ws.api.server;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum LazyMOMProvider
/*     */ {
/* 106 */   INSTANCE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Scope scope;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<DefaultScopeChangeListener> listeners;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<WSEndpointScopeChangeListener> endpointsWaitingForMOM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LazyMOMProvider() {
/* 150 */     this.endpointsWaitingForMOM = new HashSet<WSEndpointScopeChangeListener>();
/* 151 */     this.listeners = new HashSet<DefaultScopeChangeListener>();
/*     */     
/* 153 */     this.scope = Scope.STANDALONE;
/*     */   }
/*     */   
/*     */   public static interface WSEndpointScopeChangeListener
/*     */     extends ScopeChangeListener {}
/*     */   
/*     */   public static interface DefaultScopeChangeListener
/*     */     extends ScopeChangeListener {}
/*     */   
/*     */   public void initMOMForScope(Scope scope) {
/* 163 */     if (this.scope == Scope.GLASSFISH_JMX || (scope == Scope.STANDALONE && (this.scope == Scope.GLASSFISH_JMX || this.scope == Scope.GLASSFISH_NO_JMX)) || this.scope == scope) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 169 */     this.scope = scope;
/*     */     
/* 171 */     fireScopeChanged();
/*     */   }
/*     */   
/*     */   public static interface ScopeChangeListener {
/*     */     void scopeChanged(LazyMOMProvider.Scope param1Scope); }
/*     */   
/*     */   private void fireScopeChanged() {
/* 178 */     for (ScopeChangeListener wsEndpoint : this.endpointsWaitingForMOM) {
/* 179 */       wsEndpoint.scopeChanged(this.scope);
/*     */     }
/*     */     
/* 182 */     for (ScopeChangeListener listener : this.listeners) {
/* 183 */       listener.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Scope
/*     */   {
/*     */     STANDALONE, GLASSFISH_NO_JMX, GLASSFISH_JMX;
/*     */   }
/*     */   
/*     */   public void registerListener(DefaultScopeChangeListener listener) {
/* 193 */     this.listeners.add(listener);
/*     */     
/* 195 */     if (!isProviderInDefaultScope()) {
/* 196 */       listener.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isProviderInDefaultScope() {
/* 207 */     return (this.scope == Scope.STANDALONE);
/*     */   }
/*     */   
/*     */   public Scope getScope() {
/* 211 */     return this.scope;
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
/*     */   public void registerEndpoint(WSEndpointScopeChangeListener wsEndpoint) {
/* 223 */     this.endpointsWaitingForMOM.add(wsEndpoint);
/*     */     
/* 225 */     if (!isProviderInDefaultScope()) {
/* 226 */       wsEndpoint.scopeChanged(this.scope);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unregisterEndpoint(WSEndpointScopeChangeListener wsEndpoint) {
/* 236 */     this.endpointsWaitingForMOM.remove(wsEndpoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\server\LazyMOMProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */