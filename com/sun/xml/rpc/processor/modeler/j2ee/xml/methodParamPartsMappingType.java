/*    */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class methodParamPartsMappingType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setParamPosition(xsdNonNegativeIntegerType paramPosition) {
/* 43 */     setElementValue("param-position", paramPosition);
/*    */   }
/*    */   
/*    */   public xsdNonNegativeIntegerType getParamPosition() {
/* 47 */     return (xsdNonNegativeIntegerType)getElementValue("param-position", "xsdNonNegativeIntegerType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeParamPosition() {
/* 53 */     return removeElement("param-position");
/*    */   }
/*    */   
/*    */   public void setParamType(javaTypeType paramType) {
/* 57 */     setElementValue("param-type", paramType);
/*    */   }
/*    */   
/*    */   public javaTypeType getParamType() {
/* 61 */     return (javaTypeType)getElementValue("param-type", "javaTypeType");
/*    */   }
/*    */   
/*    */   public boolean removeParamType() {
/* 65 */     return removeElement("param-type");
/*    */   }
/*    */   
/*    */   public void setWsdlMessageMapping(wsdlMessageMappingType wsdlMessageMapping) {
/* 69 */     setElementValue("wsdl-message-mapping", wsdlMessageMapping);
/*    */   }
/*    */   
/*    */   public wsdlMessageMappingType getWsdlMessageMapping() {
/* 73 */     return (wsdlMessageMappingType)getElementValue("wsdl-message-mapping", "wsdlMessageMappingType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeWsdlMessageMapping() {
/* 79 */     return removeElement("wsdl-message-mapping");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 83 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 87 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 91 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\methodParamPartsMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */