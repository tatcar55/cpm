/*      */ package com.sun.xml.messaging.saaj.soap.impl;
/*      */ 
/*      */ import com.sun.org.apache.xerces.internal.dom.CoreDocumentImpl;
/*      */ import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
/*      */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*      */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*      */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*      */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*      */ import com.sun.xml.messaging.saaj.util.NamespaceContextIterator;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.Name;
/*      */ import javax.xml.soap.Node;
/*      */ import javax.xml.soap.SOAPBodyElement;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import org.w3c.dom.Attr;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.Text;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ElementImpl
/*      */   extends ElementNSImpl
/*      */   implements SOAPElement, SOAPBodyElement
/*      */ {
/*   65 */   public static final String DSIG_NS = "http://www.w3.org/2000/09/xmldsig#".intern();
/*   66 */   public static final String XENC_NS = "http://www.w3.org/2001/04/xmlenc#".intern();
/*   67 */   public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".intern();
/*      */   
/*   69 */   private AttributeManager encodingStyleAttribute = new AttributeManager();
/*      */   
/*      */   protected QName elementQName;
/*      */   
/*   73 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap.impl", "com.sun.xml.messaging.saaj.soap.impl.LocalStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   public static final String XML_URI = "http://www.w3.org/XML/1998/namespace".intern();
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, Name name) {
/*   91 */     super((CoreDocumentImpl)ownerDoc, name.getURI(), name.getQualifiedName(), name.getLocalName());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   96 */     this.elementQName = NameImpl.convertToQName(name);
/*      */   }
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, QName name) {
/*  100 */     super((CoreDocumentImpl)ownerDoc, name.getNamespaceURI(), getQualifiedName(name), name.getLocalPart());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  105 */     this.elementQName = name;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ElementImpl(SOAPDocumentImpl ownerDoc, String uri, String qualifiedName) {
/*  113 */     super((CoreDocumentImpl)ownerDoc, uri, qualifiedName);
/*  114 */     this.elementQName = new QName(uri, getLocalPart(qualifiedName), getPrefix(qualifiedName));
/*      */   }
/*      */ 
/*      */   
/*      */   public void ensureNamespaceIsDeclared(String prefix, String uri) {
/*  119 */     String alreadyDeclaredUri = getNamespaceURI(prefix);
/*  120 */     if (alreadyDeclaredUri == null || !alreadyDeclaredUri.equals(uri)) {
/*      */       try {
/*  122 */         addNamespaceDeclaration(prefix, uri);
/*  123 */       } catch (SOAPException e) {}
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Document getOwnerDocument() {
/*  129 */     Document doc = super.getOwnerDocument();
/*  130 */     if (doc instanceof SOAPDocument) {
/*  131 */       return (Document)((SOAPDocument)doc).getDocument();
/*      */     }
/*  133 */     return doc;
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(Name name) throws SOAPException {
/*  137 */     return addElement(name);
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(QName qname) throws SOAPException {
/*  141 */     return addElement(qname);
/*      */   }
/*      */   
/*      */   public SOAPElement addChildElement(String localName) throws SOAPException {
/*  145 */     return addChildElement((Name)NameImpl.createFromUnqualifiedName(localName));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(String localName, String prefix) throws SOAPException {
/*  151 */     String uri = getNamespaceURI(prefix);
/*  152 */     if (uri == null) {
/*  153 */       log.log(Level.SEVERE, "SAAJ0101.impl.parent.of.body.elem.mustbe.body", (Object[])new String[] { prefix });
/*      */ 
/*      */ 
/*      */       
/*  157 */       throw new SOAPExceptionImpl("Unable to locate namespace for prefix " + prefix);
/*      */     } 
/*      */     
/*  160 */     return addChildElement(localName, prefix, uri);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespaceURI(String prefix) {
/*  165 */     if ("xmlns".equals(prefix)) {
/*  166 */       return XMLNS_URI;
/*      */     }
/*      */     
/*  169 */     if ("xml".equals(prefix)) {
/*  170 */       return XML_URI;
/*      */     }
/*      */     
/*  173 */     if ("".equals(prefix)) {
/*      */       
/*  175 */       Node currentAncestor = this;
/*  176 */       while (currentAncestor != null && !(currentAncestor instanceof Document))
/*      */       {
/*      */         
/*  179 */         if (currentAncestor instanceof ElementImpl) {
/*  180 */           QName name = ((ElementImpl)currentAncestor).getElementQName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  191 */           if (((Element)currentAncestor).hasAttributeNS(XMLNS_URI, "xmlns")) {
/*      */ 
/*      */             
/*  194 */             String uri = ((Element)currentAncestor).getAttributeNS(XMLNS_URI, "xmlns");
/*      */ 
/*      */             
/*  197 */             if ("".equals(uri)) {
/*  198 */               return null;
/*      */             }
/*  200 */             return uri;
/*      */           } 
/*      */         } 
/*      */         
/*  204 */         currentAncestor = currentAncestor.getParentNode();
/*      */       }
/*      */     
/*  207 */     } else if (prefix != null) {
/*      */       
/*  209 */       Node currentAncestor = this;
/*      */ 
/*      */ 
/*      */       
/*  213 */       while (currentAncestor != null && !(currentAncestor instanceof Document)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  228 */         if (((Element)currentAncestor).hasAttributeNS(XMLNS_URI, prefix))
/*      */         {
/*  230 */           return ((Element)currentAncestor).getAttributeNS(XMLNS_URI, prefix);
/*      */         }
/*      */ 
/*      */         
/*  234 */         currentAncestor = currentAncestor.getParentNode();
/*      */       } 
/*      */     } 
/*      */     
/*  238 */     return null;
/*      */   }
/*      */   
/*      */   public SOAPElement setElementQName(QName newName) throws SOAPException {
/*  242 */     ElementImpl copy = new ElementImpl((SOAPDocumentImpl)getOwnerDocument(), newName);
/*      */     
/*  244 */     return replaceElementWithSOAPElement(this, copy);
/*      */   }
/*      */ 
/*      */   
/*      */   public QName createQName(String localName, String prefix) throws SOAPException {
/*  249 */     String uri = getNamespaceURI(prefix);
/*  250 */     if (uri == null) {
/*  251 */       log.log(Level.SEVERE, "SAAJ0102.impl.cannot.locate.ns", new Object[] { prefix });
/*      */       
/*  253 */       throw new SOAPException("Unable to locate namespace for prefix " + prefix);
/*      */     } 
/*      */     
/*  256 */     return new QName(uri, localName, prefix);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getNamespacePrefix(String uri) {
/*  261 */     NamespaceContextIterator eachNamespace = getNamespaceContextNodes();
/*  262 */     while (eachNamespace.hasNext()) {
/*  263 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/*  264 */       if (namespaceDecl.getNodeValue().equals(uri)) {
/*  265 */         String candidatePrefix = namespaceDecl.getLocalName();
/*  266 */         if ("xmlns".equals(candidatePrefix)) {
/*  267 */           return "";
/*      */         }
/*  269 */         return candidatePrefix;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  274 */     Node currentAncestor = this;
/*  275 */     while (currentAncestor != null && !(currentAncestor instanceof Document)) {
/*      */ 
/*      */       
/*  278 */       if (uri.equals(currentAncestor.getNamespaceURI()))
/*  279 */         return currentAncestor.getPrefix(); 
/*  280 */       currentAncestor = currentAncestor.getParentNode();
/*      */     } 
/*      */     
/*  283 */     return null;
/*      */   }
/*      */   
/*      */   protected Attr getNamespaceAttr(String prefix) {
/*  287 */     NamespaceContextIterator eachNamespace = getNamespaceContextNodes();
/*  288 */     if (!"".equals(prefix))
/*  289 */       prefix = ":" + prefix; 
/*  290 */     while (eachNamespace.hasNext()) {
/*  291 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/*  292 */       if (!"".equals(prefix)) {
/*  293 */         if (namespaceDecl.getNodeName().endsWith(prefix))
/*  294 */           return namespaceDecl;  continue;
/*      */       } 
/*  296 */       if (namespaceDecl.getNodeName().equals("xmlns")) {
/*  297 */         return namespaceDecl;
/*      */       }
/*      */     } 
/*  300 */     return null;
/*      */   }
/*      */   
/*      */   public NamespaceContextIterator getNamespaceContextNodes() {
/*  304 */     return getNamespaceContextNodes(true);
/*      */   }
/*      */   
/*      */   public NamespaceContextIterator getNamespaceContextNodes(boolean traverseStack) {
/*  308 */     return new NamespaceContextIterator(this, traverseStack);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(String localName, String prefix, String uri) throws SOAPException {
/*  317 */     SOAPElement newElement = createElement((Name)NameImpl.create(localName, prefix, uri));
/*  318 */     addNode(newElement);
/*  319 */     return convertToSoapElement(newElement);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
/*  326 */     String elementURI = element.getElementName().getURI();
/*  327 */     String localName = element.getLocalName();
/*      */     
/*  329 */     if ("http://schemas.xmlsoap.org/soap/envelope/".equals(elementURI) || "http://www.w3.org/2003/05/soap-envelope".equals(elementURI)) {
/*      */ 
/*      */ 
/*      */       
/*  333 */       if ("Envelope".equalsIgnoreCase(localName) || "Header".equalsIgnoreCase(localName) || "Body".equalsIgnoreCase(localName)) {
/*      */         
/*  335 */         log.severe("SAAJ0103.impl.cannot.add.fragements");
/*  336 */         throw new SOAPExceptionImpl("Cannot add fragments which contain elements which are in the SOAP namespace");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  341 */       if ("Fault".equalsIgnoreCase(localName) && !"Body".equalsIgnoreCase(getLocalName())) {
/*  342 */         log.severe("SAAJ0154.impl.adding.fault.to.nonbody");
/*  343 */         throw new SOAPExceptionImpl("Cannot add a SOAPFault as a child of " + getLocalName());
/*      */       } 
/*      */       
/*  346 */       if ("Detail".equalsIgnoreCase(localName) && !"Fault".equalsIgnoreCase(getLocalName())) {
/*  347 */         log.severe("SAAJ0155.impl.adding.detail.nonfault");
/*  348 */         throw new SOAPExceptionImpl("Cannot add a Detail as a child of " + getLocalName());
/*      */       } 
/*      */       
/*  351 */       if ("Fault".equalsIgnoreCase(localName)) {
/*      */         
/*  353 */         if (!elementURI.equals(getElementName().getURI())) {
/*  354 */           log.severe("SAAJ0158.impl.version.mismatch.fault");
/*  355 */           throw new SOAPExceptionImpl("SOAP Version mismatch encountered when trying to add SOAPFault to SOAPBody");
/*      */         } 
/*  357 */         Iterator it = getChildElements();
/*  358 */         if (it.hasNext()) {
/*  359 */           log.severe("SAAJ0156.impl.adding.fault.error");
/*  360 */           throw new SOAPExceptionImpl("Cannot add SOAPFault as a child of a non-Empty SOAPBody");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  366 */     String encodingStyle = element.getEncodingStyle();
/*      */     
/*  368 */     ElementImpl importedElement = (ElementImpl)importElement(element);
/*  369 */     addNode(importedElement);
/*      */     
/*  371 */     if (encodingStyle != null) {
/*  372 */       importedElement.setEncodingStyle(encodingStyle);
/*      */     }
/*  374 */     return convertToSoapElement(importedElement);
/*      */   }
/*      */   
/*      */   protected Element importElement(Element element) {
/*  378 */     Document document = getOwnerDocument();
/*  379 */     Document oldDocument = element.getOwnerDocument();
/*  380 */     if (!oldDocument.equals(document)) {
/*  381 */       return (Element)document.importNode(element, true);
/*      */     }
/*  383 */     return element;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement addElement(Name name) throws SOAPException {
/*  388 */     SOAPElement newElement = createElement(name);
/*  389 */     addNode(newElement);
/*  390 */     return circumventBug5034339(newElement);
/*      */   }
/*      */   
/*      */   protected SOAPElement addElement(QName name) throws SOAPException {
/*  394 */     SOAPElement newElement = createElement(name);
/*  395 */     addNode(newElement);
/*  396 */     return circumventBug5034339(newElement);
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement createElement(Name name) {
/*  401 */     if (isNamespaceQualified(name)) {
/*  402 */       return (SOAPElement)getOwnerDocument().createElementNS(name.getURI(), name.getQualifiedName());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  407 */     return (SOAPElement)getOwnerDocument().createElement(name.getQualifiedName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPElement createElement(QName name) {
/*  414 */     if (isNamespaceQualified(name)) {
/*  415 */       return (SOAPElement)getOwnerDocument().createElementNS(name.getNamespaceURI(), getQualifiedName(name));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  420 */     return (SOAPElement)getOwnerDocument().createElement(getQualifiedName(name));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addNode(Node newElement) throws SOAPException {
/*  426 */     insertBefore(newElement, (Node)null);
/*      */     
/*  428 */     if (getOwnerDocument() instanceof org.w3c.dom.DocumentFragment) {
/*      */       return;
/*      */     }
/*  431 */     if (newElement instanceof ElementImpl) {
/*  432 */       ElementImpl element = (ElementImpl)newElement;
/*  433 */       QName elementName = element.getElementQName();
/*  434 */       if (!"".equals(elementName.getNamespaceURI())) {
/*  435 */         element.ensureNamespaceIsDeclared(elementName.getPrefix(), elementName.getNamespaceURI());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPElement findChild(NameImpl name) {
/*  443 */     Iterator<SOAPElement> eachChild = getChildElementNodes();
/*  444 */     while (eachChild.hasNext()) {
/*  445 */       SOAPElement child = eachChild.next();
/*  446 */       if (child.getElementName().equals(name)) {
/*  447 */         return child;
/*      */       }
/*      */     } 
/*      */     
/*  451 */     return null;
/*      */   }
/*      */   
/*      */   public SOAPElement addTextNode(String text) throws SOAPException {
/*  455 */     if (text.startsWith("<![CDATA[") || text.startsWith("<![cdata["))
/*      */     {
/*  457 */       return addCDATA(text.substring("<![CDATA[".length(), text.length() - 3));
/*      */     }
/*  459 */     return addText(text);
/*      */   }
/*      */   
/*      */   protected SOAPElement addCDATA(String text) throws SOAPException {
/*  463 */     Text cdata = getOwnerDocument().createCDATASection(text);
/*      */     
/*  465 */     addNode(cdata);
/*  466 */     return this;
/*      */   }
/*      */   
/*      */   protected SOAPElement addText(String text) throws SOAPException {
/*  470 */     Text textNode = getOwnerDocument().createTextNode(text);
/*      */     
/*  472 */     addNode(textNode);
/*  473 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addAttribute(Name name, String value) throws SOAPException {
/*  478 */     addAttributeBare(name, value);
/*  479 */     if (!"".equals(name.getURI())) {
/*  480 */       ensureNamespaceIsDeclared(name.getPrefix(), name.getURI());
/*      */     }
/*  482 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addAttribute(QName qname, String value) throws SOAPException {
/*  487 */     addAttributeBare(qname, value);
/*  488 */     if (!"".equals(qname.getNamespaceURI())) {
/*  489 */       ensureNamespaceIsDeclared(qname.getPrefix(), qname.getNamespaceURI());
/*      */     }
/*  491 */     return this;
/*      */   }
/*      */   
/*      */   private void addAttributeBare(Name name, String value) {
/*  495 */     addAttributeBare(name.getURI(), name.getPrefix(), name.getQualifiedName(), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAttributeBare(QName name, String value) {
/*  502 */     addAttributeBare(name.getNamespaceURI(), name.getPrefix(), getQualifiedName(name), value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addAttributeBare(String uri, String prefix, String qualifiedName, String value) {
/*  515 */     uri = (uri.length() == 0) ? null : uri;
/*  516 */     if (qualifiedName.equals("xmlns")) {
/*  517 */       uri = XMLNS_URI;
/*      */     }
/*      */     
/*  520 */     if (uri == null) {
/*  521 */       setAttribute(qualifiedName, value);
/*      */     } else {
/*  523 */       setAttributeNS(uri, qualifiedName, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public SOAPElement addNamespaceDeclaration(String prefix, String uri) throws SOAPException {
/*  529 */     if (prefix.length() > 0) {
/*  530 */       setAttributeNS(XMLNS_URI, "xmlns:" + prefix, uri);
/*      */     } else {
/*  532 */       setAttributeNS(XMLNS_URI, "xmlns", uri);
/*      */     } 
/*      */ 
/*      */     
/*  536 */     return this;
/*      */   }
/*      */   
/*      */   public String getAttributeValue(Name name) {
/*  540 */     return getAttributeValueFrom(this, name);
/*      */   }
/*      */   
/*      */   public String getAttributeValue(QName qname) {
/*  544 */     return getAttributeValueFrom(this, qname.getNamespaceURI(), qname.getLocalPart(), qname.getPrefix(), getQualifiedName(qname));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterator getAllAttributes() {
/*  553 */     Iterator<Name> i = getAllAttributesFrom(this);
/*  554 */     ArrayList<Name> list = new ArrayList();
/*  555 */     while (i.hasNext()) {
/*  556 */       Name name = i.next();
/*  557 */       if (!"xmlns".equalsIgnoreCase(name.getPrefix()))
/*  558 */         list.add(name); 
/*      */     } 
/*  560 */     return list.iterator();
/*      */   }
/*      */   
/*      */   public Iterator getAllAttributesAsQNames() {
/*  564 */     Iterator<Name> i = getAllAttributesFrom(this);
/*  565 */     ArrayList<QName> list = new ArrayList();
/*  566 */     while (i.hasNext()) {
/*  567 */       Name name = i.next();
/*  568 */       if (!"xmlns".equalsIgnoreCase(name.getPrefix())) {
/*  569 */         list.add(NameImpl.convertToQName(name));
/*      */       }
/*      */     } 
/*  572 */     return list.iterator();
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterator getNamespacePrefixes() {
/*  577 */     return doGetNamespacePrefixes(false);
/*      */   }
/*      */   
/*      */   public Iterator getVisibleNamespacePrefixes() {
/*  581 */     return doGetNamespacePrefixes(true);
/*      */   }
/*      */   
/*      */   protected Iterator doGetNamespacePrefixes(final boolean deep) {
/*  585 */     return new Iterator() {
/*  586 */         String next = null;
/*  587 */         String last = null;
/*  588 */         NamespaceContextIterator eachNamespace = ElementImpl.this.getNamespaceContextNodes(deep);
/*      */ 
/*      */         
/*      */         void findNext() {
/*  592 */           while (this.next == null && this.eachNamespace.hasNext()) {
/*  593 */             String attributeKey = this.eachNamespace.nextNamespaceAttr().getNodeName();
/*      */             
/*  595 */             if (attributeKey.startsWith("xmlns:")) {
/*  596 */               this.next = attributeKey.substring("xmlns:".length());
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/*      */         public boolean hasNext() {
/*  602 */           findNext();
/*  603 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  607 */           findNext();
/*  608 */           if (this.next == null) {
/*  609 */             throw new NoSuchElementException();
/*      */           }
/*      */           
/*  612 */           this.last = this.next;
/*  613 */           this.next = null;
/*  614 */           return this.last;
/*      */         }
/*      */         
/*      */         public void remove() {
/*  618 */           if (this.last == null) {
/*  619 */             throw new IllegalStateException();
/*      */           }
/*  621 */           this.eachNamespace.remove();
/*  622 */           this.next = null;
/*  623 */           this.last = null;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Name getElementName() {
/*  629 */     return NameImpl.convertToName(this.elementQName);
/*      */   }
/*      */   
/*      */   public QName getElementQName() {
/*  633 */     return this.elementQName;
/*      */   }
/*      */   
/*      */   public boolean removeAttribute(Name name) {
/*  637 */     return removeAttribute(name.getURI(), name.getLocalName());
/*      */   }
/*      */   
/*      */   public boolean removeAttribute(QName name) {
/*  641 */     return removeAttribute(name.getNamespaceURI(), name.getLocalPart());
/*      */   }
/*      */   
/*      */   private boolean removeAttribute(String uri, String localName) {
/*  645 */     String nonzeroLengthUri = (uri == null || uri.length() == 0) ? null : uri;
/*      */     
/*  647 */     Attr attribute = getAttributeNodeNS(nonzeroLengthUri, localName);
/*      */     
/*  649 */     if (attribute == null) {
/*  650 */       return false;
/*      */     }
/*  652 */     removeAttributeNode(attribute);
/*  653 */     return true;
/*      */   }
/*      */   
/*      */   public boolean removeNamespaceDeclaration(String prefix) {
/*  657 */     Attr declaration = getNamespaceAttr(prefix);
/*  658 */     if (declaration == null) {
/*  659 */       return false;
/*      */     }
/*      */     try {
/*  662 */       removeAttributeNode(declaration);
/*  663 */     } catch (DOMException de) {}
/*      */ 
/*      */     
/*  666 */     return true;
/*      */   }
/*      */   
/*      */   public Iterator getChildElements() {
/*  670 */     return getChildElementsFrom(this);
/*      */   }
/*      */   
/*      */   protected SOAPElement convertToSoapElement(Element element) {
/*  674 */     if (element instanceof SOAPElement) {
/*  675 */       return (SOAPElement)element;
/*      */     }
/*  677 */     return replaceElementWithSOAPElement(element, (ElementImpl)createElement(NameImpl.copyElementName(element)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static SOAPElement replaceElementWithSOAPElement(Element element, ElementImpl copy) {
/*  687 */     Iterator<Name> eachAttribute = getAllAttributesFrom(element);
/*  688 */     while (eachAttribute.hasNext()) {
/*  689 */       Name name = eachAttribute.next();
/*  690 */       copy.addAttributeBare(name, getAttributeValueFrom(element, name));
/*      */     } 
/*      */     
/*  693 */     Iterator<Node> eachChild = getChildElementsFrom(element);
/*  694 */     while (eachChild.hasNext()) {
/*  695 */       Node nextChild = eachChild.next();
/*  696 */       copy.insertBefore(nextChild, (Node)null);
/*      */     } 
/*      */     
/*  699 */     Node parent = element.getParentNode();
/*  700 */     if (parent != null) {
/*  701 */       parent.replaceChild(copy, element);
/*      */     }
/*      */     
/*  704 */     return copy;
/*      */   }
/*      */   
/*      */   protected Iterator getChildElementNodes() {
/*  708 */     return new Iterator() {
/*  709 */         Iterator eachNode = ElementImpl.this.getChildElements();
/*  710 */         Node next = null;
/*  711 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/*  714 */           if (this.next == null) {
/*  715 */             while (this.eachNode.hasNext()) {
/*  716 */               Node node = this.eachNode.next();
/*  717 */               if (node instanceof SOAPElement) {
/*  718 */                 this.next = node;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*  723 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  727 */           if (hasNext()) {
/*  728 */             this.last = this.next;
/*  729 */             this.next = null;
/*  730 */             return this.last;
/*      */           } 
/*  732 */           throw new NoSuchElementException();
/*      */         }
/*      */         
/*      */         public void remove() {
/*  736 */           if (this.last == null) {
/*  737 */             throw new IllegalStateException();
/*      */           }
/*  739 */           Node target = this.last;
/*  740 */           this.last = null;
/*  741 */           ElementImpl.this.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public Iterator getChildElements(Name name) {
/*  747 */     return getChildElements(name.getURI(), name.getLocalName());
/*      */   }
/*      */   
/*      */   public Iterator getChildElements(QName qname) {
/*  751 */     return getChildElements(qname.getNamespaceURI(), qname.getLocalPart());
/*      */   }
/*      */   
/*      */   private Iterator getChildElements(final String nameUri, final String nameLocal) {
/*  755 */     return new Iterator() {
/*  756 */         Iterator eachElement = ElementImpl.this.getChildElementNodes();
/*  757 */         Node next = null;
/*  758 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/*  761 */           if (this.next == null) {
/*  762 */             while (this.eachElement.hasNext()) {
/*  763 */               Node element = this.eachElement.next();
/*  764 */               String elementUri = element.getNamespaceURI();
/*  765 */               elementUri = (elementUri == null) ? "" : elementUri;
/*  766 */               String elementName = element.getLocalName();
/*  767 */               if (elementUri.equals(nameUri) && elementName.equals(nameLocal)) {
/*      */                 
/*  769 */                 this.next = element;
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           }
/*  774 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/*  778 */           if (!hasNext()) {
/*  779 */             throw new NoSuchElementException();
/*      */           }
/*  781 */           this.last = this.next;
/*  782 */           this.next = null;
/*  783 */           return this.last;
/*      */         }
/*      */         
/*      */         public void remove() {
/*  787 */           if (this.last == null) {
/*  788 */             throw new IllegalStateException();
/*      */           }
/*  790 */           Node target = this.last;
/*  791 */           this.last = null;
/*  792 */           ElementImpl.this.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public void removeContents() {
/*  798 */     Node currentChild = getFirstChild();
/*      */     
/*  800 */     while (currentChild != null) {
/*  801 */       Node temp = currentChild.getNextSibling();
/*  802 */       if (currentChild instanceof Node) {
/*  803 */         ((Node)currentChild).detachNode();
/*      */       } else {
/*  805 */         Node parent = currentChild.getParentNode();
/*  806 */         if (parent != null) {
/*  807 */           parent.removeChild(currentChild);
/*      */         }
/*      */       } 
/*      */       
/*  811 */       currentChild = temp;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setEncodingStyle(String encodingStyle) throws SOAPException {
/*  816 */     if (!"".equals(encodingStyle)) {
/*      */       try {
/*  818 */         new URI(encodingStyle);
/*  819 */       } catch (URISyntaxException m) {
/*  820 */         log.log(Level.SEVERE, "SAAJ0105.impl.encoding.style.mustbe.valid.URI", (Object[])new String[] { encodingStyle });
/*      */ 
/*      */ 
/*      */         
/*  824 */         throw new IllegalArgumentException("Encoding style (" + encodingStyle + ") should be a valid URI");
/*      */       } 
/*      */     }
/*      */     
/*  828 */     this.encodingStyleAttribute.setValue(encodingStyle);
/*  829 */     tryToFindEncodingStyleAttributeName();
/*      */   }
/*      */   
/*      */   public String getEncodingStyle() {
/*  833 */     String encodingStyle = this.encodingStyleAttribute.getValue();
/*  834 */     if (encodingStyle != null)
/*  835 */       return encodingStyle; 
/*  836 */     String soapNamespace = getSOAPNamespace();
/*  837 */     if (soapNamespace != null) {
/*  838 */       Attr attr = getAttributeNodeNS(soapNamespace, "encodingStyle");
/*  839 */       if (attr != null) {
/*  840 */         encodingStyle = attr.getValue();
/*      */         try {
/*  842 */           setEncodingStyle(encodingStyle);
/*  843 */         } catch (SOAPException se) {}
/*      */ 
/*      */         
/*  846 */         return encodingStyle;
/*      */       } 
/*      */     } 
/*  849 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getValue() {
/*  854 */     Node valueNode = getValueNode();
/*  855 */     return (valueNode == null) ? null : valueNode.getValue();
/*      */   }
/*      */   
/*      */   public void setValue(String value) {
/*  859 */     Node valueNode = getValueNodeStrict();
/*  860 */     if (valueNode != null) {
/*  861 */       valueNode.setNodeValue(value);
/*      */     } else {
/*      */       try {
/*  864 */         addTextNode(value);
/*  865 */       } catch (SOAPException e) {
/*  866 */         throw new RuntimeException(e.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Node getValueNodeStrict() {
/*  872 */     Node node = getFirstChild();
/*  873 */     if (node != null) {
/*  874 */       if (node.getNextSibling() == null && node.getNodeType() == 3)
/*      */       {
/*  876 */         return node;
/*      */       }
/*  878 */       log.severe("SAAJ0107.impl.elem.child.not.single.text");
/*  879 */       throw new IllegalStateException();
/*      */     } 
/*      */ 
/*      */     
/*  883 */     return null;
/*      */   }
/*      */   
/*      */   protected Node getValueNode() {
/*  887 */     Iterator<Node> i = getChildElements();
/*  888 */     while (i.hasNext()) {
/*  889 */       Node n = i.next();
/*  890 */       if (n.getNodeType() == 3 || n.getNodeType() == 4) {
/*      */ 
/*      */         
/*  893 */         normalize();
/*      */ 
/*      */         
/*  896 */         return n;
/*      */       } 
/*      */     } 
/*  899 */     return null;
/*      */   }
/*      */   
/*      */   public void setParentElement(SOAPElement element) throws SOAPException {
/*  903 */     if (element == null) {
/*  904 */       log.severe("SAAJ0106.impl.no.null.to.parent.elem");
/*  905 */       throw new SOAPException("Cannot pass NULL to setParentElement");
/*      */     } 
/*  907 */     element.addChildElement(this);
/*  908 */     findEncodingStyleAttributeName();
/*      */   }
/*      */   
/*      */   protected void findEncodingStyleAttributeName() throws SOAPException {
/*  912 */     String soapNamespace = getSOAPNamespace();
/*  913 */     if (soapNamespace != null) {
/*  914 */       String soapNamespacePrefix = getNamespacePrefix(soapNamespace);
/*  915 */       if (soapNamespacePrefix != null) {
/*  916 */         setEncodingStyleNamespace(soapNamespace, soapNamespacePrefix);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setEncodingStyleNamespace(String soapNamespace, String soapNamespacePrefix) throws SOAPException {
/*  925 */     NameImpl nameImpl = NameImpl.create("encodingStyle", soapNamespacePrefix, soapNamespace);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  930 */     this.encodingStyleAttribute.setName((Name)nameImpl);
/*      */   }
/*      */   
/*      */   public SOAPElement getParentElement() {
/*  934 */     Node parentNode = getParentNode();
/*  935 */     if (parentNode instanceof SOAPDocument) {
/*  936 */       return null;
/*      */     }
/*  938 */     return (SOAPElement)parentNode;
/*      */   }
/*      */   
/*      */   protected String getSOAPNamespace() {
/*  942 */     String soapNamespace = null;
/*      */     
/*  944 */     SOAPElement antecedent = this;
/*  945 */     while (antecedent != null) {
/*  946 */       Name antecedentName = antecedent.getElementName();
/*  947 */       String antecedentNamespace = antecedentName.getURI();
/*      */       
/*  949 */       if ("http://schemas.xmlsoap.org/soap/envelope/".equals(antecedentNamespace) || "http://www.w3.org/2003/05/soap-envelope".equals(antecedentNamespace)) {
/*      */ 
/*      */         
/*  952 */         soapNamespace = antecedentNamespace;
/*      */         
/*      */         break;
/*      */       } 
/*  956 */       antecedent = antecedent.getParentElement();
/*      */     } 
/*      */     
/*  959 */     return soapNamespace;
/*      */   }
/*      */   
/*      */   public void detachNode() {
/*  963 */     Node parent = getParentNode();
/*  964 */     if (parent != null) {
/*  965 */       parent.removeChild(this);
/*      */     }
/*  967 */     this.encodingStyleAttribute.clearNameAndValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void tryToFindEncodingStyleAttributeName() {
/*      */     try {
/*  974 */       findEncodingStyleAttributeName();
/*  975 */     } catch (SOAPException e) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public void recycleNode() {
/*  980 */     detachNode();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   class AttributeManager
/*      */   {
/*  987 */     Name attributeName = null;
/*  988 */     String attributeValue = null;
/*      */     
/*      */     public void setName(Name newName) throws SOAPException {
/*  991 */       clearAttribute();
/*  992 */       this.attributeName = newName;
/*  993 */       reconcileAttribute();
/*      */     }
/*      */     public void clearName() {
/*  996 */       clearAttribute();
/*  997 */       this.attributeName = null;
/*      */     }
/*      */     public void setValue(String value) throws SOAPException {
/* 1000 */       this.attributeValue = value;
/* 1001 */       reconcileAttribute();
/*      */     }
/*      */     public Name getName() {
/* 1004 */       return this.attributeName;
/*      */     }
/*      */     public String getValue() {
/* 1007 */       return this.attributeValue;
/*      */     }
/*      */ 
/*      */     
/*      */     public void clearNameAndValue() {
/* 1012 */       this.attributeName = null;
/* 1013 */       this.attributeValue = null;
/*      */     }
/*      */     
/*      */     private void reconcileAttribute() throws SOAPException {
/* 1017 */       if (this.attributeName != null) {
/* 1018 */         ElementImpl.this.removeAttribute(this.attributeName);
/* 1019 */         if (this.attributeValue != null)
/* 1020 */           ElementImpl.this.addAttribute(this.attributeName, this.attributeValue); 
/*      */       } 
/*      */     }
/*      */     
/*      */     private void clearAttribute() {
/* 1025 */       if (this.attributeName != null) {
/* 1026 */         ElementImpl.this.removeAttribute(this.attributeName);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Attr getNamespaceAttrFrom(Element element, String prefix) {
/* 1034 */     NamespaceContextIterator eachNamespace = new NamespaceContextIterator(element);
/*      */     
/* 1036 */     while (eachNamespace.hasNext()) {
/* 1037 */       Attr namespaceDecl = eachNamespace.nextNamespaceAttr();
/* 1038 */       String declaredPrefix = NameImpl.getLocalNameFromTagName(namespaceDecl.getNodeName());
/*      */       
/* 1040 */       if (declaredPrefix.equals(prefix)) {
/* 1041 */         return namespaceDecl;
/*      */       }
/*      */     } 
/* 1044 */     return null;
/*      */   }
/*      */   
/*      */   protected static Iterator getAllAttributesFrom(Element element) {
/* 1048 */     final NamedNodeMap attributes = element.getAttributes();
/*      */     
/* 1050 */     return new Iterator() {
/* 1051 */         int attributesLength = attributes.getLength();
/* 1052 */         int attributeIndex = 0;
/*      */         String currentName;
/*      */         
/*      */         public boolean hasNext() {
/* 1056 */           return (this.attributeIndex < this.attributesLength);
/*      */         }
/*      */         
/*      */         public Object next() {
/* 1060 */           if (!hasNext()) {
/* 1061 */             throw new NoSuchElementException();
/*      */           }
/* 1063 */           Node current = attributes.item(this.attributeIndex++);
/* 1064 */           this.currentName = current.getNodeName();
/*      */           
/* 1066 */           String prefix = NameImpl.getPrefixFromTagName(this.currentName);
/* 1067 */           if (prefix.length() == 0) {
/* 1068 */             return NameImpl.createFromUnqualifiedName(this.currentName);
/*      */           }
/* 1070 */           Name attributeName = NameImpl.createFromQualifiedName(this.currentName, current.getNamespaceURI());
/*      */ 
/*      */ 
/*      */           
/* 1074 */           return attributeName;
/*      */         }
/*      */ 
/*      */         
/*      */         public void remove() {
/* 1079 */           if (this.currentName == null) {
/* 1080 */             throw new IllegalStateException();
/*      */           }
/* 1082 */           attributes.removeNamedItem(this.currentName);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   protected static String getAttributeValueFrom(Element element, Name name) {
/* 1088 */     return getAttributeValueFrom(element, name.getURI(), name.getLocalName(), name.getPrefix(), name.getQualifiedName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getAttributeValueFrom(Element element, String uri, String localName, String prefix, String qualifiedName) {
/* 1103 */     String nonzeroLengthUri = (uri == null || uri.length() == 0) ? null : uri;
/*      */ 
/*      */     
/* 1106 */     boolean mustUseGetAttributeNodeNS = (nonzeroLengthUri != null);
/*      */     
/* 1108 */     if (mustUseGetAttributeNodeNS) {
/*      */       
/* 1110 */       if (!element.hasAttributeNS(uri, localName)) {
/* 1111 */         return null;
/*      */       }
/*      */       
/* 1114 */       String attrValue = element.getAttributeNS(nonzeroLengthUri, localName);
/*      */ 
/*      */       
/* 1117 */       return attrValue;
/*      */     } 
/*      */     
/* 1120 */     Attr attribute = null;
/* 1121 */     attribute = element.getAttributeNode(qualifiedName);
/*      */     
/* 1123 */     return (attribute == null) ? null : attribute.getValue();
/*      */   }
/*      */   
/*      */   protected static Iterator getChildElementsFrom(final Element element) {
/* 1127 */     return new Iterator() {
/* 1128 */         Node next = element.getFirstChild();
/* 1129 */         Node nextNext = null;
/* 1130 */         Node last = null;
/*      */         
/*      */         public boolean hasNext() {
/* 1133 */           if (this.next != null) {
/* 1134 */             return true;
/*      */           }
/* 1136 */           if (this.next == null && this.nextNext != null) {
/* 1137 */             this.next = this.nextNext;
/*      */           }
/*      */           
/* 1140 */           return (this.next != null);
/*      */         }
/*      */         
/*      */         public Object next() {
/* 1144 */           if (hasNext()) {
/* 1145 */             this.last = this.next;
/* 1146 */             this.next = null;
/*      */             
/* 1148 */             if (element instanceof ElementImpl && this.last instanceof Element)
/*      */             {
/* 1150 */               this.last = ((ElementImpl)element).convertToSoapElement((Element)this.last);
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 1155 */             this.nextNext = this.last.getNextSibling();
/* 1156 */             return this.last;
/*      */           } 
/* 1158 */           throw new NoSuchElementException();
/*      */         }
/*      */         
/*      */         public void remove() {
/* 1162 */           if (this.last == null) {
/* 1163 */             throw new IllegalStateException();
/*      */           }
/* 1165 */           Node target = this.last;
/* 1166 */           this.last = null;
/* 1167 */           element.removeChild(target);
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static String getQualifiedName(QName name) {
/* 1173 */     String prefix = name.getPrefix();
/* 1174 */     String localName = name.getLocalPart();
/* 1175 */     String qualifiedName = null;
/*      */     
/* 1177 */     if (prefix != null && prefix.length() > 0) {
/* 1178 */       qualifiedName = prefix + ":" + localName;
/*      */     } else {
/* 1180 */       qualifiedName = localName;
/*      */     } 
/* 1182 */     return qualifiedName;
/*      */   }
/*      */   
/*      */   public static String getLocalPart(String qualifiedName) {
/* 1186 */     if (qualifiedName == null)
/*      */     {
/* 1188 */       throw new IllegalArgumentException("Cannot get local name for a \"null\" qualified name");
/*      */     }
/*      */     
/* 1191 */     int index = qualifiedName.indexOf(':');
/* 1192 */     if (index < 0) {
/* 1193 */       return qualifiedName;
/*      */     }
/* 1195 */     return qualifiedName.substring(index + 1);
/*      */   }
/*      */   
/*      */   public static String getPrefix(String qualifiedName) {
/* 1199 */     if (qualifiedName == null)
/*      */     {
/* 1201 */       throw new IllegalArgumentException("Cannot get prefix for a  \"null\" qualified name");
/*      */     }
/*      */     
/* 1204 */     int index = qualifiedName.indexOf(':');
/* 1205 */     if (index < 0) {
/* 1206 */       return "";
/*      */     }
/* 1208 */     return qualifiedName.substring(0, index);
/*      */   }
/*      */   
/*      */   protected boolean isNamespaceQualified(Name name) {
/* 1212 */     return !"".equals(name.getURI());
/*      */   }
/*      */   
/*      */   protected boolean isNamespaceQualified(QName name) {
/* 1216 */     return !"".equals(name.getNamespaceURI());
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPElement circumventBug5034339(SOAPElement element) {
/* 1221 */     Name elementName = element.getElementName();
/* 1222 */     if (!isNamespaceQualified(elementName)) {
/* 1223 */       String prefix = elementName.getPrefix();
/* 1224 */       String defaultNamespace = getNamespaceURI(prefix);
/* 1225 */       if (defaultNamespace != null) {
/* 1226 */         NameImpl nameImpl = NameImpl.create(elementName.getLocalName(), elementName.getPrefix(), defaultNamespace);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1231 */         SOAPElement newElement = createElement((Name)nameImpl);
/* 1232 */         replaceChild(newElement, element);
/* 1233 */         return newElement;
/*      */       } 
/*      */     } 
/* 1236 */     return element;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value) {
/*      */     String localName;
/* 1244 */     int index = qualifiedName.indexOf(':');
/*      */     
/* 1246 */     if (index < 0) {
/* 1247 */       localName = qualifiedName;
/*      */     } else {
/* 1249 */       localName = qualifiedName.substring(index + 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1261 */     super.setAttributeNS(namespaceURI, qualifiedName, value);
/*      */     
/* 1263 */     String tmpURI = getNamespaceURI();
/* 1264 */     boolean isIDNS = false;
/* 1265 */     if (tmpURI != null && (tmpURI.equals(DSIG_NS) || tmpURI.equals(XENC_NS))) {
/* 1266 */       isIDNS = true;
/*      */     }
/*      */ 
/*      */     
/* 1270 */     if (localName.equals("Id"))
/* 1271 */       if (namespaceURI == null || namespaceURI.equals("")) {
/* 1272 */         setIdAttribute(localName, true);
/* 1273 */       } else if (isIDNS || WSU_NS.equals(namespaceURI)) {
/* 1274 */         setIdAttributeNS(namespaceURI, localName, true);
/*      */       }  
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\impl\ElementImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */