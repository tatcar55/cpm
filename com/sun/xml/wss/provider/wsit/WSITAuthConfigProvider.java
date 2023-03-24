/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
/*     */ import javax.security.auth.message.config.AuthConfigProvider;
/*     */ import javax.security.auth.message.config.ClientAuthConfig;
/*     */ import javax.security.auth.message.config.ServerAuthConfig;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSITAuthConfigProvider
/*     */   implements AuthConfigProvider
/*     */ {
/*  79 */   String description = "WSIT AuthConfigProvider";
/*     */ 
/*     */ 
/*     */   
/*  83 */   WeakHashMap clientConfigMap = new WeakHashMap<Object, Object>();
/*  84 */   WeakHashMap serverConfigMap = new WeakHashMap<Object, Object>();
/*     */   
/*     */   private ReentrantReadWriteLock rwLock;
/*     */   
/*     */   private ReentrantReadWriteLock.ReadLock rLock;
/*     */   
/*     */   private ReentrantReadWriteLock.WriteLock wLock;
/*     */ 
/*     */   
/*     */   public WSITAuthConfigProvider(Map props, AuthConfigFactory factory) {
/*  94 */     if (factory != null) {
/*  95 */       factory.registerConfigProvider(this, "SOAP", null, this.description);
/*     */     }
/*  97 */     this.rwLock = new ReentrantReadWriteLock(true);
/*  98 */     this.rLock = this.rwLock.readLock();
/*  99 */     this.wLock = this.rwLock.writeLock();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientAuthConfig getClientAuthConfig(String layer, String appContext, CallbackHandler callbackHandler) throws AuthException {
/* 105 */     ClientAuthConfig clientConfig = null;
/* 106 */     this.rLock.lock();
/*     */     try {
/* 108 */       clientConfig = (ClientAuthConfig)this.clientConfigMap.get(appContext);
/* 109 */       if (clientConfig != null) {
/* 110 */         return clientConfig;
/*     */       }
/*     */     } finally {
/* 113 */       this.rLock.unlock();
/*     */     } 
/*     */ 
/*     */     
/* 117 */     this.wLock.lock();
/*     */     
/*     */     try {
/* 120 */       if (clientConfig == null) {
/* 121 */         clientConfig = new WSITClientAuthConfig(layer, appContext, callbackHandler);
/* 122 */         this.clientConfigMap.put(appContext, clientConfig);
/*     */       } 
/* 124 */       return clientConfig;
/*     */     } finally {
/* 126 */       this.wLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerAuthConfig getServerAuthConfig(String layer, String appContext, CallbackHandler callbackHandler) throws AuthException {
/* 132 */     ServerAuthConfig serverConfig = null;
/* 133 */     this.rLock.lock();
/*     */     try {
/* 135 */       serverConfig = (ServerAuthConfig)this.serverConfigMap.get(appContext);
/* 136 */       if (serverConfig != null) {
/* 137 */         return serverConfig;
/*     */       }
/*     */     } finally {
/* 140 */       this.rLock.unlock();
/*     */     } 
/*     */ 
/*     */     
/* 144 */     this.wLock.lock();
/*     */     
/*     */     try {
/* 147 */       if (serverConfig == null) {
/* 148 */         serverConfig = new WSITServerAuthConfig(layer, appContext, callbackHandler);
/* 149 */         this.serverConfigMap.put(appContext, serverConfig);
/*     */       } 
/* 151 */       return serverConfig;
/*     */     } finally {
/* 153 */       this.wLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSecurityEnabled(PolicyMap policyMap, WSDLPort wsdlPort) {
/* 169 */     if (policyMap == null || wsdlPort == null) {
/* 170 */       return false;
/*     */     }
/*     */     try {
/* 173 */       PolicyMapKey endpointKey = PolicyMap.createWsdlEndpointScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName());
/*     */       
/* 175 */       Policy policy = policyMap.getEndpointEffectivePolicy(endpointKey);
/*     */       
/* 177 */       if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY200512.namespaceUri)))
/*     */       {
/*     */ 
/*     */         
/* 181 */         return true;
/*     */       }
/*     */       
/* 184 */       for (WSDLBoundOperation wbo : wsdlPort.getBinding().getBindingOperations()) {
/* 185 */         PolicyMapKey operationKey = PolicyMap.createWsdlOperationScopeKey(wsdlPort.getOwner().getName(), wsdlPort.getName(), wbo.getName());
/*     */ 
/*     */         
/* 188 */         policy = policyMap.getOperationEffectivePolicy(operationKey);
/* 189 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 192 */           return true;
/*     */         }
/* 194 */         policy = policyMap.getInputMessageEffectivePolicy(operationKey);
/* 195 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 198 */           return true;
/*     */         }
/* 200 */         policy = policyMap.getOutputMessageEffectivePolicy(operationKey);
/* 201 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 204 */           return true;
/*     */         }
/* 206 */         policy = policyMap.getFaultMessageEffectivePolicy(operationKey);
/* 207 */         if (policy != null && (policy.contains(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri) || policy.contains(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri)))
/*     */         {
/*     */           
/* 210 */           return true; } 
/*     */       } 
/* 212 */     } catch (PolicyException e) {
/* 213 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 216 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITAuthConfigProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */