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
/*    */ public class wsdlReturnValueMappingType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setMethodReturnValue(fullyQualifiedClassType methodReturnValue) {
/* 43 */     setElementValue("method-return-value", methodReturnValue);
/*    */   }
/*    */   
/*    */   public fullyQualifiedClassType getMethodReturnValue() {
/* 47 */     return (fullyQualifiedClassType)getElementValue("method-return-value", "fullyQualifiedClassType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeMethodReturnValue() {
/* 53 */     return removeElement("method-return-value");
/*    */   }
/*    */   
/*    */   public void setWsdlMessage(wsdlMessageType wsdlMessage) {
/* 57 */     setElementValue("wsdl-message", wsdlMessage);
/*    */   }
/*    */   
/*    */   public wsdlMessageType getWsdlMessage() {
/* 61 */     return (wsdlMessageType)getElementValue("wsdl-message", "wsdlMessageType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeWsdlMessage() {
/* 67 */     return removeElement("wsdl-message");
/*    */   }
/*    */   
/*    */   public void setWsdlMessagePartName(wsdlMessagePartNameType wsdlMessagePartName) {
/* 71 */     setElementValue("wsdl-message-part-name", wsdlMessagePartName);
/*    */   }
/*    */   
/*    */   public wsdlMessagePartNameType getWsdlMessagePartName() {
/* 75 */     return (wsdlMessagePartNameType)getElementValue("wsdl-message-part-name", "wsdlMessagePartNameType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeWsdlMessagePartName() {
/* 81 */     return removeElement("wsdl-message-part-name");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 85 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 89 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 93 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\wsdlReturnValueMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */