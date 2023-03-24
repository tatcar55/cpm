/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.AttachmentData;
/*     */ import com.sun.xml.wss.impl.c14n.Canonicalizer;
/*     */ import com.sun.xml.wss.impl.c14n.CanonicalizerFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ACOTransform
/*     */   extends TransformService
/*     */ {
/*     */   public void init(TransformParameterSpec params) throws InvalidAlgorithmParameterException {}
/*     */   
/*     */   public void marshalParams(XMLStructure parent, XMLCryptoContext context) throws MarshalException {}
/*     */   
/*     */   public void init(XMLStructure parent, XMLCryptoContext context) throws InvalidAlgorithmParameterException {}
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  87 */     return null;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext context) throws TransformException {
/*  91 */     if (data instanceof AttachmentData) {
/*  92 */       ByteArrayOutputStream os = null;
/*  93 */       return canonicalize((AttachmentData)data, os);
/*     */     } 
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext context, OutputStream os) throws TransformException {
/*  99 */     if (data instanceof AttachmentData) {
/* 100 */       return canonicalize((AttachmentData)data, os);
/*     */     }
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String feature) {
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   private Data canonicalize(AttachmentData attachmentData, OutputStream os) throws TransformException {
/*     */     try {
/* 111 */       Attachment attachment = attachmentData.getAttachment();
/* 112 */       InputStream is = attachment.asInputStream();
/* 113 */       OutputStream byteStream = null;
/* 114 */       if (os == null) {
/* 115 */         byteStream = new ByteArrayOutputStream();
/*     */       } else {
/* 117 */         byteStream = os;
/*     */       } 
/* 119 */       Canonicalizer canonicalizer = CanonicalizerFactory.getCanonicalizer(attachment.getContentType());
/* 120 */       InputStream resultIs = canonicalizer.canonicalize(is, byteStream);
/* 121 */       if (resultIs != null) return new OctetStreamData(resultIs); 
/* 122 */       return null;
/* 123 */     } catch (Exception ex) {
/* 124 */       throw new TransformException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\ACOTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */