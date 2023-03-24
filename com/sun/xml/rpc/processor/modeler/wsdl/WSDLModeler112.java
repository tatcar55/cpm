/*    */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*    */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*    */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*    */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*    */ import java.util.Properties;
/*    */ import java.util.Set;
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
/*    */ public class WSDLModeler112
/*    */   extends WSDLModelerBase
/*    */ {
/*    */   public WSDLModeler112(WSDLModelInfo modelInfo, Properties options) {
/* 48 */     super(modelInfo, options);
/*    */   }
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
/*    */   protected SchemaAnalyzerBase getSchemaAnalyzerInstance(WSDLDocument document, WSDLModelInfo _modelInfo, Properties _options, Set _conflictingClassNames, JavaSimpleTypeCreator _javaTypes) {
/* 61 */     return new SchemaAnalyzer112((AbstractDocument)document, (ModelInfo)_modelInfo, _options, _conflictingClassNames, _javaTypes);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\WSDLModeler112.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */