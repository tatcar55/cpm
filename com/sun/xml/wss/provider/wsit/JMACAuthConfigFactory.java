/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.message.config.AuthConfigFactory;
/*     */ import javax.security.auth.message.config.AuthConfigProvider;
/*     */ import javax.security.auth.message.config.RegistrationListener;
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
/*     */ public class JMACAuthConfigFactory
/*     */   extends AuthConfigFactory
/*     */ {
/*  74 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.provider.wsit", "com.sun.xml.wss.provider.wsit.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String AUTH_CONFIG_PROVIDER_PROP = "META-INF/services/javax.security.auth.message.config.AuthConfigProvider";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static ReadWriteLock rwLock = new ReentrantReadWriteLock(true);
/*  96 */   private static Lock rLock = rwLock.readLock();
/*  97 */   private static Lock wLock = rwLock.writeLock();
/*     */   private static Map<String, AuthConfigProvider> id2ProviderMap;
/*     */   private static Map<String, AuthConfigFactory.RegistrationContext> id2RegisContextMap;
/*     */   private static Map<String, List<RegistrationListener>> id2RegisListenersMap;
/*     */   
/*     */   public JMACAuthConfigFactory(ClassLoader loader) {
/* 103 */     this.loader = loader;
/* 104 */     regStore = new RegStoreFileParser(System.getProperty("user.dir"), "auth.conf", false);
/*     */     
/* 106 */     _loadFactory();
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
/*     */   private static Map<AuthConfigProvider, List<String>> provider2IdsMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String CONF_FILE_NAME = "auth.conf";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static RegStoreFileParser regStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassLoader loader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthConfigProvider getConfigProvider(String layer, String appContext, RegistrationListener listener) {
/* 159 */     AuthConfigProvider provider = null;
/* 160 */     String regisID = getRegistrationID(layer, appContext);
/* 161 */     rLock.lock();
/*     */     try {
/* 163 */       provider = id2ProviderMap.get(regisID);
/* 164 */       if (provider == null) {
/* 165 */         provider = id2ProviderMap.get(getRegistrationID(null, appContext));
/*     */       }
/* 167 */       if (provider == null) {
/* 168 */         provider = id2ProviderMap.get(getRegistrationID(layer, null));
/*     */       }
/* 170 */       if (provider == null) {
/* 171 */         provider = id2ProviderMap.get(getRegistrationID(null, null));
/*     */       }
/*     */     } finally {
/* 174 */       rLock.unlock();
/*     */     } 
/*     */     
/* 177 */     if (listener != null) {
/*     */       
/* 179 */       boolean lregister = false;
/* 180 */       rLock.lock();
/*     */       try {
/* 182 */         List<RegistrationListener> listeners = id2RegisListenersMap.get(regisID);
/*     */         
/* 184 */         if (listeners != null) {
/* 185 */           lregister = listeners.contains(listener);
/*     */         }
/*     */       } finally {
/* 188 */         rLock.unlock();
/*     */       } 
/*     */       
/* 191 */       if (!lregister) {
/* 192 */         wLock.lock();
/*     */         try {
/* 194 */           List<RegistrationListener> listeners = id2RegisListenersMap.get(regisID);
/*     */           
/* 196 */           if (listeners == null) {
/* 197 */             listeners = new ArrayList<RegistrationListener>();
/* 198 */             id2RegisListenersMap.put(regisID, listeners);
/*     */           } 
/* 200 */           if (!listeners.contains(listener)) {
/* 201 */             listeners.add(listener);
/*     */           }
/*     */         } finally {
/* 204 */           wLock.unlock();
/*     */         } 
/*     */       } 
/*     */     } 
/* 208 */     return provider;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String registerConfigProvider(String className, Map<String, String> properties, String layer, String appContext, String description) {
/* 275 */     AuthConfigProvider provider = _constructProvider(className, properties, null);
/*     */     
/* 277 */     return _register(provider, properties, layer, appContext, description, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String registerConfigProvider(AuthConfigProvider provider, String layer, String appContext, String description) {
/* 283 */     return _register(provider, null, layer, appContext, description, false);
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
/*     */   public boolean removeRegistration(String registrationID) {
/* 302 */     return _unRegister(registrationID);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] detachListener(RegistrationListener listener, String layer, String appContext) {
/* 328 */     String regisID = getRegistrationID(layer, appContext);
/* 329 */     wLock.lock();
/*     */     try {
/* 331 */       RegistrationListener ler = null;
/* 332 */       List<RegistrationListener> listeners = id2RegisListenersMap.get(regisID);
/*     */       
/* 334 */       if (listeners != null && listeners.remove(listener)) {
/* 335 */         ler = listener;
/*     */       }
/* 337 */       (new String[1])[0] = regisID; return (ler != null) ? new String[1] : new String[0];
/*     */     } finally {
/* 339 */       wLock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRegistrationIDs(AuthConfigProvider provider) {
/* 358 */     rLock.lock();
/*     */     try {
/* 360 */       Collection<String> regisIDs = null;
/* 361 */       if (provider != null) {
/* 362 */         regisIDs = provider2IdsMap.get(provider);
/*     */       } else {
/* 364 */         Collection<List<String>> collList = provider2IdsMap.values();
/* 365 */         if (collList != null) {
/* 366 */           regisIDs = new HashSet<String>();
/* 367 */           for (List<String> listIds : collList) {
/* 368 */             if (listIds != null) {
/* 369 */               regisIDs.addAll(listIds);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 374 */       return (regisIDs != null) ? regisIDs.<String>toArray(new String[regisIDs.size()]) : new String[0];
/*     */     }
/*     */     finally {
/*     */       
/* 378 */       rLock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthConfigFactory.RegistrationContext getRegistrationContext(String registrationID) {
/* 394 */     rLock.lock();
/*     */     try {
/* 396 */       return id2RegisContextMap.get(registrationID);
/*     */     } finally {
/* 398 */       rLock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refresh() {
/* 417 */     _loadFactory();
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
/*     */   static List<EntryInfo> getDefaultProviders() {
/* 430 */     List<EntryInfo> entries = new ArrayList<EntryInfo>(1);
/* 431 */     URL url = loadFromClasspath("META-INF/services/javax.security.auth.message.config.AuthConfigProvider");
/* 432 */     if (url != null) {
/* 433 */       InputStream is = null;
/*     */       try {
/* 435 */         is = url.openStream();
/* 436 */         ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 437 */         int val = is.read();
/* 438 */         while (val != -1) {
/* 439 */           os.write(val);
/* 440 */           val = is.read();
/*     */         } 
/* 442 */         String classname = os.toString();
/* 443 */         entries.add(new EntryInfo(classname, null));
/*     */       }
/* 445 */       catch (IOException ex) {
/* 446 */         logger.log(Level.SEVERE, LogStringsMessages.WSITPVD_0062_ERROR_LOAD_DEFAULT_PROVIDERS(), ex);
/* 447 */         throw new WebServiceException(ex);
/*     */       } finally {
/*     */         try {
/* 450 */           is.close();
/* 451 */         } catch (IOException ex) {
/* 452 */           logger.log(Level.WARNING, LogStringsMessages.WSITPVD_0062_ERROR_LOAD_DEFAULT_PROVIDERS(), ex);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 456 */       entries.add(new EntryInfo("com.sun.xml.wss.provider.wsit.WSITAuthConfigProvider", null));
/*     */     } 
/*     */     
/* 459 */     return entries;
/*     */   }
/*     */ 
/*     */   
/*     */   public static URL loadFromClasspath(String configFileName) {
/* 464 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 465 */     if (loader == null) {
/* 466 */       return ClassLoader.getSystemResource(configFileName);
/*     */     }
/* 468 */     return loader.getResource(configFileName);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getRegistrationID(String layer, String appContext) {
/* 473 */     String regisID = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 480 */     if (layer != null) {
/* 481 */       regisID = (appContext != null) ? ("__3" + layer.length() + "_" + layer + appContext) : ("__2" + layer);
/*     */     }
/*     */     else {
/*     */       
/* 485 */       regisID = (appContext != null) ? ("__1" + appContext) : "__0";
/*     */     } 
/*     */ 
/*     */     
/* 489 */     return regisID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] decomposeRegisID(String regisID) {
/* 498 */     String layer = null;
/* 499 */     String appContext = null;
/* 500 */     if (!regisID.equals("__0"))
/*     */     {
/* 502 */       if (regisID.startsWith("__1")) {
/* 503 */         appContext = (regisID.length() == 3) ? "" : regisID.substring(3);
/*     */       }
/* 505 */       else if (regisID.startsWith("__2")) {
/* 506 */         layer = (regisID.length() == 3) ? "" : regisID.substring(3);
/*     */       }
/* 508 */       else if (regisID.startsWith("__3")) {
/* 509 */         int ind = regisID.indexOf('_', 3);
/* 510 */         if (regisID.length() > 3 && ind > 0) {
/* 511 */           int n; String numberString = regisID.substring(3, ind);
/*     */           
/*     */           try {
/* 514 */             n = Integer.parseInt(numberString);
/* 515 */           } catch (Exception ex) {
/* 516 */             throw new IllegalArgumentException();
/*     */           } 
/* 518 */           layer = regisID.substring(ind + 1, ind + 1 + n);
/* 519 */           appContext = regisID.substring(ind + 1 + n);
/*     */         } else {
/* 521 */           throw new IllegalArgumentException();
/*     */         } 
/*     */       } else {
/* 524 */         throw new IllegalArgumentException();
/*     */       } 
/*     */     }
/* 527 */     return new String[] { layer, appContext };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AuthConfigProvider _constructProvider(String className, Map properties, AuthConfigFactory factory) {
/* 534 */     AuthConfigProvider provider = null;
/* 535 */     if (className != null) {
/*     */       try {
/* 537 */         if (this.loader == null) {
/* 538 */           this.loader = Thread.currentThread().getContextClassLoader();
/*     */         }
/* 540 */         Class<?> c = Class.forName(className, true, this.loader);
/* 541 */         Constructor<AuthConfigProvider> constr = (Constructor)c.getConstructor(new Class[] { Map.class, AuthConfigFactory.class });
/*     */         
/* 543 */         provider = constr.newInstance(new Object[] { properties, factory });
/*     */       }
/* 545 */       catch (Exception ex) {
/* 546 */         if (logger.isLoggable(Level.FINE)) {
/* 547 */           logger.log(Level.FINE, "Cannot load AuthConfigProvider: " + className, ex);
/*     */         }
/* 549 */         else if (logger.isLoggable(Level.WARNING)) {
/* 550 */           logger.log(Level.WARNING, LogStringsMessages.WSITPVD_0060_JMAC_JMAC_FACTORY_UNABLETO_LOAD_PROVIDER(className), (Object[])new String[] { className, ex.toString() });
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 556 */     return provider;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String _register(AuthConfigProvider provider, Map<String, String> properties, String layer, String appContext, String description, boolean persist) {
/* 567 */     String regisID = getRegistrationID(layer, appContext);
/* 568 */     AuthConfigFactory.RegistrationContext rc = new RegistrationContextImpl(layer, appContext, description, persist);
/*     */     
/* 570 */     AuthConfigFactory.RegistrationContext prevRegisContext = null;
/* 571 */     List<RegistrationListener> listeners = null;
/* 572 */     wLock.lock();
/*     */     try {
/* 574 */       prevRegisContext = id2RegisContextMap.get(regisID);
/* 575 */       AuthConfigProvider prevProvider = id2ProviderMap.get(regisID);
/* 576 */       id2ProviderMap.put(regisID, provider);
/* 577 */       id2RegisContextMap.put(regisID, rc);
/*     */       
/* 579 */       if (prevProvider != null) {
/* 580 */         List<String> prevRegisIDs = provider2IdsMap.get(prevProvider);
/* 581 */         prevRegisIDs.remove(regisID);
/* 582 */         if (!prevProvider.equals(provider) && prevRegisIDs.size() == 0)
/*     */         {
/* 584 */           provider2IdsMap.remove(prevProvider);
/*     */         }
/*     */       } 
/* 587 */       List<String> regisIDs = provider2IdsMap.get(provider);
/* 588 */       if (regisIDs == null) {
/* 589 */         regisIDs = new ArrayList<String>();
/* 590 */         provider2IdsMap.put(provider, regisIDs);
/*     */       } 
/* 592 */       regisIDs.add(regisID);
/*     */       
/* 594 */       if ((provider != null && !provider.equals(prevProvider)) || (provider == null && prevProvider != null))
/*     */       {
/* 596 */         listeners = id2RegisListenersMap.get(regisID);
/*     */       }
/*     */     } finally {
/* 599 */       wLock.unlock();
/* 600 */       if (persist) {
/* 601 */         _storeRegistration(regisID, rc, provider, properties);
/* 602 */       } else if (prevRegisContext != null && prevRegisContext.isPersistent()) {
/* 603 */         _deleteStoredRegistration(regisID, prevRegisContext);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 608 */     if (listeners != null && listeners.size() > 0) {
/* 609 */       for (RegistrationListener listener : listeners) {
/* 610 */         listener.notify(layer, appContext);
/*     */       }
/*     */     }
/*     */     
/* 614 */     return regisID;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean _unRegister(String regisID) {
/* 619 */     boolean rvalue = false;
/* 620 */     AuthConfigFactory.RegistrationContext rc = null;
/* 621 */     List<RegistrationListener> listeners = null;
/* 622 */     String[] dIds = decomposeRegisID(regisID);
/* 623 */     wLock.lock();
/*     */     try {
/* 625 */       rc = id2RegisContextMap.remove(regisID);
/* 626 */       AuthConfigProvider provider = id2ProviderMap.remove(regisID);
/* 627 */       List<String> regisIDs = provider2IdsMap.get(provider);
/* 628 */       if (regisIDs != null) {
/* 629 */         regisIDs.remove(regisID);
/*     */       }
/* 631 */       if (regisIDs == null || regisIDs.size() == 0) {
/* 632 */         provider2IdsMap.remove(provider);
/*     */       }
/*     */       
/* 635 */       listeners = id2RegisListenersMap.remove(regisID);
/* 636 */       rvalue = (provider != null);
/*     */     } finally {
/* 638 */       wLock.unlock();
/* 639 */       if (rc != null && rc.isPersistent()) {
/* 640 */         _deleteStoredRegistration(regisID, rc);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 645 */     if (listeners != null && listeners.size() > 0) {
/* 646 */       for (RegistrationListener listener : listeners) {
/* 647 */         listener.notify(dIds[0], dIds[1]);
/*     */       }
/*     */     }
/*     */     
/* 651 */     return rvalue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void _loadFactory() {
/* 660 */     wLock.lock();
/*     */     try {
/* 662 */       id2ProviderMap = new HashMap<String, AuthConfigProvider>();
/* 663 */       id2RegisContextMap = new HashMap<String, AuthConfigFactory.RegistrationContext>();
/* 664 */       id2RegisListenersMap = new HashMap<String, List<RegistrationListener>>();
/*     */       
/* 666 */       provider2IdsMap = new HashMap<AuthConfigProvider, List<String>>();
/*     */     } finally {
/* 668 */       wLock.unlock();
/*     */     } 
/*     */     try {
/* 671 */       for (EntryInfo info : regStore.getPersistedEntries()) {
/* 672 */         if (info.isConstructorEntry()) {
/* 673 */           _constructProvider(info.getClassName(), info.getProperties(), this);
/*     */           continue;
/*     */         } 
/* 676 */         for (AuthConfigFactory.RegistrationContext ctx : info.getRegContexts()) {
/* 677 */           registerConfigProvider(info.getClassName(), info.getProperties(), ctx.getMessageLayer(), ctx.getAppContext(), ctx.getDescription());
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 683 */     catch (Exception e) {
/* 684 */       if (logger.isLoggable(Level.WARNING)) {
/* 685 */         logger.log(Level.WARNING, LogStringsMessages.WSITPVD_0061_JMAC_AUTHCONFIG_LOADER_FAILURE());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _storeRegistration(String regId, AuthConfigFactory.RegistrationContext ctx, AuthConfigProvider p, Map<String, String> properties) {
/* 695 */     String className = null;
/* 696 */     if (p != null) {
/* 697 */       className = p.getClass().getName();
/*     */     }
/* 699 */     if (ctx.isPersistent()) {
/* 700 */       regStore.store(className, ctx, properties);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void _deleteStoredRegistration(String regId, AuthConfigFactory.RegistrationContext ctx) {
/* 707 */     if (ctx.isPersistent())
/* 708 */       regStore.delete(ctx); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\JMACAuthConfigFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */