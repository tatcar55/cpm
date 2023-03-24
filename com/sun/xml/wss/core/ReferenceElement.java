/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.misc.SOAPElementExtension;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
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
/*     */ public abstract class ReferenceElement
/*     */   extends SOAPElementExtension
/*     */   implements SOAPElement
/*     */ {
/*     */   protected SOAPElement delegateElement;
/*     */   protected static SOAPFactory soapFactory;
/*     */   
/*     */   static {
/*     */     try {
/*  77 */       soapFactory = SOAPFactory.newInstance();
/*  78 */     } catch (SOAPException e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSOAPElement(SOAPElement delegateElement) {
/*  83 */     this.delegateElement = delegateElement;
/*     */   }
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/*  87 */     return this.delegateElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name arg0, String arg1) throws SOAPException {
/*  93 */     return this.delegateElement.addAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String arg0) throws SOAPException {
/*  97 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1) throws SOAPException {
/* 102 */     return this.delegateElement.addChildElement(arg0, arg1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1, String arg2) throws SOAPException {
/* 107 */     return this.delegateElement.addChildElement(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(Name arg0) throws SOAPException {
/* 111 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement arg0) throws SOAPException {
/* 115 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addNamespaceDeclaration(String arg0, String arg1) throws SOAPException {
/* 120 */     return this.delegateElement.addNamespaceDeclaration(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String arg0) throws SOAPException {
/* 124 */     return this.delegateElement.addTextNode(arg0);
/*     */   }
/*     */   
/*     */   public Node appendChild(Node arg0) throws DOMException {
/* 128 */     return this.delegateElement.appendChild(arg0);
/*     */   }
/*     */   
/*     */   public Node cloneNode(boolean arg0) {
/* 132 */     return this.delegateElement.cloneNode(arg0);
/*     */   }
/*     */   
/*     */   public void detachNode() {
/* 136 */     this.delegateElement.detachNode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 140 */     return this.delegateElement.equals(obj);
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributes() {
/* 144 */     return this.delegateElement.getAllAttributes();
/*     */   }
/*     */   
/*     */   public String getAttribute(String arg0) {
/* 148 */     return this.delegateElement.getAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNode(String arg0) {
/* 152 */     return this.delegateElement.getAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNodeNS(String arg0, String arg1) {
/* 156 */     return this.delegateElement.getAttributeNodeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getAttributeNS(String arg0, String arg1) {
/* 160 */     return this.delegateElement.getAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 164 */     return this.delegateElement.getAttributes();
/*     */   }
/*     */   
/*     */   public String getAttributeValue(Name arg0) {
/* 168 */     return this.delegateElement.getAttributeValue(arg0);
/*     */   }
/*     */   
/*     */   public Iterator getChildElements() {
/* 172 */     return this.delegateElement.getChildElements();
/*     */   }
/*     */   
/*     */   public Iterator getChildElements(Name arg0) {
/* 176 */     return this.delegateElement.getChildElements(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getChildNodes() {
/* 180 */     return this.delegateElement.getChildNodes();
/*     */   }
/*     */   
/*     */   public Name getElementName() {
/* 184 */     return this.delegateElement.getElementName();
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String arg0) {
/* 188 */     return this.delegateElement.getElementsByTagName(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String arg0, String arg1) {
/* 192 */     return this.delegateElement.getElementsByTagNameNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 196 */     return this.delegateElement.getEncodingStyle();
/*     */   }
/*     */   
/*     */   public Node getFirstChild() {
/* 200 */     return this.delegateElement.getFirstChild();
/*     */   }
/*     */   
/*     */   public Node getLastChild() {
/* 204 */     return this.delegateElement.getLastChild();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 208 */     return this.delegateElement.getLocalName();
/*     */   }
/*     */   
/*     */   public Iterator getNamespacePrefixes() {
/* 212 */     return this.delegateElement.getNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 216 */     return this.delegateElement.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String arg0) {
/* 220 */     return this.delegateElement.getNamespaceURI(arg0);
/*     */   }
/*     */   
/*     */   public Node getNextSibling() {
/* 224 */     return this.delegateElement.getNextSibling();
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 228 */     return this.delegateElement.getNodeName();
/*     */   }
/*     */   
/*     */   public short getNodeType() {
/* 232 */     return this.delegateElement.getNodeType();
/*     */   }
/*     */   
/*     */   public String getNodeValue() throws DOMException {
/* 236 */     return this.delegateElement.getNodeValue();
/*     */   }
/*     */   
/*     */   public Document getOwnerDocument() {
/* 240 */     return this.delegateElement.getOwnerDocument();
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/* 244 */     return this.delegateElement.getParentElement();
/*     */   }
/*     */   
/*     */   public Node getParentNode() {
/* 248 */     return this.delegateElement.getParentNode();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 252 */     return this.delegateElement.getPrefix();
/*     */   }
/*     */   
/*     */   public Node getPreviousSibling() {
/* 256 */     return this.delegateElement.getPreviousSibling();
/*     */   }
/*     */   
/*     */   public String getTagName() {
/* 260 */     return this.delegateElement.getTagName();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 264 */     return this.delegateElement.getValue();
/*     */   }
/*     */   
/*     */   public Iterator getVisibleNamespacePrefixes() {
/* 268 */     return this.delegateElement.getVisibleNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(String arg0) {
/* 272 */     return this.delegateElement.hasAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean hasAttributeNS(String arg0, String arg1) {
/* 276 */     return this.delegateElement.hasAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 280 */     return this.delegateElement.hasAttributes();
/*     */   }
/*     */   
/*     */   public boolean hasChildNodes() {
/* 284 */     return this.delegateElement.hasChildNodes();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 288 */     return this.delegateElement.hashCode();
/*     */   }
/*     */   
/*     */   public Node insertBefore(Node arg0, Node arg1) throws DOMException {
/* 292 */     return this.delegateElement.insertBefore(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String arg0, String arg1) {
/* 296 */     return this.delegateElement.isSupported(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void normalize() {
/* 300 */     this.delegateElement.normalize();
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/* 304 */     this.delegateElement.recycleNode();
/*     */   }
/*     */   
/*     */   public void removeAttribute(String arg0) throws DOMException {
/* 308 */     this.delegateElement.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean removeAttribute(Name arg0) {
/* 312 */     return this.delegateElement.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr removeAttributeNode(Attr arg0) throws DOMException {
/* 316 */     return this.delegateElement.removeAttributeNode(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributeNS(String arg0, String arg1) throws DOMException {
/* 321 */     this.delegateElement.removeAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Node removeChild(Node arg0) throws DOMException {
/* 325 */     return this.delegateElement.removeChild(arg0);
/*     */   }
/*     */   
/*     */   public void removeContents() {
/* 329 */     this.delegateElement.removeContents();
/*     */   }
/*     */   
/*     */   public boolean removeNamespaceDeclaration(String arg0) {
/* 333 */     return this.delegateElement.removeNamespaceDeclaration(arg0);
/*     */   }
/*     */   
/*     */   public Node replaceChild(Node arg0, Node arg1) throws DOMException {
/* 337 */     return this.delegateElement.replaceChild(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void setAttribute(String arg0, String arg1) throws DOMException {
/* 341 */     this.delegateElement.setAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNode(Attr arg0) throws DOMException {
/* 345 */     return this.delegateElement.setAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
/* 349 */     return this.delegateElement.setAttributeNodeNS(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeNS(String arg0, String arg1, String arg2) throws DOMException {
/* 354 */     this.delegateElement.setAttributeNS(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String arg0) throws SOAPException {
/* 358 */     this.delegateElement.setEncodingStyle(arg0);
/*     */   }
/*     */   
/*     */   public void setNodeValue(String arg0) throws DOMException {
/* 362 */     this.delegateElement.setNodeValue(arg0);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement arg0) throws SOAPException {
/* 366 */     this.delegateElement.setParentElement(arg0);
/*     */   }
/*     */   
/*     */   public void setPrefix(String arg0) throws DOMException {
/* 370 */     this.delegateElement.setPrefix(arg0);
/*     */   }
/*     */   
/*     */   public void setValue(String arg0) {
/* 374 */     this.delegateElement.setValue(arg0);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 378 */     return this.delegateElement.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 384 */     return this.delegateElement.getBaseURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public short compareDocumentPosition(Node other) throws DOMException {
/* 389 */     return this.delegateElement.compareDocumentPosition(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 394 */     return this.delegateElement.getTextContent();
/*     */   }
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {
/* 398 */     this.delegateElement.setTextContent(textContent);
/*     */   }
/*     */   
/*     */   public boolean isSameNode(Node other) {
/* 402 */     return this.delegateElement.isSameNode(other);
/*     */   }
/*     */   
/*     */   public String lookupPrefix(String namespaceURI) {
/* 406 */     return this.delegateElement.lookupPrefix(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean isDefaultNamespace(String namespaceURI) {
/* 410 */     return this.delegateElement.isDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public String lookupNamespaceURI(String prefix) {
/* 414 */     return this.delegateElement.lookupNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 418 */     return this.delegateElement.isEqualNode(arg);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 423 */     return this.delegateElement.getFeature(feature, version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 429 */     return this.delegateElement.setUserData(key, data, handler);
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 433 */     return this.delegateElement.getUserData(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdAttribute(String name, boolean isId) throws DOMException {
/* 439 */     this.delegateElement.setIdAttribute(name, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
/* 443 */     this.delegateElement.setIdAttributeNode(idAttr, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
/* 447 */     this.delegateElement.setIdAttributeNS(namespaceURI, localName, isId);
/*     */   }
/*     */   
/*     */   public TypeInfo getSchemaTypeInfo() {
/* 451 */     return this.delegateElement.getSchemaTypeInfo();
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributesAsQNames() {
/* 455 */     return this.delegateElement.getAllAttributesAsQNames();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\ReferenceElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */