/*     */ package com.sun.xml.rpc.processor.schema;
/*     */ 
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
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
/*     */ public class InternalSchemaBuilder103
/*     */   extends InternalSchemaBuilderBase
/*     */ {
/*     */   public InternalSchemaBuilder103(AbstractDocument document, Properties options) {
/*  53 */     super(document, options);
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
/*     */   protected void processElementParticle(SchemaElement element, ParticleComponent component, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/*  68 */     component.setTermTag(3);
/*     */ 
/*     */     
/*  71 */     ElementDeclarationComponent term = new ElementDeclarationComponent();
/*     */ 
/*     */     
/*  74 */     internalBuildElementDeclaration(term, element, schema);
/*     */     
/*  76 */     String refAttr = element.getValueOfAttributeOrNull("ref");
/*  77 */     if (refAttr != null)
/*     */     {
/*     */       
/*  80 */       failUnimplemented("F004");
/*     */     }
/*     */ 
/*     */     
/*  84 */     String nameAttr = element.getValueOfMandatoryAttribute("name");
/*     */     
/*  86 */     String formAttr = element.getValueOfAttributeOrNull("form");
/*     */     
/*  88 */     if (formAttr == null) {
/*  89 */       formAttr = element.getRoot().getValueOfAttributeOrNull("elementFormDefault");
/*     */       
/*  91 */       if (formAttr == null) {
/*  92 */         formAttr = "";
/*     */       }
/*     */     } 
/*  95 */     if (formAttr.equals("qualified")) {
/*  96 */       term.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*     */     } else {
/*     */       
/*  99 */       term.setName(new QName(nameAttr));
/*     */     } 
/*     */ 
/*     */     
/* 103 */     term.setScope(scope);
/*     */     
/* 105 */     component.setTermTag(3);
/* 106 */     component.setElementTerm(term);
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
/*     */   protected void buildRestrictionSimpleTypeDefinition(SimpleTypeDefinitionComponent component, SchemaElement element, InternalSchema schema) {
/* 118 */     String baseAttr = element.getValueOfAttributeOrNull("base");
/*     */     
/* 120 */     if (baseAttr != null) {
/* 121 */       TypeDefinitionComponent base = schema.findTypeDefinition(element.asQName(baseAttr));
/*     */       
/* 123 */       if (base.isSimple()) {
/* 124 */         component.setBaseTypeDefinition((SimpleTypeDefinitionComponent)base);
/*     */       } else {
/*     */         
/* 127 */         failValidation("validation.notSimpleType", base.getName().getLocalPart());
/*     */       } 
/*     */     } else {
/*     */       
/* 131 */       failUnimplemented("F012");
/*     */     } 
/*     */ 
/*     */     
/* 135 */     component.setVarietyTag(component.getBaseTypeDefinition().getVarietyTag());
/*     */ 
/*     */ 
/*     */     
/* 139 */     component.setPrimitiveTypeDefinition(component.getBaseTypeDefinition().getPrimitiveTypeDefinition());
/*     */ 
/*     */ 
/*     */     
/* 143 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*     */     
/* 145 */     if (finalAttr == null) {
/* 146 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*     */       
/* 148 */       if (finalAttr == null) {
/* 149 */         finalAttr = "";
/*     */       }
/*     */     } 
/* 152 */     if (finalAttr.equals("")) {
/*     */ 
/*     */       
/* 155 */       component.setFinal(_setEmpty);
/* 156 */     } else if (finalAttr.equals("#all")) {
/* 157 */       component.setFinal(_setExtResListUnion);
/*     */     } else {
/* 159 */       component.setFinal(parseSymbolSet(finalAttr, _setExtResListUnion));
/*     */ 
/*     */       
/* 162 */       failUnimplemented("F013");
/*     */     } 
/*     */ 
/*     */     
/* 166 */     boolean gotOne = false;
/* 167 */     EnumerationFacet enumeration = new EnumerationFacet();
/* 168 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 169 */       SchemaElement child = iter.next();
/* 170 */       gotOne = true;
/* 171 */       if (child.getQName().equals(SchemaConstants.QNAME_ENUMERATION)) {
/* 172 */         String valueAttr = child.getValueOfAttributeOrNull("value");
/*     */         
/* 174 */         if (valueAttr == null) {
/* 175 */           failValidation("validation.missingRequiredAttribute", "value", child.getQName().getLocalPart());
/*     */         }
/*     */         
/* 178 */         enumeration.addValue(valueAttr); continue;
/*     */       } 
/* 180 */       failUnimplemented("F014");
/*     */     } 
/*     */ 
/*     */     
/* 184 */     component.addFacet(enumeration);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\InternalSchemaBuilder103.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */