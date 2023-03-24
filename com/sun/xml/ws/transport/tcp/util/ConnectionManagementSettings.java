/*     */ package com.sun.xml.ws.transport.tcp.util;
/*     */ 
/*     */ import com.sun.xml.ws.transport.tcp.resources.MessagesMessages;
/*     */ import java.lang.reflect.Method;
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
/*     */ public class ConnectionManagementSettings
/*     */ {
/*  54 */   private static final Logger logger = Logger.getLogger("com.sun.metro.transport.tcp");
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_VALUE = -1;
/*     */   
/*  59 */   private int highWatermark = -1;
/*  60 */   private int maxParallelConnections = -1;
/*  61 */   private int numberToReclaim = -1;
/*     */   
/*     */   private static volatile ConnectionManagementSettingsHolder holder;
/*     */   
/*     */   public static ConnectionManagementSettingsHolder getSettingsHolder() {
/*  66 */     if (holder == null) {
/*  67 */       synchronized (ConnectionManagementSettings.class) {
/*  68 */         if (holder == null && 
/*  69 */           !createDefaultHolder()) {
/*  70 */           holder = new SystemPropsConnectionManagementSettingsHolder();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  75 */     return holder;
/*     */   }
/*     */   
/*     */   public static void setSettingsHolder(ConnectionManagementSettingsHolder holder) {
/*  79 */     ConnectionManagementSettings.holder = holder;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionManagementSettings(int highWatermark, int maxParallelConnections, int numberToReclaim) {
/*  85 */     this.highWatermark = (highWatermark != -1) ? highWatermark : 1500;
/*     */     
/*  87 */     this.maxParallelConnections = (maxParallelConnections != -1) ? maxParallelConnections : 5;
/*     */     
/*  89 */     this.numberToReclaim = (numberToReclaim != -1) ? numberToReclaim : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ConnectionManagementSettings(int highWatermark, int numberToReclaim) {
/*  95 */     this.highWatermark = (highWatermark != -1) ? highWatermark : 1500;
/*     */     
/*  97 */     this.maxParallelConnections = -1;
/*  98 */     this.numberToReclaim = (numberToReclaim != -1) ? numberToReclaim : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHighWatermark() {
/* 103 */     return this.highWatermark;
/*     */   }
/*     */   
/*     */   public int getMaxParallelConnections() {
/* 107 */     return this.maxParallelConnections;
/*     */   }
/*     */   
/*     */   public int getNumberToReclaim() {
/* 111 */     return this.numberToReclaim;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean createDefaultHolder() {
/* 121 */     boolean isOk = true;
/*     */     try {
/* 123 */       Class<?> policyHolderClass = Class.forName("com.sun.xml.ws.transport.tcp.wsit.PolicyConnectionManagementSettingsHolder");
/*     */       
/* 125 */       Method getSingltonMethod = policyHolderClass.getMethod("getInstance", new Class[0]);
/* 126 */       holder = (ConnectionManagementSettingsHolder)getSingltonMethod.invoke(null, new Object[0]);
/* 127 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1150_CON_MNGMNT_SETTINGS_POLICY());
/* 128 */     } catch (Exception e) {
/* 129 */       logger.log(Level.FINE, MessagesMessages.WSTCP_1151_CON_MNGMNT_SETTINGS_SYST_PROPS());
/* 130 */       isOk = false;
/*     */     } 
/*     */     
/* 133 */     return isOk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SystemPropsConnectionManagementSettingsHolder
/*     */     implements ConnectionManagementSettingsHolder
/*     */   {
/*     */     private volatile ConnectionManagementSettings clientSettings;
/*     */ 
/*     */ 
/*     */     
/*     */     private volatile ConnectionManagementSettings serverSettings;
/*     */ 
/*     */ 
/*     */     
/*     */     private SystemPropsConnectionManagementSettingsHolder() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public ConnectionManagementSettings getClientSettings() {
/* 155 */       if (this.clientSettings == null) {
/* 156 */         synchronized (this) {
/* 157 */           if (this.clientSettings == null) {
/* 158 */             this.clientSettings = createSettings(true);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 163 */       return this.clientSettings;
/*     */     }
/*     */     
/*     */     public ConnectionManagementSettings getServerSettings() {
/* 167 */       if (this.serverSettings == null) {
/* 168 */         synchronized (this) {
/* 169 */           if (this.serverSettings == null) {
/* 170 */             this.serverSettings = createSettings(false);
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 175 */       return this.serverSettings;
/*     */     }
/*     */     
/*     */     private static ConnectionManagementSettings createSettings(boolean isClient) {
/* 179 */       int highWatermark = Integer.getInteger("high-water-mark", -1).intValue();
/*     */ 
/*     */       
/* 182 */       int maxParallelConnections = Integer.getInteger("max-parallel-connections", -1).intValue();
/*     */ 
/*     */       
/* 185 */       int numberToReclaim = Integer.getInteger("number-to-reclaim", -1).intValue();
/*     */ 
/*     */ 
/*     */       
/* 189 */       ConnectionManagementSettings settings = null;
/* 190 */       if (isClient) {
/* 191 */         settings = new ConnectionManagementSettings(highWatermark, maxParallelConnections, numberToReclaim);
/*     */       } else {
/*     */         
/* 194 */         settings = new ConnectionManagementSettings(highWatermark, numberToReclaim);
/*     */       } 
/*     */ 
/*     */       
/* 198 */       return settings;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface ConnectionManagementSettingsHolder {
/*     */     ConnectionManagementSettings getClientSettings();
/*     */     
/*     */     ConnectionManagementSettings getServerSettings();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tc\\util\ConnectionManagementSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */