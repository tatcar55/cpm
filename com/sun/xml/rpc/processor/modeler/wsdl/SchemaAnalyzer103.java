/*     */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDByteEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDecimalEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDoubleEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDFloatEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDIntegerEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDLongEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDShortEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*     */ import com.sun.xml.rpc.processor.schema.AttributeUseComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
/*     */ import com.sun.xml.rpc.processor.schema.EnumerationFacet;
/*     */ import com.sun.xml.rpc.processor.schema.Facet;
/*     */ import com.sun.xml.rpc.processor.schema.ParticleComponent;
/*     */ import com.sun.xml.rpc.processor.schema.SimpleTypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
/*     */ import com.sun.xml.rpc.processor.schema.UnimplementedFeatureException;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ public class SchemaAnalyzer103
/*     */   extends SchemaAnalyzerBase
/*     */ {
/*     */   public SchemaAnalyzer103(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*  89 */     super(document, modelInfo, options, conflictingClassNames, javaTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPType simpleSchemaTypeToSOAPType(SimpleTypeDefinitionComponent component, QName nameHint) {
/*  95 */     if (component.getBaseTypeDefinition() == this._schema.getSimpleUrType()) {
/*  96 */       if (component.getVarietyTag() == 1)
/*     */       {
/*     */         
/*  99 */         String nsURI = component.getName().getNamespaceURI();
/* 100 */         if (nsURI != null && (nsURI.equals("http://www.w3.org/2001/XMLSchema") || nsURI.equals(soap11WSDLConstants.getSOAPEncodingNamespace())))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 105 */           if (!component.facets().hasNext()) {
/*     */             
/* 107 */             if (this._strictCompliance && (component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) || component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 113 */               if (!checkStrictCompliance(component.getName())) {
/* 114 */                 return null;
/*     */               }
/*     */             }
/* 117 */             if (!this._strictCompliance && component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)) {
/*     */ 
/*     */               
/* 120 */               SOAPAnyType anyType = new SOAPAnyType(component.getName());
/*     */               
/* 122 */               JavaSimpleType javaSimpleType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 127 */               if (javaSimpleType == null)
/*     */               {
/*     */ 
/*     */                 
/* 131 */                 fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*     */               }
/*     */ 
/*     */               
/* 135 */               anyType.setJavaType((JavaType)javaSimpleType);
/* 136 */               return (SOAPType)anyType;
/*     */             } 
/* 138 */             SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*     */             
/* 140 */             simpleType.setSchemaTypeRef(component.getName());
/* 141 */             JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 146 */             if (javaType == null)
/*     */             {
/*     */ 
/*     */               
/* 150 */               fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*     */             }
/*     */ 
/*     */             
/* 154 */             simpleType.setJavaType((JavaType)javaType);
/* 155 */             setReferenceable(simpleType);
/* 156 */             return (SOAPType)simpleType;
/*     */           } 
/*     */ 
/*     */           
/* 160 */           fail("model.schema.simpleTypeWithFacets", new Object[] { component.getName(), component.facets().next() });
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */           
/* 169 */           fail("model.schema.invalidSimpleType", new Object[] { component.getName() });
/*     */         }
/*     */       
/*     */       }
/* 173 */       else if (component.getVarietyTag() == 2)
/*     */       {
/*     */         
/* 176 */         if (doWeHandleSimpleSchemaTypeDerivationByList()) {
/* 177 */           return listToSOAPType(component, nameHint);
/*     */         }
/* 179 */         fail("model.schema.listNotSupported", new Object[] { component.getName() });
/*     */ 
/*     */       
/*     */       }
/*     */       else
/*     */       {
/*     */         
/* 186 */         fail("model.schema.unionNotSupported", new Object[] { component.getName() });
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 193 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*     */ 
/*     */       
/* 196 */       if (javaType != null) {
/* 197 */         SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*     */         
/* 199 */         simpleType.setSchemaTypeRef(component.getName());
/* 200 */         simpleType.setJavaType((JavaType)javaType);
/* 201 */         setReferenceable(simpleType);
/* 202 */         return (SOAPType)simpleType;
/*     */       } 
/* 204 */       SimpleTypeDefinitionComponent baseTypeComponent = component.getBaseTypeDefinition();
/*     */       
/* 206 */       Iterator<Facet> iter = component.facets();
/*     */       
/* 208 */       if (iter.hasNext() && component.getVarietyTag() == 1) {
/*     */ 
/*     */         
/* 211 */         Facet facet = iter.next();
/* 212 */         if (facet instanceof EnumerationFacet) {
/* 213 */           Iterator values = ((EnumerationFacet)facet).values();
/* 214 */           if (values.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 219 */             if (this._jaxbEnumType && component.getName() == null) {
/* 220 */               String nsURI = baseTypeComponent.getName().getNamespaceURI();
/*     */               
/* 222 */               if (nsURI != null) {
/* 223 */                 return schemaTypeToSOAPType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*     */               }
/*     */ 
/*     */ 
/*     */               
/* 228 */               fail("model.schema.invalidSimpleType.noNamespaceURI", new Object[] { component.getName() });
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 234 */             return enumerationToSOAPType(component, (EnumerationFacet)facet, nameHint);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 241 */       if (component.getVarietyTag() == 1) {
/*     */         
/* 243 */         String nsURI = baseTypeComponent.getName().getNamespaceURI();
/* 244 */         if (nsURI != null) {
/*     */ 
/*     */ 
/*     */           
/* 248 */           SOAPType baseType = schemaTypeToSOAPType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*     */           
/* 250 */           return baseType;
/*     */         } 
/*     */         
/* 253 */         fail("model.schema.invalidSimpleType.noNamespaceURI", new Object[] { component.getName() });
/*     */ 
/*     */       
/*     */       }
/* 257 */       else if (component.getVarietyTag() == 2) {
/*     */ 
/*     */         
/* 260 */         if (doWeHandleSimpleSchemaTypeDerivationByList()) {
/* 261 */           return listToSOAPType(component, nameHint);
/*     */         }
/* 263 */         fail("model.schema.listNotSupported", new Object[] { component.getName() });
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 269 */         fail("model.schema.unionNotSupported", new Object[] { component.getName() });
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralType simpleSchemaTypeToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 285 */     if (component.getBaseTypeDefinition() == this._schema.getSimpleUrType()) {
/* 286 */       if (component.getVarietyTag() == 1) {
/*     */ 
/*     */         
/* 289 */         String nsURI = component.getName().getNamespaceURI();
/* 290 */         if (nsURI != null && nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/*     */           
/* 292 */           if (!component.facets().hasNext()) {
/*     */ 
/*     */             
/* 295 */             if (this._strictCompliance && (component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) || component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)))
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 301 */               if (!checkStrictCompliance(component.getName())) {
/* 302 */                 return null;
/*     */               }
/*     */             }
/* 305 */             if (!this._strictCompliance && component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE))
/*     */             {
/*     */               
/* 308 */               return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 315 */             if (component.getName().equals(SchemaConstants.QNAME_TYPE_ID) || component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF))
/*     */             {
/*     */ 
/*     */ 
/*     */               
/* 320 */               return handleIDIDREF(component);
/*     */             }
/* 322 */             LiteralSimpleType simpleType = new LiteralSimpleType(component.getName());
/*     */             
/* 324 */             simpleType.setSchemaTypeRef(component.getName());
/* 325 */             JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 330 */             if (javaType == null)
/*     */             {
/* 332 */               return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */             }
/*     */ 
/*     */             
/* 336 */             simpleType.setJavaType((JavaType)javaType);
/* 337 */             return (LiteralType)simpleType;
/*     */           } 
/*     */           
/* 340 */           return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 346 */         return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */       } 
/* 348 */       if (component.getVarietyTag() == 2) {
/*     */ 
/*     */         
/* 351 */         if (doWeHandleSimpleSchemaTypeDerivationByList())
/* 352 */           return listToLiteralType(component, nameHint); 
/* 353 */         return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */       } 
/*     */       
/* 356 */       return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */     } 
/*     */     
/* 359 */     return anonymousSimpleSchemaTypeToLiteralType(component, nameHint, mappingNameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralType schemaElementTypeToLiteralType(QName elementName) {
/*     */     try {
/* 368 */       ElementDeclarationComponent component = this._schema.findElementDeclaration(elementName);
/*     */       
/* 370 */       LiteralType literalType = schemaTypeToLiteralType(component.getTypeDefinition(), elementName);
/*     */ 
/*     */ 
/*     */       
/* 374 */       if (literalType.getName() == null)
/*     */       {
/* 376 */         literalType.setName(getUniqueTypeNameForElement(elementName));
/*     */       }
/*     */       
/* 379 */       return literalType;
/* 380 */     } catch (UnimplementedFeatureException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       LiteralFragmentType literalFragmentType = new LiteralFragmentType();
/* 387 */       literalFragmentType.setName(elementName);
/* 388 */       literalFragmentType.setJavaType((JavaType)this.javaTypes.SOAPELEMENT_JAVATYPE);
/* 389 */       return (LiteralType)literalFragmentType;
/*     */     } 
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
/*     */   protected LiteralType anonymousSimpleSchemaTypeToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 402 */     SimpleTypeDefinitionComponent baseTypeComponent = component.getBaseTypeDefinition();
/*     */     
/* 404 */     if (component.getVarietyTag() == 1) {
/*     */       
/* 406 */       String nsURI = baseTypeComponent.getName().getNamespaceURI();
/* 407 */       if (nsURI != null && nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/*     */         
/* 409 */         LiteralType baseType = schemaTypeToLiteralType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*     */         
/* 411 */         return baseType;
/*     */       } 
/* 413 */       return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 418 */     return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralType soapStructureExtensionComplexSchemaTypeToLiteralType(ComplexTypeDefinitionComponent component, LiteralStructuredType parentType, QName nameHint) {
/* 426 */     return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAttributeEnumeration(LiteralType attributeType) {
/* 433 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAttributeOptional(AttributeUseComponent attributeUse) {
/* 441 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isParticleOptional(ParticleComponent memberParticle) {
/* 449 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doWeHandleSimpleSchemaTypeDerivationByList() {
/* 456 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doWeHandleWildcard() {
/* 463 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doWeHandleComplexSchemaTypeExtensionBySimpleContent() {
/* 470 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPType listToSOAPType(SimpleTypeDefinitionComponent component, QName nameHint) {
/* 477 */     fail("model.schema.listNotSupported", new Object[] { component.getName() });
/*     */ 
/*     */     
/* 480 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralType listToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint) {
/* 486 */     return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resolveEnumerationNamingConflictsFor(JavaEnumerationType javaEnumType) {
/* 493 */     resolveNamingConflictsFor((JavaType)javaEnumType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doMakeMemberBoxedType() {
/* 500 */     return false;
/*     */   }
/*     */   
/*     */   protected void initializeMaps() {
/* 504 */     this._builtinSchemaTypeToJavaTypeMap = new HashMap<Object, Object>();
/* 505 */     if (this._useDataHandlerOnly) {
/* 506 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_IMAGE, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */       
/* 509 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */       
/* 512 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SOURCE, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */     }
/*     */     else {
/*     */       
/* 516 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_IMAGE, this.javaTypes.IMAGE_JAVATYPE);
/*     */ 
/*     */       
/* 519 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MIME_MULTIPART, this.javaTypes.MIME_MULTIPART_JAVATYPE);
/*     */ 
/*     */       
/* 522 */       this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SOURCE, this.javaTypes.SOURCE_JAVATYPE);
/*     */     } 
/*     */ 
/*     */     
/* 526 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_DATA_HANDLER, this.javaTypes.DATA_HANDLER_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 530 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.STRING, this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 533 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.INTEGER, this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 536 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.INT, this.javaTypes.INT_JAVATYPE);
/*     */ 
/*     */     
/* 539 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.LONG, this.javaTypes.LONG_JAVATYPE);
/*     */ 
/*     */     
/* 542 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.SHORT, this.javaTypes.SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 545 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DECIMAL, this.javaTypes.DECIMAL_JAVATYPE);
/*     */ 
/*     */     
/* 548 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.FLOAT, this.javaTypes.FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 551 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DOUBLE, this.javaTypes.DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 554 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BOOLEAN, this.javaTypes.BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 557 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BYTE, this.javaTypes.BYTE_JAVATYPE);
/*     */ 
/*     */     
/* 560 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.QNAME, this.javaTypes.QNAME_JAVATYPE);
/*     */ 
/*     */     
/* 563 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.DATE_TIME, this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 566 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.BASE64_BINARY, this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 569 */     this._builtinSchemaTypeToJavaTypeMap.put(BuiltInTypes.HEX_BINARY, this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 573 */     this._builtinSchemaTypeToJavaTypeMap.put(SchemaConstants.QNAME_TYPE_URTYPE, this.javaTypes.OBJECT_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 577 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeString(), this.javaTypes.STRING_JAVATYPE);
/*     */ 
/*     */     
/* 580 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeInteger(), this.javaTypes.BIG_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 583 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeInt(), this.javaTypes.BOXED_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 586 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeLong(), this.javaTypes.BOXED_LONG_JAVATYPE);
/*     */ 
/*     */     
/* 589 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeShort(), this.javaTypes.BOXED_SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 592 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDecimal(), this.javaTypes.DECIMAL_JAVATYPE);
/*     */ 
/*     */     
/* 595 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeFloat(), this.javaTypes.BOXED_FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 598 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDouble(), this.javaTypes.BOXED_DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 601 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBoolean(), this.javaTypes.BOXED_BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 604 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeByte(), this.javaTypes.BOXED_BYTE_JAVATYPE);
/*     */ 
/*     */     
/* 607 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeQName(), this.javaTypes.QNAME_JAVATYPE);
/*     */ 
/*     */     
/* 610 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeDateTime(), this.javaTypes.CALENDAR_JAVATYPE);
/*     */ 
/*     */     
/* 613 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBase64Binary(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 616 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeHexBinary(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */     
/* 619 */     this._builtinSchemaTypeToJavaTypeMap.put(soap11WSDLConstants.getQNameTypeBase64(), this.javaTypes.BYTE_ARRAY_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 623 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_COLLECTION, this.javaTypes.COLLECTION_JAVATYPE);
/*     */ 
/*     */     
/* 626 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_LIST, this.javaTypes.LIST_JAVATYPE);
/*     */ 
/*     */     
/* 629 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_SET, this.javaTypes.SET_JAVATYPE);
/*     */ 
/*     */     
/* 632 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_ARRAY_LIST, this.javaTypes.ARRAY_LIST_JAVATYPE);
/*     */ 
/*     */     
/* 635 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_VECTOR, this.javaTypes.VECTOR_JAVATYPE);
/*     */ 
/*     */     
/* 638 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_STACK, this.javaTypes.STACK_JAVATYPE);
/*     */ 
/*     */     
/* 641 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_LINKED_LIST, this.javaTypes.LINKED_LIST_JAVATYPE);
/*     */ 
/*     */     
/* 644 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_SET, this.javaTypes.HASH_SET_JAVATYPE);
/*     */ 
/*     */     
/* 647 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_SET, this.javaTypes.TREE_SET_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 651 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_MAP, this.javaTypes.MAP_JAVATYPE);
/*     */ 
/*     */     
/* 654 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY, this.javaTypes.JAX_RPC_MAP_ENTRY_JAVATYPE);
/*     */ 
/*     */     
/* 657 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASH_MAP, this.javaTypes.HASH_MAP_JAVATYPE);
/*     */ 
/*     */     
/* 660 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_TREE_MAP, this.javaTypes.TREE_MAP_JAVATYPE);
/*     */ 
/*     */     
/* 663 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_HASHTABLE, this.javaTypes.HASHTABLE_JAVATYPE);
/*     */ 
/*     */     
/* 666 */     this._builtinSchemaTypeToJavaTypeMap.put(InternalEncodingConstants.QNAME_TYPE_PROPERTIES, this.javaTypes.PROPERTIES_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 670 */     this._builtinSchemaTypeToJavaWrapperTypeMap = new HashMap<Object, Object>();
/* 671 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.INT, this.javaTypes.BOXED_INTEGER_JAVATYPE);
/*     */ 
/*     */     
/* 674 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.LONG, this.javaTypes.BOXED_LONG_JAVATYPE);
/*     */ 
/*     */     
/* 677 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.SHORT, this.javaTypes.BOXED_SHORT_JAVATYPE);
/*     */ 
/*     */     
/* 680 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.FLOAT, this.javaTypes.BOXED_FLOAT_JAVATYPE);
/*     */ 
/*     */     
/* 683 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.DOUBLE, this.javaTypes.BOXED_DOUBLE_JAVATYPE);
/*     */ 
/*     */     
/* 686 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.BOOLEAN, this.javaTypes.BOXED_BOOLEAN_JAVATYPE);
/*     */ 
/*     */     
/* 689 */     this._builtinSchemaTypeToJavaWrapperTypeMap.put(BuiltInTypes.BYTE, this.javaTypes.BOXED_BYTE_JAVATYPE);
/*     */ 
/*     */ 
/*     */     
/* 693 */     this._simpleTypeEncoderMap = new HashMap<Object, Object>();
/* 694 */     this._simpleTypeEncoderMap.put(BuiltInTypes.STRING, XSDStringEncoder.getInstance());
/*     */ 
/*     */     
/* 697 */     this._simpleTypeEncoderMap.put(BuiltInTypes.INTEGER, XSDIntegerEncoder.getInstance());
/*     */ 
/*     */     
/* 700 */     this._simpleTypeEncoderMap.put(BuiltInTypes.INT, XSDIntEncoder.getInstance());
/*     */ 
/*     */     
/* 703 */     this._simpleTypeEncoderMap.put(BuiltInTypes.LONG, XSDLongEncoder.getInstance());
/*     */ 
/*     */     
/* 706 */     this._simpleTypeEncoderMap.put(BuiltInTypes.SHORT, XSDShortEncoder.getInstance());
/*     */ 
/*     */     
/* 709 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DECIMAL, XSDDecimalEncoder.getInstance());
/*     */ 
/*     */     
/* 712 */     this._simpleTypeEncoderMap.put(BuiltInTypes.FLOAT, XSDFloatEncoder.getInstance());
/*     */ 
/*     */     
/* 715 */     this._simpleTypeEncoderMap.put(BuiltInTypes.DOUBLE, XSDDoubleEncoder.getInstance());
/*     */ 
/*     */     
/* 718 */     this._simpleTypeEncoderMap.put(BuiltInTypes.BYTE, XSDByteEncoder.getInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doWeHandleComplexSchemaTypeExtensionByComplexType() {
/* 729 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\SchemaAnalyzer103.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */