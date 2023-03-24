/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityHeaderException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
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
/*     */ public class X509SubjectKeyIdentifier
/*     */   extends KeyIdentifier
/*     */ {
/*  69 */   private String encodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */   
/*  71 */   private String valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier";
/*     */   
/*  73 */   private X509Certificate cert = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509SubjectKeyIdentifier(Document doc) throws XWSSecurityException {
/*  80 */     super(doc);
/*     */     
/*  82 */     setAttribute("EncodingType", this.encodingType);
/*  83 */     setAttribute("ValueType", this.valueType);
/*     */   }
/*     */ 
/*     */   
/*     */   public X509SubjectKeyIdentifier(SOAPElement element) throws XWSSecurityException {
/*  88 */     super(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getDecodedBase64EncodedValue() throws XWSSecurityException {
/*     */     try {
/*  94 */       return Base64.decode(getReferenceValue());
/*  95 */     } catch (Base64DecodingException e) {
/*  96 */       log.log(Level.SEVERE, "WSS0144.unableto.decode.base64.data", new Object[] { e.getMessage() });
/*     */       
/*  98 */       throw new SecurityHeaderException("Unable to decode Base64 encoded data", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getSubjectKeyIdentifier(X509Certificate cert) throws XWSSecurityException {
/* 110 */     KeyIdentifierSPI spi = KeyIdentifierSPI.getInstance();
/* 111 */     if (spi != null) {
/*     */       try {
/* 113 */         return spi.getSubjectKeyIdentifier(cert);
/* 114 */       } catch (KeyIdentifierSPIException ex) {
/* 115 */         throw new XWSSecurityException(ex);
/*     */       } 
/*     */     }
/*     */     
/* 119 */     throw new XWSSecurityException("Could not locate SPI class for KeyIdentifierSPI");
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 123 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 127 */     return this.cert;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\X509SubjectKeyIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */