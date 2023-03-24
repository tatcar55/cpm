/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaAnalyzer111
/*     */   extends SchemaAnalyzer11
/*     */ {
/*     */   public SchemaAnalyzer111(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*  64 */     super(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPType nillableSchemaTypeToSOAPType(TypeDefinitionComponent component) {
/*  69 */     QName baseTypeName = getSimpleTypeBaseName(component);
/*  70 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaWrapperTypeMap.get(baseTypeName);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     if (javaType == null)
/*     */     {
/*  80 */       return schemaTypeToSOAPType(component, component.getName());
/*     */     }
/*     */     
/*  83 */     SOAPSimpleType result = (SOAPSimpleType)this._nillableSimpleTypeComponentToSOAPTypeMap.get(component);
/*     */ 
/*     */     
/*  86 */     if (result != null) {
/*  87 */       return (SOAPType)result;
/*     */     }
/*     */     
/*  90 */     result = new SOAPSimpleType(baseTypeName, javaType);
/*     */     
/*  92 */     result.setSchemaTypeRef(component.getName());
/*  93 */     setReferenceable(result);
/*  94 */     this._nillableSimpleTypeComponentToSOAPTypeMap.put(component, result);
/*  95 */     return (SOAPType)result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralSimpleType getNillableLiteralSimpleType(QName typeName, TypeDefinitionComponent typeDef) {
/* 104 */     QName baseTypeName = getSimpleTypeBaseName(typeDef);
/* 105 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaWrapperTypeMap.get(baseTypeName);
/*     */ 
/*     */ 
/*     */     
/* 109 */     if (javaType == null) {
/* 110 */       return null;
/*     */     }
/*     */     
/* 113 */     LiteralSimpleType result = (LiteralSimpleType)this._nillableSimpleTypeComponentToLiteralTypeMap.get(typeDef);
/*     */ 
/*     */     
/* 116 */     if (result == null) {
/* 117 */       result = new LiteralSimpleType(typeName, javaType, true);
/*     */ 
/*     */ 
/*     */       
/* 121 */       result.setSchemaTypeRef(typeDef.getName());
/* 122 */       this._nillableSimpleTypeComponentToLiteralTypeMap.put(typeDef, result);
/*     */     } 
/*     */     
/* 125 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\SchemaAnalyzer111.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */