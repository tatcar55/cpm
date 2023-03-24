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
/*    */ public class serviceInterfaceMappingType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setServiceInterface(fullyQualifiedClassType serviceInterface) {
/* 43 */     setElementValue("service-interface", serviceInterface);
/*    */   }
/*    */   
/*    */   public fullyQualifiedClassType getServiceInterface() {
/* 47 */     return (fullyQualifiedClassType)getElementValue("service-interface", "fullyQualifiedClassType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeServiceInterface() {
/* 53 */     return removeElement("service-interface");
/*    */   }
/*    */   
/*    */   public void setWsdlServiceName(xsdQNameType wsdlServiceName) {
/* 57 */     setElementValue("wsdl-service-name", wsdlServiceName);
/*    */   }
/*    */   
/*    */   public xsdQNameType getWsdlServiceName() {
/* 61 */     return (xsdQNameType)getElementValue("wsdl-service-name", "xsdQNameType");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean removeWsdlServiceName() {
/* 67 */     return removeElement("wsdl-service-name");
/*    */   }
/*    */   
/*    */   public void setPortMapping(int index, portMappingType portMapping) {
/* 71 */     setElementValue(index, "port-mapping", portMapping);
/*    */   }
/*    */   
/*    */   public portMappingType getPortMapping(int index) {
/* 75 */     return (portMappingType)getElementValue("port-mapping", "portMappingType", index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPortMappingCount() {
/* 82 */     return sizeOfElement("port-mapping");
/*    */   }
/*    */   
/*    */   public boolean removePortMapping(int index) {
/* 86 */     return removeElement(index, "port-mapping");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 90 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 94 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 98 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\serviceInterfaceMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */