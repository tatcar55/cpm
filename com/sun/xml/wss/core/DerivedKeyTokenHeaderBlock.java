/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerivedKeyTokenHeaderBlock
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements Token, SecurityToken
/*     */ {
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/*  84 */     return SecurityHeaderBlockImpl.fromSoapElement(element, DerivedKeyTokenHeaderBlock.class);
/*     */   }
/*     */ 
/*     */   
/*  88 */   private Document contextDocument = null;
/*  89 */   private SecurityTokenReference securityTokenRefElement = null;
/*  90 */   private long offset = 0L;
/*  91 */   private long length = 32L;
/*  92 */   private String nonce = null;
/*  93 */   private long generation = -1L;
/*  94 */   private String wsuId = null;
/*  95 */   private String label = null;
/*     */   
/*  97 */   private byte[] decodedNonce = null;
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(Document contextDocument, SecurityTokenReference securityTokenRefElement, String wsuId) throws XWSSecurityException {
/* 101 */     if (securityTokenRefElement != null) {
/* 102 */       this.contextDocument = contextDocument;
/* 103 */       this.securityTokenRefElement = securityTokenRefElement;
/* 104 */       this.wsuId = wsuId;
/*     */     } else {
/* 106 */       throw new XWSSecurityException("DerivedKeyToken can not be null");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(Document contextDocument, SecurityTokenReference securityTokenRefElement, String nonce, String wsuId) throws XWSSecurityException {
/* 114 */     if (securityTokenRefElement != null) {
/* 115 */       this.contextDocument = contextDocument;
/* 116 */       this.securityTokenRefElement = securityTokenRefElement;
/* 117 */       this.wsuId = wsuId;
/*     */     } else {
/* 119 */       throw new XWSSecurityException("DerivedKeyToken can not be null");
/*     */     } 
/*     */     
/* 122 */     if (nonce != null) {
/* 123 */       this.nonce = nonce;
/*     */     } else {
/* 125 */       throw new XWSSecurityException("Nonce can not be null");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(Document contextDocument, SecurityTokenReference securityTokenRefElement, String nonce, long generation, String wsuId) throws XWSSecurityException {
/* 135 */     this(contextDocument, securityTokenRefElement, nonce, wsuId);
/* 136 */     this.generation = generation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(Document contextDocument, SecurityTokenReference securityTokenRefElement, String nonce, long offset, long length, String wsuId) throws XWSSecurityException {
/* 144 */     this(contextDocument, securityTokenRefElement, nonce, -1L, wsuId);
/* 145 */     this.length = length;
/* 146 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(Document contextDocument, SecurityTokenReference securityTokenRefElement, String nonce, long offset, long length, String wsuId, String label) throws XWSSecurityException {
/* 155 */     this(contextDocument, securityTokenRefElement, nonce, -1L, wsuId);
/* 156 */     this.length = length;
/* 157 */     this.offset = offset;
/* 158 */     this.label = label;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedKeyTokenHeaderBlock(SOAPElement derivedKeyTokenHeaderBlock) throws XWSSecurityException {
/* 164 */     setSOAPElement(derivedKeyTokenHeaderBlock);
/*     */     
/* 166 */     this.contextDocument = getOwnerDocument();
/*     */     
/* 168 */     if (!"DerivedKeyToken".equals(getLocalName()) || !XMLUtil.inWsscNS(this))
/*     */     {
/* 170 */       throw new SecurityTokenException("Expected DerivedKeyToken Element, but Found " + getPrefix() + ":" + getLocalName());
/*     */     }
/*     */ 
/*     */     
/* 174 */     boolean invalidToken = false;
/*     */     
/* 176 */     Iterator<Node> children = getChildElements();
/*     */ 
/*     */     
/* 179 */     String wsuId = getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 180 */     if (!"".equals(wsuId)) {
/* 181 */       setId(wsuId);
/*     */     }
/* 183 */     Node object = null;
/* 184 */     boolean offsetSpecified = false;
/* 185 */     boolean genSpecified = false;
/* 186 */     boolean lenSpecified = false;
/*     */     
/* 188 */     while (children.hasNext()) {
/*     */       
/* 190 */       object = children.next();
/*     */       
/* 192 */       if (object.getNodeType() == 1) {
/*     */         
/* 194 */         SOAPElement element = (SOAPElement)object;
/*     */ 
/*     */         
/* 197 */         if ("SecurityTokenReference".equals(element.getLocalName()) && XMLUtil.inWsseNS(element)) {
/*     */           
/* 199 */           this.securityTokenRefElement = new SecurityTokenReference(element); continue;
/* 200 */         }  if ("Offset".equals(element.getLocalName())) {
/*     */           try {
/* 202 */             offsetSpecified = true;
/* 203 */             this.offset = Long.valueOf(element.getValue()).longValue();
/* 204 */           } catch (NumberFormatException nfe) {
/* 205 */             throw new XWSSecurityException(nfe);
/*     */           }  continue;
/* 207 */         }  if ("Length".equals(element.getLocalName())) {
/*     */           try {
/* 209 */             lenSpecified = true;
/* 210 */             this.length = Long.valueOf(element.getValue()).longValue();
/* 211 */           } catch (NumberFormatException nfe) {
/* 212 */             throw new XWSSecurityException(nfe);
/*     */           }  continue;
/* 214 */         }  if ("Nonce".equals(element.getLocalName())) {
/* 215 */           this.nonce = element.getValue(); continue;
/* 216 */         }  if ("Generation".equals(element.getLocalName())) {
/*     */           try {
/* 218 */             genSpecified = true;
/* 219 */             this.generation = Long.valueOf(element.getValue()).longValue();
/* 220 */           } catch (NumberFormatException nfe) {
/* 221 */             throw new XWSSecurityException(nfe);
/*     */           }  continue;
/* 223 */         }  if ("Label".equals(element.getLocalName())) {
/* 224 */           this.label = element.getValue(); continue;
/*     */         } 
/* 226 */         invalidToken = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     if (offsetSpecified && genSpecified) {
/* 233 */       invalidToken = true;
/*     */     }
/*     */     
/* 236 */     if (invalidToken) {
/* 237 */       throw new XWSSecurityException("Invalid DerivedKeyToken");
/*     */     }
/*     */   }
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 242 */     if (this.delegateElement != null) {
/* 243 */       return this.delegateElement;
/*     */     }
/*     */     try {
/* 246 */       setSOAPElement((SOAPElement)this.contextDocument.createElementNS("http://schemas.xmlsoap.org/ws/2005/02/sc", "wsc:DerivedKeyToken"));
/*     */ 
/*     */ 
/*     */       
/* 250 */       addNamespaceDeclaration("wsc", "http://schemas.xmlsoap.org/ws/2005/02/sc");
/*     */ 
/*     */ 
/*     */       
/* 254 */       if (this.securityTokenRefElement == null) {
/* 255 */         throw new SecurityTokenException("securitytokenreference was not set");
/*     */       }
/* 257 */       SOAPElement elem = this.securityTokenRefElement.getAsSoapElement();
/* 258 */       this.delegateElement.appendChild(elem);
/*     */       
/* 260 */       if (this.generation == -1L) {
/* 261 */         addChildElement("Offset", "wsc").addTextNode(String.valueOf(this.offset));
/* 262 */         addChildElement("Length", "wsc").addTextNode(String.valueOf(this.length));
/*     */       } else {
/* 264 */         addChildElement("Generation", "wsc").addTextNode(String.valueOf(this.generation));
/* 265 */         addChildElement("Length", "wsc").addTextNode(String.valueOf(this.length));
/*     */       } 
/* 267 */       if (this.label != null) {
/* 268 */         addChildElement("Label", "wsc").addTextNode(this.label);
/*     */       }
/* 270 */       if (this.nonce != null) {
/* 271 */         addChildElement("Nonce", "wsc").addTextNode(this.nonce);
/*     */       }
/*     */       
/* 274 */       if (this.wsuId != null) {
/* 275 */         setWsuIdAttr(this, this.wsuId);
/*     */       }
/*     */     }
/* 278 */     catch (SOAPException se) {
/* 279 */       throw new SecurityTokenException("There was an error creating DerivedKey Token " + se.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 284 */     return super.getAsSoapElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Document getContextDocument() {
/* 291 */     return this.contextDocument;
/*     */   }
/*     */   
/*     */   public byte[] getNonce() {
/* 295 */     if (this.decodedNonce != null)
/* 296 */       return this.decodedNonce; 
/*     */     try {
/* 298 */       this.decodedNonce = Base64.decode(this.nonce);
/* 299 */     } catch (Base64DecodingException bde) {
/* 300 */       throw new RuntimeException(bde);
/*     */     } 
/* 302 */     return this.decodedNonce;
/*     */   }
/*     */   
/*     */   public long getOffset() {
/* 306 */     return this.offset;
/*     */   }
/*     */   
/*     */   public long getLength() {
/* 310 */     return this.length;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference getDerivedKeyElement() {
/* 314 */     return this.securityTokenRefElement;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 318 */     return "http://schemas.xmlsoap.org/ws/2005/02/sc/dk";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 322 */     return this;
/*     */   }
/*     */   
/*     */   private void setId(String wsuId) {
/* 326 */     this.wsuId = wsuId;
/*     */   }
/*     */   
/*     */   public String getLabel() {
/* 330 */     return this.label;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\DerivedKeyTokenHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */