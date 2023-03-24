/*      */ package com.sun.xml.ws.wsdl.writer;
/*      */ 
/*      */ import com.oracle.webservices.api.databinding.WSDLResolver;
/*      */ import com.sun.xml.bind.v2.schemagen.Util;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ComplexType;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.Element;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.ExplicitGroup;
/*      */ import com.sun.xml.bind.v2.schemagen.xmlschema.LocalElement;
/*      */ import com.sun.xml.txw2.TXW;
/*      */ import com.sun.xml.txw2.TypedXmlWriter;
/*      */ import com.sun.xml.txw2.output.ResultFactory;
/*      */ import com.sun.xml.txw2.output.TXWResult;
/*      */ import com.sun.xml.txw2.output.XmlSerializer;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.WSBinding;
/*      */ import com.sun.xml.ws.api.model.CheckedException;
/*      */ import com.sun.xml.ws.api.model.JavaMethod;
/*      */ import com.sun.xml.ws.api.model.MEP;
/*      */ import com.sun.xml.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.ws.api.model.SEIModel;
/*      */ import com.sun.xml.ws.api.model.soap.SOAPBinding;
/*      */ import com.sun.xml.ws.api.server.Container;
/*      */ import com.sun.xml.ws.api.wsdl.writer.WSDLGenExtnContext;
/*      */ import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
/*      */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*      */ import com.sun.xml.ws.model.CheckedExceptionImpl;
/*      */ import com.sun.xml.ws.model.JavaMethodImpl;
/*      */ import com.sun.xml.ws.model.ParameterImpl;
/*      */ import com.sun.xml.ws.model.WrapperParameter;
/*      */ import com.sun.xml.ws.policy.jaxws.PolicyWSDLGeneratorExtension;
/*      */ import com.sun.xml.ws.spi.db.BindingContext;
/*      */ import com.sun.xml.ws.spi.db.BindingHelper;
/*      */ import com.sun.xml.ws.spi.db.TypeInfo;
/*      */ import com.sun.xml.ws.spi.db.WrapperComposite;
/*      */ import com.sun.xml.ws.util.RuntimeVersion;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Binding;
/*      */ import com.sun.xml.ws.wsdl.writer.document.BindingOperationType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Definitions;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Fault;
/*      */ import com.sun.xml.ws.wsdl.writer.document.FaultType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Import;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Message;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Operation;
/*      */ import com.sun.xml.ws.wsdl.writer.document.ParamType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Part;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Port;
/*      */ import com.sun.xml.ws.wsdl.writer.document.PortType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Service;
/*      */ import com.sun.xml.ws.wsdl.writer.document.StartWithExtensionsType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.Types;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.Body;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.BodyType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.Header;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.SOAPAddress;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.SOAPBinding;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap.SOAPFault;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.Body;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.BodyType;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.Header;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.SOAPAddress;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.SOAPBinding;
/*      */ import com.sun.xml.ws.wsdl.writer.document.soap12.SOAPFault;
/*      */ import com.sun.xml.ws.wsdl.writer.document.xsd.Import;
/*      */ import com.sun.xml.ws.wsdl.writer.document.xsd.Schema;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.bind.SchemaOutputResolver;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.transform.Result;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMResult;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.sax.SAXResult;
/*      */ import javax.xml.ws.Holder;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import org.w3c.dom.Document;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSDLGenerator
/*      */ {
/*      */   private JAXWSOutputSchemaResolver resolver;
/*  136 */   private WSDLResolver wsdlResolver = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private AbstractSEIModelImpl model;
/*      */ 
/*      */ 
/*      */   
/*      */   private Definitions serviceDefinitions;
/*      */ 
/*      */ 
/*      */   
/*      */   private Definitions portDefinitions;
/*      */ 
/*      */ 
/*      */   
/*      */   private Types types;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String DOT_WSDL = ".wsdl";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RESPONSE = "Response";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PARAMETERS = "parameters";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RESULT = "parameters";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String UNWRAPPABLE_RESULT = "result";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String WSDL_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String XSD_NAMESPACE = "http://www.w3.org/2001/XMLSchema";
/*      */ 
/*      */   
/*      */   private static final String XSD_PREFIX = "xsd";
/*      */ 
/*      */   
/*      */   private static final String SOAP11_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap/";
/*      */ 
/*      */   
/*      */   private static final String SOAP12_NAMESPACE = "http://schemas.xmlsoap.org/wsdl/soap12/";
/*      */ 
/*      */   
/*      */   private static final String SOAP_PREFIX = "soap";
/*      */ 
/*      */   
/*      */   private static final String SOAP12_PREFIX = "soap12";
/*      */ 
/*      */   
/*      */   private static final String TNS_PREFIX = "tns";
/*      */ 
/*      */   
/*      */   private static final String DOCUMENT = "document";
/*      */ 
/*      */   
/*      */   private static final String RPC = "rpc";
/*      */ 
/*      */   
/*      */   private static final String LITERAL = "literal";
/*      */ 
/*      */   
/*      */   private static final String REPLACE_WITH_ACTUAL_URL = "REPLACE_WITH_ACTUAL_URL";
/*      */ 
/*      */   
/*  213 */   private Set<QName> processedExceptions = new HashSet<QName>();
/*      */   
/*      */   private WSBinding binding;
/*      */   private String wsdlLocation;
/*      */   private String portWSDLID;
/*      */   private String schemaPrefix;
/*      */   private WSDLGeneratorExtension extension;
/*      */   List<WSDLGeneratorExtension> extensionHandlers;
/*  221 */   private String endpointAddress = "REPLACE_WITH_ACTUAL_URL";
/*      */ 
/*      */ 
/*      */   
/*      */   private Container container;
/*      */ 
/*      */ 
/*      */   
/*      */   private final Class implType;
/*      */ 
/*      */   
/*      */   private boolean inlineSchemas;
/*      */ 
/*      */ 
/*      */   
/*      */   public WSDLGenerator(AbstractSEIModelImpl model, WSDLResolver wsdlResolver, WSBinding binding, Container container, Class implType, boolean inlineSchemas, WSDLGeneratorExtension... extensions) {
/*  237 */     this.model = model;
/*  238 */     this.resolver = new JAXWSOutputSchemaResolver();
/*  239 */     this.wsdlResolver = wsdlResolver;
/*  240 */     this.binding = binding;
/*  241 */     this.container = container;
/*  242 */     this.implType = implType;
/*  243 */     this.extensionHandlers = new ArrayList<WSDLGeneratorExtension>();
/*  244 */     this.inlineSchemas = inlineSchemas;
/*      */ 
/*      */     
/*  247 */     register(new W3CAddressingWSDLGeneratorExtension());
/*  248 */     register(new W3CAddressingMetadataWSDLGeneratorExtension());
/*  249 */     register((WSDLGeneratorExtension)new PolicyWSDLGeneratorExtension());
/*      */     
/*  251 */     if (container != null) {
/*  252 */       WSDLGeneratorExtension[] wsdlGeneratorExtensions = (WSDLGeneratorExtension[])container.getSPI(WSDLGeneratorExtension[].class);
/*  253 */       if (wsdlGeneratorExtensions != null) {
/*  254 */         for (WSDLGeneratorExtension wsdlGeneratorExtension : wsdlGeneratorExtensions) {
/*  255 */           register(wsdlGeneratorExtension);
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/*  260 */     for (WSDLGeneratorExtension w : extensions) {
/*  261 */       register(w);
/*      */     }
/*  263 */     this.extension = new WSDLGeneratorExtensionFacade(this.extensionHandlers.<WSDLGeneratorExtension>toArray(new WSDLGeneratorExtension[0]));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEndpointAddress(String address) {
/*  273 */     this.endpointAddress = address;
/*      */   }
/*      */   
/*      */   protected String mangleName(String name) {
/*  277 */     return BindingHelper.mangleNameToClassName(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void doGeneration() {
/*  285 */     XmlSerializer portWriter = null;
/*  286 */     String fileName = mangleName(this.model.getServiceQName().getLocalPart());
/*  287 */     Result result = this.wsdlResolver.getWSDL(fileName + ".wsdl");
/*  288 */     this.wsdlLocation = result.getSystemId();
/*  289 */     XmlSerializer serviceWriter = new CommentFilter(ResultFactory.createSerializer(result));
/*  290 */     if (this.model.getServiceQName().getNamespaceURI().equals(this.model.getTargetNamespace())) {
/*  291 */       portWriter = serviceWriter;
/*  292 */       this.schemaPrefix = fileName + "_";
/*      */     } else {
/*  294 */       String wsdlName = mangleName(this.model.getPortTypeName().getLocalPart());
/*  295 */       if (wsdlName.equals(fileName))
/*  296 */         wsdlName = wsdlName + "PortType"; 
/*  297 */       Holder<String> absWSDLName = new Holder<String>();
/*  298 */       absWSDLName.value = (T)(wsdlName + ".wsdl");
/*  299 */       result = this.wsdlResolver.getAbstractWSDL(absWSDLName);
/*      */       
/*  301 */       if (result != null) {
/*  302 */         this.portWSDLID = result.getSystemId();
/*  303 */         if (this.portWSDLID.equals(this.wsdlLocation)) {
/*  304 */           portWriter = serviceWriter;
/*      */         } else {
/*  306 */           portWriter = new CommentFilter(ResultFactory.createSerializer(result));
/*      */         } 
/*      */       } else {
/*  309 */         this.portWSDLID = (String)absWSDLName.value;
/*      */       } 
/*  311 */       this.schemaPrefix = (new File(this.portWSDLID)).getName();
/*  312 */       int idx = this.schemaPrefix.lastIndexOf('.');
/*  313 */       if (idx > 0)
/*  314 */         this.schemaPrefix = this.schemaPrefix.substring(0, idx); 
/*  315 */       this.schemaPrefix = mangleName(this.schemaPrefix) + "_";
/*      */     } 
/*  317 */     generateDocument(serviceWriter, portWriter);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CommentFilter
/*      */     implements XmlSerializer
/*      */   {
/*      */     final XmlSerializer serializer;
/*      */     
/*  327 */     private static final String VERSION_COMMENT = " Generated by JAX-WS RI at http://jax-ws.dev.java.net. RI's version is " + RuntimeVersion.VERSION + ". ";
/*      */ 
/*      */     
/*      */     CommentFilter(XmlSerializer serializer) {
/*  331 */       this.serializer = serializer;
/*      */     }
/*      */ 
/*      */     
/*      */     public void startDocument() {
/*  336 */       this.serializer.startDocument();
/*  337 */       comment(new StringBuilder(VERSION_COMMENT));
/*  338 */       text(new StringBuilder("\n"));
/*      */     }
/*      */ 
/*      */     
/*      */     public void beginStartTag(String uri, String localName, String prefix) {
/*  343 */       this.serializer.beginStartTag(uri, localName, prefix);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*  348 */       this.serializer.writeAttribute(uri, localName, prefix, value);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeXmlns(String prefix, String uri) {
/*  353 */       this.serializer.writeXmlns(prefix, uri);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endStartTag(String uri, String localName, String prefix) {
/*  358 */       this.serializer.endStartTag(uri, localName, prefix);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endTag() {
/*  363 */       this.serializer.endTag();
/*      */     }
/*      */ 
/*      */     
/*      */     public void text(StringBuilder text) {
/*  368 */       this.serializer.text(text);
/*      */     }
/*      */ 
/*      */     
/*      */     public void cdata(StringBuilder text) {
/*  373 */       this.serializer.cdata(text);
/*      */     }
/*      */ 
/*      */     
/*      */     public void comment(StringBuilder comment) {
/*  378 */       this.serializer.comment(comment);
/*      */     }
/*      */ 
/*      */     
/*      */     public void endDocument() {
/*  383 */       this.serializer.endDocument();
/*      */     }
/*      */ 
/*      */     
/*      */     public void flush() {
/*  388 */       this.serializer.flush();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateDocument(XmlSerializer serviceStream, XmlSerializer portStream) {
/*  394 */     this.serviceDefinitions = (Definitions)TXW.create(Definitions.class, serviceStream);
/*  395 */     this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/", "");
/*  396 */     this.serviceDefinitions._namespace("http://www.w3.org/2001/XMLSchema", "xsd");
/*  397 */     this.serviceDefinitions.targetNamespace(this.model.getServiceQName().getNamespaceURI());
/*  398 */     this.serviceDefinitions._namespace(this.model.getServiceQName().getNamespaceURI(), "tns");
/*  399 */     if (this.binding.getSOAPVersion() == SOAPVersion.SOAP_12) {
/*  400 */       this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/soap12/", "soap12");
/*      */     } else {
/*  402 */       this.serviceDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/soap/", "soap");
/*  403 */     }  this.serviceDefinitions.name(this.model.getServiceQName().getLocalPart());
/*  404 */     WSDLGenExtnContext serviceCtx = new WSDLGenExtnContext((TypedXmlWriter)this.serviceDefinitions, (SEIModel)this.model, this.binding, this.container, this.implType);
/*  405 */     this.extension.start(serviceCtx);
/*  406 */     if (serviceStream != portStream && portStream != null) {
/*      */       
/*  408 */       this.portDefinitions = (Definitions)TXW.create(Definitions.class, portStream);
/*  409 */       this.portDefinitions._namespace("http://schemas.xmlsoap.org/wsdl/", "");
/*  410 */       this.portDefinitions._namespace("http://www.w3.org/2001/XMLSchema", "xsd");
/*  411 */       if (this.model.getTargetNamespace() != null) {
/*  412 */         this.portDefinitions.targetNamespace(this.model.getTargetNamespace());
/*  413 */         this.portDefinitions._namespace(this.model.getTargetNamespace(), "tns");
/*      */       } 
/*      */       
/*  416 */       String schemaLoc = relativize(this.portWSDLID, this.wsdlLocation);
/*  417 */       Import _import = this.serviceDefinitions._import().namespace(this.model.getTargetNamespace());
/*  418 */       _import.location(schemaLoc);
/*  419 */     } else if (portStream != null) {
/*      */       
/*  421 */       this.portDefinitions = this.serviceDefinitions;
/*      */     } else {
/*      */       
/*  424 */       String schemaLoc = relativize(this.portWSDLID, this.wsdlLocation);
/*  425 */       Import _import = this.serviceDefinitions._import().namespace(this.model.getTargetNamespace());
/*  426 */       _import.location(schemaLoc);
/*      */     } 
/*  428 */     this.extension.addDefinitionsExtension((TypedXmlWriter)this.serviceDefinitions);
/*      */     
/*  430 */     if (this.portDefinitions != null) {
/*  431 */       generateTypes();
/*  432 */       generateMessages();
/*  433 */       generatePortType();
/*      */     } 
/*  435 */     generateBinding();
/*  436 */     generateService();
/*      */     
/*  438 */     this.extension.end(serviceCtx);
/*  439 */     this.serviceDefinitions.commit();
/*  440 */     if (this.portDefinitions != null && this.portDefinitions != this.serviceDefinitions) {
/*  441 */       this.portDefinitions.commit();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateTypes() {
/*  449 */     this.types = this.portDefinitions.types();
/*  450 */     if (this.model.getBindingContext() != null) {
/*  451 */       if (this.inlineSchemas && this.model.getBindingContext().getClass().getName().indexOf("glassfish") == -1) {
/*  452 */         this.resolver.nonGlassfishSchemas = new ArrayList<DOMResult>();
/*      */       }
/*      */       try {
/*  455 */         this.model.getBindingContext().generateSchema(this.resolver);
/*  456 */       } catch (IOException e) {
/*      */ 
/*      */         
/*  459 */         throw new WebServiceException(e.getMessage());
/*      */       } 
/*      */     } 
/*  462 */     if (this.resolver.nonGlassfishSchemas != null) {
/*  463 */       TransformerFactory tf = TransformerFactory.newInstance();
/*      */       try {
/*  465 */         Transformer t = tf.newTransformer();
/*  466 */         for (DOMResult xsd : this.resolver.nonGlassfishSchemas) {
/*  467 */           Document doc = (Document)xsd.getNode();
/*  468 */           SAXResult sax = new SAXResult(new TXWContentHandler((TypedXmlWriter)this.types));
/*  469 */           t.transform(new DOMSource(doc.getDocumentElement()), sax);
/*      */         } 
/*  471 */       } catch (TransformerConfigurationException e) {
/*      */         
/*  473 */         throw new WebServiceException(e.getMessage(), e);
/*  474 */       } catch (TransformerException e) {
/*      */         
/*  476 */         throw new WebServiceException(e.getMessage(), e);
/*      */       } 
/*      */     } 
/*  479 */     generateWrappers();
/*      */   }
/*      */   
/*      */   void generateWrappers() {
/*  483 */     List<WrapperParameter> wrappers = new ArrayList<WrapperParameter>();
/*  484 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  485 */       if (method.getBinding().isRpcLit())
/*  486 */         continue;  for (ParameterImpl p : method.getRequestParameters()) {
/*  487 */         if (p instanceof WrapperParameter && 
/*  488 */           WrapperComposite.class.equals((((WrapperParameter)p).getTypeInfo()).type)) {
/*  489 */           wrappers.add((WrapperParameter)p);
/*      */         }
/*      */       } 
/*      */       
/*  493 */       for (ParameterImpl p : method.getResponseParameters()) {
/*  494 */         if (p instanceof WrapperParameter && 
/*  495 */           WrapperComposite.class.equals((((WrapperParameter)p).getTypeInfo()).type)) {
/*  496 */           wrappers.add((WrapperParameter)p);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  501 */     if (wrappers.isEmpty())
/*  502 */       return;  HashMap<String, Schema> xsds = new HashMap<String, Schema>();
/*  503 */     for (WrapperParameter wp : wrappers) {
/*  504 */       String tns = wp.getName().getNamespaceURI();
/*  505 */       Schema xsd = xsds.get(tns);
/*  506 */       if (xsd == null) {
/*  507 */         xsd = this.types.schema();
/*  508 */         xsd.targetNamespace(tns);
/*  509 */         xsds.put(tns, xsd);
/*      */       } 
/*  511 */       Element e = (Element)xsd._element(Element.class);
/*  512 */       e._attribute("name", wp.getName().getLocalPart());
/*  513 */       e.type(wp.getName());
/*  514 */       ComplexType ct = (ComplexType)xsd._element(ComplexType.class);
/*  515 */       ct._attribute("name", wp.getName().getLocalPart());
/*  516 */       ExplicitGroup sq = ct.sequence();
/*  517 */       for (ParameterImpl p : wp.getWrapperChildren()) {
/*  518 */         if (p.getBinding().isBody()) {
/*  519 */           LocalElement le = sq.element();
/*  520 */           le._attribute("name", p.getName().getLocalPart());
/*  521 */           TypeInfo typeInfo = p.getItemType();
/*  522 */           boolean repeatedElement = false;
/*  523 */           if (typeInfo == null) {
/*  524 */             typeInfo = p.getTypeInfo();
/*      */           } else {
/*  526 */             repeatedElement = true;
/*      */           } 
/*  528 */           QName type = this.model.getBindingContext().getTypeName(typeInfo);
/*  529 */           le.type(type);
/*  530 */           if (repeatedElement) {
/*  531 */             le.minOccurs(0);
/*  532 */             le.maxOccurs("unbounded");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateMessages() {
/*  543 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  544 */       generateSOAPMessages(method, method.getBinding());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateSOAPMessages(JavaMethodImpl method, SOAPBinding binding) {
/*  554 */     boolean isDoclit = binding.isDocLit();
/*      */     
/*  556 */     Message message = this.portDefinitions.message().name(method.getRequestMessageName());
/*  557 */     this.extension.addInputMessageExtension((TypedXmlWriter)message, (JavaMethod)method);
/*      */     
/*  559 */     BindingContext jaxbContext = this.model.getBindingContext();
/*  560 */     boolean unwrappable = true;
/*  561 */     for (ParameterImpl param : method.getRequestParameters()) {
/*  562 */       if (isDoclit) {
/*  563 */         if (isHeaderParameter(param)) {
/*  564 */           unwrappable = false;
/*      */         }
/*  566 */         Part part1 = message.part().name(param.getPartName());
/*  567 */         part1.element(param.getName()); continue;
/*      */       } 
/*  569 */       if (param.isWrapperStyle()) {
/*  570 */         for (ParameterImpl childParam : ((WrapperParameter)param).getWrapperChildren()) {
/*  571 */           Part part1 = message.part().name(childParam.getPartName());
/*  572 */           part1.type(jaxbContext.getTypeName(childParam.getXMLBridge().getTypeInfo()));
/*      */         }  continue;
/*      */       } 
/*  575 */       Part part = message.part().name(param.getPartName());
/*  576 */       part.element(param.getName());
/*      */     } 
/*      */ 
/*      */     
/*  580 */     if (method.getMEP() != MEP.ONE_WAY) {
/*  581 */       message = this.portDefinitions.message().name(method.getResponseMessageName());
/*  582 */       this.extension.addOutputMessageExtension((TypedXmlWriter)message, (JavaMethod)method);
/*      */       
/*  584 */       for (ParameterImpl param : method.getResponseParameters()) {
/*  585 */         if (isDoclit) {
/*  586 */           Part part1 = message.part().name(param.getPartName());
/*  587 */           part1.element(param.getName());
/*      */           continue;
/*      */         } 
/*  590 */         if (param.isWrapperStyle()) {
/*  591 */           for (ParameterImpl childParam : ((WrapperParameter)param).getWrapperChildren()) {
/*  592 */             Part part1 = message.part().name(childParam.getPartName());
/*  593 */             part1.type(jaxbContext.getTypeName(childParam.getXMLBridge().getTypeInfo()));
/*      */           }  continue;
/*      */         } 
/*  596 */         Part part = message.part().name(param.getPartName());
/*  597 */         part.element(param.getName());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  602 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  603 */       QName tagName = (exception.getDetailType()).tagName;
/*  604 */       String messageName = exception.getMessageName();
/*  605 */       QName messageQName = new QName(this.model.getTargetNamespace(), messageName);
/*  606 */       if (this.processedExceptions.contains(messageQName))
/*      */         continue; 
/*  608 */       message = this.portDefinitions.message().name(messageName);
/*      */       
/*  610 */       this.extension.addFaultMessageExtension((TypedXmlWriter)message, (JavaMethod)method, (CheckedException)exception);
/*  611 */       Part part = message.part().name("fault");
/*  612 */       part.element(tagName);
/*  613 */       this.processedExceptions.add(messageQName);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generatePortType() {
/*  622 */     PortType portType = this.portDefinitions.portType().name(this.model.getPortTypeName().getLocalPart());
/*  623 */     this.extension.addPortTypeExtension((TypedXmlWriter)portType);
/*  624 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  625 */       Operation operation = portType.operation().name(method.getOperationName());
/*  626 */       generateParameterOrder(operation, method);
/*  627 */       this.extension.addOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  628 */       switch (method.getMEP()) {
/*      */         
/*      */         case REQUEST_RESPONSE:
/*  631 */           generateInputMessage(operation, method);
/*      */           
/*  633 */           generateOutputMessage(operation, method);
/*      */           break;
/*      */         case ONE_WAY:
/*  636 */           generateInputMessage(operation, method);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  642 */       for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  643 */         QName messageName = new QName(this.model.getTargetNamespace(), exception.getMessageName());
/*  644 */         FaultType paramType = operation.fault().message(messageName).name(exception.getMessageName());
/*  645 */         this.extension.addOperationFaultExtension((TypedXmlWriter)paramType, (JavaMethod)method, (CheckedException)exception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isWrapperStyle(JavaMethodImpl method) {
/*  656 */     if (method.getRequestParameters().size() > 0) {
/*  657 */       ParameterImpl param = method.getRequestParameters().iterator().next();
/*  658 */       return param.isWrapperStyle();
/*      */     } 
/*  660 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRpcLit(JavaMethodImpl method) {
/*  669 */     return (method.getBinding().getStyle() == SOAPBinding.Style.RPC);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateParameterOrder(Operation operation, JavaMethodImpl method) {
/*  678 */     if (method.getMEP() == MEP.ONE_WAY)
/*      */       return; 
/*  680 */     if (isRpcLit(method)) {
/*  681 */       generateRpcParameterOrder(operation, method);
/*      */     } else {
/*  683 */       generateDocumentParameterOrder(operation, method);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateRpcParameterOrder(Operation operation, JavaMethodImpl method) {
/*  693 */     StringBuilder paramOrder = new StringBuilder();
/*  694 */     Set<String> partNames = new HashSet<String>();
/*  695 */     List<ParameterImpl> sortedParams = sortMethodParameters(method);
/*  696 */     int i = 0;
/*  697 */     for (ParameterImpl parameter : sortedParams) {
/*  698 */       if (parameter.getIndex() >= 0) {
/*  699 */         String partName = parameter.getPartName();
/*  700 */         if (!partNames.contains(partName)) {
/*  701 */           if (i++ > 0)
/*  702 */             paramOrder.append(' '); 
/*  703 */           paramOrder.append(partName);
/*  704 */           partNames.add(partName);
/*      */         } 
/*      */       } 
/*      */     } 
/*  708 */     if (i > 1) {
/*  709 */       operation.parameterOrder(paramOrder.toString());
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
/*      */   protected void generateDocumentParameterOrder(Operation operation, JavaMethodImpl method) {
/*  721 */     StringBuilder paramOrder = new StringBuilder();
/*  722 */     Set<String> partNames = new HashSet<String>();
/*  723 */     List<ParameterImpl> sortedParams = sortMethodParameters(method);
/*      */     
/*  725 */     int i = 0;
/*  726 */     for (ParameterImpl parameter : sortedParams) {
/*      */       
/*  728 */       if (parameter.getIndex() < 0) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/*  733 */       String partName = parameter.getPartName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  749 */       if (!partNames.contains(partName)) {
/*  750 */         if (i++ > 0)
/*  751 */           paramOrder.append(' '); 
/*  752 */         paramOrder.append(partName);
/*  753 */         partNames.add(partName);
/*      */       } 
/*      */     } 
/*  756 */     if (i > 1) {
/*  757 */       operation.parameterOrder(paramOrder.toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<ParameterImpl> sortMethodParameters(JavaMethodImpl method) {
/*  767 */     Set<ParameterImpl> paramSet = new HashSet<ParameterImpl>();
/*  768 */     List<ParameterImpl> sortedParams = new ArrayList<ParameterImpl>();
/*  769 */     if (isRpcLit(method)) {
/*  770 */       for (ParameterImpl parameterImpl : method.getRequestParameters()) {
/*  771 */         if (parameterImpl instanceof WrapperParameter) {
/*  772 */           paramSet.addAll(((WrapperParameter)parameterImpl).getWrapperChildren()); continue;
/*      */         } 
/*  774 */         paramSet.add(parameterImpl);
/*      */       } 
/*      */       
/*  777 */       for (ParameterImpl parameterImpl : method.getResponseParameters()) {
/*  778 */         if (parameterImpl instanceof WrapperParameter) {
/*  779 */           paramSet.addAll(((WrapperParameter)parameterImpl).getWrapperChildren()); continue;
/*      */         } 
/*  781 */         paramSet.add(parameterImpl);
/*      */       } 
/*      */     } else {
/*      */       
/*  785 */       paramSet.addAll(method.getRequestParameters());
/*  786 */       paramSet.addAll(method.getResponseParameters());
/*      */     } 
/*  788 */     Iterator<ParameterImpl> params = paramSet.iterator();
/*  789 */     if (paramSet.isEmpty())
/*  790 */       return sortedParams; 
/*  791 */     ParameterImpl param = params.next();
/*  792 */     sortedParams.add(param);
/*      */ 
/*      */     
/*  795 */     for (int i = 1; i < paramSet.size(); i++) {
/*  796 */       param = params.next(); int pos;
/*  797 */       for (pos = 0; pos < i; pos++) {
/*  798 */         ParameterImpl sortedParam = sortedParams.get(pos);
/*  799 */         if (param.getIndex() == sortedParam.getIndex() && param instanceof WrapperParameter) {
/*      */           break;
/*      */         }
/*  802 */         if (param.getIndex() < sortedParam.getIndex()) {
/*      */           break;
/*      */         }
/*      */       } 
/*  806 */       sortedParams.add(pos, param);
/*      */     } 
/*  808 */     return sortedParams;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isBodyParameter(ParameterImpl parameter) {
/*  817 */     ParameterBinding paramBinding = parameter.getBinding();
/*  818 */     return paramBinding.isBody();
/*      */   }
/*      */   
/*      */   protected boolean isHeaderParameter(ParameterImpl parameter) {
/*  822 */     ParameterBinding paramBinding = parameter.getBinding();
/*  823 */     return paramBinding.isHeader();
/*      */   }
/*      */   
/*      */   protected boolean isAttachmentParameter(ParameterImpl parameter) {
/*  827 */     ParameterBinding paramBinding = parameter.getBinding();
/*  828 */     return paramBinding.isAttachment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateBinding() {
/*  836 */     Binding newBinding = this.serviceDefinitions.binding().name(this.model.getBoundPortTypeName().getLocalPart());
/*  837 */     this.extension.addBindingExtension((TypedXmlWriter)newBinding);
/*  838 */     newBinding.type(this.model.getPortTypeName());
/*  839 */     boolean first = true;
/*  840 */     for (JavaMethodImpl method : this.model.getJavaMethods()) {
/*  841 */       if (first) {
/*  842 */         SOAPBinding sBinding = method.getBinding();
/*  843 */         SOAPVersion soapVersion = sBinding.getSOAPVersion();
/*  844 */         if (soapVersion == SOAPVersion.SOAP_12)
/*  845 */         { SOAPBinding soapBinding = newBinding.soap12Binding();
/*  846 */           soapBinding.transport(this.binding.getBindingId().getTransport());
/*  847 */           if (sBinding.getStyle().equals(SOAPBinding.Style.DOCUMENT)) {
/*  848 */             soapBinding.style("document");
/*      */           } else {
/*  850 */             soapBinding.style("rpc");
/*      */           }  }
/*  852 */         else { SOAPBinding soapBinding = newBinding.soapBinding();
/*  853 */           soapBinding.transport(this.binding.getBindingId().getTransport());
/*  854 */           if (sBinding.getStyle().equals(SOAPBinding.Style.DOCUMENT)) {
/*  855 */             soapBinding.style("document");
/*      */           } else {
/*  857 */             soapBinding.style("rpc");
/*      */           }  }
/*  859 */          first = false;
/*      */       } 
/*  861 */       if (this.binding.getBindingId().getSOAPVersion() == SOAPVersion.SOAP_12) {
/*  862 */         generateSOAP12BindingOperation(method, newBinding); continue;
/*      */       } 
/*  864 */       generateBindingOperation(method, newBinding);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateBindingOperation(JavaMethodImpl method, Binding binding) {
/*  869 */     BindingOperationType operation = binding.operation().name(method.getOperationName());
/*  870 */     this.extension.addBindingOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  871 */     String targetNamespace = this.model.getTargetNamespace();
/*  872 */     QName requestMessage = new QName(targetNamespace, method.getOperationName());
/*  873 */     List<ParameterImpl> bodyParams = new ArrayList<ParameterImpl>();
/*  874 */     List<ParameterImpl> headerParams = new ArrayList<ParameterImpl>();
/*  875 */     splitParameters(bodyParams, headerParams, method.getRequestParameters());
/*  876 */     SOAPBinding soapBinding = method.getBinding();
/*  877 */     operation.soapOperation().soapAction(soapBinding.getSOAPAction());
/*      */ 
/*      */     
/*  880 */     StartWithExtensionsType startWithExtensionsType = operation.input();
/*  881 */     this.extension.addBindingOperationInputExtension((TypedXmlWriter)startWithExtensionsType, (JavaMethod)method);
/*  882 */     BodyType body = (BodyType)startWithExtensionsType._element(Body.class);
/*  883 */     boolean isRpc = soapBinding.getStyle().equals(SOAPBinding.Style.RPC);
/*  884 */     if (soapBinding.getUse() == SOAPBinding.Use.LITERAL) {
/*  885 */       body.use("literal");
/*  886 */       if (headerParams.size() > 0) {
/*  887 */         if (bodyParams.size() > 0) {
/*  888 */           ParameterImpl param = bodyParams.iterator().next();
/*  889 */           if (isRpc) {
/*  890 */             StringBuilder parts = new StringBuilder();
/*  891 */             int i = 0;
/*  892 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  893 */               if (i++ > 0)
/*  894 */                 parts.append(' '); 
/*  895 */               parts.append(parameter.getPartName());
/*      */             } 
/*  897 */             body.parts(parts.toString());
/*      */           } else {
/*  899 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/*  902 */           body.parts("");
/*      */         } 
/*  904 */         generateSOAPHeaders((TypedXmlWriter)startWithExtensionsType, headerParams, requestMessage);
/*      */       } 
/*  906 */       if (isRpc) {
/*  907 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } else {
/*      */       
/*  911 */       throw new WebServiceException("encoded use is not supported");
/*      */     } 
/*      */     
/*  914 */     if (method.getMEP() != MEP.ONE_WAY) {
/*      */       
/*  916 */       bodyParams.clear();
/*  917 */       headerParams.clear();
/*  918 */       splitParameters(bodyParams, headerParams, method.getResponseParameters());
/*  919 */       StartWithExtensionsType startWithExtensionsType1 = operation.output();
/*  920 */       this.extension.addBindingOperationOutputExtension((TypedXmlWriter)startWithExtensionsType1, (JavaMethod)method);
/*  921 */       body = (BodyType)startWithExtensionsType1._element(Body.class);
/*  922 */       body.use("literal");
/*  923 */       if (headerParams.size() > 0) {
/*  924 */         StringBuilder parts = new StringBuilder();
/*  925 */         if (bodyParams.size() > 0) {
/*  926 */           ParameterImpl param = bodyParams.iterator().hasNext() ? bodyParams.iterator().next() : null;
/*  927 */           if (param != null) {
/*  928 */             if (isRpc) {
/*  929 */               int i = 0;
/*  930 */               for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  931 */                 if (i++ > 0) {
/*  932 */                   parts.append(" ");
/*      */                 }
/*  934 */                 parts.append(parameter.getPartName());
/*      */               } 
/*      */             } else {
/*  937 */               parts = new StringBuilder(param.getPartName());
/*      */             } 
/*      */           }
/*      */         } 
/*  941 */         body.parts(parts.toString());
/*  942 */         QName responseMessage = new QName(targetNamespace, method.getResponseMessageName());
/*  943 */         generateSOAPHeaders((TypedXmlWriter)startWithExtensionsType1, headerParams, responseMessage);
/*      */       } 
/*  945 */       if (isRpc) {
/*  946 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } 
/*  949 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/*  950 */       Fault fault = operation.fault().name(exception.getMessageName());
/*  951 */       this.extension.addBindingOperationFaultExtension((TypedXmlWriter)fault, (JavaMethod)method, (CheckedException)exception);
/*  952 */       SOAPFault soapFault = ((SOAPFault)fault._element(SOAPFault.class)).name(exception.getMessageName());
/*  953 */       soapFault.use("literal");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateSOAP12BindingOperation(JavaMethodImpl method, Binding binding) {
/*  958 */     BindingOperationType operation = binding.operation().name(method.getOperationName());
/*  959 */     this.extension.addBindingOperationExtension((TypedXmlWriter)operation, (JavaMethod)method);
/*  960 */     String targetNamespace = this.model.getTargetNamespace();
/*  961 */     QName requestMessage = new QName(targetNamespace, method.getOperationName());
/*  962 */     ArrayList<ParameterImpl> bodyParams = new ArrayList<ParameterImpl>();
/*  963 */     ArrayList<ParameterImpl> headerParams = new ArrayList<ParameterImpl>();
/*  964 */     splitParameters(bodyParams, headerParams, method.getRequestParameters());
/*  965 */     SOAPBinding soapBinding = method.getBinding();
/*      */     
/*  967 */     String soapAction = soapBinding.getSOAPAction();
/*  968 */     if (soapAction != null) {
/*  969 */       operation.soap12Operation().soapAction(soapAction);
/*      */     }
/*      */ 
/*      */     
/*  973 */     StartWithExtensionsType startWithExtensionsType = operation.input();
/*  974 */     this.extension.addBindingOperationInputExtension((TypedXmlWriter)startWithExtensionsType, (JavaMethod)method);
/*  975 */     BodyType body = (BodyType)startWithExtensionsType._element(Body.class);
/*  976 */     boolean isRpc = soapBinding.getStyle().equals(SOAPBinding.Style.RPC);
/*  977 */     if (soapBinding.getUse().equals(SOAPBinding.Use.LITERAL)) {
/*  978 */       body.use("literal");
/*  979 */       if (headerParams.size() > 0) {
/*  980 */         if (bodyParams.size() > 0) {
/*  981 */           ParameterImpl param = bodyParams.iterator().next();
/*  982 */           if (isRpc) {
/*  983 */             StringBuilder parts = new StringBuilder();
/*  984 */             int i = 0;
/*  985 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/*  986 */               if (i++ > 0)
/*  987 */                 parts.append(' '); 
/*  988 */               parts.append(parameter.getPartName());
/*      */             } 
/*  990 */             body.parts(parts.toString());
/*      */           } else {
/*  992 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/*  995 */           body.parts("");
/*      */         } 
/*  997 */         generateSOAP12Headers((TypedXmlWriter)startWithExtensionsType, headerParams, requestMessage);
/*      */       } 
/*  999 */       if (isRpc) {
/* 1000 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } else {
/*      */       
/* 1004 */       throw new WebServiceException("encoded use is not supported");
/*      */     } 
/*      */     
/* 1007 */     if (method.getMEP() != MEP.ONE_WAY) {
/*      */       
/* 1009 */       bodyParams.clear();
/* 1010 */       headerParams.clear();
/* 1011 */       splitParameters(bodyParams, headerParams, method.getResponseParameters());
/* 1012 */       StartWithExtensionsType startWithExtensionsType1 = operation.output();
/* 1013 */       this.extension.addBindingOperationOutputExtension((TypedXmlWriter)startWithExtensionsType1, (JavaMethod)method);
/* 1014 */       body = (BodyType)startWithExtensionsType1._element(Body.class);
/* 1015 */       body.use("literal");
/* 1016 */       if (headerParams.size() > 0) {
/* 1017 */         if (bodyParams.size() > 0) {
/* 1018 */           ParameterImpl param = bodyParams.iterator().next();
/* 1019 */           if (isRpc) {
/* 1020 */             StringBuilder parts = new StringBuilder();
/* 1021 */             int i = 0;
/* 1022 */             for (ParameterImpl parameter : ((WrapperParameter)param).getWrapperChildren()) {
/* 1023 */               if (i++ > 0) {
/* 1024 */                 parts.append(" ");
/*      */               }
/* 1026 */               parts.append(parameter.getPartName());
/*      */             } 
/* 1028 */             body.parts(parts.toString());
/*      */           } else {
/* 1030 */             body.parts(param.getPartName());
/*      */           } 
/*      */         } else {
/* 1033 */           body.parts("");
/*      */         } 
/* 1035 */         QName responseMessage = new QName(targetNamespace, method.getResponseMessageName());
/* 1036 */         generateSOAP12Headers((TypedXmlWriter)startWithExtensionsType1, headerParams, responseMessage);
/*      */       } 
/* 1038 */       if (isRpc) {
/* 1039 */         body.namespace(((ParameterImpl)method.getRequestParameters().iterator().next()).getName().getNamespaceURI());
/*      */       }
/*      */     } 
/* 1042 */     for (CheckedExceptionImpl exception : method.getCheckedExceptions()) {
/* 1043 */       Fault fault = operation.fault().name(exception.getMessageName());
/* 1044 */       this.extension.addBindingOperationFaultExtension((TypedXmlWriter)fault, (JavaMethod)method, (CheckedException)exception);
/* 1045 */       SOAPFault soapFault = ((SOAPFault)fault._element(SOAPFault.class)).name(exception.getMessageName());
/* 1046 */       soapFault.use("literal");
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void splitParameters(List<ParameterImpl> bodyParams, List<ParameterImpl> headerParams, List<ParameterImpl> params) {
/* 1051 */     for (ParameterImpl parameter : params) {
/* 1052 */       if (isBodyParameter(parameter)) {
/* 1053 */         bodyParams.add(parameter); continue;
/*      */       } 
/* 1055 */       headerParams.add(parameter);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateSOAPHeaders(TypedXmlWriter writer, List<ParameterImpl> parameters, QName message) {
/* 1062 */     for (ParameterImpl headerParam : parameters) {
/* 1063 */       Header header = (Header)writer._element(Header.class);
/* 1064 */       header.message(message);
/* 1065 */       header.part(headerParam.getPartName());
/* 1066 */       header.use("literal");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void generateSOAP12Headers(TypedXmlWriter writer, List<ParameterImpl> parameters, QName message) {
/* 1072 */     for (ParameterImpl headerParam : parameters) {
/* 1073 */       Header header = (Header)writer._element(Header.class);
/* 1074 */       header.message(message);
/*      */ 
/*      */       
/* 1077 */       header.part(headerParam.getPartName());
/* 1078 */       header.use("literal");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateService() {
/* 1086 */     QName portQName = this.model.getPortName();
/* 1087 */     QName serviceQName = this.model.getServiceQName();
/* 1088 */     Service service = this.serviceDefinitions.service().name(serviceQName.getLocalPart());
/* 1089 */     this.extension.addServiceExtension((TypedXmlWriter)service);
/* 1090 */     Port port = service.port().name(portQName.getLocalPart());
/* 1091 */     port.binding(this.model.getBoundPortTypeName());
/* 1092 */     this.extension.addPortExtension((TypedXmlWriter)port);
/* 1093 */     if (this.model.getJavaMethods().isEmpty()) {
/*      */       return;
/*      */     }
/* 1096 */     if (this.binding.getBindingId().getSOAPVersion() == SOAPVersion.SOAP_12) {
/* 1097 */       SOAPAddress address = (SOAPAddress)port._element(SOAPAddress.class);
/* 1098 */       address.location(this.endpointAddress);
/*      */     } else {
/* 1100 */       SOAPAddress address = (SOAPAddress)port._element(SOAPAddress.class);
/* 1101 */       address.location(this.endpointAddress);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void generateInputMessage(Operation operation, JavaMethodImpl method) {
/* 1106 */     ParamType paramType = operation.input();
/* 1107 */     this.extension.addOperationInputExtension((TypedXmlWriter)paramType, (JavaMethod)method);
/*      */     
/* 1109 */     paramType.message(new QName(this.model.getTargetNamespace(), method.getRequestMessageName()));
/*      */   }
/*      */   
/*      */   protected void generateOutputMessage(Operation operation, JavaMethodImpl method) {
/* 1113 */     ParamType paramType = operation.output();
/* 1114 */     this.extension.addOperationOutputExtension((TypedXmlWriter)paramType, (JavaMethod)method);
/*      */     
/* 1116 */     paramType.message(new QName(this.model.getTargetNamespace(), method.getResponseMessageName()));
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
/*      */   public Result createOutputFile(String namespaceUri, String suggestedFileName) throws IOException {
/*      */     String schemaLoc;
/* 1129 */     if (namespaceUri == null) {
/* 1130 */       return null;
/*      */     }
/*      */     
/* 1133 */     Holder<String> fileNameHolder = new Holder<String>();
/* 1134 */     fileNameHolder.value = (T)(this.schemaPrefix + suggestedFileName);
/* 1135 */     Result result = this.wsdlResolver.getSchemaOutput(namespaceUri, fileNameHolder);
/*      */ 
/*      */ 
/*      */     
/* 1139 */     if (result == null) {
/* 1140 */       schemaLoc = (String)fileNameHolder.value;
/*      */     } else {
/* 1142 */       schemaLoc = relativize(result.getSystemId(), this.wsdlLocation);
/* 1143 */     }  boolean isEmptyNs = namespaceUri.trim().equals("");
/* 1144 */     if (!isEmptyNs) {
/* 1145 */       Import _import = this.types.schema()._import();
/* 1146 */       _import.namespace(namespaceUri);
/* 1147 */       _import.schemaLocation(schemaLoc);
/*      */     } 
/* 1149 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private Result createInlineSchema(String namespaceUri, String suggestedFileName) throws IOException {
/* 1154 */     if (namespaceUri.equals("")) {
/* 1155 */       return null;
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
/* 1167 */     TXWResult tXWResult = new TXWResult((TypedXmlWriter)this.types);
/* 1168 */     tXWResult.setSystemId("");
/*      */     
/* 1170 */     return (Result)tXWResult;
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
/*      */   protected static String relativize(String uri, String baseUri) {
/*      */     try {
/* 1194 */       assert uri != null;
/*      */       
/* 1196 */       if (baseUri == null) return uri;
/*      */       
/* 1198 */       URI theUri = new URI(Util.escapeURI(uri));
/* 1199 */       URI theBaseUri = new URI(Util.escapeURI(baseUri));
/*      */       
/* 1201 */       if (theUri.isOpaque() || theBaseUri.isOpaque()) {
/* 1202 */         return uri;
/*      */       }
/* 1204 */       if (!Util.equalsIgnoreCase(theUri.getScheme(), theBaseUri.getScheme()) || !Util.equal(theUri.getAuthority(), theBaseUri.getAuthority()))
/*      */       {
/* 1206 */         return uri;
/*      */       }
/* 1208 */       String uriPath = theUri.getPath();
/* 1209 */       String basePath = theBaseUri.getPath();
/*      */ 
/*      */       
/* 1212 */       if (!basePath.endsWith("/")) {
/* 1213 */         basePath = Util.normalizeUriPath(basePath);
/*      */       }
/*      */       
/* 1216 */       if (uriPath.equals(basePath)) {
/* 1217 */         return ".";
/*      */       }
/* 1219 */       String relPath = calculateRelativePath(uriPath, basePath);
/*      */       
/* 1221 */       if (relPath == null)
/* 1222 */         return uri; 
/* 1223 */       StringBuilder relUri = new StringBuilder();
/* 1224 */       relUri.append(relPath);
/* 1225 */       if (theUri.getQuery() != null)
/* 1226 */         relUri.append('?').append(theUri.getQuery()); 
/* 1227 */       if (theUri.getFragment() != null) {
/* 1228 */         relUri.append('#').append(theUri.getFragment());
/*      */       }
/* 1230 */       return relUri.toString();
/* 1231 */     } catch (URISyntaxException e) {
/* 1232 */       throw new InternalError("Error escaping one of these uris:\n\t" + uri + "\n\t" + baseUri);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String calculateRelativePath(String uri, String base) {
/* 1237 */     if (base == null) {
/* 1238 */       return null;
/*      */     }
/* 1240 */     if (uri.startsWith(base)) {
/* 1241 */       return uri.substring(base.length());
/*      */     }
/* 1243 */     return "../" + calculateRelativePath(uri, Util.getParentUriPath(base));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class JAXWSOutputSchemaResolver
/*      */     extends SchemaOutputResolver
/*      */   {
/* 1252 */     ArrayList<DOMResult> nonGlassfishSchemas = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
/* 1264 */       return WSDLGenerator.this.inlineSchemas ? ((this.nonGlassfishSchemas != null) ? nonGlassfishSchemaResult(namespaceUri, suggestedFileName) : WSDLGenerator.this.createInlineSchema(namespaceUri, suggestedFileName)) : WSDLGenerator.this.createOutputFile(namespaceUri, suggestedFileName);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Result nonGlassfishSchemaResult(String namespaceUri, String suggestedFileName) throws IOException {
/* 1271 */       DOMResult result = new DOMResult();
/* 1272 */       result.setSystemId("");
/* 1273 */       this.nonGlassfishSchemas.add(result);
/* 1274 */       return result;
/*      */     }
/*      */   }
/*      */   
/*      */   private void register(WSDLGeneratorExtension h) {
/* 1279 */     this.extensionHandlers.add(h);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\WSDLGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */