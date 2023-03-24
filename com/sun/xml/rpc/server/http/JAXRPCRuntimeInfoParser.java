/*     */ package com.sun.xml.rpc.server.http;
/*     */ 
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ public class JAXRPCRuntimeInfoParser
/*     */ {
/*     */   protected ClassLoader classLoader;
/*     */   public static final String NS_RUNTIME = "http://java.sun.com/xml/ns/jax-rpc/ri/runtime";
/*     */   
/*     */   public JAXRPCRuntimeInfoParser(ClassLoader cl) {
/*  48 */     this.classLoader = cl;
/*     */   }
/*     */   
/*     */   public JAXRPCRuntimeInfo parse(InputStream is) {
/*     */     try {
/*  53 */       XMLReader reader = XMLReaderFactory.newInstance().createXMLReader(is);
/*     */       
/*  55 */       reader.next();
/*  56 */       return parseEndpoints(reader);
/*  57 */     } catch (XMLReaderException e) {
/*  58 */       throw new JAXRPCServletException("runtime.parser.xmlReader", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected JAXRPCRuntimeInfo parseEndpoints(XMLReader reader) {
/*  63 */     if (!reader.getName().equals(QNAME_ENDPOINTS)) {
/*  64 */       failWithFullName("runtime.parser.invalidElement", reader);
/*     */     }
/*     */     
/*  67 */     JAXRPCRuntimeInfo info = new JAXRPCRuntimeInfo();
/*  68 */     List<RuntimeEndpointInfo> endpoints = new ArrayList();
/*     */     
/*  70 */     String version = getMandatoryNonEmptyAttribute(reader, "version");
/*  71 */     if (!version.equals("1.0")) {
/*  72 */       failWithLocalName("runtime.parser.invalidVersionNumber", reader, version);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     while (reader.nextElementContent() != 2) {
/*  79 */       if (reader.getName().equals(QNAME_ENDPOINT)) {
/*  80 */         RuntimeEndpointInfo rei = new RuntimeEndpointInfo();
/*  81 */         rei.setName(getMandatoryNonEmptyAttribute(reader, "name"));
/*  82 */         String interfaceName = getMandatoryNonEmptyAttribute(reader, "interface");
/*     */         
/*  84 */         rei.setRemoteInterface(loadClass(interfaceName));
/*  85 */         String implementationName = getMandatoryNonEmptyAttribute(reader, "implementation");
/*     */         
/*  87 */         rei.setImplementationClass(loadClass(implementationName));
/*  88 */         String tieName = getMandatoryNonEmptyAttribute(reader, "tie");
/*     */         
/*  90 */         rei.setTieClass(loadClass(tieName));
/*  91 */         rei.setModelFileName(getAttribute(reader, "model"));
/*  92 */         rei.setWSDLFileName(getAttribute(reader, "wsdl"));
/*  93 */         rei.setServiceName(getQNameAttribute(reader, "service"));
/*  94 */         rei.setPortName(getQNameAttribute(reader, "port"));
/*  95 */         rei.setUrlPattern(getMandatoryNonEmptyAttribute(reader, "urlpattern"));
/*     */         
/*  97 */         ensureNoContent(reader);
/*  98 */         rei.setDeployed(true);
/*  99 */         endpoints.add(rei); continue;
/*     */       } 
/* 101 */       failWithLocalName("runtime.parser.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 105 */     reader.close();
/*     */     
/* 107 */     info.setEndpoints(endpoints);
/* 108 */     return info;
/*     */   }
/*     */   
/*     */   protected String getAttribute(XMLReader reader, String name) {
/* 112 */     Attributes attributes = reader.getAttributes();
/* 113 */     String value = attributes.getValue(name);
/* 114 */     if (value != null) {
/* 115 */       value = value.trim();
/*     */     }
/* 117 */     return value;
/*     */   }
/*     */   
/*     */   protected QName getQNameAttribute(XMLReader reader, String name) {
/* 121 */     String value = getAttribute(reader, name);
/* 122 */     if (value == null || value.equals("")) {
/* 123 */       return null;
/*     */     }
/* 125 */     return QName.valueOf(value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getNonEmptyAttribute(XMLReader reader, String name) {
/* 130 */     String value = getAttribute(reader, name);
/* 131 */     if (value != null && value.equals("")) {
/* 132 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 137 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryAttribute(XMLReader reader, String name) {
/* 141 */     String value = getAttribute(reader, name);
/* 142 */     if (value == null) {
/* 143 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/*     */     }
/* 145 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getMandatoryNonEmptyAttribute(XMLReader reader, String name) {
/* 151 */     String value = getAttribute(reader, name);
/* 152 */     if (value == null) {
/* 153 */       failWithLocalName("runtime.parser.missing.attribute", reader, name);
/* 154 */     } else if (value.equals("")) {
/* 155 */       failWithLocalName("runtime.parser.invalidAttributeValue", reader, name);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 160 */     return value;
/*     */   }
/*     */   
/*     */   protected static void ensureNoContent(XMLReader reader) {
/* 164 */     if (reader.nextElementContent() != 2) {
/* 165 */       fail("runtime.parser.unexpectedContent", reader);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void fail(String key, XMLReader reader) {
/* 170 */     logger.log(Level.SEVERE, key + reader.getLineNumber());
/* 171 */     throw new JAXRPCServletException(key, Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithFullName(String key, XMLReader reader) {
/* 177 */     throw new JAXRPCServletException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getName().toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLReader reader) {
/* 185 */     throw new JAXRPCServletException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLReader reader, String arg) {
/* 196 */     throw new JAXRPCServletException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName(), arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Class loadClass(String name) {
/*     */     try {
/* 206 */       return Class.forName(name, true, this.classLoader);
/* 207 */     } catch (ClassNotFoundException e) {
/* 208 */       logger.log(Level.SEVERE, e.getMessage(), e);
/* 209 */       throw new JAXRPCServletException("runtime.parser.classNotFound", name);
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
/* 220 */   public static final QName QNAME_ENDPOINTS = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/runtime", "endpoints");
/*     */   
/* 222 */   public static final QName QNAME_ENDPOINT = new QName("http://java.sun.com/xml/ns/jax-rpc/ri/runtime", "endpoint");
/*     */   
/*     */   public static final String ATTR_VERSION = "version";
/*     */   
/*     */   public static final String ATTR_NAME = "name";
/*     */   
/*     */   public static final String ATTR_INTERFACE = "interface";
/*     */   public static final String ATTR_IMPLEMENTATION = "implementation";
/*     */   public static final String ATTR_TIE = "tie";
/*     */   public static final String ATTR_MODEL = "model";
/*     */   public static final String ATTR_WSDL = "wsdl";
/*     */   public static final String ATTR_SERVICE = "service";
/*     */   public static final String ATTR_PORT = "port";
/*     */   public static final String ATTR_URL_PATTERN = "urlpattern";
/*     */   public static final String ATTRVALUE_VERSION_1_0 = "1.0";
/* 237 */   private static final Logger logger = Logger.getLogger("javax.enterprise.resource.webservices.rpc.server.http");
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\JAXRPCRuntimeInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */