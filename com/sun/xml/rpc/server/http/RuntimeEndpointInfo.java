/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo;
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
/*     */ 
/*     */ public class RuntimeEndpointInfo
/*     */   implements RuntimeEndpointInfo
/*     */ {
/*     */   private Class remoteInterface;
/*     */   private Class implementationClass;
/*     */   private Class tieClass;
/*     */   private String name;
/*     */   private Exception exception;
/*     */   private QName portName;
/*     */   private QName serviceName;
/*     */   private String modelFileName;
/*     */   private String wsdlFileName;
/*     */   private boolean deployed;
/*     */   private String urlPattern;
/*     */   
/*     */   public Class getRemoteInterface() {
/*  41 */     return this.remoteInterface;
/*     */   }
/*     */   
/*     */   public void setRemoteInterface(Class klass) {
/*  45 */     this.remoteInterface = klass;
/*     */   }
/*     */   
/*     */   public Class getImplementationClass() {
/*  49 */     return this.implementationClass;
/*     */   }
/*     */   
/*     */   public void setImplementationClass(Class klass) {
/*  53 */     this.implementationClass = klass;
/*     */   }
/*     */   
/*     */   public Class getTieClass() {
/*  57 */     return this.tieClass;
/*     */   }
/*     */   
/*     */   public void setTieClass(Class klass) {
/*  61 */     this.tieClass = klass;
/*     */   }
/*     */   
/*     */   public Exception getException() {
/*  65 */     return this.exception;
/*     */   }
/*     */   
/*     */   public void setException(Exception e) {
/*  69 */     this.exception = e;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  73 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String s) {
/*  77 */     this.name = s;
/*     */   }
/*     */   
/*     */   public String getModelFileName() {
/*  81 */     return this.modelFileName;
/*     */   }
/*     */   
/*     */   public void setModelFileName(String s) {
/*  85 */     this.modelFileName = s;
/*     */   }
/*     */   
/*     */   public String getWSDLFileName() {
/*  89 */     return this.wsdlFileName;
/*     */   }
/*     */   
/*     */   public void setWSDLFileName(String s) {
/*  93 */     this.wsdlFileName = s;
/*     */   }
/*     */   
/*     */   public boolean isDeployed() {
/*  97 */     return this.deployed;
/*     */   }
/*     */   
/*     */   public void setDeployed(boolean b) {
/* 101 */     this.deployed = b;
/*     */   }
/*     */   
/*     */   public QName getPortName() {
/* 105 */     return this.portName;
/*     */   }
/*     */   
/*     */   public void setPortName(QName n) {
/* 109 */     this.portName = n;
/*     */   }
/*     */   
/*     */   public QName getServiceName() {
/* 113 */     return this.serviceName;
/*     */   }
/*     */   
/*     */   public void setServiceName(QName n) {
/* 117 */     this.serviceName = n;
/*     */   }
/*     */   
/*     */   public String getUrlPattern() {
/* 121 */     return this.urlPattern;
/*     */   }
/*     */   
/*     */   public void setUrlPattern(String s) {
/* 125 */     this.urlPattern = s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\RuntimeEndpointInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */