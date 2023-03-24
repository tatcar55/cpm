/*     */ package com.sun.xml.rpc.processor.config;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*     */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ModelInfo
/*     */   implements ModelInfo
/*     */ {
/*     */   private Configuration _parent;
/*     */   private String _name;
/*     */   private String _javaPackageName;
/*     */   private TypeMappingRegistryInfo _typeMappingRegistryInfo;
/*     */   private HandlerChainInfo _clientHandlerChainInfo;
/*     */   private HandlerChainInfo _serverHandlerChainInfo;
/*     */   private NamespaceMappingRegistryInfo _namespaceMappingRegistryInfo;
/*     */   
/*     */   public Configuration getParent() {
/*  43 */     return this._parent;
/*     */   }
/*     */   
/*     */   public void setParent(Configuration c) {
/*  47 */     this._parent = c;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  51 */     return this._name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  55 */     this._name = s;
/*     */   }
/*     */   
/*     */   public Configuration getConfiguration() {
/*  59 */     return this._parent;
/*     */   }
/*     */   
/*     */   public TypeMappingRegistryInfo getTypeMappingRegistry() {
/*  63 */     return this._typeMappingRegistryInfo;
/*     */   }
/*     */   
/*     */   public void setTypeMappingRegistry(TypeMappingRegistryInfo i) {
/*  67 */     this._typeMappingRegistryInfo = i;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getClientHandlerChainInfo() {
/*  71 */     return this._clientHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setClientHandlerChainInfo(HandlerChainInfo i) {
/*  75 */     this._clientHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getServerHandlerChainInfo() {
/*  79 */     return this._serverHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setServerHandlerChainInfo(HandlerChainInfo i) {
/*  83 */     this._serverHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public NamespaceMappingRegistryInfo getNamespaceMappingRegistry() {
/*  87 */     return this._namespaceMappingRegistryInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceMappingRegistry(NamespaceMappingRegistryInfo i) {
/*  93 */     this._namespaceMappingRegistryInfo = (NamespaceMappingRegistryInfo)i;
/*     */   }
/*     */   
/*     */   public String getJavaPackageName() {
/*  97 */     return this._javaPackageName;
/*     */   }
/*     */   
/*     */   public void setJavaPackageName(String s) {
/* 101 */     this._javaPackageName = s;
/*     */   }
/*     */   
/*     */   public Model buildModel(Properties options) {
/* 105 */     return getModeler(options).buildModel();
/*     */   }
/*     */   
/*     */   protected abstract Modeler getModeler(Properties paramProperties);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\ModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */