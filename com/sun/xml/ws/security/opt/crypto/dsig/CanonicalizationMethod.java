/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.CanonicalizationMethodType;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.spec.AlgorithmParameterSpec;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.TransformException;
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
/*     */ @XmlRootElement(name = "CanonicalizationMethod", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class CanonicalizationMethod
/*     */   extends CanonicalizationMethodType
/*     */   implements CanonicalizationMethod
/*     */ {
/*     */   @XmlTransient
/*  71 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */   
/*     */   @XmlTransient
/*  74 */   private Exc14nCanonicalizer _exc14nCanonicalizer = new Exc14nCanonicalizer(); @XmlTransient
/*  75 */   private AlgorithmParameterSpec _algSpec = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParameterSpec(AlgorithmParameterSpec algSpec) {
/*  82 */     this._algSpec = algSpec;
/*     */   }
/*     */   
/*     */   public AlgorithmParameterSpec getParameterSpec() {
/*  86 */     return this._algSpec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/*  92 */     return false;
/*     */   }
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext) throws TransformException {
/*  96 */     if (this.algorithm == "http://www.w3.org/2001/10/xml-exc-c14n#") {
/*     */       try {
/*  98 */         this._exc14nCanonicalizer.init((TransformParameterSpec)this._algSpec);
/*  99 */       } catch (InvalidAlgorithmParameterException ex) {
/* 100 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1758_TRANSFORM_INIT(), ex);
/* 101 */         throw new TransformException(ex);
/*     */       } 
/* 103 */       this._exc14nCanonicalizer.transform(data, xMLCryptoContext);
/*     */     } 
/* 105 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Data transform(Data data, XMLCryptoContext xMLCryptoContext, OutputStream outputStream) throws TransformException {
/* 110 */     if (this.algorithm == "http://www.w3.org/2001/10/xml-exc-c14n#") {
/* 111 */       this._exc14nCanonicalizer.transform(data, xMLCryptoContext, outputStream);
/*     */     }
/* 113 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(List content) {
/* 118 */     this.content = content;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\CanonicalizationMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */