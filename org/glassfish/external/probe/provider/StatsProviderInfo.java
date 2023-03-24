/*    */ package org.glassfish.external.probe.provider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatsProviderInfo
/*    */ {
/*    */   private String configElement;
/*    */   private PluginPoint pp;
/*    */   private String subTreeRoot;
/*    */   private Object statsProvider;
/*    */   private String configLevelStr;
/*    */   private final String invokerId;
/*    */   
/*    */   public StatsProviderInfo(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider) {
/* 51 */     this(configElement, pp, subTreeRoot, statsProvider, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatsProviderInfo(String configElement, PluginPoint pp, String subTreeRoot, Object statsProvider, String invokerId) {
/* 68 */     this.configLevelStr = null; this.configElement = configElement;
/*    */     this.pp = pp;
/*    */     this.subTreeRoot = subTreeRoot;
/*    */     this.statsProvider = statsProvider;
/* 72 */     this.invokerId = invokerId; } public String getConfigElement() { return this.configElement; }
/*    */ 
/*    */   
/*    */   public PluginPoint getPluginPoint() {
/* 76 */     return this.pp;
/*    */   }
/*    */   
/*    */   public String getSubTreeRoot() {
/* 80 */     return this.subTreeRoot;
/*    */   }
/*    */   
/*    */   public Object getStatsProvider() {
/* 84 */     return this.statsProvider;
/*    */   }
/*    */   
/*    */   public String getConfigLevel() {
/* 88 */     return this.configLevelStr;
/*    */   }
/*    */   
/*    */   public void setConfigLevel(String configLevelStr) {
/* 92 */     this.configLevelStr = configLevelStr;
/*    */   }
/*    */   
/*    */   public String getInvokerId() {
/* 96 */     return this.invokerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\probe\provider\StatsProviderInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */