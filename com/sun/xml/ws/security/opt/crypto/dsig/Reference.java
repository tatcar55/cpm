/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.utils.UnsyncBufferedOutputStream;
/*     */ import com.sun.xml.security.core.dsig.DigestMethodType;
/*     */ import com.sun.xml.security.core.dsig.ReferenceType;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.internal.DigesterOutputStream;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.TransformException;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRootElement(name = "Reference", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class Reference
/*     */   extends ReferenceType
/*     */   implements Reference
/*     */ {
/*     */   @XmlTransient
/*  82 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   @XmlTransient
/*     */   private Data _appliedTransformData;
/*     */   @XmlTransient
/*     */   private MessageDigest _md;
/*     */   @XmlTransient
/*     */   private boolean _validated;
/*     */   @XmlTransient
/*     */   private boolean _validationStatus;
/*     */   @XmlTransient
/*     */   private byte[] _calcDigestValue;
/*     */   
/*     */   public byte[] getCalculatedDigestValue() {
/*  96 */     return this._calcDigestValue;
/*     */   }
/*     */   
/*     */   public boolean validate(XMLValidateContext xMLValidateContext) throws XMLSignatureException {
/* 100 */     if (xMLValidateContext == null) {
/* 101 */       throw new NullPointerException("validateContext cannot be null");
/*     */     }
/* 103 */     if (this._validated) {
/* 104 */       return this._validationStatus;
/*     */     }
/* 106 */     Data data = dereference(xMLValidateContext);
/* 107 */     this._calcDigestValue = transform(data, xMLValidateContext);
/* 108 */     if (logger.isLoggable(Level.FINEST)) {
/* 109 */       logger.log(Level.FINEST, "Calculated digest value is: " + new String(this._calcDigestValue));
/*     */     }
/*     */     
/* 112 */     if (logger.isLoggable(Level.FINEST)) {
/* 113 */       logger.log(Level.FINEST, " Expected digest value is: " + new String(this.digestValue));
/*     */     }
/*     */     
/* 116 */     this._validationStatus = Arrays.equals(this.digestValue, this._calcDigestValue);
/* 117 */     this._validated = true;
/* 118 */     return this._validationStatus;
/*     */   }
/*     */   
/*     */   public void digest(XMLCryptoContext signContext) throws XMLSignatureException {
/* 122 */     if (getDigestValue() == null) {
/* 123 */       Data data = null;
/* 124 */       if (this._appliedTransformData == null) {
/* 125 */         data = dereference(signContext);
/*     */       } else {
/* 127 */         data = this._appliedTransformData;
/*     */       } 
/* 129 */       byte[] digest = transform(data, signContext);
/* 130 */       setDigestValue(digest);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigesterOutputStream getDigestOutputStream() throws XMLSignatureException {
/*     */     try {
/* 141 */       String algo = StreamUtil.convertDigestAlgorithm(getDigestMethod().getAlgorithm());
/* 142 */       if (logger.isLoggable(Level.FINE)) {
/* 143 */         logger.log(Level.FINE, "Digest Algorithm is " + getDigestMethod().getAlgorithm());
/* 144 */         logger.log(Level.FINE, "Mapped Digest Algorithm is " + algo);
/*     */       } 
/* 146 */       this._md = MessageDigest.getInstance(algo);
/* 147 */     } catch (NoSuchAlgorithmException nsae) {
/* 148 */       throw new XMLSignatureException(nsae);
/*     */     } 
/* 150 */     DigesterOutputStream dos = new DigesterOutputStream(this._md);
/* 151 */     return dos;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] transform(Data dereferencedData, XMLCryptoContext context) throws XMLSignatureException {
/* 157 */     if (this._md == null) {
/*     */       try {
/* 159 */         String algo = StreamUtil.convertDigestAlgorithm(getDigestMethod().getAlgorithm());
/* 160 */         if (logger.isLoggable(Level.FINE)) {
/* 161 */           logger.log(Level.FINE, "Digest Algorithm is " + getDigestMethod().getAlgorithm());
/* 162 */           logger.log(Level.FINE, "Mapped Digest Algorithm is " + algo);
/*     */         } 
/* 164 */         this._md = MessageDigest.getInstance(algo);
/*     */       }
/* 166 */       catch (NoSuchAlgorithmException nsae) {
/* 167 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1760_DIGEST_INIT_ERROR(), nsae);
/* 168 */         throw new XMLSignatureException(nsae);
/*     */       } 
/*     */     }
/* 171 */     this._md.reset();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     DigesterOutputStream dos = new DigesterOutputStream(this._md);
/* 177 */     OutputStream os = new UnsyncBufferedOutputStream((OutputStream)dos);
/* 178 */     Data data = dereferencedData;
/* 179 */     if (this.transforms != null) {
/* 180 */       List<Transform> transformList = this.transforms.getTransform();
/* 181 */       if (transformList != null) {
/* 182 */         for (int i = 0, size = transformList.size(); i < size; i++) {
/* 183 */           Transform transform = transformList.get(i);
/*     */           try {
/* 185 */             if (i < size - 1) {
/* 186 */               data = transform.transform(data, context);
/*     */             } else {
/* 188 */               data = transform.transform(data, context, os);
/*     */             } 
/* 190 */           } catch (TransformException te) {
/* 191 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1759_TRANSFORM_ERROR(te.getMessage()), te);
/* 192 */             throw new XMLSignatureException(te);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 199 */       os.flush();
/* 200 */       dos.flush();
/* 201 */     } catch (IOException ex) {
/* 202 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1761_TRANSFORM_IO_ERROR(), ex);
/* 203 */       throw new XMLSignatureException(ex);
/*     */     } 
/*     */ 
/*     */     
/* 207 */     return dos.getDigestValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private Data dereference(XMLCryptoContext context) throws XMLSignatureException {
/* 212 */     Data data = null;
/*     */ 
/*     */     
/* 215 */     URIDereferencer deref = context.getURIDereferencer();
/*     */     
/*     */     try {
/* 218 */       data = deref.dereference(this, context);
/* 219 */     } catch (URIReferenceException ure) {
/* 220 */       throw new XMLSignatureException(ure);
/*     */     } 
/* 222 */     return data;
/*     */   }
/*     */   
/*     */   public Data getDereferencedData() {
/* 226 */     return this._appliedTransformData;
/*     */   }
/*     */   
/*     */   public InputStream getDigestInputStream() {
/* 230 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 235 */     return false;
/*     */   }
/*     */   
/*     */   public DigestMethod getDigestMethod() {
/* 239 */     return this.digestMethod;
/*     */   }
/*     */ 
/*     */   
/*     */   public List getTransforms() {
/* 244 */     return this.transforms.getTransform();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\Reference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */