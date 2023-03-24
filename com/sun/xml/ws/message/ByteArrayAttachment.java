/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ByteArrayAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private final String contentId;
/*     */   private byte[] data;
/*     */   private int start;
/*     */   private final int len;
/*     */   private final String mimeType;
/*     */   
/*     */   public ByteArrayAttachment(@NotNull String contentId, byte[] data, int start, int len, String mimeType) {
/*  72 */     this.contentId = contentId;
/*  73 */     this.data = data;
/*  74 */     this.start = start;
/*  75 */     this.len = len;
/*  76 */     this.mimeType = mimeType;
/*     */   }
/*     */   
/*     */   public ByteArrayAttachment(@NotNull String contentId, byte[] data, String mimeType) {
/*  80 */     this(contentId, data, 0, data.length, mimeType);
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  84 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  88 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*  92 */     if (this.start != 0 || this.len != this.data.length) {
/*     */       
/*  94 */       byte[] exact = new byte[this.len];
/*  95 */       System.arraycopy(this.data, this.start, exact, 0, this.len);
/*  96 */       this.start = 0;
/*  97 */       this.data = exact;
/*     */     } 
/*  99 */     return this.data;
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/* 103 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, this.start, this.len, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/* 107 */     return new StreamSource(asInputStream());
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/* 111 */     return new ByteArrayInputStream(this.data, this.start, this.len);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 115 */     os.write(asByteArray());
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 119 */     AttachmentPart part = saaj.createAttachmentPart();
/* 120 */     part.setDataHandler(asDataHandler());
/* 121 */     part.setContentId(this.contentId);
/* 122 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\ByteArrayAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */