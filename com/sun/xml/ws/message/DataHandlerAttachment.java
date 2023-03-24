/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataHandlerAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final DataHandler dh;
/*     */   private final String contentId;
/*     */   String contentIdNoAngleBracket;
/*     */   
/*     */   public DataHandlerAttachment(@NotNull String contentId, @NotNull DataHandler dh) {
/*  72 */     this.dh = dh;
/*  73 */     this.contentId = contentId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentId() {
/*  78 */     if (this.contentIdNoAngleBracket == null) {
/*  79 */       this.contentIdNoAngleBracket = this.contentId;
/*  80 */       if (this.contentIdNoAngleBracket != null && this.contentIdNoAngleBracket.charAt(0) == '<')
/*  81 */         this.contentIdNoAngleBracket = this.contentIdNoAngleBracket.substring(1, this.contentIdNoAngleBracket.length() - 1); 
/*     */     } 
/*  83 */     return this.contentIdNoAngleBracket;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  87 */     return this.dh.getContentType();
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*     */     try {
/*  92 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/*  93 */       this.dh.writeTo(os);
/*  94 */       return os.toByteArray();
/*  95 */     } catch (IOException e) {
/*  96 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/* 101 */     return this.dh;
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*     */     try {
/* 106 */       return new StreamSource(this.dh.getInputStream());
/* 107 */     } catch (IOException e) {
/* 108 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/*     */     try {
/* 114 */       return this.dh.getInputStream();
/* 115 */     } catch (IOException e) {
/* 116 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 121 */     this.dh.writeTo(os);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 125 */     AttachmentPart part = saaj.createAttachmentPart();
/* 126 */     part.setDataHandler(this.dh);
/* 127 */     part.setContentId(this.contentId);
/* 128 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\DataHandlerAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */