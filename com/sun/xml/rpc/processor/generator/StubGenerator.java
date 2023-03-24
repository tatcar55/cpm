/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Message;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
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
/*      */ import com.sun.xml.rpc.spi.model.Port;
/*      */ import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
/*      */ import com.sun.xml.rpc.tools.wscompile.StubHooksIf;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
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
/*      */ public class StubGenerator
/*      */   extends StubTieGeneratorBase
/*      */ {
/*      */   private static final String prefix = "_";
/*   80 */   private String dirPath = "";
/*      */ 
/*      */   
/*      */   public StubGenerator() {}
/*      */ 
/*      */   
/*      */   public StubGenerator(SOAPVersion ver) {
/*   87 */     super(ver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*   94 */     return new StubGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  102 */     return new StubGenerator(model, config, properties, ver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StubGenerator(Model model, Configuration config, Properties properties) {
/*  109 */     super(model, config, properties);
/*  110 */     String key = "sourceDirectory";
/*  111 */     this.dirPath = properties.getProperty(key);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StubGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  119 */     super(model, config, properties, ver);
/*  120 */     String key = "sourceDirectory";
/*  121 */     this.dirPath = properties.getProperty(key);
/*      */   }
/*      */   
/*      */   protected String getClassName() {
/*  125 */     return this.env.getNames().stubFor((Port)this.port);
/*      */   }
/*      */   
/*      */   protected String getPrefix() {
/*  129 */     return "_";
/*      */   }
/*      */   
/*      */   protected String getStateType() {
/*  133 */     return "StreamingSenderState";
/*      */   }
/*      */   
/*      */   protected Message getMessageToDeserialize(Operation operation) {
/*  137 */     return (Message)operation.getResponse();
/*      */   }
/*      */   
/*      */   protected String getStateGetRequestResponseString() {
/*  141 */     return "getResponse";
/*      */   }
/*      */   
/*      */   protected String getInitializeAccess() {
/*  145 */     return "public";
/*      */   }
/*      */   
/*      */   protected boolean superClassHasInitialize() {
/*  149 */     return true;
/*      */   }
/*      */   
/*      */   protected String getSOAPVersion() {
/*  153 */     if (this.port.getSOAPVersion().equals(SOAPVersion.SOAP_12.toString())) {
/*  154 */       return "SOAPVersion.SOAP_12";
/*      */     }
/*  156 */     return "SOAPVersion.SOAP_11";
/*      */   }
/*      */   
/*      */   protected void writeImports(IndentingWriter p) throws IOException {
/*  160 */     super.writeImports(p);
/*  161 */     p.pln("import com.sun.xml.rpc.client.SenderException;");
/*  162 */     p.pln("import com.sun.xml.rpc.client.*;");
/*  163 */     p.pln("import com.sun.xml.rpc.client.http.*;");
/*  164 */     p.pln("import javax.xml.rpc.handler.*;");
/*  165 */     p.pln("import javax.xml.rpc.JAXRPCException;");
/*  166 */     p.pln("import javax.xml.rpc.soap.SOAPFaultException;");
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeClassDecl(IndentingWriter p, String stubClassName) throws IOException {
/*  171 */     JavaInterface javaInterface = this.port.getJavaInterface();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  178 */     File classFile = this.env.getNames().sourceFileForClass(stubClassName, stubClassName, new File(this.dirPath), this.env);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  184 */     GeneratedFileInfo fi = new GeneratedFileInfo();
/*  185 */     fi.setFile(classFile);
/*  186 */     fi.setType("Stub");
/*  187 */     this.env.addGeneratedFile(fi);
/*      */     
/*  189 */     p.plnI("public class " + Names.stripQualifier(stubClassName));
/*  190 */     p.pln("extends com.sun.xml.rpc.client.StubBase");
/*  191 */     p.p("implements " + javaInterface.getName());
/*  192 */     Iterator<String> remoteInterfaces = javaInterface.getInterfaces();
/*  193 */     if (remoteInterfaces.hasNext()) {
/*  194 */       while (remoteInterfaces.hasNext()) {
/*  195 */         p.p(", ");
/*  196 */         p.p(remoteInterfaces.next());
/*      */       } 
/*      */     }
/*  199 */     p.pln(" {");
/*  200 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeConstructor(IndentingWriter p, String stubClassName) throws IOException {
/*  206 */     p.pln("/*");
/*  207 */     p.pln(" *  public constructor");
/*  208 */     p.pln(" */");
/*  209 */     p.plnI("public " + Names.stripQualifier(stubClassName) + "(HandlerChain handlerChain) {");
/*      */ 
/*      */ 
/*      */     
/*  213 */     p.pln("super(handlerChain);");
/*  214 */     String address = this.port.getAddress();
/*  215 */     if (address != null && address.length() > 0) {
/*  216 */       p.pln("_setProperty(ENDPOINT_ADDRESS_PROPERTY, \"" + address + "\");");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  221 */     p.pOln("}");
/*  222 */     p.pln("");
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
/*      */   protected void writeRpcEncodedOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {
/*  244 */     JavaMethod javaMethod = operation.getJavaMethod();
/*  245 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */     
/*  248 */     declareOperationMethod(p, operation);
/*      */ 
/*      */     
/*  251 */     Iterator<JavaParameter> iterator = javaMethod.getParameters();
/*      */     
/*  253 */     for (int i = 0; iterator.hasNext(); i++) {
/*  254 */       JavaParameter javaParameter = iterator.next();
/*  255 */       if (javaParameter.isHolder()) {
/*  256 */         p.plnI("if (" + javaParameter.getName() + " == null) {");
/*  257 */         p.pln("throw new IllegalArgumentException(\"" + javaParameter.getName() + " cannot be null\");");
/*      */ 
/*      */ 
/*      */         
/*  261 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */     
/*  265 */     p.plnI("try {");
/*      */     
/*  267 */     Request request = operation.getRequest();
/*  268 */     Block block = null;
/*  269 */     iterator = request.getBodyBlocks();
/*  270 */     if (iterator.hasNext())
/*  271 */       block = (Block)iterator.next(); 
/*  272 */     SOAPType type = (SOAPType)block.getType();
/*  273 */     String objType = type.getJavaType().getName();
/*  274 */     String objName = "_" + this.env.getNames().getTypeMemberName(type.getJavaType());
/*      */     
/*  276 */     p.pln();
/*      */     
/*  278 */     QName name = this.port.getName();
/*      */     
/*  280 */     p.pln("StreamingSenderState _state = _start(_handlerChain);");
/*  281 */     p.pln();
/*  282 */     p.pln("InternalSOAPMessage _request = _state.getRequest();");
/*  283 */     p.pln("_request.setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */ 
/*      */ 
/*      */     
/*  287 */     p.plnI(objType + " " + objName + " =");
/*  288 */     p.pln("new " + objType + "();");
/*  289 */     p.pO();
/*  290 */     p.pln();
/*  291 */     iterator = request.getParameters();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  296 */     boolean declaredHeaderBlockInfo = false;
/*  297 */     while (iterator.hasNext()) {
/*  298 */       String memberName; Parameter parameter = (Parameter)iterator.next();
/*  299 */       Block paramBlock = parameter.getBlock();
/*  300 */       if (paramBlock.getLocation() == 1) {
/*  301 */         JavaStructureMember javaMember = getJavaMember(parameter);
/*  302 */         if (parameter.getJavaParameter() != null && parameter.getJavaParameter().isHolder()) {
/*      */           
/*  304 */           memberName = parameter.getName() + ".value";
/*      */         } else {
/*  306 */           memberName = parameter.getName();
/*      */         } 
/*  308 */         if (javaMember.isPublic()) {
/*  309 */           p.pln(objName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  317 */         p.pln(objName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
/*      */ 
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */ 
/*      */       
/*  325 */       if (!declaredHeaderBlockInfo) {
/*  326 */         p.pln("SOAPHeaderBlockInfo _headerInfo;");
/*  327 */         declaredHeaderBlockInfo = true;
/*      */       } 
/*  329 */       JavaParameter javaParameter = parameter.getJavaParameter();
/*  330 */       String qname = this.env.getNames().getBlockQNameName(null, paramBlock);
/*      */       
/*  332 */       if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */         
/*  334 */         memberName = parameter.getName() + ".value";
/*      */       } else {
/*  336 */         memberName = parameter.getName();
/*      */       } 
/*  338 */       String serializer = this.writerFactory.createWriter(this.servicePackage, paramBlock.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  344 */       p.pln("_headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */       
/*  348 */       p.pln("_headerInfo.setValue(" + memberName + ");");
/*  349 */       p.pln("_headerInfo.setSerializer(" + serializer + ");");
/*  350 */       p.pln("_request.add(_headerInfo);");
/*      */     } 
/*      */ 
/*      */     
/*  354 */     p.pln();
/*  355 */     p.pln("SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, block) + ");");
/*      */ 
/*      */ 
/*      */     
/*  359 */     p.pln("_bodyBlock.setValue(" + objName + ");");
/*  360 */     p.pln("_bodyBlock.setSerializer(" + this.writerFactory.createWriter(this.servicePackage, (AbstractType)type).serializerMemberName() + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     p.pln("_request.setBody(_bodyBlock);");
/*  367 */     p.pln();
/*  368 */     String soapAction = (operation.getSOAPAction() != null) ? operation.getSOAPAction() : "";
/*      */     
/*  370 */     p.pln("_state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, \"" + soapAction + "\");");
/*      */ 
/*      */ 
/*      */     
/*  374 */     p.pln();
/*  375 */     if (operation.getResponse() != null) {
/*  376 */       p.pln("_send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } else {
/*      */       
/*  379 */       p.pln("_sendOneWay((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } 
/*      */ 
/*      */     
/*  383 */     p.pln();
/*      */ 
/*      */     
/*  386 */     Response response = operation.getResponse();
/*  387 */     if (response != null) {
/*  388 */       iterator = response.getBodyBlocks();
/*  389 */       objName = null;
/*  390 */       objType = null;
/*  391 */       block = null;
/*  392 */       while (iterator.hasNext()) {
/*  393 */         block = (Block)iterator.next();
/*  394 */         if (block.getName().getLocalPart().equals(this.env.getNames().getResponseName(operation.getName().getLocalPart()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  400 */           type = (SOAPType)block.getType();
/*  401 */           objType = type.getJavaType().getName();
/*  402 */           objName = "_" + this.env.getNames().getTypeMemberName(type.getJavaType());
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  410 */       String initString = (type == null) ? "null" : type.getJavaType().getInitString();
/*      */       
/*  412 */       p.pln(objType + " " + objName + " = " + initString + ";");
/*      */       
/*  414 */       String objMemberName = "_responseObj";
/*  415 */       p.pln("Object " + objMemberName + " = _state.getResponse().getBody().getValue();");
/*      */ 
/*      */ 
/*      */       
/*  419 */       p.plnI("if (" + objMemberName + " instanceof SOAPDeserializationState) {");
/*      */ 
/*      */ 
/*      */       
/*  423 */       p.plnI(objName + " =");
/*  424 */       p.pln("(" + objType + ")((SOAPDeserializationState)" + objMemberName + ").getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  430 */       p.pO();
/*  431 */       p.pOlnI("} else {");
/*  432 */       p.plnI(objName + " =");
/*  433 */       p.pln("(" + objType + ")" + objMemberName + ";");
/*  434 */       p.pO();
/*  435 */       p.pOln("}");
/*  436 */       p.pln();
/*  437 */       iterator = response.getParameters();
/*  438 */       boolean hasReturn = (resultType != null && !resultType.getName().equals("void"));
/*      */ 
/*      */       
/*  441 */       while (iterator.hasNext()) {
/*  442 */         Parameter parameter = (Parameter)iterator.next();
/*  443 */         JavaParameter javaParameter = parameter.getJavaParameter();
/*  444 */         Block paramBlock = parameter.getBlock();
/*  445 */         if (javaParameter == null || !javaParameter.isHolder()) {
/*      */           continue;
/*      */         }
/*  448 */         if (paramBlock.getLocation() == 1) {
/*  449 */           JavaStructureMember javaMember = getJavaMember(parameter);
/*  450 */           p.plnI(javaParameter.getName() + ".value =");
/*  451 */           if (javaMember.isPublic()) {
/*  452 */             p.pln(objName + "." + javaMember.getName() + ";");
/*      */           } else {
/*  454 */             p.pln(objName + "." + javaMember.getReadMethod() + "();");
/*      */           } 
/*  456 */           p.pO();
/*      */         } 
/*      */       } 
/*  459 */       boolean hasResponseHeaders = false;
/*  460 */       iterator = operation.getResponse().getHeaderBlocks();
/*  461 */       hasResponseHeaders = iterator.hasNext();
/*  462 */       iterator = operation.getResponse().getParameters();
/*  463 */       if (hasResponseHeaders && iterator.hasNext()) {
/*  464 */         p.pln("Iterator _headers = _state.getResponse().headers();");
/*  465 */         p.pln("SOAPHeaderBlockInfo _curHeader;");
/*  466 */         p.pln("Object _headerObj;");
/*  467 */         p.plnI("while (_headers.hasNext()) {");
/*  468 */         p.pln("_curHeader = (SOAPHeaderBlockInfo)_headers.next();");
/*  469 */         boolean startedHeaders = false;
/*  470 */         while (iterator.hasNext()) {
/*  471 */           Parameter parameter = (Parameter)iterator.next();
/*  472 */           if (parameter.getBlock().getLocation() == 2) {
/*  473 */             String varName; if (startedHeaders) {
/*  474 */               p.p(" else ");
/*      */             }
/*  476 */             startedHeaders = true;
/*  477 */             String paramName = parameter.getName();
/*  478 */             String paramType = parameter.getType().getJavaType().getName();
/*      */             
/*  480 */             String qname = this.env.getNames().getBlockQNameName(null, parameter.getBlock());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  485 */             if (parameter.getType().getJavaType().isHolder()) {
/*  486 */               varName = paramName + ".value";
/*      */             } else {
/*  488 */               varName = paramName;
/*      */             } 
/*  490 */             p.plnI("if (_curHeader.getName().equals(" + qname + ")) {");
/*      */ 
/*      */ 
/*      */             
/*  494 */             p.pln("_headerObj = _curHeader.getValue();");
/*  495 */             p.plnI("if (_headerObj instanceof SOAPDeserializationState) {");
/*      */             
/*  497 */             p.pln(paramName + ".value = (" + paramType + ")((SOAPDeserializationState)" + "_headerObj).getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  503 */             p.pOlnI("} else {");
/*  504 */             p.pln(varName + " = (" + paramType + ")_headerObj;");
/*  505 */             p.pOln("}");
/*  506 */             p.pO("}");
/*      */           } 
/*      */         } 
/*  509 */         if (startedHeaders)
/*  510 */           p.pln(); 
/*  511 */         p.pOln("}");
/*      */       } 
/*  513 */       if (hasReturn) {
/*  514 */         iterator = response.getParameters();
/*  515 */         Parameter parameter = (Parameter)iterator.next();
/*  516 */         if (parameter.getBlock() == block) {
/*  517 */           JavaStructureMember javaMember = getJavaMember(parameter);
/*  518 */           if (javaMember.isPublic()) {
/*  519 */             p.pln("return " + parameter.getName() + ";");
/*      */           } else {
/*  521 */             p.pln("return " + objName + "." + javaMember.getReadMethod() + "();");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  529 */       p.pO();
/*      */     } 
/*      */     
/*  532 */     writeOperationCatchBlock(p, operation);
/*      */     
/*  534 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void declareOperationMethod(IndentingWriter p, Operation operation) throws IOException {
/*  544 */     JavaMethod javaMethod = operation.getJavaMethod();
/*  545 */     String methodName = javaMethod.getName();
/*  546 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */     
/*  549 */     p.pln("/*");
/*  550 */     p.pln(" *  implementation of " + methodName);
/*  551 */     p.pln(" */");
/*      */     
/*  553 */     String resultName = "void";
/*  554 */     if (resultType != null)
/*      */     {
/*  556 */       if (resultType instanceof JavaStructureType) {
/*  557 */         AbstractType literalResultType = (AbstractType)((JavaStructureType)resultType).getOwner();
/*      */         
/*  559 */         if (literalResultType instanceof LiteralArrayWrapperType && operation.getStyle().equals(SOAPStyle.RPC)) {
/*      */           
/*  561 */           resultName = ((LiteralArrayWrapperType)literalResultType).getJavaArrayType().getName();
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  566 */           resultName = resultType.getName();
/*      */         } 
/*      */       } else {
/*  569 */         resultName = resultType.getName();
/*      */       } 
/*      */     }
/*      */     
/*  573 */     p.p("public " + resultName + " " + methodName + "(");
/*      */     
/*  575 */     Iterator<JavaParameter> iterator = javaMethod.getParameters();
/*      */     int i;
/*  577 */     for (i = 0; iterator.hasNext(); i++) {
/*  578 */       if (i > 0)
/*  579 */         p.p(", "); 
/*  580 */       JavaParameter javaParameter = iterator.next();
/*  581 */       if (javaParameter.isHolder()) {
/*  582 */         if (javaParameter.getHolderName() == null) {
/*  583 */           p.p(this.env.getNames().holderClassName(this.port, javaParameter.getType()) + " " + javaParameter.getName());
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  590 */           p.p(javaParameter.getHolderName() + " " + javaParameter.getName());
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  596 */         AbstractType paramType = javaParameter.getParameter().getType();
/*      */         
/*  598 */         if (paramType instanceof LiteralArrayWrapperType && operation.getStyle().equals(SOAPStyle.RPC)) {
/*      */           
/*  600 */           p.p(((LiteralArrayWrapperType)paramType).getJavaArrayType().getName() + " " + javaParameter.getName());
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  607 */           p.p(javaParameter.getType().getName() + " " + javaParameter.getName());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  614 */     p.plnI(")");
/*  615 */     iterator = javaMethod.getExceptions();
/*  616 */     if (iterator.hasNext()) {
/*  617 */       p.p("throws ");
/*  618 */       for (i = 0; iterator.hasNext(); i++) {
/*  619 */         if (i > 0)
/*  620 */           p.p(", "); 
/*  621 */         p.p((String)iterator.next());
/*      */       } 
/*  623 */       p.p(", ");
/*      */     } else {
/*  625 */       p.p("throws ");
/*      */     } 
/*  627 */     p.p("java.rmi.RemoteException");
/*  628 */     p.pln(" {");
/*  629 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeOperationCatchBlock(IndentingWriter p, Operation operation) throws IOException {
/*  636 */     Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*  637 */     faultSet.addAll(operation.getFaultsSet());
/*  638 */     Iterator<Fault> faults = faultSet.iterator();
/*      */     
/*  640 */     boolean hasIOException = false;
/*  641 */     if (faults != null)
/*      */     {
/*  643 */       while (faults.hasNext()) {
/*  644 */         Fault fault = faults.next();
/*  645 */         if (this.env.getNames().customExceptionClassName(fault).equals("java.io.IOException")) {
/*      */ 
/*      */ 
/*      */           
/*  649 */           hasIOException = true; continue;
/*      */         } 
/*  651 */         p.plnI("} catch (" + this.env.getNames().customExceptionClassName(fault) + " e) {");
/*      */ 
/*      */ 
/*      */         
/*  655 */         p.pln("throw e;");
/*  656 */         p.pO();
/*      */       } 
/*      */     }
/*      */     
/*  660 */     p.plnI("} catch (RemoteException e) {");
/*      */     
/*  662 */     Iterator<Fault> faultsIter = faultSet.iterator();
/*  663 */     if (faultsIter.hasNext()) {
/*  664 */       p.plnI("if (e.detail instanceof com.sun.xml.rpc.util.HeaderFaultException) {");
/*      */       
/*  666 */       p.pln("com.sun.xml.rpc.util.HeaderFaultException hfe = (com.sun.xml.rpc.util.HeaderFaultException) e.detail;");
/*      */       
/*  668 */       p.pln("SOAPHeaderBlockInfo headerBlock = (SOAPHeaderBlockInfo) hfe.getObject();");
/*      */       
/*  670 */       p.pln("java.lang.Object obj = headerBlock.getValue();");
/*  671 */       while (faultsIter.hasNext()) {
/*  672 */         Fault fault = faultsIter.next();
/*  673 */         if (fault instanceof com.sun.xml.rpc.processor.model.HeaderFault) {
/*  674 */           p.plnI("if (obj instanceof " + this.env.getNames().customExceptionClassName(fault) + ") {");
/*      */ 
/*      */ 
/*      */           
/*  678 */           p.pln("throw (" + this.env.getNames().customExceptionClassName(fault) + ") obj;");
/*      */ 
/*      */ 
/*      */           
/*  682 */           p.pOln("}");
/*      */         } 
/*      */       } 
/*  685 */       p.pOln("}");
/*      */     } 
/*  687 */     p.pln("// let this one through unchanged");
/*  688 */     p.pln("throw e;");
/*  689 */     if (hasIOException) {
/*  690 */       p.pOlnI("} catch (java.io.IOException e) {");
/*  691 */       p.pln("throw e;");
/*      */     } 
/*  693 */     p.pOlnI("} catch (JAXRPCException e) {");
/*  694 */     p.pln("throw new RemoteException(e.getMessage(), e);");
/*  695 */     p.pOlnI("} catch (Exception e) {");
/*  696 */     p.plnI("if (e instanceof RuntimeException) {");
/*  697 */     p.pln("throw (RuntimeException)e;");
/*  698 */     p.pOlnI("} else {");
/*  699 */     p.pln("throw new RemoteException(e.getMessage(), e);");
/*  700 */     p.pOln("}");
/*  701 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeRpcLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {
/*  710 */     JavaMethod javaMethod = operation.getJavaMethod();
/*  711 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  719 */     declareOperationMethod(p, operation);
/*      */ 
/*      */     
/*  722 */     Iterator<JavaParameter> iterator = javaMethod.getParameters();
/*  723 */     for (int i = 0; iterator.hasNext(); i++) {
/*  724 */       JavaParameter javaParameter = iterator.next();
/*  725 */       if (javaParameter.isHolder()) {
/*  726 */         p.plnI("if (" + javaParameter.getName() + " == null) {");
/*  727 */         p.pln("throw new IllegalArgumentException(\"" + javaParameter.getName() + " cannot be null\");");
/*      */ 
/*      */ 
/*      */         
/*  731 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */     
/*  735 */     p.plnI("try {");
/*  736 */     Request request = operation.getRequest();
/*  737 */     Block block = null;
/*  738 */     iterator = request.getBodyBlocks();
/*  739 */     if (iterator.hasNext()) {
/*  740 */       block = (Block)iterator.next();
/*      */     }
/*  742 */     p.pln();
/*  743 */     p.pln("StreamingSenderState _state = _start(_handlerChain);");
/*  744 */     p.pln();
/*  745 */     p.pln("InternalSOAPMessage _request = _state.getRequest();");
/*  746 */     p.pln("_request.setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */ 
/*      */ 
/*      */     
/*  750 */     p.pln();
/*      */     
/*  752 */     LiteralType type = (block == null) ? null : (LiteralType)block.getType();
/*      */     
/*  754 */     String objType = (type == null) ? null : type.getJavaType().getName();
/*  755 */     String objName = (type == null) ? null : ("_" + this.env.getNames().getTypeMemberName(type.getJavaType()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     boolean declaredHeaderBlockInfo = false;
/*  761 */     for (iterator = request.getParameters(); iterator.hasNext(); ) {
/*  762 */       Parameter parameter = (Parameter)iterator.next();
/*  763 */       Block paramBlock = parameter.getBlock();
/*  764 */       if (paramBlock.getLocation() == 1) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  772 */       if (paramBlock.getLocation() == 2) {
/*      */         
/*  774 */         if (!declaredHeaderBlockInfo) {
/*  775 */           p.pln("SOAPHeaderBlockInfo _headerInfo;");
/*  776 */           declaredHeaderBlockInfo = true;
/*      */         } 
/*  778 */         JavaParameter javaParameter = parameter.getJavaParameter();
/*  779 */         String qname = this.env.getNames().getBlockQNameName(null, paramBlock);
/*      */         
/*  781 */         String memberName = parameter.getName();
/*  782 */         String varName = null;
/*      */ 
/*      */         
/*  785 */         if (parameter.getLinkedParameter() != null || (javaParameter != null && javaParameter.isHolder())) {
/*      */           
/*  787 */           varName = memberName + ".value";
/*      */         } else {
/*  789 */           varName = memberName;
/*      */         } 
/*      */         
/*  792 */         String serializer = this.writerFactory.createWriter(this.servicePackage, paramBlock.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  798 */         p.pln("_headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */         
/*  802 */         p.pln("_headerInfo.setValue(" + varName + ");");
/*  803 */         p.pln("_headerInfo.setSerializer(" + serializer + ");");
/*  804 */         p.pln("_request.add(_headerInfo);");
/*      */       } 
/*      */     } 
/*  807 */     addAttachmentsToRequest(p, request.getParameters());
/*      */ 
/*      */     
/*  810 */     if (objName != null && objType != null) {
/*  811 */       p.pln(objType + " " + objName + " = new " + objType + "();");
/*      */     }
/*      */     
/*  814 */     iterator = request.getParameters();
/*  815 */     while (iterator.hasNext()) {
/*  816 */       Parameter parameter = (Parameter)iterator.next();
/*  817 */       Block paramBlock = parameter.getBlock();
/*  818 */       if (paramBlock.getLocation() == 1)
/*      */       {
/*  820 */         if (parameter.isEmbedded()) {
/*  821 */           JavaStructureMember javaMember = getJavaMember(parameter);
/*  822 */           if (parameter.getJavaParameter() != null) {
/*  823 */             String memberName; if (parameter.getJavaParameter().isHolder()) {
/*  824 */               memberName = parameter.getName() + ".value";
/*      */             } else {
/*  826 */               memberName = parameter.getName();
/*      */             } 
/*      */             
/*  829 */             if (parameter.getType() instanceof LiteralArrayWrapperType)
/*      */             {
/*  831 */               memberName = "new " + ((LiteralArrayWrapperType)parameter.getType()).getJavaType().getName() + "(" + memberName + ")";
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  842 */             if (javaMember.isPublic()) {
/*  843 */               p.pln(objName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */             
/*  851 */             p.pln(objName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
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
/*      */     
/*  863 */     p.pln();
/*  864 */     iterator = request.getBodyBlocks();
/*  865 */     if (iterator.hasNext()) {
/*  866 */       Block paramBlock = (Block)iterator.next();
/*  867 */       p.pln("SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, block) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  873 */       String valueStr = objName;
/*  874 */       if (SimpleToBoxedUtil.isPrimitive(objType)) {
/*  875 */         valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, objType);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  880 */       p.pln("_bodyBlock.setValue(" + valueStr + ");");
/*      */       
/*  882 */       String serializer = this.writerFactory.createWriter(this.servicePackage, paramBlock.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  888 */       p.pln("_bodyBlock.setSerializer(" + serializer + ");");
/*  889 */       p.pln("_request.setBody(_bodyBlock);");
/*  890 */       p.pln();
/*      */     } else {
/*  892 */       p.pln("SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(null);");
/*  893 */       p.pln("_bodyBlock.setSerializer(DummySerializer.getInstance());");
/*  894 */       p.pln("_request.setBody(_bodyBlock);");
/*  895 */       p.pln();
/*      */     } 
/*      */     
/*  898 */     String soapAction = (operation.getSOAPAction() != null) ? operation.getSOAPAction() : "";
/*      */     
/*  900 */     p.pln("_state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, \"" + soapAction + "\");");
/*      */ 
/*      */ 
/*      */     
/*  904 */     p.pln();
/*  905 */     if (operation.getResponse() != null) {
/*  906 */       p.pln("_send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } else {
/*      */       
/*  909 */       p.pln("_sendOneWay((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } 
/*      */     
/*  912 */     p.pln();
/*      */ 
/*      */     
/*  915 */     Response response = operation.getResponse();
/*  916 */     if (response != null) {
/*  917 */       iterator = response.getBodyBlocks();
/*  918 */       objName = null;
/*  919 */       objType = null;
/*  920 */       block = null;
/*  921 */       while (iterator.hasNext()) {
/*  922 */         block = (Block)iterator.next();
/*  923 */         if (block.getName().getLocalPart().equals(this.env.getNames().getResponseName(operation.getName().getLocalPart()))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  929 */           type = (LiteralType)block.getType();
/*  930 */           objType = type.getJavaType().getName();
/*  931 */           objName = "_" + this.env.getNames().getTypeMemberName(type.getJavaType());
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  938 */       boolean hasReturn = (resultType != null && !resultType.getName().equals("void"));
/*      */ 
/*      */ 
/*      */       
/*  942 */       String initString = (type == null) ? "null" : type.getJavaType().getInitString();
/*      */       
/*  944 */       p.pln(objType + " " + objName + " = " + initString + ";");
/*      */       
/*  946 */       String objMemberName = "_responseObj";
/*  947 */       p.pln("Object " + objMemberName + " = _state.getResponse().getBody().getValue();");
/*      */ 
/*      */ 
/*      */       
/*  951 */       p.plnI("if (" + objMemberName + " instanceof SOAPDeserializationState) {");
/*      */ 
/*      */ 
/*      */       
/*  955 */       p.plnI(objName + " =");
/*  956 */       p.pln("(" + objType + ")((SOAPDeserializationState)" + objMemberName + ").getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  962 */       p.pO();
/*  963 */       p.pOlnI("} else {");
/*  964 */       p.plnI(objName + " =");
/*  965 */       p.pln("(" + objType + ")" + objMemberName + ";");
/*  966 */       p.pO();
/*  967 */       p.pOln("}");
/*  968 */       p.pln();
/*  969 */       iterator = response.getParameters();
/*  970 */       while (iterator.hasNext()) {
/*  971 */         Parameter parameter = (Parameter)iterator.next();
/*  972 */         JavaParameter javaParameter = parameter.getJavaParameter();
/*  973 */         Block paramBlock = parameter.getBlock();
/*  974 */         if (javaParameter == null || !javaParameter.isHolder()) {
/*      */           continue;
/*      */         }
/*  977 */         if (paramBlock.getLocation() == 1) {
/*  978 */           JavaStructureMember javaMember = getJavaMember(parameter);
/*  979 */           p.plnI(javaParameter.getName() + ".value =");
/*  980 */           if (javaMember.isPublic()) {
/*  981 */             p.pln(objName + "." + javaMember.getName() + ";");
/*      */           
/*      */           }
/*  984 */           else if (javaMember.getType() instanceof JavaStructureType && ((JavaStructureType)javaMember.getType()).getOwner() instanceof LiteralArrayWrapperType) {
/*      */ 
/*      */ 
/*      */             
/*  988 */             LiteralArrayWrapperType owner = (LiteralArrayWrapperType)((JavaStructureType)javaMember.getType()).getOwner();
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  993 */             JavaStructureMember tmpMember = ((JavaStructureType)owner.getJavaType()).getMembers().next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  999 */             p.pln("(" + objName + "." + javaMember.getReadMethod() + "() != null) ?");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1005 */             p.pln(objName + "." + javaMember.getReadMethod() + "()." + tmpMember.getReadMethod() + "() : null;");
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/* 1013 */             p.pln(objName + "." + javaMember.getReadMethod() + "();");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1020 */           p.pO();
/*      */         } 
/*      */       } 
/* 1023 */       boolean hasResponseHeaders = false;
/* 1024 */       iterator = operation.getResponse().getHeaderBlocks();
/* 1025 */       hasResponseHeaders = iterator.hasNext();
/* 1026 */       iterator = operation.getResponse().getParameters();
/* 1027 */       if (hasResponseHeaders && iterator.hasNext()) {
/* 1028 */         p.pln("java.util.Iterator _headers = _state.getResponse().headers();");
/* 1029 */         p.pln("SOAPHeaderBlockInfo _curHeader;");
/* 1030 */         p.pln("java.lang.Object _headerObj;");
/* 1031 */         p.plnI("while (_headers.hasNext()) {");
/* 1032 */         p.pln("_curHeader = (SOAPHeaderBlockInfo)_headers.next();");
/* 1033 */         boolean startedHeaders = false;
/* 1034 */         while (iterator.hasNext()) {
/* 1035 */           Parameter parameter = (Parameter)iterator.next();
/* 1036 */           if (parameter.getBlock().getLocation() == 2) {
/* 1037 */             String varName; if (startedHeaders) {
/* 1038 */               p.p(" else ");
/*      */             }
/* 1040 */             startedHeaders = true;
/* 1041 */             String paramName = parameter.getName();
/* 1042 */             String paramType = parameter.getType().getJavaType().getName();
/*      */             
/* 1044 */             String qname = this.env.getNames().getBlockQNameName(null, parameter.getBlock());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1049 */             if (parameter.getType().getJavaType().isHolder()) {
/* 1050 */               varName = paramName + ".value";
/*      */             } else {
/* 1052 */               varName = paramName;
/*      */             } 
/* 1054 */             p.plnI("if (_curHeader.getName().equals(" + qname + ")) {");
/*      */ 
/*      */ 
/*      */             
/* 1058 */             p.pln("_headerObj = _curHeader.getValue();");
/* 1059 */             p.plnI("if (_headerObj instanceof SOAPDeserializationState) {");
/*      */             
/* 1061 */             p.pln(paramName + ".value = (" + paramType + ")((SOAPDeserializationState)" + "_headerObj).getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1067 */             p.pOlnI("} else {");
/* 1068 */             p.pln(varName + " = (" + paramType + ")_headerObj;");
/* 1069 */             p.pOln("}");
/* 1070 */             p.pO("}");
/*      */           } 
/*      */         } 
/* 1073 */         if (startedHeaders)
/* 1074 */           p.pln(); 
/* 1075 */         p.pOln("}");
/*      */       } 
/* 1077 */       p.pln();
/* 1078 */       getAttachmentFromResponse(p, response.getParameters());
/* 1079 */       if (hasReturn) {
/* 1080 */         iterator = response.getParameters();
/* 1081 */         Parameter parameter = (Parameter)iterator.next();
/* 1082 */         if (parameter.getBlock() == block) {
/* 1083 */           JavaStructureMember javaMember = getJavaMember(parameter);
/* 1084 */           String unWrapMethod = "";
/*      */           
/* 1086 */           if (parameter.getType() instanceof LiteralArrayWrapperType)
/*      */           {
/* 1088 */             unWrapMethod = ".toArray()";
/*      */           }
/* 1090 */           if (javaMember.isPublic()) {
/* 1091 */             p.pln("return " + parameter.getName() + unWrapMethod + ";");
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/* 1097 */             p.pln("return " + objName + "." + javaMember.getReadMethod() + "()" + unWrapMethod + ";");
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1105 */         else if (parameter.getBlock().getLocation() == 3) {
/* 1106 */           p.pln("return " + parameter.getName() + ";");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1111 */       p.pO();
/*      */     } 
/* 1113 */     writeOperationCatchBlock(p, operation);
/*      */     
/* 1115 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeDocumentLiteralOperation(IndentingWriter p, String remoteClassName, Operation operation) throws IOException, GeneratorException {
/* 1124 */     JavaMethod javaMethod = operation.getJavaMethod();
/* 1125 */     JavaType resultType = javaMethod.getReturnType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1133 */     declareOperationMethod(p, operation);
/*      */ 
/*      */     
/* 1136 */     Iterator<JavaParameter> iterator = javaMethod.getParameters();
/* 1137 */     for (int i = 0; iterator.hasNext(); i++) {
/* 1138 */       JavaParameter javaParameter = iterator.next();
/* 1139 */       if (javaParameter.isHolder()) {
/* 1140 */         p.plnI("if (" + javaParameter.getName() + " == null) {");
/* 1141 */         p.pln("throw new IllegalArgumentException(\"" + javaParameter.getName() + " cannot be null\");");
/*      */ 
/*      */ 
/*      */         
/* 1145 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */     
/* 1149 */     p.plnI("try {");
/* 1150 */     Request request = operation.getRequest();
/* 1151 */     Block block = null;
/* 1152 */     iterator = request.getBodyBlocks();
/* 1153 */     if (iterator.hasNext()) {
/* 1154 */       block = (Block)iterator.next();
/*      */     }
/* 1156 */     p.pln();
/* 1157 */     p.pln("StreamingSenderState _state = _start(_handlerChain);");
/* 1158 */     p.pln();
/* 1159 */     p.pln("InternalSOAPMessage _request = _state.getRequest();");
/* 1160 */     p.pln("_request.setOperationCode(" + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ");");
/*      */ 
/*      */ 
/*      */     
/* 1164 */     p.pln();
/*      */     
/* 1166 */     LiteralType type = (block == null) ? null : (LiteralType)block.getType();
/*      */     
/* 1168 */     String objType = (type == null) ? null : type.getJavaType().getName();
/* 1169 */     String objName = (type == null) ? null : ("_" + this.env.getNames().getTypeMemberName(type.getJavaType()));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1174 */     int embeddedParameterCount = 0;
/* 1175 */     int nonEmbeddedParameterCount = 0;
/* 1176 */     boolean declaredHeaderBlockInfo = false;
/* 1177 */     boolean declaredAttachmentBlockInfo = false;
/* 1178 */     for (iterator = request.getParameters(); iterator.hasNext(); ) {
/* 1179 */       Parameter parameter = (Parameter)iterator.next();
/* 1180 */       Block paramBlock = parameter.getBlock();
/* 1181 */       if (paramBlock.getLocation() == 1) {
/* 1182 */         if (parameter.isEmbedded()) {
/* 1183 */           embeddedParameterCount++; continue;
/*      */         } 
/* 1185 */         objName = parameter.getJavaParameter().getName();
/* 1186 */         nonEmbeddedParameterCount++; continue;
/*      */       } 
/* 1188 */       if (paramBlock.getLocation() == 2) {
/*      */         
/* 1190 */         if (!declaredHeaderBlockInfo) {
/* 1191 */           p.pln("SOAPHeaderBlockInfo _headerInfo;");
/* 1192 */           declaredHeaderBlockInfo = true;
/*      */         } 
/* 1194 */         JavaParameter javaParameter = parameter.getJavaParameter();
/* 1195 */         String qname = this.env.getNames().getBlockQNameName(null, paramBlock);
/*      */         
/* 1197 */         String memberName = parameter.getName();
/* 1198 */         String varName = null;
/* 1199 */         if (javaParameter != null && javaParameter.isHolder()) {
/* 1200 */           varName = memberName + ".value";
/*      */         } else {
/* 1202 */           varName = memberName;
/*      */         } 
/* 1204 */         String serializer = this.writerFactory.createWriter(this.servicePackage, paramBlock.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1210 */         p.pln("_headerInfo = new SOAPHeaderBlockInfo(" + qname + ", null, false);");
/*      */ 
/*      */ 
/*      */         
/* 1214 */         p.pln("_headerInfo.setValue(" + varName + ");");
/* 1215 */         p.pln("_headerInfo.setSerializer(" + serializer + ");");
/* 1216 */         p.pln("_request.add(_headerInfo);");
/*      */       } 
/*      */     } 
/*      */     
/* 1220 */     addAttachmentsToRequest(p, request.getParameters());
/*      */ 
/*      */     
/* 1223 */     if (nonEmbeddedParameterCount > 1 || (nonEmbeddedParameterCount > 0 && embeddedParameterCount > 0))
/*      */     {
/* 1225 */       throw new GeneratorException("generator.internal.error.should.not.happen", "stub.generator.002");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1230 */     if (embeddedParameterCount > 0 || (embeddedParameterCount == 0 && nonEmbeddedParameterCount == 0))
/*      */     {
/* 1232 */       if (objName != null && objType != null) {
/* 1233 */         p.pln(objType + " " + objName + " = new " + objType + "();");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 1238 */     iterator = request.getParameters();
/* 1239 */     while (iterator.hasNext()) {
/* 1240 */       Parameter parameter = (Parameter)iterator.next();
/* 1241 */       Block paramBlock = parameter.getBlock();
/* 1242 */       if (paramBlock.getLocation() == 1)
/*      */       {
/* 1244 */         if (parameter.isEmbedded()) {
/* 1245 */           JavaStructureMember javaMember = getJavaMember(parameter);
/* 1246 */           if (parameter.getJavaParameter() != null) {
/* 1247 */             String memberName = parameter.getJavaParameter().getName();
/* 1248 */             if (javaMember.isPublic()) {
/* 1249 */               p.pln(objName + "." + javaMember.getName() + " = " + memberName + ";");
/*      */ 
/*      */ 
/*      */               
/*      */               continue;
/*      */             } 
/*      */ 
/*      */             
/* 1257 */             p.pln(objName + "." + javaMember.getWriteMethod() + "(" + memberName + ");");
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
/*      */     
/* 1269 */     p.pln();
/* 1270 */     iterator = request.getBodyBlocks();
/* 1271 */     if (iterator.hasNext()) {
/* 1272 */       Block paramBlock = (Block)iterator.next();
/* 1273 */       p.pln("SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(" + this.env.getNames().getBlockQNameName(operation, block) + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1279 */       String valueStr = objName;
/* 1280 */       if (SimpleToBoxedUtil.isPrimitive(objType)) {
/* 1281 */         valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, objType);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1286 */       p.pln("_bodyBlock.setValue(" + valueStr + ");");
/*      */       
/* 1288 */       String serializer = this.writerFactory.createWriter(this.servicePackage, paramBlock.getType()).serializerMemberName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1294 */       p.pln("_bodyBlock.setSerializer(" + serializer + ");");
/* 1295 */       p.pln("_request.setBody(_bodyBlock);");
/* 1296 */       p.pln();
/*      */     } else {
/* 1298 */       p.pln("SOAPBlockInfo _bodyBlock = new SOAPBlockInfo(null);");
/* 1299 */       p.pln("_bodyBlock.setSerializer(DummySerializer.getInstance());");
/* 1300 */       p.pln("_request.setBody(_bodyBlock);");
/* 1301 */       p.pln();
/*      */     } 
/*      */     
/* 1304 */     String soapAction = (operation.getSOAPAction() != null) ? operation.getSOAPAction() : "";
/*      */     
/* 1306 */     p.pln("_state.getMessageContext().setProperty(HttpClientTransport.HTTP_SOAPACTION_PROPERTY, \"" + soapAction + "\");");
/*      */ 
/*      */ 
/*      */     
/* 1310 */     p.pln();
/* 1311 */     if (operation.getResponse() != null) {
/* 1312 */       p.pln("_send((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } else {
/*      */       
/* 1315 */       p.pln("_sendOneWay((java.lang.String) _getProperty(ENDPOINT_ADDRESS_PROPERTY), _state);");
/*      */     } 
/*      */     
/* 1318 */     p.pln();
/*      */     
/* 1320 */     Response response = operation.getResponse();
/* 1321 */     if (response != null) {
/* 1322 */       iterator = response.getBodyBlocks();
/* 1323 */       if (iterator.hasNext()) {
/* 1324 */         block = (Block)iterator.next();
/*      */       }
/* 1326 */       type = (block == null) ? null : (LiteralType)block.getType();
/*      */ 
/*      */       
/* 1329 */       boolean hasResponseHeaders = false;
/* 1330 */       iterator = operation.getResponse().getHeaderBlocks();
/* 1331 */       hasResponseHeaders = iterator.hasNext();
/* 1332 */       iterator = operation.getResponse().getParameters();
/* 1333 */       if (hasResponseHeaders && iterator.hasNext()) {
/* 1334 */         p.pln("Iterator _headers = _state.getResponse().headers();");
/* 1335 */         p.pln("SOAPHeaderBlockInfo _curHeader;");
/* 1336 */         p.pln("Object _headerObj;");
/* 1337 */         p.plnI("while (_headers.hasNext()) {");
/* 1338 */         p.pln("_curHeader = (SOAPHeaderBlockInfo)_headers.next();");
/* 1339 */         boolean startedHeaders = false;
/* 1340 */         while (iterator.hasNext()) {
/* 1341 */           Parameter parameter = (Parameter)iterator.next();
/* 1342 */           if (parameter.getBlock().getLocation() == 2) {
/* 1343 */             String varName; if (startedHeaders) {
/* 1344 */               p.p(" else ");
/*      */             }
/* 1346 */             startedHeaders = true;
/* 1347 */             String paramName = parameter.getName();
/* 1348 */             String paramType = parameter.getType().getJavaType().getName();
/*      */             
/* 1350 */             String qname = this.env.getNames().getBlockQNameName(null, parameter.getBlock());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1355 */             if (parameter.getType().getJavaType().isHolder()) {
/* 1356 */               varName = paramName + ".value";
/*      */             } else {
/* 1358 */               varName = paramName;
/*      */             } 
/* 1360 */             p.plnI("if (_curHeader.getName().equals(" + qname + ")) {");
/*      */ 
/*      */ 
/*      */             
/* 1364 */             p.pln("_headerObj = _curHeader.getValue();");
/* 1365 */             p.plnI("if (_headerObj instanceof SOAPDeserializationState) {");
/*      */             
/* 1367 */             p.pln(paramName + ".value = (" + paramType + ")((SOAPDeserializationState)" + "_headerObj).getInstance();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1373 */             p.pOlnI("} else {");
/* 1374 */             p.pln(varName + " = (" + paramType + ")_headerObj;");
/* 1375 */             p.pOln("}");
/* 1376 */             p.pO("}");
/*      */           } 
/*      */         } 
/* 1379 */         if (startedHeaders)
/* 1380 */           p.pln(); 
/* 1381 */         p.pOln("}");
/*      */       } 
/*      */ 
/*      */       
/* 1385 */       iterator = response.getParameters();
/* 1386 */       while (iterator.hasNext()) {
/* 1387 */         Parameter parameter = (Parameter)iterator.next();
/* 1388 */         if (parameter.getBlock().getLocation() == 1) {
/* 1389 */           if (parameter.isEmbedded()) {
/* 1390 */             objName = "_result";
/* 1391 */             objType = type.getJavaType().getName();
/*      */           } else {
/* 1393 */             objName = "_result";
/* 1394 */             objType = parameter.getType().getJavaType().getName();
/*      */           } 
/*      */ 
/*      */           
/* 1398 */           String initString = (type == null) ? "null" : type.getJavaType().getInitString();
/*      */ 
/*      */ 
/*      */           
/* 1402 */           p.pln(objType + " " + objName + " = " + initString + ";");
/* 1403 */           p.pln("java.lang.Object _responseObj = _state.getResponse().getBody().getValue();");
/*      */           
/* 1405 */           p.plnI("if (_responseObj instanceof SOAPDeserializationState) {");
/*      */           
/* 1407 */           String valueStr = "((SOAPDeserializationState) _responseObj).getInstance()";
/*      */           
/* 1409 */           if (SimpleToBoxedUtil.isPrimitive(objType)) {
/* 1410 */             String boxName = SimpleToBoxedUtil.getBoxedClassName(objType);
/*      */             
/* 1412 */             valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + valueStr, objType);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1417 */             valueStr = "(" + objType + ")" + valueStr;
/*      */           } 
/* 1419 */           p.pln(objName + " = " + valueStr + ";");
/* 1420 */           p.pOlnI("} else {");
/* 1421 */           valueStr = "_responseObj";
/* 1422 */           if (SimpleToBoxedUtil.isPrimitive(objType)) {
/* 1423 */             String boxName = SimpleToBoxedUtil.getBoxedClassName(objType);
/*      */             
/* 1425 */             valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + valueStr, objType);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1430 */             valueStr = "(" + objType + ")" + valueStr;
/*      */           } 
/* 1432 */           p.pln(objName + " = " + valueStr + ";");
/* 1433 */           p.pOln("}");
/*      */           
/* 1435 */           p.pln();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1440 */       iterator = response.getParameters();
/* 1441 */       while (iterator.hasNext()) {
/* 1442 */         Parameter param = (Parameter)iterator.next();
/* 1443 */         JavaParameter javaParameter = param.getJavaParameter();
/* 1444 */         Block paramBlock = param.getBlock();
/* 1445 */         if (javaParameter == null || !javaParameter.isHolder()) {
/*      */           continue;
/*      */         }
/* 1448 */         if (paramBlock.getLocation() == 1) {
/* 1449 */           p.plnI(javaParameter.getName() + ".value = " + objName + ";");
/* 1450 */           p.pO();
/*      */         } 
/*      */       } 
/*      */       
/* 1454 */       getAttachmentFromResponse(p, response.getParameters());
/* 1455 */       iterator = response.getParameters();
/* 1456 */       while (iterator.hasNext()) {
/* 1457 */         Parameter parameter = (Parameter)iterator.next();
/* 1458 */         if (!resultType.getName().equals("void")) {
/* 1459 */           if (parameter.getBlock().getLocation() == 1) {
/* 1460 */             if (parameter.isEmbedded()) {
/* 1461 */               JavaStructureMember javaMember = getJavaMember(parameter);
/* 1462 */               if (javaMember.isPublic()) {
/* 1463 */                 p.pln("return " + objName + "." + parameter.getName() + ";");
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */ 
/*      */               
/* 1470 */               p.pln("return " + objName + "." + javaMember.getReadMethod() + "();");
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */ 
/*      */             
/* 1478 */             p.pln("return " + objName + ";");
/*      */             break;
/*      */           } 
/* 1481 */           if (parameter.getBlock().getLocation() == 3) {
/* 1482 */             p.pln("return " + parameter.getName() + ";");
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/* 1488 */       p.pln();
/* 1489 */       p.pO();
/*      */     } 
/*      */     
/* 1492 */     writeOperationCatchBlock(p, operation);
/*      */     
/* 1494 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getAttachmentFromResponse(IndentingWriter p, Iterator<Parameter> params) throws IOException {
/* 1502 */     boolean mimeTypesDeclared = false;
/* 1503 */     boolean isDataHandler = false;
/* 1504 */     while (params.hasNext()) {
/* 1505 */       Parameter parameter = params.next();
/* 1506 */       if (parameter.getBlock().getLocation() == 3) {
/* 1507 */         if (!mimeTypesDeclared) {
/* 1508 */           p.pln("java.lang.String[] mimeTypes = null;");
/* 1509 */           mimeTypesDeclared = true;
/*      */         } 
/* 1511 */         String paramName = parameter.getName();
/* 1512 */         JavaParameter javaParam = parameter.getJavaParameter();
/*      */         
/* 1514 */         if (javaParam != null && javaParam.isHolder()) {
/* 1515 */           paramName = paramName + ".value";
/*      */         } else {
/* 1517 */           paramName = parameter.getType().getJavaType().getName() + " " + paramName;
/*      */         } 
/* 1519 */         LiteralAttachmentType attType = null;
/* 1520 */         AbstractType pType = parameter.getType();
/* 1521 */         if (pType instanceof LiteralAttachmentType) {
/* 1522 */           attType = (LiteralAttachmentType)pType;
/* 1523 */           int index = attType.getContentID().indexOf('@');
/* 1524 */           String cId = attType.getContentID().substring(index + 1);
/* 1525 */           if (attType.getJavaType().getRealName().equals("javax.activation.DataHandler")) {
/* 1526 */             isDataHandler = true;
/*      */           } else {
/* 1528 */             isDataHandler = false;
/*      */           } 
/* 1530 */           List mimeList = attType.getAlternateMIMETypes();
/* 1531 */           p.pln("mimeTypes = new java.lang.String[" + mimeList.size() + "];");
/*      */           
/* 1533 */           int i = 0;
/* 1534 */           for (Iterator<String> iter = mimeList.iterator(); iter.hasNext(); i++) {
/* 1535 */             p.pln("mimeTypes[" + i + "] = new java.lang.String(\"" + (String)iter.next() + "\");");
/*      */           }
/*      */           
/* 1538 */           String typeName = parameter.getType().getJavaType().getName();
/* 1539 */           p.pln(paramName + "= (" + typeName + ")getAttachment(_state.getResponse().getMessage(), mimeTypes, \"" + cId + "\", " + String.valueOf(isDataHandler) + ");");
/*      */           
/* 1541 */           setGetAttachmentMethodFlag(true);
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
/*      */   private void addAttachmentsToRequest(IndentingWriter p, Iterator<Parameter> params) throws IOException {
/* 1553 */     while (params.hasNext()) {
/*      */       
/* 1555 */       String getUUIDMethod = null;
/* 1556 */       Parameter parameter = params.next();
/* 1557 */       Block responseBlock = parameter.getBlock();
/* 1558 */       if (responseBlock.getLocation() == 3) {
/* 1559 */         JavaParameter javaParameter = parameter.getJavaParameter();
/* 1560 */         String paramName = parameter.getName();
/* 1561 */         if (javaParameter.isHolder())
/* 1562 */           paramName = paramName + ".value"; 
/* 1563 */         String mimeType = null;
/* 1564 */         String contentID = null;
/* 1565 */         AbstractType pType = javaParameter.getParameter().getType();
/* 1566 */         if (pType instanceof LiteralAttachmentType) {
/* 1567 */           LiteralAttachmentType attType = (LiteralAttachmentType)pType;
/* 1568 */           if (attType.getJavaType().getRealName().equals("javax.activation.DataHandler")) {
/* 1569 */             mimeType = "((" + parameter.getType().getJavaType().getName() + ")" + paramName + ").getContentType()";
/*      */           } else {
/* 1571 */             mimeType = "\"" + attType.getMIMEType() + "\"";
/*      */           } 
/* 1573 */           contentID = attType.getContentID();
/*      */         } 
/* 1575 */         p.pln("addAttachment(_state.getRequest().getMessage(), (Object)" + paramName + ", " + mimeType + ", " + "\"" + contentID + "\");");
/* 1576 */         setAddAttachmentMethodFlag(true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeReadBodyFaultElement(IndentingWriter p) throws IOException {
/* 1583 */     boolean hasFaults = false;
/* 1584 */     Iterator<Operation> operationsIter = this.operations.iterator();
/*      */     
/* 1586 */     while (!hasFaults && operationsIter.hasNext()) {
/* 1587 */       Operation operation = operationsIter.next();
/* 1588 */       hasFaults = operation.getFaults().hasNext();
/*      */     } 
/* 1590 */     if (!hasFaults)
/*      */       return; 
/* 1592 */     p.pln("/*");
/* 1593 */     p.pln(" *  this method deserializes fault responses");
/* 1594 */     p.pln(" */");
/* 1595 */     p.plnI("protected Object _readBodyFaultElement(XMLReader bodyReader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {");
/*      */     
/* 1597 */     p.pln("Object faultInfo = null;");
/* 1598 */     p.pln("int opcode = state.getRequest().getOperationCode();");
/* 1599 */     p.plnI("switch (opcode) {");
/* 1600 */     operationsIter = this.operations.iterator();
/* 1601 */     while (operationsIter.hasNext()) {
/* 1602 */       Operation operation = operationsIter.next();
/*      */       
/* 1604 */       if (operation.getFaults().hasNext()) {
/* 1605 */         p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */         
/* 1609 */         p.pln("faultInfo = " + this.env.getNames().getClassMemberName(this.env.getNames().faultSerializerClassName(this.servicePackage, this.port, operation)) + ".deserialize(null, bodyReader, deserializationContext);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1617 */         p.pln("break;");
/* 1618 */         p.pO();
/*      */       } 
/*      */     } 
/* 1621 */     p.plnI("default:");
/* 1622 */     p.pln("return super._readBodyFaultElement(bodyReader, deserializationContext, state);");
/*      */     
/* 1624 */     p.pO();
/* 1625 */     p.pOln("}");
/* 1626 */     p.pln("return faultInfo;");
/* 1627 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeReadFirstBodyElementDefault(IndentingWriter p, String opCode) throws IOException {
/* 1634 */     p.pln("throw new SenderException(\"sender.response.unrecognizedOperation\", java.lang.Integer.toString(" + opCode + "));");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean needsReadFirstBodyElementFor(Operation operation) {
/* 1641 */     return (operation.getResponse() != null);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeHandleEmptyBody(IndentingWriter p, Operation operation) throws IOException {
/* 1646 */     p.pln("/*");
/* 1647 */     p.pln(" * This method handles the case of an empty SOAP body.");
/* 1648 */     p.pln(" */");
/* 1649 */     p.plnI("protected void _handleEmptyBody(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {");
/*      */     
/* 1651 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   public void writeGenericMethods(IndentingWriter p) throws IOException {
/* 1655 */     super.writeGenericMethods(p);
/*      */     
/* 1657 */     p.pln();
/* 1658 */     p.plnI("public java.lang.String _getEncodingStyle() {");
/* 1659 */     p.pln("return " + getEncodingStyle() + ";");
/* 1660 */     p.pOln("}");
/* 1661 */     p.pln();
/* 1662 */     p.plnI("public void _setEncodingStyle(java.lang.String encodingStyle) {");
/* 1663 */     p.pln("throw new UnsupportedOperationException(\"cannot set encoding style\");");
/*      */     
/* 1665 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeUnderstoodHeadersMember(IndentingWriter p, Map headerMap) throws IOException {
/* 1673 */     p.p("private static final QName[] understoodHeaderNames = new QName[] { ");
/*      */     
/* 1675 */     boolean first = true;
/* 1676 */     Iterator<Operation> operationsIter = this.operations.iterator();
/* 1677 */     for (int i = 0; operationsIter.hasNext(); i++) {
/* 1678 */       Operation operation = operationsIter.next();
/* 1679 */       if (operation.getResponse() != null) {
/* 1680 */         Iterator<Block> blocks = operation.getResponse().getHeaderBlocks();
/* 1681 */         while (blocks.hasNext()) {
/* 1682 */           Block block = blocks.next();
/* 1683 */           String qname = this.env.getNames().getBlockQNameName(operation, block);
/*      */           
/* 1685 */           if (!first) {
/* 1686 */             p.p(", ");
/*      */           }
/* 1688 */           p.p(qname);
/* 1689 */           first = false;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1693 */     p.pln(" };");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePreSendingHookMethod(IndentingWriter p, List operations) throws IOException {
/* 1701 */     p.plnI("protected void _preSendingHook(StreamingSenderState state) throws Exception {");
/* 1702 */     p.pln("super._preSendingHook(state);");
/* 1703 */     p.plnI("switch (state.getRequest().getOperationCode()) {");
/* 1704 */     Iterator<Operation> operationsIter = operations.iterator();
/*      */     
/* 1706 */     while (operationsIter.hasNext()) {
/* 1707 */       Operation operation = operationsIter.next();
/* 1708 */       if (!needsReadFirstBodyElementFor(operation))
/*      */         continue; 
/* 1710 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/* 1714 */       p.pln("addNonExplicitAttachment(state);");
/* 1715 */       p.pln("break;");
/* 1716 */       p.pO();
/*      */     } 
/*      */     
/* 1719 */     p.pOln("}");
/* 1720 */     p.pOln("}");
/* 1721 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePostSendingHook(IndentingWriter p, List operations) throws IOException {
/* 1728 */     p.pln();
/* 1729 */     p.plnI("protected void _postSendingHook(StreamingSenderState state) throws Exception {");
/* 1730 */     p.pln("super._postSendingHook(state);");
/* 1731 */     p.plnI("switch (state.getRequest().getOperationCode()) {");
/* 1732 */     Iterator<Operation> operationsIter = operations.iterator();
/*      */     
/* 1734 */     while (operationsIter.hasNext()) {
/* 1735 */       Operation operation = operationsIter.next();
/* 1736 */       if (!needsReadFirstBodyElementFor(operation))
/*      */         continue; 
/* 1738 */       p.plnI("case " + this.env.getNames().getOPCodeName(operation.getUniqueName()) + ":");
/*      */ 
/*      */ 
/*      */       
/* 1742 */       p.pln("getNonExplicitAttachment(state);");
/* 1743 */       p.pln("break;");
/* 1744 */       p.pO();
/*      */     } 
/*      */     
/* 1747 */     p.pOln("}");
/* 1748 */     p.pOln("}");
/* 1749 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeAddNonExplicitAttachment(IndentingWriter p) throws IOException {
/* 1756 */     p.pln();
/* 1757 */     p.plnI("private void addNonExplicitAttachment(StreamingSenderState state) throws Exception {");
/* 1758 */     p.pln("javax.xml.rpc.handler.soap.SOAPMessageContext smc = state.getMessageContext();");
/* 1759 */     p.pln("javax.xml.soap.SOAPMessage message = state.getRequest().getMessage();");
/* 1760 */     p.pln("Object c = _getProperty(StubPropertyConstants.SET_ATTACHMENT_PROPERTY);");
/* 1761 */     p.pln("_setProperty(StubPropertyConstants.SET_ATTACHMENT_PROPERTY, null);");
/* 1762 */     p.plnI("if(c != null && c instanceof java.util.Collection) {");
/* 1763 */     p.plnI("for(java.util.Iterator iter = ((java.util.Collection)c).iterator(); iter.hasNext();) {");
/* 1764 */     p.pln("Object attachment = iter.next();");
/* 1765 */     p.plnI("if(attachment instanceof javax.xml.soap.AttachmentPart) {");
/* 1766 */     p.pln("message.addAttachmentPart((javax.xml.soap.AttachmentPart)attachment);");
/* 1767 */     p.pOln("}");
/* 1768 */     p.pOln("}");
/* 1769 */     p.pOln("}");
/* 1770 */     p.pOln("}");
/* 1771 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeGetNonExplicitAttachment(IndentingWriter p) throws IOException {
/* 1778 */     p.pln();
/* 1779 */     p.plnI("private void getNonExplicitAttachment(StreamingSenderState state) throws Exception {");
/* 1780 */     p.pln("javax.xml.rpc.handler.soap.SOAPMessageContext smc = state.getMessageContext();");
/* 1781 */     p.pln("javax.xml.soap.SOAPMessage message = state.getResponse().getMessage();");
/* 1782 */     p.pln("java.util.ArrayList attachments = null;");
/* 1783 */     p.pln("java.util.Iterator iter = message.getAttachments();");
/* 1784 */     p.plnI("while(iter.hasNext()) {");
/* 1785 */     p.plnI("if(attachments == null) {");
/* 1786 */     p.pln("attachments = new java.util.ArrayList();");
/* 1787 */     p.pOln("}");
/* 1788 */     p.pln("attachments.add(iter.next());");
/* 1789 */     p.pOln("}");
/* 1790 */     p.pln("_setProperty(StubPropertyConstants.GET_ATTACHMENT_PROPERTY, attachments);");
/* 1791 */     p.pOln("}");
/* 1792 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeAttachmentHooks(IndentingWriter p) throws IOException {
/* 1799 */     boolean generateGetNonExplicitAttachmentMethod = false;
/* 1800 */     boolean generateAddNonExplicitAttachmentMethod = false;
/* 1801 */     List<Operation> reqOps = new ArrayList();
/* 1802 */     List<Operation> resOps = new ArrayList();
/* 1803 */     Iterator<Operation> iter = this.operations.iterator();
/* 1804 */     for (int i = 0; iter.hasNext(); i++) {
/* 1805 */       Operation operation = iter.next();
/* 1806 */       Request req = operation.getRequest();
/* 1807 */       if (req != null && req.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding") != null) {
/* 1808 */         if (!generateAddNonExplicitAttachmentMethod)
/* 1809 */           generateAddNonExplicitAttachmentMethod = true; 
/* 1810 */         reqOps.add(operation);
/*      */       } 
/*      */       
/* 1813 */       Response res = operation.getResponse();
/* 1814 */       if (res != null && res.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.mimeMultipartRelatedBinding") != null) {
/* 1815 */         if (!generateGetNonExplicitAttachmentMethod)
/* 1816 */           generateGetNonExplicitAttachmentMethod = true; 
/* 1817 */         resOps.add(operation);
/*      */       } 
/*      */     } 
/*      */     
/* 1821 */     if (generateAddNonExplicitAttachmentMethod) {
/* 1822 */       writePreSendingHookMethod(p, reqOps);
/* 1823 */       writeAddNonExplicitAttachment(p);
/*      */     } 
/*      */     
/* 1826 */     if (generateGetNonExplicitAttachmentMethod) {
/* 1827 */       writePostSendingHook(p, resOps);
/* 1828 */       writeGetNonExplicitAttachment(p);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void writeHooks(IndentingWriter p) throws IOException {
/* 1833 */     Iterator iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.stubHooks");
/*      */ 
/*      */     
/* 1836 */     if (iter != null && iter.hasNext()) {
/*      */       
/* 1838 */       writePreHandlingHook(p);
/* 1839 */       writePreRequestSendingHook(p);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void writePreHandlingHook(IndentingWriter p) throws IOException {
/* 1844 */     p.pln();
/* 1845 */     p.plnI("protected void _preHandlingHook(StreamingSenderState state) throws Exception {");
/*      */     
/* 1847 */     Iterator<StubHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.stubHooks");
/*      */ 
/*      */     
/* 1850 */     StubHooksIf.StubHooksState state = new StubHooksIf.StubHooksState();
/* 1851 */     state.superDone = false;
/* 1852 */     while (iter != null && iter.hasNext()) {
/* 1853 */       StubHooksIf plugin = iter.next();
/* 1854 */       plugin._preHandlingHook(this.model, p, state);
/*      */     } 
/* 1856 */     if (!state.superDone) {
/* 1857 */       p.pln("super._preHandlingHook(state);");
/*      */     }
/* 1859 */     p.pOln("}");
/* 1860 */     p.pln();
/*      */   }
/*      */   
/*      */   protected void writePreRequestSendingHook(IndentingWriter p) throws IOException {
/* 1864 */     p.pln();
/* 1865 */     p.plnI("protected boolean _preRequestSendingHook(StreamingSenderState state) throws Exception {");
/* 1866 */     p.pln("boolean bool = false;");
/*      */     
/* 1868 */     Iterator<StubHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.stubHooks");
/*      */ 
/*      */     
/* 1871 */     StubHooksIf.StubHooksState state = new StubHooksIf.StubHooksState();
/* 1872 */     state.superDone = false;
/* 1873 */     while (iter != null && iter.hasNext()) {
/* 1874 */       StubHooksIf plugin = iter.next();
/* 1875 */       plugin._preRequestSendingHook(this.model, p, state);
/*      */     } 
/* 1877 */     if (!state.superDone) {
/* 1878 */       p.pln("bool = super._preRequestSendingHook(state);");
/*      */     }
/* 1880 */     p.pln("return bool;");
/* 1881 */     p.pOln("}");
/* 1882 */     p.pln();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void writeStatic(IndentingWriter p) throws IOException {
/* 1887 */     Iterator<StubHooksIf> iter = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.stubHooks");
/*      */ 
/*      */     
/* 1890 */     while (iter != null && iter.hasNext()) {
/* 1891 */       StubHooksIf plugin = iter.next();
/* 1892 */       plugin.writeStubStatic(this.model, this.port, p);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Operation operationHasEmptyBody(Operation operation) {
/* 1900 */     if (operation.getResponse() != null && operation.getResponse().getBodyBlockCount() == 0)
/*      */     {
/* 1902 */       return operation;
/*      */     }
/* 1904 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\StubGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */