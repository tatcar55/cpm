/*     */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*     */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.HeaderImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Header1_2Impl
/*     */   extends HeaderImpl
/*     */ {
/*  66 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.ver1_2", "com.sun.xml.messaging.saaj.soap.ver1_2.LocalStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Header1_2Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/*  72 */     super(ownerDocument, NameImpl.createHeader1_2Name(prefix));
/*     */   }
/*     */   
/*     */   protected NameImpl getNotUnderstoodName() {
/*  76 */     return NameImpl.createNotUnderstood1_2Name(null);
/*     */   }
/*     */   
/*     */   protected NameImpl getUpgradeName() {
/*  80 */     return NameImpl.createUpgrade1_2Name(null);
/*     */   }
/*     */   
/*     */   protected NameImpl getSupportedEnvelopeName() {
/*  84 */     return NameImpl.createSupportedEnvelope1_2Name(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPHeaderElement addNotUnderstoodHeaderElement(QName sourceName) throws SOAPException {
/*  90 */     if (sourceName == null) {
/*  91 */       log.severe("SAAJ0410.ver1_2.no.null.to.addNotUnderstoodHeader");
/*  92 */       throw new SOAPException("Cannot pass NULL to addNotUnderstoodHeaderElement");
/*     */     } 
/*  94 */     if ("".equals(sourceName.getNamespaceURI())) {
/*  95 */       log.severe("SAAJ0417.ver1_2.qname.not.ns.qualified");
/*  96 */       throw new SOAPException("The qname passed to addNotUnderstoodHeaderElement must be namespace-qualified");
/*     */     } 
/*  98 */     String prefix = sourceName.getPrefix();
/*  99 */     if ("".equals(prefix)) {
/* 100 */       prefix = "ns1";
/*     */     }
/* 102 */     NameImpl nameImpl = getNotUnderstoodName();
/* 103 */     SOAPHeaderElement notunderstoodHeaderElement = (SOAPHeaderElement)addChildElement((Name)nameImpl);
/*     */     
/* 105 */     notunderstoodHeaderElement.addAttribute((Name)NameImpl.createFromUnqualifiedName("qname"), getQualifiedName(new QName(sourceName.getNamespaceURI(), sourceName.getLocalPart(), prefix)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     notunderstoodHeaderElement.addNamespaceDeclaration(prefix, sourceName.getNamespaceURI());
/*     */ 
/*     */     
/* 115 */     return notunderstoodHeaderElement;
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String text) throws SOAPException {
/* 119 */     log.log(Level.SEVERE, "SAAJ0416.ver1_2.adding.text.not.legal", getElementQName());
/*     */ 
/*     */ 
/*     */     
/* 123 */     throw new SOAPExceptionImpl("Adding text to SOAP 1.2 Header is not legal");
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPHeaderElement createHeaderElement(Name name) throws SOAPException {
/* 128 */     String uri = name.getURI();
/* 129 */     if (uri == null || uri.equals("")) {
/* 130 */       log.severe("SAAJ0413.ver1_2.header.elems.must.be.ns.qualified");
/* 131 */       throw new SOAPExceptionImpl("SOAP 1.2 header elements must be namespace qualified");
/*     */     } 
/* 133 */     return (SOAPHeaderElement)new HeaderElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPHeaderElement createHeaderElement(QName name) throws SOAPException {
/* 140 */     String uri = name.getNamespaceURI();
/* 141 */     if (uri == null || uri.equals("")) {
/* 142 */       log.severe("SAAJ0413.ver1_2.header.elems.must.be.ns.qualified");
/* 143 */       throw new SOAPExceptionImpl("SOAP 1.2 header elements must be namespace qualified");
/*     */     } 
/* 145 */     return (SOAPHeaderElement)new HeaderElement1_2Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/* 151 */     log.severe("SAAJ0409.ver1_2.no.encodingstyle.in.header");
/* 152 */     throw new SOAPExceptionImpl("encodingStyle attribute cannot appear on Header");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/* 157 */     if (name.getLocalName().equals("encodingStyle") && name.getURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/*     */       
/* 160 */       setEncodingStyle(value);
/*     */     }
/* 162 */     return super.addAttribute(name, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(QName name, String value) throws SOAPException {
/* 167 */     if (name.getLocalPart().equals("encodingStyle") && name.getNamespaceURI().equals("http://www.w3.org/2003/05/soap-envelope"))
/*     */     {
/*     */       
/* 170 */       setEncodingStyle(value);
/*     */     }
/* 172 */     return super.addAttribute(name, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\Header1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */