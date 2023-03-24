/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementFactory;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
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
/*     */ public abstract class SOAPFactoryImpl
/*     */   extends SOAPFactory
/*     */ {
/*  63 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*     */   protected abstract SOAPDocumentImpl createDocument();
/*     */   
/*     */   public SOAPElement createElement(String tagName) throws SOAPException {
/*  69 */     if (tagName == null) {
/*  70 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "tagName", "SOAPFactory.createElement" });
/*     */ 
/*     */       
/*  73 */       throw new SOAPException("Null tagName argument passed to createElement");
/*     */     } 
/*  75 */     return ElementFactory.createElement(createDocument(), NameImpl.createFromTagName(tagName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement createElement(Name name) throws SOAPException {
/*  82 */     if (name == null) {
/*  83 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "name", "SOAPFactory.createElement" });
/*     */       
/*  85 */       throw new SOAPException("Null name argument passed to createElement");
/*     */     } 
/*  87 */     return ElementFactory.createElement(createDocument(), name);
/*     */   }
/*     */   
/*     */   public SOAPElement createElement(QName qname) throws SOAPException {
/*  91 */     if (qname == null) {
/*  92 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "qname", "SOAPFactory.createElement" });
/*     */       
/*  94 */       throw new SOAPException("Null qname argument passed to createElement");
/*     */     } 
/*  96 */     return ElementFactory.createElement(createDocument(), qname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement createElement(String localName, String prefix, String uri) throws SOAPException {
/* 107 */     if (localName == null) {
/* 108 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "localName", "SOAPFactory.createElement" });
/*     */       
/* 110 */       throw new SOAPException("Null localName argument passed to createElement");
/*     */     } 
/* 112 */     return ElementFactory.createElement(createDocument(), localName, prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name createName(String localName, String prefix, String uri) throws SOAPException {
/* 120 */     if (localName == null) {
/* 121 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "localName", "SOAPFactory.createName" });
/*     */ 
/*     */       
/* 124 */       throw new SOAPException("Null localName argument passed to createName");
/*     */     } 
/* 126 */     return (Name)NameImpl.create(localName, prefix, uri);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Name createName(String localName) throws SOAPException {
/* 133 */     if (localName == null) {
/* 134 */       log.log(Level.SEVERE, "SAAJ0567.soap.null.input", new Object[] { "localName", "SOAPFactory.createName" });
/*     */ 
/*     */       
/* 137 */       throw new SOAPException("Null localName argument passed to createName");
/*     */     } 
/* 139 */     return (Name)NameImpl.createFromUnqualifiedName(localName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement createElement(Element domElement) throws SOAPException {
/* 145 */     if (domElement == null) {
/* 146 */       return null;
/*     */     }
/* 148 */     return convertToSoapElement(domElement);
/*     */   }
/*     */ 
/*     */   
/*     */   private SOAPElement convertToSoapElement(Element element) throws SOAPException {
/* 153 */     if (element instanceof SOAPElement) {
/* 154 */       return (SOAPElement)element;
/*     */     }
/*     */     
/* 157 */     SOAPElement copy = createElement(element.getLocalName(), element.getPrefix(), element.getNamespaceURI());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     Document ownerDoc = copy.getOwnerDocument();
/*     */     
/* 164 */     NamedNodeMap attrMap = element.getAttributes();
/* 165 */     for (int i = 0; i < attrMap.getLength(); i++) {
/* 166 */       Attr nextAttr = (Attr)attrMap.item(i);
/* 167 */       Attr importedAttr = (Attr)ownerDoc.importNode(nextAttr, true);
/* 168 */       copy.setAttributeNodeNS(importedAttr);
/*     */     } 
/*     */ 
/*     */     
/* 172 */     NodeList nl = element.getChildNodes();
/* 173 */     for (int j = 0; j < nl.getLength(); j++) {
/* 174 */       Node next = nl.item(j);
/* 175 */       Node imported = ownerDoc.importNode(next, true);
/* 176 */       copy.appendChild(imported);
/*     */     } 
/*     */     
/* 179 */     return copy;
/*     */   }
/*     */   
/*     */   public Detail createDetail() throws SOAPException {
/* 183 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException {
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SOAPFault createFault() throws SOAPException {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\SOAPFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */