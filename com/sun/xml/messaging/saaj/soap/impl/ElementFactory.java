/*     */ package com.sun.xml.messaging.saaj.soap.impl;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Body1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Detail1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Envelope1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Fault1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.FaultElement1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.Header1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_1.SOAPPart1_1Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Body1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Detail1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Envelope1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Fault1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.Header1_2Impl;
/*     */ import com.sun.xml.messaging.saaj.soap.ver1_2.SOAPPart1_2Impl;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElementFactory
/*     */ {
/*     */   public static SOAPElement createElement(SOAPDocumentImpl ownerDocument, Name name) {
/*  56 */     return createElement(ownerDocument, name.getLocalName(), name.getPrefix(), name.getURI());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPElement createElement(SOAPDocumentImpl ownerDocument, QName name) {
/*  65 */     return createElement(ownerDocument, name.getLocalPart(), name.getPrefix(), name.getNamespaceURI());
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
/*     */   
/*     */   public static SOAPElement createElement(SOAPDocumentImpl ownerDocument, String localName, String prefix, String namespaceUri) {
/*  79 */     if (ownerDocument == null) {
/*  80 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri)) {
/*  81 */         ownerDocument = (new SOAPPart1_1Impl()).getDocument();
/*  82 */       } else if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/*  83 */         ownerDocument = (new SOAPPart1_2Impl()).getDocument();
/*     */       } else {
/*  85 */         ownerDocument = new SOAPDocumentImpl(null);
/*     */       } 
/*     */     }
/*     */     
/*  89 */     SOAPElement newElement = createNamedElement(ownerDocument, localName, prefix, namespaceUri);
/*     */ 
/*     */     
/*  92 */     return (newElement != null) ? newElement : new ElementImpl(ownerDocument, namespaceUri, NameImpl.createQName(prefix, localName));
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
/*     */   
/*     */   public static SOAPElement createNamedElement(SOAPDocumentImpl ownerDocument, String localName, String prefix, String namespaceUri) {
/* 106 */     if (prefix == null) {
/* 107 */       prefix = "SOAP-ENV";
/*     */     }
/*     */     
/* 110 */     if (localName.equalsIgnoreCase("Envelope")) {
/* 111 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri))
/* 112 */         return (SOAPElement)new Envelope1_1Impl(ownerDocument, prefix); 
/* 113 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/* 114 */         return (SOAPElement)new Envelope1_2Impl(ownerDocument, prefix);
/*     */       }
/*     */     } 
/* 117 */     if (localName.equalsIgnoreCase("Body")) {
/* 118 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri))
/* 119 */         return (SOAPElement)new Body1_1Impl(ownerDocument, prefix); 
/* 120 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/* 121 */         return (SOAPElement)new Body1_2Impl(ownerDocument, prefix);
/*     */       }
/*     */     } 
/* 124 */     if (localName.equalsIgnoreCase("Header")) {
/* 125 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri))
/* 126 */         return (SOAPElement)new Header1_1Impl(ownerDocument, prefix); 
/* 127 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/* 128 */         return (SOAPElement)new Header1_2Impl(ownerDocument, prefix);
/*     */       }
/*     */     } 
/* 131 */     if (localName.equalsIgnoreCase("Fault")) {
/* 132 */       Fault1_2Impl fault1_2Impl; SOAPFault fault = null;
/* 133 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri)) {
/* 134 */         Fault1_1Impl fault1_1Impl = new Fault1_1Impl(ownerDocument, prefix);
/* 135 */       } else if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/* 136 */         fault1_2Impl = new Fault1_2Impl(ownerDocument, prefix);
/*     */       } 
/*     */       
/* 139 */       if (fault1_2Impl != null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         return (SOAPElement)fault1_2Impl;
/*     */       }
/*     */     } 
/*     */     
/* 157 */     if (localName.equalsIgnoreCase("Detail")) {
/* 158 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri))
/* 159 */         return (SOAPElement)new Detail1_1Impl(ownerDocument, prefix); 
/* 160 */       if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/* 161 */         return (SOAPElement)new Detail1_2Impl(ownerDocument, prefix);
/*     */       }
/*     */     } 
/* 164 */     if (localName.equalsIgnoreCase("faultcode") || localName.equalsIgnoreCase("faultstring") || localName.equalsIgnoreCase("faultactor"))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 169 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri)) {
/* 170 */         return (SOAPElement)new FaultElement1_1Impl(ownerDocument, localName, prefix);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   protected static void invalidCreate(String msg) {
/* 180 */     throw new TreeException(msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\ElementFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */