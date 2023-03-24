/*     */ package com.sun.xml.wss.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.EncryptedKeySHA1Identifier;
/*     */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.SamlKeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class KeyIdentifierStrategy
/*     */   extends KeyInfoStrategy
/*     */ {
/*     */   public static final int THUMBPRINT = 0;
/*     */   public static final int ENCRYPTEDKEYSHA1 = 1;
/*  74 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   X509Certificate cert = null;
/*  80 */   String alias = null;
/*     */   
/*     */   boolean forSigning;
/*     */   boolean thumbprint;
/*     */   boolean encryptedKey = false;
/*  85 */   String samlAssertionId = null;
/*     */ 
/*     */   
/*     */   public KeyIdentifierStrategy() {}
/*     */ 
/*     */   
/*     */   public KeyIdentifierStrategy(int value) {
/*  92 */     if (value == 0) {
/*  93 */       this.thumbprint = true;
/*  94 */     } else if (value == 1) {
/*  95 */       this.encryptedKey = true;
/*     */     } 
/*     */   }
/*     */   public KeyIdentifierStrategy(String samlAssertionId) {
/*  99 */     this.samlAssertionId = samlAssertionId;
/* 100 */     this.forSigning = false;
/*     */   }
/*     */   
/*     */   public KeyIdentifierStrategy(String alias, boolean forSigning) {
/* 104 */     this.alias = alias;
/* 105 */     this.forSigning = forSigning;
/*     */   }
/*     */   
/*     */   public KeyIdentifierStrategy(String alias, boolean forSigning, boolean thumbprint) {
/* 109 */     this.alias = alias;
/* 110 */     this.forSigning = forSigning;
/* 111 */     this.thumbprint = thumbprint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(SecurityTokenReference tokenRef, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/* 117 */     KeyIdentifier keyIdentifier = getKeyIdentifier(secureMsg);
/* 118 */     if (keyIdentifier == null) {
/* 119 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0701_CANNOT_LOCATE_CERTIFICATE(this.alias), this.alias);
/*     */ 
/*     */       
/* 122 */       throw new XWSSecurityException("Unable to locate certificate for the alias '" + this.alias + "'");
/*     */     } 
/*     */     
/* 125 */     tokenRef.setReference((ReferenceElement)keyIdentifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertKey(KeyInfoHeaderBlock keyInfo, SecurableSoapMessage secureMsg, String x509TokenId) throws XWSSecurityException {
/* 134 */     KeyIdentifier keyIdentifier = getKeyIdentifier(secureMsg);
/*     */     
/* 136 */     if (keyIdentifier == null) {
/* 137 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0701_CANNOT_LOCATE_CERTIFICATE(this.alias), this.alias);
/*     */ 
/*     */       
/* 140 */       throw new XWSSecurityException("Unable to locate certificate for the alias '" + this.alias + "'");
/*     */     } 
/*     */     
/* 143 */     Document ownerDoc = keyInfo.getOwnerDocument();
/* 144 */     SecurityTokenReference tokenRef = new SecurityTokenReference(ownerDoc);
/*     */     
/* 146 */     tokenRef.setReference((ReferenceElement)keyIdentifier);
/* 147 */     keyInfo.addSecurityTokenReference(tokenRef);
/*     */   }
/*     */ 
/*     */   
/*     */   private KeyIdentifier getKeyIdentifier(SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*     */     EncryptedKeySHA1Identifier encryptedKeySHA1Identifier;
/* 153 */     KeyIdentifier keyIdentifier = null;
/* 154 */     if (this.samlAssertionId != null) {
/* 155 */       SamlKeyIdentifier samlKeyIdentifier = new SamlKeyIdentifier(secureMsg.getSOAPPart());
/*     */       
/* 157 */       samlKeyIdentifier.setReferenceValue(this.samlAssertionId);
/* 158 */       return (KeyIdentifier)samlKeyIdentifier;
/*     */     } 
/*     */     
/* 161 */     if (this.cert != null) {
/* 162 */       if (!this.thumbprint) {
/* 163 */         byte[] subjectKeyIdentifier = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(this.cert);
/*     */         
/* 165 */         if (subjectKeyIdentifier == null) {
/* 166 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0702_NO_SUBJECT_KEYIDENTIFIER(this.alias), this.alias);
/*     */ 
/*     */           
/* 169 */           throw new XWSSecurityException("The found certificate does not contain subject key identifier X509 extension");
/*     */         } 
/*     */         
/* 172 */         String keyId = Base64.encode(subjectKeyIdentifier);
/* 173 */         X509SubjectKeyIdentifier x509SubjectKeyIdentifier = new X509SubjectKeyIdentifier(secureMsg.getSOAPPart());
/*     */         
/* 175 */         x509SubjectKeyIdentifier.setReferenceValue(keyId);
/*     */       } else {
/* 177 */         byte[] thumbPrintIdentifier = X509ThumbPrintIdentifier.getThumbPrintIdentifier(this.cert);
/*     */         
/* 179 */         if (thumbPrintIdentifier == null) {
/* 180 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0702_NO_SUBJECT_KEYIDENTIFIER(this.alias), this.alias);
/*     */ 
/*     */           
/* 183 */           throw new XWSSecurityException("Error while calculating thumb print identifier");
/*     */         } 
/*     */         
/* 186 */         String keyId = Base64.encode(thumbPrintIdentifier);
/* 187 */         X509ThumbPrintIdentifier x509ThumbPrintIdentifier = new X509ThumbPrintIdentifier(secureMsg.getSOAPPart());
/*     */         
/* 189 */         x509ThumbPrintIdentifier.setReferenceValue(keyId);
/*     */       } 
/* 191 */     } else if (this.encryptedKey) {
/* 192 */       encryptedKeySHA1Identifier = new EncryptedKeySHA1Identifier(secureMsg.getSOAPPart());
/*     */     } 
/* 194 */     return (KeyIdentifier)encryptedKeySHA1Identifier;
/*     */   }
/*     */   
/*     */   public void setCertificate(X509Certificate cert) {
/* 198 */     this.cert = cert;
/*     */   }
/*     */   
/*     */   public String getAlias() {
/* 202 */     return this.alias;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\keyinfo\KeyIdentifierStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */