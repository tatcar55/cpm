/*     */ package com.sun.xml.ws.message.stream;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.ByteArrayDataSource;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final String contentId;
/*     */   private final String contentType;
/*     */   private final ByteArrayBuffer byteArrayBuffer;
/*     */   private final byte[] data;
/*     */   private final int len;
/*     */   
/*     */   public StreamAttachment(ByteArrayBuffer buffer, String contentId, String contentType) {
/*  74 */     this.contentId = contentId;
/*  75 */     this.contentType = contentType;
/*  76 */     this.byteArrayBuffer = buffer;
/*  77 */     this.data = this.byteArrayBuffer.getRawData();
/*  78 */     this.len = this.byteArrayBuffer.size();
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  82 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  86 */     return this.contentType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] asByteArray() {
/*  92 */     return this.byteArrayBuffer.toByteArray();
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  96 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, 0, this.len, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/* 100 */     return new StreamSource(new ByteArrayInputStream(this.data, 0, this.len));
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/* 104 */     return this.byteArrayBuffer.newInputStream();
/*     */   }
/*     */   
/*     */   public Base64Data asBase64Data() {
/* 108 */     Base64Data base64Data = new Base64Data();
/* 109 */     base64Data.set(this.data, this.len, this.contentType);
/* 110 */     return base64Data;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 114 */     this.byteArrayBuffer.writeTo(os);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 118 */     AttachmentPart part = saaj.createAttachmentPart();
/* 119 */     part.setRawContentBytes(this.data, 0, this.len, getContentType());
/* 120 */     part.setContentId(this.contentId);
/* 121 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\StreamAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */