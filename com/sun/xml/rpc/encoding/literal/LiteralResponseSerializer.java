/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.ParameterMemberInfo;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.DynamicInternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPResponseStructure;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class LiteralResponseSerializer
/*     */   extends LiteralRequestSerializer
/*     */   implements Initializable
/*     */ {
/*  63 */   private static final QName RETURN_VALUE_QNAME = new QName("return");
/*  64 */   private static final QName[] EMPTY_QNAME_ARRAY = new QName[0];
/*  65 */   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
/*     */   
/*     */   protected QName returnXmlType;
/*     */   
/*     */   protected QName returnXmlTypeQName;
/*     */   
/*     */   protected Class returnJavaType;
/*     */   protected ParameterMemberInfo[] returnMemberInfo;
/*     */   protected JAXRPCSerializer returnSerializer;
/*     */   protected JAXRPCDeserializer returnDeserializer;
/*     */   protected boolean isReturnVoid;
/*  76 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  80 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean isNullable, String encodingStyle) {
/*  84 */     super(type, isNullable, encodingStyle);
/*     */   }
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/*  88 */     super(type, isNullable, encodingStyle, encodeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterXmlTypes, QName[] parameterXmlTypeQNames, Class[] parameterClasses, ArrayList parameterMembers, QName returnXmlType, QName returnXmlTypeQName, Class returnJavaType, ParameterMemberInfo[] returnMembers) {
/*  98 */     this(type, encodeType, isNullable, encodingStyle, operationStyle, parameterNames, parameterXmlTypes, parameterXmlTypeQNames, parameterClasses, parameterMembers, returnXmlType, returnXmlTypeQName, returnJavaType, returnMembers, SOAPVersion.SOAP_11);
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
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterXmlTypes, QName[] parameterXmlTypeQNames, Class[] parameterClasses, ArrayList parameterMembers, QName returnXmlType, QName returnXmlTypeQName, Class returnJavaType, ParameterMemberInfo[] returnMembers, SOAPVersion ver) {
/* 113 */     super(type, encodeType, isNullable, "", operationStyle, parameterNames, parameterXmlTypes, parameterXmlTypeQNames, parameterClasses, parameterMembers, SOAPVersion.SOAP_11);
/*     */ 
/*     */ 
/*     */     
/* 117 */     init(ver);
/* 118 */     this.isReturnVoid = (returnJavaType == null && returnXmlType == null);
/* 119 */     this.returnXmlType = returnXmlType;
/* 120 */     this.returnJavaType = returnJavaType;
/* 121 */     this.returnXmlTypeQName = returnXmlTypeQName;
/* 122 */     this.returnMemberInfo = returnMembers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, ArrayList parameterMembers, QName returnXmlType, Class returnJavaType, ParameterMemberInfo[] returnMembers) {
/* 132 */     this(type, encodeType, isNullable, encodingStyle, operationStyle, parameterNames, parameterXmlTypes, parameterClasses, parameterMembers, returnXmlType, returnJavaType, returnMembers, SOAPVersion.SOAP_11);
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
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, String operationStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, ArrayList parameterMembers, QName returnXmlType, Class returnJavaType, ParameterMemberInfo[] returnMembers, SOAPVersion ver) {
/* 144 */     super(type, encodeType, isNullable, "", operationStyle, parameterNames, parameterXmlTypes, parameterClasses, parameterMembers, SOAPVersion.SOAP_11);
/*     */ 
/*     */     
/* 147 */     init(ver);
/* 148 */     this.isReturnVoid = (returnJavaType == null && returnXmlType == null);
/* 149 */     this.returnXmlType = returnXmlType;
/* 150 */     this.returnJavaType = returnJavaType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType) {
/* 157 */     this(type, encodeType, isNullable, encodingStyle, parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 166 */     super(type, encodeType, isNullable, "", parameterNames, parameterXmlTypes, parameterClasses, SOAPVersion.SOAP_11);
/*     */ 
/*     */     
/* 169 */     init(ver);
/* 170 */     this.isReturnVoid = (returnJavaType == null && returnXmlType == null);
/* 171 */     this.returnXmlType = returnXmlType;
/* 172 */     this.returnJavaType = returnJavaType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName returnXmlType, Class returnJavaType) {
/* 179 */     this(type, encodeType, isNullable, encodingStyle, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 186 */     this(type, encodeType, isNullable, encodingStyle, EMPTY_QNAME_ARRAY, EMPTY_QNAME_ARRAY, EMPTY_CLASS_ARRAY, returnXmlType, returnJavaType, ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType) {
/* 193 */     this(type, parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 199 */     this(type, false, true, getURIEncoding(ver), parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, ver);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralResponseSerializer(QName type, QName returnXmlType, Class returnJavaType) {
/* 205 */     this(type, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
/*     */   }
/*     */   
/*     */   public LiteralResponseSerializer(QName type, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 209 */     this(type, EMPTY_QNAME_ARRAY, EMPTY_QNAME_ARRAY, EMPTY_CLASS_ARRAY, returnXmlType, returnJavaType, ver);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getURIEncoding(SOAPVersion ver) {
/* 214 */     if (ver == SOAPVersion.SOAP_11)
/* 215 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 216 */     if (ver == SOAPVersion.SOAP_11)
/* 217 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 218 */     return null;
/*     */   }
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 222 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 227 */     ((DynamicInternalTypeMappingRegistry)registry).addDynamicRegistryMembers(this.returnJavaType, this.returnXmlType, "", this.returnMemberInfo);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     super.initialize(registry);
/*     */ 
/*     */     
/* 240 */     if (isRPCLiteral()) {
/* 241 */       if (this.returnJavaType != null && this.returnXmlType != null) {
/* 242 */         this.returnSerializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */         
/* 244 */         this.returnDeserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */       }
/* 246 */       else if (this.returnXmlType != null) {
/* 247 */         this.returnSerializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, null, this.returnXmlType);
/*     */         
/* 249 */         this.returnDeserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, null, this.returnXmlType);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 254 */     else if (this.returnJavaType != null && this.returnXmlTypeQName != null) {
/*     */       
/* 256 */       if (DynamicInternalTypeMappingRegistry.isLiteralArray(this.returnJavaType, null, null) || DynamicInternalTypeMappingRegistry.isValueType(this.returnJavaType)) {
/*     */         
/* 258 */         this.returnSerializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */         
/* 260 */         this.returnDeserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */       } else {
/*     */         
/* 263 */         this.returnSerializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.returnJavaType, this.returnXmlTypeQName);
/*     */         
/* 265 */         this.returnDeserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlTypeQName);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 276 */     SOAPResponseStructure instance = new SOAPResponseStructure();
/*     */     
/* 278 */     SOAPResponseStructureBuilder builder = null;
/* 279 */     boolean isComplete = true;
/*     */     
/* 281 */     if (isRPCLiteral())
/* 282 */       reader.nextElementContent(); 
/* 283 */     int responseMemberIndex = 0;
/*     */ 
/*     */     
/* 286 */     if (!this.isReturnVoid) {
/* 287 */       Object returnedObject = getReturnDeserializer(reader).deserialize(null, reader, context);
/* 288 */       if (returnedObject instanceof com.sun.xml.rpc.encoding.SOAPDeserializationState) {
/* 289 */         if (builder == null) {
/* 290 */           builder = new SOAPResponseStructureBuilder(instance);
/*     */         }
/*     */         
/* 293 */         isComplete = false;
/*     */       } else {
/* 295 */         instance.returnValue = returnedObject;
/*     */       } 
/*     */       
/* 298 */       if (this.parameterXmlTypes == null || this.parameterXmlTypes.length > 0);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     if (this.parameterXmlTypes != null) {
/* 306 */       for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 307 */         QName parameterName = this.parameterNames[i];
/*     */         
/* 309 */         if (reader.getName().equals(parameterName)) {
/* 310 */           Object returnedObject = getParameterDeserializer(i, reader).deserialize(parameterName, reader, context);
/*     */           
/* 312 */           if (returnedObject instanceof com.sun.xml.rpc.encoding.SOAPDeserializationState) {
/* 313 */             if (builder == null) {
/* 314 */               builder = new SOAPResponseStructureBuilder(instance);
/*     */             }
/* 316 */             responseMemberIndex = i + 1;
/* 317 */             builder.setOutParameterName(responseMemberIndex, parameterName);
/*     */             
/* 319 */             isComplete = false;
/*     */           } else {
/* 321 */             instance.outParameters.put(parameterName, returnedObject);
/*     */             
/* 323 */             instance.outParametersStringKeys.put(parameterName.getLocalPart(), returnedObject);
/*     */           } 
/*     */         } else {
/*     */           
/* 327 */           throw new DeserializationException("soap.unexpectedElementName", new Object[] { parameterName, reader.getName() });
/*     */         } 
/*     */         
/* 330 */         XMLReaderUtil.verifyReaderState(reader, 2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 335 */     if (isRPCLiteral())
/* 336 */       reader.nextElementContent(); 
/* 337 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/*     */     try {
/* 344 */       return internalDeserialize(name, reader, context);
/* 345 */     } catch (DeserializationException e) {
/* 346 */       throw e;
/* 347 */     } catch (JAXRPCExceptionBase e) {
/* 348 */       throw new DeserializationException(e);
/* 349 */     } catch (Exception e) {
/* 350 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 359 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/* 361 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 363 */       if (name != null) {
/* 364 */         QName actualName = reader.getName();
/* 365 */         if (!actualName.equals(name)) {
/* 366 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       verifyType(reader);
/*     */       
/* 376 */       Attributes attrs = reader.getAttributes();
/* 377 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 378 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/* 379 */       Object obj = null;
/*     */       
/* 381 */       if (isNull) {
/* 382 */         if (!this.isNullable) {
/* 383 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/* 385 */         reader.next();
/*     */       } else {
/* 387 */         obj = doDeserialize(reader, context);
/*     */       } 
/*     */       
/* 390 */       XMLReaderUtil.verifyReaderState(reader, 2);
/* 391 */       return obj;
/*     */     } finally {
/* 393 */       if (pushedEncodingStyle) {
/* 394 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getReturnSerializer(Object returnValue) throws Exception {
/* 401 */     JAXRPCSerializer serializer = this.returnSerializer;
/* 402 */     if (serializer == null) {
/* 403 */       serializer = (JAXRPCSerializer)this.typeRegistry.getSerializer(this.encodingStyle, returnValue.getClass(), this.returnXmlType);
/*     */     }
/*     */     
/* 406 */     return serializer;
/*     */   }
/*     */   
/*     */   protected JAXRPCDeserializer getReturnDeserializer(XMLReader reader) throws Exception {
/* 410 */     JAXRPCDeserializer deserializer = this.returnDeserializer;
/* 411 */     if (deserializer == null) {
/* 412 */       QName xmlType = null;
/* 413 */       if (isRPCLiteral()) {
/* 414 */         xmlType = (this.returnXmlType != null) ? this.returnXmlType : SerializerBase.getType(reader);
/*     */       }
/* 416 */       else if (!"".equals(this.encodingStyle)) {
/* 417 */         if (this.returnJavaType != null) {
/* 418 */           xmlType = new QName("http://www.w3.org/2001/XMLSchema", this.returnJavaType.getName());
/*     */         } else {
/* 420 */           xmlType = (this.returnXmlType != null) ? this.returnXmlType : SerializerBase.getType(reader);
/*     */         } 
/*     */       } else {
/* 423 */         xmlType = (this.returnXmlTypeQName != null) ? this.returnXmlType : SerializerBase.getType(reader);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 428 */       if (DynamicInternalTypeMappingRegistry.isLiteralArray(this.returnJavaType, null, null) || DynamicInternalTypeMappingRegistry.isValueType(this.returnJavaType)) {
/*     */         
/* 430 */         deserializer = (JAXRPCDeserializer)this.typeRegistry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */       } else {
/*     */         
/* 433 */         deserializer = (JAXRPCDeserializer)this.typeRegistry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlTypeQName);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 438 */     return deserializer;
/*     */   }
/*     */   
/*     */   protected static class SOAPResponseStructureBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 444 */     SOAPResponseStructure instance = null;
/* 445 */     List outParameterNames = new ArrayList();
/*     */     
/*     */     public void setOutParameterName(int index, QName name) {
/* 448 */       this.outParameterNames.set(index, name);
/*     */     }
/*     */     
/*     */     SOAPResponseStructureBuilder(SOAPResponseStructure instance) {
/* 452 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 456 */       return 6;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void construct() {}
/*     */ 
/*     */     
/*     */     public void setMember(int index, Object memberValue) {
/*     */       try {
/* 466 */         if (index == 0) {
/* 467 */           this.instance.returnValue = memberValue;
/*     */         } else {
/* 469 */           this.instance.outParameters.put(this.outParameterNames.get(index), memberValue);
/*     */           
/* 471 */           this.instance.outParametersStringKeys.put(((QName)this.outParameterNames.get(index)).getLocalPart(), memberValue);
/*     */         } 
/* 473 */       } catch (Exception e) {
/* 474 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void initialize() {}
/*     */ 
/*     */     
/*     */     public void setInstance(Object instance) {
/* 483 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 487 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralResponseSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */