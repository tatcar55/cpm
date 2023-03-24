/*     */ package com.sun.xml.ws.transport.http;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.DatabindingModeFeature;
/*     */ import com.oracle.webservices.api.databinding.ExternalMetadataFeature;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.SDDocumentSource;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.binding.WebServiceFeatureList;
/*     */ import com.sun.xml.ws.handler.HandlerChainsModel;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import com.sun.xml.ws.resources.WsservletMessages;
/*     */ import com.sun.xml.ws.server.EndpointFactory;
/*     */ import com.sun.xml.ws.server.ServerRtException;
/*     */ import com.sun.xml.ws.streaming.Attributes;
/*     */ import com.sun.xml.ws.streaming.TidyXMLStreamReader;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.ws.util.exception.LocatableWebServiceException;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
/*     */ import javax.xml.ws.soap.SOAPBinding;
/*     */ import org.xml.sax.EntityResolver;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeploymentDescriptorParser<A>
/*     */ {
/*     */   public static final String NS_RUNTIME = "http://java.sun.com/xml/ns/jax-ws/ri/runtime";
/*     */   public static final String JAXWS_WSDL_DD_DIR = "WEB-INF/wsdl";
/* 113 */   public static final QName QNAME_ENDPOINTS = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoints");
/* 114 */   public static final QName QNAME_ENDPOINT = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoint");
/* 115 */   public static final QName QNAME_EXT_METADA = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "external-metadata");
/*     */   
/*     */   public static final String ATTR_FILE = "file";
/*     */   
/*     */   public static final String ATTR_RESOURCE = "resource";
/*     */   
/*     */   public static final String ATTR_VERSION = "version";
/*     */   public static final String ATTR_NAME = "name";
/*     */   public static final String ATTR_IMPLEMENTATION = "implementation";
/*     */   public static final String ATTR_WSDL = "wsdl";
/*     */   public static final String ATTR_SERVICE = "service";
/*     */   public static final String ATTR_PORT = "port";
/*     */   public static final String ATTR_URL_PATTERN = "url-pattern";
/*     */   public static final String ATTR_ENABLE_MTOM = "enable-mtom";
/*     */   public static final String ATTR_MTOM_THRESHOLD_VALUE = "mtom-threshold-value";
/*     */   public static final String ATTR_BINDING = "binding";
/*     */   public static final String ATTR_DATABINDING = "databinding";
/* 132 */   public static final List<String> ATTRVALUE_SUPPORTED_VERSIONS = Arrays.asList(new String[] { "2.0", "2.1" });
/*     */   
/* 134 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.server.http");
/*     */ 
/*     */   
/*     */   private final Container container;
/*     */   
/*     */   private final ClassLoader classLoader;
/*     */   
/*     */   private final ResourceLoader loader;
/*     */   
/*     */   private final AdapterFactory<A> adapterFactory;
/*     */   
/* 145 */   private final Set<String> names = new HashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   private final Map<String, SDDocumentSource> docs = new HashMap<String, SDDocumentSource>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DeploymentDescriptorParser(ClassLoader cl, ResourceLoader loader, Container container, AdapterFactory<A> adapterFactory) throws MalformedURLException {
/* 160 */     this.classLoader = cl;
/* 161 */     this.loader = loader;
/* 162 */     this.container = container;
/* 163 */     this.adapterFactory = adapterFactory;
/*     */     
/* 165 */     collectDocs("/WEB-INF/wsdl/");
/* 166 */     logger.log(Level.FINE, "war metadata={0}", this.docs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<A> parse(String systemId, InputStream is) {
/*     */     TidyXMLStreamReader tidyXMLStreamReader;
/* 174 */     XMLStreamReader reader = null;
/*     */     try {
/* 176 */       tidyXMLStreamReader = new TidyXMLStreamReader(XMLStreamReaderFactory.create(systemId, is, true), is);
/*     */       
/* 178 */       XMLStreamReaderUtil.nextElementContent((XMLStreamReader)tidyXMLStreamReader);
/* 179 */       return parseAdapters((XMLStreamReader)tidyXMLStreamReader);
/*     */     } finally {
/* 181 */       if (tidyXMLStreamReader != null) {
/*     */         try {
/* 183 */           tidyXMLStreamReader.close();
/* 184 */         } catch (XMLStreamException e) {
/* 185 */           throw new ServerRtException("runtime.parser.xmlReader", new Object[] { e });
/*     */         } 
/*     */       }
/*     */       try {
/* 189 */         is.close();
/* 190 */       } catch (IOException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public List<A> parse(File f) throws IOException {
/* 201 */     FileInputStream in = new FileInputStream(f);
/*     */     try {
/* 203 */       return parse(f.getPath(), in);
/*     */     } finally {
/* 205 */       in.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectDocs(String dirPath) throws MalformedURLException {
/* 213 */     Set<String> paths = this.loader.getResourcePaths(dirPath);
/* 214 */     if (paths != null) {
/* 215 */       for (String path : paths) {
/* 216 */         if (path.endsWith("/")) {
/* 217 */           if (path.endsWith("/CVS/") || path.endsWith("/.svn/")) {
/*     */             continue;
/*     */           }
/* 220 */           collectDocs(path); continue;
/*     */         } 
/* 222 */         URL res = this.loader.getResource(path);
/* 223 */         this.docs.put(res.toString(), SDDocumentSource.create(res));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<A> parseAdapters(XMLStreamReader reader) {
/* 231 */     if (!reader.getName().equals(QNAME_ENDPOINTS)) {
/* 232 */       failWithFullName("runtime.parser.invalidElement", reader);
/*     */     }
/*     */     
/* 235 */     List<A> adapters = new ArrayList<A>();
/*     */     
/* 237 */     Attributes attrs = XMLStreamReaderUtil.getAttributes(reader);
/* 238 */     String version = getMandatoryNonEmptyAttribute(reader, attrs, "version");
/* 239 */     if (!ATTRVALUE_SUPPORTED_VERSIONS.contains(version)) {
/* 240 */       failWithLocalName("runtime.parser.invalidVersionNumber", reader, version);
/*     */     }
/*     */     
/* 243 */     while (XMLStreamReaderUtil.nextElementContent(reader) != 2) {
/*     */       
/* 245 */       if (reader.getName().equals(QNAME_ENDPOINT)) {
/* 246 */         attrs = XMLStreamReaderUtil.getAttributes(reader);
/*     */         
/* 248 */         String name = getMandatoryNonEmptyAttribute(reader, attrs, "name");
/* 249 */         if (!this.names.add(name)) {
/* 250 */           logger.warning(WsservletMessages.SERVLET_WARNING_DUPLICATE_ENDPOINT_NAME());
/*     */         }
/*     */ 
/*     */         
/* 254 */         String implementationName = getMandatoryNonEmptyAttribute(reader, attrs, "implementation");
/*     */         
/* 256 */         Class<?> implementorClass = getImplementorClass(implementationName, reader);
/*     */         
/* 258 */         MetadataReader metadataReader = null;
/* 259 */         ExternalMetadataFeature externalMetadataFeature = null;
/*     */ 
/*     */         
/* 262 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 263 */         if (reader.getEventType() != 2) {
/* 264 */           externalMetadataFeature = configureExternalMetadataReader(reader);
/* 265 */           if (externalMetadataFeature != null) {
/* 266 */             metadataReader = externalMetadataFeature.getMetadataReader(implementorClass.getClassLoader());
/*     */           }
/*     */         } 
/*     */         
/* 270 */         QName serviceName = getQNameAttribute(attrs, "service");
/* 271 */         if (serviceName == null) {
/* 272 */           serviceName = EndpointFactory.getDefaultServiceName(implementorClass, metadataReader);
/*     */         }
/*     */         
/* 275 */         QName portName = getQNameAttribute(attrs, "port");
/* 276 */         if (portName == null) {
/* 277 */           portName = EndpointFactory.getDefaultPortName(serviceName, implementorClass, metadataReader);
/*     */         }
/*     */ 
/*     */         
/* 281 */         String enable_mtom = getAttribute(attrs, "enable-mtom");
/* 282 */         String mtomThreshold = getAttribute(attrs, "mtom-threshold-value");
/* 283 */         String dbMode = getAttribute(attrs, "databinding");
/* 284 */         String bindingId = getAttribute(attrs, "binding");
/* 285 */         if (bindingId != null)
/*     */         {
/* 287 */           bindingId = getBindingIdForToken(bindingId);
/*     */         }
/* 289 */         WSBinding binding = createBinding(bindingId, implementorClass, enable_mtom, mtomThreshold, dbMode);
/* 290 */         if (externalMetadataFeature != null) {
/* 291 */           binding.getFeatures().mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)externalMetadataFeature }, true);
/*     */         }
/*     */ 
/*     */         
/* 295 */         String urlPattern = getMandatoryNonEmptyAttribute(reader, attrs, "url-pattern");
/*     */ 
/*     */         
/* 298 */         boolean handlersSetInDD = setHandlersAndRoles(binding, reader, serviceName, portName);
/*     */         
/* 300 */         EndpointFactory.verifyImplementorClass(implementorClass, metadataReader);
/* 301 */         SDDocumentSource primaryWSDL = getPrimaryWSDL(reader, attrs, implementorClass, metadataReader);
/*     */         
/* 303 */         WSEndpoint<?> endpoint = WSEndpoint.create(implementorClass, !handlersSetInDD, null, serviceName, portName, this.container, binding, primaryWSDL, this.docs.values(), createEntityResolver(), false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 309 */         adapters.add(this.adapterFactory.createAdapter(name, urlPattern, endpoint)); continue;
/*     */       } 
/* 311 */       failWithLocalName("runtime.parser.invalidElement", reader);
/*     */     } 
/*     */     
/* 314 */     return adapters;
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
/*     */   
/*     */   private static WSBinding createBinding(String ddBindingId, Class implClass, String mtomEnabled, String mtomThreshold, String dataBindingMode) {
/*     */     WebServiceFeatureList features;
/*     */     BindingID bindingID;
/* 329 */     MTOMFeature mtomfeature = null;
/* 330 */     if (mtomEnabled != null) {
/* 331 */       if (mtomThreshold != null) {
/* 332 */         mtomfeature = new MTOMFeature(Boolean.valueOf(mtomEnabled).booleanValue(), Integer.valueOf(mtomThreshold).intValue());
/*     */       } else {
/*     */         
/* 335 */         mtomfeature = new MTOMFeature(Boolean.valueOf(mtomEnabled).booleanValue());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 340 */     if (ddBindingId != null) {
/* 341 */       bindingID = BindingID.parse(ddBindingId);
/* 342 */       features = bindingID.createBuiltinFeatureList();
/*     */       
/* 344 */       if (checkMtomConflict((MTOMFeature)features.get(MTOMFeature.class), mtomfeature)) {
/* 345 */         throw new ServerRtException(ServerMessages.DD_MTOM_CONFLICT(ddBindingId, mtomEnabled), new Object[0]);
/*     */       }
/*     */     } else {
/* 348 */       bindingID = BindingID.parse(implClass);
/*     */ 
/*     */ 
/*     */       
/* 352 */       features = new WebServiceFeatureList();
/* 353 */       if (mtomfeature != null) {
/* 354 */         features.add(mtomfeature);
/*     */       }
/* 356 */       features.addAll((Iterable)bindingID.createBuiltinFeatureList());
/*     */     } 
/*     */     
/* 359 */     if (dataBindingMode != null) {
/* 360 */       features.add((WebServiceFeature)new DatabindingModeFeature(dataBindingMode));
/*     */     }
/*     */     
/* 363 */     return bindingID.createBinding(features.toArray());
/*     */   }
/*     */   
/*     */   private static boolean checkMtomConflict(MTOMFeature lhs, MTOMFeature rhs) {
/* 367 */     if (lhs == null || rhs == null) {
/* 368 */       return false;
/*     */     }
/* 370 */     return lhs.isEnabled() ^ rhs.isEnabled();
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
/*     */   @NotNull
/*     */   public static String getBindingIdForToken(@NotNull String lexical) {
/* 383 */     if (lexical.equals("##SOAP11_HTTP"))
/* 384 */       return "http://schemas.xmlsoap.org/wsdl/soap/http"; 
/* 385 */     if (lexical.equals("##SOAP11_HTTP_MTOM"))
/* 386 */       return "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true"; 
/* 387 */     if (lexical.equals("##SOAP12_HTTP"))
/* 388 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/"; 
/* 389 */     if (lexical.equals("##SOAP12_HTTP_MTOM"))
/* 390 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true"; 
/* 391 */     if (lexical.equals("##XML_HTTP")) {
/* 392 */       return "http://www.w3.org/2004/08/wsdl/http";
/*     */     }
/* 394 */     return lexical;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SDDocumentSource getPrimaryWSDL(XMLStreamReader xsr, Attributes attrs, Class<?> implementorClass, MetadataReader metadataReader) {
/* 415 */     String wsdlFile = getAttribute(attrs, "wsdl");
/* 416 */     if (wsdlFile == null) {
/* 417 */       wsdlFile = EndpointFactory.getWsdlLocation(implementorClass, metadataReader);
/*     */     }
/*     */     
/* 420 */     if (wsdlFile != null) {
/* 421 */       URL wsdl; if (!wsdlFile.startsWith("WEB-INF/wsdl")) {
/* 422 */         logger.log(Level.WARNING, "Ignoring wrong wsdl={0}. It should start with {1}. Going to generate and publish a new WSDL.", new Object[] { wsdlFile, "WEB-INF/wsdl" });
/* 423 */         return null;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 428 */         wsdl = this.loader.getResource('/' + wsdlFile);
/* 429 */       } catch (MalformedURLException e) {
/* 430 */         throw new LocatableWebServiceException(ServerMessages.RUNTIME_PARSER_WSDL_NOT_FOUND(wsdlFile), e, xsr);
/*     */       } 
/*     */       
/* 433 */       if (wsdl == null) {
/* 434 */         throw new LocatableWebServiceException(ServerMessages.RUNTIME_PARSER_WSDL_NOT_FOUND(wsdlFile), xsr);
/*     */       }
/*     */       
/* 437 */       SDDocumentSource docInfo = this.docs.get(wsdl.toExternalForm());
/* 438 */       assert docInfo != null;
/* 439 */       return docInfo;
/*     */     } 
/*     */     
/* 442 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityResolver createEntityResolver() {
/*     */     try {
/* 450 */       return XmlUtil.createEntityResolver(this.loader.getCatalogFile());
/* 451 */     } catch (MalformedURLException e) {
/* 452 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getAttribute(Attributes attrs, String name) {
/* 457 */     String value = attrs.getValue(name);
/* 458 */     if (value != null) {
/* 459 */       value = value.trim();
/*     */     }
/* 461 */     return value;
/*     */   }
/*     */   
/*     */   protected QName getQNameAttribute(Attributes attrs, String name) {
/* 465 */     String value = getAttribute(attrs, name);
/* 466 */     if (value == null || value.equals("")) {
/* 467 */       return null;
/*     */     }
/* 469 */     return QName.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNonEmptyAttribute(XMLStreamReader reader, Attributes attrs, String name) {
/* 474 */     String value = getAttribute(attrs, name);
/* 475 */     if (value != null && value.equals("")) {
/* 476 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 481 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryAttribute(XMLStreamReader reader, Attributes attrs, String name) {
/* 485 */     String value = getAttribute(attrs, name);
/* 486 */     if (value == null) {
/* 487 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/*     */     }
/* 489 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMandatoryNonEmptyAttribute(XMLStreamReader reader, Attributes attributes, String name) {
/* 494 */     String value = getAttribute(attributes, name);
/* 495 */     if (value == null) {
/* 496 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/* 497 */     } else if (value.equals("")) {
/* 498 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 503 */     return value;
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
/*     */   protected boolean setHandlersAndRoles(WSBinding binding, XMLStreamReader reader, QName serviceName, QName portName) {
/* 515 */     if (reader.getEventType() == 2 || !reader.getName().equals(HandlerChainsModel.QNAME_HANDLER_CHAINS))
/*     */     {
/* 517 */       return false;
/*     */     }
/*     */     
/* 520 */     HandlerAnnotationInfo handlerInfo = HandlerChainsModel.parseHandlerFile(reader, this.classLoader, serviceName, portName, binding);
/*     */ 
/*     */     
/* 523 */     binding.setHandlerChain(handlerInfo.getHandlers());
/* 524 */     if (binding instanceof SOAPBinding) {
/* 525 */       ((SOAPBinding)binding).setRoles(handlerInfo.getRoles());
/*     */     }
/*     */ 
/*     */     
/* 529 */     XMLStreamReaderUtil.nextContent(reader);
/* 530 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ExternalMetadataFeature configureExternalMetadataReader(XMLStreamReader reader) {
/* 535 */     ExternalMetadataFeature.Builder featureBuilder = null;
/* 536 */     while (QNAME_EXT_METADA.equals(reader.getName())) {
/*     */       
/* 538 */       if (reader.getEventType() == 1) {
/* 539 */         Attributes attrs = XMLStreamReaderUtil.getAttributes(reader);
/* 540 */         String file = getAttribute(attrs, "file");
/* 541 */         if (file != null) {
/* 542 */           if (featureBuilder == null) {
/* 543 */             featureBuilder = ExternalMetadataFeature.builder();
/*     */           }
/* 545 */           featureBuilder.addFiles(new File[] { new File(file) });
/*     */         } 
/*     */         
/* 548 */         String res = getAttribute(attrs, "resource");
/* 549 */         if (res != null) {
/* 550 */           if (featureBuilder == null) {
/* 551 */             featureBuilder = ExternalMetadataFeature.builder();
/*     */           }
/* 553 */           featureBuilder.addResources(new String[] { res });
/*     */         } 
/*     */       } 
/*     */       
/* 557 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     } 
/*     */     
/* 560 */     return buildFeature(featureBuilder);
/*     */   }
/*     */   
/*     */   private ExternalMetadataFeature buildFeature(ExternalMetadataFeature.Builder builder) {
/* 564 */     return (builder != null) ? builder.build() : null;
/*     */   }
/*     */   
/*     */   protected static void fail(String key, XMLStreamReader reader) {
/* 568 */     logger.log(Level.SEVERE, "{0}{1}", new Object[] { key, Integer.valueOf(reader.getLocation().getLineNumber()) });
/* 569 */     throw new ServerRtException(key, new Object[] { Integer.toString(reader.getLocation().getLineNumber()) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithFullName(String key, XMLStreamReader reader) {
/* 575 */     throw new ServerRtException(key, new Object[] { Integer.valueOf(reader.getLocation().getLineNumber()), reader.getName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader) {
/* 582 */     throw new ServerRtException(key, new Object[] { Integer.valueOf(reader.getLocation().getLineNumber()), reader.getLocalName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader, String arg) {
/* 589 */     throw new ServerRtException(key, new Object[] { Integer.valueOf(reader.getLocation().getLineNumber()), reader.getLocalName(), arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class loadClass(String name) {
/*     */     try {
/* 598 */       return Class.forName(name, true, this.classLoader);
/* 599 */     } catch (ClassNotFoundException e) {
/* 600 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 601 */       throw new ServerRtException("runtime.parser.classNotFound", new Object[] { name });
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
/*     */   private Class getImplementorClass(String name, XMLStreamReader xsr) {
/*     */     try {
/* 615 */       return Class.forName(name, true, this.classLoader);
/* 616 */     } catch (ClassNotFoundException e) {
/* 617 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 618 */       throw new LocatableWebServiceException(ServerMessages.RUNTIME_PARSER_CLASS_NOT_FOUND(name), e, xsr);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface AdapterFactory<A> {
/*     */     A createAdapter(String param1String1, String param1String2, WSEndpoint<?> param1WSEndpoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\http\DeploymentDescriptorParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */