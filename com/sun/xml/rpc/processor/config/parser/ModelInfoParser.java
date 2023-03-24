/*     */ package com.sun.xml.rpc.processor.config.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*     */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ModelInfoParser
/*     */ {
/*     */   private ProcessorEnvironment env;
/*  58 */   private LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.configuration");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelInfoParser(ProcessorEnvironment env) {
/*  66 */     this.env = env;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract ModelInfo parse(XMLReader paramXMLReader);
/*     */ 
/*     */   
/*     */   protected TypeMappingRegistryInfo parseTypeMappingRegistryInfo(XMLReader reader) {
/*  74 */     TypeMappingRegistryInfo typeMappingRegistryInfo = new TypeMappingRegistryInfo();
/*     */ 
/*     */     
/*  77 */     boolean readyForImport = true;
/*  78 */     boolean gotAdditionalTypes = false;
/*  79 */     while (reader.nextElementContent() != 2) {
/*  80 */       if (reader.getName().equals(Constants.QNAME_IMPORT)) {
/*  81 */         if (!readyForImport) {
/*  82 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */         }
/*     */         
/*  85 */         readyForImport = false;
/*  86 */         while (reader.nextElementContent() != 2) {
/*  87 */           if (reader.getName().equals(Constants.QNAME_SCHEMA)) {
/*  88 */             String namespace = ParserUtil.getMandatoryNonEmptyAttribute(reader, "namespace");
/*     */ 
/*     */             
/*  91 */             String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*     */ 
/*     */             
/*  94 */             ImportedDocumentInfo docInfo = new ImportedDocumentInfo(1);
/*     */ 
/*     */             
/*  97 */             docInfo.setNamespace(namespace);
/*  98 */             docInfo.setLocation(location);
/*  99 */             typeMappingRegistryInfo.addImportedDocument(docInfo);
/* 100 */             ParserUtil.ensureNoContent(reader); continue;
/*     */           } 
/* 102 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */         } 
/*     */         continue;
/*     */       } 
/* 106 */       if (reader.getName().equals(Constants.QNAME_TYPE_MAPPING)) {
/* 107 */         if (gotAdditionalTypes) {
/* 108 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */         }
/*     */         
/* 111 */         parseTypeMapping(typeMappingRegistryInfo, reader); continue;
/* 112 */       }  if (reader.getName().equals(Constants.QNAME_ADDITIONAL_TYPES)) {
/*     */ 
/*     */         
/* 115 */         if (gotAdditionalTypes) {
/* 116 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */         }
/*     */         
/* 119 */         while (reader.nextElementContent() != 2) {
/* 120 */           if (reader.getName().equals(Constants.QNAME_CLASS)) {
/* 121 */             String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*     */             
/* 123 */             typeMappingRegistryInfo.addExtraTypeName(name);
/* 124 */             ParserUtil.ensureNoContent(reader); continue;
/*     */           } 
/* 126 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 131 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 135 */     return typeMappingRegistryInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseTypeMapping(TypeMappingRegistryInfo typeMappingRegistryInfo, XMLReader reader) {
/* 141 */     String encodingStyle = ParserUtil.getMandatoryAttribute(reader, "encodingStyle");
/*     */ 
/*     */     
/* 144 */     if (!knownEncodingStyles.contains(encodingStyle)) {
/* 145 */       warn("configuration.typemapping.unrecognized.encodingstyle", encodingStyle);
/*     */     }
/*     */     
/* 148 */     while (reader.nextElementContent() != 2) {
/* 149 */       if (reader.getName().equals(Constants.QNAME_ENTRY)) {
/* 150 */         parseEntry(typeMappingRegistryInfo, encodingStyle, reader); continue;
/*     */       } 
/* 152 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseEntry(TypeMappingRegistryInfo typeMappingRegistryInfo, String encodingStyle, XMLReader reader) {
/* 161 */     String rawSchemaType = ParserUtil.getMandatoryNonEmptyAttribute(reader, "schemaType");
/*     */     
/* 163 */     String javaTypeName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "javaType");
/*     */     
/* 165 */     String serializerFactoryName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "serializerFactory");
/*     */ 
/*     */     
/* 168 */     String deserializerFactoryName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "deserializerFactory");
/*     */ 
/*     */ 
/*     */     
/* 172 */     ParserUtil.ensureNoContent(reader);
/*     */     
/* 174 */     String prefix = XmlUtil.getPrefix(rawSchemaType);
/* 175 */     String uri = (prefix == null) ? null : reader.getURI(prefix);
/* 176 */     if (prefix != null && uri == null) {
/* 177 */       ParserUtil.failWithLocalName("configuration.configuration.invalid.attribute.value", reader, rawSchemaType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     String localPart = XmlUtil.getLocalPart(rawSchemaType);
/* 183 */     QName xmlType = new QName(uri, localPart);
/*     */     
/* 185 */     TypeMappingInfo i = new TypeMappingInfo(encodingStyle, xmlType, javaTypeName, serializerFactoryName, deserializerFactoryName);
/*     */ 
/*     */ 
/*     */     
/* 189 */     typeMappingRegistryInfo.addMapping(i);
/*     */   }
/*     */   
/*     */   protected HandlerChainInfoData parseHandlerChainInfoData(XMLReader reader) {
/* 193 */     HandlerChainInfoData data = new HandlerChainInfoData();
/*     */     
/* 195 */     boolean gotClient = false;
/* 196 */     boolean gotServer = false;
/* 197 */     while (reader.nextElementContent() != 2) {
/* 198 */       if (reader.getName().equals(Constants.QNAME_CHAIN)) {
/* 199 */         String runatAttr = ParserUtil.getMandatoryNonEmptyAttribute(reader, "runAt");
/*     */         
/* 201 */         if (runatAttr.equals("client")) {
/* 202 */           if (gotClient) {
/* 203 */             ParserUtil.failWithLocalName("configuration.handlerChain.duplicate", reader, runatAttr);
/*     */             
/*     */             continue;
/*     */           } 
/* 207 */           data.setClientHandlerChainInfo(parseHandlerChainInfo(reader));
/*     */           
/* 209 */           gotClient = true; continue;
/*     */         } 
/* 211 */         if (runatAttr.equals("server")) {
/* 212 */           if (gotServer) {
/* 213 */             ParserUtil.failWithLocalName("configuration.handlerChain.duplicate", reader, runatAttr);
/*     */             
/*     */             continue;
/*     */           } 
/* 217 */           data.setServerHandlerChainInfo(parseHandlerChainInfo(reader));
/*     */           
/* 219 */           gotServer = true;
/*     */           continue;
/*     */         } 
/* 222 */         ParserUtil.failWithLocalName("configuration.invalidAttributeValue", reader, "runAt");
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 227 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 231 */     return data;
/*     */   }
/*     */   
/*     */   protected HandlerChainInfo parseHandlerChainInfo(XMLReader reader) {
/* 235 */     HandlerChainInfo chain = new HandlerChainInfo();
/*     */ 
/*     */     
/* 238 */     chain.addRole("http://schemas.xmlsoap.org/soap/actor/next");
/*     */ 
/*     */     
/* 241 */     String rolesAttr = ParserUtil.getAttribute(reader, "roles");
/*     */     
/* 243 */     if (rolesAttr != null) {
/* 244 */       List rolesList = XmlUtil.parseTokenList(rolesAttr);
/* 245 */       for (Iterator<String> iter = rolesList.iterator(); iter.hasNext();) {
/* 246 */         chain.addRole(iter.next());
/*     */       }
/*     */     } 
/*     */     
/* 250 */     while (reader.nextElementContent() != 2) {
/* 251 */       if (reader.getName().equals(Constants.QNAME_HANDLER)) {
/* 252 */         chain.add(parseHandlerInfo(reader));
/*     */         continue;
/*     */       } 
/* 255 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     return chain;
/*     */   }
/*     */   
/*     */   protected HandlerInfo parseHandlerInfo(XMLReader reader) {
/* 263 */     HandlerInfo handler = new HandlerInfo();
/*     */     
/* 265 */     String className = ParserUtil.getMandatoryNonEmptyAttribute(reader, "className");
/*     */     
/* 267 */     handler.setHandlerClassName(className);
/* 268 */     String headers = ParserUtil.getAttribute(reader, "headers");
/*     */     
/* 270 */     if (headers != null) {
/* 271 */       List headersList = XmlUtil.parseTokenList(headers);
/* 272 */       for (Iterator<String> iter = headersList.iterator(); iter.hasNext(); ) {
/* 273 */         String name = iter.next();
/* 274 */         String prefix = XmlUtil.getPrefix(name);
/* 275 */         String localPart = XmlUtil.getLocalPart(name);
/* 276 */         if (prefix == null)
/*     */         {
/*     */           
/* 279 */           prefix = "";
/*     */         }
/* 281 */         String uri = reader.getURI(prefix);
/* 282 */         if (uri == null) {
/* 283 */           ParserUtil.failWithLocalName("configuration.invalidAttributeValue", reader, "headers");
/*     */         }
/*     */ 
/*     */         
/* 287 */         handler.addHeaderName(new QName(uri, localPart));
/*     */       } 
/*     */     } 
/*     */     
/* 291 */     Map<String, String> properties = handler.getProperties();
/* 292 */     while (reader.nextElementContent() != 2) {
/* 293 */       if (reader.getName().equals(Constants.QNAME_PROPERTY)) {
/* 294 */         String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*     */         
/* 296 */         String value = ParserUtil.getMandatoryAttribute(reader, "value");
/*     */         
/* 298 */         properties.put(name, value);
/* 299 */         ParserUtil.ensureNoContent(reader); continue;
/*     */       } 
/* 301 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 305 */     return handler;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NamespaceMappingRegistryInfo parseNamespaceMappingRegistryInfo(XMLReader reader) {
/* 311 */     NamespaceMappingRegistryInfo namespaceMappingRegistryInfo = new NamespaceMappingRegistryInfo();
/*     */ 
/*     */     
/* 314 */     while (reader.nextElementContent() != 2) {
/* 315 */       if (reader.getName().equals(Constants.QNAME_NAMESPACE_MAPPING)) {
/* 316 */         parseNamespaceMapping(namespaceMappingRegistryInfo, reader); continue;
/*     */       } 
/* 318 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 322 */     return namespaceMappingRegistryInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseNamespaceMapping(NamespaceMappingRegistryInfo namespaceMappingRegistryInfo, XMLReader reader) {
/* 329 */     String namespaceURI = ParserUtil.getMandatoryAttribute(reader, "namespace");
/*     */     
/* 331 */     String javaPackageName = ParserUtil.getMandatoryAttribute(reader, "packageName");
/*     */ 
/*     */     
/* 334 */     ParserUtil.ensureNoContent(reader);
/*     */     
/* 336 */     NamespaceMappingInfo i = new NamespaceMappingInfo(namespaceURI, javaPackageName);
/*     */     
/* 338 */     namespaceMappingRegistryInfo.addMapping(i);
/*     */   }
/*     */   
/*     */   protected ProcessorEnvironment getEnvironment() {
/* 342 */     return this.env;
/*     */   }
/*     */   
/*     */   protected void warn(String key) {
/* 346 */     getEnvironment().warn(this.messageFactory.getMessage(key));
/*     */   }
/*     */   
/*     */   protected void warn(String key, String arg) {
/* 350 */     getEnvironment().warn(this.messageFactory.getMessage(key, arg));
/*     */   }
/*     */   
/*     */   protected void warn(String key, Object[] args) {
/* 354 */     getEnvironment().warn(this.messageFactory.getMessage(key, args));
/*     */   }
/*     */   
/*     */   protected void info(String key) {
/* 358 */     getEnvironment().info(this.messageFactory.getMessage(key));
/*     */   }
/*     */   
/*     */   protected void info(String key, String arg) {
/* 362 */     getEnvironment().info(this.messageFactory.getMessage(key, arg));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 368 */   private static final HashSet knownEncodingStyles = new HashSet();
/*     */   
/*     */   static {
/* 371 */     knownEncodingStyles.add("http://schemas.xmlsoap.org/soap/encoding/");
/* 372 */     knownEncodingStyles.add("http://www.w3.org/2002/06/soap-encoding");
/* 373 */     knownEncodingStyles.add("");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\ModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */