/*      */ package com.sun.xml.ws.wsdl.parser;
/*      */ 
/*      */ import com.sun.istack.NotNull;
/*      */ import com.sun.istack.Nullable;
/*      */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*      */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*      */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*      */ import com.sun.xml.ws.api.BindingID;
/*      */ import com.sun.xml.ws.api.BindingIDFactory;
/*      */ import com.sun.xml.ws.api.EndpointAddress;
/*      */ import com.sun.xml.ws.api.SOAPVersion;
/*      */ import com.sun.xml.ws.api.WSDLLocator;
/*      */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*      */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*      */ import com.sun.xml.ws.api.model.ParameterBinding;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLDescriptorKind;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLInput;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLMessage;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOutput;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPartDescriptor;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLPortType;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLService;
/*      */ import com.sun.xml.ws.api.policy.PolicyResolver;
/*      */ import com.sun.xml.ws.api.policy.PolicyResolverFactory;
/*      */ import com.sun.xml.ws.api.server.Container;
/*      */ import com.sun.xml.ws.api.server.ContainerResolver;
/*      */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*      */ import com.sun.xml.ws.api.wsdl.parser.MetaDataResolver;
/*      */ import com.sun.xml.ws.api.wsdl.parser.MetadataResolverFactory;
/*      */ import com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor;
/*      */ import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
/*      */ import com.sun.xml.ws.api.wsdl.parser.XMLEntityResolver;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLBoundFaultImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLBoundOperationImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLFaultImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLInputImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLMessageImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLModelImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLOperationImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLOutputImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLPartDescriptorImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLPartImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLPortTypeImpl;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLServiceImpl;
/*      */ import com.sun.xml.ws.policy.jaxws.PolicyWSDLParserExtension;
/*      */ import com.sun.xml.ws.resources.ClientMessages;
/*      */ import com.sun.xml.ws.resources.WsdlmodelMessages;
/*      */ import com.sun.xml.ws.streaming.SourceReaderFactory;
/*      */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*      */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*      */ import com.sun.xml.ws.util.ServiceFinder;
/*      */ import com.sun.xml.ws.util.xml.XmlUtil;
/*      */ import java.io.FilterInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Logger;
/*      */ import javax.jws.soap.SOAPBinding;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import javax.xml.ws.Service;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import org.xml.sax.EntityResolver;
/*      */ import org.xml.sax.SAXException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RuntimeWSDLParser
/*      */ {
/*      */   private final WSDLModelImpl wsdlDoc;
/*      */   private String targetNamespace;
/*  112 */   private final Set<String> importedWSDLs = new HashSet<String>();
/*      */ 
/*      */ 
/*      */   
/*      */   private final XMLEntityResolver resolver;
/*      */ 
/*      */   
/*      */   private final PolicyResolver policyResolver;
/*      */ 
/*      */   
/*      */   private final WSDLParserExtension extensionFacade;
/*      */ 
/*      */   
/*      */   private final WSDLParserExtensionContextImpl context;
/*      */ 
/*      */   
/*      */   List<WSDLParserExtension> extensions;
/*      */ 
/*      */   
/*  131 */   Map<String, String> wsdldef_nsdecl = new HashMap<String, String>();
/*  132 */   Map<String, String> service_nsdecl = new HashMap<String, String>();
/*  133 */   Map<String, String> port_nsdecl = new HashMap<String, String>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WSDLModelImpl parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  147 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, Service.class, PolicyResolverFactory.create(), extensions);
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
/*      */   public static WSDLModelImpl parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  162 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, serviceClass, PolicyResolverFactory.create(), extensions);
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
/*      */   public static WSDLModelImpl parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, @NotNull PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  177 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, Service.class, policyResolver, extensions);
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
/*      */   public static WSDLModelImpl parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, @NotNull PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  193 */     return parse(wsdlLoc, wsdlSource, resolver, isClientSide, container, serviceClass, policyResolver, false, extensions);
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
/*      */   public static WSDLModelImpl parse(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, @NotNull PolicyResolver policyResolver, boolean isUseStreamFromEntityResolverWrapper, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*      */     XMLEntityResolver.Parser parser;
/*  210 */     assert resolver != null;
/*      */     
/*  212 */     RuntimeWSDLParser wsdlParser = new RuntimeWSDLParser(wsdlSource.getSystemId(), new EntityResolverWrapper(resolver, isUseStreamFromEntityResolverWrapper), isClientSide, container, policyResolver, extensions);
/*      */     
/*      */     try {
/*  215 */       parser = wsdlParser.resolveWSDL(wsdlLoc, wsdlSource, serviceClass);
/*  216 */       if (!hasWSDLDefinitions(parser.parser)) {
/*  217 */         throw new XMLStreamException(ClientMessages.RUNTIME_WSDLPARSER_INVALID_WSDL(parser.systemId, WSDLConstants.QNAME_DEFINITIONS, parser.parser.getName(), parser.parser.getLocation()));
/*      */       }
/*      */     }
/*  220 */     catch (XMLStreamException e) {
/*      */       
/*  222 */       if (wsdlLoc == null)
/*  223 */         throw e; 
/*  224 */       return tryWithMex(wsdlParser, wsdlLoc, resolver, isClientSide, container, e, serviceClass, policyResolver, extensions);
/*      */     }
/*  226 */     catch (IOException e) {
/*      */       
/*  228 */       if (wsdlLoc == null)
/*  229 */         throw e; 
/*  230 */       return tryWithMex(wsdlParser, wsdlLoc, resolver, isClientSide, container, e, serviceClass, policyResolver, extensions);
/*      */     } 
/*  232 */     wsdlParser.extensionFacade.start(wsdlParser.context);
/*  233 */     wsdlParser.parseWSDL(parser, false);
/*  234 */     wsdlParser.wsdlDoc.freeze();
/*  235 */     wsdlParser.extensionFacade.finished(wsdlParser.context);
/*  236 */     wsdlParser.extensionFacade.postFinished(wsdlParser.context);
/*      */     
/*  238 */     if (wsdlParser.wsdlDoc.getServices().isEmpty()) {
/*  239 */       throw new WebServiceException(ClientMessages.WSDL_CONTAINS_NO_SERVICE(wsdlLoc));
/*      */     }
/*  241 */     return wsdlParser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private static WSDLModelImpl tryWithMex(@NotNull RuntimeWSDLParser wsdlParser, @NotNull URL wsdlLoc, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Throwable e, Class serviceClass, PolicyResolver policyResolver, WSDLParserExtension... extensions) throws SAXException, XMLStreamException {
/*  245 */     ArrayList<Throwable> exceptions = new ArrayList<Throwable>();
/*      */     try {
/*  247 */       WSDLModelImpl wsdlModel = wsdlParser.parseUsingMex(wsdlLoc, resolver, isClientSide, container, serviceClass, policyResolver, extensions);
/*  248 */       if (wsdlModel == null) {
/*  249 */         throw new WebServiceException(ClientMessages.FAILED_TO_PARSE(wsdlLoc.toExternalForm(), e.getMessage()), e);
/*      */       }
/*  251 */       return wsdlModel;
/*  252 */     } catch (URISyntaxException e1) {
/*  253 */       exceptions.add(e);
/*  254 */       exceptions.add(e1);
/*  255 */     } catch (IOException e1) {
/*  256 */       exceptions.add(e);
/*  257 */       exceptions.add(e1);
/*      */     } 
/*  259 */     throw new InaccessibleWSDLException(exceptions);
/*      */   }
/*      */ 
/*      */   
/*      */   private WSDLModelImpl parseUsingMex(@NotNull URL wsdlLoc, @NotNull EntityResolver resolver, boolean isClientSide, Container container, Class serviceClass, PolicyResolver policyResolver, WSDLParserExtension[] extensions) throws IOException, SAXException, XMLStreamException, URISyntaxException {
/*  264 */     MetaDataResolver mdResolver = null;
/*  265 */     ServiceDescriptor serviceDescriptor = null;
/*  266 */     RuntimeWSDLParser wsdlParser = null;
/*      */ 
/*      */     
/*  269 */     for (MetadataResolverFactory resolverFactory : ServiceFinder.find(MetadataResolverFactory.class)) {
/*  270 */       mdResolver = resolverFactory.metadataResolver(resolver);
/*  271 */       serviceDescriptor = mdResolver.resolve(wsdlLoc.toURI());
/*      */       
/*  273 */       if (serviceDescriptor != null)
/*      */         break; 
/*      */     } 
/*  276 */     if (serviceDescriptor != null) {
/*  277 */       List<? extends Source> wsdls = serviceDescriptor.getWSDLs();
/*  278 */       wsdlParser = new RuntimeWSDLParser(wsdlLoc.toExternalForm(), new MexEntityResolver(wsdls), isClientSide, container, policyResolver, extensions);
/*  279 */       wsdlParser.extensionFacade.start(wsdlParser.context);
/*      */       
/*  281 */       for (Source src : wsdls) {
/*  282 */         String systemId = src.getSystemId();
/*  283 */         XMLEntityResolver.Parser parser = wsdlParser.resolver.resolveEntity(null, systemId);
/*  284 */         wsdlParser.parseWSDL(parser, false);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  289 */     if ((mdResolver == null || serviceDescriptor == null) && (wsdlLoc.getProtocol().equals("http") || wsdlLoc.getProtocol().equals("https")) && wsdlLoc.getQuery() == null) {
/*  290 */       String urlString = wsdlLoc.toExternalForm();
/*  291 */       urlString = urlString + "?wsdl";
/*  292 */       wsdlLoc = new URL(urlString);
/*  293 */       wsdlParser = new RuntimeWSDLParser(wsdlLoc.toExternalForm(), new EntityResolverWrapper(resolver), isClientSide, container, policyResolver, extensions);
/*  294 */       wsdlParser.extensionFacade.start(wsdlParser.context);
/*  295 */       XMLEntityResolver.Parser parser = resolveWSDL(wsdlLoc, new StreamSource(wsdlLoc.toExternalForm()), serviceClass);
/*  296 */       wsdlParser.parseWSDL(parser, false);
/*      */     } 
/*      */     
/*  299 */     if (wsdlParser == null) {
/*  300 */       return null;
/*      */     }
/*  302 */     wsdlParser.wsdlDoc.freeze();
/*  303 */     wsdlParser.extensionFacade.finished(wsdlParser.context);
/*  304 */     wsdlParser.extensionFacade.postFinished(wsdlParser.context);
/*  305 */     return wsdlParser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private static boolean hasWSDLDefinitions(XMLStreamReader reader) {
/*  309 */     XMLStreamReaderUtil.nextElementContent(reader);
/*  310 */     return reader.getName().equals(WSDLConstants.QNAME_DEFINITIONS);
/*      */   }
/*      */   
/*      */   public static WSDLModelImpl parse(XMLEntityResolver.Parser wsdl, XMLEntityResolver resolver, boolean isClientSide, Container container, PolicyResolver policyResolver, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  314 */     assert resolver != null;
/*  315 */     RuntimeWSDLParser parser = new RuntimeWSDLParser(wsdl.systemId.toExternalForm(), resolver, isClientSide, container, policyResolver, extensions);
/*  316 */     parser.extensionFacade.start(parser.context);
/*  317 */     parser.parseWSDL(wsdl, false);
/*  318 */     parser.wsdlDoc.freeze();
/*  319 */     parser.extensionFacade.finished(parser.context);
/*  320 */     parser.extensionFacade.postFinished(parser.context);
/*  321 */     return parser.wsdlDoc;
/*      */   }
/*      */   
/*      */   public static WSDLModelImpl parse(XMLEntityResolver.Parser wsdl, XMLEntityResolver resolver, boolean isClientSide, Container container, WSDLParserExtension... extensions) throws IOException, XMLStreamException, SAXException {
/*  325 */     assert resolver != null;
/*  326 */     RuntimeWSDLParser parser = new RuntimeWSDLParser(wsdl.systemId.toExternalForm(), resolver, isClientSide, container, PolicyResolverFactory.create(), extensions);
/*  327 */     parser.extensionFacade.start(parser.context);
/*  328 */     parser.parseWSDL(wsdl, false);
/*  329 */     parser.wsdlDoc.freeze();
/*  330 */     parser.extensionFacade.finished(parser.context);
/*  331 */     parser.extensionFacade.postFinished(parser.context);
/*  332 */     return parser.wsdlDoc;
/*      */   }
/*      */   
/*      */   private RuntimeWSDLParser(@NotNull String sourceLocation, XMLEntityResolver resolver, boolean isClientSide, Container container, PolicyResolver policyResolver, WSDLParserExtension... extensions) {
/*  336 */     this.wsdlDoc = (sourceLocation != null) ? new WSDLModelImpl(sourceLocation) : new WSDLModelImpl();
/*  337 */     this.resolver = resolver;
/*  338 */     this.policyResolver = policyResolver;
/*  339 */     this.extensions = new ArrayList<WSDLParserExtension>();
/*  340 */     this.context = new WSDLParserExtensionContextImpl((WSDLModel)this.wsdlDoc, isClientSide, container, policyResolver);
/*      */     
/*  342 */     boolean isPolicyExtensionFound = false;
/*  343 */     for (WSDLParserExtension e : extensions) {
/*  344 */       if (e instanceof com.sun.xml.ws.api.wsdl.parser.PolicyWSDLParserExtension)
/*  345 */         isPolicyExtensionFound = true; 
/*  346 */       register(e);
/*      */     } 
/*      */ 
/*      */     
/*  350 */     if (!isPolicyExtensionFound)
/*  351 */       register((WSDLParserExtension)new PolicyWSDLParserExtension()); 
/*  352 */     register(new MemberSubmissionAddressingWSDLParserExtension());
/*  353 */     register(new W3CAddressingWSDLParserExtension());
/*  354 */     register(new W3CAddressingMetadataWSDLParserExtension());
/*      */     
/*  356 */     this.extensionFacade = new WSDLParserExtensionFacade(this.extensions.<WSDLParserExtension>toArray(new WSDLParserExtension[0]));
/*      */   }
/*      */   
/*      */   private XMLEntityResolver.Parser resolveWSDL(@Nullable URL wsdlLoc, @NotNull Source wsdlSource, Class<Service> serviceClass) throws IOException, SAXException, XMLStreamException {
/*  360 */     String systemId = wsdlSource.getSystemId();
/*      */     
/*  362 */     XMLEntityResolver.Parser parser = this.resolver.resolveEntity(null, systemId);
/*  363 */     if (parser == null && wsdlLoc != null) {
/*  364 */       String exForm = wsdlLoc.toExternalForm();
/*  365 */       parser = this.resolver.resolveEntity(null, exForm);
/*      */       
/*  367 */       if (parser == null && serviceClass != null) {
/*  368 */         URL ru = serviceClass.getResource(".");
/*  369 */         if (ru != null) {
/*  370 */           String ruExForm = ru.toExternalForm();
/*  371 */           if (exForm.startsWith(ruExForm)) {
/*  372 */             parser = this.resolver.resolveEntity(null, exForm.substring(ruExForm.length()));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  377 */     if (parser == null) {
/*      */ 
/*      */ 
/*      */       
/*  381 */       if (isKnownReadableSource(wsdlSource)) {
/*  382 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlSource));
/*  383 */       } else if (wsdlLoc != null) {
/*  384 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlLoc, serviceClass));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  390 */       if (parser == null) {
/*  391 */         parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlSource));
/*      */       }
/*      */     } 
/*  394 */     return parser;
/*      */   }
/*      */   
/*      */   private boolean isKnownReadableSource(Source wsdlSource) {
/*  398 */     if (wsdlSource instanceof StreamSource) {
/*  399 */       return (((StreamSource)wsdlSource).getInputStream() != null || ((StreamSource)wsdlSource).getReader() != null);
/*      */     }
/*      */     
/*  402 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private XMLStreamReader createReader(@NotNull Source src) throws XMLStreamException {
/*  407 */     return (XMLStreamReader)new TidyXMLStreamReader(SourceReaderFactory.createSourceReader(src, true), null);
/*      */   }
/*      */   
/*      */   private void parseImport(@NotNull URL wsdlLoc) throws XMLStreamException, IOException, SAXException {
/*  411 */     String systemId = wsdlLoc.toExternalForm();
/*  412 */     XMLEntityResolver.Parser parser = this.resolver.resolveEntity(null, systemId);
/*  413 */     if (parser == null) {
/*  414 */       parser = new XMLEntityResolver.Parser(wsdlLoc, createReader(wsdlLoc));
/*      */     }
/*  416 */     parseWSDL(parser, true);
/*      */   }
/*      */   
/*      */   private void parseWSDL(XMLEntityResolver.Parser parser, boolean imported) throws XMLStreamException, IOException, SAXException {
/*  420 */     XMLStreamReader reader = parser.parser;
/*      */ 
/*      */     
/*      */     try {
/*  424 */       if (parser.systemId != null && !this.importedWSDLs.add(parser.systemId.toExternalForm())) {
/*      */         return;
/*      */       }
/*  427 */       if (reader.getEventType() == 7)
/*  428 */         XMLStreamReaderUtil.nextElementContent(reader); 
/*  429 */       if (WSDLConstants.QNAME_DEFINITIONS.equals(reader.getName())) {
/*  430 */         readNSDecl(this.wsdldef_nsdecl, reader);
/*      */       }
/*  432 */       if (reader.getEventType() != 8 && reader.getName().equals(WSDLConstants.QNAME_SCHEMA) && 
/*  433 */         imported) {
/*      */         
/*  435 */         LOGGER.warning(WsdlmodelMessages.WSDL_IMPORT_SHOULD_BE_WSDL(parser.systemId));
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  441 */       String tns = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/*      */       
/*  443 */       String oldTargetNamespace = this.targetNamespace;
/*  444 */       this.targetNamespace = tns;
/*      */       
/*  446 */       while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*      */         
/*  448 */         if (reader.getEventType() == 8) {
/*      */           break;
/*      */         }
/*  451 */         QName name = reader.getName();
/*  452 */         if (WSDLConstants.QNAME_IMPORT.equals(name)) {
/*  453 */           parseImport(parser.systemId, reader); continue;
/*  454 */         }  if (WSDLConstants.QNAME_MESSAGE.equals(name)) {
/*  455 */           parseMessage(reader); continue;
/*  456 */         }  if (WSDLConstants.QNAME_PORT_TYPE.equals(name)) {
/*  457 */           parsePortType(reader); continue;
/*  458 */         }  if (WSDLConstants.QNAME_BINDING.equals(name)) {
/*  459 */           parseBinding(reader); continue;
/*  460 */         }  if (WSDLConstants.QNAME_SERVICE.equals(name)) {
/*  461 */           parseService(reader); continue;
/*      */         } 
/*  463 */         this.extensionFacade.definitionsElements(reader);
/*      */       } 
/*      */       
/*  466 */       this.targetNamespace = oldTargetNamespace;
/*      */     } finally {
/*  468 */       this.wsdldef_nsdecl = new HashMap<String, String>();
/*  469 */       reader.close();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void parseService(XMLStreamReader reader) {
/*  474 */     this.service_nsdecl.putAll(this.wsdldef_nsdecl);
/*  475 */     readNSDecl(this.service_nsdecl, reader);
/*      */     
/*  477 */     String serviceName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  478 */     WSDLServiceImpl service = new WSDLServiceImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, serviceName));
/*  479 */     this.extensionFacade.serviceAttributes((WSDLService)service, reader);
/*  480 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  481 */       QName name = reader.getName();
/*  482 */       if (WSDLConstants.QNAME_PORT.equals(name)) {
/*  483 */         parsePort(reader, service);
/*  484 */         if (reader.getEventType() != 2)
/*  485 */           XMLStreamReaderUtil.next(reader); 
/*      */         continue;
/*      */       } 
/*  488 */       this.extensionFacade.serviceElements((WSDLService)service, reader);
/*      */     } 
/*      */     
/*  491 */     this.wsdlDoc.addService(service);
/*  492 */     this.service_nsdecl = new HashMap<String, String>();
/*      */   }
/*      */   
/*      */   private void parsePort(XMLStreamReader reader, WSDLServiceImpl service) {
/*  496 */     this.port_nsdecl.putAll(this.service_nsdecl);
/*  497 */     readNSDecl(this.port_nsdecl, reader);
/*      */     
/*  499 */     String portName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  500 */     String binding = ParserUtil.getMandatoryNonEmptyAttribute(reader, "binding");
/*      */     
/*  502 */     QName bindingName = ParserUtil.getQName(reader, binding);
/*  503 */     QName portQName = new QName(service.getName().getNamespaceURI(), portName);
/*  504 */     WSDLPortImpl port = new WSDLPortImpl(reader, service, portQName, bindingName);
/*      */     
/*  506 */     this.extensionFacade.portAttributes((WSDLPort)port, reader);
/*      */ 
/*      */     
/*  509 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  510 */       QName name = reader.getName();
/*  511 */       if (SOAPConstants.QNAME_ADDRESS.equals(name) || SOAPConstants.QNAME_SOAP12ADDRESS.equals(name)) {
/*  512 */         String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*  513 */         if (location != null) {
/*      */           try {
/*  515 */             port.setAddress(new EndpointAddress(location));
/*  516 */           } catch (URISyntaxException e) {}
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  521 */         XMLStreamReaderUtil.next(reader); continue;
/*  522 */       }  if (AddressingVersion.W3C.nsUri.equals(name.getNamespaceURI()) && "EndpointReference".equals(name.getLocalPart())) {
/*      */         
/*      */         try {
/*  525 */           StreamReaderBufferCreator creator = new StreamReaderBufferCreator(new MutableXMLStreamBuffer());
/*  526 */           XMLStreamBufferMark xMLStreamBufferMark = new XMLStreamBufferMark(this.port_nsdecl, (AbstractCreatorProcessor)creator);
/*  527 */           creator.createElementFragment(reader, false);
/*      */           
/*  529 */           WSEndpointReference wsepr = new WSEndpointReference((XMLStreamBuffer)xMLStreamBufferMark, AddressingVersion.W3C);
/*      */           
/*  531 */           port.setEPR(wsepr);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  537 */           if (reader.getEventType() == 2 && reader.getName().equals(WSDLConstants.QNAME_PORT))
/*      */             break; 
/*  539 */         } catch (XMLStreamException e) {
/*  540 */           throw new WebServiceException(e);
/*      */         } 
/*      */         continue;
/*      */       } 
/*  544 */       this.extensionFacade.portElements((WSDLPort)port, reader);
/*      */     } 
/*      */     
/*  547 */     if (port.getAddress() == null) {
/*      */       try {
/*  549 */         port.setAddress(new EndpointAddress(""));
/*  550 */       } catch (URISyntaxException e) {}
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  555 */     service.put(portQName, port);
/*  556 */     this.port_nsdecl = new HashMap<String, String>();
/*      */   }
/*      */   
/*      */   private void parseBinding(XMLStreamReader reader) {
/*  560 */     String bindingName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  561 */     String portTypeName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "type");
/*  562 */     if (bindingName == null || portTypeName == null) {
/*      */ 
/*      */ 
/*      */       
/*  566 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       return;
/*      */     } 
/*  569 */     WSDLBoundPortTypeImpl binding = new WSDLBoundPortTypeImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, bindingName), ParserUtil.getQName(reader, portTypeName));
/*      */     
/*  571 */     this.extensionFacade.bindingAttributes((WSDLBoundPortType)binding, reader);
/*      */     
/*  573 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  574 */       QName name = reader.getName();
/*  575 */       if (WSDLConstants.NS_SOAP_BINDING.equals(name)) {
/*  576 */         String transport = reader.getAttributeValue(null, "transport");
/*  577 */         binding.setBindingId(createBindingId(transport, SOAPVersion.SOAP_11));
/*      */         
/*  579 */         String style = reader.getAttributeValue(null, "style");
/*      */         
/*  581 */         if (style != null && style.equals("rpc")) {
/*  582 */           binding.setStyle(SOAPBinding.Style.RPC);
/*      */         } else {
/*  584 */           binding.setStyle(SOAPBinding.Style.DOCUMENT);
/*      */         } 
/*  586 */         goToEnd(reader); continue;
/*  587 */       }  if (WSDLConstants.NS_SOAP12_BINDING.equals(name)) {
/*  588 */         String transport = reader.getAttributeValue(null, "transport");
/*  589 */         binding.setBindingId(createBindingId(transport, SOAPVersion.SOAP_12));
/*      */         
/*  591 */         String style = reader.getAttributeValue(null, "style");
/*  592 */         if (style != null && style.equals("rpc")) {
/*  593 */           binding.setStyle(SOAPBinding.Style.RPC);
/*      */         } else {
/*  595 */           binding.setStyle(SOAPBinding.Style.DOCUMENT);
/*      */         } 
/*  597 */         goToEnd(reader); continue;
/*  598 */       }  if (WSDLConstants.QNAME_OPERATION.equals(name)) {
/*  599 */         parseBindingOperation(reader, binding); continue;
/*      */       } 
/*  601 */       this.extensionFacade.bindingElements((WSDLBoundPortType)binding, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static BindingID createBindingId(String transport, SOAPVersion soapVersion) {
/*  607 */     if (!transport.equals("http://schemas.xmlsoap.org/soap/http")) {
/*  608 */       for (BindingIDFactory f : ServiceFinder.find(BindingIDFactory.class)) {
/*  609 */         BindingID bindingId = f.create(transport, soapVersion);
/*  610 */         if (bindingId != null) {
/*  611 */           return bindingId;
/*      */         }
/*      */       } 
/*      */     }
/*  615 */     return soapVersion.equals(SOAPVersion.SOAP_11) ? (BindingID)BindingID.SOAP11_HTTP : (BindingID)BindingID.SOAP12_HTTP;
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseBindingOperation(XMLStreamReader reader, WSDLBoundPortTypeImpl binding) {
/*  620 */     String bindingOpName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  621 */     if (bindingOpName == null) {
/*      */ 
/*      */       
/*  624 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       
/*      */       return;
/*      */     } 
/*  628 */     QName opName = new QName(binding.getPortTypeName().getNamespaceURI(), bindingOpName);
/*  629 */     WSDLBoundOperationImpl bindingOp = new WSDLBoundOperationImpl(reader, binding, opName);
/*  630 */     binding.put(opName, bindingOp);
/*  631 */     this.extensionFacade.bindingOperationAttributes((WSDLBoundOperation)bindingOp, reader);
/*      */     
/*  633 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  634 */       QName name = reader.getName();
/*  635 */       String style = null;
/*  636 */       if (WSDLConstants.QNAME_INPUT.equals(name)) {
/*  637 */         parseInputBinding(reader, bindingOp);
/*  638 */       } else if (WSDLConstants.QNAME_OUTPUT.equals(name)) {
/*  639 */         parseOutputBinding(reader, bindingOp);
/*  640 */       } else if (WSDLConstants.QNAME_FAULT.equals(name)) {
/*  641 */         parseFaultBinding(reader, bindingOp);
/*  642 */       } else if (SOAPConstants.QNAME_OPERATION.equals(name) || SOAPConstants.QNAME_SOAP12OPERATION.equals(name)) {
/*      */         
/*  644 */         style = reader.getAttributeValue(null, "style");
/*  645 */         String soapAction = reader.getAttributeValue(null, "soapAction");
/*      */         
/*  647 */         if (soapAction != null) {
/*  648 */           bindingOp.setSoapAction(soapAction);
/*      */         }
/*  650 */         goToEnd(reader);
/*      */       } else {
/*  652 */         this.extensionFacade.bindingOperationElements((WSDLBoundOperation)bindingOp, reader);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  658 */       if (style != null) {
/*  659 */         if (style.equals("rpc")) {
/*  660 */           bindingOp.setStyle(SOAPBinding.Style.RPC); continue;
/*      */         } 
/*  662 */         bindingOp.setStyle(SOAPBinding.Style.DOCUMENT); continue;
/*      */       } 
/*  664 */       bindingOp.setStyle(binding.getStyle());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseInputBinding(XMLStreamReader reader, WSDLBoundOperationImpl bindingOp) {
/*  670 */     boolean bodyFound = false;
/*  671 */     this.extensionFacade.bindingOperationInputAttributes((WSDLBoundOperation)bindingOp, reader);
/*  672 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  673 */       QName name = reader.getName();
/*  674 */       if ((SOAPConstants.QNAME_BODY.equals(name) || SOAPConstants.QNAME_SOAP12BODY.equals(name)) && !bodyFound) {
/*  675 */         bodyFound = true;
/*  676 */         bindingOp.setInputExplicitBodyParts(parseSOAPBodyBinding(reader, bindingOp, BindingMode.INPUT));
/*  677 */         goToEnd(reader); continue;
/*  678 */       }  if (SOAPConstants.QNAME_HEADER.equals(name) || SOAPConstants.QNAME_SOAP12HEADER.equals(name)) {
/*  679 */         parseSOAPHeaderBinding(reader, bindingOp.getInputParts()); continue;
/*  680 */       }  if (MIMEConstants.QNAME_MULTIPART_RELATED.equals(name)) {
/*  681 */         parseMimeMultipartBinding(reader, bindingOp, BindingMode.INPUT); continue;
/*      */       } 
/*  683 */       this.extensionFacade.bindingOperationInputElements((WSDLBoundOperation)bindingOp, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseOutputBinding(XMLStreamReader reader, WSDLBoundOperationImpl bindingOp) {
/*  689 */     boolean bodyFound = false;
/*  690 */     this.extensionFacade.bindingOperationOutputAttributes((WSDLBoundOperation)bindingOp, reader);
/*  691 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  692 */       QName name = reader.getName();
/*  693 */       if ((SOAPConstants.QNAME_BODY.equals(name) || SOAPConstants.QNAME_SOAP12BODY.equals(name)) && !bodyFound) {
/*  694 */         bodyFound = true;
/*  695 */         bindingOp.setOutputExplicitBodyParts(parseSOAPBodyBinding(reader, bindingOp, BindingMode.OUTPUT));
/*  696 */         goToEnd(reader); continue;
/*  697 */       }  if (SOAPConstants.QNAME_HEADER.equals(name) || SOAPConstants.QNAME_SOAP12HEADER.equals(name)) {
/*  698 */         parseSOAPHeaderBinding(reader, bindingOp.getOutputParts()); continue;
/*  699 */       }  if (MIMEConstants.QNAME_MULTIPART_RELATED.equals(name)) {
/*  700 */         parseMimeMultipartBinding(reader, bindingOp, BindingMode.OUTPUT); continue;
/*      */       } 
/*  702 */       this.extensionFacade.bindingOperationOutputElements((WSDLBoundOperation)bindingOp, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void parseFaultBinding(XMLStreamReader reader, WSDLBoundOperationImpl bindingOp) {
/*  708 */     String faultName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  709 */     WSDLBoundFaultImpl wsdlBoundFault = new WSDLBoundFaultImpl(reader, faultName, bindingOp);
/*  710 */     bindingOp.addFault(wsdlBoundFault);
/*      */     
/*  712 */     this.extensionFacade.bindingOperationFaultAttributes((WSDLBoundFault)wsdlBoundFault, reader);
/*      */     
/*  714 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2)
/*  715 */       this.extensionFacade.bindingOperationFaultElements((WSDLBoundFault)wsdlBoundFault, reader); 
/*      */   }
/*      */   
/*      */   private enum BindingMode
/*      */   {
/*  720 */     INPUT, OUTPUT, FAULT; }
/*      */   
/*      */   private static boolean parseSOAPBodyBinding(XMLStreamReader reader, WSDLBoundOperationImpl op, BindingMode mode) {
/*  723 */     String namespace = reader.getAttributeValue(null, "namespace");
/*  724 */     if (mode == BindingMode.INPUT) {
/*  725 */       op.setRequestNamespace(namespace);
/*  726 */       return parseSOAPBodyBinding(reader, op.getInputParts());
/*      */     } 
/*      */     
/*  729 */     op.setResponseNamespace(namespace);
/*  730 */     return parseSOAPBodyBinding(reader, op.getOutputParts());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean parseSOAPBodyBinding(XMLStreamReader reader, Map<String, ParameterBinding> parts) {
/*  737 */     String partsString = reader.getAttributeValue(null, "parts");
/*  738 */     if (partsString != null) {
/*  739 */       List<String> partsList = XmlUtil.parseTokenList(partsString);
/*  740 */       if (partsList.isEmpty()) {
/*  741 */         parts.put(" ", ParameterBinding.BODY);
/*      */       } else {
/*  743 */         for (String part : partsList) {
/*  744 */           parts.put(part, ParameterBinding.BODY);
/*      */         }
/*      */       } 
/*  747 */       return true;
/*      */     } 
/*  749 */     return false;
/*      */   }
/*      */   
/*      */   private static void parseSOAPHeaderBinding(XMLStreamReader reader, Map<String, ParameterBinding> parts) {
/*  753 */     String part = reader.getAttributeValue(null, "part");
/*      */     
/*  755 */     if (part == null || part.equals("")) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  762 */     parts.put(part, ParameterBinding.HEADER);
/*  763 */     goToEnd(reader);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void parseMimeMultipartBinding(XMLStreamReader reader, WSDLBoundOperationImpl op, BindingMode mode) {
/*  768 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  769 */       QName name = reader.getName();
/*  770 */       if (MIMEConstants.QNAME_PART.equals(name)) {
/*  771 */         parseMIMEPart(reader, op, mode); continue;
/*      */       } 
/*  773 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void parseMIMEPart(XMLStreamReader reader, WSDLBoundOperationImpl op, BindingMode mode) {
/*  779 */     boolean bodyFound = false;
/*  780 */     Map<String, ParameterBinding> parts = null;
/*  781 */     if (mode == BindingMode.INPUT) {
/*  782 */       parts = op.getInputParts();
/*  783 */     } else if (mode == BindingMode.OUTPUT) {
/*  784 */       parts = op.getOutputParts();
/*  785 */     } else if (mode == BindingMode.FAULT) {
/*  786 */       parts = op.getFaultParts();
/*      */     } 
/*  788 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  789 */       QName name = reader.getName();
/*  790 */       if (SOAPConstants.QNAME_BODY.equals(name) && !bodyFound) {
/*  791 */         bodyFound = true;
/*  792 */         parseSOAPBodyBinding(reader, op, mode);
/*  793 */         XMLStreamReaderUtil.next(reader); continue;
/*  794 */       }  if (SOAPConstants.QNAME_HEADER.equals(name)) {
/*  795 */         bodyFound = true;
/*  796 */         parseSOAPHeaderBinding(reader, parts);
/*  797 */         XMLStreamReaderUtil.next(reader); continue;
/*  798 */       }  if (MIMEConstants.QNAME_CONTENT.equals(name)) {
/*  799 */         String part = reader.getAttributeValue(null, "part");
/*  800 */         String type = reader.getAttributeValue(null, "type");
/*  801 */         if (part == null || type == null) {
/*  802 */           XMLStreamReaderUtil.skipElement(reader);
/*      */           continue;
/*      */         } 
/*  805 */         ParameterBinding sb = ParameterBinding.createAttachment(type);
/*  806 */         if (parts != null && sb != null && part != null)
/*  807 */           parts.put(part, sb); 
/*  808 */         XMLStreamReaderUtil.next(reader); continue;
/*      */       } 
/*  810 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void parseImport(@Nullable URL baseURL, XMLStreamReader reader) throws IOException, SAXException, XMLStreamException {
/*      */     URL importURL;
/*  817 */     String importLocation = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*      */ 
/*      */     
/*  820 */     if (baseURL != null) {
/*  821 */       importURL = new URL(baseURL, importLocation);
/*      */     } else {
/*  823 */       importURL = new URL(importLocation);
/*  824 */     }  parseImport(importURL);
/*  825 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  826 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortType(XMLStreamReader reader) {
/*  831 */     String portTypeName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  832 */     if (portTypeName == null) {
/*      */ 
/*      */       
/*  835 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       return;
/*      */     } 
/*  838 */     WSDLPortTypeImpl portType = new WSDLPortTypeImpl(reader, this.wsdlDoc, new QName(this.targetNamespace, portTypeName));
/*  839 */     this.extensionFacade.portTypeAttributes((WSDLPortType)portType, reader);
/*  840 */     this.wsdlDoc.addPortType(portType);
/*  841 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  842 */       QName name = reader.getName();
/*  843 */       if (WSDLConstants.QNAME_OPERATION.equals(name)) {
/*  844 */         parsePortTypeOperation(reader, portType); continue;
/*      */       } 
/*  846 */       this.extensionFacade.portTypeElements((WSDLPortType)portType, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void parsePortTypeOperation(XMLStreamReader reader, WSDLPortTypeImpl portType) {
/*  853 */     String operationName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  854 */     if (operationName == null) {
/*      */ 
/*      */       
/*  857 */       XMLStreamReaderUtil.skipElement(reader);
/*      */       
/*      */       return;
/*      */     } 
/*  861 */     QName operationQName = new QName(portType.getName().getNamespaceURI(), operationName);
/*  862 */     WSDLOperationImpl operation = new WSDLOperationImpl(reader, portType, operationQName);
/*  863 */     this.extensionFacade.portTypeOperationAttributes((WSDLOperation)operation, reader);
/*  864 */     String parameterOrder = ParserUtil.getAttribute(reader, "parameterOrder");
/*  865 */     operation.setParameterOrder(parameterOrder);
/*  866 */     portType.put(operationName, operation);
/*  867 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  868 */       QName name = reader.getName();
/*  869 */       if (name.equals(WSDLConstants.QNAME_INPUT)) {
/*  870 */         parsePortTypeOperationInput(reader, operation); continue;
/*  871 */       }  if (name.equals(WSDLConstants.QNAME_OUTPUT)) {
/*  872 */         parsePortTypeOperationOutput(reader, operation); continue;
/*  873 */       }  if (name.equals(WSDLConstants.QNAME_FAULT)) {
/*  874 */         parsePortTypeOperationFault(reader, operation); continue;
/*      */       } 
/*  876 */       this.extensionFacade.portTypeOperationElements((WSDLOperation)operation, reader);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void parsePortTypeOperationFault(XMLStreamReader reader, WSDLOperationImpl operation) {
/*  883 */     String msg = ParserUtil.getMandatoryNonEmptyAttribute(reader, "message");
/*  884 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  885 */     String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  886 */     WSDLFaultImpl fault = new WSDLFaultImpl(reader, name, msgName, operation);
/*  887 */     operation.addFault(fault);
/*  888 */     this.extensionFacade.portTypeOperationFaultAttributes((WSDLFault)fault, reader);
/*  889 */     this.extensionFacade.portTypeOperationFault((WSDLOperation)operation, reader);
/*  890 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  891 */       this.extensionFacade.portTypeOperationFaultElements((WSDLFault)fault, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortTypeOperationInput(XMLStreamReader reader, WSDLOperationImpl operation) {
/*  896 */     String msg = ParserUtil.getMandatoryNonEmptyAttribute(reader, "message");
/*  897 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  898 */     String name = ParserUtil.getAttribute(reader, "name");
/*  899 */     WSDLInputImpl input = new WSDLInputImpl(reader, name, msgName, operation);
/*  900 */     operation.setInput(input);
/*  901 */     this.extensionFacade.portTypeOperationInputAttributes((WSDLInput)input, reader);
/*  902 */     this.extensionFacade.portTypeOperationInput((WSDLOperation)operation, reader);
/*  903 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  904 */       this.extensionFacade.portTypeOperationInputElements((WSDLInput)input, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parsePortTypeOperationOutput(XMLStreamReader reader, WSDLOperationImpl operation) {
/*  909 */     String msg = ParserUtil.getAttribute(reader, "message");
/*  910 */     QName msgName = ParserUtil.getQName(reader, msg);
/*  911 */     String name = ParserUtil.getAttribute(reader, "name");
/*  912 */     WSDLOutputImpl output = new WSDLOutputImpl(reader, name, msgName, operation);
/*  913 */     operation.setOutput(output);
/*  914 */     this.extensionFacade.portTypeOperationOutputAttributes((WSDLOutput)output, reader);
/*  915 */     this.extensionFacade.portTypeOperationOutput((WSDLOperation)operation, reader);
/*  916 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  917 */       this.extensionFacade.portTypeOperationOutputElements((WSDLOutput)output, reader);
/*      */     }
/*      */   }
/*      */   
/*      */   private void parseMessage(XMLStreamReader reader) {
/*  922 */     String msgName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  923 */     WSDLMessageImpl msg = new WSDLMessageImpl(reader, new QName(this.targetNamespace, msgName));
/*  924 */     this.extensionFacade.messageAttributes((WSDLMessage)msg, reader);
/*  925 */     int partIndex = 0;
/*  926 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  927 */       QName name = reader.getName();
/*  928 */       if (WSDLConstants.QNAME_PART.equals(name)) {
/*  929 */         String part = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*  930 */         String desc = null;
/*  931 */         int index = reader.getAttributeCount();
/*  932 */         WSDLDescriptorKind kind = WSDLDescriptorKind.ELEMENT;
/*  933 */         for (int i = 0; i < index; i++) {
/*  934 */           QName descName = reader.getAttributeName(i);
/*  935 */           if (descName.getLocalPart().equals("element")) {
/*  936 */             kind = WSDLDescriptorKind.ELEMENT;
/*  937 */           } else if (descName.getLocalPart().equals("type")) {
/*  938 */             kind = WSDLDescriptorKind.TYPE;
/*      */           } 
/*  940 */           if (descName.getLocalPart().equals("element") || descName.getLocalPart().equals("type")) {
/*  941 */             desc = reader.getAttributeValue(i);
/*      */             break;
/*      */           } 
/*      */         } 
/*  945 */         if (desc != null) {
/*  946 */           WSDLPartImpl wsdlPart = new WSDLPartImpl(reader, part, partIndex, (WSDLPartDescriptor)new WSDLPartDescriptorImpl(reader, ParserUtil.getQName(reader, desc), kind));
/*  947 */           msg.add(wsdlPart);
/*      */         } 
/*  949 */         if (reader.getEventType() != 2)
/*  950 */           goToEnd(reader);  continue;
/*      */       } 
/*  952 */       this.extensionFacade.messageElements((WSDLMessage)msg, reader);
/*      */     } 
/*      */     
/*  955 */     this.wsdlDoc.addMessage(msg);
/*  956 */     if (reader.getEventType() != 2)
/*  957 */       goToEnd(reader); 
/*      */   }
/*      */   
/*      */   private static void goToEnd(XMLStreamReader reader) {
/*  961 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*  962 */       XMLStreamReaderUtil.skipElement(reader);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamReader createReader(URL wsdlLoc) throws IOException, XMLStreamException {
/*  972 */     return createReader(wsdlLoc, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLStreamReader createReader(URL wsdlLoc, Class<Service> serviceClass) throws IOException, XMLStreamException {
/*      */     InputStream inputStream;
/*      */     try {
/*  983 */       inputStream = wsdlLoc.openStream();
/*  984 */     } catch (IOException io) {
/*      */ 
/*      */       
/*  987 */       if (serviceClass != null)
/*  988 */       { WSDLLocator locator = (WSDLLocator)ContainerResolver.getInstance().getContainer().getSPI(WSDLLocator.class);
/*  989 */         if (locator != null)
/*  990 */         { String exForm = wsdlLoc.toExternalForm();
/*  991 */           URL ru = serviceClass.getResource(".");
/*  992 */           String loc = wsdlLoc.getPath();
/*  993 */           if (ru != null) {
/*  994 */             String ruExForm = ru.toExternalForm();
/*  995 */             if (exForm.startsWith(ruExForm)) {
/*  996 */               loc = exForm.substring(ruExForm.length());
/*      */             }
/*      */           } 
/*  999 */           wsdlLoc = locator.locateWSDL(serviceClass, loc);
/* 1000 */           if (wsdlLoc != null)
/* 1001 */           { inputStream = new FilterInputStream(wsdlLoc.openStream())
/*      */               {
/*      */                 boolean closed;
/*      */                 
/*      */                 public void close() throws IOException {
/* 1006 */                   if (!this.closed) {
/* 1007 */                     this.closed = true;
/* 1008 */                     byte[] buf = new byte[8192];
/* 1009 */                     while (read(buf) != -1);
/* 1010 */                     super.close();
/*      */                   }
/*      */                 
/*      */                 }
/*      */               }; }
/*      */           
/*      */           else
/*      */           
/* 1018 */           { throw io; }  } else { throw io; }  } else { throw io; }
/*      */     
/*      */     } 
/*      */     
/* 1022 */     return (XMLStreamReader)new TidyXMLStreamReader(XMLStreamReaderFactory.create(wsdlLoc.toExternalForm(), inputStream, false), inputStream);
/*      */   }
/*      */ 
/*      */   
/*      */   private void register(WSDLParserExtension e) {
/* 1027 */     this.extensions.add(new FoolProofParserExtension(e));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void readNSDecl(Map<String, String> ns_map, XMLStreamReader reader) {
/* 1038 */     if (reader.getNamespaceCount() > 0) {
/* 1039 */       for (int i = 0; i < reader.getNamespaceCount(); i++) {
/* 1040 */         ns_map.put(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 1045 */   private static final Logger LOGGER = Logger.getLogger(RuntimeWSDLParser.class.getName());
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\RuntimeWSDLParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */