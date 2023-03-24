/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLModeler11
/*     */   extends WSDLModelerBase
/*     */ {
/*     */   public WSDLModeler11(WSDLModelInfo modelInfo, Properties options) {
/*  54 */     super(modelInfo, options);
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
/*     */   protected String getFaultName(String faultPartName, String soapFaultName, String bindFaultName, String faultMessageName) {
/*  73 */     return (soapFaultName == null) ? bindFaultName : soapFaultName;
/*     */   }
/*     */   
/*     */   protected String getLiteralJavaMemberName(Fault fault) {
/*  77 */     String javaMemberName = fault.getBlock().getName().getLocalPart();
/*     */     
/*  79 */     return javaMemberName;
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
/*     */   protected SchemaAnalyzerBase getSchemaAnalyzerInstance(WSDLDocument document, WSDLModelInfo _modelInfo, Properties _options, Set _conflictingClassNames, JavaSimpleTypeCreator _javaTypes) {
/*  91 */     return new SchemaAnalyzer11((AbstractDocument)document, (ModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
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
/*     */   protected AbstractType getHeaderFaultSequenceType(AbstractType faultType, MessagePart faultPart, QName elemName) {
/* 106 */     return faultType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSingleInOutPart(Set inputParameterNames, MessagePart outputPart) {
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Extension getAnyExtensionOfType(Extensible extensible, Class type) {
/* 125 */     return getExtensionOfType(extensible, type);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\WSDLModeler11.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */