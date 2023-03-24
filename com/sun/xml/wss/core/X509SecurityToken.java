/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509SecurityToken
/*     */   extends BinarySecurityToken
/*     */   implements Token
/*     */ {
/*  77 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate cert;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509SecurityToken(Document document, X509Certificate cert, String wsuId, String valueType) throws SecurityTokenException {
/*  90 */     super(document, wsuId, valueType);
/*  91 */     this.cert = cert;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public X509SecurityToken(Document document, X509Certificate cert) throws SecurityTokenException {
/*  97 */     super(document, null, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
/*  98 */     this.cert = cert;
/*     */   }
/*     */ 
/*     */   
/*     */   public X509SecurityToken(Document document, X509Certificate cert, String valueType) throws SecurityTokenException {
/* 103 */     super(document, null, valueType);
/*     */     
/* 105 */     this.cert = cert;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public X509SecurityToken(SOAPElement tokenElement, boolean isBSP) throws XWSSecurityException {
/* 111 */     super(tokenElement, isBSP);
/* 112 */     if (!tokenElement.getLocalName().equals("BinarySecurityToken") || !XMLUtil.inWsseNS(tokenElement)) {
/*     */ 
/*     */       
/* 115 */       log.log(Level.SEVERE, "WSS0391.error.creating.X509SecurityToken", tokenElement.getTagName());
/* 116 */       throw new XWSSecurityException("BinarySecurityToken expected, found " + tokenElement.getTagName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public X509SecurityToken(SOAPElement tokenElement) throws XWSSecurityException {
/* 123 */     this(tokenElement, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Certificate getCertificate() throws XWSSecurityException {
/* 128 */     if (this.cert == null) {
/*     */       byte[] data;
/*     */       
/* 131 */       String encodedData = XMLUtil.getFullTextFromChildren(this);
/*     */       try {
/* 133 */         data = Base64.decode(encodedData);
/* 134 */       } catch (Base64DecodingException bde) {
/* 135 */         log.log(Level.SEVERE, "WSS0301.unableto.decode.data");
/* 136 */         throw new SecurityTokenException("Unable to decode data", bde);
/*     */       } 
/*     */       try {
/* 139 */         CertificateFactory certFact = CertificateFactory.getInstance("X.509");
/* 140 */         this.cert = (X509Certificate)certFact.generateCertificate(new ByteArrayInputStream(data));
/* 141 */       } catch (Exception e) {
/* 142 */         log.log(Level.SEVERE, "WSS0302.unableto.create.x509cert");
/* 143 */         throw new XWSSecurityException("Unable to create X509Certificate from data");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 148 */     return this.cert;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 153 */     return SecurityHeaderBlockImpl.fromSoapElement(element, X509SecurityToken.class);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextValue() throws XWSSecurityException {
/* 159 */     if (this.encodedText == null) {
/*     */       
/*     */       try {
/* 162 */         byte[] rawBytes = this.cert.getEncoded();
/* 163 */         setRawValue(rawBytes);
/* 164 */       } catch (CertificateEncodingException e) {
/* 165 */         log.log(Level.SEVERE, "WSS0303.unableto.get.encoded.x509cert");
/*     */         
/* 167 */         throw new XWSSecurityException("Unable to get encoded representation of X509Certificate", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 172 */     return this.encodedText;
/*     */   }
/*     */   
/*     */   private void checkCertVersion() throws SecurityTokenException {
/* 176 */     if (this.cert.getVersion() != 3 || this.cert.getVersion() != 1) {
/* 177 */       log.log(Level.SEVERE, "WSS0392.invalid.X509cert.version", Integer.toString(this.cert.getVersion()));
/*     */ 
/*     */       
/* 180 */       throw new SecurityTokenException("Expected Version 1 or 3 Certificate, found Version " + this.cert.getVersion());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 188 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     try {
/* 193 */       return getCertificate();
/* 194 */     } catch (XWSSecurityException ex) {
/* 195 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\X509SecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */