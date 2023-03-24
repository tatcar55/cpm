/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.config.ClientAuthConfig;
/*     */ import javax.security.auth.message.config.ClientAuthContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSITClientAuthConfig
/*     */   implements ClientAuthConfig
/*     */ {
/*  78 */   private static final Logger log = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  82 */   private String layer = null;
/*  83 */   private String appContext = null;
/*  84 */   private CallbackHandler callbackHandler = null;
/*     */   
/*     */   private ReentrantReadWriteLock rwLock;
/*     */   private ReentrantReadWriteLock.ReadLock rLock;
/*     */   private ReentrantReadWriteLock.WriteLock wLock;
/*     */   private volatile boolean secEnabled;
/*  90 */   private Map<Integer, WSITClientAuthContext> tubetoClientAuthContextHash = Collections.synchronizedMap(new WeakHashMap<Integer, WSITClientAuthContext>());
/*     */   
/*     */   public WSITClientAuthConfig(String layer, String appContext, CallbackHandler callbackHandler) {
/*  93 */     this.layer = layer;
/*  94 */     this.appContext = appContext;
/*  95 */     this.callbackHandler = callbackHandler;
/*  96 */     this.rwLock = new ReentrantReadWriteLock(true);
/*  97 */     this.rLock = this.rwLock.readLock();
/*  98 */     this.wLock = this.rwLock.writeLock();
/*     */   }
/*     */   
/*     */   public ClientAuthContext getAuthContext(String operation, Subject subject, Map<Object, Object> rawMap) throws AuthException {
/* 102 */     Map<Object, Object> map = rawMap;
/*     */     
/* 104 */     PolicyMap pMap = (PolicyMap)map.get("POLICY");
/* 105 */     WSDLPort port = (WSDLPort)map.get("WSDL_MODEL");
/* 106 */     Object tubeOrPipe = map.get("SECURITY_PIPE");
/* 107 */     Integer hashCode = (tubeOrPipe != null) ? Integer.valueOf(tubeOrPipe.hashCode()) : null;
/* 108 */     map.put("AUTH_CONFIG", this);
/*     */     
/* 110 */     if (pMap == null || pMap.isEmpty()) {
/* 111 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       this.rLock.lock();
/* 122 */       if (!this.secEnabled || !this.tubetoClientAuthContextHash.containsKey(hashCode)) {
/* 123 */         this.rLock.unlock();
/* 124 */         this.wLock.lock();
/*     */         try {
/* 126 */           if (!this.secEnabled || !this.tubetoClientAuthContextHash.containsKey(hashCode)) {
/* 127 */             if (!WSITAuthConfigProvider.isSecurityEnabled(pMap, port)) {
/* 128 */               return null;
/*     */             }
/* 130 */             this.secEnabled = true;
/*     */           } 
/*     */         } finally {
/* 133 */           this.rLock.lock();
/* 134 */           this.wLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 138 */       this.rLock.unlock();
/*     */     } 
/* 140 */     WSITClientAuthContext clientAuthContext = null;
/* 141 */     this.rLock.lock();
/*     */     try {
/* 143 */       if (this.tubetoClientAuthContextHash.containsKey(hashCode)) {
/* 144 */         clientAuthContext = this.tubetoClientAuthContextHash.get(hashCode);
/*     */       }
/*     */     } finally {
/* 147 */       this.rLock.unlock();
/*     */     } 
/*     */     
/* 150 */     if (clientAuthContext == null) {
/* 151 */       this.wLock.lock();
/*     */       
/*     */       try {
/* 154 */         if (!this.tubetoClientAuthContextHash.containsKey(hashCode)) {
/* 155 */           clientAuthContext = new WSITClientAuthContext(operation, subject, map, this.callbackHandler);
/* 156 */           this.tubetoClientAuthContextHash.put(hashCode, clientAuthContext);
/*     */         } 
/*     */       } finally {
/* 159 */         this.wLock.unlock();
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     startSecureConversation(map, clientAuthContext);
/* 164 */     return clientAuthContext;
/*     */   }
/*     */   
/*     */   public String getMessageLayer() {
/* 168 */     return this.layer;
/*     */   }
/*     */   
/*     */   public String getAppContext() {
/* 172 */     return this.appContext;
/*     */   }
/*     */   
/*     */   public String getOperation(MessageInfo messageInfo) {
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */   
/*     */   public String getAuthContextID(MessageInfo messageInfo) {
/* 183 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isProtected() {
/* 187 */     return true;
/*     */   }
/*     */   
/*     */   public ClientAuthContext cleanupAuthContext(Integer hashCode) {
/* 191 */     return this.tubetoClientAuthContextHash.remove(hashCode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private JAXBElement startSecureConversation(Map map, WSITClientAuthContext clientAuthContext) {
/* 197 */     JAXBElement ret = null;
/*     */     try {
/* 199 */       MessageInfo info = (MessageInfo)map.get("SECURITY_TOKEN");
/* 200 */       if (info != null) {
/* 201 */         Packet packet = (Packet)info.getMap().get("REQ_PACKET");
/* 202 */         if (packet != null) {
/* 203 */           if (clientAuthContext != null) {
/* 204 */             ret = clientAuthContext.startSecureConversation(packet);
/*     */             
/* 206 */             info.getMap().put("SECURITY_TOKEN", ret);
/*     */           } else {
/* 208 */             log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0024_NULL_CLIENT_AUTH_CONTEXT());
/*     */             
/* 210 */             throw new WSSecureConversationException(LogStringsMessages.WSITPVD_0024_NULL_CLIENT_AUTH_CONTEXT());
/*     */           } 
/*     */         } else {
/*     */           
/* 214 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0025_NULL_PACKET());
/* 215 */           throw new RuntimeException(LogStringsMessages.WSITPVD_0025_NULL_PACKET());
/*     */         } 
/*     */       } 
/* 218 */     } catch (WSSecureConversationException ex) {
/* 219 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0026_ERROR_STARTING_SC(), (Throwable)ex);
/* 220 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0026_ERROR_STARTING_SC(), ex);
/*     */     } 
/* 222 */     return ret;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITClientAuthConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */