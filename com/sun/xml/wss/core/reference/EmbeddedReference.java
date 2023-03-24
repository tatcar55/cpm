/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Node;
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
/*     */ public class EmbeddedReference
/*     */   extends ReferenceElement
/*     */ {
/*  66 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPElement embeddedElement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmbeddedReference() throws XWSSecurityException {
/*     */     try {
/*  78 */       setSOAPElement(soapFactory.createElement("Embedded", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  83 */     catch (SOAPException e) {
/*  84 */       log.log(Level.SEVERE, "WSS0750.soap.exception", new Object[] { "wsse:Embedded", e.getMessage() });
/*     */ 
/*     */       
/*  87 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmbeddedReference(SOAPElement element) throws XWSSecurityException {
/*  96 */     setSOAPElement(element);
/*     */     
/*  98 */     if (!element.getLocalName().equals("Embedded") || !XMLUtil.inWsseNS(element)) {
/*     */       
/* 100 */       log.log(Level.SEVERE, "WSS0752.invalid.embedded.reference");
/*     */       
/* 102 */       throw new XWSSecurityException("Invalid EmbeddedReference passed");
/*     */     } 
/*     */     
/* 105 */     Iterator<Node> eachChild = getChildElements();
/* 106 */     Node node = null;
/* 107 */     while (!(node instanceof SOAPElement) && eachChild.hasNext()) {
/* 108 */       node = eachChild.next();
/*     */     }
/* 110 */     if (node != null && node.getNodeType() == 1) {
/* 111 */       this.embeddedElement = (SOAPElement)node;
/*     */     } else {
/* 113 */       log.log(Level.SEVERE, "WSS0753.missing.embedded.token");
/*     */       
/* 115 */       throw new XWSSecurityException("Passed EmbeddedReference does not contain an embedded element");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getEmbeddedSoapElement() {
/* 126 */     return this.embeddedElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmbeddedSoapElement(SOAPElement element) throws XWSSecurityException {
/* 132 */     if (this.embeddedElement != null) {
/* 133 */       log.log(Level.SEVERE, "WSS0754.token.already.set");
/*     */       
/* 135 */       throw new XWSSecurityException("Embedded element is already present");
/*     */     } 
/*     */ 
/*     */     
/* 139 */     this.embeddedElement = element;
/*     */     
/*     */     try {
/* 142 */       addChildElement(this.embeddedElement);
/* 143 */     } catch (SOAPException e) {
/* 144 */       log.log(Level.SEVERE, "WSS0755.soap.exception", e.getMessage());
/*     */ 
/*     */       
/* 147 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsuId() {
/* 155 */     String wsuId = getAttribute("wsu:Id");
/* 156 */     if (wsuId.equals(""))
/* 157 */       return null; 
/* 158 */     return wsuId;
/*     */   }
/*     */   
/*     */   public void setWsuId(String wsuId) {
/* 162 */     setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 166 */     setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\EmbeddedReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */