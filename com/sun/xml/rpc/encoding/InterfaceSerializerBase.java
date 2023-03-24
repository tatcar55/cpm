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
/*     */ public abstract class InterfaceSerializerBase
/*     */   extends SerializerBase
/*     */ {
/*     */   protected InterfaceSerializerBase(QName type, String encodingStyle, boolean encodeType) {
/*  49 */     super(type, encodeType, false, encodingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InterfaceSerializerBase(QName type, boolean encodeType, boolean isNullable, String encodingStyle) {
/*  58 */     super(type, encodeType, isNullable, encodingStyle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Object doDeserialize(QName paramQName, XMLReader paramXMLReader, SOAPDeserializationContext paramSOAPDeserializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void doSerializeInstance(Object paramObject, QName paramQName, SerializerCallback paramSerializerCallback, XMLWriter paramXMLWriter, SOAPSerializationContext paramSOAPSerializationContext) throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*     */     try {
/*  83 */       if (obj == null) {
/*  84 */         if (!this.isNullable) {
/*  85 */           throw new SerializationException("soap.unexpectedNull");
/*     */         }
/*  87 */         serializeNull(name, writer, context);
/*     */       } else {
/*  89 */         doSerializeInstance(obj, name, callback, writer, context);
/*     */       } 
/*  91 */     } catch (SerializationException e) {
/*  92 */       throw e;
/*  93 */     } catch (JAXRPCExceptionBase e) {
/*  94 */       throw new SerializationException(e);
/*  95 */     } catch (Exception e) {
/*  96 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void serializeNull(QName name, XMLWriter writer, SOAPSerializationContext context) throws Exception {
/* 107 */     writer.startElement((name != null) ? name : this.type);
/*     */     
/* 109 */     boolean pushedEncodingStyle = false;
/* 110 */     if (this.encodingStyle != null) {
/* 111 */       pushedEncodingStyle = context.pushEncodingStyle(this.encodingStyle, writer);
/*     */     }
/*     */     
/* 114 */     if (this.encodeType) {
/* 115 */       String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 116 */       writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_TYPE, attrVal);
/*     */     } 
/*     */     
/* 119 */     writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, "1");
/* 120 */     writer.endElement();
/* 121 */     if (pushedEncodingStyle) {
/* 122 */       context.popEncodingStyle();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 131 */     boolean pushedEncodingStyle = false;
/*     */     try {
/* 133 */       boolean isNull = getNullStatus(reader);
/* 134 */       if (!isNull) {
/* 135 */         return doDeserialize(name, reader, context);
/*     */       }
/* 137 */       if (!this.isNullable) {
/* 138 */         throw new DeserializationException("soap.unexpectedNull");
/*     */       }
/* 140 */       String id = getID(reader);
/* 141 */       skipEmptyContent(reader);
/* 142 */       if (id != null) {
/* 143 */         SOAPDeserializationState state = context.getStateFor(id);
/* 144 */         state.setDeserializer(this);
/* 145 */         state.setInstance(null);
/* 146 */         state.doneReading();
/*     */       } 
/*     */       
/* 149 */       return null;
/*     */     }
/* 151 */     catch (DeserializationException e) {
/* 152 */       throw e;
/* 153 */     } catch (JAXRPCExceptionBase e) {
/* 154 */       throw new DeserializationException(e);
/* 155 */     } catch (Exception e) {
/* 156 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } finally {
/*     */       
/* 159 */       if (pushedEncodingStyle) {
/* 160 */         context.popEncodingStyle();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPDeserializationState registerWithMemberState(Object instance, SOAPDeserializationState state, Object member, int memberIndex, SOAPInstanceBuilder builder) {
/* 171 */     return ObjectSerializerBase.registerWithMemberState(instance, state, member, memberIndex, builder);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\InterfaceSerializerBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */