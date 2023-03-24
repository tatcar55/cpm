/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.CDATAImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.CommentImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementFactory;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.TextImpl;
/*     */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*     */ import java.util.logging.Logger;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.CDATASection;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.EntityReference;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPDocumentImpl
/*     */   extends DocumentImpl
/*     */   implements SOAPDocument
/*     */ {
/*  58 */   private static final String XMLNS = "xmlns".intern();
/*  59 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*     */   SOAPPartImpl enclosingSOAPPart;
/*     */ 
/*     */   
/*     */   public SOAPDocumentImpl(SOAPPartImpl enclosingDocument) {
/*  66 */     this.enclosingSOAPPart = enclosingDocument;
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
/*     */ 
/*     */   
/*     */   public SOAPPartImpl getSOAPPart() {
/*  82 */     if (this.enclosingSOAPPart == null) {
/*  83 */       log.severe("SAAJ0541.soap.fragment.not.bound.to.part");
/*  84 */       throw new RuntimeException("Could not complete operation. Fragment not bound to SOAP part.");
/*     */     } 
/*  86 */     return this.enclosingSOAPPart;
/*     */   }
/*     */   
/*     */   public SOAPDocumentImpl getDocument() {
/*  90 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public DocumentType getDoctype() {
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public DOMImplementation getImplementation() {
/*  99 */     return super.getImplementation();
/*     */   }
/*     */ 
/*     */   
/*     */   public Element getDocumentElement() {
/* 104 */     getSOAPPart().doGetDocumentElement();
/* 105 */     return doGetDocumentElement();
/*     */   }
/*     */   
/*     */   protected Element doGetDocumentElement() {
/* 109 */     return super.getDocumentElement();
/*     */   }
/*     */   
/*     */   public Element createElement(String tagName) throws DOMException {
/* 113 */     return ElementFactory.createElement(this, NameImpl.getLocalNameFromTagName(tagName), NameImpl.getPrefixFromTagName(tagName), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentFragment createDocumentFragment() {
/* 121 */     return new SOAPDocumentFragment(this);
/*     */   }
/*     */   
/*     */   public Text createTextNode(String data) {
/* 125 */     return (Text)new TextImpl(this, data);
/*     */   }
/*     */   
/*     */   public Comment createComment(String data) {
/* 129 */     return (Comment)new CommentImpl(this, data);
/*     */   }
/*     */   
/*     */   public CDATASection createCDATASection(String data) throws DOMException {
/* 133 */     return (CDATASection)new CDATAImpl(this, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
/* 140 */     log.severe("SAAJ0542.soap.proc.instructions.not.allowed.in.docs");
/* 141 */     throw new UnsupportedOperationException("Processing Instructions are not allowed in SOAP documents");
/*     */   }
/*     */   
/*     */   public Attr createAttribute(String name) throws DOMException {
/* 145 */     boolean isQualifiedName = (name.indexOf(":") > 0);
/* 146 */     if (isQualifiedName) {
/* 147 */       String nsUri = null;
/* 148 */       String prefix = name.substring(0, name.indexOf(":"));
/*     */ 
/*     */       
/* 151 */       if (XMLNS.equals(prefix)) {
/* 152 */         nsUri = ElementImpl.XMLNS_URI;
/* 153 */         return createAttributeNS(nsUri, name);
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     return super.createAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityReference createEntityReference(String name) throws DOMException {
/* 162 */     log.severe("SAAJ0543.soap.entity.refs.not.allowed.in.docs");
/* 163 */     throw new UnsupportedOperationException("Entity References are not allowed in SOAP documents");
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String tagname) {
/* 167 */     return super.getElementsByTagName(tagname);
/*     */   }
/*     */ 
/*     */   
/*     */   public Node importNode(Node importedNode, boolean deep) throws DOMException {
/* 172 */     return super.importNode(importedNode, deep);
/*     */   }
/*     */ 
/*     */   
/*     */   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 177 */     return ElementFactory.createElement(this, NameImpl.getLocalNameFromTagName(qualifiedName), NameImpl.getPrefixFromTagName(qualifiedName), namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 186 */     return super.createAttributeNS(namespaceURI, qualifiedName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
/* 192 */     return super.getElementsByTagNameNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public Element getElementById(String elementId) {
/* 196 */     return super.getElementById(elementId);
/*     */   }
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/* 200 */     SOAPPartImpl newSoapPart = getSOAPPart().doCloneNode();
/* 201 */     cloneNode(newSoapPart.getDocument(), deep);
/* 202 */     return newSoapPart;
/*     */   }
/*     */   
/*     */   public void cloneNode(SOAPDocumentImpl newdoc, boolean deep) {
/* 206 */     cloneNode(newdoc, deep);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\SOAPDocumentImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */