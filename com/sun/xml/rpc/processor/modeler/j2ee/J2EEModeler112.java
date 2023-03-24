/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler112;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModelerBase;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.Service;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class J2EEModeler112
/*     */   extends WSDLModeler112
/*     */   implements J2EEModelerIf
/*     */ {
/*     */   private static final String PROPERTY_OPERATION_JAVA_NAME = "com.sun.enterprise.webservice.mapping.operationJavaName";
/*     */   private static final String WSDL_PARAMETER_ORDER = "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
/*     */   private J2EEModelerHelper helper;
/*     */   
/*     */   public J2EEModeler112(J2EEModelInfo modelInfo, Properties options) {
/*  55 */     super((WSDLModelInfo)modelInfo, options);
/*  56 */     this.helper = new J2EEModelerHelper(this, modelInfo);
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
/*     */   protected SchemaAnalyzerBase getSchemaAnalyzerInstance(WSDLDocument document, WSDLModelInfo _modelInfo, Properties _options, Set _conflictingClassNames, JavaSimpleTypeCreator _javaTypes) {
/*  74 */     return (SchemaAnalyzerBase)new J2EESchemaAnalyzer112((AbstractDocument)document, (J2EEModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getServiceInterfaceName(QName serviceQName, Service wsdlService) {
/*  85 */     return this.helper.getServiceInterfaceName(serviceQName, wsdlService);
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfPort(QName portQName) {
/*  89 */     return this.helper.getJavaNameOfPort(portQName);
/*     */   }
/*     */   
/*     */   protected void setJavaOperationNameProperty(Message inputMessage) {
/*  93 */     this.helper.setJavaOperationNameProperty(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
/* 103 */     return this.helper.useExplicitServiceContextForDocLit(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
/* 112 */     return this.helper.useExplicitServiceContextForRpcLit(inputMessage);
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
/*     */   protected boolean useExplicitServiceContextForRpcEncoded(Message inputMessage) {
/* 124 */     return this.helper.useExplicitServiceContextForRpcEncoded(inputMessage);
/*     */   }
/*     */   
/*     */   protected boolean isUnwrappable(Message inputMessage) {
/* 128 */     boolean unwrap = this.helper.isUnwrappable(inputMessage);
/* 129 */     if (unwrap) {
/* 130 */       this.info.operation.setProperty("J2EE_UNWRAP", "true");
/*     */     }
/* 132 */     return unwrap;
/*     */   }
/*     */   
/*     */   protected void setCurrentPort(Port port) {
/* 136 */     this.helper.setCurrentPort(port);
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfSEI(Port port) {
/* 140 */     return this.helper.getJavaNameOfSEI(port);
/*     */   }
/*     */   
/*     */   public LiteralType getElementTypeToLiteralType(QName elementType) {
/* 144 */     return this.helper.getElementTypeToLiteralType(elementType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractType verifyResultType(AbstractType type, Operation operation) {
/* 151 */     return this.helper.verifyResultType(type, operation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractType verifyParameterType(AbstractType type, String partName, Operation operation) {
/* 159 */     return this.helper.verifyParameterType(type, partName, operation);
/*     */   }
/*     */   
/*     */   protected void postProcessSOAPOperation(Operation operation) {
/* 163 */     this.helper.postProcessSOAPOperation(operation);
/*     */   }
/*     */   
/*     */   protected WSDLModelerBase.WSDLExceptionInfo getExceptionInfo(Fault fault) {
/* 167 */     return this.helper.getExceptionInfo(fault);
/*     */   }
/*     */   
/*     */   protected void setSOAPUse() {
/* 171 */     this.helper.setSOAPUse();
/*     */   }
/*     */   
/*     */   protected String getJavaNameForOperation(Operation operation) {
/* 175 */     return this.helper.getJavaNameForOperation(operation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForDocLit(Message inputMessage) {
/* 181 */     return super.useExplicitServiceContextForDocLit(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForRpcLit(Message inputMessage) {
/* 187 */     return super.useExplicitServiceContextForRpcLit(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForRpcEncoded(Message inputMessage) {
/* 193 */     return super.useExplicitServiceContextForRpcEncoded(inputMessage);
/*     */   }
/*     */   
/*     */   public boolean isSuperUnwrappable() {
/* 197 */     return isUnwrappable();
/*     */   }
/*     */   
/*     */   public LiteralType getSuperElementTypeToLiteralType(QName elementType) {
/* 201 */     return super.getElementTypeToLiteralType(elementType);
/*     */   }
/*     */   
/*     */   public String getSuperJavaNameForOperation(Operation operation) {
/* 205 */     return super.getJavaNameForOperation(operation);
/*     */   }
/*     */   
/*     */   public WSDLModelerBase.ProcessSOAPOperationInfo getInfo() {
/* 209 */     return this.info;
/*     */   }
/*     */   
/*     */   public Message getSuperOutputMessage() {
/* 213 */     return getOutputMessage();
/*     */   }
/*     */   
/*     */   public Message getSuperInputMessage() {
/* 217 */     return getInputMessage();
/*     */   }
/*     */   
/*     */   public SOAPBody getSuperSOAPRequestBody() {
/* 221 */     return getSOAPRequestBody();
/*     */   }
/*     */   
/*     */   public SOAPBody getSuperSOAPResponseBody() {
/* 225 */     return getSOAPResponseBody();
/*     */   }
/*     */   
/*     */   public JavaSimpleTypeCreator getJavaTypes() {
/* 229 */     return this._javaTypes;
/*     */   }
/*     */   
/*     */   public boolean isConflictingServiceClassName(String name) {
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConflictingPortClassName(String name) {
/* 237 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isConflictingExceptionClassName(String name) {
/* 241 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EEModeler112.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */