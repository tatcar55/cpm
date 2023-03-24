/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ public abstract class SecurityHeaderBlockImpl
/*     */   extends SOAPElementExtension
/*     */   implements SecurityHeaderBlock
/*     */ {
/*  79 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*     */   private static final Name idAttributeName;
/*     */ 
/*     */   
/*     */   private static SOAPFactory soapFactory;
/*     */   
/*     */   protected SOAPElement delegateElement;
/*     */   
/*     */   private boolean bsp = false;
/*     */ 
/*     */   
/*     */   static {
/*  93 */     Name temp = null;
/*     */     try {
/*  95 */       soapFactory = SOAPFactory.newInstance();
/*  96 */       temp = getSoapFactory().createName("Id", "wsu", "http://schemas.xmlsoap.org/ws/2003/06/utility");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 101 */     catch (SOAPException e) {
/* 102 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0654_SOAP_EXCEPTION(e.getMessage()), e.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 107 */     idAttributeName = temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityHeaderBlockImpl(SOAPElement delegateElement) {
/* 115 */     setSOAPElement(delegateElement);
/*     */   }
/*     */   
/*     */   protected void setSOAPElement(SOAPElement delegateElement) {
/* 119 */     this.delegateElement = delegateElement;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 123 */     return this.delegateElement.getAttributeValue(idAttributeName);
/*     */   }
/*     */   
/*     */   protected void setWsuIdAttr(Element element, String wsuId) {
/* 127 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 131 */     element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
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
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element, Class implClass) throws XWSSecurityException {
/* 143 */     SecurityHeaderBlock block = null;
/*     */     
/*     */     try {
/* 146 */       Constructor<SecurityHeaderBlock> implConstructor = implClass.getConstructor(new Class[] { SOAPElement.class });
/*     */       
/* 148 */       block = implConstructor.newInstance(new Object[] { element });
/*     */     
/*     */     }
/* 151 */     catch (Exception e) {
/* 152 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0655_ERROR_CREATING_HEADERBLOCK(e.getMessage()));
/*     */       
/* 154 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 157 */     return block;
/*     */   }
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 161 */     return this.delegateElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement addAttribute(Name arg0, String arg1) throws SOAPException {
/* 167 */     return this.delegateElement.addAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String arg0) throws SOAPException {
/* 171 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1) throws SOAPException {
/* 176 */     return this.delegateElement.addChildElement(arg0, arg1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addChildElement(String arg0, String arg1, String arg2) throws SOAPException {
/* 181 */     return this.delegateElement.addChildElement(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(Name arg0) throws SOAPException {
/* 185 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement arg0) throws SOAPException {
/* 189 */     return this.delegateElement.addChildElement(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement addNamespaceDeclaration(String arg0, String arg1) throws SOAPException {
/* 194 */     return this.delegateElement.addNamespaceDeclaration(arg0, arg1);
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String arg0) throws SOAPException {
/* 198 */     return this.delegateElement.addTextNode(arg0);
/*     */   }
/*     */   
/*     */   public Node appendChild(Node arg0) throws DOMException {
/* 202 */     return this.delegateElement.appendChild(arg0);
/*     */   }
/*     */   
/*     */   public Node cloneNode(boolean arg0) {
/* 206 */     return this.delegateElement.cloneNode(arg0);
/*     */   }
/*     */   
/*     */   public void detachNode() {
/* 210 */     this.delegateElement.detachNode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 214 */     return this.delegateElement.equals(obj);
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributes() {
/* 218 */     return this.delegateElement.getAllAttributes();
/*     */   }
/*     */   
/*     */   public String getAttribute(String arg0) {
/* 222 */     return this.delegateElement.getAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNode(String arg0) {
/* 226 */     return this.delegateElement.getAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNodeNS(String arg0, String arg1) {
/* 230 */     return this.delegateElement.getAttributeNodeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getAttributeNS(String arg0, String arg1) {
/* 234 */     return this.delegateElement.getAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 238 */     return this.delegateElement.getAttributes();
/*     */   }
/*     */   
/*     */   public String getAttributeValue(Name arg0) {
/* 242 */     return this.delegateElement.getAttributeValue(arg0);
/*     */   }
/*     */   
/*     */   public Iterator getChildElements() {
/* 246 */     return this.delegateElement.getChildElements();
/*     */   }
/*     */   
/*     */   public Iterator getChildElements(Name arg0) {
/* 250 */     return this.delegateElement.getChildElements(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getChildNodes() {
/* 254 */     return this.delegateElement.getChildNodes();
/*     */   }
/*     */   
/*     */   public Name getElementName() {
/* 258 */     return this.delegateElement.getElementName();
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String arg0) {
/* 262 */     return this.delegateElement.getElementsByTagName(arg0);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String arg0, String arg1) {
/* 266 */     return this.delegateElement.getElementsByTagNameNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 270 */     return this.delegateElement.getEncodingStyle();
/*     */   }
/*     */   
/*     */   public Node getFirstChild() {
/* 274 */     return this.delegateElement.getFirstChild();
/*     */   }
/*     */   
/*     */   public Node getLastChild() {
/* 278 */     return this.delegateElement.getLastChild();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 282 */     return this.delegateElement.getLocalName();
/*     */   }
/*     */   
/*     */   public Iterator getNamespacePrefixes() {
/* 286 */     return this.delegateElement.getNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 290 */     return this.delegateElement.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String arg0) {
/* 294 */     return this.delegateElement.getNamespaceURI(arg0);
/*     */   }
/*     */   
/*     */   public Node getNextSibling() {
/* 298 */     return this.delegateElement.getNextSibling();
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 302 */     return this.delegateElement.getNodeName();
/*     */   }
/*     */   
/*     */   public short getNodeType() {
/* 306 */     return this.delegateElement.getNodeType();
/*     */   }
/*     */   
/*     */   public String getNodeValue() throws DOMException {
/* 310 */     return this.delegateElement.getNodeValue();
/*     */   }
/*     */   
/*     */   public Document getOwnerDocument() {
/* 314 */     return this.delegateElement.getOwnerDocument();
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/* 318 */     return this.delegateElement.getParentElement();
/*     */   }
/*     */   
/*     */   public Node getParentNode() {
/* 322 */     return this.delegateElement.getParentNode();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 326 */     return this.delegateElement.getPrefix();
/*     */   }
/*     */   
/*     */   public Node getPreviousSibling() {
/* 330 */     return this.delegateElement.getPreviousSibling();
/*     */   }
/*     */   
/*     */   public String getTagName() {
/* 334 */     return this.delegateElement.getTagName();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 338 */     return this.delegateElement.getValue();
/*     */   }
/*     */   
/*     */   public Iterator getVisibleNamespacePrefixes() {
/* 342 */     return this.delegateElement.getVisibleNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(String arg0) {
/* 346 */     return this.delegateElement.hasAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean hasAttributeNS(String arg0, String arg1) {
/* 350 */     return this.delegateElement.hasAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 354 */     return this.delegateElement.hasAttributes();
/*     */   }
/*     */   
/*     */   public boolean hasChildNodes() {
/* 358 */     return this.delegateElement.hasChildNodes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node insertBefore(Node arg0, Node arg1) throws DOMException {
/* 366 */     return this.delegateElement.insertBefore(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String arg0, String arg1) {
/* 370 */     return this.delegateElement.isSupported(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void normalize() {
/* 374 */     this.delegateElement.normalize();
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/* 378 */     this.delegateElement.recycleNode();
/*     */   }
/*     */   
/*     */   public void removeAttribute(String arg0) throws DOMException {
/* 382 */     this.delegateElement.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public boolean removeAttribute(Name arg0) {
/* 386 */     return this.delegateElement.removeAttribute(arg0);
/*     */   }
/*     */   
/*     */   public Attr removeAttributeNode(Attr arg0) throws DOMException {
/* 390 */     return this.delegateElement.removeAttributeNode(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributeNS(String arg0, String arg1) throws DOMException {
/* 395 */     this.delegateElement.removeAttributeNS(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Node removeChild(Node arg0) throws DOMException {
/* 399 */     return this.delegateElement.removeChild(arg0);
/*     */   }
/*     */   
/*     */   public void removeContents() {
/* 403 */     this.delegateElement.removeContents();
/*     */   }
/*     */   
/*     */   public boolean removeNamespaceDeclaration(String arg0) {
/* 407 */     return this.delegateElement.removeNamespaceDeclaration(arg0);
/*     */   }
/*     */   
/*     */   public Node replaceChild(Node arg0, Node arg1) throws DOMException {
/* 411 */     return this.delegateElement.replaceChild(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void setAttribute(String arg0, String arg1) throws DOMException {
/* 415 */     this.delegateElement.setAttribute(arg0, arg1);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNode(Attr arg0) throws DOMException {
/* 419 */     return this.delegateElement.setAttributeNode(arg0);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNodeNS(Attr arg0) throws DOMException {
/* 423 */     return this.delegateElement.setAttributeNodeNS(arg0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttributeNS(String arg0, String arg1, String arg2) throws DOMException {
/* 428 */     this.delegateElement.setAttributeNS(arg0, arg1, arg2);
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String arg0) throws SOAPException {
/* 432 */     this.delegateElement.setEncodingStyle(arg0);
/*     */   }
/*     */   
/*     */   public void setNodeValue(String arg0) throws DOMException {
/* 436 */     this.delegateElement.setNodeValue(arg0);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement arg0) throws SOAPException {
/* 440 */     this.delegateElement.setParentElement(arg0);
/*     */   }
/*     */   
/*     */   public void setPrefix(String arg0) throws DOMException {
/* 444 */     this.delegateElement.setPrefix(arg0);
/*     */   }
/*     */   
/*     */   public void setValue(String arg0) {
/* 448 */     this.delegateElement.setValue(arg0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SOAPFactory getSoapFactory() {
/* 456 */     return soapFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 462 */     return this.delegateElement.getBaseURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public short compareDocumentPosition(Node other) throws DOMException {
/* 467 */     return this.delegateElement.compareDocumentPosition(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 472 */     return this.delegateElement.getTextContent();
/*     */   }
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {
/* 476 */     this.delegateElement.setTextContent(textContent);
/*     */   }
/*     */   
/*     */   public boolean isSameNode(Node other) {
/* 480 */     return this.delegateElement.isSameNode(other);
/*     */   }
/*     */   
/*     */   public String lookupPrefix(String namespaceURI) {
/* 484 */     return this.delegateElement.lookupPrefix(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean isDefaultNamespace(String namespaceURI) {
/* 488 */     return this.delegateElement.isDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public String lookupNamespaceURI(String prefix) {
/* 492 */     return this.delegateElement.lookupNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 496 */     return this.delegateElement.isEqualNode(arg);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 501 */     return this.delegateElement.getFeature(feature, version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 507 */     return this.delegateElement.setUserData(key, data, handler);
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 511 */     return this.delegateElement.getUserData(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIdAttribute(String name, boolean isId) throws DOMException {
/* 517 */     this.delegateElement.setIdAttribute(name, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
/* 521 */     this.delegateElement.setIdAttributeNode(idAttr, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
/* 525 */     this.delegateElement.setIdAttributeNS(namespaceURI, localName, isId);
/*     */   }
/*     */   
/*     */   public TypeInfo getSchemaTypeInfo() {
/* 529 */     return this.delegateElement.getSchemaTypeInfo();
/*     */   }
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 533 */     this.bsp = flag;
/*     */   }
/*     */   
/*     */   public boolean isBSP() {
/* 537 */     return this.bsp;
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributesAsQNames() {
/* 541 */     return this.delegateElement.getAllAttributesAsQNames();
/*     */   }
/*     */   
/*     */   protected SecurityHeaderBlockImpl() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\SecurityHeaderBlockImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */