/*     */ package org.glassfish.external.probe.provider;
/*     */ 
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StatsProviderManager
/*     */ {
/*     */   static StatsProviderManagerDelegate spmd;
/*     */   
/*     */   public static boolean register(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider) {
/*  57 */     return register(pp, configElement, subTreeRoot, statsProvider, (String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean register(PluginPoint pp, String configElement, String subTreeRoot, Object statsProvider, String invokerId) {
/*  63 */     StatsProviderInfo spInfo = new StatsProviderInfo(configElement, pp, subTreeRoot, statsProvider, invokerId);
/*     */ 
/*     */     
/*  66 */     return registerStatsProvider(spInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean register(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider, String configLevelStr) {
/*  72 */     return register(configElement, pp, subTreeRoot, statsProvider, configLevelStr, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean register(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider, String configLevelStr, String invokerId) {
/*  78 */     StatsProviderInfo spInfo = new StatsProviderInfo(configElement, pp, subTreeRoot, statsProvider, invokerId);
/*     */     
/*  80 */     spInfo.setConfigLevel(configLevelStr);
/*     */     
/*  82 */     return registerStatsProvider(spInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean registerStatsProvider(StatsProviderInfo spInfo) {
/*  87 */     if (spmd == null) {
/*     */       
/*  89 */       toBeRegistered.add(spInfo);
/*     */     } else {
/*  91 */       spmd.register(spInfo);
/*  92 */       return true;
/*     */     } 
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean unregister(Object statsProvider) {
/*  99 */     if (spmd == null) {
/* 100 */       for (StatsProviderInfo spInfo : toBeRegistered) {
/* 101 */         if (spInfo.getStatsProvider() == statsProvider) {
/* 102 */           toBeRegistered.remove(spInfo);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 108 */       spmd.unregister(statsProvider);
/* 109 */       return true;
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasListeners(String probeStr) {
/* 117 */     if (spmd == null) {
/* 118 */       return false;
/*     */     }
/* 120 */     return spmd.hasListeners(probeStr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStatsProviderManagerDelegate(StatsProviderManagerDelegate lspmd) {
/* 128 */     if (lspmd == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 134 */     spmd = lspmd;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     for (StatsProviderInfo spInfo : toBeRegistered) {
/* 140 */       spmd.register(spInfo);
/*     */     }
/*     */ 
/*     */     
/* 144 */     toBeRegistered.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 149 */   static Vector<StatsProviderInfo> toBeRegistered = new Vector<StatsProviderInfo>();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\probe\provider\StatsProviderManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */