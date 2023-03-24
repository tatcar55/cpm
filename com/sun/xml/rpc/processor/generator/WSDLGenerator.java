/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Message;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.wsdl.document.Binding;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingFault;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingInput;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingOperation;
/*     */ import com.sun.xml.rpc.wsdl.document.BindingOutput;
/*     */ import com.sun.xml.rpc.wsdl.document.Definitions;
/*     */ import com.sun.xml.rpc.wsdl.document.Fault;
/*     */ import com.sun.xml.rpc.wsdl.document.Input;
/*     */ import com.sun.xml.rpc.wsdl.document.Message;
/*     */ import com.sun.xml.rpc.wsdl.document.MessagePart;
/*     */ import com.sun.xml.rpc.wsdl.document.Operation;
/*     */ import com.sun.xml.rpc.wsdl.document.OperationStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.Output;
/*     */ import com.sun.xml.rpc.wsdl.document.Port;
/*     */ import com.sun.xml.rpc.wsdl.document.PortType;
/*     */ import com.sun.xml.rpc.wsdl.document.Service;
/*     */ import com.sun.xml.rpc.wsdl.document.WSDLDocument;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaKinds;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPAddress;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBinding;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPBody;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPFault;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPOperation;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPStyle;
/*     */ import com.sun.xml.rpc.wsdl.document.soap.SOAPUse;
/*     */ import com.sun.xml.rpc.wsdl.framework.AbstractDocument;
/*     */ import com.sun.xml.rpc.wsdl.framework.Defining;
/*     */ import com.sun.xml.rpc.wsdl.framework.DuplicateEntityException;
/*     */ import com.sun.xml.rpc.wsdl.framework.Extension;
/*     */ import com.sun.xml.rpc.wsdl.parser.Constants;
/*     */ import com.sun.xml.rpc.wsdl.parser.WSDLWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLGenerator
/*     */   implements Constants, ProcessorAction
/*     */ {
/*  92 */   private SOAPWSDLConstants soapWSDLConstants = null;
/*  93 */   private SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*     */   
/*     */   private File destDir;
/*     */   private ProcessorEnvironment env;
/*     */   private Model model;
/*  98 */   private Properties options = null; protected static final String PART_NAME_LITERAL_REQUEST_WRAPPER = "parameters";
/*     */   
/*     */   public WSDLGenerator() {
/* 101 */     this(SOAPVersion.SOAP_11);
/*     */   }
/*     */   protected static final String PART_NAME_LITERAL_RESPONSE_WRAPPER = "result";
/*     */   public WSDLGenerator(SOAPVersion ver) {
/* 105 */     init(ver);
/* 106 */     this.destDir = null;
/* 107 */     this.env = null;
/* 108 */     this.model = null;
/*     */   }
/*     */   
/*     */   private void init(SOAPVersion ver) {
/* 112 */     this.soapWSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(ver);
/* 113 */     this.soapVer = ver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties properties) {
/* 120 */     perform(model, config, properties, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/* 128 */     ProcessorEnvironment env = (ProcessorEnvironment)config.getEnvironment();
/*     */     
/* 130 */     String key = "nonclassDestinationDirectory";
/* 131 */     String dirPath = properties.getProperty(key);
/* 132 */     File destDir = new File(dirPath);
/*     */     
/* 134 */     WSDLGenerator generator = new WSDLGenerator(env, properties, destDir, model, ver);
/*     */ 
/*     */     
/* 137 */     generator.doGeneration();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WSDLGenerator(ProcessorEnvironment env, Properties properties, File destDir, Model model) {
/* 145 */     this(env, properties, destDir, model, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WSDLGenerator(ProcessorEnvironment env, Properties properties, File destDir, Model model, SOAPVersion ver) {
/* 154 */     init(ver);
/* 155 */     this.env = env;
/* 156 */     this.model = model;
/* 157 */     this.destDir = destDir;
/* 158 */     this.options = properties;
/*     */   }
/*     */   
/*     */   private void doGeneration() {
/*     */     try {
/* 163 */       doGeneration(this.model);
/* 164 */     } catch (Exception e) {
/* 165 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doGeneration(Model model) throws Exception {
/* 173 */     String modelerName = (String)model.getProperty("com.sun.xml.rpc.processor.model.ModelerName");
/*     */ 
/*     */     
/* 176 */     if (modelerName != null && modelerName.equals("com.sun.xml.rpc.processor.modeler.wsdl.WSDLModeler")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     File wsdlFile = new File(this.destDir, model.getName().getLocalPart() + ".wsdl");
/*     */ 
/*     */     
/* 188 */     WSDLDocument document = generateDocument(model);
/*     */     
/*     */     try {
/* 191 */       WSDLWriter writer = new WSDLWriter();
/* 192 */       FileOutputStream fos = new FileOutputStream(wsdlFile);
/*     */ 
/*     */       
/* 195 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 196 */       fi.setFile(wsdlFile);
/* 197 */       fi.setType("Wsdl");
/* 198 */       this.env.addGeneratedFile(fi);
/*     */       
/* 200 */       writer.write(document, fos);
/* 201 */       fos.close();
/* 202 */     } catch (IOException e) {
/* 203 */       fail("generator.cant.write", wsdlFile.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   private WSDLDocument generateDocument(Model model) throws Exception {
/* 208 */     WSDLDocument document = new WSDLDocument();
/* 209 */     Definitions definitions = new Definitions((AbstractDocument)document);
/* 210 */     definitions.setName(model.getName().getLocalPart());
/* 211 */     definitions.setTargetNamespaceURI(model.getTargetNamespaceURI());
/* 212 */     document.setDefinitions(definitions);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     generateTypes(model, document);
/* 219 */     generateMessages(model, definitions);
/* 220 */     generatePortTypes(model, definitions);
/* 221 */     generateBindings(model, definitions);
/* 222 */     generateServices(model, definitions);
/*     */     
/* 224 */     return document;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateTypes(Model model, WSDLDocument document) throws Exception {
/* 230 */     WSDLTypeGenerator typeGenerator = new WSDLTypeGenerator(model, document, this.options, this.soapVer);
/*     */     
/* 232 */     typeGenerator.run();
/*     */   }
/*     */   
/*     */   protected String getSOAPEncodingNamespace(Port port) {
/* 236 */     if (port.getSOAPVersion().equals(SOAPVersion.SOAP_12.toString())) {
/* 237 */       return "http://www.w3.org/2002/06/soap-encoding";
/*     */     }
/* 239 */     return "http://schemas.xmlsoap.org/soap/encoding/";
/*     */   }
/*     */   
/*     */   protected String getSOAPTransportHttpURI(Port port) {
/* 243 */     if (port.getSOAPVersion().equals(SOAPVersion.SOAP_12.toString())) {
/* 244 */       return "http://www.w3.org/2002/06/soap/bindings/HTTP/";
/*     */     }
/* 246 */     return "http://schemas.xmlsoap.org/soap/http";
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateMessages(Model model, Definitions definitions) throws Exception {
/* 251 */     for (Iterator<Service> services = model.getServices(); services.hasNext(); ) {
/* 252 */       Service service = services.next();
/*     */       
/* 254 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/* 255 */         Port port = ports.next();
/*     */         
/* 257 */         PortType wsdlPortType = new PortType((Defining)definitions);
/* 258 */         wsdlPortType.setName(getWSDLPortTypeName(port));
/*     */         
/* 260 */         Iterator<Operation> operations = port.getOperations();
/* 261 */         while (operations.hasNext()) {
/*     */           
/* 263 */           Operation operation = operations.next();
/*     */           
/* 265 */           String localOperationName = operation.getName().getLocalPart();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 270 */           Request request = operation.getRequest();
/* 271 */           Message wsdlRequestMessage = new Message((Defining)definitions);
/*     */           
/* 273 */           wsdlRequestMessage.setName(getWSDLInputMessageName(operation));
/*     */           
/* 275 */           fillInMessageParts((Message)request, wsdlRequestMessage, true, operation.getStyle());
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 280 */           definitions.add(wsdlRequestMessage);
/*     */           
/* 282 */           Response response = operation.getResponse();
/* 283 */           if (response != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 290 */             Message wsdlResponseMessage = new Message((Defining)definitions);
/*     */ 
/*     */             
/* 293 */             wsdlResponseMessage.setName(getWSDLOutputMessageName(operation));
/*     */             
/* 295 */             fillInMessageParts((Message)response, wsdlResponseMessage, false, operation.getStyle());
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 300 */             definitions.add(wsdlResponseMessage);
/*     */           } 
/*     */           
/* 303 */           Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*     */           
/* 305 */           faultSet.addAll(operation.getFaultsSet());
/* 306 */           Iterator<Fault> faults = faultSet.iterator();
/* 307 */           while (faults.hasNext()) {
/*     */             
/* 309 */             Fault fault = faults.next();
/*     */             
/* 311 */             Message wsdlFaultMessage = new Message((Defining)definitions);
/*     */ 
/*     */             
/* 314 */             wsdlFaultMessage.setName(getWSDLFaultMessageName(fault));
/*     */             
/* 316 */             MessagePart part = new MessagePart();
/* 317 */             part.setName(fault.getBlock().getName().getLocalPart());
/* 318 */             JavaException javaException = fault.getJavaException();
/* 319 */             AbstractType type = fault.getBlock().getType();
/* 320 */             if (type.isSOAPType()) {
/* 321 */               part.setDescriptorKind(SchemaKinds.XSD_TYPE);
/* 322 */               if (fault.getSubfaults() != null) {
/* 323 */                 QName ownerName = ((AbstractType)javaException.getOwner()).getName();
/*     */ 
/*     */                 
/* 326 */                 part.setDescriptor(ownerName);
/*     */               } else {
/* 328 */                 part.setDescriptor(type.getName());
/*     */               } 
/* 330 */             } else if (type.isLiteralType()) {
/* 331 */               part.setDescriptorKind(SchemaKinds.XSD_ELEMENT);
/* 332 */               part.setDescriptor(fault.getElementName());
/*     */             } 
/* 334 */             wsdlFaultMessage.add(part);
/*     */             
/*     */             try {
/* 337 */               definitions.add(wsdlFaultMessage);
/* 338 */             } catch (DuplicateEntityException e) {}
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillInMessageParts(Message message, Message wsdlMessage, boolean isRequest, SOAPStyle style) throws Exception {
/* 354 */     if (message == null) {
/*     */       return;
/*     */     }
/*     */     
/* 358 */     if (style == SOAPStyle.RPC) {
/* 359 */       Iterator<Parameter> parameters = message.getParameters();
/* 360 */       while (parameters.hasNext()) {
/*     */         
/* 362 */         Parameter parameter = parameters.next();
/* 363 */         MessagePart part = new MessagePart();
/* 364 */         part.setName(parameter.getName());
/* 365 */         AbstractType type = parameter.getType();
/* 366 */         if (type.getName() == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 370 */         if (type.isSOAPType() || style == SOAPStyle.RPC) {
/* 371 */           part.setDescriptorKind(SchemaKinds.XSD_TYPE);
/* 372 */           part.setDescriptor(type.getName());
/* 373 */         } else if (type.isLiteralType()) {
/* 374 */           part.setDescriptorKind(SchemaKinds.XSD_ELEMENT);
/* 375 */           part.setDescriptor(type.getName());
/*     */         } 
/* 377 */         wsdlMessage.add(part);
/*     */       } 
/*     */     } else {
/*     */       
/* 381 */       Iterator<Block> iter = message.getBodyBlocks();
/* 382 */       if (iter.hasNext()) {
/* 383 */         Block bodyBlock = iter.next();
/* 384 */         MessagePart part = new MessagePart();
/* 385 */         part.setName(isRequest ? "parameters" : "result");
/*     */ 
/*     */ 
/*     */         
/* 389 */         part.setDescriptorKind(SchemaKinds.XSD_ELEMENT);
/* 390 */         part.setDescriptor(bodyBlock.getName());
/* 391 */         wsdlMessage.add(part);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generatePortTypes(Model model, Definitions definitions) throws Exception {
/* 399 */     for (Iterator<Service> services = model.getServices(); services.hasNext(); ) {
/* 400 */       Service service = services.next();
/*     */       
/* 402 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/* 403 */         Port port = ports.next();
/*     */         
/* 405 */         PortType wsdlPortType = new PortType((Defining)definitions);
/* 406 */         wsdlPortType.setName(getWSDLPortTypeName(port));
/*     */         
/* 408 */         Iterator<Operation> operations = port.getOperations();
/* 409 */         while (operations.hasNext()) {
/*     */           
/* 411 */           Operation operation = operations.next();
/*     */           
/* 413 */           String localOperationName = operation.getName().getLocalPart();
/*     */ 
/*     */           
/* 416 */           Operation wsdlOperation = new Operation();
/*     */           
/* 418 */           wsdlOperation.setName(localOperationName);
/* 419 */           wsdlOperation.setStyle(OperationStyle.REQUEST_RESPONSE);
/*     */ 
/*     */           
/* 422 */           if (operation.getStyle().equals(SOAPStyle.RPC) && operation.getResponse() != null) {
/*     */ 
/*     */             
/* 425 */             String paramOrder = "";
/* 426 */             Iterator<Parameter> parameters = operation.getRequest().getParameters();
/*     */ 
/*     */             
/* 429 */             for (int i = 0; parameters.hasNext(); i++) {
/* 430 */               if (i > 0)
/* 431 */                 paramOrder = paramOrder + " "; 
/* 432 */               Parameter parameter = parameters.next();
/* 433 */               paramOrder = paramOrder + parameter.getName();
/*     */             } 
/* 435 */             wsdlOperation.setParameterOrder(paramOrder);
/*     */           } 
/*     */           
/* 438 */           Input input = new Input();
/* 439 */           input.setMessage(new QName(model.getTargetNamespaceURI(), getWSDLInputMessageName(operation)));
/*     */ 
/*     */ 
/*     */           
/* 443 */           wsdlOperation.setInput(input);
/*     */           
/* 445 */           if (getWSDLOutputMessageName(operation) != null) {
/* 446 */             Output output = new Output();
/* 447 */             output.setMessage(new QName(model.getTargetNamespaceURI(), getWSDLOutputMessageName(operation)));
/*     */ 
/*     */ 
/*     */             
/* 451 */             wsdlOperation.setOutput(output);
/*     */           } 
/*     */           
/* 454 */           Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*     */           
/* 456 */           faultSet.addAll(operation.getFaultsSet());
/* 457 */           Iterator<Fault> faults = faultSet.iterator();
/* 458 */           while (faults.hasNext()) {
/*     */             
/* 460 */             Fault fault = faults.next();
/*     */             
/* 462 */             Fault wsdlFault = new Fault();
/*     */             
/* 464 */             wsdlFault.setName(fault.getName());
/* 465 */             wsdlFault.setMessage(new QName(model.getTargetNamespaceURI(), getWSDLFaultMessageName(fault)));
/*     */ 
/*     */ 
/*     */             
/* 469 */             wsdlOperation.addFault(wsdlFault);
/*     */           } 
/*     */           
/* 472 */           wsdlPortType.add(wsdlOperation);
/*     */         } 
/*     */         
/* 475 */         definitions.add(wsdlPortType);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateBindings(Model model, Definitions definitions) throws Exception {
/* 482 */     for (Iterator<Service> services = model.getServices(); services.hasNext(); ) {
/* 483 */       Service service = services.next();
/*     */       
/* 485 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/* 486 */         Port port = ports.next();
/*     */ 
/*     */         
/* 489 */         boolean isMixed = false;
/* 490 */         SOAPStyle defaultStyle = null;
/* 491 */         Iterator<Operation> operations = port.getOperations();
/* 492 */         while (operations.hasNext()) {
/*     */           
/* 494 */           Operation operation = operations.next();
/*     */           
/* 496 */           if (operation.getStyle() == null) {
/* 497 */             operation.setStyle(SOAPStyle.RPC);
/*     */           }
/*     */           
/* 500 */           if (defaultStyle == null) {
/* 501 */             defaultStyle = operation.getStyle(); continue;
/*     */           } 
/* 503 */           if (defaultStyle != operation.getStyle()) {
/* 504 */             isMixed = true;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 509 */         String localPortName = port.getName().getLocalPart();
/* 510 */         Binding wsdlBinding = new Binding((Defining)definitions);
/* 511 */         wsdlBinding.setName(getWSDLBindingName(port));
/* 512 */         wsdlBinding.setPortType(new QName(model.getTargetNamespaceURI(), getWSDLPortTypeName(port)));
/*     */ 
/*     */ 
/*     */         
/* 516 */         SOAPBinding soapBinding = new SOAPBinding();
/* 517 */         if (defaultStyle != null && !isMixed) {
/* 518 */           soapBinding.setStyle(defaultStyle);
/*     */         }
/* 520 */         soapBinding.setTransport(getSOAPTransportHttpURI(port));
/* 521 */         wsdlBinding.addExtension((Extension)soapBinding);
/*     */         
/* 523 */         Iterator<Operation> iterator1 = port.getOperations();
/* 524 */         while (iterator1.hasNext()) {
/*     */           
/* 526 */           Operation operation = iterator1.next();
/*     */           
/* 528 */           BindingOperation wsdlOperation = new BindingOperation();
/* 529 */           wsdlOperation.setName(operation.getName().getLocalPart());
/* 530 */           wsdlOperation.setStyle(OperationStyle.REQUEST_RESPONSE);
/* 531 */           SOAPOperation soapOperation = new SOAPOperation();
/* 532 */           soapOperation.setSOAPAction(operation.getSOAPAction());
/* 533 */           if (!operation.getStyle().equals(defaultStyle)) {
/* 534 */             soapOperation.setStyle(operation.getStyle());
/*     */           }
/* 536 */           wsdlOperation.addExtension((Extension)soapOperation);
/*     */           
/* 538 */           Request request = operation.getRequest();
/* 539 */           BindingInput input = new BindingInput();
/* 540 */           SOAPBody soapBody = new SOAPBody();
/* 541 */           soapBody.setUse(operation.getUse());
/* 542 */           if (operation.getUse() == SOAPUse.ENCODED) {
/* 543 */             soapBody.setEncodingStyle(getSOAPEncodingNamespace(port));
/*     */           }
/* 545 */           if (operation.getStyle() == SOAPStyle.RPC) {
/* 546 */             soapBody.setNamespace(model.getTargetNamespaceURI());
/*     */           }
/* 548 */           input.addExtension((Extension)soapBody);
/* 549 */           wsdlOperation.setInput(input);
/*     */           
/* 551 */           Response response = operation.getResponse();
/* 552 */           if (response != null) {
/* 553 */             BindingOutput output = new BindingOutput();
/* 554 */             soapBody = new SOAPBody();
/* 555 */             soapBody.setUse(operation.getUse());
/* 556 */             if (operation.getUse() == SOAPUse.ENCODED) {
/* 557 */               soapBody.setEncodingStyle(getSOAPEncodingNamespace(port));
/*     */             }
/* 559 */             if (operation.getStyle() == SOAPStyle.RPC) {
/* 560 */               soapBody.setNamespace(model.getTargetNamespaceURI());
/*     */             }
/*     */             
/* 563 */             output.addExtension((Extension)soapBody);
/* 564 */             wsdlOperation.setOutput(output);
/*     */           } 
/*     */           
/* 567 */           Set faultSet = new TreeSet(new GeneratorUtil.FaultComparator());
/*     */           
/* 569 */           faultSet.addAll(operation.getFaultsSet());
/* 570 */           Iterator<Fault> faults = faultSet.iterator();
/* 571 */           while (faults.hasNext()) {
/*     */             
/* 573 */             Fault fault = faults.next();
/* 574 */             BindingFault bindingFault = new BindingFault();
/* 575 */             bindingFault.setName(fault.getName());
/* 576 */             SOAPFault soapFault = new SOAPFault();
/* 577 */             soapFault.setName(fault.getName());
/* 578 */             if (fault.getBlock().getType().isSOAPType()) {
/* 579 */               soapFault.setUse(SOAPUse.ENCODED);
/* 580 */               soapFault.setEncodingStyle(getSOAPEncodingNamespace(port));
/*     */               
/* 582 */               soapFault.setNamespace(model.getTargetNamespaceURI());
/*     */             } else {
/*     */               
/* 585 */               soapFault.setUse(SOAPUse.LITERAL);
/*     */             } 
/* 587 */             bindingFault.addExtension((Extension)soapFault);
/* 588 */             wsdlOperation.addFault(bindingFault);
/*     */           } 
/*     */           
/* 591 */           wsdlBinding.add(wsdlOperation);
/*     */         } 
/*     */         
/* 594 */         definitions.add(wsdlBinding);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateServices(Model model, Definitions definitions) throws Exception {
/* 602 */     for (Iterator<Service> services = model.getServices(); services.hasNext(); ) {
/* 603 */       Service service = services.next();
/* 604 */       Service wsdlService = new Service((Defining)definitions);
/*     */       
/* 606 */       wsdlService.setName(service.getName().getLocalPart());
/* 607 */       for (Iterator<Port> ports = service.getPorts(); ports.hasNext(); ) {
/* 608 */         Port port = ports.next();
/* 609 */         String localPortName = port.getName().getLocalPart();
/* 610 */         Port wsdlPort = new Port((Defining)definitions);
/*     */         
/* 612 */         wsdlPort.setName(getWSDLPortName(port));
/* 613 */         wsdlPort.setBinding(new QName(model.getTargetNamespaceURI(), getWSDLBindingName(port)));
/*     */ 
/*     */ 
/*     */         
/* 617 */         SOAPAddress soapAddress = new SOAPAddress();
/* 618 */         soapAddress.setLocation((port.getAddress() == null) ? "REPLACE_WITH_ACTUAL_URL" : port.getAddress());
/*     */ 
/*     */ 
/*     */         
/* 622 */         wsdlPort.addExtension((Extension)soapAddress);
/* 623 */         wsdlService.add(wsdlPort);
/*     */       } 
/*     */       
/* 626 */       definitions.add(wsdlService);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getWSDLBaseName(Port port) {
/* 631 */     return port.getName().getLocalPart();
/*     */   }
/*     */   
/*     */   private String getWSDLPortName(Port port) {
/* 635 */     QName value = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName");
/*     */     
/* 637 */     if (value != null) {
/* 638 */       return value.getLocalPart();
/*     */     }
/* 640 */     return getWSDLBaseName(port) + "Port";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getWSDLBindingName(Port port) {
/* 645 */     QName value = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLBindingName");
/*     */ 
/*     */     
/* 648 */     if (value != null) {
/* 649 */       return value.getLocalPart();
/*     */     }
/* 651 */     return getWSDLBaseName(port) + "Binding";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getWSDLPortTypeName(Port port) {
/* 656 */     QName value = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortTypeName");
/*     */ 
/*     */     
/* 659 */     if (value != null) {
/* 660 */       return value.getLocalPart();
/*     */     }
/* 662 */     return port.getName().getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getWSDLInputMessageName(Operation operation) {
/* 667 */     QName value = (QName)operation.getRequest().getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */ 
/*     */     
/* 670 */     if (value != null) {
/* 671 */       return value.getLocalPart();
/*     */     }
/* 673 */     return operation.getName().getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getWSDLOutputMessageName(Operation operation) {
/* 678 */     if (operation.getResponse() == null)
/*     */     {
/* 680 */       return null;
/*     */     }
/* 682 */     QName value = (QName)operation.getResponse().getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */ 
/*     */     
/* 685 */     if (value != null) {
/* 686 */       return value.getLocalPart();
/*     */     }
/* 688 */     return operation.getName().getLocalPart() + "Response";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getWSDLFaultMessageName(Fault fault) {
/* 693 */     QName value = (QName)fault.getProperty("com.sun.xml.rpc.processor.model.WSDLMessageName");
/*     */ 
/*     */     
/* 696 */     if (value != null) {
/* 697 */       return value.getLocalPart();
/*     */     }
/* 699 */     return fault.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fail(String key, String arg) {
/* 704 */     throw new GeneratorException(key, arg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\WSDLGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */