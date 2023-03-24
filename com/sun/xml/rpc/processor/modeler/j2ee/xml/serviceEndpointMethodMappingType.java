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
/*     */ public class serviceEndpointMethodMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setJavaMethodName(string javaMethodName) {
/*  43 */     setElementValue("java-method-name", javaMethodName);
/*     */   }
/*     */   
/*     */   public string getJavaMethodName() {
/*  47 */     return (string)getElementValue("java-method-name", "string");
/*     */   }
/*     */   
/*     */   public boolean removeJavaMethodName() {
/*  51 */     return removeElement("java-method-name");
/*     */   }
/*     */   
/*     */   public void setWsdlOperation(string wsdlOperation) {
/*  55 */     setElementValue("wsdl-operation", wsdlOperation);
/*     */   }
/*     */   
/*     */   public string getWsdlOperation() {
/*  59 */     return (string)getElementValue("wsdl-operation", "string");
/*     */   }
/*     */   
/*     */   public boolean removeWsdlOperation() {
/*  63 */     return removeElement("wsdl-operation");
/*     */   }
/*     */   
/*     */   public void setWrappedElement(emptyType wrappedElement) {
/*  67 */     setElementValue("wrapped-element", wrappedElement);
/*     */   }
/*     */   
/*     */   public emptyType getWrappedElement() {
/*  71 */     return (emptyType)getElementValue("wrapped-element", "emptyType");
/*     */   }
/*     */   
/*     */   public boolean removeWrappedElement() {
/*  75 */     return removeElement("wrapped-element");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMethodParamPartsMapping(int index, methodParamPartsMappingType methodParamPartsMapping) {
/*  81 */     setElementValue(index, "method-param-parts-mapping", methodParamPartsMapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public methodParamPartsMappingType getMethodParamPartsMapping(int index) {
/*  88 */     return (methodParamPartsMappingType)getElementValue("method-param-parts-mapping", "methodParamPartsMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMethodParamPartsMappingCount() {
/*  95 */     return sizeOfElement("method-param-parts-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeMethodParamPartsMapping(int index) {
/*  99 */     return removeElement(index, "method-param-parts-mapping");
/*     */   }
/*     */   
/*     */   public void setWsdlReturnValueMapping(wsdlReturnValueMappingType wsdlReturnValueMapping) {
/* 103 */     setElementValue("wsdl-return-value-mapping", wsdlReturnValueMapping);
/*     */   }
/*     */   
/*     */   public wsdlReturnValueMappingType getWsdlReturnValueMapping() {
/* 107 */     return (wsdlReturnValueMappingType)getElementValue("wsdl-return-value-mapping", "wsdlReturnValueMappingType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeWsdlReturnValueMapping() {
/* 113 */     return removeElement("wsdl-return-value-mapping");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 117 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 121 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 125 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\serviceEndpointMethodMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */