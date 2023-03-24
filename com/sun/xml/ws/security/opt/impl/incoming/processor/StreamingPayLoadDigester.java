/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.DigesterOutputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.stream.StreamFilter;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class StreamingPayLoadDigester
/*     */   implements StreamFilter
/*     */ {
/*  68 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */   
/*  71 */   private XMLStreamReader reader = null;
/*  72 */   private Reference ref = null;
/*  73 */   private StAXEXC14nCanonicalizerImpl canonicalizer = null;
/*  74 */   private int index = 0;
/*     */   private boolean payLoad = false;
/*     */   private boolean digestDone = false;
/*     */   
/*     */   public StreamingPayLoadDigester(Reference ref, XMLStreamReader reader, StAXEXC14nCanonicalizerImpl canonicalizer, boolean payLoad) {
/*  79 */     this.ref = ref;
/*  80 */     this.reader = reader;
/*  81 */     this.canonicalizer = canonicalizer;
/*  82 */     this.payLoad = payLoad;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean accept(XMLStreamReader xMLStreamReader) {
/*     */     try {
/*  91 */       if (!this.digestDone) {
/*  92 */         StreamUtil.writeCurrentEvent(xMLStreamReader, (XMLStreamWriter)this.canonicalizer);
/*  93 */         if (this.reader.getEventType() == 1) {
/*  94 */           this.index++;
/*  95 */         } else if (this.reader.getEventType() == 2) {
/*  96 */           this.index--;
/*     */           
/*  98 */           if (this.index == 0) {
/*  99 */             byte[] originalDigest = this.ref.getDigestValue();
/* 100 */             if (logger.isLoggable(Level.FINEST)) {
/* 101 */               logger.log(Level.FINEST, LogStringsMessages.WSS_1763_ACTUAL_DEGEST_VALUE(new String(originalDigest)));
/*     */             }
/* 103 */             this.canonicalizer.writeEndDocument();
/* 104 */             this.digestDone = true;
/* 105 */             if (this.canonicalizer.getOutputStream() instanceof DigesterOutputStream) {
/* 106 */               byte[] calculatedDigest = ((DigesterOutputStream)this.canonicalizer.getOutputStream()).getDigestValue();
/* 107 */               if (logger.isLoggable(Level.FINEST)) {
/* 108 */                 logger.log(Level.FINEST, LogStringsMessages.WSS_1762_CALCULATED_DIGEST_VALUE(new String(calculatedDigest)));
/*     */               }
/* 110 */               if (!Arrays.equals(originalDigest, calculatedDigest)) {
/* 111 */                 XMLSignatureException xe = new XMLSignatureException(LogStringsMessages.WSS_1717_ERROR_PAYLOAD_VERIFICATION());
/* 112 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1717_ERROR_PAYLOAD_VERIFICATION(), xe);
/* 113 */                 throw new WebServiceException(xe);
/*     */               } 
/* 115 */               if (logger.isLoggable(Level.FINEST)) {
/* 116 */                 if (!this.payLoad) {
/* 117 */                   logger.log(Level.FINEST, "Digest verification of Body was successful");
/*     */                 } else {
/* 119 */                   logger.log(Level.FINEST, "Digest verification of PayLoad was successful");
/*     */                 }
/*     */               
/*     */               }
/* 123 */             } else if (this.canonicalizer.getOutputStream() instanceof ByteArrayOutputStream) {
/* 124 */               byte[] canonicalizedData = ((ByteArrayOutputStream)this.canonicalizer.getOutputStream()).toByteArray();
/* 125 */               byte[] calculatedDigest = null;
/* 126 */               MessageDigest md = null;
/* 127 */               String algo = null;
/*     */               try {
/* 129 */                 algo = (this.ref != null) ? StreamUtil.convertDigestAlgorithm(this.ref.getDigestMethod().getAlgorithm()) : "SHA-1";
/*     */ 
/*     */                 
/* 132 */                 md = MessageDigest.getInstance(algo);
/*     */               }
/* 134 */               catch (NoSuchAlgorithmException nsae) {
/* 135 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1705_INVALID_DIGEST_ALGORITHM(algo), nsae);
/* 136 */                 throw new WebServiceException(nsae);
/*     */               } 
/* 138 */               calculatedDigest = md.digest(canonicalizedData);
/* 139 */               if (logger.isLoggable(Level.FINEST)) {
/* 140 */                 logger.log(Level.FINEST, LogStringsMessages.WSS_1762_CALCULATED_DIGEST_VALUE(new String(calculatedDigest)));
/* 141 */                 logger.log(Level.FINEST, LogStringsMessages.WSS_1764_CANONICALIZED_PAYLOAD_VALUE(new String(canonicalizedData)));
/*     */               } 
/* 143 */               if (!Arrays.equals(originalDigest, calculatedDigest)) {
/* 144 */                 XMLSignatureException xe = new XMLSignatureException(LogStringsMessages.WSS_1717_ERROR_PAYLOAD_VERIFICATION());
/* 145 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1717_ERROR_PAYLOAD_VERIFICATION(), xe);
/* 146 */                 throw new WebServiceException(xe);
/*     */               } 
/* 148 */               if (logger.isLoggable(Level.FINEST)) {
/* 149 */                 if (!this.payLoad) {
/* 150 */                   logger.log(Level.FINEST, "Digest verification of Body was successful");
/*     */                 } else {
/* 152 */                   logger.log(Level.FINEST, "Digest verification of PayLoad was successful");
/*     */                 }
/*     */               
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 160 */     } catch (XMLStreamException ex) {
/* 161 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1717_ERROR_PAYLOAD_VERIFICATION(), ex);
/* 162 */       throw new WebServiceException(ex);
/*     */     } 
/* 164 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\StreamingPayLoadDigester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */