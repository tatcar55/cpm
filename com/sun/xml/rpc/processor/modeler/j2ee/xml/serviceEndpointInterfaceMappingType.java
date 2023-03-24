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
/*     */ public class serviceEndpointInterfaceMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setServiceEndpointInterface(fullyQualifiedClassType serviceEndpointInterface) {
/*  43 */     setElementValue("service-endpoint-interface", serviceEndpointInterface);
/*     */   }
/*     */   
/*     */   public fullyQualifiedClassType getServiceEndpointInterface() {
/*  47 */     return (fullyQualifiedClassType)getElementValue("service-endpoint-interface", "fullyQualifiedClassType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeServiceEndpointInterface() {
/*  53 */     return removeElement("service-endpoint-interface");
/*     */   }
/*     */   
/*     */   public void setWsdlPortType(xsdQNameType wsdlPortType) {
/*  57 */     setElementValue("wsdl-port-type", wsdlPortType);
/*     */   }
/*     */   
/*     */   public xsdQNameType getWsdlPortType() {
/*  61 */     return (xsdQNameType)getElementValue("wsdl-port-type", "xsdQNameType");
/*     */   }
/*     */   
/*     */   public boolean removeWsdlPortType() {
/*  65 */     return removeElement("wsdl-port-type");
/*     */   }
/*     */   
/*     */   public void setWsdlBinding(xsdQNameType wsdlBinding) {
/*  69 */     setElementValue("wsdl-binding", wsdlBinding);
/*     */   }
/*     */   
/*     */   public xsdQNameType getWsdlBinding() {
/*  73 */     return (xsdQNameType)getElementValue("wsdl-binding", "xsdQNameType");
/*     */   }
/*     */   
/*     */   public boolean removeWsdlBinding() {
/*  77 */     return removeElement("wsdl-binding");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceEndpointMethodMapping(int index, serviceEndpointMethodMappingType serviceEndpointMethodMapping) {
/*  83 */     setElementValue(index, "service-endpoint-method-mapping", serviceEndpointMethodMapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceEndpointMethodMappingType getServiceEndpointMethodMapping(int index) {
/*  90 */     return (serviceEndpointMethodMappingType)getElementValue("service-endpoint-method-mapping", "serviceEndpointMethodMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServiceEndpointMethodMappingCount() {
/*  97 */     return sizeOfElement("service-endpoint-method-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeServiceEndpointMethodMapping(int index) {
/* 101 */     return removeElement(index, "service-endpoint-method-mapping");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 105 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 109 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 113 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\serviceEndpointInterfaceMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */