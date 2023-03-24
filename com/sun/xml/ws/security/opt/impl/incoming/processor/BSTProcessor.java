/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BSTProcessor
/*     */   implements StreamFilter
/*     */ {
/*  67 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */ 
/*     */   
/*  70 */   private byte[] bstValue = null;
/*  71 */   private X509Certificate cert = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getValue() {
/*  77 */     return this.bstValue;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/*  81 */     return this.cert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(XMLStreamReader reader) {
/*  89 */     if (reader.getEventType() == 4) {
/*  90 */       if (reader instanceof XMLStreamReaderEx) {
/*     */         try {
/*  92 */           CharSequence data = ((XMLStreamReaderEx)reader).getPCDATA();
/*  93 */           if (data instanceof Base64Data) {
/*  94 */             Base64Data binaryData = (Base64Data)data;
/*     */             
/*  96 */             buildCertificate(binaryData.getInputStream());
/*  97 */             return true;
/*     */           } 
/*  99 */         } catch (XMLStreamException ex) {
/* 100 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex), ex);
/* 101 */           throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/* 102 */         } catch (IOException ex) {
/* 103 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex), ex);
/* 104 */           throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1603_ERROR_READING_STREAM(ex));
/*     */         } 
/*     */       }
/*     */       
/*     */       try {
/* 109 */         this.bstValue = Base64.decode(reader.getText());
/* 110 */         buildCertificate(new ByteArrayInputStream(this.bstValue));
/*     */       }
/* 112 */       catch (Base64DecodingException ex) {
/* 113 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex), ex);
/* 114 */         throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1604_ERROR_DECODING_BASE_64_DATA(ex));
/*     */       } 
/*     */     } 
/* 117 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void buildCertificate(InputStream certValue) {
/*     */     try {
/* 127 */       CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 128 */       this.cert = (X509Certificate)certFact.generateCertificate(certValue);
/* 129 */     } catch (CertificateException ex) {
/* 130 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex), ex);
/* 131 */       throw new XWSSecurityRuntimeException(LogStringsMessages.WSS_1605_ERROR_GENERATING_CERTIFICATE(ex));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\BSTProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */