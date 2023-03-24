/*      */ package com.sun.xml.rpc.processor.modeler.wsdl;
/*      */ 
/*      */ import com.sun.xml.rpc.encoding.DeserializationException;
/*      */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*      */ import com.sun.xml.rpc.encoding.simpletype.EncoderUtils;
/*      */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*      */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*      */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SimpleTypeSerializerWriter;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.ModelException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaCustomType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationEntry;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralContentMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*      */ import com.sun.xml.rpc.processor.schema.AttributeDeclarationComponent;
/*      */ import com.sun.xml.rpc.processor.schema.AttributeUseComponent;
/*      */ import com.sun.xml.rpc.processor.schema.ComplexTypeDefinitionComponent;
/*      */ import com.sun.xml.rpc.processor.schema.ElementDeclarationComponent;
/*      */ import com.sun.xml.rpc.processor.schema.EnumerationFacet;
/*      */ import com.sun.xml.rpc.processor.schema.Facet;
/*      */ import com.sun.xml.rpc.processor.schema.InternalSchema;
/*      */ import com.sun.xml.rpc.processor.schema.ModelGroupComponent;
/*      */ import com.sun.xml.rpc.processor.schema.ParticleComponent;
/*      */ import com.sun.xml.rpc.processor.schema.SimpleTypeDefinitionComponent;
/*      */ import com.sun.xml.rpc.processor.schema.Symbol;
/*      */ import com.sun.xml.rpc.processor.schema.TypeDefinitionComponent;
/*      */ import com.sun.xml.rpc.processor.schema.UnimplementedFeatureException;
/*      */ import com.sun.xml.rpc.processor.schema.WildcardComponent;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*      */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.BuiltInTypes;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class SchemaAnalyzerBase
/*      */ {
/*      */   private LocalizableMessageFactory _messageFactory;
/*      */   protected InternalSchema _schema;
/*      */   private ModelInfo _modelInfo;
/*      */   protected ProcessorEnvironment _env;
/*      */   private Set _typesBeingResolved;
/*      */   private Set _namePool;
/*      */   private Set _conflictingClassNames;
/*      */   private Map _componentToSOAPTypeMap;
/*      */   private Map _componentToLiteralTypeMap;
/*      */   private Map _typeNameToCustomSOAPTypeMap;
/*      */   protected Map _nillableSimpleTypeComponentToSOAPTypeMap;
/*      */   protected Map _nillableSimpleTypeComponentToLiteralTypeMap;
/*      */   private boolean _noDataBinding;
/*      */   protected boolean _useDataHandlerOnly;
/*      */   private int _nextUniqueID;
/*      */   protected boolean _resolveIDREF;
/*      */   protected boolean _strictCompliance;
/*      */   protected boolean _jaxbEnumType;
/*      */   protected JavaSimpleTypeCreator javaTypes;
/*      */   
/*      */   public SchemaAnalyzerBase(AbstractDocument document, ModelInfo modelInfo, Properties options, Set conflictingClassNames, JavaSimpleTypeCreator javaTypes) {
/*  128 */     init();
/*  129 */     this._messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.model");
/*      */ 
/*      */     
/*  132 */     this._schema = JAXRPCClassFactory.newInstance().createInternalSchemaBuilder(document, options).getSchema();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  137 */     this._modelInfo = modelInfo;
/*  138 */     this._env = (ProcessorEnvironment)modelInfo.getParent().getEnvironment();
/*  139 */     this._conflictingClassNames = conflictingClassNames;
/*  140 */     this._typesBeingResolved = new HashSet();
/*  141 */     this._namePool = new HashSet();
/*  142 */     this._componentToSOAPTypeMap = new HashMap<Object, Object>();
/*  143 */     this._componentToLiteralTypeMap = new HashMap<Object, Object>();
/*  144 */     this._typeNameToCustomSOAPTypeMap = new HashMap<Object, Object>();
/*  145 */     this._nillableSimpleTypeComponentToSOAPTypeMap = new HashMap<Object, Object>();
/*  146 */     this._nillableSimpleTypeComponentToLiteralTypeMap = new HashMap<Object, Object>();
/*  147 */     this._nextUniqueID = 1;
/*  148 */     this._noDataBinding = Boolean.valueOf(options.getProperty("noDataBinding")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  154 */     this._useDataHandlerOnly = Boolean.valueOf(options.getProperty("useDataHandlerOnly")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  160 */     this._resolveIDREF = Boolean.valueOf(options.getProperty("resolveIDREF")).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  164 */     this._strictCompliance = Boolean.valueOf(options.getProperty("strictCompliance")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  169 */     this._jaxbEnumType = Boolean.valueOf(options.getProperty("jaxbenum")).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  173 */     this.javaTypes = javaTypes;
/*  174 */     initializeMaps();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/*  182 */     soap11NamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(SOAPVersion.SOAP_11);
/*      */     
/*  184 */     soap11WSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(SOAPVersion.SOAP_11);
/*      */ 
/*      */     
/*  187 */     soap12NamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(SOAPVersion.SOAP_12);
/*      */     
/*  189 */     soap12WSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(SOAPVersion.SOAP_12);
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPType schemaTypeToSOAPType(QName typeName) {
/*      */     try {
/*  195 */       TypeDefinitionComponent component = this._schema.findTypeDefinition(typeName);
/*      */       
/*  197 */       return schemaTypeToSOAPType(component, typeName);
/*  198 */     } catch (UnimplementedFeatureException e) {
/*  199 */       fail("model.schema.unsupportedSchemaType", new Object[] { typeName });
/*      */ 
/*      */       
/*  202 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   public LiteralType schemaTypeToLiteralType(QName typeName) {
/*      */     try {
/*  208 */       TypeDefinitionComponent component = this._schema.findTypeDefinition(typeName);
/*      */       
/*  210 */       return schemaTypeToLiteralType(component, typeName);
/*  211 */     } catch (UnimplementedFeatureException e) {
/*  212 */       LiteralFragmentType literalFragmentType = new LiteralFragmentType();
/*  213 */       literalFragmentType.setName(typeName);
/*  214 */       literalFragmentType.setSchemaTypeRef(typeName);
/*  215 */       literalFragmentType.setJavaType((JavaType)this.javaTypes.SOAPELEMENT_JAVATYPE);
/*      */       
/*  217 */       literalFragmentType.setNillable(true);
/*  218 */       return (LiteralType)literalFragmentType;
/*      */     } 
/*      */   }
/*      */   
/*      */   public LiteralType schemaElementTypeToLiteralType(QName elementName) {
/*  223 */     ElementDeclarationComponent component = null; try {
/*      */       LiteralSimpleType literalSimpleType;
/*  225 */       component = this._schema.findElementDeclaration(elementName);
/*  226 */       String mappingNameHint = null;
/*  227 */       if (component.getTypeDefinition().getName() == null) {
/*  228 */         mappingNameHint = ">" + elementName.getLocalPart();
/*      */       } else {
/*  230 */         mappingNameHint = component.getTypeDefinition().getName().getLocalPart();
/*      */       } 
/*      */       
/*  233 */       LiteralType literalType = schemaTypeToLiteralType(component.getTypeDefinition(), elementName, new QName(elementName.getNamespaceURI(), mappingNameHint));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  238 */       if (literalType.getName() == null)
/*      */       {
/*  240 */         literalType.setName(getUniqueTypeNameForElement(elementName));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  245 */       if (component.isNillable()) {
/*  246 */         LiteralSimpleType result = (LiteralSimpleType)this._nillableSimpleTypeComponentToLiteralTypeMap.get(component.getTypeDefinition());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  251 */         if (result == null) {
/*      */ 
/*      */           
/*  254 */           QName baseTypeName = getSimpleTypeBaseName(component.getTypeDefinition());
/*      */           
/*  256 */           JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaWrapperTypeMap.get(baseTypeName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  266 */           if (javaType == null && literalType instanceof LiteralSimpleType)
/*      */           {
/*  268 */             javaType = (JavaSimpleType)((LiteralSimpleType)literalType).getJavaType();
/*      */           }
/*      */ 
/*      */           
/*  272 */           if (javaType != null) {
/*      */             
/*  274 */             result = new LiteralSimpleType(baseTypeName, javaType, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  284 */             result.setSchemaTypeRef(component.getTypeDefinition().getName());
/*      */             
/*  286 */             this._nillableSimpleTypeComponentToLiteralTypeMap.put(component.getTypeDefinition(), result);
/*      */ 
/*      */             
/*  289 */             return (LiteralType)result;
/*      */           } 
/*      */         } else {
/*  292 */           literalSimpleType = result;
/*      */         } 
/*      */       } 
/*  295 */       return (LiteralType)literalSimpleType;
/*  296 */     } catch (UnimplementedFeatureException e) {
/*      */       LiteralFragmentType literalFragmentType;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  302 */       LiteralType literalType = (LiteralType)this._componentToLiteralTypeMap.get(component);
/*      */       
/*  304 */       if (literalType == null) {
/*  305 */         literalFragmentType = new LiteralFragmentType();
/*  306 */         literalFragmentType.setName(elementName);
/*  307 */         literalFragmentType.setJavaType((JavaType)this.javaTypes.SOAPELEMENT_JAVATYPE);
/*  308 */         this._componentToLiteralTypeMap.put(component, literalFragmentType);
/*      */       } 
/*  310 */       return (LiteralType)literalFragmentType;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType schemaTypeToSOAPType(TypeDefinitionComponent component, QName nameHint) {
/*  317 */     SOAPType result = (SOAPType)this._componentToSOAPTypeMap.get(component);
/*  318 */     if (result == null) {
/*      */       try {
/*  320 */         if (component.isSimple()) {
/*  321 */           result = simpleSchemaTypeToSOAPType((SimpleTypeDefinitionComponent)component, nameHint);
/*      */ 
/*      */         
/*      */         }
/*  325 */         else if (component.isComplex()) {
/*  326 */           result = complexSchemaTypeToSOAPType((ComplexTypeDefinitionComponent)component, nameHint);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  332 */           throw new IllegalArgumentException();
/*      */         } 
/*      */         
/*  335 */         this._componentToSOAPTypeMap.put(component, result);
/*      */       } finally {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  341 */     return result;
/*      */   }
/*      */   
/*      */   protected SOAPType nillableSchemaTypeToSOAPType(TypeDefinitionComponent component) {
/*  345 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaWrapperTypeMap.get(component.getName());
/*      */ 
/*      */     
/*  348 */     if (javaType == null)
/*      */     {
/*  350 */       return schemaTypeToSOAPType(component, component.getName());
/*      */     }
/*      */     
/*  353 */     SOAPSimpleType result = (SOAPSimpleType)this._nillableSimpleTypeComponentToSOAPTypeMap.get(component);
/*      */ 
/*      */     
/*  356 */     if (result != null) {
/*  357 */       return (SOAPType)result;
/*      */     }
/*  359 */     result = new SOAPSimpleType(component.getName(), javaType);
/*  360 */     result.setSchemaTypeRef(component.getName());
/*  361 */     setReferenceable(result);
/*  362 */     this._nillableSimpleTypeComponentToSOAPTypeMap.put(component, result);
/*  363 */     return (SOAPType)result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType simpleSchemaTypeToSOAPType(SimpleTypeDefinitionComponent component, QName nameHint) {
/*  370 */     if (component.getBaseTypeDefinition() == this._schema.getSimpleUrType()) {
/*  371 */       if (component.getVarietyTag() == 1)
/*      */       {
/*      */         
/*  374 */         String nsURI = component.getName().getNamespaceURI();
/*  375 */         if (nsURI != null && (nsURI.equals("http://www.w3.org/2001/XMLSchema") || nsURI.equals(soap11WSDLConstants.getSOAPEncodingNamespace())))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  380 */           if (!component.facets().hasNext()) {
/*      */             
/*  382 */             if (this._strictCompliance && (component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) || component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  388 */               if (!checkStrictCompliance(component.getName())) {
/*  389 */                 return null;
/*      */               }
/*      */             }
/*  392 */             if (!this._strictCompliance && component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)) {
/*      */ 
/*      */               
/*  395 */               SOAPAnyType anyType = new SOAPAnyType(component.getName());
/*      */               
/*  397 */               JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  402 */               if (javaType == null)
/*      */               {
/*      */ 
/*      */                 
/*  406 */                 fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */               }
/*      */ 
/*      */               
/*  410 */               anyType.setJavaType((JavaType)javaType);
/*  411 */               return (SOAPType)anyType;
/*      */             } 
/*      */             
/*  414 */             return (SOAPType)createSOAPSimpleType(component);
/*      */           } 
/*      */ 
/*      */           
/*  418 */           fail("model.schema.simpleTypeWithFacets", new Object[] { component.getName(), component.facets().next() });
/*      */ 
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*      */           
/*  426 */           fail("model.schema.invalidSimpleType", new Object[] { component.getName() });
/*      */         }
/*      */       
/*      */       }
/*  430 */       else if (component.getVarietyTag() == 2)
/*      */       {
/*      */         
/*  433 */         if (doWeHandleSimpleSchemaTypeDerivationByList())
/*  434 */           return listToSOAPType(component, nameHint); 
/*  435 */         fail("model.schema.listNotSupported", new Object[] { component.getName() });
/*      */ 
/*      */       
/*      */       }
/*  439 */       else if (component.getVarietyTag() == 3)
/*      */       {
/*      */ 
/*      */         
/*  443 */         fail("model.schema.unionNotSupported", new Object[] { component.getName() });
/*      */       
/*      */       }
/*      */       else
/*      */       {
/*      */         
/*  449 */         if (component.getName().equals(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE))
/*      */         {
/*      */           
/*  452 */           return (SOAPType)createSOAPSimpleType(component);
/*      */         }
/*  454 */         fail("model.schema.invalidSimpleType", new Object[] { component.getName() });
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  462 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/*  465 */       if (javaType != null) {
/*  466 */         SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */         
/*  468 */         simpleType.setSchemaTypeRef(component.getName());
/*  469 */         simpleType.setJavaType((JavaType)javaType);
/*  470 */         setReferenceable(simpleType);
/*  471 */         return (SOAPType)simpleType;
/*      */       } 
/*  473 */       SimpleTypeDefinitionComponent baseTypeComponent = component.getBaseTypeDefinition();
/*      */       
/*  475 */       Iterator<Facet> iter = component.facets();
/*      */       
/*  477 */       if (iter.hasNext() && component.getVarietyTag() == 1) {
/*      */ 
/*      */         
/*  480 */         Facet facet = iter.next();
/*  481 */         if (facet instanceof EnumerationFacet) {
/*  482 */           Iterator values = ((EnumerationFacet)facet).values();
/*  483 */           if (values.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  488 */             if (this._jaxbEnumType && component.getName() == null) {
/*  489 */               String nsURI = baseTypeComponent.getName().getNamespaceURI();
/*      */               
/*  491 */               if (nsURI != null) {
/*  492 */                 return schemaTypeToSOAPType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*      */               }
/*      */ 
/*      */               
/*  496 */               fail("model.schema.invalidSimpleType.noNamespaceURI", new Object[] { component.getName() });
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  502 */             return enumerationToSOAPType(component, (EnumerationFacet)facet, nameHint);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  509 */       if (component.getVarietyTag() == 1) {
/*      */         
/*  511 */         String nsURI = baseTypeComponent.getName().getNamespaceURI();
/*  512 */         if (nsURI != null) {
/*      */ 
/*      */ 
/*      */           
/*  516 */           SOAPType baseType = schemaTypeToSOAPType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*      */           
/*  518 */           return baseType;
/*      */         } 
/*  520 */         fail("model.schema.invalidSimpleType.noNamespaceURI", new Object[] { component.getName() });
/*      */ 
/*      */       
/*      */       }
/*  524 */       else if (component.getVarietyTag() == 2) {
/*      */ 
/*      */         
/*  527 */         if (doWeHandleSimpleSchemaTypeDerivationByList())
/*  528 */           return listToSOAPType(component, nameHint); 
/*  529 */         fail("model.schema.listNotSupported", new Object[] { component.getName() });
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  534 */         fail("model.schema.unionNotSupported", new Object[] { component.getName() });
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  539 */     return null;
/*      */   }
/*      */   
/*      */   protected SOAPSimpleType createSOAPSimpleType(SimpleTypeDefinitionComponent component) {
/*  543 */     SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*  544 */     simpleType.setSchemaTypeRef(component.getName());
/*  545 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */     
/*  548 */     if (javaType == null) {
/*  549 */       fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */     }
/*      */ 
/*      */     
/*  553 */     simpleType.setJavaType((JavaType)javaType);
/*  554 */     setReferenceable(simpleType);
/*  555 */     return simpleType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJavaNameOfSOAPStructureType(SOAPStructureType structureType, TypeDefinitionComponent component, QName nameHint) {
/*  562 */     return makePackageQualified(this._env.getNames().validJavaClassName(structureType.getName().getLocalPart()), structureType.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJavaNameOfType(TypeDefinitionComponent component, QName nameHint) {
/*  571 */     QName componentName = component.getName();
/*  572 */     if (componentName == null)
/*      */     {
/*      */       
/*  575 */       componentName = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart());
/*      */     }
/*      */     
/*  578 */     return makePackageQualified(this._env.getNames().validJavaClassName(componentName.getLocalPart()), componentName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType enumerationToSOAPType(SimpleTypeDefinitionComponent component, EnumerationFacet facet, QName nameHint) {
/*  588 */     SimpleTypeDefinitionComponent baseType = component.getBaseTypeDefinition();
/*      */     
/*  590 */     SimpleTypeEncoder encoder = (SimpleTypeEncoder)this._simpleTypeEncoderMap.get(baseType.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  596 */     SimpleTypeDefinitionComponent tempComponent = component;
/*      */     
/*  598 */     while (encoder == null && !baseType.getName().equals(SchemaConstants.QNAME_TYPE_BOOLEAN)) {
/*  599 */       baseType = component.getBaseTypeDefinition();
/*  600 */       encoder = (SimpleTypeEncoder)this._simpleTypeEncoderMap.get(baseType.getName());
/*      */ 
/*      */       
/*  603 */       component = baseType;
/*      */     } 
/*      */     
/*  606 */     component = tempComponent;
/*  607 */     if (encoder != null) {
/*  608 */       QName componentName = component.getName();
/*  609 */       if (componentName == null)
/*      */       {
/*  611 */         if (componentName == null)
/*      */         {
/*  613 */           componentName = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart());
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  619 */       JavaType javaEntryType = (JavaType)this._builtinSchemaTypeToJavaTypeMap.get(baseType.getName());
/*      */ 
/*      */       
/*  622 */       String javaEnumName = getJavaNameOfType((TypeDefinitionComponent)component, nameHint);
/*  623 */       JavaEnumerationType javaEnumType = new JavaEnumerationType(javaEnumName, javaEntryType, false);
/*      */ 
/*      */       
/*  626 */       resolveEnumerationNamingConflictsFor(javaEnumType);
/*  627 */       SOAPEnumerationType soapEnumType = new SOAPEnumerationType(componentName, schemaTypeToSOAPType((TypeDefinitionComponent)baseType, nameHint), (JavaType)javaEnumType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  633 */       boolean mustRename = false;
/*  634 */       for (Iterator<String> values = facet.values(); values.hasNext(); ) {
/*  635 */         String value = values.next();
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  640 */           JavaEnumerationEntry entry = null;
/*  641 */           if (baseType.getName().equals(SchemaConstants.QNAME_TYPE_QNAME)) {
/*      */ 
/*      */             
/*  644 */             entry = new JavaEnumerationEntry(value, valueToQName(value, facet.getPrefixes()), value);
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  650 */             entry = new JavaEnumerationEntry(value, encoder.stringToObject(value, null), value);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  656 */           if (!mustRename && isInvalidEnumerationLabel(value)) {
/*  657 */             mustRename = true;
/*      */           }
/*  659 */           javaEnumType.add(entry);
/*  660 */         } catch (Exception e) {
/*      */           
/*  662 */           fail("model.schema.invalidLiteralInEnumeration", value, componentName);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  669 */       if (mustRename) {
/*  670 */         int index = 1;
/*  671 */         Iterator<JavaEnumerationEntry> iter = javaEnumType.getEntries();
/*  672 */         for (; iter.hasNext(); 
/*  673 */           index++) {
/*  674 */           JavaEnumerationEntry entry = iter.next();
/*      */           
/*  676 */           entry.setName("value" + Integer.toString(index));
/*      */         } 
/*      */       } 
/*      */       
/*  680 */       return (SOAPType)soapEnumType;
/*      */     } 
/*  682 */     fail("model.schema.encoderNotFound", new Object[] { component.getName() });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  687 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType listToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint) {
/*  694 */     LiteralType itemLiteralType = null;
/*  695 */     SimpleTypeDefinitionComponent itemType = component.getItemTypeDefinition();
/*      */ 
/*      */     
/*  698 */     if (component.getName() != null && itemType.getName() != null && component.getName().equals(itemType.getName()))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  703 */       fail("model.schema.invalidSimpleType.invalidItemType", new Object[] { component.getName(), itemType.getName() });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  709 */     itemLiteralType = schemaTypeToLiteralType((itemType.getName() == null) ? itemType.getBaseTypeDefinition().getName() : itemType.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  714 */     if (itemLiteralType != null) {
/*  715 */       JavaArrayType javaArrayType = new JavaArrayType(itemLiteralType.getJavaType().getName() + "[]");
/*      */ 
/*      */       
/*  718 */       javaArrayType.setElementType(itemLiteralType.getJavaType());
/*  719 */       JavaArrayType javaArrayType1 = javaArrayType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  728 */       QName componentName = component.getName();
/*  729 */       if (componentName == null)
/*      */       {
/*  731 */         componentName = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "_Type");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  736 */       return (LiteralType)new LiteralListType(componentName, itemLiteralType, (JavaType)javaArrayType1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  741 */     fail("model.schema.invalidSimpleType.noItemLiteralType", new Object[] { component.getName(), itemType.getName() });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  746 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType listToSOAPType(SimpleTypeDefinitionComponent component, QName nameHint) {
/*  755 */     SOAPType itemSOAPType = null;
/*  756 */     SimpleTypeDefinitionComponent itemType = component.getItemTypeDefinition();
/*      */ 
/*      */     
/*  759 */     if (component.getName() != null && itemType.getName() != null && component.getName().equals(itemType.getName()))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  764 */       fail("model.schema.invalidSimpleType.invalidItemType", new Object[] { component.getName(), itemType.getName() });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  769 */     itemSOAPType = schemaTypeToSOAPType((itemType.getName() == null) ? itemType.getBaseTypeDefinition().getName() : itemType.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  775 */     if (itemSOAPType != null) {
/*  776 */       JavaArrayType javaArrayType = new JavaArrayType(itemSOAPType.getJavaType().getName() + "[]");
/*      */       
/*  778 */       javaArrayType.setElementType(itemSOAPType.getJavaType());
/*  779 */       JavaArrayType javaArrayType1 = javaArrayType;
/*  780 */       QName componentName = component.getName();
/*  781 */       if (componentName == null)
/*      */       {
/*  783 */         componentName = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "_Type");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  788 */       return (SOAPType)new SOAPListType(componentName, itemSOAPType, (JavaType)javaArrayType1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  793 */     fail("model.schema.invalidSimpleType.noItemLiteralType", new Object[] { component.getName(), itemType.getName() });
/*      */ 
/*      */ 
/*      */     
/*  797 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType enumerationToLiteralType(SimpleTypeDefinitionComponent component, EnumerationFacet facet, QName nameHint, QName mappingNameHint) {
/*  806 */     SimpleTypeDefinitionComponent simpleTypeDefinitionComponent = component.getBaseTypeDefinition();
/*  807 */     SimpleTypeEncoder encoder = (SimpleTypeEncoder)this._simpleTypeEncoderMap.get(simpleTypeDefinitionComponent.getName());
/*      */     
/*  809 */     if (encoder != null && !simpleTypeDefinitionComponent.getName().equals(SchemaConstants.QNAME_TYPE_BOOLEAN)) {
/*      */       
/*  811 */       JavaType javaEntryType = (JavaType)this._builtinSchemaTypeToJavaTypeMap.get(simpleTypeDefinitionComponent.getName());
/*      */ 
/*      */       
/*  814 */       QName componentName = component.getName();
/*  815 */       if (componentName == null)
/*      */       {
/*  817 */         componentName = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  822 */       JavaEnumerationType javaEnumType = new JavaEnumerationType(makePackageQualified(this._env.getNames().validJavaClassName(componentName.getLocalPart()), componentName), javaEntryType, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  831 */       resolveEnumerationNamingConflictsFor(javaEnumType);
/*  832 */       LiteralEnumerationType literalEnumType = new LiteralEnumerationType(componentName, schemaTypeToLiteralType((TypeDefinitionComponent)simpleTypeDefinitionComponent, new QName("value")), (JavaType)javaEnumType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  839 */       if (component.getName() == null && mappingNameHint != null) {
/*  840 */         literalEnumType.setProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName", mappingNameHint.getLocalPart());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  845 */       boolean mustRename = false;
/*  846 */       for (Iterator<String> values = facet.values(); values.hasNext(); ) {
/*  847 */         String value = values.next();
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/*  852 */           JavaEnumerationEntry entry = null;
/*  853 */           if (simpleTypeDefinitionComponent.getName().equals(SchemaConstants.QNAME_TYPE_QNAME)) {
/*      */ 
/*      */             
/*  856 */             entry = new JavaEnumerationEntry(value, valueToQName(value, facet.getPrefixes()), value);
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  862 */             entry = new JavaEnumerationEntry(value, encoder.stringToObject(value, null), value);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  868 */           if (!mustRename && isInvalidEnumerationLabel(value)) {
/*  869 */             mustRename = true;
/*      */           }
/*  871 */           javaEnumType.add(entry);
/*  872 */         } catch (Exception e) {
/*      */           
/*  874 */           fail("model.schema.invalidLiteralInEnumeration", value, component.getName());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  881 */       if (mustRename) {
/*  882 */         int index = 1;
/*  883 */         Iterator<JavaEnumerationEntry> iter = javaEnumType.getEntries();
/*  884 */         for (; iter.hasNext(); 
/*  885 */           index++) {
/*  886 */           JavaEnumerationEntry entry = iter.next();
/*      */           
/*  888 */           entry.setName("value" + Integer.toString(index));
/*      */         } 
/*      */       } 
/*      */       
/*  892 */       return (LiteralType)literalEnumType;
/*      */     } 
/*  894 */     fail("model.schema.encoderNotFound", new Object[] { component.getName() });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  899 */     return null;
/*      */   }
/*      */   
/*      */   private QName valueToQName(String str, Map prefixes) throws Exception {
/*  903 */     if (str == null) {
/*  904 */       return null;
/*      */     }
/*  906 */     String uri = "";
/*  907 */     str = EncoderUtils.collapseWhitespace(str);
/*  908 */     String prefix = XmlUtil.getPrefix(str);
/*  909 */     if (prefix != null) {
/*  910 */       uri = (String)prefixes.get(prefix);
/*  911 */       if (uri == null) {
/*  912 */         throw new DeserializationException("xsd.unknownPrefix", prefix);
/*      */       }
/*      */     } 
/*      */     
/*  916 */     String localPart = XmlUtil.getLocalPart(str);
/*      */     
/*  918 */     return new QName(uri, localPart);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType complexSchemaTypeToSOAPType(ComplexTypeDefinitionComponent component, QName nameHint) {
/*  924 */     SOAPCustomType userDefinedType = getCustomTypeFor((TypeDefinitionComponent)component);
/*  925 */     if (userDefinedType != null) {
/*  926 */       return (SOAPType)userDefinedType;
/*      */     }
/*  928 */     if (component == this._schema.getUrType()) {
/*      */       
/*  930 */       if (component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)) {
/*      */ 
/*      */ 
/*      */         
/*  934 */         if (this._strictCompliance && 
/*  935 */           !checkStrictCompliance(component.getName())) {
/*  936 */           return null;
/*      */         }
/*  938 */         SOAPAnyType anyType = new SOAPAnyType(component.getName());
/*  939 */         JavaSimpleType javaSimpleType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */         
/*  942 */         if (javaSimpleType == null)
/*      */         {
/*      */           
/*  945 */           fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */         }
/*      */ 
/*      */         
/*  949 */         anyType.setJavaType((JavaType)javaSimpleType);
/*  950 */         return (SOAPType)anyType;
/*      */       } 
/*  952 */       SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */       
/*  954 */       simpleType.setSchemaTypeRef(component.getName());
/*  955 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/*  958 */       if (javaType == null)
/*      */       {
/*      */         
/*  961 */         fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */       }
/*      */ 
/*      */       
/*  965 */       simpleType.setJavaType((JavaType)javaType);
/*  966 */       setReferenceable(simpleType);
/*  967 */       return (SOAPType)simpleType;
/*      */     } 
/*      */     
/*  970 */     if (component.getName() != null && component.getName().equals(soap11WSDLConstants.getQNameTypeArray())) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  975 */       SOAPType anyType = schemaTypeToSOAPType(SchemaConstants.QNAME_TYPE_URTYPE);
/*      */       
/*  977 */       SOAPArrayType arrayType = new SOAPArrayType(component.getName());
/*  978 */       arrayType.setElementName(InternalEncodingConstants.ARRAY_ELEMENT_NAME);
/*      */       
/*  980 */       arrayType.setElementType(anyType);
/*  981 */       arrayType.setRank(1);
/*  982 */       arrayType.setSize(null);
/*  983 */       JavaArrayType javaArrayType = new JavaArrayType(anyType.getJavaType().getName() + "[]");
/*      */       
/*  985 */       javaArrayType.setElementType(anyType.getJavaType());
/*  986 */       arrayType.setJavaType((JavaType)javaArrayType);
/*  987 */       return (SOAPType)arrayType;
/*  988 */     }  if (component.getBaseTypeDefinition() == this._schema.getUrType())
/*  989 */       return urTypeBasedComplexSchemaTypeToSOAPType(component, nameHint); 
/*  990 */     if (component.getBaseTypeDefinition().getName() != null && component.getBaseTypeDefinition().getName().equals(soap11WSDLConstants.getQNameTypeArray()))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  996 */       return soapArrayBasedComplexSchemaTypeToSOAPType(component, nameHint);
/*      */     }
/*      */ 
/*      */     
/* 1000 */     if (component.getName() != null) {
/* 1001 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/* 1004 */       if (javaType != null) {
/* 1005 */         SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */         
/* 1007 */         simpleType.setSchemaTypeRef(component.getName());
/* 1008 */         simpleType.setJavaType((JavaType)javaType);
/* 1009 */         setReferenceable(simpleType);
/* 1010 */         return (SOAPType)simpleType;
/*      */       } 
/*      */     } 
/*      */     
/* 1014 */     if (component.getDerivationMethod() == Symbol.EXTENSION) {
/* 1015 */       SOAPType parentType = schemaTypeToSOAPType(component.getBaseTypeDefinition(), nameHint);
/*      */ 
/*      */ 
/*      */       
/* 1019 */       if (parentType instanceof SOAPStructureType) {
/* 1020 */         return soapStructureExtensionComplexSchemaTypeToSOAPType(component, (SOAPStructureType)parentType, nameHint);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1026 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/*      */ 
/*      */     
/* 1030 */     return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SchemaJavaMemberInfo getJavaMemberInfo(TypeDefinitionComponent component, ElementDeclarationComponent element) {
/* 1039 */     return new SchemaJavaMemberInfo(element.getName().getLocalPart(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType urTypeBasedComplexSchemaTypeToSOAPType(ComplexTypeDefinitionComponent component, QName nameHint) {
/* 1048 */     if (component.getName() != null) {
/* 1049 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/* 1052 */       if (javaType != null) {
/* 1053 */         SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */         
/* 1055 */         simpleType.setSchemaTypeRef(component.getName());
/* 1056 */         simpleType.setJavaType((JavaType)javaType);
/* 1057 */         setReferenceable(simpleType);
/* 1058 */         return (SOAPType)simpleType;
/*      */       } 
/*      */     } 
/*      */     
/* 1062 */     if (component.getContentTag() == 4) {
/*      */ 
/*      */       
/* 1065 */       ParticleComponent particle = component.getParticleContent();
/* 1066 */       if (particle.occursOnce()) {
/* 1067 */         if (particle.getTermTag() == 1) {
/*      */           
/* 1069 */           ModelGroupComponent modelGroup = particle.getModelGroupTerm();
/*      */           
/* 1071 */           if (modelGroup.getCompositor() == Symbol.ALL || modelGroup.getCompositor() == Symbol.SEQUENCE) {
/*      */             SOAPOrderedStructureType sOAPOrderedStructureType;
/*      */             
/* 1074 */             SOAPStructureType structureType = null;
/*      */             
/* 1076 */             if (modelGroup.getCompositor() == Symbol.ALL) {
/*      */               
/* 1078 */               SOAPUnorderedStructureType sOAPUnorderedStructureType = new SOAPUnorderedStructureType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */             }
/*      */             else {
/*      */               
/* 1082 */               sOAPOrderedStructureType = new SOAPOrderedStructureType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1088 */             String javaTypeName = getJavaNameOfSOAPStructureType((SOAPStructureType)sOAPOrderedStructureType, (TypeDefinitionComponent)component, nameHint);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1093 */             JavaStructureType javaStructureType = new JavaStructureType(javaTypeName, false, sOAPOrderedStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1098 */             resolveNamingConflictsFor((JavaType)javaStructureType);
/* 1099 */             sOAPOrderedStructureType.setJavaType((JavaType)javaStructureType);
/* 1100 */             this._componentToSOAPTypeMap.put(component, sOAPOrderedStructureType);
/*      */ 
/*      */             
/* 1103 */             Iterator<AttributeUseComponent> iterator = component.attributeUses();
/* 1104 */             while (iterator.hasNext()) {
/*      */               
/* 1106 */               AttributeUseComponent attributeUse = iterator.next();
/*      */               
/* 1108 */               AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */ 
/*      */ 
/*      */               
/* 1112 */               if (attributeDeclaration.getTypeDefinition().getName() != null)
/*      */               {
/*      */ 
/*      */                 
/* 1116 */                 if (this._strictCompliance && attributeDeclaration.getTypeDefinition().getName().equals(SchemaConstants.QNAME_TYPE_IDREF))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1122 */                   return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */                 }
/*      */               }
/* 1125 */               SOAPType attributeType = schemaTypeToSOAPType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), getAttributeQNameHint(attributeDeclaration, nameHint));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1136 */               if (isAttributeOptional(attributeUse)) {
/*      */                 
/* 1138 */                 SOAPType tmpType = nillableSchemaTypeToSOAPType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition());
/*      */                 
/* 1140 */                 if (tmpType != null) {
/* 1141 */                   attributeType = tmpType;
/*      */                 }
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1182 */               if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)attributeType) == null && !isAttributeEnumeration(attributeType))
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/* 1187 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               }
/* 1189 */               SOAPAttributeMember member = new SOAPAttributeMember(attributeDeclaration.getName(), attributeType);
/*      */ 
/*      */ 
/*      */               
/* 1193 */               if (attributeUse.isRequired()) {
/* 1194 */                 member.setRequired(true);
/*      */               }
/* 1196 */               SchemaJavaMemberInfo memberInfo = getJavaMemberOfElementInfo(nameHint, attributeDeclaration.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1202 */               JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, attributeType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1208 */               javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */               
/* 1211 */               javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */               
/* 1214 */               member.setJavaStructureMember(javaMember);
/* 1215 */               javaStructureType.add(javaMember);
/* 1216 */               sOAPOrderedStructureType.add(member);
/*      */             } 
/*      */             
/* 1219 */             Iterator<ParticleComponent> iter = modelGroup.particles();
/* 1220 */             while (iter.hasNext()) {
/*      */               
/* 1222 */               ParticleComponent memberParticle = iter.next();
/*      */               
/* 1224 */               if (memberParticle.occursOnce() || memberParticle.occursAtMostOnce()) {
/*      */                 
/* 1226 */                 if (memberParticle.getTermTag() == 3) {
/*      */ 
/*      */                   
/* 1229 */                   ElementDeclarationComponent element = memberParticle.getElementTerm();
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1234 */                   SOAPType memberType = getSOAPMemberType(component, (SOAPStructureType)sOAPOrderedStructureType, element, nameHint, memberParticle.occursZeroOrOne());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1241 */                   SOAPStructureMember member = new SOAPStructureMember(element.getName(), memberType);
/*      */ 
/*      */ 
/*      */                   
/* 1245 */                   SchemaJavaMemberInfo memberInfo = getJavaMemberInfo((TypeDefinitionComponent)component, element);
/*      */                   
/* 1247 */                   JavaStructureMember javaMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(memberInfo.javaMemberName), memberType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1256 */                   javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1261 */                   javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1266 */                   member.setJavaStructureMember(javaMember);
/* 1267 */                   javaStructureType.add(javaMember);
/* 1268 */                   sOAPOrderedStructureType.add(member);
/*      */                   continue;
/*      */                 } 
/* 1271 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               } 
/*      */ 
/*      */               
/* 1275 */               return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */             } 
/*      */ 
/*      */             
/* 1279 */             sOAPOrderedStructureType.setJavaType((JavaType)javaStructureType);
/* 1280 */             return (SOAPType)sOAPOrderedStructureType;
/*      */           } 
/*      */           
/* 1283 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         } 
/*      */ 
/*      */         
/* 1287 */         return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */       } 
/*      */ 
/*      */       
/* 1291 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/* 1293 */     if (component.getContentTag() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1299 */       SOAPOrderedStructureType structureType = new SOAPOrderedStructureType(component.getName());
/*      */       
/* 1301 */       String javaName = getJavaNameOfType((TypeDefinitionComponent)component, component.getName());
/* 1302 */       JavaStructureType javaStructureType = new JavaStructureType(javaName, false, structureType);
/*      */       
/* 1304 */       structureType.setJavaType((JavaType)javaStructureType);
/*      */       
/* 1306 */       this._componentToLiteralTypeMap.put(component, structureType);
/*      */ 
/*      */       
/* 1309 */       for (Iterator<AttributeUseComponent> iter = component.attributeUses(); iter.hasNext(); ) {
/* 1310 */         AttributeUseComponent attributeUse = iter.next();
/*      */         
/* 1312 */         AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */ 
/*      */         
/* 1315 */         SOAPType attributeType = schemaTypeToSOAPType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), getAttributeQNameHint(attributeDeclaration, nameHint));
/*      */ 
/*      */ 
/*      */         
/* 1319 */         if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)attributeType) == null)
/*      */         {
/*      */           
/* 1322 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         }
/* 1324 */         SOAPAttributeMember member = new SOAPAttributeMember(attributeDeclaration.getName(), attributeType);
/*      */ 
/*      */ 
/*      */         
/* 1328 */         if (attributeUse.isRequired()) {
/* 1329 */           member.setRequired(true);
/*      */         }
/* 1331 */         SchemaJavaMemberInfo memberInfo = getJavaMemberOfElementInfo(nameHint, attributeDeclaration.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */         
/* 1335 */         JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, attributeType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1341 */         javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */         
/* 1343 */         javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */         
/* 1345 */         member.setJavaStructureMember(javaMember);
/* 1346 */         javaStructureType.add(javaMember);
/* 1347 */         structureType.add(member);
/*      */       } 
/*      */       
/* 1350 */       return (SOAPType)structureType;
/* 1351 */     }  if (component.getContentTag() == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1358 */       if (component.hasNoAttributeUses()) {
/* 1359 */         if (component.getName().getNamespaceURI().equals(soap11WSDLConstants.getSOAPEncodingNamespace())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1365 */           SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */           
/* 1367 */           simpleType.setSchemaTypeRef(component.getName());
/* 1368 */           JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */           
/* 1371 */           if (javaType == null)
/*      */           {
/* 1373 */             fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */           }
/*      */ 
/*      */           
/* 1377 */           simpleType.setJavaType((JavaType)javaType);
/* 1378 */           setReferenceable(simpleType);
/* 1379 */           return (SOAPType)simpleType;
/*      */         } 
/*      */         
/* 1382 */         return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */       } 
/*      */ 
/*      */       
/* 1386 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1392 */     return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType getSOAPMemberType(ComplexTypeDefinitionComponent component, SOAPStructureType structureType, ElementDeclarationComponent element, QName nameHint, boolean occursZeroOrOne) {
/* 1407 */     SOAPType memberType = null;
/* 1408 */     if (element.isNillable() || occursZeroOrOne) {
/*      */       
/* 1410 */       if (element.getTypeDefinition().isSimple()) {
/* 1411 */         memberType = nillableSchemaTypeToSOAPType(element.getTypeDefinition());
/*      */       } else {
/*      */         
/* 1414 */         memberType = schemaTypeToSOAPType(element.getTypeDefinition(), nameHint);
/*      */       } 
/*      */     } else {
/*      */       
/* 1418 */       memberType = schemaTypeToSOAPType(element.getTypeDefinition(), getElementQNameHint(element, nameHint));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1423 */     return memberType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType getLiteralMemberType(ComplexTypeDefinitionComponent component, LiteralType memberType, ElementDeclarationComponent element, LiteralStructuredType structureType) {
/* 1432 */     return memberType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType soapStructureExtensionComplexSchemaTypeToSOAPType(ComplexTypeDefinitionComponent component, SOAPStructureType parentType, QName nameHint) {
/* 1447 */     if (component.getContentTag() == 4) {
/*      */       
/* 1449 */       if (component.hasNoAttributeUses()) {
/* 1450 */         ParticleComponent particle = component.getParticleContent();
/* 1451 */         if (particle.occursOnce()) {
/* 1452 */           if (particle.getTermTag() == 1) {
/*      */             
/* 1454 */             ModelGroupComponent modelGroup = particle.getModelGroupTerm();
/*      */             
/* 1456 */             if (modelGroup.getCompositor() == Symbol.ALL || modelGroup.getCompositor() == Symbol.SEQUENCE) {
/*      */               SOAPOrderedStructureType sOAPOrderedStructureType;
/*      */               
/* 1459 */               SOAPStructureType structureType = null;
/*      */               
/* 1461 */               if (modelGroup.getCompositor() == Symbol.ALL) {
/* 1462 */                 SOAPUnorderedStructureType sOAPUnorderedStructureType = new SOAPUnorderedStructureType(component.getName());
/*      */               }
/*      */               else {
/*      */                 
/* 1466 */                 sOAPOrderedStructureType = new SOAPOrderedStructureType(component.getName());
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 1471 */               if (parentType != null && parentType.getClass() != sOAPOrderedStructureType.getClass())
/*      */               {
/*      */ 
/*      */                 
/* 1475 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               }
/* 1477 */               String javaName = getJavaNameOfType((TypeDefinitionComponent)component, component.getName());
/*      */ 
/*      */ 
/*      */               
/* 1481 */               JavaStructureType javaStructureType = new JavaStructureType(javaName, false, sOAPOrderedStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1486 */               resolveNamingConflictsFor((JavaType)javaStructureType);
/* 1487 */               sOAPOrderedStructureType.setJavaType((JavaType)javaStructureType);
/* 1488 */               this._componentToSOAPTypeMap.put(component, sOAPOrderedStructureType);
/*      */ 
/*      */ 
/*      */               
/* 1492 */               if (parentType != null) {
/* 1493 */                 processSuperType(parentType, (SOAPStructureType)sOAPOrderedStructureType, javaStructureType);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1499 */               Iterator<ParticleComponent> iter = modelGroup.particles();
/* 1500 */               while (iter.hasNext()) {
/*      */                 
/* 1502 */                 ParticleComponent memberParticle = iter.next();
/*      */                 
/* 1504 */                 if (memberParticle.occursOnce() || memberParticle.occursAtMostOnce()) {
/*      */                   
/* 1506 */                   if (memberParticle.getTermTag() == 3) {
/*      */                     SOAPType memberType;
/*      */                     
/* 1509 */                     ElementDeclarationComponent element = memberParticle.getElementTerm();
/*      */ 
/*      */ 
/*      */                     
/* 1513 */                     if (element.isNillable()) {
/*      */                       
/* 1515 */                       if (element.getTypeDefinition().isSimple())
/*      */                       {
/*      */                         
/* 1518 */                         memberType = nillableSchemaTypeToSOAPType(element.getTypeDefinition());
/*      */                       
/*      */                       }
/*      */                       else
/*      */                       {
/* 1523 */                         memberType = schemaTypeToSOAPType(element.getTypeDefinition(), nameHint);
/*      */                       
/*      */                       }
/*      */                     
/*      */                     }
/*      */                     else {
/*      */                       
/* 1530 */                       memberType = schemaTypeToSOAPType(element.getTypeDefinition(), nameHint);
/*      */                     } 
/*      */ 
/*      */ 
/*      */                     
/* 1535 */                     SOAPStructureMember member = new SOAPStructureMember(element.getName(), memberType);
/*      */ 
/*      */ 
/*      */                     
/* 1539 */                     SchemaJavaMemberInfo memberInfo = getJavaMemberInfo((TypeDefinitionComponent)component, element);
/*      */ 
/*      */ 
/*      */                     
/* 1543 */                     JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, memberType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1549 */                     javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1554 */                     javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1559 */                     member.setJavaStructureMember(javaMember);
/*      */                     
/* 1561 */                     javaStructureType.add(javaMember);
/* 1562 */                     sOAPOrderedStructureType.add(member);
/*      */                     continue;
/*      */                   } 
/* 1565 */                   return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */                 } 
/*      */ 
/*      */                 
/* 1569 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               } 
/*      */ 
/*      */               
/* 1573 */               sOAPOrderedStructureType.setJavaType((JavaType)javaStructureType);
/* 1574 */               return (SOAPType)sOAPOrderedStructureType;
/*      */             } 
/*      */             
/* 1577 */             return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */           } 
/*      */ 
/*      */           
/* 1581 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         } 
/*      */ 
/*      */         
/* 1585 */         return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */       } 
/*      */ 
/*      */       
/* 1589 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/* 1591 */     if (component.getContentTag() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1597 */       if (component.hasNoAttributeUses()) {
/*      */         SOAPOrderedStructureType sOAPOrderedStructureType;
/* 1599 */         SOAPStructureType structureType = null;
/* 1600 */         if (parentType != null) {
/* 1601 */           if (parentType instanceof SOAPOrderedStructureType) {
/* 1602 */             sOAPOrderedStructureType = new SOAPOrderedStructureType(component.getName());
/*      */           } else {
/*      */             
/* 1605 */             SOAPUnorderedStructureType sOAPUnorderedStructureType = new SOAPUnorderedStructureType(component.getName());
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1610 */           sOAPOrderedStructureType = new SOAPOrderedStructureType(component.getName());
/*      */         } 
/*      */         
/* 1613 */         String javaTypeName = getJavaNameOfType((TypeDefinitionComponent)component, component.getName());
/*      */         
/* 1615 */         JavaStructureType javaStructureType = new JavaStructureType(javaTypeName, false, sOAPOrderedStructureType);
/*      */ 
/*      */         
/* 1618 */         sOAPOrderedStructureType.setJavaType((JavaType)javaStructureType);
/*      */         
/* 1620 */         if (parentType != null && parentType.getClass() != sOAPOrderedStructureType.getClass())
/*      */         {
/*      */           
/* 1623 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         }
/*      */         
/* 1626 */         if (parentType != null) {
/* 1627 */           processSuperType(parentType, (SOAPStructureType)sOAPOrderedStructureType, javaStructureType);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1633 */         return (SOAPType)sOAPOrderedStructureType;
/*      */       } 
/*      */       
/* 1636 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/* 1638 */     if (component.getContentTag() == 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1645 */       if (component.hasNoAttributeUses()) {
/* 1646 */         if (component.getName().getNamespaceURI().equals(soap11WSDLConstants.getSOAPEncodingNamespace())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1652 */           SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */           
/* 1654 */           simpleType.setSchemaTypeRef(component.getName());
/* 1655 */           JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */           
/* 1658 */           if (javaType == null)
/*      */           {
/* 1660 */             fail("model.schema.invalidSimpleType.noJavaType", new Object[] { component.getName() });
/*      */           }
/*      */ 
/*      */           
/* 1664 */           simpleType.setJavaType((JavaType)javaType);
/* 1665 */           setReferenceable(simpleType);
/* 1666 */           return (SOAPType)simpleType;
/*      */         } 
/*      */         
/* 1669 */         return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */       } 
/*      */ 
/*      */       
/* 1673 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1679 */     return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType soapArrayBasedComplexSchemaTypeToSOAPType(ComplexTypeDefinitionComponent component, QName nameHint) {
/* 1688 */     if (component.getName() != null) {
/* 1689 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/* 1692 */       if (javaType != null) {
/* 1693 */         SOAPSimpleType simpleType = new SOAPSimpleType(component.getName());
/*      */         
/* 1695 */         simpleType.setSchemaTypeRef(component.getName());
/* 1696 */         simpleType.setJavaType((JavaType)javaType);
/* 1697 */         setReferenceable(simpleType);
/* 1698 */         return (SOAPType)simpleType;
/*      */       } 
/*      */     } 
/*      */     
/* 1702 */     boolean found = false;
/* 1703 */     for (Iterator<AttributeUseComponent> iter = component.attributeUses(); iter.hasNext(); ) {
/* 1704 */       AttributeUseComponent attributeUse = iter.next();
/*      */       
/* 1706 */       AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */       
/* 1708 */       if (attributeDeclaration.getName() != null && attributeDeclaration.getName().equals(soap11WSDLConstants.getQNameAttrArrayType())) {
/*      */ 
/*      */         
/* 1711 */         if (found)
/*      */         {
/* 1713 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         }
/*      */         
/* 1716 */         found = true;
/*      */ 
/*      */         
/* 1719 */         Iterator<SchemaAttribute> iter2 = attributeUse.getAnnotation().attributes();
/* 1720 */         while (iter2.hasNext()) {
/*      */           
/* 1722 */           SchemaAttribute attr = iter2.next();
/* 1723 */           if (attr.getQName().equals(WSDLConstants.QNAME_ATTR_ARRAY_TYPE)) {
/*      */ 
/*      */             
/* 1726 */             String typeSpecifier = attr.getValue();
/* 1727 */             if (typeSpecifier == null) {
/* 1728 */               throw new ModelException(new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" }));
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1735 */             return processSOAPArrayType((TypeDefinitionComponent)component, attr.getParent(), attr.getValue());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1746 */     if (component.getContentTag() == 4) {
/*      */       
/* 1748 */       ParticleComponent particle = component.getParticleContent();
/* 1749 */       if (particle.occursOnce()) {
/* 1750 */         if (particle.getTermTag() == 1) {
/*      */           
/* 1752 */           ModelGroupComponent modelGroup = particle.getModelGroupTerm();
/*      */           
/* 1754 */           if (modelGroup.getCompositor() == Symbol.SEQUENCE) {
/*      */             
/* 1756 */             SOAPArrayType arrayType = new SOAPArrayType(component.getName());
/*      */             
/* 1758 */             found = false;
/*      */             
/* 1760 */             Iterator<ParticleComponent> iterator = modelGroup.particles();
/* 1761 */             while (iterator.hasNext()) {
/*      */               
/* 1763 */               ParticleComponent memberParticle = iterator.next();
/*      */ 
/*      */               
/* 1766 */               if (found)
/*      */               {
/* 1768 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               }
/* 1770 */               found = true;
/*      */               
/* 1772 */               if (memberParticle.mayOccurMoreThanOnce()) {
/* 1773 */                 if (memberParticle.getTermTag() == 3) {
/*      */ 
/*      */                   
/* 1776 */                   ElementDeclarationComponent element = memberParticle.getElementTerm();
/*      */ 
/*      */                   
/* 1779 */                   SOAPType arrayElementType = schemaTypeToSOAPType(element.getTypeDefinition(), nameHint);
/*      */ 
/*      */ 
/*      */                   
/* 1783 */                   arrayType.setElementName(element.getName());
/* 1784 */                   arrayType.setElementType(arrayElementType);
/* 1785 */                   arrayType.setRank(1);
/* 1786 */                   if (arrayElementType.getJavaType() != null) {
/*      */                     
/* 1788 */                     JavaArrayType javaArrayType = new JavaArrayType(arrayElementType.getJavaType().getName() + "[]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 1794 */                     javaArrayType.setElementType(arrayElementType.getJavaType());
/*      */                     
/* 1796 */                     arrayType.setJavaType((JavaType)javaArrayType);
/*      */                   } 
/*      */                   continue;
/*      */                 } 
/* 1800 */                 return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */               } 
/*      */ 
/*      */               
/* 1804 */               return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */             } 
/*      */ 
/*      */             
/* 1808 */             if (found) {
/* 1809 */               return (SOAPType)arrayType;
/*      */             }
/*      */             
/* 1812 */             return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */           } 
/*      */ 
/*      */           
/* 1816 */           return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1821 */         return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */       } 
/*      */ 
/*      */       
/* 1825 */       return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1830 */     return (SOAPType)mustGetCustomTypeFor((TypeDefinitionComponent)component);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPType processSOAPArrayType(TypeDefinitionComponent component, SchemaElement element, String typeSpecifier) {
/*      */     try {
/* 1842 */       int openingBracketIndex = typeSpecifier.indexOf('[');
/* 1843 */       if (openingBracketIndex == -1) {
/* 1844 */         throw new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1849 */       int currentRank = 0;
/* 1850 */       String typeName = typeSpecifier.substring(0, openingBracketIndex).trim();
/*      */       
/* 1852 */       QName typeQName = element.asQName(typeName);
/* 1853 */       SOAPType elementType = schemaTypeToSOAPType(typeQName);
/* 1854 */       if (elementType instanceof SOAPArrayType) {
/* 1855 */         currentRank = ((SOAPArrayType)elementType).getRank();
/*      */       }
/*      */       while (true)
/*      */       { SOAPArrayType sOAPArrayType;
/* 1859 */         int closingBracketIndex = typeSpecifier.indexOf(']', openingBracketIndex);
/*      */         
/* 1861 */         if (closingBracketIndex == -1) {
/* 1862 */           throw new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" });
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1867 */         int commaIndex = typeSpecifier.indexOf(',', openingBracketIndex + 1);
/*      */         
/* 1869 */         if (commaIndex == -1 || commaIndex > closingBracketIndex) {
/*      */           
/* 1871 */           int[] size = null;
/* 1872 */           if (closingBracketIndex - openingBracketIndex > 1) {
/* 1873 */             int i = Integer.parseInt(typeSpecifier.substring(openingBracketIndex + 1, closingBracketIndex));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1878 */             size = new int[] { i };
/*      */           } 
/*      */           
/* 1881 */           SOAPArrayType arrayType = new SOAPArrayType(component.getName());
/*      */           
/* 1883 */           arrayType.setElementName(InternalEncodingConstants.ARRAY_ELEMENT_NAME);
/*      */           
/* 1885 */           arrayType.setElementType(elementType);
/* 1886 */           arrayType.setRank(++currentRank);
/* 1887 */           arrayType.setSize(size);
/* 1888 */           if (elementType.getJavaType() != null) {
/* 1889 */             JavaArrayType javaArrayType = new JavaArrayType(elementType.getJavaType().getName() + "[]");
/*      */ 
/*      */             
/* 1892 */             javaArrayType.setElementType(elementType.getJavaType());
/* 1893 */             arrayType.setJavaType((JavaType)javaArrayType);
/*      */           } 
/* 1895 */           sOAPArrayType = arrayType;
/*      */         } else {
/* 1897 */           List<Integer> sizeList = null;
/* 1898 */           boolean allowSizeSpecifiers = true;
/* 1899 */           boolean timeToGo = false;
/* 1900 */           int rank = 0;
/* 1901 */           int contentIndex = openingBracketIndex + 1;
/*      */           while (true) {
/* 1903 */             rank++;
/* 1904 */             if (commaIndex - contentIndex > 0) {
/* 1905 */               if (!allowSizeSpecifiers) {
/* 1906 */                 throw new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1912 */               int i = Integer.parseInt(typeSpecifier.substring(contentIndex, commaIndex));
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1917 */               if (sizeList == null) {
/* 1918 */                 sizeList = new ArrayList();
/*      */               }
/* 1920 */               sizeList.add(new Integer(i));
/*      */             } else {
/*      */               
/* 1923 */               if (sizeList != null) {
/* 1924 */                 throw new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1930 */               allowSizeSpecifiers = false;
/*      */             } 
/*      */             
/* 1933 */             if (timeToGo) {
/*      */               break;
/*      */             }
/* 1936 */             contentIndex = commaIndex + 1;
/* 1937 */             commaIndex = typeSpecifier.indexOf(',', contentIndex);
/* 1938 */             if (commaIndex == -1 || commaIndex > closingBracketIndex) {
/*      */               
/* 1940 */               commaIndex = closingBracketIndex;
/* 1941 */               timeToGo = true;
/*      */             } 
/*      */           } 
/*      */           
/* 1945 */           SOAPArrayType arrayType = new SOAPArrayType(component.getName());
/*      */           
/* 1947 */           arrayType.setElementName(InternalEncodingConstants.ARRAY_ELEMENT_NAME);
/*      */           
/* 1949 */           arrayType.setElementType((SOAPType)sOAPArrayType);
/* 1950 */           currentRank += rank;
/* 1951 */           arrayType.setRank(currentRank);
/* 1952 */           int[] size = null;
/* 1953 */           if (allowSizeSpecifiers && sizeList != null) {
/* 1954 */             size = new int[sizeList.size()];
/* 1955 */             for (int i = 0; i < size.length; i++) {
/* 1956 */               size[i] = ((Integer)sizeList.get(i)).intValue();
/*      */             }
/*      */           } 
/* 1959 */           arrayType.setSize(size);
/* 1960 */           if (sOAPArrayType.getJavaType() != null) {
/* 1961 */             StringBuffer sb = new StringBuffer();
/* 1962 */             sb.append(sOAPArrayType.getJavaType().getName());
/* 1963 */             for (int i = 0; i < rank; i++) {
/* 1964 */               sb.append("[]");
/*      */             }
/* 1966 */             String javaArrayTypeName = sb.toString();
/* 1967 */             JavaArrayType javaArrayType = new JavaArrayType(javaArrayTypeName);
/*      */             
/* 1969 */             javaArrayType.setElementType(sOAPArrayType.getJavaType());
/* 1970 */             arrayType.setJavaType((JavaType)javaArrayType);
/*      */           } 
/* 1972 */           sOAPArrayType = arrayType;
/*      */         } 
/*      */         
/* 1975 */         openingBracketIndex = typeSpecifier.indexOf('[', closingBracketIndex + 1);
/*      */         
/* 1977 */         if (openingBracketIndex == -1)
/* 1978 */         { if (closingBracketIndex != typeSpecifier.length() - 1) {
/* 1979 */             throw new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" });
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1988 */           setSOAPArrayHolderName((SOAPType)sOAPArrayType);
/* 1989 */           return (SOAPType)sOAPArrayType; }  } 
/* 1990 */     } catch (NumberFormatException e) {
/* 1991 */       throw new ModelException(new ValidationException("validation.invalidAttributeValue", new Object[] { typeSpecifier, "arrayType" }));
/*      */ 
/*      */     
/*      */     }
/* 1995 */     catch (ValidationException e) {
/* 1996 */       throw new ModelException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setSOAPArrayHolderName(SOAPType type) {
/* 2005 */     if (type instanceof SOAPArrayType) {
/* 2006 */       JavaType javaType = type.getJavaType();
/* 2007 */       if (javaType instanceof JavaArrayType) {
/* 2008 */         ((JavaArrayType)javaType).setSOAPArrayHolderName(type.getName().getLocalPart());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType schemaTypeToLiteralType(TypeDefinitionComponent component, QName nameHint) {
/* 2021 */     QName mappingNameHint = component.getName();
/* 2022 */     QName hint = component.getName();
/*      */ 
/*      */ 
/*      */     
/* 2026 */     return schemaTypeToLiteralType(component, nameHint, mappingNameHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType schemaTypeToLiteralType(TypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 2038 */     LiteralType result = (LiteralType)this._componentToLiteralTypeMap.get(component);
/*      */     
/* 2040 */     if (result == null) {
/*      */       try {
/* 2042 */         if (this._noDataBinding) {
/* 2043 */           LiteralFragmentType literalFragmentType = getLiteralFragmentTypeFor(component, nameHint);
/*      */         }
/* 2045 */         else if (component.isSimple()) {
/* 2046 */           result = simpleSchemaTypeToLiteralType((SimpleTypeDefinitionComponent)component, nameHint, mappingNameHint);
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 2051 */         else if (component.isComplex()) {
/* 2052 */           result = complexSchemaTypeToLiteralType((ComplexTypeDefinitionComponent)component, nameHint, mappingNameHint);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 2059 */           throw new IllegalArgumentException();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2066 */         this._componentToLiteralTypeMap.put(component, result);
/*      */       } finally {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2072 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType simpleSchemaTypeToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint) {
/* 2078 */     return simpleSchemaTypeToLiteralType(component, nameHint, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType simpleSchemaTypeToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 2089 */     if (component.getBaseTypeDefinition() == this._schema.getSimpleUrType()) {
/* 2090 */       if (component.getVarietyTag() == 1) {
/*      */ 
/*      */         
/* 2093 */         String nsURI = component.getName().getNamespaceURI();
/* 2094 */         if (nsURI != null && nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/*      */           
/* 2096 */           if (!component.facets().hasNext()) {
/*      */ 
/*      */             
/* 2099 */             if (this._strictCompliance && (component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) || component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)))
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2105 */               if (!checkStrictCompliance(component.getName())) {
/* 2106 */                 return null;
/*      */               }
/*      */             }
/* 2109 */             if (!this._strictCompliance && component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE))
/*      */             {
/*      */               
/* 2112 */               return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2119 */             if (component.getName().equals(SchemaConstants.QNAME_TYPE_ID) || component.getName().equals(SchemaConstants.QNAME_TYPE_IDREF))
/*      */             {
/*      */ 
/*      */ 
/*      */               
/* 2124 */               return handleIDIDREF(component);
/*      */             }
/* 2126 */             LiteralSimpleType simpleType = new LiteralSimpleType(component.getName());
/*      */             
/* 2128 */             simpleType.setSchemaTypeRef(component.getName());
/* 2129 */             JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2134 */             if (javaType == null)
/*      */             {
/* 2136 */               return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */             }
/*      */ 
/*      */             
/* 2140 */             simpleType.setJavaType((JavaType)javaType);
/* 2141 */             return (LiteralType)simpleType;
/*      */           } 
/*      */           
/* 2144 */           return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2150 */         return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */       } 
/* 2152 */       if (component.getVarietyTag() == 2) {
/*      */ 
/*      */         
/* 2155 */         if (doWeHandleSimpleSchemaTypeDerivationByList())
/* 2156 */           return listToLiteralType(component, nameHint); 
/* 2157 */         return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */       } 
/* 2159 */       if (component.getVarietyTag() == 3) {
/*      */ 
/*      */ 
/*      */         
/* 2163 */         fail("model.schema.unionNotSupported", new Object[] { component.getName() });
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 2169 */         if (component.getName().equals(SchemaConstants.QNAME_TYPE_SIMPLE_URTYPE))
/*      */         {
/*      */           
/* 2172 */           return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */         }
/* 2174 */         fail("model.schema.invalidSimpleType", new Object[] { component.getName() });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2179 */       fail("model.schema.invalidSimpleType", new Object[] { component.getName() });
/*      */ 
/*      */ 
/*      */       
/* 2183 */       return null;
/*      */     } 
/* 2185 */     return anonymousSimpleSchemaTypeToLiteralType(component, nameHint, mappingNameHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWeHandleSimpleSchemaTypeDerivationByList() {
/* 2196 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType handleIDIDREF(SimpleTypeDefinitionComponent component) {
/* 2204 */     LiteralIDType baseType = new LiteralIDType(component.getName());
/* 2205 */     baseType.setSchemaTypeRef(component.getName());
/* 2206 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */     
/* 2209 */     baseType.setJavaType((JavaType)javaType);
/* 2210 */     baseType.setResolveIDREF(this._resolveIDREF);
/* 2211 */     return (LiteralType)baseType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType anonymousSimpleSchemaTypeToLiteralType(SimpleTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 2225 */     Iterator<Facet> iter = component.facets();
/*      */     
/* 2227 */     SimpleTypeDefinitionComponent baseTypeComponent = component.getBaseTypeDefinition();
/*      */ 
/*      */     
/* 2230 */     if (iter.hasNext() && component.getVarietyTag() == 1) {
/*      */ 
/*      */       
/* 2233 */       Facet facet = iter.next();
/* 2234 */       if (facet instanceof EnumerationFacet) {
/* 2235 */         Iterator values = ((EnumerationFacet)facet).values();
/* 2236 */         if (values.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2241 */           if (this._jaxbEnumType && component.getName() == null) {
/* 2242 */             String nsURI = baseTypeComponent.getName().getNamespaceURI();
/*      */             
/* 2244 */             if (nsURI != null && nsURI.equals("http://www.w3.org/2001/XMLSchema"))
/*      */             {
/* 2246 */               return schemaTypeToLiteralType((TypeDefinitionComponent)baseTypeComponent, nameHint);
/*      */             }
/*      */ 
/*      */             
/* 2250 */             fail("model.schema.invalidSimpleType.noNamespaceURI", new Object[] { component.getName() });
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 2255 */           return enumerationToLiteralType(component, (EnumerationFacet)facet, nameHint, mappingNameHint);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2264 */     if (component.getVarietyTag() == 1) {
/*      */       
/* 2266 */       String nsURI = baseTypeComponent.getName().getNamespaceURI();
/* 2267 */       if (nsURI != null && nsURI.equals("http://www.w3.org/2001/XMLSchema")) {
/*      */         
/* 2269 */         LiteralType baseType = schemaTypeToLiteralType((TypeDefinitionComponent)baseTypeComponent, nameHint, mappingNameHint);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2274 */         return baseType;
/*      */       } 
/* 2276 */       SimpleTypeEncoder encoder = null;
/* 2277 */       SimpleTypeDefinitionComponent _baseType = null;
/* 2278 */       while (encoder == null) {
/* 2279 */         if (baseTypeComponent.getVarietyTag() == 1) {
/*      */           
/* 2281 */           _baseType = baseTypeComponent.getBaseTypeDefinition();
/* 2282 */         } else if (baseTypeComponent.getVarietyTag() == 2) {
/*      */ 
/*      */           
/* 2285 */           _baseType = (baseTypeComponent.getItemTypeDefinition().getName() == null) ? baseTypeComponent.getBaseTypeDefinition() : baseTypeComponent.getItemTypeDefinition();
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2293 */         encoder = (SimpleTypeEncoder)this._simpleTypeEncoderMap.get(_baseType.getName());
/*      */ 
/*      */ 
/*      */         
/* 2297 */         if (baseTypeComponent.getName().equals(_baseType.getName())) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2302 */         baseTypeComponent = _baseType;
/*      */       } 
/*      */ 
/*      */       
/* 2306 */       return simpleSchemaTypeToLiteralType(baseTypeComponent, baseTypeComponent.getName(), mappingNameHint);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2314 */     if (component.getVarietyTag() == 2) {
/*      */ 
/*      */       
/* 2317 */       if (doWeHandleSimpleSchemaTypeDerivationByList())
/* 2318 */         return listToLiteralType(component, nameHint); 
/* 2319 */       return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */     } 
/*      */     
/* 2322 */     return getLiteralSimpleStringTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType complexSchemaTypeToLiteralType(ComplexTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 2331 */     if (component == this._schema.getUrType()) {
/*      */ 
/*      */       
/* 2334 */       if (component.getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)) {
/*      */ 
/*      */ 
/*      */         
/* 2338 */         if (this._strictCompliance && 
/* 2339 */           !checkStrictCompliance(component.getName())) {
/* 2340 */           return null;
/*      */         }
/* 2342 */         LiteralFragmentType literalFragmentType = new LiteralFragmentType();
/* 2343 */         literalFragmentType.setName(component.getName());
/* 2344 */         literalFragmentType.setJavaType((JavaType)this.javaTypes.SOAPELEMENT_JAVATYPE);
/* 2345 */         return (LiteralType)literalFragmentType;
/*      */       } 
/* 2347 */       LiteralSimpleType simpleType = new LiteralSimpleType(component.getName());
/*      */       
/* 2349 */       simpleType.setSchemaTypeRef(component.getName());
/* 2350 */       JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */       
/* 2353 */       if (javaType == null)
/*      */       {
/* 2355 */         return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */       }
/* 2357 */       simpleType.setJavaType((JavaType)javaType);
/* 2358 */       return (LiteralType)simpleType;
/*      */     } 
/* 2360 */     if (component.getBaseTypeDefinition() == this._schema.getUrType()) {
/* 2361 */       return urTypeBasedComplexSchemaTypeToLiteralType(component, nameHint, mappingNameHint);
/*      */     }
/*      */ 
/*      */     
/* 2365 */     if (doWeHandleComplexSchemaTypeExtensionBySimpleContent() && component.getContentTag() == 2 && component.getDerivationMethod() == Symbol.EXTENSION)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 2370 */       return complexSchemaTypeSimpleContentExtensionToLiteralType(component, nameHint);
/*      */     }
/*      */     
/* 2373 */     if (doWeHandleComplexSchemaTypeExtensionByComplexType() && component.getContentTag() == 4 && component.getDerivationMethod() == Symbol.EXTENSION)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2379 */       return urTypeBasedComplexSchemaTypeToLiteralType(component, nameHint, mappingNameHint);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2384 */     return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWeHandleComplexSchemaTypeExtensionByComplexType() {
/* 2392 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWeHandleComplexSchemaTypeExtensionBySimpleContent() {
/* 2399 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJavaNameOfElementType(LiteralStructuredType structureType, TypeDefinitionComponent component, QName nameHint) {
/* 2406 */     return makePackageQualified(this._env.getNames().validJavaClassName(structureType.getName().getLocalPart()), structureType.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SchemaJavaMemberInfo getJavaMemberOfElementInfo(QName typeName, String memberName) {
/* 2415 */     return new SchemaJavaMemberInfo(this._env.getNames().validJavaMemberName(memberName), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getSimpleTypeBaseName(TypeDefinitionComponent typeDefinition) {
/* 2423 */     if (typeDefinition instanceof SimpleTypeDefinitionComponent && !((SimpleTypeDefinitionComponent)typeDefinition).getBaseTypeDefinition().getName().equals(BuiltInTypes.ANY_SIMPLE_URTYPE))
/*      */     {
/*      */ 
/*      */       
/* 2427 */       return getSimpleTypeBaseName((TypeDefinitionComponent)((SimpleTypeDefinitionComponent)typeDefinition).getBaseTypeDefinition());
/*      */     }
/*      */     
/* 2430 */     return typeDefinition.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType urTypeBasedComplexSchemaTypeToLiteralType(ComplexTypeDefinitionComponent component, QName nameHint, QName mappingNameHint) {
/* 2438 */     if (component.getContentTag() == 4) {
/*      */       
/* 2440 */       ParticleComponent particle = component.getParticleContent();
/* 2441 */       if (particle.occursOnce()) {
/* 2442 */         if (particle.getTermTag() == 1) {
/*      */           
/* 2444 */           ModelGroupComponent modelGroup = particle.getModelGroupTerm();
/*      */           
/* 2446 */           if (modelGroup.getCompositor() == Symbol.ALL || modelGroup.getCompositor() == Symbol.SEQUENCE) {
/*      */             LiteralSequenceType literalSequenceType;
/* 2448 */             LiteralStructuredType structureType = null;
/* 2449 */             if (modelGroup.getCompositor() == Symbol.ALL) {
/* 2450 */               LiteralAllType literalAllType = new LiteralAllType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */             }
/*      */             else {
/*      */               
/* 2454 */               literalSequenceType = new LiteralSequenceType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2459 */             if (component.getName() == null && mappingNameHint != null) {
/* 2460 */               literalSequenceType.setProperty("com.sun.xml.rpc.processor.model.AnonymousTypeName", mappingNameHint.getLocalPart());
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 2465 */             String javaName = getJavaNameOfElementType((LiteralStructuredType)literalSequenceType, (TypeDefinitionComponent)component, nameHint);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2470 */             JavaStructureType javaStructureType = new JavaStructureType(javaName, false, literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2476 */             updateModifiers(javaStructureType);
/*      */             
/* 2478 */             resolveNamingConflictsFor((JavaType)javaStructureType);
/* 2479 */             literalSequenceType.setJavaType((JavaType)javaStructureType);
/* 2480 */             this._componentToLiteralTypeMap.put(component, literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2486 */             if (doWeHandleComplexSchemaTypeExtensionByComplexType() && component.getContentTag() == 4 && component.getDerivationMethod() == Symbol.EXTENSION) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2493 */               LiteralType parentType = schemaTypeToLiteralType(component.getBaseTypeDefinition(), new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "_Base"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2500 */               if (parentType instanceof LiteralStructuredType) {
/* 2501 */                 processSuperType((LiteralStructuredType)parentType, (LiteralStructuredType)literalSequenceType, javaStructureType);
/*      */               }
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2508 */             Iterator<AttributeUseComponent> iterator = component.attributeUses();
/* 2509 */             while (iterator.hasNext()) {
/*      */               LiteralSimpleType literalSimpleType;
/* 2511 */               AttributeUseComponent attributeUse = iterator.next();
/*      */               
/* 2513 */               AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */ 
/*      */ 
/*      */               
/* 2517 */               if (attributeDeclaration.getTypeDefinition().getName() != null)
/*      */               {
/*      */ 
/*      */                 
/* 2521 */                 if (this._strictCompliance && attributeDeclaration.getTypeDefinition().getName().equals(SchemaConstants.QNAME_TYPE_IDREF))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2527 */                   return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), SchemaConstants.QNAME_TYPE_IDREF);
/*      */                 }
/*      */               }
/*      */ 
/*      */ 
/*      */               
/* 2533 */               LiteralType attributeType = schemaTypeToLiteralType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), getAttributeQNameHint(attributeDeclaration, nameHint));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2544 */               if (isAttributeOptional(attributeUse)) {
/*      */                 
/* 2546 */                 LiteralSimpleType literalSimpleType1 = getNillableLiteralSimpleType(attributeType.getName(), (TypeDefinitionComponent)attributeDeclaration.getTypeDefinition());
/*      */ 
/*      */                 
/* 2549 */                 if (literalSimpleType1 != null) {
/* 2550 */                   literalSimpleType = literalSimpleType1;
/*      */                 }
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2607 */               if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)literalSimpleType) == null && !isAttributeEnumeration((LiteralType)literalSimpleType))
/*      */               {
/*      */ 
/*      */ 
/*      */                 
/* 2612 */                 return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */               }
/*      */ 
/*      */               
/* 2616 */               LiteralAttributeMember member = new LiteralAttributeMember(attributeDeclaration.getName(), (LiteralType)literalSimpleType);
/*      */ 
/*      */ 
/*      */               
/* 2620 */               if (attributeUse.isRequired()) {
/* 2621 */                 member.setRequired(true);
/*      */               }
/* 2623 */               SchemaJavaMemberInfo memberInfo = getJavaMemberOfElementInfo(nameHint, attributeDeclaration.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2629 */               JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, literalSimpleType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2635 */               javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */               
/* 2638 */               javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */               
/* 2641 */               member.setJavaStructureMember(javaMember);
/* 2642 */               javaStructureType.add(javaMember);
/* 2643 */               literalSequenceType.add(member);
/*      */             } 
/*      */ 
/*      */             
/* 2647 */             Iterator<ParticleComponent> iter = modelGroup.particles();
/* 2648 */             while (iter.hasNext()) {
/*      */               
/* 2650 */               ParticleComponent memberParticle = iter.next();
/*      */ 
/*      */               
/* 2653 */               if (memberParticle.doesNotOccur()) {
/*      */                 continue;
/*      */               }
/*      */               
/* 2657 */               if (memberParticle.getTermTag() == 3) {
/*      */                 LiteralSimpleType literalSimpleType;
/*      */                 
/* 2660 */                 ElementDeclarationComponent element = memberParticle.getElementTerm();
/*      */ 
/*      */                 
/* 2663 */                 if (element.getTypeDefinition().getName() != null)
/*      */                 {
/* 2665 */                   if (this._strictCompliance && (element.getTypeDefinition().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) || element.getTypeDefinition().getName().equals(SchemaConstants.QNAME_TYPE_URTYPE)))
/*      */                   {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2678 */                     return (LiteralType)getLiteralFragmentTypeFor(element.getTypeDefinition(), SchemaConstants.QNAME_TYPE_IDREF);
/*      */                   }
/*      */                 }
/*      */ 
/*      */ 
/*      */                 
/* 2684 */                 LiteralType memberType = null;
/* 2685 */                 if (element.getTypeDefinition().getName() != null) {
/*      */                   
/* 2687 */                   memberType = schemaTypeToLiteralType(element.getTypeDefinition(), getElementQNameHint(element, nameHint));
/*      */ 
/*      */                 
/*      */                 }
/*      */                 else {
/*      */ 
/*      */                   
/* 2694 */                   memberType = schemaTypeToLiteralType(element.getTypeDefinition(), getElementQNameHint(element, nameHint), getElementMappingNameHint(element, mappingNameHint));
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2707 */                 LiteralElementMember member = new LiteralElementMember(element.getName(), memberType);
/*      */ 
/*      */ 
/*      */                 
/* 2711 */                 JavaType javaMemberType = null;
/*      */                 
/* 2713 */                 if (element.isNillable() || isParticleOptional(memberParticle)) {
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2718 */                   QName baseTypeName = getSimpleTypeBaseName(element.getTypeDefinition());
/*      */                   
/* 2720 */                   if (element.getName().getLocalPart().equals("maxEntities"))
/* 2721 */                     System.out.println("stop"); 
/* 2722 */                   LiteralSimpleType literalSimpleType1 = getNillableLiteralSimpleType(baseTypeName, element.getTypeDefinition());
/*      */                   
/* 2724 */                   if (literalSimpleType1 != null) {
/* 2725 */                     literalSimpleType = literalSimpleType1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                     
/* 2780 */                     if (doMakeMemberBoxedType()) {
/* 2781 */                       member.setType((LiteralType)literalSimpleType);
/*      */                     }
/*      */                   } 
/*      */                 } 
/*      */                 
/* 2786 */                 LiteralType literalType1 = getLiteralMemberType(component, (LiteralType)literalSimpleType, element, (LiteralStructuredType)literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2794 */                 if (element.isNillable()) {
/* 2795 */                   member.setNillable(true);
/*      */                 }
/* 2797 */                 if (memberParticle.occursAtLeastOnce()) {
/* 2798 */                   member.setRequired(true);
/*      */                 }
/* 2800 */                 if (memberParticle.mayOccurMoreThanOnce()) {
/* 2801 */                   member.setRepeated(true);
/*      */                   
/* 2803 */                   JavaArrayType javaArrayType = new JavaArrayType(literalType1.getJavaType().getName() + "[]");
/*      */ 
/*      */ 
/*      */                   
/* 2807 */                   javaArrayType.setElementType(literalType1.getJavaType());
/*      */                   
/* 2809 */                   JavaArrayType javaArrayType1 = javaArrayType;
/*      */ 
/*      */                   
/* 2812 */                   String n = getJ2EEAnonymousArrayTypeName(memberParticle, element, mappingNameHint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2818 */                   literalSequenceType.setProperty("com.sun.xml.rpc.processor.model.AnonymousArrayTypeName", n);
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2823 */                   literalSequenceType.setProperty("com.sun.xml.rpc.processor.model.AnonymousArrayJavaType", javaArrayType1.getName());
/*      */                 
/*      */                 }
/*      */                 else {
/*      */ 
/*      */                   
/* 2829 */                   javaMemberType = literalType1.getJavaType();
/*      */                 } 
/* 2831 */                 SchemaJavaMemberInfo memberInfo = getJavaMemberOfElementInfo(mappingNameHint, element.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */                 
/* 2835 */                 JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, javaMemberType, member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2841 */                 javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */                 
/* 2844 */                 javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */                 
/* 2847 */                 member.setJavaStructureMember(javaMember);
/* 2848 */                 javaStructureType.add(javaMember);
/* 2849 */                 literalSequenceType.add(member); continue;
/* 2850 */               }  if (memberParticle.getTermTag() == 2 && doWeHandleWildcard()) {
/*      */ 
/*      */ 
/*      */                 
/* 2854 */                 WildcardComponent wildcard = memberParticle.getWildcardTerm();
/*      */ 
/*      */                 
/* 2857 */                 if (modelGroup.getCompositor() == Symbol.ALL)
/*      */                 {
/* 2859 */                   fail("model.schema.invalidWildcard.allCompositor", new Object[] { component.getName() });
/*      */                 }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2870 */                 TypeDefinitionComponent anyURIcomponent = this._schema.findTypeDefinition(SchemaConstants.QNAME_TYPE_URTYPE);
/*      */ 
/*      */                 
/* 2873 */                 LiteralFragmentType literalFragmentType = getLiteralFragmentTypeFor(anyURIcomponent, SchemaConstants.QNAME_TYPE_URTYPE);
/*      */ 
/*      */ 
/*      */                 
/* 2877 */                 LiteralWildcardMember member = new LiteralWildcardMember((LiteralType)literalFragmentType);
/*      */                 
/* 2879 */                 if (wildcard.getNamespaceConstraintTag() == 2) {
/*      */ 
/*      */                   
/* 2882 */                   member.setExcludedNamespaceName(wildcard.getNamespaceName());
/*      */                 }
/* 2884 */                 else if (wildcard.getNamespaceConstraintTag() != 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 2891 */                   return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */                 } 
/*      */ 
/*      */ 
/*      */                 
/* 2896 */                 JavaType javaMemberType = null;
/* 2897 */                 if (memberParticle.occursAtLeastOnce()) {
/* 2898 */                   member.setRequired(true);
/*      */                 }
/* 2900 */                 if (memberParticle.mayOccurMoreThanOnce()) {
/* 2901 */                   member.setRepeated(true);
/*      */                   
/* 2903 */                   JavaArrayType javaArrayType = new JavaArrayType(literalFragmentType.getJavaType().getName() + "[]");
/*      */ 
/*      */ 
/*      */                   
/* 2907 */                   javaArrayType.setElementType(literalFragmentType.getJavaType());
/*      */                   
/* 2909 */                   JavaArrayType javaArrayType1 = javaArrayType;
/*      */                 } else {
/* 2911 */                   javaMemberType = literalFragmentType.getJavaType();
/*      */                 } 
/*      */                 
/* 2914 */                 JavaStructureMember javaMember = new JavaStructureMember(getUniqueMemberName(javaStructureType, "_any"), javaMemberType, member, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2922 */                 javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */ 
/*      */                 
/* 2925 */                 javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */ 
/*      */                 
/* 2928 */                 member.setJavaStructureMember(javaMember);
/* 2929 */                 javaStructureType.add(javaMember);
/* 2930 */                 literalSequenceType.add((LiteralElementMember)member);
/*      */                 continue;
/*      */               } 
/* 2933 */               if (memberParticle.getModelGroupTerm() != null && memberParticle.getModelGroupTerm().getCompositor() == Symbol.CHOICE)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2939 */                 warn("model.schema.notImplemented.generatingSOAPElement", new Object[] { "xsd:choice", nameHint });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2946 */               return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2951 */             return (LiteralType)literalSequenceType;
/*      */           } 
/*      */           
/* 2954 */           if (modelGroup.getCompositor() == Symbol.CHOICE) {
/* 2955 */             warn("model.schema.notImplemented.generatingSOAPElement", new Object[] { "xsd:choice", getUniqueQNameFor((TypeDefinitionComponent)component, nameHint) });
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2961 */           return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */         } 
/*      */ 
/*      */         
/* 2965 */         return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */       } 
/*      */ 
/*      */       
/* 2969 */       return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */     } 
/* 2971 */     if (component.getContentTag() == 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2976 */       LiteralSequenceType structureType = new LiteralSequenceType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */       
/* 2978 */       JavaStructureType javaStructureType = new JavaStructureType(makePackageQualified(this._env.getNames().validJavaClassName(structureType.getName().getLocalPart()), structureType.getName()), false, structureType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2986 */       resolveNamingConflictsFor((JavaType)javaStructureType);
/* 2987 */       structureType.setJavaType((JavaType)javaStructureType);
/* 2988 */       this._componentToLiteralTypeMap.put(component, structureType);
/*      */ 
/*      */       
/* 2991 */       for (Iterator<AttributeUseComponent> iter = component.attributeUses(); iter.hasNext(); ) {
/* 2992 */         LiteralSimpleType literalSimpleType; AttributeUseComponent attributeUse = iter.next();
/*      */         
/* 2994 */         AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */ 
/*      */         
/* 2997 */         LiteralType attributeType = schemaTypeToLiteralType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), getAttributeQNameHint(attributeDeclaration, nameHint));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3008 */         if (isAttributeOptional(attributeUse)) {
/* 3009 */           LiteralSimpleType literalSimpleType1 = getNillableLiteralSimpleType(attributeType.getName(), (TypeDefinitionComponent)attributeDeclaration.getTypeDefinition());
/*      */ 
/*      */           
/* 3012 */           if (literalSimpleType1 != null) {
/* 3013 */             literalSimpleType = literalSimpleType1;
/*      */           }
/*      */         } 
/*      */         
/* 3017 */         if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)literalSimpleType) == null && !doWeHandleAttributeTypeEnumeration((LiteralType)literalSimpleType))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 3022 */           return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 3027 */         LiteralAttributeMember member = new LiteralAttributeMember(attributeDeclaration.getName(), (LiteralType)literalSimpleType);
/*      */ 
/*      */ 
/*      */         
/* 3031 */         if (attributeUse.isRequired()) {
/* 3032 */           member.setRequired(true);
/*      */         }
/* 3034 */         SchemaJavaMemberInfo memberInfo = getJavaMemberOfElementInfo(nameHint, attributeDeclaration.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */         
/* 3038 */         JavaStructureMember javaMember = new JavaStructureMember(memberInfo.javaMemberName, literalSimpleType.getJavaType(), member, memberInfo.isDataMember);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3044 */         javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */         
/* 3046 */         javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */         
/* 3048 */         member.setJavaStructureMember(javaMember);
/* 3049 */         javaStructureType.add(javaMember);
/* 3050 */         structureType.add(member);
/*      */       } 
/*      */       
/* 3053 */       return (LiteralType)structureType;
/*      */     } 
/*      */     
/* 3056 */     return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWeHandleAttributeTypeEnumeration(LiteralType attributeType) {
/* 3067 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralSimpleType getNillableLiteralSimpleType(QName typeName, TypeDefinitionComponent typeDef) {
/* 3075 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaWrapperTypeMap.get(typeDef.getName());
/*      */ 
/*      */ 
/*      */     
/* 3079 */     if (javaType == null) {
/* 3080 */       return null;
/*      */     }
/*      */     
/* 3083 */     LiteralSimpleType result = (LiteralSimpleType)this._nillableSimpleTypeComponentToLiteralTypeMap.get(typeDef);
/*      */ 
/*      */     
/* 3086 */     if (result == null) {
/* 3087 */       result = new LiteralSimpleType(typeDef.getName(), javaType, true);
/*      */ 
/*      */ 
/*      */       
/* 3091 */       result.setSchemaTypeRef(typeDef.getName());
/* 3092 */       this._nillableSimpleTypeComponentToLiteralTypeMap.put(typeDef, result);
/*      */     } 
/*      */     
/* 3095 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processSuperType(SOAPStructureType parentType, SOAPStructureType structureType, JavaStructureType javaStructureType) {
/* 3109 */     for (Iterator<SOAPStructureMember> iterator = parentType.getMembers(); iterator.hasNext(); ) {
/* 3110 */       SOAPStructureMember member = iterator.next();
/* 3111 */       SOAPStructureMember inheritedMember = new SOAPStructureMember(member.getName(), member.getType());
/*      */       
/* 3113 */       inheritedMember.setInherited(true);
/* 3114 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 3115 */       JavaStructureMember inheritedJavaMember = new JavaStructureMember(javaMember.getName(), javaMember.getType(), inheritedMember, javaMember.isPublic());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3121 */       inheritedJavaMember.setInherited(true);
/* 3122 */       inheritedJavaMember.setReadMethod(javaMember.getReadMethod());
/* 3123 */       inheritedJavaMember.setWriteMethod(javaMember.getWriteMethod());
/* 3124 */       inheritedMember.setJavaStructureMember(inheritedJavaMember);
/* 3125 */       structureType.add(inheritedMember);
/* 3126 */       javaStructureType.add(inheritedJavaMember);
/*      */     } 
/*      */ 
/*      */     
/* 3130 */     Iterator<SOAPAttributeMember> iter = parentType.getAttributeMembers();
/* 3131 */     while (iter.hasNext()) {
/*      */       
/* 3133 */       SOAPAttributeMember member = iter.next();
/* 3134 */       SOAPAttributeMember inheritedMember = new SOAPAttributeMember(member.getName(), member.getType());
/*      */       
/* 3136 */       inheritedMember.setRequired(member.isRequired());
/* 3137 */       inheritedMember.setInherited(true);
/* 3138 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 3139 */       JavaStructureMember inheritedJavaMember = new JavaStructureMember(javaMember.getName(), javaMember.getType(), inheritedMember, javaMember.isPublic());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3145 */       inheritedJavaMember.setInherited(true);
/* 3146 */       inheritedJavaMember.setReadMethod(javaMember.getReadMethod());
/* 3147 */       inheritedJavaMember.setWriteMethod(javaMember.getWriteMethod());
/* 3148 */       inheritedMember.setJavaStructureMember(inheritedJavaMember);
/* 3149 */       structureType.add(inheritedMember);
/* 3150 */       javaStructureType.add(inheritedJavaMember);
/*      */     } 
/* 3152 */     parentType.addSubtype(structureType);
/* 3153 */     ((JavaStructureType)parentType.getJavaType()).addSubclass(javaStructureType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processSuperType(LiteralStructuredType parentType, LiteralStructuredType structureType, JavaStructureType javaStructureType) {
/* 3166 */     if (parentType != null) {
/*      */       
/* 3168 */       Iterator<LiteralElementMember> iterator = parentType.getElementMembers();
/* 3169 */       while (iterator.hasNext()) {
/*      */         
/* 3171 */         LiteralElementMember inheritedMember, member = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3176 */         if (member.isWildcard() && member instanceof LiteralWildcardMember) {
/* 3177 */           LiteralWildcardMember literalWildcardMember = new LiteralWildcardMember(member.getType());
/* 3178 */           literalWildcardMember.setExcludedNamespaceName(((LiteralWildcardMember)member).getExcludedNamespaceName());
/*      */         } else {
/*      */           
/* 3181 */           inheritedMember = new LiteralElementMember(member.getName(), member.getType());
/*      */         } 
/*      */         
/* 3184 */         inheritedMember.setNillable(member.isNillable());
/* 3185 */         inheritedMember.setInherited(true);
/* 3186 */         inheritedMember.setRepeated(member.isRepeated());
/* 3187 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 3189 */         JavaStructureMember inheritedJavaMember = new JavaStructureMember(javaMember.getName(), javaMember.getType(), inheritedMember, javaMember.isPublic());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3195 */         inheritedJavaMember.setInherited(true);
/* 3196 */         inheritedJavaMember.setReadMethod(javaMember.getReadMethod());
/* 3197 */         inheritedJavaMember.setWriteMethod(javaMember.getWriteMethod());
/* 3198 */         inheritedMember.setJavaStructureMember(inheritedJavaMember);
/* 3199 */         structureType.add(inheritedMember);
/* 3200 */         javaStructureType.add(inheritedJavaMember);
/*      */       } 
/*      */ 
/*      */       
/* 3204 */       Iterator<LiteralAttributeMember> iter = parentType.getAttributeMembers();
/* 3205 */       while (iter.hasNext()) {
/*      */         
/* 3207 */         LiteralAttributeMember member = iter.next();
/*      */         
/* 3209 */         LiteralAttributeMember inheritedMember = new LiteralAttributeMember(member.getName(), member.getType());
/*      */ 
/*      */ 
/*      */         
/* 3213 */         inheritedMember.setRequired(member.isRequired());
/* 3214 */         inheritedMember.setInherited(true);
/* 3215 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 3217 */         JavaStructureMember inheritedJavaMember = new JavaStructureMember(javaMember.getName(), javaMember.getType(), inheritedMember, javaMember.isPublic());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3223 */         inheritedJavaMember.setInherited(true);
/* 3224 */         inheritedJavaMember.setReadMethod(javaMember.getReadMethod());
/* 3225 */         inheritedJavaMember.setWriteMethod(javaMember.getWriteMethod());
/* 3226 */         inheritedMember.setJavaStructureMember(inheritedJavaMember);
/* 3227 */         structureType.add(inheritedMember);
/* 3228 */         javaStructureType.add(inheritedJavaMember);
/*      */       } 
/*      */       
/* 3231 */       parentType.addSubtype(structureType);
/* 3232 */       ((JavaStructureType)parentType.getJavaType()).addSubclass(javaStructureType);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doMakeMemberBoxedType() {
/* 3241 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWeHandleWildcard() {
/* 3248 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isParticleOptional(ParticleComponent memberParticle) {
/* 3256 */     return memberParticle.occursZeroOrOne();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isAttributeEnumeration(LiteralType attributeType) {
/* 3263 */     return attributeType instanceof LiteralEnumerationType;
/*      */   }
/*      */   
/*      */   protected boolean isAttributeEnumeration(SOAPType attributeType) {
/* 3267 */     return attributeType instanceof SOAPEnumerationType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isAttributeOptional(AttributeUseComponent attributeUse) {
/* 3275 */     return (!attributeUse.isRequired() && attributeUse.getValueKind() != Symbol.DEFAULT && attributeUse.getValueKind() != Symbol.FIXED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType complexSchemaTypeSimpleContentExtensionToLiteralType(ComplexTypeDefinitionComponent component, QName nameHint) {
/* 3287 */     LiteralType baseType = schemaTypeToLiteralType(component.getBaseTypeDefinition(), nameHint);
/*      */ 
/*      */ 
/*      */     
/* 3291 */     if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)baseType) == null)
/*      */     {
/* 3293 */       return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */     }
/*      */     
/* 3296 */     LiteralSequenceType literalSequenceType = new LiteralSequenceType(getUniqueQNameFor((TypeDefinitionComponent)component, nameHint));
/*      */ 
/*      */     
/* 3299 */     JavaStructureType javaStructureType = new JavaStructureType(makePackageQualified(this._env.getNames().validJavaClassName(literalSequenceType.getName().getLocalPart()), literalSequenceType.getName()), false, literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3307 */     resolveNamingConflictsFor((JavaType)javaStructureType);
/* 3308 */     literalSequenceType.setJavaType((JavaType)javaStructureType);
/* 3309 */     this._componentToLiteralTypeMap.put(component, literalSequenceType);
/*      */ 
/*      */     
/* 3312 */     for (Iterator<AttributeUseComponent> iter = component.attributeUses(); iter.hasNext(); ) {
/* 3313 */       AttributeUseComponent attributeUse = iter.next();
/*      */       
/* 3315 */       AttributeDeclarationComponent attributeDeclaration = attributeUse.getAttributeDeclaration();
/*      */ 
/*      */       
/* 3318 */       LiteralType attributeType = schemaTypeToLiteralType((TypeDefinitionComponent)attributeDeclaration.getTypeDefinition(), getAttributeQNameHint(attributeDeclaration, nameHint));
/*      */ 
/*      */ 
/*      */       
/* 3322 */       if (SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)attributeType) == null && !(attributeType instanceof LiteralEnumerationType))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 3327 */         return (LiteralType)getLiteralFragmentTypeFor((TypeDefinitionComponent)component, nameHint);
/*      */       }
/*      */       
/* 3330 */       if (attributeDeclaration.getName().getLocalPart().equals("_value"))
/*      */       {
/*      */ 
/*      */         
/* 3334 */         throw new ModelException("model.complexType.simpleContent.reservedName", literalSequenceType.getName().getLocalPart());
/*      */       }
/*      */ 
/*      */       
/* 3338 */       LiteralAttributeMember literalAttributeMember = new LiteralAttributeMember(attributeDeclaration.getName(), attributeType);
/*      */ 
/*      */ 
/*      */       
/* 3342 */       if (attributeUse.isRequired()) {
/* 3343 */         literalAttributeMember.setRequired(true);
/*      */       }
/* 3345 */       JavaStructureMember javaStructureMember = new JavaStructureMember(this._env.getNames().validJavaMemberName(attributeDeclaration.getName().getLocalPart()), attributeType.getJavaType(), literalAttributeMember, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3352 */       javaStructureMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaStructureMember));
/*      */       
/* 3354 */       javaStructureMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaStructureMember));
/*      */       
/* 3356 */       literalAttributeMember.setJavaStructureMember(javaStructureMember);
/* 3357 */       javaStructureType.add(javaStructureMember);
/* 3358 */       literalSequenceType.add(literalAttributeMember);
/*      */     } 
/*      */ 
/*      */     
/* 3362 */     LiteralContentMember member = new LiteralContentMember(baseType);
/* 3363 */     JavaStructureMember javaMember = new JavaStructureMember("_value", baseType.getJavaType(), member, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3369 */     javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */     
/* 3371 */     javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */     
/* 3373 */     member.setJavaStructureMember(javaMember);
/* 3374 */     javaStructureType.add(javaMember);
/* 3375 */     literalSequenceType.setContentMember(member);
/*      */     
/* 3377 */     return (LiteralType)literalSequenceType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralFragmentType getLiteralFragmentTypeFor(TypeDefinitionComponent component, QName nameHint) {
/* 3383 */     LiteralFragmentType literalType = new LiteralFragmentType();
/* 3384 */     literalType.setName(getUniqueQNameFor(component, nameHint));
/*      */     
/* 3386 */     literalType.setJavaType((JavaType)this.javaTypes.SOAPELEMENT_JAVATYPE);
/*      */     
/* 3388 */     literalType.setNillable(true);
/* 3389 */     return literalType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType getLiteralSimpleStringTypeFor(TypeDefinitionComponent component, QName nameHint) {
/* 3395 */     LiteralSimpleType literalType = new LiteralSimpleType(getUniqueQNameFor(component, nameHint));
/*      */ 
/*      */     
/* 3398 */     literalType.setJavaType((JavaType)this.javaTypes.STRING_JAVATYPE);
/* 3399 */     return (LiteralType)literalType;
/*      */   }
/*      */   
/*      */   protected String makePackageQualified(String s, QName name) {
/* 3403 */     String javaPackageName = getJavaPackageName(name);
/* 3404 */     if (javaPackageName != null)
/* 3405 */       return javaPackageName + "." + s; 
/* 3406 */     if (this._modelInfo.getJavaPackageName() != null && !this._modelInfo.getJavaPackageName().equals(""))
/*      */     {
/*      */       
/* 3409 */       return this._modelInfo.getJavaPackageName() + "." + s;
/*      */     }
/* 3411 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   protected QName makePackageQualified(QName name) {
/* 3416 */     return new QName(name.getNamespaceURI(), makePackageQualified(name.getLocalPart(), name));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPCustomType getCustomTypeFor(TypeDefinitionComponent component) {
/* 3422 */     QName typeName = component.getName();
/* 3423 */     if (typeName == null)
/*      */     {
/* 3425 */       return null;
/*      */     }
/*      */     
/* 3428 */     SOAPCustomType customType = (SOAPCustomType)this._typeNameToCustomSOAPTypeMap.get(typeName);
/*      */     
/* 3430 */     if (customType == null && 
/* 3431 */       this._modelInfo.getTypeMappingRegistry() != null) {
/* 3432 */       TypeMappingInfo tmi = this._modelInfo.getTypeMappingRegistry().getTypeMappingInfo(soap11NamespaceConstants.getEncoding(), typeName);
/*      */ 
/*      */ 
/*      */       
/* 3436 */       if (tmi != null) {
/* 3437 */         customType = new SOAPCustomType(typeName);
/*      */         
/* 3439 */         JavaCustomType javaCustomType = new JavaCustomType(tmi.getJavaTypeName(), tmi);
/*      */         
/* 3441 */         customType.setJavaType((JavaType)javaCustomType);
/* 3442 */         this._typeNameToCustomSOAPTypeMap.put(typeName, customType);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 3447 */     return customType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected LiteralType getIDREFLiteralType(SimpleTypeDefinitionComponent component, QName nameHint) {
/* 3453 */     LiteralIDType baseType = new LiteralIDType(component.getName());
/* 3454 */     baseType.setSchemaTypeRef(component.getName());
/*      */     
/* 3456 */     JavaSimpleType javaType = (JavaSimpleType)this._builtinSchemaTypeToJavaTypeMap.get(component.getName());
/*      */ 
/*      */ 
/*      */     
/* 3460 */     baseType.setJavaType((JavaType)javaType);
/* 3461 */     baseType.setResolveIDREF(this._resolveIDREF);
/*      */     
/* 3463 */     if (!this._resolveIDREF) {
/* 3464 */       return (LiteralType)baseType;
/*      */     }
/* 3466 */     LiteralSequenceType literalSequenceType = new LiteralSequenceType(getUniqueQNameFor((TypeDefinitionComponent)new SimpleTypeDefinitionComponent(), nameHint));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3472 */     LiteralContentMember member = new LiteralContentMember((LiteralType)baseType);
/* 3473 */     JavaStructureMember javaMember = new JavaStructureMember("_value", baseType.getJavaType(), member, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3480 */     JavaStructureType javaStructureType = new JavaStructureType(makePackageQualified(this._env.getNames().validJavaClassName(literalSequenceType.getName().getLocalPart()), literalSequenceType.getName()), false, literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3489 */     resolveNamingConflictsFor((JavaType)javaStructureType);
/* 3490 */     literalSequenceType.setJavaType((JavaType)javaStructureType);
/* 3491 */     this._componentToLiteralTypeMap.put(component, literalSequenceType);
/*      */     
/* 3493 */     javaMember.setReadMethod(this._env.getNames().getJavaMemberReadMethod(javaMember));
/*      */     
/* 3495 */     javaMember.setWriteMethod(this._env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */     
/* 3497 */     member.setJavaStructureMember(javaMember);
/* 3498 */     javaStructureType.add(javaMember);
/* 3499 */     literalSequenceType.setContentMember(member);
/*      */     
/* 3501 */     return (LiteralType)literalSequenceType;
/*      */   }
/*      */   
/*      */   protected SOAPCustomType mustGetCustomTypeFor(TypeDefinitionComponent component) {
/* 3505 */     SOAPCustomType type = getCustomTypeFor(component);
/* 3506 */     if (type == null)
/*      */     {
/* 3508 */       fail("model.schema.unsupportedSchemaType", new Object[] { component.getName() });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3513 */     return type;
/*      */   }
/*      */   
/*      */   protected boolean isInvalidEnumerationLabel(String s) {
/* 3517 */     if (s == null || s.equals("") || !Character.isJavaIdentifierStart(s.charAt(0)))
/*      */     {
/*      */       
/* 3520 */       return true;
/*      */     }
/*      */     
/* 3523 */     for (int i = 1; i < s.length(); i++) {
/* 3524 */       if (!Character.isJavaIdentifierPart(s.charAt(i))) {
/* 3525 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3529 */     return this._env.getNames().isJavaReservedWord(s);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateModifiers(JavaStructureType javaStructureType) {}
/*      */ 
/*      */   
/*      */   private String getJavaPackageName(QName name) {
/* 3537 */     String packageName = null;
/* 3538 */     if (this._modelInfo.getNamespaceMappingRegistry() != null) {
/* 3539 */       NamespaceMappingInfo i = this._modelInfo.getNamespaceMappingRegistry().getNamespaceMappingInfo(name);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3544 */       if (i != null)
/* 3545 */         return i.getJavaPackageName(); 
/*      */     } 
/* 3547 */     return packageName;
/*      */   }
/*      */   
/*      */   protected void fail(String key, String code, QName arg) {
/* 3551 */     if (arg == null) {
/* 3552 */       throw new ModelException(key + ".anonymous", code);
/*      */     }
/* 3554 */     throw new ModelException(key, new Object[] { code, arg.getLocalPart(), arg.getNamespaceURI() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void fail(String key, Object[] arg) {
/* 3564 */     throw new ModelException(key, arg);
/*      */   }
/*      */   
/*      */   protected void failUnsupported(String code, QName arg) {
/* 3568 */     fail("model.schema.unsupportedType", code, arg);
/*      */   }
/*      */   
/*      */   protected void warn(String key, Object[] args) {
/* 3572 */     this._env.warn(this._messageFactory.getMessage(key, args));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getElementQNameHint(ElementDeclarationComponent component, QName nameHint) {
/* 3578 */     QName componentName = component.getName();
/* 3579 */     if (!componentName.getNamespaceURI().equals("")) {
/* 3580 */       return componentName;
/*      */     }
/* 3582 */     return new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "-" + componentName.getLocalPart());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getElementMappingNameHint(ElementDeclarationComponent component, QName mappingNameHint) {
/* 3592 */     String hint = (mappingNameHint == null) ? "" : (mappingNameHint.getLocalPart() + ">" + component.getName().getLocalPart());
/*      */ 
/*      */     
/* 3595 */     if (component.getTypeDefinition().getName() == null) {
/* 3596 */       hint = ">" + hint;
/*      */     }
/* 3598 */     QName qnameHint = new QName(component.getName().getNamespaceURI(), hint);
/*      */     
/* 3600 */     return qnameHint;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJ2EEAnonymousArrayTypeName(ParticleComponent memberParticle, ElementDeclarationComponent element, QName mappingNameHint) {
/* 3608 */     String upperBound = memberParticle.isMaxOccursUnbounded() ? "unbounded" : String.valueOf(memberParticle.getMaxOccurs());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3613 */     String lowerBound = String.valueOf(memberParticle.getMinOccurs());
/*      */ 
/*      */ 
/*      */     
/* 3617 */     String name = (mappingNameHint == null) ? "" : (mappingNameHint.getLocalPart() + ">" + element.getName().getLocalPart());
/* 3618 */     String boundary = "[" + lowerBound + "," + upperBound + "]";
/*      */     
/* 3620 */     return name + boundary;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getAttributeQNameHint(AttributeDeclarationComponent component, QName nameHint) {
/* 3626 */     return new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "-" + component.getName().getLocalPart());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getUniqueLiteralArrayTypeQNameFor(QName subTypeName, QName nameHint) {
/* 3634 */     return new QName(subTypeName.getNamespaceURI(), subTypeName.getLocalPart() + "-Array-" + getUniqueID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getUniqueTypeNameForElement(QName elementName) {
/* 3641 */     return new QName(elementName.getNamespaceURI(), elementName.getLocalPart() + "-AnonType-" + getUniqueID());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getUniqueNCNameFor(TypeDefinitionComponent component) {
/* 3647 */     if (component.getName() != null) {
/* 3648 */       return component.getName().getLocalPart();
/*      */     }
/* 3650 */     return "genType-" + getUniqueID();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QName getUniqueQNameFor(TypeDefinitionComponent component, QName nameHint) {
/* 3657 */     if (component.getName() != null) {
/* 3658 */       return component.getName();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3663 */     QName result = null;
/*      */     
/* 3665 */     if (nameHint != null) {
/* 3666 */       if (!this._namePool.contains(nameHint)) {
/* 3667 */         result = nameHint;
/*      */       } else {
/* 3669 */         result = new QName(nameHint.getNamespaceURI(), nameHint.getLocalPart() + "-gen-" + getUniqueID());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3675 */     this._namePool.add(result);
/* 3676 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getUniqueID() {
/* 3681 */     return this._nextUniqueID++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getUniqueMemberName(JavaStructureType javaStructureType, String prefix) {
/* 3687 */     JavaStructureMember member = javaStructureType.getMemberByName(prefix);
/* 3688 */     if (member == null) {
/* 3689 */       return prefix;
/*      */     }
/*      */     
/* 3692 */     int i = 2;
/*      */     
/*      */     while (true) {
/* 3695 */       String name = prefix + Integer.toString(i);
/* 3696 */       if (javaStructureType.getMemberByName(name) == null)
/* 3697 */         return name; 
/*      */     } 
/*      */   }
/*      */   protected void resolveNamingConflictsFor(JavaType javaType) {
/* 3701 */     resolveNamingConflictsFor(javaType, "_Type");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resolveEnumerationNamingConflictsFor(JavaEnumerationType javaEnumType) {
/* 3708 */     String enumSuffix = "_Enumeration";
/* 3709 */     if (!this._strictCompliance) {
/* 3710 */       if (this.enumerationNames == null)
/* 3711 */         this.enumerationNames = new HashMap<Object, Object>(); 
/* 3712 */       if (this.enumerationNames.containsKey(javaEnumType.getName())) {
/* 3713 */         String originalName = javaEnumType.getName();
/* 3714 */         Integer occur = (Integer)this.enumerationNames.get(javaEnumType.getName());
/* 3715 */         String suffix = enumSuffix;
/* 3716 */         for (int i = 0; i < occur.intValue(); i++) {
/* 3717 */           suffix = suffix + enumSuffix;
/*      */         }
/* 3719 */         javaEnumType.doSetName(javaEnumType.getName() + suffix);
/* 3720 */         this.enumerationNames.put(originalName, new Integer(occur.intValue() + 1));
/*      */       } else {
/* 3722 */         this.enumerationNames.put(javaEnumType.getName(), new Integer(0));
/*      */       } 
/*      */     } 
/* 3725 */     resolveNamingConflictsFor((JavaType)javaEnumType, enumSuffix);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resolveNamingConflictsFor(JavaType javaType, String suffix) {
/* 3731 */     if (this._conflictingClassNames != null && this._conflictingClassNames.contains(javaType.getName()))
/*      */     {
/* 3733 */       if (javaType.getName().equals(javaType.getRealName()))
/*      */       {
/* 3735 */         javaType.doSetName(javaType.getName() + suffix);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void setReferenceable(SOAPSimpleType simpleType) {
/* 3741 */     boolean referenceable = true;
/* 3742 */     QName name = simpleType.getName();
/* 3743 */     if (name.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema") && !name.equals(BuiltInTypes.STRING) && !name.equals(BuiltInTypes.BASE64_BINARY) && !name.equals(BuiltInTypes.HEX_BINARY))
/*      */     {
/*      */ 
/*      */       
/* 3747 */       referenceable = false;
/*      */     }
/* 3749 */     simpleType.setReferenceable(referenceable);
/*      */   }
/*      */   
/*      */   protected boolean checkStrictCompliance(QName typeName) {
/* 3753 */     boolean ret = true;
/*      */     
/* 3755 */     if (this._strictCompliance && typeName != null && (typeName.equals(SchemaConstants.QNAME_TYPE_IDREF) || typeName.equals(SchemaConstants.QNAME_TYPE_URTYPE))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3765 */       fail("model.schema.unsupportedSchemaType", new Object[] { typeName });
/*      */ 
/*      */       
/* 3768 */       return false;
/*      */     } 
/* 3770 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SchemaJavaMemberInfo
/*      */   {
/*      */     public boolean isDataMember;
/*      */     
/*      */     public String javaMemberName;
/*      */ 
/*      */     
/*      */     public SchemaJavaMemberInfo() {}
/*      */     
/*      */     public SchemaJavaMemberInfo(String javaMemberName, boolean isDataMember) {
/* 3784 */       this.javaMemberName = javaMemberName;
/* 3785 */       this.isDataMember = isDataMember;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 3809 */   private static SOAPNamespaceConstants soap11NamespaceConstants = null;
/* 3810 */   protected static SOAPWSDLConstants soap11WSDLConstants = null;
/*      */   
/* 3812 */   private static SOAPNamespaceConstants soap12NamespaceConstants = null;
/* 3813 */   private static SOAPWSDLConstants soap12WSDLConstants = null;
/*      */   private static final String VALUE_MEMBER_NAME = "_value";
/*      */   private static final String ANY_MEMBER_NAME_PREFIX = "_any";
/*      */   protected Map _builtinSchemaTypeToJavaTypeMap;
/*      */   protected Map _builtinSchemaTypeToJavaWrapperTypeMap;
/*      */   protected Map _simpleTypeEncoderMap;
/*      */   private Map enumerationNames;
/*      */   
/*      */   protected abstract void initializeMaps();
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\wsdl\SchemaAnalyzerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */