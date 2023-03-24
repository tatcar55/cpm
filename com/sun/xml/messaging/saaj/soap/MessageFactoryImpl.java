/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ParseException;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Message1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Message1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.util.TeeInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageFactoryImpl
/*     */   extends MessageFactory
/*     */ {
/*  66 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*     */   protected OutputStream listener;
/*     */   
/*     */   protected boolean lazyAttachments = false;
/*     */ 
/*     */   
/*     */   public OutputStream listen(OutputStream newListener) {
/*  75 */     OutputStream oldListener = this.listener;
/*  76 */     this.listener = newListener;
/*  77 */     return oldListener;
/*     */   }
/*     */   
/*     */   public SOAPMessage createMessage() throws SOAPException {
/*  81 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage createMessage(boolean isFastInfoset, boolean acceptFastInfoset) throws SOAPException {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SOAPMessage createMessage(MimeHeaders headers, InputStream in) throws SOAPException, IOException {
/*     */     TeeInputStream teeInputStream;
/*  92 */     String contentTypeString = MessageImpl.getContentType(headers);
/*     */     
/*  94 */     if (this.listener != null) {
/*  95 */       teeInputStream = new TeeInputStream(in, this.listener);
/*     */     }
/*     */     
/*     */     try {
/*  99 */       ContentType contentType = new ContentType(contentTypeString);
/* 100 */       int stat = MessageImpl.identifyContentType(contentType);
/*     */       
/* 102 */       if (MessageImpl.isSoap1_1Content(stat))
/* 103 */         return (SOAPMessage)new Message1_1Impl(headers, contentType, stat, (InputStream)teeInputStream); 
/* 104 */       if (MessageImpl.isSoap1_2Content(stat)) {
/* 105 */         return (SOAPMessage)new Message1_2Impl(headers, contentType, stat, (InputStream)teeInputStream);
/*     */       }
/* 107 */       log.severe("SAAJ0530.soap.unknown.Content-Type");
/* 108 */       throw new SOAPExceptionImpl("Unrecognized Content-Type");
/*     */     }
/* 110 */     catch (ParseException e) {
/* 111 */       log.severe("SAAJ0531.soap.cannot.parse.Content-Type");
/* 112 */       throw new SOAPExceptionImpl("Unable to parse content type: " + e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static final String getContentType(MimeHeaders headers) {
/* 118 */     String[] values = headers.getHeader("Content-Type");
/* 119 */     if (values == null) {
/* 120 */       return null;
/*     */     }
/* 122 */     return values[0];
/*     */   }
/*     */   
/*     */   public void setLazyAttachmentOptimization(boolean flag) {
/* 126 */     this.lazyAttachments = flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\MessageFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */