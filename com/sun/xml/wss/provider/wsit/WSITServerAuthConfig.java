/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.config.ServerAuthConfig;
/*     */ import javax.security.auth.message.config.ServerAuthContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSITServerAuthConfig
/*     */   implements ServerAuthConfig
/*     */ {
/*  68 */   private String layer = null;
/*  69 */   private String appContext = null;
/*  70 */   private CallbackHandler callbackHandler = null;
/*  71 */   private WSITServerAuthContext serverAuthContext = null;
/*  72 */   private PolicyMap policyMap = null;
/*     */   
/*     */   private boolean secEnabled;
/*     */   private ReentrantReadWriteLock rwLock;
/*     */   private ReentrantReadWriteLock.ReadLock rLock;
/*     */   private ReentrantReadWriteLock.WriteLock wLock;
/*     */   
/*     */   public WSITServerAuthConfig(String layer, String appContext, CallbackHandler callbackHandler) {
/*  80 */     this.layer = layer;
/*  81 */     this.appContext = appContext;
/*  82 */     this.callbackHandler = callbackHandler;
/*  83 */     this.rwLock = new ReentrantReadWriteLock(true);
/*  84 */     this.rLock = this.rwLock.readLock();
/*  85 */     this.wLock = this.rwLock.writeLock();
/*     */   }
/*     */   
/*     */   public ServerAuthContext getAuthContext(String operation, Subject subject, Map<Object, Object> rawMap) throws AuthException {
/*  89 */     Map<Object, Object> map = rawMap;
/*  90 */     PolicyMap pMap = (PolicyMap)map.get("POLICY");
/*  91 */     WSDLPort port = (WSDLPort)map.get("WSDL_MODEL");
/*  92 */     if (pMap == null || pMap.isEmpty())
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 100 */       this.rLock.lock();
/* 101 */       if (!this.secEnabled || this.policyMap != pMap) {
/* 102 */         this.rLock.unlock();
/* 103 */         this.wLock.lock();
/*     */         try {
/* 105 */           if (!this.secEnabled || this.policyMap != pMap) {
/* 106 */             if (!WSITAuthConfigProvider.isSecurityEnabled(pMap, port)) {
/* 107 */               return null;
/*     */             }
/* 109 */             this.secEnabled = true;
/*     */           } 
/*     */         } finally {
/* 112 */           this.rLock.lock();
/* 113 */           this.wLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 117 */       this.rLock.unlock();
/*     */     } 
/*     */     
/* 120 */     this.rLock.lock();
/*     */     try {
/* 122 */       if (this.serverAuthContext != null)
/*     */       {
/* 124 */         if (this.policyMap == pMap) {
/* 125 */           return this.serverAuthContext;
/*     */         }
/*     */       }
/*     */     } finally {
/* 129 */       this.rLock.unlock();
/*     */     } 
/*     */ 
/*     */     
/* 133 */     this.wLock.lock();
/*     */     
/*     */     try {
/* 136 */       if (this.serverAuthContext == null || this.policyMap != pMap) {
/* 137 */         this.serverAuthContext = new WSITServerAuthContext(operation, subject, map, this.callbackHandler);
/* 138 */         this.policyMap = pMap;
/*     */       } 
/* 140 */       return this.serverAuthContext;
/*     */     } finally {
/* 142 */       this.wLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getMessageLayer() {
/* 147 */     return this.layer;
/*     */   }
/*     */   
/*     */   public String getAppContext() {
/* 151 */     return this.appContext;
/*     */   }
/*     */   
/*     */   public String getOperation(MessageInfo messageInfo) {
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */   
/*     */   public String getAuthContextID(MessageInfo messageInfo) {
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isProtected() {
/* 166 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITServerAuthConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */