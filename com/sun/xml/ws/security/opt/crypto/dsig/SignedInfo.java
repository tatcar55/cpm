/*     */ package com.sun.xml.ws.security.opt.crypto.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.SignedInfoType;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
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
/*     */ @XmlRootElement(name = "SignedInfo", namespace = "http://www.w3.org/2000/09/xmldsig#")
/*     */ public class SignedInfo
/*     */   extends SignedInfoType
/*     */   implements SignedInfo
/*     */ {
/*     */   @XmlTransient
/*  61 */   private XMLStreamReaderEx _streamSI = null; @XmlTransient
/*  62 */   private byte[] canonInfo = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getReferences() {
/*  68 */     return this.reference;
/*     */   }
/*     */   
/*     */   public InputStream getCanonicalizedData() {
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/*  76 */     return false;
/*     */   }
/*     */   
/*     */   public SignatureMethod getSignatureMethod() {
/*  80 */     return this.signatureMethod;
/*     */   }
/*     */   
/*     */   public CanonicalizationMethod getCanonicalizationMethod() {
/*  84 */     return this.canonicalizationMethod;
/*     */   }
/*     */   
/*     */   public void setReference(List<Reference> reference) {
/*  88 */     this.reference = reference;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getCanonicalizedSI() {
/*  93 */     return this.canonInfo;
/*     */   }
/*     */   
/*     */   public void setCanonicalizedSI(byte[] info) {
/*  97 */     this.canonInfo = info;
/*     */   }
/*     */   
/*     */   public XMLStreamReaderEx getSignedInfo() {
/* 101 */     return this._streamSI;
/*     */   }
/*     */   
/*     */   public void setSignedInfo(XMLStreamReaderEx _streamSI) {
/* 105 */     this._streamSI = _streamSI;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\dsig\SignedInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */