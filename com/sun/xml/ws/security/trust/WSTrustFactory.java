/*     */ package com.sun.xml.ws.security.trust;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.STSAttributeProvider;
/*     */ import com.sun.xml.ws.api.security.trust.STSAuthorizationProvider;
/*     */ import com.sun.xml.ws.api.security.trust.STSTokenProvider;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustContract;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfigurationProvider;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.impl.DefaultSAMLTokenProvider;
/*     */ import com.sun.xml.ws.security.trust.impl.DefaultSTSAttributeProvider;
/*     */ import com.sun.xml.ws.security.trust.impl.DefaultSTSAuthorizationProvider;
/*     */ import com.sun.xml.ws.security.trust.impl.TrustPluginImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.WSTrustClientContractImpl;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSTrustFactory
/*     */ {
/*  72 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TrustPlugin newTrustPlugin() {
/*  81 */     return (TrustPlugin)new TrustPluginImpl();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSTrustContract<BaseSTSRequest, BaseSTSResponse> newWSTrustContract(STSConfiguration config, String appliesTo) throws WSTrustException {
/* 102 */     String type = config.getType();
/* 103 */     if (log.isLoggable(Level.FINE)) {
/* 104 */       log.log(Level.FINE, LogStringsMessages.WST_1002_PROVIDER_TYPE(type));
/*     */     }
/*     */     
/* 107 */     WSTrustContract<BaseSTSRequest, BaseSTSResponse> contract = null;
/*     */     try {
/* 109 */       Class<?> clazz = null;
/* 110 */       ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 112 */       if (loader == null) {
/* 113 */         clazz = Class.forName(type);
/*     */       } else {
/* 115 */         clazz = loader.loadClass(type);
/*     */       } 
/*     */       
/* 118 */       if (clazz != null) {
/*     */         
/* 120 */         Class<WSTrustContract<BaseSTSRequest, BaseSTSResponse>> typedClass = (Class)clazz;
/* 121 */         contract = typedClass.newInstance();
/* 122 */         contract.init(config);
/*     */       } 
/* 124 */     } catch (ClassNotFoundException ex) {
/* 125 */       contract = null;
/* 126 */       log.log(Level.SEVERE, LogStringsMessages.WST_0005_CLASSNOTFOUND_NULL_CONTRACT(type), ex);
/*     */       
/* 128 */       throw new WSTrustException(LogStringsMessages.WST_0005_CLASSNOTFOUND_NULL_CONTRACT(type), ex);
/* 129 */     } catch (Exception ex) {
/* 130 */       log.log(Level.SEVERE, LogStringsMessages.WST_0038_INIT_CONTRACT_FAIL(), ex);
/*     */       
/* 132 */       throw new WSTrustException(LogStringsMessages.WST_0038_INIT_CONTRACT_FAIL(), ex);
/*     */     } 
/*     */     
/* 135 */     return contract;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSTrustClientContract createWSTrustClientContract() {
/* 142 */     return (WSTrustClientContract)new WSTrustClientContractImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static STSAuthorizationProvider getSTSAuthorizationProvider() {
/*     */     DefaultSTSAuthorizationProvider defaultSTSAuthorizationProvider;
/* 154 */     STSAuthorizationProvider authzProvider = null;
/* 155 */     ServiceFinder<STSAuthorizationProvider> finder = ServiceFinder.find(STSAuthorizationProvider.class);
/*     */     
/* 157 */     Iterator<STSAuthorizationProvider> it = finder.iterator();
/* 158 */     if (it.hasNext()) {
/* 159 */       authzProvider = it.next();
/*     */     } else {
/* 161 */       defaultSTSAuthorizationProvider = new DefaultSTSAuthorizationProvider();
/*     */     } 
/* 163 */     return (STSAuthorizationProvider)defaultSTSAuthorizationProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static STSAttributeProvider getSTSAttributeProvider() {
/*     */     DefaultSTSAttributeProvider defaultSTSAttributeProvider;
/* 175 */     STSAttributeProvider attrProvider = null;
/* 176 */     ServiceFinder<STSAttributeProvider> finder = ServiceFinder.find(STSAttributeProvider.class);
/*     */     
/* 178 */     Iterator<STSAttributeProvider> it = finder.iterator();
/* 179 */     if (it.hasNext()) {
/* 180 */       attrProvider = it.next();
/*     */     } else {
/* 182 */       defaultSTSAttributeProvider = new DefaultSTSAttributeProvider();
/*     */     } 
/* 184 */     return (STSAttributeProvider)defaultSTSAttributeProvider;
/*     */   }
/*     */   
/*     */   public static STSTokenProvider getSTSTokenProvider() {
/*     */     DefaultSAMLTokenProvider defaultSAMLTokenProvider;
/* 189 */     STSTokenProvider tokenProvider = null;
/* 190 */     ServiceFinder<STSTokenProvider> finder = ServiceFinder.find(STSTokenProvider.class);
/*     */     
/* 192 */     Iterator<STSTokenProvider> it = finder.iterator();
/* 193 */     if (it.hasNext()) {
/* 194 */       tokenProvider = it.next();
/*     */     } else {
/* 196 */       defaultSAMLTokenProvider = new DefaultSAMLTokenProvider();
/*     */     } 
/* 198 */     return (STSTokenProvider)defaultSAMLTokenProvider;
/*     */   }
/*     */   
/*     */   public static STSConfiguration getRuntimeSTSConfiguration() {
/* 202 */     STSConfigurationProvider configProvider = null;
/* 203 */     ServiceFinder<STSConfigurationProvider> finder = ServiceFinder.find(STSConfigurationProvider.class);
/*     */     
/* 205 */     Iterator<STSConfigurationProvider> it = finder.iterator();
/* 206 */     if (it.hasNext()) {
/* 207 */       configProvider = it.next();
/*     */     }
/*     */     
/* 210 */     if (configProvider != null) {
/* 211 */       return configProvider.getSTSConfiguration();
/*     */     }
/*     */     
/* 214 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */