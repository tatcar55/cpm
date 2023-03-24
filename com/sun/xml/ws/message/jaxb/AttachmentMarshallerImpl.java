/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
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
/*     */ final class AttachmentMarshallerImpl
/*     */   extends AttachmentMarshaller
/*     */ {
/*  67 */   private static final Logger LOGGER = Logger.getLogger(AttachmentMarshallerImpl.class);
/*     */   
/*     */   private AttachmentSet attachments;
/*     */   
/*     */   public AttachmentMarshallerImpl(AttachmentSet attachemnts) {
/*  72 */     this.attachments = attachemnts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void cleanup() {
/*  79 */     this.attachments = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addMtomAttachment(DataHandler data, String elementNamespace, String elementLocalName) {
/*  85 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String addMtomAttachment(byte[] data, int offset, int length, String mimeType, String elementNamespace, String elementLocalName) {
/*  91 */     throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   
/*     */   public String addSwaRefAttachment(DataHandler data) {
/*  96 */     String cid = encodeCid(null);
/*  97 */     DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, data);
/*  98 */     this.attachments.add((Attachment)dataHandlerAttachment);
/*  99 */     cid = "cid:" + cid;
/* 100 */     return cid;
/*     */   }
/*     */   
/*     */   private String encodeCid(String ns) {
/* 104 */     String cid = "example.jaxws.sun.com";
/* 105 */     String name = UUID.randomUUID() + "@";
/* 106 */     if (ns != null && ns.length() > 0) {
/*     */       try {
/* 108 */         URI uri = new URI(ns);
/* 109 */         cid = uri.toURL().getHost();
/* 110 */       } catch (URISyntaxException e) {
/* 111 */         if (LOGGER.isLoggable(Level.INFO)) {
/* 112 */           LOGGER.log(Level.INFO, null, e);
/*     */         }
/* 114 */         return null;
/* 115 */       } catch (MalformedURLException e) {
/*     */         try {
/* 117 */           cid = URLEncoder.encode(ns, "UTF-8");
/* 118 */         } catch (UnsupportedEncodingException e1) {
/* 119 */           throw new WebServiceException(e);
/*     */         } 
/*     */       } 
/*     */     }
/* 123 */     return name + cid;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\AttachmentMarshallerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */