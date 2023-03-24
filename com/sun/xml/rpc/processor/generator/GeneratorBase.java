/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.ProcessorAction;
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriterFactory;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriterFactoryImpl;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.ModelVisitor;
/*      */ import com.sun.xml.rpc.processor.model.Operation;
/*      */ import com.sun.xml.rpc.processor.model.Parameter;
/*      */ import com.sun.xml.rpc.processor.model.Port;
/*      */ import com.sun.xml.rpc.processor.model.Request;
/*      */ import com.sun.xml.rpc.processor.model.Response;
/*      */ import com.sun.xml.rpc.processor.model.Service;
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
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAttributeMember;
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
/*      */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*      */ import com.sun.xml.rpc.util.localization.Localizable;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class GeneratorBase
/*      */   implements GeneratorConstants, ProcessorAction, ModelVisitor, SOAPTypeVisitor, LiteralTypeVisitor
/*      */ {
/*      */   protected File sourceDir;
/*      */   protected File destDir;
/*      */   protected File nonclassDestDir;
/*      */   protected ProcessorEnvironment env;
/*      */   protected Model model;
/*      */   protected Service service;
/*      */   protected IndentingWriter out;
/*      */   protected boolean encodeTypes;
/*      */   protected boolean multiRefEncoding;
/*      */   protected boolean serializeInterfaces;
/*      */   protected SerializerWriterFactory writerFactory;
/*      */   protected SOAPVersion curSOAPVersion;
/*      */   protected String JAXRPCVersion;
/*      */   protected String targetVersion;
/*      */   protected boolean generateSerializableIf;
/*      */   protected boolean donotOverride;
/*      */   protected String servicePackage;
/*      */   private LocalizableMessageFactory messageFactory;
/*      */   private Set visitedTypes;
/*      */   
/*      */   public GeneratorBase() {
/*  124 */     this.sourceDir = null;
/*  125 */     this.destDir = null;
/*  126 */     this.nonclassDestDir = null;
/*  127 */     this.env = null;
/*  128 */     this.model = null;
/*  129 */     this.out = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void perform(Model model, Configuration config, Properties properties) {
/*  136 */     ProcessorEnvironment env = (ProcessorEnvironment)config.getEnvironment();
/*      */     
/*  138 */     String key = "destinationDirectory";
/*  139 */     String dirPath = properties.getProperty(key);
/*  140 */     File destDir = new File(dirPath);
/*  141 */     key = "sourceDirectory";
/*  142 */     String sourcePath = properties.getProperty(key);
/*  143 */     File sourceDir = new File(sourcePath);
/*  144 */     key = "nonclassDestinationDirectory";
/*  145 */     String nonclassDestPath = properties.getProperty(key);
/*  146 */     File nonclassDestDir = new File(nonclassDestPath);
/*      */     
/*  148 */     GeneratorBase generator = getGenerator(model, config, properties);
/*      */     
/*  150 */     generator.doGeneration();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract GeneratorBase getGenerator(Model paramModel, Configuration paramConfiguration, Properties paramProperties);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public abstract GeneratorBase getGenerator(Model paramModel, Configuration paramConfiguration, Properties paramProperties, SOAPVersion paramSOAPVersion);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected GeneratorBase(Model model, Configuration config, Properties properties) {
/*  168 */     this.model = model;
/*  169 */     this.env = (ProcessorEnvironment)config.getEnvironment();
/*  170 */     String key = "destinationDirectory";
/*  171 */     String dirPath = properties.getProperty(key);
/*  172 */     this.destDir = new File(dirPath);
/*  173 */     key = "sourceDirectory";
/*  174 */     String sourcePath = properties.getProperty(key);
/*  175 */     this.sourceDir = new File(sourcePath);
/*  176 */     key = "nonclassDestinationDirectory";
/*  177 */     String nonclassDestPath = properties.getProperty(key);
/*  178 */     this.nonclassDestDir = new File(nonclassDestPath);
/*  179 */     key = "encodeTypes";
/*  180 */     this.encodeTypes = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*      */     
/*  182 */     key = "multiRefEncoding";
/*  183 */     this.multiRefEncoding = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*      */     
/*  185 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.generator");
/*      */     
/*  187 */     key = "serializerInterfaces";
/*  188 */     this.serializeInterfaces = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*      */     
/*  190 */     this.JAXRPCVersion = properties.getProperty("JAXRPC Version");
/*      */     
/*  192 */     this.targetVersion = properties.getProperty("sourceVersion");
/*      */     
/*  194 */     key = "serializable";
/*  195 */     this.generateSerializableIf = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*      */     
/*  197 */     key = "donotOverride";
/*  198 */     this.donotOverride = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doGeneration() {
/*      */     try {
/*  205 */       this.model.accept(this);
/*  206 */     } catch (Exception e) {
/*  207 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(Model model) throws Exception {
/*  214 */     this.visitedTypes = new HashSet();
/*  215 */     preVisitModel(model);
/*  216 */     visitModel(model);
/*  217 */     postVisitModel(model);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitModel(Model model) throws Exception {}
/*      */   
/*      */   protected void visitModel(Model model) throws Exception {
/*  224 */     this.env.getNames().resetPrefixFactory();
/*  225 */     this.writerFactory = (SerializerWriterFactory)new SerializerWriterFactoryImpl(this.env.getNames());
/*  226 */     Iterator<Service> services = model.getServices();
/*  227 */     while (services.hasNext()) {
/*  228 */       ((Service)services.next()).accept(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitModel(Model model) throws Exception {}
/*      */   
/*      */   public void visit(Service service) throws Exception {
/*  236 */     preVisitService(service);
/*  237 */     visitService(service);
/*  238 */     postVisitService(service);
/*      */   }
/*      */   
/*      */   protected void preVisitService(Service service) throws Exception {
/*  242 */     this.servicePackage = Names.getPackageName(service);
/*      */   }
/*      */   
/*      */   protected void visitService(Service service) throws Exception {
/*  246 */     this.service = service;
/*  247 */     Iterator<Port> ports = service.getPorts();
/*  248 */     while (ports.hasNext()) {
/*  249 */       ((Port)ports.next()).accept(this);
/*      */     }
/*  251 */     this.service = null;
/*      */   }
/*      */   
/*      */   protected void postVisitService(Service service) throws Exception {
/*  255 */     Iterator<AbstractType> extraTypes = this.model.getExtraTypes();
/*  256 */     while (extraTypes.hasNext()) {
/*  257 */       AbstractType type = extraTypes.next();
/*  258 */       if (type.isSOAPType()) {
/*  259 */         ((SOAPType)type).accept(this); continue;
/*  260 */       }  if (type.isLiteralType()) {
/*  261 */         ((LiteralType)type).accept(this);
/*      */       }
/*      */     } 
/*  264 */     this.servicePackage = null;
/*      */   }
/*      */   
/*      */   public void visit(Port port) throws Exception {
/*  268 */     preVisitPort(port);
/*  269 */     visitPort(port);
/*  270 */     postVisitPort(port);
/*      */   }
/*      */   
/*      */   protected void preVisitPort(Port port) throws Exception {
/*  274 */     this.curSOAPVersion = port.getSOAPVersion();
/*      */   }
/*      */   
/*      */   protected void visitPort(Port port) throws Exception {
/*  278 */     Iterator<Operation> operations = port.getOperations();
/*  279 */     while (operations.hasNext()) {
/*  280 */       ((Operation)operations.next()).accept(this);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void postVisitPort(Port port) throws Exception {
/*  285 */     this.curSOAPVersion = null;
/*      */   }
/*      */   
/*      */   public void visit(Operation operation) throws Exception {
/*  289 */     preVisitOperation(operation);
/*  290 */     visitOperation(operation);
/*  291 */     postVisitOperation(operation);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitOperation(Operation operation) throws Exception {}
/*      */   
/*      */   protected void visitOperation(Operation operation) throws Exception {
/*  298 */     operation.getRequest().accept(this);
/*  299 */     if (operation.getResponse() != null)
/*  300 */       operation.getResponse().accept(this); 
/*  301 */     Iterator<Fault> faults = operation.getAllFaults();
/*  302 */     if (faults != null)
/*      */     {
/*  304 */       while (faults.hasNext()) {
/*  305 */         Fault fault = faults.next();
/*  306 */         fault.accept(this);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitOperation(Operation operation) throws Exception {}
/*      */   
/*      */   public void visit(Parameter param) throws Exception {
/*  315 */     preVisitParameter(param);
/*  316 */     visitParameter(param);
/*  317 */     postVisitParameter(param);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitParameter(Parameter param) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitParameter(Parameter param) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitParameter(Parameter param) throws Exception {}
/*      */   
/*      */   public void visit(Block block) throws Exception {
/*  330 */     preVisitBlock(block);
/*  331 */     visitBlock(block);
/*  332 */     postVisitBlock(block);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitBlock(Block block) throws Exception {}
/*      */   
/*      */   public void visit(Response response) throws Exception {
/*  345 */     preVisitResponse(response);
/*  346 */     visitResponse(response);
/*  347 */     postVisitResponse(response);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitResponse(Response response) throws Exception {}
/*      */   
/*      */   protected void visitResponse(Response response) throws Exception {
/*  354 */     Iterator<Parameter> iter = response.getParameters();
/*      */ 
/*      */     
/*  357 */     while (iter.hasNext()) {
/*  358 */       ((Parameter)iter.next()).accept(this);
/*      */     }
/*  360 */     iter = response.getBodyBlocks();
/*  361 */     while (iter.hasNext()) {
/*  362 */       Block block = (Block)iter.next();
/*  363 */       AbstractType type = block.getType();
/*  364 */       if (type.isSOAPType()) {
/*  365 */         ((SOAPType)type).accept(this);
/*  366 */       } else if (type.isLiteralType()) {
/*  367 */         ((LiteralType)type).accept(this);
/*      */       } 
/*  369 */       responseBodyBlock(block);
/*      */     } 
/*  371 */     iter = response.getHeaderBlocks();
/*  372 */     while (iter.hasNext()) {
/*  373 */       Block block = (Block)iter.next();
/*  374 */       AbstractType type = block.getType();
/*  375 */       if (type.isSOAPType()) {
/*  376 */         ((SOAPType)type).accept(this);
/*  377 */       } else if (type.isLiteralType()) {
/*  378 */         ((LiteralType)type).accept(this);
/*      */       } 
/*  380 */       responseHeaderBlock(block);
/*      */     } 
/*      */ 
/*      */     
/*  384 */     iter = response.getAttachmentBlocks();
/*  385 */     while (iter.hasNext()) {
/*  386 */       Block block = (Block)iter.next();
/*  387 */       AbstractType type = block.getType();
/*  388 */       if (type.isSOAPType()) {
/*  389 */         ((SOAPType)type).accept(this);
/*  390 */       } else if (type.isLiteralType()) {
/*  391 */         ((LiteralType)type).accept(this);
/*      */       } 
/*  393 */       responseAttachmentBlock(block);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void responseBodyBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void responseHeaderBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void responseAttachmentBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitResponse(Response response) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(Request request) throws Exception {
/*  411 */     preVisitRequest(request);
/*  412 */     visitRequest(request);
/*  413 */     postVisitRequest(request);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitRequest(Request request) throws Exception {}
/*      */   
/*      */   protected void visitRequest(Request request) throws Exception {
/*  420 */     Iterator<Parameter> iter = request.getParameters();
/*      */ 
/*      */     
/*  423 */     while (iter.hasNext()) {
/*  424 */       ((Parameter)iter.next()).accept(this);
/*      */     }
/*  426 */     iter = request.getBodyBlocks();
/*  427 */     while (iter.hasNext()) {
/*  428 */       Block block = (Block)iter.next();
/*  429 */       AbstractType type = block.getType();
/*  430 */       if (type.isSOAPType()) {
/*  431 */         ((SOAPType)type).accept(this);
/*  432 */       } else if (type.isLiteralType()) {
/*  433 */         ((LiteralType)type).accept(this);
/*      */       } 
/*  435 */       requestBodyBlock(block);
/*      */     } 
/*  437 */     iter = request.getHeaderBlocks();
/*  438 */     while (iter.hasNext()) {
/*  439 */       Block block = (Block)iter.next();
/*  440 */       AbstractType type = block.getType();
/*  441 */       if (type.isSOAPType()) {
/*  442 */         ((SOAPType)type).accept(this);
/*  443 */       } else if (type.isLiteralType()) {
/*  444 */         ((LiteralType)type).accept(this);
/*      */       } 
/*  446 */       requestHeaderBlock(block);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void requestBodyBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void requestHeaderBlock(Block block) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitRequest(Request request) throws Exception {}
/*      */   
/*      */   public void visit(Fault fault) throws Exception {
/*  460 */     preVisitFault(fault);
/*  461 */     visitFault(fault);
/*  462 */     postVisitFault(fault);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitFault(Fault fault) throws Exception {}
/*      */   
/*      */   protected void visitFault(Fault fault) throws Exception {
/*  469 */     AbstractType type = fault.getBlock().getType();
/*  470 */     if (type.isSOAPType()) {
/*  471 */       ((SOAPType)type).accept(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitFault(Fault fault) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(SOAPCustomType type) throws Exception {
/*  480 */     preVisitSOAPCustomType(type);
/*  481 */     visitSOAPCustomType(type);
/*  482 */     postVisitSOAPCustomType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPCustomType(SOAPCustomType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitSOAPCustomType(SOAPCustomType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPCustomType(SOAPCustomType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(SOAPSimpleType type) throws Exception {
/*  497 */     preVisitSOAPSimpleType(type);
/*  498 */     visitSOAPSimpleType(type);
/*  499 */     postVisitSOAPSimpleType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPSimpleType(SOAPSimpleType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitSOAPSimpleType(SOAPSimpleType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPSimpleType(SOAPSimpleType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(SOAPAnyType type) throws Exception {
/*  514 */     preVisitSOAPAnyType(type);
/*  515 */     visitSOAPAnyType(type);
/*  516 */     postVisitSOAPAnyType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPAnyType(SOAPAnyType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitSOAPAnyType(SOAPAnyType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPAnyType(SOAPAnyType type) throws Exception {}
/*      */   
/*      */   public void visit(SOAPEnumerationType type) throws Exception {
/*  529 */     preVisitSOAPEnumerationType(type);
/*  530 */     visitSOAPEnumerationType(type);
/*  531 */     postVisitSOAPEnumerationType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(SOAPListType type) throws Exception {
/*  548 */     preVisitSOAPListType(type);
/*  549 */     visitSOAPListType(type);
/*  550 */     postVisitSOAPListType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postVisitSOAPListType(SOAPListType type) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void preVisitSOAPListType(SOAPListType type) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(SOAPArrayType type) throws Exception {
/*  570 */     preVisitSOAPArrayType(type);
/*  571 */     visitSOAPArrayType(type);
/*  572 */     postVisitSOAPArrayType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {}
/*      */   
/*      */   protected void visitSOAPArrayType(SOAPArrayType type) throws Exception {
/*  579 */     SOAPType elemType = type.getElementType();
/*  580 */     elemType.accept(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPArrayType(SOAPArrayType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(SOAPOrderedStructureType type) throws Exception {
/*  588 */     preVisitSOAPOrderedStructureType(type);
/*  589 */     visitSOAPOrderedStructureType(type);
/*  590 */     postVisitSOAPOrderedStructureType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPOrderedStructureType(SOAPOrderedStructureType type) throws Exception {
/*  595 */     preVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitSOAPOrderedStructureType(SOAPOrderedStructureType type) throws Exception {
/*  600 */     visit((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPOrderedStructureType(SOAPOrderedStructureType type) throws Exception {
/*  605 */     postVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */   
/*      */   public void visit(SOAPUnorderedStructureType type) throws Exception {
/*  609 */     preVisitSOAPUnorderedStructureType(type);
/*  610 */     visitSOAPUnorderedStructureType(type);
/*  611 */     postVisitSOAPUnorderedStructureType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPUnorderedStructureType(SOAPUnorderedStructureType type) throws Exception {
/*  616 */     preVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitSOAPUnorderedStructureType(SOAPUnorderedStructureType type) throws Exception {
/*  621 */     visit((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPUnorderedStructureType(SOAPUnorderedStructureType type) throws Exception {
/*  626 */     postVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */   
/*      */   public void visit(RPCRequestOrderedStructureType type) throws Exception {
/*  630 */     preVisitRPCRequestOrderedStructureType(type);
/*  631 */     visitRPCRequestOrderedStructureType(type);
/*  632 */     postVisitRPCRequestOrderedStructureType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitRPCRequestOrderedStructureType(RPCRequestOrderedStructureType type) throws Exception {
/*  637 */     preVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitRPCRequestOrderedStructureType(RPCRequestOrderedStructureType type) throws Exception {
/*  642 */     visit((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitRPCRequestOrderedStructureType(RPCRequestOrderedStructureType type) throws Exception {
/*  647 */     postVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */   
/*      */   public void visit(RPCRequestUnorderedStructureType type) throws Exception {
/*  651 */     preVisitRPCRequestUnorderedStructureType(type);
/*  652 */     visitRPCRequestUnorderedStructureType(type);
/*  653 */     postVisitRPCRequestUnorderedStructureType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitRPCRequestUnorderedStructureType(RPCRequestUnorderedStructureType type) throws Exception {
/*  658 */     preVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitRPCRequestUnorderedStructureType(RPCRequestUnorderedStructureType type) throws Exception {
/*  663 */     visit((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitRPCRequestUnorderedStructureType(RPCRequestUnorderedStructureType type) throws Exception {
/*  668 */     postVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */   
/*      */   public void visit(RPCResponseStructureType type) throws Exception {
/*  672 */     preVisitRPCResponseStructureType(type);
/*  673 */     visitRPCResponseStructureType(type);
/*  674 */     postVisitRPCResponseStructureType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitRPCResponseStructureType(RPCResponseStructureType type) throws Exception {
/*  679 */     preVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void visitRPCResponseStructureType(RPCResponseStructureType type) throws Exception {
/*  684 */     visit((SOAPStructureType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitRPCResponseStructureType(RPCResponseStructureType type) throws Exception {
/*  689 */     postVisitSOAPStructureType((SOAPStructureType)type);
/*      */   }
/*      */   
/*      */   public void visit(SOAPStructureType type) throws Exception {
/*  693 */     preVisitSOAPStructureType(type);
/*  694 */     visitSOAPStructureType(type);
/*  695 */     postVisitSOAPStructureType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitSOAPStructureType(SOAPStructureType type) throws Exception {
/*  704 */     if (!this.visitedTypes.contains(type)) {
/*  705 */       this.visitedTypes.add(type);
/*  706 */       if (type.getParentType() != null) {
/*  707 */         type.getParentType().accept(this);
/*      */       }
/*  709 */       Iterator<SOAPStructureMember> members = type.getMembers();
/*      */       
/*  711 */       while (members.hasNext()) {
/*  712 */         SOAPStructureMember member = members.next();
/*  713 */         member.getType().accept(this);
/*      */       } 
/*      */       
/*  716 */       Iterator<SOAPAttributeMember> attributes = type.getAttributeMembers();
/*      */       
/*  718 */       while (attributes.hasNext()) {
/*  719 */         SOAPAttributeMember attribute = attributes.next();
/*  720 */         attribute.getType().accept(this);
/*      */       } 
/*  722 */       Iterator<SOAPStructureType> subTypes = type.getSubtypes();
/*  723 */       while (subTypes != null && subTypes.hasNext())
/*      */       {
/*  725 */         ((SOAPStructureType)subTypes.next()).accept(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postVisitSOAPStructureType(SOAPStructureType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(LiteralListType type) throws Exception {
/*  737 */     preVisitLiteralListType(type);
/*  738 */     visitLiteralListType(type);
/*  739 */     postVisitLiteralListType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void postVisitLiteralListType(LiteralListType type) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitLiteralListType(LiteralListType type) throws Exception {
/*  756 */     LiteralType itemType = type.getItemType();
/*  757 */     itemType.accept(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitSOAPListType(SOAPListType type) throws Exception {
/*  766 */     SOAPType itemType = type.getItemType();
/*  767 */     itemType.accept(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void preVisitLiteralListType(LiteralListType type) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(LiteralIDType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(LiteralSimpleType type) throws Exception {
/*  782 */     preVisitLiteralSimpleType(type);
/*  783 */     visitLiteralSimpleType(type);
/*  784 */     postVisitLiteralSimpleType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralSimpleType(LiteralSimpleType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitLiteralSimpleType(LiteralSimpleType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralSimpleType(LiteralSimpleType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralSequenceType type) throws Exception {
/*  800 */     preVisitLiteralSequenceType(type);
/*  801 */     visitLiteralSequenceType(type);
/*  802 */     postVisitLiteralSequenceType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/*  811 */     if (!this.visitedTypes.contains(type)) {
/*  812 */       this.visitedTypes.add(type);
/*  813 */       if (type.getParentType() != null) {
/*  814 */         type.getParentType().accept(this);
/*      */       }
/*  816 */       Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/*      */       
/*  818 */       while (attributes.hasNext()) {
/*  819 */         LiteralAttributeMember attribute = attributes.next();
/*  820 */         attribute.getType().accept(this);
/*      */       } 
/*  822 */       Iterator<LiteralElementMember> elements = type.getElementMembers();
/*      */       
/*  824 */       while (elements.hasNext()) {
/*  825 */         LiteralElementMember element = elements.next();
/*  826 */         element.getType().accept(this);
/*      */       } 
/*  828 */       Iterator<LiteralStructuredType> subTypes = type.getSubtypes();
/*  829 */       while (subTypes != null && subTypes.hasNext())
/*      */       {
/*  831 */         ((LiteralStructuredType)subTypes.next()).accept(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralAllType type) throws Exception {
/*  841 */     preVisitLiteralAllType(type);
/*  842 */     visitLiteralAllType(type);
/*  843 */     postVisitLiteralAllType(type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralAllType(LiteralAllType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitLiteralAllType(LiteralAllType type) throws Exception {
/*  851 */     if (!this.visitedTypes.contains(type)) {
/*  852 */       this.visitedTypes.add(type);
/*  853 */       if (type.getParentType() != null) {
/*  854 */         type.getParentType().accept(this);
/*      */       }
/*  856 */       Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/*      */       
/*  858 */       while (attributes.hasNext()) {
/*  859 */         LiteralAttributeMember attribute = attributes.next();
/*  860 */         attribute.getType().accept(this);
/*      */       } 
/*  862 */       Iterator<LiteralElementMember> elements = type.getElementMembers();
/*      */       
/*  864 */       while (elements.hasNext()) {
/*  865 */         LiteralElementMember element = elements.next();
/*  866 */         element.getType().accept(this);
/*      */       } 
/*  868 */       Iterator<LiteralStructuredType> subTypes = type.getSubtypes();
/*  869 */       while (subTypes != null && subTypes.hasNext())
/*      */       {
/*  871 */         ((LiteralStructuredType)subTypes.next()).accept(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralAllType(LiteralAllType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralArrayType type) throws Exception {
/*  881 */     preVisitLiteralArrayType(type);
/*  882 */     visitLiteralArrayType(type);
/*  883 */     postVisitLiteralArrayType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralArrayType(LiteralArrayType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitLiteralArrayType(LiteralArrayType type) throws Exception {
/*  892 */     type.getElementType().accept(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralArrayType(LiteralArrayType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralArrayWrapperType type) throws Exception {
/*  900 */     preVisitLiteralArrayWrapperType(type);
/*  901 */     visitLiteralArrayWrapperType(type);
/*  902 */     postVisitLiteralArrayWrapperType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void visitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {
/*  911 */     type.getElementMember().getType().accept(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralFragmentType type) throws Exception {
/*  919 */     preVisitLiteralFragmentType(type);
/*  920 */     visitLiteralFragmentType(type);
/*  921 */     postVisitLiteralFragmentType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralFragmentType(LiteralFragmentType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitLiteralFragmentType(LiteralFragmentType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralFragmentType(LiteralFragmentType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visit(LiteralEnumerationType type) throws Exception {
/*  937 */     preVisitLiteralEnumerationType(type);
/*  938 */     visitLiteralEnumerationType(type);
/*  939 */     postVisitLiteralEnumerationType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void preVisitLiteralEnumerationType(LiteralEnumerationType type) throws Exception {}
/*      */ 
/*      */   
/*      */   public void visitLiteralEnumerationType(LiteralEnumerationType type) throws Exception {
/*  948 */     type.getBaseType().accept(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postVisitLiteralEnumerationType(LiteralEnumerationType type) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visit(LiteralAttachmentType type) throws Exception {
/*  960 */     preVisitLiteralAttachmentType(type);
/*  961 */     visitLiteralAttachmentType(type);
/*  962 */     postVisitLiteralAttachmentType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralAttachmentType(LiteralAttachmentType type) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void visitLiteralAttachmentType(LiteralAttachmentType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postVisitLiteralAttachmentType(LiteralAttachmentType type) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void writeWarning(IndentingWriter p) throws IOException {
/*  978 */     writeWarning(p, this.JAXRPCVersion, this.targetVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writeWarning(IndentingWriter p, String version, String targetVersion) throws IOException {
/*  986 */     p.pln("// This class was generated by the JAXRPC SI, do not edit.");
/*  987 */     p.pln("// Contents subject to change without notice.");
/*  988 */     p.pln("// " + version);
/*  989 */     p.pln("// Generated source version: " + targetVersion);
/*  990 */     p.pln();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void writePackage(IndentingWriter p, String classNameStr) throws IOException {
/*  996 */     writePackage(p, classNameStr, this.JAXRPCVersion, this.targetVersion);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writePackage(IndentingWriter p, String classNameStr, String version, String sourceVersion) throws IOException {
/* 1006 */     writeWarning(p, version, sourceVersion);
/* 1007 */     writePackageOnly(p, classNameStr);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writePackageOnly(IndentingWriter p, String classNameStr) throws IOException {
/* 1012 */     int idx = classNameStr.lastIndexOf(".");
/* 1013 */     if (idx > 0) {
/* 1014 */       p.pln("package " + classNameStr.substring(0, idx) + ";");
/* 1015 */       p.pln();
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void log(String msg) {
/* 1020 */     if (this.env.verbose()) {
/* 1021 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void warn(String key) {
/* 1031 */     this.env.warn(this.messageFactory.getMessage(key));
/*      */   }
/*      */   
/*      */   protected void warn(String key, String arg) {
/* 1035 */     this.env.warn(this.messageFactory.getMessage(key, arg));
/*      */   }
/*      */   
/*      */   protected void warn(String key, Object[] args) {
/* 1039 */     this.env.warn(this.messageFactory.getMessage(key, args));
/*      */   }
/*      */   
/*      */   protected void info(String key) {
/* 1043 */     this.env.info(this.messageFactory.getMessage(key));
/*      */   }
/*      */   
/*      */   protected void info(String key, String arg) {
/* 1047 */     this.env.info(this.messageFactory.getMessage(key, arg));
/*      */   }
/*      */   
/*      */   protected static void fail(String key) {
/* 1051 */     throw new GeneratorException(key);
/*      */   }
/*      */   
/*      */   protected static void fail(String key, String arg) {
/* 1055 */     throw new GeneratorException(key, arg);
/*      */   }
/*      */   
/*      */   protected static void fail(String key, String arg1, String arg2) {
/* 1059 */     throw new GeneratorException(key, new Object[] { arg1, arg2 });
/*      */   }
/*      */   
/*      */   protected static void fail(Localizable arg) {
/* 1063 */     throw new GeneratorException("generator.nestedGeneratorError", arg);
/*      */   }
/*      */   
/*      */   protected static void fail(Throwable arg) {
/* 1067 */     throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(arg));
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\GeneratorBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */