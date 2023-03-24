/*      */ package com.sun.xml.rpc.encoding;
/*      */ 
/*      */ import com.sun.xml.rpc.client.dii.BasicCall;
/*      */ import com.sun.xml.rpc.client.dii.OperationInfo;
/*      */ import com.sun.xml.rpc.client.dii.ParameterMemberInfo;
/*      */ import com.sun.xml.rpc.encoding.literal.LiteralArraySerializer;
/*      */ import com.sun.xml.rpc.encoding.literal.LiteralFragmentSerializer;
/*      */ import com.sun.xml.rpc.encoding.literal.LiteralObjectArraySerializer;
/*      */ import com.sun.xml.rpc.encoding.literal.LiteralSimpleTypeSerializer;
/*      */ import com.sun.xml.rpc.encoding.literal.ValueTypeLiteralSerializer;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDIntEncoder;
/*      */ import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
/*      */ import com.sun.xml.rpc.encoding.soap.SOAP12Constants;
/*      */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*      */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*      */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*      */ import java.beans.Introspector;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.rmi.Remote;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.rpc.encoding.Deserializer;
/*      */ import javax.xml.rpc.encoding.DeserializerFactory;
/*      */ import javax.xml.rpc.encoding.Serializer;
/*      */ import javax.xml.rpc.encoding.SerializerFactory;
/*      */ import javax.xml.soap.SOAPElement;
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
/*      */ public class DynamicInternalTypeMappingRegistry
/*      */   implements InternalTypeMappingRegistry, SerializerConstants
/*      */ {
/*   77 */   protected static final QName ELEMENT_NAME = null;
/*      */   
/*   79 */   protected static String DEFAULT_OPERATION_STYLE = "document";
/*   80 */   protected InternalTypeMappingRegistry registry = null;
/*      */   
/*   82 */   private TypeMappingImpl cachedEncodedMappings = new TypeMappingImpl();
/*   83 */   private TypeMappingImpl cachedLiteralMappings = new TypeMappingImpl();
/*   84 */   private HashMap qnameToJavaClass = new HashMap<Object, Object>();
/*   85 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*      */   
/*      */   private String operationStyle;
/*      */   private BasicCall currentCall;
/*   89 */   private HashMap dynamicMemberRegistry = new HashMap<Object, Object>();
/*      */ 
/*      */   
/*      */   public void setStyles(String operationStyle) {
/*   93 */     this.operationStyle = operationStyle;
/*   94 */     if (operationStyle == null)
/*   95 */       operationStyle = DEFAULT_OPERATION_STYLE; 
/*      */   }
/*      */   
/*      */   public HashMap getDynamicMemberRegistry() {
/*   99 */     return this.dynamicMemberRegistry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDynamicRegistryMembers(Class parentClass, QName parentXmlType, String encoding, ParameterMemberInfo[] memberInfo) {
/*  106 */     String key = makeKey(parentClass, parentXmlType, encoding);
/*      */     
/*  108 */     this.dynamicMemberRegistry.put(key, memberInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ParameterMemberInfo[] getDynamicRegistryMembers(Class parentClass, QName parentXmlType, String encoding) {
/*  114 */     String key = makeKey(parentClass, parentXmlType, encoding);
/*  115 */     return (ParameterMemberInfo[])this.dynamicMemberRegistry.get(key);
/*      */   }
/*      */   
/*      */   private String makeKey(Class parentClass, QName parentXmlType, String encoding) {
/*  119 */     String pname = (parentClass != null) ? parentClass.getName() : "";
/*  120 */     String pxml = (parentXmlType != null) ? parentXmlType.getLocalPart() : "";
/*  121 */     return new String(pname + pxml + encoding);
/*      */   }
/*      */   
/*      */   public String getStyle() {
/*  125 */     return this.operationStyle;
/*      */   }
/*      */   
/*      */   private void init(SOAPVersion ver) {
/*  129 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DynamicInternalTypeMappingRegistry(InternalTypeMappingRegistry registry, BasicCall currentCall) {
/*  137 */     this(registry, currentCall, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */   
/*      */   public DynamicInternalTypeMappingRegistry(InternalTypeMappingRegistry registry) {
/*  142 */     this(registry, null, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DynamicInternalTypeMappingRegistry(InternalTypeMappingRegistry registry, BasicCall currentCall, SOAPVersion ver) {
/*  150 */     init(ver);
/*  151 */     if (registry == null) {
/*  152 */       throw new IllegalArgumentException("registry must not be null");
/*      */     }
/*  154 */     this.currentCall = currentCall;
/*  155 */     this.registry = registry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializer getSerializer(String encoding, Class javaType, QName xmlType) throws Exception {
/*      */     try {
/*  165 */       return this.registry.getSerializer(encoding, javaType, xmlType);
/*  166 */     } catch (TypeMappingException ex) {
/*      */       try {
/*  168 */         if (encoding.equals(this.soapEncodingConstants.getSOAPEncodingNamespace())) {
/*      */           
/*  170 */           Serializer serializer = getCachedEncodedSerializer(javaType, xmlType);
/*      */           
/*  172 */           if (serializer != null) {
/*  173 */             return serializer;
/*      */           }
/*  175 */           if (isArray(javaType, xmlType)) {
/*  176 */             return createArraySerializer(javaType, xmlType);
/*      */           }
/*      */           
/*  179 */           return createValueSerializer(javaType, xmlType);
/*      */         } 
/*  181 */         if (encoding.equals("") && "document".equals(this.operationStyle)) {
/*      */           
/*  183 */           Serializer serializer = getCachedLiteralSerializer(javaType, xmlType);
/*      */           
/*  185 */           if (serializer != null) {
/*  186 */             return serializer;
/*      */           }
/*  188 */           if (isLiteralArray(javaType, xmlType)) {
/*  189 */             return createLiteralArraySerializer(javaType, xmlType);
/*      */           }
/*  191 */           return createLiteralValueTypeSerializer(xmlType, javaType);
/*      */         } 
/*      */ 
/*      */         
/*  195 */         if (encoding.equals("") && "rpc".equals(this.operationStyle))
/*      */         {
/*  197 */           Serializer serializer = getCachedLiteralSerializer(javaType, xmlType);
/*      */           
/*  199 */           if (serializer != null) {
/*  200 */             return serializer;
/*      */           }
/*  202 */           if (isLiteralArray(javaType, xmlType)) {
/*  203 */             return createRPCLiteralArraySerializer(javaType, xmlType);
/*      */           }
/*      */ 
/*      */           
/*  207 */           return createLiteralValueTypeSerializer(xmlType, javaType);
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  212 */       catch (JAXRPCExceptionBase e) {
/*  213 */         throw new SerializationException(e);
/*  214 */       } catch (Exception e) {
/*  215 */         throw new SerializationException(new LocalizableExceptionAdapter(e));
/*      */       } 
/*      */ 
/*      */       
/*  219 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Deserializer getDeserializer(String encoding, Class javaType, QName xmlType) throws Exception {
/*      */     try {
/*  229 */       return this.registry.getDeserializer(encoding, javaType, xmlType);
/*  230 */     } catch (TypeMappingException ex) {
/*      */       try {
/*  232 */         if (encoding.equals(this.soapEncodingConstants.getSOAPEncodingNamespace())) {
/*      */           
/*  234 */           Deserializer deserializer = getCachedEncodedDeserializer(javaType, xmlType);
/*      */           
/*  236 */           if (deserializer != null) {
/*  237 */             return deserializer;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*  242 */           if (isArray(javaType, xmlType)) {
/*  243 */             return createArraySerializer(javaType, xmlType);
/*      */           }
/*  245 */           return createValueSerializer(javaType, xmlType);
/*      */         } 
/*  247 */         if (encoding.equals("") && "document".equals(this.operationStyle)) {
/*      */           
/*  249 */           Deserializer deserializer = getCachedLiteralDeserializer(javaType, xmlType);
/*      */           
/*  251 */           if (deserializer != null) {
/*  252 */             return deserializer;
/*      */           }
/*  254 */           if (isLiteralArray(javaType, xmlType)) {
/*  255 */             return (Deserializer)createLiteralArraySerializer(javaType, xmlType);
/*      */           }
/*      */ 
/*      */           
/*  259 */           return (Deserializer)createLiteralValueTypeSerializer(xmlType, javaType);
/*      */         } 
/*      */ 
/*      */         
/*  263 */         if (encoding.equals("") && "rpc".equals(this.operationStyle)) {
/*      */           
/*  265 */           Deserializer deserializer = getCachedLiteralDeserializer(javaType, xmlType);
/*      */           
/*  267 */           if (deserializer != null) {
/*  268 */             return deserializer;
/*      */           }
/*  270 */           if (isLiteralArray(javaType, xmlType)) {
/*  271 */             return (Deserializer)createRPCLiteralArraySerializer(javaType, xmlType);
/*      */           }
/*      */ 
/*      */           
/*  275 */           return (Deserializer)createLiteralValueTypeSerializer(xmlType, javaType);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  280 */         throw ex;
/*  281 */       } catch (JAXRPCExceptionBase e) {
/*  282 */         throw new SerializationException(e);
/*  283 */       } catch (Exception e) {
/*  284 */         throw new SerializationException(new LocalizableExceptionAdapter(e));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Serializer getCachedEncodedSerializer(Class javaType, QName xmlType) {
/*  294 */     SerializerFactory serializerFactory = this.cachedEncodedMappings.getSerializer(javaType, xmlType);
/*      */     
/*  296 */     if (serializerFactory != null) {
/*  297 */       return serializerFactory.getSerializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*      */     }
/*      */     
/*  300 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Deserializer getCachedEncodedDeserializer(Class javaType, QName xmlType) {
/*  307 */     DeserializerFactory deserializerFactory = this.cachedEncodedMappings.getDeserializer(javaType, xmlType);
/*      */     
/*  309 */     if (deserializerFactory != null) {
/*  310 */       return deserializerFactory.getDeserializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*      */     }
/*      */     
/*  313 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Serializer getCachedLiteralSerializer(Class javaType, QName xmlType) {
/*  320 */     SerializerFactory serializerFactory = this.cachedLiteralMappings.getSerializer(javaType, xmlType);
/*      */     
/*  322 */     if (serializerFactory != null) {
/*  323 */       return serializerFactory.getSerializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*      */     }
/*      */     
/*  326 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Deserializer getCachedLiteralDeserializer(Class javaType, QName xmlType) {
/*  333 */     DeserializerFactory deserializerFactory = this.cachedLiteralMappings.getDeserializer(javaType, xmlType);
/*      */     
/*  335 */     if (deserializerFactory != null) {
/*  336 */       return deserializerFactory.getDeserializerAs("http://java.sun.com/jax-rpc-ri/1.0/streaming/");
/*      */     }
/*      */     
/*  339 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReferenceableSerializerImpl createArraySerializer(Class javaType, QName xmlType) throws Exception {
/*  347 */     if (javaType == null || xmlType == null) {
/*  348 */       return null;
/*      */     }
/*      */     
/*  351 */     Class<?> elementType = javaType.getComponentType();
/*  352 */     String elementName = null;
/*  353 */     if (elementType != null) {
/*  354 */       elementName = elementType.getName();
/*  355 */       int idx = elementName.lastIndexOf(".");
/*  356 */       if (idx != -1) {
/*  357 */         elementName = elementName.substring(idx + 1, elementName.length()).toLowerCase();
/*      */       }
/*      */     } 
/*      */     
/*  361 */     Serializer componentSerializer = null;
/*      */     try {
/*  363 */       componentSerializer = this.registry.getSerializer(this.soapEncodingConstants.getURIEncoding(), elementType);
/*      */ 
/*      */     
/*      */     }
/*  367 */     catch (TypeMappingException ex) {}
/*      */ 
/*      */     
/*  370 */     ObjectArraySerializer objectArraySerializer = null;
/*  371 */     ReferenceableSerializerImpl serializer = null;
/*  372 */     if (componentSerializer != null) {
/*  373 */       objectArraySerializer = new ObjectArraySerializer(xmlType, true, true, "http://schemas.xmlsoap.org/soap/encoding/", ELEMENT_NAME, new QName("http://www.w3.org/2001/XMLSchema", elementName), elementType, 1, null);
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
/*  384 */       serializer = new ReferenceableSerializerImpl(false, objectArraySerializer);
/*      */ 
/*      */ 
/*      */       
/*  388 */       this.cachedEncodedMappings.register(javaType, this.soapEncodingConstants.getQNameEncodingArray(), new SingletonSerializerFactory(objectArraySerializer), new SingletonDeserializerFactory(objectArraySerializer));
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  394 */       serializer = new ReferenceableSerializerImpl(false, new PolymorphicArraySerializer(xmlType, false, true, this.soapEncodingConstants.getURIEncoding(), ELEMENT_NAME));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  403 */       this.cachedEncodedMappings.register(javaType, xmlType, new SingletonSerializerFactory(serializer), new SingletonDeserializerFactory(serializer));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  409 */     serializer.initialize(this);
/*      */     
/*  411 */     return serializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Serializer createRPCLiteralArraySerializer(Class javaType, QName xmlType) throws Exception {
/*      */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer;
/*      */     LiteralObjectArraySerializer literalObjectArraySerializer;
/*  419 */     if (javaType == null || xmlType == null) {
/*  420 */       return null;
/*      */     }
/*      */     
/*  423 */     if (javaType.isArray()) {
/*  424 */       QName foundXmlType = checkParameterXmlTypesUsingModels(xmlType);
/*  425 */       if (foundXmlType != null) {
/*  426 */         xmlType = foundXmlType;
/*      */       }
/*      */     } 
/*  429 */     ParameterMemberInfo[] pmemberInfos = getDynamicRegistryMembers(javaType, xmlType, "");
/*      */ 
/*      */ 
/*      */     
/*  433 */     int size = 0;
/*  434 */     if (pmemberInfos != null)
/*  435 */       size = pmemberInfos.length; 
/*  436 */     String pmname = null;
/*  437 */     QName pmXmlType = null;
/*  438 */     Class<?> pmClass = null;
/*  439 */     if (size > 0) {
/*  440 */       ParameterMemberInfo pinfo = pmemberInfos[0];
/*  441 */       pmname = pinfo.getMemberName();
/*  442 */       pmXmlType = pinfo.getMemberXmlType();
/*  443 */       pmClass = pinfo.getMemberJavaClass();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  449 */     Class<?> elementType = javaType.getComponentType();
/*  450 */     String elementName = null;
/*  451 */     if (elementType != null) {
/*  452 */       elementName = elementType.getName();
/*  453 */       int idx = elementName.lastIndexOf(".");
/*  454 */       if (idx != -1) {
/*  455 */         elementName = elementName.substring(idx + 1, elementName.length()).toLowerCase();
/*      */ 
/*      */ 
/*      */         
/*  459 */         idx = elementName.indexOf("big");
/*  460 */         if (idx != -1) {
/*  461 */           elementName = elementName.substring(idx + 3, elementName.length());
/*      */         }
/*      */         
/*  464 */         if (elementName.equals("calendar")) {
/*  465 */           elementName = "dateTime";
/*      */         }
/*      */       } 
/*      */     } 
/*  469 */     QName componentQName = null;
/*  470 */     if (elementType == pmClass && 
/*  471 */       pmXmlType != null) {
/*  472 */       componentQName = pmXmlType;
/*      */     }
/*      */ 
/*      */     
/*  476 */     componentQName = new QName("http://www.w3.org/2001/XMLSchema", elementName);
/*      */     
/*  478 */     JAXRPCSerializer componentSerializer = null;
/*      */     try {
/*  480 */       componentSerializer = (JAXRPCSerializer)this.registry.getSerializer("", elementType, componentQName);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  485 */     catch (TypeMappingException ex) {}
/*      */ 
/*      */     
/*  488 */     JAXRPCSerializer ArraySerializer = null;
/*      */     
/*  490 */     if (componentSerializer != null && componentSerializer instanceof LiteralSimpleTypeSerializer) {
/*      */ 
/*      */       
/*  493 */       literalSimpleTypeSerializer = new LiteralSimpleTypeSerializer(xmlType, "", ((LiteralSimpleTypeSerializer)componentSerializer).getEncoder());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  500 */       LiteralArraySerializer literalArraySerializer = new LiteralArraySerializer(xmlType, true, "", false, javaType, (JAXRPCSerializer)literalSimpleTypeSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  509 */       this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalArraySerializer), new SingletonDeserializerFactory((Deserializer)literalArraySerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  516 */     else if (literalSimpleTypeSerializer != null) {
/*      */       
/*  518 */       literalObjectArraySerializer = new LiteralObjectArraySerializer(xmlType, true, "", false, javaType, (JAXRPCSerializer)literalSimpleTypeSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  527 */       this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalObjectArraySerializer), new SingletonDeserializerFactory((Deserializer)literalObjectArraySerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  538 */       QName foundQName = checkParameterXmlTypesUsingModels(componentQName);
/*      */       
/*  540 */       if (foundQName != null) {
/*  541 */         componentQName = foundQName;
/*      */       }
/*  543 */       if (!isLiteralArray(elementType, componentQName)) {
/*      */         
/*  545 */         JAXRPCSerializer jAXRPCSerializer = (JAXRPCSerializer)createLiteralValueTypeSerializer(xmlType, elementType);
/*      */ 
/*      */         
/*  548 */         literalObjectArraySerializer = new LiteralObjectArraySerializer(xmlType, true, "", false, javaType, jAXRPCSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  557 */         this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalObjectArraySerializer), new SingletonDeserializerFactory((Deserializer)literalObjectArraySerializer));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  566 */       if (literalObjectArraySerializer instanceof LiteralArraySerializer) {
/*  567 */         ((LiteralArraySerializer)literalObjectArraySerializer).initialize(this);
/*      */       } else {
/*  569 */         literalObjectArraySerializer.initialize(this);
/*      */       } 
/*  571 */     } catch (ClassCastException ce) {
/*  572 */       System.out.println("literal ArraySerializer.initialize" + ce.getMessage());
/*      */       
/*  574 */       ce.printStackTrace();
/*      */     } 
/*  576 */     return (Serializer)literalObjectArraySerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Serializer createLiteralArraySerializer(Class javaType, QName xmlType) throws Exception {
/*      */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer;
/*      */     LiteralObjectArraySerializer literalObjectArraySerializer;
/*  584 */     if (javaType == null || xmlType == null) {
/*  585 */       return null;
/*      */     }
/*      */     
/*  588 */     LiteralType type = getParameterLiteralType(xmlType.getLocalPart());
/*  589 */     if (type != null && type instanceof com.sun.xml.rpc.processor.model.literal.LiteralListType) {
/*  590 */       return createLiteralListSerializer(javaType, xmlType);
/*      */     }
/*  592 */     if (javaType.isArray()) {
/*  593 */       QName foundXmlType = checkParameterXmlTypesUsingModels(xmlType);
/*  594 */       if (foundXmlType != null) {
/*  595 */         xmlType = foundXmlType;
/*      */       }
/*      */     } 
/*      */     
/*  599 */     Class<?> elementType = javaType.getComponentType();
/*  600 */     String elementName = null;
/*  601 */     if (elementType != null) {
/*  602 */       elementName = elementType.getName();
/*  603 */       int idx = elementName.lastIndexOf(".");
/*  604 */       if (idx != -1) {
/*  605 */         elementName = elementName.substring(idx + 1, elementName.length()).toLowerCase();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  610 */     QName componentQName = new QName("http://www.w3.org/2001/XMLSchema", elementName);
/*      */     
/*  612 */     JAXRPCSerializer componentSerializer = null;
/*      */     try {
/*  614 */       componentSerializer = (JAXRPCSerializer)this.registry.getSerializer("", elementType, componentQName);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  619 */     catch (TypeMappingException ex) {}
/*      */ 
/*      */     
/*  622 */     JAXRPCSerializer ArraySerializer = null;
/*      */     
/*  624 */     if (componentSerializer != null && componentSerializer instanceof LiteralSimpleTypeSerializer) {
/*      */ 
/*      */       
/*  627 */       literalSimpleTypeSerializer = new LiteralSimpleTypeSerializer(xmlType, "", ((LiteralSimpleTypeSerializer)componentSerializer).getEncoder());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  634 */       LiteralArraySerializer literalArraySerializer = new LiteralArraySerializer(xmlType, true, "", false, javaType, (JAXRPCSerializer)literalSimpleTypeSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  643 */       this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalArraySerializer), new SingletonDeserializerFactory((Deserializer)literalArraySerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  650 */     else if (literalSimpleTypeSerializer != null) {
/*      */       
/*  652 */       literalObjectArraySerializer = new LiteralObjectArraySerializer(xmlType, true, "", false, javaType, (JAXRPCSerializer)literalSimpleTypeSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  661 */       this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalObjectArraySerializer), new SingletonDeserializerFactory((Deserializer)literalObjectArraySerializer));
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  671 */       QName foundQName = checkParameterXmlTypesUsingModels(componentQName);
/*      */       
/*  673 */       if (foundQName != null) {
/*  674 */         componentQName = foundQName;
/*      */       }
/*  676 */       if (!isLiteralArray(elementType, componentQName)) {
/*      */         
/*  678 */         JAXRPCSerializer jAXRPCSerializer = (JAXRPCSerializer)createLiteralValueTypeSerializer(xmlType, elementType);
/*      */ 
/*      */         
/*  681 */         literalObjectArraySerializer = new LiteralObjectArraySerializer(xmlType, true, "", false, javaType, jAXRPCSerializer, elementType);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  690 */         this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalObjectArraySerializer), new SingletonDeserializerFactory((Deserializer)literalObjectArraySerializer));
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  700 */       if (literalObjectArraySerializer instanceof LiteralArraySerializer) {
/*  701 */         ((LiteralArraySerializer)literalObjectArraySerializer).initialize(this);
/*      */       } else {
/*  703 */         literalObjectArraySerializer.initialize(this);
/*      */       } 
/*  705 */     } catch (ClassCastException ce) {
/*  706 */       System.out.println("literal ArraySerializer.initialize" + ce.getMessage());
/*      */       
/*  708 */       ce.printStackTrace();
/*      */     } 
/*  710 */     return (Serializer)literalObjectArraySerializer;
/*      */   }
/*      */   
/*      */   Serializer createLiteralListSerializer(Class javaType, QName xmlType) {
/*  714 */     QName type = xmlType;
/*      */     
/*  716 */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer = new LiteralSimpleTypeSerializer(type, "", XSDListTypeEncoder.getInstance(XSDIntEncoder.getInstance(), int.class));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  723 */     this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalSimpleTypeSerializer), new SingletonDeserializerFactory((Deserializer)literalSimpleTypeSerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  729 */     return (Serializer)literalSimpleTypeSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ReferenceableSerializerImpl createValueSerializer(Class javaType, QName xmlType) throws Exception {
/*  738 */     if (javaType == null || xmlType == null) {
/*  739 */       return null;
/*      */     }
/*      */     
/*  742 */     ReferenceableSerializerImpl serializer = new ReferenceableSerializerImpl(true, new ValueTypeSerializer(xmlType, false, true, this.soapEncodingConstants.getURIEncoding(), javaType));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  751 */     this.cachedEncodedMappings.register(javaType, xmlType, new SingletonSerializerFactory(serializer), new SingletonDeserializerFactory(serializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  757 */     serializer.initialize(this);
/*      */     
/*  759 */     return serializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Serializer createLiteralValueTypeSerializer(QName xmlType, Class<?> javaType) throws Exception {
/*  767 */     if (javaType != null && SOAPElement.class.isAssignableFrom(javaType))
/*      */     {
/*  769 */       return SOAPElementLiteralSerializer(xmlType, javaType);
/*      */     }
/*      */     
/*  772 */     JAXRPCSerializer stdSerializer = getStandardSerializer("", javaType, xmlType);
/*      */     
/*  774 */     if (stdSerializer != null) {
/*  775 */       this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory(stdSerializer), new SingletonDeserializerFactory((JAXRPCDeserializer)stdSerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  781 */       return stdSerializer;
/*      */     } 
/*      */     
/*  784 */     Collection params = orderCurrentMembersPerWsdl(xmlType, javaType);
/*  785 */     ValueTypeLiteralSerializer serializer = new ValueTypeLiteralSerializer(xmlType, false, true, "", javaType, params);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  793 */     this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)serializer), new SingletonDeserializerFactory((Deserializer)serializer));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  799 */     serializer.initialize(this);
/*      */     
/*  801 */     return (Serializer)serializer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializer SOAPElementLiteralSerializer(QName xmlType, Class javaType) {
/*  808 */     LiteralFragmentSerializer literalFragmentSerializer = new LiteralFragmentSerializer(xmlType, false, "");
/*      */     
/*  810 */     this.cachedLiteralMappings.register(javaType, xmlType, new SingletonSerializerFactory((Serializer)literalFragmentSerializer), new SingletonDeserializerFactory((Deserializer)literalFragmentSerializer));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     return (Serializer)literalFragmentSerializer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializer getSerializer(String encoding, Class javaType) throws Exception {
/*  821 */     return this.registry.getSerializer(encoding, javaType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Serializer getSerializer(String encoding, QName xmlType) throws Exception {
/*  827 */     return this.registry.getSerializer(encoding, xmlType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Deserializer getDeserializer(String encoding, QName xmlType) throws Exception {
/*  833 */     return this.registry.getDeserializer(encoding, xmlType);
/*      */   }
/*      */   
/*      */   public Class getJavaType(String encoding, QName xmlType) throws Exception {
/*  837 */     return this.registry.getJavaType(encoding, xmlType);
/*      */   }
/*      */   
/*      */   public QName getXmlType(String encoding, Class javaType) throws Exception {
/*  841 */     return this.registry.getXmlType(encoding, javaType);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isArray(Class javaType, QName xmlType) {
/*  846 */     return isArray(javaType, xmlType, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isLiteralArray(Class javaType, QName xmlType) {
/*  851 */     return isLiteralArray(javaType, xmlType, SOAPVersion.SOAP_11);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isArray(Class javaType, QName xmlType, SOAPVersion ver) {
/*  859 */     if (javaType == null) {
/*  860 */       return false;
/*      */     }
/*  862 */     QName encArray = SOAPConstants.QNAME_ENCODING_ARRAY;
/*      */ 
/*      */     
/*  865 */     if (ver == SOAPVersion.SOAP_12) {
/*  866 */       encArray = SOAP12Constants.QNAME_ENCODING_ARRAY;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  875 */     boolean isArray = javaType.isArray();
/*  876 */     boolean isSame = false;
/*  877 */     if (xmlType != null) {
/*  878 */       if (xmlType.getLocalPart().indexOf(encArray.getLocalPart()) != -1)
/*      */       {
/*  880 */         isSame = true; } 
/*  881 */       return (isArray && isSame);
/*      */     } 
/*  883 */     return isArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isLiteralArray(Class javaType, QName xmlType, SOAPVersion ver) {
/*  891 */     if (javaType == null)
/*  892 */       return false; 
/*  893 */     return javaType.isArray();
/*      */   }
/*      */   
/*      */   public static boolean isValueType(Class<?> javaType) throws Exception {
/*  897 */     if (javaType == null || Remote.class.isAssignableFrom(javaType))
/*      */     {
/*  899 */       return false;
/*      */     }
/*      */     
/*  902 */     boolean hasPublicConstructor = false;
/*  903 */     Constructor[] constructors = (Constructor[])javaType.getConstructors();
/*  904 */     for (int i = 0; i < constructors.length; i++) {
/*  905 */       if ((constructors[i].getParameterTypes()).length == 0) {
/*  906 */         hasPublicConstructor = true;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  911 */     if (!hasPublicConstructor) {
/*  912 */       return false;
/*      */     }
/*      */     
/*  915 */     boolean hasPropertiesOrPublicFields = false;
/*  916 */     if ((Introspector.getBeanInfo(javaType).getPropertyDescriptors()).length == 0) {
/*      */       
/*  918 */       Field[] fields = javaType.getFields();
/*  919 */       for (int j = 0; j < fields.length; j++) {
/*  920 */         Field currentField = fields[j];
/*      */         
/*  922 */         int fieldModifiers = currentField.getModifiers();
/*  923 */         if (Modifier.isPublic(fieldModifiers))
/*      */         {
/*      */           
/*  926 */           if (!Modifier.isTransient(fieldModifiers))
/*      */           {
/*      */             
/*  929 */             if (!Modifier.isFinal(fieldModifiers)) {
/*      */ 
/*      */ 
/*      */               
/*  933 */               hasPropertiesOrPublicFields = true; break;
/*      */             }  }  } 
/*      */       } 
/*      */     } else {
/*  937 */       hasPropertiesOrPublicFields = true;
/*      */     } 
/*      */     
/*  940 */     if (!hasPropertiesOrPublicFields) {
/*  941 */       return false;
/*      */     }
/*      */     
/*  944 */     return true;
/*      */   }
/*      */   
/*      */   private Collection getCurrentOperationParameterModels() {
/*  948 */     if (this.currentCall != null) {
/*  949 */       OperationInfo info = this.currentCall.getOperationInfo();
/*  950 */       if (info != null)
/*  951 */         return info.getParameterModels(); 
/*      */     } 
/*  953 */     return new ArrayList();
/*      */   }
/*      */ 
/*      */   
/*      */   private QName checkParameterXmlTypesUsingModels(QName suppliedXmlType) {
/*  958 */     Collection parameterModels = getCurrentOperationParameterModels();
/*  959 */     if (parameterModels == null)
/*  960 */       return null; 
/*  961 */     Iterator iter = parameterModels.iterator();
/*  962 */     return recursiveCheck(iter, suppliedXmlType);
/*      */   }
/*      */ 
/*      */   
/*      */   private QName recursiveCheck(Iterator<LiteralElementMember> iter, QName suppliedQName) {
/*  967 */     if (iter.hasNext()) {
/*  968 */       LiteralElementMember type = iter.next();
/*  969 */       QName qname = type.getName();
/*  970 */       if (qname.getLocalPart().equalsIgnoreCase(suppliedQName.getLocalPart()))
/*      */       {
/*      */         
/*  973 */         return qname;
/*      */       }
/*      */       
/*  976 */       LiteralType literalType = type.getType();
/*  977 */       qname = literalType.getName();
/*  978 */       if (qname.getLocalPart().equalsIgnoreCase(suppliedQName.getLocalPart()))
/*      */       {
/*      */         
/*  981 */         return qname;
/*      */       }
/*  983 */       if (literalType instanceof LiteralSequenceType || literalType instanceof LiteralAllType) {
/*      */         
/*  985 */         Iterator members = ((LiteralSequenceType)literalType).getElementMembers();
/*      */         
/*  987 */         return recursiveCheck(members, suppliedQName);
/*      */       } 
/*      */     } 
/*      */     
/*  991 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Collection orderCurrentMembersPerWsdl(QName suppliedQName, Class javaType) {
/*  999 */     if (javaType == null)
/* 1000 */       return null; 
/* 1001 */     String className = javaType.getName();
/* 1002 */     Collection<String> names = new ArrayList();
/* 1003 */     Collection parameterModels = getCurrentOperationParameterModels();
/* 1004 */     if (parameterModels == null)
/* 1005 */       return null; 
/* 1006 */     Iterator iter = parameterModels.iterator();
/* 1007 */     while (iter.hasNext()) {
/* 1008 */       Object model = iter.next();
/* 1009 */       if (model instanceof LiteralElementMember) {
/* 1010 */         QName qname = ((LiteralElementMember)model).getName();
/* 1011 */         if (suppliedQName != null && 
/* 1012 */           !qname.getLocalPart().equalsIgnoreCase(suppliedQName.getLocalPart())) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1017 */         this.qnameToJavaClass.put(qname, className);
/*      */         
/* 1019 */         LiteralType literalType = ((LiteralElementMember)model).getType();
/*      */         
/* 1021 */         if (literalType instanceof LiteralSequenceType || literalType instanceof LiteralAllType) {
/*      */           
/* 1023 */           qname = literalType.getName();
/*      */           
/* 1025 */           Iterator<LiteralElementMember> elements = null;
/* 1026 */           if (literalType instanceof LiteralSequenceType) {
/* 1027 */             elements = ((LiteralSequenceType)literalType).getElementMembers();
/*      */           }
/*      */           
/* 1030 */           if (literalType instanceof LiteralAllType) {
/* 1031 */             elements = ((LiteralAllType)literalType).getElementMembers();
/*      */           }
/* 1033 */           while (elements.hasNext()) {
/* 1034 */             LiteralElementMember member = elements.next();
/*      */             
/* 1036 */             if (member.getType() instanceof LiteralSequenceType) {
/*      */               
/* 1038 */               JavaStructureMember structureMember = member.getJavaStructureMember();
/*      */               
/* 1040 */               String javaName = structureMember.getType().getName();
/*      */ 
/*      */               
/* 1043 */               this.qnameToJavaClass.put(qname, javaName);
/*      */             } 
/* 1045 */             String name = member.getName().getLocalPart();
/* 1046 */             names.add(name);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1051 */     return names;
/*      */   }
/*      */   
/*      */   LiteralType getParameterLiteralType(String parameterName) {
/* 1055 */     Collection parameterModels = getCurrentOperationParameterModels();
/* 1056 */     Iterator modelsIter = parameterModels.iterator();
/* 1057 */     while (modelsIter.hasNext()) {
/* 1058 */       Object model = modelsIter.next();
/* 1059 */       if (model instanceof LiteralElementMember) {
/*      */         
/* 1061 */         QName name = ((LiteralElementMember)model).getName();
/* 1062 */         if (name.getLocalPart().equalsIgnoreCase(parameterName)) {
/*      */           
/* 1064 */           LiteralType type = ((LiteralElementMember)model).getType();
/* 1065 */           return type;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1070 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JAXRPCSerializer getStandardSerializer(String encoding, Class javaType, QName xmlType) throws TypeMappingException, Exception {
/* 1079 */     Serializer serializer = null;
/*      */     try {
/* 1081 */       return (JAXRPCSerializer)this.registry.getSerializer(encoding, javaType, xmlType);
/*      */ 
/*      */     
/*      */     }
/* 1085 */     catch (Exception e) {
/*      */       try {
/* 1087 */         return (JAXRPCSerializer)this.registry.getSerializer(encoding, javaType);
/*      */       
/*      */       }
/* 1090 */       catch (Exception ex) {
/*      */         
/* 1092 */         return null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JAXRPCDeserializer getStandardDeserializer(String encoding, Class javaType, QName xmlType) throws TypeMappingException, Exception {
/* 1103 */     Deserializer serializer = null;
/*      */     try {
/* 1105 */       return (JAXRPCDeserializer)this.registry.getDeserializer(encoding, javaType, xmlType);
/*      */ 
/*      */     
/*      */     }
/* 1109 */     catch (Exception e) {
/*      */       try {
/* 1111 */         return (JAXRPCDeserializer)this.registry.getSerializer(encoding, javaType);
/*      */       
/*      */       }
/* 1114 */       catch (Exception ex) {
/*      */         
/* 1116 */         return null;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\DynamicInternalTypeMappingRegistry.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */