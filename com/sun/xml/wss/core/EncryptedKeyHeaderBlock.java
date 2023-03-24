/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedKeyHeaderBlock
/*     */   extends EncryptedTypeHeaderBlock
/*     */ {
/*     */   ReferenceListHeaderBlock referenceList;
/*     */   SOAPElement carriedKeyName;
/*     */   
/*     */   public EncryptedKeyHeaderBlock() throws XWSSecurityException {
/*     */     try {
/*  93 */       setSOAPElement(getSoapFactory().createElement("EncryptedKey", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */     
/*     */     }
/* 101 */     catch (SOAPException e) {
/* 102 */       log.log(Level.SEVERE, "WSS0348.error.creating.ekhb", e.getMessage());
/* 103 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKeyHeaderBlock(Document doc) throws XWSSecurityException {
/*     */     try {
/* 115 */       setSOAPElement((SOAPElement)doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptedKey"));
/*     */ 
/*     */ 
/*     */       
/* 119 */       addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */     
/*     */     }
/* 122 */     catch (Exception e) {
/* 123 */       log.log(Level.SEVERE, "WSS0348.error.creating.ekhb", e.getMessage());
/* 124 */       throw new XWSSecurityException(e);
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
/*     */   public EncryptedKeyHeaderBlock(SOAPElement element) throws XWSSecurityException {
/*     */     try {
/* 137 */       setSOAPElement(element);
/*     */       
/* 139 */       if (!element.getLocalName().equals("EncryptedKey") || !XMLUtil.inEncryptionNS(element)) {
/*     */ 
/*     */         
/* 142 */         log.log(Level.SEVERE, "WSS0349.error.creating.ekhb", element.getTagName());
/* 143 */         throw new XWSSecurityException("Invalid EncryptedKey passed");
/*     */       } 
/*     */       
/* 146 */       initializeEncryptedType(element);
/*     */       
/* 148 */       Iterator<SOAPElement> referenceLists = getChildElements(getSoapFactory().createName("ReferenceList", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       if (referenceLists.hasNext()) {
/* 155 */         this.referenceList = new ReferenceListHeaderBlock(referenceLists.next());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 160 */       Iterator<SOAPElement> carriedKeyNames = getChildElements(getSoapFactory().createName("CarriedKeyName", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       if (carriedKeyNames.hasNext()) {
/* 167 */         this.carriedKeyName = carriedKeyNames.next();
/*     */       }
/* 169 */     } catch (Exception e) {
/* 170 */       log.log(Level.SEVERE, "WSS0348.error.creating.ekhb", e.getMessage());
/* 171 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCipherData(SOAPElement cipherData) {
/* 176 */     this.cipherData = cipherData;
/* 177 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCipherValue(String cipherValue) throws XWSSecurityException {
/*     */     try {
/* 184 */       this.cipherData = getSoapFactory().createElement("CipherData", "xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 189 */       this.cipherData.addNamespaceDeclaration("xenc", "http://www.w3.org/2001/04/xmlenc#");
/*     */ 
/*     */ 
/*     */       
/* 193 */       this.cipherData.addTextNode("\n    ");
/* 194 */       SOAPElement cipherValueElement = this.cipherData.addChildElement("CipherValue", "xenc");
/*     */ 
/*     */       
/* 197 */       this.cipherData.addTextNode("\n    ");
/*     */       
/* 199 */       cipherValueElement.addTextNode(cipherValue);
/*     */       
/* 201 */       this.cipherData.removeNamespaceDeclaration("xenc");
/*     */     }
/* 203 */     catch (SOAPException e) {
/* 204 */       log.log(Level.SEVERE, "WSS0350.error.setting.ciphervalue", e.getMessage());
/* 205 */       throw new XWSSecurityException(e);
/*     */     } 
/* 207 */     this.updateRequired = true;
/*     */   }
/*     */   
/*     */   public ReferenceListHeaderBlock getReferenceList() {
/* 211 */     return this.referenceList;
/*     */   }
/*     */   
/*     */   public void setReferenceList(ReferenceListHeaderBlock referenceList) {
/* 215 */     this.referenceList = referenceList;
/* 216 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRecipient() {
/* 223 */     String recipient = getAttribute("Recipient");
/* 224 */     if (recipient.equals(""))
/* 225 */       return null; 
/* 226 */     return recipient;
/*     */   }
/*     */   
/*     */   public void setRecipient(String recipient) {
/* 230 */     setAttribute("Recipient", recipient);
/*     */   }
/*     */   
/*     */   public SOAPElement getCarriedKeyName() {
/* 234 */     return this.carriedKeyName;
/*     */   }
/*     */   
/*     */   public void setCarriedKeyName(SOAPElement carriedKeyName) {
/* 238 */     this.carriedKeyName = carriedKeyName;
/* 239 */     this.updateRequired = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 244 */     return SecurityHeaderBlockImpl.fromSoapElement(element, EncryptedKeyHeaderBlock.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 249 */     if (this.updateRequired) {
/* 250 */       removeContents();
/*     */       try {
/* 252 */         addTextNode("\n    ");
/* 253 */         if (this.encryptionMethod != null) {
/* 254 */           addChildElement(this.encryptionMethod);
/* 255 */           addTextNode("\n    ");
/*     */         } 
/* 257 */         if (this.keyInfo != null) {
/* 258 */           addChildElement(this.keyInfo.getAsSoapElement());
/* 259 */           addTextNode("\n    ");
/*     */         } 
/* 261 */         if (this.cipherData == null) {
/* 262 */           log.log(Level.SEVERE, "WSS0347.missing.cipher.data");
/*     */           
/* 264 */           throw new XWSSecurityException("CipherData is not present inside EncryptedType");
/*     */         } 
/*     */         
/* 267 */         addChildElement(this.cipherData);
/* 268 */         addTextNode("\n    ");
/* 269 */         if (this.encryptionProperties != null) {
/* 270 */           addChildElement(this.encryptionProperties);
/* 271 */           addTextNode("\n    ");
/*     */         } 
/* 273 */         if (this.referenceList != null) {
/* 274 */           addChildElement(this.referenceList.getAsSoapElement());
/* 275 */           addTextNode("\n    ");
/*     */         } 
/* 277 */         if (this.carriedKeyName != null) {
/* 278 */           addChildElement(this.carriedKeyName);
/* 279 */           addTextNode("\n    ");
/*     */         } 
/* 281 */       } catch (SOAPException e) {
/* 282 */         log.log(Level.SEVERE, "WSS0348.error.creating.ekhb", e.getMessage());
/* 283 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     } 
/* 286 */     return super.getAsSoapElement();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedKeyHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */