/*     */ package com.sun.xml.rpc.encoding.soap;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.EncodingException;
/*     */ import com.sun.xml.rpc.encoding.Initializable;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.ObjectSerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
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
/*     */ public class SOAPRequestSerializer
/*     */   extends ObjectSerializerBase
/*     */   implements Initializable
/*     */ {
/*     */   protected QName[] parameterNames;
/*     */   protected QName[] parameterXmlTypes;
/*     */   protected Class[] parameterJavaTypes;
/*     */   protected JAXRPCSerializer[] serializers;
/*     */   protected JAXRPCDeserializer[] deserializers;
/*  68 */   protected InternalTypeMappingRegistry typeRegistry = null;
/*     */   
/*  70 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  74 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
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
/*     */   public SOAPRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses) {
/*  87 */     this(type, encodeType, isNullable, encodingStyle, parameterNames, parameterTypes, parameterClasses, SOAPVersion.SOAP_11);
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
/*     */   public SOAPRequestSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, SOAPVersion ver) {
/* 108 */     super(type, encodeType, isNullable, encodingStyle);
/* 109 */     init(ver);
/*     */     
/* 111 */     this.parameterNames = parameterNames;
/* 112 */     this.parameterXmlTypes = parameterTypes;
/* 113 */     this.parameterJavaTypes = parameterClasses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPRequestSerializer(QName type, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses) {
/* 122 */     this(type, parameterNames, parameterTypes, parameterClasses, SOAPVersion.SOAP_11);
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
/*     */   public SOAPRequestSerializer(QName type, QName[] parameterNames, QName[] parameterTypes, Class[] parameterClasses, SOAPVersion ver) {
/* 138 */     this(type, false, true, getURIEncoding(ver), parameterNames, parameterTypes, parameterClasses);
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
/*     */   private static String getURIEncoding(SOAPVersion ver) {
/* 150 */     if (ver == SOAPVersion.SOAP_11)
/* 151 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 152 */     if (ver == SOAPVersion.SOAP_11)
/* 153 */       return "http://schemas.xmlsoap.org/soap/encoding/"; 
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 160 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/*     */     
/* 164 */     this.serializers = new JAXRPCSerializer[this.parameterXmlTypes.length];
/* 165 */     this.deserializers = new JAXRPCDeserializer[this.parameterXmlTypes.length];
/*     */     
/* 167 */     for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 168 */       if (this.parameterXmlTypes[i] != null && this.parameterJavaTypes[i] != null) {
/*     */         
/* 170 */         this.serializers[i] = (JAXRPCSerializer)registry.getSerializer(this.encodingStyle, this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 175 */         this.deserializers[i] = (JAXRPCDeserializer)registry.getDeserializer(this.encodingStyle, this.parameterJavaTypes[i], this.parameterXmlTypes[i]);
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 181 */         this.serializers[i] = null;
/* 182 */         this.deserializers[i] = null;
/*     */       } 
/*     */     } 
/*     */     
/* 186 */     this.typeRegistry = registry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 195 */     if (this.typeRegistry == null) {
/* 196 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/*     */     
/* 199 */     Object[] parameters = (Object[])instance;
/*     */     
/* 201 */     checkParameterListLength(parameters);
/* 202 */     for (int i = 0; i < parameters.length; i++) {
/* 203 */       Object parameter = parameters[i];
/* 204 */       getParameterSerializer(i, parameter).serialize(parameter, getParameterName(i), null, writer, context);
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
/* 219 */     if (this.typeRegistry == null) {
/* 220 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/*     */     
/* 223 */     Object[] instance = new Object[this.parameterXmlTypes.length];
/*     */     
/* 225 */     ParameterArrayBuilder builder = null;
/* 226 */     boolean isComplete = true;
/* 227 */     SOAPDeserializationState state = existingState;
/*     */     
/* 229 */     for (int i = 0; i < this.parameterXmlTypes.length; i++) {
/* 230 */       reader.nextElementContent();
/* 231 */       QName parameterName = getParameterName(i);
/*     */       
/* 233 */       if (reader.getName().equals(parameterName)) {
/* 234 */         Object parameter = getParameterDeserializer(i, reader).deserialize(parameterName, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 239 */         if (parameter instanceof SOAPDeserializationState) {
/* 240 */           if (builder == null) {
/* 241 */             builder = new ParameterArrayBuilder(instance);
/*     */           }
/* 243 */           state = registerWithMemberState(instance, state, parameter, i, builder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 250 */           isComplete = false;
/*     */         } else {
/* 252 */           instance[i] = parameter;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     reader.nextElementContent();
/* 258 */     XMLReaderUtil.verifyReaderState(reader, 2);
/* 259 */     return isComplete ? instance : state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getParameterSerializer(int index, Object parameter) throws Exception {
/* 267 */     JAXRPCSerializer serializer = getSerializer(index);
/* 268 */     if (serializer == null) {
/* 269 */       Class<?> parameterClass = null;
/* 270 */       if (parameter != null) {
/* 271 */         parameterClass = parameter.getClass();
/*     */       }
/* 273 */       serializer = (JAXRPCSerializer)this.typeRegistry.getSerializer(this.encodingStyle, parameterClass, getParameterXmlType(index));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     return serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getParameterDeserializer(int index, XMLReader reader) throws Exception {
/* 287 */     JAXRPCDeserializer deserializer = getDeserializer(index);
/* 288 */     if (deserializer == null) {
/* 289 */       QName parameterXmlType = (this.parameterXmlTypes[index] != null) ? this.parameterXmlTypes[index] : SerializerBase.getType(reader);
/*     */ 
/*     */ 
/*     */       
/* 293 */       deserializer = (JAXRPCDeserializer)this.typeRegistry.getDeserializer(this.encodingStyle, getParameterJavaType(index), parameterXmlType);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     return deserializer;
/*     */   }
/*     */   
/*     */   protected static class ParameterArrayBuilder
/*     */     implements SOAPInstanceBuilder
/*     */   {
/* 305 */     Object[] instance = null;
/*     */     
/*     */     ParameterArrayBuilder(Object[] instance) {
/* 308 */       this.instance = instance;
/*     */     }
/*     */     
/*     */     public int memberGateType(int memberIndex) {
/* 312 */       return 6;
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
/* 323 */         this.instance[index] = memberValue;
/* 324 */       } catch (Exception e) {
/* 325 */         throw new DeserializationException("nestedSerializationError", new LocalizableExceptionAdapter(e));
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
/* 336 */       instance = instance;
/*     */     }
/*     */     
/*     */     public Object getInstance() {
/* 340 */       return this.instance;
/*     */     }
/*     */   }
/*     */   
/*     */   private Class getParameterJavaType(int index) {
/* 345 */     if (index < this.parameterJavaTypes.length) {
/* 346 */       return this.parameterJavaTypes[index];
/*     */     }
/* 348 */     return null;
/*     */   }
/*     */   
/*     */   private QName getParameterXmlType(int index) {
/* 352 */     if (index < this.parameterXmlTypes.length) {
/* 353 */       return this.parameterXmlTypes[index];
/*     */     }
/* 355 */     return null;
/*     */   }
/*     */   
/*     */   private QName getParameterName(int index) {
/* 359 */     if (index < this.parameterNames.length) {
/* 360 */       return this.parameterNames[index];
/*     */     }
/* 362 */     return null;
/*     */   }
/*     */   
/*     */   private JAXRPCDeserializer getDeserializer(int index) {
/* 366 */     if (index < this.deserializers.length) {
/* 367 */       return this.deserializers[index];
/*     */     }
/* 369 */     return null;
/*     */   }
/*     */   
/*     */   private JAXRPCSerializer getSerializer(int index) {
/* 373 */     if (index < this.serializers.length) {
/* 374 */       return this.serializers[index];
/*     */     }
/* 376 */     return null;
/*     */   }
/*     */   
/*     */   private void checkParameterListLength(Object[] parameters) {
/* 380 */     if (this.serializers.length > 0 && parameters.length != this.serializers.length) {
/*     */       
/* 382 */       String expectedParameters = "\n";
/* 383 */       String actualParameters = "\n";
/*     */       int i;
/* 385 */       for (i = 0; i < this.parameterNames.length; i++) {
/* 386 */         QName name = this.parameterNames[i];
/* 387 */         QName xmlType = this.parameterXmlTypes[i];
/* 388 */         expectedParameters = expectedParameters + name + ":" + xmlType;
/*     */         
/* 390 */         if (i + 1 != this.parameterNames.length) {
/* 391 */           expectedParameters = expectedParameters + "\n";
/*     */         }
/*     */       } 
/* 394 */       for (i = 0; i < parameters.length; i++) {
/* 395 */         Object parameter = parameters[i];
/* 396 */         String javaType = (parameter == null) ? "null" : parameter.getClass().getName();
/*     */         
/* 398 */         actualParameters = actualParameters + javaType;
/*     */         
/* 400 */         if (i + 1 != parameters.length) {
/* 401 */           actualParameters = actualParameters + "\n";
/*     */         }
/*     */       } 
/*     */       
/* 405 */       throw new SerializationException("request.parameter.count.incorrect", new Object[] { new Integer(this.serializers.length), new Integer(parameters.length), expectedParameters, actualParameters });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\soap\SOAPRequestSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */