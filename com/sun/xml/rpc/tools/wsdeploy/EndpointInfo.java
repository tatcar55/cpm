/*     */ package com.sun.xml.rpc.tools.wsdeploy;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointInfo
/*     */ {
/*     */   private String name;
/*     */   private String displayName;
/*     */   private String description;
/*     */   private String interfaceClass;
/*     */   private String implementationClass;
/*     */   private String model;
/*     */   private HandlerChainInfo clientHandlerChainInfo;
/*     */   private HandlerChainInfo serverHandlerChainInfo;
/*     */   private boolean runtimeDeployed;
/*     */   private String runtimeModel;
/*     */   private String runtimeWSDL;
/*     */   private String runtimeTie;
/*     */   private QName runtimeServiceName;
/*     */   private QName runtimePortName;
/*     */   private String runtimeUrlPattern;
/*     */   
/*     */   public String getName() {
/*  43 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  47 */     this.name = s;
/*     */   }
/*     */   
/*     */   public String getDisplayName() {
/*  51 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public void setDisplayName(String s) {
/*  55 */     this.displayName = s;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  59 */     return this.description;
/*     */   }
/*     */   
/*     */   public void setDescription(String s) {
/*  63 */     this.description = s;
/*     */   }
/*     */   
/*     */   public String getInterface() {
/*  67 */     return this.interfaceClass;
/*     */   }
/*     */   
/*     */   public void setInterface(String s) {
/*  71 */     this.interfaceClass = s;
/*     */   }
/*     */   
/*     */   public String getImplementation() {
/*  75 */     return this.implementationClass;
/*     */   }
/*     */   
/*     */   public void setImplementation(String s) {
/*  79 */     this.implementationClass = s;
/*     */   }
/*     */   
/*     */   public String getModel() {
/*  83 */     return this.model;
/*     */   }
/*     */   
/*     */   public void setModel(String s) {
/*  87 */     this.model = s;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getClientHandlerChainInfo() {
/*  91 */     return this.clientHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setClientHandlerChainInfo(HandlerChainInfo i) {
/*  95 */     this.clientHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public HandlerChainInfo getServerHandlerChainInfo() {
/*  99 */     return this.serverHandlerChainInfo;
/*     */   }
/*     */   
/*     */   public void setServerHandlerChainInfo(HandlerChainInfo i) {
/* 103 */     this.serverHandlerChainInfo = i;
/*     */   }
/*     */   
/*     */   public boolean isRuntimeDeployed() {
/* 107 */     return this.runtimeDeployed;
/*     */   }
/*     */   
/*     */   public void setRuntimeDeployed(boolean b) {
/* 111 */     this.runtimeDeployed = b;
/*     */   }
/*     */   
/*     */   public String getRuntimeModel() {
/* 115 */     return this.runtimeModel;
/*     */   }
/*     */   
/*     */   public void setRuntimeModel(String s) {
/* 119 */     this.runtimeModel = s;
/*     */   }
/*     */   
/*     */   public String getRuntimeWSDL() {
/* 123 */     return this.runtimeWSDL;
/*     */   }
/*     */   
/*     */   public void setRuntimeWSDL(String s) {
/* 127 */     this.runtimeWSDL = s;
/*     */   }
/*     */   
/*     */   public String getRuntimeTie() {
/* 131 */     return this.runtimeTie;
/*     */   }
/*     */   
/*     */   public void setRuntimeTie(String s) {
/* 135 */     this.runtimeTie = s;
/*     */   }
/*     */   
/*     */   public QName getRuntimeServiceName() {
/* 139 */     return this.runtimeServiceName;
/*     */   }
/*     */   
/*     */   public void setRuntimeServiceName(QName n) {
/* 143 */     this.runtimeServiceName = n;
/*     */   }
/*     */   
/*     */   public QName getRuntimePortName() {
/* 147 */     return this.runtimePortName;
/*     */   }
/*     */   
/*     */   public void setRuntimePortName(QName n) {
/* 151 */     this.runtimePortName = n;
/*     */   }
/*     */   
/*     */   public String getRuntimeUrlPattern() {
/* 155 */     return this.runtimeUrlPattern;
/*     */   }
/*     */   
/*     */   public void setRuntimeUrlPattern(String s) {
/* 159 */     this.runtimeUrlPattern = s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\EndpointInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */