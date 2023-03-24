/*     */ package com.sun.xml.ws.encoding.xml;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.ws.encoding.ContentTypeImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public final class XMLCodec
/*     */   implements Codec
/*     */ {
/*     */   public static final String XML_APPLICATION_MIME_TYPE = "application/xml";
/*     */   public static final String XML_TEXT_MIME_TYPE = "text/xml";
/*  68 */   private static final ContentType contentType = (ContentType)new ContentTypeImpl("text/xml");
/*     */ 
/*     */   
/*     */   private WSFeatureList features;
/*     */ 
/*     */   
/*     */   public XMLCodec(WSFeatureList f) {
/*  75 */     this.features = f;
/*     */   }
/*     */   
/*     */   public String getMimeType() {
/*  79 */     return "application/xml";
/*     */   }
/*     */   
/*     */   public ContentType getStaticContentType(Packet packet) {
/*  83 */     return contentType;
/*     */   }
/*     */   
/*     */   public ContentType encode(Packet packet, OutputStream out) {
/*  87 */     String encoding = (String)packet.invocationProperties.get("com.sun.jaxws.rest.contenttype");
/*     */ 
/*     */     
/*  90 */     XMLStreamWriter writer = null;
/*     */     
/*  92 */     if (encoding != null && encoding.length() > 0) {
/*  93 */       writer = XMLStreamWriterFactory.create(out, encoding);
/*     */     } else {
/*  95 */       writer = XMLStreamWriterFactory.create(out);
/*     */     } 
/*     */     
/*     */     try {
/*  99 */       if (packet.getMessage().hasPayload()) {
/* 100 */         writer.writeStartDocument();
/* 101 */         packet.getMessage().writePayloadTo(writer);
/* 102 */         writer.flush();
/*     */       } 
/* 104 */     } catch (XMLStreamException e) {
/* 105 */       throw new WebServiceException(e);
/*     */     } 
/* 107 */     return contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public ContentType encode(Packet packet, WritableByteChannel buffer) {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Codec copy() {
/* 116 */     return this;
/*     */   }
/*     */   
/*     */   public void decode(InputStream in, String contentType, Packet packet) throws IOException {
/* 120 */     Message message = XMLMessage.create(contentType, in, this.features);
/* 121 */     packet.setMessage(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decode(ReadableByteChannel in, String contentType, Packet packet) {
/* 126 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\xml\XMLCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */