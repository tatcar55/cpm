/*     */ package com.sun.xml.rpc.tools.wsdeploy;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ public class DeploymentDescriptorParser
/*     */ {
/*     */   public WebServicesInfo parse(InputStream is) {
/*     */     try {
/*  56 */       XMLReader reader = XMLReaderFactory.newInstance().createXMLReader(is);
/*     */       
/*  58 */       reader.next();
/*  59 */       return parseWebServices(reader);
/*  60 */     } catch (XMLReaderException e) {
/*  61 */       throw new DeploymentException("deployment.parser.xmlReader", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected WebServicesInfo parseWebServices(XMLReader reader) {
/*  66 */     if (!reader.getName().equals(Constants.QNAME_WEB_SERVICES)) {
/*  67 */       failWithFullName("deployment.parser.invalidElement", reader);
/*     */     }
/*     */     
/*  70 */     WebServicesInfo wsInfo = new WebServicesInfo();
/*     */     
/*  72 */     String version = getMandatoryNonEmptyAttribute(reader, "version");
/*     */     
/*  74 */     if (!version.equals("1.0")) {
/*  75 */       failWithLocalName("deployment.parser.invalidVersionNumber", reader, version);
/*     */     }
/*     */     
/*  78 */     wsInfo.setTargetNamespaceBase(getNonEmptyAttribute(reader, "targetNamespaceBase"));
/*     */     
/*  80 */     wsInfo.setTypeNamespaceBase(getNonEmptyAttribute(reader, "typeNamespaceBase"));
/*     */     
/*  82 */     wsInfo.setUrlPatternBase(getNonEmptyAttribute(reader, "urlPatternBase"));
/*     */ 
/*     */     
/*  85 */     boolean gotEndpointMapping = false;
/*  86 */     while (reader.nextElementContent() != 2) {
/*  87 */       if (reader.getName().equals(Constants.QNAME_ENDPOINT)) {
/*  88 */         if (gotEndpointMapping) {
/*  89 */           failWithLocalName("deployment.parser.invalidElement", reader);
/*     */           continue;
/*     */         } 
/*  92 */         wsInfo.add(parseEndpoint(reader)); continue;
/*     */       } 
/*  94 */       if (reader.getName().equals(Constants.QNAME_ENDPOINT_MAPPING)) {
/*     */ 
/*     */         
/*  97 */         wsInfo.add(parseEndpointMapping(reader));
/*  98 */         gotEndpointMapping = true; continue;
/*  99 */       }  if (reader.getName().equals(Constants.QNAME_ENDPOINT_CLIENT_MAPPING)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 108 */       failWithLocalName("deployment.parser.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 112 */     reader.close();
/*     */ 
/*     */     
/* 115 */     Map endpoints = wsInfo.getEndpoints();
/* 116 */     Map endpointMappings = wsInfo.getEndpointMappings();
/* 117 */     Map endpointClients = wsInfo.getEndpointClients();
/*     */     
/* 119 */     boolean gotModels = true;
/* 120 */     boolean gotMappings = true;
/* 121 */     for (Iterator<EndpointInfo> iterator = endpoints.values().iterator(); iterator.hasNext(); ) {
/* 122 */       EndpointInfo endpoint = iterator.next();
/* 123 */       if (endpoint.getModel() == null) {
/* 124 */         gotModels = false;
/*     */       }
/* 126 */       if (endpointMappings.get(endpoint.getName()) == null) {
/* 127 */         gotMappings = false;
/*     */       }
/*     */     } 
/*     */     
/* 131 */     if (!gotModels) {
/* 132 */       if (wsInfo.getTargetNamespaceBase() == null) {
/* 133 */         throw new DeploymentException("deployment.parser.missing.attribute.no.line", new Object[] { Constants.QNAME_WEB_SERVICES.getLocalPart(), "targetNamespaceBase" });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 139 */       if (wsInfo.getTypeNamespaceBase() == null) {
/* 140 */         throw new DeploymentException("deployment.parser.missing.attribute.no.line", new Object[] { Constants.QNAME_WEB_SERVICES.getLocalPart(), "typeNamespaceBase" });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     if (!gotMappings && 
/* 148 */       wsInfo.getUrlPatternBase() == null) {
/* 149 */       throw new DeploymentException("deployment.parser.missing.attribute.no.line", new Object[] { Constants.QNAME_WEB_SERVICES.getLocalPart(), "urlPatternBase" });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     Iterator<EndpointClientInfo> iter = endpointClients.values().iterator();
/* 158 */     while (iter.hasNext()) {
/*     */       
/* 160 */       EndpointClientInfo endpointClient = iter.next();
/*     */       
/* 162 */       if (endpointClient.getModel() == null) {
/* 163 */         throw new DeploymentException("deployment.parser.missing.attribute.no.line", new Object[] { Constants.QNAME_ENDPOINT_CLIENT_MAPPING.getLocalPart(), Constants.QNAME_ENDPOINT_CLIENT_MAPPING });
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       if (endpointClient.getService() == null) {
/* 171 */         throw new DeploymentException("deployment.parser.missing.attribute.no.line", new Object[] { "service" });
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 176 */     return wsInfo;
/*     */   }
/*     */   
/*     */   protected EndpointClientInfo parseEndpointClient(XMLReader reader) {
/* 180 */     EndpointClientInfo client = new EndpointClientInfo();
/* 181 */     client.setName(getMandatoryNonEmptyAttribute(reader, "name"));
/*     */     
/* 183 */     client.setDisplayName(getAttribute(reader, "displayName"));
/*     */     
/* 185 */     client.setDescription(getAttribute(reader, "description"));
/* 186 */     client.setModel(getAttribute(reader, "model"));
/* 187 */     client.setService(getAttribute(reader, "service"));
/* 188 */     return client;
/*     */   }
/*     */   
/*     */   protected EndpointInfo parseEndpoint(XMLReader reader) {
/* 192 */     EndpointInfo endpoint = new EndpointInfo();
/*     */     
/* 194 */     endpoint.setName(getMandatoryNonEmptyAttribute(reader, "name"));
/*     */     
/* 196 */     endpoint.setDisplayName(getAttribute(reader, "displayName"));
/*     */     
/* 198 */     endpoint.setDescription(getAttribute(reader, "description"));
/*     */     
/* 200 */     endpoint.setInterface(getAttribute(reader, "interface"));
/* 201 */     endpoint.setImplementation(getAttribute(reader, "implementation"));
/*     */     
/* 203 */     endpoint.setModel(getAttribute(reader, "model"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 211 */     if (getAttribute(reader, "port") != null) {
/* 212 */       String portValue = getAttribute(reader, "port");
/* 213 */       StringTokenizer str = new StringTokenizer(portValue, "}");
/* 214 */       QName portQName = new QName((new StringTokenizer(str.nextToken(), "{")).nextToken(), str.nextToken());
/*     */ 
/*     */       
/* 217 */       endpoint.setRuntimePortName(portQName);
/*     */     } 
/*     */     
/* 220 */     if (getAttribute(reader, "wsdl") != null) {
/* 221 */       String wsdlValue = getAttribute(reader, "wsdl");
/* 222 */       endpoint.setRuntimeWSDL(wsdlValue);
/*     */     } 
/*     */     
/* 225 */     if (reader.nextElementContent() == 1) {
/* 226 */       if (reader.getName().equals(Constants.QNAME_HANDLER_CHAINS)) {
/* 227 */         while (reader.nextElementContent() != 2) {
/* 228 */           if (reader.getName().equals(Constants.QNAME_CHAIN)) {
/* 229 */             String runatAttr = getMandatoryNonEmptyAttribute(reader, "runAt");
/*     */ 
/*     */             
/* 232 */             HandlerChainInfo handlerChainInfo = new HandlerChainInfo();
/*     */ 
/*     */             
/* 235 */             String rolesAttr = getAttribute(reader, "roles");
/*     */             
/* 237 */             if (rolesAttr != null) {
/* 238 */               List rolesList = XmlUtil.parseTokenList(rolesAttr);
/* 239 */               Iterator<String> iter = rolesList.iterator();
/* 240 */               while (iter.hasNext())
/*     */               {
/* 242 */                 handlerChainInfo.addRole(iter.next());
/*     */               }
/*     */             } 
/*     */             
/* 246 */             while (reader.nextElementContent() != 2) {
/* 247 */               if (reader.getName().equals(Constants.QNAME_HANDLER)) {
/*     */ 
/*     */                 
/* 250 */                 HandlerInfo handlerInfo = new HandlerInfo();
/*     */                 
/* 252 */                 String className = getMandatoryNonEmptyAttribute(reader, "className");
/*     */ 
/*     */                 
/* 255 */                 handlerInfo.setHandlerClassName(className);
/* 256 */                 String headers = getAttribute(reader, "headers");
/*     */                 
/* 258 */                 if (headers != null) {
/* 259 */                   List headersList = XmlUtil.parseTokenList(headers);
/*     */                   
/* 261 */                   Iterator<String> iter = headersList.iterator();
/* 262 */                   while (iter.hasNext()) {
/*     */                     
/* 264 */                     String name = iter.next();
/* 265 */                     String prefix = XmlUtil.getPrefix(name);
/* 266 */                     String localPart = XmlUtil.getLocalPart(name);
/*     */                     
/* 268 */                     if (prefix == null)
/*     */                     {
/*     */                       
/* 271 */                       prefix = "";
/*     */                     }
/* 273 */                     String uri = reader.getURI(prefix);
/* 274 */                     if (uri == null) {
/* 275 */                       failWithLocalName("configuration.invalidAttributeValue", reader, "headers");
/*     */                     }
/*     */ 
/*     */                     
/* 279 */                     handlerInfo.addHeaderName(new QName(uri, localPart));
/*     */                   } 
/*     */                 } 
/*     */ 
/*     */                 
/* 284 */                 Map<String, String> properties = handlerInfo.getProperties();
/* 285 */                 while (reader.nextElementContent() != 2) {
/*     */ 
/*     */                   
/* 288 */                   if (reader.getName().equals(Constants.QNAME_PROPERTY)) {
/*     */ 
/*     */                     
/* 291 */                     String name = getMandatoryNonEmptyAttribute(reader, "name");
/*     */ 
/*     */                     
/* 294 */                     String value = getMandatoryAttribute(reader, "value");
/*     */ 
/*     */                     
/* 297 */                     properties.put(name, value);
/* 298 */                     ensureNoContent(reader); continue;
/*     */                   } 
/* 300 */                   failWithLocalName("configuration.invalidElement", reader);
/*     */                 } 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 306 */                 handlerChainInfo.add(handlerInfo); continue;
/*     */               } 
/* 308 */               failWithLocalName("configuration.invalidElement", reader);
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 313 */             if (runatAttr.equals("client")) {
/* 314 */               endpoint.setClientHandlerChainInfo(handlerChainInfo); continue;
/*     */             } 
/* 316 */             if (runatAttr.equals("server")) {
/*     */ 
/*     */               
/* 319 */               endpoint.setServerHandlerChainInfo(handlerChainInfo);
/*     */               continue;
/*     */             } 
/* 322 */             failWithLocalName("configuration.invalidAttributeValue", reader, "runAt");
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 327 */           failWithLocalName("deployment.parser.invalidElement", reader);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 332 */         ensureNoContent(reader);
/*     */       } else {
/* 334 */         failWithLocalName("deployment.parser.invalidElement", reader);
/*     */       } 
/*     */     }
/*     */     
/* 338 */     return endpoint;
/*     */   }
/*     */   
/*     */   protected EndpointMappingInfo parseEndpointMapping(XMLReader reader) {
/* 342 */     EndpointMappingInfo endpointMapping = new EndpointMappingInfo();
/*     */     
/* 344 */     endpointMapping.setName(getMandatoryNonEmptyAttribute(reader, "endpointName"));
/*     */     
/* 346 */     endpointMapping.setUrlPattern(getMandatoryNonEmptyAttribute(reader, "urlPattern"));
/*     */ 
/*     */     
/* 349 */     ensureNoContent(reader);
/* 350 */     return endpointMapping;
/*     */   }
/*     */   
/*     */   protected String getAttribute(XMLReader reader, String name) {
/* 354 */     Attributes attributes = reader.getAttributes();
/* 355 */     String value = attributes.getValue(name);
/* 356 */     if (value != null) {
/* 357 */       value = value.trim();
/*     */     }
/* 359 */     return value;
/*     */   }
/*     */   
/*     */   protected String getNonEmptyAttribute(XMLReader reader, String name) {
/* 363 */     String value = getAttribute(reader, name);
/* 364 */     if (value != null && value.equals("")) {
/* 365 */       failWithLocalName("deployment.parser.invalidAttributeValue", reader, name);
/*     */     }
/*     */     
/* 368 */     return value;
/*     */   }
/*     */   
/*     */   protected String getMandatoryAttribute(XMLReader reader, String name) {
/* 372 */     String value = getAttribute(reader, name);
/* 373 */     if (value == null) {
/* 374 */       failWithLocalName("deployment.parser.missing.attribute", reader, name);
/*     */     }
/*     */     
/* 377 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getMandatoryNonEmptyAttribute(XMLReader reader, String name) {
/* 383 */     String value = getAttribute(reader, name);
/* 384 */     if (value == null) {
/* 385 */       failWithLocalName("deployment.parser.missing.attribute", reader, name);
/*     */     }
/* 387 */     else if (value.equals("")) {
/* 388 */       failWithLocalName("deployment.parser.invalidAttributeValue", reader, name);
/*     */     } 
/*     */     
/* 391 */     return value;
/*     */   }
/*     */   
/*     */   protected static void ensureNoContent(XMLReader reader) {
/* 395 */     if (reader.nextElementContent() != 2) {
/* 396 */       fail("deployment.parser.unexpectedContent", reader);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void fail(String key, XMLReader reader) {
/* 401 */     throw new DeploymentException(key, Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void failWithFullName(String key, XMLReader reader) {
/* 406 */     throw new DeploymentException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getName().toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLReader reader) {
/* 413 */     throw new DeploymentException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static void failWithLocalName(String key, XMLReader reader, String arg) {
/* 422 */     throw new DeploymentException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName(), arg });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\DeploymentDescriptorParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */