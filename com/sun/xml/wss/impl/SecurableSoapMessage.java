/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.impl.dsig.NamespaceContextImpl;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.util.NodeListImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.Name;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ import javax.xml.xpath.XPath;
/*     */ import javax.xml.xpath.XPathConstants;
/*     */ import javax.xml.xpath.XPathExpression;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class SecurableSoapMessage
/*     */   extends SOAPMessage
/*     */ {
/*     */   private NamespaceContext nsContext;
/*  96 */   Random rnd = new Random();
/*  97 */   static XPathFactory xpathFactory = null;
/*     */   
/*     */   private SOAPMessage soapMessage;
/*     */   private boolean optimized = false;
/*     */   private SOAPElement wsseSecurity;
/*     */   private boolean doNotSetMU = false;
/* 103 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 108 */     Init.init();
/* 109 */     xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
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
/*     */   public SecurableSoapMessage(SOAPMessage soapMessage) throws XWSSecurityException {
/* 125 */     init(soapMessage);
/*     */   }
/*     */   
/*     */   public void init(SOAPMessage soapMessage) throws XWSSecurityException {
/* 129 */     this.soapMessage = soapMessage;
/* 130 */     if (log.isLoggable(Level.FINEST)) {
/* 131 */       log.log(Level.FINEST, LogStringsMessages.WSS_0100_CREATE_FOR_CREATING_IMPL(getClass().getName()));
/*     */     }
/*     */   }
/*     */   
/*     */   public SOAPEnvelope getEnvelope() throws XWSSecurityException {
/* 136 */     SOAPEnvelope envelope = null;
/*     */     
/*     */     try {
/* 139 */       envelope = getSOAPPart().getEnvelope();
/* 140 */     } catch (Exception e) {
/* 141 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0399_SOAP_ENVELOPE_EXCEPTION(), e);
/* 142 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 145 */     return envelope;
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
/*     */   private SOAPHeader findSoapHeader(boolean doCreate) throws XWSSecurityException {
/*     */     try {
/* 158 */       SOAPHeader header = getSOAPPart().getEnvelope().getHeader();
/* 159 */       if (header != null) {
/* 160 */         return header;
/*     */       }
/* 162 */       if (doCreate) {
/* 163 */         return getSOAPPart().getEnvelope().addHeader();
/*     */       }
/* 165 */     } catch (SOAPException e) {
/* 166 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0369_SOAP_EXCEPTION(e.getMessage()));
/* 167 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 170 */     return null;
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
/*     */   public SecurityHeader findWsseSecurityHeaderBlock(boolean doCreate, boolean mustUnderstand) throws XWSSecurityException {
/* 187 */     if (this.wsseSecurity != null)
/*     */     {
/* 189 */       if (this.wsseSecurity.getParentNode() == null) {
/* 190 */         this.wsseSecurity = null;
/*     */       } else {
/* 192 */         return (SecurityHeader)this.wsseSecurity;
/*     */       } 
/*     */     }
/* 195 */     SOAPHeader header = findSoapHeader(doCreate);
/* 196 */     if (null == header) return null;
/*     */ 
/*     */     
/* 199 */     NodeList headerChildNodes = header.getChildNodes();
/* 200 */     if (headerChildNodes != null) {
/* 201 */       Node currentNode = null;
/* 202 */       for (int i = 0; i < headerChildNodes.getLength(); i++) {
/* 203 */         currentNode = headerChildNodes.item(i);
/* 204 */         if ("Security".equals(currentNode.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(currentNode.getNamespaceURI())) {
/*     */ 
/*     */ 
/*     */           
/* 208 */           this.wsseSecurity = (SOAPElement)currentNode;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 214 */     if (this.wsseSecurity == null && !doCreate) return null;
/*     */     
/* 216 */     if (this.wsseSecurity == null && doCreate) {
/*     */       
/* 218 */       this.wsseSecurity = (SOAPElement)getSOAPPart().createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Security");
/*     */ 
/*     */ 
/*     */       
/* 222 */       this.wsseSecurity.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */ 
/*     */ 
/*     */       
/* 226 */       if (mustUnderstand && !this.doNotSetMU) {
/* 227 */         this.wsseSecurity.setAttributeNS(getEnvelope().getNamespaceURI(), getEnvelope().getPrefix() + ":mustUnderstand", "1");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 232 */       XMLUtil.prependChildElement(header, this.wsseSecurity, getSOAPPart());
/*     */     } 
/*     */     
/* 235 */     if (this.wsseSecurity != null) {
/* 236 */       this.wsseSecurity = (SOAPElement)new SecurityHeader(this.wsseSecurity);
/*     */     } else {
/* 238 */       throw new XWSSecurityException("Internal Error: wsse:Security Header found null");
/*     */     } 
/*     */     
/* 241 */     return (SecurityHeader)this.wsseSecurity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityHeader findSecurityHeader() throws XWSSecurityException {
/* 252 */     return findWsseSecurityHeaderBlock(false, false);
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
/*     */   public SecurityHeader findOrCreateSecurityHeader() throws XWSSecurityException {
/* 264 */     return findWsseSecurityHeaderBlock(true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteSecurityHeader() {
/*     */     try {
/* 272 */       findSecurityHeader();
/* 273 */       if (null != this.wsseSecurity) {
/* 274 */         this.wsseSecurity.detachNode();
/* 275 */         this.wsseSecurity = null;
/*     */       } 
/* 277 */     } catch (XWSSecurityException e) {
/* 278 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0370_ERROR_DELETING_SECHEADER(), e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetMustUnderstandOnSecHeader() {
/*     */     try {
/* 287 */       findSecurityHeader();
/* 288 */       if (null != this.wsseSecurity) {
/* 289 */         this.wsseSecurity.removeAttributeNS(getEnvelope().getNamespaceURI(), "mustUnderstand");
/*     */       }
/* 291 */     } catch (XWSSecurityException e) {
/* 292 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0370_ERROR_DELETING_SECHEADER(), e.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateSecurityHeaderException(String exceptionMessage) throws SecurityHeaderException, XWSSecurityException {
/* 310 */     SecurityHeaderException she = new SecurityHeaderException(exceptionMessage);
/*     */ 
/*     */     
/* 313 */     generateFault(newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Error while processing Security Header", (Throwable)she));
/*     */ 
/*     */ 
/*     */     
/* 317 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0370_ERROR_PROCESSING_SECHEADER(), (Throwable)she);
/* 318 */     throw she;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WssSoapFaultException newSOAPFaultException(String faultstring, Throwable th) {
/* 328 */     String fault = SOAPUtil.isEnableFaultDetail() ? faultstring : SOAPUtil.getLocalizedGenericError();
/*     */     
/* 330 */     WssSoapFaultException sfe = new WssSoapFaultException(null, fault, null, null);
/*     */     
/* 332 */     if (SOAPUtil.isEnableFaultDetail()) {
/* 333 */       sfe.initCause(th);
/*     */     }
/* 335 */     return sfe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WssSoapFaultException newSOAPFaultException(QName faultCode, String faultstring, Throwable th) {
/* 346 */     String fault = SOAPUtil.isEnableFaultDetail() ? faultstring : SOAPUtil.getLocalizedGenericError();
/*     */     
/* 348 */     QName fc = SOAPUtil.isEnableFaultDetail() ? faultCode : MessageConstants.WSSE_INVALID_SECURITY;
/* 349 */     WssSoapFaultException sfe = new WssSoapFaultException(fc, fault, null, null);
/*     */     
/* 351 */     if (SOAPUtil.isEnableFaultDetail()) {
/* 352 */       sfe.initCause(th);
/*     */     }
/* 354 */     return sfe;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateFault(WssSoapFaultException sfe) throws XWSSecurityException {
/*     */     try {
/* 365 */       SOAPBody body = this.soapMessage.getSOAPBody();
/* 366 */       body.removeContents();
/* 367 */       QName faultCode = sfe.getFaultCode();
/* 368 */       Name faultCodeName = null;
/* 369 */       if (faultCode == null) {
/* 370 */         faultCodeName = SOAPFactory.newInstance().createName("Client", null, "http://schemas.xmlsoap.org/soap/envelope/");
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 376 */         faultCodeName = SOAPFactory.newInstance().createName(faultCode.getLocalPart(), faultCode.getPrefix(), faultCode.getNamespaceURI());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 381 */       String msg = SOAPUtil.isEnableFaultDetail() ? sfe.getFaultString() : SOAPUtil.getLocalizedGenericError();
/*     */       
/* 383 */       body.addFault(faultCodeName, msg);
/*     */     }
/* 385 */     catch (SOAPException e) {
/* 386 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0371_ERROR_GENERATE_FAULT(e.getMessage()));
/* 387 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPPart getSOAPPart() {
/* 393 */     return this.soapMessage.getSOAPPart();
/*     */   }
/*     */   
/*     */   public SOAPBody getSOAPBody() throws SOAPException {
/*     */     try {
/* 398 */       return this.soapMessage.getSOAPBody();
/* 399 */     } catch (Exception e) {
/* 400 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0398_SOAP_BODY_EXCEPTION(), e);
/* 401 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPMessage getSOAPMessage() {
/* 406 */     return this.soapMessage;
/*     */   }
/*     */   
/*     */   public void setSOAPMessage(SOAPMessage soapMsg) throws XWSSecurityException {
/* 410 */     init(soapMsg);
/*     */   }
/*     */   
/*     */   public void addAttachmentPart(AttachmentPart attachmentPart) {
/* 414 */     this.soapMessage.addAttachmentPart(attachmentPart);
/*     */   }
/*     */   
/*     */   public int countAttachments() {
/* 418 */     return this.soapMessage.countAttachments();
/*     */   }
/*     */   
/*     */   public AttachmentPart createAttachmentPart() {
/* 422 */     return this.soapMessage.createAttachmentPart();
/*     */   }
/*     */   
/*     */   public AttachmentPart createAttachmentPart(Object content, String contentType) {
/* 426 */     return this.soapMessage.createAttachmentPart(content, contentType);
/*     */   }
/*     */   
/*     */   public AttachmentPart createAttachmentPart(DataHandler dataHandler) {
/* 430 */     return this.soapMessage.createAttachmentPart(dataHandler);
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 434 */     return this.soapMessage.equals(obj);
/*     */   }
/*     */   
/*     */   public Iterator getAttachments() {
/* 438 */     return this.soapMessage.getAttachments();
/*     */   }
/*     */   
/*     */   public Iterator getAttachments(MimeHeaders headers) {
/* 442 */     return this.soapMessage.getAttachments(headers);
/*     */   }
/*     */   
/*     */   public String getContentDescription() {
/* 446 */     return this.soapMessage.getContentDescription();
/*     */   }
/*     */   
/*     */   public MimeHeaders getMimeHeaders() {
/* 450 */     return this.soapMessage.getMimeHeaders();
/*     */   }
/*     */   
/*     */   public Object getProperty(String property) throws SOAPException {
/* 454 */     return this.soapMessage.getProperty(property);
/*     */   }
/*     */   
/*     */   public SOAPHeader getSOAPHeader() throws SOAPException {
/* 458 */     return this.soapMessage.getSOAPHeader();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 462 */     return this.soapMessage.hashCode();
/*     */   }
/*     */   
/*     */   public void removeAllAttachments() {
/* 466 */     this.soapMessage.removeAllAttachments();
/*     */   }
/*     */   
/*     */   public boolean saveRequired() {
/* 470 */     return this.soapMessage.saveRequired();
/*     */   }
/*     */   
/*     */   public void setContentDescription(String description) {
/* 474 */     this.soapMessage.setContentDescription(description);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProperty(String property, Object value) throws SOAPException {
/* 479 */     this.soapMessage.setProperty(property, value);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 483 */     return this.soapMessage.toString();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream out) throws SOAPException, IOException {
/* 487 */     this.soapMessage.writeTo(out);
/*     */   }
/*     */   
/*     */   public void saveChanges() throws SOAPException {
/* 491 */     this.soapMessage.saveChanges();
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() throws XWSSecurityException {
/* 495 */     if (this.nsContext == null) {
/* 496 */       this.nsContext = (NamespaceContext)new NamespaceContextImpl();
/*     */       
/* 498 */       ((NamespaceContextImpl)this.nsContext).add(getEnvelope().getPrefix(), getEnvelope().getNamespaceURI());
/*     */       
/* 500 */       if (getEnvelope().getNamespaceURI() == "http://www.w3.org/2003/05/soap-envelope") {
/* 501 */         ((NamespaceContextImpl)this.nsContext).add("SOAP-ENV", "http://www.w3.org/2003/05/soap-envelope");
/* 502 */         ((NamespaceContextImpl)this.nsContext).add("env", "http://www.w3.org/2003/05/soap-envelope");
/*     */       } 
/*     */     } 
/* 505 */     return this.nsContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateId() throws XWSSecurityException {
/* 514 */     int intRandom = this.rnd.nextInt();
/* 515 */     String id = "XWSSGID-" + String.valueOf(System.currentTimeMillis()) + String.valueOf(intRandom);
/* 516 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateWsuId(Element element) throws XWSSecurityException {
/* 524 */     element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", generateId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void generateWsuId(Element element, String id) throws XWSSecurityException {
/* 534 */     element.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu:Id", id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wsuIdIsUnique(NodeList wsuIdElements, String id) {
/* 543 */     boolean result = true;
/*     */ 
/*     */ 
/*     */     
/* 547 */     if (wsuIdElements == null) {
/* 548 */       return result;
/*     */     }
/* 550 */     for (int i = 0; i < wsuIdElements.getLength(); i++) {
/* 551 */       if (((Element)wsuIdElements.item(i)).getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id").equals(id))
/*     */       {
/*     */         
/* 554 */         result = false;
/*     */       }
/*     */     } 
/*     */     
/* 558 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPElement getElementByWsuId(String id) throws XWSSecurityException {
/* 564 */     Element element = getSOAPPart().getElementById(id);
/* 565 */     if (element != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 570 */       return (SOAPElement)element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 577 */     SOAPElement result = null;
/* 578 */     String xpath = "//*[@wsu:Id='" + id + "']";
/*     */     
/*     */     try {
/* 581 */       XPath xPATH = xpathFactory.newXPath();
/* 582 */       xPATH.setNamespaceContext(getNamespaceContext());
/* 583 */       XPathExpression xpathExpr = xPATH.compile(xpath);
/* 584 */       NodeList elements = (NodeList)xpathExpr.evaluate(getSOAPPart(), XPathConstants.NODESET);
/*     */ 
/*     */       
/* 587 */       if (elements != null)
/* 588 */         result = (SOAPElement)elements.item(0); 
/* 589 */     } catch (Exception e) {
/* 590 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0374_ERROR_APACHE_XPATH_API(id, e.getMessage()), new Object[] { id, e.getMessage() });
/*     */ 
/*     */       
/* 593 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */     
/* 596 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getElementById(String id) throws XWSSecurityException {
/* 607 */     if (id.startsWith("#"))
/* 608 */       id = id.substring(1); 
/* 609 */     Element element = getSOAPPart().getElementById(id);
/* 610 */     if (element != null)
/*     */     {
/*     */ 
/*     */       
/* 614 */       return element;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 621 */     Element result = null;
/* 622 */     result = getElementByWsuId(id);
/*     */     
/* 624 */     if (result == null) {
/*     */       
/* 626 */       Document soapPart = getSOAPPart();
/* 627 */       NodeList assertions = soapPart.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/*     */ 
/*     */       
/* 630 */       if (assertions.getLength() <= 0 || assertions.item(0) == null) {
/* 631 */         assertions = soapPart.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 636 */       String assertionId = null;
/* 637 */       int len = assertions.getLength();
/* 638 */       if (len > 0) {
/* 639 */         for (int i = 0; i < len; i++) {
/* 640 */           SOAPElement elem = (SOAPElement)assertions.item(i);
/* 641 */           if (elem.getAttributeNode("ID") != null) {
/* 642 */             assertionId = elem.getAttribute("ID");
/*     */           } else {
/* 644 */             assertionId = elem.getAttribute("AssertionID");
/*     */           } 
/* 646 */           if (id.equals(assertionId)) {
/* 647 */             result = elem;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 654 */     if (result == null) {
/*     */       NodeList elems;
/* 656 */       String xpath = "//*[@Id='" + id + "']";
/*     */       try {
/* 658 */         XPath xPATH = xpathFactory.newXPath();
/* 659 */         xPATH.setNamespaceContext(getNamespaceContext());
/* 660 */         XPathExpression xpathExpr = xPATH.compile(xpath);
/* 661 */         elems = (NodeList)xpathExpr.evaluate(getSOAPPart(), XPathConstants.NODESET);
/*     */       
/*     */       }
/* 664 */       catch (Exception e) {
/* 665 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0375_ERROR_APACHE_XPATH_API(id, e.getMessage()), new Object[] { id, e.getMessage() });
/*     */ 
/*     */         
/* 668 */         throw new XWSSecurityException(e);
/*     */       } 
/*     */       
/* 671 */       if (elems == null || elems.getLength() == 0) {
/* 672 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0285_ERROR_NO_ELEMENT());
/* 673 */         throw new XWSSecurityException("No elements exist with Id/WsuId: " + id);
/*     */       } 
/*     */ 
/*     */       
/* 677 */       for (int i = 0; i < elems.getLength(); i++) {
/* 678 */         Element elem = (Element)elems.item(i);
/* 679 */         String namespace = elem.getNamespaceURI();
/* 680 */         if (namespace.equals("http://www.w3.org/2000/09/xmldsig#") || namespace.equals("http://www.w3.org/2001/04/xmlenc#")) {
/*     */           
/* 682 */           result = elem;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 687 */       if (elems.getLength() > 1) {
/* 688 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0286_INVALID_NOOF_ELEMENTS());
/* 689 */         throw new XWSSecurityException("More than one element exists with Id/WsuId: " + id);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 694 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AttachmentPart getAttachmentPart(String uri) throws XWSSecurityException {
/* 700 */     AttachmentPart _part = null;
/* 701 */     String uri_tmp = uri;
/*     */     
/*     */     try {
/* 704 */       if (uri.startsWith("cid:")) {
/*     */         
/* 706 */         uri = "<" + uri.substring("cid:".length()) + ">";
/*     */         
/* 708 */         MimeHeaders headersToMatch = new MimeHeaders();
/* 709 */         headersToMatch.addHeader("Content-ID", uri);
/*     */         
/* 711 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/* 712 */         _part = (i == null) ? null : i.next();
/* 713 */         if (_part == null) {
/* 714 */           uri = uri_tmp;
/* 715 */           uri = uri.substring("cid:".length());
/* 716 */           headersToMatch = new MimeHeaders();
/* 717 */           headersToMatch.addHeader("Content-ID", uri);
/*     */           
/* 719 */           i = getAttachments(headersToMatch);
/* 720 */           _part = (i == null) ? null : i.next();
/*     */         } 
/* 722 */         if (_part == null) {
/* 723 */           throw new XWSSecurityException("Unable to Locate AttachmentPart for uri " + uri);
/*     */         }
/*     */       }
/* 726 */       else if (uri.startsWith("attachmentRef:")) {
/*     */         
/* 728 */         Iterator<AttachmentPart> j = getAttachments();
/*     */         
/* 730 */         while (j.hasNext()) {
/* 731 */           AttachmentPart p = j.next();
/* 732 */           String cl = p.getContentId();
/* 733 */           if (cl != null) {
/*     */             
/* 735 */             int eqIndex = cl.indexOf("=");
/* 736 */             if (eqIndex > -1) {
/* 737 */               cl = cl.substring(1, eqIndex);
/* 738 */               if (cl.equalsIgnoreCase(uri.substring("attachmentRef:".length()))) {
/*     */                 
/* 740 */                 _part = p;
/*     */                 break;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/* 747 */         String clocation = convertAbsolute2Relative(uri);
/*     */         
/* 749 */         MimeHeaders headersToMatch = new MimeHeaders();
/* 750 */         headersToMatch.addHeader("Content-Location", clocation);
/*     */         
/* 752 */         Iterator<AttachmentPart> i = getAttachments(headersToMatch);
/* 753 */         _part = (i == null) ? null : i.next();
/*     */         
/* 755 */         if (_part == null)
/*     */         {
/* 757 */           clocation = uri;
/* 758 */           headersToMatch.removeAllHeaders();
/* 759 */           headersToMatch.addHeader("Content-Location", clocation);
/*     */           
/* 761 */           i = getAttachments(headersToMatch);
/* 762 */           _part = (i == null) ? null : i.next();
/*     */         }
/*     */       
/*     */       } 
/* 766 */     } catch (Exception se) {
/* 767 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0287_ERROR_EXTRACTING_ATTACHMENTPART(), se);
/* 768 */       throw new XWSSecurityException(se);
/*     */     } 
/*     */     
/* 771 */     return _part;
/*     */   }
/*     */   
/*     */   private String convertAbsolute2Relative(String clocation) {
/* 775 */     MimeHeaders mimeHeaders = getMimeHeaders();
/*     */     
/* 777 */     String enclsgClocation = null;
/*     */     
/* 779 */     if (mimeHeaders != null) {
/* 780 */       Iterator<MimeHeader> clocs = mimeHeaders.getMatchingHeaders(new String[] { "Content-Location" });
/*     */       
/* 782 */       if (clocs != null) {
/* 783 */         MimeHeader mh = clocs.next();
/* 784 */         if (mh != null) enclsgClocation = mh.getValue();
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 789 */     if (enclsgClocation != null && clocation.startsWith(enclsgClocation)) {
/* 790 */       clocation = clocation.substring(enclsgClocation.length());
/*     */     }
/* 792 */     else if (clocation.startsWith("thismessage:/")) {
/* 793 */       clocation = clocation.substring("thismessage:/".length());
/*     */     } 
/* 795 */     return clocation;
/*     */   }
/*     */   
/*     */   public static String getIdFromFragmentRef(String ref) {
/* 799 */     char start = ref.charAt(0);
/* 800 */     if (start == '#') {
/* 801 */       return ref.substring(1);
/*     */     }
/* 803 */     return ref;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getMessageParts(Target target) throws XWSSecurityException {
/* 808 */     Object retValue = null;
/* 809 */     String type = target.getType();
/* 810 */     String value = target.getValue();
/* 811 */     boolean throwFault = false;
/* 812 */     boolean headersOnly = target.isSOAPHeadersOnly();
/*     */     
/* 814 */     if (type.equals("qname")) {
/*     */       
/*     */       try {
/* 817 */         if (value == "{http://schemas.xmlsoap.org/soap/envelope/}Body") {
/*     */ 
/*     */ 
/*     */           
/* 821 */           final SOAPElement se = getSOAPBody();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 826 */           retValue = new NodeList() {
/* 827 */               Node node = se;
/*     */               public int getLength() {
/* 829 */                 return 1;
/*     */               }
/*     */               public Node item(int num) {
/* 832 */                 if (num == 0) {
/* 833 */                   return this.node;
/*     */                 }
/* 835 */                 return null;
/*     */               }
/*     */             };
/*     */         } else {
/*     */           
/* 840 */           QName name = QName.valueOf(value);
/* 841 */           if (!headersOnly) {
/* 842 */             if ("".equals(name.getNamespaceURI())) {
/* 843 */               retValue = getSOAPPart().getElementsByTagNameNS("*", name.getLocalPart());
/*     */             } else {
/* 845 */               retValue = getSOAPPart().getElementsByTagNameNS(name.getNamespaceURI(), name.getLocalPart());
/*     */             } 
/*     */           } else {
/*     */             
/* 849 */             retValue = new NodeListImpl();
/* 850 */             NodeList hdrChilds = getSOAPHeader().getChildNodes();
/* 851 */             for (int i = 0; i < hdrChilds.getLength(); i++) {
/* 852 */               Node child = hdrChilds.item(i);
/* 853 */               if (child.getNodeType() == 1) {
/* 854 */                 if ("".equals(name.getNamespaceURI())) {
/* 855 */                   if (name.getLocalPart().equals(child.getLocalName())) {
/* 856 */                     ((NodeListImpl)retValue).add(child);
/*     */                   }
/*     */                 }
/* 859 */                 else if (name.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || name.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing")) {
/*     */                   
/* 861 */                   if (child.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || child.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing"))
/*     */                   {
/* 863 */                     if (!"".equals(name.getLocalPart())) {
/* 864 */                       if (name.getLocalPart().equals(child.getLocalName()))
/* 865 */                         ((NodeListImpl)retValue).add(child); 
/*     */                     } else {
/* 867 */                       ((NodeListImpl)retValue).add(child);
/*     */                     }
/*     */                   
/*     */                   }
/* 871 */                 } else if (!"".equals(name.getLocalPart())) {
/* 872 */                   if (name.getNamespaceURI().equals(child.getNamespaceURI()) && name.getLocalPart().equals(child.getLocalName()))
/*     */                   {
/* 874 */                     ((NodeListImpl)retValue).add(child);
/*     */                   }
/* 876 */                 } else if (name.getNamespaceURI().equals(child.getNamespaceURI())) {
/* 877 */                   ((NodeListImpl)retValue).add(child);
/*     */                 }
/*     */               
/*     */               }
/*     */             }
/*     */           
/*     */           } 
/*     */         } 
/* 885 */       } catch (Exception e) {
/* 886 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0288_FAILED_GET_MESSAGE_PARTS_QNAME(), e);
/* 887 */         throw new XWSSecurityRuntimeException(e);
/*     */       } 
/* 889 */       if (retValue == null || ((NodeList)retValue).getLength() == 0) throwFault = true;
/*     */     
/*     */     }
/* 892 */     else if (type.equals("xpath")) {
/*     */       try {
/* 894 */         XPathFactory xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 895 */         XPath xpath = xpathFactory.newXPath();
/*     */         
/* 897 */         xpath.setNamespaceContext(getNamespaceContext());
/*     */ 
/*     */         
/* 900 */         XPathExpression xpathExpr = xpath.compile(value);
/* 901 */         retValue = xpathExpr.evaluate(getSOAPPart(), XPathConstants.NODESET);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 909 */       catch (Exception e) {
/* 910 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0289_FAILED_GET_MESSAGE_PARTS_X_PATH(), e);
/* 911 */         throw new XWSSecurityRuntimeException(e);
/*     */       } 
/* 913 */       if (retValue == null || ((NodeList)retValue).getLength() == 0) throwFault = true; 
/* 914 */     } else if (type.equals("uri")) {
/*     */       try {
/* 916 */         retValue = getElementById(value);
/* 917 */       } catch (XWSSecurityException xwse) {
/*     */         try {
/* 919 */           retValue = getAttachmentPart(value);
/* 920 */           if (retValue == null) throwFault = true; 
/* 921 */         } catch (Exception se) {
/* 922 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0290_FAILED_GET_MESSAGE_PARTS_URI(), se);
/* 923 */           throw new XWSSecurityException("No message part can be identified by the Target: " + value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 928 */     if (throwFault) {
/* 929 */       if (log.isLoggable(Level.FINE)) {
/* 930 */         log.log(Level.FINE, "No message part can be identified by the Target:" + value);
/*     */       }
/*     */ 
/*     */       
/* 934 */       return null;
/*     */     } 
/*     */     
/* 937 */     return retValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentPart getAttachment(SOAPElement element) throws SOAPException {
/* 942 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0291_UNSUPPORTED_OPERATION_GET_ATTACHMENT());
/* 943 */     throw new UnsupportedOperationException("Operation Not Supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAttachments(MimeHeaders hdrs) {
/* 948 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0292_UNSUPPORTED_OPERATION_REMOVE_ATTACHMENT());
/* 949 */     throw new UnsupportedOperationException("Operation Not Supported");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOptimized() {
/* 954 */     return this.optimized;
/*     */   }
/*     */   
/*     */   public void setOptimized(boolean optimized) {
/* 958 */     this.optimized = optimized;
/*     */   }
/*     */   
/*     */   public void setDoNotSetMU(boolean doNotSetMU) {
/* 962 */     this.doNotSetMU = doNotSetMU;
/*     */   }
/*     */   
/*     */   public SecurableSoapMessage() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\SecurableSoapMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */