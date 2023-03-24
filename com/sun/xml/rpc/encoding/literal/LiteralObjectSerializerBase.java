/*     */ package com.sun.xml.rpc.encoding.literal;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.CombinedSerializer;
/*     */ import com.sun.xml.rpc.encoding.DeserializationException;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPInstanceBuilder;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.SerializationException;
/*     */ import com.sun.xml.rpc.encoding.SerializerBase;
/*     */ import com.sun.xml.rpc.encoding.SerializerCallback;
/*     */ import com.sun.xml.rpc.encoding.SerializerConstants;
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderUtil;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import javax.activation.DataHandler;
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
/*     */ public abstract class LiteralObjectSerializerBase
/*     */   implements SerializerConstants, CombinedSerializer
/*     */ {
/*     */   protected QName type;
/*     */   protected boolean isNullable;
/*     */   protected String encodingStyle;
/*     */   protected boolean encodeType = false;
/*     */   
/*     */   protected LiteralObjectSerializerBase(QName type, boolean isNullable, String encodingStyle) {
/*  68 */     init(type, isNullable, encodingStyle, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LiteralObjectSerializerBase(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/*  77 */     init(type, isNullable, encodingStyle, encodeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(QName type, boolean isNullable, String encodingStyle, boolean encodeType) {
/*  86 */     if (type == null) {
/*  87 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  90 */     this.type = type;
/*  91 */     this.isNullable = isNullable;
/*  92 */     this.encodingStyle = encodingStyle;
/*  93 */     this.encodeType = encodeType;
/*     */   }
/*     */   
/*     */   public QName getXmlType() {
/*  97 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean getEncodeType() {
/* 101 */     return false;
/*     */   }
/*     */   
/*     */   public CombinedSerializer getInnermostSerializer() {
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isNullable() {
/* 109 */     return this.isNullable;
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 113 */     return this.encodingStyle;
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
/* 124 */       internalSerialize(value, name, writer, context);
/* 125 */     } catch (SerializationException e) {
/* 126 */       throw e;
/* 127 */     } catch (JAXRPCExceptionBase e) {
/* 128 */       throw new SerializationException(e);
/* 129 */     } catch (Exception e) {
/* 130 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
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
/* 141 */       return internalDeserialize(name, reader, context);
/* 142 */     } catch (DeserializationException e) {
/* 143 */       throw e;
/* 144 */     } catch (JAXRPCExceptionBase e) {
/* 145 */       throw new DeserializationException(e);
/* 146 */     } catch (Exception e) {
/* 147 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(DataHandler dataHandler, SOAPDeserializationContext context) throws DeserializationException, UnsupportedOperationException {
/* 157 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void internalSerialize(Object obj, QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 167 */     context.beginSerializing(obj);
/*     */     
/* 169 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 171 */     boolean pushedEncodingStyle = false;
/* 172 */     if (this.encodingStyle != null) {
/* 173 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 176 */     if (this.encodeType) {
/* 177 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 178 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/* 180 */     if (obj == null) {
/* 181 */       if (!this.isNullable) {
/* 182 */         throw new SerializationException("literal.unexpectedNull");
/*     */       }
/*     */       
/* 185 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/*     */     } else {
/* 187 */       writeAdditionalNamespaceDeclarations(obj, writer);
/* 188 */       doSerializeAttributes(obj, writer, context);
/* 189 */       doSerialize(obj, writer, context);
/*     */     } 
/*     */     
/* 192 */     writer.endElement();
/* 193 */     if (pushedEncodingStyle) {
/* 194 */       context.popEncodingStyle();
/*     */     }
/*     */     
/* 197 */     context.doneSerializing(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object internalDeserialize(QName name, XMLReader reader, SOAPDeserializationContext context) throws Exception {
/* 206 */     boolean pushedEncodingStyle = context.processEncodingStyle(reader);
/*     */     try {
/* 208 */       context.verifyEncodingStyle(this.encodingStyle);
/*     */       
/* 210 */       if (name != null) {
/* 211 */         QName actualName = reader.getName();
/* 212 */         if (!actualName.equals(name)) {
/* 213 */           throw new DeserializationException("xsd.unexpectedElementName", new Object[] { name.toString(), actualName.toString() });
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 219 */       verifyType(reader);
/*     */       
/* 221 */       Attributes attrs = reader.getAttributes();
/* 222 */       String nullVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "nil");
/* 223 */       boolean isNull = (nullVal != null && SerializerBase.decodeBoolean(nullVal));
/*     */       
/* 225 */       Object obj = null;
/*     */       
/* 227 */       if (isNull) {
/* 228 */         if (!this.isNullable) {
/* 229 */           throw new DeserializationException("xsd.unexpectedNull");
/*     */         }
/* 231 */         reader.next();
/*     */       } else {
/* 233 */         obj = doDeserialize(reader, context);
/*     */       } 
/*     */       
/* 236 */       XMLReaderUtil.verifyReaderState(reader, 2);
/* 237 */       return obj;
/*     */     } finally {
/* 239 */       if (pushedEncodingStyle) {
/* 240 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void verifyType(XMLReader reader) throws Exception {
/* 246 */     QName actualType = getType(reader);
/*     */     
/* 248 */     if (actualType != null && 
/* 249 */       !actualType.equals(this.type) && !isAcceptableType(actualType)) {
/* 250 */       throw new DeserializationException("xsd.unexpectedElementType", new Object[] { this.type.toString(), actualType.toString() });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAcceptableType(QName actualType) {
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAdditionalNamespaceDeclarations(Object obj, XMLWriter writer) throws Exception {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doSerialize(Object paramObject, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doSerializeAttributes(Object paramObject, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object doDeserialize(XMLReader paramXMLReader, SOAPDeserializationContext paramSOAPDeserializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMechanismType() {
/* 285 */     return "http://java.sun.com/jax-rpc-ri/1.0/streaming/";
/*     */   }
/*     */   
/*     */   public static QName getType(XMLReader reader) throws Exception {
/* 289 */     QName type = null;
/*     */     
/* 291 */     Attributes attrs = reader.getAttributes();
/* 292 */     String typeVal = attrs.getValue("http://www.w3.org/2001/XMLSchema-instance", "type");
/*     */     
/* 294 */     if (typeVal != null) {
/* 295 */       type = XMLReaderUtil.decodeQName(reader, typeVal);
/*     */     }
/*     */     
/* 298 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPDeserializationState registerWithMemberState(Object instance, SOAPDeserializationState state, Object member, int memberIndex, SOAPInstanceBuilder builder) {
/*     */     try {
/*     */       SOAPDeserializationState deserializationState;
/* 310 */       if (state == null) {
/* 311 */         deserializationState = new SOAPDeserializationState();
/*     */       } else {
/* 313 */         deserializationState = state;
/*     */       } 
/*     */       
/* 316 */       deserializationState.setInstance(instance);
/* 317 */       if (deserializationState.getBuilder() == null) {
/* 318 */         if (builder == null) {
/* 319 */           throw new IllegalArgumentException();
/*     */         }
/* 321 */         deserializationState.setBuilder(builder);
/*     */       } 
/*     */       
/* 324 */       SOAPDeserializationState memberState = (SOAPDeserializationState)member;
/*     */       
/* 326 */       memberState.registerListener(deserializationState, memberIndex);
/*     */       
/* 328 */       return deserializationState;
/* 329 */     } catch (JAXRPCExceptionBase e) {
/* 330 */       throw new DeserializationException(e);
/* 331 */     } catch (Exception e) {
/* 332 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\literal\LiteralObjectSerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */