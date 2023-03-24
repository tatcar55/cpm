/*     */ package com.sun.xml.ws.security.opt.impl.attachment;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.encoding.DataSourceStreamingDataHandler;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.CryptoProcessor;
/*     */ import com.sun.xml.ws.util.ByteArrayDataSource;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.Key;
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
/*     */ 
/*     */ public class EncryptedAttachment
/*     */   implements Attachment
/*     */ {
/*     */   private Attachment attachment;
/*     */   private String dataAlgo;
/*     */   private Key key;
/*     */   private byte[] data;
/*     */   
/*     */   public EncryptedAttachment(Attachment attachment, String dataAlgo, Key key) throws XWSSecurityException {
/*  74 */     this.attachment = attachment;
/*  75 */     this.dataAlgo = dataAlgo;
/*  76 */     this.key = key;
/*     */     
/*  78 */     doEncryption();
/*     */   }
/*     */   
/*     */   public String getContentId() {
/*  82 */     return this.attachment.getContentId();
/*     */   }
/*     */   
/*     */   public String getContentType() {
/*  86 */     return "application/octet-stream";
/*     */   }
/*     */   
/*     */   public byte[] asByteArray() {
/*  90 */     return this.data;
/*     */   }
/*     */   
/*     */   public DataHandler asDataHandler() {
/*  94 */     return (DataHandler)new DataSourceStreamingDataHandler((DataSource)new ByteArrayDataSource(this.data, getContentType()));
/*     */   }
/*     */   
/*     */   public Source asSource() {
/*  98 */     return new StreamSource(asInputStream());
/*     */   }
/*     */   
/*     */   public InputStream asInputStream() {
/* 102 */     return new ByteArrayInputStream(this.data);
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) throws IOException {
/* 106 */     os.write(asByteArray());
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 110 */     AttachmentPart part = saaj.createAttachmentPart();
/* 111 */     part.setDataHandler(asDataHandler());
/* 112 */     part.setContentId(getContentId());
/* 113 */     saaj.addAttachmentPart(part);
/*     */   }
/*     */   
/*     */   private void doEncryption() throws XWSSecurityException {
/* 117 */     CryptoProcessor dep = new CryptoProcessor(1, this.dataAlgo, this.key);
/* 118 */     this.data = dep.encryptData(this.attachment.asByteArray());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\attachment\EncryptedAttachment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */