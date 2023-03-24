/*     */ package com.sun.xml.wss.impl.resolver;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
/*     */ import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
/*     */ import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.dsig.NamespaceContextImpl;
/*     */ import com.sun.xml.wss.impl.misc.URI;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.IOException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class URIResolver
/*     */   extends ResourceResolverSpi
/*     */ {
/* 107 */   int referenceType = -1;
/*     */   
/* 109 */   private SOAPMessage soapMsg = null;
/*     */   
/* 111 */   private static String implementationClassName = URIResolver.class.getName();
/*     */ 
/*     */   
/* 114 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl", "com.sun.xml.wss.logging.LogStrings");
/*     */   
/*     */   private static final int ID_REFERENCE = 0;
/*     */   
/*     */   private static final int CID_REFERENCE = 1;
/*     */   
/*     */   private static final int CLOCATION_REFERENCE = 2;
/*     */   
/*     */   private final String[] errors;
/*     */ 
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage soapMsg) {
/* 126 */     this.soapMsg = soapMsg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getResolverName() {
/* 135 */     return implementationClassName;
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
/*     */   public XMLSignatureInput engineResolve(Attr uri, String baseURI) throws ResourceResolverException {
/* 151 */     XMLSignatureInput result = null;
/*     */     
/* 153 */     if (this.referenceType == -1 && 
/* 154 */       !engineCanResolve(uri, baseURI)) {
/* 155 */       throw generateException(uri, baseURI, this.errors[0]);
/*     */     }
/* 157 */     switch (this.referenceType) {
/*     */       case 0:
/* 159 */         result = _resolveId(uri, baseURI);
/*     */         break;
/*     */       case 1:
/* 162 */         result = _resolveCid(uri, baseURI);
/*     */         break;
/*     */       case 2:
/*     */         try {
/* 166 */           result = _resolveClocation(uri, baseURI);
/* 167 */         } catch (URIResolverException ure) {
/* 168 */           result = ResourceResolver.getInstance(uri, baseURI).resolve(uri, baseURI);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     this.referenceType = -1;
/* 175 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSignatureInput _resolveId(Attr uri, String baseUri) throws ResourceResolverException {
/* 181 */     XMLSignatureInput result = null;
/*     */     
/* 183 */     String uriNodeValue = uri.getNodeValue();
/* 184 */     Document doc = uri.getOwnerDocument();
/*     */     
/* 186 */     XMLUtils.circumventBug2650(doc);
/*     */     
/* 188 */     Element selectedElem = null;
/* 189 */     if (uriNodeValue.equals("")) {
/* 190 */       selectedElem = doc.getDocumentElement();
/*     */     } else {
/* 192 */       String id = uriNodeValue.substring(1);
/*     */       try {
/* 194 */         selectedElem = getElementById(doc, id);
/* 195 */       } catch (TransformerException e) {
/* 196 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0603_XPATHAPI_TRANSFORMER_EXCEPTION(e.getMessage()), e.getMessage());
/*     */ 
/*     */         
/* 199 */         throw new ResourceResolverException("empty", e, uri, baseUri);
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     if (selectedElem == null) {
/* 204 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0604_CANNOT_FIND_ELEMENT());
/*     */       
/* 206 */       throw new ResourceResolverException("empty", uri, baseUri);
/*     */     } 
/*     */     
/* 209 */     Set<Node> resultSet = prepareNodeSet(selectedElem);
/* 210 */     result = new XMLSignatureInput(resultSet);
/* 211 */     result.setMIMEType("text/xml");
/*     */     
/*     */     try {
/* 214 */       URI uriNew = new URI(new URI(baseUri), uriNodeValue);
/* 215 */       result.setSourceURI(uriNew.toString());
/* 216 */     } catch (com.sun.xml.wss.impl.misc.URI.MalformedURIException ex) {
/* 217 */       result.setSourceURI(baseUri);
/*     */     } 
/* 219 */     return result;
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
/*     */   private XMLSignatureInput _resolveCid(Attr uri, String baseUri) throws ResourceResolverException {
/* 235 */     XMLSignatureInput result = null;
/* 236 */     String uriNodeValue = uri.getNodeValue();
/*     */     
/* 238 */     if (this.soapMsg == null) throw generateException(uri, baseUri, this.errors[1]);
/*     */     
/*     */     try {
/* 241 */       AttachmentPart _part = ((SecurableSoapMessage)this.soapMsg).getAttachmentPart(uriNodeValue);
/*     */       
/* 243 */       if (_part == null)
/*     */       {
/* 245 */         throw new ResourceResolverException("empty", uri, baseUri);
/*     */       }
/* 247 */       Object[] obj = AttachmentSignatureInput._getSignatureInput(_part);
/* 248 */       result = new AttachmentSignatureInput((byte[])obj[1]);
/* 249 */       ((AttachmentSignatureInput)result).setMimeHeaders((Vector)obj[0]);
/* 250 */       ((AttachmentSignatureInput)result).setContentType(_part.getContentType());
/* 251 */     } catch (Exception e) {
/*     */       
/* 253 */       throw new ResourceResolverException("empty", e, uri, baseUri);
/*     */     } 
/*     */     
/*     */     try {
/* 257 */       URI uriNew = new URI(new URI(baseUri), uriNodeValue);
/* 258 */       result.setSourceURI(uriNew.toString());
/* 259 */     } catch (com.sun.xml.wss.impl.misc.URI.MalformedURIException ex) {
/* 260 */       result.setSourceURI(baseUri);
/*     */     } 
/* 262 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   private XMLSignatureInput _resolveClocation(Attr uri, String baseUri) throws ResourceResolverException, URIResolverException {
/* 267 */     URI uriNew = null;
/* 268 */     XMLSignatureInput result = null;
/*     */     try {
/* 270 */       uriNew = getNewURI(uri.getNodeValue(), baseUri);
/* 271 */     } catch (com.sun.xml.wss.impl.misc.URI.MalformedURIException ex) {
/*     */       
/* 273 */       throw new ResourceResolverException("empty", ex, uri, baseUri);
/*     */     } 
/*     */     
/* 276 */     if (this.soapMsg == null) throw generateException(uri, baseUri, this.errors[1]);
/*     */     
/*     */     try {
/* 279 */       AttachmentPart _part = ((SecurableSoapMessage)this.soapMsg).getAttachmentPart(uriNew.toString());
/* 280 */       if (_part == null)
/*     */       {
/* 282 */         throw new URIResolverException();
/*     */       }
/* 284 */       Object[] obj = AttachmentSignatureInput._getSignatureInput(_part);
/* 285 */       result = new AttachmentSignatureInput((byte[])obj[1]);
/* 286 */       ((AttachmentSignatureInput)result).setMimeHeaders((Vector)obj[0]);
/* 287 */       ((AttachmentSignatureInput)result).setContentType(_part.getContentType());
/* 288 */     } catch (XWSSecurityException e) {
/* 289 */       throw new ResourceResolverException("empty", e, uri, baseUri);
/* 290 */     } catch (SOAPException spe) {
/*     */       
/* 292 */       throw new ResourceResolverException("empty", spe, uri, baseUri);
/* 293 */     } catch (IOException ioe) {
/*     */       
/* 295 */       throw new ResourceResolverException("empty", ioe, uri, baseUri);
/*     */     } 
/*     */ 
/*     */     
/* 299 */     result.setSourceURI(uriNew.toString());
/* 300 */     return result;
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
/*     */   public boolean engineCanResolve(Attr uri, String baseURI) {
/* 312 */     if (uri == null) return false;
/*     */     
/* 314 */     String uriNodeValue = uri.getNodeValue();
/*     */ 
/*     */     
/* 317 */     if (uriNodeValue.startsWith("#")) {
/* 318 */       this.referenceType = 0;
/* 319 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 323 */     if (uriNodeValue.startsWith("cid:")) {
/* 324 */       this.referenceType = 1;
/* 325 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 329 */     if (uriNodeValue.startsWith("attachmentRef:")) {
/* 330 */       this.referenceType = 1;
/* 331 */       return true;
/*     */     } 
/*     */     
/* 334 */     URI uriNew = null;
/*     */     try {
/* 336 */       uriNew = getNewURI(uriNodeValue, baseURI);
/* 337 */     } catch (com.sun.xml.wss.impl.misc.URI.MalformedURIException ex) {
/*     */       
/* 339 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 343 */     if ((uriNew != null && uriNew.getScheme().equals("http")) || uriNodeValue.startsWith("thismessage:/") || (!uriNew.getScheme().equals("ftp") && !uriNew.getScheme().equals("telnet") && !uriNew.getScheme().equals("gopher") && !uriNew.getScheme().equals("news") && !uriNew.getScheme().equals("mailto") && !uriNew.getScheme().equals("file"))) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 353 */       this.referenceType = 2;
/* 354 */       return true;
/*     */     } 
/*     */     
/* 357 */     return false;
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
/* 375 */     Element selement = doc.getElementById(id);
/* 376 */     if (selement != null)
/*     */     {
/*     */ 
/*     */       
/* 380 */       return selement;
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
/*     */ 
/*     */     
/* 412 */     Element element = null;
/* 413 */     NodeList elems = null;
/* 414 */     String xpath = "//*[@wsu:Id='" + id + "']";
/* 415 */     XPathFactory xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 416 */     XPath xPATH = xpathFactory.newXPath();
/* 417 */     xPATH.setNamespaceContext(getNamespaceContext(doc));
/*     */     
/*     */     try {
/* 420 */       XPathExpression xpathExpr = xPATH.compile(xpath);
/* 421 */       elems = (NodeList)xpathExpr.evaluate(doc, XPathConstants.NODESET);
/* 422 */     } catch (XPathExpressionException ex) {
/*     */       
/* 424 */       log.log(Level.SEVERE, "WSS0375.error.apache.xpathAPI", new Object[] { id, ex.getMessage() });
/*     */ 
/*     */       
/* 427 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */     
/* 430 */     if (elems != null) {
/* 431 */       if (elems.getLength() > 1)
/*     */       {
/* 433 */         throw new XWSSecurityRuntimeException("XPath Query resulted in more than one node");
/*     */       }
/* 435 */       element = (Element)elems.item(0);
/*     */     } 
/*     */ 
/*     */     
/* 439 */     if (element == null) {
/* 440 */       xpath = "//*[@Id='" + id + "']";
/*     */       try {
/* 442 */         XPathExpression xPathExpression = xPATH.compile(xpath);
/* 443 */         elems = (NodeList)xPathExpression.evaluate(doc, XPathConstants.NODESET);
/* 444 */       } catch (XPathExpressionException ex) {
/*     */         
/* 446 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0375_ERROR_APACHE_XPATH_API(id, ex.getMessage()), new Object[] { id, ex.getMessage() });
/*     */ 
/*     */         
/* 449 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     } 
/* 452 */     if (elems != null) {
/* 453 */       if (elems.getLength() > 1) {
/* 454 */         for (int i = 0; i < elems.getLength(); i++) {
/* 455 */           Element elem = (Element)elems.item(i);
/* 456 */           String namespace = elem.getNamespaceURI();
/* 457 */           if (namespace.equals("http://www.w3.org/2000/09/xmldsig#") || namespace.equals("http://www.w3.org/2001/04/xmlenc#")) {
/*     */             
/* 459 */             element = elem;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 465 */         element = (Element)elems.item(0);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 470 */     if (element == null) {
/*     */       
/* 472 */       NodeList assertions = doc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/*     */ 
/*     */       
/* 475 */       int len = assertions.getLength();
/* 476 */       if (len > 0) {
/* 477 */         for (int i = 0; i < len; i++) {
/* 478 */           Element elem = (Element)assertions.item(i);
/* 479 */           String assertionId = elem.getAttribute("AssertionID");
/* 480 */           if (id.equals(assertionId)) {
/* 481 */             element = elem;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 488 */     return element;
/*     */   }
/*     */   
/*     */   private ResourceResolverException generateException(Attr uri, String baseUri, String error) {
/* 492 */     XWSSecurityException xwssE = new XWSSecurityException(error);
/* 493 */     return new ResourceResolverException("empty", (Exception)xwssE, uri, baseUri);
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
/*     */   private Set prepareNodeSet(Node node) {
/* 505 */     Set nodeSet = new HashSet();
/* 506 */     if (node != null) {
/* 507 */       nodeSetMinusCommentNodes(node, nodeSet, null);
/*     */     }
/* 509 */     return nodeSet;
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
/* 522 */     switch (node.getNodeType()) {
/*     */       case 1:
/* 524 */         attrs = node.getAttributes();
/* 525 */         if (attrs != null) {
/* 526 */           for (int i = 0; i < attrs.getLength(); i++) {
/* 527 */             nodeSet.add(attrs.item(i));
/*     */           }
/*     */         }
/* 530 */         nodeSet.add(node);
/* 531 */         pSibling = null;
/* 532 */         for (child = node.getFirstChild(); child != null; 
/* 533 */           child = child.getNextSibling()) {
/* 534 */           nodeSetMinusCommentNodes(child, nodeSet, pSibling);
/* 535 */           pSibling = child;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 3:
/*     */       case 4:
/* 542 */         if (prevSibling != null && (prevSibling.getNodeType() == 3 || prevSibling.getNodeType() == 4)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */       
/*     */       case 7:
/* 548 */         nodeSet.add(node);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private URI getNewURI(String uri, String baseUri) throws URI.MalformedURIException {
/* 554 */     if (baseUri == null || "".equals(baseUri)) {
/* 555 */       return new URI(uri);
/*     */     }
/* 557 */     return new URI(new URI(baseUri), uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext(Document doc) {
/* 562 */     NamespaceContextImpl namespaceContextImpl = new NamespaceContextImpl();
/* 563 */     namespaceContextImpl.add(doc.getDocumentElement().getPrefix(), doc.getDocumentElement().getNamespaceURI());
/*     */     
/* 565 */     if (doc.getDocumentElement().getNamespaceURI() == "http://www.w3.org/2003/05/soap-envelope") {
/* 566 */       namespaceContextImpl.add("SOAP-ENV", "http://www.w3.org/2003/05/soap-envelope");
/* 567 */       namespaceContextImpl.add("env", "http://www.w3.org/2003/05/soap-envelope");
/*     */     } 
/* 569 */     namespaceContextImpl.add("wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/* 570 */     namespaceContextImpl.add("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/* 571 */     return (NamespaceContext)namespaceContextImpl;
/*     */   }
/*     */   
/*     */   private static final class URIResolverException
/*     */     extends Exception {
/*     */     private URIResolverException() {}
/*     */   }
/*     */   
/*     */   public URIResolver() {
/* 580 */     this.errors = new String[] { "Can not resolve reference type", "Required SOAPMessage instance to resolve reference" }; } public URIResolver(SOAPMessage soapMsg) { this.errors = new String[] { "Can not resolve reference type", "Required SOAPMessage instance to resolve reference" };
/*     */     this.soapMsg = soapMsg; }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\resolver\URIResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */