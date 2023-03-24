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
/*    */ 
/*    */ 
/*    */ public class deploymentExtensionType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setExtensionElement(int index, extensibleType extensionElement) {
/* 45 */     setElementValue(index, "extension-element", extensionElement);
/*    */   }
/*    */   
/*    */   public extensibleType getExtensionElement(int index) {
/* 49 */     return (extensibleType)getElementValue("extension-element", "extensibleType", index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getExtensionElementCount() {
/* 56 */     return sizeOfElement("extension-element");
/*    */   }
/*    */   
/*    */   public boolean removeExtensionElement(int index) {
/* 60 */     return removeElement(index, "extension-element");
/*    */   }
/*    */   
/*    */   public void setNamespace(String namespace) {
/* 64 */     setAttributeValue("namespace", namespace);
/*    */   }
/*    */   
/*    */   public String getNamespace() {
/* 68 */     return getAttributeValue("namespace");
/*    */   }
/*    */   
/*    */   public boolean removeNamespace() {
/* 72 */     return removeAttribute("namespace");
/*    */   }
/*    */   
/*    */   public void setMustUnderstand(boolean mustUnderstand) {
/* 76 */     setAttributeValue("mustUnderstand", mustUnderstand);
/*    */   }
/*    */   
/*    */   public boolean getMustUnderstand() {
/* 80 */     return getAttributeBooleanValue("mustUnderstand");
/*    */   }
/*    */   
/*    */   public boolean removeMustUnderstand() {
/* 84 */     return removeAttribute("mustUnderstand");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 88 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 92 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 96 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\deploymentExtensionType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */