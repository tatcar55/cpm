/*    */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*    */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SchemaAnalyzer112
/*    */   extends SchemaAnalyzer111
/*    */ {
/*    */   public SchemaAnalyzer112(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/* 56 */     super(document, modelInfo, options, conflictingClassNames, javaTypes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean doWeHandleAttributeTypeEnumeration(LiteralType attributeType) {
/* 63 */     return isAttributeEnumeration(attributeType);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\SchemaAnalyzer112.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */