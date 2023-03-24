/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.security.Key;
/*     */ import java.util.Iterator;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedKeyToken
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityToken, Token
/*     */ {
/*  68 */   EncryptedKey encryptedKey = null;
/*  69 */   SOAPElement elem = null;
/*     */   
/*     */   public EncryptedKeyToken(SOAPElement elem) {
/*  72 */     this.elem = elem;
/*     */   }
/*     */   
/*     */   public Key getSecretKey(Key privKey, String dataEncAlgo) throws XWSSecurityException {
/*     */     try {
/*  77 */       XMLCipher xmlc = null;
/*  78 */       String algorithm = null;
/*  79 */       if (this.elem != null) {
/*  80 */         NodeList nl = this.elem.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod");
/*  81 */         if (nl != null)
/*  82 */           algorithm = ((Element)nl.item(0)).getAttribute("Algorithm"); 
/*  83 */         xmlc = XMLCipher.getInstance(algorithm);
/*  84 */         if (this.encryptedKey == null)
/*  85 */           this.encryptedKey = xmlc.loadEncryptedKey(this.elem); 
/*     */       } 
/*  87 */       if (xmlc == null) {
/*  88 */         throw new XWSSecurityException("XMLCipher is null while getting SecretKey from EncryptedKey");
/*     */       }
/*  90 */       xmlc.init(4, privKey);
/*  91 */       SecretKey symmetricKey = (SecretKey)xmlc.decryptKey(this.encryptedKey, dataEncAlgo);
/*  92 */       return symmetricKey;
/*  93 */     } catch (Exception ex) {
/*  94 */       ex.printStackTrace();
/*  95 */       throw new XWSSecurityException("Error while getting SecretKey from EncryptedKey");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() {
/* 101 */     if (this.elem != null) {
/* 102 */       return this.elem;
/*     */     }
/* 104 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*     */     try {
/* 110 */       return this.elem.getAttribute("Id");
/* 111 */     } catch (Exception ex) {
/* 112 */       throw new RuntimeException("Error while extracting ID");
/*     */     } 
/*     */   }
/*     */   
/*     */   public KeyInfoHeaderBlock getKeyInfo() {
/*     */     try {
/* 118 */       if (this.encryptedKey != null) {
/* 119 */         return new KeyInfoHeaderBlock(this.encryptedKey.getKeyInfo());
/*     */       }
/* 121 */       Iterator<Element> iter = this.elem.getChildElements(new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo"));
/* 122 */       Element keyInfoElem = null;
/* 123 */       if (iter.hasNext()) {
/* 124 */         keyInfoElem = iter.next();
/*     */       }
/* 126 */       KeyInfo keyInfo = new KeyInfo(keyInfoElem, "MessageConstants.DSIG_NS");
/* 127 */       return new KeyInfoHeaderBlock(keyInfo);
/*     */     }
/* 129 */     catch (XWSSecurityException ex) {
/* 130 */       throw new XWSSecurityRuntimeException("Error while extracting KeyInfo", ex);
/* 131 */     } catch (XMLSecurityException ex) {
/* 132 */       throw new XWSSecurityRuntimeException("Error while extracting KeyInfo", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getType() {
/* 137 */     return "xenc:EncryptedKey";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 141 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedKeyToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */