/*     */ package com.sun.xml.wss.saml.util;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import com.sun.xml.ws.security.opt.impl.util.StreamUtil;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.dsig.WSSPolicyConsumerImpl;
/*     */ import com.sun.xml.wss.logging.saml.LogStringsMessages;
/*     */ import com.sun.xml.wss.util.DateUtils;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.security.PublicKey;
/*     */ import java.text.ParseException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.dom.DOMValidateContext;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.stream.XMLOutputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.dom.DOMResult;
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
/*     */ public class SAMLUtil
/*     */ {
/*  95 */   private static Logger logger = Logger.getLogger("javax.enterprise.resource.xml.webservices.security.saml", "com.sun.xml.wss.logging.saml.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element locateSamlAssertion(String assertionId, Document soapDocument) throws XWSSecurityException {
/* 103 */     NodeList nodeList = null;
/*     */ 
/*     */     
/* 106 */     nodeList = soapDocument.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/* 107 */     if (nodeList.item(0) == null) {
/* 108 */       nodeList = soapDocument.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
/*     */     }
/*     */ 
/*     */     
/* 112 */     int nodeListLength = nodeList.getLength();
/* 113 */     if (nodeListLength == 0) {
/* 114 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_001_SAML_ASSERTION_NOT_FOUND(assertionId));
/* 115 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, "Referenced Security Token could not be retrieved", null);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     for (int i = 0; i < nodeListLength; i++) {
/* 124 */       Element assertion = (Element)nodeList.item(i);
/* 125 */       String aId = assertion.getAttribute("AssertionID");
/* 126 */       String id = assertion.getAttribute("ID");
/* 127 */       if (aId.equals(assertionId) || id.equals(assertionId))
/*     */       {
/* 129 */         return assertion;
/*     */       }
/*     */     } 
/* 132 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_001_SAML_ASSERTION_NOT_FOUND(assertionId));
/* 133 */     throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, "Referenced Security Token could not be retrieved", null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element toElement(Node doc, Object element) throws XWSSecurityException {
/* 141 */     return toElement(doc, element, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Element toElement(Node doc, Object element, JAXBContext jcc) throws XWSSecurityException {
/* 146 */     DOMResult result = null;
/* 147 */     Document document = null;
/*     */     
/* 149 */     if (doc != null) {
/*     */       
/* 151 */       result = new DOMResult(doc);
/*     */     } else {
/*     */       
/*     */       try {
/* 155 */         DocumentBuilderFactory factory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 156 */         DocumentBuilder builder = factory.newDocumentBuilder();
/* 157 */         document = builder.newDocument();
/* 158 */       } catch (Exception ex) {
/* 159 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_002_FAILED_CREATE_DOCUMENT(), ex);
/* 160 */         throw new XWSSecurityException("Unable to create Document : " + ex.getMessage());
/*     */       } 
/* 162 */       result = new DOMResult(document);
/*     */     } 
/*     */     
/*     */     try {
/* 166 */       JAXBContext jc = jcc;
/* 167 */       if (jc == null) {
/* 168 */         if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/* 169 */           if (element instanceof com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion) {
/* 170 */             jc = SAML20JAXBUtil.getJAXBContext();
/*     */           } else {
/* 172 */             jc = SAMLJAXBUtil.getJAXBContext();
/*     */           } 
/*     */         } else {
/* 175 */           jc = SAMLJAXBUtil.getJAXBContext();
/*     */         } 
/*     */       }
/*     */       
/* 179 */       Marshaller m = jc.createMarshaller();
/*     */       
/* 181 */       if (element == null && 
/* 182 */         logger.isLoggable(Level.FINE)) {
/* 183 */         logger.log(Level.FINE, "Element is Null in SAMLUtil.toElement()");
/*     */       }
/*     */ 
/*     */       
/* 187 */       m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new WSSNamespacePrefixMapper());
/* 188 */       m.marshal(element, result);
/*     */     }
/* 190 */     catch (Exception ex) {
/* 191 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_003_FAILEDTO_MARSHAL(), ex);
/* 192 */       throw new XWSSecurityException("Not able to Marshal " + element.getClass().getName() + ", got exception: " + ex.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 196 */     if (doc != null) {
/*     */ 
/*     */ 
/*     */       
/* 200 */       if (doc.getNodeType() == 1) {
/* 201 */         if (doc.getFirstChild().getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")) {
/* 202 */           Element element3 = (Element)((Element)doc).getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion").item(0);
/* 203 */           return element3;
/*     */         } 
/* 205 */         Element element2 = (Element)((Element)doc).getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion").item(0);
/* 206 */         return element2;
/*     */       } 
/*     */       
/* 209 */       if (doc.getFirstChild().getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")) {
/* 210 */         Element element2 = (Element)((Document)doc).getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion").item(0);
/* 211 */         return element2;
/*     */       } 
/* 213 */       Element element1 = (Element)((Document)doc).getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion").item(0);
/* 214 */       return element1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (document.getFirstChild().getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")) {
/* 220 */       Element element1 = (Element)document.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion").item(0);
/* 221 */       return element1;
/*     */     } 
/* 223 */     Element el = (Element)document.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion").item(0);
/* 224 */     return el;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element createSAMLAssertion(XMLStreamReader reader) throws XWSSecurityException, XMLStreamException {
/* 230 */     XMLOutputFactory xof = XMLOutputFactory.newInstance();
/* 231 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 232 */     MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/* 233 */     StreamWriterBufferCreator bCreator = new StreamWriterBufferCreator(buffer);
/* 234 */     Document doc = null;
/*     */     try {
/* 236 */       XMLStreamWriter writer = xof.createXMLStreamWriter(baos);
/* 237 */       StreamWriterBufferCreator streamWriterBufferCreator = bCreator;
/* 238 */       while (8 != reader.getEventType()) {
/* 239 */         StreamUtil.writeCurrentEvent(reader, (XMLStreamWriter)streamWriterBufferCreator);
/* 240 */         reader.next();
/*     */       } 
/* 242 */       buffer.writeToXMLStreamWriter(writer);
/* 243 */       writer.close();
/*     */       try {
/* 245 */         baos.close();
/* 246 */       } catch (IOException ex) {
/* 247 */         throw new XWSSecurityException("Error occurred while trying to convert SAMLAssertion stream into DOM Element", ex);
/*     */       } 
/* 249 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 250 */       dbf.setNamespaceAware(true);
/* 251 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 252 */       doc = db.parse(new ByteArrayInputStream(baos.toByteArray()));
/* 253 */       return doc.getDocumentElement();
/* 254 */     } catch (XMLStreamException xe) {
/* 255 */       throw new XMLStreamException("Error occurred while trying to convert SAMLAssertion stream into DOM Element", xe);
/* 256 */     } catch (Exception xe) {
/* 257 */       throw new XWSSecurityException("Error occurred while trying to convert SAMLAssertion stream into DOM Element", xe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean validateTimeInConditionsStatement(Element samlAssertion) throws XWSSecurityException {
/* 263 */     Date _notBefore = null;
/* 264 */     Date _notOnOrAfter = null;
/*     */     
/* 266 */     NodeList nl = samlAssertion.getElementsByTagNameNS(samlAssertion.getNamespaceURI(), "Conditions");
/* 267 */     Node conditionsElement = null;
/* 268 */     if (nl != null && nl.getLength() > 0) {
/* 269 */       conditionsElement = nl.item(0);
/*     */     } else {
/*     */       
/* 272 */       logger.log(Level.INFO, "No Conditions Element found in SAML Assertion");
/* 273 */       return true;
/*     */     } 
/* 275 */     Element elt = (Element)conditionsElement;
/* 276 */     String eltName = elt.getLocalName();
/* 277 */     if (eltName == null) {
/* 278 */       throw new XWSSecurityException("Internal Error: LocalName of Conditions Element found Null");
/*     */     }
/* 280 */     if (!eltName.equals("Conditions")) {
/* 281 */       throw new XWSSecurityException("Internal Error: LocalName of Conditions Element found to be :" + eltName);
/*     */     }
/*     */     
/* 284 */     String dt = elt.getAttribute("NotBefore");
/* 285 */     if (dt != null && !dt.equals("")) {
/*     */       try {
/* 287 */         _notBefore = DateUtils.stringToDate(dt);
/* 288 */       } catch (ParseException pe) {
/* 289 */         throw new XWSSecurityException(pe);
/*     */       } 
/*     */     }
/*     */     
/* 293 */     dt = elt.getAttribute("NotOnOrAfter");
/* 294 */     if (dt != null && !dt.equals("")) {
/*     */       try {
/* 296 */         _notOnOrAfter = DateUtils.stringToDate(elt.getAttribute("NotOnOrAfter"));
/*     */       }
/* 298 */       catch (ParseException pe) {
/* 299 */         throw new XWSSecurityException(pe);
/*     */       } 
/*     */     }
/*     */     
/* 303 */     long someTime = System.currentTimeMillis();
/*     */     
/* 305 */     if (_notBefore == null) {
/* 306 */       if (_notOnOrAfter == null) {
/* 307 */         return true;
/*     */       }
/* 309 */       if (someTime < _notOnOrAfter.getTime()) {
/* 310 */         return true;
/*     */       }
/*     */     }
/* 313 */     else if (_notOnOrAfter == null) {
/* 314 */       if (someTime >= _notBefore.getTime()) {
/* 315 */         return true;
/*     */       }
/* 317 */     } else if (someTime >= _notBefore.getTime() && someTime < _notOnOrAfter.getTime()) {
/*     */ 
/*     */       
/* 320 */       return true;
/*     */     } 
/* 322 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean verifySignature(Element samlAssertion, PublicKey pubKey) throws XWSSecurityException {
/*     */     try {
/* 327 */       Map<String, Object> map = new HashMap<String, Object>();
/* 328 */       String id = samlAssertion.getAttribute("ID");
/* 329 */       if (id == null || id.length() < 1) {
/* 330 */         id = samlAssertion.getAttribute("AssertionID");
/*     */       }
/* 332 */       map.put(id, samlAssertion);
/* 333 */       NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*     */ 
/*     */       
/* 336 */       if (nl.getLength() == 0) {
/* 337 */         throw new XWSSecurityException("Unsigned SAML Assertion encountered while verifying the SAML signature");
/*     */       }
/* 339 */       Element signElement = (Element)nl.item(0);
/* 340 */       DOMValidateContext validationContext = new DOMValidateContext(pubKey, signElement);
/* 341 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*     */ 
/*     */       
/* 344 */       XMLSignature xmlSignature = signatureFactory.unmarshalXMLSignature(validationContext);
/* 345 */       validationContext.setURIDereferencer(new DSigResolver(map, samlAssertion));
/* 346 */       boolean coreValidity = xmlSignature.validate(validationContext);
/* 347 */       return coreValidity;
/* 348 */     } catch (Exception ex) {
/* 349 */       throw new XWSSecurityException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DSigResolver
/*     */     implements URIDereferencer {
/* 355 */     Element elem = null;
/* 356 */     Map map = null;
/* 357 */     Class<?> _nodeSetClass = null;
/* 358 */     String optNSClassName = "org.jcp.xml.dsig.internal.dom.DOMSubTreeData";
/* 359 */     Constructor _constructor = null;
/* 360 */     Boolean _false = Boolean.valueOf(false);
/*     */     DSigResolver(Map map, Element elem) {
/* 362 */       this.elem = elem;
/* 363 */       this.map = map;
/* 364 */       init();
/*     */     }
/*     */     
/*     */     void init() {
/*     */       try {
/* 369 */         this._nodeSetClass = Class.forName(this.optNSClassName);
/* 370 */         this._constructor = this._nodeSetClass.getConstructor(new Class[] { Node.class, boolean.class });
/* 371 */       } catch (LinkageError le) {
/*     */       
/* 373 */       } catch (ClassNotFoundException cne) {
/*     */       
/* 375 */       } catch (NoSuchMethodException ne) {}
/*     */     }
/*     */ 
/*     */     
/*     */     public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/*     */       try {
/* 381 */         String uri = null;
/* 382 */         uri = uriRef.getURI();
/* 383 */         return dereferenceURI(uri, context);
/* 384 */       } catch (Exception ex) {
/*     */         
/* 386 */         throw new URIReferenceException(ex);
/*     */       } 
/*     */     }
/*     */     Data dereferenceURI(String uri, XMLCryptoContext context) throws URIReferenceException {
/* 390 */       if (uri.charAt(0) == '#') {
/* 391 */         uri = uri.substring(1, uri.length());
/* 392 */         Element el = this.elem.getOwnerDocument().getElementById(uri);
/* 393 */         if (el == null) {
/* 394 */           el = (Element)this.map.get(uri);
/*     */         }
/*     */         
/* 397 */         if (this._constructor != null) {
/*     */           try {
/* 399 */             return this._constructor.newInstance(new Object[] { el, this._false });
/* 400 */           } catch (Exception ex) {
/*     */             
/* 402 */             ex.printStackTrace();
/*     */           } 
/*     */         } else {
/* 405 */           final HashSet<Object> nodeSet = new HashSet();
/* 406 */           toNodeSet(el, nodeSet);
/* 407 */           return new NodeSetData() {
/*     */               public Iterator iterator() {
/* 409 */                 return nodeSet.iterator();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 416 */       return null;
/*     */     }
/*     */     void toNodeSet(Node rootNode, Set<Object> result) {
/*     */       Element el;
/*     */       Node r;
/* 421 */       switch (rootNode.getNodeType()) {
/*     */         case 1:
/* 423 */           result.add(rootNode);
/* 424 */           el = (Element)rootNode;
/* 425 */           if (el.hasAttributes()) {
/* 426 */             NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 427 */             for (int i = 0; i < nl.getLength(); i++) {
/* 428 */               result.add(nl.item(i));
/*     */             }
/*     */           } 
/*     */         
/*     */         case 9:
/* 433 */           for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 434 */             if (r.getNodeType() == 3) {
/* 435 */               result.add(r);
/* 436 */               while (r != null && r.getNodeType() == 3) {
/* 437 */                 r = r.getNextSibling();
/*     */               }
/* 439 */               if (r == null)
/*     */                 return; 
/*     */             } 
/* 442 */             toNodeSet(r, result);
/*     */           } 
/*     */           return;
/*     */         case 8:
/*     */           return;
/*     */         case 10:
/*     */           return;
/*     */       } 
/* 450 */       result.add(rootNode);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\sam\\util\SAMLUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */