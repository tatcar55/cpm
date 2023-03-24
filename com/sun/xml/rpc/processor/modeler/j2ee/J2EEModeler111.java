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
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler111;
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
/*     */ public class J2EEModeler111
/*     */   extends WSDLModeler111
/*     */   implements J2EEModelerIf
/*     */ {
/*     */   private static final String PROPERTY_OPERATION_JAVA_NAME = "com.sun.enterprise.webservice.mapping.operationJavaName";
/*     */   private static final String WSDL_PARAMETER_ORDER = "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
/*     */   private J2EEModelerHelper helper;
/*     */   
/*     */   public J2EEModeler111(J2EEModelInfo modelInfo, Properties options) {
/*  54 */     super((WSDLModelInfo)modelInfo, options);
/*  55 */     this.helper = new J2EEModelerHelper(this, modelInfo);
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
/*  73 */     return (SchemaAnalyzerBase)new J2EESchemaAnalyzer111((AbstractDocument)document, (J2EEModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
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
/*  84 */     return this.helper.getServiceInterfaceName(serviceQName, wsdlService);
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfPort(QName portQName) {
/*  88 */     return this.helper.getJavaNameOfPort(portQName);
/*     */   }
/*     */   
/*     */   protected void setJavaOperationNameProperty(Message inputMessage) {
/*  92 */     this.helper.setJavaOperationNameProperty(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useExplicitServiceContextForDocLit(Message inputMessage) {
/* 102 */     return this.helper.useExplicitServiceContextForDocLit(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useExplicitServiceContextForRpcLit(Message inputMessage) {
/* 111 */     return this.helper.useExplicitServiceContextForRpcLit(inputMessage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useExplicitServiceContextForRpcEncoded(Message inputMessage) {
/* 121 */     return this.helper.useExplicitServiceContextForRpcEncoded(inputMessage);
/*     */   }
/*     */   
/*     */   protected boolean isUnwrappable(Message inputMessage) {
/* 125 */     return this.helper.isUnwrappable(inputMessage);
/*     */   }
/*     */   
/*     */   protected void setCurrentPort(Port port) {
/* 129 */     this.helper.setCurrentPort(port);
/*     */   }
/*     */   
/*     */   protected String getJavaNameOfSEI(Port port) {
/* 133 */     return this.helper.getJavaNameOfSEI(port);
/*     */   }
/*     */   
/*     */   public LiteralType getElementTypeToLiteralType(QName elementType) {
/* 137 */     return this.helper.getElementTypeToLiteralType(elementType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractType verifyResultType(AbstractType type, Operation operation) {
/* 144 */     return this.helper.verifyResultType(type, operation);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractType verifyParameterType(AbstractType type, String partName, Operation operation) {
/* 152 */     return this.helper.verifyParameterType(type, partName, operation);
/*     */   }
/*     */   
/*     */   protected void postProcessSOAPOperation(Operation operation) {
/* 156 */     this.helper.postProcessSOAPOperation(operation);
/*     */   }
/*     */   
/*     */   protected WSDLModelerBase.WSDLExceptionInfo getExceptionInfo(Fault fault) {
/* 160 */     return this.helper.getExceptionInfo(fault);
/*     */   }
/*     */   
/*     */   protected void setSOAPUse() {
/* 164 */     this.helper.setSOAPUse();
/*     */   }
/*     */   
/*     */   protected String getJavaNameForOperation(Operation operation) {
/* 168 */     return this.helper.getJavaNameForOperation(operation);
/*     */   }
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForDocLit(Message inputMessage) {
/* 172 */     return super.useExplicitServiceContextForDocLit(inputMessage);
/*     */   }
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForRpcLit(Message inputMessage) {
/* 176 */     return super.useExplicitServiceContextForRpcLit(inputMessage);
/*     */   }
/*     */   
/*     */   public boolean useSuperExplicitServiceContextForRpcEncoded(Message inputMessage) {
/* 180 */     return super.useExplicitServiceContextForRpcEncoded(inputMessage);
/*     */   }
/*     */   
/*     */   public boolean isSuperUnwrappable() {
/* 184 */     return isUnwrappable();
/*     */   }
/*     */   
/*     */   public LiteralType getSuperElementTypeToLiteralType(QName elementType) {
/* 188 */     return super.getElementTypeToLiteralType(elementType);
/*     */   }
/*     */   
/*     */   public String getSuperJavaNameForOperation(Operation operation) {
/* 192 */     return super.getJavaNameForOperation(operation);
/*     */   }
/*     */   
/*     */   public WSDLModelerBase.ProcessSOAPOperationInfo getInfo() {
/* 196 */     return this.info;
/*     */   }
/*     */   
/*     */   public Message getSuperOutputMessage() {
/* 200 */     return getOutputMessage();
/*     */   }
/*     */   
/*     */   public Message getSuperInputMessage() {
/* 204 */     return getInputMessage();
/*     */   }
/*     */   
/*     */   public SOAPBody getSuperSOAPRequestBody() {
/* 208 */     return getSOAPRequestBody();
/*     */   }
/*     */   
/*     */   public SOAPBody getSuperSOAPResponseBody() {
/* 212 */     return getSOAPResponseBody();
/*     */   }
/*     */   
/*     */   public JavaSimpleTypeCreator getJavaTypes() {
/* 216 */     return this._javaTypes;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EEModeler111.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */