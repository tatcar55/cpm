/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.ParameterMemberInfo;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.DynamicInternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.EncodingException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ public class LiteralRequestSerializer
/*     */   extends GenericLiteralObjectSerializer
/*     */   implements Initializable
/*     */ {
/*     */   protected QName[] parameterNames;
/*     */   protected QName[] parameterXmlTypes;
/*     */   protected QName[] parameterXmlTypeQNames;
/*     */   protected Class[] parameterJavaTypes;
/*     */   protected ArrayList parameterMembers;
/*  76 */   protected String operationStyle = "document";
/*     */   
/*     */   protected JAXRPCSerializer[] serializers;
/*     */   
/*     */   protected JAXRPCDeserializer[] deserializers;
/*  81 */   protected InternalTypeMappingRegistry typeRegistry = null;
/*     */   
/*  83 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  87 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, ArrayList parameterMembers) {
/*  96 */     this(type, encodeType, isNullable, "", operationStyle, parameterNames, parameterTypes, parameterClasses, parameterMembers, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, ArrayList parameterMembers, SOAPVersion ver) {
/* 105 */     super(type, encodeType, isNullable, encodingStyle);
/* 106 */     init(ver);
/* 107 */     this.parameterNames = parameterNames;
/* 108 */     this.parameterXmlTypes = parameterTypes;
/* 109 */     this.parameterJavaTypes = parameterClasses;
/* 110 */     this.parameterMembers = parameterMembers;
/* 111 */     this.operationStyle = operationStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterTypes, QName[] parameterXmlTypeQNames, Class[] parameterClasses, ArrayList parameterMembers) {
/* 119 */     this(type, encodeType, isNullable, "", operationStyle, parameterNames, parameterTypes, parameterXmlTypeQNames, parameterClasses, parameterMembers, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterTypes, QName[] parameterXmlTypeQNames, Class[] parameterClasses, ArrayList parameterMembers, SOAPVersion ver) {
/* 128 */     super(type, encodeType, isNullable, encodingStyle);
/* 129 */     init(ver);
/* 130 */     this.parameterNames = parameterNames;
/* 131 */     this.parameterXmlTypes = parameterTypes;
/* 132 */     this.parameterXmlTypeQNames = parameterXmlTypeQNames;
/* 133 */     this.parameterJavaTypes = parameterClasses;
/* 134 */     this.parameterMembers = parameterMembers;
/* 135 */     this.operationStyle = operationStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses) {
/* 142 */     this(type, encodeType, isNullable, "", parameterNames, parameterTypes, parameterClasses, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, SOAPVersion ver) {
/* 150 */     super(type, encodeType, isNullable, encodingStyle);
/* 151 */     init(ver);
/* 152 */     this.parameterNames = parameterNames;
/* 153 */     this.parameterXmlTypes = parameterTypes;
/* 154 */     this.parameterJavaTypes = parameterClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses) {
/* 161 */     this(type, parameterNames, parameterTypes, parameterClasses, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, SOAPVersion ver) {
/* 167 */     this(type, false, true, "", parameterNames, parameterTypes, parameterClasses);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean isNullable, String encodingStyle) {
/* 173 */     super(type, isNullable, false, encodingStyle);
/*     */   }
/*     */   
/*     */   public LiteralRequestSerializer(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/* 177 */     super(type, isNullable, encodeType, encodingStyle);
/*     */   }
/*     */   
/*     */   private static String getURIEncoding(SOAPVersion ver) {
/* 181 */     if (ver == SOAPVersion.SOAP_11)
/* 182 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 183 */     if (ver == SOAPVersion.SOAP_11)
/* 184 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 185 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 191 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/*     */     
/* 195 */     int membersSize = this.parameterMembers.size();
/* 196 */     if (this.operationStyle.equals("document")) {
/* 197 */       if (this.parameterXmlTypeQNames != null) {
/* 198 */         this.serializers = new JAXRPCSerializer[this.parameterXmlTypeQNames.length];
/* 199 */         this.deserializers = new JAXRPCDeserializer[this.parameterXmlTypeQNames.length];
/*     */         
/* 201 */         for (int i = 0; i < this.parameterXmlTypeQNames.length; i++) {
/* 202 */           if (this.parameterXmlTypeQNames[i] != null && this.parameterJavaTypes[i] != null) {
/*     */             
/* 204 */             if (i < membersSize) {
/* 205 */               ParameterMemberInfo[] thisMembers = this.parameterMembers.get(i);
/*     */               
/* 207 */               ((DynamicInternalTypeMappingRegistry)registry).addDynamicRegistryMembers(this.parameterJavaTypes[i], this.parameterXmlTypeQNames[i], "", thisMembers);
/*     */ 
/*     */ 
/*     */               
/* 211 */               ((DynamicInternalTypeMappingRegistry)registry).addDynamicRegistryMembers(this.parameterJavaTypes[i], this.parameterXmlTypes[i], "", thisMembers);
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 217 */             if (DynamicInternalTypeMappingRegistry.isLiteralArray(this.parameterJavaTypes[i], null, null) || DynamicInternalTypeMappingRegistry.isValueType(this.parameterJavaTypes[i])) {
/*     */ 
/*     */               
/* 220 */               this.serializers[i] = (JAXRPCSerializer)registry.getSerializer("", this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */               
/* 222 */               this.deserializers[i] = (JAXRPCDeserializer)registry.getDeserializer("", this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */             } else {
/*     */               
/* 225 */               this.serializers[i] = (JAXRPCSerializer)registry.getSerializer("", this.parameterJavaTypes[i], this.parameterXmlTypeQNames[i]);
/*     */               
/* 227 */               this.deserializers[i] = (JAXRPCDeserializer)registry.getDeserializer("", this.parameterJavaTypes[i], this.parameterXmlTypeQNames[i]);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 232 */             this.serializers[i] = null;
/* 233 */             this.deserializers[i] = null;
/*     */           } 
/*     */         } 
/*     */       } 
/* 237 */     } else if (this.operationStyle.equals("rpc") && 
/* 238 */       this.parameterXmlTypes != null) {
/* 239 */       this.serializers = new JAXRPCSerializer[this.parameterXmlTypes.length];
/* 240 */       this.deserializers = new JAXRPCDeserializer[this.parameterXmlTypes.length];
/*     */       
/* 242 */       for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 243 */         if (this.parameterXmlTypes[i] != null && this.parameterJavaTypes[i] != null) {
/*     */           
/* 245 */           if (i < membersSize) {
/* 246 */             ParameterMemberInfo[] thisMembers = this.parameterMembers.get(i);
/*     */             
/* 248 */             ((DynamicInternalTypeMappingRegistry)registry).addDynamicRegistryMembers(this.parameterJavaTypes[i], this.parameterXmlTypes[i], "", thisMembers);
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 253 */           this.serializers[i] = (JAXRPCSerializer)registry.getSerializer("", this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */           
/* 255 */           this.deserializers[i] = (JAXRPCDeserializer)registry.getDeserializer("", this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */         } else {
/*     */           
/* 258 */           this.serializers[i] = null;
/* 259 */           this.deserializers[i] = null;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     this.typeRegistry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object value, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*     */     try {
/* 276 */       if (isRPCLiteral())
/* 277 */       { internalSerialize(value, name, writer, context); }
/*     */       else
/* 279 */       { internalSerialize(value, (QName)null, writer, context); } 
/* 280 */     } catch (SerializationException e) {
/* 281 */       throw e;
/* 282 */     } catch (JAXRPCExceptionBase e) {
/* 283 */       throw new SerializationException(e);
/* 284 */     } catch (Exception e) {
/* 285 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
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
/*     */   protected void internalSerialize(Object obj, QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 297 */     context.beginSerializing(obj);
/*     */     
/* 299 */     if (isRPCLiteral()) {
/* 300 */       writer.startElement(name.getLocalPart(), name.getNamespaceURI());
/*     */     }
/* 302 */     boolean pushedEncodingStyle = false;
/* 303 */     if (this.encodingStyle != null) {
/* 304 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 307 */     if (this.encodeType) {
/* 308 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 309 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/* 311 */     if (obj == null) {
/* 312 */       if (!this.isNullable) {
/* 313 */         throw new SerializationException("literal.unexpectedNull");
/*     */       }
/*     */       
/* 316 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     } else {
/* 318 */       writeAdditionalNamespaceDeclarations(obj, writer);
/* 319 */       doSerializeAttributes(obj, writer, context);
/* 320 */       doSerialize(obj, writer, context);
/*     */     } 
/*     */     
/* 323 */     if (isRPCLiteral())
/* 324 */       writer.endElement(); 
/* 325 */     if (pushedEncodingStyle) {
/* 326 */       context.popEncodingStyle();
/*     */     }
/*     */     
/* 329 */     context.doneSerializing(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 338 */     if (this.typeRegistry == null) {
/* 339 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     Object[] parameters = (Object[])instance;
/*     */ 
/*     */     
/* 348 */     for (int i = 0; i < parameters.length; i++) {
/* 349 */       Object parameter = parameters[i];
/*     */       
/* 351 */       if (isRPCLiteral()) {
/* 352 */         getParameterSerializer(i, parameter).serialize(parameter, getParameterName(i), null, writer, context);
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 359 */         getParameterSerializer(i, parameter).serialize(parameter, null, null, writer, context);
/*     */       } 
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
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 373 */     if (this.typeRegistry == null) {
/* 374 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/*     */     
/* 377 */     Object[] instance = new Object[this.parameterXmlTypes.length];
/*     */     
/* 379 */     ParameterArrayBuilder builder = null;
/* 380 */     boolean isComplete = true;
/*     */     
/* 382 */     for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 383 */       reader.nextElementContent();
/* 384 */       QName parameterName = getParameterName(i);
/*     */       
/* 386 */       if (reader.getName().equals(parameterName)) {
/* 387 */         Object parameter = getParameterDeserializer(i, reader).deserialize(parameterName, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 392 */         if (parameter instanceof com.sun.xml.rpc.encoding.SOAPDeserializationState) {
/* 393 */           if (builder == null) {
/* 394 */             builder = new ParameterArrayBuilder(instance);
/*     */           }
/*     */ 
/*     */           
/* 398 */           isComplete = false;
/*     */         } else {
/* 400 */           instance[i] = parameter;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 405 */     reader.nextElementContent();
/* 406 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 407 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 423 */     doSerializeInstance(obj, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeAttributes(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getParameterSerializer(int index, Object parameter) throws Exception {
/*     */     LiteralSimpleTypeSerializer literalSimpleTypeSerializer;
/* 439 */     JAXRPCSerializer serializer = getSerializer(index);
/* 440 */     if (serializer == null) {
/* 441 */       Class<?> parameterClass = null;
/*     */       
/* 443 */       if (parameter != null) {
/* 444 */         parameterClass = parameter.getClass();
/*     */       }
/*     */       
/* 447 */       serializer = (JAXRPCSerializer)this.typeRegistry.getSerializer("", parameterClass, getParameterXmlType(index));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 452 */       if (serializer == null) {
/* 453 */         serializer = (JAXRPCSerializer)this.typeRegistry.getSerializer("", parameterClass, null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 458 */       if (serializer == null)
/*     */       {
/* 460 */         return null;
/*     */       }
/*     */     } 
/* 463 */     if (!isRPCLiteral() && 
/* 464 */       serializer instanceof LiteralSimpleTypeSerializer) {
/*     */       
/* 466 */       SimpleTypeEncoder encoder = ((LiteralSimpleTypeSerializer)serializer).getEncoder();
/*     */       
/* 468 */       if (!(((LiteralSimpleTypeSerializer)serializer).getEncoder() instanceof com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder))
/*     */       {
/*     */ 
/*     */         
/* 472 */         literalSimpleTypeSerializer = new LiteralSimpleTypeSerializer(getParameterXmlType(index), "", encoder);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 479 */     if (literalSimpleTypeSerializer != null) {
/* 480 */       this.serializers[index] = (JAXRPCSerializer)literalSimpleTypeSerializer;
/* 481 */       this.deserializers[index] = (JAXRPCDeserializer)literalSimpleTypeSerializer;
/*     */     } 
/* 483 */     return (JAXRPCSerializer)literalSimpleTypeSerializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getParameterDeserializer(int index, XMLReader reader) throws Exception {
/* 491 */     JAXRPCDeserializer deserializer = getDeserializer(index);
/* 492 */     if (deserializer == null) {
/* 493 */       QName parameterXmlType = (this.parameterXmlTypes[index] != null) ? this.parameterXmlTypes[index] : SerializerBase.getType(reader);
/*     */ 
/*     */ 
/*     */       
/* 497 */       deserializer = (JAXRPCDeserializer)this.typeRegistry.getDeserializer("", getParameterJavaType(index), parameterXmlType);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 503 */     return deserializer;
/*     */   }
/*     */   
/*     */   protected static class ParameterArrayBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 509 */     Object[] instance = null;
/*     */     
/*     */     ParameterArrayBuilder(Object[] instance) {
/* 512 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 516 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/*     */       try {
/* 527 */         this.instance[index] = memberValue;
/* 528 */       } catch (Exception e) {
/* 529 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 540 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 544 */       return this.instance;
/*     */     }
/*     */   }
/*     */   
/*     */   private Class getParameterJavaType(int index) {
/* 549 */     if (index < this.parameterJavaTypes.length) {
/* 550 */       return this.parameterJavaTypes[index];
/*     */     }
/* 552 */     return null;
/*     */   }
/*     */   
/*     */   private QName getParameterXmlType(int index) {
/* 556 */     if (index < this.parameterXmlTypes.length) {
/* 557 */       return this.parameterXmlTypes[index];
/*     */     }
/* 559 */     return null;
/*     */   }
/*     */   
/*     */   private QName getParameterName(int index) {
/* 563 */     if (index < this.parameterNames.length) {
/* 564 */       return this.parameterNames[index];
/*     */     }
/* 566 */     return null;
/*     */   }
/*     */   
/*     */   private JAXRPCDeserializer getDeserializer(int index) {
/* 570 */     if (index < this.deserializers.length) {
/* 571 */       return this.deserializers[index];
/*     */     }
/* 573 */     return null;
/*     */   }
/*     */   
/*     */   private JAXRPCSerializer getSerializer(int index) {
/* 577 */     if (index < this.serializers.length) {
/* 578 */       return this.serializers[index];
/*     */     }
/* 580 */     return null;
/*     */   }
/*     */   
/*     */   private void checkParameterListLength(Object[] parameters) {
/* 584 */     if (this.serializers == null)
/*     */       return; 
/* 586 */     if (this.serializers.length > 0 && parameters.length != this.serializers.length) {
/* 587 */       String expectedParameters = "\n";
/* 588 */       String actualParameters = "\n";
/*     */       int i;
/* 590 */       for (i = 0; i < this.parameterNames.length; i++) {
/* 591 */         QName name = this.parameterNames[i];
/* 592 */         QName xmlType = this.parameterXmlTypes[i];
/* 593 */         expectedParameters = expectedParameters + name + ":" + xmlType;
/*     */         
/* 595 */         if (i + 1 != this.parameterNames.length) {
/* 596 */           expectedParameters = expectedParameters + "\n";
/*     */         }
/*     */       } 
/* 599 */       for (i = 0; i < parameters.length; i++) {
/* 600 */         Object parameter = parameters[i];
/* 601 */         String javaType = (parameter == null) ? "null" : parameter.getClass().getName();
/*     */         
/* 603 */         actualParameters = actualParameters + javaType;
/*     */         
/* 605 */         if (i + 1 != parameters.length) {
/* 606 */           actualParameters = actualParameters + "\n";
/*     */         }
/*     */       } 
/*     */       
/* 610 */       throw new SerializationException("request.parameter.count.incorrect", new Object[] { new Integer(this.serializers.length), new Integer(parameters.length), expectedParameters, actualParameters });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isRPCLiteral() {
/* 621 */     return (this.operationStyle.equalsIgnoreCase("rpc") && this.encodingStyle.equals(""));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralRequestSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */