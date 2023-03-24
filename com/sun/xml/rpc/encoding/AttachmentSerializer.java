/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.simpletype.AttachmentEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.SimpleTypeEncoder;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterUtil;
/*     */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.Iterator;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttachmentSerializer
/*     */   extends SimpleTypeSerializer
/*     */ {
/*     */   protected AttachmentEncoder attachmentEncoder;
/*     */   protected boolean serializerAsAttachment;
/*  57 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/*  61 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
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
/*     */   public AttachmentSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, boolean serializerAsAttachment, SimpleTypeEncoder encoder) {
/*  73 */     this(type, encodeType, isNullable, encodingStyle, serializerAsAttachment, encoder, SOAPVersion.SOAP_11);
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
/*     */   public AttachmentSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, boolean serializerAsAttachment, SimpleTypeEncoder encoder, SOAPVersion ver) {
/*  92 */     super(type, encodeType, isNullable, encodingStyle, encoder);
/*  93 */     init(ver);
/*  94 */     this.serializerAsAttachment = serializerAsAttachment;
/*  95 */     if (encoder instanceof AttachmentEncoder) {
/*  96 */       this.attachmentEncoder = (AttachmentEncoder)encoder;
/*  97 */     } else if (serializerAsAttachment) {
/*  98 */       throw new SerializationException("soap.no.attachment.encoder.and.serializeAsAttachment", type.toString());
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
/*     */   public AttachmentSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, boolean serializerAsAttachment, AttachmentEncoder encoder) {
/* 112 */     this(type, encodeType, isNullable, encodingStyle, serializerAsAttachment, encoder, SOAPVersion.SOAP_11);
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
/*     */   public AttachmentSerializer(QName type, boolean encodeType, boolean isNullable, String encodingStyle, boolean serializerAsAttachment, AttachmentEncoder encoder, SOAPVersion ver) {
/* 131 */     super(type, encodeType, isNullable, encodingStyle, (SimpleTypeEncoder)null);
/* 132 */     init(ver);
/* 133 */     this.serializerAsAttachment = serializerAsAttachment;
/* 134 */     this.attachmentEncoder = encoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void serialize(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/* 144 */     if (this.serializerAsAttachment) {
/* 145 */       serializeAsAttachment(obj, name, callback, writer, context);
/*     */     }
/* 147 */     else if (this.encoder != null) {
/* 148 */       super.serialize(obj, name, callback, writer, context);
/*     */     } else {
/* 150 */       throw new UnsupportedOperationException();
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
/*     */   private void serializeAsAttachment(Object obj, QName name, SerializerCallback callback, XMLWriter writer, SOAPSerializationContext context) {
/*     */     try {
/* 163 */       writer.startElement((name != null) ? name : this.type);
/* 164 */       if (obj == null) {
/* 165 */         if (!this.isNullable) {
/* 166 */           throw new SerializationException("xsd.unexpectedNull");
/*     */         }
/*     */         
/* 169 */         writer.writeAttributeUnquoted(QNAME_XSI_NIL, "1");
/*     */       } else {
/* 171 */         if (this.encodeType) {
/* 172 */           String attrVal = XMLWriterUtil.encodeQName(writer, this.type);
/* 173 */           writer.writeAttributeUnquoted(QNAME_XSI_TYPE, attrVal);
/*     */         } 
/* 175 */         String id = context.nextID();
/* 176 */         writer.writeAttribute(this.soapEncodingConstants.getQNameAttrHREF(), "cid:" + id);
/*     */ 
/*     */         
/* 179 */         SOAPMessage message = context.getMessage();
/* 180 */         AttachmentPart attachment = message.createAttachmentPart(this.attachmentEncoder.objectToDataHandler(obj));
/*     */ 
/*     */         
/* 183 */         attachment.setContentId(id);
/* 184 */         message.addAttachmentPart(attachment);
/*     */       } 
/*     */       
/* 187 */       writer.endElement();
/* 188 */     } catch (SerializationException e) {
/* 189 */       throw e;
/* 190 */     } catch (JAXRPCExceptionBase e) {
/* 191 */       throw new SerializationException(e);
/* 192 */     } catch (Exception e) {
/* 193 */       throw new SerializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(QName name, XMLReader reader, SOAPDeserializationContext context) {
/* 203 */     boolean pushedEncodingStyle = false;
/*     */     
/*     */     try {
/* 206 */       String href = getHRef(reader);
/* 207 */       if (href != null) {
/* 208 */         skipEmptyContent(reader);
/*     */         
/* 210 */         SOAPMessage message = context.getMessage();
/* 211 */         MimeHeaders mimeHeaders = new MimeHeaders();
/* 212 */         mimeHeaders.addHeader("Content-Id", href.substring(4));
/* 213 */         Iterator<AttachmentPart> attachments = message.getAttachments(mimeHeaders);
/* 214 */         if (!attachments.hasNext()) {
/* 215 */           throw new DeserializationException("soap.missing.attachment.for.id", href);
/*     */         }
/*     */ 
/*     */         
/* 219 */         AttachmentPart attachment = attachments.next();
/* 220 */         if (attachments.hasNext()) {
/* 221 */           throw new DeserializationException("soap.multiple.attachments.for.id", href);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 226 */         return deserialize(attachment.getDataHandler(), context);
/*     */       } 
/* 228 */     } catch (DeserializationException e) {
/* 229 */       throw e;
/* 230 */     } catch (JAXRPCExceptionBase e) {
/* 231 */       throw new DeserializationException(e);
/* 232 */     } catch (Exception e) {
/* 233 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */     
/* 236 */     return super.deserialize(name, reader, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object deserialize(DataHandler dataHandler, SOAPDeserializationContext context) throws DeserializationException, UnsupportedOperationException {
/* 244 */     if (this.attachmentEncoder == null)
/* 245 */       throw new UnsupportedOperationException(); 
/*     */     try {
/* 247 */       return this.attachmentEncoder.dataHandlerToObject(dataHandler);
/* 248 */     } catch (DeserializationException e) {
/* 249 */       throw e;
/* 250 */     } catch (JAXRPCExceptionBase e) {
/* 251 */       throw new DeserializationException(e);
/* 252 */     } catch (Exception e) {
/* 253 */       throw new DeserializationException(new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getHRef(XMLReader reader) throws Exception {
/* 259 */     String href = null;
/*     */     
/* 261 */     Attributes attrs = reader.getAttributes();
/* 262 */     href = attrs.getValue("", "href");
/*     */     
/* 264 */     if (href != null && 
/* 265 */       !href.startsWith("cid:")) {
/* 266 */       throw new DeserializationException("soap.nonLocalReference", href);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 272 */     return href;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\AttachmentSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */