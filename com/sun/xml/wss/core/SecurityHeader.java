/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SOAPElementExtension;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.Node;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.TypeInfo;
/*     */ import org.w3c.dom.UserDataHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityHeader
/*     */   extends SOAPElementExtension
/*     */   implements SOAPElement
/*     */ {
/*     */   private final SOAPElement delegateHeader;
/*     */   private Document ownerDoc;
/*  78 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPElement currentSoapElement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPElement topMostSoapElement;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityHeader(SOAPElement delegateHeader) {
/*  94 */     this.delegateHeader = delegateHeader;
/*  95 */     this.ownerDoc = delegateHeader.getOwnerDocument();
/*  96 */     this.topMostSoapElement = getFirstChildElement();
/*  97 */     this.currentSoapElement = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertHeaderBlock(SecurityHeaderBlock block) throws XWSSecurityException {
/* 107 */     SOAPElement elementToInsert = block.getAsSoapElement();
/*     */     try {
/* 109 */       if (elementToInsert.getOwnerDocument() != this.ownerDoc) {
/* 110 */         elementToInsert = (SOAPElement)this.ownerDoc.importNode(elementToInsert, true);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 115 */       updateTopMostSoapElement();
/*     */       
/* 117 */       insertBefore(elementToInsert, this.topMostSoapElement);
/*     */     }
/* 119 */     catch (DOMException e) {
/* 120 */       log.log(Level.SEVERE, "WSS0376.error.inserting.header", e.getMessage());
/* 121 */       throw new XWSSecurityException(e);
/*     */     } 
/* 123 */     this.topMostSoapElement = elementToInsert;
/*     */   }
/*     */   
/*     */   public void insertBefore(SecurityHeaderBlock block, Node elem) throws XWSSecurityException {
/* 127 */     SOAPElement elementToInsert = block.getAsSoapElement();
/*     */     try {
/* 129 */       if (elementToInsert.getOwnerDocument() != this.ownerDoc) {
/* 130 */         elementToInsert = (SOAPElement)this.ownerDoc.importNode(elementToInsert, true);
/*     */       
/*     */       }
/*     */     }
/* 134 */     catch (DOMException e) {
/* 135 */       log.log(Level.SEVERE, "WSS0376.error.inserting.header", e.getMessage());
/* 136 */       throw new XWSSecurityException(e);
/*     */     } 
/* 138 */     insertBefore(elementToInsert, elem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendChild(SecurityHeaderBlock block) throws XWSSecurityException {
/* 143 */     SOAPElement elementToInsert = block.getAsSoapElement();
/*     */     try {
/* 145 */       if (elementToInsert.getOwnerDocument() != this.ownerDoc) {
/* 146 */         elementToInsert = (SOAPElement)this.ownerDoc.importNode(elementToInsert, true);
/*     */       }
/*     */ 
/*     */       
/* 150 */       appendChild(elementToInsert);
/*     */     }
/* 152 */     catch (DOMException e) {
/* 153 */       log.log(Level.SEVERE, "WSS0376.error.inserting.header", e.getMessage());
/* 154 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertHeaderBlockElement(SOAPElement blockElement) throws XWSSecurityException {
/*     */     try {
/* 161 */       if (blockElement.getOwnerDocument() != this.ownerDoc) {
/* 162 */         blockElement = (SOAPElement)this.ownerDoc.importNode(blockElement, true);
/*     */       }
/*     */       
/* 165 */       updateTopMostSoapElement();
/*     */       
/* 167 */       insertBefore(blockElement, this.topMostSoapElement);
/*     */     }
/* 169 */     catch (DOMException e) {
/* 170 */       log.log(Level.SEVERE, "WSS0376.error.inserting.header", e.getMessage());
/* 171 */       throw new XWSSecurityException(e);
/*     */     } 
/* 173 */     this.topMostSoapElement = blockElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityHeaderBlock getCurrentHeaderBlock(Class implType) throws XWSSecurityException {
/* 182 */     if (null == this.currentSoapElement) {
/* 183 */       this.currentSoapElement = getFirstChildElement();
/*     */     } else {
/* 185 */       Node nextChild = this.currentSoapElement.getNextSibling();
/* 186 */       while (null != nextChild && nextChild.getNodeType() != 1)
/* 187 */         nextChild = nextChild.getNextSibling(); 
/* 188 */       this.currentSoapElement = (SOAPElement)nextChild;
/*     */     } 
/* 190 */     return SecurityHeaderBlockImpl.fromSoapElement(this.currentSoapElement, implType);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getCurrentHeaderBlockElement() {
/* 195 */     if (null == this.currentSoapElement) {
/* 196 */       this.currentSoapElement = getFirstChildElement();
/*     */     } else {
/* 198 */       Node nextChild = this.currentSoapElement.getNextSibling();
/* 199 */       while (null != nextChild && nextChild.getNodeType() != 1)
/* 200 */         nextChild = nextChild.getNextSibling(); 
/* 201 */       this.currentSoapElement = (SOAPElement)nextChild;
/*     */     } 
/* 203 */     return this.currentSoapElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentHeaderElement(SOAPElement currentElement) throws XWSSecurityException {
/* 208 */     if (currentElement != null && currentElement.getParentNode() != this.delegateHeader) {
/*     */       
/* 210 */       log.log(Level.SEVERE, "WSS0396.notchild.securityHeader", new Object[] { currentElement.toString() });
/*     */       
/* 212 */       throw new XWSSecurityException("Element set is not a child of SecurityHeader");
/*     */     } 
/*     */     
/* 215 */     this.currentSoapElement = currentElement;
/*     */   }
/*     */   
/*     */   public SOAPElement getCurrentHeaderElement() {
/* 219 */     return this.currentSoapElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTopMostSoapElement() {
/* 225 */     this.topMostSoapElement = getNextSiblingOfTimestamp();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getFirstChildElement() {
/* 230 */     Iterator<Node> eachChild = getChildElements();
/* 231 */     Node node = null;
/*     */     
/* 233 */     if (eachChild.hasNext()) {
/* 234 */       node = eachChild.next();
/*     */     } else {
/* 236 */       return null;
/*     */     } 
/*     */     
/* 239 */     while (node.getNodeType() != 1 && eachChild.hasNext()) {
/* 240 */       node = eachChild.next();
/*     */     }
/* 242 */     if (null != node) {
/* 243 */       return (SOAPElement)node;
/*     */     }
/* 245 */     return null;
/*     */   }
/*     */   
/*     */   public SOAPElement getNextSiblingOfTimestamp() {
/* 249 */     SOAPElement firstElement = getFirstChildElement();
/*     */     
/* 251 */     if (firstElement != null && "Timestamp".equals(firstElement.getLocalName())) {
/* 252 */       Node temp = firstElement.getNextSibling();
/* 253 */       if (temp == null)
/* 254 */         return null; 
/* 255 */       while (temp.getNodeType() != 1 && temp.getNextSibling() != null) {
/* 256 */         temp = temp.getNextSibling();
/*     */       }
/* 258 */       if (null != temp) {
/* 259 */         while (temp != null && "SignatureConfirmation".equals(temp.getLocalName())) {
/* 260 */           temp = temp.getNextSibling();
/* 261 */           if (temp == null)
/* 262 */             return null; 
/* 263 */           while (temp.getNodeType() != 1 && temp.getNextSibling() != null) {
/* 264 */             temp = temp.getNextSibling();
/*     */           }
/*     */         } 
/* 267 */         if (temp == null)
/* 268 */           return null; 
/* 269 */         return (SOAPElement)temp;
/*     */       } 
/* 271 */       return null;
/*     */     } 
/* 273 */     return firstElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() {
/* 280 */     return this.delegateHeader;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRole(String roleURI) {
/* 285 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public String getRole() {
/* 288 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public void setMustUnderstand(boolean mustUnderstand) {
/* 291 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   public boolean isMustUnderstand() {
/* 294 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name arg0, String arg1) throws SOAPException {
/* 300 */     return this.delegateHeader.addAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String arg0) throws SOAPException {
/* 304 */     return this.delegateHeader.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1) throws SOAPException {
/* 309 */     return this.delegateHeader.addChildElement(arg0, arg1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1, String arg2) throws SOAPException {
/* 314 */     return this.delegateHeader.addChildElement(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(Name arg0) throws SOAPException {
/* 318 */     return this.delegateHeader.addChildElement(arg0);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement arg0) throws SOAPException {
/* 322 */     return this.delegateHeader.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addNamespaceDeclaration(String arg0, String arg1) throws SOAPException {
/* 327 */     return this.delegateHeader.addNamespaceDeclaration(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String arg0) throws SOAPException {
/* 331 */     return this.delegateHeader.addTextNode(arg0);
/*     */   }
/*     */   
/*     */   public Node appendChild(Node arg0) throws DOMException {
/* 335 */     return this.delegateHeader.appendChild(arg0);
/*     */   }
/*     */   
/*     */   public SOAPElement makeUsable(SOAPElement elem) throws XWSSecurityException {
/* 339 */     SOAPElement elementToInsert = elem;
/*     */     try {
/* 341 */       if (elem.getOwnerDocument() != this.ownerDoc) {
/* 342 */         elementToInsert = (SOAPElement)this.ownerDoc.importNode(elem, true);
/*     */       }
/*     */ 
/*     */       
/* 346 */       return elementToInsert;
/* 347 */     } catch (DOMException e) {
/* 348 */       log.log(Level.SEVERE, "WSS0376.error.inserting.header", e.getMessage());
/* 349 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   public Node cloneNode(boolean arg0) {
/* 353 */     return this.delegateHeader.cloneNode(arg0);
/*     */   }
/*     */   
/*     */   public void detachNode() {
/* 357 */     this.delegateHeader.detachNode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 361 */     return this.delegateHeader.equals(obj);
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributes() {
/* 365 */     return this.delegateHeader.getAllAttributes();
/*     */   }
/*     */   
/*     */   public String getAttribute(String arg0) {
/* 369 */     return this.delegateHeader.getAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNode(String arg0) {
/* 373 */     return this.delegateHeader.getAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNodeNS(String arg0, String arg1) {
/* 377 */     return this.delegateHeader.getAttributeNodeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getAttributeNS(String arg0, String arg1) {
/* 381 */     return this.delegateHeader.getAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 385 */     return this.delegateHeader.getAttributes();
/*     */   }
/*     */   
/*     */   public String getAttributeValue(Name arg0) {
/* 389 */     return this.delegateHeader.getAttributeValue(arg0);
/*     */   }
/*     */   
/*     */   public Iterator getChildElements() {
/* 393 */     return this.delegateHeader.getChildElements();
/*     */   }
/*     */   
/*     */   public Iterator getChildElements(Name arg0) {
/* 397 */     return this.delegateHeader.getChildElements(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getChildNodes() {
/* 401 */     return this.delegateHeader.getChildNodes();
/*     */   }
/*     */   
/*     */   public Name getElementName() {
/* 405 */     return this.delegateHeader.getElementName();
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String arg0) {
/* 409 */     return this.delegateHeader.getElementsByTagName(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String arg0, String arg1) {
/* 413 */     return this.delegateHeader.getElementsByTagNameNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 417 */     return this.delegateHeader.getEncodingStyle();
/*     */   }
/*     */   
/*     */   public Node getFirstChild() {
/* 421 */     return this.delegateHeader.getFirstChild();
/*     */   }
/*     */   
/*     */   public Node getLastChild() {
/* 425 */     return this.delegateHeader.getLastChild();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 429 */     return this.delegateHeader.getLocalName();
/*     */   }
/*     */   
/*     */   public Iterator getNamespacePrefixes() {
/* 433 */     return this.delegateHeader.getNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 437 */     return this.delegateHeader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String arg0) {
/* 441 */     return this.delegateHeader.getNamespaceURI(arg0);
/*     */   }
/*     */   
/*     */   public Node getNextSibling() {
/* 445 */     return this.delegateHeader.getNextSibling();
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 449 */     return this.delegateHeader.getNodeName();
/*     */   }
/*     */   
/*     */   public short getNodeType() {
/* 453 */     return this.delegateHeader.getNodeType();
/*     */   }
/*     */   
/*     */   public String getNodeValue() throws DOMException {
/* 457 */     return this.delegateHeader.getNodeValue();
/*     */   }
/*     */   
/*     */   public Document getOwnerDocument() {
/* 461 */     return this.delegateHeader.getOwnerDocument();
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/* 465 */     return this.delegateHeader.getParentElement();
/*     */   }
/*     */   
/*     */   public Node getParentNode() {
/* 469 */     return this.delegateHeader.getParentNode();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 473 */     return this.delegateHeader.getPrefix();
/*     */   }
/*     */   
/*     */   public Node getPreviousSibling() {
/* 477 */     return this.delegateHeader.getPreviousSibling();
/*     */   }
/*     */   
/*     */   public String getTagName() {
/* 481 */     return this.delegateHeader.getTagName();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 485 */     return this.delegateHeader.getValue();
/*     */   }
/*     */   
/*     */   public Iterator getVisibleNamespacePrefixes() {
/* 489 */     return this.delegateHeader.getVisibleNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(String arg0) {
/* 493 */     return this.delegateHeader.hasAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean hasAttributeNS(String arg0, String arg1) {
/* 497 */     return this.delegateHeader.hasAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 501 */     return this.delegateHeader.hasAttributes();
/*     */   }
/*     */   
/*     */   public boolean hasChildNodes() {
/* 505 */     return this.delegateHeader.hasChildNodes();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 509 */     return this.delegateHeader.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node insertBefore(Node arg0, Node arg1) throws DOMException {
/* 514 */     return this.delegateHeader.insertBefore(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String arg0, String arg1) {
/* 518 */     return this.delegateHeader.isSupported(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void normalize() {
/* 522 */     this.delegateHeader.normalize();
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/* 526 */     this.delegateHeader.recycleNode();
/*     */   }
/*     */   
/*     */   public void removeAttribute(String arg0) throws DOMException {
/* 530 */     this.delegateHeader.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean removeAttribute(Name arg0) {
/* 534 */     return this.delegateHeader.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr removeAttributeNode(Attr arg0) throws DOMException {
/* 538 */     return this.delegateHeader.removeAttributeNode(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributeNS(String arg0, String arg1) throws DOMException {
/* 543 */     this.delegateHeader.removeAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Node removeChild(Node arg0) throws DOMException {
/* 547 */     return this.delegateHeader.removeChild(arg0);
/*     */   }
/*     */   
/*     */   public void removeContents() {
/* 551 */     this.delegateHeader.removeContents();
/*     */   }
/*     */   
/*     */   public boolean removeNamespaceDeclaration(String arg0) {
/* 555 */     return this.delegateHeader.removeNamespaceDeclaration(arg0);
/*     */   }
/*     */   
/*     */   public Node replaceChild(Node arg0, Node arg1) throws DOMException {
/* 559 */     return this.delegateHeader.replaceChild(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void setAttribute(String arg0, String arg1) throws DOMException {
/* 563 */     this.delegateHeader.setAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNode(Attr arg0) throws DOMException {
/* 567 */     return this.delegateHeader.setAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
/* 571 */     return this.delegateHeader.setAttributeNodeNS(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeNS(String arg0, String arg1, String arg2) throws DOMException {
/* 576 */     this.delegateHeader.setAttributeNS(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String arg0) throws SOAPException {
/* 580 */     this.delegateHeader.setEncodingStyle(arg0);
/*     */   }
/*     */   
/*     */   public void setNodeValue(String arg0) throws DOMException {
/* 584 */     this.delegateHeader.setNodeValue(arg0);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement arg0) throws SOAPException {
/* 588 */     this.delegateHeader.setParentElement(arg0);
/*     */   }
/*     */   
/*     */   public void setPrefix(String arg0) throws DOMException {
/* 592 */     this.delegateHeader.setPrefix(arg0);
/*     */   }
/*     */   
/*     */   public void setValue(String arg0) {
/* 596 */     this.delegateHeader.setValue(arg0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 600 */     return this.delegateHeader.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 605 */     return this.delegateHeader.getBaseURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public short compareDocumentPosition(Node other) throws DOMException {
/* 610 */     return this.delegateHeader.compareDocumentPosition(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 615 */     return this.delegateHeader.getTextContent();
/*     */   }
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {
/* 619 */     this.delegateHeader.setTextContent(textContent);
/*     */   }
/*     */   
/*     */   public boolean isSameNode(Node other) {
/* 623 */     return this.delegateHeader.isSameNode(other);
/*     */   }
/*     */   
/*     */   public String lookupPrefix(String namespaceURI) {
/* 627 */     return this.delegateHeader.lookupPrefix(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean isDefaultNamespace(String namespaceURI) {
/* 631 */     return this.delegateHeader.isDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public String lookupNamespaceURI(String prefix) {
/* 635 */     return this.delegateHeader.lookupNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 639 */     return this.delegateHeader.isEqualNode(arg);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 644 */     return this.delegateHeader.getFeature(feature, version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 650 */     return this.delegateHeader.setUserData(key, data, handler);
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 654 */     return this.delegateHeader.getUserData(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdAttribute(String name, boolean isId) throws DOMException {
/* 660 */     this.delegateHeader.setIdAttribute(name, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
/* 664 */     this.delegateHeader.setIdAttributeNode(idAttr, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
/* 668 */     this.delegateHeader.setIdAttributeNS(namespaceURI, localName, isId);
/*     */   }
/*     */   
/*     */   public TypeInfo getSchemaTypeInfo() {
/* 672 */     return this.delegateHeader.getSchemaTypeInfo();
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributesAsQNames() {
/* 676 */     return this.delegateHeader.getAllAttributesAsQNames();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\SecurityHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */