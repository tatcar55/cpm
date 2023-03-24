/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.net.URI;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class SecurityContextTokenImpl
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityContextToken, SecurityToken
/*     */ {
/*  80 */   private String securityContextId = null;
/*  81 */   private String instance = null;
/*  82 */   private List extElements = null;
/*     */   
/*  84 */   private String wsuId = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/*  94 */     return SecurityHeaderBlockImpl.fromSoapElement(element, SecurityContextTokenImpl.class);
/*     */   }
/*     */ 
/*     */   
/*  98 */   private Document contextDocument = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenImpl(Document contextDocument, String contextId, String instance, String wsuId, List extElements) throws XWSSecurityException {
/* 103 */     this.securityContextId = contextId;
/* 104 */     this.instance = instance;
/* 105 */     this.wsuId = wsuId;
/* 106 */     this.extElements = extElements;
/* 107 */     this.contextDocument = contextDocument;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityContextTokenImpl(SOAPElement sct) throws XWSSecurityException {
/* 113 */     setSOAPElement(sct);
/*     */     
/* 115 */     this.contextDocument = getOwnerDocument();
/*     */     
/* 117 */     if (!"SecurityContextToken".equals(getLocalName()) || !XMLUtil.inWsscNS(this))
/*     */     {
/* 119 */       throw new SecurityTokenException("Expected wsc:SecurityContextToken Element, but Found " + getPrefix() + ":" + getLocalName());
/*     */     }
/*     */ 
/*     */     
/* 123 */     String wsuId = getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/* 124 */     if (!"".equals(wsuId)) {
/* 125 */       setId(wsuId);
/*     */     }
/*     */     
/* 128 */     Iterator<Node> children = getChildElements();
/* 129 */     Node object = null;
/*     */     
/* 131 */     while (children.hasNext()) {
/*     */       
/* 133 */       object = children.next();
/* 134 */       if (object.getNodeType() == 1) {
/*     */         
/* 136 */         SOAPElement element = (SOAPElement)object;
/* 137 */         if ("Identifier".equals(element.getLocalName()) && XMLUtil.inWsscNS(element)) {
/*     */           
/* 139 */           this.securityContextId = element.getFirstChild().getNodeValue(); continue;
/* 140 */         }  if ("Instance".equals(element.getLocalName()) && XMLUtil.inWsscNS(element)) {
/*     */           
/* 142 */           this.instance = element.getFirstChild().getNodeValue(); continue;
/*     */         } 
/* 144 */         this.extElements.add(object);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 149 */     if (this.securityContextId == null) {
/* 150 */       throw new XWSSecurityException("Missing Identifier subelement in SecurityContextToken");
/*     */     }
/*     */   }
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 155 */     if (this.delegateElement != null) {
/* 156 */       return this.delegateElement;
/*     */     }
/*     */     try {
/* 159 */       setSOAPElement((SOAPElement)this.contextDocument.createElementNS("http://schemas.xmlsoap.org/ws/2005/02/sc", "wsc:SecurityContextToken"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 166 */       addNamespaceDeclaration("wsc", "http://schemas.xmlsoap.org/ws/2005/02/sc");
/*     */ 
/*     */       
/* 169 */       if (this.securityContextId == null) {
/* 170 */         throw new XWSSecurityException("Missing SecurityContextToken Identifier");
/*     */       }
/* 172 */       addChildElement("Identifier", "wsc").addTextNode(this.securityContextId);
/*     */ 
/*     */       
/* 175 */       if (this.instance != null) {
/* 176 */         addChildElement("Instance", "wsc").addTextNode(this.instance);
/*     */       }
/*     */       
/* 179 */       if (this.wsuId != null) {
/* 180 */         setWsuIdAttr(this, this.wsuId);
/*     */       }
/*     */       
/* 183 */       if (this.extElements != null) {
/* 184 */         for (int i = 0; i < this.extElements.size(); i++) {
/* 185 */           Element element = this.extElements.get(i);
/* 186 */           Node newElement = this.delegateElement.getOwnerDocument().importNode(element, true);
/* 187 */           appendChild(newElement);
/*     */         }
/*     */       
/*     */       }
/* 191 */     } catch (SOAPException se) {
/* 192 */       throw new SecurityTokenException("There was an error creating SecurityContextToken " + se.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     return super.getAsSoapElement();
/*     */   }
/*     */   
/*     */   public Document getContextDocument() {
/* 201 */     return this.contextDocument;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 205 */     return "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 209 */     return this;
/*     */   }
/*     */   
/*     */   public void setId(String wsuId) {
/* 213 */     this.wsuId = wsuId;
/*     */   }
/*     */   
/*     */   public String getWsuId() {
/* 217 */     return this.wsuId;
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getIdentifier() {
/*     */     try {
/* 223 */       return new URI(this.securityContextId);
/* 224 */     } catch (Exception e) {
/* 225 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getSCId() {
/* 230 */     return this.securityContextId;
/*     */   }
/*     */   
/*     */   public String getInstance() {
/* 234 */     return this.instance;
/*     */   }
/*     */   
/*     */   public List getExtElements() {
/* 238 */     return this.extElements;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SecurityContextTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */