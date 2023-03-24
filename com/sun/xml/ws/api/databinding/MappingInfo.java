/*     */ package com.sun.xml.ws.api.databinding;
/*     */ 
/*     */ import com.sun.xml.ws.api.BindingID;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MappingInfo
/*     */ {
/*     */   protected String targetNamespace;
/*     */   protected String databindingMode;
/*     */   protected SoapBodyStyle soapBodyStyle;
/*     */   protected BindingID bindingID;
/*     */   protected QName serviceName;
/*     */   protected QName portName;
/*     */   protected String defaultSchemaNamespaceSuffix;
/*     */   
/*     */   public String getTargetNamespace() {
/*  66 */     return this.targetNamespace;
/*     */   }
/*     */   public void setTargetNamespace(String targetNamespace) {
/*  69 */     this.targetNamespace = targetNamespace;
/*     */   }
/*     */   public String getDatabindingMode() {
/*  72 */     return this.databindingMode;
/*     */   }
/*     */   public void setDatabindingMode(String databindingMode) {
/*  75 */     this.databindingMode = databindingMode;
/*     */   }
/*     */   public SoapBodyStyle getSoapBodyStyle() {
/*  78 */     return this.soapBodyStyle;
/*     */   }
/*     */   public void setSoapBodyStyle(SoapBodyStyle soapBodyStyle) {
/*  81 */     this.soapBodyStyle = soapBodyStyle;
/*     */   }
/*     */   public BindingID getBindingID() {
/*  84 */     return this.bindingID;
/*     */   }
/*     */   public void setBindingID(BindingID bindingID) {
/*  87 */     this.bindingID = bindingID;
/*     */   }
/*     */   public QName getServiceName() {
/*  90 */     return this.serviceName;
/*     */   }
/*     */   public void setServiceName(QName serviceName) {
/*  93 */     this.serviceName = serviceName;
/*     */   }
/*     */   public QName getPortName() {
/*  96 */     return this.portName;
/*     */   }
/*     */   public void setPortName(QName portName) {
/*  99 */     this.portName = portName;
/*     */   }
/*     */   public String getDefaultSchemaNamespaceSuffix() {
/* 102 */     return this.defaultSchemaNamespaceSuffix;
/*     */   }
/*     */   public void setDefaultSchemaNamespaceSuffix(String defaultSchemaNamespaceSuffix) {
/* 105 */     this.defaultSchemaNamespaceSuffix = defaultSchemaNamespaceSuffix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\MappingInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */