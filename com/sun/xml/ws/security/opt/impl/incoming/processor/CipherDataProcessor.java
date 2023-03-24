/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ public class CipherDataProcessor
/*     */ {
/*  70 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  73 */   private static String CIPHER_VALUE = "CipherValue".intern();
/*  74 */   private static String CIPHER_REFERENCE = "CipherReference".intern();
/*  75 */   private static String TRANSFORMS = "Transforms".intern();
/*  76 */   private static String TRANSFORM = "Transform".intern();
/*  77 */   private Base64Data bd = null;
/*     */   private byte[] cipherValue;
/*  79 */   private JAXBFilterProcessingContext pc = null;
/*     */   boolean hasCipherReference = false;
/*  81 */   String attachmentContentId = null;
/*  82 */   String attachmentContentType = null;
/*     */   
/*     */   public CipherDataProcessor(JAXBFilterProcessingContext pc) {
/*  85 */     this.pc = pc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(XMLStreamReader reader) throws XWSSecurityException {
/*     */     try {
/*  94 */       if (StreamUtil.moveToNextElement(reader)) {
/*  95 */         if (reader.getLocalName() == CIPHER_VALUE) {
/*  96 */           if (reader instanceof XMLStreamReaderEx) {
/*  97 */             reader.next();
/*  98 */             if (reader.getEventType() == 4) {
/*  99 */               CharSequence charSeq = ((XMLStreamReaderEx)reader).getPCDATA();
/* 100 */               if (charSeq instanceof Base64Data) {
/* 101 */                 this.bd = (Base64Data)charSeq;
/*     */               } else {
/*     */                 try {
/* 104 */                   this.cipherValue = Base64.decode(StreamUtil.getCV((XMLStreamReaderEx)reader));
/* 105 */                 } catch (Base64DecodingException ex) {
/* 106 */                   logger.log(Level.SEVERE, LogStringsMessages.WSS_1922_ERROR_DECODING_CIPHERVAL(ex), ex);
/* 107 */                   throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1922_ERROR_DECODING_CIPHERVAL(ex), ex);
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } else {
/*     */ 
/*     */             
/*     */             try {
/* 115 */               this.cipherValue = Base64.decode(StreamUtil.getCV(reader));
/* 116 */             } catch (Base64DecodingException ex) {
/* 117 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1922_ERROR_DECODING_CIPHERVAL(ex), ex);
/* 118 */               throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1922_ERROR_DECODING_CIPHERVAL(ex), ex);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 123 */           reader.next();
/* 124 */           reader.next(); return;
/*     */         } 
/* 126 */         if (reader.getLocalName() == CIPHER_REFERENCE) {
/* 127 */           this.hasCipherReference = true;
/* 128 */           String attachUri = reader.getAttributeValue(null, "URI");
/* 129 */           if (attachUri.startsWith("cid:")) {
/* 130 */             attachUri = attachUri.substring("cid:".length());
/*     */           }
/* 132 */           String algorithm = null;
/* 133 */           if (StreamUtil.moveToNextElement(reader) && 
/* 134 */             reader.getLocalName() == TRANSFORMS) {
/* 135 */             if (StreamUtil.moveToNextElement(reader) && 
/* 136 */               reader.getLocalName() == TRANSFORM) {
/* 137 */               algorithm = reader.getAttributeValue(null, "Algorithm");
/* 138 */               reader.next();
/*     */             } 
/*     */             
/* 141 */             reader.next();
/*     */           } 
/*     */           
/* 144 */           if (algorithm != null && algorithm.equals("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform")) {
/* 145 */             AttachmentSet attachmentSet = this.pc.getSecurityContext().getAttachmentSet();
/* 146 */             Attachment as = attachmentSet.get(attachUri);
/* 147 */             this.cipherValue = as.asByteArray();
/* 148 */             this.attachmentContentId = as.getContentId();
/* 149 */             this.attachmentContentType = as.getContentType();
/* 150 */             reader.next();
/* 151 */             reader.next();
/*     */             return;
/*     */           } 
/* 154 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(algorithm));
/* 155 */           throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(algorithm), new XWSSecurityException(LogStringsMessages.WSS_1928_UNRECOGNIZED_CIPHERTEXT_TRANSFORM(algorithm)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 160 */       reader.next();
/* 161 */     } catch (XMLStreamException ex) {
/* 162 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL(ex), ex);
/* 163 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL(ex), ex);
/*     */     } 
/* 165 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL("unexpected element:" + reader.getLocalName()));
/* 166 */     throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL("unexpected element:" + reader.getLocalName()), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream readAsStream() throws XWSSecurityException {
/* 176 */     if (this.bd != null) {
/*     */       try {
/* 178 */         return this.bd.getInputStream();
/* 179 */       } catch (IOException ex) {
/* 180 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL(ex), ex);
/* 181 */         throw new XWSSecurityException(LogStringsMessages.WSS_1923_ERROR_PROCESSING_CIPHERVAL(ex));
/*     */       } 
/*     */     }
/* 184 */     if (this.cipherValue != null) {
/* 185 */       return new ByteArrayInputStream(this.cipherValue);
/*     */     }
/* 187 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA());
/* 188 */     throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] readAsBytes() throws XWSSecurityException {
/* 197 */     if (this.cipherValue != null) {
/* 198 */       return this.cipherValue;
/*     */     }
/* 200 */     if (this.bd != null) {
/* 201 */       this.cipherValue = this.bd.getExact();
/* 202 */       return this.cipherValue;
/*     */     } 
/* 204 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA());
/* 205 */     throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, LogStringsMessages.WSS_1924_CIPHERVAL_MISSINGIN_CIPHERDATA(), null);
/*     */   }
/*     */   
/*     */   public boolean hasCipherReference() {
/* 209 */     return this.hasCipherReference;
/*     */   }
/*     */   
/*     */   public String getAttachmentContentId() {
/* 213 */     return this.attachmentContentId;
/*     */   }
/*     */   
/*     */   public String getAttachmentContentType() {
/* 217 */     return this.attachmentContentType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\CipherDataProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */