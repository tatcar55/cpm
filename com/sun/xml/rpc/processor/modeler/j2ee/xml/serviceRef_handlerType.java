/*     */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class serviceRef_handlerType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setHandlerName(String handlerName) {
/*  43 */     setElementValue("handler-name", handlerName);
/*     */   }
/*     */   
/*     */   public String getHandlerName() {
/*  47 */     return getElementValue("handler-name");
/*     */   }
/*     */   
/*     */   public boolean removeHandlerName() {
/*  51 */     return removeElement("handler-name");
/*     */   }
/*     */   
/*     */   public void setHandlerClass(String handlerClass) {
/*  55 */     setElementValue("handler-class", handlerClass);
/*     */   }
/*     */   
/*     */   public String getHandlerClass() {
/*  59 */     return getElementValue("handler-class");
/*     */   }
/*     */   
/*     */   public boolean removeHandlerClass() {
/*  63 */     return removeElement("handler-class");
/*     */   }
/*     */   
/*     */   public void setInitParam(int index, String initParam) {
/*  67 */     setElementValue(index, "init-param", initParam);
/*     */   }
/*     */   
/*     */   public String getInitParam(int index) {
/*  71 */     return getElementValue("init-param", index);
/*     */   }
/*     */   
/*     */   public int getInitParamCount() {
/*  75 */     return sizeOfElement("init-param");
/*     */   }
/*     */   
/*     */   public boolean removeInitParam(int index) {
/*  79 */     return removeElement(index, "init-param");
/*     */   }
/*     */   
/*     */   public void setSoapHeader(int index, String soapHeader) {
/*  83 */     setElementValue(index, "soap-header", soapHeader);
/*     */   }
/*     */   
/*     */   public String getSoapHeader(int index) {
/*  87 */     return getElementValue("soap-header", index);
/*     */   }
/*     */   
/*     */   public int getSoapHeaderCount() {
/*  91 */     return sizeOfElement("soap-header");
/*     */   }
/*     */   
/*     */   public boolean removeSoapHeader(int index) {
/*  95 */     return removeElement(index, "soap-header");
/*     */   }
/*     */   
/*     */   public void setSoapRole(int index, String soapRole) {
/*  99 */     setElementValue(index, "soap-role", soapRole);
/*     */   }
/*     */   
/*     */   public String getSoapRole(int index) {
/* 103 */     return getElementValue("soap-role", index);
/*     */   }
/*     */   
/*     */   public int getSoapRoleCount() {
/* 107 */     return sizeOfElement("soap-role");
/*     */   }
/*     */   
/*     */   public boolean removeSoapRole(int index) {
/* 111 */     return removeElement(index, "soap-role");
/*     */   }
/*     */   
/*     */   public void setPortName(int index, String portName) {
/* 115 */     setElementValue(index, "port-name", portName);
/*     */   }
/*     */   
/*     */   public String getPortName(int index) {
/* 119 */     return getElementValue("port-name", index);
/*     */   }
/*     */   
/*     */   public int getPortNameCount() {
/* 123 */     return sizeOfElement("port-name");
/*     */   }
/*     */   
/*     */   public boolean removePortName(int index) {
/* 127 */     return removeElement(index, "port-name");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 131 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 135 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 139 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\serviceRef_handlerType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */