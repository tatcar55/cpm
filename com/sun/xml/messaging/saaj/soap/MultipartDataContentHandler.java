/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeMultipart;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultipartDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*  51 */   private ActivationDataFlavor myDF = new ActivationDataFlavor(MimeMultipart.class, "multipart/mixed", "Multipart");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  62 */     return new DataFlavor[] { this.myDF };
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) {
/*  75 */     if (this.myDF.equals(df)) {
/*  76 */       return getContent(ds);
/*     */     }
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource ds) {
/*     */     try {
/*  86 */       return new MimeMultipart(ds, new ContentType(ds.getContentType()));
/*     */     }
/*  88 */     catch (Exception e) {
/*  89 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/*  98 */     if (obj instanceof MimeMultipart)
/*     */       
/*     */       try {
/*     */         
/* 102 */         ByteOutputStream baos = null;
/* 103 */         if (os instanceof ByteOutputStream) {
/* 104 */           baos = (ByteOutputStream)os;
/*     */         } else {
/* 106 */           throw new IOException("Input Stream expected to be a com.sun.xml.messaging.saaj.util.ByteOutputStream, but found " + os.getClass().getName());
/*     */         } 
/*     */         
/* 109 */         ((MimeMultipart)obj).writeTo((OutputStream)baos);
/* 110 */       } catch (Exception e) {
/* 111 */         throw new IOException(e.toString());
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\MultipartDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */