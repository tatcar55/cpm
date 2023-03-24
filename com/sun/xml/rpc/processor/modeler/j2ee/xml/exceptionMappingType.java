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
/*     */ public class exceptionMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setExceptionType(fullyQualifiedClassType exceptionType) {
/*  43 */     setElementValue("exception-type", exceptionType);
/*     */   }
/*     */   
/*     */   public fullyQualifiedClassType getExceptionType() {
/*  47 */     return (fullyQualifiedClassType)getElementValue("exception-type", "fullyQualifiedClassType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeExceptionType() {
/*  53 */     return removeElement("exception-type");
/*     */   }
/*     */   
/*     */   public void setWsdlMessage(wsdlMessageType wsdlMessage) {
/*  57 */     setElementValue("wsdl-message", wsdlMessage);
/*     */   }
/*     */   
/*     */   public wsdlMessageType getWsdlMessage() {
/*  61 */     return (wsdlMessageType)getElementValue("wsdl-message", "wsdlMessageType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWsdlMessage() {
/*  67 */     return removeElement("wsdl-message");
/*     */   }
/*     */   
/*     */   public void setWsdlMessagePartName(wsdlMessagePartNameType wsdlMessagePartName) {
/*  71 */     setElementValue("wsdl-message-part-name", wsdlMessagePartName);
/*     */   }
/*     */   
/*     */   public wsdlMessagePartNameType getWsdlMessagePartName() {
/*  75 */     return (wsdlMessagePartNameType)getElementValue("wsdl-message-part-name", "wsdlMessagePartNameType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWsdlMessagePartName() {
/*  81 */     return removeElement("wsdl-message-part-name");
/*     */   }
/*     */   
/*     */   public void setConstructorParameterOrder(constructorParameterOrderType constructorParameterOrder) {
/*  85 */     setElementValue("constructor-parameter-order", constructorParameterOrder);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public constructorParameterOrderType getConstructorParameterOrder() {
/*  91 */     return (constructorParameterOrderType)getElementValue("constructor-parameter-order", "constructorParameterOrderType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeConstructorParameterOrder() {
/*  97 */     return removeElement("constructor-parameter-order");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 101 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 105 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 109 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\exceptionMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */