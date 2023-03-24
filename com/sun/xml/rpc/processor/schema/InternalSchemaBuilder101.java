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
/*     */ public class InternalSchemaBuilder101
/*     */   extends InternalSchemaBuilderBase
/*     */ {
/*     */   public InternalSchemaBuilder101(AbstractDocument document, Properties options) {
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
/*     */   protected void processElementParticle(SchemaElement element, ParticleComponent component, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/*  66 */     component.setTermTag(3);
/*     */ 
/*     */     
/*  69 */     ElementDeclarationComponent term = new ElementDeclarationComponent();
/*     */ 
/*     */     
/*  72 */     internalBuildElementDeclaration(term, element, schema);
/*     */     
/*  74 */     String refAttr = element.getValueOfAttributeOrNull("ref");
/*  75 */     if (refAttr != null)
/*     */     {
/*     */       
/*  78 */       failUnimplemented("F004");
/*     */     }
/*     */ 
/*     */     
/*  82 */     String nameAttr = element.getValueOfMandatoryAttribute("name");
/*     */     
/*  84 */     String formAttr = element.getValueOfAttributeOrNull("form");
/*     */     
/*  86 */     if (formAttr == null) {
/*  87 */       formAttr = element.getRoot().getValueOfAttributeOrNull("elementFormDefault");
/*     */       
/*  89 */       if (formAttr == null) {
/*  90 */         formAttr = "";
/*     */       }
/*     */     } 
/*  93 */     if (formAttr.equals("qualified")) {
/*  94 */       term.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*     */     } else {
/*     */       
/*  97 */       term.setName(new QName(nameAttr));
/*     */     } 
/*     */ 
/*     */     
/* 101 */     term.setScope(scope);
/*     */     
/* 103 */     component.setTermTag(3);
/* 104 */     component.setElementTerm(term);
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
/* 116 */     String baseAttr = element.getValueOfAttributeOrNull("base");
/*     */     
/* 118 */     if (baseAttr != null) {
/* 119 */       TypeDefinitionComponent base = schema.findTypeDefinition(element.asQName(baseAttr));
/*     */       
/* 121 */       if (base.isSimple()) {
/* 122 */         component.setBaseTypeDefinition((SimpleTypeDefinitionComponent)base);
/*     */       } else {
/*     */         
/* 125 */         failValidation("validation.notSimpleType", base.getName().getLocalPart());
/*     */       } 
/*     */     } else {
/*     */       
/* 129 */       failUnimplemented("F012");
/*     */     } 
/*     */ 
/*     */     
/* 133 */     component.setVarietyTag(component.getBaseTypeDefinition().getVarietyTag());
/*     */ 
/*     */ 
/*     */     
/* 137 */     component.setPrimitiveTypeDefinition(component.getBaseTypeDefinition().getPrimitiveTypeDefinition());
/*     */ 
/*     */ 
/*     */     
/* 141 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*     */     
/* 143 */     if (finalAttr == null) {
/* 144 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*     */       
/* 146 */       if (finalAttr == null) {
/* 147 */         finalAttr = "";
/*     */       }
/*     */     } 
/* 150 */     if (finalAttr.equals("")) {
/*     */ 
/*     */       
/* 153 */       component.setFinal(_setEmpty);
/* 154 */     } else if (finalAttr.equals("#all")) {
/* 155 */       component.setFinal(_setExtResListUnion);
/*     */     } else {
/* 157 */       component.setFinal(parseSymbolSet(finalAttr, _setExtResListUnion));
/*     */ 
/*     */       
/* 160 */       failUnimplemented("F013");
/*     */     } 
/*     */ 
/*     */     
/* 164 */     boolean gotOne = false;
/* 165 */     EnumerationFacet enumeration = new EnumerationFacet();
/* 166 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 167 */       SchemaElement child = iter.next();
/* 168 */       gotOne = true;
/* 169 */       if (child.getQName().equals(SchemaConstants.QNAME_ENUMERATION)) {
/* 170 */         String valueAttr = child.getValueOfAttributeOrNull("value");
/*     */         
/* 172 */         if (valueAttr == null) {
/* 173 */           failValidation("validation.missingRequiredAttribute", "value", child.getQName().getLocalPart());
/*     */         }
/*     */         
/* 176 */         enumeration.addValue(valueAttr); continue;
/*     */       } 
/* 178 */       failUnimplemented("F014");
/*     */     } 
/*     */ 
/*     */     
/* 182 */     component.addFacet(enumeration);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\InternalSchemaBuilder101.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */