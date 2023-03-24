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
/*    */ public class paramValueType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setDescription(int index, descriptionType description) {
/* 43 */     setElementValue(index, "description", description);
/*    */   }
/*    */   
/*    */   public descriptionType getDescription(int index) {
/* 47 */     return (descriptionType)getElementValue("description", "descriptionType", index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDescriptionCount() {
/* 54 */     return sizeOfElement("description");
/*    */   }
/*    */   
/*    */   public boolean removeDescription(int index) {
/* 58 */     return removeElement(index, "description");
/*    */   }
/*    */   
/*    */   public void setParamName(string paramName) {
/* 62 */     setElementValue("param-name", paramName);
/*    */   }
/*    */   
/*    */   public string getParamName() {
/* 66 */     return (string)getElementValue("param-name", "string");
/*    */   }
/*    */   
/*    */   public boolean removeParamName() {
/* 70 */     return removeElement("param-name");
/*    */   }
/*    */   
/*    */   public void setParamValue(xsdStringType paramValue) {
/* 74 */     setElementValue("param-value", paramValue);
/*    */   }
/*    */   
/*    */   public xsdStringType getParamValue() {
/* 78 */     return (xsdStringType)getElementValue("param-value", "xsdStringType");
/*    */   }
/*    */   
/*    */   public boolean removeParamValue() {
/* 82 */     return removeElement("param-value");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 86 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 90 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 94 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\paramValueType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */