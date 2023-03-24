/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
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
/*     */ public final class JAXBAttachment
/*     */   implements Attachment, DataSource
/*     */ {
/*     */   private final String contentId;
/*     */   private final String mimeType;
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   
/*     */   public JAXBAttachment(@NotNull String contentId, Object jaxbObject, XMLBridge bridge, String mimeType) {
/*  73 */     this.contentId = contentId;
/*  74 */     this.jaxbObject = jaxbObject;
/*  75 */     this.bridge = bridge;
/*  76 */     this.mimeType = mimeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentId() {
/*  81 */     return this.contentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  86 */     return this.mimeType;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] asByteArray() {
/*  91 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/*  93 */       writeTo((OutputStream)bab);
/*  94 */     } catch (IOException e) {
/*  95 */       throw new WebServiceException(e);
/*     */     } 
/*  97 */     return bab.getRawData();
/*     */   }
/*     */ 
/*     */   
/*     */   public DataHandler asDataHandler() {
/* 102 */     return (DataHandler)new DataSourceStreamingDataHandler(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Source asSource() {
/* 107 */     return new StreamSource(asInputStream());
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream asInputStream() {
/* 112 */     ByteArrayBuffer bab = new ByteArrayBuffer();
/*     */     try {
/* 114 */       writeTo((OutputStream)bab);
/* 115 */     } catch (IOException e) {
/* 116 */       throw new WebServiceException(e);
/*     */     } 
/* 118 */     return bab.newInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/*     */     try {
/* 124 */       this.bridge.marshal(this.jaxbObject, os, null, null);
/* 125 */     } catch (JAXBException e) {
/* 126 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 132 */     AttachmentPart part = saaj.createAttachmentPart();
/* 133 */     part.setDataHandler(asDataHandler());
/* 134 */     part.setContentId(this.contentId);
/* 135 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() throws IOException {
/* 140 */     return asInputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream getOutputStream() throws IOException {
/* 145 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 150 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\JAXBAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */