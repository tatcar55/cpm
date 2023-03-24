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
/*     */ public class LiteralArraySerializer
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
/*     */   public LiteralArraySerializer(QName type, boolean isNullable, String encodingStyle, Class javaType, JAXRPCSerializer componentSerializer, Class componentType) {
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
/*     */   public LiteralArraySerializer(QName type, boolean isNullable, String encodingStyle, boolean encodeType, Class javaType, JAXRPCSerializer componentSerializer, Class componentType) {
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
/*     */   private void init(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/* 105 */     if (type == null) {
/* 106 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 109 */     this.type = type;
/* 110 */     this.isNullable = isNullable;
/* 111 */     this.encodingStyle = encodingStyle;
/* 112 */     this.encodeType = encodeType;
/*     */   }
/*     */   
/*     */   public QName getXmlType() {
/* 116 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean getEncodeType() {
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   public CombinedSerializer getInnermostSerializer() {
/* 124 */     return (CombinedSerializer)this.componentSerializer;
/*     */   }
/*     */   
/*     */   public boolean isNullable() {
/* 128 */     return this.isNullable;
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 132 */     return this.encodingStyle;
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
/* 143 */       internalSerialize(value, name, writer, context);
/* 144 */     } catch (SerializationException e) {
/* 145 */       throw e;
/* 146 */     } catch (JAXRPCExceptionBase e) {
/* 147 */       throw new SerializationException(e);
/* 148 */     } catch (Exception e) {
/* 149 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
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
/* 160 */       return internalDeserialize(name, reader, context);
/* 161 */     } catch (DeserializationException e) {
/* 162 */       throw e;
/* 163 */     } catch (JAXRPCExceptionBase e) {
/* 164 */       throw new DeserializationException(e);
/* 165 */     } catch (Exception e) {
/* 166 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
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
/* 178 */     context.beginSerializing(obj);
/*     */     
/* 180 */     boolean pushedEncodingStyle = false;
/* 181 */     if (this.encodingStyle != null) {
/* 182 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 185 */     if (this.encodeType) {
/* 186 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 187 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/* 189 */     if (obj == null) {
/* 190 */       if (!this.isNullable) {
/* 191 */         throw new SerializationException("literal.unexpectedNull");
/*     */       }
/*     */       
/* 194 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     } else {
/* 196 */       writeAdditionalNamespaceDeclarations(obj, writer);
/* 197 */       doSerializeAttributes(obj, writer, context);
/* 198 */       doSerialize(obj, writer, context);
/*     */     } 
/*     */     
/* 201 */     if (pushedEncodingStyle) {
/* 202 */       context.popEncodingStyle();
/*     */     }
/*     */     
/* 205 */     context.doneSerializing(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 214 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/* 216 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 218 */       if (name != null) {
/* 219 */         QName actualName = reader.getName();
/* 220 */         if (!actualName.equals(name)) {
/* 221 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 227 */       verifyType(reader);
/*     */       
/* 229 */       Attributes attrs = reader.getAttributes();
/* 230 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 231 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/*     */       
/* 233 */       Object obj = null;
/*     */       
/* 235 */       if (isNull) {
/* 236 */         if (!this.isNullable) {
/* 237 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/* 239 */         reader.next();
/*     */       } else {
/* 241 */         obj = doDeserialize(reader, context);
/*     */       } 
/*     */       
/* 244 */       return obj;
/*     */     } finally {
/* 246 */       if (pushedEncodingStyle) {
/* 247 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 256 */     if (this.typeRegistry == null) {
/* 257 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/* 259 */     if (this.componentDeserializer == null) {
/* 260 */       this.componentDeserializer = (JAXRPCDeserializer)this.componentSerializer;
/*     */     }
/*     */     
/* 263 */     ArrayList<Object> values = null;
/* 264 */     Object currentValue = null;
/* 265 */     Object array = null;
/* 266 */     QName elementName = null;
/* 267 */     elementName = reader.getName();
/*     */ 
/*     */     
/* 270 */     if (reader.getState() == 1 && elementName.equals(this.type)) {
/*     */ 
/*     */       
/* 273 */       values = new ArrayList();
/*     */       while (true) {
/* 275 */         elementName = reader.getName();
/* 276 */         if (reader.getState() == 1 && elementName.equals(this.type)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 281 */           currentValue = this.componentDeserializer.deserialize(this.type, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 287 */           if (currentValue != null) {
/* 288 */             values.add(currentValue);
/*     */           } else {
/* 290 */             throw new DeserializationException("literal unexpected null");
/*     */           } 
/* 292 */           XMLReaderUtil.verifyReaderState(reader, 2);
/* 293 */           reader.nextElementContent();
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 302 */       array = Array.newInstance(this.componentType, values.size());
/* 303 */       Object[] valuesArray = values.toArray();
/*     */ 
/*     */       
/* 306 */       for (int i = 0; i < valuesArray.length; i++) {
/* 307 */         Array.set(array, i, valuesArray[i]);
/*     */       }
/*     */     } 
/*     */     
/* 311 */     return array;
/*     */   }
/*     */   
/*     */   protected void verifyType(XMLReader reader) throws Exception {
/* 315 */     QName actualType = getType(reader);
/*     */     
/* 317 */     if (actualType != null && 
/* 318 */       !actualType.equals(this.type) && !isAcceptableType(actualType)) {
/* 319 */       throw new DeserializationException("xsd.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAcceptableType(QName actualType) {
/* 327 */     return false;
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
/* 342 */     doSerializeInstance(obj, writer, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doSerializeInstance(Object instance, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 351 */     if (this.typeRegistry == null) {
/* 352 */       throw new EncodingException("initializable.not.initialized");
/*     */     }
/* 354 */     if (instance.getClass().isArray()) {
/* 355 */       int length = Array.getLength(instance);
/*     */       
/* 357 */       for (int i = 0; i < length; i++) {
/* 358 */         Object parameter = Array.get(instance, i);
/* 359 */         if (this.componentSerializer != null);
/*     */         
/* 361 */         ((CombinedSerializer)this.componentSerializer).serialize(parameter, null, null, writer, context);
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
/* 379 */     return "http://java.sun.com/jax-rpc-ri/1.0/streaming/";
/*     */   }
/*     */   
/*     */   public static QName getType(XMLReader reader) throws Exception {
/* 383 */     QName type = null;
/*     */     
/* 385 */     Attributes attrs = reader.getAttributes();
/* 386 */     String typeVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/* 388 */     if (typeVal != null) {
/* 389 */       type = XMLReaderUtil.decodeQName(reader, typeVal);
/*     */     }
/*     */     
/* 392 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCSerializer getParameterSerializer(int index, Object parameter) throws Exception {
/* 400 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JAXRPCDeserializer getParameterDeserializer(int index, XMLReader reader) throws Exception {
/* 408 */     return null;
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
/* 419 */       if (state == null) {
/* 420 */         deserializationState = new SOAPDeserializationState();
/*     */       } else {
/* 422 */         deserializationState = state;
/*     */       } 
/*     */       
/* 425 */       deserializationState.setInstance(instance);
/* 426 */       if (deserializationState.getBuilder() == null) {
/* 427 */         if (builder == null) {
/* 428 */           throw new IllegalArgumentException();
/*     */         }
/* 430 */         deserializationState.setBuilder(builder);
/*     */       } 
/*     */       
/* 433 */       SOAPDeserializationState memberState = (SOAPDeserializationState)member;
/*     */       
/* 435 */       memberState.registerListener(deserializationState, memberIndex);
/*     */       
/* 437 */       return deserializationState;
/* 438 */     } catch (JAXRPCExceptionBase e) {
/* 439 */       throw new DeserializationException(e);
/* 440 */     } catch (Exception e) {
/* 441 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initialize(InternalTypeMappingRegistry registry) throws Exception {
/* 448 */     if (this.typeRegistry != null) {
/*     */       return;
/*     */     }
/*     */     
/* 452 */     this.typeRegistry = registry;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralArraySerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */