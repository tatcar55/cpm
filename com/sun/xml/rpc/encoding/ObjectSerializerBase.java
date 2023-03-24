/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.xsd.XSDConstants;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
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
/*     */ public abstract class ObjectSerializerBase
/*     */   extends SerializerBase
/*     */ {
/*     */   protected ObjectSerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  49 */     super(type, encodeType, isNullable, encodingStyle);
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
/*     */   protected abstract Object doDeserialize(SOAPDeserializationState paramSOAPDeserializationState, XMLReader paramXMLReader, SOAPDeserializationContext paramSOAPDeserializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doSerializeInstance(Object paramObject, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/* 116 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 118 */       if (obj == null) {
/* 119 */         if (!this.isNullable) {
/* 120 */           throw new SerializationException("soap.unexpectedNull");
/*     */         }
/* 122 */         serializeNull(name, writer, context);
/*     */       } else {
/* 124 */         writer.startElement((name != null) ? name : this.type);
/* 125 */         if (callback != null) {
/* 126 */           callback.onStartTag(obj, name, writer, context);
/*     */         }
/*     */         
/* 129 */         if (this.encodingStyle != null) {
/* 130 */           pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */         }
/*     */ 
/*     */         
/* 134 */         if (this.encodeType) {
/* 135 */           String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 136 */           writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */         } 
/*     */ 
/*     */         
/* 140 */         doSerializeAttributes(obj, writer, context);
/* 141 */         doSerializeInstance(obj, writer, context);
/* 142 */         writer.endElement();
/*     */       } 
/* 144 */     } catch (SerializationException e) {
/* 145 */       throw e;
/* 146 */     } catch (JAXRPCExceptionBase e) {
/* 147 */       throw new SerializationException(e);
/* 148 */     } catch (Exception e) {
/* 149 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 152 */       if (pushedEncodingStyle) {
/* 153 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeNull(QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 164 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 166 */     boolean pushedEncodingStyle = false;
/* 167 */     if (this.encodingStyle != null) {
/* 168 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 171 */     if (this.encodeType) {
/* 172 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 173 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/*     */     
/* 176 */     writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/* 177 */     writer.endElement();
/* 178 */     if (pushedEncodingStyle) {
/* 179 */       context.popEncodingStyle();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 188 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 190 */       pushedEncodingStyle = context.processEncodingStyle(reader);
/* 191 */       if (this.encodingStyle != null) {
/* 192 */         context.verifyEncodingStyle(this.encodingStyle);
/*     */       }
/* 194 */       if (name != null) {
/* 195 */         verifyName(reader, name);
/*     */       }
/*     */       
/* 198 */       String id = getID(reader);
/* 199 */       boolean isNull = getNullStatus(reader);
/* 200 */       if (!isNull) {
/* 201 */         verifyType(reader);
/*     */         
/* 203 */         SOAPDeserializationState state = null;
/* 204 */         if (id != null) {
/* 205 */           state = context.getStateFor(id);
/* 206 */           state.setDeserializer(this);
/*     */         } 
/*     */         
/* 209 */         Object instance = doDeserialize(state, reader, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 219 */         if (instance instanceof SOAPDeserializationState) {
/* 220 */           state = (SOAPDeserializationState)instance;
/* 221 */         } else if (state != null) {
/* 222 */           state.setInstance(instance);
/*     */         } 
/*     */         
/* 225 */         if (state != null) {
/* 226 */           state.doneReading();
/* 227 */           return state;
/*     */         } 
/*     */         
/* 230 */         return instance;
/*     */       } 
/* 232 */       if (!this.isNullable) {
/* 233 */         throw new DeserializationException("soap.unexpectedNull");
/*     */       }
/*     */       
/* 236 */       skipEmptyContent(reader);
/*     */       
/* 238 */       if (id != null) {
/* 239 */         SOAPDeserializationState state = context.getStateFor(id);
/* 240 */         state.setDeserializer(this);
/* 241 */         state.setInstance(null);
/* 242 */         state.doneReading();
/*     */       } 
/*     */       
/* 245 */       return null;
/*     */     }
/* 247 */     catch (DeserializationException e) {
/* 248 */       throw e;
/* 249 */     } catch (JAXRPCExceptionBase e) {
/* 250 */       throw new DeserializationException(e);
/* 251 */     } catch (Exception e) {
/* 252 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 255 */       if (pushedEncodingStyle) {
/* 256 */         context.popEncodingStyle();
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
/*     */   public static SOAPDeserializationState registerWithMemberState(Object instance, SOAPDeserializationState state, Object member, int memberIndex, SOAPInstanceBuilder builder) {
/*     */     try {
/*     */       SOAPDeserializationState deserializationState;
/* 270 */       if (state == null) {
/* 271 */         deserializationState = new SOAPDeserializationState();
/*     */       } else {
/* 273 */         deserializationState = state;
/*     */       } 
/*     */       
/* 276 */       deserializationState.setInstance(instance);
/* 277 */       if (deserializationState.getBuilder() == null) {
/* 278 */         if (builder == null) {
/* 279 */           throw new IllegalArgumentException();
/*     */         }
/* 281 */         deserializationState.setBuilder(builder);
/*     */       } 
/*     */       
/* 284 */       SOAPDeserializationState memberState = (SOAPDeserializationState)member;
/*     */       
/* 286 */       memberState.registerListener(deserializationState, memberIndex);
/*     */       
/* 288 */       return deserializationState;
/* 289 */     } catch (JAXRPCExceptionBase e) {
/* 290 */       throw new DeserializationException(e);
/* 291 */     } catch (Exception e) {
/* 292 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\ObjectSerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */