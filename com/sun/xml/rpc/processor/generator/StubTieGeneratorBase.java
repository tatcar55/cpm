/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Message;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
/*      */ import com.sun.xml.rpc.processor.model.Port;
/*      */ import com.sun.xml.rpc.processor.model.Request;
/*      */ import com.sun.xml.rpc.processor.model.Response;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*      */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
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
/*      */ public abstract class StubTieGeneratorBase
/*      */   extends GeneratorBase
/*      */ {
/*      */   protected Port port;
/*   80 */   protected HashSet operations = null;
/*      */   protected Set types;
/*      */   protected Map portTypes;
/*      */   private String prefix;
/*      */   protected File srcFile;
/*   85 */   protected SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*   86 */   protected SOAPEncodingConstants soapEncodingConstants = null;
/*   87 */   protected SOAPNamespaceConstants soapNamespaceConstants = null;
/*   88 */   protected SOAPWSDLConstants soapWSDLConstants = null;
/*      */   private boolean genAddAttachmentMethod = false;
/*      */   private boolean genGetAttachmentMethod = false;
/*      */   
/*      */   public StubTieGeneratorBase() {
/*   93 */     this(SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */   
/*      */   public StubTieGeneratorBase(SOAPVersion ver) {
/*   98 */     init(ver);
/*      */   }
/*      */   
/*      */   protected String getPrefix() {
/*  102 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract String getClassName();
/*      */ 
/*      */   
/*      */   protected abstract String getStateType();
/*      */ 
/*      */   
/*      */   protected abstract Message getMessageToDeserialize(Operation paramOperation);
/*      */ 
/*      */   
/*      */   protected StubTieGeneratorBase(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  117 */     super(model, config, properties);
/*  118 */     init(ver);
/*  119 */     this.prefix = getPrefix();
/*  120 */     this.srcFile = null;
/*      */   }
/*      */   protected abstract String getStateGetRequestResponseString();
/*      */   protected abstract String getInitializeAccess();
/*      */   protected abstract boolean superClassHasInitialize();
/*      */   
/*      */   protected StubTieGeneratorBase(Model model, Configuration config, Properties properties) {
/*  127 */     super(model, config, properties);
/*  128 */     this.prefix = getPrefix();
/*  129 */     this.srcFile = null;
/*  130 */     init(SOAPVersion.SOAP_11);
/*      */   }
/*      */   
/*      */   private void init(SOAPVersion ver) {
/*  134 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*      */     
/*  136 */     this.soapNamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(ver);
/*      */     
/*  138 */     this.soapWSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(ver);
/*  139 */     this.soapVer = ver;
/*      */   }
/*      */   
/*      */   protected void preVisitModel(Model model) throws Exception {
/*  143 */     this.types = new HashSet();
/*      */   }
/*      */   
/*      */   protected void postVisitModel(Model model) throws Exception {
/*  147 */     this.types = null;
/*      */   }
/*      */   
/*      */   protected void preVisitPort(Port port) throws Exception {
/*  151 */     super.preVisitPort(port);
/*  152 */     this.operations = new HashSet();
/*  153 */     this.portTypes = new HashMap<Object, Object>();
/*  154 */     this.port = port;
/*      */   }
/*      */   
/*      */   protected void postVisitPort(Port port) throws Exception {
/*  158 */     writeClass();
/*  159 */     this.port = null;
/*  160 */     this.portTypes = null;
/*  161 */     this.operations = null;
/*  162 */     super.postVisitPort(port);
/*      */   }
/*      */   
/*      */   protected void postVisitOperation(Operation operation) throws Exception {
/*  166 */     this.operations.add(operation);
/*      */   }
/*      */   
/*      */   protected void responseBodyBlock(Block block) throws Exception {
/*  170 */     registerBlock(block);
/*      */   }
/*      */   
/*      */   protected void responseHeaderBlock(Block block) throws Exception {
/*  174 */     registerBlock(block);
/*      */   }
/*      */   
/*      */   protected void requestBodyBlock(Block block) throws Exception {
/*  178 */     registerBlock(block);
/*      */   }
/*      */   
/*      */   protected void requestHeaderBlock(Block block) throws Exception {
/*  182 */     registerBlock(block);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {
/*  187 */     if (isRegistered((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  190 */     registerType((AbstractType)type);
/*      */   }
/*      */   
/*      */   protected void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {
/*  194 */     if (isRegistered((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  197 */     registerType((AbstractType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {
/*  202 */     if (isRegistered((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  205 */     registerType((AbstractType)type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralFragmentType(LiteralFragmentType type) throws Exception {
/*  211 */     if (isRegistered((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  214 */     registerType((AbstractType)type);
/*      */   }
/*      */   
/*      */   private void registerBlock(Block block) {
/*  218 */     String key = null;
/*  219 */     if (block.getType().isSOAPType()) {
/*  220 */       key = block.getType().getJavaType().getRealName();
/*  221 */     } else if (block.getType().isLiteralType()) {
/*  222 */       key = block.getType().getName().toString() + block.getType().getJavaType().getRealName();
/*      */     } 
/*      */ 
/*      */     
/*  226 */     if (!this.portTypes.containsKey(key)) {
/*  227 */       this.portTypes.put(key, block);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isRegistered(AbstractType type) {
/*  232 */     return this.types.contains(type);
/*      */   }
/*      */   
/*      */   private void registerType(AbstractType type) {
/*  236 */     this.types.add(type);
/*      */   }
/*      */   
/*      */   protected void writeClass() {
/*  240 */     String remoteClassName = this.port.getJavaInterface().getName();
/*  241 */     String className = getClassName();
/*  242 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*  243 */       log("Class " + className + " exists. Not overriding.");
/*      */       return;
/*      */     } 
/*  246 */     this.srcFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
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
/*      */     try {
/*  258 */       this.out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(this.srcFile)));
/*      */ 
/*      */       
/*  261 */       writePackage(this.out, className);
/*  262 */       writeImports(this.out);
/*  263 */       this.out.pln();
/*  264 */       writeClassDecl(this.out, className);
/*  265 */       this.out.pln();
/*  266 */       writeStatic(this.out);
/*  267 */       this.out.pln();
/*  268 */       writeConstructor(this.out, className);
/*  269 */       this.out.pln();
/*  270 */       writeOperations(this.out, remoteClassName);
/*  271 */       this.out.pln();
/*  272 */       writePeekFirstBodyElementMethod(this.out);
/*  273 */       this.out.pln();
/*  274 */       writeReadFirstBodyElement(this.out);
/*  275 */       this.out.pln();
/*  276 */       Map headerMap = writeReadHeaderElementMethod(this.out);
/*  277 */       this.out.pln();
/*  278 */       writeHeaderDeserializeMethods(this.out, headerMap.values().iterator());
/*  279 */       this.out.pln();
/*  280 */       writeOperationDeserializeMethods(this.out);
/*  281 */       this.out.pln();
/*  282 */       writeReadBodyFaultElement(this.out);
/*  283 */       this.out.pln();
/*  284 */       writeProcessingHookMethod(this.out);
/*  285 */       this.out.pln();
/*  286 */       writeGenericMethods(this.out);
/*  287 */       this.out.pln();
/*  288 */       writeUsesSOAPActionForDispatching(this.out);
/*  289 */       this.out.pln();
/*  290 */       writeGetOpcodeForFirstBodyElementName(this.out);
/*  291 */       this.out.pln();
/*  292 */       writeGetOpcodeForSOAPAction(this.out);
/*  293 */       this.out.pln();
/*  294 */       writeGetMethodForOpcode(this.out);
/*  295 */       this.out.pln();
/*  296 */       writeGetNamespaceDeclarationsMethod(this.out);
/*  297 */       this.out.pln();
/*  298 */       writeGetUnderstoodHeadersMethod(this.out);
/*  299 */       this.out.pln();
/*  300 */       if (this.genAddAttachmentMethod) {
/*  301 */         writeAddAttachmentMethod(this.out);
/*  302 */         this.out.pln();
/*      */       } 
/*  304 */       if (this.genGetAttachmentMethod) {
/*  305 */         writeGetAttachmentMethod(this.out);
/*  306 */         this.out.pln();
/*      */       } 
/*  308 */       writeHooks(this.out);
/*  309 */       writeAttachmentHooks(this.out);
/*  310 */       writeInitialize(this.out);
/*  311 */       this.out.pln();
/*  312 */       writeStaticMembers(this.out, headerMap);
/*  313 */       this.out.pln();
/*  314 */       writeUnderstoodHeadersMember(this.out, headerMap);
/*  315 */       closeSrcFile();
/*  316 */     } catch (IOException e) {
/*  317 */       fail("generator.cant.write", this.port.getName().getLocalPart());
/*  318 */     } catch (ClassNotFoundException c) {
/*  319 */       fail("generator.cant.find Class");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeAttachmentHooks(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeHooks(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeImports(IndentingWriter p) throws IOException {
/*  336 */     p.pln("import com.sun.xml.rpc.server.http.MessageContextProperties;");
/*  337 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/*  338 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/*  339 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAPConstants;");
/*  340 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAP12Constants;");
/*  341 */     p.pln("import com.sun.xml.rpc.encoding.literal.*;");
/*  342 */     p.pln("import com.sun.xml.rpc.soap.streaming.*;");
/*  343 */     p.pln("import com.sun.xml.rpc.soap.message.*;");
/*  344 */     p.pln("import com.sun.xml.rpc.soap.SOAPVersion;");
/*  345 */     p.pln("import com.sun.xml.rpc.soap.SOAPEncodingConstants;");
/*  346 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/*  347 */     p.pln("import javax.xml.namespace.QName;");
/*  348 */     p.pln("import java.rmi.RemoteException;");
/*  349 */     p.pln("import java.util.Iterator;");
/*  350 */     p.pln("import java.lang.reflect.*;");
/*  351 */     p.pln("import java.lang.Class;");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeStatic(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeClassDecl(IndentingWriter p, String className) throws IOException {
/*  365 */     p.pln("public class " + Names.stripQualifier(className));
/*  366 */     p.pln(" {");
/*  367 */     p.pln();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeConstructor(IndentingWriter p, String stubClassName) throws IOException {
/*  372 */     p.pln("/*");
/*  373 */     p.pln(" *  public constructor");
/*  374 */     p.pln(" */");
/*  375 */     p.plnI("public " + Names.stripQualifier(stubClassName) + "() {");
/*  376 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeOperations(IndentingWriter p, String remoteClassName) throws IOException {
/*  381 */     Iterator<Operation> iter = this.operations.iterator();
/*      */     
/*  383 */     for (int i = 0; iter.hasNext(); i++) {
/*  384 */       if (i > 0)
/*  385 */         p.pln(); 
/*  386 */       Operation operation = iter.next();
/*  387 */       if (operation.getStyle() == SOAPStyle.DOCUMENT) {
/*  388 */         writeDocumentLiteralOperation(p, remoteClassName, operation);
/*      */       }
/*  390 */       else if (operation.getUse() == SOAPUse.LITERAL) {
/*  391 */         writeRpcLiteralOperation(p, remoteClassName, operation);
/*      */       } else {
/*  393 */         writeRpcEncodedOperation(p, remoteClassName, operation);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeRpcEncodedOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeRpcLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeDocumentLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePeekFirstBodyElementMethod(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePreSendingHookMethod(IndentingWriter p, List operations) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePostSendingHook(IndentingWriter p, List operations) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePostEnvelopeReadingHook(IndentingWriter p, List operations) throws IOException {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePreResponseWritingHook(IndentingWriter p, List operations) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeReadFirstBodyElement(IndentingWriter p) throws IOException {
/*  441 */     Operation operationWithEmptyBody = null;
/*      */     
/*  443 */     String stateType = getStateType();
/*  444 */     p.pln("/*");
/*  445 */     p.pln(" *  this method deserializes the request/response structure in the body");
/*      */     
/*  447 */     p.pln(" */");
/*  448 */     p.plnI("protected void " + this.prefix + "readFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, " + stateType + "  state) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  454 */     p.pln("int opcode = state.getRequest().getOperationCode();");
/*  455 */     p.plnI("switch (opcode) {");
/*  456 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */     
/*  458 */     while (operationsIter.hasNext()) {
/*  459 */       Operation operation = operationsIter.next();
/*  460 */       if (!needsReadFirstBodyElementFor(operation))
/*      */         continue; 
/*  462 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/*  466 */       p.pln(this.prefix + "deserialize_" + this.env.getNames().validInternalJavaIdentifier(operation.getUniqueName()) + "(bodyReader, deserializationContext, state);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  472 */       p.pln("break;");
/*  473 */       p.pO();
/*      */       
/*  475 */       if (operationWithEmptyBody == null)
/*  476 */         operationWithEmptyBody = operationHasEmptyBody(operation); 
/*      */     } 
/*  478 */     p.plnI("default:");
/*  479 */     writeReadFirstBodyElementDefault(p, "opcode");
/*  480 */     p.pO();
/*  481 */     p.pOln("}");
/*  482 */     p.pOln("}");
/*      */     
/*  484 */     if (operationWithEmptyBody != null) {
/*  485 */       writeHandleEmptyBody(p, operationWithEmptyBody);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Operation operationHasEmptyBody(Operation operation) {
/*  494 */     return null;
/*      */   }
/*      */   
/*      */   protected boolean needsReadFirstBodyElementFor(Operation operation) {
/*  498 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeHandleEmptyBody(IndentingWriter p, Operation operation) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeReadFirstBodyElementDefault(IndentingWriter p, String state) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeOperationDeserializeMethods(IndentingWriter p) throws IOException {
/*  513 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */     
/*  515 */     for (int i = 0; operationsIter.hasNext(); i++) {
/*  516 */       if (i > 0)
/*  517 */         p.pln(); 
/*  518 */       Operation operation = operationsIter.next();
/*  519 */       writeOperationDeserializeMethod(p, operation);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeOperationDeserializeMethod(IndentingWriter p, Operation operation) throws IOException {
/*  527 */     String stateType = getStateType();
/*  528 */     String messageName = operation.getName().getLocalPart();
/*      */     
/*  530 */     Message message = getMessageToDeserialize(operation);
/*  531 */     if (message == null)
/*      */       return; 
/*  533 */     p.pln("/*");
/*  534 */     p.pln(" * This method deserializes the body of the " + messageName + " operation.");
/*      */ 
/*      */ 
/*      */     
/*  538 */     p.pln(" */");
/*  539 */     p.plnI("private void " + this.prefix + "deserialize_" + this.env.getNames().validInternalJavaIdentifier(operation.getUniqueName()) + "(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, " + stateType + " state) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  549 */     if (!message.getBodyBlocks().hasNext()) {
/*  550 */       p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(null);");
/*  551 */       p.pln("state." + getStateGetRequestResponseString() + "().setBody(bodyBlock);");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  556 */       Block bodyBlock = message.getBodyBlocks().next();
/*  557 */       AbstractType type = bodyBlock.getType();
/*  558 */       String objName = this.env.getNames().getTypeMemberName(type.getJavaType());
/*      */       
/*  560 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*      */       
/*  562 */       String serializer = writer.serializerMemberName();
/*      */       
/*  564 */       p.plnI("java.lang.Object " + objName + "Obj =");
/*  565 */       p.plnI(serializer + ".deserialize(" + this.env.getNames().getBlockQNameName(operation, bodyBlock) + ",");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  570 */       p.pln("bodyReader, deserializationContext);");
/*  571 */       p.pO();
/*  572 */       p.pO();
/*      */       
/*  574 */       objName = this.env.getNames().getTypeMemberName(type.getJavaType()) + "Obj";
/*      */       
/*  576 */       p.pln();
/*  577 */       p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, bodyBlock) + ");");
/*      */ 
/*      */ 
/*      */       
/*  581 */       p.pln("bodyBlock.setValue(" + objName + ");");
/*  582 */       p.pln("state." + getStateGetRequestResponseString() + "().setBody(bodyBlock);");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeReadBodyFaultElement(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map writeReadHeaderElementMethod(IndentingWriter p) throws IOException {
/*  598 */     Iterator<Operation> ops = this.operations.iterator();
/*      */ 
/*      */ 
/*      */     
/*  602 */     boolean hasHeaders = false;
/*  603 */     while (!hasHeaders && ops.hasNext()) {
/*  604 */       Operation operation = ops.next();
/*  605 */       Request request = operation.getRequest();
/*  606 */       Iterator headers = request.getHeaderBlocks();
/*  607 */       hasHeaders = (!hasHeaders && headers.hasNext()) ? true : hasHeaders;
/*  608 */       Response response = operation.getResponse();
/*  609 */       headers = (response != null) ? response.getHeaderBlocks() : null;
/*  610 */       hasHeaders = (!hasHeaders && headers != null && headers.hasNext()) ? true : hasHeaders;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  615 */     Map<Object, Object> headerMap = new HashMap<Object, Object>();
/*  616 */     if (!hasHeaders) {
/*  617 */       return headerMap;
/*      */     }
/*  619 */     String stateType = getStateType();
/*  620 */     p.pln("/*");
/*  621 */     p.pln(" * This method must deserialize headers. It dispatches to a read method based on the name");
/*      */     
/*  623 */     p.pln(" * of the header.");
/*  624 */     p.pln(" */");
/*  625 */     p.plnI("protected boolean " + this.prefix + "readHeaderElement(SOAPHeaderBlockInfo headerInfo, XMLReader headerReader, SOAPDeserializationContext deserializationContext, " + stateType + " state) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     ops = this.operations.iterator();
/*  632 */     boolean first = true;
/*  633 */     ArrayList<Block> list = new ArrayList();
/*  634 */     while (ops.hasNext()) {
/*  635 */       Operation operation = ops.next();
/*      */       
/*  637 */       Request request = operation.getRequest();
/*  638 */       list.clear(); Iterator<Fault> iterator1;
/*  639 */       for (iterator1 = request.getHeaderBlocks(); iterator1.hasNext();) {
/*  640 */         list.add(iterator1.next());
/*      */       }
/*      */       
/*  643 */       for (iterator1 = operation.getFaults(); iterator1.hasNext();) {
/*  644 */         list.add(((Fault)iterator1.next()).getBlock());
/*      */       }
/*      */       
/*  647 */       writeHeaderChecks(p, list.iterator(), first, headerMap);
/*  648 */       first = (first && request.getHeaderBlocks().hasNext()) ? false : first;
/*      */ 
/*      */       
/*  651 */       Response response = operation.getResponse();
/*  652 */       list.clear();
/*      */       
/*  654 */       if (response != null) {
/*  655 */         for (Iterator<Block> iterator = response.getHeaderBlocks(); iterator.hasNext();) {
/*  656 */           list.add(iterator.next());
/*      */         }
/*      */       }
/*  659 */       for (Iterator<Fault> iter = operation.getFaults(); iter.hasNext();) {
/*  660 */         list.add(((Fault)iter.next()).getBlock());
/*      */       }
/*      */       
/*  663 */       writeHeaderChecks(p, list.iterator(), first, headerMap);
/*  664 */       first = (first && response.getHeaderBlocks().hasNext()) ? false : first;
/*      */     } 
/*      */     
/*  667 */     p.pln();
/*  668 */     p.pln("headerReader.skipElement();");
/*  669 */     p.pln("return false;");
/*  670 */     p.pOln("}");
/*  671 */     return headerMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeHeaderChecks(IndentingWriter p, Iterator<Block> headers, boolean first, Map<QName, Block> headerMap) throws IOException {
/*  681 */     while (headers.hasNext()) {
/*  682 */       Block header = headers.next();
/*  683 */       if (headerMap.containsKey(header.getName())) {
/*      */         continue;
/*      */       }
/*  686 */       headerMap.put(header.getName(), header);
/*      */       
/*  688 */       if (!first) {
/*  689 */         p.p(" else ");
/*      */       }
/*  691 */       first = false;
/*  692 */       String qname = this.env.getNames().getBlockQNameName(null, header);
/*  693 */       String uname = this.env.getNames().getBlockUniqueName(null, header);
/*  694 */       p.plnI("if (headerInfo.getName().equals(" + qname + ")) {");
/*  695 */       p.pln(this.prefix + "deserialize_" + uname + "(headerInfo, headerReader, deserializationContext, state);");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  700 */       p.pln("return true;");
/*  701 */       p.pO("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeHeaderDeserializeMethods(IndentingWriter p, Iterator<Block> headers) throws IOException {
/*  710 */     for (int i = 0; headers.hasNext(); i++) {
/*  711 */       if (i > 0)
/*  712 */         p.pln(); 
/*  713 */       Block header = headers.next();
/*  714 */       writeHeaderDeserializeMethod(p, header);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeHeaderDeserializeMethod(IndentingWriter p, Block header) throws IOException {
/*  720 */     String javaType = header.getType().getJavaType().getName();
/*  721 */     String serializer = this.writerFactory.createWriter(this.servicePackage, header.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */     
/*  725 */     String qname = this.env.getNames().getBlockQNameName(null, header);
/*  726 */     String uname = this.env.getNames().getBlockUniqueName(null, header);
/*  727 */     String stateType = getStateType();
/*  728 */     p.pln("/*");
/*  729 */     p.pln(" *  This method does the actual deserialization for the header: " + header.getName().getLocalPart() + ".");
/*      */ 
/*      */ 
/*      */     
/*  733 */     p.pln(" */");
/*  734 */     p.plnI("private void " + this.prefix + "deserialize_" + uname + "(SOAPHeaderBlockInfo headerInfo, XMLReader bodyReader, SOAPDeserializationContext deserializationContext, " + stateType + " state) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  742 */     p.pln("QName elementName = bodyReader.getName();");
/*  743 */     p.plnI("if (elementName.equals(" + qname + ")) {");
/*      */ 
/*      */     
/*  746 */     String boxName = null;
/*  747 */     if (SimpleToBoxedUtil.isPrimitive(javaType)) {
/*  748 */       boxName = SimpleToBoxedUtil.getBoxedClassName(javaType);
/*      */     } else {
/*  750 */       boxName = javaType;
/*      */     } 
/*      */     
/*  753 */     p.plnI(boxName + " obj =");
/*  754 */     p.pln("(" + boxName + ")" + serializer + ".deserialize(" + qname + ", bodyReader, deserializationContext);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  762 */     p.pOln("headerInfo.setValue(obj);");
/*  763 */     p.pln("state." + getStateGetRequestResponseString() + "().add(headerInfo);");
/*      */ 
/*      */ 
/*      */     
/*  767 */     p.pOlnI("} else {");
/*  768 */     p.pln("// the QName of the header is not what we expected and not a fault either");
/*      */     
/*  770 */     p.pln("throw new SOAPProtocolViolationException(\"soap.unexpectedHeaderBlock\", elementName.getLocalPart());");
/*      */     
/*  772 */     p.pOln("}");
/*  773 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeProcessingHookMethod(IndentingWriter p) throws IOException {}
/*      */ 
/*      */   
/*      */   public String getEncodingStyle() {
/*  781 */     if (this.port.getSOAPVersion().equals(SOAPVersion.SOAP_12.toString())) {
/*  782 */       return "SOAP12NamespaceConstants.ENCODING";
/*      */     }
/*  784 */     return "SOAPNamespaceConstants.ENCODING";
/*      */   }
/*      */   
/*      */   public void writeGenericMethods(IndentingWriter p) throws IOException {
/*  788 */     writeGetDefaultEnvelopeEncodingStyle(p);
/*  789 */     p.pln();
/*  790 */     p.plnI("public java.lang.String " + this.prefix + "getImplicitEnvelopeEncodingStyle() {");
/*      */     
/*  792 */     p.pln("return \"\";");
/*  793 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeUsesSOAPActionForDispatching(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeGetOpcodeForFirstBodyElementName(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeGetOpcodeForSOAPAction(IndentingWriter p) throws IOException {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeGetMethodForOpcode(IndentingWriter p) throws IOException, ClassNotFoundException {}
/*      */ 
/*      */   
/*      */   private void writeGetNamespaceDeclarationsMethod(IndentingWriter p) throws IOException {
/*  814 */     p.pln("/*");
/*  815 */     p.pln(" * This method returns an array containing (prefix, nsURI) pairs.");
/*      */     
/*  817 */     p.pln(" */");
/*  818 */     p.plnI("protected java.lang.String[] " + this.prefix + "getNamespaceDeclarations() {");
/*  819 */     p.pln("return myNamespace_declarations;");
/*  820 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetDefaultEnvelopeEncodingStyle(IndentingWriter p) throws IOException {
/*  825 */     boolean useLiteral = false;
/*  826 */     for (Iterator<Operation> iter = this.operations.iterator(); iter.hasNext(); ) {
/*  827 */       Operation operation = iter.next();
/*  828 */       if (!operation.getRequest().isBodyEncoded()) {
/*  829 */         useLiteral = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  834 */     if (useLiteral) {
/*  835 */       p.plnI("protected java.lang.String " + this.prefix + "getDefaultEnvelopeEncodingStyle() {");
/*      */ 
/*      */ 
/*      */       
/*  839 */       p.pln("return null;");
/*  840 */       p.pOln("}");
/*      */     } else {
/*  842 */       p.plnI("public java.lang.String " + this.prefix + "getDefaultEnvelopeEncodingStyle() {");
/*      */ 
/*      */ 
/*      */       
/*  846 */       p.pln("return " + getEncodingStyle() + ";");
/*  847 */       p.pOln("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetUnderstoodHeadersMethod(IndentingWriter p) throws IOException {
/*  853 */     p.pln("/*");
/*  854 */     p.pln(" * This method returns an array containing the names of the headers we understand.");
/*      */     
/*  856 */     p.pln(" */");
/*  857 */     p.plnI("public javax.xml.namespace.QName[] " + this.prefix + "getUnderstoodHeaders() {");
/*  858 */     p.pln("return understoodHeaderNames;");
/*  859 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   protected void writeInitialize(IndentingWriter p) throws IOException {
/*  863 */     Iterator<Map.Entry> types = this.portTypes.entrySet().iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  868 */     String access = getInitializeAccess();
/*  869 */     p.plnI(access + " void " + this.prefix + "initialize(InternalTypeMappingRegistry registry) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  874 */     if (superClassHasInitialize()) {
/*  875 */       p.pln("super." + this.prefix + "initialize(registry);");
/*      */     }
/*  877 */     while (types.hasNext()) {
/*  878 */       Map.Entry entry = types.next();
/*  879 */       Block block = (Block)entry.getValue();
/*  880 */       AbstractType type = block.getType();
/*  881 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*      */       
/*  883 */       writer.initializeSerializer(p, this.env.getNames().getTypeQName(type.getName()), "registry");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  888 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */ 
/*      */     
/*  891 */     for (int i = 0; operationsIter.hasNext(); i++) {
/*  892 */       Operation operation = operationsIter.next();
/*  893 */       if (operation.getFaults().hasNext()) {
/*  894 */         String serName = this.env.getNames().getClassMemberName(this.env.getNames().faultSerializerClassName(this.servicePackage, this.port, operation));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  900 */         p.pln("((Initializable)" + serName + ").initialize(registry);");
/*      */       } 
/*      */       
/*  903 */       for (Iterator<Fault> iter = operation.getFaults(); iter.hasNext(); ) {
/*  904 */         Fault fault = iter.next();
/*  905 */         if (fault instanceof com.sun.xml.rpc.processor.model.HeaderFault) {
/*  906 */           AbstractType type = fault.getBlock().getType();
/*  907 */           SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*      */           
/*  909 */           writer.initializeSerializer(p, this.env.getNames().getTypeQName(type.getName()), "registry");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  916 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeStaticMembers(IndentingWriter p, Map headerMap) throws IOException {
/*  921 */     ArrayList list = new ArrayList();
/*  922 */     ArrayList visited = new ArrayList();
/*  923 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */ 
/*      */     
/*  926 */     p.p("private static final javax.xml.namespace.QName " + this.prefix + "portName = ");
/*  927 */     GeneratorUtil.writeNewQName(p, this.port.getName());
/*  928 */     p.pln(";");
/*      */ 
/*      */     
/*  931 */     for (int i = 0; operationsIter.hasNext(); i++) {
/*  932 */       Operation operation = operationsIter.next();
/*  933 */       p.pln("private static final int " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + " = " + i + ";");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  941 */     operationsIter = this.operations.iterator();
/*      */ 
/*      */     
/*  944 */     for (int k = 0; operationsIter.hasNext(); k++) {
/*  945 */       Operation operation = operationsIter.next();
/*  946 */       Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*  947 */       faultSet.addAll(operation.getFaultsSet());
/*  948 */       Iterator<Fault> faults = faultSet.iterator();
/*  949 */       if (faults.hasNext()) {
/*  950 */         declareStaticFaultSerializerForOperation(p, this.port, operation, this.encodeTypes, this.multiRefEncoding);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  957 */       while (faults.hasNext()) {
/*  958 */         collectNamespaces(((Fault)faults.next()).getBlock().getType(), list, visited);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  965 */       for (Iterator<Fault> iter = operation.getFaults(); iter.hasNext(); ) {
/*  966 */         Fault fault = iter.next();
/*  967 */         if (fault instanceof com.sun.xml.rpc.processor.model.HeaderFault) {
/*  968 */           Set set1 = new HashSet();
/*  969 */           Set faultNames = new HashSet();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  977 */     Set processedTypes = new HashSet();
/*      */     
/*  979 */     operationsIter = this.operations.iterator();
/*      */     
/*  981 */     Iterator blocks = headerMap.values().iterator();
/*  982 */     declareBlockTypes(p, (Operation)null, blocks, processedTypes, list, visited);
/*  983 */     for (int m = 0; operationsIter.hasNext(); m++) {
/*  984 */       Operation operation = operationsIter.next();
/*  985 */       blocks = operation.getRequest().getHeaderBlocks();
/*  986 */       declareBlockTypes(p, operation, blocks, processedTypes, list, visited);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  993 */       blocks = operation.getRequest().getBodyBlocks();
/*  994 */       declareBlockTypes(p, operation, blocks, processedTypes, list, visited);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1001 */       if (operation.getResponse() != null) {
/* 1002 */         blocks = operation.getResponse().getHeaderBlocks();
/* 1003 */         declareBlockTypes(p, operation, blocks, processedTypes, list, visited);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1010 */         blocks = operation.getResponse().getBodyBlocks();
/* 1011 */         declareBlockTypes(p, operation, blocks, processedTypes, list, visited);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1020 */     list.remove(this.soapNamespaceConstants.getXSD());
/* 1021 */     list.remove(this.soapNamespaceConstants.getEncoding());
/* 1022 */     Iterator<String> namespaces = list.iterator();
/*      */     
/* 1024 */     p.plnI("private static final java.lang.String[] myNamespace_declarations =");
/* 1025 */     p.pI(8);
/* 1026 */     p.plnI("new java.lang.String[] {");
/* 1027 */     for (int j = 0; namespaces.hasNext(); j++) {
/* 1028 */       if (j > 0)
/* 1029 */         p.pln(","); 
/* 1030 */       p.p("\"ns" + j + "\", ");
/* 1031 */       p.p("\"" + (String)namespaces.next() + "\"");
/*      */     } 
/* 1033 */     p.pln();
/* 1034 */     p.pOln("};");
/* 1035 */     p.pO(8);
/* 1036 */     p.pO();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeAddAttachmentMethod(IndentingWriter p) throws IOException {
/* 1041 */     p.plnI("private void addAttachment(javax.xml.soap.SOAPMessage message, Object value, java.lang.String mimeType, java.lang.String part) throws Exception{");
/* 1042 */     p.pln("java.lang.String contentId = java.net.URLEncoder.encode(part, \"UTF-8\")+\"=\"+com.sun.xml.rpc.util.JAXRPCUtils.getUUID()+\"@jaxrpc.sun.com\";");
/* 1043 */     p.pln("javax.xml.soap.AttachmentPart _attPart = null;");
/* 1044 */     p.plnI("if(value == null || mimeType == null) {");
/* 1045 */     p.pln("return;");
/* 1046 */     p.pOln("}");
/* 1047 */     p.plnI("if(value instanceof javax.activation.DataHandler) {");
/* 1048 */     p.pln("_attPart = message.createAttachmentPart((javax.activation.DataHandler)value);");
/* 1049 */     p.pOln("}");
/* 1050 */     p.plnI("else if(value instanceof javax.mail.internet.MimeMultipart) {");
/* 1051 */     p.pln("java.lang.String contentType = ((javax.mail.internet.MimeMultipart) value).getContentType();");
/* 1052 */     p.pln("javax.activation.DataHandler dataHandler = new javax.activation.DataHandler(value, contentType);");
/* 1053 */     p.pln("_attPart = message.createAttachmentPart(dataHandler);");
/* 1054 */     p.pOln("}");
/* 1055 */     p.plnI("else {");
/* 1056 */     p.pln("_attPart = message.createAttachmentPart(value, mimeType);");
/* 1057 */     p.pOln("}");
/* 1058 */     p.pln("_attPart.setContentId(\"<\"+contentId+\">\");");
/* 1059 */     p.pln("message.addAttachmentPart(_attPart);");
/* 1060 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetAttachmentMethod(IndentingWriter p) throws IOException {
/* 1065 */     p.plnI("private Object getAttachment(javax.xml.soap.SOAPMessage message, java.lang.String[] mimeTypes, java.lang.String partName, boolean isDataHandler) throws Exception{");
/* 1066 */     p.pln("javax.xml.soap.AttachmentPart _attPart = null;");
/* 1067 */     p.plnI("for(int i = 0; i < mimeTypes.length; i++) {");
/* 1068 */     p.pln("java.lang.String mimeType = mimeTypes[i];");
/* 1069 */     p.pln("javax.xml.soap.MimeHeaders mimeHeaders = new javax.xml.soap.MimeHeaders();");
/* 1070 */     p.pln("mimeHeaders.addHeader(\"Content-Type\", mimeType);");
/* 1071 */     p.pln("java.util.Iterator attachments = null;");
/* 1072 */     p.plnI("if(mimeType.endsWith(\"/*\") || mimeType.startsWith(\"multipart/\")) {");
/* 1073 */     p.pln("attachments = message.getAttachments();");
/* 1074 */     p.pOln("}");
/* 1075 */     p.plnI("else {");
/* 1076 */     p.pln("attachments = message.getAttachments(mimeHeaders);");
/* 1077 */     p.pOln("}");
/* 1078 */     p.plnI("if(attachments == null) {");
/* 1079 */     p.pln("continue;");
/* 1080 */     p.pOln("}");
/* 1081 */     p.plnI("while (attachments.hasNext()) {");
/* 1082 */     p.pln("_attPart = (javax.xml.soap.AttachmentPart)attachments.next();");
/* 1083 */     p.pln("java.lang.String cId = _attPart.getContentId();");
/* 1084 */     p.pln("int index = cId.lastIndexOf('@', cId.length());");
/* 1085 */     p.plnI("if(index == -1){");
/* 1086 */     p.pln("continue;");
/* 1087 */     p.pOln("}");
/* 1088 */     p.pln("java.lang.String localPart = cId.substring(0, index);");
/* 1089 */     p.pln("index = localPart.lastIndexOf('=', localPart.length());");
/* 1090 */     p.plnI("if(index == -1){");
/* 1091 */     p.pln("continue;");
/* 1092 */     p.pOln("}");
/* 1093 */     p.pln("java.lang.String part = java.net.URLDecoder.decode(localPart.substring(0, index), \"UTF-8\");");
/* 1094 */     p.plnI("if(part.equals(partName) || part.equals(\"<\"+partName)) {");
/* 1095 */     p.plnI("if(isDataHandler) {");
/* 1096 */     p.pln("return _attPart.getDataHandler();");
/* 1097 */     p.pOln("}");
/* 1098 */     p.plnI("else {");
/* 1099 */     p.pln("return _attPart.getContent();");
/* 1100 */     p.pOln("}");
/* 1101 */     p.pOln("}");
/* 1102 */     p.pOln("}");
/* 1103 */     p.pOln("}");
/* 1104 */     p.pln("throw new DeserializationException(\"soap.missing.attachment.for.id\", partName);");
/* 1105 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   protected void setAddAttachmentMethodFlag(boolean value) {
/* 1109 */     this.genAddAttachmentMethod = value;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setGetAttachmentMethodFlag(boolean value) {
/* 1114 */     this.genGetAttachmentMethod = value;
/*      */   }
/*      */   
/*      */   protected boolean getAddAttachmentMethodFlag() {
/* 1118 */     return this.genAddAttachmentMethod;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean getGetAttachmentMethodFlag() {
/* 1123 */     return this.genGetAttachmentMethod;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void writeUnderstoodHeadersMember(IndentingWriter paramIndentingWriter, Map paramMap) throws IOException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareStaticFaultSerializerForOperation(IndentingWriter p, Port port, Operation operation, boolean encodeTypesNow, boolean multiRefEncodingNow) throws IOException {
/* 1140 */     String nillable = "NOT_NULLABLE";
/* 1141 */     String referenceable = "REFERENCEABLE";
/* 1142 */     String multiRef = "DONT_SERIALIZE_AS_REF";
/* 1143 */     String encodeType = encodeTypesNow ? "ENCODE_TYPE" : "DONT_ENCODE_TYPE";
/*      */ 
/*      */ 
/*      */     
/* 1147 */     String serializerClassName = this.env.getNames().faultSerializerClassName(this.servicePackage, port, operation);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1152 */     String memberName = this.env.getNames().getClassMemberName(serializerClassName);
/* 1153 */     p.plnI("private final CombinedSerializer " + memberName + " = new ReferenceableSerializerImpl(" + multiRef + ",");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1159 */     p.pln("new " + serializerClassName + "(" + encodeType + ", " + nillable + "));");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1167 */     p.pO();
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
/*      */   private void declareBlockTypes(IndentingWriter p, Operation operation, Iterator<Block> blocks, Set<String> processedTypes, List list, List visited) throws IOException {
/* 1179 */     while (blocks.hasNext()) {
/* 1180 */       Block block = blocks.next();
/* 1181 */       collectNamespaces(block.getType(), list, visited);
/* 1182 */       if (!processedTypes.contains(this.env.getNames().getBlockQNameName(operation, block))) {
/*      */         
/* 1184 */         GeneratorUtil.writeBlockQNameDeclaration(p, operation, block, this.env.getNames());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1189 */         processedTypes.add(this.env.getNames().getBlockQNameName(operation, block));
/*      */       } 
/*      */       
/* 1192 */       if (block.getType().isSOAPType()) {
/* 1193 */         SOAPEncoding.writeStaticSerializer(p, this.servicePackage, (SOAPType)block.getType(), processedTypes, this.writerFactory, this.env.getNames());
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/* 1200 */       if (block.getType().isLiteralType()) {
/* 1201 */         LiteralEncoding.writeStaticSerializer(p, this.servicePackage, (LiteralType)block.getType(), processedTypes, this.writerFactory, this.env.getNames());
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
/*      */ 
/*      */   
/*      */   protected static void collectNamespaces(AbstractType type, List<String> list, List<String> visited) {
/* 1216 */     if (visited.contains(type.getJavaType().getRealName()))
/*      */       return; 
/* 1218 */     visited.add(type.getJavaType().getRealName());
/* 1219 */     if (type.getName().getNamespaceURI().length() > 0 && !list.contains(type.getName().getNamespaceURI()))
/*      */     {
/* 1221 */       list.add(type.getName().getNamespaceURI());
/*      */     }
/* 1223 */     if (type instanceof SOAPStructureType) {
/*      */       
/* 1225 */       Iterator<SOAPStructureMember> members = ((SOAPStructureType)type).getMembers();
/* 1226 */       while (members.hasNext()) {
/* 1227 */         SOAPStructureMember member = members.next();
/* 1228 */         if (member.getName().getNamespaceURI().length() > 0 && !list.contains(member.getName().getNamespaceURI()))
/*      */         {
/* 1230 */           list.add(member.getName().getNamespaceURI());
/*      */         }
/* 1232 */         collectNamespaces((AbstractType)member.getType(), list, visited);
/*      */       } 
/* 1234 */     } else if (type instanceof SOAPArrayType) {
/* 1235 */       collectNamespaces((AbstractType)((SOAPArrayType)type).getElementType(), list, visited);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JavaStructureMember getJavaMember(Parameter parameter) {
/* 1243 */     Block block = parameter.getBlock();
/* 1244 */     JavaType type = block.getType().getJavaType();
/* 1245 */     JavaStructureMember member = null;
/* 1246 */     if (type instanceof JavaStructureType) {
/* 1247 */       member = ((JavaStructureType)type).getMemberByName(parameter.getName());
/*      */       
/* 1249 */       return member;
/*      */     } 
/*      */     
/* 1252 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private void closeSrcFile() throws IOException {
/* 1257 */     if (this.out != null) {
/* 1258 */       this.out.pOln("}");
/* 1259 */       this.out.close();
/* 1260 */       this.out = null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\StubTieGeneratorBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */