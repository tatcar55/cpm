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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class javaWsdlMappingFactory
/*     */   extends Factory
/*     */ {
/*     */   public javaWsdlMapping createRoot(String rootElementname) {
/*  50 */     return (javaWsdlMapping)createRootDOMFromComplexType("javaWsdlMapping", rootElementname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javaWsdlMapping loadDocument(String filename) {
/*  61 */     return (javaWsdlMapping)loadDocument("javaWsdlMapping", filename);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public portComponentRefType createportComponentRefType(String elementName) {
/*  70 */     return (portComponentRefType)createDOMElementFromComplexType("portComponentRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceRefType createserviceRefType(String elementName) {
/*  81 */     return (serviceRefType)createDOMElementFromComplexType("serviceRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceRef_handlerType createserviceRef_handlerType(String elementName) {
/*  92 */     return (serviceRef_handlerType)createDOMElementFromComplexType("serviceRef_handlerType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public deploymentExtensionType createdeploymentExtensionType(String elementName) {
/* 103 */     return (deploymentExtensionType)createDOMElementFromComplexType("deploymentExtensionType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public descriptionType createdescriptionType(String elementName) {
/* 114 */     return (descriptionType)createDOMElementFromComplexType("descriptionType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public deweyVersionType createdeweyVersionType(String elementName) {
/* 125 */     return (deweyVersionType)createDOMElementFromSimpleType("deweyVersionType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public displayNameType createdisplayNameType(String elementName) {
/* 136 */     return (displayNameType)createDOMElementFromComplexType("displayNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ejbLinkType createejbLinkType(String elementName) {
/* 147 */     return (ejbLinkType)createDOMElementFromComplexType("ejbLinkType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ejbLocalRefType createejbLocalRefType(String elementName) {
/* 158 */     return (ejbLocalRefType)createDOMElementFromComplexType("ejbLocalRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ejbRefNameType createejbRefNameType(String elementName) {
/* 169 */     return (ejbRefNameType)createDOMElementFromComplexType("ejbRefNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ejbRefTypeType createejbRefTypeType(String elementName) {
/* 180 */     return (ejbRefTypeType)createDOMElementFromComplexType("ejbRefTypeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ejbRefType createejbRefType(String elementName) {
/* 191 */     return (ejbRefType)createDOMElementFromComplexType("ejbRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public emptyType createemptyType(String elementName) {
/* 202 */     return (emptyType)createDOMElementFromComplexType("emptyType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public envEntryTypeValuesType createenvEntryTypeValuesType(String elementName) {
/* 213 */     return (envEntryTypeValuesType)createDOMElementFromComplexType("envEntryTypeValuesType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public envEntryType createenvEntryType(String elementName) {
/* 224 */     return (envEntryType)createDOMElementFromComplexType("envEntryType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public extensibleType createextensibleType(String elementName) {
/* 235 */     return (extensibleType)createDOMElementFromComplexType("extensibleType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public fullyQualifiedClassType createfullyQualifiedClassType(String elementName) {
/* 246 */     return (fullyQualifiedClassType)createDOMElementFromComplexType("fullyQualifiedClassType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public genericBooleanType creategenericBooleanType(String elementName) {
/* 257 */     return (genericBooleanType)createDOMElementFromComplexType("genericBooleanType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public homeType createhomeType(String elementName) {
/* 268 */     return (homeType)createDOMElementFromComplexType("homeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public iconType createiconType(String elementName) {
/* 279 */     return (iconType)createDOMElementFromComplexType("iconType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javaIdentifierType createjavaIdentifierType(String elementName) {
/* 290 */     return (javaIdentifierType)createDOMElementFromComplexType("javaIdentifierType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public jndiNameType createjndiNameType(String elementName) {
/* 301 */     return (jndiNameType)createDOMElementFromComplexType("jndiNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public localHomeType createlocalHomeType(String elementName) {
/* 312 */     return (localHomeType)createDOMElementFromComplexType("localHomeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public localType createlocalType(String elementName) {
/* 323 */     return (localType)createDOMElementFromComplexType("localType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public messageDestinationLinkType createmessageDestinationLinkType(String elementName) {
/* 334 */     return (messageDestinationLinkType)createDOMElementFromComplexType("messageDestinationLinkType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public messageDestinationRefType createmessageDestinationRefType(String elementName) {
/* 345 */     return (messageDestinationRefType)createDOMElementFromComplexType("messageDestinationRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public messageDestinationTypeType createmessageDestinationTypeType(String elementName) {
/* 356 */     return (messageDestinationTypeType)createDOMElementFromComplexType("messageDestinationTypeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public messageDestinationUsageType createmessageDestinationUsageType(String elementName) {
/* 367 */     return (messageDestinationUsageType)createDOMElementFromComplexType("messageDestinationUsageType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public messageDestinationType createmessageDestinationType(String elementName) {
/* 378 */     return (messageDestinationType)createDOMElementFromComplexType("messageDestinationType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public paramValueType createparamValueType(String elementName) {
/* 389 */     return (paramValueType)createDOMElementFromComplexType("paramValueType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public pathType createpathType(String elementName) {
/* 400 */     return (pathType)createDOMElementFromComplexType("pathType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public remoteType createremoteType(String elementName) {
/* 411 */     return (remoteType)createDOMElementFromComplexType("remoteType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public resAuthType createresAuthType(String elementName) {
/* 422 */     return (resAuthType)createDOMElementFromComplexType("resAuthType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public resSharingScopeType createresSharingScopeType(String elementName) {
/* 433 */     return (resSharingScopeType)createDOMElementFromComplexType("resSharingScopeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public resourceEnvRefType createresourceEnvRefType(String elementName) {
/* 444 */     return (resourceEnvRefType)createDOMElementFromComplexType("resourceEnvRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public resourceRefType createresourceRefType(String elementName) {
/* 455 */     return (resourceRefType)createDOMElementFromComplexType("resourceRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public roleNameType createroleNameType(String elementName) {
/* 466 */     return (roleNameType)createDOMElementFromComplexType("roleNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public runAsType createrunAsType(String elementName) {
/* 477 */     return (runAsType)createDOMElementFromComplexType("runAsType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public securityRoleRefType createsecurityRoleRefType(String elementName) {
/* 488 */     return (securityRoleRefType)createDOMElementFromComplexType("securityRoleRefType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public securityRoleType createsecurityRoleType(String elementName) {
/* 499 */     return (securityRoleType)createDOMElementFromComplexType("securityRoleType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public string createstring(String elementName) {
/* 510 */     return (string)createDOMElementFromComplexType("string", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public trueFalseType createtrueFalseType(String elementName) {
/* 519 */     return (trueFalseType)createDOMElementFromComplexType("trueFalseType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public urlPatternType createurlPatternType(String elementName) {
/* 530 */     return (urlPatternType)createDOMElementFromComplexType("urlPatternType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public warPathType createwarPathType(String elementName) {
/* 541 */     return (warPathType)createDOMElementFromComplexType("warPathType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdAnyURIType createxsdAnyURIType(String elementName) {
/* 552 */     return (xsdAnyURIType)createDOMElementFromComplexType("xsdAnyURIType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdBooleanType createxsdBooleanType(String elementName) {
/* 563 */     return (xsdBooleanType)createDOMElementFromComplexType("xsdBooleanType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdIntegerType createxsdIntegerType(String elementName) {
/* 574 */     return (xsdIntegerType)createDOMElementFromComplexType("xsdIntegerType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdNMTOKENType createxsdNMTOKENType(String elementName) {
/* 585 */     return (xsdNMTOKENType)createDOMElementFromComplexType("xsdNMTOKENType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdNonNegativeIntegerType createxsdNonNegativeIntegerType(String elementName) {
/* 596 */     return (xsdNonNegativeIntegerType)createDOMElementFromComplexType("xsdNonNegativeIntegerType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdPositiveIntegerType createxsdPositiveIntegerType(String elementName) {
/* 607 */     return (xsdPositiveIntegerType)createDOMElementFromComplexType("xsdPositiveIntegerType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdQNameType createxsdQNameType(String elementName) {
/* 618 */     return (xsdQNameType)createDOMElementFromComplexType("xsdQNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public xsdStringType createxsdStringType(String elementName) {
/* 629 */     return (xsdStringType)createDOMElementFromComplexType("xsdStringType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javaWsdlMapping createjavaWsdlMapping(String elementName) {
/* 640 */     return (javaWsdlMapping)createDOMElementFromComplexType("javaWsdlMapping", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public constructorParameterOrderType createconstructorParameterOrderType(String elementName) {
/* 651 */     return (constructorParameterOrderType)createDOMElementFromComplexType("constructorParameterOrderType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public exceptionMappingType createexceptionMappingType(String elementName) {
/* 662 */     return (exceptionMappingType)createDOMElementFromComplexType("exceptionMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javaWsdlMappingType createjavaWsdlMappingType(String elementName) {
/* 673 */     return (javaWsdlMappingType)createDOMElementFromComplexType("javaWsdlMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public javaXmlTypeMappingType createjavaXmlTypeMappingType(String elementName) {
/* 684 */     return (javaXmlTypeMappingType)createDOMElementFromComplexType("javaXmlTypeMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public methodParamPartsMappingType createmethodParamPartsMappingType(String elementName) {
/* 695 */     return (methodParamPartsMappingType)createDOMElementFromComplexType("methodParamPartsMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public packageMappingType createpackageMappingType(String elementName) {
/* 706 */     return (packageMappingType)createDOMElementFromComplexType("packageMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public parameterModeType createparameterModeType(String elementName) {
/* 717 */     return (parameterModeType)createDOMElementFromComplexType("parameterModeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public portMappingType createportMappingType(String elementName) {
/* 728 */     return (portMappingType)createDOMElementFromComplexType("portMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public qnameScopeType createqnameScopeType(String elementName) {
/* 739 */     return (qnameScopeType)createDOMElementFromComplexType("qnameScopeType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceEndpointInterfaceMappingType createserviceEndpointInterfaceMappingType(String elementName) {
/* 750 */     return (serviceEndpointInterfaceMappingType)createDOMElementFromComplexType("serviceEndpointInterfaceMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceEndpointMethodMappingType createserviceEndpointMethodMappingType(String elementName) {
/* 761 */     return (serviceEndpointMethodMappingType)createDOMElementFromComplexType("serviceEndpointMethodMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public serviceInterfaceMappingType createserviceInterfaceMappingType(String elementName) {
/* 772 */     return (serviceInterfaceMappingType)createDOMElementFromComplexType("serviceInterfaceMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public variableMappingType createvariableMappingType(String elementName) {
/* 783 */     return (variableMappingType)createDOMElementFromComplexType("variableMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public wsdlMessageMappingType createwsdlMessageMappingType(String elementName) {
/* 794 */     return (wsdlMessageMappingType)createDOMElementFromComplexType("wsdlMessageMappingType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public wsdlMessagePartNameType createwsdlMessagePartNameType(String elementName) {
/* 805 */     return (wsdlMessagePartNameType)createDOMElementFromComplexType("wsdlMessagePartNameType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public wsdlMessageType createwsdlMessageType(String elementName) {
/* 816 */     return (wsdlMessageType)createDOMElementFromComplexType("wsdlMessageType", elementName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public wsdlReturnValueMappingType createwsdlReturnValueMappingType(String elementName) {
/* 827 */     return (wsdlReturnValueMappingType)createDOMElementFromComplexType("wsdlReturnValueMappingType", elementName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\javaWsdlMappingFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */