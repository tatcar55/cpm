/*      */ package com.sun.xml.rpc.processor.schema;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.model.ModelException;
/*      */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaEntity;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class InternalSchemaBuilderBase
/*      */ {
/*      */   private boolean _noDataBinding = false;
/*      */   private AbstractDocument _document;
/*      */   private InternalSchema _schema;
/*      */   private Map _wellKnownTypes;
/*      */   private Map _wellKnownAttributes;
/*      */   private Map _wellKnownAttributeGroups;
/*      */   private Map _wellKnownElements;
/*      */   private ComplexTypeDefinitionComponent _urType;
/*      */   private SimpleTypeDefinitionComponent _simpleUrType;
/*      */   private Map _namedTypeComponentsBeingDefined;
/*      */   private static final Set _primitiveTypeNames;
/*      */   private static final Set _soapTypeNames;
/*      */   
/*      */   public InternalSchemaBuilderBase(AbstractDocument document, Properties options) {
/*   63 */     this._document = document;
/*   64 */     this._schema = new InternalSchema(this);
/*   65 */     createWellKnownTypes();
/*   66 */     createWellKnownAttributes();
/*   67 */     createWellKnownAttributeGroups();
/*   68 */     createWellKnownElements();
/*   69 */     this._noDataBinding = Boolean.valueOf(options.getProperty("noDataBinding")).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InternalSchema getSchema() {
/*   78 */     return this._schema;
/*      */   }
/*      */   
/*      */   public TypeDefinitionComponent buildTypeDefinition(QName name) {
/*   82 */     boolean createdTypeComponentMap = false;
/*   83 */     if (this._namedTypeComponentsBeingDefined == null) {
/*   84 */       this._namedTypeComponentsBeingDefined = new HashMap<Object, Object>();
/*   85 */       createdTypeComponentMap = true;
/*      */     } 
/*      */     
/*      */     try {
/*   89 */       TypeDefinitionComponent component = (TypeDefinitionComponent)this._wellKnownTypes.get(name);
/*      */       
/*   91 */       if (component != null) {
/*   92 */         return component;
/*      */       }
/*      */       
/*   95 */       SchemaEntity entity = (SchemaEntity)this._document.find(SchemaKinds.XSD_TYPE, name);
/*      */       
/*   97 */       SchemaElement element = entity.getElement();
/*   98 */       component = buildTopLevelTypeDefinition(element, this._schema);
/*   99 */       this._schema.add(component);
/*      */       
/*  101 */       return component;
/*  102 */     } catch (ValidationException e) {
/*  103 */       throw new ModelException(e);
/*      */     } finally {
/*  105 */       if (createdTypeComponentMap) {
/*  106 */         this._namedTypeComponentsBeingDefined = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public AttributeDeclarationComponent buildAttributeDeclaration(QName name) {
/*      */     try {
/*  113 */       AttributeDeclarationComponent component = (AttributeDeclarationComponent)this._wellKnownAttributes.get(name);
/*      */       
/*  115 */       if (component != null) {
/*  116 */         return component;
/*      */       }
/*      */       
/*  119 */       SchemaEntity entity = (SchemaEntity)this._document.find(SchemaKinds.XSD_ATTRIBUTE, name);
/*      */       
/*  121 */       SchemaElement element = entity.getElement();
/*  122 */       component = buildTopLevelAttributeDeclaration(element, this._schema);
/*  123 */       this._schema.add(component);
/*      */       
/*  125 */       return component;
/*  126 */     } catch (ValidationException e) {
/*  127 */       throw new ModelException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public ElementDeclarationComponent buildElementDeclaration(QName name) {
/*      */     try {
/*  133 */       ElementDeclarationComponent component = (ElementDeclarationComponent)this._wellKnownElements.get(name);
/*      */       
/*  135 */       if (component != null) {
/*  136 */         return component;
/*      */       }
/*      */       
/*  139 */       SchemaEntity entity = (SchemaEntity)this._document.find(SchemaKinds.XSD_ELEMENT, name);
/*      */       
/*  141 */       SchemaElement element = entity.getElement();
/*  142 */       component = buildTopLevelElementDeclaration(element, this._schema);
/*  143 */       return component;
/*  144 */     } catch (ValidationException e) {
/*  145 */       throw new ModelException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeGroupDefinitionComponent buildAttributeGroupDefinition(QName name) {
/*      */     try {
/*  153 */       AttributeGroupDefinitionComponent component = (AttributeGroupDefinitionComponent)this._wellKnownAttributeGroups.get(name);
/*      */ 
/*      */       
/*  156 */       if (component != null) {
/*  157 */         return component;
/*      */       }
/*      */       
/*  160 */       SchemaEntity entity = (SchemaEntity)this._document.find(SchemaKinds.XSD_ATTRIBUTE_GROUP, name);
/*      */       
/*  162 */       SchemaElement element = entity.getElement();
/*  163 */       component = buildTopLevelAttributeGroupDefinition(element, this._schema);
/*  164 */       this._schema.add(component);
/*      */       
/*  166 */       return component;
/*  167 */     } catch (ValidationException e) {
/*  168 */       throw new ModelException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ModelGroupDefinitionComponent buildModelGroupDefinition(QName name) {
/*  175 */     failUnimplemented("F002");
/*  176 */     return null;
/*      */   }
/*      */   
/*      */   public ComplexTypeDefinitionComponent getUrType() {
/*  180 */     return this._urType;
/*      */   }
/*      */   
/*      */   public SimpleTypeDefinitionComponent getSimpleUrType() {
/*  184 */     return this._simpleUrType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ElementDeclarationComponent buildTopLevelElementDeclaration(SchemaElement element, InternalSchema schema) {
/*  190 */     ElementDeclarationComponent component = new ElementDeclarationComponent();
/*      */ 
/*      */ 
/*      */     
/*  194 */     String nameAttr = element.getValueOfMandatoryAttribute("name");
/*      */     
/*  196 */     component.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */ 
/*      */ 
/*      */     
/*  200 */     this._schema.add(component);
/*      */ 
/*      */     
/*  203 */     internalBuildElementDeclaration(component, element, schema);
/*  204 */     if (element.getValueOfAttributeOrNull("minOccurs") != null)
/*      */     {
/*      */       
/*  207 */       failValidation("validation.invalidAttribute", "minOccurs", element.getLocalName());
/*      */     }
/*      */ 
/*      */     
/*  211 */     if (element.getValueOfAttributeOrNull("maxOccurs") != null)
/*      */     {
/*      */       
/*  214 */       failValidation("validation.invalidAttribute", "maxOccurs", element.getLocalName());
/*      */     }
/*      */     
/*  217 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AttributeGroupDefinitionComponent buildTopLevelAttributeGroupDefinition(SchemaElement element, InternalSchema schema) {
/*  225 */     AttributeGroupDefinitionComponent component = new AttributeGroupDefinitionComponent();
/*      */ 
/*      */ 
/*      */     
/*  229 */     String nameAttr = element.getValueOfMandatoryAttribute("name");
/*      */     
/*  231 */     component.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */ 
/*      */ 
/*      */     
/*  235 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  236 */       SchemaElement child = iter.next();
/*  237 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/*  238 */         component.addAttributeUse(buildAttributeUse(child, null, schema)); continue;
/*      */       } 
/*  240 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*      */ 
/*      */         
/*  243 */         String refAttr = child.getValueOfMandatoryAttribute("ref");
/*      */         
/*  245 */         component.addAttributeGroup(schema.findAttributeGroupDefinition(child.asQName(refAttr))); continue;
/*      */       } 
/*  247 */       if (child.getQName().equals(SchemaConstants.QNAME_ANY_ATTRIBUTE)) {
/*      */ 
/*      */         
/*  250 */         failUnimplemented("F003"); continue;
/*  251 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  257 */       failValidation("validation.invalidElement", child.getLocalName());
/*      */     } 
/*      */ 
/*      */     
/*  261 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AttributeDeclarationComponent buildTopLevelAttributeDeclaration(SchemaElement element, InternalSchema schema) {
/*  268 */     AttributeDeclarationComponent component = new AttributeDeclarationComponent();
/*      */ 
/*      */ 
/*      */     
/*  272 */     String nameAttr = element.getValueOfMandatoryAttribute("name");
/*      */ 
/*      */     
/*  275 */     component.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */ 
/*      */ 
/*      */     
/*  279 */     component.setScope(null);
/*      */ 
/*      */     
/*  282 */     boolean foundType = false;
/*  283 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  284 */       SchemaElement child = iter.next();
/*  285 */       if (child.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
/*  286 */         if (foundType) {
/*  287 */           failValidation("validation.invalidElement", element.getLocalName());
/*      */         }
/*      */         
/*  290 */         component.setTypeDefinition(buildSimpleTypeDefinition(child, schema));
/*      */         
/*  292 */         foundType = true; continue;
/*  293 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  299 */       failValidation("validation.invalidElement", child.getLocalName());
/*      */     } 
/*      */ 
/*      */     
/*  303 */     if (foundType) {
/*  304 */       assertNoAttribute(element, "type");
/*      */     } else {
/*  306 */       String typeAttr = element.getValueOfAttributeOrNull("type");
/*      */       
/*  308 */       if (typeAttr == null) {
/*  309 */         component.setTypeDefinition(getSimpleUrType());
/*      */       } else {
/*  311 */         TypeDefinitionComponent typeComponent = schema.findTypeDefinition(element.asQName(typeAttr));
/*      */         
/*  313 */         if (typeComponent instanceof SimpleTypeDefinitionComponent) {
/*  314 */           component.setTypeDefinition((SimpleTypeDefinitionComponent)typeComponent);
/*      */         } else {
/*      */           
/*  317 */           failValidation("validation.notSimpleType", component.getName().getLocalPart());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  324 */     component.setAnnotation(buildNonSchemaAttributesAnnotation(element));
/*      */ 
/*      */ 
/*      */     
/*  328 */     String defaultAttr = element.getValueOfAttributeOrNull("default");
/*      */     
/*  330 */     String fixedAttr = element.getValueOfAttributeOrNull("fixed");
/*      */     
/*  332 */     if (defaultAttr != null && fixedAttr != null) {
/*  333 */       fail("validation.exclusiveAttributes", "default", "fixed");
/*      */     }
/*      */     
/*  336 */     if (defaultAttr != null) {
/*  337 */       component.setValue(defaultAttr);
/*  338 */       component.setValueKind(Symbol.DEFAULT);
/*      */     } 
/*  340 */     if (fixedAttr != null) {
/*  341 */       component.setValue(defaultAttr);
/*  342 */       component.setValueKind(Symbol.FIXED);
/*      */     } 
/*      */     
/*  345 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processElementParticle(SchemaElement element, ParticleComponent component, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/*  354 */     component.setTermTag(3);
/*      */ 
/*      */     
/*  357 */     ElementDeclarationComponent term = new ElementDeclarationComponent();
/*      */ 
/*      */ 
/*      */     
/*  361 */     internalBuildElementDeclaration(term, element, schema);
/*      */     
/*  363 */     String refAttr = element.getValueOfAttributeOrNull("ref");
/*  364 */     if (refAttr != null) {
/*      */ 
/*      */       
/*  367 */       term = schema.findElementDeclaration(element.asQName(refAttr));
/*      */     }
/*      */     else {
/*      */       
/*  371 */       String nameAttr = element.getValueOfMandatoryAttribute("name");
/*      */       
/*  373 */       String formAttr = element.getValueOfAttributeOrNull("form");
/*      */       
/*  375 */       if (formAttr == null) {
/*  376 */         formAttr = element.getRoot().getValueOfAttributeOrNull("elementFormDefault");
/*      */         
/*  378 */         if (formAttr == null) {
/*  379 */           formAttr = "";
/*      */         }
/*      */       } 
/*  382 */       if (formAttr.equals("qualified")) {
/*  383 */         term.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */       } else {
/*      */         
/*  386 */         term.setName(new QName(nameAttr));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  391 */     term.setScope(scope);
/*      */     
/*  393 */     component.setTermTag(3);
/*  394 */     component.setElementTerm(term);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void internalBuildElementDeclaration(ElementDeclarationComponent component, SchemaElement element, InternalSchema schema) {
/*  400 */     boolean foundType = false;
/*  401 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  402 */       SchemaElement child = iter.next();
/*  403 */       if (child.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
/*  404 */         if (foundType) {
/*  405 */           failValidation("validation.invalidElement", element.getLocalName());
/*      */         }
/*      */         
/*  408 */         component.setTypeDefinition(buildSimpleTypeDefinition(child, schema));
/*      */         
/*  410 */         foundType = true; continue;
/*  411 */       }  if (child.getQName().equals(SchemaConstants.QNAME_COMPLEX_TYPE)) {
/*      */ 
/*      */         
/*  414 */         if (foundType) {
/*  415 */           failValidation("validation.invalidElement", element.getLocalName());
/*      */         }
/*      */         
/*  418 */         component.setTypeDefinition(buildComplexTypeDefinition(child, schema));
/*      */         
/*  420 */         foundType = true;
/*      */       } 
/*      */     } 
/*  423 */     if (foundType) {
/*  424 */       assertNoAttribute(element, "type");
/*  425 */       assertNoAttribute(element, "substitutionGroup");
/*      */     } else {
/*  427 */       String typeAttr = element.getValueOfAttributeOrNull("type");
/*      */       
/*  429 */       String str1 = element.getValueOfAttributeOrNull("substitutionGroup");
/*      */       
/*  431 */       if (typeAttr == null && str1 == null) {
/*  432 */         component.setTypeDefinition(getUrType());
/*      */       }
/*  434 */       else if (typeAttr != null && str1 != null && !this._noDataBinding) {
/*  435 */         failValidation("validation.exclusiveAttributes", "type", "substitutionGroup");
/*      */       }
/*  437 */       else if (typeAttr != null) {
/*  438 */         component.setTypeDefinition(schema.findTypeDefinition(element.asQName(typeAttr)));
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  443 */         failUnimplemented("F005");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  449 */     component.setNillable(element.getValueOfBooleanAttributeOrDefault("nillable", false));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     String defaultAttr = element.getValueOfAttributeOrNull("default");
/*      */     
/*  456 */     String fixedAttr = element.getValueOfAttributeOrNull("fixed");
/*      */     
/*  458 */     if (defaultAttr != null && fixedAttr != null) {
/*  459 */       fail("validation.exclusiveAttributes", "default", "fixed");
/*      */     }
/*      */     
/*  462 */     if (defaultAttr != null) {
/*  463 */       component.setValue(defaultAttr);
/*  464 */       component.setValueKind(Symbol.DEFAULT);
/*  465 */       if (component.getTypeDefinition() instanceof ComplexTypeDefinitionComponent)
/*      */       {
/*      */         
/*  468 */         failValidation("validation.notSimpleType", component.getName().getLocalPart());
/*      */       }
/*      */     } 
/*      */     
/*  472 */     if (fixedAttr != null) {
/*  473 */       component.setValue(defaultAttr);
/*  474 */       component.setValueKind(Symbol.FIXED);
/*  475 */       if (component.getTypeDefinition() instanceof ComplexTypeDefinitionComponent)
/*      */       {
/*      */         
/*  478 */         failValidation("validation.notSimpleType", component.getName().getLocalPart());
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  484 */     for (Iterator<SchemaElement> iterator1 = element.children(); iterator1.hasNext(); ) {
/*  485 */       SchemaElement child = iterator1.next();
/*  486 */       if (child.getQName().equals(SchemaConstants.QNAME_KEY) || child.getQName().equals(SchemaConstants.QNAME_KEYREF) || child.getQName().equals(SchemaConstants.QNAME_UNIQUE))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  491 */         failUnimplemented("F006");
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  496 */     QName substitutionGroupAttr = element.getValueOfQNameAttributeOrNull("substitutionGroup");
/*      */     
/*  498 */     if (substitutionGroupAttr != null)
/*      */     {
/*      */       
/*  501 */       failUnimplemented("F007");
/*      */     }
/*      */ 
/*      */     
/*  505 */     String blockAttr = element.getValueOfAttributeOrNull("block");
/*      */     
/*  507 */     if (blockAttr == null) {
/*  508 */       blockAttr = element.getRoot().getValueOfAttributeOrNull("blockDefault");
/*      */       
/*  510 */       if (blockAttr == null) {
/*  511 */         blockAttr = "";
/*      */       }
/*      */     } 
/*  514 */     if (blockAttr.equals("")) {
/*      */ 
/*      */       
/*  517 */       component.setDisallowedSubstitutions(_setEmpty);
/*  518 */     } else if (blockAttr.equals("#all")) {
/*  519 */       component.setDisallowedSubstitutions(_setExtResSub);
/*      */     } else {
/*  521 */       component.setDisallowedSubstitutions(parseSymbolSet(blockAttr, _setExtResSub));
/*      */ 
/*      */ 
/*      */       
/*  525 */       failUnimplemented("F008");
/*      */     } 
/*      */ 
/*      */     
/*  529 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*      */     
/*  531 */     if (finalAttr == null) {
/*  532 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*      */       
/*  534 */       if (finalAttr == null) {
/*  535 */         finalAttr = "";
/*      */       }
/*      */     } 
/*  538 */     if (finalAttr.equals("")) {
/*      */ 
/*      */       
/*  541 */       component.setSubstitutionsGroupExclusions(_setEmpty);
/*  542 */     } else if (finalAttr.equals("#all")) {
/*  543 */       component.setSubstitutionsGroupExclusions(_setExtRes);
/*      */     } else {
/*  545 */       component.setSubstitutionsGroupExclusions(parseSymbolSet(finalAttr, _setExtRes));
/*      */ 
/*      */ 
/*      */       
/*  549 */       failUnimplemented("F009");
/*      */     } 
/*      */ 
/*      */     
/*  553 */     component.setAbstract(element.getValueOfBooleanAttributeOrDefault("abstract", false));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected TypeDefinitionComponent buildTopLevelTypeDefinition(SchemaElement element, InternalSchema schema) {
/*  562 */     TypeDefinitionComponent component = null;
/*      */     
/*  564 */     if (element.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
/*  565 */       component = buildSimpleTypeDefinition(element, schema);
/*  566 */     } else if (element.getQName().equals(SchemaConstants.QNAME_COMPLEX_TYPE)) {
/*      */ 
/*      */       
/*  569 */       component = buildComplexTypeDefinition(element, schema);
/*      */     } else {
/*  571 */       failValidation("validation.invalidElement", element.getLocalName());
/*      */     } 
/*      */     
/*  574 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SimpleTypeDefinitionComponent buildSimpleTypeDefinition(SchemaElement element, InternalSchema schema) {
/*  582 */     SimpleTypeDefinitionComponent component = new SimpleTypeDefinitionComponent();
/*      */ 
/*      */ 
/*      */     
/*  586 */     String nameAttr = element.getValueOfAttributeOrNull("name");
/*      */ 
/*      */ 
/*      */     
/*  590 */     if (nameAttr != null && element.getParent().getQName().equals(SchemaConstants.QNAME_ELEMENT))
/*      */     {
/*  592 */       failValidation("validation.invalidSimpleTypeInElement", nameAttr, element.getParent().getValueOfAttributeOrNull("name"));
/*      */     }
/*      */ 
/*      */     
/*  596 */     if (nameAttr != null) {
/*  597 */       component.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */ 
/*      */ 
/*      */       
/*  601 */       this._namedTypeComponentsBeingDefined.put(component.getName(), component);
/*      */     } 
/*      */ 
/*      */     
/*  605 */     boolean gotOne = false;
/*  606 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  607 */       SchemaElement child = iter.next();
/*  608 */       if (gotOne)
/*      */       {
/*      */         
/*  611 */         failValidation("validation.invalidElement", child.getLocalName());
/*      */       }
/*      */ 
/*      */       
/*  615 */       if (child.getQName().equals(SchemaConstants.QNAME_RESTRICTION)) {
/*  616 */         buildRestrictionSimpleTypeDefinition(component, child, schema);
/*  617 */         gotOne = true; continue;
/*  618 */       }  if (child.getQName().equals(SchemaConstants.QNAME_LIST)) {
/*  619 */         buildListSimpleTypeDefinition(component, child, schema);
/*  620 */         gotOne = true; continue;
/*  621 */       }  if (child.getQName().equals(SchemaConstants.QNAME_UNION)) {
/*  622 */         failUnimplemented("F011"); continue;
/*  623 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  628 */       failValidation("validation.invalidElement", child.getLocalName());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  633 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void buildRestrictionSimpleTypeDefinition(SimpleTypeDefinitionComponent component, SchemaElement element, InternalSchema schema) {
/*  642 */     String baseAttr = element.getValueOfAttributeOrNull("base");
/*      */     
/*  644 */     if (baseAttr != null) {
/*  645 */       TypeDefinitionComponent base = schema.findTypeDefinition(element.asQName(baseAttr));
/*      */       
/*  647 */       if (base.isSimple()) {
/*  648 */         component.setBaseTypeDefinition((SimpleTypeDefinitionComponent)base);
/*      */       } else {
/*      */         
/*  651 */         failValidation("validation.notSimpleType", base.getName().getLocalPart());
/*      */       } 
/*      */     } else {
/*      */       
/*  655 */       failUnimplemented("F012");
/*      */     } 
/*      */ 
/*      */     
/*  659 */     component.setVarietyTag(component.getBaseTypeDefinition().getVarietyTag());
/*      */ 
/*      */ 
/*      */     
/*  663 */     component.setPrimitiveTypeDefinition(component.getBaseTypeDefinition().getPrimitiveTypeDefinition());
/*      */ 
/*      */ 
/*      */     
/*  667 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*      */     
/*  669 */     if (finalAttr == null) {
/*  670 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*      */       
/*  672 */       if (finalAttr == null) {
/*  673 */         finalAttr = "";
/*      */       }
/*      */     } 
/*  676 */     if (finalAttr.equals("")) {
/*      */ 
/*      */       
/*  679 */       component.setFinal(_setEmpty);
/*  680 */     } else if (finalAttr.equals("#all")) {
/*  681 */       component.setFinal(_setExtResListUnion);
/*      */     } else {
/*  683 */       component.setFinal(parseSymbolSet(finalAttr, _setExtResListUnion));
/*      */ 
/*      */       
/*  686 */       failUnimplemented("F013");
/*      */     } 
/*      */ 
/*      */     
/*  690 */     boolean gotOne = false;
/*  691 */     EnumerationFacet enumeration = new EnumerationFacet();
/*  692 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  693 */       SchemaElement child = iter.next();
/*  694 */       gotOne = true;
/*  695 */       if (child.getQName().equals(SchemaConstants.QNAME_ENUMERATION)) {
/*      */ 
/*      */         
/*  698 */         if (baseAttr.equals("xsd:QName")) {
/*  699 */           addPrefixesToEnumerationFacet(enumeration, element);
/*      */         }
/*  701 */         String valueAttr = child.getValueOfAttributeOrNull("value");
/*      */         
/*  703 */         if (valueAttr == null) {
/*  704 */           failValidation("validation.missingRequiredAttribute", "value", child.getQName().getLocalPart());
/*      */         }
/*      */         
/*  707 */         enumeration.addValue(valueAttr); continue;
/*  708 */       }  if (child.getQName().equals(SchemaConstants.QNAME_LENGTH) || child.getQName().equals(SchemaConstants.QNAME_MAX_INCLUSIVE) || child.getQName().equals(SchemaConstants.QNAME_MIN_INCLUSIVE) || child.getQName().equals(SchemaConstants.QNAME_MIN_EXCLUSIVE) || child.getQName().equals(SchemaConstants.QNAME_MAX_EXCLUSIVE) || child.getQName().equals(SchemaConstants.QNAME_MAX_LENGTH) || child.getQName().equals(SchemaConstants.QNAME_MIN_LENGTH) || child.getQName().equals(SchemaConstants.QNAME_PATTERN) || child.getQName().equals(SchemaConstants.QNAME_TOTAL_DIGITS) || child.getQName().equals(SchemaConstants.QNAME_FRACTION_DIGITS) || child.getQName().equals(SchemaConstants.QNAME_WHITE_SPACE)) {
/*      */         continue;
/*      */       }
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
/*  724 */       failUnimplemented("F014");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  729 */     if (enumeration.values().hasNext()) {
/*  730 */       component.addFacet(enumeration);
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
/*      */   private void addPrefixesToEnumerationFacet(EnumerationFacet enumeration, SchemaElement element) {
/*  745 */     for (Iterator<String> iter = element.prefixes(); iter.hasNext(); ) {
/*  746 */       String pfix = iter.next();
/*  747 */       if (pfix != null) {
/*  748 */         String ns = element.getURIForPrefix(pfix);
/*  749 */         if (ns != null) {
/*  750 */           enumeration.addPrefix(pfix, ns);
/*      */         }
/*      */       } 
/*      */     } 
/*  754 */     SchemaElement p = element.getParent();
/*  755 */     if (p != null) {
/*  756 */       addPrefixesToEnumerationFacet(enumeration, p);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void buildListSimpleTypeDefinition(SimpleTypeDefinitionComponent component, SchemaElement element, InternalSchema schema) {
/*  766 */     component.setBaseTypeDefinition(getSimpleUrType());
/*      */ 
/*      */     
/*  769 */     String itemTypeAttr = element.getValueOfAttributeOrNull("itemType");
/*      */     
/*  771 */     if (itemTypeAttr != null) {
/*  772 */       TypeDefinitionComponent itemType = schema.findTypeDefinition(element.asQName(itemTypeAttr));
/*      */       
/*  774 */       if (itemType.isSimple()) {
/*  775 */         component.setItemTypeDefinition((SimpleTypeDefinitionComponent)itemType);
/*      */       } else {
/*      */         
/*  778 */         failValidation("validation.notSimpleType", itemType.getName().getLocalPart());
/*      */       } 
/*      */     } else {
/*      */       
/*  782 */       SchemaElement simpleTypeElement = getOnlyChildIgnoring(element, SchemaConstants.QNAME_ANNOTATION);
/*      */       
/*  784 */       if (!simpleTypeElement.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE))
/*      */       {
/*      */         
/*  787 */         failValidation("validation.invalidElement", simpleTypeElement.getLocalName());
/*      */       }
/*      */ 
/*      */       
/*  791 */       TypeDefinitionComponent itemType = buildSimpleTypeDefinition(simpleTypeElement, schema);
/*      */       
/*  793 */       component.setItemTypeDefinition((SimpleTypeDefinitionComponent)itemType);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  798 */     component.setVarietyTag(2);
/*      */ 
/*      */ 
/*      */     
/*  802 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*      */     
/*  804 */     if (finalAttr == null) {
/*  805 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*      */       
/*  807 */       if (finalAttr == null) {
/*  808 */         finalAttr = "";
/*      */       }
/*      */     } 
/*  811 */     if (finalAttr.equals("")) {
/*      */ 
/*      */       
/*  814 */       component.setFinal(_setEmpty);
/*  815 */     } else if (finalAttr.equals("#all")) {
/*  816 */       component.setFinal(_setExtResListUnion);
/*      */     } else {
/*  818 */       component.setFinal(parseSymbolSet(finalAttr, _setExtResListUnion));
/*      */ 
/*      */       
/*  821 */       failUnimplemented("F013");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComplexTypeDefinitionComponent buildComplexTypeDefinition(SchemaElement element, InternalSchema schema) {
/*  830 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/*  831 */       SchemaElement child = iter.next();
/*  832 */       if (child.getQName().equals(SchemaConstants.QNAME_SIMPLE_CONTENT))
/*  833 */         return buildSimpleContentComplexTypeDefinition(element, schema); 
/*  834 */       if (child.getQName().equals(SchemaConstants.QNAME_COMPLEX_CONTENT)) {
/*      */ 
/*      */         
/*  837 */         boolean bool = element.getValueOfBooleanAttributeOrDefault("mixed", false);
/*      */ 
/*      */         
/*  840 */         return buildExplicitComplexContentComplexTypeDefinition(element, bool, schema);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  845 */     boolean mixedContent = element.getValueOfBooleanAttributeOrDefault("mixed", false);
/*      */     
/*  847 */     return buildImplicitComplexContentComplexTypeDefinition(element, mixedContent, schema);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComplexTypeDefinitionComponent commonBuildComplexTypeDefinition(SchemaElement element, InternalSchema schema) {
/*  856 */     ComplexTypeDefinitionComponent component = new ComplexTypeDefinitionComponent();
/*      */ 
/*      */ 
/*      */     
/*  860 */     String nameAttr = element.getValueOfAttributeOrNull("name");
/*      */ 
/*      */ 
/*      */     
/*  864 */     if (nameAttr != null && element.getParent().getQName().equals(SchemaConstants.QNAME_ELEMENT))
/*      */     {
/*      */       
/*  867 */       failValidation("validation.invalidComplexTypeInElement", nameAttr, element.getParent().getValueOfAttributeOrNull("name"));
/*      */     }
/*      */ 
/*      */     
/*  871 */     if (nameAttr != null) {
/*  872 */       component.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */ 
/*      */ 
/*      */       
/*  876 */       this._namedTypeComponentsBeingDefined.put(component.getName(), component);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  881 */     component.setAbstract(element.getValueOfBooleanAttributeOrDefault("abstract", false));
/*      */ 
/*      */ 
/*      */     
/*  885 */     String blockAttr = element.getValueOfAttributeOrNull("block");
/*      */     
/*  887 */     if (blockAttr == null) {
/*  888 */       blockAttr = element.getRoot().getValueOfAttributeOrNull("blockDefault");
/*      */       
/*  890 */       if (blockAttr == null) {
/*  891 */         blockAttr = "";
/*      */       }
/*      */     } 
/*  894 */     if (blockAttr.equals("")) {
/*      */ 
/*      */       
/*  897 */       component.setProhibitedSubstitutions(_setEmpty);
/*  898 */     } else if (blockAttr.equals("#all")) {
/*  899 */       component.setProhibitedSubstitutions(_setExtRes);
/*      */     } else {
/*  901 */       component.setProhibitedSubstitutions(parseSymbolSet(blockAttr, _setExtRes));
/*      */ 
/*      */ 
/*      */       
/*  905 */       failUnimplemented("F015");
/*      */     } 
/*      */ 
/*      */     
/*  909 */     String finalAttr = element.getValueOfAttributeOrNull("final");
/*      */     
/*  911 */     if (finalAttr == null) {
/*  912 */       finalAttr = element.getRoot().getValueOfAttributeOrNull("finalDefault");
/*      */       
/*  914 */       if (finalAttr == null) {
/*  915 */         finalAttr = "";
/*      */       }
/*      */     } 
/*  918 */     if (finalAttr.equals("")) {
/*      */ 
/*      */       
/*  921 */       component.setFinal(_setEmpty);
/*  922 */     } else if (finalAttr.equals("#all")) {
/*  923 */       component.setFinal(_setExtRes);
/*      */     } else {
/*  925 */       component.setFinal(parseSymbolSet(finalAttr, _setExtRes));
/*      */ 
/*      */       
/*  928 */       failUnimplemented("F016");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  933 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComplexTypeDefinitionComponent buildSimpleContentComplexTypeDefinition(SchemaElement element, InternalSchema schema) {
/*  942 */     ComplexTypeDefinitionComponent component = commonBuildComplexTypeDefinition(element, schema);
/*      */ 
/*      */     
/*  945 */     SchemaElement simpleContentElement = getOnlyChildIgnoring(element, SchemaConstants.QNAME_ANNOTATION);
/*      */     
/*  947 */     if (!simpleContentElement.getQName().equals(SchemaConstants.QNAME_SIMPLE_CONTENT))
/*      */     {
/*      */       
/*  950 */       failValidation("validation.invalidElement", simpleContentElement.getLocalName());
/*      */     }
/*      */ 
/*      */     
/*  954 */     component.setContentTag(2);
/*      */     
/*  956 */     SchemaElement derivationElement = getOnlyChildIgnoring(simpleContentElement, SchemaConstants.QNAME_ANNOTATION);
/*      */     
/*  958 */     boolean isRestriction = true;
/*  959 */     if (!derivationElement.getQName().equals(SchemaConstants.QNAME_RESTRICTION))
/*      */     {
/*      */ 
/*      */       
/*  963 */       if (derivationElement.getQName().equals(SchemaConstants.QNAME_EXTENSION)) {
/*      */ 
/*      */         
/*  966 */         isRestriction = false;
/*      */       } else {
/*  968 */         failValidation("validation.invalidElement", derivationElement.getLocalName());
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  973 */     String baseAttr = derivationElement.getValueOfAttributeOrNull("base");
/*      */     
/*  975 */     if (baseAttr == null) {
/*  976 */       component.setBaseTypeDefinition(getUrType());
/*      */     } else {
/*  978 */       component.setBaseTypeDefinition(schema.findTypeDefinition(element.asQName(baseAttr)));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  983 */     component.setDerivationMethod(isRestriction ? Symbol.RESTRICTION : Symbol.EXTENSION);
/*      */ 
/*      */     
/*  986 */     if (isRestriction) {
/*  987 */       processRestrictionSimpleTypeDefinition(derivationElement, component, schema);
/*      */     } else {
/*      */       
/*  990 */       processExtensionSimpleTypeDefinition(derivationElement, component, schema);
/*      */     } 
/*      */ 
/*      */     
/*  994 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processRestrictionSimpleTypeDefinition(SchemaElement element, ComplexTypeDefinitionComponent component, InternalSchema schema) {
/* 1002 */     boolean gotContent = false;
/* 1003 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1004 */       SchemaElement child = iter.next();
/* 1005 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/* 1006 */         AttributeUseComponent attribute = buildAttributeUse(child, component, schema);
/*      */         
/* 1008 */         if (attribute == null) {
/*      */ 
/*      */           
/* 1011 */           failUnimplemented("F019"); continue;
/*      */         } 
/* 1013 */         component.addAttributeUse(attribute); continue;
/*      */       } 
/* 1015 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*      */ 
/*      */         
/* 1018 */         String refAttr = child.getValueOfMandatoryAttribute("ref");
/*      */         
/* 1020 */         component.addAttributeGroup(schema.findAttributeGroupDefinition(child.asQName(refAttr))); continue;
/*      */       } 
/* 1022 */       if (child.getQName().equals(SchemaConstants.QNAME_ANY_ATTRIBUTE)) {
/*      */ 
/*      */ 
/*      */         
/* 1026 */         failUnimplemented("F020"); continue;
/* 1027 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1034 */       failUnimplemented("F023");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processExtensionSimpleTypeDefinition(SchemaElement element, ComplexTypeDefinitionComponent component, InternalSchema schema) {
/* 1044 */     boolean gotContent = false;
/* 1045 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1046 */       SchemaElement child = iter.next();
/* 1047 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/* 1048 */         AttributeUseComponent attribute = buildAttributeUse(child, component, schema);
/*      */         
/* 1050 */         if (attribute == null) {
/*      */ 
/*      */           
/* 1053 */           failUnimplemented("F019"); continue;
/*      */         } 
/* 1055 */         component.addAttributeUse(attribute); continue;
/*      */       } 
/* 1057 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*      */ 
/*      */         
/* 1060 */         String refAttr = child.getValueOfMandatoryAttribute("ref");
/*      */         
/* 1062 */         component.addAttributeGroup(schema.findAttributeGroupDefinition(child.asQName(refAttr))); continue;
/*      */       } 
/* 1064 */       if (child.getQName().equals(SchemaConstants.QNAME_ANY_ATTRIBUTE)) {
/*      */ 
/*      */ 
/*      */         
/* 1068 */         failUnimplemented("F020"); continue;
/* 1069 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1076 */       failUnimplemented("F023");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComplexTypeDefinitionComponent buildExplicitComplexContentComplexTypeDefinition(SchemaElement element, boolean mixedContent, InternalSchema schema) {
/* 1087 */     ComplexTypeDefinitionComponent component = commonBuildComplexTypeDefinition(element, schema);
/*      */ 
/*      */     
/* 1090 */     SchemaElement complexContentElement = getOnlyChildIgnoring(element, SchemaConstants.QNAME_ANNOTATION);
/*      */     
/* 1092 */     if (!complexContentElement.getQName().equals(SchemaConstants.QNAME_COMPLEX_CONTENT))
/*      */     {
/*      */       
/* 1095 */       failValidation("validation.invalidElement", complexContentElement.getLocalName());
/*      */     }
/*      */ 
/*      */     
/* 1099 */     boolean mixed = complexContentElement.getValueOfBooleanAttributeOrDefault("mixed", mixedContent);
/*      */ 
/*      */ 
/*      */     
/* 1103 */     SchemaElement derivationElement = getOnlyChildIgnoring(complexContentElement, SchemaConstants.QNAME_ANNOTATION);
/*      */     
/* 1105 */     boolean isRestriction = true;
/* 1106 */     if (!derivationElement.getQName().equals(SchemaConstants.QNAME_RESTRICTION))
/*      */     {
/*      */ 
/*      */       
/* 1110 */       if (derivationElement.getQName().equals(SchemaConstants.QNAME_EXTENSION)) {
/*      */ 
/*      */         
/* 1113 */         isRestriction = false;
/*      */       } else {
/* 1115 */         failValidation("validation.invalidElement", derivationElement.getLocalName());
/*      */       } 
/*      */     }
/*      */     
/* 1119 */     if (isRestriction) {
/* 1120 */       String baseAttr = derivationElement.getValueOfMandatoryAttribute("base");
/*      */       
/* 1122 */       TypeDefinitionComponent baseType = schema.findTypeDefinition(derivationElement.asQName(baseAttr));
/*      */       
/* 1124 */       component.setBaseTypeDefinition(baseType);
/* 1125 */       if (mixed) {
/* 1126 */         component.setContentTag(3);
/*      */       } else {
/*      */         
/* 1129 */         component.setContentTag(4);
/*      */       } 
/*      */       
/* 1132 */       processRestrictionComplexTypeDefinition(derivationElement, component, schema);
/*      */     } else {
/*      */       
/* 1135 */       String baseAttr = derivationElement.getValueOfMandatoryAttribute("base");
/*      */       
/* 1137 */       TypeDefinitionComponent baseType = schema.findTypeDefinition(derivationElement.asQName(baseAttr));
/*      */       
/* 1139 */       component.setBaseTypeDefinition(baseType);
/* 1140 */       if (mixed) {
/* 1141 */         component.setContentTag(3);
/*      */       } else {
/*      */         
/* 1144 */         component.setContentTag(4);
/*      */       } 
/*      */       
/* 1147 */       processExtensionComplexTypeDefinition(derivationElement, component, schema);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     component.setDerivationMethod(isRestriction ? Symbol.RESTRICTION : Symbol.EXTENSION);
/*      */ 
/*      */     
/* 1155 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ComplexTypeDefinitionComponent buildImplicitComplexContentComplexTypeDefinition(SchemaElement element, boolean mixedContent, InternalSchema schema) {
/* 1164 */     ComplexTypeDefinitionComponent component = commonBuildComplexTypeDefinition(element, schema);
/*      */     
/* 1166 */     component.setBaseTypeDefinition(getUrType());
/* 1167 */     if (mixedContent) {
/* 1168 */       component.setContentTag(3);
/*      */     } else {
/*      */       
/* 1171 */       component.setContentTag(4);
/*      */     } 
/*      */     
/* 1174 */     processRestrictionComplexTypeDefinition(element, component, schema);
/* 1175 */     return component;
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
/*      */   protected void processRestrictionComplexTypeDefinition(SchemaElement element, ComplexTypeDefinitionComponent component, InternalSchema schema) {
/* 1190 */     boolean gotContent = false;
/* 1191 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1192 */       SchemaElement child = iter.next();
/* 1193 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/* 1194 */         AttributeUseComponent attribute = buildAttributeUse(child, component, schema);
/*      */         
/* 1196 */         if (attribute == null) {
/*      */ 
/*      */           
/* 1199 */           failUnimplemented("F019"); continue;
/*      */         } 
/* 1201 */         component.addAttributeUse(attribute); continue;
/*      */       } 
/* 1203 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*      */ 
/*      */         
/* 1206 */         String refAttr = child.getValueOfMandatoryAttribute("ref");
/*      */         
/* 1208 */         component.addAttributeGroup(schema.findAttributeGroupDefinition(child.asQName(refAttr))); continue;
/*      */       } 
/* 1210 */       if (child.getQName().equals(SchemaConstants.QNAME_ANY_ATTRIBUTE)) {
/*      */ 
/*      */ 
/*      */         
/* 1214 */         failUnimplemented("F020"); continue;
/* 1215 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1223 */       if (gotContent) {
/* 1224 */         failValidation("validation.invalidElement", child.getLocalName());
/*      */       }
/*      */       
/* 1227 */       gotContent = true;
/* 1228 */       if (child.getQName().equals(SchemaConstants.QNAME_GROUP)) {
/*      */ 
/*      */         
/* 1231 */         failUnimplemented("F021"); continue;
/*      */       } 
/* 1233 */       component.setParticleContent(buildParticle(child, component, schema));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     if (!gotContent) {
/* 1240 */       component.setContentTag(1);
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
/*      */   protected void processExtensionComplexTypeDefinition(SchemaElement element, ComplexTypeDefinitionComponent component, InternalSchema schema) {
/* 1257 */     boolean gotContent = false;
/* 1258 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1259 */       SchemaElement child = iter.next();
/* 1260 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE)) {
/* 1261 */         AttributeUseComponent attribute = buildAttributeUse(child, component, schema);
/*      */         
/* 1263 */         if (attribute == null) {
/*      */ 
/*      */           
/* 1266 */           failUnimplemented("F019"); continue;
/*      */         } 
/* 1268 */         component.addAttributeUse(attribute); continue;
/*      */       } 
/* 1270 */       if (child.getQName().equals(SchemaConstants.QNAME_ATTRIBUTE_GROUP)) {
/*      */ 
/*      */         
/* 1273 */         String refAttr = child.getValueOfMandatoryAttribute("ref");
/*      */         
/* 1275 */         component.addAttributeGroup(schema.findAttributeGroupDefinition(child.asQName(refAttr))); continue;
/*      */       } 
/* 1277 */       if (child.getQName().equals(SchemaConstants.QNAME_ANY_ATTRIBUTE)) {
/*      */ 
/*      */ 
/*      */         
/* 1281 */         failUnimplemented("F020"); continue;
/* 1282 */       }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1290 */       if (gotContent) {
/* 1291 */         failValidation("validation.invalidElement", child.getLocalName());
/*      */       }
/*      */       
/* 1294 */       gotContent = true;
/* 1295 */       if (child.getQName().equals(SchemaConstants.QNAME_GROUP)) {
/*      */ 
/*      */         
/* 1298 */         failUnimplemented("F021"); continue;
/*      */       } 
/* 1300 */       component.setParticleContent(buildParticle(child, component, schema));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1306 */     if (!gotContent) {
/* 1307 */       component.setContentTag(1);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected AttributeUseComponent buildAttributeUse(SchemaElement element, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/* 1317 */     AttributeUseComponent component = new AttributeUseComponent();
/*      */ 
/*      */     
/* 1320 */     String useAttr = element.getValueOfAttributeOrNull("use");
/* 1321 */     if (useAttr != null) {
/* 1322 */       if (useAttr.equals("required")) {
/* 1323 */         component.setRequired(true);
/* 1324 */       } else if (useAttr.equals("prohibited")) {
/* 1325 */         return null;
/*      */       } 
/*      */     }
/*      */     
/* 1329 */     String refAttr = element.getValueOfAttributeOrNull("ref");
/* 1330 */     if (refAttr != null) {
/* 1331 */       assertNoAttribute(element, "name");
/* 1332 */       assertNoAttribute(element, "type");
/*      */       
/* 1334 */       component.setAttributeDeclaration(schema.findAttributeDeclaration(element.asQName(refAttr)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       component.setAnnotation(buildNonSchemaAttributesAnnotation(element));
/*      */ 
/*      */ 
/*      */       
/* 1344 */       AttributeDeclarationComponent attComp = component.getAttributeDeclaration();
/* 1345 */       component.setValue(attComp.getValue());
/* 1346 */       component.setValueKind(attComp.getValueKind());
/*      */     }
/*      */     else {
/*      */       
/* 1350 */       AttributeDeclarationComponent declaration = new AttributeDeclarationComponent();
/*      */ 
/*      */ 
/*      */       
/* 1354 */       String nameAttr = element.getValueOfMandatoryAttribute("name");
/*      */       
/* 1356 */       String formAttr = element.getValueOfAttributeOrNull("form");
/*      */       
/* 1358 */       if (formAttr == null) {
/* 1359 */         formAttr = element.getRoot().getValueOfAttributeOrNull("attributeFormDefault");
/*      */         
/* 1361 */         if (formAttr == null) {
/* 1362 */           formAttr = "";
/*      */         }
/*      */       } 
/* 1365 */       if (formAttr.equals("qualified")) {
/* 1366 */         declaration.setName(new QName(element.getSchema().getTargetNamespaceURI(), nameAttr));
/*      */       } else {
/*      */         
/* 1369 */         declaration.setName(new QName(nameAttr));
/*      */       } 
/*      */ 
/*      */       
/* 1373 */       declaration.setScope(scope);
/*      */ 
/*      */       
/* 1376 */       boolean foundType = false;
/* 1377 */       for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1378 */         SchemaElement child = iter.next();
/* 1379 */         if (child.getQName().equals(SchemaConstants.QNAME_SIMPLE_TYPE)) {
/*      */ 
/*      */           
/* 1382 */           if (foundType) {
/* 1383 */             failValidation("validation.invalidElement", element.getLocalName());
/*      */           }
/*      */           
/* 1386 */           declaration.setTypeDefinition(buildSimpleTypeDefinition(child, schema));
/*      */           
/* 1388 */           foundType = true; continue;
/* 1389 */         }  if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1394 */         failValidation("validation.invalidElement", child.getLocalName());
/*      */       } 
/*      */ 
/*      */       
/* 1398 */       if (foundType) {
/* 1399 */         assertNoAttribute(element, "type");
/*      */       } else {
/* 1401 */         String typeAttr = element.getValueOfAttributeOrNull("type");
/*      */         
/* 1403 */         if (typeAttr == null) {
/* 1404 */           declaration.setTypeDefinition(getSimpleUrType());
/*      */         } else {
/* 1406 */           TypeDefinitionComponent typeComponent = schema.findTypeDefinition(element.asQName(typeAttr));
/*      */           
/* 1408 */           if (typeComponent instanceof SimpleTypeDefinitionComponent) {
/*      */ 
/*      */             
/* 1411 */             declaration.setTypeDefinition((SimpleTypeDefinitionComponent)typeComponent);
/*      */           } else {
/*      */             
/* 1414 */             failValidation("validation.notSimpleType", declaration.getName().getLocalPart());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1421 */       declaration.setAnnotation(buildNonSchemaAttributesAnnotation(element));
/*      */ 
/*      */       
/* 1424 */       component.setAttributeDeclaration(declaration);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1430 */     String defaultAttr = element.getValueOfAttributeOrNull("default");
/*      */     
/* 1432 */     String fixedAttr = element.getValueOfAttributeOrNull("fixed");
/*      */     
/* 1434 */     if (defaultAttr != null && fixedAttr != null) {
/* 1435 */       fail("validation.exclusiveAttributes", "default", "fixed");
/*      */     }
/*      */     
/* 1438 */     if (defaultAttr != null) {
/* 1439 */       component.setValue(defaultAttr);
/* 1440 */       component.setValueKind(Symbol.DEFAULT);
/*      */     } 
/* 1442 */     if (fixedAttr != null) {
/* 1443 */       component.setValue(defaultAttr);
/* 1444 */       component.setValueKind(Symbol.FIXED);
/*      */     } 
/*      */     
/* 1447 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ParticleComponent buildParticle(SchemaElement element, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/* 1455 */     ParticleComponent component = new ParticleComponent();
/*      */ 
/*      */     
/* 1458 */     int minOccurs = element.getValueOfIntegerAttributeOrDefault("minOccurs", 1);
/*      */     
/* 1460 */     component.setMinOccurs(minOccurs);
/* 1461 */     String maxOccursAttr = element.getValueOfAttributeOrNull("maxOccurs");
/*      */     
/* 1463 */     if (maxOccursAttr == null) {
/* 1464 */       component.setMaxOccurs(1);
/*      */     }
/* 1466 */     else if (maxOccursAttr.equals("unbounded")) {
/* 1467 */       component.setMaxOccursUnbounded();
/*      */     } else {
/*      */       try {
/* 1470 */         int i = Integer.parseInt(maxOccursAttr);
/* 1471 */         if (i < 0 || i < minOccurs) {
/* 1472 */           failValidation("validation.invalidAttributeValue", "maxOccurs", maxOccursAttr);
/*      */         }
/*      */         
/* 1475 */         component.setMaxOccurs(i);
/* 1476 */       } catch (NumberFormatException e) {
/* 1477 */         failValidation("validation.invalidAttributeValue", "maxOccurs", maxOccursAttr);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1483 */     if (element.getQName().equals(SchemaConstants.QNAME_ELEMENT)) {
/* 1484 */       processElementParticle(element, component, scope, schema);
/* 1485 */     } else if (element.getQName().equals(SchemaConstants.QNAME_ALL) || element.getQName().equals(SchemaConstants.QNAME_CHOICE) || element.getQName().equals(SchemaConstants.QNAME_SEQUENCE)) {
/*      */ 
/*      */ 
/*      */       
/* 1489 */       component.setTermTag(1);
/* 1490 */       component.setModelGroupTerm(buildModelGroup(element, scope, schema));
/*      */     }
/* 1492 */     else if (element.getQName().equals(SchemaConstants.QNAME_ANY)) {
/* 1493 */       component.setTermTag(2);
/* 1494 */       component.setWildcardTerm(buildAnyWildcard(element, scope, schema));
/*      */     } else {
/* 1496 */       failValidation("validation.invalidElement", element.getLocalName());
/*      */     } 
/*      */     
/* 1499 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ModelGroupComponent buildModelGroup(SchemaElement element, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/* 1505 */     ModelGroupComponent component = new ModelGroupComponent();
/*      */     
/* 1507 */     if (element.getQName().equals(SchemaConstants.QNAME_ALL)) {
/* 1508 */       component.setCompositor(Symbol.ALL);
/* 1509 */     } else if (element.getQName().equals(SchemaConstants.QNAME_CHOICE)) {
/* 1510 */       component.setCompositor(Symbol.CHOICE);
/* 1511 */     } else if (element.getQName().equals(SchemaConstants.QNAME_SEQUENCE)) {
/* 1512 */       component.setCompositor(Symbol.SEQUENCE);
/*      */     } else {
/* 1514 */       failValidation("validation.invalidElement", element.getLocalName());
/*      */     } 
/*      */     
/* 1517 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1518 */       SchemaElement child = iter.next();
/* 1519 */       if (child.getQName().equals(SchemaConstants.QNAME_ANNOTATION)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1524 */       ParticleComponent particle = buildParticle(child, scope, schema);
/* 1525 */       component.addParticle(particle);
/*      */     } 
/*      */     
/* 1528 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected WildcardComponent buildAnyWildcard(SchemaElement element, ComplexTypeDefinitionComponent scope, InternalSchema schema) {
/* 1536 */     WildcardComponent component = new WildcardComponent();
/*      */ 
/*      */     
/* 1539 */     String processContentsAttr = element.getValueOfAttributeOrNull("processContents");
/*      */     
/* 1541 */     if (processContentsAttr != null) {
/* 1542 */       Symbol processContents = parseSymbolInSet(processContentsAttr, _setLaxSkipStrict);
/*      */       
/* 1544 */       if (processContents == null) {
/* 1545 */         failValidation("validation.invalidAttribute", "processContents", element.getLocalName());
/*      */       }
/*      */       
/* 1548 */       component.setProcessContents(processContents);
/*      */     } else {
/* 1550 */       component.setProcessContents(Symbol.STRICT);
/*      */     } 
/*      */ 
/*      */     
/* 1554 */     String namespaceAttr = element.getValueOfAttributeOrNull("namespace");
/*      */     
/* 1556 */     if (namespaceAttr != null) {
/* 1557 */       if (namespaceAttr.equals("##any")) {
/* 1558 */         component.setNamespaceConstraintTag(1);
/*      */       }
/* 1560 */       else if (namespaceAttr.equals("##other")) {
/* 1561 */         String targetNamespaceURI = element.getSchema().getTargetNamespaceURI();
/*      */         
/* 1563 */         if (targetNamespaceURI == null || targetNamespaceURI.equals("")) {
/*      */ 
/*      */           
/* 1566 */           component.setNamespaceConstraintTag(3);
/*      */         } else {
/*      */           
/* 1569 */           component.setNamespaceConstraintTag(2);
/*      */           
/* 1571 */           component.setNamespaceName(targetNamespaceURI);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1576 */         failUnimplemented("F022");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1581 */       component.setNamespaceConstraintTag(1);
/*      */     } 
/*      */     
/* 1584 */     return component;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected AnnotationComponent buildNonSchemaAttributesAnnotation(SchemaElement element) {
/* 1590 */     AnnotationComponent annotation = null;
/* 1591 */     for (Iterator<SchemaAttribute> iter = element.attributes(); iter.hasNext(); ) {
/* 1592 */       SchemaAttribute attribute = iter.next();
/* 1593 */       if (attribute.getNamespaceURI() != null && !attribute.getNamespaceURI().equals("") && !attribute.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1598 */         if (annotation == null) {
/* 1599 */           annotation = new AnnotationComponent();
/*      */         }
/*      */         
/* 1602 */         annotation.addAttribute(attribute);
/*      */       } 
/*      */     } 
/*      */     
/* 1606 */     return annotation;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public TypeDefinitionComponent getTypeDefinitionComponentBeingDefined(QName name) {
/* 1612 */     if (this._namedTypeComponentsBeingDefined != null) {
/* 1613 */       return (TypeDefinitionComponent)this._namedTypeComponentsBeingDefined.get(name);
/*      */     }
/*      */     
/* 1616 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createWellKnownTypes() {
/* 1622 */     this._wellKnownTypes = new HashMap<Object, Object>();
/*      */ 
/*      */     
/* 1625 */     this._urType = new ComplexTypeDefinitionComponent();
/* 1626 */     this._urType.setName(InternalSchemaConstants.QNAME_TYPE_URTYPE);
/* 1627 */     this._urType.setBaseTypeDefinition(this._urType);
/* 1628 */     this._urType.setFinal(_setEmpty);
/* 1629 */     this._urType.setProhibitedSubstitutions(_setEmpty);
/* 1630 */     this._urType.setDerivationMethod(Symbol.RESTRICTION);
/* 1631 */     this._urType.setContentTag(3);
/* 1632 */     ParticleComponent utp = new ParticleComponent();
/* 1633 */     utp.setMinOccurs(1);
/* 1634 */     utp.setMaxOccurs(1);
/* 1635 */     ModelGroupComponent utpmg = new ModelGroupComponent();
/* 1636 */     utpmg.setCompositor(Symbol.SEQUENCE);
/* 1637 */     ParticleComponent utpmgp = new ParticleComponent();
/* 1638 */     utpmgp.setMinOccurs(0);
/* 1639 */     utpmgp.setMaxOccursUnbounded();
/* 1640 */     WildcardComponent utpmgpw = new WildcardComponent();
/* 1641 */     utpmgpw.setNamespaceConstraintTag(1);
/*      */     
/* 1643 */     utpmgp.setTermTag(2);
/* 1644 */     utpmgp.setWildcardTerm(utpmgpw);
/* 1645 */     utpmg.addParticle(utpmgp);
/* 1646 */     utp.setTermTag(1);
/* 1647 */     utp.setModelGroupTerm(utpmg);
/* 1648 */     this._urType.setParticleContent(utp);
/*      */ 
/*      */     
/* 1651 */     this._wellKnownTypes.put(this._urType.getName(), this._urType);
/*      */ 
/*      */     
/* 1654 */     this._simpleUrType = new SimpleTypeDefinitionComponent();
/* 1655 */     this._simpleUrType.setName(InternalSchemaConstants.QNAME_TYPE_SIMPLE_URTYPE);
/* 1656 */     this._simpleUrType.setBaseTypeDefinition(this._simpleUrType);
/* 1657 */     this._simpleUrType.setFinal(_setEmpty);
/*      */ 
/*      */     
/* 1660 */     this._wellKnownTypes.put(this._simpleUrType.getName(), this._simpleUrType);
/*      */     
/* 1662 */     for (Iterator<QName> iterator1 = _primitiveTypeNames.iterator(); iterator1.hasNext(); ) {
/* 1663 */       QName name = iterator1.next();
/*      */ 
/*      */       
/* 1666 */       SimpleTypeDefinitionComponent type = new SimpleTypeDefinitionComponent();
/*      */       
/* 1668 */       type.setName(name);
/* 1669 */       type.setVarietyTag(1);
/* 1670 */       type.setFinal(_setEmpty);
/* 1671 */       type.setBaseTypeDefinition(this._simpleUrType);
/* 1672 */       type.setPrimitiveTypeDefinition(type);
/*      */       
/* 1674 */       this._wellKnownTypes.put(type.getName(), type);
/*      */     } 
/*      */     
/* 1677 */     for (Iterator<QName> iter = _soapTypeNames.iterator(); iter.hasNext(); ) {
/* 1678 */       QName name = iter.next();
/* 1679 */       ComplexTypeDefinitionComponent type = new ComplexTypeDefinitionComponent();
/*      */       
/* 1681 */       type.setName(name);
/* 1682 */       type.setBaseTypeDefinition(this._urType);
/* 1683 */       type.setContentTag(2);
/* 1684 */       QName xName = new QName("http://www.w3.org/2001/XMLSchema", name.getLocalPart());
/*      */       
/* 1686 */       SimpleTypeDefinitionComponent xComponent = (SimpleTypeDefinitionComponent)this._wellKnownTypes.get(xName);
/*      */       
/* 1688 */       if (xComponent == null) {
/*      */         continue;
/*      */       }
/* 1691 */       type.setSimpleTypeContent(xComponent);
/*      */ 
/*      */       
/* 1694 */       this._wellKnownTypes.put(type.getName(), type);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1702 */     SimpleTypeDefinitionComponent base64Type = new SimpleTypeDefinitionComponent();
/*      */     
/* 1704 */     base64Type.setName(SOAPConstants.QNAME_TYPE_BASE64);
/* 1705 */     base64Type.setVarietyTag(1);
/* 1706 */     base64Type.setFinal(_setEmpty);
/* 1707 */     base64Type.setBaseTypeDefinition(this._simpleUrType);
/* 1708 */     base64Type.setPrimitiveTypeDefinition(base64Type);
/* 1709 */     this._wellKnownTypes.put(base64Type.getName(), base64Type);
/*      */ 
/*      */     
/* 1712 */     ComplexTypeDefinitionComponent arrayType = new ComplexTypeDefinitionComponent();
/*      */     
/* 1714 */     arrayType.setName(SOAPConstants.QNAME_TYPE_ARRAY);
/* 1715 */     arrayType.setBaseTypeDefinition(this._urType);
/* 1716 */     arrayType.setDerivationMethod(Symbol.RESTRICTION);
/* 1717 */     arrayType.setContentTag(4);
/*      */     
/* 1719 */     ParticleComponent atp = new ParticleComponent();
/* 1720 */     atp.setMinOccurs(1);
/* 1721 */     atp.setMaxOccurs(1);
/* 1722 */     ModelGroupComponent atpmg = new ModelGroupComponent();
/* 1723 */     atpmg.setCompositor(Symbol.SEQUENCE);
/* 1724 */     ParticleComponent atpmgp = new ParticleComponent();
/* 1725 */     atpmgp.setMinOccurs(0);
/* 1726 */     atpmgp.setMaxOccursUnbounded();
/* 1727 */     WildcardComponent atpmgpw = new WildcardComponent();
/* 1728 */     atpmgpw.setNamespaceConstraintTag(1);
/*      */     
/* 1730 */     atpmgp.setTermTag(2);
/* 1731 */     atpmgp.setWildcardTerm(atpmgpw);
/* 1732 */     atpmg.addParticle(atpmgp);
/* 1733 */     atp.setTermTag(1);
/* 1734 */     atp.setModelGroupTerm(atpmg);
/* 1735 */     arrayType.setParticleContent(atp);
/*      */ 
/*      */     
/* 1738 */     this._wellKnownTypes.put(arrayType.getName(), arrayType);
/*      */   }
/*      */   
/*      */   protected void createWellKnownAttributes() {
/* 1742 */     this._wellKnownAttributes = new HashMap<Object, Object>();
/*      */     
/* 1744 */     AttributeDeclarationComponent arrayTypeAttr = new AttributeDeclarationComponent();
/*      */     
/* 1746 */     arrayTypeAttr.setName(SOAPConstants.QNAME_ATTR_ARRAY_TYPE);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1751 */     arrayTypeAttr.setTypeDefinition((SimpleTypeDefinitionComponent)this._wellKnownTypes.get(SchemaConstants.QNAME_TYPE_STRING));
/*      */ 
/*      */     
/* 1754 */     this._wellKnownAttributes.put(arrayTypeAttr.getName(), arrayTypeAttr);
/*      */     
/* 1756 */     AttributeDeclarationComponent offsetAttr = new AttributeDeclarationComponent();
/*      */     
/* 1758 */     offsetAttr.setName(SOAPConstants.QNAME_ATTR_OFFSET);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1763 */     offsetAttr.setTypeDefinition((SimpleTypeDefinitionComponent)this._wellKnownTypes.get(SchemaConstants.QNAME_TYPE_STRING));
/*      */     
/* 1765 */     this._wellKnownAttributes.put(offsetAttr.getName(), offsetAttr);
/*      */     
/* 1767 */     AttributeDeclarationComponent xmlLangAttr = new AttributeDeclarationComponent();
/*      */     
/* 1769 */     xmlLangAttr.setName(new QName("http://www.w3.org/XML/1998/namespace", "lang"));
/*      */ 
/*      */ 
/*      */     
/* 1773 */     xmlLangAttr.setTypeDefinition((SimpleTypeDefinitionComponent)this._wellKnownTypes.get(SchemaConstants.QNAME_TYPE_STRING));
/*      */     
/* 1775 */     this._wellKnownAttributes.put(xmlLangAttr.getName(), xmlLangAttr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createWellKnownAttributeGroups() {
/* 1785 */     this._wellKnownAttributeGroups = new HashMap<Object, Object>();
/*      */     
/* 1787 */     AttributeGroupDefinitionComponent commonAttributesAttrGroup = new AttributeGroupDefinitionComponent();
/*      */     
/* 1789 */     commonAttributesAttrGroup.setName(SOAPConstants.QNAME_ATTR_GROUP_COMMON_ATTRIBUTES);
/*      */ 
/*      */     
/* 1792 */     AttributeDeclarationComponent idAttr = new AttributeDeclarationComponent();
/*      */     
/* 1794 */     idAttr.setName(SOAPConstants.QNAME_ATTR_ID);
/* 1795 */     idAttr.setTypeDefinition((SimpleTypeDefinitionComponent)this._wellKnownTypes.get(SchemaConstants.QNAME_TYPE_ID));
/*      */     
/* 1797 */     AttributeUseComponent idAttrUse = new AttributeUseComponent();
/* 1798 */     idAttrUse.setAttributeDeclaration(idAttr);
/* 1799 */     commonAttributesAttrGroup.addAttributeUse(idAttrUse);
/*      */     
/* 1801 */     AttributeDeclarationComponent hrefAttr = new AttributeDeclarationComponent();
/*      */     
/* 1803 */     hrefAttr.setName(SOAPConstants.QNAME_ATTR_HREF);
/* 1804 */     hrefAttr.setTypeDefinition((SimpleTypeDefinitionComponent)this._wellKnownTypes.get(SchemaConstants.QNAME_TYPE_ANY_URI));
/*      */     
/* 1806 */     AttributeUseComponent hrefAttrUse = new AttributeUseComponent();
/* 1807 */     hrefAttrUse.setAttributeDeclaration(hrefAttr);
/* 1808 */     commonAttributesAttrGroup.addAttributeUse(hrefAttrUse);
/*      */ 
/*      */ 
/*      */     
/* 1812 */     this._wellKnownAttributeGroups.put(commonAttributesAttrGroup.getName(), commonAttributesAttrGroup);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createWellKnownElements() {
/* 1817 */     this._wellKnownElements = new HashMap<Object, Object>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Set parseSymbolSet(String s, Set values) {
/* 1825 */     if (s.equals("#all")) {
/* 1826 */       return values;
/*      */     }
/*      */     
/* 1829 */     Set<Symbol> result = new HashSet();
/* 1830 */     List tokens = XmlUtil.parseTokenList(s);
/* 1831 */     for (Iterator<String> iter = tokens.iterator(); iter.hasNext(); ) {
/* 1832 */       String v = iter.next();
/* 1833 */       Symbol sym = Symbol.named(v);
/* 1834 */       if (sym != null && values.contains(sym)) {
/* 1835 */         result.add(sym);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1841 */     return result;
/*      */   }
/*      */   
/*      */   private Symbol parseSymbolInSet(String s, Set values) {
/* 1845 */     Symbol sym = Symbol.named(s);
/* 1846 */     if (sym != null && values.contains(sym)) {
/* 1847 */       return sym;
/*      */     }
/* 1849 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SchemaElement getOnlyChildIgnoring(SchemaElement element, QName name) {
/* 1856 */     SchemaElement result = null;
/* 1857 */     for (Iterator<SchemaElement> iter = element.children(); iter.hasNext(); ) {
/* 1858 */       SchemaElement child = iter.next();
/* 1859 */       if (!child.getQName().equals(name)) {
/* 1860 */         if (result != null) {
/* 1861 */           failValidation("validation.invalidElement", child.getLocalName());
/*      */         }
/*      */         
/* 1864 */         result = child;
/*      */       } 
/*      */     } 
/* 1867 */     if (result == null) {
/* 1868 */       failValidation("validation.invalidElement", element.getLocalName());
/*      */     }
/* 1870 */     return result;
/*      */   }
/*      */   
/*      */   private void assertNoAttribute(SchemaElement element, String name) {
/* 1874 */     String value = element.getValueOfAttributeOrNull(name);
/* 1875 */     if (value != null)
/*      */     {
/*      */       
/* 1878 */       failValidation("validation.invalidAttribute", name, element.getValueOfAttributeOrNull("name"));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void fail(String key) {
/* 1884 */     throw new ModelException(key);
/*      */   }
/*      */   
/*      */   private void fail(String key, QName name) {
/* 1888 */     fail(key, toString(name));
/*      */   }
/*      */   
/*      */   private void fail(String key, String arg) {
/* 1892 */     throw new ModelException(key, arg);
/*      */   }
/*      */   
/*      */   private void fail(String key, String arg1, String arg2) {
/* 1896 */     throw new ModelException(key, new Object[] { arg1, arg2 });
/*      */   }
/*      */   
/*      */   protected void failUnimplemented(String arg) {
/* 1900 */     throw new UnimplementedFeatureException(arg);
/*      */   }
/*      */   
/*      */   private void failValidation(String key) {
/* 1904 */     throw new ValidationException(key);
/*      */   }
/*      */   
/*      */   protected void failValidation(String key, String arg) {
/* 1908 */     throw new ValidationException(key, arg);
/*      */   }
/*      */   
/*      */   protected void failValidation(String key, String arg1, String arg2) {
/* 1912 */     throw new ValidationException(key, new Object[] { arg1, arg2 });
/*      */   }
/*      */   
/*      */   private String toString(QName name) {
/* 1916 */     return name.getLocalPart() + " (" + name.getNamespaceURI() + ")";
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
/* 1933 */   protected static final Set _setEmpty = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1940 */   private static final Set _setExtRes = new HashSet(); static {
/* 1941 */     _setExtRes.add(Symbol.EXTENSION);
/* 1942 */     _setExtRes.add(Symbol.RESTRICTION);
/*      */   }
/* 1944 */   private static final Set _setExtResSub = new HashSet(); static {
/* 1945 */     _setExtResSub.add(Symbol.EXTENSION);
/* 1946 */     _setExtResSub.add(Symbol.RESTRICTION);
/* 1947 */     _setExtResSub.add(Symbol.SUBSTITUTION);
/*      */   }
/* 1949 */   protected static final Set _setExtResListUnion = new HashSet(); static {
/* 1950 */     _setExtResListUnion.add(Symbol.EXTENSION);
/* 1951 */     _setExtResListUnion.add(Symbol.RESTRICTION);
/* 1952 */     _setExtResListUnion.add(Symbol.LIST);
/* 1953 */     _setExtResListUnion.add(Symbol.UNION);
/*      */   }
/* 1955 */   private static final Set _setLaxSkipStrict = new HashSet(); static {
/* 1956 */     _setLaxSkipStrict.add(Symbol.LAX);
/* 1957 */     _setLaxSkipStrict.add(Symbol.SKIP);
/* 1958 */     _setLaxSkipStrict.add(Symbol.STRICT);
/*      */     
/* 1960 */     _primitiveTypeNames = new HashSet();
/* 1961 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_STRING);
/* 1962 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NORMALIZED_STRING);
/* 1963 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_TOKEN);
/* 1964 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_BYTE);
/* 1965 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_UNSIGNED_BYTE);
/* 1966 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_BASE64_BINARY);
/* 1967 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_HEX_BINARY);
/* 1968 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_INTEGER);
/* 1969 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_POSITIVE_INTEGER);
/* 1970 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NEGATIVE_INTEGER);
/* 1971 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
/*      */     
/* 1973 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
/*      */     
/* 1975 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_INT);
/* 1976 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_UNSIGNED_INT);
/* 1977 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_LONG);
/* 1978 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_UNSIGNED_LONG);
/* 1979 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_SHORT);
/* 1980 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_UNSIGNED_SHORT);
/* 1981 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_DECIMAL);
/* 1982 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_FLOAT);
/* 1983 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_DOUBLE);
/* 1984 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_BOOLEAN);
/* 1985 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_TIME);
/* 1986 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_DATE_TIME);
/* 1987 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_DURATION);
/* 1988 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_DATE);
/* 1989 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_G_MONTH);
/* 1990 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_G_YEAR);
/* 1991 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_G_YEAR_MONTH);
/* 1992 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_G_DAY);
/* 1993 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_G_MONTH_DAY);
/* 1994 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NAME);
/* 1995 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_QNAME);
/* 1996 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NCNAME);
/* 1997 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_ANY_URI);
/* 1998 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_ID);
/* 1999 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_IDREF);
/* 2000 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_IDREFS);
/* 2001 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_ENTITY);
/* 2002 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_ENTITIES);
/* 2003 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NOTATION);
/* 2004 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NMTOKEN);
/* 2005 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_NMTOKENS);
/*      */ 
/*      */     
/* 2008 */     _primitiveTypeNames.add(SchemaConstants.QNAME_TYPE_LANGUAGE);
/*      */ 
/*      */     
/* 2011 */     _soapTypeNames = new HashSet();
/* 2012 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_STRING);
/* 2013 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NORMALIZED_STRING);
/* 2014 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_TOKEN);
/* 2015 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_BYTE);
/* 2016 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_UNSIGNED_BYTE);
/* 2017 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_BASE64_BINARY);
/* 2018 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_HEX_BINARY);
/* 2019 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_INTEGER);
/* 2020 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_POSITIVE_INTEGER);
/* 2021 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NEGATIVE_INTEGER);
/* 2022 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NON_NEGATIVE_INTEGER);
/* 2023 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NON_POSITIVE_INTEGER);
/* 2024 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_INT);
/* 2025 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_UNSIGNED_INT);
/* 2026 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_LONG);
/* 2027 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_UNSIGNED_LONG);
/* 2028 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_SHORT);
/* 2029 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_UNSIGNED_SHORT);
/* 2030 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_DECIMAL);
/* 2031 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_FLOAT);
/* 2032 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_DOUBLE);
/* 2033 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_BOOLEAN);
/* 2034 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_TIME);
/* 2035 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_DATE_TIME);
/* 2036 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_DURATION);
/* 2037 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_DATE);
/* 2038 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_G_MONTH);
/* 2039 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_G_YEAR);
/* 2040 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_G_YEAR_MONTH);
/* 2041 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_G_DAY);
/* 2042 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_G_MONTH_DAY);
/* 2043 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NAME);
/* 2044 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_QNAME);
/* 2045 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NCNAME);
/* 2046 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_ANY_URI);
/* 2047 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_ID);
/* 2048 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_IDREF);
/* 2049 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_IDREFS);
/* 2050 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_ENTITY);
/* 2051 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_ENTITIES);
/* 2052 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NOTATION);
/* 2053 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NMTOKEN);
/* 2054 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_NMTOKENS);
/* 2055 */     _soapTypeNames.add(SOAPConstants.QNAME_TYPE_BASE64);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\schema\InternalSchemaBuilderBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */