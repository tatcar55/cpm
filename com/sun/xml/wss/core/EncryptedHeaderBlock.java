/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.misc.SOAPElementExtension;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptedHeaderBlock
/*     */   extends SOAPElementExtension
/*     */   implements SOAPElement
/*     */ {
/*  95 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */   
/*     */   protected SOAPElement delegateElement;
/*     */ 
/*     */   
/*     */   private static SOAPFactory soapFactory;
/*     */ 
/*     */   
/*     */   private static final Name idAttributeName;
/*     */   
/*     */   private boolean bsp = false;
/*     */ 
/*     */   
/*     */   static {
/* 110 */     Name temp = null;
/*     */     try {
/* 112 */       soapFactory = SOAPFactory.newInstance();
/* 113 */       temp = getSoapFactory().createName("Id", "wsu", "http://schemas.xmlsoap.org/ws/2003/06/utility");
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 118 */     catch (SOAPException e) {
/* 119 */       log.log(Level.SEVERE, "WSS0654.soap.exception", e.getMessage());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     idAttributeName = temp;
/*     */   }
/*     */   
/*     */   public EncryptedHeaderBlock(Document doc) throws XWSSecurityException {
/*     */     try {
/* 129 */       setSOAPElement((SOAPElement)doc.createElementNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:EncryptedHeader"));
/*     */ 
/*     */ 
/*     */       
/* 133 */       addNamespaceDeclaration("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */     
/*     */     }
/* 136 */     catch (SOAPException e) {
/* 137 */       log.log(Level.SEVERE, "WSS0360.error.creating.ehb", e.getMessage());
/* 138 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedHeaderBlock(SOAPElement delegateElement) throws XWSSecurityException {
/* 145 */     setSOAPElement(delegateElement);
/*     */     try {
/* 147 */       setSOAPElement(getSoapFactory().createElement("EncryptedHeader", "wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 152 */       addNamespaceDeclaration("wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd");
/*     */ 
/*     */     
/*     */     }
/* 156 */     catch (SOAPException e) {
/* 157 */       log.log(Level.SEVERE, "WSS0360.error.creating.ehb", e.getMessage());
/* 158 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSOAPElement(SOAPElement delegateElement) {
/* 164 */     this.delegateElement = delegateElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyAttributes(SecurableSoapMessage secureMsg, SecurityHeader _secHeader) throws XWSSecurityException {
/* 169 */     String SOAP_namespace = secureMsg.getEnvelope().getNamespaceURI();
/* 170 */     String SOAP_prefix = secureMsg.getEnvelope().getPrefix();
/* 171 */     String value_mustUnderstand = _secHeader.getAttributeNS(SOAP_namespace, "mustUnderstand");
/* 172 */     String value_S12_role = _secHeader.getAttributeNS(SOAP_namespace, "role");
/* 173 */     String value_S11_actor = _secHeader.getAttributeNS(SOAP_namespace, "actor");
/* 174 */     String value_S12_relay = _secHeader.getAttributeNS(SOAP_namespace, "relay");
/*     */     
/* 176 */     if (value_mustUnderstand != null && !value_mustUnderstand.equals("")) {
/* 177 */       setAttributeNS(SOAP_namespace, SOAP_prefix + ":mustUnderstand", value_mustUnderstand);
/*     */     }
/* 179 */     if (value_S12_role != null && !value_S12_role.equals("")) {
/* 180 */       setAttributeNS(SOAP_namespace, SOAP_prefix + ":role", value_S12_role);
/*     */     }
/* 182 */     if (value_S11_actor != null && !value_S11_actor.equals("")) {
/* 183 */       setAttributeNS(SOAP_namespace, SOAP_prefix + ":actor", value_S11_actor);
/*     */     }
/* 185 */     if (value_S12_relay != null && !value_S12_relay.equals("")) {
/* 186 */       setAttributeNS(SOAP_namespace, SOAP_prefix + ":relay", value_S12_relay);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setWsuIdAttr(Element element, String wsuId) {
/* 191 */     element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */     
/* 195 */     element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", wsuId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static SOAPFactory getSoapFactory() {
/* 202 */     return soapFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 209 */     String id = getAttribute("Id");
/* 210 */     if (id.equals(""))
/* 211 */       return null; 
/* 212 */     return id;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/* 216 */     setAttribute("Id", id);
/* 217 */     setIdAttribute("Id", true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 224 */     String type = getAttribute("Type");
/* 225 */     if (type.equals(""))
/* 226 */       return null; 
/* 227 */     return type;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/* 231 */     setAttribute("Type", type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMimeType() {
/* 238 */     String mimeType = getAttribute("MimeType");
/* 239 */     if (mimeType.equals(""))
/* 240 */       return null; 
/* 241 */     return mimeType;
/*     */   }
/*     */   
/*     */   public void setMimeType(String mimeType) {
/* 245 */     setAttribute("MimeType", mimeType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws XWSSecurityException {
/* 253 */     return this.delegateElement;
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(Name name) throws SOAPException {
/* 257 */     return this.delegateElement.addChildElement(name);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String string) throws SOAPException {
/* 261 */     return this.delegateElement.addChildElement(string);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String string, String string0) throws SOAPException {
/* 265 */     return this.delegateElement.addChildElement(string, string0);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(String string, String string0, String string1) throws SOAPException {
/* 269 */     return this.delegateElement.addChildElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public SOAPElement addChildElement(SOAPElement sOAPElement) throws SOAPException {
/* 273 */     return this.delegateElement.addChildElement(sOAPElement);
/*     */   }
/*     */   
/*     */   public void removeContents() {
/* 277 */     this.delegateElement.removeContents();
/*     */   }
/*     */   
/*     */   public SOAPElement addTextNode(String string) throws SOAPException {
/* 281 */     return this.delegateElement.addTextNode(string);
/*     */   }
/*     */   
/*     */   public SOAPElement addAttribute(Name name, String string) throws SOAPException {
/* 285 */     return this.delegateElement.addAttribute(name, string);
/*     */   }
/*     */   
/*     */   public SOAPElement addNamespaceDeclaration(String string, String string0) throws SOAPException {
/* 289 */     return this.delegateElement.addNamespaceDeclaration(string, string0);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(Name name) {
/* 293 */     return this.delegateElement.getAttributeValue(name);
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributes() {
/* 297 */     return this.delegateElement.getAllAttributes();
/*     */   }
/*     */   
/*     */   public Iterator getAllAttributesAsQNames() {
/* 301 */     return this.delegateElement.getAllAttributesAsQNames();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String string) {
/* 305 */     return this.delegateElement.getNamespaceURI(string);
/*     */   }
/*     */   
/*     */   public Iterator getNamespacePrefixes() {
/* 309 */     return this.delegateElement.getNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public Iterator getVisibleNamespacePrefixes() {
/* 313 */     return this.delegateElement.getVisibleNamespacePrefixes();
/*     */   }
/*     */   
/*     */   public Name getElementName() {
/* 317 */     return this.delegateElement.getElementName();
/*     */   }
/*     */   
/*     */   public boolean removeAttribute(Name name) {
/* 321 */     return this.delegateElement.removeAttribute(name);
/*     */   }
/*     */   
/*     */   public boolean removeNamespaceDeclaration(String string) {
/* 325 */     return this.delegateElement.removeNamespaceDeclaration(string);
/*     */   }
/*     */   
/*     */   public Iterator getChildElements() {
/* 329 */     return this.delegateElement.getChildElements();
/*     */   }
/*     */   
/*     */   public Iterator getChildElements(Name name) {
/* 333 */     return this.delegateElement.getChildElements(name);
/*     */   }
/*     */   
/*     */   public void setEncodingStyle(String string) throws SOAPException {
/* 337 */     this.delegateElement.setEncodingStyle(string);
/*     */   }
/*     */   
/*     */   public String getEncodingStyle() {
/* 341 */     return this.delegateElement.getEncodingStyle();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 345 */     return this.delegateElement.getValue();
/*     */   }
/*     */   
/*     */   public void setValue(String string) {
/* 349 */     this.delegateElement.setValue(string);
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement sOAPElement) throws SOAPException {
/* 353 */     this.delegateElement.setParentElement(sOAPElement);
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/* 357 */     return this.delegateElement.getParentElement();
/*     */   }
/*     */   
/*     */   public void detachNode() {
/* 361 */     this.delegateElement.detachNode();
/*     */   }
/*     */   
/*     */   public void recycleNode() {
/* 365 */     this.delegateElement.recycleNode();
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 369 */     return this.delegateElement.getNodeName();
/*     */   }
/*     */   
/*     */   public String getNodeValue() throws DOMException {
/* 373 */     return this.delegateElement.getNodeValue();
/*     */   }
/*     */   
/*     */   public void setNodeValue(String nodeValue) throws DOMException {
/* 377 */     this.delegateElement.setNodeValue(nodeValue);
/*     */   }
/*     */   
/*     */   public short getNodeType() {
/* 381 */     return this.delegateElement.getNodeType();
/*     */   }
/*     */   
/*     */   public Node getParentNode() {
/* 385 */     return this.delegateElement.getParentNode();
/*     */   }
/*     */   
/*     */   public NodeList getChildNodes() {
/* 389 */     return this.delegateElement.getChildNodes();
/*     */   }
/*     */   
/*     */   public Node getFirstChild() {
/* 393 */     return this.delegateElement.getFirstChild();
/*     */   }
/*     */   
/*     */   public Node getLastChild() {
/* 397 */     return this.delegateElement.getLastChild();
/*     */   }
/*     */   
/*     */   public Node getPreviousSibling() {
/* 401 */     return this.delegateElement.getPreviousSibling();
/*     */   }
/*     */   
/*     */   public Node getNextSibling() {
/* 405 */     return this.delegateElement.getNextSibling();
/*     */   }
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 409 */     return this.delegateElement.getAttributes();
/*     */   }
/*     */   
/*     */   public Document getOwnerDocument() {
/* 413 */     return this.delegateElement.getOwnerDocument();
/*     */   }
/*     */   
/*     */   public Node insertBefore(Node newChild, Node refChild) throws DOMException {
/* 417 */     return this.delegateElement.insertBefore(newChild, refChild);
/*     */   }
/*     */   
/*     */   public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
/* 421 */     return this.delegateElement.replaceChild(newChild, oldChild);
/*     */   }
/*     */   
/*     */   public Node removeChild(Node oldChild) throws DOMException {
/* 425 */     return this.delegateElement.removeChild(oldChild);
/*     */   }
/*     */   
/*     */   public Node appendChild(Node newChild) throws DOMException {
/* 429 */     return this.delegateElement.appendChild(newChild);
/*     */   }
/*     */   
/*     */   public boolean hasChildNodes() {
/* 433 */     return this.delegateElement.hasChildNodes();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/* 438 */     return this.delegateElement.cloneNode(deep);
/*     */   }
/*     */   
/*     */   public void normalize() {
/* 442 */     this.delegateElement.normalize();
/*     */   }
/*     */   
/*     */   public boolean isSupported(String feature, String version) {
/* 446 */     return this.delegateElement.isSupported(feature, version);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 450 */     return this.delegateElement.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 454 */     return this.delegateElement.getPrefix();
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) throws DOMException {
/* 458 */     this.delegateElement.setPrefix(prefix);
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 462 */     return this.delegateElement.getLocalName();
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 466 */     return this.delegateElement.hasAttributes();
/*     */   }
/*     */   
/*     */   public String getBaseURI() {
/* 470 */     return this.delegateElement.getBaseURI();
/*     */   }
/*     */   
/*     */   public short compareDocumentPosition(Node other) throws DOMException {
/* 474 */     return this.delegateElement.compareDocumentPosition(other);
/*     */   }
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 478 */     return this.delegateElement.getTextContent();
/*     */   }
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {
/* 482 */     this.delegateElement.setTextContent(textContent);
/*     */   }
/*     */   
/*     */   public boolean isSameNode(Node other) {
/* 486 */     return this.delegateElement.isSameNode(other);
/*     */   }
/*     */   
/*     */   public String lookupPrefix(String namespaceURI) {
/* 490 */     return this.delegateElement.lookupPrefix(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean isDefaultNamespace(String namespaceURI) {
/* 494 */     return this.delegateElement.isDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public String lookupNamespaceURI(String prefix) {
/* 498 */     return this.delegateElement.lookupNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 502 */     return this.delegateElement.isEqualNode(arg);
/*     */   }
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 506 */     return this.delegateElement.getFeature(feature, version);
/*     */   }
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 510 */     return this.delegateElement.setUserData(key, data, handler);
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 514 */     return this.delegateElement.getUserData(key);
/*     */   }
/*     */   
/*     */   public String getTagName() {
/* 518 */     return this.delegateElement.getTagName();
/*     */   }
/*     */   
/*     */   public String getAttribute(String name) {
/* 522 */     return this.delegateElement.getAttribute(name);
/*     */   }
/*     */   
/*     */   public void setAttribute(String name, String value) throws DOMException {
/* 526 */     this.delegateElement.setAttribute(name, value);
/*     */   }
/*     */   
/*     */   public void removeAttribute(String name) throws DOMException {
/* 530 */     this.delegateElement.removeAttribute(name);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNode(String name) {
/* 534 */     return this.delegateElement.getAttributeNode(name);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNode(Attr newAttr) throws DOMException {
/* 538 */     return this.delegateElement.setAttributeNode(newAttr);
/*     */   }
/*     */   
/*     */   public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
/* 542 */     return this.delegateElement.removeAttributeNode(oldAttr);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String name) {
/* 546 */     return this.delegateElement.getElementsByTagName(name);
/*     */   }
/*     */   
/*     */   public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
/* 550 */     return this.delegateElement.getAttributeNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
/* 554 */     this.delegateElement.setAttributeNS(namespaceURI, qualifiedName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
/* 559 */     this.delegateElement.removeAttributeNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
/* 563 */     return this.delegateElement.getAttributeNodeNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
/* 567 */     return this.delegateElement.setAttributeNodeNS(newAttr);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
/* 571 */     return this.delegateElement.getElementsByTagNameNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public boolean hasAttribute(String name) {
/* 575 */     return this.delegateElement.hasAttribute(name);
/*     */   }
/*     */   
/*     */   public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
/* 579 */     return this.delegateElement.hasAttributeNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public TypeInfo getSchemaTypeInfo() {
/* 583 */     return this.delegateElement.getSchemaTypeInfo();
/*     */   }
/*     */   
/*     */   public void setIdAttribute(String name, boolean isId) throws DOMException {
/* 587 */     this.delegateElement.setIdAttribute(name, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
/* 591 */     this.delegateElement.setIdAttributeNS(namespaceURI, localName, isId);
/*     */   }
/*     */   
/*     */   public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
/* 595 */     this.delegateElement.setIdAttributeNode(idAttr, isId);
/*     */   }
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 599 */     this.bsp = flag;
/*     */   }
/*     */   
/*     */   public boolean isBSP() {
/* 603 */     return this.bsp;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\EncryptedHeaderBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */