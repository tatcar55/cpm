/*     */ package com.sun.xml.ws.security.opt.impl.attachment;
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
/*     */ public class AttachmentImpl
/*     */   implements Attachment
/*     */ {
/*     */   private final String contentId;
/*     */   private byte[] data;
/*     */   private final String mimeType;
/*     */   
/*     */   public AttachmentImpl(@NotNull String contentId, byte[] data, String mimeType) {
/*  69 */     this.contentId = contentId;
/*  70 */     this.data = data;
/*  71 */     this.mimeType = mimeType;
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  75 */     return this.contentId;
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  79 */     return this.mimeType;
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*  83 */     return this.data;
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  87 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*  91 */     return new StreamSource(asInputStream());
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/*  95 */     return new ByteArrayInputStream(this.data);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/*  99 */     os.write(asByteArray());
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 103 */     AttachmentPart part = saaj.createAttachmentPart();
/* 104 */     part.setDataHandler(asDataHandler());
/* 105 */     part.setContentId(this.contentId);
/* 106 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\attachment\AttachmentImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */