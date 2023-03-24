/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityHeaderException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.CertificateEncodingException;
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
/*     */ public class X509ThumbPrintIdentifier
/*     */   extends KeyIdentifier
/*     */ {
/*  65 */   private String encodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */   
/*  67 */   private String valueType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1";
/*     */   
/*  69 */   private X509Certificate cert = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509ThumbPrintIdentifier(Document doc) throws XWSSecurityException {
/*  76 */     super(doc);
/*     */     
/*  78 */     setAttribute("EncodingType", this.encodingType);
/*  79 */     setAttribute("ValueType", this.valueType);
/*     */   }
/*     */ 
/*     */   
/*     */   public X509ThumbPrintIdentifier(SOAPElement element) throws XWSSecurityException {
/*  84 */     super(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getDecodedBase64EncodedValue() throws XWSSecurityException {
/*     */     try {
/*  90 */       return Base64.decode(getReferenceValue());
/*  91 */     } catch (Base64DecodingException e) {
/*  92 */       log.log(Level.SEVERE, "WSS0144.unableto.decode.base64.data", new Object[] { e.getMessage() });
/*     */       
/*  94 */       throw new SecurityHeaderException("Unable to decode Base64 encoded data", e);
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
/*     */   public static byte[] getThumbPrintIdentifier(X509Certificate cert) throws XWSSecurityException {
/* 106 */     byte[] thumbPrintIdentifier = null;
/*     */     
/*     */     try {
/* 109 */       thumbPrintIdentifier = MessageDigest.getInstance("SHA-1").digest(cert.getEncoded());
/* 110 */     } catch (NoSuchAlgorithmException ex) {
/* 111 */       throw new XWSSecurityException("Digest algorithm SHA-1 not found");
/* 112 */     } catch (CertificateEncodingException ex) {
/* 113 */       throw new XWSSecurityException("Error while getting certificate's raw content");
/*     */     } 
/*     */     
/* 116 */     return thumbPrintIdentifier;
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 120 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public X509Certificate getCertificate() {
/* 124 */     return this.cert;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\X509ThumbPrintIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */