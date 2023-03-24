/*     */ package com.sun.xml.rpc.processor.config;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.J2EEModeler;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.J2EEModeler111;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.J2EEModeler112;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.J2EEModelerHelper;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.J2EEModelerIf;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.JaxRpcMappingXml;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.NamespaceHelper;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.ComplexType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.constructorParameterOrderType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.exceptionMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaWsdlMapping;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaXmlTypeMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.methodParamPartsMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.portMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.serviceEndpointInterfaceMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.serviceEndpointMethodMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.serviceInterfaceMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.variableMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.wsdlMessageMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.wsdlMessageType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.wsdlReturnValueMappingType;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.xml.xsdQNameType;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
/*     */ import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.spi.tools.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*     */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class J2EEModelInfo
/*     */   extends WSDLModelInfo
/*     */   implements J2EEModelInfo
/*     */ {
/*     */   private JaxRpcMappingXml mappingFileXml;
/*     */   
/*     */   public J2EEModelInfo() {}
/*     */   
/*     */   public J2EEModelInfo(JaxRpcMappingXml mappingXml) {
/* 101 */     setJaxRcpMappingXml(mappingXml);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJaxRcpMappingXml(JaxRpcMappingXml mappingXml) {
/* 106 */     this.mappingFileXml = mappingXml;
/*     */     
/* 108 */     NamespaceMappingRegistryInfo nsMapInfo = new NamespaceMappingRegistryInfo();
/* 109 */     HashMap nsMap = this.mappingFileXml.getNSToPkgMapping();
/* 110 */     Set keys = nsMap.keySet();
/* 111 */     for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
/* 112 */       String ns = it.next();
/* 113 */       String pkg = (String)nsMap.get(ns);
/* 114 */       NamespaceMappingInfo map = new NamespaceMappingInfo(ns, pkg);
/* 115 */       nsMapInfo.addMapping(map);
/*     */     } 
/* 117 */     setNamespaceMappingRegistry(nsMapInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Modeler getModeler(Properties options) {
/* 128 */     if (VersionUtil.isVersion111(options.getProperty("sourceVersion")))
/*     */     {
/* 130 */       return (Modeler)new J2EEModeler111(this, options); } 
/* 131 */     if (VersionUtil.isVersion112(options.getProperty("sourceVersion")))
/*     */     {
/* 133 */       return (Modeler)new J2EEModeler112(this, options);
/*     */     }
/* 135 */     return (Modeler)new J2EEModeler(this, options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String javaNameOfService(QName service) {
/* 146 */     String serviceInterface = null;
/* 147 */     serviceInterfaceMappingType serviceMapping = (serviceInterfaceMappingType)this.serviceMap.get(service);
/* 148 */     if (serviceMapping != null) {
/*     */       
/* 150 */       serviceInterface = serviceMapping.getServiceInterface().getElementValue();
/*     */     } else {
/* 152 */       serviceInterface = getNames().validJavaClassName(service.getLocalPart());
/* 153 */       String javaPackageName = getJavaPackageName(service);
/* 154 */       if (javaPackageName != null) {
/* 155 */         serviceInterface = javaPackageName + "." + serviceInterface;
/*     */       }
/*     */     } 
/* 158 */     return serviceInterface;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String javaNameOfSEI(QName bindingQName, QName portTypeQName, QName portQName) {
/* 170 */     String sei = null;
/* 171 */     MetadataSEIInfo seiInfo = (MetadataSEIInfo)this.serviceEndpointMap.get(bindingQName);
/* 172 */     if (seiInfo != null) {
/*     */       
/* 174 */       sei = seiInfo.javaName;
/*     */     } else {
/* 176 */       debug("javaNameOfSEI: seiInfo is null");
/*     */       
/* 178 */       if (portTypeQName != null) {
/*     */         
/* 180 */         sei = makePackageQualified(getNonQualifiedNameFor(portTypeQName), portTypeQName);
/*     */       } else {
/*     */         
/* 183 */         sei = makePackageQualified(getNonQualifiedNameFor(portQName), portQName);
/*     */       } 
/*     */     } 
/* 186 */     debug("javaNameofSEI" + bindingQName + " is:" + sei);
/* 187 */     return sei;
/*     */   }
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
/*     */   public MetadataOperationInfo findOperationInfo(QName bindingQName, String operationName, Message inputMsg, Message outputMsg, J2EEModelerIf modeler) {
/* 207 */     MetadataSEIInfo seiInfo = (MetadataSEIInfo)this.serviceEndpointMap.get(bindingQName);
/*     */     
/* 209 */     if (seiInfo == null) {
/* 210 */       return null;
/*     */     }
/*     */     
/* 213 */     QName inMsgQName = J2EEModelerHelper.getQNameOf((GloballyKnown)inputMsg);
/* 214 */     QName outMsgQName = (outputMsg != null) ? J2EEModelerHelper.getQNameOf((GloballyKnown)outputMsg) : null;
/*     */     
/* 216 */     for (Iterator<MetadataOperationInfo> it = seiInfo.operationInfo.iterator(); it.hasNext(); ) {
/* 217 */       boolean match = true;
/* 218 */       MetadataOperationInfo opInfo = it.next();
/*     */       
/* 220 */       if (!opInfo.wsdlOpName.equals(operationName)) {
/*     */         continue;
/*     */       }
/* 223 */       if (opInfo.inputMessage != null && inputMsg != null && !opInfo.inputMessage.equals(inMsgQName)) {
/*     */         continue;
/*     */       }
/*     */       
/* 227 */       if (opInfo.outputMessage != null && outputMsg != null && !opInfo.outputMessage.equals(outMsgQName)) {
/*     */         continue;
/*     */       }
/*     */       
/* 231 */       Map<Object, Object> retMap = new HashMap<Object, Object>();
/* 232 */       if (opInfo.retPart != null) {
/* 233 */         retMap.put(opInfo.retPart.partName, opInfo.retPart);
/*     */       }
/* 235 */       if (opInfo.isWrapped ? (
/* 236 */         !matchPartsWrapped(opInfo.inputParts, inputMsg, modeler) || !matchPartsWrapped(opInfo.outputParts, outputMsg, modeler) || !matchPartsWrapped(opInfo.inoutParts, inputMsg, modeler) || !matchPartsWrapped(opInfo.inoutParts, outputMsg, modeler) || !matchPartsWrapped(retMap, outputMsg, modeler)) : (
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 244 */         !matchParts(opInfo.inputParts, inputMsg) || !matchParts(opInfo.outputParts, outputMsg) || !matchParts(opInfo.inoutParts, inputMsg) || !matchParts(opInfo.inoutParts, outputMsg) || !matchParts(retMap, outputMsg))) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 252 */       return opInfo;
/*     */     } 
/*     */     
/* 255 */     throw new ModelerException("Unable to locate jax-rpc mapping meta-data for wsdl operation " + operationName + " in binding " + bindingQName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchParts(Map parts, Message msg) {
/* 261 */     Iterator<Map.Entry> it = parts.entrySet().iterator();
/* 262 */     while (it.hasNext()) {
/* 263 */       Map.Entry e = it.next();
/* 264 */       String partName = (String)e.getKey();
/* 265 */       MetadataParamInfo param = (MetadataParamInfo)e.getValue();
/*     */       
/* 267 */       if (!param.isSoapHeader && (
/* 268 */         msg == null || msg.getPart(partName) == null)) {
/* 269 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 273 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean matchPartsWrapped(Map parts, Message msg, J2EEModelerIf modeler) {
/* 279 */     LiteralSequenceType seqType = null;
/* 280 */     Iterator<Map.Entry> it = parts.entrySet().iterator();
/* 281 */     while (it.hasNext()) {
/* 282 */       Map.Entry e = it.next();
/* 283 */       String partName = (String)e.getKey();
/* 284 */       MetadataParamInfo param = (MetadataParamInfo)e.getValue();
/*     */       
/* 286 */       if (!param.isSoapHeader) {
/* 287 */         if (seqType == null) {
/* 288 */           if (msg == null || msg.numParts() != 1) {
/* 289 */             return false;
/*     */           }
/* 291 */           MessagePart part = msg.parts().next();
/* 292 */           if (part.getDescriptorKind() != SchemaKinds.XSD_ELEMENT) {
/* 293 */             return false;
/*     */           }
/* 295 */           QName elementType = part.getDescriptor();
/* 296 */           LiteralType literalType = modeler.getElementTypeToLiteralType(elementType);
/*     */           
/* 298 */           if (literalType == null || !(literalType instanceof LiteralSequenceType))
/*     */           {
/* 300 */             return false;
/*     */           }
/* 302 */           seqType = (LiteralSequenceType)literalType;
/*     */         } 
/* 304 */         if (seqType.getElementMemberByName(partName) == null) {
/* 305 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 309 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String javaNameOfType(TypeDefinitionComponent component) {
/* 319 */     QName typeQName = component.getName();
/* 320 */     debug("javaNameOfType component.getName = " + component.getName());
/* 321 */     javaXmlTypeMappingType xmlMap = null;
/*     */     
/* 323 */     if (component.isSimple()) {
/* 324 */       xmlMap = (javaXmlTypeMappingType)this.simpleTypeMap.get(typeQName);
/*     */     
/*     */     }
/* 327 */     else if (component.isComplex()) {
/* 328 */       xmlMap = (javaXmlTypeMappingType)this.complexTypeMap.get(typeQName);
/*     */ 
/*     */ 
/*     */       
/* 332 */       if (xmlMap == null) {
/* 333 */         xmlMap = (javaXmlTypeMappingType)this.elementMap.get(typeQName);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 338 */       throw new IllegalArgumentException("type is neither simple nor complex");
/*     */     } 
/*     */     
/* 341 */     if (xmlMap != null) {
/* 342 */       return xmlMap.getJavaType().getElementValue();
/*     */     }
/*     */ 
/*     */     
/* 346 */     return makePackageQualified(getNames().validJavaClassName(typeQName.getLocalPart()), typeQName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaMemberInfo javaMemberInfo(TypeDefinitionComponent component, String member) {
/* 358 */     QName typeQName = component.getName();
/* 359 */     javaXmlTypeMappingType xmlMap = null;
/* 360 */     if (component.isComplex()) {
/* 361 */       xmlMap = (javaXmlTypeMappingType)this.complexTypeMap.get(typeQName);
/*     */     }
/*     */     else {
/*     */       
/* 365 */       throw new IllegalArgumentException("type is neither simple nor complex");
/*     */     } 
/*     */     
/* 368 */     JavaMemberInfo ret = null;
/* 369 */     if (xmlMap != null) {
/* 370 */       int numVariableMappings = xmlMap.getVariableMappingCount();
/* 371 */       for (int i = 0; i < numVariableMappings; i++) {
/* 372 */         variableMappingType variableMap = xmlMap.getVariableMapping(i);
/* 373 */         if (variableMap.getXmlElementName() != null && variableMap.getXmlElementName().getElementValue().equals(member)) {
/* 374 */           ret = new JavaMemberInfo();
/* 375 */           ret.javaMemberName = variableMap.getJavaVariableName().getElementValue();
/* 376 */           ret.isDataMember = !(variableMap.getDataMember() == null);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 382 */     if (ret == null) {
/*     */       
/* 384 */       ret = new JavaMemberInfo();
/* 385 */       ret.javaMemberName = getNames().validJavaMemberName(member);
/* 386 */       ret.isDataMember = false;
/*     */     } 
/* 388 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String javaNameOfElementType(QName typeQName, String anonymousName) {
/* 399 */     javaXmlTypeMappingType xmlMap = null;
/*     */     
/* 401 */     debug("Looking for type = " + typeQName + "; anonymous = " + anonymousName);
/*     */     
/* 403 */     if (anonymousName != null) {
/* 404 */       String anonymousTypeName = typeQName.getNamespaceURI() + ":" + anonymousName;
/* 405 */       xmlMap = (javaXmlTypeMappingType)this.anonymousElementMap.get(anonymousTypeName);
/*     */ 
/*     */       
/* 408 */       if (xmlMap == null) {
/* 409 */         xmlMap = (javaXmlTypeMappingType)this.anonymousComplexTypeMap.get(anonymousTypeName);
/*     */       }
/*     */     } else {
/* 412 */       xmlMap = (javaXmlTypeMappingType)this.elementMap.get(typeQName);
/*     */ 
/*     */       
/* 415 */       if (xmlMap == null) {
/* 416 */         xmlMap = (javaXmlTypeMappingType)this.complexTypeMap.get(typeQName);
/*     */       }
/*     */     } 
/*     */     
/* 420 */     debug("111 typeQName = " + typeQName + "; xmlMap = " + xmlMap);
/* 421 */     if (xmlMap != null) {
/* 422 */       return xmlMap.getJavaType().getElementValue();
/*     */     }
/*     */ 
/*     */     
/* 426 */     return makePackageQualified(getNames().validJavaClassName(typeQName.getLocalPart()), typeQName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JavaMemberInfo javaMemberOfElementInfo(QName typeQName, String member) {
/* 437 */     javaXmlTypeMappingType xmlMap = (javaXmlTypeMappingType)this.complexTypeMap.get(typeQName);
/*     */ 
/*     */     
/* 440 */     JavaMemberInfo ret = null;
/* 441 */     if (xmlMap != null) {
/* 442 */       int numVariableMappings = xmlMap.getVariableMappingCount();
/* 443 */       for (int i = 0; i < numVariableMappings; i++) {
/* 444 */         variableMappingType variableMap = xmlMap.getVariableMapping(i);
/* 445 */         if (variableMap.getXmlElementName() != null && variableMap.getXmlElementName().getElementValue().equals(member)) {
/* 446 */           ret = new JavaMemberInfo();
/* 447 */           ret.javaMemberName = variableMap.getJavaVariableName().getElementValue();
/* 448 */           ret.isDataMember = !(variableMap.getDataMember() == null);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 454 */     if (ret == null) {
/*     */       
/* 456 */       ret = new JavaMemberInfo();
/* 457 */       ret.javaMemberName = getNames().validJavaMemberName(member);
/* 458 */       ret.isDataMember = false;
/*     */     } 
/* 460 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String javaNameOfPort(QName port) {
/* 469 */     portMappingType portMapping = (portMappingType)this.portMap.get(port);
/* 470 */     if (portMapping != null)
/* 471 */       return portMapping.getJavaPortName().getElementValue(); 
/* 472 */     return null;
/*     */   }
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
/*     */   public ExceptionInfo getExceptionInfo(QName wsdlMessage, String partName) {
/* 486 */     return (ExceptionInfo)this.exceptionMap.get(wsdlMessage.toString() + partName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap exceptionConstructorOrder(QName wsdlMessage) {
/* 496 */     ExceptionInfo exInfo = (ExceptionInfo)this.exceptionMap.get(wsdlMessage.toString());
/* 497 */     if (exInfo == null) {
/* 498 */       return null;
/*     */     }
/* 500 */     return exInfo.constructorOrder;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getJavaPackageName(QName qname) {
/* 505 */     String ret = null;
/* 506 */     NamespaceMappingRegistryInfo nsMap = getNamespaceMappingRegistry();
/* 507 */     if (nsMap != null) {
/* 508 */       NamespaceMappingInfo info = nsMap.getNamespaceMappingInfo(qname);
/* 509 */       if (info != null)
/* 510 */         return info.getJavaPackageName(); 
/*     */     } 
/* 512 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private String makePackageQualified(String s, QName name) {
/* 517 */     String javaPackageName = getJavaPackageName(name);
/* 518 */     if (javaPackageName != null) {
/* 519 */       return javaPackageName + "." + s;
/*     */     }
/*     */     
/* 522 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QName stringToQName(String s) {
/* 529 */     return new QName(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void init() {
/* 535 */     NamespaceHelper nsHelper = new NamespaceHelper();
/* 536 */     javaWsdlMapping javaWsdlMap = this.mappingFileXml.getJavaWsdlMapping();
/* 537 */     nsHelper = nsHelper.push((ComplexType)javaWsdlMap);
/*     */     
/* 539 */     int numjavaXmlMap = javaWsdlMap.getJavaXmlTypeMappingCount();
/* 540 */     debug("----------- numcount = : " + numjavaXmlMap);
/* 541 */     for (int i = 0; i < numjavaXmlMap; i++) {
/* 542 */       javaXmlTypeMappingType xmlMap = javaWsdlMap.getJavaXmlTypeMapping(i);
/* 543 */       nsHelper = nsHelper.push((ComplexType)xmlMap);
/* 544 */       String scope = xmlMap.getQnameScope().getElementValue();
/* 545 */       xsdQNameType rtQname = xmlMap.getRootTypeQname();
/*     */       
/* 547 */       if (rtQname != null) {
/* 548 */         nsHelper = nsHelper.push((ComplexType)rtQname);
/* 549 */         QName qName = nsHelper.getQName(rtQname.getElementValue());
/* 550 */         nsHelper = nsHelper.pop();
/* 551 */         debug("rootTypeQNameID = " + rtQname.getId() + "; scope = " + scope + "; qName = " + qName);
/*     */         
/* 553 */         if (scope.equals("simpleType")) {
/* 554 */           this.simpleTypeMap.put(qName, xmlMap);
/* 555 */         } else if (scope.equals("complexType")) {
/* 556 */           this.complexTypeMap.put(qName, xmlMap);
/* 557 */         } else if (scope.equals("element")) {
/* 558 */           this.elementMap.put(qName, xmlMap);
/*     */         } 
/*     */       } else {
/* 561 */         String anonymousTypeName = xmlMap.getAnonymousTypeQname().getElementValue();
/* 562 */         debug("anonymousTypeQName = " + anonymousTypeName + " scope = " + scope);
/*     */         
/* 564 */         if (scope.equals("simpleType")) {
/* 565 */           this.anonymousSimpleTypeMap.put(anonymousTypeName, xmlMap);
/* 566 */         } else if (scope.equals("complexType")) {
/* 567 */           this.anonymousComplexTypeMap.put(anonymousTypeName, xmlMap);
/* 568 */         } else if (scope.equals("element")) {
/* 569 */           this.anonymousElementMap.put(anonymousTypeName, xmlMap);
/*     */         } 
/*     */       } 
/* 572 */       nsHelper = nsHelper.pop();
/*     */     } 
/*     */     
/* 575 */     int numExceptions = javaWsdlMap.getExceptionMappingCount();
/* 576 */     for (int j = 0; j < numExceptions; j++) {
/* 577 */       ExceptionInfo exInfo = new ExceptionInfo();
/* 578 */       exceptionMappingType exMap = javaWsdlMap.getExceptionMapping(j);
/* 579 */       nsHelper = nsHelper.push((ComplexType)exMap);
/* 580 */       exInfo.exceptionType = exMap.getExceptionType().getElementValue();
/* 581 */       wsdlMessageType wsdlMsg = exMap.getWsdlMessage();
/* 582 */       nsHelper = nsHelper.push((ComplexType)wsdlMsg);
/* 583 */       QName qname = nsHelper.getQName(wsdlMsg.getElementValue());
/* 584 */       nsHelper = nsHelper.pop();
/* 585 */       exInfo.wsdlMessage = qname;
/* 586 */       String partName = null;
/* 587 */       if (exMap.getWsdlMessagePartName() != null) {
/* 588 */         partName = exMap.getWsdlMessagePartName().getElementValue();
/* 589 */         exInfo.wsdlMessagePartName = partName;
/*     */       } 
/* 591 */       exInfo.constructorOrder = new HashMap<Object, Object>();
/* 592 */       constructorParameterOrderType ctorOrder = exMap.getConstructorParameterOrder();
/* 593 */       if (ctorOrder != null) {
/* 594 */         int numConstructorParams = ctorOrder.getElementNameCount();
/* 595 */         for (int n = 0; n < numConstructorParams; n++) {
/* 596 */           String param = ctorOrder.getElementName(n).getElementValue();
/*     */           
/* 598 */           exInfo.constructorOrder.put(param, new Integer(n));
/*     */         } 
/*     */       } 
/* 601 */       this.exceptionMap.put(qname.toString() + partName, exInfo);
/* 602 */       debug("===> ADD EXCEPTION MAP = " + qname.toString() + partName);
/* 603 */       nsHelper = nsHelper.pop();
/*     */     } 
/*     */     
/* 606 */     int numServiceInterfaceMappings = javaWsdlMap.getServiceInterfaceMappingCount();
/* 607 */     debug("Retrieving " + numServiceInterfaceMappings + "serviceInterfaceMapping");
/*     */     
/* 609 */     for (int k = 0; k < numServiceInterfaceMappings; k++) {
/* 610 */       serviceInterfaceMappingType serviceMapping = javaWsdlMap.getServiceInterfaceMapping(k);
/* 611 */       nsHelper = nsHelper.push((ComplexType)serviceMapping);
/* 612 */       xsdQNameType wsdlSvcname = serviceMapping.getWsdlServiceName();
/* 613 */       nsHelper = nsHelper.push((ComplexType)wsdlSvcname);
/* 614 */       QName serviceQName = nsHelper.getQName(wsdlSvcname.getElementValue());
/* 615 */       nsHelper = nsHelper.pop();
/* 616 */       String serviceNS = serviceQName.getNamespaceURI();
/* 617 */       this.serviceMap.put(serviceQName, serviceMapping);
/*     */       
/* 619 */       int numPortMaps = serviceMapping.getPortMappingCount();
/* 620 */       for (int n = 0; n < numPortMaps; n++) {
/* 621 */         portMappingType portMapping = serviceMapping.getPortMapping(n);
/* 622 */         QName portQName = new QName(serviceNS, portMapping.getPortName().getElementValue());
/*     */ 
/*     */         
/* 625 */         this.portMap.put(portQName, portMapping);
/*     */       } 
/*     */       
/* 628 */       nsHelper = nsHelper.pop();
/*     */     } 
/*     */     
/* 631 */     int numSEIMapping = javaWsdlMap.getServiceEndpointInterfaceMappingCount();
/* 632 */     debug("Retrieving " + numSEIMapping + "serviceEndpointInterfaceMapping");
/* 633 */     for (int m = 0; m < numSEIMapping; m++) {
/* 634 */       serviceEndpointInterfaceMappingType seiMap = javaWsdlMap.getServiceEndpointInterfaceMapping(m);
/*     */       
/* 636 */       nsHelper = nsHelper.push((ComplexType)seiMap);
/*     */       
/* 638 */       MetadataSEIInfo seiInfo = new MetadataSEIInfo();
/* 639 */       xsdQNameType wsdlBnd = seiMap.getWsdlBinding();
/* 640 */       nsHelper = nsHelper.push((ComplexType)wsdlBnd);
/* 641 */       seiInfo.bindingQName = nsHelper.getQName(wsdlBnd.getElementValue());
/*     */       
/* 643 */       nsHelper = nsHelper.pop();
/*     */       
/* 645 */       xsdQNameType wsdlPT = seiMap.getWsdlPortType();
/* 646 */       nsHelper = nsHelper.push((ComplexType)wsdlPT);
/* 647 */       seiInfo.portTypeQName = nsHelper.getQName(wsdlPT.getElementValue());
/* 648 */       nsHelper = nsHelper.pop();
/*     */       
/* 650 */       seiInfo.javaName = seiMap.getServiceEndpointInterface().getElementValue();
/*     */       
/* 652 */       int numMethodMaps = seiMap.getServiceEndpointMethodMappingCount();
/* 653 */       debug("adding binding: " + seiInfo.bindingQName + " portType " + seiInfo.portTypeQName + " with " + numMethodMaps + " methods");
/* 654 */       for (int n = 0; n < numMethodMaps; n++) {
/* 655 */         serviceEndpointMethodMappingType methodMap = seiMap.getServiceEndpointMethodMapping(n);
/*     */         
/* 657 */         nsHelper = nsHelper.push((ComplexType)methodMap);
/* 658 */         MetadataOperationInfo opInfo = new MetadataOperationInfo();
/* 659 */         opInfo.wsdlOpName = methodMap.getWsdlOperation().getElementValue();
/* 660 */         opInfo.javaOpName = methodMap.getJavaMethodName().getElementValue();
/* 661 */         opInfo.isWrapped = !(methodMap.getWrappedElement() == null);
/* 662 */         int numParams = methodMap.getMethodParamPartsMappingCount();
/* 663 */         debug("adding wsdlOp " + opInfo.wsdlOpName + " javaOp " + opInfo.javaOpName + " with " + numParams + " parameters" + "; isWrapped = " + opInfo.isWrapped);
/* 664 */         for (int i1 = 0; i1 < numParams; i1++) {
/* 665 */           methodParamPartsMappingType methodParamMap = methodMap.getMethodParamPartsMapping(i1);
/*     */           
/* 667 */           nsHelper = nsHelper.push((ComplexType)methodParamMap);
/* 668 */           MetadataParamInfo paramInfo = new MetadataParamInfo();
/* 669 */           paramInfo.position = (new Integer(methodParamMap.getParamPosition().getElementValue())).intValue();
/* 670 */           paramInfo.javaType = methodParamMap.getParamType().getElementValue();
/* 671 */           wsdlMessageMappingType wsdlMsgMap = methodParamMap.getWsdlMessageMapping();
/* 672 */           paramInfo.partName = wsdlMsgMap.getWsdlMessagePartName().getElementValue();
/* 673 */           paramInfo.mode = wsdlMsgMap.getParameterMode().getElementValue();
/* 674 */           wsdlMessageType wsdlMessageType = wsdlMsgMap.getWsdlMessage();
/* 675 */           nsHelper = nsHelper.push((ComplexType)wsdlMessageType);
/* 676 */           QName msgQName = nsHelper.getQName(wsdlMessageType.getElementValue());
/*     */           
/* 678 */           nsHelper = nsHelper.pop();
/* 679 */           paramInfo.isSoapHeader = !(wsdlMsgMap.getSoapHeader() == null);
/* 680 */           if (paramInfo.mode.equals("IN")) {
/* 681 */             if (paramInfo.isSoapHeader) {
/* 682 */               paramInfo.headerMessage = msgQName;
/* 683 */               opInfo.explicitcontext = true;
/*     */             }
/* 685 */             else if (opInfo.inputMessage == null) {
/* 686 */               opInfo.inputMessage = msgQName;
/* 687 */             } else if (!opInfo.inputMessage.equals(msgQName)) {
/* 688 */               throw new ModelerException("inconsistent input message QNames found: " + opInfo.inputMessage + " and " + msgQName + " for IN param of operation " + opInfo.wsdlOpName);
/*     */             } 
/*     */             
/* 691 */             opInfo.inputParts.put(paramInfo.partName, paramInfo);
/*     */           }
/* 693 */           else if (paramInfo.mode.equals("OUT")) {
/* 694 */             if (paramInfo.isSoapHeader) {
/* 695 */               paramInfo.headerMessage = msgQName;
/* 696 */               opInfo.explicitcontext = true;
/*     */             }
/* 698 */             else if (opInfo.outputMessage == null) {
/* 699 */               opInfo.outputMessage = msgQName;
/* 700 */             } else if (!opInfo.outputMessage.equals(msgQName)) {
/* 701 */               throw new ModelerException("Inconsistent output message QNames found: " + opInfo.outputMessage + " and " + msgQName);
/*     */             } 
/*     */             
/* 704 */             opInfo.outputParts.put(paramInfo.partName, paramInfo);
/*     */           }
/* 706 */           else if (paramInfo.mode.equals("INOUT")) {
/* 707 */             if (paramInfo.isSoapHeader) {
/* 708 */               paramInfo.headerMessage = msgQName;
/* 709 */               opInfo.explicitcontext = true;
/*     */ 
/*     */             
/*     */             }
/* 713 */             else if (opInfo.inputMessage == null) {
/* 714 */               opInfo.inputMessage = msgQName;
/* 715 */             } else if (!opInfo.inputMessage.equals(msgQName)) {
/* 716 */               throw new ModelerException("inconsistent input message QNames found: " + opInfo.inputMessage + " and " + msgQName + " for INOUT param of operation " + opInfo.wsdlOpName);
/*     */             } 
/*     */             
/* 719 */             opInfo.inoutParts.put(paramInfo.partName, paramInfo);
/*     */           } else {
/* 721 */             throw new ModelerException("invalid jaxrpc mapping meta-data: found param mode " + paramInfo.mode);
/* 722 */           }  nsHelper = nsHelper.pop();
/*     */           
/* 724 */           debug("adding parameter: " + paramInfo.javaType + " from message: " + msgQName + " and part " + paramInfo.partName + "; input msg = " + opInfo.inputMessage + "; outputMsg = " + opInfo.outputMessage + "; is header = " + paramInfo.isSoapHeader);
/*     */         } 
/*     */ 
/*     */         
/* 728 */         wsdlReturnValueMappingType retMap = methodMap.getWsdlReturnValueMapping();
/* 729 */         if (retMap != null) {
/*     */           
/* 731 */           wsdlMessageType wsdlMessageType = retMap.getWsdlMessage();
/* 732 */           nsHelper = nsHelper.push((ComplexType)wsdlMessageType);
/* 733 */           QName msgQName = nsHelper.getQName(wsdlMessageType.getElementValue());
/*     */           
/* 735 */           nsHelper = nsHelper.pop();
/*     */           
/* 737 */           if (opInfo.outputMessage == null) {
/* 738 */             opInfo.outputMessage = msgQName;
/* 739 */           } else if (!opInfo.outputMessage.equals(msgQName)) {
/* 740 */             throw new ModelerException("inconsistent input message QNames found: " + opInfo.outputMessage + " and " + msgQName + " in return value of operation " + opInfo.wsdlOpName);
/*     */           } 
/* 742 */           if (retMap.getWsdlMessagePartName() != null) {
/*     */             
/* 744 */             MetadataParamInfo retParam = new MetadataParamInfo();
/* 745 */             retParam.javaType = retMap.getMethodReturnValue().getElementValue();
/* 746 */             retParam.partName = retMap.getWsdlMessagePartName().getElementValue();
/* 747 */             opInfo.retPart = retParam;
/* 748 */             debug("adding return value: " + retParam.javaType + " from message: " + msgQName + " and part " + retParam.partName);
/*     */           } 
/*     */         } 
/* 751 */         debug("adding wsdlOp " + opInfo.wsdlOpName + " javaOp " + opInfo.javaOpName + " with " + numParams + " parameters" + "; isWrapped = " + opInfo.isWrapped + "; inputMsg = " + opInfo.inputMessage + "; outputMessage = " + opInfo.outputMessage);
/* 752 */         seiInfo.operationInfo.add(opInfo);
/*     */         
/* 754 */         nsHelper = nsHelper.pop();
/*     */       } 
/*     */       
/* 757 */       debug("putting " + seiInfo.bindingQName + " in serviceEndpointMap");
/* 758 */       this.serviceEndpointMap.put(seiInfo.bindingQName, seiInfo);
/*     */       
/* 760 */       nsHelper = nsHelper.pop();
/*     */     } 
/* 762 */     nsHelper = nsHelper.pop();
/*     */     
/* 764 */     debug("init complete");
/*     */   }
/*     */   
/*     */   String getNonQualifiedNameFor(QName name) {
/* 768 */     return getNames().validJavaClassName(name.getLocalPart());
/*     */   }
/*     */   
/*     */   private Names getNames() {
/* 772 */     return ((ProcessorEnvironment)getConfiguration().getEnvironment()).getNames();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 777 */   HashMap serviceMap = new HashMap<Object, Object>();
/* 778 */   HashMap serviceEndpointMap = new HashMap<Object, Object>();
/* 779 */   HashMap portMap = new HashMap<Object, Object>();
/* 780 */   HashMap elementMap = new HashMap<Object, Object>();
/* 781 */   HashMap exceptionMap = new HashMap<Object, Object>();
/* 782 */   HashMap complexTypeMap = new HashMap<Object, Object>();
/* 783 */   HashMap simpleTypeMap = new HashMap<Object, Object>();
/* 784 */   HashMap anonymousSimpleTypeMap = new HashMap<Object, Object>();
/* 785 */   HashMap anonymousComplexTypeMap = new HashMap<Object, Object>();
/* 786 */   HashMap anonymousElementMap = new HashMap<Object, Object>();
/*     */ 
/*     */   
/*     */   public static class JavaMemberInfo
/*     */     extends SchemaAnalyzerBase.SchemaJavaMemberInfo {}
/*     */ 
/*     */   
/*     */   public static class ExceptionInfo
/*     */     extends WSDLModelerBase.WSDLExceptionInfo {}
/*     */ 
/*     */   
/*     */   public class MetadataSEIInfo
/*     */   {
/*     */     public String javaName;
/*     */     public QName bindingQName;
/*     */     public QName portTypeQName;
/* 802 */     List operationInfo = new Vector();
/*     */   }
/*     */ 
/*     */   
/*     */   public class MetadataOperationInfo
/*     */   {
/*     */     public String wsdlOpName;
/*     */     public String javaOpName;
/*     */     public QName inputMessage;
/*     */     public QName outputMessage;
/* 812 */     public HashMap inputParts = new HashMap<Object, Object>();
/* 813 */     public HashMap outputParts = new HashMap<Object, Object>();
/* 814 */     public HashMap inoutParts = new HashMap<Object, Object>();
/*     */     public boolean isWrapped;
/*     */     public boolean explicitcontext = false;
/*     */     public J2EEModelInfo.MetadataParamInfo retPart;
/*     */   }
/*     */   
/*     */   public class MetadataParamInfo
/*     */   {
/*     */     public QName headerMessage;
/*     */     public int position;
/*     */     public String javaType;
/*     */     public String partName;
/*     */     public String mode;
/*     */     public boolean isSoapHeader;
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 831 */     if (DEBUG != null)
/* 832 */       System.out.println("[J2EEModelInfo] ==> " + msg); 
/*     */   }
/*     */   
/* 835 */   private static String DEBUG = System.getProperty("com.sun.xml.rpc.j2ee.debug");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\J2EEModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */