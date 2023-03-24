/*      */ package com.sun.xml.rpc.processor.modeler.rmi;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
/*      */ import com.sun.xml.rpc.processor.config.RmiInterfaceInfo;
/*      */ import com.sun.xml.rpc.processor.config.RmiModelInfo;
/*      */ import com.sun.xml.rpc.processor.config.TypeMappingRegistryInfo;
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
/*      */ import com.sun.xml.rpc.processor.model.Service;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*      */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.processor.util.StringUtils;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.util.ClassNameInfo;
/*      */ import com.sun.xml.rpc.util.VersionUtil;
/*      */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*      */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*      */ import java.lang.reflect.Method;
/*      */ import java.rmi.RemoteException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
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
/*      */ public class RmiModeler
/*      */   implements RmiConstants, Modeler
/*      */ {
/*      */   private final String modelName;
/*      */   private final String typeUri;
/*      */   private final String wsdlUri;
/*      */   private final RmiModelInfo modelInfo;
/*      */   private ProcessorEnvironment env;
/*      */   private Map structMap;
/*      */   private TypeMappingRegistryInfo typeMappingRegistry;
/*      */   private Map messageMap;
/*      */   private NamespaceMappingRegistryInfo namespaceMappingRegistry;
/*      */   private Model model;
/*  108 */   private static final Set excludedInterfaces = new HashSet();
/*      */   
/*  110 */   private Class remoteExceptionClass = null;
/*  111 */   private Class defHolder = null;
/*  112 */   private Class defRemote = null;
/*      */   
/*      */   private RmiTypeModeler rmiTypeModeler_11;
/*      */   
/*      */   private RmiTypeModeler rmiTypeModeler_12;
/*      */   private LiteralTypeModeler literalTypeModeler;
/*      */   private ExceptionModelerBase exceptionModeler_11;
/*      */   private ExceptionModelerBase exceptionModeler_12;
/*      */   private ExceptionModelerBase literalExceptionModeler;
/*      */   private boolean useDocLiteral = false;
/*      */   private boolean useWSIBasicProfile = false;
/*      */   private boolean useRPCLiteral = false;
/*      */   private boolean generateOneWayOperations = false;
/*      */   private boolean strictCompliance = false;
/*      */   private Properties options;
/*      */   private String sourceVersion;
/*      */   
/*      */   public RmiModeler(RmiModelInfo rmiModelInfo, Properties options) {
/*  130 */     this.modelInfo = rmiModelInfo;
/*  131 */     this.options = options;
/*  132 */     this.modelName = rmiModelInfo.getName();
/*  133 */     this.typeUri = rmiModelInfo.getTypeNamespaceURI();
/*  134 */     this.wsdlUri = rmiModelInfo.getTargetNamespaceURI();
/*  135 */     this.env = (ProcessorEnvironment)rmiModelInfo.getConfiguration().getEnvironment();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  140 */       this.remoteExceptionClass = RmiUtils.getClassForName(REMOTE_EXCEPTION_CLASSNAME, this.env.getClassLoader());
/*      */ 
/*      */ 
/*      */       
/*  144 */       this.defHolder = RmiUtils.getClassForName(HOLDER_CLASSNAME, this.env.getClassLoader());
/*      */ 
/*      */ 
/*      */       
/*  148 */       this.defRemote = RmiUtils.getClassForName(REMOTE_CLASSNAME, this.env.getClassLoader());
/*      */ 
/*      */     
/*      */     }
/*  152 */     catch (ClassNotFoundException e) {
/*  153 */       String className = REMOTE_CLASSNAME;
/*  154 */       if (this.remoteExceptionClass == null) {
/*  155 */         className = REMOTE_EXCEPTION_CLASSNAME;
/*  156 */       } else if (this.defHolder == null) {
/*  157 */         className = HOLDER_CLASSNAME;
/*      */       } 
/*  159 */       throw new ModelerException("rmimodeler.class.not.found", className);
/*      */     } 
/*  161 */     this.typeMappingRegistry = rmiModelInfo.getTypeMappingRegistry();
/*  162 */     this.namespaceMappingRegistry = rmiModelInfo.getNamespaceMappingRegistry();
/*  163 */     this.useDocLiteral = Boolean.valueOf(options.getProperty("useDocumentLiteralEncoding")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  169 */     this.useWSIBasicProfile = Boolean.valueOf(options.getProperty("useWSIBasicProfile")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  175 */     this.useRPCLiteral = Boolean.valueOf(options.getProperty("useRPCLiteralEncoding")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  181 */     this.generateOneWayOperations = Boolean.valueOf(options.getProperty("generateOneWayOperations")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  187 */     this.strictCompliance = Boolean.valueOf(options.getProperty("strictCompliance")).booleanValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  193 */     if (this.useDocLiteral && this.useRPCLiteral) {
/*  194 */       throw new ModelerException("rmimodeler.invalid.encoding", new LocalizableExceptionAdapter(new Exception("Both -f:docliteral and -f:rpcliteral specified.")));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  199 */     this.rmiTypeModeler_11 = new RmiTypeModeler(this, this.env, SOAPVersion.SOAP_11);
/*  200 */     this.rmiTypeModeler_12 = new RmiTypeModeler(this, this.env, SOAPVersion.SOAP_12);
/*  201 */     this.exceptionModeler_11 = getExceptionModeler(this.rmiTypeModeler_11);
/*  202 */     this.exceptionModeler_12 = new ExceptionModeler(this, this.rmiTypeModeler_12);
/*  203 */     this.literalTypeModeler = new LiteralTypeModeler(this, this.env);
/*  204 */     this.literalExceptionModeler = getLiteralExceptionModeler(this.literalTypeModeler);
/*  205 */     this.sourceVersion = options.getProperty("sourceVersion");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExceptionModelerBase getExceptionModeler(RmiTypeModeler rmiTypeModeler_11) {
/*  214 */     if (VersionUtil.isVersion101(this.options.getProperty("sourceVersion")))
/*      */     {
/*      */       
/*  217 */       return new ExceptionModeler101(this, rmiTypeModeler_11); } 
/*  218 */     if (VersionUtil.isVersion103(this.options.getProperty("sourceVersion")))
/*      */     {
/*      */       
/*  221 */       return new ExceptionModeler103(this, rmiTypeModeler_11); } 
/*  222 */     if (VersionUtil.isVersion11(this.options.getProperty("sourceVersion")))
/*      */     {
/*      */ 
/*      */       
/*  226 */       return new ExceptionModeler(this, rmiTypeModeler_11);
/*      */     }
/*      */ 
/*      */     
/*  230 */     return new ExceptionModeler(this, rmiTypeModeler_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ExceptionModelerBase getLiteralExceptionModeler(LiteralTypeModeler typeModeler) {
/*  239 */     String version = this.options.getProperty("sourceVersion");
/*  240 */     if (VersionUtil.isVersion11(version) || VersionUtil.isVersion111(version))
/*      */     {
/*  242 */       return new LiteralExceptionModeler111(this, typeModeler);
/*      */     }
/*      */     
/*  245 */     return new LiteralExceptionModeler(this, typeModeler);
/*      */   }
/*      */   
/*      */   public ProcessorEnvironment getProcessorEnvironment() {
/*  249 */     return this.env;
/*      */   }
/*      */   
/*      */   protected Properties getOptions() {
/*  253 */     return this.options;
/*      */   }
/*      */   
/*      */   public TypeMappingRegistryInfo getTypeMappingRegistryInfo() {
/*  257 */     return this.typeMappingRegistry;
/*      */   }
/*      */   
/*      */   public NamespaceMappingRegistryInfo getNamespaceMappingRegistryInfo() {
/*  261 */     return this.namespaceMappingRegistry;
/*      */   }
/*      */   
/*      */   public Class getDefHolder() {
/*  265 */     return this.defHolder;
/*      */   }
/*      */   
/*      */   public Model getModel() {
/*  269 */     return this.model;
/*      */   }
/*      */   
/*      */   public boolean isStrictCompliant() {
/*  273 */     return this.strictCompliance;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServicePackage() {
/*  278 */     String servicePackage = "";
/*  279 */     if (this.model != null) {
/*  280 */       Iterator<Service> services = getModel().getServices();
/*  281 */       Service service = null;
/*  282 */       while (services != null && services.hasNext())
/*  283 */         service = services.next(); 
/*  284 */       if (service != null)
/*  285 */         servicePackage = service.getJavaInterface().getName(); 
/*  286 */       int idx = servicePackage.lastIndexOf(".");
/*  287 */       if (idx > 0) {
/*  288 */         servicePackage = servicePackage.substring(0, idx);
/*      */       } else {
/*  290 */         servicePackage = "";
/*      */       } 
/*      */     } 
/*  293 */     return servicePackage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LiteralElementMember modelTypeLiteral(QName elemName, String typeUri, RmiType type) {
/*  301 */     return this.literalTypeModeler.modelTypeLiteral(elemName, typeUri, type);
/*      */   }
/*      */   
/*      */   public LiteralSimpleTypeCreator getLieralTypes() {
/*  305 */     return this.literalTypeModeler.getLiteralTypes();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addFaultParent(Fault fault, ExceptionModelerBase exceptionModeler) {
/*      */     try {
/*  313 */       Class javaClass = RmiUtils.getClassForName(fault.getJavaException().getRealName(), this.env.getClassLoader());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  318 */       javaClass = javaClass.getSuperclass();
/*  319 */       if (javaClass != null && !javaClass.getName().equals(EXCEPTION_CLASSNAME))
/*      */       {
/*  321 */         Fault parentFault = exceptionModeler.modelException(this.typeUri, this.wsdlUri, javaClass);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  326 */         parentFault.addSubfault(fault);
/*  327 */         parentFault.getJavaException().addSubclass((JavaStructureType)fault.getJavaException());
/*      */         
/*  329 */         if (parentFault.getJavaException().getOwner() instanceof SOAPStructureType) {
/*      */           
/*  331 */           ((SOAPStructureType)parentFault.getJavaException().getOwner()).addSubtype((SOAPStructureType)fault.getJavaException().getOwner());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  339 */         else if (parentFault.getJavaException().getOwner() instanceof LiteralStructuredType) {
/*      */ 
/*      */           
/*  342 */           ((LiteralStructuredType)parentFault.getJavaException().getOwner()).addSubtype((LiteralStructuredType)fault.getJavaException().getOwner());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  352 */         Block block = parentFault.getBlock();
/*  353 */         block.setType((AbstractType)parentFault.getJavaException().getOwner());
/*      */         
/*  355 */         addFaultParent(parentFault, exceptionModeler);
/*  356 */         if (parentFault.getJavaException().getOwner() instanceof SOAPStructureType)
/*      */         {
/*  358 */           markInheritedMembers((SOAPStructureType)fault.getJavaException().getOwner(), (SOAPStructureType)parentFault.getJavaException().getOwner());
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  363 */         else if (parentFault.getJavaException().getOwner() instanceof LiteralStructuredType)
/*      */         {
/*      */           
/*  366 */           markInheritedMembers((LiteralStructuredType)fault.getJavaException().getOwner(), (LiteralStructuredType)parentFault.getJavaException().getOwner());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*  375 */     catch (ClassNotFoundException e) {
/*  376 */       throw new ModelerException("rmimodeler.nestedRmiModelerError", new LocalizableExceptionAdapter(e));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void markInheritedMembers(SOAPStructureType type1, SOAPStructureType type2) {
/*  386 */     Iterator<SOAPStructureMember> members1 = type1.getMembers();
/*      */ 
/*      */     
/*  389 */     while (members1.hasNext()) {
/*  390 */       Iterator<SOAPStructureMember> members2 = type2.getMembers();
/*  391 */       SOAPStructureMember member1 = members1.next();
/*  392 */       while (members2.hasNext()) {
/*  393 */         SOAPStructureMember member2 = members2.next();
/*  394 */         if (membersMatch(member1, member2)) {
/*  395 */           member1.setInherited(true);
/*  396 */           member1.getJavaStructureMember().setInherited(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean membersMatch(SOAPStructureMember member1, SOAPStructureMember member2) {
/*  407 */     return (member1.getName().equals(member2.getName()) && member1.getType().equals(member2.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void markInheritedMembers(LiteralStructuredType type1, LiteralStructuredType type2) {
/*  416 */     Iterator<LiteralElementMember> members1 = type1.getElementMembers();
/*      */ 
/*      */     
/*  419 */     while (members1.hasNext()) {
/*  420 */       Iterator<LiteralElementMember> members2 = type2.getElementMembers();
/*  421 */       LiteralElementMember member1 = members1.next();
/*  422 */       while (members2.hasNext()) {
/*  423 */         LiteralElementMember member2 = members2.next();
/*  424 */         if (membersMatch(member1, member2)) {
/*  425 */           member1.setInherited(true);
/*  426 */           member1.getJavaStructureMember().setInherited(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  431 */     members1 = type1.getAttributeMembers();
/*      */     
/*  433 */     while (members1.hasNext()) {
/*  434 */       Iterator<LiteralAttributeMember> members2 = type2.getAttributeMembers();
/*  435 */       LiteralAttributeMember attr1 = (LiteralAttributeMember)members1.next();
/*  436 */       while (members2.hasNext()) {
/*  437 */         LiteralAttributeMember attr2 = members2.next();
/*  438 */         if (membersMatch(attr1, attr2)) {
/*  439 */           attr1.setInherited(true);
/*  440 */           attr1.getJavaStructureMember().setInherited(true);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean membersMatch(LiteralElementMember member1, LiteralElementMember member2) {
/*  451 */     return (member1.getName().equals(member2.getName()) && member1.getType().equals(member2.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean membersMatch(LiteralAttributeMember member1, LiteralAttributeMember member2) {
/*  460 */     return (member1.getName().equals(member2.getName()) && member1.getType().equals(member2.getType()));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Model buildModel() {
/*  466 */     log("creating model: " + this.modelName);
/*  467 */     this.model = new Model(new QName(null, this.modelName));
/*  468 */     this.model.setProperty("com.sun.xml.rpc.processor.model.ModelerName", getClass().getName());
/*      */ 
/*      */     
/*  471 */     this.model.setTargetNamespaceURI(this.wsdlUri);
/*      */     
/*  473 */     if (this.useDocLiteral || this.useWSIBasicProfile || this.useRPCLiteral) {
/*  474 */       return buildLiteralModel();
/*      */     }
/*  476 */     return buildEncodedModel();
/*      */   }
/*      */   
/*      */   private Model buildEncodedModel() {
/*      */     try {
/*      */       String serviceInterface;
/*  482 */       if (this.typeMappingRegistry != null) {
/*  483 */         Iterator<ImportedDocumentInfo> iter = this.typeMappingRegistry.getImportedDocuments();
/*  484 */         while (iter.hasNext())
/*      */         {
/*  486 */           this.model.addImportedDocument(iter.next());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  492 */         Iterator<String> iterator = this.typeMappingRegistry.getExtraTypeNames();
/*  493 */         while (iterator.hasNext()) {
/*      */           
/*  495 */           RmiType type = RmiType.getRmiType(RmiUtils.getClassForName(iterator.next(), this.env.getClassLoader()));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  500 */           if (isException(this.env, type)) {
/*  501 */             Fault fault = this.exceptionModeler_11.modelException(this.typeUri, this.wsdlUri, type.getTypeClass(this.env.getClassLoader()));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  506 */             addFaultParent(fault, this.exceptionModeler_11); continue;
/*      */           } 
/*  508 */           SOAPType extraType = this.rmiTypeModeler_11.modelTypeSOAP(this.typeUri, type);
/*      */           
/*  510 */           this.model.addExtraType((AbstractType)extraType);
/*      */         } 
/*      */       } 
/*      */       
/*  514 */       this.structMap = new HashMap<Object, Object>();
/*      */ 
/*      */       
/*  517 */       String javaServiceName = StringUtils.capitalize(this.modelInfo.getName());
/*      */       
/*  519 */       log("creating service: " + javaServiceName);
/*      */       
/*  521 */       if (this.modelInfo.getJavaPackageName() != null && !this.modelInfo.getJavaPackageName().equals("")) {
/*      */         
/*  523 */         serviceInterface = this.modelInfo.getJavaPackageName() + "." + javaServiceName;
/*      */       } else {
/*      */         
/*  526 */         serviceInterface = javaServiceName;
/*      */       } 
/*      */       
/*  529 */       Service service = new Service(new QName(this.wsdlUri, javaServiceName), new JavaInterface(serviceInterface, serviceInterface + "Impl"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  535 */       this.model.addService(service);
/*  536 */       Iterator<RmiInterfaceInfo> interfaces = this.modelInfo.getInterfaces();
/*      */       
/*  538 */       while (interfaces.hasNext()) {
/*  539 */         RmiInterfaceInfo interfaceInfo = interfaces.next();
/*  540 */         service.addPort(modelPort(interfaceInfo));
/*      */       } 
/*  542 */       this.rmiTypeModeler_11.modelSubclasses(this.typeUri);
/*      */       
/*  544 */       this.messageMap = null;
/*  545 */     } catch (ModelerException e) {
/*  546 */       throw e;
/*  547 */     } catch (JAXRPCExceptionBase e) {
/*  548 */       throw new ModelerException(e);
/*  549 */     } catch (Exception e) {
/*  550 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*      */     } 
/*  552 */     this.structMap = null;
/*  553 */     return this.model;
/*      */   }
/*      */   private Model buildLiteralModel() {
/*      */     try {
/*      */       String serviceInterface;
/*  558 */       if (this.typeMappingRegistry != null) {
/*  559 */         Iterator<ImportedDocumentInfo> iter = this.typeMappingRegistry.getImportedDocuments();
/*  560 */         while (iter.hasNext())
/*      */         {
/*  562 */           this.model.addImportedDocument(iter.next());
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  568 */         Iterator<String> iterator = this.typeMappingRegistry.getExtraTypeNames();
/*  569 */         while (iterator.hasNext()) {
/*      */           
/*  571 */           RmiType type = RmiType.getRmiType(RmiUtils.getClassForName(iterator.next(), this.env.getClassLoader()));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  576 */           if (isException(this.env, type)) {
/*  577 */             Fault fault = this.literalExceptionModeler.modelException(this.typeUri, this.wsdlUri, type.getTypeClass(this.env.getClassLoader()));
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  582 */             addFaultParent(fault, this.literalExceptionModeler); continue;
/*      */           } 
/*  584 */           QName elemName = new QName(type.getTypeSignature());
/*  585 */           LiteralElementMember member = this.literalTypeModeler.modelTypeLiteral(elemName, this.typeUri, type);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  590 */           this.model.addExtraType((AbstractType)member.getType());
/*      */         } 
/*      */       } 
/*      */       
/*  594 */       this.structMap = new HashMap<Object, Object>();
/*      */ 
/*      */       
/*  597 */       String javaServiceName = StringUtils.capitalize(this.modelInfo.getName());
/*      */       
/*  599 */       log("creating service: " + javaServiceName);
/*      */       
/*  601 */       if (this.modelInfo.getJavaPackageName() != null && !this.modelInfo.getJavaPackageName().equals("")) {
/*      */         
/*  603 */         serviceInterface = this.modelInfo.getJavaPackageName() + "." + javaServiceName;
/*      */       } else {
/*      */         
/*  606 */         serviceInterface = javaServiceName;
/*      */       } 
/*      */       
/*  609 */       Service service = new Service(new QName(this.wsdlUri, javaServiceName), new JavaInterface(serviceInterface, serviceInterface + "Impl"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  615 */       this.model.addService(service);
/*  616 */       Iterator<RmiInterfaceInfo> interfaces = this.modelInfo.getInterfaces();
/*      */       
/*  618 */       while (interfaces.hasNext()) {
/*  619 */         RmiInterfaceInfo interfaceInfo = interfaces.next();
/*  620 */         service.addPort(modelPort(interfaceInfo));
/*      */       } 
/*  622 */       this.literalTypeModeler.modelSubclasses(this.typeUri);
/*  623 */       if (this.useDocLiteral) {
/*  624 */         checkForDocLiteralNameClashes(this.model);
/*      */       }
/*  626 */       this.messageMap = null;
/*  627 */     } catch (ModelerException e) {
/*  628 */       throw e;
/*  629 */     } catch (JAXRPCExceptionBase e) {
/*  630 */       throw new ModelerException(e);
/*  631 */     } catch (Exception e) {
/*  632 */       throw new ModelerException(new LocalizableExceptionAdapter(e));
/*      */     } 
/*  634 */     this.structMap = null;
/*  635 */     return this.model;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkForDocLiteralNameClashes(Model model) throws ModelerException {
/*  641 */     Iterator<Service> services = model.getServices();
/*      */     
/*  643 */     while (services.hasNext()) {
/*  644 */       Iterator<Port> ports = ((Service)services.next()).getPorts();
/*  645 */       while (ports.hasNext()) {
/*  646 */         Iterator<Operation> operations = ((Port)ports.next()).getOperations();
/*  647 */         while (operations.hasNext()) {
/*  648 */           Operation operation = operations.next();
/*  649 */           checkForMessageNameClash((Message)operation.getRequest());
/*  650 */           checkForMessageNameClash((Message)operation.getResponse());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkForMessageNameClash(Message message) throws ModelerException {
/*  659 */     if (message != null) {
/*      */       
/*  661 */       Iterator<Block> bodyBlocks = message.getBodyBlocks();
/*  662 */       while (bodyBlocks.hasNext()) {
/*  663 */         Block block = bodyBlocks.next();
/*  664 */         if (this.literalTypeModeler.nameClashes(block.getType().getName().getLocalPart()))
/*      */         {
/*  666 */           throw new ModelerException("rmimodeler.operation.name.clashes.with.type.name", block.getType().getName().getLocalPart());
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isException(ProcessorEnvironment env, RmiType type) {
/*      */     try {
/*  676 */       if (type.getTypeCode() != 10)
/*  677 */         return false; 
/*  678 */       Class typeClass = type.getTypeClass(env.getClassLoader());
/*  679 */       while (typeClass != null) {
/*  680 */         if (typeClass.getName().equals(EXCEPTION_CLASSNAME))
/*  681 */           return true; 
/*  682 */         typeClass = typeClass.getSuperclass();
/*      */       } 
/*  684 */     } catch (Exception e) {
/*  685 */       e.printStackTrace();
/*      */     } 
/*  687 */     return false;
/*      */   }
/*      */   
/*      */   private Port modelPort(RmiInterfaceInfo interfaceInfo) {
/*  691 */     Port port = null;
/*      */     try {
/*  693 */       String implClassName = RmiUtils.getRealName(interfaceInfo.getName(), this.env.getClassLoader());
/*      */ 
/*      */ 
/*      */       
/*  697 */       Class def = RmiUtils.getClassForName(implClassName, this.env.getClassLoader());
/*      */       
/*  699 */       validateEndpointClass(def);
/*  700 */       port = processInterface(def, implClassName, interfaceInfo);
/*  701 */     } catch (ClassNotFoundException e) {
/*  702 */       throw new ModelerException("rmimodeler.nestedRmiModelerError", new LocalizableExceptionAdapter(e));
/*      */     } 
/*      */ 
/*      */     
/*  706 */     return port;
/*      */   }
/*      */ 
/*      */   
/*      */   private void validateEndpointClass(Class<?> endpointClass) throws ModelerException {
/*  711 */     if (!endpointClass.isInterface()) {
/*  712 */       throw new ModelerException("rmimodeler.service.endpoint.must.be.interface", endpointClass.getName());
/*      */     }
/*      */ 
/*      */     
/*  716 */     if (!this.defRemote.isAssignableFrom(endpointClass)) {
/*  717 */       throw new ModelerException("rmimodeler.must.implement.remote", endpointClass.getName());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Port processInterface(Class endpointClass, String intfName, RmiInterfaceInfo interfaceInfo) {
/*  728 */     this.messageMap = new HashMap<Object, Object>();
/*  729 */     String servant = interfaceInfo.getServantName();
/*  730 */     log("creating port: " + ClassNameInfo.replaceInnerClassSym(endpointClass.getName()));
/*      */ 
/*      */     
/*  733 */     String portName = ClassNameInfo.getName(endpointClass.getName().replace('$', '_'));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  738 */     String packageName = endpointClass.getPackage().getName();
/*  739 */     String namespace = this.wsdlUri;
/*  740 */     Port port = new Port(new QName(namespace, portName));
/*  741 */     port.setSOAPVersion(interfaceInfo.getSOAPVersion());
/*  742 */     JavaInterface javaInterface = new JavaInterface(intfName, servant);
/*  743 */     port.setJavaInterface(javaInterface);
/*  744 */     Class[] remoteInterfaces = endpointClass.getInterfaces();
/*      */     
/*  746 */     for (int i = 0; i < remoteInterfaces.length; i++) {
/*  747 */       String interfaceName = this.env.getNames().removeCharacter(32, remoteInterfaces[i].getName());
/*      */ 
/*      */ 
/*      */       
/*  751 */       if (!interfaceName.equals(javaInterface.getName())) {
/*  752 */         javaInterface.addInterface(interfaceName);
/*      */       }
/*      */     } 
/*  755 */     Iterator<Method> methods = sortMethods(endpointClass, endpointClass.getMethods());
/*      */ 
/*      */     
/*  758 */     while (methods.hasNext()) {
/*  759 */       Method method = methods.next();
/*  760 */       if (!verifyRemoteMethod(method)) {
/*  761 */         throw new ModelerException("rmimodeler.must.throw.remoteexception", new Object[] { endpointClass.getName(), method.getName() });
/*      */       }
/*      */ 
/*      */       
/*  765 */       port.addOperation(processMethod(interfaceInfo, endpointClass, method, namespace));
/*      */     } 
/*      */ 
/*      */     
/*  769 */     port.setClientHandlerChainInfo(interfaceInfo.getClientHandlerChainInfo());
/*      */     
/*  771 */     port.setServerHandlerChainInfo(interfaceInfo.getServerHandlerChainInfo());
/*      */ 
/*      */ 
/*      */     
/*  775 */     String stubClassName = this.env.getNames().stubFor(port, null);
/*  776 */     String tieClassName = this.env.getNames().tieFor(port, this.env.getNames().getSerializerNameInfix());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  781 */     port.setProperty("com.sun.xml.rpc.processor.model.StubClassName", stubClassName);
/*      */ 
/*      */     
/*  784 */     port.setProperty("com.sun.xml.rpc.processor.model.TieClassName", tieClassName);
/*      */ 
/*      */     
/*  787 */     port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortName", getWSDLPortName(portName));
/*      */ 
/*      */     
/*  790 */     port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName", getWSDLPortTypeName(portName));
/*      */ 
/*      */     
/*  793 */     port.setProperty("com.sun.xml.rpc.processor.model.WSDLBindingName", getWSDLBindingName(portName));
/*      */ 
/*      */ 
/*      */     
/*  797 */     this.messageMap = null;
/*  798 */     return port;
/*      */   }
/*      */ 
/*      */   
/*      */   private Iterator sortMethods(Class endpointClass, Method[] methods) {
/*  803 */     if (VersionUtil.isVersion101(this.options.getProperty("sourceVersion")))
/*      */     {
/*      */       
/*  806 */       return Arrays.<Method>asList(methods).iterator();
/*      */     }
/*  808 */     Set<Method> sortedMethods = new TreeSet(new MethodComparator());
/*  809 */     Set<String> methodSigs = new HashSet();
/*      */     
/*  811 */     for (int i = 0; i < methods.length; i++) {
/*  812 */       String sig = getMethodSig(methods[i]);
/*  813 */       if (!methodSigs.contains(sig)) {
/*  814 */         sortedMethods.add(methods[i]);
/*  815 */         methodSigs.add(sig);
/*      */       } 
/*      */     } 
/*  818 */     return sortedMethods.iterator();
/*      */   }
/*      */   
/*      */   private boolean verifyRemoteMethod(Method method) {
/*  822 */     Class[] exceptions = method.getExceptionTypes();
/*  823 */     boolean hasRemoteException = false;
/*  824 */     for (int i = 0; i < exceptions.length; i++) {
/*  825 */       if (RemoteException.class.isAssignableFrom(exceptions[i]))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  830 */         return true;
/*      */       }
/*      */     } 
/*  833 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation processMethod(RmiInterfaceInfo interfaceInfo, Class endpointClass, Method method, String namespaceURI) {
/*  842 */     if (this.useDocLiteral) {
/*  843 */       return processDocumentLiteralMethod(interfaceInfo, endpointClass, method, namespaceURI);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  848 */     if (this.useWSIBasicProfile || this.useRPCLiteral) {
/*  849 */       return processRpcLiteralMethod(interfaceInfo, endpointClass, method, namespaceURI);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  855 */     return processRpcEncodedMethod(interfaceInfo, endpointClass, method, namespaceURI);
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
/*      */   private Operation processRpcEncodedMethod(RmiInterfaceInfo interfaceInfo, Class endpointClass, Method method, String namespaceURI) {
/*      */     RPCResponseStructureType rPCResponseStructureType;
/*  869 */     String portName = ClassNameInfo.getName(endpointClass.getName());
/*  870 */     portName = portName.replace('$', '_');
/*  871 */     RmiType returnType = RmiType.getRmiType(method.getReturnType());
/*  872 */     RmiType[] paramTypes = getParameterTypes(method);
/*  873 */     String[] paramNames = nameParameters(paramTypes);
/*  874 */     Class[] exceptions = method.getExceptionTypes();
/*  875 */     String messageName = method.getName();
/*  876 */     String operationName = getOperationName(messageName);
/*  877 */     String methodName = method.getName().toString();
/*  878 */     log("creating operation: " + methodName);
/*  879 */     Operation operation = new Operation(new QName(operationName));
/*      */     
/*  881 */     operation.setSOAPAction(getSOAPAction(interfaceInfo, operationName));
/*  882 */     JavaMethod javaMethod = new JavaMethod(methodName);
/*  883 */     javaMethod.setDeclaringClass(method.getDeclaringClass().getName());
/*  884 */     operation.setJavaMethod(javaMethod);
/*  885 */     String packageName = endpointClass.getPackage().getName();
/*  886 */     String typeNamespace = getNamespaceURI(packageName);
/*  887 */     SOAPVersion soapVersion = interfaceInfo.getSOAPVersion();
/*  888 */     RmiTypeModeler rmiTypeModeler = (soapVersion == SOAPVersion.SOAP_11) ? this.rmiTypeModeler_11 : this.rmiTypeModeler_12;
/*      */ 
/*      */ 
/*      */     
/*  892 */     ExceptionModelerBase exceptionModeler = (soapVersion == SOAPVersion.SOAP_11) ? this.exceptionModeler_11 : this.exceptionModeler_12;
/*      */ 
/*      */ 
/*      */     
/*  896 */     if (typeNamespace == null)
/*  897 */       typeNamespace = this.typeUri; 
/*  898 */     if (packageName.length() > 0) {
/*  899 */       packageName = packageName + ".";
/*      */     }
/*      */     
/*  902 */     operation.setStyle(SOAPStyle.RPC);
/*  903 */     operation.setUse(SOAPUse.ENCODED);
/*      */ 
/*      */     
/*  906 */     SOAPStructureMember member = null;
/*  907 */     JavaStructureMember javaMember = null;
/*  908 */     JavaStructureType javaRespStructure = null;
/*  909 */     SOAPStructureType responseStruct = null;
/*  910 */     JavaParameter javaParameter = null;
/*  911 */     Block responseBlock = null;
/*  912 */     Response response = null;
/*  913 */     boolean hasHolders = false;
/*  914 */     for (int i = 0; i < paramTypes.length && !hasHolders; i++) {
/*  915 */       hasHolders = (RmiTypeModeler.getHolderValueType(this.env, this.defHolder, paramTypes[i]) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  925 */     if (!this.generateOneWayOperations || returnType.getTypeCode() != 11 || hasHolders || exceptions.length > 1) {
/*      */ 
/*      */ 
/*      */       
/*  929 */       rPCResponseStructureType = new RPCResponseStructureType(new QName(typeNamespace, this.env.getNames().getResponseName(operationName)), soapVersion);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  935 */       javaRespStructure = new JavaStructureType(getStructName(packageName + portName + "_" + methodName + "_ResponseStruct"), false, rPCResponseStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  945 */       rPCResponseStructureType.setJavaType((JavaType)javaRespStructure);
/*  946 */       response = new Response();
/*      */       
/*  948 */       responseBlock = new Block(new QName(namespaceURI, this.env.getNames().getResponseName(operationName)));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  953 */       SOAPType resultType = rmiTypeModeler.modelTypeSOAP(this.typeUri, returnType);
/*  954 */       if (returnType.getTypeCode() != 11) {
/*  955 */         if (soapVersion.equals(SOAPVersion.SOAP_12)) {
/*  956 */           member = new SOAPStructureMember(new QName(namespaceURI, "result"), resultType);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  961 */           member = new SOAPStructureMember(new QName(null, "result"), resultType);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  966 */         javaMember = new JavaStructureMember(member.getName().getLocalPart(), member.getType().getJavaType(), member, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  972 */         this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/*  973 */         member.setJavaStructureMember(javaMember);
/*  974 */         javaRespStructure.add(javaMember);
/*  975 */         rPCResponseStructureType.add(member);
/*      */       } 
/*  977 */       response.addBodyBlock(responseBlock);
/*  978 */       Parameter resultParam = new Parameter("result");
/*  979 */       resultParam.setEmbedded(true);
/*  980 */       resultParam.setType((AbstractType)resultType);
/*  981 */       resultParam.setBlock(responseBlock);
/*  982 */       responseBlock.setType((AbstractType)rPCResponseStructureType);
/*  983 */       javaParameter = new JavaParameter(null, resultType.getJavaType(), resultParam);
/*      */       
/*  985 */       javaMethod.setReturnType(resultType.getJavaType());
/*  986 */       resultParam.setJavaParameter(javaParameter);
/*  987 */       response.addParameter(resultParam);
/*  988 */       response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLOutputMessageName(portName + "_" + operationName));
/*      */ 
/*      */ 
/*      */       
/*  992 */       operation.setResponse(response);
/*      */     } else {
/*  994 */       javaMethod.setReturnType(rmiTypeModeler.modelTypeSOAP(this.typeUri, returnType).getJavaType());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1000 */     RPCRequestOrderedStructureType rPCRequestOrderedStructureType = new RPCRequestOrderedStructureType(new QName(typeNamespace, operationName), soapVersion);
/*      */ 
/*      */ 
/*      */     
/* 1004 */     JavaStructureType javaStruct = new JavaStructureType(getStructName(packageName + portName + "_" + rPCRequestOrderedStructureType.getName().getLocalPart() + "_RequestStruct"), false, rPCRequestOrderedStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1014 */     rPCRequestOrderedStructureType.setJavaType((JavaType)javaStruct);
/*      */     
/* 1016 */     Request request = new Request();
/*      */ 
/*      */     
/* 1019 */     JavaStructureType javaStructure = (JavaStructureType)rPCRequestOrderedStructureType.getJavaType();
/*      */     
/* 1021 */     Block block = new Block(new QName(namespaceURI, operationName));
/*      */     
/*      */     int j;
/* 1024 */     for (j = 0; j < paramTypes.length; j++) {
/* 1025 */       QName typeName = new QName(null, paramNames[j]);
/* 1026 */       SOAPType memberType = rmiTypeModeler.modelTypeSOAP(this.typeUri, paramTypes[j]);
/* 1027 */       boolean isHolder = (RmiTypeModeler.getHolderValueType(this.env, this.defHolder, paramTypes[j]) != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1033 */       member = new SOAPStructureMember(typeName, memberType);
/* 1034 */       javaMember = new JavaStructureMember(member.getName().getLocalPart(), member.getType().getJavaType(), member, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1040 */       this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/* 1041 */       member.setJavaStructureMember(javaMember);
/* 1042 */       javaStructure.add(javaMember);
/* 1043 */       rPCRequestOrderedStructureType.add(member);
/* 1044 */       Parameter parameter = new Parameter(paramNames[j]);
/* 1045 */       if (isHolder) {
/* 1046 */         javaRespStructure.add(javaMember);
/* 1047 */         rPCResponseStructureType.add(member);
/* 1048 */         Parameter responseParam = new Parameter(paramNames[j]);
/* 1049 */         responseParam.setEmbedded(true);
/* 1050 */         javaParameter = new JavaParameter(paramNames[j], member.getType().getJavaType(), responseParam, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1056 */         javaParameter.setHolderName(paramTypes[j].toString());
/* 1057 */         responseParam.setJavaParameter(javaParameter);
/* 1058 */         responseParam.setType((AbstractType)member.getType());
/* 1059 */         responseParam.setBlock(responseBlock);
/* 1060 */         parameter.setLinkedParameter(responseParam);
/* 1061 */         responseParam.setLinkedParameter(parameter);
/* 1062 */         response.addParameter(responseParam);
/*      */       } 
/* 1064 */       parameter.setEmbedded(true);
/* 1065 */       javaParameter = new JavaParameter(paramNames[j], member.getType().getJavaType(), parameter, isHolder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1071 */       if (isHolder)
/* 1072 */         javaParameter.setHolderName(paramTypes[j].toString()); 
/* 1073 */       parameter.setJavaParameter(javaParameter);
/* 1074 */       parameter.setType((AbstractType)member.getType());
/* 1075 */       parameter.setBlock(block);
/* 1076 */       javaMethod.addParameter(javaParameter);
/* 1077 */       request.addParameter(parameter);
/*      */     } 
/* 1079 */     block.setType((AbstractType)rPCRequestOrderedStructureType);
/* 1080 */     request.addBodyBlock(block);
/* 1081 */     request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLInputMessageName(portName + "_" + operationName));
/*      */ 
/*      */     
/* 1084 */     operation.setRequest(request);
/*      */ 
/*      */     
/* 1087 */     if (exceptions.length > 0) {
/* 1088 */       for (j = 0; j < exceptions.length; j++) {
/* 1089 */         if (!isRemoteException(this.env, exceptions[j].getName().toString())) {
/*      */           
/* 1091 */           javaMethod.addException(exceptions[j].getName().toString());
/* 1092 */           if (!exceptions[j].getName().toString().equals(EXCEPTION_CLASSNAME)) {
/*      */ 
/*      */ 
/*      */             
/* 1096 */             Fault fault = exceptionModeler.modelException(this.typeUri, this.wsdlUri, exceptions[j]);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1101 */             fault.setElementName(fault.getBlock().getName());
/* 1102 */             response.addFaultBlock(fault.getBlock());
/* 1103 */             fault.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLFaultMessageName(fault.getName()));
/*      */ 
/*      */             
/* 1106 */             operation.addFault(fault);
/* 1107 */             addFaultParent(fault, exceptionModeler);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1113 */     return operation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation processDocumentLiteralMethod(RmiInterfaceInfo interfaceInfo, Class endpointClass, Method method, String namespaceURI) {
/* 1122 */     String portName = ClassNameInfo.getName(endpointClass.getName());
/* 1123 */     portName = portName.replace('$', '_');
/* 1124 */     RmiType returnType = RmiType.getRmiType(method.getReturnType());
/* 1125 */     RmiType[] paramTypes = getParameterTypes(method);
/* 1126 */     String[] paramNames = nameParameters(paramTypes);
/* 1127 */     Class[] exceptions = method.getExceptionTypes();
/* 1128 */     String messageName = method.getName();
/* 1129 */     String operationName = getOperationName(messageName);
/* 1130 */     String methodName = method.getName().toString();
/* 1131 */     log("creating operation: " + methodName);
/* 1132 */     Operation operation = new Operation(new QName(namespaceURI, operationName));
/*      */     
/* 1134 */     operation.setSOAPAction(getSOAPAction(interfaceInfo, operationName));
/* 1135 */     JavaMethod javaMethod = new JavaMethod(methodName);
/* 1136 */     javaMethod.setDeclaringClass(method.getDeclaringClass().getName());
/* 1137 */     operation.setJavaMethod(javaMethod);
/* 1138 */     String packageName = endpointClass.getPackage().getName();
/* 1139 */     String typeNamespace = getNamespaceURI(packageName);
/* 1140 */     if (typeNamespace == null)
/* 1141 */       typeNamespace = this.typeUri; 
/* 1142 */     if (packageName.length() > 0) {
/* 1143 */       packageName = packageName + ".";
/*      */     }
/* 1145 */     SOAPVersion soapVersion = interfaceInfo.getSOAPVersion();
/*      */     
/* 1147 */     operation.setStyle(SOAPStyle.DOCUMENT);
/* 1148 */     operation.setUse(SOAPUse.LITERAL);
/*      */     
/* 1150 */     boolean ver111Above = (VersionUtil.compare(this.sourceVersion, "1.1.1") >= 0);
/*      */     
/* 1152 */     LiteralSequenceType literalSequenceType = new LiteralSequenceType();
/* 1153 */     if (ver111Above) {
/* 1154 */       literalSequenceType.setUnwrapped(true);
/*      */     }
/* 1156 */     literalSequenceType.setName(new QName(typeNamespace, operationName));
/*      */ 
/*      */     
/* 1159 */     JavaStructureType javaStruct = new JavaStructureType(getStructName(packageName + portName + "_" + literalSequenceType.getName().getLocalPart() + "_RequestStruct"), false, literalSequenceType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1169 */     literalSequenceType.setJavaType((JavaType)javaStruct);
/*      */     
/* 1171 */     Request request = new Request();
/*      */ 
/*      */     
/* 1174 */     Block block = new Block(new QName(typeNamespace, literalSequenceType.getName().getLocalPart()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     for (int i = 0; i < paramTypes.length; i++) {
/* 1182 */       QName qName = new QName(paramNames[i]);
/* 1183 */       LiteralElementMember literalElementMember = this.literalTypeModeler.modelTypeLiteral(qName, this.typeUri, paramTypes[i]);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1188 */       JavaStructureMember javaMember = literalElementMember.getJavaStructureMember();
/* 1189 */       javaMember.setName(paramNames[i]);
/* 1190 */       this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/* 1191 */       javaStruct.add(javaMember);
/* 1192 */       literalSequenceType.add(literalElementMember);
/* 1193 */       Parameter parameter = new Parameter(paramNames[i]);
/* 1194 */       parameter.setEmbedded(true);
/* 1195 */       JavaParameter javaParameter = new JavaParameter(paramNames[i], literalElementMember.getJavaStructureMember().getType(), parameter, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1201 */       parameter.setJavaParameter(javaParameter);
/* 1202 */       parameter.setType((AbstractType)literalElementMember.getType());
/* 1203 */       parameter.setBlock(block);
/* 1204 */       parameter.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", parameter.getName());
/*      */ 
/*      */       
/* 1207 */       javaMethod.addParameter(javaParameter);
/* 1208 */       request.addParameter(parameter);
/*      */     } 
/* 1210 */     block.setType((AbstractType)literalSequenceType);
/* 1211 */     request.addBodyBlock(block);
/* 1212 */     request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLInputMessageName(portName + "_" + operationName));
/*      */ 
/*      */     
/* 1215 */     operation.setRequest(request);
/*      */ 
/*      */     
/* 1218 */     QName elemName = new QName("result");
/* 1219 */     LiteralElementMember member = this.literalTypeModeler.modelTypeLiteral(elemName, this.typeUri, returnType);
/*      */     
/* 1221 */     Response response = null;
/* 1222 */     boolean genResponsePart = true;
/* 1223 */     if (this.generateOneWayOperations && returnType.getTypeCode() == 11 && exceptions.length <= 1)
/*      */     {
/* 1225 */       genResponsePart = false;
/*      */     }
/* 1227 */     if (genResponsePart) {
/* 1228 */       LiteralSequenceType responseStruct = new LiteralSequenceType();
/* 1229 */       responseStruct.setName(new QName(typeNamespace, this.env.getNames().getResponseName(operationName)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1235 */       javaStruct = new JavaStructureType(getStructName(packageName + portName + "_" + methodName + "_ResponseStruct"), false, responseStruct);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1245 */       responseStruct.setJavaType((JavaType)javaStruct);
/* 1246 */       response = new Response();
/*      */       
/* 1248 */       block = new Block(new QName(typeNamespace, responseStruct.getName().getLocalPart()));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1253 */       if (returnType.getTypeCode() != 11) {
/* 1254 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/* 1255 */         javaMember.setName("result");
/* 1256 */         javaStruct.add(javaMember);
/* 1257 */         responseStruct.add(member);
/*      */         
/* 1259 */         Parameter resultParam = new Parameter("result");
/* 1260 */         resultParam.setEmbedded(true);
/* 1261 */         resultParam.setType((AbstractType)responseStruct);
/* 1262 */         resultParam.setBlock(block);
/* 1263 */         block.setType((AbstractType)responseStruct);
/* 1264 */         response.addBodyBlock(block);
/* 1265 */         JavaParameter javaParameter = new JavaParameter(null, block.getType().getJavaType(), resultParam);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1270 */         resultParam.setJavaParameter(javaParameter);
/* 1271 */         resultParam.setProperty("com.sun.xml.rpc.processor.model.ParamMessagePartName", resultParam.getName());
/*      */ 
/*      */         
/* 1274 */         response.addParameter(resultParam);
/*      */       }
/* 1276 */       else if (ver111Above) {
/* 1277 */         block.setType((AbstractType)responseStruct);
/* 1278 */         response.addBodyBlock(block);
/*      */       } 
/*      */       
/* 1281 */       response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLOutputMessageName(portName + "_" + operationName));
/*      */ 
/*      */ 
/*      */       
/* 1285 */       operation.setResponse(response);
/*      */     } 
/*      */     
/* 1288 */     javaMethod.setReturnType(member.getJavaStructureMember().getType());
/*      */ 
/*      */     
/* 1291 */     if (exceptions.length > 0) {
/* 1292 */       for (int j = 0; j < exceptions.length; j++) {
/* 1293 */         if (!isRemoteException(this.env, exceptions[j].getName().toString())) {
/*      */           
/* 1295 */           javaMethod.addException(exceptions[j].getName().toString());
/* 1296 */           if (!exceptions[j].getName().toString().equals(EXCEPTION_CLASSNAME)) {
/*      */ 
/*      */ 
/*      */             
/* 1300 */             Fault fault = this.literalExceptionModeler.modelException(this.typeUri, this.wsdlUri, exceptions[j]);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1305 */             fault.setElementName(new QName(fault.getBlock().getType().getName().getNamespaceURI(), fault.getBlock().getType().getName().getLocalPart()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1317 */             if (response == null) {
/* 1318 */               response = new Response();
/* 1319 */               operation.setResponse(response);
/*      */             } 
/* 1321 */             response.addFaultBlock(fault.getBlock());
/* 1322 */             fault.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLFaultMessageName(fault.getName()));
/*      */ 
/*      */             
/* 1325 */             operation.addFault(fault);
/* 1326 */             addFaultParent(fault, this.literalExceptionModeler);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1332 */     return operation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Operation processRpcLiteralMethod(RmiInterfaceInfo interfaceInfo, Class endpointClass, Method method, String namespaceURI) {
/* 1341 */     String portName = ClassNameInfo.getName(endpointClass.getName());
/* 1342 */     portName = portName.replace('$', '_');
/* 1343 */     RmiType returnType = RmiType.getRmiType(method.getReturnType());
/* 1344 */     RmiType[] paramTypes = getParameterTypes(method);
/* 1345 */     String[] paramNames = nameParameters(paramTypes);
/* 1346 */     Class[] exceptions = method.getExceptionTypes();
/* 1347 */     String messageName = method.getName();
/* 1348 */     String operationName = getOperationName(messageName);
/* 1349 */     String methodName = method.getName().toString();
/* 1350 */     log("creating operation: " + methodName);
/* 1351 */     Operation operation = new Operation(new QName(namespaceURI, operationName));
/*      */     
/* 1353 */     operation.setSOAPAction(getSOAPAction(interfaceInfo, operationName));
/* 1354 */     JavaMethod javaMethod = new JavaMethod(methodName);
/* 1355 */     javaMethod.setDeclaringClass(method.getDeclaringClass().getName());
/* 1356 */     operation.setJavaMethod(javaMethod);
/* 1357 */     String packageName = endpointClass.getPackage().getName();
/* 1358 */     String typeNamespace = getNamespaceURI(packageName);
/* 1359 */     if (typeNamespace == null)
/* 1360 */       typeNamespace = this.typeUri; 
/* 1361 */     if (packageName.length() > 0) {
/* 1362 */       packageName = packageName + ".";
/*      */     }
/* 1364 */     SOAPVersion soapVersion = interfaceInfo.getSOAPVersion();
/*      */     
/* 1366 */     operation.setStyle(SOAPStyle.RPC);
/* 1367 */     operation.setUse(SOAPUse.LITERAL);
/*      */ 
/*      */     
/* 1370 */     JavaStructureMember javaMember = null;
/*      */     
/* 1372 */     JavaStructureType javaRespStructure = null;
/* 1373 */     Block responseBlock = null;
/*      */ 
/*      */     
/* 1376 */     String elemNamespaceURI = null;
/* 1377 */     if (soapVersion.equals(SOAPVersion.SOAP_12))
/* 1378 */       elemNamespaceURI = typeNamespace; 
/* 1379 */     QName elemName = new QName(elemNamespaceURI, "result");
/* 1380 */     LiteralElementMember member = this.literalTypeModeler.modelTypeLiteral(elemName, this.typeUri, returnType, true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1387 */     member.setRequired(true);
/* 1388 */     Response response = null;
/* 1389 */     boolean hasHolders = false;
/* 1390 */     for (int i = 0; i < paramTypes.length && !hasHolders; i++) {
/* 1391 */       hasHolders = (LiteralTypeModeler.getHolderValueType(this.env, this.defHolder, paramTypes[i]) != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1399 */     LiteralSequenceType responseStruct = null;
/*      */ 
/*      */     
/* 1402 */     if (!this.generateOneWayOperations || returnType.getTypeCode() != 11 || hasHolders || exceptions.length > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1408 */       responseStruct = new LiteralSequenceType();
/*      */       
/* 1410 */       responseStruct.setName(new QName(elemNamespaceURI, this.env.getNames().getResponseName(operationName)));
/*      */ 
/*      */ 
/*      */       
/* 1414 */       responseStruct.setRpcWrapper(true);
/* 1415 */       javaRespStructure = new JavaStructureType(getStructName(packageName + portName + "_" + methodName + "_ResponseStruct"), false, responseStruct);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1425 */       responseStruct.setJavaType((JavaType)javaRespStructure);
/* 1426 */       response = new Response();
/*      */       
/* 1428 */       responseBlock = new Block(new QName(namespaceURI, this.env.getNames().getResponseName(operationName)));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1433 */       javaMember = member.getJavaStructureMember();
/* 1434 */       javaMember.setName("result");
/* 1435 */       if (returnType.getTypeCode() != 11) {
/* 1436 */         javaRespStructure.add(javaMember);
/* 1437 */         responseStruct.add(member);
/*      */       } 
/* 1439 */       Parameter resultParam = new Parameter("result");
/* 1440 */       response.addBodyBlock(responseBlock);
/* 1441 */       resultParam.setEmbedded(true);
/* 1442 */       resultParam.setType((AbstractType)member.getType());
/* 1443 */       resultParam.setBlock(responseBlock);
/* 1444 */       responseBlock.setType((AbstractType)responseStruct);
/* 1445 */       JavaParameter javaParameter = new JavaParameter(null, responseStruct.getJavaType(), resultParam);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1450 */       javaMethod.setReturnType(javaMember.getType());
/* 1451 */       resultParam.setJavaParameter(javaParameter);
/* 1452 */       response.addParameter(resultParam);
/*      */       
/* 1454 */       response.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLOutputMessageName(portName + "_" + operationName));
/*      */ 
/*      */ 
/*      */       
/* 1458 */       operation.setResponse(response);
/*      */     } else {
/* 1460 */       javaMethod.setReturnType(member.getJavaStructureMember().getType());
/*      */     } 
/*      */ 
/*      */     
/* 1464 */     LiteralSequenceType literalSequenceType1 = new LiteralSequenceType();
/* 1465 */     literalSequenceType1.setName(new QName(typeNamespace, operationName));
/* 1466 */     literalSequenceType1.setRpcWrapper(true);
/* 1467 */     JavaStructureType javaStruct = new JavaStructureType(getStructName(packageName + portName + "_" + literalSequenceType1.getName().getLocalPart() + "_RequestStruct"), false, literalSequenceType1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1477 */     literalSequenceType1.setJavaType((JavaType)javaStruct);
/*      */     
/* 1479 */     Request request = new Request();
/*      */ 
/*      */     
/* 1482 */     Block requestBlock = new Block(new QName(namespaceURI, literalSequenceType1.getName().getLocalPart()));
/*      */ 
/*      */     
/*      */     int j;
/*      */ 
/*      */     
/* 1488 */     for (j = 0; j < paramTypes.length; j++) {
/* 1489 */       elemName = new QName(paramNames[j]);
/* 1490 */       member = this.literalTypeModeler.modelTypeLiteral(elemName, this.typeUri, paramTypes[j], true, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1497 */       member.setRequired(true);
/* 1498 */       boolean isHolder = (LiteralTypeModeler.getHolderValueType(this.env, this.defHolder, paramTypes[j]) != null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1504 */       javaMember = member.getJavaStructureMember();
/* 1505 */       Parameter parameter = new Parameter(paramNames[j]);
/* 1506 */       if (isHolder) {
/* 1507 */         javaRespStructure.add(javaMember);
/* 1508 */         responseStruct.add(member);
/* 1509 */         Parameter responseParam = new Parameter(paramNames[j]);
/* 1510 */         responseParam.setEmbedded(true);
/* 1511 */         JavaParameter javaParameter1 = new JavaParameter(paramNames[j], member.getType().getJavaType(), responseParam, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1517 */         javaParameter1.setHolderName(paramTypes[j].toString());
/* 1518 */         responseParam.setJavaParameter(javaParameter1);
/* 1519 */         responseParam.setType((AbstractType)member.getType());
/* 1520 */         responseParam.setBlock(responseBlock);
/* 1521 */         parameter.setLinkedParameter(responseParam);
/* 1522 */         responseParam.setLinkedParameter(parameter);
/* 1523 */         response.addParameter(responseParam);
/*      */       } 
/* 1525 */       parameter.setEmbedded(true);
/* 1526 */       javaStruct.add(javaMember);
/* 1527 */       literalSequenceType1.add(member);
/* 1528 */       JavaParameter javaParameter = new JavaParameter(paramNames[j], member.getJavaStructureMember().getType(), parameter, isHolder);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1534 */       if (isHolder)
/* 1535 */         javaParameter.setHolderName(paramTypes[j].toString()); 
/* 1536 */       parameter.setJavaParameter(javaParameter);
/* 1537 */       parameter.setType((AbstractType)member.getType());
/* 1538 */       parameter.setBlock(requestBlock);
/* 1539 */       javaMethod.addParameter(javaParameter);
/* 1540 */       request.addParameter(parameter);
/*      */     } 
/* 1542 */     requestBlock.setType((AbstractType)literalSequenceType1);
/* 1543 */     request.addBodyBlock(requestBlock);
/* 1544 */     request.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLInputMessageName(portName + "_" + operationName));
/*      */ 
/*      */     
/* 1547 */     operation.setRequest(request);
/*      */ 
/*      */     
/* 1550 */     if (exceptions.length > 0) {
/* 1551 */       for (j = 0; j < exceptions.length; j++) {
/* 1552 */         if (!isRemoteException(this.env, exceptions[j].getName().toString())) {
/*      */           
/* 1554 */           javaMethod.addException(exceptions[j].getName().toString());
/* 1555 */           if (!exceptions[j].getName().toString().equals(EXCEPTION_CLASSNAME)) {
/*      */ 
/*      */ 
/*      */             
/* 1559 */             Fault fault = this.literalExceptionModeler.modelException(this.typeUri, this.wsdlUri, exceptions[j]);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1564 */             fault.setElementName(new QName(fault.getBlock().getType().getName().getNamespaceURI(), fault.getBlock().getType().getName().getLocalPart()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1576 */             if (response == null) {
/* 1577 */               response = new Response();
/* 1578 */               operation.setResponse(response);
/*      */             } 
/* 1580 */             response.addFaultBlock(fault.getBlock());
/* 1581 */             fault.setProperty("com.sun.xml.rpc.processor.model.WSDLMessageName", getWSDLFaultMessageName(fault.getName()));
/*      */ 
/*      */             
/* 1584 */             operation.addFault(fault);
/* 1585 */             addFaultParent(fault, this.literalExceptionModeler);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1591 */     return operation;
/*      */   }
/*      */   
/*      */   private RmiType[] getParameterTypes(Method method) {
/* 1595 */     Class[] args = method.getParameterTypes();
/* 1596 */     RmiType[] types = new RmiType[args.length];
/* 1597 */     for (int i = 0; i < args.length; i++) {
/* 1598 */       types[i] = RmiType.getRmiType(args[i]);
/*      */     }
/* 1600 */     return types;
/*      */   }
/*      */   
/*      */   private String getStructName(String name) {
/* 1604 */     String tmp = name.toLowerCase();
/* 1605 */     Integer count = (Integer)this.structMap.get(tmp);
/* 1606 */     if (count != null) {
/* 1607 */       count = new Integer(count.intValue() + 1);
/* 1608 */       name = name + count;
/*      */     } else {
/* 1610 */       count = new Integer(0);
/*      */     } 
/* 1612 */     this.structMap.put(tmp, count);
/* 1613 */     return name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRemoteException(ProcessorEnvironment env, String exceptionName) {
/*      */     try {
/* 1621 */       Class<?> exceptionClass = RmiUtils.getClassForName(exceptionName, env.getClassLoader());
/*      */       
/* 1623 */       return this.remoteExceptionClass.isAssignableFrom(exceptionClass);
/* 1624 */     } catch (ClassNotFoundException e) {
/* 1625 */       throw new ModelerException("rmimodeler.class.not.found", exceptionName);
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
/*      */   private String[] nameParameters(RmiType[] types) {
/* 1641 */     String[] names = new String[types.length];
/* 1642 */     for (int i = 0; i < names.length; i++) {
/* 1643 */       names[i] = generateNameFromType(types[i], this.env) + "_" + (i + 1);
/*      */     }
/*      */     
/* 1646 */     return names;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String generateNameFromType(RmiType type, ProcessorEnvironment env) {
/*      */     RmiType holderValueType;
/*      */     String tmp;
/* 1657 */     int typeCode = type.getTypeCode();
/* 1658 */     switch (typeCode) {
/*      */       case 0:
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/* 1667 */         return type.toString();
/*      */       case 9:
/* 1669 */         return "arrayOf" + generateNameFromType(type.getElementType(), env);
/*      */       
/*      */       case 10:
/* 1672 */         holderValueType = RmiTypeModeler.getHolderValueType(env, this.defHolder, type);
/*      */         
/* 1674 */         if (holderValueType != null) {
/* 1675 */           return generateNameFromType(holderValueType, env);
/*      */         }
/* 1677 */         tmp = ClassNameInfo.getName(type.getClassName());
/* 1678 */         return ClassNameInfo.replaceInnerClassSym(tmp);
/*      */     } 
/* 1680 */     throw new Error("unexpected type code: " + typeCode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSOAPAction(RmiInterfaceInfo interfaceInfo, String operationName) {
/* 1688 */     if (interfaceInfo.getSOAPAction() != null) {
/* 1689 */       return interfaceInfo.getSOAPAction();
/*      */     }
/*      */     
/* 1692 */     if (interfaceInfo.getSOAPActionBase() != null) {
/* 1693 */       return interfaceInfo.getSOAPActionBase() + operationName;
/*      */     }
/*      */     
/* 1696 */     return "";
/*      */   }
/*      */   
/*      */   public String getOperationName(String messageName) {
/* 1700 */     String operationName = null;
/* 1701 */     Integer cnt = (Integer)this.messageMap.get(messageName);
/* 1702 */     if (cnt == null) {
/* 1703 */       cnt = new Integer(0);
/* 1704 */       operationName = messageName;
/*      */     } 
/* 1706 */     this.messageMap.put(messageName, new Integer(cnt.intValue() + 1));
/* 1707 */     if (operationName == null) {
/* 1708 */       operationName = messageName + (cnt.intValue() + 1);
/*      */     }
/* 1710 */     return operationName;
/*      */   }
/*      */   
/*      */   public String getNamespaceURI(String javaPackageName) {
/* 1714 */     if (this.namespaceMappingRegistry != null) {
/* 1715 */       NamespaceMappingInfo i = this.namespaceMappingRegistry.getNamespaceMappingInfo(javaPackageName);
/*      */ 
/*      */       
/* 1718 */       if (i != null)
/* 1719 */         return i.getNamespaceURI(); 
/*      */     } 
/* 1721 */     return null;
/*      */   }
/*      */   
/*      */   private void log(String msg) {
/* 1725 */     if (this.env.verbose()) {
/* 1726 */       System.out.println("[" + msg + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private QName getWSDLPortName(String portName) {
/* 1734 */     return new QName(this.wsdlUri, portName + "Port");
/*      */   }
/*      */   
/*      */   private QName getWSDLBindingName(String portName) {
/* 1738 */     return new QName(this.wsdlUri, portName + "Binding");
/*      */   }
/*      */   
/*      */   private QName getWSDLPortTypeName(String portName) {
/* 1742 */     return new QName(this.wsdlUri, portName);
/*      */   }
/*      */   
/*      */   private QName getWSDLInputMessageName(String operationName) {
/* 1746 */     return new QName(this.wsdlUri, operationName);
/*      */   }
/*      */   
/*      */   private QName getWSDLOutputMessageName(String operationName) {
/* 1750 */     return new QName(this.wsdlUri, operationName + "Response");
/*      */   }
/*      */   
/*      */   private QName getWSDLFaultMessageName(String faultName) {
/* 1754 */     return new QName(this.wsdlUri, faultName);
/*      */   }
/*      */   
/*      */   public static String getMethodSig(Method method) {
/* 1758 */     String sig = method.getName() + "(";
/* 1759 */     Class[] params = method.getParameterTypes();
/* 1760 */     for (int i = 0; i < params.length; i++) {
/* 1761 */       if (i > 0)
/* 1762 */         sig = sig + ", "; 
/* 1763 */       sig = sig + params[i].getName();
/*      */     } 
/* 1765 */     return sig + ")";
/*      */   }
/*      */   
/*      */   static {
/* 1769 */     excludedInterfaces.add(SERIALIZABLE_CLASSNAME);
/* 1770 */     excludedInterfaces.add(HOLDER_CLASSNAME);
/* 1771 */     excludedInterfaces.add(REMOTE_CLASSNAME);
/*      */   }
/*      */ 
/*      */   
/*      */   public static class MethodComparator
/*      */     implements Comparator
/*      */   {
/*      */     public int compare(Object o1, Object o2) {
/* 1779 */       Method method1 = (Method)o1;
/* 1780 */       Method method2 = (Method)o2;
/* 1781 */       return sort(method1, method2);
/*      */     }
/*      */ 
/*      */     
/*      */     protected int sort(Method method1, Method method2) {
/* 1786 */       String sig1 = RmiModeler.getMethodSig(method1);
/* 1787 */       String sig2 = RmiModeler.getMethodSig(method2);
/* 1788 */       Class<?> class1 = method1.getDeclaringClass();
/* 1789 */       Class<?> class2 = method2.getDeclaringClass();
/* 1790 */       if (class1.equals(class2))
/* 1791 */         return sig1.compareTo(sig2); 
/* 1792 */       if (class1.isAssignableFrom(class2))
/* 1793 */         return -1; 
/* 1794 */       return 1;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */