/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.EncodingException;
/*     */ import com.sun.xml.rpc.encoding.InternalTypeMappingRegistry;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCDeserializer;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.lang.reflect.Array;
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
/*     */ public class LiteralObjectArraySerializer
/*     */   extends LiteralObjectSerializerBase
/*     */ {
/*     */   protected Class javaType;
/*     */   protected Class componentType;
/*  64 */   protected InternalTypeMappingRegistry typeRegistry = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer componentSerializer;
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer componentDeserializer;
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralObjectArraySerializer(QName type, boolean isNullable, String encodingStyle, Class javaType, JAXRPCSerializer componentSerializer, Class componentType) {
/*  76 */     super(type, isNullable, encodingStyle, false);
/*  77 */     this.componentSerializer = componentSerializer;
/*  78 */     this.componentDeserializer = (JAXRPCDeserializer)componentSerializer;
/*  79 */     this.javaType = javaType;
/*  80 */     this.componentType = componentType;
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
/*     */   public LiteralObjectArraySerializer(QName type, boolean isNullable, String encodingStyle, boolean encodeType, Class javaType, JAXRPCSerializer componentSerializer, Class componentType) {
/*  93 */     super(type, isNullable, encodingStyle, encodeType);
/*  94 */     this.javaType = javaType;
/*  95 */     this.componentSerializer = componentSerializer;
/*  96 */     this.componentType = componentType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/* 106 */     if (type == null) {
/* 107 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 110 */     this.type = type;
/* 111 */     this.isNullable = isNullable;
/* 112 */     this.encodingStyle = encodingStyle;
/* 113 */     this.encodeType = encodeType;
/*     */   }
/*     */   
/*     */   public QName getXmlType() {
/* 117 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean getEncodeType() {
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public CombinedSerializer getInnermostSerializer() {
/* 125 */     return (CombinedSerializer)this.componentSerializer;
/*     */   }
/*     */   
/*     */   public boolean isNullable() {
/* 129 */     return this.isNullable;
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 133 */     return this.encodingStyle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object value, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*     */     try {
/* 144 */       internalSerialize(value, name, writer, context);
/* 145 */     } catch (JAXRPCExceptionBase e) {
/* 146 */       throw new SerializationException(e);
/* 147 */     } catch (Exception e) {
/* 148 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/*     */     try {
/* 159 */       return internalDeserialize(name, reader, context);
/* 160 */     } catch (JAXRPCExceptionBase e) {
/* 161 */       throw new DeserializationException(e);
/* 162 */     } catch (Exception e) {
/* 163 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
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
/* 175 */     context.beginSerializing(obj);
/*     */     
/* 177 */     boolean pushedEncodingStyle = false;
/* 178 */     if (this.encodingStyle != null) {
/* 179 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 182 */     if (this.encodeType) {
/* 183 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 184 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/* 186 */     if (obj == null) {
/* 187 */       if (!this.isNullable) {
/* 188 */         throw new SerializationException("literal.unexpectedNull");
/*     */       }
/*     */       
/* 191 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     } else {
/* 193 */       writeAdditionalNamespaceDeclarations(obj, writer);
/* 194 */       doSerializeAttributes(obj, writer, context);
/* 195 */       doSerialize(obj, writer, context);
/*     */     } 
/*     */     
/* 198 */     if (pushedEncodingStyle) {
/* 199 */       context.popEncodingStyle();
/*     */     }
/*     */     
/* 202 */     context.doneSerializing(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 211 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/* 213 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 215 */       if (name != null) {
/* 216 */         QName actualName = reader.getName();
/* 217 */         if (!actualName.equals(name)) {
/* 218 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 224 */       verifyType(reader);
/*     */       
/* 226 */       Attributes attrs = reader.getAttributes();
/* 227 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 228 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/*     */       
/* 230 */       Object obj = null;
/*     */       
/* 232 */       if (isNull) {
/* 233 */         if (!this.isNullable) {
/* 234 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/* 236 */         reader.next();
/*     */       } else {
/* 238 */         obj = doDeserialize(reader, context);
/*     */       } 
/*     */       
/* 241 */       XMLReaderUtil.verifyReaderState(reader, 2);
/* 242 */       return obj;
/*     */     } finally {
/* 244 */       if (pushedEncodingStyle) {
/* 245 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 254 */     if (this.typeRegistry == null) {
/* 255 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/* 257 */     if (this.componentDeserializer == null) {
/* 258 */       this.componentDeserializer = (JAXRPCDeserializer)this.componentSerializer;
/*     */     }
/*     */     
/* 261 */     ArrayList<Object> values = null;
/* 262 */     Object currentValue = null;
/* 263 */     Object array = null;
/* 264 */     QName elementName = null;
/* 265 */     boolean rpclit = true;
/* 266 */     elementName = reader.getName();
/*     */ 
/*     */     
/* 269 */     if (reader.getState() == 1 && elementName.equals(this.type)) {
/*     */ 
/*     */       
/* 272 */       values = new ArrayList();
/*     */       
/*     */       while (true) {
/* 275 */         if (reader.getName().equals(this.type) && reader.getState() == 2)
/*     */         {
/* 277 */           reader.nextElementContent();
/*     */         }
/* 279 */         elementName = reader.getName();
/* 280 */         if (reader.getState() == 1 && elementName.equals(this.type)) {
/*     */ 
/*     */ 
/*     */           
/* 284 */           currentValue = this.componentDeserializer.deserialize(this.type, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 290 */           if (currentValue != null) {
/* 291 */             values.add(currentValue);
/*     */           } else {
/* 293 */             throw new DeserializationException("literal unexpected null");
/*     */           } 
/*     */           
/* 296 */           XMLReaderUtil.verifyReaderState(reader, 2);
/* 297 */           while (!this.type.equals(reader.getName())) {
/* 298 */             reader.nextElementContent();
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/* 305 */       array = Array.newInstance(this.componentType, values.size());
/* 306 */       Object[] valuesArray = values.toArray();
/*     */ 
/*     */       
/* 309 */       for (int i = 0; i < valuesArray.length; i++) {
/* 310 */         Array.set(array, i, valuesArray[i]);
/*     */       }
/*     */     } else {
/* 313 */       array = Array.newInstance(this.componentType, 0);
/*     */     } 
/*     */     
/* 316 */     XMLReaderUtil.verifyReaderState(reader, 2);
/*     */     
/* 318 */     return array;
/*     */   }
/*     */   
/*     */   protected void verifyType(XMLReader reader) throws Exception {
/* 322 */     QName actualType = getType(reader);
/*     */     
/* 324 */     if (actualType != null && 
/* 325 */       !actualType.equals(this.type) && !isAcceptableType(actualType)) {
/* 326 */       throw new DeserializationException("xsd.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAcceptableType(QName actualType) {
/* 334 */     return false;
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
/*     */   protected void doSerialize(Object obj, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 349 */     doSerializeInstance(obj, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 358 */     if (this.typeRegistry == null) {
/* 359 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/*     */     
/* 362 */     if (instance.getClass().isArray()) {
/* 363 */       int length = Array.getLength(instance);
/*     */       
/* 365 */       for (int i = 0; i < length; i++) {
/* 366 */         Object parameter = Array.get(instance, i);
/*     */         
/* 368 */         if (this.componentSerializer != null);
/*     */         
/* 370 */         ((CombinedSerializer)this.componentSerializer).serialize(parameter, null, null, writer, context);
/*     */       } 
/*     */     } 
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
/*     */   
/*     */   public String getMechanismType() {
/* 388 */     return "http://java.sun.com/jax-rpc-ri/1.0/streaming/";
/*     */   }
/*     */   
/*     */   public static QName getType(XMLReader reader) throws Exception {
/* 392 */     QName type = null;
/*     */     
/* 394 */     Attributes attrs = reader.getAttributes();
/* 395 */     String typeVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/* 397 */     if (typeVal != null) {
/* 398 */       type = XMLReaderUtil.decodeQName(reader, typeVal);
/*     */     }
/*     */     
/* 401 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getParameterSerializer(int index, Object parameter) throws Exception {
/* 409 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getParameterDeserializer(int index, XMLReader reader) throws Exception {
/* 417 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPDeserializationState registerWithMemberState(Object instance, SOAPDeserializationState state, Object member, int memberIndex, SOAPInstanceBuilder builder) {
/*     */     try {
/*     */       SOAPDeserializationState deserializationState;
/* 428 */       if (state == null) {
/* 429 */         deserializationState = new SOAPDeserializationState();
/*     */       } else {
/* 431 */         deserializationState = state;
/*     */       } 
/*     */       
/* 434 */       deserializationState.setInstance(instance);
/* 435 */       if (deserializationState.getBuilder() == null) {
/* 436 */         if (builder == null) {
/* 437 */           throw new IllegalArgumentException();
/*     */         }
/* 439 */         deserializationState.setBuilder(builder);
/*     */       } 
/*     */       
/* 442 */       SOAPDeserializationState memberState = (SOAPDeserializationState)member;
/*     */       
/* 444 */       memberState.registerListener(deserializationState, memberIndex);
/*     */       
/* 446 */       return deserializationState;
/* 447 */     } catch (JAXRPCExceptionBase e) {
/* 448 */       throw new DeserializationException(e);
/* 449 */     } catch (Exception e) {
/* 450 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 458 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/* 461 */     this.typeRegistry = registry;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralObjectArraySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */