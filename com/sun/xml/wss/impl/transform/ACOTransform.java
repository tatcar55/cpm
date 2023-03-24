/*     */ package com.sun.xml.wss.impl.transform;
/*     */ 
/*     */ import com.sun.xml.wss.impl.c14n.Canonicalizer;
/*     */ import com.sun.xml.wss.impl.c14n.CanonicalizerFactory;
/*     */ import com.sun.xml.wss.impl.dsig.AttachmentData;
/*     */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
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
/*     */ public class ACOTransform
/*     */   extends TransformService
/*     */ {
/*  71 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String implementedTransformURI = "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  81 */     return null;
/*     */   }
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
/*     */   public void marshalParams(XMLStructure xMLStructure, XMLCryptoContext xMLCryptoContext) throws MarshalException {}
/*     */ 
/*     */   
/*     */   private Data canonicalize(OctetStreamData data) {
/*  98 */     throw new UnsupportedOperationException();
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
/*     */   private Data canonicalize(AttachmentData data, OutputStream outputStream) throws TransformException {
/*     */     try {
/* 116 */       AttachmentPart attachment = data.getAttachmentPart();
/* 117 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 118 */       attachment.getDataHandler().writeTo(os);
/* 119 */       OutputStream byteStream = null;
/* 120 */       ByteArrayInputStream byteInputStream = new ByteArrayInputStream(os.toByteArray());
/* 121 */       if (outputStream == null) {
/* 122 */         byteStream = new ByteArrayOutputStream();
/*     */       } else {
/* 124 */         byteStream = outputStream;
/*     */       } 
/* 126 */       Canonicalizer canonicalizer = CanonicalizerFactory.getCanonicalizer(attachment.getContentType());
/* 127 */       InputStream is = canonicalizer.canonicalize(byteInputStream, byteStream);
/* 128 */       if (is != null) return new OctetStreamData(is);
/*     */       
/* 130 */       return null;
/* 131 */     } catch (TransformException te) {
/* 132 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1318_AC_TRANSFORM_ERROR(), te);
/* 133 */       throw te;
/* 134 */     } catch (Exception ex) {
/* 135 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1318_AC_TRANSFORM_ERROR(), ex);
/* 136 */       throw new TransformException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String str) {
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext) throws TransformException {
/* 145 */     if (data instanceof OctetStreamData)
/* 146 */       return canonicalize((OctetStreamData)data); 
/* 147 */     if (data instanceof AttachmentData) {
/* 148 */       ByteArrayOutputStream os = null;
/* 149 */       return canonicalize((AttachmentData)data, os);
/*     */     } 
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext, OutputStream outputStream) throws TransformException {
/* 155 */     if (data instanceof AttachmentData) {
/* 156 */       return canonicalize((AttachmentData)data, outputStream);
/*     */     }
/* 158 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\transform\ACOTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */