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
/*    */ public class constructorParameterOrderType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setElementName(int index, string elementName) {
/* 43 */     setElementValue(index, "element-name", elementName);
/*    */   }
/*    */   
/*    */   public string getElementName(int index) {
/* 47 */     return (string)getElementValue("element-name", "string", index);
/*    */   }
/*    */   
/*    */   public int getElementNameCount() {
/* 51 */     return sizeOfElement("element-name");
/*    */   }
/*    */   
/*    */   public boolean removeElementName(int index) {
/* 55 */     return removeElement(index, "element-name");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 59 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 63 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 67 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\constructorParameterOrderType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */