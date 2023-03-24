/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedDataHeaderBlock
/*     */   extends EncryptedTypeHeaderBlock
/*     */ {
/*     */   public EncryptedDataHeaderBlock() throws XWSSecurityException {
/*     */     try {
/*  84 */       setSOAPElement(getSoapFactory().createElement("EncryptedData", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */     
/*     */     }
/*  92 */     catch (SOAPException e) {
/*  93 */       log.log(Level.SEVERE, "WSS0345.error.creating.edhb", e.getMessage());
/*  94 */       throw new XWSSecurityException(e);
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
/*     */   
/*     */   public EncryptedDataHeaderBlock(SOAPElement element) throws XWSSecurityException {
/* 107 */     setSOAPElement(element);
/*     */     
/* 109 */     if (!element.getLocalName().equals("EncryptedData") || !XMLUtil.inEncryptionNS(element)) {
/*     */ 
/*     */       
/* 112 */       log.log(Level.SEVERE, "WSS0346.error.creating.edhb", element.getTagName());
/* 113 */       throw new XWSSecurityException("Invalid EncryptedData passed");
/*     */     } 
/* 115 */     initializeEncryptedType(element);
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 120 */     return SecurityHeaderBlockImpl.fromSoapElement(element, EncryptedDataHeaderBlock.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 125 */     if (this.updateRequired) {
/* 126 */       removeContents();
/*     */       try {
/* 128 */         addTextNode("\n    ");
/* 129 */         if (this.encryptionMethod != null) {
/* 130 */           addChildElement(this.encryptionMethod);
/* 131 */           addTextNode("\n    ");
/*     */         } 
/* 133 */         if (this.keyInfo != null) {
/* 134 */           addChildElement(this.keyInfo.getAsSoapElement());
/* 135 */           addTextNode("\n    ");
/*     */         } 
/* 137 */         if (this.cipherData == null) {
/* 138 */           log.log(Level.SEVERE, "WSS0347.missing.cipher.data");
/*     */           
/* 140 */           throw new XWSSecurityException("CipherData is not present inside EncryptedType");
/*     */         } 
/*     */         
/* 143 */         addChildElement(this.cipherData);
/* 144 */         addTextNode("\n    ");
/* 145 */         if (this.encryptionProperties != null) {
/* 146 */           addChildElement(this.encryptionProperties);
/* 147 */           addTextNode("\n    ");
/*     */         } 
/* 149 */       } catch (SOAPException e) {
/* 150 */         log.log(Level.SEVERE, "WSS0345.error.creating.edhb", e.getMessage());
/* 151 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     } 
/* 154 */     return super.getAsSoapElement();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedDataHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */