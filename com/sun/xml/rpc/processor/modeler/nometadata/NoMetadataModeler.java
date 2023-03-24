/*      */ package com.sun.xml.rpc.processor.modeler.nometadata;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*      */ import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
/*      */ import com.sun.xml.rpc.processor.config.NoMetadataModelInfo;
/*      */ import com.sun.xml.rpc.processor.generator.Names;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Block;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
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
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.modeler.JavaSimpleTypeCreator;
/*      */ import com.sun.xml.rpc.processor.modeler.Modeler;
/*      */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*      */ import com.sun.xml.rpc.processor.modeler.rmi.RmiUtils;
/*      */ import com.sun.xml.rpc.processor.modeler.wsdl.SchemaAnalyzer;
/*      */ import com.sun.xml.rpc.processor.util.ClassNameCollector;
/*      */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*      */ import com.sun.xml.rpc.processor.util.StringUtils;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*      */ import com.sun.xml.rpc.wsdl.document.Binding;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*      */ import com.sun.xml.rpc.wsdl.document.BindingOperation;
/*      */ import com.sun.xml.rpc.wsdl.document.Documentation;
/*      */ import com.sun.xml.rpc.wsdl.document.Fault;
/*      */ import com.sun.xml.rpc.wsdl.document.Message;
/*      */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*      */ import com.sun.xml.rpc.wsdl.document.Operation;
/*      */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*      */ import com.sun.xml.rpc.wsdl.document.Port;
/*      */ import com.sun.xml.rpc.wsdl.document.PortType;
/*      */ import com.sun.xml.rpc.wsdl.document.Service;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPAddress;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBinding;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPFault;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPHeader;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPOperation;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*      */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*      */ import com.sun.xml.rpc.wsdl.framework.Entity;
/*      */ import com.sun.xml.rpc.wsdl.framework.EntityReferenceValidator;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extensible;
/*      */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*      */ import com.sun.xml.rpc.wsdl.framework.GloballyKnown;
/*      */ import com.sun.xml.rpc.wsdl.framework.NoSuchEntityException;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParseException;
/*      */ import com.sun.xml.rpc.wsdl.framework.ParserListener;
/*      */ import com.sun.xml.rpc.wsdl.framework.ValidationException;
/*      */ import com.sun.xml.rpc.wsdl.parser.SOAPEntityReferenceValidator;
/*      */ import com.sun.xml.rpc.wsdl.parser.WSDLParser;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Method;
/*      */ import java.rmi.Remote;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.rpc.Service;
/*      */ import org.xml.sax.InputSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NoMetadataModeler
/*      */   implements Modeler
/*      */ {
/*      */   private Model model;
/*      */   private Properties options;
/*      */   private ProcessorEnvironment env;
/*      */   private NoMetadataModelInfo modelInfo;
/*      */   private NamespaceMappingRegistryInfo namespaceMappingRegistry;
/*      */   private SchemaAnalyzer analyzer;
/*      */   private LocalizableMessageFactory messageFactory;
/*      */   private JavaSimpleTypeCreator javaTypes;
/*      */   private Map javaExceptions;
/*      */   private static final String OPERATION_HAS_VOID_RETURN_TYPE = "com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType";
/*      */   private static final String WSDL_DOCUMENTATION = "com.sun.xml.rpc.processor.modeler.wsdl.documentation";
/*      */   private static final String WSDL_PARAMETER_ORDER = "com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder";
/*      */   private static final String WSDL_RESULT_PARAMETER = "com.sun.xml.rpc.processor.modeler.wsdl.resultParameter";
/*      */   
/*      */   public NoMetadataModeler(NoMetadataModelInfo modelInfo, Properties options) {
/*  138 */     this.modelInfo = modelInfo;
/*  139 */     this.env = (ProcessorEnvironment)modelInfo.getConfiguration().getEnvironment();
/*      */     
/*  141 */     this.options = options;
/*  142 */     this.namespaceMappingRegistry = modelInfo.getNamespaceMappingRegistry();
/*  143 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.modeler");
/*      */   }
/*      */ 
/*      */   
/*      */   public ProcessorEnvironment getProcessorEnvironment() {
/*  148 */     return this.env;
/*      */   }
/*      */   
/*      */   public NamespaceMappingRegistryInfo getNamespaceMappingRegistryInfo() {
/*  152 */     return this.namespaceMappingRegistry;
/*      */   }
/*      */   
/*      */   public Model getModel() {
/*  156 */     return this.model;
/*      */   }
/*      */   
/*      */   public Model buildModel() {
/*      */     try {
/*  161 */       WSDLParser parser = new WSDLParser();
/*  162 */       InputSource inputSource = new InputSource(this.modelInfo.getLocation());
/*  163 */       parser.addParserListener(new ParserListener() {
/*      */             public void ignoringExtension(QName name, QName parent) {
/*  165 */               if (parent.equals(WSDLConstants.QNAME_TYPES))
/*      */               {
/*      */                 
/*  168 */                 if (name.getLocalPart().equals("schema") && !name.getNamespaceURI().equals(""))
/*      */                 {
/*      */                   
/*  171 */                   NoMetadataModeler.this.warn("wsdlmodeler.warning.ignoringUnrecognizedSchemaExtension", name.getNamespaceURI());
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             public void doneParsingEntity(QName element, Entity entity) {}
/*      */           });
/*  182 */       boolean useWSIBasicProfile = Boolean.valueOf(this.options.getProperty("useWSIBasicProfile")).booleanValue();
/*      */       
/*  184 */       WSDLDocument document = parser.parse(inputSource, useWSIBasicProfile);
/*      */       
/*  186 */       document.validateLocally();
/*      */       
/*  188 */       boolean validateWSDL = Boolean.valueOf(this.options.getProperty("validationWSDL")).booleanValue();
/*      */ 
/*      */       
/*  191 */       if (validateWSDL) {
/*  192 */         document.validate((EntityReferenceValidator)new SOAPEntityReferenceValidator());
/*      */       }
/*      */       
/*  195 */       Model model = internalBuildModel(document);
/*  196 */       ClassNameCollector collector = new ClassNameCollector();
/*  197 */       collector.process(model);
/*  198 */       if (collector.getConflictingClassNames().isEmpty()) {
/*  199 */         return model;
/*      */       }
/*      */ 
/*      */       
/*  203 */       StringBuffer conflictList = new StringBuffer();
/*  204 */       boolean first = true;
/*  205 */       Iterator<String> iter = collector.getConflictingClassNames().iterator();
/*      */       
/*  207 */       while (iter.hasNext()) {
/*      */         
/*  209 */         if (!first) {
/*  210 */           conflictList.append(", ");
/*      */         } else {
/*  212 */           first = false;
/*      */         } 
/*  214 */         conflictList.append(iter.next());
/*      */       } 
/*  216 */       throw new ModelerException("wsdlmodeler.unsolvableNamingConflicts", conflictList.toString());
/*      */ 
/*      */     
/*      */     }
/*  220 */     catch (ModelException e) {
/*  221 */       throw new ModelerException(e);
/*  222 */     } catch (ParseException e) {
/*  223 */       throw new ModelerException(e);
/*  224 */     } catch (ValidationException e) {
/*  225 */       throw new ModelerException(e);
/*      */     } finally {
/*  227 */       this.analyzer = null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private Model internalBuildModel(WSDLDocument document) {
/*  232 */     QName modelName = new QName(document.getDefinitions().getTargetNamespaceURI(), (document.getDefinitions().getName() == null) ? "model" : document.getDefinitions().getName());
/*      */ 
/*      */ 
/*      */     
/*  236 */     Model model = new Model(modelName);
/*  237 */     model.setProperty("com.sun.xml.rpc.processor.model.ModelerName", getClass().getName());
/*      */     
/*  239 */     this.javaTypes = new JavaSimpleTypeCreator();
/*  240 */     this.javaExceptions = new HashMap<Object, Object>();
/*  241 */     this.analyzer = new SchemaAnalyzer((AbstractDocument)document, (ModelInfo)this.modelInfo, this.options, new HashSet(), this.javaTypes);
/*      */ 
/*      */ 
/*      */     
/*  245 */     model.setTargetNamespaceURI(document.getDefinitions().getTargetNamespaceURI());
/*      */ 
/*      */     
/*  248 */     setDocumentationIfPresent((ModelObject)model, document.getDefinitions().getDocumentation());
/*      */ 
/*      */     
/*  251 */     boolean hasServices = document.getDefinitions().services().hasNext();
/*  252 */     if (hasServices) {
/*  253 */       boolean gotOne = false;
/*  254 */       Iterator<Service> iter = document.getDefinitions().services();
/*  255 */       while (iter.hasNext()) {
/*      */         
/*  257 */         Service service = iter.next();
/*      */         
/*  259 */         if (gotOne) {
/*  260 */           throw new ModelerException("nometadatamodeler.error.moreThanOneServiceDefinition");
/*      */         }
/*      */         
/*  263 */         processService(service, model, document);
/*  264 */         gotOne = true;
/*      */       } 
/*      */     } else {
/*      */       
/*  268 */       throw new ModelerException("nometadatamodeler.error.noServiceDefinitionsFound");
/*      */     } 
/*      */     
/*  271 */     return model;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processService(Service wsdlService, Model model, WSDLDocument document) {
/*  278 */     QName serviceQName = getQNameOf((GloballyKnown)wsdlService);
/*  279 */     if (this.modelInfo.getServiceName() != null && !this.modelInfo.getServiceName().equals(serviceQName))
/*      */     {
/*      */       
/*  282 */       throw new ModelerException("nometadatamodeler.error.incorrectServiceName", serviceQName.toString());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  287 */     String serviceInterface = null;
/*  288 */     if (this.modelInfo.getServiceInterfaceName() != null) {
/*  289 */       serviceInterface = this.modelInfo.getServiceInterfaceName();
/*      */     } else {
/*  291 */       serviceInterface = "";
/*  292 */       String javaPackageName = getJavaPackageName(serviceQName);
/*  293 */       if (javaPackageName == null && this.modelInfo.getJavaPackageName() != null && !this.modelInfo.getJavaPackageName().equals(""))
/*      */       {
/*      */ 
/*      */         
/*  297 */         javaPackageName = this.modelInfo.getJavaPackageName();
/*      */       }
/*  299 */       if (javaPackageName == null) {
/*  300 */         throw new ModelerException("nometadatamodeler.error.cannotMapNamespace", serviceQName.getNamespaceURI());
/*      */       }
/*      */ 
/*      */       
/*  304 */       serviceInterface = javaPackageName + ".";
/*  305 */       serviceInterface = serviceInterface + this.env.getNames().validJavaClassName(wsdlService.getName());
/*      */     } 
/*      */     
/*  308 */     Service service = new Service(serviceQName, new JavaInterface(serviceInterface, serviceInterface + "Impl"));
/*      */ 
/*      */     
/*  311 */     setDocumentationIfPresent((ModelObject)service, wsdlService.getDocumentation());
/*      */     
/*  313 */     boolean gotOne = false;
/*  314 */     for (Iterator<Port> iter = wsdlService.ports(); iter.hasNext(); ) {
/*  315 */       if (gotOne) {
/*  316 */         throw new ModelerException("nometadatamodeler.error.moreThanOnePortDefinition", wsdlService.getName());
/*      */       }
/*      */ 
/*      */       
/*  320 */       Port port = iter.next();
/*      */       
/*  322 */       boolean processed = processPort(port, service, document);
/*  323 */       if (!processed) {
/*  324 */         throw new ModelerException("nometadatamodeler.error.failedToProcessPort", port.getName());
/*      */       }
/*      */ 
/*      */       
/*  328 */       gotOne = true;
/*      */     } 
/*      */     
/*  331 */     if (!gotOne) {
/*  332 */       throw new ModelerException("nometadatamodeler.error.noPortsInService", wsdlService.getName());
/*      */     }
/*      */ 
/*      */     
/*  336 */     model.addService(service);
/*  337 */     verifyServiceInterface(service);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean processPort(Port wsdlPort, Service service, WSDLDocument document) {
/*      */     try {
/*  344 */       QName portQName = getQNameOf((GloballyKnown)wsdlPort);
/*      */       
/*  346 */       if (this.modelInfo.getPortName() != null && !this.modelInfo.getPortName().equals(portQName))
/*      */       {
/*      */         
/*  349 */         throw new ModelerException("nometadatamodeler.error.incorrectPortName", portQName.toString());
/*      */       }
/*      */       
/*  352 */       Port port = new Port(portQName);
/*  353 */       setDocumentationIfPresent((ModelObject)port, wsdlPort.getDocumentation());
/*      */       
/*  355 */       SOAPAddress soapAddress = (SOAPAddress)getExtensionOfType((Extensible)wsdlPort, SOAPAddress.class);
/*      */       
/*  357 */       if (soapAddress == null) {
/*  358 */         warn("wsdlmodeler.warning.ignoringNonSOAPPort.noAddress", wsdlPort.getName());
/*      */         
/*  360 */         return false;
/*      */       } 
/*      */       
/*  363 */       port.setAddress(soapAddress.getLocation());
/*  364 */       Binding binding = wsdlPort.resolveBinding((AbstractDocument)document);
/*  365 */       PortType portType = binding.resolvePortType((AbstractDocument)document);
/*      */ 
/*      */       
/*  368 */       SOAPBinding soapBinding = (SOAPBinding)getExtensionOfType((Extensible)binding, SOAPBinding.class);
/*      */ 
/*      */       
/*  371 */       if (soapBinding == null) {
/*  372 */         warn("wsdlmodeler.warning.ignoringNonSOAPPort", wsdlPort.getName());
/*      */         
/*  374 */         return false;
/*      */       } 
/*      */       
/*  377 */       if (soapBinding.getTransport() == null || !soapBinding.getTransport().equals("http://schemas.xmlsoap.org/soap/http")) {
/*      */ 
/*      */ 
/*      */         
/*  381 */         warn("wsdlmodeler.warning.ignoringSOAPBinding.nonHTTPTransport", wsdlPort.getName());
/*      */         
/*  383 */         return false;
/*      */       } 
/*      */       
/*  386 */       boolean hasOverloadedOperations = false;
/*  387 */       Set<String> operationNames = new HashSet();
/*  388 */       for (Iterator<Operation> iter = portType.operations(); iter.hasNext(); ) {
/*  389 */         Operation operation = iter.next();
/*      */         
/*  391 */         if (operationNames.contains(operation.getName())) {
/*  392 */           hasOverloadedOperations = true;
/*      */           break;
/*      */         } 
/*  395 */         operationNames.add(operation.getName());
/*      */       } 
/*      */       
/*  398 */       if (hasOverloadedOperations) {
/*  399 */         throw new ModelerException("nometadatamodeler.error.overloadedOperationsFound", wsdlPort.getName());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  404 */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortName", getQNameOf((GloballyKnown)wsdlPort));
/*      */       
/*  406 */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName", getQNameOf((GloballyKnown)portType));
/*      */       
/*  408 */       port.setProperty("com.sun.xml.rpc.processor.model.WSDLBindingName", getQNameOf((GloballyKnown)binding));
/*      */ 
/*      */       
/*  411 */       boolean hasOperations = false;
/*  412 */       for (Iterator<BindingOperation> iterator = binding.operations(); iterator.hasNext(); ) {
/*  413 */         BindingOperation bindingOperation = iterator.next();
/*      */         
/*  415 */         Operation portTypeOperation = null;
/*      */         
/*  417 */         Set<Operation> operations = portType.getOperationsNamed(bindingOperation.getName());
/*      */         
/*  419 */         if (operations.size() == 0)
/*      */         {
/*      */           
/*  422 */           throw new ModelerException("wsdlmodeler.invalid.bindingOperation.notInPortType", new Object[] { bindingOperation.getName(), binding.getName() });
/*      */         }
/*      */ 
/*      */         
/*  426 */         if (operations.size() == 1) {
/*  427 */           portTypeOperation = operations.iterator().next();
/*      */         }
/*      */         else {
/*      */           
/*  431 */           boolean found = false;
/*  432 */           String expectedInputName = bindingOperation.getInput().getName();
/*      */           
/*  434 */           String expectedOutputName = bindingOperation.getOutput().getName();
/*      */ 
/*      */           
/*  437 */           Iterator<Operation> iter2 = operations.iterator();
/*  438 */           while (iter2.hasNext()) {
/*      */ 
/*      */             
/*  441 */             Operation candidateOperation = iter2.next();
/*      */ 
/*      */ 
/*      */             
/*  445 */             if (expectedInputName == null)
/*      */             {
/*      */               
/*  448 */               throw new ModelerException("wsdlmodeler.invalid.bindingOperation.missingInputName", new Object[] { bindingOperation.getName(), binding.getName() });
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  453 */             if (expectedOutputName == null)
/*      */             {
/*      */               
/*  456 */               throw new ModelerException("wsdlmodeler.invalid.bindingOperation.missingOutputName", new Object[] { bindingOperation.getName(), binding.getName() });
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*  461 */             if (expectedInputName.equals(candidateOperation.getInput().getName()) && expectedOutputName.equals(candidateOperation.getOutput().getName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  466 */               if (found)
/*      */               {
/*      */                 
/*  469 */                 throw new ModelerException("wsdlmodeler.invalid.bindingOperation.multipleMatchingOperations", new Object[] { bindingOperation.getName(), binding.getName() });
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  476 */               found = true;
/*  477 */               portTypeOperation = candidateOperation;
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  482 */           if (!found)
/*      */           {
/*      */             
/*  485 */             throw new ModelerException("wsdlmodeler.invalid.bindingOperation.notFound", new Object[] { bindingOperation.getName(), binding.getName() });
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  492 */         Operation operation = processSOAPOperation(new ProcessSOAPOperationInfo(port, wsdlPort, portTypeOperation, bindingOperation, soapBinding, document));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  500 */         if (operation != null) {
/*  501 */           port.addOperation(operation);
/*  502 */           hasOperations = true;
/*      */         } 
/*      */       } 
/*      */       
/*  506 */       if (!hasOperations)
/*      */       {
/*      */         
/*  509 */         warn("wsdlmodeler.warning.noOperationsInPort", wsdlPort.getName());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  514 */       port.setClientHandlerChainInfo(this.modelInfo.getClientHandlerChainInfo());
/*      */       
/*  516 */       port.setServerHandlerChainInfo(this.modelInfo.getServerHandlerChainInfo());
/*      */ 
/*      */       
/*  519 */       service.addPort(port);
/*  520 */       createJavaInterfaceForPort(port);
/*  521 */       verifyJavaInterface(port);
/*      */ 
/*      */       
/*  524 */       String stubClassName = this.env.getNames().stubFor(port, null);
/*  525 */       String tieClassName = this.env.getNames().tieFor(port, null);
/*      */       
/*  527 */       port.setProperty("com.sun.xml.rpc.processor.model.StubClassName", stubClassName);
/*      */       
/*  529 */       port.setProperty("com.sun.xml.rpc.processor.model.TieClassName", tieClassName);
/*      */ 
/*      */       
/*  532 */       return true;
/*      */     }
/*  534 */     catch (NoSuchEntityException e) {
/*      */ 
/*      */       
/*  537 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Operation processSOAPOperation(ProcessSOAPOperationInfo info) {
/*  543 */     Operation operation = new Operation(new QName(null, info.bindingOperation.getName()));
/*      */ 
/*      */     
/*  546 */     setDocumentationIfPresent((ModelObject)operation, info.portTypeOperation.getDocumentation());
/*      */ 
/*      */ 
/*      */     
/*  550 */     if (info.portTypeOperation.getStyle() != OperationStyle.REQUEST_RESPONSE && info.portTypeOperation.getStyle() != OperationStyle.ONE_WAY) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  555 */       warn("wsdlmodeler.warning.ignoringOperation.notRequestResponse", info.portTypeOperation.getName());
/*      */       
/*  557 */       return null;
/*      */     } 
/*      */     
/*  560 */     SOAPStyle soapStyle = info.soapBinding.getStyle();
/*      */ 
/*      */     
/*  563 */     SOAPOperation soapOperation = (SOAPOperation)getExtensionOfType((Extensible)info.bindingOperation, SOAPOperation.class);
/*      */ 
/*      */     
/*  566 */     if (soapOperation != null) {
/*  567 */       if (soapOperation.getStyle() != null) {
/*  568 */         soapStyle = soapOperation.getStyle();
/*      */       }
/*  570 */       if (soapOperation.getSOAPAction() != null) {
/*  571 */         operation.setSOAPAction(soapOperation.getSOAPAction());
/*      */       }
/*      */     } 
/*      */     
/*  575 */     operation.setStyle(soapStyle);
/*      */     
/*  577 */     String uniqueOperationName = info.portTypeOperation.getName();
/*      */     
/*  579 */     info.operation = operation;
/*  580 */     info.uniqueOperationName = uniqueOperationName;
/*      */     
/*  582 */     if (soapStyle == SOAPStyle.RPC)
/*      */     {
/*      */       
/*  585 */       return processSOAPOperationRPCStyle(info);
/*      */     }
/*      */ 
/*      */     
/*  589 */     throw new ModelerException("nometadatamodeler.error.documentStyleOperation", operation.getName().getLocalPart());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Operation processSOAPOperationRPCStyle(ProcessSOAPOperationInfo info) {
/*      */     RPCResponseStructureType rPCResponseStructureType;
/*  599 */     boolean isRequestResponse = (info.portTypeOperation.getStyle() == OperationStyle.REQUEST_RESPONSE);
/*      */ 
/*      */ 
/*      */     
/*  603 */     Request request = new Request();
/*  604 */     Response response = new Response();
/*      */     
/*  606 */     SOAPBody soapRequestBody = (SOAPBody)getExtensionOfType((Extensible)info.bindingOperation.getInput(), SOAPBody.class);
/*      */     
/*  608 */     if (soapRequestBody == null)
/*      */     {
/*      */       
/*  611 */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.inputMissingSoapBody", new Object[] { info.bindingOperation.getName() });
/*      */     }
/*      */ 
/*      */     
/*  615 */     SOAPBody soapResponseBody = null;
/*      */ 
/*      */     
/*  618 */     if (isRequestResponse) {
/*  619 */       soapResponseBody = (SOAPBody)getExtensionOfType((Extensible)info.bindingOperation.getOutput(), SOAPBody.class);
/*      */       
/*  621 */       if (soapResponseBody == null)
/*      */       {
/*      */         
/*  624 */         throw new ModelerException("wsdlmodeler.invalid.bindingOperation.outputMissingSoapBody", new Object[] { info.bindingOperation.getName() });
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  630 */     if (soapRequestBody.isLiteral() || !tokenListContains(soapRequestBody.getEncodingStyle(), "http://schemas.xmlsoap.org/soap/encoding/") || (soapResponseBody != null && (soapResponseBody.isLiteral() || !tokenListContains(soapResponseBody.getEncodingStyle(), "http://schemas.xmlsoap.org/soap/encoding/"))))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  637 */       throw new ModelerException("nometadatamodeler.error.operationNotEncoded", info.portTypeOperation.getName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  642 */     String requestNamespaceURI = soapRequestBody.getNamespace();
/*  643 */     if (requestNamespaceURI == null)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  649 */       throw new ModelerException("wsdlmodeler.invalid.bindingOperation.inputSoapBody.missingNamespace", new Object[] { info.bindingOperation.getName() });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  654 */     String responseNamespaceURI = null;
/*      */ 
/*      */     
/*  657 */     if (isRequestResponse) {
/*  658 */       responseNamespaceURI = soapResponseBody.getNamespace();
/*  659 */       if (responseNamespaceURI == null)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  665 */         throw new ModelerException("wsdlmodeler.invalid.bindingOperation.outputSoapBody.missingNamespace", new Object[] { info.bindingOperation.getName() });
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  671 */     String structureNamePrefix = null;
/*  672 */     QName portTypeName = (QName)info.modelPort.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*      */     
/*  674 */     if (portTypeName != null) {
/*  675 */       structureNamePrefix = getNonQualifiedNameFor(portTypeName);
/*      */     } else {
/*  677 */       structureNamePrefix = getNonQualifiedNameFor(info.modelPort.getName());
/*      */     } 
/*  679 */     structureNamePrefix = structureNamePrefix + "_";
/*      */     
/*  681 */     QName requestBodyName = new QName(requestNamespaceURI, info.portTypeOperation.getName());
/*      */     
/*  683 */     RPCRequestUnorderedStructureType rPCRequestUnorderedStructureType = new RPCRequestUnorderedStructureType(requestBodyName);
/*      */     
/*  685 */     JavaStructureType requestBodyJavaType = new JavaStructureType(makePackageQualified(StringUtils.capitalize(structureNamePrefix + info.uniqueOperationName) + "_RequestStruct", requestBodyName), false, rPCRequestUnorderedStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  691 */     rPCRequestUnorderedStructureType.setJavaType((JavaType)requestBodyJavaType);
/*      */     
/*  693 */     Block requestBodyBlock = new Block(requestBodyName, (AbstractType)rPCRequestUnorderedStructureType);
/*  694 */     request.addBodyBlock(requestBodyBlock);
/*      */ 
/*      */     
/*  697 */     SOAPStructureType responseBodyType = null;
/*  698 */     JavaStructureType responseBodyJavaType = null;
/*  699 */     Block responseBodyBlock = null;
/*  700 */     if (isRequestResponse) {
/*  701 */       QName responseBodyName = new QName(responseNamespaceURI, info.portTypeOperation.getName() + "Response");
/*      */       
/*  703 */       rPCResponseStructureType = new RPCResponseStructureType(responseBodyName);
/*  704 */       responseBodyJavaType = new JavaStructureType(makePackageQualified(StringUtils.capitalize(structureNamePrefix + info.uniqueOperationName + "_ResponseStruct"), responseBodyName), false, rPCResponseStructureType);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  709 */       rPCResponseStructureType.setJavaType((JavaType)responseBodyJavaType);
/*      */       
/*  711 */       responseBodyBlock = new Block(responseBodyName, (AbstractType)rPCResponseStructureType);
/*  712 */       response.addBodyBlock(responseBodyBlock);
/*      */     } 
/*      */     
/*  715 */     if (soapRequestBody.getParts() != null) {
/*      */ 
/*      */ 
/*      */       
/*  719 */       warn("wsdlmodeler.warning.ignoringOperation.cannotHandleBodyPartsAttribute", info.portTypeOperation.getName());
/*      */       
/*  721 */       return null;
/*      */     } 
/*      */     
/*  724 */     Message inputMessage = info.portTypeOperation.getInput().resolveMessage((AbstractDocument)info.document);
/*      */ 
/*      */     
/*  727 */     Message outputMessage = null;
/*      */ 
/*      */     
/*  730 */     if (isRequestResponse) {
/*  731 */       outputMessage = info.portTypeOperation.getOutput().resolveMessage((AbstractDocument)info.document);
/*      */     }
/*      */ 
/*      */     
/*  735 */     String parameterOrder = info.portTypeOperation.getParameterOrder();
/*  736 */     List<String> parameterList = null;
/*  737 */     boolean buildParameterList = false;
/*      */     
/*  739 */     if (parameterOrder != null) {
/*  740 */       parameterList = XmlUtil.parseTokenList(parameterOrder);
/*      */     } else {
/*  742 */       parameterList = new ArrayList();
/*  743 */       buildParameterList = true;
/*      */     } 
/*      */     
/*  746 */     Set<String> partNames = new HashSet();
/*  747 */     Set<String> inputParameterNames = new HashSet();
/*  748 */     Set<String> outputParameterNames = new HashSet();
/*  749 */     String resultParameterName = null;
/*      */     
/*  751 */     for (Iterator<MessagePart> iter = inputMessage.parts(); iter.hasNext(); ) {
/*  752 */       MessagePart part = iter.next();
/*  753 */       if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/*  754 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { inputMessage.getName(), part.getName() });
/*      */       }
/*      */ 
/*      */       
/*  758 */       partNames.add(part.getName());
/*  759 */       inputParameterNames.add(part.getName());
/*  760 */       if (buildParameterList) {
/*  761 */         parameterList.add(part.getName());
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  766 */     if (isRequestResponse) {
/*  767 */       for (Iterator<MessagePart> iterator4 = outputMessage.parts(); iterator4.hasNext(); ) {
/*  768 */         MessagePart part = iterator4.next();
/*  769 */         if (part.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/*  770 */           throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { outputMessage.getName(), part.getName() });
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  775 */         partNames.add(part.getName());
/*  776 */         if (buildParameterList && resultParameterName == null) {
/*      */ 
/*      */           
/*  779 */           resultParameterName = part.getName(); continue;
/*      */         } 
/*  781 */         outputParameterNames.add(part.getName());
/*  782 */         if (buildParameterList && 
/*  783 */           !inputParameterNames.contains(part.getName())) {
/*  784 */           parameterList.add(part.getName());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  791 */     if (!buildParameterList) {
/*      */ 
/*      */       
/*  794 */       for (Iterator<String> iterator4 = parameterList.iterator(); iterator4.hasNext(); ) {
/*  795 */         String name = iterator4.next();
/*  796 */         if (!partNames.contains(name)) {
/*  797 */           throw new ModelerException("wsdlmodeler.invalid.parameterorder.parameter", new Object[] { name, info.operation.getName().getLocalPart() });
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  802 */         partNames.remove(name);
/*      */       } 
/*      */ 
/*      */       
/*  806 */       if (partNames.size() > 1) {
/*  807 */         throw new ModelerException("wsdlmodeler.invalid.parameterOrder.tooManyUnmentionedParts", new Object[] { info.operation.getName().getLocalPart() });
/*      */       }
/*      */ 
/*      */       
/*  811 */       if (partNames.size() == 1) {
/*      */ 
/*      */         
/*  814 */         String partName = partNames.iterator().next();
/*  815 */         if (outputParameterNames.contains(partName)) {
/*  816 */           resultParameterName = partName;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  821 */     if (resultParameterName == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  828 */       info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType", "true");
/*      */     }
/*      */     else {
/*      */       
/*  832 */       MessagePart part = outputMessage.getPart(resultParameterName);
/*  833 */       SOAPType soapType = this.analyzer.schemaTypeToSOAPType(part.getDescriptor());
/*      */       
/*  835 */       SOAPStructureMember member = new SOAPStructureMember(new QName(null, part.getName()), soapType);
/*      */       
/*  837 */       JavaStructureMember javaMember = new JavaStructureMember(this.env.getNames().validJavaMemberName(part.getName()), soapType.getJavaType(), member, false);
/*      */ 
/*      */       
/*  840 */       javaMember.setReadMethod(this.env.getNames().getJavaMemberReadMethod(javaMember));
/*      */       
/*  842 */       javaMember.setWriteMethod(this.env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */       
/*  844 */       member.setJavaStructureMember(javaMember);
/*  845 */       rPCResponseStructureType.add(member);
/*  846 */       responseBodyJavaType.add(javaMember);
/*  847 */       Parameter parameter = new Parameter(this.env.getNames().validJavaMemberName(part.getName()));
/*      */       
/*  849 */       parameter.setEmbedded(true);
/*  850 */       parameter.setType((AbstractType)soapType);
/*  851 */       parameter.setBlock(responseBodyBlock);
/*  852 */       response.addParameter(parameter);
/*  853 */       info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter", parameter.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  860 */     List<String> definitiveParameterList = new ArrayList();
/*      */     
/*  862 */     for (Iterator<String> iterator = parameterList.iterator(); iterator.hasNext(); ) {
/*  863 */       String name = iterator.next();
/*  864 */       boolean isInput = inputParameterNames.contains(name);
/*  865 */       boolean isOutput = outputParameterNames.contains(name);
/*  866 */       SOAPType soapType = null;
/*  867 */       Parameter inParameter = null;
/*      */       
/*  869 */       if (isInput && isOutput)
/*      */       {
/*      */         
/*  872 */         if (!inputMessage.getPart(name).getDescriptor().equals(outputMessage.getPart(name).getDescriptor()))
/*      */         {
/*      */           
/*  875 */           throw new ModelerException("wsdlmodeler.invalid.parameter.differentTypes", new Object[] { name, info.operation.getName().getLocalPart() });
/*      */         }
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  882 */       if (isInput) {
/*  883 */         MessagePart part = inputMessage.getPart(name);
/*  884 */         soapType = this.analyzer.schemaTypeToSOAPType(part.getDescriptor());
/*  885 */         SOAPStructureMember member = new SOAPStructureMember(new QName(null, part.getName()), soapType);
/*      */         
/*  887 */         JavaStructureMember javaMember = new JavaStructureMember(this.env.getNames().validJavaMemberName(part.getName()), soapType.getJavaType(), member, false);
/*      */ 
/*      */         
/*  890 */         javaMember.setReadMethod(this.env.getNames().getJavaMemberReadMethod(javaMember));
/*      */         
/*  892 */         javaMember.setWriteMethod(this.env.getNames().getJavaMemberWriteMethod(javaMember));
/*      */         
/*  894 */         member.setJavaStructureMember(javaMember);
/*  895 */         rPCRequestUnorderedStructureType.add(member);
/*  896 */         requestBodyJavaType.add(javaMember);
/*  897 */         inParameter = new Parameter(this.env.getNames().validJavaMemberName(part.getName()));
/*      */         
/*  899 */         inParameter.setEmbedded(true);
/*  900 */         inParameter.setType((AbstractType)soapType);
/*  901 */         inParameter.setBlock(requestBodyBlock);
/*  902 */         request.addParameter(inParameter);
/*  903 */         definitiveParameterList.add(inParameter.getName());
/*      */       } 
/*  905 */       if (isOutput)
/*      */       {
/*      */         
/*  908 */         throw new ModelerException("nometadatamodeler.outputParameterEncountered", new Object[] { info.portTypeOperation.getName(), name });
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  916 */     Set<QName> duplicateNames = new HashSet();
/*      */ 
/*      */     
/*  919 */     Set<QName> faultNames = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  924 */     for (Iterator<BindingFault> iterator2 = info.bindingOperation.faults(); iterator2.hasNext(); ) {
/*  925 */       BindingFault bindingFault = iterator2.next();
/*      */       
/*  927 */       Fault portTypeFault = null;
/*  928 */       Iterator<Fault> iter2 = info.portTypeOperation.faults();
/*  929 */       while (iter2.hasNext()) {
/*      */         
/*  931 */         Fault aFault = iter2.next();
/*      */ 
/*      */         
/*  934 */         if (aFault.getName().equals(bindingFault.getName())) {
/*  935 */           if (portTypeFault != null)
/*      */           {
/*      */             
/*  938 */             throw new ModelerException("wsdlmodeler.invalid.bindingFault.notUnique", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  943 */           portTypeFault = aFault;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  948 */       if (portTypeFault == null)
/*      */       {
/*      */         
/*  951 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.notFound", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  957 */       SOAPFault soapFault = (SOAPFault)getExtensionOfType((Extensible)bindingFault, SOAPFault.class);
/*      */       
/*  959 */       if (soapFault == null)
/*      */       {
/*      */         
/*  962 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.outputMissingSoapFault", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  968 */       Message faultMessage = portTypeFault.resolveMessage((AbstractDocument)info.document);
/*      */       
/*  970 */       Iterator<MessagePart> iterator4 = faultMessage.parts();
/*  971 */       if (!iterator4.hasNext())
/*      */       {
/*      */         
/*  974 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.emptyMessage", new Object[] { bindingFault.getName(), faultMessage.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  979 */       String faultNamespaceURI = soapFault.getNamespace();
/*  980 */       MessagePart faultPart = iterator4.next();
/*  981 */       QName faultQName = new QName(faultNamespaceURI, faultPart.getName());
/*      */       
/*  983 */       if (faultNames.contains(faultQName)) {
/*  984 */         duplicateNames.add(faultQName); continue;
/*      */       } 
/*  986 */       faultNames.add(faultQName);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  991 */     for (Iterator<BindingFault> iterator1 = info.bindingOperation.faults(); iterator1.hasNext(); ) {
/*  992 */       BindingFault bindingFault = iterator1.next();
/*      */       
/*  994 */       Fault portTypeFault = null;
/*  995 */       Iterator<Fault> iter2 = info.portTypeOperation.faults();
/*  996 */       while (iter2.hasNext()) {
/*      */         
/*  998 */         Fault aFault = iter2.next();
/*      */ 
/*      */         
/* 1001 */         if (aFault.getName().equals(bindingFault.getName())) {
/* 1002 */           if (portTypeFault != null)
/*      */           {
/*      */             
/* 1005 */             throw new ModelerException("wsdlmodeler.invalid.bindingFault.notUnique", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1010 */           portTypeFault = aFault;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1015 */       if (portTypeFault == null)
/*      */       {
/*      */         
/* 1018 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.notFound", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
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
/* 1029 */       Fault fault = new Fault(portTypeFault.getMessage().getLocalPart());
/*      */       
/* 1031 */       SOAPFault soapFault = (SOAPFault)getExtensionOfType((Extensible)bindingFault, SOAPFault.class);
/*      */       
/* 1033 */       if (soapFault == null)
/*      */       {
/*      */         
/* 1036 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.outputMissingSoapFault", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1042 */       if (soapFault.isLiteral() || !tokenListContains(soapFault.getEncodingStyle(), "http://schemas.xmlsoap.org/soap/encoding/")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1047 */         warn("wsdlmodeler.warning.ignoringFault.notEncoded", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */ 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1053 */       String faultNamespaceURI = soapFault.getNamespace();
/* 1054 */       if (faultNamespaceURI == null)
/*      */       {
/*      */         
/* 1057 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.missingNamespace", new Object[] { bindingFault.getName(), info.bindingOperation.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1063 */       Message faultMessage = portTypeFault.resolveMessage((AbstractDocument)info.document);
/*      */       
/* 1065 */       Iterator<MessagePart> iterator4 = faultMessage.parts();
/* 1066 */       if (!iterator4.hasNext())
/*      */       {
/*      */         
/* 1069 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.emptyMessage", new Object[] { bindingFault.getName(), faultMessage.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1074 */       MessagePart faultPart = iterator4.next();
/*      */       
/* 1076 */       if (isIncorrectFaultPartName(faultPart.getName())) {
/* 1077 */         throw new ModelerException("nometadatamodeler.error.incorrectFaultPartName", new Object[] { info.portTypeOperation.getName(), bindingFault.getName(), faultPart.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1083 */       QName faultQName = new QName(faultNamespaceURI, faultPart.getName());
/*      */ 
/*      */ 
/*      */       
/* 1087 */       if (duplicateNames.contains(faultQName)) {
/* 1088 */         warn("wsdlmodeler.duplicate.fault.part.name", new Object[] { bindingFault.getName(), info.portTypeOperation.getName(), faultPart.getName() });
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1093 */       if (iterator4.hasNext())
/*      */       {
/*      */         
/* 1096 */         throw new ModelerException("wsdlmodeler.invalid.bindingFault.messageHasMoreThanOnePart", new Object[] { bindingFault.getName(), faultMessage.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1102 */       if (faultPart.getDescriptorKind() != SchemaKinds.XSD_TYPE) {
/* 1103 */         throw new ModelerException("wsdlmodeler.invalid.message.partMustHaveTypeDescriptor", new Object[] { faultMessage.getName(), faultPart.getName() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1109 */       if (isIncorrectFaultPartType(faultPart.getDescriptor())) {
/* 1110 */         throw new ModelerException("nometadatamodeler.error.incorrectFaultPartType", new Object[] { info.portTypeOperation.getName(), bindingFault.getName(), faultPart.getName(), faultPart.getDescriptor() });
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1118 */       SOAPType faultType = this.analyzer.schemaTypeToSOAPType(faultPart.getDescriptor());
/*      */       
/* 1120 */       Block faultBlock = new Block(faultQName, (AbstractType)faultType);
/* 1121 */       fault.setBlock(faultBlock);
/* 1122 */       response.addFaultBlock(faultBlock);
/* 1123 */       info.operation.addFault(fault);
/*      */     } 
/*      */ 
/*      */     
/* 1127 */     boolean explicitServiceContext = Boolean.valueOf(this.options.getProperty("explicitServiceContext")).booleanValue();
/*      */     
/* 1129 */     Iterator<Extension> iterator3 = info.bindingOperation.getInput().extensions();
/* 1130 */     while (iterator3.hasNext()) {
/*      */       
/* 1132 */       Extension extension = iterator3.next();
/* 1133 */       if (extension instanceof SOAPHeader) {
/* 1134 */         SOAPHeader header = (SOAPHeader)extension;
/* 1135 */         warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), info.bindingOperation.getName() });
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1143 */     if (isRequestResponse) {
/* 1144 */       Iterator<Extension> iterator4 = info.bindingOperation.getOutput().extensions();
/* 1145 */       while (iterator4.hasNext()) {
/*      */         
/* 1147 */         Extension extension = iterator4.next();
/* 1148 */         if (extension instanceof SOAPHeader) {
/* 1149 */           SOAPHeader header = (SOAPHeader)extension;
/* 1150 */           warn("wsdlmodeler.warning.ignoringHeader", new Object[] { header.getPart(), info.bindingOperation.getName() });
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1159 */     info.operation.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder", definitiveParameterList);
/*      */ 
/*      */     
/* 1162 */     info.operation.setRequest(request);
/*      */ 
/*      */     
/* 1165 */     if (isRequestResponse) {
/* 1166 */       info.operation.setResponse(response);
/*      */     }
/* 1168 */     return info.operation;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createJavaInterfaceForPort(Port port) {
/* 1173 */     QName portTypeName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*      */     
/* 1175 */     QName bindingName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*      */     
/* 1177 */     String interfaceName = null;
/* 1178 */     if (this.modelInfo.getInterfaceName() != null) {
/* 1179 */       interfaceName = this.modelInfo.getInterfaceName();
/*      */     }
/* 1181 */     else if (portTypeName != null) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1186 */       interfaceName = makePackageQualified(this.env.getNames().validJavaClassName(getNonQualifiedNameFor(portTypeName)), portTypeName);
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1192 */       interfaceName = makePackageQualified(this.env.getNames().validJavaClassName(getNonQualifiedNameFor(port.getName())), port.getName());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1199 */     JavaInterface intf = new JavaInterface(interfaceName);
/*      */     
/* 1201 */     Set methodNames = new HashSet();
/* 1202 */     Set methodSignatures = new HashSet();
/*      */     
/* 1204 */     for (Iterator<Operation> iter = port.getOperations(); iter.hasNext(); ) {
/* 1205 */       Operation operation = iter.next();
/* 1206 */       createJavaMethodForOperation(port, operation, intf, methodNames, methodSignatures);
/*      */     } 
/*      */     
/* 1209 */     port.setJavaInterface(intf);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createJavaMethodForOperation(Port port, Operation operation, JavaInterface intf, Set<String> methodNames, Set<String> methodSignatures) {
/* 1215 */     String candidateName = this.env.getNames().validJavaMemberName(operation.getName().getLocalPart());
/*      */     
/* 1217 */     JavaMethod method = new JavaMethod(candidateName);
/*      */     
/* 1219 */     Request request = operation.getRequest();
/* 1220 */     Iterator requestBodyBlocks = request.getBodyBlocks();
/* 1221 */     Block requestBlock = requestBodyBlocks.hasNext() ? request.getBodyBlocks().next() : null;
/*      */ 
/*      */ 
/*      */     
/* 1225 */     Response response = operation.getResponse();
/* 1226 */     Iterator responseBodyBlocks = null;
/* 1227 */     Block responseBlock = null;
/* 1228 */     if (response != null) {
/* 1229 */       responseBodyBlocks = response.getBodyBlocks();
/* 1230 */       responseBlock = responseBodyBlocks.hasNext() ? response.getBodyBlocks().next() : null;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1239 */     String signature = candidateName;
/*      */     
/* 1241 */     List parameterOrder = (List)operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.parameterOrder");
/*      */     
/* 1243 */     if (parameterOrder == null) {
/* 1244 */       for (Iterator<Parameter> iterator = request.getParameters(); iterator.hasNext(); ) {
/* 1245 */         Parameter parameter = iterator.next();
/*      */         
/* 1247 */         if (parameter.getJavaParameter() != null) {
/* 1248 */           throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */         
/* 1252 */         JavaType parameterType = parameter.getType().getJavaType();
/* 1253 */         JavaParameter javaParameter = new JavaParameter(this.env.getNames().validJavaMemberName(parameter.getName()), parameterType, parameter, (parameter.getLinkedParameter() != null));
/*      */ 
/*      */ 
/*      */         
/* 1257 */         method.addParameter(javaParameter);
/* 1258 */         parameter.setJavaParameter(javaParameter);
/*      */         
/* 1260 */         signature = signature + "%" + parameterType.getName();
/*      */       } 
/*      */       
/* 1263 */       boolean operationHasVoidReturnType = (operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType") != null);
/*      */       
/* 1265 */       Parameter resultParameter = null;
/*      */ 
/*      */       
/* 1268 */       if (response != null) {
/* 1269 */         Iterator<Parameter> iterator2 = response.getParameters();
/* 1270 */         while (iterator2.hasNext()) {
/*      */           
/* 1272 */           if (!operationHasVoidReturnType && resultParameter == null) {
/*      */ 
/*      */             
/* 1275 */             resultParameter = iterator2.next();
/*      */             
/* 1277 */             if (resultParameter.getJavaParameter() != null) {
/* 1278 */               throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart());
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1283 */             if (resultParameter.getLinkedParameter() != null)
/*      */             {
/*      */               
/* 1286 */               throw new ModelerException("wsdlmodeler.resultIsInOutParameter", operation.getName().getLocalPart());
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1291 */             if (resultParameter.getBlock() != responseBlock)
/*      */             {
/*      */               
/* 1294 */               throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart());
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1299 */             JavaType returnType = resultParameter.getType().getJavaType();
/*      */             
/* 1301 */             method.setReturnType(returnType);
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1306 */           Parameter parameter = iterator2.next();
/*      */           
/* 1308 */           if (parameter.getJavaParameter() != null) {
/* 1309 */             throw new ModelerException("wsdlmodeler.invalidOperation", operation.getName().getLocalPart());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1314 */           JavaParameter javaParameter = null;
/* 1315 */           if (parameter.getLinkedParameter() != null) {
/* 1316 */             javaParameter = parameter.getLinkedParameter().getJavaParameter();
/*      */           }
/*      */           
/* 1319 */           JavaType parameterType = parameter.getType().getJavaType();
/*      */           
/* 1321 */           parameterType.setHolder(true);
/* 1322 */           parameterType.setHolderPresent(false);
/* 1323 */           if (javaParameter == null) {
/* 1324 */             javaParameter = new JavaParameter(this.env.getNames().validJavaMemberName(parameter.getName()), parameterType, parameter, true);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1331 */           parameter.setJavaParameter(javaParameter);
/* 1332 */           if (parameter.getLinkedParameter() == null) {
/* 1333 */             method.addParameter(javaParameter);
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1340 */       if (response == null || operationHasVoidReturnType) {
/* 1341 */         method.setReturnType((JavaType)this.javaTypes.VOID_JAVATYPE);
/*      */       
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1347 */       boolean operationHasVoidReturnType = (operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.operationHasVoidReturnType") != null);
/*      */       
/* 1349 */       for (Iterator<String> iterator = parameterOrder.iterator(); iterator.hasNext(); ) {
/* 1350 */         String parameterName = iterator.next();
/* 1351 */         Parameter requestParameter = request.getParameterByName(parameterName);
/*      */ 
/*      */ 
/*      */         
/* 1355 */         Parameter responseParameter = (response != null) ? response.getParameterByName(parameterName) : null;
/*      */ 
/*      */         
/* 1358 */         if (requestParameter == null && responseParameter == null)
/*      */         {
/*      */           
/* 1361 */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1366 */         if (requestParameter != null) {
/* 1367 */           Parameter linkedParameter = requestParameter.getLinkedParameter();
/*      */           
/* 1369 */           if (responseParameter == null || linkedParameter == null) {
/*      */ 
/*      */             
/* 1372 */             JavaType javaType = requestParameter.getType().getJavaType();
/*      */             
/* 1374 */             JavaParameter javaParameter1 = new JavaParameter(this.env.getNames().validJavaMemberName(requestParameter.getName()), javaType, requestParameter, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1380 */             method.addParameter(javaParameter1);
/* 1381 */             requestParameter.setJavaParameter(javaParameter1);
/* 1382 */             signature = signature + "%" + javaType.getName();
/*      */             
/*      */             continue;
/*      */           } 
/* 1386 */           if (responseParameter != linkedParameter)
/*      */           {
/* 1388 */             throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1393 */           JavaType parameterType = responseParameter.getType().getJavaType();
/*      */           
/* 1395 */           JavaParameter javaParameter = new JavaParameter(this.env.getNames().validJavaMemberName(responseParameter.getName()), parameterType, responseParameter, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1401 */           parameterType.setHolder(true);
/* 1402 */           parameterType.setHolderPresent(false);
/* 1403 */           requestParameter.setJavaParameter(javaParameter);
/* 1404 */           responseParameter.setJavaParameter(javaParameter);
/* 1405 */           method.addParameter(javaParameter);
/* 1406 */           requestParameter.setJavaParameter(javaParameter);
/* 1407 */           responseParameter.setJavaParameter(javaParameter);
/* 1408 */           signature = signature + "%" + parameterType.getName(); continue;
/*      */         } 
/* 1410 */         if (responseParameter != null) {
/*      */ 
/*      */           
/* 1413 */           Parameter linkedParameter = responseParameter.getLinkedParameter();
/*      */           
/* 1415 */           if (linkedParameter != null)
/*      */           {
/*      */             
/* 1418 */             throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart());
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1423 */           JavaType parameterType = responseParameter.getType().getJavaType();
/*      */           
/* 1425 */           parameterType.setHolder(true);
/* 1426 */           parameterType.setHolderPresent(false);
/* 1427 */           JavaParameter javaParameter = new JavaParameter(this.env.getNames().validJavaMemberName(responseParameter.getName()), parameterType, responseParameter, true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1433 */           responseParameter.setJavaParameter(javaParameter);
/* 1434 */           method.addParameter(javaParameter);
/* 1435 */           signature = signature + "%" + parameterType.getName();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1440 */       String resultParameterName = (String)operation.getProperty("com.sun.xml.rpc.processor.modeler.wsdl.resultParameter");
/*      */       
/* 1442 */       if (resultParameterName == null) {
/* 1443 */         if (!operationHasVoidReturnType)
/*      */         {
/*      */           
/* 1446 */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */         
/* 1450 */         method.setReturnType((JavaType)this.javaTypes.VOID_JAVATYPE);
/*      */       } else {
/* 1452 */         if (operationHasVoidReturnType)
/*      */         {
/*      */           
/* 1455 */           throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operation.getName().getLocalPart());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/* 1460 */         Parameter resultParameter = response.getParameterByName(resultParameterName);
/*      */         
/* 1462 */         JavaType returnType = resultParameter.getType().getJavaType();
/* 1463 */         method.setReturnType(returnType);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1468 */     String operationName = candidateName;
/* 1469 */     if (methodSignatures.contains(signature)) {
/* 1470 */       throw new ModelerException("nometadatamodeler.duplicateMethodSignature", operationName);
/*      */     }
/*      */     
/* 1473 */     methodSignatures.add(signature);
/* 1474 */     methodNames.add(method.getName());
/*      */     
/* 1476 */     operation.setJavaMethod(method);
/* 1477 */     intf.addMethod(method);
/*      */     
/* 1479 */     Iterator<Fault> iter = operation.getFaults();
/* 1480 */     while (iter != null && iter.hasNext()) {
/*      */       
/* 1482 */       Fault fault = iter.next();
/* 1483 */       createJavaException(fault, port, operationName);
/*      */     } 
/*      */ 
/*      */     
/* 1487 */     for (Iterator<Fault> iterator1 = operation.getFaults(); iterator1.hasNext(); ) {
/* 1488 */       Fault fault = iterator1.next();
/* 1489 */       JavaException javaException = fault.getJavaException();
/* 1490 */       method.addException(javaException.getName());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean createJavaException(Fault fault, Port port, String operationName) {
/* 1498 */     String exceptionName = null;
/* 1499 */     String propertyName = this.env.getNames().validJavaMemberName(fault.getName());
/*      */     
/* 1501 */     SOAPType faultType = (SOAPType)fault.getBlock().getType();
/*      */     
/* 1503 */     if (faultType instanceof SOAPStructureType)
/*      */     {
/*      */       
/* 1506 */       throw new ModelerException("wsdlmodeler.invalidState.modelingOperation", operationName);
/*      */     }
/*      */     
/* 1509 */     exceptionName = makePackageQualified(this.env.getNames().validJavaClassName(fault.getName()), port.getName());
/*      */ 
/*      */     
/* 1512 */     SOAPOrderedStructureType sOAPOrderedStructureType = new SOAPOrderedStructureType(new QName(faultType.getName().getNamespaceURI(), fault.getName()));
/*      */     
/* 1514 */     QName memberName = new QName(fault.getBlock().getName().getNamespaceURI(), StringUtils.capitalize(faultType.getName().getLocalPart()));
/*      */ 
/*      */     
/* 1517 */     SOAPStructureMember soapMember = new SOAPStructureMember(memberName, faultType);
/*      */     
/* 1519 */     JavaStructureMember javaMember = new JavaStructureMember(memberName.getLocalPart(), faultType.getJavaType(), soapMember);
/*      */ 
/*      */     
/* 1522 */     soapMember.setJavaStructureMember(javaMember);
/* 1523 */     javaMember.setConstructorPos(0);
/* 1524 */     javaMember.setReadMethod("get" + memberName.getLocalPart());
/* 1525 */     javaMember.setInherited(soapMember.isInherited());
/* 1526 */     soapMember.setJavaStructureMember(javaMember);
/* 1527 */     sOAPOrderedStructureType.add(soapMember);
/*      */ 
/*      */     
/* 1530 */     JavaException existingJavaException = (JavaException)this.javaExceptions.get(exceptionName);
/*      */     
/* 1532 */     if (existingJavaException != null && 
/* 1533 */       existingJavaException.getName().equals(exceptionName) && (
/* 1534 */       (SOAPType)existingJavaException.getOwner()).getName().equals(sOAPOrderedStructureType.getName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1539 */       if (faultType instanceof SOAPStructureType) {
/* 1540 */         fault.getBlock().setType((AbstractType)existingJavaException.getOwner());
/*      */       }
/*      */       
/* 1543 */       fault.setJavaException(existingJavaException);
/* 1544 */       createRelativeJavaExceptions(fault, port, operationName);
/* 1545 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1550 */     JavaException javaException = new JavaException(exceptionName, false, sOAPOrderedStructureType);
/*      */     
/* 1552 */     sOAPOrderedStructureType.setJavaType((JavaType)javaException);
/*      */     
/* 1554 */     this.javaExceptions.put(javaException.getName(), javaException);
/*      */     
/* 1556 */     Iterator<SOAPStructureMember> members = sOAPOrderedStructureType.getMembers();
/* 1557 */     SOAPStructureMember member = null;
/*      */     
/* 1559 */     for (int i = 0; members.hasNext(); i++) {
/* 1560 */       member = members.next();
/* 1561 */       JavaStructureMember javaStructureMember = member.getJavaStructureMember();
/* 1562 */       javaStructureMember.setConstructorPos(i);
/* 1563 */       javaException.add(javaStructureMember);
/*      */     } 
/* 1565 */     if (faultType instanceof SOAPStructureType) {
/* 1566 */       fault.getBlock().setType((AbstractType)sOAPOrderedStructureType);
/*      */     }
/* 1568 */     fault.setJavaException(javaException);
/*      */     
/* 1570 */     createRelativeJavaExceptions(fault, port, operationName);
/* 1571 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createRelativeJavaExceptions(Fault fault, Port port, String operationName) {
/* 1577 */     if (fault.getParentFault() != null && fault.getParentFault().getJavaException() == null) {
/*      */ 
/*      */       
/* 1580 */       createJavaException(fault.getParentFault(), port, operationName);
/* 1581 */       fault.getParentFault().getJavaException().addSubclass((JavaStructureType)fault.getJavaException());
/*      */       
/* 1583 */       ((SOAPStructureType)fault.getParentFault().getJavaException().getOwner()).addSubtype((SOAPStructureType)fault.getJavaException().getOwner());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1588 */     Iterator<Fault> subfaults = fault.getSubfaults();
/* 1589 */     if (subfaults != null)
/*      */     {
/* 1591 */       while (subfaults.hasNext()) {
/* 1592 */         Fault subfault = subfaults.next();
/* 1593 */         if (subfault.getJavaException() == null) {
/* 1594 */           boolean didCreateNewException = createJavaException(subfault, port, operationName);
/*      */           
/* 1596 */           fault.getJavaException().addSubclass((JavaStructureType)subfault.getJavaException());
/*      */           
/* 1598 */           ((SOAPStructureType)fault.getJavaException().getOwner()).addSubtype((SOAPStructureType)subfault.getJavaException().getOwner());
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void verifyJavaInterface(Port port) {
/* 1607 */     JavaInterface javaInterface = port.getJavaInterface();
/* 1608 */     Class<?> remoteInterface = getClassForNameOrFail(javaInterface.getName());
/* 1609 */     if (!Remote.class.isAssignableFrom(remoteInterface)) {
/* 1610 */       throw new ModelerException("nometadatamodeler.error.notRemoteInterface", remoteInterface.getName());
/*      */     }
/*      */ 
/*      */     
/* 1614 */     Map<Object, Object> javaTypeMap = new HashMap<Object, Object>();
/* 1615 */     for (Iterator<JavaMethod> iterator = javaInterface.getMethods(); iterator.hasNext(); ) {
/* 1616 */       JavaMethod javaMethod = iterator.next();
/* 1617 */       Method[] methods = remoteInterface.getMethods();
/* 1618 */       Method method = null;
/* 1619 */       boolean found = false;
/* 1620 */       for (int i = 0; i < methods.length; i++) {
/* 1621 */         if (methods[i].getName().equals(javaMethod.getName())) {
/* 1622 */           Class[] argTypes = methods[i].getParameterTypes();
/* 1623 */           if (argTypes.length == javaMethod.getParameterCount()) {
/*      */ 
/*      */             
/* 1626 */             int index = 0;
/* 1627 */             Iterator<JavaParameter> iter2 = javaMethod.getParameters();
/* 1628 */             for (; iter2.hasNext(); index++) {
/*      */               
/* 1630 */               JavaParameter param = iter2.next();
/* 1631 */               if (!param.getType().getName().equals(getReadableClassName(argTypes[index]))) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */ 
/*      */             
/* 1637 */             if (index >= argTypes.length) {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1642 */               Class<?> returnType = methods[i].getReturnType();
/* 1643 */               if (javaMethod.getReturnType().getName().equals(getReadableClassName(returnType)))
/*      */               
/*      */               { 
/*      */ 
/*      */                 
/* 1648 */                 found = true;
/* 1649 */                 method = methods[i]; break; } 
/*      */             } 
/*      */           } 
/*      */         } 
/* 1653 */       }  if (found) {
/*      */ 
/*      */         
/* 1656 */         Class[] exceptionTypes = method.getExceptionTypes();
/* 1657 */         Iterator<JavaException> iter2 = javaMethod.getExceptions();
/* 1658 */         while (iter2.hasNext()) {
/*      */           
/* 1660 */           JavaException javaException = iter2.next();
/* 1661 */           boolean foundException = false;
/* 1662 */           for (int j = 0; j < exceptionTypes.length; j++) {
/* 1663 */             if (javaException.getName().equals(exceptionTypes[j].getName())) {
/*      */ 
/*      */               
/* 1666 */               foundException = true;
/* 1667 */               getConstructorForSignatureOrFail(exceptionTypes[j], new Class[] { String.class });
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */           
/* 1673 */           if (!foundException) {
/* 1674 */             throw new ModelerException("nometadatamodeler.error.exceptionNotFound", new Object[] { javaMethod.getName(), javaException.getName() });
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1682 */         Class[] argTypes = method.getParameterTypes();
/* 1683 */         int index = 0;
/* 1684 */         Iterator<JavaParameter> iterator1 = javaMethod.getParameters();
/* 1685 */         for (; iterator1.hasNext(); index++) {
/*      */           
/* 1687 */           JavaParameter param = iterator1.next();
/* 1688 */           javaTypeMap.put(param.getType(), argTypes[index]);
/*      */         } 
/* 1690 */         javaTypeMap.put(javaMethod.getReturnType(), method.getReturnType());
/*      */         continue;
/*      */       } 
/* 1693 */       throw new ModelerException("nometadatamodeler.error.methodNotFound", new Object[] { javaMethod.getName(), remoteInterface.getName() });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1701 */     Iterator<Map.Entry> iter = javaTypeMap.entrySet().iterator();
/* 1702 */     while (iter.hasNext()) {
/*      */       
/* 1704 */       Map.Entry entry = iter.next();
/* 1705 */       verifyJavaType((JavaType)entry.getKey(), (Class)entry.getValue());
/*      */     } 
/*      */   }
/*      */   
/*      */   protected static String getReadableClassName(Class<boolean> klass) {
/* 1710 */     if (klass.isArray())
/* 1711 */       return getReadableClassName(klass.getComponentType()) + "[]"; 
/* 1712 */     if (klass.isPrimitive()) {
/* 1713 */       if (klass == boolean.class)
/* 1714 */         return "boolean"; 
/* 1715 */       if (klass == char.class)
/* 1716 */         return "char"; 
/* 1717 */       if (klass == byte.class)
/* 1718 */         return "byte"; 
/* 1719 */       if (klass == short.class)
/* 1720 */         return "short"; 
/* 1721 */       if (klass == int.class)
/* 1722 */         return "int"; 
/* 1723 */       if (klass == long.class)
/* 1724 */         return "long"; 
/* 1725 */       if (klass == float.class)
/* 1726 */         return "float"; 
/* 1727 */       if (klass == double.class)
/* 1728 */         return "double"; 
/* 1729 */       if (klass == void.class) {
/* 1730 */         return "void";
/*      */       }
/* 1732 */       throw new IllegalArgumentException();
/*      */     } 
/*      */     
/* 1735 */     return klass.getName();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void verifyJavaType(JavaType javaType, Class klass) {
/* 1740 */     if (javaType.isHolder())
/*      */     {
/*      */       
/* 1743 */       throw new ModelerException("nometadatamodeler.error.holderDetected");
/*      */     }
/*      */     
/* 1746 */     if (javaType instanceof JavaArrayType) {
/* 1747 */       JavaArrayType javaArrayType = (JavaArrayType)javaType;
/* 1748 */       if (javaArrayType.getElementType() instanceof JavaArrayType)
/*      */       {
/*      */         
/* 1751 */         throw new ModelerException("nometadatamodeler.error.nestedArrayDetected");
/*      */       }
/*      */     } 
/*      */     
/* 1755 */     if (javaType instanceof JavaStructureType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void verifyServiceInterface(Service service) {
/* 1762 */     JavaInterface theInterface = service.getJavaInterface();
/* 1763 */     Class<?> serviceInterface = getClassForNameOrNull(theInterface.getName());
/* 1764 */     if (serviceInterface != null) {
/* 1765 */       if (!Service.class.isAssignableFrom(serviceInterface))
/*      */       {
/*      */         
/* 1768 */         throw new ModelerException("nometadatamodeler.error.notServiceInterface", serviceInterface.getName());
/*      */       }
/*      */ 
/*      */       
/* 1772 */       for (Iterator<Port> iter = service.getPorts(); iter.hasNext(); ) {
/* 1773 */         Port port = iter.next();
/* 1774 */         String remoteInterfaceName = port.getJavaInterface().getName();
/* 1775 */         this.env.getNames(); String portName = Names.getPortName(port);
/* 1776 */         Class remoteInterface = getClassForNameOrFail(remoteInterfaceName);
/*      */         
/* 1778 */         Method getPort = getMethodForNameAndSignatureAndReturnTypeOrFail(serviceInterface, "get" + portName, new Class[0], remoteInterface);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void warn(String key) {
/* 1785 */     this.env.warn(this.messageFactory.getMessage(key));
/*      */   }
/*      */   
/*      */   protected void warn(String key, String arg) {
/* 1789 */     this.env.warn(this.messageFactory.getMessage(key, arg));
/*      */   }
/*      */   
/*      */   protected void warn(String key, Object[] args) {
/* 1793 */     this.env.warn(this.messageFactory.getMessage(key, args));
/*      */   }
/*      */   
/*      */   protected Class getClassForNameOrNull(String name) {
/*      */     try {
/* 1798 */       return RmiUtils.getClassForName(name, this.env.getClassLoader());
/* 1799 */     } catch (ClassNotFoundException e) {
/* 1800 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Class getClassForNameOrFail(String name) {
/*      */     try {
/* 1806 */       return RmiUtils.getClassForName(name, this.env.getClassLoader());
/* 1807 */     } catch (ClassNotFoundException e) {
/* 1808 */       throw new ModelerException("nometadatamodeler.error.classNotFound", name);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Method getMethodForNameAndSignatureAndReturnTypeOrFail(Class klass, String methodName, Class[] argTypes, Class<?> returnType) {
/*      */     try {
/* 1817 */       Method method = klass.getMethod(methodName, argTypes);
/* 1818 */       if (method.getReturnType() != returnType) {
/* 1819 */         throw new ModelerException("nometadatamodeler.error.methodNotFound", new Object[] { methodName, klass.getName() });
/*      */       }
/*      */ 
/*      */       
/* 1823 */       return method;
/* 1824 */     } catch (NoSuchMethodException e) {
/* 1825 */       throw new ModelerException("nometadatamodeler.error.methodNotFound", new Object[] { methodName, klass.getName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Constructor getConstructorForSignatureOrFail(Class klass, Class[] argTypes) {
/*      */     try {
/* 1832 */       Constructor constructor = klass.getConstructor(argTypes);
/* 1833 */       return constructor;
/* 1834 */     } catch (NoSuchMethodException e) {
/* 1835 */       throw new ModelerException("nometadatamodeler.error.constructorNotFound", new Object[] { klass.getName() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getJavaPackageName(QName name) {
/* 1842 */     String packageName = null;
/* 1843 */     if (this.modelInfo.getNamespaceMappingRegistry() != null) {
/* 1844 */       NamespaceMappingInfo i = this.modelInfo.getNamespaceMappingRegistry().getNamespaceMappingInfo(name);
/*      */ 
/*      */       
/* 1847 */       if (i != null) {
/* 1848 */         return i.getJavaPackageName();
/*      */       }
/*      */     } 
/* 1851 */     return packageName;
/*      */   }
/*      */   
/*      */   protected String makePackageQualified(String s, QName name) {
/* 1855 */     String javaPackageName = getJavaPackageName(name);
/* 1856 */     if (javaPackageName != null)
/* 1857 */       return javaPackageName + "." + s; 
/* 1858 */     if (this.modelInfo.getJavaPackageName() != null && !this.modelInfo.getJavaPackageName().equals(""))
/*      */     {
/*      */       
/* 1861 */       return this.modelInfo.getJavaPackageName() + "." + s;
/*      */     }
/* 1863 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   protected QName makePackageQualified(QName name) {
/* 1868 */     return new QName(name.getNamespaceURI(), makePackageQualified(name.getLocalPart(), name));
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getNonQualifiedNameFor(QName name) {
/* 1873 */     return this.env.getNames().validJavaClassName(name.getLocalPart());
/*      */   }
/*      */   
/*      */   protected static boolean isIncorrectFaultPartName(String s) {
/* 1877 */     return !s.equals("message");
/*      */   }
/*      */   
/*      */   protected static boolean isIncorrectFaultPartType(QName n) {
/* 1881 */     return !n.equals(SchemaConstants.QNAME_TYPE_STRING);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Extension getExtensionOfType(Extensible extensible, Class type) {
/* 1890 */     for (Iterator<Extension> iter = extensible.extensions(); iter.hasNext(); ) {
/* 1891 */       Extension extension = iter.next();
/* 1892 */       if (extension.getClass().equals(type)) {
/* 1893 */         return extension;
/*      */       }
/*      */     } 
/* 1896 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void setDocumentationIfPresent(ModelObject obj, Documentation documentation) {
/* 1902 */     if (documentation != null && documentation.getContent() != null) {
/* 1903 */       obj.setProperty("com.sun.xml.rpc.processor.modeler.wsdl.documentation", documentation.getContent());
/*      */     }
/*      */   }
/*      */   
/*      */   protected static QName getQNameOf(GloballyKnown entity) {
/* 1908 */     return new QName(entity.getDefining().getTargetNamespaceURI(), entity.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static boolean tokenListContains(String tokenList, String target) {
/* 1915 */     if (tokenList == null) {
/* 1916 */       return false;
/*      */     }
/*      */     
/* 1919 */     StringTokenizer tokenizer = new StringTokenizer(tokenList, " ");
/* 1920 */     while (tokenizer.hasMoreTokens()) {
/* 1921 */       String s = tokenizer.nextToken();
/* 1922 */       if (target.equals(s)) {
/* 1923 */         return true;
/*      */       }
/*      */     } 
/* 1926 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public class ProcessSOAPOperationInfo
/*      */   {
/*      */     public Port modelPort;
/*      */     
/*      */     public Port port;
/*      */     
/*      */     public Operation portTypeOperation;
/*      */     
/*      */     public BindingOperation bindingOperation;
/*      */     
/*      */     public SOAPBinding soapBinding;
/*      */     
/*      */     public WSDLDocument document;
/*      */     
/*      */     public Operation operation;
/*      */     public String uniqueOperationName;
/*      */     
/*      */     public ProcessSOAPOperationInfo(Port modelPort, Port port, Operation portTypeOperation, BindingOperation bindingOperation, SOAPBinding soapBinding, WSDLDocument document) {
/* 1948 */       this.modelPort = modelPort;
/* 1949 */       this.port = port;
/* 1950 */       this.portTypeOperation = portTypeOperation;
/* 1951 */       this.bindingOperation = bindingOperation;
/* 1952 */       this.soapBinding = soapBinding;
/* 1953 */       this.document = document;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\nometadata\NoMetadataModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */