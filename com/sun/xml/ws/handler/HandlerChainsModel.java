/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
/*     */ import com.sun.xml.ws.util.HandlerAnnotationInfo;
/*     */ import com.sun.xml.ws.util.JAXWSUtils;
/*     */ import com.sun.xml.ws.util.UtilException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.PortInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandlerChainsModel
/*     */ {
/*  67 */   private static final Logger logger = Logger.getLogger("com.sun.xml.ws.util");
/*     */   
/*     */   private Class annotatedClass;
/*     */   
/*     */   private List<HandlerChainType> handlerChains;
/*     */   private String id;
/*     */   
/*     */   private HandlerChainsModel(Class annotatedClass) {
/*  75 */     this.annotatedClass = annotatedClass;
/*     */   }
/*     */   public static final String PROTOCOL_SOAP11_TOKEN = "##SOAP11_HTTP"; public static final String PROTOCOL_SOAP12_TOKEN = "##SOAP12_HTTP"; public static final String PROTOCOL_XML_TOKEN = "##XML_HTTP"; public static final String NS_109 = "http://java.sun.com/xml/ns/javaee";
/*     */   private List<HandlerChainType> getHandlerChain() {
/*  79 */     if (this.handlerChains == null) {
/*  80 */       this.handlerChains = new ArrayList<HandlerChainType>();
/*     */     }
/*  82 */     return this.handlerChains;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  86 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(String value) {
/*  90 */     this.id = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static HandlerChainsModel parseHandlerConfigFile(Class annotatedClass, XMLStreamReader reader) {
/*  96 */     ensureProperName(reader, QNAME_HANDLER_CHAINS);
/*  97 */     HandlerChainsModel handlerModel = new HandlerChainsModel(annotatedClass);
/*  98 */     List<HandlerChainType> hChains = handlerModel.getHandlerChain();
/*  99 */     XMLStreamReaderUtil.nextElementContent(reader);
/*     */     
/* 101 */     while (reader.getName().equals(QNAME_HANDLER_CHAIN)) {
/* 102 */       HandlerChainType hChain = new HandlerChainType();
/* 103 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */       
/* 105 */       if (reader.getName().equals(QNAME_CHAIN_PORT_PATTERN)) {
/* 106 */         QName portNamePattern = XMLStreamReaderUtil.getElementQName(reader);
/* 107 */         hChain.setPortNamePattern(portNamePattern);
/* 108 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 109 */       } else if (reader.getName().equals(QNAME_CHAIN_PROTOCOL_BINDING)) {
/* 110 */         String bindingList = XMLStreamReaderUtil.getElementText(reader);
/* 111 */         StringTokenizer stk = new StringTokenizer(bindingList);
/* 112 */         while (stk.hasMoreTokens()) {
/* 113 */           String token = stk.nextToken();
/*     */           
/* 115 */           hChain.addProtocolBinding(token);
/*     */         } 
/* 117 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 118 */       } else if (reader.getName().equals(QNAME_CHAIN_SERVICE_PATTERN)) {
/* 119 */         QName serviceNamepattern = XMLStreamReaderUtil.getElementQName(reader);
/* 120 */         hChain.setServiceNamePattern(serviceNamepattern);
/* 121 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       } 
/* 123 */       List<HandlerType> handlers = hChain.getHandlers();
/*     */       
/* 125 */       while (reader.getName().equals(QNAME_HANDLER)) {
/* 126 */         HandlerType handler = new HandlerType();
/*     */         
/* 128 */         XMLStreamReaderUtil.nextContent(reader);
/* 129 */         if (reader.getName().equals(QNAME_HANDLER_NAME)) {
/* 130 */           String handlerName = XMLStreamReaderUtil.getElementText(reader).trim();
/*     */           
/* 132 */           handler.setHandlerName(handlerName);
/* 133 */           XMLStreamReaderUtil.nextContent(reader);
/*     */         } 
/*     */ 
/*     */         
/* 137 */         ensureProperName(reader, QNAME_HANDLER_CLASS);
/* 138 */         String handlerClass = XMLStreamReaderUtil.getElementText(reader).trim();
/*     */         
/* 140 */         handler.setHandlerClass(handlerClass);
/* 141 */         XMLStreamReaderUtil.nextContent(reader);
/*     */ 
/*     */         
/* 144 */         while (reader.getName().equals(QNAME_HANDLER_PARAM)) {
/* 145 */           skipInitParamElement(reader);
/*     */         }
/*     */ 
/*     */         
/* 149 */         while (reader.getName().equals(QNAME_HANDLER_HEADER)) {
/* 150 */           skipTextElement(reader);
/*     */         }
/*     */ 
/*     */         
/* 154 */         while (reader.getName().equals(QNAME_HANDLER_ROLE)) {
/* 155 */           List<String> soapRoles = handler.getSoapRoles();
/* 156 */           soapRoles.add(XMLStreamReaderUtil.getElementText(reader));
/* 157 */           XMLStreamReaderUtil.nextContent(reader);
/*     */         } 
/*     */         
/* 160 */         handlers.add(handler);
/*     */ 
/*     */         
/* 163 */         ensureProperName(reader, QNAME_HANDLER);
/* 164 */         XMLStreamReaderUtil.nextContent(reader);
/*     */       } 
/*     */ 
/*     */       
/* 168 */       ensureProperName(reader, QNAME_HANDLER_CHAIN);
/* 169 */       hChains.add(hChain);
/* 170 */       XMLStreamReaderUtil.nextContent(reader);
/*     */     } 
/*     */     
/* 173 */     return handlerModel;
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
/*     */   public static HandlerAnnotationInfo parseHandlerFile(XMLStreamReader reader, ClassLoader classLoader, QName serviceName, QName portName, WSBinding wsbinding) {
/* 192 */     ensureProperName(reader, QNAME_HANDLER_CHAINS);
/* 193 */     String bindingId = wsbinding.getBindingId().toString();
/* 194 */     HandlerAnnotationInfo info = new HandlerAnnotationInfo();
/*     */     
/* 196 */     XMLStreamReaderUtil.nextElementContent(reader);
/*     */     
/* 198 */     List<Handler> handlerChain = new ArrayList<Handler>();
/* 199 */     Set<String> roles = new HashSet<String>();
/*     */     
/* 201 */     while (reader.getName().equals(QNAME_HANDLER_CHAIN)) {
/*     */       
/* 203 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */       
/* 205 */       if (reader.getName().equals(QNAME_CHAIN_PORT_PATTERN)) {
/* 206 */         if (portName == null) {
/* 207 */           logger.warning("handler chain sepcified for port but port QName passed to parser is null");
/*     */         }
/*     */         
/* 210 */         boolean parseChain = JAXWSUtils.matchQNames(portName, XMLStreamReaderUtil.getElementQName(reader));
/*     */         
/* 212 */         if (!parseChain) {
/* 213 */           skipChain(reader);
/*     */           continue;
/*     */         } 
/* 216 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 217 */       } else if (reader.getName().equals(QNAME_CHAIN_PROTOCOL_BINDING)) {
/* 218 */         if (bindingId == null) {
/* 219 */           logger.warning("handler chain sepcified for bindingId but bindingId passed to parser is null");
/*     */         }
/*     */         
/* 222 */         String bindingConstraint = XMLStreamReaderUtil.getElementText(reader);
/* 223 */         boolean skipThisChain = true;
/* 224 */         StringTokenizer stk = new StringTokenizer(bindingConstraint);
/* 225 */         List<String> bindingList = new ArrayList<String>();
/* 226 */         while (stk.hasMoreTokens()) {
/* 227 */           String tokenOrURI = stk.nextToken();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 232 */           tokenOrURI = DeploymentDescriptorParser.getBindingIdForToken(tokenOrURI);
/* 233 */           String binding = BindingID.parse(tokenOrURI).toString();
/* 234 */           bindingList.add(binding);
/*     */         } 
/* 236 */         if (bindingList.contains(bindingId)) {
/* 237 */           skipThisChain = false;
/*     */         }
/*     */         
/* 240 */         if (skipThisChain) {
/* 241 */           skipChain(reader);
/*     */           continue;
/*     */         } 
/* 244 */         XMLStreamReaderUtil.nextElementContent(reader);
/* 245 */       } else if (reader.getName().equals(QNAME_CHAIN_SERVICE_PATTERN)) {
/* 246 */         if (serviceName == null) {
/* 247 */           logger.warning("handler chain sepcified for service but service QName passed to parser is null");
/*     */         }
/*     */         
/* 250 */         boolean parseChain = JAXWSUtils.matchQNames(serviceName, XMLStreamReaderUtil.getElementQName(reader));
/*     */ 
/*     */         
/* 253 */         if (!parseChain) {
/* 254 */           skipChain(reader);
/*     */           continue;
/*     */         } 
/* 257 */         XMLStreamReaderUtil.nextElementContent(reader);
/*     */       } 
/*     */ 
/*     */       
/* 261 */       while (reader.getName().equals(QNAME_HANDLER)) {
/*     */         Handler handler;
/*     */         
/* 264 */         XMLStreamReaderUtil.nextContent(reader);
/* 265 */         if (reader.getName().equals(QNAME_HANDLER_NAME)) {
/* 266 */           skipTextElement(reader);
/*     */         }
/*     */ 
/*     */         
/* 270 */         ensureProperName(reader, QNAME_HANDLER_CLASS);
/*     */         try {
/* 272 */           handler = loadClass(classLoader, XMLStreamReaderUtil.getElementText(reader).trim()).newInstance();
/*     */         }
/* 274 */         catch (InstantiationException ie) {
/* 275 */           throw new RuntimeException(ie);
/* 276 */         } catch (IllegalAccessException e) {
/* 277 */           throw new RuntimeException(e);
/*     */         } 
/* 279 */         XMLStreamReaderUtil.nextContent(reader);
/*     */ 
/*     */         
/* 282 */         while (reader.getName().equals(QNAME_HANDLER_PARAM)) {
/* 283 */           skipInitParamElement(reader);
/*     */         }
/*     */ 
/*     */         
/* 287 */         while (reader.getName().equals(QNAME_HANDLER_HEADER)) {
/* 288 */           skipTextElement(reader);
/*     */         }
/*     */ 
/*     */         
/* 292 */         while (reader.getName().equals(QNAME_HANDLER_ROLE)) {
/* 293 */           roles.add(XMLStreamReaderUtil.getElementText(reader));
/* 294 */           XMLStreamReaderUtil.nextContent(reader);
/*     */         } 
/*     */         Method[] arr$;
/*     */         int len$, i$;
/* 298 */         for (arr$ = handler.getClass().getMethods(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Method method = arr$[i$];
/* 299 */           if (method.getAnnotation(PostConstruct.class) == null) {
/*     */             i$++; continue;
/*     */           } 
/*     */           try {
/* 303 */             method.invoke(handler, new Object[0]);
/*     */           }
/* 305 */           catch (Exception e) {
/* 306 */             throw new RuntimeException(e);
/*     */           }  }
/*     */ 
/*     */         
/* 310 */         handlerChain.add(handler);
/*     */ 
/*     */         
/* 313 */         ensureProperName(reader, QNAME_HANDLER);
/* 314 */         XMLStreamReaderUtil.nextContent(reader);
/*     */       } 
/*     */ 
/*     */       
/* 318 */       ensureProperName(reader, QNAME_HANDLER_CHAIN);
/* 319 */       XMLStreamReaderUtil.nextContent(reader);
/*     */     } 
/*     */     
/* 322 */     info.setHandlers(handlerChain);
/* 323 */     info.setRoles(roles);
/* 324 */     return info;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerAnnotationInfo getHandlersForPortInfo(PortInfo info) {
/* 329 */     HandlerAnnotationInfo handlerInfo = new HandlerAnnotationInfo();
/* 330 */     List<Handler> handlerClassList = new ArrayList<Handler>();
/* 331 */     Set<String> roles = new HashSet<String>();
/*     */     
/* 333 */     for (HandlerChainType hchain : this.handlerChains) {
/* 334 */       boolean hchainMatched = false;
/* 335 */       if (!hchain.isConstraintSet() || JAXWSUtils.matchQNames(info.getServiceName(), hchain.getServiceNamePattern()) || JAXWSUtils.matchQNames(info.getPortName(), hchain.getPortNamePattern()) || hchain.getProtocolBindings().contains(info.getBindingID()))
/*     */       {
/*     */ 
/*     */         
/* 339 */         hchainMatched = true;
/*     */       }
/*     */       
/* 342 */       if (hchainMatched) {
/* 343 */         for (HandlerType handler : hchain.getHandlers()) {
/*     */           try {
/* 345 */             Handler handlerClass = loadClass(this.annotatedClass.getClassLoader(), handler.getHandlerClass()).newInstance();
/*     */             
/* 347 */             callHandlerPostConstruct(handlerClass);
/* 348 */             handlerClassList.add(handlerClass);
/* 349 */           } catch (InstantiationException ie) {
/* 350 */             throw new RuntimeException(ie);
/* 351 */           } catch (IllegalAccessException e) {
/* 352 */             throw new RuntimeException(e);
/*     */           } 
/*     */           
/* 355 */           roles.addAll(handler.getSoapRoles());
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 361 */     handlerInfo.setHandlers(handlerClassList);
/* 362 */     handlerInfo.setRoles(roles);
/* 363 */     return handlerInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class loadClass(ClassLoader loader, String name) {
/*     */     try {
/* 369 */       return Class.forName(name, true, loader);
/* 370 */     } catch (ClassNotFoundException e) {
/* 371 */       throw new UtilException("util.handler.class.not.found", new Object[] { name });
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void callHandlerPostConstruct(Object handlerClass) {
/*     */     Method[] arr$;
/*     */     int len$;
/*     */     int i$;
/* 379 */     for (arr$ = handlerClass.getClass().getMethods(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Method method = arr$[i$];
/* 380 */       if (method.getAnnotation(PostConstruct.class) == null) {
/*     */         i$++; continue;
/*     */       } 
/*     */       try {
/* 384 */         method.invoke(handlerClass, new Object[0]);
/*     */       }
/* 386 */       catch (Exception e) {
/* 387 */         throw new RuntimeException(e);
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private static void skipChain(XMLStreamReader reader) {
/* 394 */     while (XMLStreamReaderUtil.nextContent(reader) != 2 || !reader.getName().equals(QNAME_HANDLER_CHAIN));
/*     */     
/* 396 */     XMLStreamReaderUtil.nextElementContent(reader);
/*     */   }
/*     */   
/*     */   private static void skipTextElement(XMLStreamReader reader) {
/* 400 */     XMLStreamReaderUtil.nextContent(reader);
/* 401 */     XMLStreamReaderUtil.nextElementContent(reader);
/* 402 */     XMLStreamReaderUtil.nextElementContent(reader);
/*     */   }
/*     */   
/*     */   private static void skipInitParamElement(XMLStreamReader reader) {
/*     */     int state;
/*     */     do {
/* 408 */       state = XMLStreamReaderUtil.nextContent(reader);
/* 409 */     } while (state != 2 || !reader.getName().equals(QNAME_HANDLER_PARAM));
/*     */     
/* 411 */     XMLStreamReaderUtil.nextElementContent(reader);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureProperName(XMLStreamReader reader, QName expectedName) {
/* 417 */     if (!reader.getName().equals(expectedName)) {
/* 418 */       failWithLocalName("util.parser.wrong.element", reader, expectedName.getLocalPart());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static void ensureProperName(XMLStreamReader reader, String expectedName) {
/* 424 */     if (!reader.getLocalName().equals(expectedName)) {
/* 425 */       failWithLocalName("util.parser.wrong.element", reader, expectedName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void failWithLocalName(String key, XMLStreamReader reader, String arg) {
/* 432 */     throw new UtilException(key, new Object[] { Integer.toString(reader.getLocation().getLineNumber()), reader.getLocalName(), arg });
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
/* 444 */   public static final QName QNAME_CHAIN_PORT_PATTERN = new QName("http://java.sun.com/xml/ns/javaee", "port-name-pattern");
/*     */   
/* 446 */   public static final QName QNAME_CHAIN_PROTOCOL_BINDING = new QName("http://java.sun.com/xml/ns/javaee", "protocol-bindings");
/*     */   
/* 448 */   public static final QName QNAME_CHAIN_SERVICE_PATTERN = new QName("http://java.sun.com/xml/ns/javaee", "service-name-pattern");
/*     */   
/* 450 */   public static final QName QNAME_HANDLER_CHAIN = new QName("http://java.sun.com/xml/ns/javaee", "handler-chain");
/*     */   
/* 452 */   public static final QName QNAME_HANDLER_CHAINS = new QName("http://java.sun.com/xml/ns/javaee", "handler-chains");
/*     */   
/* 454 */   public static final QName QNAME_HANDLER = new QName("http://java.sun.com/xml/ns/javaee", "handler");
/*     */   
/* 456 */   public static final QName QNAME_HANDLER_NAME = new QName("http://java.sun.com/xml/ns/javaee", "handler-name");
/*     */   
/* 458 */   public static final QName QNAME_HANDLER_CLASS = new QName("http://java.sun.com/xml/ns/javaee", "handler-class");
/*     */   
/* 460 */   public static final QName QNAME_HANDLER_PARAM = new QName("http://java.sun.com/xml/ns/javaee", "init-param");
/*     */   
/* 462 */   public static final QName QNAME_HANDLER_PARAM_NAME = new QName("http://java.sun.com/xml/ns/javaee", "param-name");
/*     */   
/* 464 */   public static final QName QNAME_HANDLER_PARAM_VALUE = new QName("http://java.sun.com/xml/ns/javaee", "param-value");
/*     */   
/* 466 */   public static final QName QNAME_HANDLER_HEADER = new QName("http://java.sun.com/xml/ns/javaee", "soap-header");
/*     */   
/* 468 */   public static final QName QNAME_HANDLER_ROLE = new QName("http://java.sun.com/xml/ns/javaee", "soap-role");
/*     */ 
/*     */   
/*     */   static class HandlerChainType
/*     */   {
/*     */     QName serviceNamePattern;
/*     */     
/*     */     QName portNamePattern;
/*     */     
/*     */     List<String> protocolBindings;
/*     */     
/*     */     boolean constraintSet = false;
/*     */     
/*     */     List<HandlerChainsModel.HandlerType> handlers;
/*     */     
/*     */     String id;
/*     */     
/*     */     public HandlerChainType() {
/* 486 */       this.protocolBindings = new ArrayList<String>();
/*     */     }
/*     */     
/*     */     public void setServiceNamePattern(QName value) {
/* 490 */       this.serviceNamePattern = value;
/* 491 */       this.constraintSet = true;
/*     */     }
/*     */     
/*     */     public QName getServiceNamePattern() {
/* 495 */       return this.serviceNamePattern;
/*     */     }
/*     */     
/*     */     public void setPortNamePattern(QName value) {
/* 499 */       this.portNamePattern = value;
/* 500 */       this.constraintSet = true;
/*     */     }
/*     */     
/*     */     public QName getPortNamePattern() {
/* 504 */       return this.portNamePattern;
/*     */     }
/*     */     
/*     */     public List<String> getProtocolBindings() {
/* 508 */       return this.protocolBindings;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addProtocolBinding(String tokenOrURI) {
/* 516 */       tokenOrURI = DeploymentDescriptorParser.getBindingIdForToken(tokenOrURI);
/* 517 */       String binding = BindingID.parse(tokenOrURI).toString();
/* 518 */       this.protocolBindings.add(binding);
/* 519 */       this.constraintSet = true;
/*     */     }
/*     */     
/*     */     public boolean isConstraintSet() {
/* 523 */       return (this.constraintSet || !this.protocolBindings.isEmpty());
/*     */     }
/*     */     public String getId() {
/* 526 */       return this.id;
/*     */     }
/*     */     
/*     */     public void setId(String value) {
/* 530 */       this.id = value;
/*     */     }
/*     */     
/*     */     public List<HandlerChainsModel.HandlerType> getHandlers() {
/* 534 */       if (this.handlers == null) {
/* 535 */         this.handlers = new ArrayList<HandlerChainsModel.HandlerType>();
/*     */       }
/* 537 */       return this.handlers;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class HandlerType
/*     */   {
/*     */     String handlerName;
/*     */     
/*     */     String handlerClass;
/*     */     
/*     */     List<String> soapRoles;
/*     */     
/*     */     String id;
/*     */     
/*     */     public String getHandlerName() {
/* 553 */       return this.handlerName;
/*     */     }
/*     */     
/*     */     public void setHandlerName(String value) {
/* 557 */       this.handlerName = value;
/*     */     }
/*     */     
/*     */     public String getHandlerClass() {
/* 561 */       return this.handlerClass;
/*     */     }
/*     */     
/*     */     public void setHandlerClass(String value) {
/* 565 */       this.handlerClass = value;
/*     */     }
/*     */     
/*     */     public String getId() {
/* 569 */       return this.id;
/*     */     }
/*     */     
/*     */     public void setId(String value) {
/* 573 */       this.id = value;
/*     */     }
/*     */     
/*     */     public List<String> getSoapRoles() {
/* 577 */       if (this.soapRoles == null) {
/* 578 */         this.soapRoles = new ArrayList<String>();
/*     */       }
/* 580 */       return this.soapRoles;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\HandlerChainsModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */