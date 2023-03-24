/*     */ package com.sun.xml.ws.api.tx.at;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.FeatureConstructor;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransactionalFeature
/*     */   extends WebServiceFeature
/*     */ {
/*  64 */   private static final Logger LOGGER = Logger.getLogger(TransactionalFeature.class);
/*     */ 
/*     */   
/*     */   public static final String ID = "http://docs.oasis-open.org/ws-tx/";
/*     */   
/*  69 */   private Transactional.TransactionFlowType flowType = Transactional.TransactionFlowType.SUPPORTS;
/*     */   private boolean isExplicitMode;
/*  71 */   private Transactional.Version version = Transactional.Version.DEFAULT;
/*  72 */   private Map<String, Transactional.TransactionFlowType> flowTypeMap = new HashMap<String, Transactional.TransactionFlowType>();
/*  73 */   private Map<String, Boolean> enabledMap = new HashMap<String, Boolean>();
/*     */   
/*     */   @FeatureConstructor({"enabled", "value", "version"})
/*     */   public TransactionalFeature(boolean enabled, Transactional.TransactionFlowType value, Transactional.Version version) {
/*  77 */     LOGGER.entering(new Object[] { Boolean.valueOf(enabled), value, version });
/*     */     
/*  79 */     this.enabled = enabled;
/*  80 */     this.flowType = value;
/*  81 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionalFeature() {
/*  89 */     LOGGER.entering();
/*     */     
/*  91 */     this.enabled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransactionalFeature(boolean enabled) {
/* 101 */     LOGGER.entering(new Object[] { Boolean.valueOf(enabled) });
/*     */     
/* 103 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transactional.TransactionFlowType getFlowType() {
/* 111 */     return this.flowType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transactional.TransactionFlowType getFlowType(String operationName) {
/* 119 */     Transactional.TransactionFlowType type = this.flowTypeMap.get(operationName);
/* 120 */     if (!this.isExplicitMode && type == null) {
/* 121 */       type = this.flowType;
/*     */     }
/* 123 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlowType(Transactional.TransactionFlowType flowType) {
/* 131 */     this.flowType = flowType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlowType(String operationName, Transactional.TransactionFlowType flowType) {
/* 140 */     this.flowTypeMap.put(operationName, flowType);
/*     */   }
/*     */   
/*     */   public String getID() {
/* 144 */     return "http://docs.oasis-open.org/ws-tx/";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 153 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabled(String operationName, boolean enabled) {
/* 163 */     this.enabledMap.put(operationName, Boolean.valueOf(enabled));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled(String operationName) {
/* 173 */     Boolean isEnabled = this.enabledMap.get(operationName);
/* 174 */     if (isEnabled == null) {
/* 175 */       return this.isExplicitMode ? false : this.enabled;
/*     */     }
/* 177 */     return isEnabled.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transactional.Version getVersion() {
/* 187 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVersion(Transactional.Version version) {
/* 196 */     this.version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Transactional.TransactionFlowType> getFlowTypeMap() {
/* 204 */     return this.flowTypeMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Boolean> getEnabledMap() {
/* 212 */     return this.enabledMap;
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
/*     */   public boolean isExplicitMode() {
/* 224 */     return this.isExplicitMode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExplicitMode(boolean explicitMode) {
/* 232 */     this.isExplicitMode = explicitMode;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\tx\at\TransactionalFeature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */