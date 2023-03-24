/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiType;
/*     */ import com.sun.xml.rpc.processor.modeler.rmi.RmiUtils;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.Service;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class J2EEModelerHelper
/*     */ {
/*     */   private static final String PROPERTY_OPERATION_JAVA_NAME = "com.sun.enterprise.webservice.mapping.operationJavaName";
/*     */   private static final String WSDL_PARAMETER_ORDER = "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
/*     */   private J2EEModelInfo _j2eeModelInfo;
/*     */   private ProcessorEnvironment _env;
/*     */   private Port port;
/*     */   private Map modifiedTypes;
/*     */   private J2EEModelerIf base;
/*     */   
/*     */   public J2EEModelerHelper(J2EEModelerIf base, J2EEModelInfo modelInfo) {
/* 439 */     this.modifiedTypes = new HashMap<Object, Object>();
/*     */     this.base = base;
/*     */     this._j2eeModelInfo = modelInfo;
/*     */     this._env = (ProcessorEnvironment)modelInfo.getParent().getEnvironment();
/*     */   }
/*     */   
/*     */   protected String getServiceInterfaceName(QName serviceQName, Service wsdlService) {
/*     */     String serviceInterface = "";
/*     */     serviceInterface = this._j2eeModelInfo.javaNameOfService(serviceQName);
/*     */     return serviceInterface;
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfPort(QName portQName) {
/*     */     return this._j2eeModelInfo.javaNameOfPort(portQName);
/*     */   }
/*     */   
/*     */   protected void setJavaOperationNameProperty(Message inputMessage) {
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
/*     */     if (opMetaData != null)
/*     */       (this.base.getInfo()).operation.setProperty("com.sun.enterprise.webservice.mapping.operationJavaName", opMetaData.javaOpName); 
/*     */   }
/*     */   
/*     */   protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
/*     */     if (opMetaData != null)
/*     */       return opMetaData.explicitcontext; 
/*     */     return this.base.useSuperExplicitServiceContextForDocLit(inputMessage);
/*     */   }
/*     */   
/*     */   protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
/*     */     if (opMetaData != null)
/*     */       return opMetaData.explicitcontext; 
/*     */     return this.base.useSuperExplicitServiceContextForRpcLit(inputMessage);
/*     */   }
/*     */   
/*     */   protected J2EEModelInfo.MetadataOperationInfo findOperationInfo(Message inputMessage) {
/*     */     Message outputMessage = ((this.base.getInfo()).portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE) ? (this.base.getInfo()).portTypeOperation.getOutput().resolveMessage((AbstractDocument)(this.base.getInfo()).document) : null;
/*     */     QName bindingName = (QName)(this.base.getInfo()).modelPort.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = this._j2eeModelInfo.findOperationInfo(bindingName, (this.base.getInfo()).portTypeOperation.getName(), inputMessage, outputMessage, this.base);
/*     */     return opMetaData;
/*     */   }
/*     */   
/*     */   protected boolean useExplicitServiceContextForRpcEncoded(Message inputMessage) {
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
/*     */     if (opMetaData != null)
/*     */       return opMetaData.explicitcontext; 
/*     */     return this.base.useSuperExplicitServiceContextForRpcEncoded(inputMessage);
/*     */   }
/*     */   
/*     */   protected boolean isUnwrappable(Message inputMessage) {
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = findOperationInfo(inputMessage);
/*     */     if (opMetaData != null)
/*     */       return opMetaData.isWrapped; 
/*     */     return this.base.isSuperUnwrappable();
/*     */   }
/*     */   
/*     */   protected void setCurrentPort(Port port) {
/*     */     this.port = port;
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfSEI(Port port) {
/*     */     QName portTypeName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*     */     QName bindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     String interfaceName = this._j2eeModelInfo.javaNameOfSEI(bindingName, portTypeName, port.getName());
/*     */     return interfaceName;
/*     */   }
/*     */   
/*     */   public LiteralType getElementTypeToLiteralType(QName elementType) {
/*     */     return this.base.getSuperElementTypeToLiteralType(elementType);
/*     */   }
/*     */   
/*     */   protected AbstractType verifyResultType(AbstractType type, Operation operation) {
/*     */     QName bindingName = (QName)(this.base.getInfo()).modelPort.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = null;
/*     */     opMetaData = this._j2eeModelInfo.findOperationInfo(bindingName, (this.base.getInfo()).portTypeOperation.getName(), this.base.getSuperInputMessage(), this.base.getSuperOutputMessage(), this.base);
/*     */     if (opMetaData == null)
/*     */       return type; 
/*     */     J2EEModelInfo.MetadataParamInfo paramInfo = opMetaData.retPart;
/*     */     if (paramInfo == null)
/*     */       return type; 
/*     */     try {
/*     */       Class paramClass = RmiUtils.getClassForName(paramInfo.javaType, this._env.getClassLoader());
/*     */       return replaceJavaType(type, RmiType.getRmiType(paramClass));
/*     */     } catch (ClassNotFoundException e) {
/*     */       debug("ClassNotFoundException: " + e.getMessage());
/*     */       return type;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected AbstractType verifyParameterType(AbstractType type, String partName, Operation operation) {
/*     */     QName bindingName = (QName)(this.base.getInfo()).modelPort.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */     J2EEModelInfo.MetadataOperationInfo opMetaData = null;
/*     */     opMetaData = this._j2eeModelInfo.findOperationInfo(bindingName, (this.base.getInfo()).portTypeOperation.getName(), this.base.getSuperInputMessage(), this.base.getSuperOutputMessage(), this.base);
/*     */     if (opMetaData == null)
/*     */       return type; 
/*     */     J2EEModelInfo.MetadataParamInfo paramInfo = (J2EEModelInfo.MetadataParamInfo)opMetaData.inputParts.get(partName);
/*     */     if (paramInfo == null)
/*     */       paramInfo = (J2EEModelInfo.MetadataParamInfo)opMetaData.inoutParts.get(partName); 
/*     */     if (paramInfo == null)
/*     */       paramInfo = (J2EEModelInfo.MetadataParamInfo)opMetaData.outputParts.get(partName); 
/*     */     if (paramInfo == null)
/*     */       return type; 
/*     */     try {
/*     */       Class paramClass = RmiUtils.getClassForName(paramInfo.javaType, this._env.getClassLoader());
/*     */       return replaceJavaType(type, RmiType.getRmiType(paramClass));
/*     */     } catch (ClassNotFoundException e) {
/*     */       debug("ClassNotFoundException: " + e.getMessage());
/*     */       return type;
/*     */     } 
/*     */   }
/*     */   
/*     */   private AbstractType replaceJavaType(AbstractType type, RmiType rmiType) {
/*     */     if (type instanceof SOAPSimpleType || type instanceof LiteralSimpleType) {
/*     */       JavaSimpleType javaSimpleType = this.base.getJavaTypes().getJavaSimpleType(rmiType.typeString(false));
/*     */       if (javaSimpleType != null) {
/*     */         LiteralSimpleType literalSimpleType;
/*     */         if (type instanceof SOAPSimpleType) {
/*     */           SOAPSimpleType sOAPSimpleType = new SOAPSimpleType(type.getName(), javaSimpleType, Boolean.valueOf(((SOAPSimpleType)type).isReferenceable()).booleanValue());
/*     */         } else {
/*     */           literalSimpleType = new LiteralSimpleType(type.getName(), javaSimpleType, ((LiteralSimpleType)type).isNillable());
/*     */         } 
/*     */         literalSimpleType.setVersion(type.getVersion());
/*     */         return (AbstractType)literalSimpleType;
/*     */       } 
/*     */     } else if (type instanceof LiteralSequenceType && rmiType.getTypeCode() == 9 && ((LiteralSequenceType)type).getElementMembersCount() == 1) {
/*     */       LiteralElementMember elementMember = ((LiteralSequenceType)type).getElementMembers().next();
/*     */       String typeString = rmiType.typeString(false);
/*     */       if (type.getJavaType() instanceof JavaStructureType) {
/*     */         AbstractType modType = (AbstractType)this.modifiedTypes.get(type.getName());
/*     */         if (modType != null)
/*     */           return modType; 
/*     */         String elemName = elementMember.getJavaStructureMember().getName();
/*     */         JavaType elemType = elementMember.getJavaStructureMember().getType();
/*     */         if (!elemType.getName().equals("byte[]")) {
/*     */           LiteralArrayWrapperType arrayType = new LiteralArrayWrapperType(type.getName());
/*     */           arrayType.setJavaType(type.getJavaType());
/*     */           ((JavaStructureType)type.getJavaType()).setOwner(arrayType);
/*     */           arrayType.setJavaArrayType((JavaArrayType)elemType);
/*     */           arrayType.add(elementMember);
/*     */           this.modifiedTypes.put(arrayType.getName(), arrayType);
/*     */           return (AbstractType)arrayType;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     return type;
/*     */   }
/*     */   
/*     */   protected void postProcessSOAPOperation(Operation operation) {}
/*     */   
/*     */   protected WSDLModelerBase.WSDLExceptionInfo getExceptionInfo(Fault fault) {
/*     */     QName faultMsgQName = null;
/*     */     String partName = null;
/*     */     if (fault instanceof HeaderFault) {
/*     */       faultMsgQName = ((HeaderFault)fault).getMessage();
/*     */       partName = ((HeaderFault)fault).getPart();
/*     */     } else {
/*     */       faultMsgQName = new QName(fault.getBlock().getName().getNamespaceURI(), fault.getName());
/*     */     } 
/*     */     debug("Looking for fault qname = " + faultMsgQName + "; partName = " + partName);
/*     */     return (WSDLModelerBase.WSDLExceptionInfo)this._j2eeModelInfo.getExceptionInfo(faultMsgQName, partName);
/*     */   }
/*     */   
/*     */   protected void setSOAPUse() {
/*     */     SOAPBody requestBody = this.base.getSuperSOAPRequestBody();
/*     */     SOAPBody responseBody = null;
/*     */     if (requestBody != null && !requestBody.isLiteral() && !requestBody.isEncoded()) {
/*     */       requestBody.setUse(SOAPUse.LITERAL);
/*     */     } else if (requestBody != null && requestBody.isEncoded()) {
/*     */       requestBody.setUse(SOAPUse.ENCODED);
/*     */     } 
/*     */     if ((this.base.getInfo()).portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE) {
/*     */       responseBody = this.base.getSuperSOAPResponseBody();
/*     */       if (responseBody != null && !responseBody.isLiteral() && !responseBody.isEncoded()) {
/*     */         responseBody.setUse(SOAPUse.LITERAL);
/*     */       } else if (responseBody != null && responseBody.isEncoded()) {
/*     */         responseBody.setUse(SOAPUse.ENCODED);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getJavaNameForOperation(Operation operation) {
/*     */     String name = (String)operation.getProperty("com.sun.enterprise.webservice.mapping.operationJavaName");
/*     */     if (name == null)
/*     */       name = this.base.getSuperJavaNameForOperation(operation); 
/*     */     return name;
/*     */   }
/*     */   
/*     */   public static QName getQNameOf(GloballyKnown entity) {
/*     */     return new QName(entity.getDefining().getTargetNamespaceURI(), entity.getName());
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/*     */     if (this._env.verbose())
/*     */       System.out.println("[J2EEModelInfo] ==> " + msg); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EEModelerHelper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */