/*      */ package com.sun.xml.rpc.processor.model.exporter;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*      */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*      */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*      */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*      */ import com.sun.xml.rpc.processor.model.Message;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.ModelException;
/*      */ import com.sun.xml.rpc.processor.model.ModelObject;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
/*      */ import com.sun.xml.rpc.processor.model.Port;
/*      */ import com.sun.xml.rpc.processor.model.Request;
/*      */ import com.sun.xml.rpc.processor.model.Response;
/*      */ import com.sun.xml.rpc.processor.model.Service;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaCustomType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationEntry;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
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
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
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
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.streaming.XMLReader;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*      */ import java.io.InputStream;
/*      */ import java.util.List;
/*      */ import java.util.Map;
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
/*      */ public class ModelImporter
/*      */   extends ImporterBase
/*      */   implements Constants
/*      */ {
/*      */   public ModelImporter(InputStream s) {
/*  103 */     super(s);
/*      */   }
/*      */   
/*      */   public Model doImport() {
/*  107 */     Object result = internalDoImport();
/*  108 */     if (!(result instanceof Model)) {
/*  109 */       throw new ModelException("model.importer.nonModel");
/*      */     }
/*  111 */     return (Model)result;
/*      */   }
/*      */   
/*      */   protected QName getContainerName() {
/*  115 */     return QNAME_MODEL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getVersion() {
/*  122 */     return "1.1.3";
/*      */   }
/*      */   
/*      */   protected String getTargetVersion() {
/*  126 */     return this.targetModelVersion;
/*      */   }
/*      */   
/*      */   protected void failInvalidSyntax(XMLReader reader) {
/*  130 */     throw new ModelException("model.importer.syntaxError", Integer.toString(reader.getLineNumber()));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void failInvalidVersion(XMLReader reader, String version) {
/*  135 */     throw new ModelException("model.importer.invalidVersion", new Object[] { Integer.toString(reader.getLineNumber()), version });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void failInvalidMinorMinorOrPatchVersion(XMLReader reader, String targetVersion, String currentVersion) {
/*  143 */     throw new ModelException("model.importer.invalidMinorMinorOrPatchVersion", new Object[] { Integer.toString(reader.getLineNumber()), targetVersion, currentVersion });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void failInvalidClass(XMLReader reader, String className) {
/*  152 */     throw new ModelException("model.importer.invalidClass", new Object[] { Integer.toString(reader.getLineNumber()), className });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void failInvalidId(XMLReader reader, Integer id) {
/*  158 */     throw new ModelException("model.importer.invalidId", new Object[] { Integer.toString(reader.getLineNumber()), id.toString() });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void failInvalidLiteral(XMLReader reader, String type, String value) {
/*  166 */     throw new ModelException("model.importer.invalidLiteral", Integer.toString(reader.getLineNumber()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void failInvalidProperty(XMLReader reader, Object subject, String name, Object value) {
/*  173 */     throw new ModelException("model.importer.invalidProperty", Integer.toString(reader.getLineNumber()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void property(XMLReader reader, Object subject, String name, Object value) {
/*  180 */     if (subject instanceof LiteralWildcardMember) {
/*  181 */       propertyLiteralWildcardMember(reader, (LiteralWildcardMember)subject, name, value);
/*      */     }
/*  183 */     else if (subject instanceof ImportedDocumentInfo) {
/*  184 */       propertyImportedDocumentInfo(reader, (ImportedDocumentInfo)subject, name, value);
/*      */     }
/*  186 */     else if (subject instanceof JavaInterface) {
/*  187 */       propertyJavaInterface(reader, (JavaInterface)subject, name, value);
/*      */     }
/*  189 */     else if (subject instanceof JavaCustomType) {
/*  190 */       propertyJavaCustomType(reader, (JavaCustomType)subject, name, value);
/*      */     }
/*  192 */     else if (subject instanceof Operation) {
/*  193 */       propertyOperation(reader, (Operation)subject, name, value);
/*      */     }
/*  195 */     else if (subject instanceof HandlerChainInfo) {
/*  196 */       propertyHandlerChainInfo(reader, (HandlerChainInfo)subject, name, value);
/*      */     }
/*  198 */     else if (subject instanceof JavaException) {
/*  199 */       propertyJavaException(reader, (JavaException)subject, name, value);
/*      */     }
/*  201 */     else if (subject instanceof JavaStructureType) {
/*  202 */       propertyJavaStructureType(reader, (JavaStructureType)subject, name, value);
/*      */     }
/*  204 */     else if (subject instanceof JavaSimpleType) {
/*  205 */       propertyJavaSimpleType(reader, (JavaSimpleType)subject, name, value);
/*      */     }
/*  207 */     else if (subject instanceof JavaStructureMember) {
/*  208 */       propertyJavaStructureMember(reader, (JavaStructureMember)subject, name, value);
/*      */     }
/*  210 */     else if (subject instanceof Block) {
/*  211 */       propertyBlock(reader, (Block)subject, name, value);
/*      */     }
/*  213 */     else if (subject instanceof LiteralElementMember) {
/*  214 */       propertyLiteralElementMember(reader, (LiteralElementMember)subject, name, value);
/*      */     }
/*  216 */     else if (subject instanceof LiteralArrayWrapperType) {
/*  217 */       propertyLiteralArrayWrapperType(reader, (LiteralArrayWrapperType)subject, name, value);
/*      */     }
/*  219 */     else if (subject instanceof LiteralSequenceType) {
/*  220 */       propertyLiteralSequenceType(reader, (LiteralSequenceType)subject, name, value);
/*      */     }
/*  222 */     else if (subject instanceof RPCRequestUnorderedStructureType) {
/*  223 */       propertyRPCRequestUnorderedStructureType(reader, (RPCRequestUnorderedStructureType)subject, name, value);
/*      */     }
/*  225 */     else if (subject instanceof JavaEnumerationEntry) {
/*  226 */       propertyJavaEnumerationEntry(reader, (JavaEnumerationEntry)subject, name, value);
/*      */     }
/*  228 */     else if (subject instanceof Response) {
/*  229 */       propertyResponse(reader, (Response)subject, name, value);
/*      */     }
/*  231 */     else if (subject instanceof RPCRequestOrderedStructureType) {
/*  232 */       propertyRPCRequestOrderedStructureType(reader, (RPCRequestOrderedStructureType)subject, name, value);
/*      */     }
/*  234 */     else if (subject instanceof LiteralEnumerationType) {
/*  235 */       propertyLiteralEnumerationType(reader, (LiteralEnumerationType)subject, name, value);
/*      */     }
/*  237 */     else if (subject instanceof Request) {
/*  238 */       propertyRequest(reader, (Request)subject, name, value);
/*      */     }
/*  240 */     else if (subject instanceof LiteralAllType) {
/*  241 */       propertyLiteralAllType(reader, (LiteralAllType)subject, name, value);
/*      */     }
/*  243 */     else if (subject instanceof JavaArrayType) {
/*  244 */       propertyJavaArrayType(reader, (JavaArrayType)subject, name, value);
/*      */     }
/*  246 */     else if (subject instanceof Port) {
/*  247 */       propertyPort(reader, (Port)subject, name, value);
/*      */     }
/*  249 */     else if (subject instanceof LiteralAttributeMember) {
/*  250 */       propertyLiteralAttributeMember(reader, (LiteralAttributeMember)subject, name, value);
/*      */     }
/*  252 */     else if (subject instanceof HandlerInfo) {
/*  253 */       propertyHandlerInfo(reader, (HandlerInfo)subject, name, value);
/*      */     }
/*  255 */     else if (subject instanceof Service) {
/*  256 */       propertyService(reader, (Service)subject, name, value);
/*      */     }
/*  258 */     else if (subject instanceof SOAPStructureMember) {
/*  259 */       propertySOAPStructureMember(reader, (SOAPStructureMember)subject, name, value);
/*      */     }
/*  261 */     else if (subject instanceof JavaParameter) {
/*  262 */       propertyJavaParameter(reader, (JavaParameter)subject, name, value);
/*      */     }
/*  264 */     else if (subject instanceof Model) {
/*  265 */       propertyModel(reader, (Model)subject, name, value);
/*      */     }
/*  267 */     else if (subject instanceof LiteralSimpleType) {
/*  268 */       propertyLiteralSimpleType(reader, (LiteralSimpleType)subject, name, value);
/*      */     }
/*  270 */     else if (subject instanceof LiteralArrayType) {
/*  271 */       propertyLiteralArrayType(reader, (LiteralArrayType)subject, name, value);
/*      */     }
/*  273 */     else if (subject instanceof LiteralListType) {
/*  274 */       propertyLiteralListType(reader, (LiteralListType)subject, name, value);
/*      */     }
/*  276 */     else if (subject instanceof JavaEnumerationType) {
/*  277 */       propertyJavaEnumerationType(reader, (JavaEnumerationType)subject, name, value);
/*      */     }
/*  279 */     else if (subject instanceof SOAPCustomType) {
/*  280 */       propertySOAPCustomType(reader, (SOAPCustomType)subject, name, value);
/*      */     }
/*  282 */     else if (subject instanceof LiteralFragmentType) {
/*  283 */       propertyLiteralFragmentType(reader, (LiteralFragmentType)subject, name, value);
/*      */     }
/*  285 */     else if (subject instanceof SOAPArrayType) {
/*  286 */       propertySOAPArrayType(reader, (SOAPArrayType)subject, name, value);
/*      */     }
/*  288 */     else if (subject instanceof SOAPUnorderedStructureType) {
/*  289 */       propertySOAPUnorderedStructureType(reader, (SOAPUnorderedStructureType)subject, name, value);
/*      */     }
/*  291 */     else if (subject instanceof Message) {
/*  292 */       propertyMessage(reader, (Message)subject, name, value);
/*      */     }
/*  294 */     else if (subject instanceof HeaderFault) {
/*  295 */       propertyHeaderFault(reader, (HeaderFault)subject, name, value);
/*      */     }
/*  297 */     else if (subject instanceof JavaMethod) {
/*  298 */       propertyJavaMethod(reader, (JavaMethod)subject, name, value);
/*      */     }
/*  300 */     else if (subject instanceof SOAPAnyType) {
/*  301 */       propertySOAPAnyType(reader, (SOAPAnyType)subject, name, value);
/*      */     }
/*  303 */     else if (subject instanceof SOAPSimpleType) {
/*  304 */       propertySOAPSimpleType(reader, (SOAPSimpleType)subject, name, value);
/*      */     }
/*  306 */     else if (subject instanceof SOAPOrderedStructureType) {
/*  307 */       propertySOAPOrderedStructureType(reader, (SOAPOrderedStructureType)subject, name, value);
/*  308 */     } else if (subject instanceof SOAPAttributeMember) {
/*  309 */       propertySOAPAttributeMember(reader, (SOAPAttributeMember)subject, name, value);
/*      */     }
/*  311 */     else if (subject instanceof RPCResponseStructureType) {
/*  312 */       propertyRPCResponseStructureType(reader, (RPCResponseStructureType)subject, name, value);
/*      */     }
/*  314 */     else if (subject instanceof Parameter) {
/*  315 */       propertyParameter(reader, (Parameter)subject, name, value);
/*      */     }
/*  317 */     else if (subject instanceof TypeMappingInfo) {
/*  318 */       propertyTypeMappingInfo(reader, (TypeMappingInfo)subject, name, value);
/*      */     }
/*  320 */     else if (subject instanceof Fault) {
/*  321 */       propertyFault(reader, (Fault)subject, name, value);
/*      */     }
/*  323 */     else if (subject instanceof LiteralContentMember) {
/*  324 */       propertyLiteralContentMember(reader, (LiteralContentMember)subject, name, value);
/*      */     }
/*  326 */     else if (subject instanceof SOAPEnumerationType) {
/*  327 */       propertySOAPEnumerationType(reader, (SOAPEnumerationType)subject, name, value);
/*      */     }
/*  329 */     else if (subject instanceof JavaType) {
/*  330 */       propertyJavaType(reader, (JavaType)subject, name, value);
/*      */     }
/*  332 */     else if (subject instanceof ModelObject) {
/*  333 */       propertyModelObject(reader, (ModelObject)subject, name, value);
/*      */     }
/*  335 */     else if (subject instanceof LiteralIDType) {
/*  336 */       propertyLiteralIDType(reader, (LiteralIDType)subject, name, value);
/*  337 */     } else if (subject instanceof SOAPListType) {
/*  338 */       propertySOAPListType(reader, (SOAPListType)subject, name, value);
/*  339 */     } else if (subject instanceof LiteralAttachmentType) {
/*  340 */       propertyLiteralAttachmentType(reader, (LiteralAttachmentType)subject, name, value);
/*      */     } else {
/*      */       
/*  343 */       super.property(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propertyLiteralAttachmentType(XMLReader reader, LiteralAttachmentType subject, String name, Object value) {
/*  354 */     if (name.equals("mimeType")) {
/*  355 */       subject.setMIMEType((String)value);
/*      */     }
/*  357 */     else if (name.equals("alternateMIMETypes")) {
/*  358 */       subject.setAlternateMIMETypes((List)value);
/*      */     }
/*  360 */     else if (name.equals("contentId")) {
/*  361 */       subject.setContentID((String)value);
/*      */     }
/*  363 */     else if (name.equals("isSwaRef")) {
/*  364 */       subject.setSwaRef(((Boolean)value).booleanValue());
/*      */     }
/*  366 */     else if (name.equals("javaType")) {
/*  367 */       subject.setJavaType((JavaType)value);
/*      */     } else {
/*      */       
/*  370 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void propertyLiteralWildcardMember(XMLReader reader, LiteralWildcardMember subject, String name, Object value) {
/*  376 */     if (name.equals("nillable")) {
/*  377 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     }
/*  379 */     else if (name.equals("javaStructureMember")) {
/*  380 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/*  382 */     else if (name.equals("required")) {
/*  383 */       subject.setRequired(((Boolean)value).booleanValue());
/*      */     }
/*  385 */     else if (name.equals("repeated")) {
/*  386 */       subject.setRepeated(((Boolean)value).booleanValue());
/*      */     }
/*  388 */     else if (name.equals("type")) {
/*  389 */       subject.setType((LiteralType)value);
/*      */     }
/*  391 */     else if (name.equals("name")) {
/*  392 */       subject.setName((QName)value);
/*      */     }
/*  394 */     else if (name.equals("excludedNamespaceName")) {
/*  395 */       subject.setExcludedNamespaceName((String)value);
/*      */     } else {
/*      */       
/*  398 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyImportedDocumentInfo(XMLReader reader, ImportedDocumentInfo subject, String name, Object value) {
/*  403 */     if (name.equals("namespace")) {
/*  404 */       subject.setNamespace((String)value);
/*      */     }
/*  406 */     else if (name.equals("type")) {
/*  407 */       subject.setType(((Integer)value).intValue());
/*      */     }
/*  409 */     else if (name.equals("location")) {
/*  410 */       subject.setLocation((String)value);
/*      */     } else {
/*      */       
/*  413 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaInterface(XMLReader reader, JavaInterface subject, String name, Object value) {
/*  418 */     if (name.equals("realName")) {
/*  419 */       subject.setRealName((String)value);
/*      */     }
/*  421 */     else if (name.equals("formalName")) {
/*  422 */       subject.setFormalName((String)value);
/*      */     }
/*  424 */     else if (name.equals("methodsList")) {
/*  425 */       subject.setMethodsList((List)value);
/*      */     }
/*  427 */     else if (name.equals("interfacesList")) {
/*  428 */       subject.setInterfacesList((List)value);
/*      */     }
/*  430 */     else if (name.equals("impl")) {
/*  431 */       subject.setImpl((String)value);
/*      */     } else {
/*      */       
/*  434 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaCustomType(XMLReader reader, JavaCustomType subject, String name, Object value) {
/*  439 */     if (name.equals("realName")) {
/*  440 */       subject.setRealName((String)value);
/*      */     }
/*  442 */     else if (name.equals("formalName")) {
/*  443 */       subject.setFormalName((String)value);
/*      */     }
/*  445 */     else if (name.equals("present")) {
/*  446 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/*  448 */     else if (name.equals("holder")) {
/*  449 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/*  451 */     else if (name.equals("holderPresent")) {
/*  452 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/*  454 */     else if (name.equals("initString")) {
/*  455 */       subject.setInitString((String)value);
/*      */     }
/*  457 */     else if (name.equals("holderName")) {
/*  458 */       subject.setHolderName((String)value);
/*      */     }
/*  460 */     else if (name.equals("typeMappingInfo")) {
/*  461 */       subject.setTypeMappingInfo((TypeMappingInfo)value);
/*      */     } else {
/*      */       
/*  464 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyOperation(XMLReader reader, Operation subject, String name, Object value) {
/*  469 */     if (name.equals("propertiesMap")) {
/*  470 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  472 */     else if (name.equals("uniqueName")) {
/*  473 */       subject.setUniqueName((String)value);
/*      */     }
/*  475 */     else if (name.equals("request")) {
/*  476 */       subject.setRequest((Request)value);
/*      */     }
/*  478 */     else if (name.equals("response")) {
/*  479 */       subject.setResponse((Response)value);
/*      */     }
/*  481 */     else if (name.equals("faultsSet")) {
/*  482 */       subject.setFaultsSet((Set)value);
/*      */     }
/*  484 */     else if (name.equals("javaMethod")) {
/*  485 */       subject.setJavaMethod((JavaMethod)value);
/*      */     }
/*  487 */     else if (name.equals("SOAPAction")) {
/*  488 */       subject.setSOAPAction((String)value);
/*      */     }
/*  490 */     else if (name.equals("use")) {
/*  491 */       subject.setUse((SOAPUse)value);
/*      */     }
/*  493 */     else if (name.equals("style")) {
/*  494 */       subject.setStyle((SOAPStyle)value);
/*      */     }
/*  496 */     else if (name.equals("name")) {
/*  497 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/*  500 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyHandlerChainInfo(XMLReader reader, HandlerChainInfo subject, String name, Object value) {
/*  505 */     if (name.equals("handlersList")) {
/*  506 */       subject.setHandlersList((List)value);
/*      */     }
/*  508 */     else if (name.equals("roles")) {
/*  509 */       subject.setRoles((Set)value);
/*      */     } else {
/*      */       
/*  512 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaException(XMLReader reader, JavaException subject, String name, Object value) {
/*  517 */     if (name.equals("realName")) {
/*  518 */       subject.setRealName((String)value);
/*      */     }
/*  520 */     else if (name.equals("formalName")) {
/*  521 */       subject.setFormalName((String)value);
/*      */     }
/*  523 */     else if (name.equals("present")) {
/*  524 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/*  526 */     else if (name.equals("holder")) {
/*  527 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/*  529 */     else if (name.equals("holderPresent")) {
/*  530 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/*  532 */     else if (name.equals("initString")) {
/*  533 */       subject.setInitString((String)value);
/*      */     }
/*  535 */     else if (name.equals("holderName")) {
/*  536 */       subject.setHolderName((String)value);
/*      */     }
/*  538 */     else if (name.equals("membersList")) {
/*  539 */       subject.setMembersList((List)value);
/*      */     }
/*  541 */     else if (name.equals("subclassesSet")) {
/*  542 */       subject.setSubclassesSet((Set)value);
/*      */     }
/*  544 */     else if (name.equals("abstract")) {
/*  545 */       subject.setAbstract(((Boolean)value).booleanValue());
/*      */     }
/*  547 */     else if (name.equals("owner")) {
/*  548 */       subject.setOwner(value);
/*      */     }
/*  550 */     else if (name.equals("superclass")) {
/*  551 */       subject.setSuperclass((JavaStructureType)value);
/*      */     } else {
/*      */       
/*  554 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaStructureType(XMLReader reader, JavaStructureType subject, String name, Object value) {
/*  559 */     if (name.equals("realName")) {
/*  560 */       subject.setRealName((String)value);
/*      */     }
/*  562 */     else if (name.equals("formalName")) {
/*  563 */       subject.setFormalName((String)value);
/*      */     }
/*  565 */     else if (name.equals("present")) {
/*  566 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/*  568 */     else if (name.equals("holder")) {
/*  569 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/*  571 */     else if (name.equals("holderPresent")) {
/*  572 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/*  574 */     else if (name.equals("initString")) {
/*  575 */       subject.setInitString((String)value);
/*      */     }
/*  577 */     else if (name.equals("holderName")) {
/*  578 */       subject.setHolderName((String)value);
/*      */     }
/*  580 */     else if (name.equals("membersList")) {
/*  581 */       subject.setMembersList((List)value);
/*      */     }
/*  583 */     else if (name.equals("subclassesSet")) {
/*  584 */       subject.setSubclassesSet((Set)value);
/*      */     }
/*  586 */     else if (name.equals("abstract")) {
/*  587 */       subject.setAbstract(((Boolean)value).booleanValue());
/*      */     }
/*  589 */     else if (name.equals("owner")) {
/*  590 */       subject.setOwner(value);
/*      */     }
/*  592 */     else if (name.equals("superclass")) {
/*  593 */       subject.setSuperclass((JavaStructureType)value);
/*      */     } else {
/*      */       
/*  596 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaSimpleType(XMLReader reader, JavaSimpleType subject, String name, Object value) {
/*  601 */     if (name.equals("realName")) {
/*  602 */       subject.setRealName((String)value);
/*      */     }
/*  604 */     else if (name.equals("formalName")) {
/*  605 */       subject.setFormalName((String)value);
/*      */     }
/*  607 */     else if (name.equals("present")) {
/*  608 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/*  610 */     else if (name.equals("holder")) {
/*  611 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/*  613 */     else if (name.equals("holderPresent")) {
/*  614 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/*  616 */     else if (name.equals("initString")) {
/*  617 */       subject.setInitString((String)value);
/*      */     }
/*  619 */     else if (name.equals("holderName")) {
/*  620 */       subject.setHolderName((String)value);
/*      */     } else {
/*      */       
/*  623 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaStructureMember(XMLReader reader, JavaStructureMember subject, String name, Object value) {
/*  628 */     if (name.equals("readMethod")) {
/*  629 */       subject.setReadMethod((String)value);
/*      */     }
/*  631 */     else if (name.equals("writeMethod")) {
/*  632 */       subject.setWriteMethod((String)value);
/*      */     }
/*  634 */     else if (name.equals("inherited")) {
/*  635 */       subject.setInherited(((Boolean)value).booleanValue());
/*      */     }
/*  637 */     else if (name.equals("constructorPos")) {
/*  638 */       subject.setConstructorPos(((Integer)value).intValue());
/*      */     }
/*  640 */     else if (name.equals("type")) {
/*  641 */       subject.setType((JavaType)value);
/*      */     }
/*  643 */     else if (name.equals("public")) {
/*  644 */       subject.setPublic(((Boolean)value).booleanValue());
/*      */     }
/*  646 */     else if (name.equals("declaringClass")) {
/*  647 */       subject.setDeclaringClass((String)value);
/*      */     }
/*  649 */     else if (name.equals("owner")) {
/*  650 */       subject.setOwner(value);
/*      */     }
/*  652 */     else if (name.equals("name")) {
/*  653 */       subject.setName((String)value);
/*      */     } else {
/*      */       
/*  656 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyBlock(XMLReader reader, Block subject, String name, Object value) {
/*  661 */     if (name.equals("propertiesMap")) {
/*  662 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  664 */     else if (name.equals("type")) {
/*  665 */       subject.setType((AbstractType)value);
/*      */     }
/*  667 */     else if (name.equals("location")) {
/*  668 */       subject.setLocation(((Integer)value).intValue());
/*      */     }
/*  670 */     else if (name.equals("name")) {
/*  671 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/*  674 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralElementMember(XMLReader reader, LiteralElementMember subject, String name, Object value) {
/*  679 */     if (name.equals("nillable")) {
/*  680 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     }
/*  682 */     else if (name.equals("javaStructureMember")) {
/*  683 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/*  685 */     else if (name.equals("required")) {
/*  686 */       subject.setRequired(((Boolean)value).booleanValue());
/*      */     }
/*  688 */     else if (name.equals("repeated")) {
/*  689 */       subject.setRepeated(((Boolean)value).booleanValue());
/*      */     }
/*  691 */     else if (name.equals("type")) {
/*  692 */       subject.setType((LiteralType)value);
/*      */     }
/*  694 */     else if (name.equals("name")) {
/*  695 */       subject.setName((QName)value);
/*      */     }
/*  697 */     else if (name.equals("inherited")) {
/*  698 */       subject.setInherited(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/*  701 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralSequenceType(XMLReader reader, LiteralSequenceType subject, String name, Object value) {
/*  706 */     if (name.equals("javaType")) {
/*  707 */       subject.setJavaType((JavaType)value);
/*      */     }
/*  709 */     else if (name.equals("propertiesMap")) {
/*  710 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  712 */     else if (name.equals("version")) {
/*  713 */       subject.setVersion((String)value);
/*      */     }
/*  715 */     else if (name.equals("name")) {
/*  716 */       subject.setName((QName)value);
/*      */     }
/*  718 */     else if (name.equals("schemaTypeRef")) {
/*  719 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/*  721 */     else if (name.equals("attributeMembersList")) {
/*  722 */       subject.setAttributeMembersList((List)value);
/*      */     }
/*  724 */     else if (name.equals("elementMembersList")) {
/*  725 */       subject.setElementMembersList((List)value);
/*      */     }
/*  727 */     else if (name.equals("contentMember")) {
/*  728 */       subject.setContentMember((LiteralContentMember)value);
/*      */     }
/*  730 */     else if (name.equals("subtypesSet")) {
/*  731 */       subject.setSubtypesSet((Set)value);
/*      */     }
/*  733 */     else if (name.equals("parentType")) {
/*  734 */       subject.setParentType((LiteralStructuredType)value);
/*      */     }
/*  736 */     else if (name.equals("nillable")) {
/*  737 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     }
/*  739 */     else if (name.equals("rpcWrapper")) {
/*  740 */       subject.setRpcWrapper(((Boolean)value).booleanValue());
/*      */     }
/*  742 */     else if (name.equals("isUnwrapped")) {
/*  743 */       subject.setUnwrapped(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/*  746 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralArrayWrapperType(XMLReader reader, LiteralArrayWrapperType subject, String name, Object value) {
/*  751 */     if (name.equals("javaArrayType")) {
/*  752 */       subject.setJavaArrayType((JavaArrayType)value);
/*      */     } else {
/*      */       
/*  755 */       propertyLiteralSequenceType(reader, (LiteralSequenceType)subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyRPCRequestUnorderedStructureType(XMLReader reader, RPCRequestUnorderedStructureType subject, String name, Object value) {
/*  760 */     if (name.equals("javaType")) {
/*  761 */       subject.setJavaType((JavaType)value);
/*      */     }
/*  763 */     else if (name.equals("propertiesMap")) {
/*  764 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  766 */     else if (name.equals("version")) {
/*  767 */       subject.setVersion((String)value);
/*      */     }
/*  769 */     else if (name.equals("name")) {
/*  770 */       subject.setName((QName)value);
/*      */     }
/*  772 */     else if (name.equals("membersList")) {
/*  773 */       subject.setMembersList((List)value);
/*      */     }
/*  775 */     else if (name.equals("attributeMembersList")) {
/*  776 */       subject.setAttributeMembersList((List)value);
/*      */     }
/*  778 */     else if (name.equals("subtypesSet")) {
/*  779 */       subject.setSubtypesSet((Set)value);
/*      */     }
/*  781 */     else if (name.equals("parentType")) {
/*  782 */       subject.setParentType((SOAPStructureType)value);
/*      */     } else {
/*      */       
/*  785 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaEnumerationEntry(XMLReader reader, JavaEnumerationEntry subject, String name, Object value) {
/*  790 */     if (name.equals("literalValue")) {
/*  791 */       subject.setLiteralValue((String)value);
/*      */     }
/*  793 */     else if (name.equals("value")) {
/*  794 */       subject.setValue(value);
/*      */     }
/*  796 */     else if (name.equals("name")) {
/*  797 */       subject.setName((String)value);
/*      */     } else {
/*      */       
/*  800 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyResponse(XMLReader reader, Response subject, String name, Object value) {
/*  805 */     if (name.equals("propertiesMap")) {
/*  806 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  808 */     else if (name.equals("bodyBlocksMap")) {
/*  809 */       subject.setBodyBlocksMap((Map)value);
/*      */     }
/*  811 */     else if (name.equals("headerBlocksMap")) {
/*  812 */       subject.setHeaderBlocksMap((Map)value);
/*      */     }
/*  814 */     else if (name.equals("parametersList")) {
/*  815 */       subject.setParametersList((List)value);
/*      */     }
/*  817 */     else if (name.equals("faultBlocksMap")) {
/*  818 */       subject.setFaultBlocksMap((Map)value);
/*      */     }
/*  820 */     else if (name.equals("attachmentBlocksMap")) {
/*  821 */       subject.setAttachmentBlocksMap((Map)value);
/*      */     } else {
/*      */       
/*  824 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyRPCRequestOrderedStructureType(XMLReader reader, RPCRequestOrderedStructureType subject, String name, Object value) {
/*  829 */     if (name.equals("javaType")) {
/*  830 */       subject.setJavaType((JavaType)value);
/*      */     }
/*  832 */     else if (name.equals("propertiesMap")) {
/*  833 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  835 */     else if (name.equals("version")) {
/*  836 */       subject.setVersion((String)value);
/*      */     }
/*  838 */     else if (name.equals("name")) {
/*  839 */       subject.setName((QName)value);
/*      */     }
/*  841 */     else if (name.equals("membersList")) {
/*  842 */       subject.setMembersList((List)value);
/*      */     }
/*  844 */     else if (name.equals("attributeMembersList")) {
/*  845 */       subject.setAttributeMembersList((List)value);
/*      */     }
/*  847 */     else if (name.equals("subtypesSet")) {
/*  848 */       subject.setSubtypesSet((Set)value);
/*      */     }
/*  850 */     else if (name.equals("parentType")) {
/*  851 */       subject.setParentType((SOAPStructureType)value);
/*      */     } else {
/*      */       
/*  854 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralEnumerationType(XMLReader reader, LiteralEnumerationType subject, String name, Object value) {
/*  859 */     if (name.equals("javaType")) {
/*  860 */       subject.setJavaType((JavaType)value);
/*      */     }
/*  862 */     else if (name.equals("propertiesMap")) {
/*  863 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  865 */     else if (name.equals("version")) {
/*  866 */       subject.setVersion((String)value);
/*      */     }
/*  868 */     else if (name.equals("name")) {
/*  869 */       subject.setName((QName)value);
/*      */     }
/*  871 */     else if (name.equals("schemaTypeRef")) {
/*  872 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/*  874 */     else if (name.equals("baseType")) {
/*  875 */       subject.setBaseType((LiteralType)value);
/*      */     }
/*  877 */     else if (name.equals("nillable")) {
/*  878 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/*  881 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyRequest(XMLReader reader, Request subject, String name, Object value) {
/*  886 */     if (name.equals("propertiesMap")) {
/*  887 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  889 */     else if (name.equals("bodyBlocksMap")) {
/*  890 */       subject.setBodyBlocksMap((Map)value);
/*      */     }
/*  892 */     else if (name.equals("headerBlocksMap")) {
/*  893 */       subject.setHeaderBlocksMap((Map)value);
/*      */     }
/*  895 */     else if (name.equals("parametersList")) {
/*  896 */       subject.setParametersList((List)value);
/*      */     } else {
/*      */       
/*  899 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralAllType(XMLReader reader, LiteralAllType subject, String name, Object value) {
/*  904 */     if (name.equals("javaType")) {
/*  905 */       subject.setJavaType((JavaType)value);
/*      */     }
/*  907 */     else if (name.equals("propertiesMap")) {
/*  908 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  910 */     else if (name.equals("version")) {
/*  911 */       subject.setVersion((String)value);
/*      */     }
/*  913 */     else if (name.equals("name")) {
/*  914 */       subject.setName((QName)value);
/*      */     }
/*  916 */     else if (name.equals("schemaTypeRef")) {
/*  917 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/*  919 */     else if (name.equals("attributeMembersList")) {
/*  920 */       subject.setAttributeMembersList((List)value);
/*      */     }
/*  922 */     else if (name.equals("elementMembersList")) {
/*  923 */       subject.setElementMembersList((List)value);
/*      */     }
/*  925 */     else if (name.equals("contentMember")) {
/*  926 */       subject.setContentMember((LiteralContentMember)value);
/*      */     }
/*  928 */     else if (name.equals("subtypesSet")) {
/*  929 */       subject.setSubtypesSet((Set)value);
/*      */     }
/*  931 */     else if (name.equals("parentType")) {
/*  932 */       subject.setParentType((LiteralStructuredType)value);
/*      */     }
/*  934 */     else if (name.equals("nillable")) {
/*  935 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     }
/*  937 */     else if (name.equals("rpcWrapper")) {
/*  938 */       subject.setRpcWrapper(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/*  941 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaArrayType(XMLReader reader, JavaArrayType subject, String name, Object value) {
/*  946 */     if (name.equals("realName")) {
/*  947 */       subject.setRealName((String)value);
/*      */     }
/*  949 */     else if (name.equals("formalName")) {
/*  950 */       subject.setFormalName((String)value);
/*      */     }
/*  952 */     else if (name.equals("present")) {
/*  953 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/*  955 */     else if (name.equals("holder")) {
/*  956 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/*  958 */     else if (name.equals("holderPresent")) {
/*  959 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/*  961 */     else if (name.equals("initString")) {
/*  962 */       subject.setInitString((String)value);
/*      */     }
/*  964 */     else if (name.equals("holderName")) {
/*  965 */       subject.setHolderName((String)value);
/*      */     }
/*  967 */     else if (name.equals("elementName")) {
/*  968 */       subject.setElementName((String)value);
/*      */     }
/*  970 */     else if (name.equals("elementType")) {
/*  971 */       subject.setElementType((JavaType)value);
/*      */     }
/*  973 */     else if (name.equals("soapArrayHolderName")) {
/*  974 */       subject.setSOAPArrayHolderName((String)value);
/*      */     } else {
/*      */       
/*  977 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyPort(XMLReader reader, Port subject, String name, Object value) {
/*  982 */     if (name.equals("propertiesMap")) {
/*  983 */       subject.setPropertiesMap((Map)value);
/*      */     }
/*  985 */     else if (name.equals("operationsList")) {
/*  986 */       subject.setOperationsList((List)value);
/*      */     }
/*  988 */     else if (name.equals("javaInterface")) {
/*  989 */       subject.setJavaInterface((JavaInterface)value);
/*      */     }
/*  991 */     else if (name.equals("clientHandlerChainInfo")) {
/*  992 */       subject.setClientHandlerChainInfo((HandlerChainInfo)value);
/*      */     }
/*  994 */     else if (name.equals("serverHandlerChainInfo")) {
/*  995 */       subject.setServerHandlerChainInfo((HandlerChainInfo)value);
/*      */     }
/*  997 */     else if (name.equals("SOAPVersion")) {
/*  998 */       subject.setSOAPVersion((SOAPVersion)value);
/*      */     }
/* 1000 */     else if (name.equals("address")) {
/* 1001 */       subject.setAddress((String)value);
/*      */     }
/* 1003 */     else if (name.equals("name")) {
/* 1004 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/* 1007 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralAttributeMember(XMLReader reader, LiteralAttributeMember subject, String name, Object value) {
/* 1012 */     if (name.equals("javaStructureMember")) {
/* 1013 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/* 1015 */     else if (name.equals("required")) {
/* 1016 */       subject.setRequired(((Boolean)value).booleanValue());
/*      */     }
/* 1018 */     else if (name.equals("type")) {
/* 1019 */       subject.setType((LiteralType)value);
/*      */     }
/* 1021 */     else if (name.equals("name")) {
/* 1022 */       subject.setName((QName)value);
/*      */     }
/* 1024 */     else if (name.equals("inherited")) {
/* 1025 */       subject.setInherited(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1028 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyHandlerInfo(XMLReader reader, HandlerInfo subject, String name, Object value) {
/* 1033 */     if (name.equals("handlerClassName")) {
/* 1034 */       subject.setHandlerClassName((String)value);
/*      */     }
/* 1036 */     else if (name.equals("headerNames")) {
/* 1037 */       subject.setHeaderNames((Set)value);
/*      */     }
/* 1039 */     else if (name.equals("properties")) {
/* 1040 */       subject.setProperties((Map)value);
/*      */     } else {
/*      */       
/* 1043 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyService(XMLReader reader, Service subject, String name, Object value) {
/* 1048 */     if (name.equals("propertiesMap")) {
/* 1049 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1051 */     else if (name.equals("javaInterface")) {
/* 1052 */       subject.setJavaInterface((JavaInterface)value);
/*      */     }
/* 1054 */     else if (name.equals("portsList")) {
/* 1055 */       subject.setPortsList((List)value);
/*      */     }
/* 1057 */     else if (name.equals("name")) {
/* 1058 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/* 1061 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPStructureMember(XMLReader reader, SOAPStructureMember subject, String name, Object value) {
/* 1066 */     if (name.equals("inherited")) {
/* 1067 */       subject.setInherited(((Boolean)value).booleanValue());
/*      */     }
/* 1069 */     else if (name.equals("javaStructureMember")) {
/* 1070 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/* 1072 */     else if (name.equals("type")) {
/* 1073 */       subject.setType((SOAPType)value);
/*      */     }
/* 1075 */     else if (name.equals("name")) {
/* 1076 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/* 1079 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaParameter(XMLReader reader, JavaParameter subject, String name, Object value) {
/* 1084 */     if (name.equals("holder")) {
/* 1085 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/* 1087 */     else if (name.equals("type")) {
/* 1088 */       subject.setType((JavaType)value);
/*      */     }
/* 1090 */     else if (name.equals("parameter")) {
/* 1091 */       subject.setParameter((Parameter)value);
/*      */     }
/* 1093 */     else if (name.equals("name")) {
/* 1094 */       subject.setName((String)value);
/*      */     }
/* 1096 */     else if (name.equals("holderName")) {
/* 1097 */       subject.setHolderName((String)value);
/*      */     } else {
/*      */       
/* 1100 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyModel(XMLReader reader, Model subject, String name, Object value) {
/* 1105 */     if (name.equals("propertiesMap")) {
/* 1106 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1108 */     else if (name.equals("targetNamespaceURI")) {
/* 1109 */       subject.setTargetNamespaceURI((String)value);
/*      */     }
/* 1111 */     else if (name.equals("servicesList")) {
/* 1112 */       subject.setServicesList((List)value);
/*      */     }
/* 1114 */     else if (name.equals("extraTypesSet")) {
/* 1115 */       subject.setExtraTypesSet((Set)value);
/*      */     }
/* 1117 */     else if (name.equals("importedDocumentsMap")) {
/* 1118 */       subject.setImportedDocumentsMap((Map)value);
/*      */     }
/* 1120 */     else if (name.equals("name")) {
/* 1121 */       subject.setName((QName)value);
/*      */     }
/* 1123 */     else if (name.equals("target")) {
/* 1124 */       subject.setSource((String)value);
/*      */     } else {
/*      */       
/* 1127 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralSimpleType(XMLReader reader, LiteralSimpleType subject, String name, Object value) {
/* 1132 */     if (name.equals("javaType")) {
/* 1133 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1135 */     else if (name.equals("propertiesMap")) {
/* 1136 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1138 */     else if (name.equals("version")) {
/* 1139 */       subject.setVersion((String)value);
/*      */     }
/* 1141 */     else if (name.equals("name")) {
/* 1142 */       subject.setName((QName)value);
/*      */     }
/* 1144 */     else if (name.equals("schemaTypeRef")) {
/* 1145 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/* 1147 */     else if (name.equals("nillable")) {
/* 1148 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1151 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralArrayType(XMLReader reader, LiteralArrayType subject, String name, Object value) {
/* 1156 */     if (name.equals("javaType")) {
/* 1157 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1159 */     else if (name.equals("propertiesMap")) {
/* 1160 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1162 */     else if (name.equals("version")) {
/* 1163 */       subject.setVersion((String)value);
/*      */     }
/* 1165 */     else if (name.equals("name")) {
/* 1166 */       subject.setName((QName)value);
/*      */     }
/* 1168 */     else if (name.equals("schemaTypeRef")) {
/* 1169 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/* 1171 */     else if (name.equals("elementType")) {
/* 1172 */       subject.setElementType((LiteralType)value);
/*      */     }
/* 1174 */     else if (name.equals("nillable")) {
/* 1175 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1178 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralListType(XMLReader reader, LiteralListType subject, String name, Object value) {
/* 1183 */     if (name.equals("javaType")) {
/* 1184 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1186 */     else if (name.equals("propertiesMap")) {
/* 1187 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1189 */     else if (name.equals("version")) {
/* 1190 */       subject.setVersion((String)value);
/*      */     }
/* 1192 */     else if (name.equals("name")) {
/* 1193 */       subject.setName((QName)value);
/*      */     }
/* 1195 */     else if (name.equals("schemaTypeRef")) {
/* 1196 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/* 1198 */     else if (name.equals("itemType")) {
/* 1199 */       subject.setItemType((LiteralType)value);
/*      */     }
/* 1201 */     else if (name.equals("nillable")) {
/* 1202 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1205 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaEnumerationType(XMLReader reader, JavaEnumerationType subject, String name, Object value) {
/* 1210 */     if (name.equals("realName")) {
/* 1211 */       subject.setRealName((String)value);
/*      */     }
/* 1213 */     else if (name.equals("formalName")) {
/* 1214 */       subject.setFormalName((String)value);
/*      */     }
/* 1216 */     else if (name.equals("present")) {
/* 1217 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/* 1219 */     else if (name.equals("holder")) {
/* 1220 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/* 1222 */     else if (name.equals("holderPresent")) {
/* 1223 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/* 1225 */     else if (name.equals("initString")) {
/* 1226 */       subject.setInitString((String)value);
/*      */     }
/* 1228 */     else if (name.equals("holderName")) {
/* 1229 */       subject.setHolderName((String)value);
/*      */     }
/* 1231 */     else if (name.equals("baseType")) {
/* 1232 */       subject.setBaseType((JavaType)value);
/*      */     }
/* 1234 */     else if (name.equals("entriesList")) {
/* 1235 */       subject.setEntriesList((List)value);
/*      */     } else {
/*      */       
/* 1238 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPCustomType(XMLReader reader, SOAPCustomType subject, String name, Object value) {
/* 1243 */     if (name.equals("javaType")) {
/* 1244 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1246 */     else if (name.equals("propertiesMap")) {
/* 1247 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1249 */     else if (name.equals("version")) {
/* 1250 */       subject.setVersion((String)value);
/*      */     }
/* 1252 */     else if (name.equals("name")) {
/* 1253 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/* 1256 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralFragmentType(XMLReader reader, LiteralFragmentType subject, String name, Object value) {
/* 1261 */     if (name.equals("javaType")) {
/* 1262 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1264 */     else if (name.equals("propertiesMap")) {
/* 1265 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1267 */     else if (name.equals("version")) {
/* 1268 */       subject.setVersion((String)value);
/*      */     }
/* 1270 */     else if (name.equals("name")) {
/* 1271 */       subject.setName((QName)value);
/*      */     }
/* 1273 */     else if (name.equals("schemaTypeRef")) {
/* 1274 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/* 1276 */     else if (name.equals("nillable")) {
/* 1277 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1280 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPArrayType(XMLReader reader, SOAPArrayType subject, String name, Object value) {
/* 1285 */     if (name.equals("javaType")) {
/* 1286 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1288 */     else if (name.equals("propertiesMap")) {
/* 1289 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1291 */     else if (name.equals("version")) {
/* 1292 */       subject.setVersion((String)value);
/*      */     }
/* 1294 */     else if (name.equals("name")) {
/* 1295 */       subject.setName((QName)value);
/*      */     }
/* 1297 */     else if (name.equals("elementName")) {
/* 1298 */       subject.setElementName((QName)value);
/*      */     }
/* 1300 */     else if (name.equals("elementType")) {
/* 1301 */       subject.setElementType((SOAPType)value);
/*      */     }
/* 1303 */     else if (name.equals("rank")) {
/* 1304 */       subject.setRank(((Integer)value).intValue());
/*      */     }
/* 1306 */     else if (name.equals("size")) {
/* 1307 */       subject.setSize((int[])value);
/*      */     } else {
/*      */       
/* 1310 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPUnorderedStructureType(XMLReader reader, SOAPUnorderedStructureType subject, String name, Object value) {
/* 1315 */     if (name.equals("javaType")) {
/* 1316 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1318 */     else if (name.equals("propertiesMap")) {
/* 1319 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1321 */     else if (name.equals("version")) {
/* 1322 */       subject.setVersion((String)value);
/*      */     }
/* 1324 */     else if (name.equals("name")) {
/* 1325 */       subject.setName((QName)value);
/*      */     }
/* 1327 */     else if (name.equals("membersList")) {
/* 1328 */       subject.setMembersList((List)value);
/*      */     }
/* 1330 */     else if (name.equals("attributeMembersList")) {
/* 1331 */       subject.setAttributeMembersList((List)value);
/*      */     }
/* 1333 */     else if (name.equals("subtypesSet")) {
/* 1334 */       subject.setSubtypesSet((Set)value);
/*      */     }
/* 1336 */     else if (name.equals("parentType")) {
/* 1337 */       subject.setParentType((SOAPStructureType)value);
/*      */     } else {
/*      */       
/* 1340 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyMessage(XMLReader reader, Message subject, String name, Object value) {
/* 1345 */     if (name.equals("propertiesMap")) {
/* 1346 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1348 */     else if (name.equals("bodyBlocksMap")) {
/* 1349 */       subject.setBodyBlocksMap((Map)value);
/*      */     }
/* 1351 */     else if (name.equals("headerBlocksMap")) {
/* 1352 */       subject.setHeaderBlocksMap((Map)value);
/*      */     }
/* 1354 */     else if (name.equals("parametersList")) {
/* 1355 */       subject.setParametersList((List)value);
/*      */     } else {
/*      */       
/* 1358 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyHeaderFault(XMLReader reader, HeaderFault subject, String name, Object value) {
/* 1363 */     if (name.equals("propertiesMap")) {
/* 1364 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1366 */     else if (name.equals("block")) {
/* 1367 */       subject.setBlock((Block)value);
/*      */     }
/* 1369 */     else if (name.equals("javaException")) {
/* 1370 */       subject.setJavaException((JavaException)value);
/*      */     }
/* 1372 */     else if (name.equals("parentFault")) {
/* 1373 */       subject.setParentFault((Fault)value);
/*      */     }
/* 1375 */     else if (name.equals("subfaultsSet")) {
/* 1376 */       subject.setSubfaultsSet((Set)value);
/*      */     }
/* 1378 */     else if (name.equals("name")) {
/* 1379 */       subject.setName((String)value);
/*      */     }
/* 1381 */     else if (name.equals("elementName")) {
/* 1382 */       subject.setElementName((QName)value);
/*      */     }
/* 1384 */     else if (name.equals("message")) {
/* 1385 */       subject.setMessage((QName)value);
/*      */     }
/* 1387 */     else if (name.equals("part")) {
/* 1388 */       subject.setPart((String)value);
/*      */     } else {
/*      */       
/* 1391 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaMethod(XMLReader reader, JavaMethod subject, String name, Object value) {
/* 1396 */     if (name.equals("parametersList")) {
/* 1397 */       subject.setParametersList((List)value);
/*      */     }
/* 1399 */     else if (name.equals("exceptionsList")) {
/* 1400 */       subject.setExceptionsList((List)value);
/*      */     }
/* 1402 */     else if (name.equals("returnType")) {
/* 1403 */       subject.setReturnType((JavaType)value);
/*      */     }
/* 1405 */     else if (name.equals("declaringClass")) {
/* 1406 */       subject.setDeclaringClass((String)value);
/*      */     }
/* 1408 */     else if (name.equals("name")) {
/* 1409 */       subject.setName((String)value);
/*      */     } else {
/*      */       
/* 1412 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPAnyType(XMLReader reader, SOAPAnyType subject, String name, Object value) {
/* 1417 */     if (name.equals("javaType")) {
/* 1418 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1420 */     else if (name.equals("propertiesMap")) {
/* 1421 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1423 */     else if (name.equals("version")) {
/* 1424 */       subject.setVersion((String)value);
/*      */     }
/* 1426 */     else if (name.equals("name")) {
/* 1427 */       subject.setName((QName)value);
/*      */     } else {
/*      */       
/* 1430 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPSimpleType(XMLReader reader, SOAPSimpleType subject, String name, Object value) {
/* 1435 */     if (name.equals("javaType")) {
/* 1436 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1438 */     else if (name.equals("propertiesMap")) {
/* 1439 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1441 */     else if (name.equals("version")) {
/* 1442 */       subject.setVersion((String)value);
/*      */     }
/* 1444 */     else if (name.equals("name")) {
/* 1445 */       subject.setName((QName)value);
/*      */     }
/* 1447 */     else if (name.equals("referenceable")) {
/* 1448 */       subject.setReferenceable(((Boolean)value).booleanValue());
/*      */     }
/* 1450 */     else if (name.equals("schemaTypeRef")) {
/* 1451 */       subject.setSchemaTypeRef((QName)value);
/*      */     } else {
/*      */       
/* 1454 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPOrderedStructureType(XMLReader reader, SOAPOrderedStructureType subject, String name, Object value) {
/* 1459 */     if (name.equals("javaType")) {
/* 1460 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1462 */     else if (name.equals("propertiesMap")) {
/* 1463 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1465 */     else if (name.equals("version")) {
/* 1466 */       subject.setVersion((String)value);
/*      */     }
/* 1468 */     else if (name.equals("name")) {
/* 1469 */       subject.setName((QName)value);
/*      */     }
/* 1471 */     else if (name.equals("membersList")) {
/* 1472 */       subject.setMembersList((List)value);
/*      */     }
/* 1474 */     else if (name.equals("attributeMembersList")) {
/* 1475 */       subject.setAttributeMembersList((List)value);
/*      */     }
/* 1477 */     else if (name.equals("subtypesSet")) {
/* 1478 */       subject.setSubtypesSet((Set)value);
/*      */     }
/* 1480 */     else if (name.equals("parentType")) {
/* 1481 */       subject.setParentType((SOAPStructureType)value);
/*      */     } else {
/*      */       
/* 1484 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPAttributeMember(XMLReader reader, SOAPAttributeMember subject, String name, Object value) {
/* 1489 */     if (name.equals("javaStructureMember")) {
/* 1490 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/* 1492 */     else if (name.equals("required")) {
/* 1493 */       subject.setRequired(((Boolean)value).booleanValue());
/*      */     }
/* 1495 */     else if (name.equals("type")) {
/* 1496 */       subject.setType((SOAPType)value);
/*      */     }
/* 1498 */     else if (name.equals("name")) {
/* 1499 */       subject.setName((QName)value);
/*      */     }
/* 1501 */     else if (name.equals("inherited")) {
/* 1502 */       subject.setInherited(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1505 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void propertyRPCResponseStructureType(XMLReader reader, RPCResponseStructureType subject, String name, Object value) {
/* 1511 */     if (name.equals("javaType")) {
/* 1512 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1514 */     else if (name.equals("propertiesMap")) {
/* 1515 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1517 */     else if (name.equals("version")) {
/* 1518 */       subject.setVersion((String)value);
/*      */     }
/* 1520 */     else if (name.equals("name")) {
/* 1521 */       subject.setName((QName)value);
/*      */     }
/* 1523 */     else if (name.equals("membersList")) {
/* 1524 */       subject.setMembersList((List)value);
/*      */     }
/* 1526 */     else if (name.equals("attributeMembersList")) {
/* 1527 */       subject.setAttributeMembersList((List)value);
/*      */     }
/* 1529 */     else if (name.equals("subtypesSet")) {
/* 1530 */       subject.setSubtypesSet((Set)value);
/*      */     }
/* 1532 */     else if (name.equals("parentType")) {
/* 1533 */       subject.setParentType((SOAPStructureType)value);
/*      */     } else {
/*      */       
/* 1536 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyParameter(XMLReader reader, Parameter subject, String name, Object value) {
/* 1541 */     if (name.equals("propertiesMap")) {
/* 1542 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1544 */     else if (name.equals("block")) {
/* 1545 */       subject.setBlock((Block)value);
/*      */     }
/* 1547 */     else if (name.equals("javaParameter")) {
/* 1548 */       subject.setJavaParameter((JavaParameter)value);
/*      */     }
/* 1550 */     else if (name.equals("linkedParameter")) {
/* 1551 */       subject.setLinkedParameter((Parameter)value);
/*      */     }
/* 1553 */     else if (name.equals("type")) {
/* 1554 */       subject.setType((AbstractType)value);
/*      */     }
/* 1556 */     else if (name.equals("embedded")) {
/* 1557 */       subject.setEmbedded(((Boolean)value).booleanValue());
/*      */     }
/* 1559 */     else if (name.equals("name")) {
/* 1560 */       subject.setName((String)value);
/*      */     } else {
/*      */       
/* 1563 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyTypeMappingInfo(XMLReader reader, TypeMappingInfo subject, String name, Object value) {
/* 1568 */     if (name.equals("encodingStyle")) {
/* 1569 */       subject.setEncodingStyle((String)value);
/*      */     }
/* 1571 */     else if (name.equals("XMLType")) {
/* 1572 */       subject.setXMLType((QName)value);
/*      */     }
/* 1574 */     else if (name.equals("javaTypeName")) {
/* 1575 */       subject.setJavaTypeName((String)value);
/*      */     }
/* 1577 */     else if (name.equals("serializerFactoryName")) {
/* 1578 */       subject.setSerializerFactoryName((String)value);
/*      */     }
/* 1580 */     else if (name.equals("deserializerFactoryName")) {
/* 1581 */       subject.setDeserializerFactoryName((String)value);
/*      */     } else {
/*      */       
/* 1584 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyFault(XMLReader reader, Fault subject, String name, Object value) {
/* 1589 */     if (name.equals("propertiesMap")) {
/* 1590 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1592 */     else if (name.equals("block")) {
/* 1593 */       subject.setBlock((Block)value);
/*      */     }
/* 1595 */     else if (name.equals("javaException")) {
/* 1596 */       subject.setJavaException((JavaException)value);
/*      */     }
/* 1598 */     else if (name.equals("parentFault")) {
/* 1599 */       subject.setParentFault((Fault)value);
/*      */     }
/* 1601 */     else if (name.equals("subfaultsSet")) {
/* 1602 */       subject.setSubfaultsSet((Set)value);
/*      */     }
/* 1604 */     else if (name.equals("name")) {
/* 1605 */       subject.setName((String)value);
/*      */     }
/* 1607 */     else if (name.equals("elementName")) {
/* 1608 */       subject.setElementName((QName)value);
/*      */     }
/* 1610 */     else if (name.equals("javaMemberName")) {
/* 1611 */       subject.setJavaMemberName((String)value);
/*      */     } else {
/*      */       
/* 1614 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralContentMember(XMLReader reader, LiteralContentMember subject, String name, Object value) {
/* 1619 */     if (name.equals("javaStructureMember")) {
/* 1620 */       subject.setJavaStructureMember((JavaStructureMember)value);
/*      */     }
/* 1622 */     else if (name.equals("type")) {
/* 1623 */       subject.setType((LiteralType)value);
/*      */     } else {
/*      */       
/* 1626 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertySOAPEnumerationType(XMLReader reader, SOAPEnumerationType subject, String name, Object value) {
/* 1631 */     if (name.equals("javaType")) {
/* 1632 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1634 */     else if (name.equals("propertiesMap")) {
/* 1635 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1637 */     else if (name.equals("version")) {
/* 1638 */       subject.setVersion((String)value);
/*      */     }
/* 1640 */     else if (name.equals("name")) {
/* 1641 */       subject.setName((QName)value);
/*      */     }
/* 1643 */     else if (name.equals("baseType")) {
/* 1644 */       subject.setBaseType((SOAPType)value);
/*      */     } else {
/*      */       
/* 1647 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyJavaType(XMLReader reader, JavaType subject, String name, Object value) {
/* 1652 */     if (name.equals("realName")) {
/* 1653 */       subject.setRealName((String)value);
/*      */     }
/* 1655 */     else if (name.equals("formalName")) {
/* 1656 */       subject.setFormalName((String)value);
/*      */     }
/* 1658 */     else if (name.equals("present")) {
/* 1659 */       subject.setPresent(((Boolean)value).booleanValue());
/*      */     }
/* 1661 */     else if (name.equals("holder")) {
/* 1662 */       subject.setHolder(((Boolean)value).booleanValue());
/*      */     }
/* 1664 */     else if (name.equals("holderPresent")) {
/* 1665 */       subject.setHolderPresent(((Boolean)value).booleanValue());
/*      */     }
/* 1667 */     else if (name.equals("initString")) {
/* 1668 */       subject.setInitString((String)value);
/*      */     }
/* 1670 */     else if (name.equals("holderName")) {
/* 1671 */       subject.setHolderName((String)value);
/*      */     } else {
/*      */       
/* 1674 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyModelObject(XMLReader reader, ModelObject subject, String name, Object value) {
/* 1679 */     if (name.equals("propertiesMap")) {
/* 1680 */       subject.setPropertiesMap((Map)value);
/*      */     } else {
/*      */       
/* 1683 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void propertyLiteralIDType(XMLReader reader, LiteralIDType subject, String name, Object value) {
/* 1688 */     if (name.equals("javaType")) {
/* 1689 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1691 */     else if (name.equals("propertiesMap")) {
/* 1692 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1694 */     else if (name.equals("version")) {
/* 1695 */       subject.setVersion((String)value);
/*      */     }
/* 1697 */     else if (name.equals("name")) {
/* 1698 */       subject.setName((QName)value);
/*      */     }
/* 1700 */     else if (name.equals("schemaTypeRef")) {
/* 1701 */       subject.setSchemaTypeRef((QName)value);
/*      */     }
/* 1703 */     else if (name.equals("nillable")) {
/* 1704 */       subject.setNillable(((Boolean)value).booleanValue());
/*      */     }
/* 1706 */     else if (name.equals("resolveIDREF")) {
/* 1707 */       subject.setResolveIDREF(((Boolean)value).booleanValue());
/*      */     } else {
/*      */       
/* 1710 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void propertySOAPListType(XMLReader reader, SOAPListType subject, String name, Object value) {
/* 1716 */     if (name.equals("javaType")) {
/* 1717 */       subject.setJavaType((JavaType)value);
/*      */     }
/* 1719 */     else if (name.equals("propertiesMap")) {
/* 1720 */       subject.setPropertiesMap((Map)value);
/*      */     }
/* 1722 */     else if (name.equals("version")) {
/* 1723 */       subject.setVersion((String)value);
/*      */     }
/* 1725 */     else if (name.equals("name")) {
/* 1726 */       subject.setName((QName)value);
/*      */     }
/* 1728 */     else if (name.equals("itemType")) {
/* 1729 */       subject.setItemType((SOAPType)value);
/*      */     } else {
/*      */       
/* 1732 */       failInvalidProperty(reader, subject, name, value);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\exporter\ModelImporter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */