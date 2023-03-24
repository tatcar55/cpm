/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import javax.xml.ws.soap.MTOMFeature;
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
/*     */   private final ClassLoader classLoader;
/*     */   private final ResourceLoader loader;
/*     */   private final AdapterFactory<A> adapterFactory;
/*  77 */   private static final XMLInputFactory xif = XMLInputFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   private final Set<String> names = new HashSet<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private final List<URL> docs = new ArrayList<URL>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String NS_RUNTIME = "http://java.sun.com/xml/ns/jax-ws/ri/runtime";
/*     */ 
/*     */   
/*     */   public static final String JAXWS_WSDL_DD_DIR = "WEB-INF/wsdl";
/*     */ 
/*     */ 
/*     */   
/*     */   public DeploymentDescriptorParser(ClassLoader cl, ResourceLoader loader, AdapterFactory<A> adapterFactory) throws IOException {
/* 100 */     this.classLoader = cl;
/* 101 */     this.loader = loader;
/* 102 */     this.adapterFactory = adapterFactory;
/*     */     
/* 104 */     collectDocs("/WEB-INF/wsdl/");
/* 105 */     logger.log(Level.FINE, "war metadata={0}", this.docs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<A> parse(String systemId, InputStream is) {
/* 113 */     XMLStreamReader reader = null;
/*     */     try {
/* 115 */       synchronized (xif) {
/* 116 */         reader = xif.createXMLStreamReader(systemId, is);
/*     */       } 
/* 118 */       nextElementContent(reader);
/* 119 */       return parseAdapters(reader);
/* 120 */     } catch (IOException e) {
/* 121 */       throw new WebServiceException(e);
/* 122 */     } catch (XMLStreamException xe) {
/* 123 */       throw new WebServiceException(xe);
/*     */     } finally {
/* 125 */       if (reader != null) {
/*     */         try {
/* 127 */           reader.close();
/* 128 */         } catch (XMLStreamException e) {}
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 133 */         is.close();
/* 134 */       } catch (IOException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int nextElementContent(XMLStreamReader reader) throws XMLStreamException {
/*     */     int state;
/*     */     do {
/* 142 */       state = reader.next();
/* 143 */     } while (state != 1 && state != 2 && state != 8);
/* 144 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<A> parse(File f) throws IOException {
/* 154 */     FileInputStream in = new FileInputStream(f);
/*     */     try {
/* 156 */       return parse(f.getPath(), in);
/*     */     } finally {
/* 158 */       in.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void collectDocs(String dirPath) throws IOException {
/* 166 */     Set<String> paths = this.loader.getResourcePaths(dirPath);
/* 167 */     if (paths != null) {
/* 168 */       for (String path : paths) {
/* 169 */         if (path.endsWith("/")) {
/* 170 */           if (path.endsWith("/CVS/") || path.endsWith("/.svn/")) {
/*     */             continue;
/*     */           }
/* 173 */           collectDocs(path); continue;
/*     */         } 
/* 175 */         URL res = this.loader.getResource(path);
/* 176 */         this.docs.add(res);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private List<A> parseAdapters(XMLStreamReader reader) throws IOException, XMLStreamException {
/* 183 */     if (!reader.getName().equals(QNAME_ENDPOINTS)) {
/* 184 */       failWithFullName("runtime.parser.invalidElement", reader);
/*     */     }
/*     */     
/* 187 */     List<A> adapters = new ArrayList<A>();
/*     */     
/* 189 */     String version = getMandatoryNonEmptyAttribute(reader, "version");
/* 190 */     if (!version.equals("2.0")) {
/* 191 */       failWithLocalName("sun-jaxws.xml's version attribut runtime.parser.invalidVersionNumber", reader, version);
/*     */     }
/*     */ 
/*     */     
/* 195 */     while (nextElementContent(reader) != 2) {
/* 196 */       if (reader.getName().equals(QNAME_ENDPOINT)) {
/*     */         
/* 198 */         String name = getMandatoryNonEmptyAttribute(reader, "name");
/* 199 */         if (!this.names.add(name)) {
/* 200 */           logger.log(Level.WARNING, "sun-jaxws.xml contains duplicate endpoint names. The first duplicate name is = {0}", name);
/*     */         }
/*     */         
/* 203 */         String implementationName = getMandatoryNonEmptyAttribute(reader, "implementation");
/*     */         
/* 205 */         Class<?> implementorClass = getImplementorClass(implementationName, reader);
/*     */         
/* 207 */         QName serviceName = getQNameAttribute(reader, "service");
/* 208 */         QName portName = getQNameAttribute(reader, "port");
/*     */         
/* 210 */         ArrayList<WebServiceFeature> features = new ArrayList<WebServiceFeature>();
/*     */ 
/*     */         
/* 213 */         String enable_mtom = getAttribute(reader, "enable-mtom");
/* 214 */         String mtomThreshold = getAttribute(reader, "mtom-threshold-value");
/*     */         
/* 216 */         if (Boolean.valueOf(enable_mtom).booleanValue()) {
/* 217 */           if (mtomThreshold != null) {
/* 218 */             features.add(new MTOMFeature(true, Integer.valueOf(mtomThreshold).intValue()));
/*     */           } else {
/* 220 */             features.add(new MTOMFeature(true));
/*     */           } 
/*     */         }
/*     */         
/* 224 */         String bindingId = getAttribute(reader, "binding");
/* 225 */         if (bindingId != null)
/*     */         {
/* 227 */           bindingId = getBindingIdForToken(bindingId);
/*     */         }
/* 229 */         String urlPattern = getMandatoryNonEmptyAttribute(reader, "url-pattern");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 234 */         nextElementContent(reader);
/* 235 */         ensureNoContent(reader);
/*     */         
/* 237 */         List<Source> metadata = new ArrayList<Source>();
/* 238 */         for (URL url : this.docs) {
/* 239 */           Source source = new StreamSource(url.openStream(), url.toExternalForm());
/* 240 */           metadata.add(source);
/*     */         } 
/*     */         
/* 243 */         adapters.add(this.adapterFactory.createAdapter(name, urlPattern, implementorClass, serviceName, portName, bindingId, metadata, features.<WebServiceFeature>toArray(new WebServiceFeature[features.size()])));
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 248 */       failWithLocalName("runtime.parser.invalidElement", reader);
/*     */     } 
/*     */     
/* 251 */     return adapters;
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
/*     */   private static String getBindingIdForToken(String lexical) {
/* 322 */     if (lexical.equals("##SOAP11_HTTP"))
/* 323 */       return "http://schemas.xmlsoap.org/wsdl/soap/http"; 
/* 324 */     if (lexical.equals("##SOAP11_HTTP_MTOM"))
/* 325 */       return "http://schemas.xmlsoap.org/wsdl/soap/http?mtom=true"; 
/* 326 */     if (lexical.equals("##SOAP12_HTTP"))
/* 327 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/"; 
/* 328 */     if (lexical.equals("##SOAP12_HTTP_MTOM"))
/* 329 */       return "http://www.w3.org/2003/05/soap/bindings/HTTP/?mtom=true"; 
/* 330 */     if (lexical.equals("##XML_HTTP")) {
/* 331 */       return "http://www.w3.org/2004/08/wsdl/http";
/*     */     }
/* 333 */     return lexical;
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
/*     */   protected String getAttribute(XMLStreamReader reader, String name) {
/* 404 */     String value = reader.getAttributeValue(null, name);
/* 405 */     if (value != null) {
/* 406 */       value = value.trim();
/*     */     }
/* 408 */     return value;
/*     */   }
/*     */   
/*     */   protected QName getQNameAttribute(XMLStreamReader reader, String name) {
/* 412 */     String value = reader.getAttributeValue(null, name);
/* 413 */     if (value == null || value.equals("")) {
/* 414 */       return null;
/*     */     }
/* 416 */     return QName.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNonEmptyAttribute(XMLStreamReader reader, String name) {
/* 421 */     String value = reader.getAttributeValue(null, name);
/* 422 */     if (value != null && value.equals("")) {
/* 423 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 428 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryAttribute(XMLStreamReader reader, String name) {
/* 432 */     String value = reader.getAttributeValue(null, name);
/* 433 */     if (value == null) {
/* 434 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/*     */     }
/* 436 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryNonEmptyAttribute(XMLStreamReader reader, String name) {
/* 440 */     String value = reader.getAttributeValue(null, name);
/* 441 */     if (value == null) {
/* 442 */       failWithLocalName("Missing attribute", reader, name);
/* 443 */     } else if (value.equals("")) {
/* 444 */       failWithLocalName("Invalid attribute value", reader, name);
/*     */     } 
/*     */ 
/*     */     
/* 448 */     return value;
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
/*     */   protected static void ensureNoContent(XMLStreamReader reader) {
/* 482 */     if (reader.getEventType() != 2) {
/* 483 */       fail("While parsing sun-jaxws.xml, found unexpected content at line=", reader);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void fail(String key, XMLStreamReader reader) {
/* 488 */     String msg = key + reader.getLocation().getLineNumber();
/* 489 */     logger.log(Level.SEVERE, msg);
/* 490 */     throw new WebServiceException(msg);
/*     */   }
/*     */   
/*     */   protected static void failWithFullName(String key, XMLStreamReader reader) {
/* 494 */     String msg = key + reader.getLocation().getLineNumber() + reader.getName();
/*     */     
/* 496 */     throw new WebServiceException(msg);
/*     */   }
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader) {
/* 500 */     String msg = key + reader.getLocation().getLineNumber() + reader.getLocalName();
/*     */     
/* 502 */     throw new WebServiceException(msg);
/*     */   }
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLStreamReader reader, String arg) {
/* 506 */     String msg = key + reader.getLocation().getLineNumber() + reader.getLocalName() + arg;
/*     */     
/* 508 */     throw new WebServiceException(msg);
/*     */   }
/*     */   
/*     */   protected Class loadClass(String name) {
/*     */     try {
/* 513 */       return Class.forName(name, true, this.classLoader);
/* 514 */     } catch (ClassNotFoundException e) {
/* 515 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 516 */       throw new WebServiceException(e);
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
/*     */   private Class getImplementorClass(String name, XMLStreamReader xsr) {
/*     */     try {
/* 529 */       return Class.forName(name, true, this.classLoader);
/* 530 */     } catch (ClassNotFoundException e) {
/* 531 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 532 */       throw new WebServiceException("Class at " + xsr.getLocation().getLineNumber() + " is not found", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 541 */   public static final QName QNAME_ENDPOINTS = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoints");
/*     */   
/* 543 */   public static final QName QNAME_ENDPOINT = new QName("http://java.sun.com/xml/ns/jax-ws/ri/runtime", "endpoint");
/*     */   
/*     */   public static final String ATTR_VERSION = "version";
/*     */   
/*     */   public static final String ATTR_NAME = "name";
/*     */   
/*     */   public static final String ATTR_IMPLEMENTATION = "implementation";
/*     */   public static final String ATTR_WSDL = "wsdl";
/*     */   public static final String ATTR_SERVICE = "service";
/*     */   public static final String ATTR_PORT = "port";
/*     */   public static final String ATTR_URL_PATTERN = "url-pattern";
/*     */   public static final String ATTR_ENABLE_MTOM = "enable-mtom";
/*     */   public static final String ATTR_MTOM_THRESHOLD_VALUE = "mtom-threshold-value";
/*     */   public static final String ATTR_BINDING = "binding";
/*     */   public static final String ATTRVALUE_VERSION_1_0 = "2.0";
/* 558 */   private static final Logger logger = Logger.getLogger(DeploymentDescriptorParser.class.getName());
/*     */   
/*     */   public static interface AdapterFactory<A> {
/*     */     A createAdapter(String param1String1, String param1String2, Class param1Class, QName param1QName1, QName param1QName2, String param1String3, List<Source> param1List, WebServiceFeature... param1VarArgs);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\DeploymentDescriptorParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */