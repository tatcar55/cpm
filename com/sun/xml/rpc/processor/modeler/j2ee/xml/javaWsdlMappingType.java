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
/*     */ 
/*     */ 
/*     */ public class javaWsdlMappingType
/*     */   extends ComplexType
/*     */ {
/*     */   public void setPackageMapping(int index, packageMappingType packageMapping) {
/*  45 */     setElementValue(index, "package-mapping", packageMapping);
/*     */   }
/*     */   
/*     */   public packageMappingType getPackageMapping(int index) {
/*  49 */     return (packageMappingType)getElementValue("package-mapping", "packageMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPackageMappingCount() {
/*  56 */     return sizeOfElement("package-mapping");
/*     */   }
/*     */   
/*     */   public boolean removePackageMapping(int index) {
/*  60 */     return removeElement(index, "package-mapping");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJavaXmlTypeMapping(int index, javaXmlTypeMappingType javaXmlTypeMapping) {
/*  66 */     setElementValue(index, "java-xml-type-mapping", javaXmlTypeMapping);
/*     */   }
/*     */   
/*     */   public javaXmlTypeMappingType getJavaXmlTypeMapping(int index) {
/*  70 */     return (javaXmlTypeMappingType)getElementValue("java-xml-type-mapping", "javaXmlTypeMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getJavaXmlTypeMappingCount() {
/*  77 */     return sizeOfElement("java-xml-type-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeJavaXmlTypeMapping(int index) {
/*  81 */     return removeElement(index, "java-xml-type-mapping");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExceptionMapping(int index, exceptionMappingType exceptionMapping) {
/*  87 */     setElementValue(index, "exception-mapping", exceptionMapping);
/*     */   }
/*     */   
/*     */   public exceptionMappingType getExceptionMapping(int index) {
/*  91 */     return (exceptionMappingType)getElementValue("exception-mapping", "exceptionMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExceptionMappingCount() {
/*  98 */     return sizeOfElement("exception-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeExceptionMapping(int index) {
/* 102 */     return removeElement(index, "exception-mapping");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceInterfaceMapping(int index, serviceInterfaceMappingType serviceInterfaceMapping) {
/* 108 */     setElementValue(index, "service-interface-mapping", serviceInterfaceMapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceInterfaceMappingType getServiceInterfaceMapping(int index) {
/* 115 */     return (serviceInterfaceMappingType)getElementValue("service-interface-mapping", "serviceInterfaceMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServiceInterfaceMappingCount() {
/* 122 */     return sizeOfElement("service-interface-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeServiceInterfaceMapping(int index) {
/* 126 */     return removeElement(index, "service-interface-mapping");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServiceEndpointInterfaceMapping(int index, serviceEndpointInterfaceMappingType serviceEndpointInterfaceMapping) {
/* 132 */     setElementValue(index, "service-endpoint-interface-mapping", serviceEndpointInterfaceMapping);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceEndpointInterfaceMappingType getServiceEndpointInterfaceMapping(int index) {
/* 139 */     return (serviceEndpointInterfaceMappingType)getElementValue("service-endpoint-interface-mapping", "serviceEndpointInterfaceMappingType", index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getServiceEndpointInterfaceMappingCount() {
/* 146 */     return sizeOfElement("service-endpoint-interface-mapping");
/*     */   }
/*     */   
/*     */   public boolean removeServiceEndpointInterfaceMapping(int index) {
/* 150 */     return removeElement(index, "service-endpoint-interface-mapping");
/*     */   }
/*     */   
/*     */   public void setVersion(deweyVersionType version) {
/* 154 */     setAttributeValue("version", version);
/*     */   }
/*     */   
/*     */   public deweyVersionType getVersion() {
/* 158 */     return (deweyVersionType)getAttributeValue("version", "deweyVersionType");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeVersion() {
/* 164 */     return removeAttribute("version");
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 168 */     setAttributeValue("id", id);
/*     */   }
/*     */   
/*     */   public String getId() {
/* 172 */     return getAttributeValue("id");
/*     */   }
/*     */   
/*     */   public boolean removeId() {
/* 176 */     return removeAttribute("id");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\javaWsdlMappingType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */