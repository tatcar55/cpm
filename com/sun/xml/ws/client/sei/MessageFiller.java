/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.message.ByteArrayAttachment;
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.ws.message.JAXBAttachment;
/*     */ import com.sun.xml.ws.model.ParameterImpl;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.UUID;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class MessageFiller
/*     */ {
/*     */   protected final int methodPos;
/*     */   
/*     */   protected MessageFiller(int methodPos) {
/*  78 */     this.methodPos = methodPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static abstract class AttachmentFiller
/*     */     extends MessageFiller
/*     */   {
/*     */     protected final ParameterImpl param;
/*     */     
/*     */     protected final ValueGetter getter;
/*     */     
/*     */     protected final String mimeType;
/*     */     
/*     */     private final String contentIdPart;
/*     */ 
/*     */     
/*     */     protected AttachmentFiller(ParameterImpl param, ValueGetter getter) {
/*  96 */       super(param.getIndex());
/*  97 */       this.param = param;
/*  98 */       this.getter = getter;
/*  99 */       this.mimeType = param.getBinding().getMimeType();
/*     */       try {
/* 101 */         this.contentIdPart = URLEncoder.encode(param.getPartName(), "UTF-8") + '=';
/* 102 */       } catch (UnsupportedEncodingException e) {
/* 103 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static MessageFiller createAttachmentFiller(ParameterImpl param, ValueGetter getter) {
/* 117 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 118 */       if (DataHandler.class.isAssignableFrom(type) || Source.class.isAssignableFrom(type))
/* 119 */         return new MessageFiller.DataHandlerFiller(param, getter); 
/* 120 */       if (byte[].class == type)
/* 121 */         return new MessageFiller.ByteArrayFiller(param, getter); 
/* 122 */       if (MessageFiller.isXMLMimeType(param.getBinding().getMimeType())) {
/* 123 */         return new MessageFiller.JAXBFiller(param, getter);
/*     */       }
/* 125 */       return new MessageFiller.DataHandlerFiller(param, getter);
/*     */     }
/*     */ 
/*     */     
/*     */     String getContentId() {
/* 130 */       return this.contentIdPart + UUID.randomUUID() + "@jaxws.sun.com";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayFiller extends AttachmentFiller {
/*     */     protected ByteArrayFiller(ParameterImpl param, ValueGetter getter) {
/* 136 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 139 */       String contentId = getContentId();
/* 140 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 141 */       ByteArrayAttachment byteArrayAttachment = new ByteArrayAttachment(contentId, (byte[])obj, this.mimeType);
/* 142 */       msg.getAttachments().add((Attachment)byteArrayAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DataHandlerFiller extends AttachmentFiller {
/*     */     protected DataHandlerFiller(ParameterImpl param, ValueGetter getter) {
/* 148 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 151 */       String contentId = getContentId();
/* 152 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 153 */       DataHandler dh = (obj instanceof DataHandler) ? (DataHandler)obj : new DataHandler(obj, this.mimeType);
/* 154 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(contentId, dh);
/* 155 */       msg.getAttachments().add((Attachment)dataHandlerAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class JAXBFiller extends AttachmentFiller {
/*     */     protected JAXBFiller(ParameterImpl param, ValueGetter getter) {
/* 161 */       super(param, getter);
/*     */     }
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 164 */       String contentId = getContentId();
/* 165 */       Object obj = this.getter.get(methodArgs[this.methodPos]);
/* 166 */       JAXBAttachment jAXBAttachment = new JAXBAttachment(contentId, obj, this.param.getXMLBridge(), this.mimeType);
/* 167 */       msg.getAttachments().add((Attachment)jAXBAttachment);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Header
/*     */     extends MessageFiller
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueGetter getter;
/*     */     
/*     */     protected Header(int methodPos, XMLBridge bridge, ValueGetter getter) {
/* 179 */       super(methodPos);
/* 180 */       this.bridge = bridge;
/* 181 */       this.getter = getter;
/*     */     }
/*     */     
/*     */     void fillIn(Object[] methodArgs, Message msg) {
/* 185 */       Object value = this.getter.get(methodArgs[this.methodPos]);
/* 186 */       msg.getMessageHeaders().add(Headers.create(this.bridge, value));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 191 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   abstract void fillIn(Object[] paramArrayOfObject, Message paramMessage);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\MessageFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */