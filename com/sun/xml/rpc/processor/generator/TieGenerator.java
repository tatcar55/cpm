/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*      */ import com.sun.xml.rpc.processor.config.HandlerInfo;
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
/*      */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttachmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*      */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
/*      */ import com.sun.xml.rpc.tools.wscompile.TieHooksIf;
/*      */ import com.sun.xml.rpc.util.VersionUtil;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.ArrayList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TieGenerator
/*      */   extends StubTieGeneratorBase
/*      */ {
/*      */   private Set operationNames;
/*      */   private Set soapActionValues;
/*      */   private boolean hasUniqueOperationNames;
/*      */   private boolean hasUniqueSoapActions;
/*   93 */   private String dirPath = "";
/*      */   
/*      */   private String sourceVersion;
/*      */ 
/*      */   
/*      */   public TieGenerator() {}
/*      */   
/*      */   public TieGenerator(SOAPVersion ver) {
/*  101 */     super(ver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  108 */     return new TieGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  116 */     return new TieGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TieGenerator(Model model, Configuration config, Properties properties) {
/*  123 */     super(model, config, properties);
/*  124 */     String key = "sourceDirectory";
/*  125 */     this.dirPath = properties.getProperty(key);
/*  126 */     key = "sourceVersion";
/*  127 */     this.sourceVersion = properties.getProperty(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TieGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  135 */     super(model, config, properties, ver);
/*  136 */     String key = "sourceDirectory";
/*  137 */     this.dirPath = properties.getProperty(key);
/*  138 */     key = "sourceVersion";
/*  139 */     this.sourceVersion = properties.getProperty(key);
/*      */   }
/*      */   
/*      */   protected String getClassName() {
/*  143 */     return this.env.getNames().tieFor(this.port);
/*      */   }
/*      */   
/*      */   protected String getStateType() {
/*  147 */     return "StreamingHandlerState";
/*      */   }
/*      */   
/*      */   protected Message getMessageToDeserialize(Operation operation) {
/*  151 */     Request request = operation.getRequest();
/*  152 */     if (request.getBodyBlockCount() > 1)
/*      */     {
/*  154 */       fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */     }
/*      */ 
/*      */     
/*  158 */     return (Message)request;
/*      */   }
/*      */   
/*      */   protected String getStateGetRequestResponseString() {
/*  162 */     return "getRequest";
/*      */   }
/*      */   
/*      */   protected String getInitializeAccess() {
/*  166 */     return "private";
/*      */   }
/*      */   
/*      */   protected boolean superClassHasInitialize() {
/*  170 */     return false;
/*      */   }
/*      */   
/*      */   protected void writeImports(IndentingWriter p) throws IOException {
/*  174 */     super.writeImports(p);
/*  175 */     p.pln("import com.sun.xml.rpc.server.*;");
/*  176 */     p.pln("import javax.xml.rpc.handler.HandlerInfo;");
/*  177 */     p.pln("import com.sun.xml.rpc.client.HandlerChainImpl;");
/*      */   }
/*      */   
/*      */   protected void preVisitPort(Port port) throws Exception {
/*  181 */     super.preVisitPort(port);
/*  182 */     this.operationNames = new HashSet();
/*  183 */     this.soapActionValues = new HashSet();
/*  184 */     this.hasUniqueOperationNames = true;
/*  185 */     this.hasUniqueSoapActions = true;
/*      */   }
/*      */   
/*      */   protected void postVisitPort(Port port) throws Exception {
/*  189 */     this.operationNames = null;
/*  190 */     this.soapActionValues = null;
/*  191 */     super.postVisitPort(port);
/*      */   }
/*      */   
/*      */   protected void preVisitOperation(Operation operation) throws Exception {
/*  195 */     String name = operation.getName().getLocalPart();
/*  196 */     if (this.operationNames.contains(name)) {
/*  197 */       this.hasUniqueOperationNames = false;
/*      */     }
/*  199 */     this.operationNames.add(name);
/*      */     
/*  201 */     if (operation.getSOAPAction() != null) {
/*  202 */       if (this.soapActionValues.contains(operation.getSOAPAction())) {
/*  203 */         this.hasUniqueSoapActions = false;
/*      */       }
/*  205 */       this.soapActionValues.add(operation.getSOAPAction());
/*      */     } else {
/*      */       
/*  208 */       this.hasUniqueSoapActions = false;
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
/*      */   protected void writeClassDecl(IndentingWriter p, String tieClassName) throws IOException {
/*  220 */     File classFile = this.env.getNames().sourceFileForClass(tieClassName, tieClassName, new File(this.dirPath), this.env);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  226 */     GeneratedFileInfo fi = new GeneratedFileInfo();
/*  227 */     fi.setFile(classFile);
/*  228 */     fi.setType("Tie");
/*  229 */     this.env.addGeneratedFile(fi);
/*      */ 
/*      */     
/*  232 */     p.plnI("public class " + Names.stripQualifier(tieClassName));
/*  233 */     p.pln("extends com.sun.xml.rpc.server.TieBase implements SerializerConstants {");
/*  234 */     p.pln();
/*      */   }
/*      */   
/*      */   protected String getSOAPVersion() {
/*  238 */     if (this.port.getSOAPVersion().equals(SOAPVersion.SOAP_11.toString())) {
/*  239 */       return " SOAPVersion.SOAP_11";
/*      */     }
/*  241 */     return " SOAPVersion.SOAP_12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeConstructor(IndentingWriter p, String tieClassName) throws IOException {
/*  248 */     JavaInterface intf = this.service.getJavaInterface();
/*  249 */     String serializerRegistryName = this.env.getNames().serializerRegistryClassName(intf);
/*      */     
/*  251 */     p.plnI("public " + Names.stripQualifier(tieClassName) + "() throws Exception {");
/*      */ 
/*      */ 
/*      */     
/*  255 */     p.pln("super(new " + serializerRegistryName + "().getRegistry());");
/*      */ 
/*      */ 
/*      */     
/*  259 */     p.pln("initialize(internalTypeMappingRegistry);");
/*  260 */     writeHandlerInfo(p);
/*  261 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeHandlerInfo(IndentingWriter p) throws IOException {
/*  266 */     HandlerChainInfo portServiceHandlers = this.port.getServerHandlerChainInfo();
/*      */     
/*  268 */     Iterator<HandlerInfo> eachHandler = portServiceHandlers.getHandlers();
/*  269 */     if (eachHandler.hasNext()) {
/*  270 */       p.pln();
/*  271 */       p.plnI("{");
/*  272 */       p.pln("java.util.List handlerInfos = new java.util.Vector();");
/*  273 */       while (eachHandler.hasNext()) {
/*  274 */         HandlerInfo currentHandler = eachHandler.next();
/*  275 */         Map properties = currentHandler.getProperties();
/*  276 */         String propertiesName = "props";
/*  277 */         p.plnI("{");
/*  278 */         p.pln("java.util.Map " + propertiesName + " = new java.util.HashMap();");
/*      */ 
/*      */ 
/*      */         
/*  282 */         Iterator<Map.Entry> entries = properties.entrySet().iterator();
/*  283 */         while (entries.hasNext()) {
/*      */           
/*  285 */           Map.Entry entry = entries.next();
/*  286 */           p.pln(propertiesName + ".put(\"" + (String)entry.getKey() + "\", \"" + (String)entry.getValue() + "\");");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  295 */         Object[] headers = currentHandler.getHeaderNames().toArray();
/*      */         
/*  297 */         if (headers != null && headers.length > 0) {
/*  298 */           p.plnI("QName[] headers = {");
/*  299 */           for (int j = 0; j < headers.length; j++) {
/*  300 */             QName hdr = (QName)headers[j];
/*      */             
/*  302 */             p.pln("new QName(\"" + hdr.getNamespaceURI() + "\"" + ", " + "\"" + hdr.getLocalPart() + "\"" + ")" + ((j != headers.length - 1) ? "," : ""));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  314 */           p.pOln("};");
/*      */         } else {
/*  316 */           p.pln("QName[] headers = null;");
/*      */         } 
/*  318 */         p.pln("HandlerInfo handlerInfo = new HandlerInfo(" + currentHandler.getHandlerClassName() + ".class" + ", " + propertiesName + ", headers);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  325 */         p.pln("handlerInfos.add(handlerInfo);");
/*  326 */         p.pOln("}");
/*      */       } 
/*      */       
/*  329 */       Set roles = portServiceHandlers.getRoles();
/*  330 */       p.p("java.lang.String[] roles = new java.lang.String[] {");
/*      */       
/*  332 */       boolean first = true;
/*  333 */       Iterator<String> i = roles.iterator();
/*  334 */       while (i.hasNext()) {
/*  335 */         if (!first) {
/*  336 */           p.p(", ");
/*      */         } else {
/*  338 */           first = false;
/*  339 */         }  p.p("\"" + i.next() + "\"");
/*      */       } 
/*      */       
/*  342 */       p.pln("};");
/*      */       
/*  344 */       p.pln("handlerChain = new HandlerChainImpl(handlerInfos);");
/*  345 */       p.pln("handlerChain.setRoles(roles);");
/*  346 */       p.pln("handlerChain.addUnderstoodHeaders(getUnderstoodHeaders());");
/*      */       
/*  348 */       p.pOln("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePeekFirstBodyElementMethod(IndentingWriter p) throws IOException {
/*  356 */     boolean useOperationNameDispatching = this.hasUniqueOperationNames;
/*  357 */     boolean useSoapActionDispatching = (!useOperationNameDispatching && this.hasUniqueSoapActions);
/*      */ 
/*      */     
/*  360 */     if (!useOperationNameDispatching && !useSoapActionDispatching)
/*      */     {
/*  362 */       throw new GeneratorException("generator.tie.port.cannot.dispatch", this.port.getName().getLocalPart());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  367 */     p.pln("/*");
/*  368 */     p.pln(" * This method must determine the opcode of the operation that has been invoked.");
/*      */     
/*  370 */     p.pln(" */");
/*  371 */     p.plnI("protected void peekFirstBodyElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {");
/*      */ 
/*      */     
/*  374 */     if (useSoapActionDispatching) {
/*  375 */       p.pln("java.lang.String soapaction = null;");
/*  376 */       p.pln("java.lang.String[] soapactionheaders = state.getMessageContext().getMessage().getMimeHeaders().getHeader(\"SOAPAction\");");
/*      */       
/*  378 */       p.plnI("if (soapactionheaders.length > 0) {");
/*  379 */       p.pln("soapaction = soapactionheaders[0];");
/*  380 */       p.pOlnI("} else {");
/*  381 */       p.pln("throw new SOAPProtocolViolationException(\"soap.request.missing.soapaction.cannot.dispatch\");");
/*      */       
/*  383 */       p.pOln("}");
/*      */     } 
/*      */     
/*  386 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */ 
/*      */     
/*  389 */     Operation operationWithEmptyBody = null;
/*  390 */     int j = 0;
/*  391 */     while (operationsIter.hasNext()) {
/*  392 */       Operation operation = operationsIter.next();
/*  393 */       if (useOperationNameDispatching) {
/*  394 */         Request request = operation.getRequest();
/*  395 */         boolean hasEmptyBody = (request.getBodyBlockCount() == 0);
/*  396 */         if (hasEmptyBody) {
/*  397 */           if (operationWithEmptyBody != null) {
/*  398 */             fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */           }
/*      */           else {
/*      */             
/*  402 */             operationWithEmptyBody = operation;
/*      */           } 
/*      */         }
/*  405 */         if (request.getBodyBlockCount() > 1)
/*      */         {
/*  407 */           fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  412 */         if (!hasEmptyBody) {
/*  413 */           Block bodyBlock = request.getBodyBlocks().next();
/*  414 */           if (j++ > 0)
/*  415 */             p.p("else "); 
/*  416 */           p.plnI("if (bodyReader.getName().equals(" + this.env.getNames().getBlockQNameName(operation, bodyBlock) + ")) {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  423 */           if (operation.isOverloaded()) {
/*  424 */             p.pln("throw new SOAPProtocolViolationException(\"soap.operation.cannot.dispatch\", \"" + operation.getName().getLocalPart() + "\");");
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  429 */             p.pln("state.getRequest().setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  435 */           p.pOln("}");
/*      */         }  continue;
/*  437 */       }  if (useSoapActionDispatching) {
/*      */         
/*  439 */         if (j++ > 0)
/*  440 */           p.p("else "); 
/*  441 */         p.plnI("if (soapaction.equals(\"\\\"" + operation.getSOAPAction() + "\\\"\")) {");
/*      */ 
/*      */ 
/*      */         
/*  445 */         p.pln("state.getRequest().setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */ 
/*      */ 
/*      */         
/*  449 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*  452 */     if (j > 0)
/*  453 */       p.plnI("else {"); 
/*  454 */     p.pln("throw new SOAPProtocolViolationException(\"soap.operation.unrecognized\", bodyReader.getName().toString());");
/*      */     
/*  456 */     if (j > 0)
/*  457 */       p.pOln("}"); 
/*  458 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeUsesSOAPActionForDispatching(IndentingWriter p) throws IOException {
/*  463 */     boolean useOperationNameDispatching = this.hasUniqueOperationNames;
/*  464 */     boolean useSoapActionDispatching = (!useOperationNameDispatching && this.hasUniqueSoapActions);
/*      */ 
/*      */     
/*  467 */     if (useSoapActionDispatching) {
/*  468 */       p.plnI("public boolean usesSOAPActionForDispatching() {");
/*  469 */       p.pln("return true;");
/*  470 */       p.pOln("}");
/*  471 */       p.pln();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetOpcodeForFirstBodyElementName(IndentingWriter p) throws IOException {
/*  477 */     boolean useOperationNameDispatching = this.hasUniqueOperationNames;
/*      */     
/*  479 */     if (useOperationNameDispatching) {
/*  480 */       p.pln("/*");
/*  481 */       p.pln(" * This method must determine the opcode of the operation given the QName of the first body element.");
/*      */       
/*  483 */       p.pln(" */");
/*  484 */       p.plnI("public int getOpcodeForFirstBodyElementName(QName name) {");
/*      */       
/*  486 */       Iterator<Operation> operationsIter = this.operations.iterator();
/*      */ 
/*      */       
/*  489 */       Operation operationWithEmptyBody = null;
/*  490 */       for (int j = 0; operationsIter.hasNext(); j++) {
/*  491 */         Operation operation = operationsIter.next();
/*  492 */         Request request = operation.getRequest();
/*  493 */         boolean hasEmptyBody = (request.getBodyBlockCount() == 0);
/*  494 */         if (hasEmptyBody) {
/*  495 */           if (operationWithEmptyBody != null) {
/*  496 */             fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */           }
/*      */           else {
/*      */             
/*  500 */             operationWithEmptyBody = operation;
/*      */           } 
/*      */         }
/*  503 */         if (request.getBodyBlockCount() > 1)
/*      */         {
/*  505 */           fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  510 */         if (j == 0) {
/*  511 */           p.plnI("if (name == null) {");
/*  512 */           p.pln("return InternalSOAPMessage.NO_OPERATION;");
/*  513 */           p.pOln("}");
/*      */         } 
/*      */         
/*  516 */         if (!hasEmptyBody) {
/*  517 */           Block bodyBlock = request.getBodyBlocks().next();
/*  518 */           p.plnI("if (name.equals(" + this.env.getNames().getBlockQNameName(operation, bodyBlock) + ")) {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  524 */           if (operation.isOverloaded()) {
/*  525 */             p.pln("return InternalSOAPMessage.NO_OPERATION;");
/*      */           } else {
/*  527 */             p.pln("return " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ";");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  533 */           p.pOln("}");
/*      */         } 
/*      */       } 
/*  536 */       p.pln("return super.getOpcodeForFirstBodyElementName(name);");
/*  537 */       p.pOln("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetOpcodeForSOAPAction(IndentingWriter p) throws IOException {
/*  543 */     boolean useOperationNameDispatching = this.hasUniqueOperationNames;
/*  544 */     boolean useSoapActionDispatching = (!useOperationNameDispatching && this.hasUniqueSoapActions);
/*      */ 
/*      */     
/*  547 */     if (useSoapActionDispatching) {
/*  548 */       p.pln("/*");
/*  549 */       p.pln(" * This method must determine the opcode of the operation given the SOAPAction string.");
/*      */       
/*  551 */       p.pln(" */");
/*  552 */       p.plnI("public int getOpcodeForSOAPAction(java.lang.String action) {");
/*  553 */       Iterator<Operation> operationsIter = this.operations.iterator();
/*      */ 
/*      */       
/*  556 */       Operation operationWithEmptyBody = null;
/*  557 */       int j = 0;
/*  558 */       while (operationsIter.hasNext()) {
/*  559 */         Operation operation = operationsIter.next();
/*      */         
/*  561 */         if (j++ == 0) {
/*  562 */           p.plnI("if (action == null) {");
/*  563 */           p.pln("return InternalSOAPMessage.NO_OPERATION;");
/*  564 */           p.pOln("}");
/*      */         } 
/*      */         
/*  567 */         p.plnI("if (action.equals(\"\\\"" + operation.getSOAPAction() + "\\\"\")) {");
/*      */ 
/*      */ 
/*      */         
/*  571 */         p.pln("return " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ";");
/*      */ 
/*      */ 
/*      */         
/*  575 */         p.pOln("}");
/*      */       } 
/*  577 */       p.pln("return super.getOpcodeForSOAPAction(action);");
/*  578 */       p.pOln("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeGetMethodForOpcode(IndentingWriter p) throws IOException, ClassNotFoundException {
/*  584 */     Method theMethods = null;
/*      */     
/*  586 */     p.plnI("private Method internalGetMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {");
/*      */ 
/*      */     
/*  589 */     p.pln();
/*  590 */     p.pln("Method theMethod = null;");
/*  591 */     p.pln();
/*  592 */     p.plnI("switch(opcode) {");
/*      */     
/*  594 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*  595 */     String str = "";
/*  596 */     String ops = "";
/*  597 */     int j = 0;
/*      */ 
/*      */     
/*  600 */     Operation operationWithEmptyBody = null;
/*  601 */     for (j = 0; operationsIter.hasNext(); j++) {
/*  602 */       Operation operation = operationsIter.next();
/*  603 */       Request request = operation.getRequest();
/*      */       
/*  605 */       boolean hasEmptyBody = (request.getBodyBlockCount() == 0);
/*  606 */       if (hasEmptyBody) {
/*  607 */         if (operationWithEmptyBody != null) {
/*  608 */           fail("generator.tie.cannot.dispatch", operation.getName().getLocalPart());
/*      */         }
/*      */         else {
/*      */           
/*  612 */           operationWithEmptyBody = operation;
/*      */         } 
/*      */       }
/*      */       
/*  616 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/*  620 */       JavaMethod jvmmethod = operation.getJavaMethod();
/*  621 */       Iterator<JavaParameter> ite = jvmmethod.getParameters();
/*  622 */       int i = 0;
/*  623 */       String data = "";
/*      */       
/*  625 */       p.plnI("{");
/*  626 */       p.p("Class[] carray = { ");
/*  627 */       while (ite.hasNext()) {
/*  628 */         JavaParameter astr = ite.next();
/*  629 */         str = astr.getType().getName();
/*  630 */         data = "";
/*      */ 
/*      */ 
/*      */         
/*  634 */         if (i != 0) {
/*  635 */           p.p(",");
/*      */         }
/*      */         
/*      */         try {
/*  639 */           if (astr.isHolder()) {
/*  640 */             if (astr.getHolderName() == null) {
/*  641 */               p.p(this.env.getNames().holderClassName(this.port, astr.getType()) + ".class");
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/*  647 */               p.p(astr.getHolderName() + ".class");
/*      */             } 
/*  649 */           } else if (str.indexOf("[") > 0) {
/*  650 */             int end = str.lastIndexOf("]");
/*  651 */             int range = (end - str.indexOf("[")) / 2;
/*      */ 
/*      */ 
/*      */             
/*  655 */             if (GeneratorUtil.ht.containsKey(str)) {
/*  656 */               for (int counter = 0; counter <= range; ) {
/*  657 */                 data = data + "[";
/*  658 */                 counter++;
/*      */               } 
/*  660 */               data = data + (String)GeneratorUtil.ht.get(str);
/*  661 */               p.p("Class.forName(\"" + data + "\")");
/*      */             } else {
/*      */               
/*  664 */               for (int counter = 0; counter <= range; ) {
/*  665 */                 data = data + "[";
/*  666 */                 counter++;
/*      */               } 
/*  668 */               data = data + "L" + str.substring(0, str.indexOf("[")) + ";";
/*      */ 
/*      */               
/*  671 */               p.p("Class.forName(\"" + data + "\")");
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  676 */           else if (GeneratorUtil.ht.containsKey(str)) {
/*      */ 
/*      */             
/*  679 */             p.p(GeneratorUtil.ht.get(str));
/*      */           } else {
/*      */             
/*  682 */             p.p(str + ".class");
/*      */           }
/*      */         
/*  685 */         } catch (Exception e) {
/*  686 */           e.printStackTrace();
/*      */         } 
/*  688 */         i++;
/*      */       } 
/*  690 */       JavaInterface intf = this.port.getJavaInterface();
/*  691 */       p.pln(" };");
/*  692 */       p.pln("theMethod = (" + intf.getName() + ".class).getMethod(\"" + jvmmethod.getName() + "\", carray);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  698 */       p.pOln("}");
/*  699 */       p.pln("break;");
/*  700 */       p.pOln("");
/*      */     } 
/*  702 */     p.pln("default:");
/*  703 */     p.pOln("}");
/*  704 */     p.pln("return theMethod;");
/*  705 */     p.pOln("}");
/*      */ 
/*      */     
/*  708 */     p.pln();
/*  709 */     p.pln("private Method[] methodMap = new Method[" + j + "];");
/*  710 */     p.pln();
/*      */     
/*  712 */     p.pln("/*");
/*  713 */     p.pln(" * This method returns the Method Obj for a specified opcode.");
/*  714 */     p.pln(" */");
/*  715 */     p.plnI("public Method getMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {");
/*      */     
/*  717 */     p.pln(" ");
/*      */     
/*  719 */     p.plnI("if (opcode <= InternalSOAPMessage.NO_OPERATION ) {");
/*  720 */     p.pln("return null;");
/*  721 */     p.pOln("}");
/*  722 */     p.pln(" ");
/*      */     
/*  724 */     p.plnI("if (opcode >= " + j + " ) {");
/*  725 */     p.pln("return null;");
/*  726 */     p.pOln("}");
/*  727 */     p.pln(" ");
/*      */     
/*  729 */     p.plnI("if (methodMap[opcode] == null)  {");
/*  730 */     p.pln("methodMap[opcode] = internalGetMethodForOpcode(opcode);");
/*  731 */     p.pOln("}");
/*  732 */     p.pln(" ");
/*      */     
/*  734 */     p.pln("return methodMap[opcode];");
/*  735 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeHandleEmptyBody(IndentingWriter p, Operation operation) throws IOException {
/*  740 */     p.pln("/*");
/*  741 */     p.pln(" * This method handles the case of an empty SOAP body.");
/*  742 */     p.pln(" */");
/*  743 */     p.plnI("protected void handleEmptyBody(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {");
/*      */     
/*  745 */     p.pln("state.getRequest().setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */ 
/*      */ 
/*      */     
/*  749 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeProcessingHookMethod(IndentingWriter p) throws IOException {
/*  755 */     p.pln("/*");
/*  756 */     p.pln(" * This method must invoke the correct method on the servant based on the opcode.");
/*      */     
/*  758 */     p.pln(" */");
/*  759 */     p.plnI("protected void processingHook(StreamingHandlerState state) throws Exception {");
/*      */     
/*  761 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */     
/*  763 */     p.plnI("switch (state.getRequest().getOperationCode()) {");
/*  764 */     while (operationsIter.hasNext()) {
/*  765 */       Operation operation = operationsIter.next();
/*  766 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/*  770 */       p.pln("invoke_" + this.env.getNames().validInternalJavaIdentifier(operation.getUniqueName()) + "(state);");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  775 */       p.pln("break;");
/*  776 */       p.pO();
/*      */     } 
/*  778 */     p.plnI("default:");
/*  779 */     p.pln("throw new SOAPProtocolViolationException(\"soap.operation.unrecognized\", java.lang.Integer.toString(state.getRequest().getOperationCode()));");
/*      */     
/*  781 */     p.pO();
/*  782 */     p.pOln("}");
/*  783 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   protected String getFaultCodeServer() {
/*  787 */     if (this.port.getSOAPVersion().equals(SOAPVersion.SOAP_12.toString())) {
/*  788 */       return "com.sun.xml.rpc.encoding.soap.SOAP12Constants.FAULT_CODE_SERVER";
/*      */     }
/*  790 */     return "com.sun.xml.rpc.encoding.soap.SOAPConstants.FAULT_CODE_SERVER";
/*      */   }
/*      */   
/*      */   protected String getQNameSOAPFault() {
/*  794 */     if (this.port.getSOAPVersion().equals(SOAPVersion.SOAP_12)) {
/*  795 */       return "com.sun.xml.rpc.encoding.soap.SOAP12Constants.QNAME_SOAP_FAULT";
/*      */     }
/*  797 */     return "com.sun.xml.rpc.encoding.soap.SOAPConstants.QNAME_SOAP_FAULT";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeRpcEncodedOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException {
/*  805 */     String messageName = operation.getName().getLocalPart();
/*  806 */     JavaMethod javaMethod = operation.getJavaMethod();
/*  807 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */ 
/*      */     
/*  811 */     String requestObjType = null;
/*  812 */     String requestObjName = null;
/*  813 */     SOAPType requestBlockType = null;
/*      */ 
/*      */ 
/*      */     
/*  817 */     Request request = operation.getRequest();
/*  818 */     Iterator<Block> iterator = request.getBodyBlocks();
/*  819 */     Block requestBlock = null;
/*  820 */     String requestObjInit = null;
/*  821 */     while (iterator.hasNext()) {
/*  822 */       requestBlock = iterator.next();
/*  823 */       if (requestBlock.getName().getLocalPart().equals(messageName)) {
/*  824 */         requestBlockType = (SOAPType)requestBlock.getType();
/*  825 */         requestObjType = requestBlockType.getJavaType().getName();
/*  826 */         requestObjInit = requestBlockType.getJavaType().getInitString();
/*  827 */         requestObjName = this.env.getNames().getTypeMemberName(requestBlockType.getJavaType());
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */     
/*  834 */     writeInvokeMethodDecl(p, operation);
/*      */ 
/*      */     
/*  837 */     if (operation.getResponse() == null) {
/*  838 */       p.pln("flushHttpResponse(state);");
/*      */     }
/*      */ 
/*      */     
/*  842 */     declareRequestObjects(p, requestObjType, requestObjName, requestObjInit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  848 */     declareHolderHeaderObjects(p, requestBlock, operation);
/*      */     
/*  850 */     boolean hasRequestHeaders = false;
/*  851 */     iterator = operation.getRequest().getHeaderBlocks();
/*  852 */     hasRequestHeaders = iterator.hasNext();
/*  853 */     if (hasRequestHeaders) {
/*  854 */       writeRequestHeaders(p, operation);
/*      */     }
/*  856 */     p.plnI("try {");
/*      */     
/*  858 */     declareRpcReturnType(p, operation, resultType);
/*      */ 
/*      */ 
/*      */     
/*  862 */     if (javaMethod.getDeclaringClass() != null) {
/*  863 */       p.p("((" + javaMethod.getDeclaringClass().replace('$', '.') + ") getTarget())." + javaMethod.getName() + "(");
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  870 */       p.p("((" + remoteClassName + ") getTarget())." + javaMethod.getName() + "(");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     if (resultType != null && !resultType.getName().equals("void"))
/*      */     {
/*  879 */       p.pO();
/*      */     }
/*  881 */     iterator = javaMethod.getParameters();
/*  882 */     request = operation.getRequest();
/*      */     
/*  884 */     for (int i = 0; iterator.hasNext(); i++) {
/*  885 */       if (i > 0)
/*  886 */         p.p(", "); 
/*  887 */       JavaParameter javaParameter = (JavaParameter)iterator.next();
/*  888 */       Parameter parameter = javaParameter.getParameter();
/*  889 */       Block paramBlock = parameter.getBlock();
/*  890 */       if (paramBlock.getLocation() != 1) {
/*  891 */         if (javaParameter.isHolder()) {
/*  892 */           p.p(javaParameter.getParameter().getName() + "_holder");
/*      */         } else {
/*  894 */           p.p(parameter.getName());
/*      */         } 
/*      */       } else {
/*  897 */         JavaType javaObjType = paramBlock.getType().getJavaType();
/*  898 */         String javaObjName = this.env.getNames().getTypeMemberName(javaObjType);
/*      */         
/*  900 */         JavaStructureMember javaMember = getJavaMember(parameter);
/*  901 */         if (javaParameter.isHolder()) {
/*  902 */           p.p(javaParameter.getParameter().getName() + "_holder");
/*      */         }
/*  904 */         else if (javaMember.isPublic()) {
/*  905 */           p.p(javaObjName + "." + parameter.getName());
/*      */         } else {
/*  907 */           p.p(javaObjName + "." + javaMember.getReadMethod() + "()");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  915 */     p.pln(");");
/*      */     
/*  917 */     if (operation.getResponse() != null)
/*      */     {
/*      */       
/*  920 */       declareRpcResponseObject(p, operation);
/*      */     }
/*      */     
/*  923 */     writeCatchClauses(p, operation);
/*      */     
/*  925 */     p.pOln("}");
/*  926 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeCatchClauses(IndentingWriter p, Operation operation) throws IOException {
/*  931 */     Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*  932 */     faultSet.addAll(operation.getFaultsSet());
/*  933 */     Iterator<Fault> faults = faultSet.iterator();
/*      */     
/*  935 */     while (faults.hasNext()) {
/*  936 */       Fault fault = faults.next();
/*  937 */       p.pOlnI("} catch (" + this.env.getNames().customExceptionClassName(fault) + " e) {");
/*      */ 
/*      */ 
/*      */       
/*  941 */       p.plnI("SOAPFaultInfo fault = new SOAPFaultInfo(" + getFaultCodeServer() + ",");
/*      */ 
/*      */ 
/*      */       
/*  945 */       p.pln("\"" + this.env.getNames().customExceptionClassName(fault) + "\", null, e);");
/*      */ 
/*      */ 
/*      */       
/*  949 */       p.pO();
/*  950 */       p.pln("SOAPBlockInfo faultBlock = new SOAPBlockInfo(" + getQNameSOAPFault() + ");");
/*      */ 
/*      */ 
/*      */       
/*  954 */       p.pln("faultBlock.setValue(fault);");
/*  955 */       p.pln("faultBlock.setSerializer(" + this.env.getNames().getClassMemberName(this.env.getNames().faultSerializerClassName(this.servicePackage, this.port, operation)) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  963 */       p.pln("state.getResponse().setBody(faultBlock);");
/*  964 */       p.pln("state.getResponse().setFailure(true);");
/*      */       
/*  966 */       if (fault instanceof com.sun.xml.rpc.processor.model.HeaderFault) {
/*  967 */         p.pln("SOAPHeaderBlockInfo headerInfo;");
/*  968 */         p.pln("headerInfo = new SOAPHeaderBlockInfo(" + this.env.getNames().getQNameName(fault.getBlock().getName()) + ", null, false);");
/*      */ 
/*      */ 
/*      */         
/*  972 */         p.pln("headerInfo.setValue(e);");
/*  973 */         p.pln("headerInfo.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, fault.getBlock().getType()).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  981 */         p.pln("state.getResponse().add(headerInfo);");
/*      */       } 
/*      */     } 
/*  984 */     p.pOlnI("} catch (javax.xml.rpc.soap.SOAPFaultException e) {");
/*  985 */     p.plnI("SOAPFaultInfo fault = new SOAPFaultInfo(e.getFaultCode(),");
/*  986 */     p.pln("e.getFaultString(), e.getFaultActor(), e.getDetail());");
/*  987 */     p.pO();
/*  988 */     p.pln("SOAPBlockInfo faultBlock = new SOAPBlockInfo(" + getQNameSOAPFault() + ");");
/*      */ 
/*      */ 
/*      */     
/*  992 */     p.pln("faultBlock.setValue(fault);");
/*  993 */     p.pln("faultBlock.setSerializer(new SOAPFaultInfoSerializer(false, e.getDetail()==null));");
/*      */     
/*  995 */     p.pln("state.getResponse().setBody(faultBlock);");
/*  996 */     p.pln("state.getResponse().setFailure(true);");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInvokeMethodDecl(IndentingWriter p, Operation operation) throws IOException {
/* 1001 */     String messageName = operation.getName().getLocalPart();
/*      */     
/* 1003 */     p.pln("/*");
/* 1004 */     p.pln(" * This method does the actual method invocation for operation: " + messageName);
/*      */ 
/*      */     
/* 1007 */     p.pln(" */");
/* 1008 */     p.plnI("private void invoke_" + this.env.getNames().validInternalJavaIdentifier(operation.getUniqueName()) + "(StreamingHandlerState state) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareRequestObjects(IndentingWriter p, String requestObjType, String requestObjName, String requestObjInit) throws IOException {
/* 1022 */     if (requestObjType != null && requestObjName != null) {
/* 1023 */       String requestObjMemberName = requestObjName + "Obj";
/*      */ 
/*      */       
/* 1026 */       p.pln(requestObjType + " " + requestObjName + " = " + requestObjInit + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1035 */       p.plnI("Object " + requestObjMemberName + " =");
/* 1036 */       p.pln("state.getRequest().getBody().getValue();");
/* 1037 */       p.pO();
/* 1038 */       p.pln();
/* 1039 */       p.plnI("if (" + requestObjMemberName + " instanceof SOAPDeserializationState) {");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1044 */       String valueStr = "((SOAPDeserializationState)" + requestObjMemberName + ").getInstance()";
/*      */ 
/*      */ 
/*      */       
/* 1048 */       if (SimpleToBoxedUtil.isPrimitive(requestObjType)) {
/* 1049 */         String boxName = SimpleToBoxedUtil.getBoxedClassName(requestObjType);
/*      */         
/* 1051 */         valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + valueStr, requestObjType);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1056 */         valueStr = "(" + requestObjType + ")" + valueStr;
/*      */       } 
/* 1058 */       p.pln(requestObjName + " = " + valueStr + ";");
/* 1059 */       p.pOlnI("} else {");
/* 1060 */       valueStr = requestObjMemberName;
/* 1061 */       if (SimpleToBoxedUtil.isPrimitive(requestObjType)) {
/* 1062 */         String boxName = SimpleToBoxedUtil.getBoxedClassName(requestObjType);
/*      */         
/* 1064 */         valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + valueStr, requestObjType);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1069 */         valueStr = "(" + requestObjType + ")" + valueStr;
/*      */       } 
/* 1071 */       p.pln(requestObjName + " = " + valueStr + ";");
/* 1072 */       p.pOln("}");
/* 1073 */       p.pln();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareHolderHeaderObjects(IndentingWriter p, Block requestBlock, Operation operation) throws IOException {
/* 1082 */     if (requestBlock != null) {
/* 1083 */       AbstractType requestBlockType = requestBlock.getType();
/* 1084 */       String requestObjType = requestBlockType.getJavaType().getName();
/* 1085 */       String requestObjName = this.env.getNames().getTypeMemberName(requestBlockType.getJavaType());
/*      */ 
/*      */       
/* 1088 */       String requestObjMemberName = requestObjName + "Obj";
/* 1089 */       JavaMethod javaMethod = operation.getJavaMethod();
/*      */       
/* 1091 */       Iterator<JavaParameter> iterator = javaMethod.getParameters();
/*      */ 
/*      */       
/* 1094 */       boolean declaredHeaderObj = false;
/* 1095 */       boolean declaredAttachmentObj = false;
/* 1096 */       for (int i = 0; iterator.hasNext(); ) {
/* 1097 */         JavaParameter javaParameter = iterator.next();
/* 1098 */         if (javaParameter.isHolder()) {
/*      */           String holderClassName;
/* 1100 */           if (javaParameter.getHolderName() != null) {
/* 1101 */             holderClassName = javaParameter.getHolderName();
/*      */           } else {
/* 1103 */             holderClassName = this.env.getNames().holderClassName(this.port, javaParameter.getType());
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1108 */           p.plnI(holderClassName + " " + javaParameter.getParameter().getName() + "_holder =");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1113 */           p.pln("new " + holderClassName + "();");
/* 1114 */           p.pO();
/*      */           
/* 1116 */           if (javaParameter.getParameter().getLinkedParameter() != null && javaParameter.getParameter().getBlock().getLocation() != 2) {
/*      */ 
/*      */ 
/*      */             
/* 1120 */             JavaStructureMember javaMember = getJavaMember(javaParameter.getParameter());
/*      */ 
/*      */             
/* 1123 */             if (javaMember != null && javaMember.getType() instanceof JavaStructureType && ((JavaStructureType)javaMember.getType()).getOwner() instanceof LiteralArrayWrapperType) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1128 */               LiteralArrayWrapperType owner = (LiteralArrayWrapperType)((JavaStructureType)javaMember.getType()).getOwner();
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1133 */               JavaStructureMember tmpMember = ((JavaStructureType)owner.getJavaType()).getMembers().next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1139 */               p.plnI(javaParameter.getParameter().getName() + "_holder.value = ");
/*      */ 
/*      */               
/* 1142 */               p.pln("(" + requestObjName + "." + javaMember.getReadMethod() + "() != null) ?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1148 */               p.pln(requestObjName + "." + javaMember.getReadMethod() + "()." + tmpMember.getReadMethod() + "() : null;");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1155 */               p.pO(); continue;
/* 1156 */             }  if (javaParameter.getParameter().getBlock().getLocation() != 3)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1162 */               p.pln(javaParameter.getParameter().getName() + "_holder.value = " + requestObjName + "." + javaMember.getReadMethod() + "();");
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */ 
/*      */           
/* 1171 */           if (javaParameter.getParameter().getBlock().getLocation() == 2 && 
/* 1172 */             !declaredHeaderObj) {
/* 1173 */             p.pln("Object _headerObj;");
/* 1174 */             declaredHeaderObj = true;
/*      */           }  continue;
/*      */         } 
/* 1177 */         if (javaParameter.getParameter().getBlock().getLocation() == 2)
/*      */         {
/*      */ 
/*      */           
/* 1181 */           if (!declaredHeaderObj) {
/* 1182 */             p.pln("Object _headerObj;");
/* 1183 */             declaredHeaderObj = true;
/*      */           } 
/* 1185 */           AbstractType paramType = javaParameter.getParameter().getType();
/*      */           
/* 1187 */           String initValue = javaParameter.getType().getInitString();
/* 1188 */           p.pln(paramType.getJavaType().getName() + " " + javaParameter.getParameter().getName() + " = " + initValue + ";");
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1198 */       JavaMethod javaMethod = operation.getJavaMethod();
/*      */       
/* 1200 */       Iterator<JavaParameter> iterator = javaMethod.getParameters();
/* 1201 */       while (iterator.hasNext()) {
/* 1202 */         JavaParameter javaParameter = iterator.next();
/* 1203 */         if (javaParameter.isHolder()) {
/*      */           String holderClassName;
/* 1205 */           if (javaParameter.getHolderName() != null) {
/* 1206 */             holderClassName = javaParameter.getHolderName();
/*      */           } else {
/* 1208 */             holderClassName = this.env.getNames().holderClassName(this.port, javaParameter.getType());
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1213 */           p.plnI(holderClassName + " " + javaParameter.getParameter().getName() + "_holder =");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1218 */           p.pln("new " + holderClassName + "();");
/* 1219 */           p.pO();
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
/*      */   private boolean declareRpcReturnType(IndentingWriter p, Operation operation, JavaType resultType) throws IOException {
/* 1231 */     Response response = operation.getResponse();
/* 1232 */     if (response == null) {
/* 1233 */       return false;
/*      */     }
/* 1235 */     Iterator<Parameter> iterator = response.getBodyBlocks();
/*      */     
/* 1237 */     if (resultType != null && !resultType.getName().equals("void")) {
/*      */       
/* 1239 */       iterator = response.getParameters();
/* 1240 */       if (iterator.hasNext()) {
/* 1241 */         String resultName; Parameter parameter = iterator.next();
/*      */ 
/*      */         
/* 1244 */         if (resultType instanceof JavaStructureType && ((JavaStructureType)resultType).getOwner() instanceof LiteralArrayWrapperType) {
/*      */ 
/*      */           
/* 1247 */           resultName = ((LiteralArrayWrapperType)((JavaStructureType)resultType).getOwner()).getJavaArrayType().getName();
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 1255 */           resultName = resultType.getName();
/*      */         } 
/*      */         
/* 1258 */         p.plnI(resultName + " " + parameter.getName() + " = ");
/* 1259 */         return true;
/*      */       } 
/*      */     } 
/* 1262 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeRequestHeaders(IndentingWriter p, Operation operation) throws IOException {
/* 1268 */     p.pln("Iterator headers = state.getRequest().headers();");
/* 1269 */     p.pln("SOAPHeaderBlockInfo curHeader;");
/* 1270 */     p.plnI("while (headers.hasNext()) {");
/* 1271 */     p.pln("curHeader = (SOAPHeaderBlockInfo)headers.next();");
/* 1272 */     Iterator<Parameter> iterator = operation.getRequest().getParameters();
/* 1273 */     boolean startedHeaders = false;
/*      */     
/* 1275 */     while (iterator.hasNext()) {
/* 1276 */       Parameter parameter = iterator.next();
/* 1277 */       if (parameter.getBlock().getLocation() == 2) {
/* 1278 */         if (startedHeaders) {
/* 1279 */           p.p(" else ");
/*      */         }
/* 1281 */         startedHeaders = true;
/* 1282 */         String paramName = parameter.getName();
/* 1283 */         String paramType = parameter.getType().getJavaType().getName();
/* 1284 */         String varName = null;
/* 1285 */         if (parameter.getJavaParameter() != null && parameter.getJavaParameter().isHolder()) {
/* 1286 */           varName = paramName + "_holder.value";
/*      */         } else {
/* 1288 */           varName = paramName;
/*      */         } 
/* 1290 */         String qname = this.env.getNames().getBlockQNameName(null, parameter.getBlock());
/*      */ 
/*      */ 
/*      */         
/* 1294 */         p.plnI("if (curHeader.getName().equals(" + qname + ")) {");
/* 1295 */         p.pln("_headerObj = (" + paramType + ")curHeader.getValue();");
/* 1296 */         p.plnI("if (_headerObj instanceof SOAPDeserializationState) {");
/* 1297 */         p.pln(varName + " = (" + paramType + ")((SOAPDeserializationState)" + "_headerObj).getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1303 */         p.pOlnI("} else {");
/* 1304 */         p.pln(varName + " = (" + paramType + ")_headerObj;");
/* 1305 */         p.pOln("}");
/* 1306 */         p.pO("}");
/*      */       } 
/*      */     } 
/* 1309 */     if (startedHeaders)
/* 1310 */       p.pln(); 
/* 1311 */     p.pOln("}");
/* 1312 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareRpcResponseObject(IndentingWriter p, Operation operation) throws IOException {
/* 1319 */     String messageName = operation.getName().getLocalPart();
/*      */     
/* 1321 */     Response response = operation.getResponse();
/* 1322 */     if (response == null) {
/*      */       return;
/*      */     }
/* 1325 */     Iterator<Block> iterator = response.getBodyBlocks();
/* 1326 */     Block responseBlock = null;
/* 1327 */     SOAPType responseBlockType = null;
/* 1328 */     String responseObjType = null;
/* 1329 */     String responseObjName = null;
/*      */ 
/*      */ 
/*      */     
/* 1333 */     while (iterator.hasNext()) {
/* 1334 */       responseBlock = iterator.next();
/* 1335 */       if (responseBlock.getName().getLocalPart().equals(messageName + "Response")) {
/*      */ 
/*      */ 
/*      */         
/* 1339 */         responseBlockType = (SOAPType)responseBlock.getType();
/* 1340 */         responseObjType = responseBlockType.getJavaType().getName();
/* 1341 */         responseObjName = this.env.getNames().getTypeMemberName(responseBlockType.getJavaType());
/*      */         
/*      */         break;
/*      */       } 
/*      */       
/* 1346 */       responseBlock = null;
/*      */     } 
/* 1348 */     p.plnI(responseObjType + " " + responseObjName + " =");
/* 1349 */     p.pln("new " + responseObjType + "();");
/* 1350 */     p.pO();
/*      */     
/* 1352 */     response = operation.getResponse();
/* 1353 */     Iterator<Parameter> iterator2 = response.getParameters();
/*      */ 
/*      */     
/* 1356 */     p.pln("SOAPHeaderBlockInfo headerInfo;");
/* 1357 */     for (int i = 0; iterator2.hasNext(); i++) {
/* 1358 */       Parameter parameter = iterator2.next();
/* 1359 */       Block block = parameter.getBlock();
/* 1360 */       if (block.getLocation() == 1) {
/* 1361 */         String memberName; JavaStructureMember javaMember = getJavaMember(parameter);
/* 1362 */         JavaParameter javaParameter = parameter.getJavaParameter();
/* 1363 */         if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */           
/* 1365 */           memberName = parameter.getName() + "_holder.value";
/*      */         } else {
/* 1367 */           memberName = parameter.getName();
/*      */         } 
/* 1369 */         if (javaMember != null) {
/* 1370 */           if (javaMember.isPublic()) {
/* 1371 */             p.pln(responseObjName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/* 1379 */             p.pln(responseObjName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
/*      */           } 
/*      */         }
/*      */       } else {
/*      */         String memberName;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1388 */         JavaParameter javaParameter = parameter.getJavaParameter();
/* 1389 */         String qname = this.env.getNames().getBlockQNameName(null, block);
/* 1390 */         if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */           
/* 1392 */           memberName = parameter.getName() + "_holder.value";
/*      */         } else {
/* 1394 */           memberName = parameter.getName();
/*      */         } 
/* 1396 */         p.pln("headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */         
/* 1400 */         p.pln("headerInfo.setValue(" + memberName + ");");
/* 1401 */         p.pln("headerInfo.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, block.getType()).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1409 */         p.pln("state.getResponse().add(headerInfo);");
/*      */       } 
/*      */     } 
/* 1412 */     p.pln();
/* 1413 */     p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, responseBlock) + ");");
/*      */ 
/*      */ 
/*      */     
/* 1417 */     p.pln("bodyBlock.setValue(" + responseObjName + ");");
/* 1418 */     p.pln("bodyBlock.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, (AbstractType)responseBlockType).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1424 */     p.pln("state.getResponse().setBody(bodyBlock);");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeRpcLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException {
/* 1433 */     String messageName = operation.getName().getLocalPart();
/* 1434 */     JavaMethod javaMethod = operation.getJavaMethod();
/* 1435 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1440 */     String requestObjType = null;
/* 1441 */     String requestObjName = null;
/* 1442 */     String responseObjType = null;
/* 1443 */     String responseObjName = null;
/* 1444 */     LiteralType responseBlockType = null;
/* 1445 */     LiteralType requestBlockType = null;
/*      */     
/* 1447 */     Block responseBlock = null;
/*      */ 
/*      */     
/* 1450 */     Request request = operation.getRequest();
/* 1451 */     int headerParameterCount = 0;
/* 1452 */     int attachmentParameterCount = 0; Iterator<Parameter> iterator;
/* 1453 */     for (iterator = request.getParameters(); iterator.hasNext(); ) {
/* 1454 */       Parameter parameter = iterator.next();
/* 1455 */       Block paramBlock = parameter.getBlock();
/* 1456 */       if (paramBlock.getLocation() == 1) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1463 */       if (paramBlock.getLocation() == 2) {
/*      */         
/* 1465 */         headerParameterCount++; continue;
/* 1466 */       }  if (paramBlock.getLocation() == 3)
/*      */       {
/* 1468 */         attachmentParameterCount++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1473 */     iterator = request.getBodyBlocks();
/* 1474 */     Block requestBlock = null;
/* 1475 */     String requestObjInit = null;
/* 1476 */     if (iterator.hasNext()) {
/* 1477 */       requestBlock = (Block)iterator.next();
/* 1478 */       requestBlockType = (LiteralType)requestBlock.getType();
/* 1479 */       requestObjType = requestBlockType.getJavaType().getName();
/* 1480 */       requestObjInit = requestBlockType.getJavaType().getInitString();
/* 1481 */       requestObjName = this.env.getNames().getTypeMemberName(requestBlockType.getJavaType());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1486 */     writeInvokeMethodDecl(p, operation);
/*      */ 
/*      */     
/* 1489 */     if (operation.getResponse() == null) {
/* 1490 */       p.pln("flushHttpResponse(state);");
/*      */     }
/*      */ 
/*      */     
/* 1494 */     declareRequestObjects(p, requestObjType, requestObjName, requestObjInit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1500 */     declareHolderHeaderObjects(p, requestBlock, operation);
/*      */     
/* 1502 */     boolean hasRequestHeaders = false;
/* 1503 */     iterator = operation.getRequest().getHeaderBlocks();
/* 1504 */     hasRequestHeaders = iterator.hasNext();
/* 1505 */     if (hasRequestHeaders) {
/* 1506 */       writeRequestHeaders(p, operation);
/*      */     }
/*      */     
/* 1509 */     declareHolderAttachmentObjects(p, requestBlock, operation);
/*      */     
/* 1511 */     p.plnI("try {");
/*      */     
/* 1513 */     Response response = operation.getResponse();
/* 1514 */     boolean resultIsEmbedded = false;
/* 1515 */     if (response != null) {
/* 1516 */       iterator = response.getBodyBlocks();
/* 1517 */       if (iterator.hasNext()) {
/* 1518 */         responseBlock = (Block)iterator.next();
/* 1519 */         responseBlockType = (LiteralType)responseBlock.getType();
/* 1520 */         responseObjType = responseBlockType.getJavaType().getName();
/* 1521 */         responseObjName = "_response";
/*      */       } 
/*      */       
/* 1524 */       resultIsEmbedded = false;
/* 1525 */       if (resultType != null && !resultType.getName().equals("void")) {
/*      */         
/* 1527 */         iterator = response.getParameters();
/* 1528 */         if (iterator.hasNext()) {
/* 1529 */           Parameter parameter = iterator.next();
/* 1530 */           p.p(responseObjType + " " + responseObjName + " = new ");
/* 1531 */           p.pln(responseObjType + "();");
/*      */         } 
/* 1533 */       } else if ((resultType == null || resultType.getName().equals("void")) && responseBlock != null) {
/*      */ 
/*      */ 
/*      */         
/* 1537 */         resultIsEmbedded = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1544 */     boolean declaredResult = declareRpcReturnType(p, operation, resultType);
/* 1545 */     p.p("((" + remoteClassName + ") getTarget())." + operation.getJavaMethod().getName() + "(");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1552 */     boolean hasBodyParams = false;
/* 1553 */     int count = 0;
/*      */ 
/*      */ 
/*      */     
/* 1557 */     iterator = javaMethod.getParameters();
/* 1558 */     for (; iterator.hasNext(); 
/* 1559 */       count++) {
/* 1560 */       JavaParameter javaParameter = (JavaParameter)iterator.next();
/* 1561 */       Parameter parameter = javaParameter.getParameter();
/* 1562 */       if (parameter.getBlock().getLocation() == 1) {
/* 1563 */         hasBodyParams = true;
/* 1564 */         JavaStructureMember javaMember = getJavaMember(parameter);
/* 1565 */         if (count > 0)
/* 1566 */           p.p(", "); 
/* 1567 */         String unWrapMethod = "";
/*      */         
/* 1569 */         if (parameter.getType() instanceof LiteralArrayWrapperType) {
/* 1570 */           JavaStructureMember tmpJMember = ((JavaStructureType)((LiteralArrayWrapperType)parameter.getType()).getJavaType()).getMembers().next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1579 */           unWrapMethod = ".toArray()";
/*      */         } 
/* 1581 */         if (parameter.getJavaParameter().isHolder()) {
/* 1582 */           p.p(parameter.getName() + "_holder");
/*      */         }
/* 1584 */         else if (javaMember.isPublic()) {
/* 1585 */           p.p(requestObjName + "." + parameter.getName() + unWrapMethod);
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1591 */           p.p(requestObjName + "." + javaMember.getReadMethod() + "()" + unWrapMethod);
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1598 */       else if (parameter.getBlock().getLocation() == 3) {
/* 1599 */         if (count > 0)
/* 1600 */           p.p(", "); 
/* 1601 */         javaParameter = parameter.getJavaParameter();
/* 1602 */         if (javaParameter.isHolder()) {
/* 1603 */           p.p(parameter.getName() + "_holder");
/*      */         } else {
/* 1605 */           p.p(parameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1650 */     count = 0;
/*      */ 
/*      */     
/* 1653 */     if (headerParameterCount > 0) {
/* 1654 */       iterator = request.getParameters();
/* 1655 */       for (; iterator.hasNext(); 
/* 1656 */         count++) {
/* 1657 */         Parameter parameter = iterator.next();
/* 1658 */         if (!parameter.getJavaParameter().isHolder() && parameter.getBlock().getLocation() == 2) {
/*      */           
/* 1660 */           if (hasBodyParams || count > 0)
/* 1661 */             p.p(", "); 
/* 1662 */           p.p(parameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1667 */     if (operation.getResponse() != null) {
/*      */       
/* 1669 */       iterator = operation.getResponse().getParameters();
/* 1670 */       while (iterator.hasNext()) {
/*      */         
/* 1672 */         Parameter parameter = iterator.next();
/* 1673 */         if (parameter.getBlock().getLocation() == 2) {
/* 1674 */           p.p(", ");
/* 1675 */           JavaParameter javaParameter = parameter.getJavaParameter();
/* 1676 */           if (javaParameter.isHolder()) {
/* 1677 */             p.p(parameter.getName() + "_holder"); continue;
/*      */           } 
/* 1679 */           p.p(parameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1685 */     p.pln(");");
/*      */     
/* 1687 */     if (declaredResult)
/* 1688 */       p.pO(); 
/* 1689 */     p.pln();
/*      */     
/* 1691 */     if (operation.getResponse() != null) {
/* 1692 */       p.pln("SOAPHeaderBlockInfo headerInfo;");
/* 1693 */       if (resultIsEmbedded) {
/* 1694 */         p.pln(responseObjType + " " + responseObjName + " = new " + responseObjType + "();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1701 */         iterator = response.getParameters();
/* 1702 */         if (iterator.hasNext()) {
/* 1703 */           Parameter parameter = iterator.next();
/* 1704 */           JavaStructureMember javaMember = getJavaMember(parameter);
/* 1705 */           if (javaMember != null && !parameter.getJavaParameter().isHolder())
/*      */           {
/* 1707 */             if (javaMember.isPublic()) {
/* 1708 */               p.pln(responseObjName + "." + parameter.getName() + " = _result;");
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 1714 */               p.pln(responseObjName + "." + javaMember.getWriteMethod() + "(_result);");
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1722 */       iterator = operation.getResponse().getParameters();
/* 1723 */       while (iterator.hasNext()) {
/*      */ 
/*      */         
/* 1726 */         Parameter parameter = iterator.next();
/* 1727 */         responseBlock = parameter.getBlock();
/* 1728 */         if (responseBlock.getLocation() == 1) {
/* 1729 */           String memberName; JavaStructureMember javaMember = getJavaMember(parameter);
/* 1730 */           JavaParameter javaParameter = parameter.getJavaParameter();
/* 1731 */           if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */             
/* 1733 */             memberName = parameter.getName() + "_holder.value";
/*      */           } else {
/* 1735 */             memberName = parameter.getName();
/*      */           } 
/* 1737 */           if (javaMember != null) {
/*      */             
/* 1739 */             if (parameter.getType() instanceof LiteralArrayWrapperType)
/*      */             {
/* 1741 */               memberName = "new " + parameter.getType().getJavaType().getName() + "(" + memberName + ")";
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1748 */             if (javaMember.isPublic()) {
/* 1749 */               p.pln(responseObjName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */ 
/*      */               
/* 1757 */               p.pln(responseObjName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1766 */         if (responseBlock.getLocation() == 2) {
/* 1767 */           String memberName; JavaParameter javaParameter = parameter.getJavaParameter();
/* 1768 */           String qname = this.env.getNames().getBlockQNameName(null, responseBlock);
/*      */           
/* 1770 */           if (javaParameter.isHolder()) {
/* 1771 */             memberName = parameter.getName() + "_holder.value";
/*      */           } else {
/* 1773 */             memberName = parameter.getName();
/*      */           } 
/* 1775 */           p.pln("headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */           
/* 1779 */           p.pln("headerInfo.setValue(" + memberName + ");");
/* 1780 */           p.pln("headerInfo.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, responseBlock.getType()).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1788 */           p.pln("state.getResponse().add(headerInfo);");
/*      */         } 
/*      */       } 
/* 1791 */       p.pln();
/* 1792 */       p.pln();
/*      */       
/* 1794 */       iterator = operation.getResponse().getBodyBlocks();
/* 1795 */       if (iterator.hasNext()) {
/* 1796 */         responseBlock = (Block)iterator.next();
/* 1797 */         if (responseBlock != null && responseBlock.getLocation() == 1) {
/*      */           
/* 1799 */           p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, responseBlock) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1806 */           String valueStr = responseObjName;
/* 1807 */           if (SimpleToBoxedUtil.isPrimitive(responseObjType)) {
/* 1808 */             valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, responseObjType);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1813 */           p.pln("bodyBlock.setValue(" + valueStr + ");");
/*      */           
/* 1815 */           String serializer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)responseBlockType).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1821 */           p.pln("bodyBlock.setSerializer(" + serializer + ");");
/* 1822 */           p.pln("state.getResponse().setBody(bodyBlock);");
/*      */         } else {
/* 1824 */           p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(null);");
/* 1825 */           p.pln("bodyBlock.setSerializer(DummySerializer.getInstance());");
/*      */           
/* 1827 */           p.pln("state.getResponse().setBody(bodyBlock);");
/*      */         } 
/*      */       } 
/* 1830 */       p.pln();
/* 1831 */       addAttachmentsToResponse(p, operation.getResponse().getParameters());
/*      */     } 
/* 1833 */     writeCatchClauses(p, operation);
/* 1834 */     p.pOln("}");
/* 1835 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeDocumentLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException {
/* 1844 */     String messageName = operation.getName().getLocalPart();
/* 1845 */     JavaMethod javaMethod = operation.getJavaMethod();
/* 1846 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1851 */     String requestObjType = null;
/* 1852 */     String requestObjName = null;
/* 1853 */     String responseObjType = null;
/* 1854 */     String responseObjName = null;
/* 1855 */     LiteralType responseBlockType = null;
/* 1856 */     LiteralType requestBlockType = null;
/*      */     
/* 1858 */     Block responseBlock = null;
/*      */ 
/*      */     
/* 1861 */     Request request = operation.getRequest();
/* 1862 */     int embeddedParameterCount = 0;
/* 1863 */     int nonEmbeddedParameterCount = 0;
/* 1864 */     int headerParameterCount = 0;
/* 1865 */     int attachmentParameterCount = 0;
/*      */     Iterator<Parameter> iterator;
/* 1867 */     for (iterator = request.getParameters(); iterator.hasNext(); ) {
/* 1868 */       Parameter parameter = iterator.next();
/* 1869 */       Block paramBlock = parameter.getBlock();
/* 1870 */       if (paramBlock.getLocation() == 1) {
/* 1871 */         if (parameter.isEmbedded()) {
/* 1872 */           embeddedParameterCount++; continue;
/*      */         } 
/* 1874 */         nonEmbeddedParameterCount++; continue;
/*      */       } 
/* 1876 */       if (paramBlock.getLocation() == 2) {
/*      */         
/* 1878 */         headerParameterCount++; continue;
/* 1879 */       }  if (paramBlock.getLocation() == 3)
/*      */       {
/* 1881 */         attachmentParameterCount++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1886 */     if (nonEmbeddedParameterCount > 1 || (nonEmbeddedParameterCount > 0 && embeddedParameterCount > 0))
/*      */     {
/* 1888 */       throw new GeneratorException("generator.internal.error.should.not.happen", "tie.generator.002");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1893 */     iterator = request.getBodyBlocks();
/* 1894 */     Block requestBlock = null;
/* 1895 */     String requestObjInit = null;
/* 1896 */     if (iterator.hasNext()) {
/* 1897 */       requestBlock = (Block)iterator.next();
/* 1898 */       requestBlockType = (LiteralType)requestBlock.getType();
/* 1899 */       requestObjType = requestBlockType.getJavaType().getName();
/* 1900 */       requestObjInit = requestBlockType.getJavaType().getInitString();
/* 1901 */       requestObjName = this.env.getNames().getTypeMemberName(requestBlockType.getJavaType());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1907 */     writeInvokeMethodDecl(p, operation);
/*      */ 
/*      */     
/* 1910 */     if (operation.getResponse() == null) {
/* 1911 */       p.pln("flushHttpResponse(state);");
/*      */     }
/*      */ 
/*      */     
/* 1915 */     declareRequestObjects(p, requestObjType, requestObjName, requestObjInit);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1921 */     declareHolderHeaderObjects(p, requestBlock, operation);
/*      */     
/* 1923 */     boolean hasRequestHeaders = false;
/* 1924 */     iterator = operation.getRequest().getHeaderBlocks();
/* 1925 */     hasRequestHeaders = iterator.hasNext();
/* 1926 */     if (hasRequestHeaders) {
/* 1927 */       writeRequestHeaders(p, operation);
/*      */     }
/*      */ 
/*      */     
/* 1931 */     declareHolderAttachmentObjects(p, requestBlock, operation);
/*      */     
/* 1933 */     p.plnI("try {");
/*      */     
/* 1935 */     Response response = operation.getResponse();
/* 1936 */     boolean resultIsEmbedded = false;
/* 1937 */     if (response != null) {
/* 1938 */       iterator = response.getBodyBlocks();
/* 1939 */       if (iterator.hasNext()) {
/* 1940 */         responseBlock = (Block)iterator.next();
/* 1941 */         responseBlockType = (LiteralType)responseBlock.getType();
/* 1942 */         responseObjType = responseBlockType.getJavaType().getName();
/* 1943 */         responseObjName = "_response";
/*      */       } 
/*      */       
/* 1946 */       if (resultType != null && !resultType.getName().equals("void")) {
/*      */         
/* 1948 */         iterator = response.getParameters();
/* 1949 */         if (iterator.hasNext()) {
/* 1950 */           Parameter parameter = iterator.next();
/* 1951 */           if (parameter.isEmbedded()) {
/* 1952 */             resultIsEmbedded = true;
/* 1953 */             p.p(resultType.getName() + " " + parameter.getName() + " = ");
/*      */           }
/*      */           else {
/*      */             
/* 1957 */             p.p(resultType.getName() + " " + parameter.getName() + " = ");
/*      */           } 
/*      */         } 
/* 1960 */       } else if (resultType == null) {
/* 1961 */         resultIsEmbedded = true;
/*      */       } else {
/* 1963 */         boolean cond = (VersionUtil.compare(this.sourceVersion, "1.1.1") >= 0);
/*      */         
/* 1965 */         if (cond && resultType.getName().equals("void")) {
/* 1966 */           resultIsEmbedded = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1977 */     p.p("((" + remoteClassName + ") getTarget())." + operation.getJavaMethod().getName() + "(");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1984 */     boolean hasBodyParams = false;
/* 1985 */     int count = 0;
/* 1986 */     for (Iterator<JavaParameter> params = operation.getJavaMethod().getParameters(); params.hasNext(); count++) {
/* 1987 */       JavaParameter jp = params.next();
/* 1988 */       Parameter param = jp.getParameter();
/* 1989 */       if (param.getBlock().getLocation() == 1) {
/* 1990 */         if (param.getBlock() == requestBlock) {
/* 1991 */           if (nonEmbeddedParameterCount > 0) {
/* 1992 */             hasBodyParams = true;
/* 1993 */             if (count > 0)
/* 1994 */               p.p(", "); 
/* 1995 */             p.p(requestObjName);
/*      */           } else {
/* 1997 */             hasBodyParams = true;
/* 1998 */             JavaStructureMember javaMember = getJavaMember(param);
/* 1999 */             if (count > 0)
/* 2000 */               p.p(", "); 
/* 2001 */             if (javaMember.isPublic()) {
/* 2002 */               p.p(requestObjName + "." + param.getName());
/*      */             } else {
/* 2004 */               p.p(requestObjName + "." + javaMember.getReadMethod() + "()");
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 2010 */         else if (param.getBlock() == responseBlock && 
/* 2011 */           param.getJavaParameter().isHolder()) {
/* 2012 */           if (hasBodyParams)
/* 2013 */             p.p(", "); 
/* 2014 */           p.p(param.getName() + "_holder");
/* 2015 */           hasBodyParams = true;
/*      */         }
/*      */       
/* 2018 */       } else if (param.getBlock().getLocation() == 3) {
/* 2019 */         if (hasBodyParams || count > 0)
/* 2020 */           p.p(", "); 
/* 2021 */         if (param.getJavaParameter().isHolder()) {
/* 2022 */           p.p(param.getName() + "_holder");
/*      */         } else {
/* 2024 */           p.p(param.getName());
/* 2025 */         }  hasBodyParams = true; count++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2030 */     if (headerParameterCount > 0) {
/* 2031 */       int count1 = 0;
/* 2032 */       iterator = request.getParameters();
/* 2033 */       for (; iterator.hasNext(); 
/* 2034 */         count++) {
/* 2035 */         Parameter parameter = iterator.next();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2040 */         if (!parameter.getJavaParameter().isHolder() && parameter.getBlock().getLocation() == 2) {
/* 2041 */           if (hasBodyParams || count > 0)
/* 2042 */             p.p(", "); 
/* 2043 */           p.p(parameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2048 */     if (operation.getResponse() != null) {
/*      */       
/* 2050 */       iterator = operation.getResponse().getParameters();
/* 2051 */       while (iterator.hasNext()) {
/*      */         
/* 2053 */         Parameter parameter = iterator.next();
/* 2054 */         if (parameter.getBlock().getLocation() == 2) {
/* 2055 */           p.p(", ");
/* 2056 */           JavaParameter javaParameter = parameter.getJavaParameter();
/* 2057 */           if (javaParameter.isHolder()) {
/* 2058 */             p.p(parameter.getName() + "_holder"); continue;
/*      */           } 
/* 2060 */           p.p(parameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2066 */     p.pln(");");
/*      */     
/* 2068 */     p.pln();
/*      */     
/* 2070 */     if (operation.getResponse() != null) {
/* 2071 */       p.pln("SOAPHeaderBlockInfo headerInfo;");
/* 2072 */       iterator = operation.getResponse().getParameters();
/* 2073 */       while (iterator.hasNext()) {
/*      */ 
/*      */         
/* 2076 */         Parameter parameter = iterator.next();
/* 2077 */         Block responseBlock1 = parameter.getBlock();
/* 2078 */         if (responseBlock1.getLocation() == 2) {
/* 2079 */           String memberName; JavaParameter javaParameter = parameter.getJavaParameter();
/* 2080 */           String qname = this.env.getNames().getBlockQNameName(null, responseBlock1);
/*      */           
/* 2082 */           if (javaParameter.isHolder()) {
/* 2083 */             memberName = parameter.getName() + "_holder.value";
/*      */           } else {
/* 2085 */             memberName = parameter.getName();
/*      */           } 
/* 2087 */           p.pln("headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */           
/* 2091 */           p.pln("headerInfo.setValue(" + memberName + ");");
/* 2092 */           p.pln("headerInfo.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, responseBlock1.getType()).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2100 */           p.pln("state.getResponse().add(headerInfo);");
/*      */         } 
/*      */       } 
/* 2103 */       p.pln();
/*      */       
/* 2105 */       p.pln();
/* 2106 */       iterator = operation.getResponse().getParameters();
/*      */       
/* 2108 */       if (!iterator.hasNext() && resultIsEmbedded && responseBlock != null) {
/* 2109 */         p.pln(responseObjType + " " + responseObjName + " = new " + responseObjType + "();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2116 */         iterator = response.getParameters();
/* 2117 */         if (iterator.hasNext()) {
/* 2118 */           Parameter parameter = iterator.next();
/* 2119 */           JavaStructureMember javaMember = getJavaMember(parameter);
/* 2120 */           if (javaMember.isPublic()) {
/* 2121 */             p.pln(responseObjName + "." + parameter.getName() + " = _result;");
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 2127 */             p.pln(responseObjName + "." + javaMember.getWriteMethod() + "(_result);");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2135 */       while (iterator.hasNext()) {
/*      */         
/* 2137 */         Parameter parameter = iterator.next();
/* 2138 */         if (parameter.getBlock().getLocation() == 1) {
/* 2139 */           if (resultIsEmbedded && responseBlock != null) {
/* 2140 */             String memberName; JavaStructureMember javaMember = getJavaMember(parameter);
/* 2141 */             JavaParameter javaParameter = parameter.getJavaParameter();
/* 2142 */             if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */               
/* 2144 */               memberName = parameter.getName() + "_holder.value";
/*      */             } else {
/* 2146 */               memberName = parameter.getName();
/*      */             } 
/* 2148 */             if (javaMember != null) {
/* 2149 */               p.pln(responseObjType + " " + responseObjName + " = new " + responseObjType + "();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2157 */               if (parameter.getType() instanceof LiteralArrayWrapperType)
/*      */               {
/* 2159 */                 memberName = "new " + parameter.getType().getJavaType().getName() + "(" + memberName + ")";
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2166 */               if (javaMember.isPublic()) {
/* 2167 */                 p.pln(responseObjName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */ 
/*      */               
/* 2175 */               p.pln(responseObjName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 2184 */             if (parameter.getBlock() == responseBlock) {
/* 2185 */               String pName = parameter.getName();
/* 2186 */               JavaParameter javaParam = parameter.getJavaParameter();
/* 2187 */               if (javaParam != null && javaParam.isHolder()) {
/* 2188 */                 pName = pName + "_holder.value";
/*      */               }
/* 2190 */               p.pln(responseObjType + " " + responseObjName + " = " + pName + ";");
/*      */               
/*      */               break;
/*      */             } 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 2198 */           if (parameter.getBlock() == responseBlock) {
/* 2199 */             String pName = parameter.getName();
/* 2200 */             JavaParameter javaParam = parameter.getJavaParameter();
/* 2201 */             if (javaParam != null && javaParam.isHolder()) {
/* 2202 */               pName = pName + "_holder.value";
/*      */             }
/* 2204 */             p.pln(responseObjType + " " + responseObjName + " = " + pName + ";");
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2215 */       if (responseBlock != null) {
/* 2216 */         p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, responseBlock) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2223 */         String valueStr = responseObjName;
/* 2224 */         if (SimpleToBoxedUtil.isPrimitive(responseObjType)) {
/* 2225 */           valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, responseObjType);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 2230 */         p.pln("bodyBlock.setValue(" + valueStr + ");");
/*      */         
/* 2232 */         String serializer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)responseBlockType).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2238 */         p.pln("bodyBlock.setSerializer(" + serializer + ");");
/* 2239 */         p.pln("state.getResponse().setBody(bodyBlock);");
/* 2240 */       } else if (responseBlock == null) {
/* 2241 */         p.pln("SOAPBlockInfo bodyBlock = new SOAPBlockInfo(null);");
/* 2242 */         p.pln("bodyBlock.setSerializer(DummySerializer.getInstance());");
/*      */         
/* 2244 */         p.pln("state.getResponse().setBody(bodyBlock);");
/*      */       } 
/* 2246 */       addAttachmentsToResponse(p, operation.getResponse().getParameters());
/*      */     } 
/* 2248 */     writeCatchClauses(p, operation);
/* 2249 */     p.pOln("}");
/* 2250 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeReadFirstBodyElementDefault(IndentingWriter p, String opCode) throws IOException {
/* 2257 */     p.pln("throw new SOAPProtocolViolationException(\"soap.operation.unrecognized\", java.lang.Integer.toString(" + opCode + "));");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAttachmentsToResponse(IndentingWriter p, Iterator<Parameter> params) throws IOException {
/* 2268 */     boolean gotone = false;
/* 2269 */     while (params.hasNext()) {
/*      */       
/* 2271 */       String getUUIDMethod = null;
/* 2272 */       Parameter parameter = params.next();
/* 2273 */       Block responseBlock = parameter.getBlock();
/* 2274 */       if (responseBlock.getLocation() == 3) {
/* 2275 */         JavaParameter javaParameter = parameter.getJavaParameter();
/* 2276 */         String paramName = parameter.getName();
/* 2277 */         if (javaParameter != null && javaParameter.isHolder())
/* 2278 */           paramName = paramName + "_holder.value"; 
/* 2279 */         String mimeType = null;
/* 2280 */         String contentID = null;
/* 2281 */         AbstractType pType = parameter.getType();
/* 2282 */         if (pType instanceof LiteralAttachmentType) {
/* 2283 */           LiteralAttachmentType attType = (LiteralAttachmentType)pType;
/* 2284 */           if (attType.getJavaType().getRealName().equals("javax.activation.DataHandler")) {
/* 2285 */             mimeType = "((" + parameter.getType().getJavaType().getName() + ")" + paramName + ").getContentType()";
/*      */           } else {
/* 2287 */             mimeType = "\"" + attType.getMIMEType() + "\"";
/*      */           } 
/*      */           
/* 2290 */           contentID = attType.getContentID();
/*      */         } 
/* 2292 */         p.pln("addAttachment(state.getResponse().getMessage(), (java.lang.Object)" + paramName + ", " + mimeType + ", " + "\"" + contentID + "\");");
/* 2293 */         setAddAttachmentMethodFlag(true);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareHolderAttachmentObjects(IndentingWriter p, Block requestBlock, Operation operation) throws IOException {
/* 2314 */     JavaMethod javaMethod = operation.getJavaMethod();
/*      */     
/* 2316 */     Iterator<JavaParameter> iterator = javaMethod.getParameters();
/*      */ 
/*      */     
/* 2319 */     boolean declaredHeaderObj = false;
/* 2320 */     boolean declaredAttachmentObj = false;
/* 2321 */     boolean mimeTypesDeclared = false;
/* 2322 */     boolean isDataHandler = false;
/* 2323 */     for (int i = 0; iterator.hasNext(); ) {
/* 2324 */       JavaParameter javaParameter = iterator.next();
/* 2325 */       if (javaParameter.getParameter().getBlock().getLocation() == 3) {
/*      */         
/* 2327 */         if (!mimeTypesDeclared) {
/* 2328 */           p.pln("String[] mimeTypes = null;");
/* 2329 */           mimeTypesDeclared = true;
/*      */         } 
/*      */         
/* 2332 */         if (javaParameter.getParameter().getLinkedParameter() != null) {
/* 2333 */           AbstractType pType = javaParameter.getParameter().getType();
/* 2334 */           if (pType instanceof LiteralAttachmentType) {
/* 2335 */             LiteralAttachmentType attType = (LiteralAttachmentType)pType;
/* 2336 */             String javaType = javaParameter.getParameter().getType().getJavaType().getName();
/* 2337 */             int index = attType.getContentID().indexOf('@');
/* 2338 */             String cId = attType.getContentID().substring(index + 1);
/* 2339 */             if (attType.getJavaType().getRealName().equals("javax.activation.DataHandler")) {
/* 2340 */               isDataHandler = true;
/*      */             } else {
/* 2342 */               isDataHandler = false;
/*      */             } 
/* 2344 */             List mimeList = attType.getAlternateMIMETypes();
/* 2345 */             p.pln("mimeTypes = new String[" + mimeList.size() + "];");
/*      */             
/* 2347 */             int j = 0;
/* 2348 */             for (Iterator<String> iter = mimeList.iterator(); iter.hasNext(); j++) {
/* 2349 */               p.pln("mimeTypes[" + j + "] = new String(\"" + (String)iter.next() + "\");");
/*      */             }
/*      */             
/* 2352 */             p.pln(javaParameter.getParameter().getName() + "_holder.value = (" + javaType + ")getAttachment(state.getRequest().getMessage(), mimeTypes, \"" + cId + "\", " + String.valueOf(isDataHandler) + ");");
/*      */ 
/*      */             
/* 2355 */             setGetAttachmentMethodFlag(true);
/*      */           }  continue;
/* 2357 */         }  if (!javaParameter.isHolder()) {
/* 2358 */           AbstractType pType = javaParameter.getParameter().getType();
/* 2359 */           if (pType instanceof LiteralAttachmentType) {
/* 2360 */             LiteralAttachmentType attType = (LiteralAttachmentType)pType;
/* 2361 */             AbstractType paramType = javaParameter.getParameter().getType();
/*      */             
/* 2363 */             int index = attType.getContentID().indexOf('@');
/* 2364 */             String cId = attType.getContentID().substring(index + 1);
/* 2365 */             if (attType.getJavaType().getRealName().equals("javax.activation.DataHandler")) {
/* 2366 */               isDataHandler = true;
/*      */             } else {
/* 2368 */               isDataHandler = false;
/*      */             } 
/* 2370 */             List mimeList = attType.getAlternateMIMETypes();
/* 2371 */             p.pln("mimeTypes = new String[" + mimeList.size() + "];");
/*      */             
/* 2373 */             int j = 0;
/* 2374 */             for (Iterator<String> iter = mimeList.iterator(); iter.hasNext(); j++) {
/* 2375 */               p.pln("mimeTypes[" + j + "] = new String(\"" + (String)iter.next() + "\");");
/*      */             }
/*      */             
/* 2378 */             p.pln(paramType.getJavaType().getName() + " " + javaParameter.getParameter().getName() + " = (" + paramType.getJavaType().getName() + ")getAttachment(state.getRequest().getMessage(), mimeTypes, \"" + cId + "\", " + String.valueOf(isDataHandler) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2385 */             setGetAttachmentMethodFlag(true);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeStaticMembers(IndentingWriter p, Map headerMap) throws IOException {
/* 2395 */     super.writeStaticMembers(p, headerMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeUnderstoodHeadersMember(IndentingWriter p, Map headerMap) throws IOException {
/* 2403 */     p.p("private static final QName[] understoodHeaderNames = new QName[] { ");
/*      */     
/* 2405 */     boolean first = true;
/* 2406 */     Iterator<Operation> operationsIter = this.operations.iterator();
/* 2407 */     for (int i = 0; operationsIter.hasNext(); i++) {
/* 2408 */       Operation operation = operationsIter.next();
/* 2409 */       Iterator<Block> blocks = operation.getRequest().getHeaderBlocks();
/* 2410 */       while (blocks.hasNext()) {
/* 2411 */         Block block = blocks.next();
/* 2412 */         String qname = this.env.getNames().getBlockQNameName(operation, block);
/*      */         
/* 2414 */         if (!first) {
/* 2415 */           p.p(", ");
/*      */         }
/* 2417 */         p.p(qname);
/* 2418 */         first = false;
/*      */       } 
/*      */     } 
/* 2421 */     p.pln(" };");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePreResponseWritingHook(IndentingWriter p, List operations) throws IOException {
/* 2429 */     p.pln("");
/* 2430 */     p.plnI("protected void preResponseWritingHook(StreamingHandlerState state) throws Exception {");
/* 2431 */     p.pln("super.preResponseWritingHook(state);");
/* 2432 */     p.plnI("switch (state.getRequest().getOperationCode()) {");
/* 2433 */     Iterator<Operation> operationsIter = operations.iterator();
/*      */     
/* 2435 */     while (operationsIter.hasNext()) {
/* 2436 */       Operation operation = operationsIter.next();
/* 2437 */       if (!needsReadFirstBodyElementFor(operation))
/*      */         continue; 
/* 2439 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/* 2443 */       p.pln("addNonExplicitAttachment(state);");
/* 2444 */       p.pln("break;");
/* 2445 */       p.pO();
/*      */     } 
/*      */     
/* 2448 */     p.pOln("}");
/* 2449 */     p.pOln("}");
/* 2450 */     p.pln();
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
/*      */   protected void writePostEnvelopeReadingHook(IndentingWriter p, List operations) throws IOException {
/* 2474 */     p.pln();
/* 2475 */     p.plnI("protected void postEnvelopeReadingHook(StreamingHandlerState state) throws Exception {");
/* 2476 */     p.pln("super.postEnvelopeReadingHook(state);");
/* 2477 */     p.plnI("switch (state.getRequest().getOperationCode()) {");
/* 2478 */     Iterator<Operation> operationsIter = operations.iterator();
/*      */     
/* 2480 */     while (operationsIter.hasNext()) {
/* 2481 */       Operation operation = operationsIter.next();
/* 2482 */       if (!needsReadFirstBodyElementFor(operation))
/*      */         continue; 
/* 2484 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/* 2488 */       p.pln("getNonExplicitAttachment(state);");
/* 2489 */       p.pln("break;");
/* 2490 */       p.pO();
/*      */     } 
/*      */     
/* 2493 */     p.pOln("}");
/* 2494 */     p.pOln("}");
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
/*      */   protected void writeAttachmentHooks(IndentingWriter p) throws IOException {
/* 2515 */     boolean generateGetNonExplicitAttachmentMethod = false;
/* 2516 */     boolean generateAddNonExplicitAttachmentMethod = false;
/* 2517 */     List<Operation> reqOps = new ArrayList();
/* 2518 */     List<Operation> resOps = new ArrayList();
/* 2519 */     Iterator<Operation> iter = this.operations.iterator();
/* 2520 */     for (int i = 0; iter.hasNext(); i++) {
/* 2521 */       Operation operation = iter.next();
/* 2522 */       Request req = operation.getRequest();
/* 2523 */       if (req != null && req.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding") != null) {
/* 2524 */         if (!generateGetNonExplicitAttachmentMethod)
/* 2525 */           generateGetNonExplicitAttachmentMethod = true; 
/* 2526 */         reqOps.add(operation);
/*      */       } 
/*      */       
/* 2529 */       Response res = operation.getResponse();
/* 2530 */       if (res != null && res.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding") != null) {
/* 2531 */         if (!generateAddNonExplicitAttachmentMethod)
/* 2532 */           generateAddNonExplicitAttachmentMethod = true; 
/* 2533 */         resOps.add(operation);
/*      */       } 
/*      */     } 
/*      */     
/* 2537 */     if (generateAddNonExplicitAttachmentMethod) {
/* 2538 */       writePreResponseWritingHook(p, resOps);
/* 2539 */       writeAddNonExplicitAttachment(p);
/*      */     } 
/*      */     
/* 2542 */     if (generateGetNonExplicitAttachmentMethod) {
/* 2543 */       writePostEnvelopeReadingHook(p, reqOps);
/* 2544 */       writeGetNonExplicitAttachment(p);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeGetNonExplicitAttachment(IndentingWriter p) throws IOException {
/* 2552 */     p.plnI("private void getNonExplicitAttachment(StreamingHandlerState state) throws Exception {");
/* 2553 */     p.pln("javax.xml.rpc.handler.soap.SOAPMessageContext smc = state.getMessageContext();");
/* 2554 */     p.pln("javax.xml.soap.SOAPMessage message = state.getRequest().getMessage();");
/* 2555 */     p.pln("java.util.ArrayList attachments = null;");
/* 2556 */     p.plnI("for(java.util.Iterator iter = message.getAttachments(); iter.hasNext();) {");
/* 2557 */     p.plnI("if(attachments == null) {");
/* 2558 */     p.pln("attachments = new java.util.ArrayList();");
/* 2559 */     p.pOln("}");
/* 2560 */     p.pln("attachments.add(iter.next());");
/* 2561 */     p.pOln("}");
/* 2562 */     p.pln("smc.setProperty(com.sun.xml.rpc.server.ServerPropertyConstants.GET_ATTACHMENT_PROPERTY, attachments);");
/* 2563 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeAddNonExplicitAttachment(IndentingWriter p) throws IOException {
/* 2571 */     p.pln();
/* 2572 */     p.plnI("private void addNonExplicitAttachment(StreamingHandlerState state) throws Exception {");
/* 2573 */     p.pln("javax.xml.rpc.handler.soap.SOAPMessageContext smc = state.getMessageContext();");
/* 2574 */     p.pln("javax.xml.soap.SOAPMessage message = state.getResponse().getMessage();");
/* 2575 */     p.pln("Object c = smc.getProperty(com.sun.xml.rpc.server.ServerPropertyConstants.SET_ATTACHMENT_PROPERTY);");
/* 2576 */     p.pln("smc.setProperty(com.sun.xml.rpc.server.ServerPropertyConstants.SET_ATTACHMENT_PROPERTY, null);");
/* 2577 */     p.plnI("if(c != null && c instanceof java.util.Collection) {");
/* 2578 */     p.plnI("for(java.util.Iterator iter = ((java.util.Collection)c).iterator(); iter.hasNext();) {");
/* 2579 */     p.pln("Object attachment = iter.next();");
/* 2580 */     p.plnI("if(attachment instanceof javax.xml.soap.AttachmentPart) {");
/* 2581 */     p.pln("message.addAttachmentPart((javax.xml.soap.AttachmentPart)attachment);");
/* 2582 */     p.pOln("}");
/* 2583 */     p.pOln("}");
/* 2584 */     p.pOln("}");
/* 2585 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   protected void writeHooks(IndentingWriter p) throws IOException {
/* 2589 */     Iterator iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.tieHooks");
/*      */ 
/*      */     
/* 2592 */     if (iter != null && iter.hasNext()) {
/*      */       
/* 2594 */       writePreHandlingHook(p);
/* 2595 */       writePostResponseWritingHook(p);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void writePreHandlingHook(IndentingWriter p) throws IOException {
/* 2600 */     p.pln();
/* 2601 */     p.plnI("protected boolean preHandlingHook(StreamingHandlerState state) throws Exception {");
/* 2602 */     p.pln("boolean bool = false;");
/*      */     
/* 2604 */     Iterator<TieHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.tieHooks");
/*      */ 
/*      */     
/* 2607 */     TieHooksIf.TieHooksState state = new TieHooksIf.TieHooksState();
/* 2608 */     state.superDone = false;
/* 2609 */     while (iter != null && iter.hasNext()) {
/* 2610 */       TieHooksIf plugin = iter.next();
/* 2611 */       plugin.preHandlingHook(this.model, p, state);
/*      */     } 
/* 2613 */     if (!state.superDone) {
/* 2614 */       p.pln("bool = super.preHandlingHook(state);");
/*      */     }
/* 2616 */     p.pln("return bool;");
/* 2617 */     p.pOln("}");
/* 2618 */     p.pln();
/*      */   }
/*      */   
/*      */   protected void writePostResponseWritingHook(IndentingWriter p) throws IOException {
/* 2622 */     p.pln();
/* 2623 */     p.plnI("protected void postResponseWritingHook(StreamingHandlerState state) throws Exception {");
/*      */     
/* 2625 */     Iterator<TieHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.tieHooks");
/*      */ 
/*      */     
/* 2628 */     TieHooksIf.TieHooksState state = new TieHooksIf.TieHooksState();
/* 2629 */     state.superDone = false;
/* 2630 */     while (iter != null && iter.hasNext()) {
/* 2631 */       TieHooksIf plugin = iter.next();
/* 2632 */       plugin.postResponseWritingHook(this.model, p, state);
/*      */     } 
/* 2634 */     if (!state.superDone) {
/* 2635 */       p.pln("super.postResponseWritingHook(state);");
/*      */     }
/* 2637 */     p.pOln("}");
/* 2638 */     p.pln();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeStatic(IndentingWriter p) throws IOException {
/* 2643 */     Iterator<TieHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.tieHooks");
/*      */ 
/*      */     
/* 2646 */     while (iter != null && iter.hasNext()) {
/* 2647 */       TieHooksIf plugin = iter.next();
/* 2648 */       plugin.writeTieStatic(this.model, this.port, p);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Operation operationHasEmptyBody(Operation operation) {
/* 2656 */     if (operation.getRequest() != null && operation.getRequest().getBodyBlockCount() == 0)
/*      */     {
/* 2658 */       return operation; } 
/* 2659 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\TieGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */