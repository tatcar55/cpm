/*     */ package com.sun.xml.rpc.client.dii;
/*     */ 
/*     */ import com.sun.xml.rpc.client.ContentNegotiationProperties;
/*     */ import com.sun.xml.rpc.client.HandlerChainImpl;
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.DynamicInternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.ReferenceableSerializerImpl;
/*     */ import com.sun.xml.rpc.encoding.SOAPFaultInfoSerializer;
/*     */ import com.sun.xml.rpc.encoding.literal.LiteralRequestSerializer;
/*     */ import com.sun.xml.rpc.encoding.literal.LiteralResponseSerializer;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPRequestSerializer;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPResponseSerializer;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPResponseStructure;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.rmi.RemoteException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.ParameterMode;
/*     */ import javax.xml.rpc.handler.HandlerChain;
/*     */ import javax.xml.rpc.handler.HandlerRegistry;
/*     */ import javax.xml.rpc.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BasicCall
/*     */   implements CallEx
/*     */ {
/*  69 */   protected static final QName EMPTY_QNAME = new QName("");
/*  70 */   protected static final QName RESULT_QNAME = null;
/*     */ 
/*     */   
/*     */   protected static final int RETURN_VALUE_INDEX = 0;
/*     */   
/*     */   private boolean isProxy = false;
/*     */   
/*     */   protected boolean isOneWay;
/*     */   
/*  79 */   protected static final JAXRPCDeserializer faultDeserializer = (JAXRPCDeserializer)new ReferenceableSerializerImpl(false, (CombinedSerializer)new SOAPFaultInfoSerializer(false, false));
/*     */ 
/*     */   
/*     */   protected static final Set recognizedProperties;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  87 */     Set<String> temp = new HashSet();
/*  88 */     temp.add("javax.xml.rpc.security.auth.username");
/*  89 */     temp.add("javax.xml.rpc.security.auth.password");
/*  90 */     temp.add("javax.xml.rpc.endpoint");
/*  91 */     temp.add("javax.xml.rpc.soap.operation.style");
/*  92 */     temp.add("javax.xml.rpc.soap.http.soapaction.use");
/*  93 */     temp.add("javax.xml.rpc.soap.http.soapaction.uri");
/*  94 */     temp.add("javax.xml.rpc.session.maintain");
/*  95 */     temp.add("javax.xml.rpc.encodingstyle.namespace.uri");
/*  96 */     temp.add("com.sun.xml.rpc.client.http.CookieJar");
/*  97 */     temp.add("com.sun.xml.rpc.client.http.HostnameVerificationProperty");
/*  98 */     temp.add("com.sun.xml.rpc.client.http.RedirectRequestProperty");
/*  99 */     temp.add("com.sun.xml.rpc.security.context");
/*     */     
/* 101 */     temp.add("com.sun.xml.rpc.attachment.SetAttachmentContext");
/* 102 */     temp.add("com.sun.xml.rpc.attachment.GetAttachmentContext");
/* 103 */     temp.add("com.sun.xml.rpc.client.ContentNegotiation");
/* 104 */     recognizedProperties = Collections.unmodifiableSet(temp);
/*     */   }
/*     */   
/* 107 */   protected JAXRPCSerializer requestSerializer = null;
/* 108 */   protected JAXRPCDeserializer responseDeserializer = null;
/*     */   
/* 110 */   protected List inParameterNames = new ArrayList();
/* 111 */   protected List outParameterNames = new ArrayList();
/* 112 */   protected List inParameterXmlTypes = new ArrayList();
/* 113 */   protected List outParameterXmlTypes = new ArrayList();
/* 114 */   protected List inParameterXmlTypeQNames = new ArrayList();
/* 115 */   protected List outParameterXmlTypeQNames = new ArrayList();
/* 116 */   protected List inParameterClasses = new ArrayList();
/* 117 */   protected List outParameterClasses = new ArrayList();
/* 118 */   protected SOAPResponseStructure response = null;
/* 119 */   protected List inParameterMembers = new ArrayList();
/* 120 */   protected List outParameterMembers = new ArrayList();
/* 121 */   protected QName returnXmlType = null;
/* 122 */   protected QName returnXmlTypeQName = null;
/* 123 */   protected Class returnClass = null;
/* 124 */   protected String returnClassName = null;
/* 125 */   protected QName returnTypeQName = null;
/* 126 */   protected ParameterMemberInfo[] returnParameterMembers = null;
/*     */   
/* 128 */   protected QName operationName = EMPTY_QNAME;
/* 129 */   protected QName portName = EMPTY_QNAME;
/* 130 */   protected QName portTypeName = EMPTY_QNAME;
/*     */   
/* 132 */   protected String targetEndpointAddress = null;
/* 133 */   protected Map properties = new HashMap<Object, Object>();
/*     */   protected InternalTypeMappingRegistry typeRegistry;
/* 135 */   protected CallInvoker invoker = new CallInvokerImpl();
/*     */   
/*     */   protected Collection packages;
/*     */   protected HandlerRegistry handlerRegistry;
/*     */   protected OperationInfo operationInfo;
/* 140 */   private SOAPNamespaceConstants soapNamespaceConstants = null;
/*     */   
/*     */   private void init(SOAPVersion ver) {
/* 143 */     this.soapNamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(ver);
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicCall(InternalTypeMappingRegistry registry, HandlerRegistry handlerRegistry) {
/* 148 */     this(registry, handlerRegistry, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */   
/*     */   public BasicCall(InternalTypeMappingRegistry registry, HandlerRegistry handlerRegistry, SOAPVersion ver) {
/* 153 */     if (registry == null) {
/* 154 */       throw new DynamicInvocationException("dii.typeregistry.missing.in.call");
/*     */     }
/* 156 */     init(ver);
/*     */     
/* 158 */     this.typeRegistry = (InternalTypeMappingRegistry)new DynamicInternalTypeMappingRegistry(registry, this);
/* 159 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 161 */     ((DynamicInternalTypeMappingRegistry)this.typeRegistry).setStyles(operationStyle);
/* 162 */     this.handlerRegistry = handlerRegistry;
/*     */     
/* 164 */     setProperty("javax.xml.rpc.soap.http.soapaction.use", new Boolean(false));
/*     */     
/* 166 */     ContentNegotiationProperties.initFromSystemProperties(this.properties);
/*     */   }
/*     */   
/*     */   public boolean isParameterAndReturnSpecRequired(QName operation) {
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerChain getHandlerChain() {
/* 175 */     if (this.handlerRegistry.getHandlerChain(this.portName) == null) {
/* 176 */       return null;
/*     */     }
/*     */     
/* 179 */     return (HandlerChain)new HandlerChainImpl(this.handlerRegistry.getHandlerChain(this.portName));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParameter(String paramName, QName paramXmlType, ParameterMode parameterMode) {
/* 185 */     checkIsParameterAndReturnTypeSpecAllowed();
/* 186 */     doAddParameter(paramName, paramXmlType, parameterMode);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAddParameter(String paramName, QName paramXmlType, ParameterMode parameterMode) {
/* 192 */     doAddParameter(paramName, paramXmlType, null, parameterMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addParameter(String paramName, QName paramXmlType, Class paramClass, ParameterMode parameterMode) {
/* 199 */     checkIsParameterAndReturnTypeSpecAllowed();
/* 200 */     doAddParameter(paramName, paramXmlType, paramClass, parameterMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAddParameter(String paramName, QName paramXmlType, Class<?> paramClass, ParameterMode parameterMode) {
/* 207 */     resetSerializers();
/*     */     
/* 209 */     if (parameterMode == ParameterMode.OUT) {
/* 210 */       this.outParameterNames.add(new QName(paramName));
/* 211 */       this.outParameterXmlTypes.add(paramXmlType);
/* 212 */       this.outParameterClasses.add(paramClass);
/* 213 */     } else if (parameterMode == ParameterMode.IN) {
/* 214 */       this.inParameterNames.add(new QName(paramName));
/* 215 */       this.inParameterXmlTypes.add(paramXmlType);
/* 216 */       this.inParameterClasses.add(paramClass);
/* 217 */     } else if (parameterMode == ParameterMode.INOUT) {
/* 218 */       this.inParameterNames.add(new QName(paramName));
/* 219 */       this.inParameterXmlTypes.add(paramXmlType);
/* 220 */       this.inParameterClasses.add(paramClass);
/* 221 */       this.outParameterNames.add(new QName(paramName));
/* 222 */       this.outParameterXmlTypes.add(paramXmlType);
/* 223 */       this.outParameterClasses.add(paramClass);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAddParameter(String paramName, QName paramXmlType, Class<?> paramClass, ParameterMemberInfo[] parameterMemberInfos, ParameterMode parameterMode) {
/* 232 */     resetSerializers();
/*     */     
/* 234 */     if (parameterMode == ParameterMode.OUT) {
/* 235 */       this.outParameterNames.add(new QName(paramName));
/* 236 */       this.outParameterXmlTypes.add(paramXmlType);
/* 237 */       this.outParameterClasses.add(paramClass);
/* 238 */       this.outParameterMembers.add(parameterMemberInfos);
/* 239 */     } else if (parameterMode == ParameterMode.IN) {
/* 240 */       this.inParameterNames.add(new QName(paramName));
/* 241 */       this.inParameterXmlTypes.add(paramXmlType);
/* 242 */       this.inParameterClasses.add(paramClass);
/* 243 */       this.inParameterMembers.add(parameterMemberInfos);
/* 244 */     } else if (parameterMode == ParameterMode.INOUT) {
/* 245 */       this.inParameterNames.add(new QName(paramName));
/* 246 */       this.inParameterXmlTypes.add(paramXmlType);
/* 247 */       this.inParameterClasses.add(paramClass);
/* 248 */       this.inParameterMembers.add(parameterMemberInfos);
/* 249 */       this.outParameterNames.add(new QName(paramName));
/* 250 */       this.outParameterXmlTypes.add(paramXmlType);
/* 251 */       this.outParameterClasses.add(paramClass);
/* 252 */       this.outParameterMembers.add(parameterMemberInfos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doAddParameter(String paramName, QName paramXmlType, QName paramXmlTypeQName, Class<?> paramClass, ParameterMemberInfo[] parameterMemberInfos, ParameterMode parameterMode) {
/* 262 */     resetSerializers();
/*     */     
/* 264 */     if (parameterMode == ParameterMode.OUT) {
/* 265 */       this.outParameterNames.add(new QName(paramName));
/* 266 */       this.outParameterXmlTypes.add(paramXmlType);
/* 267 */       this.outParameterXmlTypeQNames.add(paramXmlTypeQName);
/* 268 */       this.outParameterClasses.add(paramClass);
/* 269 */       this.outParameterMembers.add(parameterMemberInfos);
/* 270 */     } else if (parameterMode == ParameterMode.IN) {
/* 271 */       this.inParameterNames.add(new QName(paramName));
/* 272 */       this.inParameterXmlTypes.add(paramXmlType);
/* 273 */       this.inParameterXmlTypeQNames.add(paramXmlTypeQName);
/* 274 */       this.inParameterClasses.add(paramClass);
/* 275 */       this.inParameterMembers.add(parameterMemberInfos);
/* 276 */     } else if (parameterMode == ParameterMode.INOUT) {
/* 277 */       this.inParameterNames.add(new QName(paramName));
/* 278 */       this.inParameterXmlTypes.add(paramXmlType);
/* 279 */       this.inParameterXmlTypeQNames.add(paramXmlTypeQName);
/* 280 */       this.inParameterClasses.add(paramClass);
/* 281 */       this.inParameterMembers.add(parameterMemberInfos);
/* 282 */       this.outParameterNames.add(new QName(paramName));
/* 283 */       this.outParameterXmlTypes.add(paramXmlType);
/* 284 */       this.outParameterXmlTypeQNames.add(paramXmlTypeQName);
/* 285 */       this.outParameterClasses.add(paramClass);
/* 286 */       this.outParameterMembers.add(parameterMemberInfos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public QName[] getInParameterXmlTypes() {
/* 292 */     return (QName[])this.inParameterXmlTypes.toArray((Object[])new QName[this.inParameterXmlTypes.size()]);
/*     */   }
/*     */   
/*     */   public QName getParameterTypeByName(String parameterName) {
/* 296 */     ListIterator<QName> eachName = this.inParameterNames.listIterator();
/* 297 */     while (eachName.hasNext()) {
/* 298 */       String currentName = ((QName)eachName.next()).getLocalPart();
/* 299 */       if (currentName.equals(parameterName)) {
/* 300 */         return this.inParameterXmlTypes.get(eachName.previousIndex());
/*     */       }
/*     */     } 
/* 303 */     eachName = this.outParameterNames.listIterator();
/* 304 */     while (eachName.hasNext()) {
/* 305 */       String currentName = ((QName)eachName.next()).getLocalPart();
/* 306 */       if (currentName.equals(parameterName)) {
/* 307 */         return this.outParameterXmlTypes.get(eachName.previousIndex());
/*     */       }
/*     */     } 
/* 310 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean isProxy() {
/* 314 */     return this.isProxy;
/*     */   }
/*     */   
/*     */   protected void setIsProxy(boolean value) {
/* 318 */     this.isProxy = value;
/*     */   }
/*     */   
/*     */   public void setReturnTypeQName(QName returnTypeQName) {
/* 322 */     this.returnTypeQName = returnTypeQName;
/*     */   }
/*     */   
/*     */   public void setReturnXmlTypeQName(QName returnXmlTypeQName) {
/* 326 */     this.returnXmlTypeQName = returnXmlTypeQName;
/*     */   }
/*     */   
/*     */   public void setReturnType(QName type) {
/* 330 */     if (type != null) {
/* 331 */       checkIsParameterAndReturnTypeSpecAllowed();
/*     */     }
/* 333 */     doSetReturnType(type);
/*     */   }
/*     */   
/*     */   protected void doSetReturnType(QName type) {
/* 337 */     setReturnType(type, null);
/*     */   }
/*     */   
/*     */   public void setReturnParameterInfos(ParameterMemberInfo[] infos) {
/* 341 */     this.returnParameterMembers = infos;
/*     */   }
/*     */   
/*     */   public ParameterMemberInfo[] getReturnParameterMembers() {
/* 345 */     return this.returnParameterMembers;
/*     */   }
/*     */   
/*     */   public void setReturnType(QName type, Class javaType) {
/* 349 */     if (type != null && javaType != null) {
/* 350 */       checkIsParameterAndReturnTypeSpecAllowed();
/*     */     }
/* 352 */     doSetReturnType(type, javaType);
/*     */   }
/*     */   
/*     */   protected void doSetReturnType(QName type, Class javaType) {
/* 356 */     resetSerializers();
/*     */     
/* 358 */     this.returnXmlType = type;
/* 359 */     this.returnClass = javaType;
/*     */   }
/*     */   
/*     */   public QName getReturnType() {
/* 363 */     return this.returnXmlType;
/*     */   }
/*     */   
/*     */   protected void setReturnTypeName(String name) {
/* 367 */     this.returnClassName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllParameters() {
/* 373 */     doRemoveAllParameters();
/*     */   }
/*     */   
/*     */   protected void doRemoveAllParameters() {
/* 377 */     resetSerializers();
/*     */     
/* 379 */     this.inParameterNames.clear();
/* 380 */     this.inParameterXmlTypes.clear();
/*     */     
/* 382 */     this.inParameterClasses.clear();
/*     */     
/* 384 */     this.outParameterNames.clear();
/* 385 */     this.outParameterXmlTypes.clear();
/*     */     
/* 387 */     this.outParameterClasses.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetSerializers() {
/* 393 */     this.requestSerializer = null;
/* 394 */     this.responseDeserializer = null;
/*     */   }
/*     */   
/*     */   public QName getPortTypeName() {
/* 398 */     return this.portTypeName;
/*     */   }
/*     */   
/*     */   public void setPortTypeName(QName portType) {
/* 402 */     this.portTypeName = portType;
/*     */   }
/*     */   
/*     */   public QName getPortName() {
/* 406 */     return this.portName;
/*     */   }
/*     */   
/*     */   public void setPortName(QName port) {
/* 410 */     this.portName = port;
/*     */   }
/*     */   
/*     */   public QName getOperationName() {
/* 414 */     return this.operationName;
/*     */   }
/*     */   
/*     */   protected void setOperationInfo(OperationInfo info) {
/* 418 */     this.operationInfo = info;
/*     */   }
/*     */   
/*     */   public OperationInfo getOperationInfo() {
/* 422 */     return this.operationInfo;
/*     */   }
/*     */   
/*     */   public void setOperationName(QName operationName) {
/* 426 */     this.operationName = operationName;
/*     */   }
/*     */   
/*     */   public void setTargetEndpointAddress(String address) {
/* 430 */     this.targetEndpointAddress = address;
/* 431 */     this.invoker = new CallInvokerImpl();
/*     */   }
/*     */   
/*     */   public String getTargetEndpointAddress() {
/* 435 */     return this.targetEndpointAddress;
/*     */   }
/*     */   
/*     */   public void setProperty(String name, Object value) {
/* 439 */     if (!recognizedProperties.contains(name)) {
/* 440 */       throw new DynamicInvocationException("dii.call.property.set.unrecognized", new Object[] { name });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 445 */     if (name.equals("com.sun.xml.rpc.client.ContentNegotiation")) {
/* 446 */       this.properties.put(name, ((String)value).intern());
/*     */     } else {
/*     */       
/* 449 */       this.properties.put(name, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) {
/* 454 */     if (!recognizedProperties.contains(name)) {
/* 455 */       throw new DynamicInvocationException("dii.call.property.get.unrecognized", new Object[] { name });
/*     */     }
/*     */ 
/*     */     
/* 459 */     return this.properties.get(name);
/*     */   }
/*     */   
/*     */   public void removeProperty(String name) {
/* 463 */     this.properties.remove(name);
/*     */   }
/*     */   
/*     */   public Iterator getPropertyNames() {
/* 467 */     return recognizedProperties.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object[] parameters) throws RemoteException {
/* 473 */     if (this.isOneWay) {
/* 474 */       invokeOneWay(parameters);
/*     */     } else {
/* 476 */       if (this.packages != null) {
/* 477 */         this.packages.clear();
/*     */       }
/* 479 */       if (parameters == null) {
/* 480 */         parameters = new Object[0];
/*     */       } else {
/* 482 */         this.packages = collectPackages(parameters);
/*     */       } 
/*     */       
/* 485 */       this.inParameterClasses = validateParameters(this.inParameterClasses, null, this.packages);
/*     */       
/* 487 */       this.returnClass = validateReturnClass(this.returnClass, this.returnClassName, this.packages);
/*     */       
/*     */       try {
/* 490 */         String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */         
/* 492 */         ((DynamicInternalTypeMappingRegistry)this.typeRegistry).setStyles(operationStyle);
/* 493 */         String encodingStyle = (String)getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */ 
/*     */         
/* 496 */         if ("document".equals(operationStyle) && "".equals(encodingStyle)) {
/* 497 */           this.response = getInvoker().doInvoke(new CallRequest(this, parameters), getRequestSerializer(), getResponseDeserializer(), getFaultDeserializer());
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 502 */         else if ("rpc".equals(operationStyle) && "".equals(encodingStyle)) {
/* 503 */           this.response = getInvoker().doInvoke(new CallRequest(this, parameters), getRequestSerializer(), getResponseDeserializer(), getFaultDeserializer());
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 508 */         else if ("rpc".equals(operationStyle) && this.soapNamespaceConstants.getEncoding().equals(encodingStyle)) {
/* 509 */           this.response = getInvoker().doInvoke(new CallRequest(this, parameters), getRequestSerializer(), getResponseDeserializer(), getFaultDeserializer());
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 515 */           throw unsupportedOperationStyleException(operationStyle);
/*     */         } 
/* 517 */       } catch (RemoteException e) {
/* 518 */         throw e;
/* 519 */       } catch (Exception e) {
/* 520 */         if (e instanceof SOAPFaultException) {
/* 521 */           if (isProxy()) {
/* 522 */             throw (SOAPFaultException)e;
/*     */           }
/* 524 */           throw new RemoteException(((SOAPFaultException)e).getFaultString());
/* 525 */         }  if (e instanceof RuntimeException) {
/* 526 */           throw (RuntimeException)e;
/*     */         }
/* 528 */         throw new RemoteException("", new DynamicInvocationException(new LocalizableExceptionAdapter(e)));
/*     */       } 
/*     */ 
/*     */       
/* 532 */       if (this.response != null)
/* 533 */         return this.response.returnValue; 
/*     */     } 
/* 535 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(QName operationName, Object[] inputParams) throws RemoteException {
/* 540 */     setOperationName(operationName);
/* 541 */     Object returnValue = invoke(inputParams);
/* 542 */     return returnValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invokeOneWay(Object[] parameters) {
/* 547 */     if (parameters == null) {
/* 548 */       parameters = new Object[0];
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 553 */       String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */       
/* 555 */       String encodingStyle = (String)getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */ 
/*     */       
/* 558 */       if ("document".equals(operationStyle) && "".equals(encodingStyle)) {
/* 559 */         getInvoker().doInvokeOneWay(new CallRequest(this, parameters), getRequestSerializer());
/*     */       }
/* 561 */       else if ("rpc".equals(operationStyle) && "".equals(encodingStyle)) {
/* 562 */         getInvoker().doInvokeOneWay(new CallRequest(this, parameters), getRequestSerializer());
/*     */       }
/* 564 */       else if ("rpc".equals(operationStyle) && this.soapNamespaceConstants.getEncoding().equals(encodingStyle)) {
/* 565 */         getInvoker().doInvokeOneWay(new CallRequest(this, parameters), getRequestSerializer());
/*     */       } else {
/*     */         
/* 568 */         throw unsupportedOperationStyleException(operationStyle);
/*     */       } 
/* 570 */     } catch (Exception e) {
/* 571 */       if (!(e instanceof SOAPFaultException)) {
/*     */ 
/*     */         
/* 574 */         if (e instanceof RuntimeException) {
/* 575 */           throw (RuntimeException)e;
/*     */         }
/* 577 */         throw new DynamicInvocationException(new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected DynamicInvocationException unsupportedOperationStyleException(String operationStyle) {
/* 583 */     return new DynamicInvocationException("dii.operation.style.unsupported", new Object[] { operationStyle });
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getOutputParams() {
/* 588 */     if (this.response == null) {
/* 589 */       throw new DynamicInvocationException("dii.outparameters.not.available");
/*     */     }
/*     */     
/* 592 */     return Collections.unmodifiableMap(this.response.outParametersStringKeys);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map getOutputParamsQNames() {
/* 597 */     if (this.response == null) {
/* 598 */       throw new DynamicInvocationException("dii.outparameters.not.available");
/*     */     }
/* 600 */     return Collections.unmodifiableMap(this.response.outParameters);
/*     */   }
/*     */   
/*     */   public List getOutputValues() {
/* 604 */     return Collections.unmodifiableList((List)this.response.outParameters.values());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Object getRequiredProperty(String requiredProperty) {
/* 610 */     Object property = getProperty(requiredProperty);
/* 611 */     if (property == null) {
/* 612 */       throw propertyNotFoundException(requiredProperty);
/*     */     }
/* 614 */     return property;
/*     */   }
/*     */   
/*     */   protected void checkIsParameterAndReturnTypeSpecAllowed() {
/* 618 */     if (!isParameterAndReturnSpecRequired(this.operationName)) {
/* 619 */       throw new DynamicInvocationException("dii.parameterandreturntypespec.not.allowed");
/*     */     }
/*     */   }
/*     */   
/*     */   protected CallInvoker getInvoker() {
/* 624 */     return this.invoker;
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getRequestSerializer() throws Exception {
/* 629 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 631 */     String encodingStyle = (String)getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */     
/* 633 */     if (this.requestSerializer == null)
/*     */     {
/* 635 */       if (this.soapNamespaceConstants.getEncoding().equals(encodingStyle) && "rpc".equals(operationStyle)) {
/* 636 */         createRpcRequestSerializer();
/* 637 */       } else if ("".equals(encodingStyle) && "rpc".equals(operationStyle)) {
/* 638 */         createRpcLiteralRequestSerializer();
/* 639 */       } else if ("".equals(encodingStyle) && "document".equals(operationStyle)) {
/* 640 */         createLiteralRequestSerializer();
/*     */       } else {
/* 642 */         throw new DynamicInvocationException("dii.encoding.style.unsupported", new Object[] { encodingStyle });
/*     */       } 
/*     */     }
/*     */     
/* 646 */     return this.requestSerializer;
/*     */   }
/*     */   
/*     */   protected void createRpcRequestSerializer() throws Exception {
/* 650 */     int parameterCount = this.inParameterNames.size();
/*     */     
/* 652 */     this.requestSerializer = (JAXRPCSerializer)new SOAPRequestSerializer(EMPTY_QNAME, (QName[])this.inParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.inParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (Class[])this.inParameterClasses.toArray((Object[])new Class[parameterCount]));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 657 */     ((Initializable)this.requestSerializer).initialize(this.typeRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createLiteralRequestSerializer() throws Exception {
/* 662 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 664 */     int parameterCount = this.inParameterNames.size();
/*     */     
/* 666 */     this.requestSerializer = (JAXRPCSerializer)new LiteralRequestSerializer(this.inParameterXmlTypes.get(0), false, true, "", operationStyle, (QName[])this.inParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.inParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (QName[])this.inParameterXmlTypeQNames.toArray((Object[])new QName[parameterCount]), (Class[])this.inParameterClasses.toArray((Object[])new Class[parameterCount]), (ArrayList)this.inParameterMembers);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 675 */     ((Initializable)this.requestSerializer).initialize(this.typeRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createRpcLiteralRequestSerializer() throws Exception {
/* 680 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 682 */     int parameterCount = this.inParameterNames.size();
/* 683 */     this.requestSerializer = (JAXRPCSerializer)new LiteralRequestSerializer(getOperationName(), false, true, "", operationStyle, (QName[])this.inParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.inParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (Class[])this.inParameterClasses.toArray((Object[])new Class[parameterCount]), (ArrayList)this.inParameterMembers);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 692 */     ((Initializable)this.requestSerializer).initialize(this.typeRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getResponseDeserializer() throws Exception {
/* 697 */     if (this.responseDeserializer == null) {
/*     */       
/* 699 */       String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */       
/* 701 */       String encodingStyle = (String)getProperty("javax.xml.rpc.encodingstyle.namespace.uri");
/*     */ 
/*     */       
/* 704 */       if (this.soapNamespaceConstants.getEncoding().equals(encodingStyle) && "rpc".equals(operationStyle)) {
/* 705 */         createRpcResponseSerializer();
/* 706 */       } else if ("".equals(encodingStyle) && "rpc".equals(operationStyle)) {
/* 707 */         createRpcLiteralResponseSerializer();
/* 708 */       } else if ("".equals(encodingStyle) && "document".equals(operationStyle)) {
/* 709 */         createLiteralResponseSerializer();
/*     */       } else {
/* 711 */         throw new DynamicInvocationException("dii.encoding.style.unsupported", new Object[] { encodingStyle });
/*     */       } 
/*     */     } 
/*     */     
/* 715 */     return this.responseDeserializer;
/*     */   }
/*     */   
/*     */   protected void createRpcResponseSerializer() throws Exception {
/* 719 */     int parameterCount = this.outParameterNames.size();
/* 720 */     this.responseDeserializer = (JAXRPCDeserializer)new ReferenceableSerializerImpl(false, (CombinedSerializer)new SOAPResponseSerializer(EMPTY_QNAME, (QName[])this.outParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.outParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (Class[])this.outParameterClasses.toArray((Object[])new Class[parameterCount]), this.returnXmlType, this.returnClass));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 728 */     ((Initializable)this.responseDeserializer).initialize(this.typeRegistry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createLiteralResponseSerializer() throws Exception {
/* 733 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */ 
/*     */     
/* 736 */     int parameterCount = this.outParameterNames.size();
/*     */     
/* 738 */     if (this.returnClass == null && this.returnXmlType != null) {
/* 739 */       throw serializerNotFoundException(0, this.returnXmlType, null, this.returnXmlType);
/*     */     }
/*     */     
/* 742 */     this.responseDeserializer = (JAXRPCDeserializer)new LiteralResponseSerializer(this.returnXmlType, false, true, "", operationStyle, (QName[])this.outParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.outParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (QName[])this.outParameterXmlTypeQNames.toArray((Object[])new QName[parameterCount]), (Class[])this.outParameterClasses.toArray((Object[])new Class[parameterCount]), (ArrayList)this.outParameterMembers, this.returnXmlType, this.returnXmlTypeQName, this.returnClass, this.returnParameterMembers);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 754 */     ((Initializable)this.responseDeserializer).initialize(this.typeRegistry);
/*     */   }
/*     */   
/*     */   protected void createRpcLiteralResponseSerializer() throws Exception {
/* 758 */     String operationStyle = (String)getProperty("javax.xml.rpc.soap.operation.style");
/*     */     
/* 760 */     QName responseQName = new QName(this.operationName.getNamespaceURI(), this.operationName.getLocalPart() + "Response");
/*     */ 
/*     */     
/* 763 */     int parameterCount = this.outParameterNames.size();
/* 764 */     this.responseDeserializer = (JAXRPCDeserializer)new LiteralResponseSerializer(responseQName, false, true, "", operationStyle, (QName[])this.outParameterNames.toArray((Object[])new QName[parameterCount]), (QName[])this.outParameterXmlTypes.toArray((Object[])new QName[parameterCount]), (Class[])this.outParameterClasses.toArray((Object[])new Class[parameterCount]), (ArrayList)this.outParameterMembers, this.returnXmlType, this.returnClass, this.returnParameterMembers);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 774 */     ((Initializable)this.responseDeserializer).initialize(this.typeRegistry);
/*     */   }
/*     */   
/*     */   protected JAXRPCDeserializer getFaultDeserializer() {
/* 778 */     return faultDeserializer;
/*     */   }
/*     */   
/*     */   protected String getOperationStyle() {
/* 782 */     return (String)getRequiredProperty("javax.xml.rpc.soap.operation.style");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DynamicInvocationException serializerNotFoundException(int index, QName name, Class clazz, QName xmlType) {
/* 790 */     Integer indexObject = new Integer(index);
/* 791 */     if (clazz == null) {
/* 792 */       if (xmlType == null) {
/* 793 */         return new DynamicInvocationException("dii.parameter.type.underspecified", new Object[] { indexObject, name });
/*     */       }
/*     */       
/* 796 */       return new DynamicInvocationException("dii.parameter.type.ambiguous.no.class", new Object[] { indexObject, name, xmlType });
/*     */     } 
/* 798 */     if (xmlType == null) {
/* 799 */       return new DynamicInvocationException("dii.parameter.type.ambiguous.no.typename", new Object[] { indexObject, name, clazz });
/*     */     }
/*     */     
/* 802 */     return new DynamicInvocationException("dii.parameter.type.unknown", new Object[] { indexObject, name, clazz, xmlType });
/*     */   }
/*     */ 
/*     */   
/*     */   protected DynamicInvocationException propertyNotFoundException(String property) {
/* 807 */     return new DynamicInvocationException("dii.required.property.not.set", new Object[] { property });
/*     */   }
/*     */ 
/*     */   
/*     */   protected Collection collectPackages(Object[] params) {
/* 812 */     if (this.packages != null) {
/* 813 */       this.packages.clear();
/*     */     }
/* 815 */     Collection<String> packages = new ArrayList();
/* 816 */     if (params == null)
/* 817 */       return packages; 
/* 818 */     for (int i = 0; i < params.length; i++) {
/* 819 */       if (params[i] == null)
/* 820 */         return null; 
/* 821 */       Class<?> clazz = params[i].getClass();
/* 822 */       Package pack = null;
/* 823 */       if (clazz != null) {
/* 824 */         pack = clazz.getPackage();
/*     */       }
/* 826 */       if (pack != null) {
/* 827 */         breakPackageDown(pack.getName(), packages);
/* 828 */         packages.add(pack.getName());
/*     */       } 
/*     */     } 
/* 831 */     return packages;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List validateParameters(List parameterClasses, String[] classNames, Collection packages) {
/* 837 */     if (packages == null) return parameterClasses; 
/* 838 */     Iterator<Class<?>> iter = parameterClasses.iterator();
/* 839 */     int i = 0;
/* 840 */     while (iter.hasNext()) {
/* 841 */       Class pclass = iter.next();
/* 842 */       pclass = validateClassPackage(pclass, null, packages);
/*     */     } 
/*     */     
/* 845 */     return parameterClasses;
/*     */   }
/*     */ 
/*     */   
/*     */   private Class validateReturnClass(Class returnClass, String returnClassName, Collection packages) {
/* 850 */     if (returnClass == null)
/* 851 */       returnClass = getClassForName(returnClassName, packages); 
/* 852 */     return validateClassPackage(returnClass, returnClassName, packages);
/*     */   }
/*     */   
/*     */   protected Class getClassForName(String name, Collection packages) {
/* 856 */     if (name == null) {
/* 857 */       return null;
/*     */     }
/*     */     try {
/* 860 */       return Class.forName(name);
/* 861 */     } catch (ClassNotFoundException cnfe) {
/*     */       
/* 863 */       if (packages == null)
/* 864 */         return null; 
/* 865 */       Iterator<String> iter = packages.iterator();
/* 866 */       while (iter.hasNext()) {
/* 867 */         String qualifiedName = (String)iter.next() + "." + name;
/*     */         try {
/* 869 */           return Class.forName(qualifiedName);
/* 870 */         } catch (ClassNotFoundException cfe) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 875 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Class validateClassPackage(Class returnClass, String classname, Collection packages) {
/* 880 */     if (packages == null)
/* 881 */       return returnClass; 
/* 882 */     if (returnClass != null) {
/* 883 */       Package clPack = null;
/*     */       try {
/* 885 */         clPack = returnClass.getPackage();
/* 886 */       } catch (NullPointerException npe) {
/* 887 */         return returnClass;
/*     */       } 
/* 889 */       if (clPack == null)
/* 890 */         return returnClass; 
/* 891 */       String packName = clPack.getName();
/* 892 */       if (packName == null) {
/* 893 */         return returnClass;
/*     */       }
/* 895 */       Iterator<String> piter = packages.iterator();
/* 896 */       boolean found = false;
/* 897 */       while (piter.hasNext()) {
/* 898 */         if (packName.equals(piter.next())) {
/* 899 */           found = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 903 */       if (found) {
/* 904 */         return returnClass;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 910 */       Class newClass = getClassForName(classname, packages);
/* 911 */       if (newClass != null) {
/* 912 */         return newClass;
/*     */       }
/* 914 */       return returnClass;
/*     */     } 
/*     */     
/* 917 */     return returnClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void breakPackageDown(String packName, Collection<String> packages) {
/* 923 */     int idx = 0;
/* 924 */     while (idx != -1) {
/* 925 */       idx = packName.lastIndexOf(".");
/* 926 */       String newPack = null;
/* 927 */       if (idx != -1) {
/* 928 */         newPack = packName.substring(0, idx);
/*     */       } else {
/*     */         return;
/* 931 */       }  packages.add(newPack);
/* 932 */       packName = newPack;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\BasicCall.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */