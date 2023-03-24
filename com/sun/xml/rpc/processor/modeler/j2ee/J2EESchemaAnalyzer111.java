/*     */ package com.sun.xml.rpc.processor.modeler.j2ee;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer111;
/*     */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzerBase;
/*     */ import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
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
/*     */ public class J2EESchemaAnalyzer111
/*     */   extends SchemaAnalyzer111
/*     */   implements J2EESchemaAnalyzerIf
/*     */ {
/*     */   private JavaSimpleTypeCreator javaSimpleTypeCreator;
/*     */   private J2EEModelInfo _j2eeModelInfo;
/*     */   private J2EESchemaAnalyzerHelper helper;
/*     */   
/*     */   public J2EESchemaAnalyzer111(AbstractDocument document, J2EEModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*  64 */     super(document, (ModelInfo)modelInfo, options, conflictingClassNames, javaTypes);
/*  65 */     this._j2eeModelInfo = modelInfo;
/*  66 */     this.javaSimpleTypeCreator = new JavaSimpleTypeCreator();
/*  67 */     this.helper = new J2EESchemaAnalyzerHelper(this, modelInfo, this._env, javaTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfType(TypeDefinitionComponent component, QName nameHint) {
/*  74 */     return this.helper.getJavaNameOfType(component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateModifiers(JavaStructureType javaStructureType) {
/*  82 */     this.helper.updateModifiers(javaStructureType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfSOAPStructureType(SOAPStructureType structureType, TypeDefinitionComponent component, QName nameHint) {
/*  91 */     return this.helper.getJavaNameOfSOAPStructureType(structureType, component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SchemaAnalyzerBase.SchemaJavaMemberInfo getJavaMemberInfo(TypeDefinitionComponent component, ElementDeclarationComponent element) {
/*  99 */     return this.helper.getJavaMemberInfo(component, element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getJavaNameOfElementType(LiteralStructuredType structureType, TypeDefinitionComponent component, QName nameHint) {
/* 107 */     return this.helper.getJavaNameOfElementType(structureType, component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SchemaAnalyzerBase.SchemaJavaMemberInfo getJavaMemberOfElementInfo(QName typeName, String memberName) {
/* 115 */     return this.helper.getJavaMemberOfElementInfo(typeName, memberName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPType getSOAPMemberType(ComplexTypeDefinitionComponent component, SOAPStructureType structureType, ElementDeclarationComponent element, QName nameHint, boolean occursZeroOrOne) {
/* 125 */     return this.helper.getSOAPMemberType(component, structureType, element, nameHint, occursZeroOrOne);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralType getLiteralMemberType(ComplexTypeDefinitionComponent component, LiteralType memberType, ElementDeclarationComponent element, LiteralStructuredType structureType) {
/* 135 */     return this.helper.getLiteralMemberType(component, memberType, element, structureType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPType getSuperSOAPMemberType(ComplexTypeDefinitionComponent component, SOAPStructureType structureType, ElementDeclarationComponent element, QName nameHint, boolean occursZeroOrOne) {
/* 146 */     return super.getSOAPMemberType(component, structureType, element, nameHint, occursZeroOrOne);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SchemaAnalyzerBase.SchemaJavaMemberInfo getSuperJavaMemberInfo(TypeDefinitionComponent component, ElementDeclarationComponent element) {
/* 153 */     return super.getJavaMemberInfo(component, element);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\J2EESchemaAnalyzer111.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */