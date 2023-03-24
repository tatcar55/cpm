/*     */ package com.sun.xml.ws.api.security.trust.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IssuedTokenManager
/*     */ {
/*  57 */   private final Map<String, IssuedTokenProvider> itpMap = new HashMap<String, IssuedTokenProvider>();
/*  58 */   private final Map<String, String> itpClassMap = new HashMap<String, String>();
/*  59 */   private static IssuedTokenManager manager = new IssuedTokenManager();
/*     */ 
/*     */   
/*     */   private IssuedTokenManager() {
/*  63 */     addDefaultProviders();
/*     */   }
/*     */   
/*     */   public static IssuedTokenManager getInstance() {
/*  67 */     synchronized (IssuedTokenManager.class) {
/*  68 */       return manager;
/*     */     } 
/*     */   }
/*     */   
/*     */   public IssuedTokenContext createIssuedTokenContext(IssuedTokenConfiguration config, String appliesTo) {
/*  73 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/*  74 */     issuedTokenContextImpl.getSecurityPolicy().add(config);
/*  75 */     issuedTokenContextImpl.setEndpointAddress(appliesTo);
/*     */     
/*  77 */     return (IssuedTokenContext)issuedTokenContextImpl;
/*     */   }
/*     */   
/*     */   public void getIssuedToken(IssuedTokenContext ctx) throws WSTrustException {
/*  81 */     IssuedTokenConfiguration config = ctx.getSecurityPolicy().get(0);
/*  82 */     IssuedTokenProvider provider = getIssuedTokenProvider(config.getProtocol());
/*  83 */     provider.issue(ctx);
/*     */   }
/*     */   
/*     */   public void renewIssuedToken(IssuedTokenContext ctx) throws WSTrustException {
/*  87 */     IssuedTokenConfiguration config = ctx.getSecurityPolicy().get(0);
/*  88 */     IssuedTokenProvider provider = getIssuedTokenProvider(config.getProtocol());
/*  89 */     provider.renew(ctx);
/*     */   }
/*     */   
/*     */   public void cancelIssuedToken(IssuedTokenContext ctx) throws WSTrustException {
/*  93 */     IssuedTokenConfiguration config = ctx.getSecurityPolicy().get(0);
/*  94 */     IssuedTokenProvider provider = getIssuedTokenProvider(config.getProtocol());
/*  95 */     provider.cancel(ctx);
/*     */   }
/*     */   
/*     */   public void validateIssuedToken(IssuedTokenContext ctx) throws WSTrustException {
/*  99 */     IssuedTokenConfiguration config = ctx.getSecurityPolicy().get(0);
/* 100 */     IssuedTokenProvider provider = getIssuedTokenProvider(config.getProtocol());
/* 101 */     provider.validate(ctx);
/*     */   }
/*     */   
/*     */   private void addDefaultProviders() {
/* 105 */     this.itpClassMap.put("http://schemas.xmlsoap.org/ws/2005/02/trust", "com.sun.xml.ws.security.trust.impl.client.STSIssuedTokenProviderImpl");
/* 106 */     this.itpClassMap.put("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "com.sun.xml.ws.security.trust.impl.client.STSIssuedTokenProviderImpl");
/* 107 */     this.itpClassMap.put("http://schemas.xmlsoap.org/ws/2005/02/sc", "com.sun.xml.ws.security.secconv.impl.client.SCTokenProviderImpl");
/* 108 */     this.itpClassMap.put("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512", "com.sun.xml.ws.security.secconv.impl.client.SCTokenProviderImpl");
/*     */   }
/*     */   
/*     */   private IssuedTokenProvider getIssuedTokenProvider(String protocol) throws WSTrustException {
/* 112 */     IssuedTokenProvider itp = null;
/* 113 */     synchronized (this.itpMap) {
/* 114 */       itp = this.itpMap.get(protocol);
/* 115 */       if (itp == null) {
/* 116 */         String type = this.itpClassMap.get(protocol);
/* 117 */         if (type != null) {
/*     */           try {
/* 119 */             Class<?> clazz = null;
/* 120 */             ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */             
/* 122 */             if (loader == null) {
/* 123 */               clazz = Class.forName(type);
/*     */             } else {
/* 125 */               clazz = loader.loadClass(type);
/*     */             } 
/*     */             
/* 128 */             if (clazz != null) {
/*     */               
/* 130 */               Class<IssuedTokenProvider> typedClass = (Class)clazz;
/* 131 */               itp = typedClass.newInstance();
/* 132 */               this.itpMap.put(protocol, itp);
/*     */             } 
/* 134 */           } catch (Exception e) {
/* 135 */             throw new WSTrustException("IssueTokenProvider for the protocol: " + protocol + "is not supported", e);
/*     */           } 
/*     */         } else {
/* 138 */           throw new WSTrustException("IssueTokenProvider for the protocol: " + protocol + "is not supported");
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 143 */     return itp;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\client\IssuedTokenManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */