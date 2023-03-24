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
/*    */ public class packageMappingType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setPackageType(fullyQualifiedClassType packageType) {
/* 43 */     setElementValue("package-type", packageType);
/*    */   }
/*    */   
/*    */   public fullyQualifiedClassType getPackageType() {
/* 47 */     return (fullyQualifiedClassType)getElementValue("package-type", "fullyQualifiedClassType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removePackageType() {
/* 53 */     return removeElement("package-type");
/*    */   }
/*    */   
/*    */   public void setNamespaceURI(xsdAnyURIType namespaceURI) {
/* 57 */     setElementValue("namespaceURI", namespaceURI);
/*    */   }
/*    */   
/*    */   public xsdAnyURIType getNamespaceURI() {
/* 61 */     return (xsdAnyURIType)getElementValue("namespaceURI", "xsdAnyURIType");
/*    */   }
/*    */   
/*    */   public boolean removeNamespaceURI() {
/* 65 */     return removeElement("namespaceURI");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 69 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 73 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 77 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\packageMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */