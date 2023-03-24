/*     */ package com.sun.xml.ws.tx.dev;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WSATRuntimeConfig
/*     */ {
/*  47 */   private static final Logger LOGGER = Logger.getLogger(WSATRuntimeConfig.class);
/*  48 */   private static final Lock DATA_LOCK = new ReentrantLock();
/*     */   
/*     */   private static WSATRuntimeConfig instance;
/*     */   
/*     */   public static final class Initializer
/*     */   {
/*     */     private Initializer() {}
/*     */     
/*     */     public Initializer domainName(String value) {
/*  57 */       WSATRuntimeConfig.domainName = value;
/*     */       
/*  59 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer hostName(String value) {
/*  63 */       WSATRuntimeConfig.hostName = value;
/*     */       
/*  65 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer httpPort(String value) {
/*  69 */       if (value != null && value.trim().length() > 0) {
/*  70 */         WSATRuntimeConfig.httpPort = Integer.parseInt(value.trim());
/*     */       } else {
/*  72 */         WSATRuntimeConfig.LOGGER.config(String.format("Could not set HTTP port value to '%1s'. Rolling back to default: %2d", new Object[] { value, Integer.valueOf(WSATRuntimeConfig.access$200()) }));
/*     */       } 
/*     */       
/*  75 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer httpsPort(String value) {
/*  79 */       if (value != null && value.trim().length() > 0) {
/*  80 */         WSATRuntimeConfig.httpsPort = Integer.parseInt(value.trim());
/*     */       } else {
/*  82 */         WSATRuntimeConfig.LOGGER.config(String.format("Could not set HTTPS port value to '%1s'. Rolling back to default: %2d", new Object[] { value, Integer.valueOf(WSATRuntimeConfig.access$400()) }));
/*     */       } 
/*     */       
/*  85 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer txLogLocation(WSATRuntimeConfig.TxlogLocationProvider provider) {
/*  89 */       WSATRuntimeConfig.txLogLocationProvider = provider;
/*     */       
/*  91 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer enableWsatRecovery(boolean value) {
/*  95 */       WSATRuntimeConfig.isWsatRecoveryEnabled = value;
/*     */       
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer enableWsatSsl(boolean value) {
/* 101 */       WSATRuntimeConfig.isWsatSslEnabled = value;
/*     */       
/* 103 */       return this;
/*     */     }
/*     */     
/*     */     public Initializer enableRollbackOnFailedPrepare(boolean value) {
/* 107 */       WSATRuntimeConfig.isRollbackOnFailedPrepare = value;
/*     */       
/* 109 */       return this;
/*     */     }
/*     */     
/*     */     public void done() {
/* 113 */       WSATRuntimeConfig.DATA_LOCK.unlock();
/*     */     }
/*     */   }
/*     */   
/* 117 */   private static boolean isWsatRecoveryEnabled = Boolean.valueOf(System.getProperty("wsat.recovery.enabled", "true")).booleanValue();
/*     */   private static TxlogLocationProvider txLogLocationProvider;
/* 119 */   private static boolean isWsatSslEnabled = Boolean.valueOf(System.getProperty("wsat.ssl.enabled", "false")).booleanValue();
/* 120 */   private static boolean isRollbackOnFailedPrepare = Boolean.valueOf(System.getProperty("wsat.rollback.on.failed.prepare", "true")).booleanValue();
/* 121 */   private static String domainName = "domain1";
/* 122 */   private static String hostName = "localhost";
/* 123 */   private static int httpPort = 8080;
/* 124 */   private static int httpsPort = 8181;
/*     */ 
/*     */   
/*     */   private static RecoveryEventListener wsatRecoveryEventListener;
/*     */ 
/*     */ 
/*     */   
/*     */   public static Initializer initializer() {
/* 132 */     DATA_LOCK.lock();
/*     */     
/* 134 */     return new Initializer();
/*     */   }
/*     */   
/*     */   public static WSATRuntimeConfig getInstance() {
/* 138 */     DATA_LOCK.lock();
/*     */     try {
/* 140 */       if (instance == null) {
/* 141 */         instance = new WSATRuntimeConfig();
/*     */       }
/* 143 */       return instance;
/*     */     } finally {
/* 145 */       DATA_LOCK.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWSATRecoveryEnabled() {
/* 154 */     return isWsatRecoveryEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostAndPort() {
/* 162 */     return isWsatSslEnabled ? ("https://" + hostName + ":" + httpsPort) : ("http://" + hostName + ":" + httpPort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getDomainName() {
/* 170 */     return domainName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getHostName() {
/* 178 */     return hostName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHttpPort() {
/* 186 */     return httpPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHttpsPort() {
/* 194 */     return httpsPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTxLogLocation() {
/* 202 */     return (txLogLocationProvider == null) ? null : txLogLocationProvider.getTxLogLocation();
/*     */   }
/*     */   
/*     */   public boolean isRollbackOnFailedPrepare() {
/* 206 */     return isRollbackOnFailedPrepare;
/*     */   }
/*     */   
/*     */   public void setWSATRecoveryEventListener(RecoveryEventListener WSATRecoveryEventListener) {
/* 210 */     wsatRecoveryEventListener = WSATRecoveryEventListener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface TxlogLocationProvider
/*     */   {
/*     */     String getTxLogLocation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface RecoveryEventListener
/*     */   {
/*     */     void beforeRecovery(boolean param1Boolean, String param1String);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void afterRecovery(boolean param1Boolean1, boolean param1Boolean2, String param1String);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class WSATRecoveryEventListener
/*     */     implements RecoveryEventListener
/*     */   {
/*     */     public void beforeRecovery(boolean delegated, String instance) {
/* 243 */       if (WSATRuntimeConfig.wsatRecoveryEventListener != null) WSATRuntimeConfig.wsatRecoveryEventListener.beforeRecovery(delegated, instance); 
/*     */     }
/*     */     
/*     */     public void afterRecovery(boolean success, boolean delegated, String instance) {
/* 247 */       if (WSATRuntimeConfig.wsatRecoveryEventListener != null) WSATRuntimeConfig.wsatRecoveryEventListener.afterRecovery(success, delegated, instance); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\dev\WSATRuntimeConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */