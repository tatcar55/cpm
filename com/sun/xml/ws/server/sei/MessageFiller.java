/*     */ package com.sun.xml.ws.server.sei;
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
/*     */ public abstract class MessageFiller
/*     */ {
/*     */   protected final int methodPos;
/*     */   
/*     */   protected MessageFiller(int methodPos) {
/*  77 */     this.methodPos = methodPos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class AttachmentFiller
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
/*  95 */       super(param.getIndex());
/*  96 */       this.param = param;
/*  97 */       this.getter = getter;
/*  98 */       this.mimeType = param.getBinding().getMimeType();
/*     */       try {
/* 100 */         this.contentIdPart = URLEncoder.encode(param.getPartName(), "UTF-8") + '=';
/* 101 */       } catch (UnsupportedEncodingException e) {
/* 102 */         throw new WebServiceException(e);
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
/* 116 */       Class<?> type = (Class)(param.getTypeInfo()).type;
/* 117 */       if (DataHandler.class.isAssignableFrom(type) || Source.class.isAssignableFrom(type))
/* 118 */         return new MessageFiller.DataHandlerFiller(param, getter); 
/* 119 */       if (byte[].class == type)
/* 120 */         return new MessageFiller.ByteArrayFiller(param, getter); 
/* 121 */       if (MessageFiller.isXMLMimeType(param.getBinding().getMimeType())) {
/* 122 */         return new MessageFiller.JAXBFiller(param, getter);
/*     */       }
/* 124 */       return new MessageFiller.DataHandlerFiller(param, getter);
/*     */     }
/*     */ 
/*     */     
/*     */     String getContentId() {
/* 129 */       return this.contentIdPart + UUID.randomUUID() + "@jaxws.sun.com";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayFiller extends AttachmentFiller {
/*     */     protected ByteArrayFiller(ParameterImpl param, ValueGetter getter) {
/* 135 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 139 */       String contentId = getContentId();
/* 140 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 141 */       if (obj != null) {
/* 142 */         ByteArrayAttachment byteArrayAttachment = new ByteArrayAttachment(contentId, (byte[])obj, this.mimeType);
/* 143 */         msg.getAttachments().add((Attachment)byteArrayAttachment);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DataHandlerFiller extends AttachmentFiller {
/*     */     protected DataHandlerFiller(ParameterImpl param, ValueGetter getter) {
/* 150 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 154 */       String contentId = getContentId();
/* 155 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 156 */       DataHandler dh = (obj instanceof DataHandler) ? (DataHandler)obj : new DataHandler(obj, this.mimeType);
/* 157 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(contentId, dh);
/* 158 */       msg.getAttachments().add((Attachment)dataHandlerAttachment);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class JAXBFiller extends AttachmentFiller {
/*     */     protected JAXBFiller(ParameterImpl param, ValueGetter getter) {
/* 164 */       super(param, getter);
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 168 */       String contentId = getContentId();
/* 169 */       Object obj = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 170 */       JAXBAttachment jAXBAttachment = new JAXBAttachment(contentId, obj, this.param.getXMLBridge(), this.mimeType);
/* 171 */       msg.getAttachments().add((Attachment)jAXBAttachment);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static final class Header
/*     */     extends MessageFiller
/*     */   {
/*     */     private final XMLBridge bridge;
/*     */     private final ValueGetter getter;
/*     */     
/*     */     public Header(int methodPos, XMLBridge bridge, ValueGetter getter) {
/* 183 */       super(methodPos);
/* 184 */       this.bridge = bridge;
/* 185 */       this.getter = getter;
/*     */     }
/*     */     
/*     */     public void fillIn(Object[] methodArgs, Object returnValue, Message msg) {
/* 189 */       Object value = (this.methodPos == -1) ? returnValue : this.getter.get(methodArgs[this.methodPos]);
/* 190 */       msg.getMessageHeaders().add(Headers.create(this.bridge, value));
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isXMLMimeType(String mimeType) {
/* 195 */     return (mimeType.equals("text/xml") || mimeType.equals("application/xml"));
/*     */   }
/*     */   
/*     */   public abstract void fillIn(Object[] paramArrayOfObject, Object paramObject, Message paramMessage);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\sei\MessageFiller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */