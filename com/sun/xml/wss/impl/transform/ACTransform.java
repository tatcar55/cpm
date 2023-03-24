/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import com.sun.xml.wss.impl.c14n.Canonicalizer;
/*     */ import com.sun.xml.wss.impl.c14n.CanonicalizerFactory;
/*     */ import com.sun.xml.wss.impl.c14n.MimeHeaderCanonicalizer;
/*     */ import com.sun.xml.wss.impl.dsig.AttachmentData;
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.OctetStreamData;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.TransformService;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ACTransform
/*     */   extends TransformService
/*     */ {
/*  75 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(TransformParameterSpec transformParameterSpec) throws InvalidAlgorithmParameterException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws InvalidAlgorithmParameterException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  88 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void marshalParams(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws MarshalException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Data canonicalize(AttachmentData attachmentData, OutputStream outputStream) throws TransformException {
/*     */     try {
/*     */       OutputStream outputStream1;
/* 131 */       AttachmentPart attachment = attachmentData.getAttachmentPart();
/* 132 */       Iterator mimeHeaders = attachment.getAllMimeHeaders();
/*     */ 
/*     */       
/* 135 */       MimeHeaderCanonicalizer mHCanonicalizer = CanonicalizerFactory.getMimeHeaderCanonicalizer("US-ASCII");
/* 136 */       byte[] outputHeaderBytes = mHCanonicalizer._canonicalize(mimeHeaders);
/* 137 */       UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
/* 138 */       attachment.getDataHandler().writeTo((OutputStream)unsyncByteArrayOutputStream);
/* 139 */       ByteArrayInputStream byteInputStream = new ByteArrayInputStream(((ByteArrayOutputStream)unsyncByteArrayOutputStream).toByteArray());
/* 140 */       unsyncByteArrayOutputStream.close();
/* 141 */       if (outputStream == null) {
/* 142 */         outputStream1 = new ByteArrayOutputStream();
/*     */       } else {
/* 144 */         outputStream1 = outputStream;
/*     */       } 
/* 146 */       outputStream1.write(outputHeaderBytes);
/* 147 */       Canonicalizer canonicalizer = CanonicalizerFactory.getCanonicalizer(attachment.getContentType());
/* 148 */       InputStream is = canonicalizer.canonicalize(byteInputStream, outputStream1);
/* 149 */       if (is != null) return new OctetStreamData(is); 
/* 150 */       return null;
/* 151 */     } catch (TransformException te) {
/* 152 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1319_ACO_TRANSFORM_ERROR(), te);
/* 153 */       throw te;
/* 154 */     } catch (Exception ex) {
/* 155 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1319_ACO_TRANSFORM_ERROR(), ex);
/* 156 */       throw new TransformException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String str) {
/* 161 */     return false;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext) throws TransformException {
/* 165 */     if (data instanceof AttachmentData) {
/*     */       try {
/* 167 */         return canonicalize((AttachmentData)data, null);
/* 168 */       } catch (TransformException tex) {
/* 169 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1319_ACO_TRANSFORM_ERROR(), tex);
/* 170 */         throw tex;
/* 171 */       } catch (Exception ex) {
/* 172 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1319_ACO_TRANSFORM_ERROR(), ex);
/* 173 */         throw new RuntimeException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 177 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext, OutputStream outputStream) throws TransformException {
/* 182 */     if (data instanceof AttachmentData) {
/* 183 */       return canonicalize((AttachmentData)data, outputStream);
/*     */     }
/*     */     
/* 186 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\ACTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */