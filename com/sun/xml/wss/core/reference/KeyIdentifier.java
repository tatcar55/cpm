/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class KeyIdentifier
/*     */   extends ReferenceElement
/*     */ {
/*  69 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIdentifier(Document doc) throws XWSSecurityException {
/*     */     try {
/*  80 */       setSOAPElement((SOAPElement)doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:KeyIdentifier"));
/*     */ 
/*     */     
/*     */     }
/*  84 */     catch (Exception e) {
/*  85 */       log.log(Level.SEVERE, "WSS0750.soap.exception", new Object[] { "wsse:KeyIdentifier", e.getMessage() });
/*     */ 
/*     */       
/*  88 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyIdentifier(SOAPElement element) throws XWSSecurityException {
/*  96 */     setSOAPElement(element);
/*  97 */     if (!element.getLocalName().equals("KeyIdentifier") || !XMLUtil.inWsseNS(element)) {
/*     */       
/*  99 */       log.log(Level.SEVERE, "WSS0756.invalid.key.identifier", element.getLocalName());
/* 100 */       throw new XWSSecurityException("Invalid keyIdentifier passed");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueType() {
/* 108 */     String valueType = getAttribute("ValueType");
/* 109 */     if (valueType.equals(""))
/* 110 */       return null; 
/* 111 */     return valueType;
/*     */   }
/*     */   
/*     */   public void setValueType(String valueType) {
/* 115 */     setAttribute("ValueType", valueType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncodingType() {
/* 122 */     String encodingType = getAttribute("EncodingType");
/* 123 */     if (encodingType.equals(""))
/* 124 */       return null; 
/* 125 */     return encodingType;
/*     */   }
/*     */   
/*     */   public void setEncodingType(String encodingType) {
/* 129 */     setAttribute("EncodingType", encodingType);
/*     */   }
/*     */   
/*     */   public String getReferenceValue() {
/* 133 */     return XMLUtil.getFullTextFromChildren((Element)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReferenceValue(String encodedValue) throws XWSSecurityException {
/* 138 */     removeContents();
/*     */     try {
/* 140 */       addTextNode(encodedValue);
/* 141 */     } catch (SOAPException e) {
/* 142 */       log.log(Level.SEVERE, "WSS0757.error.setting.reference", e.getMessage());
/*     */ 
/*     */       
/* 145 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsuId() {
/* 153 */     String wsuId = getAttribute("wsu:Id");
/* 154 */     if (wsuId.equals(""))
/* 155 */       return null; 
/* 156 */     return wsuId;
/*     */   }
/*     */   
/*     */   public void setWsuId(String wsuId) {
/* 160 */     setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 164 */     setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
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
/*     */   
/*     */   public String getDecodedReferenceValue() throws XWSSecurityException {
/* 177 */     String encType = getEncodingType();
/*     */     
/* 179 */     if (encType == null) {
/* 180 */       return getReferenceValue();
/*     */     }
/*     */     
/* 183 */     String encodedText = XMLUtil.getFullTextFromChildren((Element)this);
/* 184 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encType)) {
/* 185 */       return new String(getDecodedBase64EncodedData(encodedText));
/*     */     }
/* 187 */     log.log(Level.SEVERE, "WSS0762.unsupported.encodingType", new Object[] { encType });
/*     */ 
/*     */     
/* 190 */     throw new XWSSecurityException("Unsupported EncodingType: " + encType + " On KeyIdentifier");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] getDecodedBase64EncodedData(String encodedData) throws XWSSecurityException {
/*     */     try {
/* 197 */       return Base64.decode(encodedData);
/* 198 */     } catch (Base64DecodingException e) {
/* 199 */       log.log(Level.SEVERE, "WSS0144.unableto.decode.base64.data", new Object[] { e.getMessage() });
/*     */ 
/*     */       
/* 202 */       throw new XWSSecurityException("Unable to decode Base64 encoded data", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\KeyIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */