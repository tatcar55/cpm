/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinarySecurityToken
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityToken
/*     */ {
/*  80 */   protected String valueType = null;
/*     */ 
/*     */   
/*  83 */   protected String encodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */   
/*  85 */   protected String wsuId = null;
/*     */   
/*  87 */   protected String encodedText = null;
/*     */   
/*  89 */   protected Document soapDoc = null;
/*     */   
/*  91 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BinarySecurityToken(Document document, String wsuId, String valueType) throws SecurityTokenException {
/* 102 */     this.soapDoc = document;
/* 103 */     this.wsuId = wsuId;
/* 104 */     setValueType(valueType);
/*     */ 
/*     */     
/* 107 */     setEncodingType(this.encodingType);
/*     */   }
/*     */ 
/*     */   
/*     */   BinarySecurityToken(SOAPElement binTokenSoapElement) throws SecurityTokenException {
/* 112 */     this(binTokenSoapElement, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   BinarySecurityToken(SOAPElement binTokenSoapElement, boolean isBSP) throws SecurityTokenException {
/* 118 */     setSOAPElement(binTokenSoapElement);
/* 119 */     this.soapDoc = getOwnerDocument();
/*     */     
/* 121 */     setTextValue(XMLUtil.getFullTextFromChildren(this));
/*     */     
/* 123 */     String wsuId = getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 124 */     if (!"".equals(wsuId)) {
/* 125 */       setId(wsuId);
/*     */     }
/* 127 */     String valueType = getAttribute("ValueType");
/*     */ 
/*     */     
/* 130 */     if (isBSP && valueType.length() < 1) {
/* 131 */       log.log(Level.SEVERE, "BSP3031.ValueType.NotPresent");
/* 132 */       throw new SecurityTokenException("Any wsse:BinarySecurityToken in a SECURE_ENVELOPE MUST have an ValueType attribute.");
/*     */     } 
/*     */     
/* 135 */     if (!"".equals(valueType)) {
/* 136 */       setValueType(valueType);
/*     */     }
/*     */     
/* 139 */     if (isBSP) {
/* 140 */       String encoding = getAttribute("EncodingType");
/*     */ 
/*     */       
/* 143 */       if (this.encodingType.length() < 1) {
/* 144 */         log.log(Level.SEVERE, "BSP3029.EncodingType.NotPresent");
/* 145 */         throw new SecurityTokenException("Any wsse:BinarySecurityToken in a SECURE_ENVELOPE MUST have an EncodingType attribute.");
/*     */       } 
/*     */       
/* 148 */       if (!this.encodingType.equalsIgnoreCase("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary")) {
/*     */         
/* 150 */         log.log(Level.SEVERE, "BSP3030.EncodingType.Invalid");
/* 151 */         throw new SecurityTokenException("EncodingType attribute value in wsse:BinarySecurityToken is invalid.");
/*     */       } 
/*     */       
/* 154 */       if (!"".equals(encoding)) {
/* 155 */         setEncodingType(encoding);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getValueType() {
/* 161 */     return this.valueType;
/*     */   }
/*     */   
/*     */   protected void setValueType(String valueType) {
/* 165 */     if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) && !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType)) {
/* 166 */       log.log(Level.SEVERE, "WSS0342.valtype.invalid");
/* 167 */       throw new RuntimeException("Unsupported value type: " + valueType);
/*     */     } 
/* 169 */     this.valueType = valueType;
/*     */   }
/*     */   
/*     */   public String getEncodingType() {
/* 173 */     return this.encodingType;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setEncodingType(String encodingType) {
/* 178 */     if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(encodingType)) {
/* 179 */       log.log(Level.SEVERE, "WSS0316.enctype.invalid");
/* 180 */       throw new RuntimeException("Encoding type invalid");
/*     */     } 
/* 182 */     this.encodingType = encodingType;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 186 */     return this.wsuId;
/*     */   }
/*     */   
/*     */   protected void setId(String wsuId) {
/* 190 */     this.wsuId = wsuId;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getRawValue() throws SecurityTokenException {
/*     */     try {
/* 196 */       return Base64.decode(this.encodedText);
/* 197 */     } catch (Base64DecodingException bde) {
/* 198 */       log.log(Level.SEVERE, "WSS0344.error.decoding.bst");
/* 199 */       throw new SecurityTokenException(bde);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setRawValue(byte[] rawText) {
/* 204 */     this.encodedText = Base64.encode(rawText);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextValue() throws XWSSecurityException {
/* 212 */     return this.encodedText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTextValue(String encodedText) {
/* 220 */     this.encodedText = encodedText;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws SecurityTokenException {
/* 225 */     if (null != this.delegateElement)
/* 226 */       return this.delegateElement; 
/*     */     try {
/* 228 */       setSOAPElement((SOAPElement)this.soapDoc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:BinarySecurityToken"));
/*     */ 
/*     */ 
/*     */       
/* 232 */       addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */ 
/*     */ 
/*     */       
/* 236 */       if (null != this.valueType) {
/* 237 */         setAttributeNS((String)null, "ValueType", this.valueType);
/*     */       }
/* 239 */       if (this.encodingType != null) {
/* 240 */         setAttributeNS((String)null, "EncodingType", this.encodingType);
/*     */       }
/*     */       
/* 243 */       if (this.wsuId != null) {
/* 244 */         setWsuIdAttr(this, this.wsuId);
/*     */       }
/*     */       
/* 247 */       addTextNode(getTextValue());
/*     */     }
/* 249 */     catch (Exception e) {
/* 250 */       log.log(Level.SEVERE, "WSS0343.error.creating.bst", e.getMessage());
/* 251 */       throw new SecurityTokenException("There was an error in creating the BinarySecurityToken " + e.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 255 */     return this.delegateElement;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\BinarySecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */