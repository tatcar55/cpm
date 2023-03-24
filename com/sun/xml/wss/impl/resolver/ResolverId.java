/*     */ package com.sun.xml.wss.impl.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.dsig.NamespaceContextImpl;
/*     */ import com.sun.xml.wss.impl.misc.URI;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathExpressionException;
/*     */ import javax.xml.xpath.XPathFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResolverId
/*     */   extends ResourceResolverSpi
/*     */ {
/*  93 */   private static String implementationClassName = ResolverId.class.getName();
/*     */ 
/*     */   
/*  96 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getResolverName() {
/* 107 */     return implementationClassName;
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
/*     */   public XMLSignatureInput engineResolve(Attr uri, String BaseURI) throws ResourceResolverException {
/* 122 */     String uriNodeValue = uri.getNodeValue();
/*     */ 
/*     */ 
/*     */     
/* 126 */     Document doc = uri.getOwnerDocument();
/*     */ 
/*     */     
/* 129 */     XMLUtils.circumventBug2650(doc);
/*     */     
/* 131 */     Element selectedElem = null;
/* 132 */     if (uriNodeValue.equals("")) {
/* 133 */       selectedElem = doc.getDocumentElement();
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       String id = uriNodeValue.substring(1);
/*     */       try {
/* 145 */         selectedElem = getElementById(doc, id);
/* 146 */       } catch (TransformerException e) {
/* 147 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0603_XPATHAPI_TRANSFORMER_EXCEPTION(e.getMessage()), e.getMessage());
/*     */ 
/*     */         
/* 150 */         throw new ResourceResolverException("empty", e, uri, BaseURI);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     if (selectedElem == null) {
/* 155 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0604_CANNOT_FIND_ELEMENT());
/*     */       
/* 157 */       throw new ResourceResolverException("empty", uri, BaseURI);
/*     */     } 
/* 159 */     Set<Node> resultSet = dereferenceSameDocumentURI(selectedElem);
/* 160 */     XMLSignatureInput result = new XMLSignatureInput(resultSet);
/*     */     
/* 162 */     result.setMIMEType("text/xml");
/*     */     
/*     */     try {
/* 165 */       URI uriNew = new URI(new URI(BaseURI), uri.getNodeValue());
/* 166 */       result.setSourceURI(uriNew.toString());
/* 167 */     } catch (com.sun.xml.wss.impl.misc.URI.MalformedURIException ex) {
/* 168 */       result.setSourceURI(BaseURI);
/*     */     } 
/*     */     
/* 171 */     return result;
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
/*     */   public boolean engineCanResolve(Attr uri, String BaseURI) {
/* 183 */     if (uri == null) return false;
/*     */     
/* 185 */     String uriNodeValue = uri.getNodeValue();
/* 186 */     if (uriNodeValue.startsWith("#")) return true;
/*     */     
/* 188 */     return false;
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext(Document doc) {
/* 192 */     NamespaceContextImpl namespaceContextImpl = new NamespaceContextImpl();
/* 193 */     namespaceContextImpl.add(doc.getDocumentElement().getPrefix(), doc.getDocumentElement().getNamespaceURI());
/*     */     
/* 195 */     if (doc.getDocumentElement().getNamespaceURI() == "http://www.w3.org/2003/05/soap-envelope") {
/* 196 */       namespaceContextImpl.add("SOAP-ENV", "http://www.w3.org/2003/05/soap-envelope");
/* 197 */       namespaceContextImpl.add("env", "http://www.w3.org/2003/05/soap-envelope");
/*     */     } 
/* 199 */     namespaceContextImpl.add("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/* 200 */     namespaceContextImpl.add("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/* 201 */     return (NamespaceContext)namespaceContextImpl;
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
/*     */ 
/*     */   
/*     */   private Element getElementById(Document doc, String id) throws TransformerException {
/* 219 */     Element selement = doc.getElementById(id);
/* 220 */     if (selement != null)
/*     */     {
/*     */ 
/*     */       
/* 224 */       return selement;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     Element element = null;
/*     */     
/* 233 */     NodeList elems = null;
/* 234 */     String xpath = "//*[@wsu:Id='" + id + "']";
/* 235 */     XPathFactory xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 236 */     XPath xPATH = xpathFactory.newXPath();
/* 237 */     xPATH.setNamespaceContext(getNamespaceContext(doc));
/*     */     
/*     */     try {
/* 240 */       XPathExpression xpathExpr = xPATH.compile(xpath);
/* 241 */       elems = (NodeList)xpathExpr.evaluate(doc, XPathConstants.NODESET);
/* 242 */     } catch (XPathExpressionException ex) {
/*     */       
/* 244 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0375_ERROR_APACHE_XPATH_API(id, ex.getMessage()), new Object[] { id, ex.getMessage() });
/*     */ 
/*     */       
/* 247 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */     
/* 250 */     if (elems != null) {
/* 251 */       if (elems.getLength() > 1)
/*     */       {
/* 253 */         throw new XWSSecurityRuntimeException("XPath Query resulted in more than one node");
/*     */       }
/* 255 */       element = (Element)elems.item(0);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     if (element == null) {
/* 260 */       xpath = "//*[@Id='" + id + "']";
/*     */       try {
/* 262 */         XPathExpression xPathExpression = xPATH.compile(xpath);
/* 263 */         elems = (NodeList)xPathExpression.evaluate(doc, XPathConstants.NODESET);
/* 264 */       } catch (XPathExpressionException ex) {
/*     */         
/* 266 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0375_ERROR_APACHE_XPATH_API(id, ex.getMessage()), new Object[] { id, ex.getMessage() });
/*     */ 
/*     */         
/* 269 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     } 
/* 272 */     if (elems != null) {
/* 273 */       if (elems.getLength() > 1) {
/* 274 */         for (int i = 0; i < elems.getLength(); i++) {
/* 275 */           Element elem = (Element)elems.item(i);
/* 276 */           String namespace = elem.getNamespaceURI();
/* 277 */           if (namespace.equals("http://www.w3.org/2000/09/xmldsig#") || namespace.equals("http://www.w3.org/2001/04/xmlenc#")) {
/*     */             
/* 279 */             element = elem;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 285 */         element = (Element)elems.item(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     if (element == null) {
/*     */       
/* 318 */       NodeList assertions = doc.getElementsByTagName("Assertion");
/*     */       
/* 320 */       int len = assertions.getLength();
/* 321 */       if (len > 0) {
/* 322 */         for (int i = 0; i < len; i++) {
/* 323 */           Element elem = (Element)assertions.item(i);
/* 324 */           String assertionId = elem.getAttribute("AssertionID");
/* 325 */           if (id.equals(assertionId)) {
/* 326 */             element = elem;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 333 */     return element;
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
/*     */   private Set dereferenceSameDocumentURI(Node node) {
/* 345 */     Set nodeSet = new HashSet();
/* 346 */     if (node != null) {
/* 347 */       nodeSetMinusCommentNodes(node, nodeSet, null);
/*     */     }
/* 349 */     return nodeSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void nodeSetMinusCommentNodes(Node node, Set<Node> nodeSet, Node prevSibling) {
/*     */     NamedNodeMap attrs;
/*     */     Node pSibling;
/*     */     Node child;
/* 362 */     switch (node.getNodeType()) {
/*     */       case 1:
/* 364 */         attrs = node.getAttributes();
/* 365 */         if (attrs != null) {
/* 366 */           for (int i = 0; i < attrs.getLength(); i++) {
/* 367 */             nodeSet.add(attrs.item(i));
/*     */           }
/*     */         }
/* 370 */         nodeSet.add(node);
/* 371 */         pSibling = null;
/* 372 */         for (child = node.getFirstChild(); child != null; 
/* 373 */           child = child.getNextSibling()) {
/* 374 */           nodeSetMinusCommentNodes(child, nodeSet, pSibling);
/* 375 */           pSibling = child;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 382 */         if (prevSibling != null && (prevSibling.getNodeType() == 3 || prevSibling.getNodeType() == 4)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */       
/*     */       case 7:
/* 388 */         nodeSet.add(node);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\resolver\ResolverId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */