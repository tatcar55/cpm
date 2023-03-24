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
/*     */ public class serviceRefType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setServiceRefName(String serviceRefName) {
/*  43 */     setElementValue("service-ref-name", serviceRefName);
/*     */   }
/*     */   
/*     */   public String getServiceRefName() {
/*  47 */     return getElementValue("service-ref-name");
/*     */   }
/*     */   
/*     */   public boolean removeServiceRefName() {
/*  51 */     return removeElement("service-ref-name");
/*     */   }
/*     */   
/*     */   public void setServiceInterface(String serviceInterface) {
/*  55 */     setElementValue("service-interface", serviceInterface);
/*     */   }
/*     */   
/*     */   public String getServiceInterface() {
/*  59 */     return getElementValue("service-interface");
/*     */   }
/*     */   
/*     */   public boolean removeServiceInterface() {
/*  63 */     return removeElement("service-interface");
/*     */   }
/*     */   
/*     */   public void setWsdlFile(String wsdlFile) {
/*  67 */     setElementValue("wsdl-file", wsdlFile);
/*     */   }
/*     */   
/*     */   public String getWsdlFile() {
/*  71 */     return getElementValue("wsdl-file");
/*     */   }
/*     */   
/*     */   public boolean removeWsdlFile() {
/*  75 */     return removeElement("wsdl-file");
/*     */   }
/*     */   
/*     */   public void setJaxrpcMappingFile(String jaxrpcMappingFile) {
/*  79 */     setElementValue("jaxrpc-mapping-file", jaxrpcMappingFile);
/*     */   }
/*     */   
/*     */   public String getJaxrpcMappingFile() {
/*  83 */     return getElementValue("jaxrpc-mapping-file");
/*     */   }
/*     */   
/*     */   public boolean removeJaxrpcMappingFile() {
/*  87 */     return removeElement("jaxrpc-mapping-file");
/*     */   }
/*     */   
/*     */   public void setServiceQname(String serviceQname) {
/*  91 */     setElementValue("service-qname", serviceQname);
/*     */   }
/*     */   
/*     */   public String getServiceQname() {
/*  95 */     return getElementValue("service-qname");
/*     */   }
/*     */   
/*     */   public boolean removeServiceQname() {
/*  99 */     return removeElement("service-qname");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPortComponentRef(int index, portComponentRefType portComponentRef) {
/* 105 */     setElementValue(index, "port-component-ref", portComponentRef);
/*     */   }
/*     */   
/*     */   public portComponentRefType getPortComponentRef(int index) {
/* 109 */     return (portComponentRefType)getElementValue("port-component-ref", "portComponentRefType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPortComponentRefCount() {
/* 116 */     return sizeOfElement("port-component-ref");
/*     */   }
/*     */   
/*     */   public boolean removePortComponentRef(int index) {
/* 120 */     return removeElement(index, "port-component-ref");
/*     */   }
/*     */   
/*     */   public void setHandler(int index, serviceRef_handlerType handler) {
/* 124 */     setElementValue(index, "handler", handler);
/*     */   }
/*     */   
/*     */   public serviceRef_handlerType getHandler(int index) {
/* 128 */     return (serviceRef_handlerType)getElementValue("handler", "serviceRef_handlerType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHandlerCount() {
/* 135 */     return sizeOfElement("handler");
/*     */   }
/*     */   
/*     */   public boolean removeHandler(int index) {
/* 139 */     return removeElement(index, "handler");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 143 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 147 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 151 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\serviceRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */