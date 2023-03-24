/*     */ package com.sun.xml.wss.core.reference;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ public class DirectReference
/*     */   extends ReferenceElement
/*     */ {
/*  65 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectReference() throws XWSSecurityException {
/*     */     try {
/*  75 */       setSOAPElement(soapFactory.createElement("Reference", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"));
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  80 */     catch (SOAPException e) {
/*  81 */       log.log(Level.SEVERE, "WSS0750.soap.exception", new Object[] { "wsse:Reference", e.getMessage() });
/*     */ 
/*     */       
/*  84 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectReference(SOAPElement element, boolean isBSP) throws XWSSecurityException {
/*  92 */     setSOAPElement(element);
/*  93 */     if (!element.getLocalName().equals("Reference") || !XMLUtil.inWsseNS(element)) {
/*     */       
/*  95 */       log.log(Level.SEVERE, "WSS0751.invalid.direct.reference", "{" + element.getNamespaceURI() + "}" + element.getLocalName());
/*     */ 
/*     */       
/*  98 */       throw new XWSSecurityException("Invalid DirectReference passed");
/*     */     } 
/*     */     
/* 101 */     if (isBSP && getURI() == null) {
/* 102 */       throw new XWSSecurityException("Violation of BSP R3062: A wsse:Reference element in a SECURITY_TOKEN_REFERENCE MUST specify a URI attribute");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public DirectReference(SOAPElement element) throws XWSSecurityException {
/* 108 */     this(element, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueType() {
/* 115 */     String valueType = getAttribute("ValueType");
/* 116 */     if (valueType.equals(""))
/* 117 */       return null; 
/* 118 */     return valueType;
/*     */   }
/*     */   
/*     */   public void setValueType(String valueType) {
/* 122 */     setAttribute("ValueType", valueType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 130 */     String uri = getAttribute("URI");
/* 131 */     if (uri.equals(""))
/* 132 */       return null; 
/* 133 */     return uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setURI(String uri) {
/* 140 */     setAttribute("URI", uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSCTURI(String uri, String instance) {
/* 147 */     setAttribute("URI", uri);
/*     */     
/* 149 */     setAttribute("ValueType", "http://schemas.xmlsoap.org/ws/2005/02/sc/sct");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\reference\DirectReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */