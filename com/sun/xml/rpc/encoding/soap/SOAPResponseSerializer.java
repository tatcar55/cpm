/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
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
/*     */ public class SOAPResponseSerializer
/*     */   extends SOAPRequestSerializer
/*     */   implements Initializable
/*     */ {
/*  60 */   private static final QName RETURN_VALUE_QNAME = new QName("return");
/*  61 */   private static final QName[] EMPTY_QNAME_ARRAY = new QName[0];
/*  62 */   private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
/*     */   
/*     */   protected QName returnXmlType;
/*     */   
/*     */   protected Class returnJavaType;
/*     */   
/*     */   protected JAXRPCSerializer returnSerializer;
/*     */   protected JAXRPCDeserializer returnDeserializer;
/*     */   protected boolean isReturnVoid;
/*  71 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  75 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
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
/*     */   public SOAPResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType) {
/*  90 */     this(type, encodeType, isNullable, encodingStyle, parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
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
/*     */   public SOAPResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 115 */     super(type, encodeType, isNullable, encodingStyle, parameterNames, parameterXmlTypes, parameterClasses);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     init(ver);
/* 124 */     this.isReturnVoid = (returnJavaType == null && returnXmlType == null);
/* 125 */     this.returnXmlType = returnXmlType;
/* 126 */     this.returnJavaType = returnJavaType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName returnXmlType, Class returnJavaType) {
/* 137 */     this(type, encodeType, isNullable, encodingStyle, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
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
/*     */   public SOAPResponseSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 156 */     this(type, encodeType, isNullable, encodingStyle, EMPTY_QNAME_ARRAY, EMPTY_QNAME_ARRAY, EMPTY_CLASS_ARRAY, returnXmlType, returnJavaType, ver);
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
/*     */   public SOAPResponseSerializer(QName type, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType) {
/* 177 */     this(type, parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
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
/*     */   public SOAPResponseSerializer(QName type, QName[] parameterNames, QName[] parameterXmlTypes, Class[] parameterClasses, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 196 */     this(type, false, true, getURIEncoding(ver), parameterNames, parameterXmlTypes, parameterClasses, returnXmlType, returnJavaType, ver);
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
/*     */   public SOAPResponseSerializer(QName type, QName returnXmlType, Class returnJavaType) {
/* 215 */     this(type, returnXmlType, returnJavaType, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPResponseSerializer(QName type, QName returnXmlType, Class returnJavaType, SOAPVersion ver) {
/* 224 */     this(type, EMPTY_QNAME_ARRAY, EMPTY_QNAME_ARRAY, EMPTY_CLASS_ARRAY, returnXmlType, returnJavaType, ver);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getURIEncoding(SOAPVersion ver) {
/* 235 */     if (ver == SOAPVersion.SOAP_11)
/* 236 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 237 */     if (ver == SOAPVersion.SOAP_11)
/* 238 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 245 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/*     */     
/* 249 */     super.initialize(registry);
/*     */     
/* 251 */     if (this.returnJavaType != null && this.returnXmlType != null) {
/* 252 */       this.returnSerializer = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       this.returnDeserializer = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.returnJavaType, this.returnXmlType);
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
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 271 */     SOAPResponseStructure response = (SOAPResponseStructure)instance;
/* 272 */     getReturnSerializer(response.returnValue).serialize(response.returnValue, RETURN_VALUE_QNAME, null, writer, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 280 */       QName parameterName = this.parameterNames[i];
/* 281 */       Object parameter = response.outParameters.get(parameterName);
/* 282 */       getParameterSerializer(i, parameter).serialize(parameter, parameterName, null, writer, context);
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
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(SOAPDeserializationState existingState, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 297 */     SOAPResponseStructure instance = new SOAPResponseStructure();
/*     */     
/* 299 */     SOAPResponseStructureBuilder builder = null;
/* 300 */     boolean isComplete = true;
/* 301 */     SOAPDeserializationState state = existingState;
/*     */     
/* 303 */     reader.nextElementContent();
/* 304 */     int responseMemberIndex = 0;
/*     */ 
/*     */     
/* 307 */     if (!this.isReturnVoid) {
/* 308 */       Object returnedObject = getReturnDeserializer(reader).deserialize(null, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 313 */       if (returnedObject instanceof SOAPDeserializationState) {
/* 314 */         if (builder == null) {
/* 315 */           builder = new SOAPResponseStructureBuilder(instance);
/*     */         }
/* 317 */         state = registerWithMemberState(instance, state, returnedObject, responseMemberIndex, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 324 */         isComplete = false;
/*     */       } else {
/* 326 */         instance.returnValue = returnedObject;
/*     */       } 
/*     */       
/* 329 */       if (this.parameterXmlTypes.length > 0) {
/* 330 */         reader.nextElementContent();
/*     */       }
/*     */     } 
/*     */     
/* 334 */     for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 335 */       QName parameterName = this.parameterNames[i];
/*     */       
/* 337 */       if (reader.getName().equals(parameterName)) {
/* 338 */         Object returnedObject = getParameterDeserializer(i, reader).deserialize(parameterName, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 343 */         if (returnedObject instanceof SOAPDeserializationState) {
/* 344 */           if (builder == null) {
/* 345 */             builder = new SOAPResponseStructureBuilder(instance);
/*     */           }
/* 347 */           responseMemberIndex = i + 1;
/* 348 */           builder.setOutParameterName(responseMemberIndex, parameterName);
/*     */ 
/*     */           
/* 351 */           state = registerWithMemberState(instance, state, returnedObject, responseMemberIndex, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           isComplete = false;
/*     */         } else {
/* 360 */           instance.outParameters.put(parameterName, returnedObject);
/*     */         } 
/*     */         
/* 363 */         instance.outParametersStringKeys.put(parameterName.getLocalPart(), returnedObject);
/*     */       }
/*     */       else {
/*     */         
/* 367 */         throw new DeserializationException("soap.unexpectedElementName", new Object[] { parameterName, reader.getName() });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 373 */     reader.nextElementContent();
/* 374 */     return isComplete ? instance : state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getReturnSerializer(Object returnValue) throws Exception {
/* 380 */     JAXRPCSerializer serializer = this.returnSerializer;
/* 381 */     if (serializer == null) {
/* 382 */       serializer = (JAXRPCSerializer)this.typeRegistry.getSerializer(this.encodingStyle, returnValue.getClass(), this.returnXmlType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getReturnDeserializer(XMLReader reader) throws Exception {
/* 394 */     JAXRPCDeserializer deserializer = this.returnDeserializer;
/* 395 */     if (deserializer == null) {
/* 396 */       QName xmlType = (this.returnXmlType != null) ? this.returnXmlType : SerializerBase.getType(reader);
/*     */ 
/*     */ 
/*     */       
/* 400 */       deserializer = (JAXRPCDeserializer)this.typeRegistry.getDeserializer(this.encodingStyle, this.returnJavaType, xmlType);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     return deserializer;
/*     */   }
/*     */   
/*     */   protected static class SOAPResponseStructureBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 412 */     SOAPResponseStructure instance = null;
/* 413 */     List outParameterNames = new ArrayList();
/*     */     
/*     */     public void setOutParameterName(int index, QName name) {
/* 416 */       this.outParameterNames.set(index, name);
/*     */     }
/*     */     
/*     */     SOAPResponseStructureBuilder(SOAPResponseStructure instance) {
/* 420 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 424 */       return 6;
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
/* 435 */         if (index == 0) {
/* 436 */           this.instance.returnValue = memberValue;
/*     */         } else {
/* 438 */           this.instance.outParameters.put(this.outParameterNames.get(index), memberValue);
/*     */ 
/*     */ 
/*     */           
/* 442 */           this.instance.outParametersStringKeys.put(((QName)this.outParameterNames.get(index)).getLocalPart(), memberValue);
/*     */         }
/*     */       
/*     */       }
/* 446 */       catch (Exception e) {
/* 447 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
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
/* 458 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 462 */       return this.instance;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\SOAPResponseSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */