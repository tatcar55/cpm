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
/*    */ public class portMappingType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setPortName(string portName) {
/* 43 */     setElementValue("port-name", portName);
/*    */   }
/*    */   
/*    */   public string getPortName() {
/* 47 */     return (string)getElementValue("port-name", "string");
/*    */   }
/*    */   
/*    */   public boolean removePortName() {
/* 51 */     return removeElement("port-name");
/*    */   }
/*    */   
/*    */   public void setJavaPortName(string javaPortName) {
/* 55 */     setElementValue("java-port-name", javaPortName);
/*    */   }
/*    */   
/*    */   public string getJavaPortName() {
/* 59 */     return (string)getElementValue("java-port-name", "string");
/*    */   }
/*    */   
/*    */   public boolean removeJavaPortName() {
/* 63 */     return removeElement("java-port-name");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 67 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 71 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 75 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\portMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */