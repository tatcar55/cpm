/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class BindingInfo
/*     */ {
/*     */   private String databindingMode;
/*     */   private String defaultNamespace;
/*  58 */   private Collection<Class> contentClasses = (Collection)new ArrayList<Class<?>>();
/*  59 */   private Collection<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
/*  60 */   private Map<Class, Class> subclassReplacements = (Map)new HashMap<Class<?>, Class<?>>();
/*  61 */   private Map<String, Object> properties = new HashMap<String, Object>();
/*     */   
/*     */   protected ClassLoader classLoader;
/*     */   private SEIModel seiModel;
/*     */   
/*     */   public String getDatabindingMode() {
/*  67 */     return this.databindingMode;
/*     */   }
/*     */   public void setDatabindingMode(String databindingMode) {
/*  70 */     this.databindingMode = databindingMode;
/*     */   }
/*     */   
/*     */   public String getDefaultNamespace() {
/*  74 */     return this.defaultNamespace;
/*     */   }
/*     */   public void setDefaultNamespace(String defaultNamespace) {
/*  77 */     this.defaultNamespace = defaultNamespace;
/*     */   }
/*     */   
/*     */   public Collection<Class> contentClasses() {
/*  81 */     return this.contentClasses;
/*     */   }
/*     */   public Collection<TypeInfo> typeInfos() {
/*  84 */     return this.typeInfos;
/*     */   }
/*     */   public Map<Class, Class> subclassReplacements() {
/*  87 */     return this.subclassReplacements;
/*     */   }
/*     */   public Map<String, Object> properties() {
/*  90 */     return this.properties;
/*     */   }
/*     */   
/*     */   public SEIModel getSEIModel() {
/*  94 */     return this.seiModel;
/*     */   }
/*     */   public void setSEIModel(SEIModel seiModel) {
/*  97 */     this.seiModel = seiModel;
/*     */   }
/*     */   public ClassLoader getClassLoader() {
/* 100 */     return this.classLoader;
/*     */   }
/*     */   public void setClassLoader(ClassLoader classLoader) {
/* 103 */     this.classLoader = classLoader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\BindingInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */