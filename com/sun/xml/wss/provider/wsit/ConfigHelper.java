/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.config.AuthConfig;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
/*     */ import javax.security.auth.message.config.AuthConfigProvider;
/*     */ import javax.security.auth.message.config.ClientAuthConfig;
/*     */ import javax.security.auth.message.config.ClientAuthContext;
/*     */ import javax.security.auth.message.config.RegistrationListener;
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
/*     */ public abstract class ConfigHelper
/*     */ {
/*     */   private static final String DEFAULT_HANDLER_CLASS = "com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler";
/*     */   private static final String HANDLER_CLASS_PROPERTY = "security.jmac.config.ConfigHelper.CallbackHandler";
/*     */   private static final String JMAC_CALLBACK_PROP = "META-INF/services/javax.security.auth.message.callback.CallbackHandler";
/*  83 */   private static String handlerClassName = null;
/*  84 */   protected AuthConfigFactory factory = null;
/*     */   
/*     */   private ReadWriteLock rwLock;
/*     */   
/*     */   private Lock rLock;
/*     */   
/*     */   private Lock wLock;
/*     */   
/*     */   protected String layer;
/*     */   protected String appCtxt;
/*     */   protected Map<Object, Object> map;
/*     */   protected CallbackHandler cbh;
/*  96 */   protected AuthConfigRegistrationWrapper listenerWrapper = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(String layer, String appContext, Map<Object, Object> map, CallbackHandler cbh) {
/* 101 */     this.factory = AuthConfigFactory.getFactory();
/* 102 */     this.layer = layer;
/* 103 */     this.appCtxt = appContext;
/* 104 */     this.map = map;
/* 105 */     this.cbh = cbh;
/* 106 */     if (this.cbh == null) {
/* 107 */       this.cbh = getCallbackHandler();
/*     */     }
/*     */     
/* 110 */     this.rwLock = new ReentrantReadWriteLock(true);
/* 111 */     this.rLock = this.rwLock.readLock();
/* 112 */     this.wLock = this.rwLock.writeLock();
/*     */     
/* 114 */     this.listenerWrapper = new AuthConfigRegistrationWrapper(this.layer, this.appCtxt);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJmacProviderRegisID(String jmacProviderRegisID) {
/* 119 */     this.listenerWrapper.setJmacProviderRegisID(jmacProviderRegisID);
/*     */   }
/*     */   
/*     */   public AuthConfigRegistrationWrapper getRegistrationWrapper() {
/* 123 */     return this.listenerWrapper;
/*     */   }
/*     */   
/*     */   public void setRegistrationWrapper(AuthConfigRegistrationWrapper wrapper) {
/* 127 */     this.listenerWrapper = wrapper;
/*     */   }
/*     */   
/*     */   public AuthConfigRegistrationWrapper.AuthConfigRegistrationListener getRegistrationListener() {
/* 131 */     return this.listenerWrapper.getListener();
/*     */   }
/*     */   
/*     */   public void disable() {
/* 135 */     this.listenerWrapper.disable();
/*     */   }
/*     */   
/*     */   public Object getProperty(String key) {
/* 139 */     return (this.map == null) ? null : this.map.get(key);
/*     */   }
/*     */   
/*     */   public String getAppContextID() {
/* 143 */     return this.appCtxt;
/*     */   }
/*     */   
/*     */   public ClientAuthConfig getClientAuthConfig() throws AuthException {
/* 147 */     return (ClientAuthConfig)getAuthConfig(false);
/*     */   }
/*     */   
/*     */   public ServerAuthConfig getServerAuthConfig() throws AuthException {
/* 151 */     return (ServerAuthConfig)getAuthConfig(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientAuthContext getClientAuthContext(MessageInfo info, Subject s) throws AuthException {
/* 156 */     ClientAuthConfig c = (ClientAuthConfig)getAuthConfig(false);
/* 157 */     if (c != null) {
/* 158 */       return c.getAuthContext(c.getAuthContextID(info), s, this.map);
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerAuthContext getServerAuthContext(MessageInfo info, Subject s) throws AuthException {
/* 165 */     ServerAuthConfig c = (ServerAuthConfig)getAuthConfig(true);
/* 166 */     if (c != null) {
/* 167 */       return c.getAuthContext(c.getAuthContextID(info), s, this.map);
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */   
/*     */   protected AuthConfig getAuthConfig(AuthConfigProvider p, boolean isServer) throws AuthException {
/*     */     ClientAuthConfig clientAuthConfig;
/* 174 */     AuthConfig c = null;
/* 175 */     if (p != null) {
/* 176 */       if (isServer) {
/* 177 */         ServerAuthConfig serverAuthConfig = p.getServerAuthConfig(this.layer, this.appCtxt, this.cbh);
/*     */       } else {
/* 179 */         clientAuthConfig = p.getClientAuthConfig(this.layer, this.appCtxt, this.cbh);
/*     */       } 
/*     */     }
/* 182 */     return (AuthConfig)clientAuthConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AuthConfig getAuthConfig(boolean isServer) throws AuthException {
/* 187 */     ConfigData d = null;
/* 188 */     AuthConfig c = null;
/* 189 */     boolean disabled = false;
/* 190 */     AuthConfigProvider lastP = null;
/*     */     
/*     */     try {
/* 193 */       this.rLock.lock();
/* 194 */       disabled = !this.listenerWrapper.isEnabled();
/* 195 */       if (!disabled) {
/* 196 */         d = this.listenerWrapper.getConfigData();
/* 197 */         if (d != null) {
/* 198 */           c = isServer ? d.sConfig : d.cConfig;
/* 199 */           lastP = d.provider;
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/* 204 */       this.rLock.unlock();
/* 205 */       if (disabled || c != null || (d != null && lastP == null)) {
/* 206 */         return c;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (d == null) {
/*     */       try {
/* 214 */         this.wLock.lock();
/* 215 */         if (this.listenerWrapper.getConfigData() == null) {
/* 216 */           AuthConfigProvider nextP = this.factory.getConfigProvider(this.layer, this.appCtxt, getRegistrationListener());
/*     */           
/* 218 */           if (nextP != null) {
/* 219 */             this.listenerWrapper.setConfigData(new ConfigData(nextP, getAuthConfig(nextP, isServer)));
/*     */           } else {
/* 221 */             this.listenerWrapper.setConfigData(new ConfigData());
/*     */           } 
/*     */         } 
/* 224 */         d = this.listenerWrapper.getConfigData();
/*     */       } finally {
/* 226 */         this.wLock.unlock();
/*     */       } 
/*     */     }
/*     */     
/* 230 */     return isServer ? d.sConfig : d.cConfig;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasExactMatchAuthProvider() {
/* 237 */     boolean exactMatch = false;
/*     */     
/* 239 */     AuthConfigProvider p = this.factory.getConfigProvider(this.layer, this.appCtxt, null);
/*     */     
/* 241 */     if (p != null) {
/* 242 */       String[] IDs = this.factory.getRegistrationIDs(p);
/* 243 */       for (String i : IDs) {
/* 244 */         AuthConfigFactory.RegistrationContext c = this.factory.getRegistrationContext(i);
/* 245 */         if (this.layer.equals(c.getMessageLayer()) && this.appCtxt.equals(c.getAppContext())) {
/*     */           
/* 247 */           exactMatch = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 253 */     return exactMatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CallbackHandler getCallbackHandler() {
/* 261 */     CallbackHandler rvalue = getDefaultCallbackHandler();
/*     */     
/* 263 */     return rvalue;
/*     */   }
/*     */   
/*     */   public static URL loadFromClasspath(String configFileName, ClassLoader cl) {
/* 267 */     if (cl == null) {
/* 268 */       return ClassLoader.getSystemResource(configFileName);
/*     */     }
/* 270 */     return cl.getResource(configFileName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallbackHandler getDefaultCallbackHandler() {
/*     */     try {
/* 278 */       final ClassLoader loader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 280 */       CallbackHandler rvalue = AccessController.<CallbackHandler>doPrivileged(new PrivilegedExceptionAction<CallbackHandler>()
/*     */           {
/*     */             
/*     */             public Object run() throws Exception
/*     */             {
/* 285 */               URL url = ConfigHelper.loadFromClasspath("META-INF/services/javax.security.auth.message.callback.CallbackHandler", loader);
/* 286 */               if (url != null) {
/* 287 */                 InputStream is = null;
/*     */                 try {
/* 289 */                   is = url.openStream();
/* 290 */                   ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 291 */                   int val = is.read();
/* 292 */                   while (val != -1) {
/* 293 */                     os.write(val);
/* 294 */                     val = is.read();
/*     */                   } 
/* 296 */                   ConfigHelper.handlerClassName = os.toString();
/*     */                 }
/* 298 */                 catch (IOException ex) {
/* 299 */                   Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */                 } finally {
/*     */                   try {
/* 302 */                     is.close();
/* 303 */                   } catch (IOException ex) {
/* 304 */                     Logger.getLogger(ConfigHelper.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 308 */               if (ConfigHelper.handlerClassName == null) {
/* 309 */                 ConfigHelper.handlerClassName = System.getProperty("security.jmac.config.ConfigHelper.CallbackHandler", "com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler");
/*     */               }
/*     */               
/* 312 */               String className = ConfigHelper.handlerClassName;
/*     */ 
/*     */               
/* 315 */               return ConfigHelper.this.loadGFHandler(className, loader);
/*     */             }
/*     */           });
/* 318 */       return rvalue;
/*     */     }
/* 320 */     catch (PrivilegedActionException pae) {
/* 321 */       throw new RuntimeException(pae.getException());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected CallbackHandler loadGFHandler(String jmacHandler, ClassLoader loader) {
/* 326 */     String classname = jmacHandler;
/* 327 */     Class<?> ret = null;
/*     */     try {
/*     */       try {
/* 330 */         if (loader != null) {
/* 331 */           ret = loader.loadClass(classname);
/*     */         }
/* 333 */       } catch (ClassNotFoundException e) {}
/*     */ 
/*     */ 
/*     */       
/* 337 */       if (ret == null) {
/*     */         
/* 339 */         loader = getClass().getClassLoader();
/* 340 */         ret = loader.loadClass(classname);
/*     */       } 
/*     */       
/* 343 */       if (ret != null) {
/* 344 */         CallbackHandler handler = (CallbackHandler)ret.newInstance();
/* 345 */         return handler;
/*     */       } 
/* 347 */     } catch (ClassNotFoundException e) {
/*     */ 
/*     */     
/* 350 */     } catch (InstantiationException e) {
/*     */     
/* 352 */     } catch (IllegalAccessException ex) {}
/*     */ 
/*     */     
/* 355 */     if ("com.sun.enterprise.security.jmac.callback.ContainerCallbackHandler".equals(classname)) {
/* 356 */       return null;
/*     */     }
/* 358 */     throw new RuntimeException("Failed to Load CallbackHandler:" + classname);
/*     */   }
/*     */   
/*     */   private class ConfigData
/*     */   {
/*     */     private AuthConfigProvider provider;
/*     */     private AuthConfig sConfig;
/*     */     private AuthConfig cConfig;
/*     */     
/*     */     ConfigData() {
/* 368 */       this.provider = null;
/* 369 */       this.sConfig = null;
/* 370 */       this.cConfig = null;
/*     */     }
/*     */     
/*     */     ConfigData(AuthConfigProvider p, AuthConfig a) {
/* 374 */       this.provider = p;
/* 375 */       if (a == null) {
/* 376 */         this.sConfig = null;
/* 377 */         this.cConfig = null;
/* 378 */       } else if (a instanceof ServerAuthConfig) {
/* 379 */         this.sConfig = a;
/* 380 */         this.cConfig = null;
/* 381 */       } else if (a instanceof ClientAuthConfig) {
/* 382 */         this.sConfig = null;
/* 383 */         this.cConfig = a;
/*     */       } else {
/* 385 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class AuthConfigRegistrationWrapper
/*     */   {
/*     */     private String layer;
/*     */     
/*     */     private String appCtxt;
/*     */     
/* 397 */     private String jmacProviderRegisID = null;
/*     */     
/*     */     private boolean enabled;
/*     */     
/*     */     private ConfigHelper.ConfigData data;
/*     */     private Lock wLock;
/*     */     private ReadWriteLock rwLock;
/*     */     AuthConfigRegistrationListener listener;
/* 405 */     int referenceCount = 1;
/*     */     
/*     */     public AuthConfigRegistrationWrapper(String layer, String appCtxt) {
/* 408 */       this.layer = layer;
/* 409 */       this.appCtxt = appCtxt;
/* 410 */       this.rwLock = new ReentrantReadWriteLock(true);
/* 411 */       this.wLock = this.rwLock.writeLock();
/* 412 */       this.enabled = (ConfigHelper.this.factory != null);
/* 413 */       this.listener = new AuthConfigRegistrationListener(layer, appCtxt);
/*     */     }
/*     */     
/*     */     public AuthConfigRegistrationListener getListener() {
/* 417 */       return this.listener;
/*     */     }
/*     */     
/*     */     public void setListener(AuthConfigRegistrationListener listener) {
/* 421 */       this.listener = listener;
/*     */     }
/*     */     
/*     */     public void disable() {
/*     */       try {
/* 426 */         this.wLock.lock();
/* 427 */         setEnabled(false);
/*     */       } finally {
/* 429 */         this.data = null;
/* 430 */         this.wLock.unlock();
/*     */       } 
/* 432 */       if (ConfigHelper.this.factory != null) {
/* 433 */         String[] ids = ConfigHelper.this.factory.detachListener(this.listener, this.layer, this.appCtxt);
/* 434 */         if (ids != null) {
/* 435 */           for (int i = 0; i < ids.length; i++) {
/* 436 */             ConfigHelper.this.factory.removeRegistration(ids[i]);
/*     */           }
/*     */         }
/* 439 */         if (getJmacProviderRegisID() != null) {
/* 440 */           ConfigHelper.this.factory.removeRegistration(getJmacProviderRegisID());
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void disableWithRefCount() {
/* 447 */       if (this.referenceCount <= 1) {
/* 448 */         disable();
/*     */       } else {
/*     */         try {
/* 451 */           this.wLock.lock();
/* 452 */           this.referenceCount--;
/*     */         } finally {
/* 454 */           this.wLock.unlock();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void incrementReference() {
/*     */       try {
/* 462 */         this.wLock.lock();
/* 463 */         this.referenceCount++;
/*     */       } finally {
/* 465 */         this.wLock.unlock();
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean isEnabled() {
/* 470 */       return this.enabled;
/*     */     }
/*     */     
/*     */     public void setEnabled(boolean enabled) {
/* 474 */       this.enabled = enabled;
/*     */     }
/*     */     
/*     */     public String getJmacProviderRegisID() {
/* 478 */       return this.jmacProviderRegisID;
/*     */     }
/*     */     
/*     */     public void setJmacProviderRegisID(String jmacProviderRegisID) {
/* 482 */       this.jmacProviderRegisID = jmacProviderRegisID;
/*     */     }
/*     */     
/*     */     public ConfigHelper.ConfigData getConfigData() {
/* 486 */       return this.data;
/*     */     }
/*     */     
/*     */     public void setConfigData(ConfigHelper.ConfigData data) {
/* 490 */       this.data = data;
/*     */     }
/*     */     
/*     */     public class AuthConfigRegistrationListener
/*     */       implements RegistrationListener
/*     */     {
/*     */       private String layer;
/*     */       private String appCtxt;
/*     */       
/*     */       public AuthConfigRegistrationListener(String layer, String appCtxt) {
/* 500 */         this.layer = layer;
/* 501 */         this.appCtxt = appCtxt;
/*     */       }
/*     */       
/*     */       public void notify(String layer, String appContext) {
/* 505 */         if (this.layer.equals(layer) && ((this.appCtxt == null && appContext == null) || (appContext != null && appContext.equals(this.appCtxt))))
/*     */ 
/*     */           
/*     */           try { 
/* 509 */             ConfigHelper.AuthConfigRegistrationWrapper.this.wLock.lock();
/* 510 */             ConfigHelper.AuthConfigRegistrationWrapper.this.data = null; }
/*     */           finally
/* 512 */           { ConfigHelper.AuthConfigRegistrationWrapper.this.wLock.unlock(); }   } } } public class AuthConfigRegistrationListener implements RegistrationListener { public void notify(String layer, String appContext) { if (this.layer.equals(layer) && ((this.appCtxt == null && appContext == null) || (appContext != null && appContext.equals(this.appCtxt)))) try { this.this$1.wLock.lock(); this.this$1.data = null; } finally { this.this$1.wLock.unlock(); }
/*     */           }
/*     */ 
/*     */     
/*     */     private String layer;
/*     */     private String appCtxt;
/*     */     
/*     */     public AuthConfigRegistrationListener(String layer, String appCtxt) {
/*     */       this.layer = layer;
/*     */       this.appCtxt = appCtxt;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\ConfigHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */