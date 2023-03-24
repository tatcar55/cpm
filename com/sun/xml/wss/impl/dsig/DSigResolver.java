/*     */ package com.sun.xml.wss.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityToken;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.X509SecurityToken;
/*     */ import com.sun.xml.wss.core.reference.DirectReference;
/*     */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509ThumbPrintIdentifier;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.AssertionUtil;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.math.BigInteger;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMURIReference;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DSigResolver
/*     */   implements URIDereferencer
/*     */ {
/* 103 */   private static volatile DSigResolver resolver = null;
/* 104 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*     */ 
/*     */   
/* 107 */   private String optNSClassName = "org.jcp.xml.dsig.internal.dom.DOMSubTreeData";
/* 108 */   private Class _nodeSetClass = null;
/* 109 */   private Constructor _constructor = null;
/* 110 */   private Boolean _false = Boolean.valueOf(false);
/*     */ 
/*     */ 
/*     */   
/*     */   private DSigResolver() {
/*     */     try {
/* 116 */       this._nodeSetClass = Class.forName(this.optNSClassName);
/* 117 */       this._constructor = this._nodeSetClass.getConstructor(new Class[] { Node.class, boolean.class });
/* 118 */     } catch (LinkageError le) {
/* 119 */       logger.log(Level.FINE, "Not able load JSR 105 RI specific NodeSetData class ", le);
/* 120 */     } catch (ClassNotFoundException cne) {
/* 121 */       logger.log(Level.FINE, "Not able load JSR 105 RI specific NodeSetData class ", cne);
/* 122 */     } catch (NoSuchMethodException ne) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URIDereferencer getInstance() {
/* 132 */     if (resolver == null) {
/* 133 */       init();
/*     */     }
/* 135 */     return resolver;
/*     */   }
/*     */   
/*     */   private static void init() {
/* 139 */     if (resolver == null) {
/* 140 */       synchronized (DSigResolver.class) {
/* 141 */         if (resolver == null) {
/* 142 */           resolver = new DSigResolver();
/*     */         }
/*     */       } 
/*     */     }
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
/*     */   public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/* 157 */     String uri = null;
/*     */     
/*     */     try {
/* 160 */       if (uriRef instanceof DOMURIReference) {
/* 161 */         DOMURIReference domRef = (DOMURIReference)uriRef;
/* 162 */         Node node = domRef.getHere();
/* 163 */         if (node.getNodeType() == 2) {
/* 164 */           uri = uriRef.getURI();
/* 165 */           return dereferenceURI(uri, context);
/* 166 */         }  if (node.getNodeType() == 1 && 
/* 167 */           "SecurityTokenReference".equals(node.getLocalName())) {
/* 168 */           return derefSecurityTokenReference(node, context);
/*     */         }
/*     */       } else {
/*     */         
/* 172 */         uri = uriRef.getURI();
/*     */ 
/*     */ 
/*     */         
/* 176 */         return dereferenceURI(uri, context);
/*     */       } 
/* 178 */     } catch (XWSSecurityException ex) {
/* 179 */       if (logger.getLevel() == Level.FINEST) {
/* 180 */         logger.log(Level.FINEST, "Error occurred while resolving" + uri, (Throwable)ex);
/*     */       }
/* 182 */       throw new URIReferenceException(ex.getMessage());
/*     */     } 
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   Data dereferenceURI(String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 189 */     FilterProcessingContext filterContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 190 */     SecurableSoapMessage secureMsg = filterContext.getSecurableSoapMessage();
/* 191 */     if (uri == null || uri.equals("")) {
/* 192 */       SOAPMessage msg = filterContext.getSOAPMessage();
/* 193 */       Document doc = msg.getSOAPPart();
/* 194 */       if (this._constructor == null) {
/* 195 */         return convertToData(doc, true);
/*     */       }
/*     */       try {
/* 198 */         return this._constructor.newInstance(new Object[] { doc, this._false });
/* 199 */       } catch (Exception ex) {
/*     */ 
/*     */         
/* 202 */         return convertToData(doc, true);
/*     */       } 
/* 204 */     }  if (uri.charAt(0) == '#')
/* 205 */       return dereferenceFragment(SecurableSoapMessage.getIdFromFragmentRef(uri), context); 
/* 206 */     if (uri.startsWith("cid:") || uri.startsWith("attachmentRef:"))
/* 207 */       return dereferenceAttachments(uri, context); 
/* 208 */     if (uri.startsWith("http"))
/*     */     {
/* 210 */       return dereferenceExternalResource(uri, context);
/*     */     }
/* 212 */     return dereferenceFragment(uri, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Data dereferenceExternalResource(final String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 219 */     URIDereferencer resolver = WSSPolicyConsumerImpl.getInstance().getDefaultResolver();
/* 220 */     URIReference uriRef = null;
/* 221 */     FilterProcessingContext filterContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 222 */     SecurableSoapMessage secureMsg = filterContext.getSecurableSoapMessage();
/* 223 */     final Attr uriAttr = secureMsg.getSOAPMessage().getSOAPPart().createAttribute("uri");
/* 224 */     uriAttr.setNodeValue(uri);
/* 225 */     uriRef = new DOMURIReference()
/*     */       {
/*     */         public String getURI() {
/* 228 */           return uri;
/*     */         }
/*     */         
/*     */         public String getType() {
/* 232 */           return null;
/*     */         }
/*     */         public Node getHere() {
/* 235 */           return uriAttr;
/*     */         }
/*     */       };
/*     */     try {
/* 239 */       Data data = resolver.dereference(uriRef, context);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 257 */       return data;
/* 258 */     } catch (URIReferenceException ue) {
/* 259 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1325_DSIG_EXTERNALTARGET(ue));
/* 260 */       throw ue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Data dereferenceAttachments(String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 267 */     boolean sunAttachmentTransformProvider = true;
/* 268 */     FilterProcessingContext filterContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*     */ 
/*     */     
/* 271 */     SecurableSoapMessage secureMsg = filterContext.getSecurableSoapMessage();
/* 272 */     AttachmentPart attachment = secureMsg.getAttachmentPart(uri);
/* 273 */     if (attachment == null) {
/* 274 */       throw new URIReferenceException("Attachment Resource with Identifier  " + uri + " was not found");
/*     */     }
/* 276 */     if (sunAttachmentTransformProvider) {
/* 277 */       AttachmentData attachData = new AttachmentData();
/* 278 */       attachData.setAttachmentPart(attachment);
/* 279 */       return attachData;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 284 */     throw new UnsupportedOperationException("Not yet supported ");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Data dereferenceFragment(String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 290 */     FilterProcessingContext filterContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 291 */     HashMap elementCache = filterContext.getElementCache();
/* 292 */     if (elementCache.size() > 0) {
/* 293 */       Object obj = elementCache.get(uri);
/* 294 */       if (obj != null) {
/* 295 */         if (this._constructor == null) {
/* 296 */           return convertToData((Element)obj, true);
/*     */         }
/*     */         try {
/* 299 */           return this._constructor.newInstance(new Object[] { obj, this._false });
/* 300 */         } catch (Exception ex) {
/*     */ 
/*     */ 
/*     */           
/* 304 */           return convertToData((Element)obj, true);
/*     */         } 
/*     */       } 
/* 307 */     }  SecurableSoapMessage secureMsg = filterContext.getSecurableSoapMessage();
/* 308 */     Element element = secureMsg.getElementById(uri);
/* 309 */     if (element == null) {
/* 310 */       throw new URIReferenceException("Resource with fragment Identifier  " + uri + " was not found");
/*     */     }
/*     */     
/* 313 */     if (this._constructor == null) {
/* 314 */       return convertToData(element, true);
/*     */     }
/*     */     try {
/* 317 */       return this._constructor.newInstance(new Object[] { element, this._false });
/* 318 */     } catch (Exception ex) {
/*     */ 
/*     */ 
/*     */       
/* 322 */       return convertToData(element, true);
/*     */     } 
/*     */   }
/*     */   Data convertToData(final Node node, boolean xpathNodeSet) {
/* 326 */     final HashSet nodeSet = new HashSet();
/* 327 */     if (xpathNodeSet) {
/* 328 */       toNodeSet(node, nodeSet);
/* 329 */       return new NodeSetData() {
/*     */           public Iterator iterator() {
/* 331 */             return nodeSet.iterator();
/*     */           }
/*     */         };
/*     */     } 
/* 335 */     return new NodeSetData() {
/*     */         public Iterator iterator() {
/* 337 */           return Collections.<Node>singletonList(node).iterator();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   void toNodeSet(Node rootNode, Set<Node> result) {
/*     */     Element el;
/*     */     Node r;
/* 346 */     if (rootNode == null)
/* 347 */       return;  switch (rootNode.getNodeType()) {
/*     */       case 1:
/* 349 */         result.add(rootNode);
/* 350 */         el = (Element)rootNode;
/* 351 */         if (el.hasAttributes()) {
/* 352 */           NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 353 */           for (int i = 0; i < nl.getLength(); i++) {
/* 354 */             result.add(nl.item(i));
/*     */           }
/*     */         } 
/*     */       
/*     */       case 9:
/* 359 */         for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 360 */           if (r.getNodeType() == 3) {
/* 361 */             result.add(r);
/* 362 */             while (r != null && r.getNodeType() == 3) {
/* 363 */               r = r.getNextSibling();
/*     */             }
/* 365 */             if (r == null)
/*     */               return; 
/*     */           } 
/* 368 */           toNodeSet(r, result);
/*     */         } 
/*     */         return;
/*     */       case 8:
/*     */         return;
/*     */       case 10:
/*     */         return;
/*     */     } 
/* 376 */     result.add(rootNode);
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
/*     */   private Data derefSecurityTokenReference(Node element, XMLCryptoContext context) throws XWSSecurityException, URIReferenceException {
/* 392 */     SecurityToken secToken = null;
/* 393 */     FilterProcessingContext filterContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 394 */     SecurableSoapMessage secureMessage = filterContext.getSecurableSoapMessage();
/* 395 */     Document soapDocument = secureMessage.getSOAPPart();
/* 396 */     SOAPElement soapElem = XMLUtil.convertToSoapElement(soapDocument, (Element)element);
/* 397 */     SecurityTokenReference tokenRef = new SecurityTokenReference(soapElem);
/* 398 */     ReferenceElement refElement = tokenRef.getReference();
/* 399 */     HashMap<String, Assertion> tokenCache = filterContext.getTokenCache();
/* 400 */     Element tokenElement = null;
/* 401 */     Element newElement = null;
/*     */     
/* 403 */     if (refElement instanceof DirectReference) {
/*     */ 
/*     */       
/* 406 */       String uri = ((DirectReference)refElement).getURI();
/* 407 */       String tokenId = uri.substring(1);
/* 408 */       secToken = (SecurityToken)tokenCache.get(tokenId);
/* 409 */       if (secToken == null) {
/* 410 */         tokenElement = secureMessage.getElementById(tokenId);
/* 411 */         if (tokenElement == null) {
/* 412 */           throw new URIReferenceException("Could not locate token with following ID" + tokenId);
/*     */         }
/*     */       } else {
/*     */         
/* 416 */         tokenElement = secToken.getAsSoapElement();
/*     */       } 
/* 418 */       newElement = (Element)element.getOwnerDocument().importNode(tokenElement, true);
/*     */     }
/* 420 */     else if (refElement instanceof KeyIdentifier) {
/* 421 */       String valueType = ((KeyIdentifier)refElement).getValueType();
/* 422 */       String keyId = ((KeyIdentifier)refElement).getReferenceValue();
/* 423 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(valueType)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 429 */         X509Certificate cert = null;
/*     */         
/* 431 */         Object token = tokenCache.get(keyId);
/* 432 */         if (token instanceof X509SubjectKeyIdentifier && 
/* 433 */           token != null) {
/* 434 */           cert = ((X509SubjectKeyIdentifier)token).getCertificate();
/*     */         }
/*     */ 
/*     */         
/* 438 */         if (cert == null) {
/* 439 */           cert = filterContext.getSecurityEnvironment().getCertificate(filterContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId));
/*     */         }
/*     */         
/* 442 */         X509SecurityToken x509SecurityToken = new X509SecurityToken(soapDocument, cert);
/* 443 */         tokenElement = x509SecurityToken.getAsSoapElement();
/* 444 */         newElement = tokenElement;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 449 */           newElement.removeAttribute("EncodingType");
/* 450 */         } catch (DOMException de) {
/* 451 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_0607_STR_TRANSFORM_EXCEPTION());
/* 452 */           throw new XWSSecurityRuntimeException(de.getMessage(), de);
/*     */         } 
/* 454 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(valueType)) {
/* 455 */         X509Certificate cert = null;
/*     */         
/* 457 */         Object token = tokenCache.get(keyId);
/* 458 */         if (token instanceof X509ThumbPrintIdentifier && 
/* 459 */           token != null) {
/* 460 */           cert = ((X509ThumbPrintIdentifier)token).getCertificate();
/*     */         }
/*     */ 
/*     */         
/* 464 */         if (cert == null) {
/* 465 */           cert = filterContext.getSecurityEnvironment().getCertificate(filterContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId), "Thumbprint");
/*     */         }
/*     */         
/* 468 */         X509SecurityToken x509SecurityToken = new X509SecurityToken(soapDocument, cert);
/* 469 */         tokenElement = x509SecurityToken.getAsSoapElement();
/* 470 */         newElement = tokenElement;
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 475 */           newElement.removeAttribute("EncodingType");
/* 476 */         } catch (DOMException de) {
/* 477 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_0607_STR_TRANSFORM_EXCEPTION());
/* 478 */           throw new XWSSecurityRuntimeException(de.getMessage(), de);
/*     */         } 
/* 480 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(valueType)) {
/*     */         
/* 482 */         newElement = null;
/* 483 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType)) {
/*     */ 
/*     */ 
/*     */         
/* 487 */         if (tokenRef.getSamlAuthorityBinding() != null) {
/* 488 */           tokenElement = filterContext.getSecurityEnvironment().locateSAMLAssertion(filterContext.getExtraneousProperties(), tokenRef.getSamlAuthorityBinding(), keyId, secureMessage.getSOAPPart());
/*     */         }
/*     */         else {
/*     */           
/* 492 */           tokenElement = SAMLUtil.locateSamlAssertion(keyId, secureMessage.getSOAPPart());
/*     */         } 
/* 494 */         newElement = (Element)element.getOwnerDocument().importNode(tokenElement, true);
/*     */         
/* 496 */         Assertion assertion = null;
/*     */         try {
/* 498 */           assertion = AssertionUtil.fromElement(tokenElement);
/* 499 */         } catch (Exception e) {
/* 500 */           throw new XWSSecurityException(e);
/*     */         } 
/* 502 */         tokenCache.put(keyId, assertion);
/*     */       } else {
/*     */         
/*     */         try {
/* 506 */           tokenElement = resolveSAMLToken(tokenRef, keyId, filterContext);
/* 507 */         } catch (Exception e) {}
/*     */ 
/*     */         
/* 510 */         if (tokenElement != null) {
/* 511 */           newElement = (Element)element.getOwnerDocument().importNode(tokenElement, true);
/*     */         }
/*     */         else {
/*     */           
/* 515 */           XWSSecurityException xwsse = new XWSSecurityException("WSS_DSIG0008:unsupported KeyIdentifier Reference Type " + valueType);
/*     */ 
/*     */ 
/*     */           
/* 519 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*     */         
/*     */         }
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 526 */     else if (refElement instanceof X509IssuerSerial) {
/*     */       
/* 528 */       BigInteger serialNumber = ((X509IssuerSerial)refElement).getSerialNumber();
/*     */       
/* 530 */       String issuerName = ((X509IssuerSerial)refElement).getIssuerName();
/* 531 */       X509Certificate cert = null;
/* 532 */       Object token = tokenCache.get(issuerName + serialNumber);
/* 533 */       if (token instanceof X509IssuerSerial) {
/* 534 */         cert = ((X509IssuerSerial)token).getCertificate();
/*     */       }
/*     */       
/* 537 */       if (cert == null) {
/* 538 */         cert = filterContext.getSecurityEnvironment().getCertificate(filterContext.getExtraneousProperties(), serialNumber, issuerName);
/*     */       }
/*     */       
/* 541 */       X509SecurityToken x509SecurityToken = new X509SecurityToken(soapDocument, cert);
/* 542 */       tokenElement = x509SecurityToken.getAsSoapElement();
/* 543 */       newElement = tokenElement;
/*     */ 
/*     */       
/*     */       try {
/* 547 */         newElement.removeAttribute("EncodingType");
/* 548 */       } catch (DOMException de) {
/* 549 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_0607_STR_TRANSFORM_EXCEPTION());
/* 550 */         throw new XWSSecurityException(de.getMessage(), de);
/*     */       } 
/*     */     } else {
/* 553 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0608_DIAG_CAUSE_1());
/* 554 */       throw new XWSSecurityException("Cannot handle reference mechanism: " + refElement.getTagName());
/*     */     } 
/*     */ 
/*     */     
/* 558 */     Attr attr = element.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns");
/* 559 */     attr.setValue("");
/* 560 */     if (newElement != null) {
/* 561 */       newElement.setAttributeNodeNS(attr);
/*     */     }
/* 563 */     return convertToData(newElement, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element resolveSAMLToken(SecurityTokenReference tokenRef, String assertionId, FilterProcessingContext context) throws XWSSecurityException {
/* 569 */     Assertion ret = (Assertion)context.getTokenCache().get(assertionId);
/* 570 */     if (ret != null) {
/*     */       try {
/* 572 */         return SAMLUtil.toElement(context.getSecurableSoapMessage().getSOAPPart(), ret, null);
/* 573 */       } catch (Exception e) {
/* 574 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */     }
/*     */     
/* 578 */     Element tokenElement = null;
/* 579 */     if (tokenRef.getSamlAuthorityBinding() != null) {
/* 580 */       tokenElement = context.getSecurityEnvironment().locateSAMLAssertion(context.getExtraneousProperties(), tokenRef.getSamlAuthorityBinding(), assertionId, context.getSOAPMessage().getSOAPPart());
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 587 */       tokenElement = SAMLUtil.locateSamlAssertion(assertionId, context.getSOAPMessage().getSOAPPart());
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 592 */       ret = AssertionUtil.fromElement(tokenElement);
/* 593 */     } catch (Exception e) {
/* 594 */       throw new XWSSecurityException(e);
/*     */     } 
/* 596 */     context.getTokenCache().put(assertionId, ret);
/*     */     
/* 598 */     return tokenElement;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\DSigResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */