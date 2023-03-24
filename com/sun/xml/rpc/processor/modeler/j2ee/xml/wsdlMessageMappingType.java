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
/*     */ public class wsdlMessageMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setWsdlMessage(wsdlMessageType wsdlMessage) {
/*  43 */     setElementValue("wsdl-message", wsdlMessage);
/*     */   }
/*     */   
/*     */   public wsdlMessageType getWsdlMessage() {
/*  47 */     return (wsdlMessageType)getElementValue("wsdl-message", "wsdlMessageType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWsdlMessage() {
/*  53 */     return removeElement("wsdl-message");
/*     */   }
/*     */   
/*     */   public void setWsdlMessagePartName(wsdlMessagePartNameType wsdlMessagePartName) {
/*  57 */     setElementValue("wsdl-message-part-name", wsdlMessagePartName);
/*     */   }
/*     */   
/*     */   public wsdlMessagePartNameType getWsdlMessagePartName() {
/*  61 */     return (wsdlMessagePartNameType)getElementValue("wsdl-message-part-name", "wsdlMessagePartNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWsdlMessagePartName() {
/*  67 */     return removeElement("wsdl-message-part-name");
/*     */   }
/*     */   
/*     */   public void setParameterMode(parameterModeType parameterMode) {
/*  71 */     setElementValue("parameter-mode", parameterMode);
/*     */   }
/*     */   
/*     */   public parameterModeType getParameterMode() {
/*  75 */     return (parameterModeType)getElementValue("parameter-mode", "parameterModeType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeParameterMode() {
/*  81 */     return removeElement("parameter-mode");
/*     */   }
/*     */   
/*     */   public void setSoapHeader(emptyType soapHeader) {
/*  85 */     setElementValue("soap-header", soapHeader);
/*     */   }
/*     */   
/*     */   public emptyType getSoapHeader() {
/*  89 */     return (emptyType)getElementValue("soap-header", "emptyType");
/*     */   }
/*     */   
/*     */   public boolean removeSoapHeader() {
/*  93 */     return removeElement("soap-header");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  97 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 101 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 105 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\wsdlMessageMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */