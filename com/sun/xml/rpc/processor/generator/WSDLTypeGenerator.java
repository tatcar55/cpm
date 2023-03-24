/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*      */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Message;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
/*      */ import com.sun.xml.rpc.processor.model.Port;
/*      */ import com.sun.xml.rpc.processor.model.Service;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationEntry;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralTypeVisitor;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPTypeVisitor;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.modeler.rmi.LiteralSimpleTypeCreator;
/*      */ import com.sun.xml.rpc.processor.modeler.rmi.SOAPSimpleTypeCreatorBase;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*      */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*      */ import com.sun.xml.rpc.util.VersionUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.Definitions;
/*      */ import com.sun.xml.rpc.wsdl.document.Import;
/*      */ import com.sun.xml.rpc.wsdl.document.Types;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.Schema;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaAttribute;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaElement;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAP12Constants;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSDLTypeGenerator
/*      */ {
/*  113 */   private SOAPWSDLConstants soapWSDLConstants = null;
/*      */   
/*      */   private Model model;
/*      */   private WSDLDocument document;
/*      */   private Definitions definitions;
/*      */   private Set generatedTypes;
/*      */   private Set generatedElements;
/*      */   private Map nsSchemaMap;
/*      */   private Set actuallyImportedDocuments;
/*      */   private SOAPSimpleTypeCreatorBase soapTypes;
/*      */   private LiteralSimpleTypeCreator literalTypes;
/*      */   private SOAPVersion soapVer;
/*      */   private boolean isEncodedWsdl;
/*      */   private Properties options;
/*      */   private boolean log = false;
/*      */   
/*      */   private void log(String msg) {
/*  130 */     if (this.log) {
/*  131 */       System.out.println("[WSDLTypeModeler] " + msg);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public WSDLTypeGenerator(Model model, WSDLDocument document, Properties options) {
/*  138 */     this(model, document, options, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WSDLTypeGenerator(Model model, WSDLDocument document, Properties options, SOAPVersion ver) {
/*  146 */     init(ver);
/*  147 */     this.model = model;
/*  148 */     this.document = document;
/*  149 */     this.definitions = document.getDefinitions();
/*  150 */     this.options = options;
/*  151 */     this.generatedTypes = new HashSet();
/*  152 */     this.generatedElements = new HashSet();
/*  153 */     this.nsSchemaMap = new HashMap<Object, Object>();
/*  154 */     this.actuallyImportedDocuments = new HashSet();
/*  155 */     this.soapTypes = JAXRPCClassFactory.newInstance().createSOAPSimpleTypeCreator();
/*      */     
/*  157 */     this.literalTypes = new LiteralSimpleTypeCreator();
/*  158 */     init(ver);
/*      */   }
/*      */   
/*      */   private void init(SOAPVersion ver) {
/*  162 */     this.soapWSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(ver);
/*  163 */     this.soapVer = ver;
/*      */   }
/*      */   
/*      */   protected SOAPVersion getSOAPVersion(AbstractType type) {
/*  167 */     if (type.isSOAPType()) {
/*  168 */       String ver = type.getVersion();
/*  169 */       if (ver.equals(SOAPVersion.SOAP_11.toString())) {
/*  170 */         this.soapVer = SOAPVersion.SOAP_11;
/*  171 */       } else if (ver.equals(SOAPVersion.SOAP_12.toString())) {
/*  172 */         this.soapVer = SOAPVersion.SOAP_12;
/*      */       } 
/*  174 */     }  return this.soapVer;
/*      */   }
/*      */   
/*      */   protected String getSOAPEncodingNamespace(AbstractType type) {
/*  178 */     if (getSOAPVersion(type).equals(SOAPVersion.SOAP_11.toString())) {
/*  179 */       return "http://schemas.xmlsoap.org/soap/encoding/";
/*      */     }
/*  181 */     return "http://www.w3.org/2002/06/soap-encoding";
/*      */   }
/*      */   
/*      */   protected String getNamespacePrefix(AbstractType type) {
/*  185 */     if (SOAPVersion.SOAP_12.equals(type.getVersion())) {
/*  186 */       return "soap12-enc";
/*      */     }
/*  188 */     return "soap11-enc";
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
/*      */   protected QName getQNameAttrArrayType(AbstractType type) {
/*  201 */     String ver = null;
/*  202 */     if (type == null) {
/*  203 */       ver = this.soapVer.toString();
/*      */     } else {
/*  205 */       ver = type.getVersion();
/*  206 */     }  if (ver.equals(SOAPVersion.SOAP_11.toString())) {
/*  207 */       return SOAPConstants.QNAME_ATTR_ARRAY_TYPE;
/*      */     }
/*  209 */     return SOAP12Constants.QNAME_ATTR_ARRAY_TYPE;
/*      */   }
/*      */   
/*      */   public void run() throws Exception {
/*  213 */     this.isEncodedWsdl = false;
/*  214 */     Types types = new Types();
/*      */ 
/*      */     
/*  217 */     for (Iterator<Service> iterator1 = this.model.getServices(); iterator1.hasNext(); ) {
/*  218 */       Service service = iterator1.next();
/*      */       
/*  220 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/*  221 */         Port port = ports.next();
/*      */         
/*  223 */         Iterator<Operation> operations = port.getOperations();
/*  224 */         while (operations.hasNext()) {
/*      */           
/*  226 */           Operation operation = operations.next();
/*      */ 
/*      */           
/*  229 */           processTypesInMessage((Message)operation.getRequest());
/*  230 */           processTypesInMessage((Message)operation.getResponse());
/*      */ 
/*      */           
/*  233 */           Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*  234 */           faultSet.addAll(operation.getAllFaultsSet());
/*  235 */           Iterator<Fault> faults = faultSet.iterator();
/*  236 */           while (faults != null && faults.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  242 */             Fault fault = faults.next();
/*      */             
/*  244 */             processFault(fault);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  249 */     for (Iterator<AbstractType> iter = this.model.getExtraTypes(); iter.hasNext();) {
/*  250 */       processType(iter.next());
/*      */     }
/*      */ 
/*      */     
/*  254 */     for (Iterator<Service> services = this.model.getServices(); services.hasNext(); ) {
/*  255 */       Service service = services.next();
/*  256 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/*  257 */         Port port = ports.next();
/*  258 */         Iterator<Operation> operations = port.getOperations();
/*  259 */         while (operations.hasNext()) {
/*      */           
/*  261 */           Operation operation = operations.next();
/*  262 */           if (operation.getStyle() == SOAPStyle.DOCUMENT) {
/*  263 */             processElementsInMessage((Message)operation.getRequest());
/*  264 */             processElementsInMessage((Message)operation.getResponse());
/*      */           } 
/*  266 */           Iterator<Fault> faults = operation.getAllFaults();
/*  267 */           while (faults != null && faults.hasNext()) {
/*      */             
/*  269 */             Fault fault = faults.next();
/*  270 */             processElementInFault(fault);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  277 */     List<ImportedDocumentInfo> importedDocumentsInReverseOrder = new ArrayList();
/*  278 */     Iterator<ImportedDocumentInfo> iterator2 = this.actuallyImportedDocuments.iterator();
/*  279 */     while (iterator2.hasNext()) {
/*      */       
/*  281 */       ImportedDocumentInfo docInfo = iterator2.next();
/*  282 */       Import anImport = new Import();
/*  283 */       anImport.setNamespace(docInfo.getNamespace());
/*  284 */       anImport.setLocation(docInfo.getLocation());
/*  285 */       this.definitions.add(anImport);
/*  286 */       importedDocumentsInReverseOrder.add(0, docInfo);
/*      */     } 
/*      */     
/*  289 */     Iterator<Schema> iterator = this.nsSchemaMap.values().iterator();
/*  290 */     while (iterator.hasNext()) {
/*      */       
/*  292 */       Schema schema = iterator.next();
/*  293 */       Iterator definedEntities = schema.definedEntities();
/*      */ 
/*      */       
/*  296 */       if (definedEntities.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  305 */         SchemaElement schemaContent = schema.getContent();
/*  306 */         Iterator<ImportedDocumentInfo> iter2 = importedDocumentsInReverseOrder.iterator();
/*      */         
/*  308 */         while (iter2.hasNext()) {
/*      */           
/*  310 */           ImportedDocumentInfo docInfo = iter2.next();
/*      */           
/*  312 */           SchemaElement importElement = new SchemaElement(SchemaConstants.QNAME_IMPORT);
/*      */           
/*  314 */           importElement.addAttribute("namespace", docInfo.getNamespace());
/*      */ 
/*      */           
/*  317 */           importElement.addAttribute("schemaLocation", docInfo.getLocation());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  327 */           schemaContent.insertChildAtTop(importElement);
/*      */         } 
/*      */ 
/*      */         
/*  331 */         Iterator<String> iter3 = this.nsSchemaMap.keySet().iterator();
/*  332 */         while (iter3.hasNext()) {
/*      */           
/*  334 */           String nsURI = iter3.next();
/*  335 */           if (schema.getTargetNamespaceURI().equals(nsURI)) {
/*      */             continue;
/*      */           }
/*      */           
/*  339 */           Schema anotherSchema = (Schema)this.nsSchemaMap.get(nsURI);
/*  340 */           Iterator anotherSchemaDefinedEntities = anotherSchema.definedEntities();
/*      */           
/*  342 */           if (anotherSchemaDefinedEntities.hasNext()) {
/*  343 */             SchemaElement importElement = new SchemaElement(SchemaConstants.QNAME_IMPORT);
/*      */             
/*  345 */             importElement.addAttribute("namespace", nsURI);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  355 */             schemaContent.insertChildAtTop(importElement);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  360 */         if (this.isEncodedWsdl) {
/*  361 */           SchemaElement importElement = new SchemaElement(SchemaConstants.QNAME_IMPORT);
/*      */           
/*  363 */           importElement.addAttribute("namespace", "http://schemas.xmlsoap.org/soap/encoding/");
/*      */ 
/*      */           
/*  366 */           schemaContent.insertChildAtTop(importElement);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  373 */         types.addExtension((Extension)schema);
/*      */       } 
/*      */     } 
/*      */     
/*  377 */     this.definitions.setTypes(types);
/*      */   }
/*      */ 
/*      */   
/*      */   private void processTypesInMessage(Message message) throws Exception {
/*  382 */     if (message == null) {
/*      */       return;
/*      */     }
/*      */     
/*  386 */     for (Iterator<Block> iterator2 = message.getBodyBlocks(); iterator2.hasNext(); ) {
/*  387 */       Block block = iterator2.next();
/*      */       
/*  389 */       AbstractType type = block.getType();
/*      */       
/*  391 */       processType(type);
/*      */     } 
/*      */     
/*  394 */     for (Iterator<Block> iterator1 = message.getHeaderBlocks(); iterator1.hasNext(); ) {
/*  395 */       Block block = iterator1.next();
/*      */       
/*  397 */       AbstractType type = block.getType();
/*      */       
/*  399 */       processType(type);
/*      */     } 
/*  401 */     for (Iterator<Parameter> iter = message.getParameters(); iter.hasNext(); ) {
/*  402 */       Parameter param = iter.next();
/*      */       
/*  404 */       AbstractType type = param.getType();
/*      */       
/*  406 */       processType(type);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void processElementsInMessage(Message message) throws Exception {
/*  411 */     if (message == null) {
/*      */       return;
/*      */     }
/*      */     
/*  415 */     for (Iterator<Block> iter = message.getBodyBlocks(); iter.hasNext(); ) {
/*  416 */       Block block = iter.next();
/*      */ 
/*      */       
/*  419 */       QName name = block.getName();
/*  420 */       AbstractType type = block.getType();
/*      */       
/*  422 */       if (type.isLiteralType()) {
/*  423 */         processElement(name, (LiteralType)type);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void processElementInFault(Fault fault) throws Exception {
/*  430 */     if (fault.getElementName() != null && fault.getBlock().getType().isLiteralType())
/*      */     {
/*      */       
/*  433 */       processElement(fault.getElementName(), (LiteralType)fault.getBlock().getType());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processType(AbstractType type) throws Exception {
/*  441 */     boolean isReqOrRespStruct = (type instanceof RPCRequestOrderedStructureType || type instanceof RPCRequestOrderedStructureType || type instanceof RPCResponseStructureType);
/*      */ 
/*      */ 
/*      */     
/*  445 */     if (!isReqOrRespStruct && (type.getName() == null || this.generatedTypes.contains(type.getName()))) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     if (type.getName().getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema") || type.getName().getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/encoding/") || type.getName().getNamespaceURI().equals("http://www.w3.org/2002/06/soap-encoding")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  460 */     ImportedDocumentInfo docInfo = this.model.getImportedDocument(type.getName().getNamespaceURI());
/*      */ 
/*      */     
/*  463 */     if (docInfo != null && docInfo.getType() == 1) {
/*      */ 
/*      */       
/*  466 */       this.actuallyImportedDocuments.add(docInfo);
/*      */       
/*      */       return;
/*      */     } 
/*  470 */     Schema schema = (Schema)this.nsSchemaMap.get(type.getName().getNamespaceURI());
/*      */     
/*  472 */     if (schema == null) {
/*  473 */       schema = new Schema((AbstractDocument)this.document);
/*  474 */       schema.setTargetNamespaceURI(type.getName().getNamespaceURI());
/*  475 */       SchemaElement schemaElement = new SchemaElement(SchemaConstants.QNAME_SCHEMA);
/*      */       
/*  477 */       schemaElement.addAttribute("targetNamespace", schema.getTargetNamespaceURI());
/*      */ 
/*      */       
/*  480 */       schemaElement.addPrefix("", "http://www.w3.org/2001/XMLSchema");
/*  481 */       schemaElement.addPrefix("wsdl", "http://schemas.xmlsoap.org/wsdl/");
/*  482 */       schemaElement.addPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*  483 */       schemaElement.addPrefix(getNamespacePrefix(type), getSOAPEncodingNamespace(type));
/*      */ 
/*      */       
/*  486 */       schemaElement.addPrefix("tns", schema.getTargetNamespaceURI());
/*  487 */       schema.setContent(schemaElement);
/*  488 */       this.nsSchemaMap.put(type.getName().getNamespaceURI(), schema);
/*      */     } 
/*      */     
/*  491 */     if (!isReqOrRespStruct) {
/*  492 */       this.generatedTypes.add(type.getName());
/*      */     }
/*      */ 
/*      */     
/*  496 */     if (type.isLiteralType()) {
/*  497 */       processType((LiteralType)type, schema);
/*      */     } else {
/*  499 */       this.isEncodedWsdl = true;
/*  500 */       processType((SOAPType)type, schema);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processType(SOAPType type, final Schema schema) throws Exception {
/*  508 */     type.accept(new SOAPTypeVisitor()
/*      */         {
/*      */           public void visit(SOAPArrayType type) throws Exception {
/*  511 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  513 */             schema.getContent().addChild(complexType);
/*  514 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  517 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */             
/*  519 */             complexType.addChild(complexContent);
/*  520 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */             
/*  522 */             complexContent.addChild(restriction);
/*  523 */             restriction.addAttribute("base", WSDLTypeGenerator.this.getNamespacePrefix((AbstractType)type) + ":Array");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  528 */             SchemaElement attribute = new SchemaElement(SchemaConstants.QNAME_ATTRIBUTE);
/*      */             
/*  530 */             restriction.addChild(attribute);
/*  531 */             attribute.addAttribute("ref", WSDLTypeGenerator.this.getQNameAttrArrayType((AbstractType)type));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  538 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  543 */             SchemaAttribute wsdlArrayTypeAttribute = new SchemaAttribute(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getLocalPart());
/*      */ 
/*      */             
/*  546 */             wsdlArrayTypeAttribute.setNamespaceURI(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getNamespaceURI());
/*      */ 
/*      */             
/*  549 */             String arrayTypeString = attribute.asString(type.getElementType().getName()) + "[]";
/*      */             
/*  551 */             wsdlArrayTypeAttribute.setValue(arrayTypeString);
/*  552 */             attribute.addAttribute(wsdlArrayTypeAttribute);
/*      */             
/*  554 */             WSDLTypeGenerator.this.processType((AbstractType)type.getElementType());
/*      */           }
/*      */           
/*      */           public void visit(SOAPCustomType type) throws Exception {
/*  558 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  560 */             schema.getContent().addChild(complexType);
/*  561 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  564 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/*  566 */             complexType.addChild(complexContent);
/*  567 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_ANY);
/*      */             
/*  569 */             complexContent.addChild(restriction);
/*  570 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(SOAPEnumerationType type) throws Exception {
/*  577 */             SchemaElement simpleType = new SchemaElement(SchemaConstants.QNAME_SIMPLE_TYPE);
/*      */             
/*  579 */             schema.getContent().addChild(simpleType);
/*  580 */             simpleType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */             
/*  584 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */             
/*  586 */             simpleType.addChild(restriction);
/*  587 */             restriction.addAttribute("base", type.getBaseType().getName());
/*      */ 
/*      */             
/*  590 */             JavaEnumerationType javaType = (JavaEnumerationType)type.getJavaType();
/*      */             
/*  592 */             for (Iterator<JavaEnumerationEntry> iter = javaType.getEntries(); iter.hasNext(); ) {
/*  593 */               JavaEnumerationEntry entry = iter.next();
/*      */               
/*  595 */               SchemaElement enumeration = new SchemaElement(SchemaConstants.QNAME_ENUMERATION);
/*      */               
/*  597 */               enumeration.addAttribute("value", entry.getLiteralValue());
/*      */ 
/*      */               
/*  600 */               restriction.addChild(enumeration);
/*      */             } 
/*  602 */             schema.defineEntity(simpleType, SchemaKinds.XSD_TYPE, type.getName());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(SOAPSimpleType type) throws Exception {
/*  609 */             if (type.getName().getNamespaceURI().equals("http://java.sun.com/jax-rpc-ri/internal"))
/*      */             {
/*      */ 
/*      */               
/*  613 */               if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY)) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  618 */                 writeJAXRpcMapEntryType();
/*  619 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_MAP)) {
/*      */ 
/*      */                 
/*  622 */                 writeMapType((AbstractType)type);
/*  623 */                 WSDLTypeGenerator.this.processType((AbstractType)WSDLTypeGenerator.this.soapTypes.JAX_RPC_MAP_ENTRY_SOAPTYPE);
/*  624 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_HASH_MAP) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_TREE_MAP) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_HASHTABLE) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_PROPERTIES)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  634 */                 writeTypeSubtype((AbstractType)type, InternalEncodingConstants.QNAME_TYPE_MAP);
/*      */ 
/*      */                 
/*  637 */                 WSDLTypeGenerator.this.processType((AbstractType)WSDLTypeGenerator.this.soapTypes.MAP_SOAPTYPE);
/*  638 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_COLLECTION)) {
/*      */ 
/*      */                 
/*  641 */                 writeCollectionType((AbstractType)type);
/*  642 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_LIST) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_SET)) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  647 */                 writeTypeSubtype((AbstractType)type, InternalEncodingConstants.QNAME_TYPE_COLLECTION);
/*      */ 
/*      */                 
/*  650 */                 WSDLTypeGenerator.this.processType((AbstractType)WSDLTypeGenerator.this.soapTypes.COLLECTION_SOAPTYPE);
/*  651 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_ARRAY_LIST) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_VECTOR) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_STACK) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_LINKED_LIST)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  661 */                 writeTypeSubtype((AbstractType)type, InternalEncodingConstants.QNAME_TYPE_LIST);
/*      */ 
/*      */                 
/*  664 */                 WSDLTypeGenerator.this.processType((AbstractType)WSDLTypeGenerator.this.soapTypes.LIST_SOAPTYPE);
/*  665 */               } else if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_HASH_SET) || type.getName().equals(InternalEncodingConstants.QNAME_TYPE_TREE_SET)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  671 */                 writeTypeSubtype((AbstractType)type, InternalEncodingConstants.QNAME_TYPE_SET);
/*      */ 
/*      */                 
/*  674 */                 WSDLTypeGenerator.this.processType((AbstractType)WSDLTypeGenerator.this.soapTypes.SET_SOAPTYPE);
/*      */               } else {
/*      */                 
/*  677 */                 SchemaElement simpleType = new SchemaElement(SchemaConstants.QNAME_SIMPLE_TYPE);
/*      */ 
/*      */                 
/*  680 */                 schema.getContent().addChild(simpleType);
/*  681 */                 simpleType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */                 
/*  684 */                 SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */ 
/*      */                 
/*  687 */                 simpleType.addChild(restriction);
/*      */                 
/*  689 */                 QName baseType = SchemaConstants.QNAME_TYPE_BASE64_BINARY;
/*      */                 
/*  691 */                 if (type.getName().equals(InternalEncodingConstants.QNAME_TYPE_SOURCE))
/*      */                 {
/*      */ 
/*      */                   
/*  695 */                   baseType = SchemaConstants.QNAME_TYPE_STRING;
/*      */                 }
/*  697 */                 restriction.addAttribute("base", baseType);
/*      */ 
/*      */                 
/*  700 */                 schema.defineEntity(simpleType, SchemaKinds.XSD_TYPE, type.getName());
/*      */               } 
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           private void writeJAXRpcMapEntryType() throws Exception {
/*  709 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  711 */             schema.getContent().addChild(complexType);
/*  712 */             complexType.addAttribute("name", InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY.getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  717 */             SchemaElement sequenceParent = complexType;
/*  718 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/*  720 */             sequenceParent.addChild(sequence);
/*  721 */             SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */             
/*  723 */             sequence.addChild(element);
/*  724 */             element.addAttribute("name", InternalEncodingConstants.JAX_RPC_MAP_ENTRY_KEY_NAME.getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  729 */             element.addAttribute("type", SchemaConstants.QNAME_TYPE_URTYPE);
/*      */ 
/*      */             
/*  732 */             element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*  733 */             sequence.addChild(element);
/*  734 */             element.addAttribute("name", InternalEncodingConstants.JAX_RPC_MAP_ENTRY_VALUE_NAME.getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  739 */             element.addAttribute("type", SchemaConstants.QNAME_TYPE_URTYPE);
/*      */ 
/*      */             
/*  742 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           private void writeMapType(AbstractType type) throws Exception {
/*  749 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  751 */             schema.getContent().addChild(complexType);
/*  752 */             complexType.addAttribute("name", InternalEncodingConstants.QNAME_TYPE_MAP.getLocalPart());
/*      */ 
/*      */             
/*  755 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */             
/*  757 */             complexType.addChild(complexContent);
/*  758 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */             
/*  760 */             complexContent.addChild(restriction);
/*  761 */             restriction.addAttribute("base", WSDLTypeGenerator.this.getNamespacePrefix(type) + ":Array");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  766 */             SchemaElement attribute = new SchemaElement(SchemaConstants.QNAME_ATTRIBUTE);
/*      */             
/*  768 */             restriction.addChild(attribute);
/*      */             
/*  770 */             attribute.addAttribute("ref", WSDLTypeGenerator.this.getQNameAttrArrayType(null));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  777 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, InternalEncodingConstants.QNAME_TYPE_MAP);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  782 */             SchemaAttribute wsdlArrayTypeAttribute = new SchemaAttribute(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getLocalPart());
/*      */ 
/*      */             
/*  785 */             wsdlArrayTypeAttribute.setNamespaceURI(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getNamespaceURI());
/*      */ 
/*      */             
/*  788 */             String arrayTypeString = attribute.asString(InternalEncodingConstants.QNAME_TYPE_JAX_RPC_MAP_ENTRY) + "[]";
/*      */ 
/*      */ 
/*      */             
/*  792 */             wsdlArrayTypeAttribute.setValue(arrayTypeString);
/*  793 */             attribute.addAttribute(wsdlArrayTypeAttribute);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           private void writeTypeSubtype(AbstractType type, QName superTypeName) throws Exception {
/*  800 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  802 */             schema.getContent().addChild(complexType);
/*  803 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  806 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */             
/*  808 */             complexType.addChild(complexContent);
/*  809 */             SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */             
/*  811 */             complexContent.addChild(extension);
/*  812 */             extension.addAttribute("base", superTypeName);
/*      */ 
/*      */             
/*  815 */             SchemaElement sequenceParent = extension;
/*  816 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/*  818 */             sequenceParent.addChild(sequence);
/*  819 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           private void writeCollectionType(AbstractType type) throws Exception {
/*  827 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  829 */             schema.getContent().addChild(complexType);
/*  830 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  833 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */             
/*  835 */             complexType.addChild(complexContent);
/*  836 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */             
/*  838 */             complexContent.addChild(restriction);
/*  839 */             restriction.addAttribute("base", WSDLTypeGenerator.this.getNamespacePrefix(type) + ":Array");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  844 */             SchemaElement attribute = new SchemaElement(SchemaConstants.QNAME_ATTRIBUTE);
/*      */             
/*  846 */             restriction.addChild(attribute);
/*  847 */             attribute.addAttribute("ref", WSDLTypeGenerator.this.getQNameAttrArrayType(type));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  854 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  859 */             SchemaAttribute wsdlArrayTypeAttribute = new SchemaAttribute(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getLocalPart());
/*      */ 
/*      */             
/*  862 */             wsdlArrayTypeAttribute.setNamespaceURI(WSDLConstants.QNAME_ATTR_ARRAY_TYPE.getNamespaceURI());
/*      */ 
/*      */             
/*  865 */             String arrayTypeString = attribute.asString(SchemaConstants.QNAME_TYPE_URTYPE) + "[]";
/*      */ 
/*      */             
/*  868 */             wsdlArrayTypeAttribute.setValue(arrayTypeString);
/*  869 */             attribute.addAttribute(wsdlArrayTypeAttribute);
/*      */           }
/*      */ 
/*      */           
/*      */           public void visit(SOAPAnyType type) throws Exception {}
/*      */ 
/*      */           
/*      */           public void visit(SOAPListType type) throws Exception {}
/*      */           
/*      */           public void visit(SOAPOrderedStructureType type) throws Exception {
/*  879 */             visit((SOAPStructureType)type);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(SOAPUnorderedStructureType type) throws Exception {
/*  885 */             SOAPStructureType parentType = type.getParentType();
/*      */             
/*  887 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  889 */             schema.getContent().addChild(complexType);
/*  890 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  893 */             SchemaElement sequenceParent = complexType;
/*  894 */             if (parentType != null) {
/*  895 */               SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */ 
/*      */               
/*  898 */               complexType.addChild(complexContent);
/*  899 */               SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */               
/*  901 */               complexContent.addChild(extension);
/*  902 */               extension.addAttribute("base", parentType.getName());
/*      */ 
/*      */               
/*  905 */               sequenceParent = extension;
/*      */             } 
/*  907 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_ALL);
/*      */             
/*  909 */             sequenceParent.addChild(sequence);
/*  910 */             for (Iterator<SOAPStructureMember> iterator1 = type.getMembers(); iterator1.hasNext(); ) {
/*  911 */               SOAPStructureMember member = iterator1.next();
/*      */               
/*  913 */               if (member.isInherited() && type.getParentType() != null) {
/*      */                 continue;
/*      */               }
/*  916 */               SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */               
/*  918 */               sequence.addChild(element);
/*  919 */               element.addAttribute("name", member.getName().getLocalPart());
/*      */ 
/*      */               
/*  922 */               element.addAttribute("type", member.getType().getName());
/*      */             } 
/*      */ 
/*      */             
/*  926 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  931 */             if (parentType != null) {
/*  932 */               WSDLTypeGenerator.this.processType((AbstractType)type.getParentType());
/*      */             }
/*      */             
/*  935 */             for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/*  936 */               SOAPStructureMember member = iter.next();
/*      */               
/*  938 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/*  940 */             Iterator<AbstractType> subtypes = type.getSubtypes();
/*  941 */             if (subtypes != null) {
/*  942 */               while (subtypes.hasNext()) {
/*  943 */                 WSDLTypeGenerator.this.processType(subtypes.next());
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*      */           
/*      */           public void visit(RPCRequestOrderedStructureType type) throws Exception {
/*  950 */             for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/*  951 */               SOAPStructureMember member = iter.next();
/*      */               
/*  953 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void visit(RPCRequestUnorderedStructureType type) throws Exception {
/*  959 */             for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/*  960 */               SOAPStructureMember member = iter.next();
/*      */               
/*  962 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/*      */           }
/*      */           
/*      */           public void visit(RPCResponseStructureType type) throws Exception {
/*  967 */             for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/*  968 */               SOAPStructureMember member = iter.next();
/*      */               
/*  970 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/*      */           }
/*      */           
/*      */           protected void visit(SOAPStructureType type) throws Exception {
/*  975 */             SOAPStructureType parentType = type.getParentType();
/*      */             
/*  977 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/*  979 */             schema.getContent().addChild(complexType);
/*  980 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/*  983 */             SchemaElement sequenceParent = complexType;
/*  984 */             if (parentType != null) {
/*  985 */               SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */ 
/*      */               
/*  988 */               complexType.addChild(complexContent);
/*  989 */               SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */               
/*  991 */               complexContent.addChild(extension);
/*  992 */               extension.addAttribute("base", parentType.getName());
/*      */ 
/*      */               
/*  995 */               sequenceParent = extension;
/*      */             } 
/*  997 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/*  999 */             sequenceParent.addChild(sequence);
/* 1000 */             for (Iterator<SOAPStructureMember> iterator1 = type.getMembers(); iterator1.hasNext(); ) {
/* 1001 */               SOAPStructureMember member = iterator1.next();
/*      */               
/* 1003 */               if (member.isInherited() && type.getParentType() != null) {
/*      */                 continue;
/*      */               }
/* 1006 */               SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */               
/* 1008 */               sequence.addChild(element);
/* 1009 */               element.addAttribute("name", member.getName().getLocalPart());
/*      */ 
/*      */               
/* 1012 */               element.addAttribute("type", member.getType().getName());
/*      */             } 
/*      */ 
/*      */             
/* 1016 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1021 */             if (parentType != null) {
/* 1022 */               WSDLTypeGenerator.this.processType((AbstractType)type.getParentType());
/*      */             }
/*      */             
/* 1025 */             for (Iterator<SOAPStructureMember> iter = type.getMembers(); iter.hasNext(); ) {
/* 1026 */               SOAPStructureMember member = iter.next();
/*      */               
/* 1028 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/* 1030 */             Iterator<AbstractType> subtypes = type.getSubtypes();
/* 1031 */             if (subtypes != null) {
/* 1032 */               while (subtypes.hasNext()) {
/* 1033 */                 WSDLTypeGenerator.this.processType(subtypes.next());
/*      */               }
/*      */             }
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processType(LiteralType type, final Schema schema) throws Exception {
/* 1043 */     type.accept(new LiteralTypeVisitor()
/*      */         {
/*      */           public void visit(LiteralArrayType type) throws Exception {
/* 1046 */             LiteralType literalType = type.getElementType();
/* 1047 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/* 1049 */             schema.getContent().addChild(complexType);
/* 1050 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */             
/* 1053 */             SchemaElement sequenceParent = complexType;
/* 1054 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/* 1056 */             sequenceParent.addChild(sequence);
/* 1057 */             SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */             
/* 1059 */             sequence.addChild(element);
/* 1060 */             element.addAttribute("name", literalType.getName().getLocalPart());
/*      */ 
/*      */             
/* 1063 */             element.addAttribute("type", literalType.getName());
/*      */ 
/*      */             
/* 1066 */             if (literalType.isNillable()) {
/* 1067 */               element.addAttribute("nillable", "true");
/*      */             }
/*      */ 
/*      */             
/* 1071 */             element.addAttribute("minOccurs", "0");
/*      */ 
/*      */             
/* 1074 */             element.addAttribute("maxOccurs", "unbounded");
/*      */ 
/*      */             
/* 1077 */             schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1082 */             WSDLTypeGenerator.this.processType((AbstractType)literalType);
/*      */           }
/*      */           
/*      */           public void visit(LiteralArrayWrapperType type) throws Exception {
/* 1086 */             visitStructuredType(SchemaConstants.QNAME_SEQUENCE, (LiteralStructuredType)type);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(LiteralSimpleType type) throws Exception {}
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(LiteralListType type) throws Exception {}
/*      */ 
/*      */           
/*      */           public void visit(LiteralIDType type) throws Exception {}
/*      */ 
/*      */           
/*      */           public void visit(LiteralSequenceType type) throws Exception {
/* 1102 */             visitStructuredType(SchemaConstants.QNAME_SEQUENCE, (LiteralStructuredType)type);
/*      */           }
/*      */           
/*      */           public void visit(LiteralAllType type) throws Exception {
/* 1106 */             visitStructuredType(SchemaConstants.QNAME_ALL, (LiteralStructuredType)type);
/*      */           }
/*      */           
/*      */           public void visit(LiteralEnumerationType type) throws Exception {
/* 1110 */             SchemaElement simpleType = new SchemaElement(SchemaConstants.QNAME_SIMPLE_TYPE);
/*      */             
/* 1112 */             schema.getContent().addChild(simpleType);
/* 1113 */             simpleType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */             
/* 1117 */             SchemaElement restriction = new SchemaElement(SchemaConstants.QNAME_RESTRICTION);
/*      */             
/* 1119 */             simpleType.addChild(restriction);
/* 1120 */             restriction.addAttribute("base", type.getBaseType().getName());
/*      */ 
/*      */             
/* 1123 */             JavaEnumerationType javaType = (JavaEnumerationType)type.getJavaType();
/*      */             
/* 1125 */             for (Iterator<JavaEnumerationEntry> iter = javaType.getEntries(); iter.hasNext(); ) {
/* 1126 */               JavaEnumerationEntry entry = iter.next();
/*      */               
/* 1128 */               SchemaElement enumeration = new SchemaElement(SchemaConstants.QNAME_ENUMERATION);
/*      */               
/* 1130 */               enumeration.addAttribute("value", entry.getLiteralValue());
/*      */ 
/*      */               
/* 1133 */               restriction.addChild(enumeration);
/*      */             } 
/* 1135 */             schema.defineEntity(simpleType, SchemaKinds.XSD_TYPE, type.getName());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(LiteralFragmentType type) throws Exception {
/* 1142 */             SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */             
/* 1144 */             schema.getContent().addChild(complexType);
/* 1145 */             complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */ 
/*      */             
/* 1149 */             SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */             
/* 1151 */             complexType.addChild(complexContent);
/* 1152 */             SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */             
/* 1154 */             complexContent.addChild(extension);
/* 1155 */             extension.addAttribute("base", SchemaConstants.QNAME_TYPE_URTYPE);
/*      */ 
/*      */             
/* 1158 */             SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */             
/* 1160 */             extension.addChild(sequence);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           protected void visitStructuredType(QName compositor, LiteralStructuredType type) throws Exception {
/* 1167 */             LiteralStructuredType literalStructuredType = type.getParentType();
/*      */             
/* 1169 */             if (!type.isRpcWrapper()) {
/* 1170 */               SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */               
/* 1172 */               schema.getContent().addChild(complexType);
/* 1173 */               complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */               
/* 1176 */               SchemaElement sequenceParent = complexType;
/* 1177 */               if (literalStructuredType != null) {
/* 1178 */                 SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */ 
/*      */                 
/* 1181 */                 complexType.addChild(complexContent);
/* 1182 */                 SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */                 
/* 1184 */                 complexContent.addChild(extension);
/* 1185 */                 extension.addAttribute("base", literalStructuredType.getName());
/*      */ 
/*      */                 
/* 1188 */                 sequenceParent = extension;
/*      */               } 
/* 1190 */               SchemaElement sequence = new SchemaElement(compositor);
/* 1191 */               sequenceParent.addChild(sequence);
/* 1192 */               Iterator<LiteralElementMember> iterator1 = type.getElementMembers();
/* 1193 */               while (iterator1.hasNext()) {
/*      */                 
/* 1195 */                 LiteralElementMember member = iterator1.next();
/*      */                 
/* 1197 */                 if (member.isInherited() && type.getParentType() != null) {
/*      */                   continue;
/*      */                 }
/*      */                 
/* 1201 */                 SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */                 
/* 1203 */                 sequence.addChild(element);
/* 1204 */                 element.addAttribute("name", member.getName().getLocalPart());
/*      */ 
/*      */                 
/* 1207 */                 element.addAttribute("type", member.getType().getName());
/*      */ 
/*      */                 
/* 1210 */                 if (member.isNillable()) {
/* 1211 */                   element.addAttribute("nillable", "true");
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
/* 1222 */                 if (member.isRepeated()) {
/* 1223 */                   element.addAttribute("minOccurs", "0");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 1233 */                   element.addAttribute("maxOccurs", "unbounded");
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
/* 1252 */               schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1258 */             Iterator<LiteralElementMember> iterator = type.getElementMembers();
/* 1259 */             while (iterator.hasNext()) {
/*      */               
/* 1261 */               LiteralElementMember member = iterator.next();
/*      */               
/* 1263 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/*      */             
/* 1266 */             Iterator<LiteralAttributeMember> iter = type.getAttributeMembers();
/* 1267 */             while (iter.hasNext()) {
/*      */               
/* 1269 */               LiteralAttributeMember member = iter.next();
/*      */               
/* 1271 */               WSDLTypeGenerator.this.processType((AbstractType)member.getType());
/*      */             } 
/* 1273 */             if (literalStructuredType != null) {
/* 1274 */               WSDLTypeGenerator.this.processType((AbstractType)type.getParentType());
/*      */             }
/*      */             
/* 1277 */             Iterator<AbstractType> subtypes = type.getSubtypes();
/* 1278 */             if (subtypes != null) {
/* 1279 */               while (subtypes.hasNext()) {
/* 1280 */                 WSDLTypeGenerator.this.processType(subtypes.next());
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public void visit(LiteralAttachmentType type) throws Exception {}
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processFault(Fault fault) throws Exception {
/* 1294 */     AbstractType faultType, type = fault.getBlock().getType();
/*      */     
/* 1296 */     if (type instanceof SOAPStructureType || type instanceof LiteralStructuredType) {
/*      */       
/* 1298 */       faultType = type;
/*      */     } else {
/* 1300 */       faultType = (AbstractType)fault.getJavaException().getOwner();
/*      */     } 
/* 1302 */     if (faultType.getName() == null || this.generatedTypes.contains(faultType.getName())) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1307 */     if ((type.getName().getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema") || type.getName().getNamespaceURI().equals("http://www.w3.org/2002/06/soap-encoding") || type.getName().getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/encoding/")) && fault.getAllFaults() == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1318 */     ImportedDocumentInfo docInfo = this.model.getImportedDocument(type.getName().getNamespaceURI());
/*      */ 
/*      */     
/* 1321 */     if (docInfo != null && docInfo.getType() == 1) {
/*      */ 
/*      */       
/* 1324 */       this.actuallyImportedDocuments.add(docInfo);
/*      */       
/*      */       return;
/*      */     } 
/* 1328 */     Schema schema = (Schema)this.nsSchemaMap.get(faultType.getName().getNamespaceURI());
/*      */     
/* 1330 */     if (schema == null) {
/* 1331 */       schema = new Schema((AbstractDocument)this.document);
/* 1332 */       schema.setTargetNamespaceURI(type.getName().getNamespaceURI());
/* 1333 */       SchemaElement schemaElement = new SchemaElement(SchemaConstants.QNAME_SCHEMA);
/*      */       
/* 1335 */       schemaElement.addAttribute("targetNamespace", schema.getTargetNamespaceURI());
/*      */ 
/*      */       
/* 1338 */       schemaElement.addPrefix("", "http://www.w3.org/2001/XMLSchema");
/* 1339 */       schemaElement.addPrefix("wsdl", "http://schemas.xmlsoap.org/wsdl/");
/* 1340 */       schemaElement.addPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/* 1341 */       schemaElement.addPrefix(getNamespacePrefix(type), getSOAPEncodingNamespace(type));
/*      */ 
/*      */       
/* 1344 */       schemaElement.addPrefix("tns", schema.getTargetNamespaceURI());
/* 1345 */       schema.setContent(schemaElement);
/* 1346 */       this.nsSchemaMap.put(type.getName().getNamespaceURI(), schema);
/*      */     } 
/* 1348 */     this.generatedTypes.add(type.getName());
/* 1349 */     processFault(fault, schema);
/*      */   }
/*      */ 
/*      */   
/*      */   private void processFault(Fault fault, Schema schema) throws Exception {
/* 1354 */     Fault parentFault = fault.getParentFault();
/* 1355 */     AbstractType type = fault.getBlock().getType();
/* 1356 */     AbstractType parentType = (parentFault != null) ? parentFault.getBlock().getType() : null;
/*      */ 
/*      */     
/* 1359 */     SchemaElement complexType = new SchemaElement(SchemaConstants.QNAME_COMPLEX_TYPE);
/*      */     
/* 1361 */     schema.getContent().addChild(complexType);
/* 1362 */     boolean deserializeToDetail = false;
/* 1363 */     if (type instanceof SOAPStructureType || type instanceof LiteralStructuredType) {
/*      */       
/* 1365 */       complexType.addAttribute("name", type.getName().getLocalPart());
/*      */ 
/*      */       
/* 1368 */       deserializeToDetail = SOAPObjectSerializerGenerator.deserializeToDetail(type);
/*      */     } else {
/*      */       
/* 1371 */       JavaException javaException = fault.getJavaException();
/* 1372 */       String localName = null;
/* 1373 */       if (type instanceof SOAPType) {
/* 1374 */         localName = ((SOAPType)javaException.getOwner()).getName().getLocalPart();
/*      */ 
/*      */       
/*      */       }
/* 1378 */       else if (type instanceof LiteralType) {
/* 1379 */         localName = ((LiteralType)javaException.getOwner()).getName().getLocalPart();
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1384 */         throw new GeneratorException("generator.unsupported.type.encountered", new Object[] { type.getName().getLocalPart(), type.getName().getNamespaceURI() });
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1390 */       complexType.addAttribute("name", localName);
/*      */     } 
/*      */ 
/*      */     
/* 1394 */     SchemaElement sequenceParent = complexType;
/* 1395 */     if (parentType != null) {
/* 1396 */       SchemaElement complexContent = new SchemaElement(SchemaConstants.QNAME_COMPLEX_CONTENT);
/*      */       
/* 1398 */       complexType.addChild(complexContent);
/* 1399 */       SchemaElement extension = new SchemaElement(SchemaConstants.QNAME_EXTENSION);
/*      */       
/* 1401 */       complexContent.addChild(extension);
/* 1402 */       if (parentType instanceof SOAPStructureType) {
/* 1403 */         extension.addAttribute("base", parentType.getName());
/*      */       }
/*      */       else {
/*      */         
/* 1407 */         JavaException javaException = parentFault.getJavaException();
/* 1408 */         QName ownerName = ((AbstractType)javaException.getOwner()).getName();
/*      */         
/* 1410 */         extension.addAttribute("base", ownerName);
/*      */       } 
/*      */ 
/*      */       
/* 1414 */       sequenceParent = extension;
/*      */     } 
/* 1416 */     SchemaElement sequence = new SchemaElement(SchemaConstants.QNAME_SEQUENCE);
/*      */     
/* 1418 */     sequenceParent.addChild(sequence);
/* 1419 */     if (type instanceof SOAPStructureType) {
/* 1420 */       Iterator<SOAPStructureMember> iter = ((SOAPStructureType)type).getMembers();
/* 1421 */       while (iter.hasNext()) {
/*      */         
/* 1423 */         SOAPStructureMember member = iter.next();
/* 1424 */         if (member.isInherited() && parentFault != null) {
/*      */           continue;
/*      */         }
/*      */         
/* 1428 */         addChildElement(sequence, member.getName(), (AbstractType)member.getType(), VersionUtil.isVersion11(this.options.getProperty("sourceVersion")), false);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1433 */       schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */ 
/*      */     
/*      */     }
/* 1437 */     else if (type instanceof LiteralStructuredType) {
/* 1438 */       Iterator<LiteralElementMember> iter = ((LiteralStructuredType)type).getElementMembers();
/*      */       
/* 1440 */       while (iter.hasNext()) {
/*      */         
/* 1442 */         LiteralElementMember member = iter.next();
/*      */         
/* 1444 */         if (member.isInherited() && parentFault != null) {
/*      */           continue;
/*      */         }
/* 1447 */         addChildElement(sequence, member.getName(), (AbstractType)member.getType(), member.isNillable(), member.isRepeated());
/*      */       } 
/*      */ 
/*      */       
/* 1451 */       schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, type.getName());
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1456 */       JavaException javaException = fault.getJavaException();
/* 1457 */       AbstractType ownerType = (AbstractType)javaException.getOwner();
/* 1458 */       if (ownerType instanceof SOAPType) {
/* 1459 */         SOAPStructureType soapStruct = (SOAPStructureType)ownerType;
/* 1460 */         Iterator<SOAPStructureMember> iter = soapStruct.getMembers();
/* 1461 */         SOAPStructureMember member = iter.next();
/* 1462 */         if (!member.isInherited() || fault.getParentFault() == null)
/*      */         {
/* 1464 */           addChildElement(sequence, member.getName(), (AbstractType)member.getType(), this.model.getSource().equals("1.1"), false);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/* 1472 */         LiteralSequenceType literalStruct = (LiteralSequenceType)ownerType;
/*      */         
/* 1474 */         Iterator<LiteralElementMember> iter = literalStruct.getElementMembers();
/* 1475 */         LiteralElementMember member = iter.next();
/*      */         
/* 1477 */         if (fault.getParentFault() == null) {
/* 1478 */           addChildElement(sequence, member.getName(), (AbstractType)member.getType(), member.isNillable(), member.isRepeated());
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1486 */       schema.defineEntity(complexType, SchemaKinds.XSD_TYPE, ownerType.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1492 */     if (parentType != null) {
/* 1493 */       processFault(parentFault);
/*      */     }
/*      */     
/* 1496 */     Iterator<Fault> subFaults = fault.getSortedSubfaults();
/* 1497 */     while (subFaults.hasNext()) {
/* 1498 */       Fault subFault = subFaults.next();
/* 1499 */       processFault(subFault);
/*      */     } 
/* 1501 */     if (type instanceof SOAPStructureType) {
/* 1502 */       Iterator<SOAPStructureMember> iter = ((SOAPStructureType)type).getMembers();
/* 1503 */       while (iter.hasNext()) {
/*      */         
/* 1505 */         SOAPStructureMember member = iter.next();
/* 1506 */         processType((AbstractType)member.getType());
/*      */       } 
/* 1508 */       Iterator<AbstractType> subtypes = ((SOAPStructureType)type).getSubtypes();
/* 1509 */       if (subtypes != null) {
/* 1510 */         while (subtypes.hasNext()) {
/* 1511 */           processType(subtypes.next());
/*      */         }
/*      */       }
/* 1514 */     } else if (type instanceof LiteralStructuredType) {
/* 1515 */       Iterator<LiteralElementMember> iter = ((LiteralStructuredType)type).getElementMembers();
/*      */       
/* 1517 */       while (iter.hasNext()) {
/*      */         
/* 1519 */         LiteralElementMember member = iter.next();
/*      */         
/* 1521 */         processType((AbstractType)member.getType());
/*      */       } 
/* 1523 */       Iterator<AbstractType> subtypes = ((LiteralStructuredType)type).getSubtypes();
/* 1524 */       if (subtypes != null) {
/* 1525 */         while (subtypes.hasNext()) {
/* 1526 */           processType(subtypes.next());
/*      */         }
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
/*      */   private void addChildElement(SchemaElement sequence, QName name, AbstractType type, boolean isNillable, boolean isRepeated) {
/* 1539 */     SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */     
/* 1541 */     sequence.addChild(element);
/* 1542 */     element.addAttribute("name", name.getLocalPart());
/*      */ 
/*      */     
/* 1545 */     element.addAttribute("type", type.getName());
/*      */ 
/*      */     
/* 1548 */     if (isNillable && !isRepeated) {
/* 1549 */       element.addAttribute("nillable", "true");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1554 */     if (isRepeated) {
/* 1555 */       element.addAttribute("minOccurs", "0");
/*      */ 
/*      */       
/* 1558 */       element.addAttribute("maxOccurs", "unbounded");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processElement(QName name, LiteralType type) throws Exception {
/* 1566 */     if (this.generatedElements.contains(name)) {
/*      */       return;
/*      */     }
/* 1569 */     if (name.getNamespaceURI().equals("http://www.w3.org/2001/XMLSchema") || name.getNamespaceURI().equals("http://schemas.xmlsoap.org/soap/encoding/") || name.getNamespaceURI().equals("http://www.w3.org/2002/06/soap-encoding")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1575 */     ImportedDocumentInfo docInfo = this.model.getImportedDocument(name.getNamespaceURI());
/*      */ 
/*      */     
/* 1578 */     if (docInfo != null && docInfo.getType() == 1) {
/*      */ 
/*      */       
/* 1581 */       this.actuallyImportedDocuments.add(docInfo);
/*      */       
/*      */       return;
/*      */     } 
/* 1585 */     Schema schema = (Schema)this.nsSchemaMap.get(name.getNamespaceURI());
/* 1586 */     if (schema == null) {
/* 1587 */       schema = new Schema((AbstractDocument)this.document);
/* 1588 */       schema.setTargetNamespaceURI(name.getNamespaceURI());
/* 1589 */       SchemaElement schemaElement = new SchemaElement(SchemaConstants.QNAME_SCHEMA);
/*      */       
/* 1591 */       schemaElement.addAttribute("targetNamespace", schema.getTargetNamespaceURI());
/*      */ 
/*      */       
/* 1594 */       schemaElement.addPrefix("", "http://www.w3.org/2001/XMLSchema");
/* 1595 */       schemaElement.addPrefix("wsdl", "http://schemas.xmlsoap.org/wsdl/");
/* 1596 */       schemaElement.addPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*      */       
/* 1598 */       schemaElement.addPrefix(getNamespacePrefix((AbstractType)type), getSOAPEncodingNamespace((AbstractType)type));
/*      */ 
/*      */ 
/*      */       
/* 1602 */       schemaElement.addPrefix("tns", schema.getTargetNamespaceURI());
/* 1603 */       schema.setContent(schemaElement);
/* 1604 */       this.nsSchemaMap.put(name.getNamespaceURI(), schema);
/*      */     } 
/*      */     
/* 1607 */     this.generatedElements.add(name);
/*      */     
/* 1609 */     SchemaElement element = new SchemaElement(SchemaConstants.QNAME_ELEMENT);
/*      */     
/* 1611 */     schema.getContent().addChild(element);
/* 1612 */     element.addAttribute("name", name.getLocalPart());
/*      */ 
/*      */     
/* 1615 */     element.addAttribute("type", type.getName());
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\WSDLTypeGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */