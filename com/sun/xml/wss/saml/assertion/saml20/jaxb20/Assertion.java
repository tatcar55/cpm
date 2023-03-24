/*     */ package com.sun.xml.wss.saml.assertion.saml20.jaxb20;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.dsig.WSSPolicyConsumerImpl;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AdviceType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AssertionType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AuthnStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AuthzDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.ConditionsType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.NameIDType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.KeySelector;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.NodeSetData;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dom.DOMStructure;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureException;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.dom.DOMSignContext;
/*     */ import javax.xml.crypto.dsig.dom.DOMValidateContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*     */ import javax.xml.crypto.dsig.keyinfo.X509Data;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.datatype.DatatypeFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPException;
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
/*     */ public class Assertion
/*     */   extends AssertionType
/*     */   implements Assertion
/*     */ {
/* 127 */   private Element signedAssertion = null;
/* 128 */   private NameIDType issuerValue = null;
/* 129 */   private BigInteger majorValue = null;
/* 130 */   private BigInteger minorValue = null;
/*     */   
/* 132 */   private List<Object> statementList = null;
/* 133 */   private String canonicalizationMethod = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */ 
/*     */ 
/*     */   
/*     */   private JAXBContext jc;
/*     */ 
/*     */   
/* 140 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*     */   
/*     */   public Assertion(AssertionType assertion) {
/* 143 */     setID(assertion.getID());
/* 144 */     setIssueInstant(assertion.getIssueInstant());
/* 145 */     setIssuer(assertion.getIssuer());
/* 146 */     setAdvice(assertion.getAdvice());
/* 147 */     setConditions(assertion.getConditions());
/* 148 */     setSubject(assertion.getSubject());
/* 149 */     setVersion(assertion.getVersion());
/* 150 */     setSignature(assertion.getSignature());
/* 151 */     setStatement(assertion.getStatementOrAuthnStatementOrAuthzDecisionStatement());
/*     */   }
/*     */   
/* 154 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getMajorVersion() {
/* 159 */     return this.majorValue;
/*     */   }
/*     */   
/*     */   public BigInteger getMinorVersion() {
/* 163 */     return this.minorValue;
/*     */   }
/*     */   
/*     */   public void setMajorVersion(BigInteger majorValue) {
/* 167 */     this.majorValue = majorValue;
/*     */   }
/*     */   public void setMinorVersion(BigInteger minorValue) {
/* 170 */     this.minorValue = minorValue;
/*     */   }
/*     */   
/*     */   public String getAssertionID() {
/* 174 */     return getID();
/*     */   }
/*     */   
/*     */   public String getSamlIssuer() {
/* 178 */     this.issuerValue = getIssuer();
/* 179 */     return this.issuerValue.getValue();
/*     */   }
/*     */   
/*     */   public String getIssueInstance() {
/* 183 */     if (this.issueInstant != null) {
/* 184 */       return this.issueInstant.toString();
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Conditions getConditions() {
/* 191 */     Conditions cond = new Conditions(super.getConditions());
/* 192 */     return cond;
/*     */   }
/*     */ 
/*     */   
/*     */   public Advice getAdvice() {
/* 197 */     Advice adv = new Advice(super.getAdvice());
/* 198 */     return adv;
/*     */   }
/*     */ 
/*     */   
/*     */   public Subject getSubject() {
/* 203 */     Subject subj = new Subject(super.getSubject());
/* 204 */     return subj;
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
/*     */   public Element sign(PublicKey pubKey, PrivateKey privKey) throws SAMLException {
/* 216 */     if (this.signedAssertion != null) {
/* 217 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 223 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 224 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", pubKey, privKey);
/*     */     }
/* 226 */     catch (Exception ex) {
/*     */       
/* 228 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert) throws SAMLException {
/* 234 */     if (this.signedAssertion != null) {
/* 235 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 241 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 242 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey, alwaysIncludeCert);
/*     */     }
/* 244 */     catch (Exception ex) {
/*     */       
/* 246 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert, String sigAlgorithm, String canonicalizationAlgorithm) throws SAMLException {
/* 252 */     if (this.signedAssertion != null) {
/* 253 */       return this.signedAssertion;
/*     */     }
/*     */     
/* 256 */     if (sigAlgorithm == null) {
/* 257 */       sigAlgorithm = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */     }
/*     */     
/* 260 */     if (canonicalizationAlgorithm != null) {
/* 261 */       this.canonicalizationMethod = canonicalizationAlgorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 267 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 268 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), sigAlgorithm, cert, privKey, alwaysIncludeCert);
/*     */     }
/* 270 */     catch (Exception ex) {
/*     */       
/* 272 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 278 */     if (this.signedAssertion != null) {
/* 279 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 285 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 286 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey);
/*     */     }
/* 288 */     catch (Exception ex) {
/*     */       
/* 290 */       throw new SAMLException(ex);
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
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, PublicKey pubKey, PrivateKey privKey) throws SAMLException {
/* 305 */     if (this.signedAssertion != null) {
/* 306 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 314 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 315 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 317 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 318 */       Transform tr2 = fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec)null);
/* 319 */       transformList.add(tr1);
/* 320 */       transformList.add(tr2);
/*     */       
/* 322 */       String uri = "#" + getID();
/* 323 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 326 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 334 */       KeyValue kv = kif.newKeyValue(pubKey);
/*     */ 
/*     */       
/* 337 */       KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
/*     */ 
/*     */       
/* 340 */       Document doc = XMLUtil.newDocument();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 360 */       DOMSignContext dsc = null;
/* 361 */       KeySelector ks = KeySelector.singletonKeySelector(privKey);
/* 362 */       NodeList nl = assertionElement.getElementsByTagNameNS(assertionElement.getNamespaceURI(), "Issuer");
/* 363 */       Node issuer = null;
/* 364 */       if (nl != null && nl.getLength() > 0) {
/* 365 */         issuer = nl.item(0);
/*     */       }
/* 367 */       Node nextSibling = (issuer != null) ? issuer.getNextSibling() : null;
/* 368 */       if (nextSibling != null) {
/* 369 */         dsc = new DOMSignContext(ks, assertionElement, nextSibling);
/*     */       } else {
/* 371 */         dsc = new DOMSignContext(privKey, assertionElement);
/*     */       } 
/* 373 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 374 */       map.put(getID(), assertionElement);
/*     */       
/* 376 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 377 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 378 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 381 */       signature.sign(dsc);
/*     */       
/* 383 */       this.signedAssertion = assertionElement;
/* 384 */       return assertionElement;
/* 385 */     } catch (Exception ex) {
/*     */       
/* 387 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey, boolean includeCert) throws SAMLException {
/* 394 */     if (this.signedAssertion != null) {
/* 395 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 401 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 402 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 404 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 405 */       Transform tr2 = fac.newTransform(this.canonicalizationMethod, (TransformParameterSpec)null);
/* 406 */       transformList.add(tr1);
/* 407 */       transformList.add(tr2);
/*     */       
/* 409 */       String uri = "#" + getID();
/* 410 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 413 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(this.canonicalizationMethod, (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       Document doc = MessageFactory.newInstance().createMessage().getSOAPPart();
/* 422 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 423 */       KeyInfo ki = null;
/* 424 */       if (!includeCert) {
/* 425 */         byte[] skid = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 426 */         if (skid != null) {
/* 427 */           X509SubjectKeyIdentifier keyIdentifier = new X509SubjectKeyIdentifier(doc);
/* 428 */           keyIdentifier.setCertificate(cert);
/* 429 */           keyIdentifier.setReferenceValue(Base64.encode(skid));
/* 430 */           SecurityTokenReference str = new SecurityTokenReference();
/* 431 */           str.setReference((ReferenceElement)keyIdentifier);
/* 432 */           DOMStructure domKeyInfo = new DOMStructure(str.getAsSoapElement());
/* 433 */           ki = kif.newKeyInfo(Collections.singletonList(domKeyInfo));
/*     */         } 
/*     */       } 
/*     */       
/* 437 */       if (ki == null) {
/* 438 */         X509Data x509Data = kif.newX509Data(Collections.singletonList(cert));
/* 439 */         ki = kif.newKeyInfo(Collections.singletonList(x509Data));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 444 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 459 */       DOMSignContext dsc = null;
/* 460 */       KeySelector ks = KeySelector.singletonKeySelector(privKey);
/* 461 */       NodeList nl = assertionElement.getElementsByTagNameNS(assertionElement.getNamespaceURI(), "Issuer");
/* 462 */       Node issuer = null;
/* 463 */       if (nl != null && nl.getLength() > 0) {
/* 464 */         issuer = nl.item(0);
/*     */       }
/* 466 */       Node nextSibling = (issuer != null) ? issuer.getNextSibling() : null;
/* 467 */       if (nextSibling != null) {
/* 468 */         dsc = new DOMSignContext(ks, assertionElement, nextSibling);
/*     */       } else {
/* 470 */         dsc = new DOMSignContext(privKey, assertionElement);
/*     */       } 
/* 472 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 473 */       map.put(getID(), assertionElement);
/*     */       
/* 475 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 476 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 477 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 480 */       signature.sign(dsc);
/*     */       
/* 482 */       this.signedAssertion = assertionElement;
/* 483 */       return assertionElement;
/* 484 */     } catch (XWSSecurityException ex) {
/* 485 */       throw new SAMLException(ex);
/* 486 */     } catch (MarshalException ex) {
/* 487 */       throw new SAMLException(ex);
/* 488 */     } catch (NoSuchAlgorithmException ex) {
/* 489 */       throw new SAMLException(ex);
/* 490 */     } catch (SOAPException ex) {
/* 491 */       throw new SAMLException(ex);
/* 492 */     } catch (XMLSignatureException ex) {
/* 493 */       throw new SAMLException(ex);
/* 494 */     } catch (InvalidAlgorithmParameterException ex) {
/* 495 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 500 */     return sign(digestMethod, signatureMethod, cert, privKey, false);
/*     */   }
/*     */   
/*     */   public Element toElement(Node doc) throws XWSSecurityException {
/* 504 */     if (this.signedAssertion == null) {
/* 505 */       this.signedAssertion = SAMLUtil.toElement(doc, this, this.jc);
/* 506 */       if (this.signedAssertion == null) {
/* 507 */         return this.signedAssertion;
/*     */       }
/* 509 */       if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/* 510 */         this.signedAssertion.setAttributeNS(XMLNS_URI, "xmlns:xs", "http://www.w3.org/2001/XMLSchema");
/*     */       }
/*     */     } 
/* 513 */     return this.signedAssertion;
/*     */   }
/*     */   
/*     */   public boolean isSigned() {
/* 517 */     return (this.signature != null);
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
/*     */   public static Assertion fromElement(Element element) throws SAMLException {
/*     */     try {
/* 533 */       JAXBContext jc = SAML20JAXBUtil.getJAXBContext();
/*     */       
/* 535 */       Unmarshaller u = jc.createUnmarshaller();
/* 536 */       Object el = u.unmarshal(element);
/*     */       
/* 538 */       return new Assertion(((JAXBElement<AssertionType>)el).getValue());
/* 539 */     } catch (Exception ex) {
/*     */       
/* 541 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setStatement(List statement) {
/* 546 */     this.statementOrAuthnStatementOrAuthzDecisionStatement = statement;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 550 */     return "urn:oasis:names:tc:SAML:2.0:assertion";
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getTokenValue() {
/*     */     
/* 556 */     try { Document doc = XMLUtil.newDocument();
/* 557 */       return toElement(doc); }
/* 558 */     catch (ParserConfigurationException ex) {  }
/* 559 */     catch (XWSSecurityException ex) {}
/*     */     
/* 561 */     return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Assertion(String assertionID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements) throws SAMLException {
/* 593 */     if (assertionID != null) {
/* 594 */       setID(assertionID);
/*     */     }
/* 596 */     if (issuer != null) {
/* 597 */       setIssuer(issuer);
/*     */     }
/* 599 */     if (issueInstant != null) {
/*     */       try {
/* 601 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 602 */         setIssueInstant(factory.newXMLGregorianCalendar(issueInstant));
/* 603 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 608 */     if (conditions != null) {
/* 609 */       setConditions(conditions);
/*     */     }
/* 611 */     if (advice != null) {
/* 612 */       setAdvice(advice);
/*     */     }
/* 614 */     if (statements != null) {
/* 615 */       setStatement(statements);
/*     */     }
/* 617 */     if (subject != null) {
/* 618 */       setSubject(subject);
/*     */     }
/* 620 */     setVersion("2.0");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Assertion(String assertionID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements, JAXBContext jcc) throws SAMLException {
/* 653 */     this(assertionID, issuer, issueInstant, conditions, advice, subject, statements);
/*     */     
/* 655 */     this.jc = jcc;
/*     */   }
/*     */   
/*     */   private static class DSigResolver
/*     */     implements URIDereferencer {
/* 660 */     Element elem = null;
/* 661 */     Map map = null;
/* 662 */     Class _nodeSetClass = null;
/* 663 */     String optNSClassName = "org.jcp.xml.dsig.internal.dom.DOMSubTreeData";
/* 664 */     Constructor _constructor = null;
/* 665 */     Boolean _false = Boolean.valueOf(false);
/*     */     DSigResolver(Map map, Element elem) {
/* 667 */       this.elem = elem;
/* 668 */       this.map = map;
/* 669 */       init();
/*     */     }
/*     */     
/*     */     void init() {
/*     */       try {
/* 674 */         this._nodeSetClass = Class.forName(this.optNSClassName);
/* 675 */         this._constructor = this._nodeSetClass.getConstructor(new Class[] { Node.class, boolean.class });
/* 676 */       } catch (LinkageError le) {
/*     */       
/* 678 */       } catch (ClassNotFoundException cne) {
/*     */       
/* 680 */       } catch (NoSuchMethodException ne) {}
/*     */     }
/*     */ 
/*     */     
/*     */     public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/*     */       try {
/* 686 */         String uri = null;
/* 687 */         uri = uriRef.getURI();
/* 688 */         return dereferenceURI(uri, context);
/* 689 */       } catch (Exception ex) {
/*     */         
/* 691 */         throw new URIReferenceException(ex);
/*     */       } 
/*     */     }
/*     */     Data dereferenceURI(String uri, XMLCryptoContext context) throws URIReferenceException {
/* 695 */       if (uri.charAt(0) == '#') {
/* 696 */         uri = uri.substring(1, uri.length());
/* 697 */         Element el = this.elem.getOwnerDocument().getElementById(uri);
/* 698 */         if (el == null) {
/* 699 */           el = (Element)this.map.get(uri);
/*     */         }
/*     */         
/* 702 */         if (this._constructor != null) {
/*     */           try {
/* 704 */             return this._constructor.newInstance(new Object[] { el, this._false });
/* 705 */           } catch (Exception ex) {
/*     */             
/* 707 */             ex.printStackTrace();
/*     */           } 
/*     */         } else {
/* 710 */           final HashSet nodeSet = new HashSet();
/* 711 */           toNodeSet(el, nodeSet);
/* 712 */           return new NodeSetData() {
/*     */               public Iterator iterator() {
/* 714 */                 return nodeSet.iterator();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 721 */       return null;
/*     */     }
/*     */     void toNodeSet(Node rootNode, Set<Node> result) {
/*     */       Element el;
/*     */       Node r;
/* 726 */       switch (rootNode.getNodeType()) {
/*     */         case 1:
/* 728 */           result.add(rootNode);
/* 729 */           el = (Element)rootNode;
/* 730 */           if (el.hasAttributes()) {
/* 731 */             NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 732 */             for (int i = 0; i < nl.getLength(); i++) {
/* 733 */               result.add(nl.item(i));
/*     */             }
/*     */           } 
/*     */         
/*     */         case 9:
/* 738 */           for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 739 */             if (r.getNodeType() == 3) {
/* 740 */               result.add(r);
/* 741 */               while (r != null && r.getNodeType() == 3) {
/* 742 */                 r = r.getNextSibling();
/*     */               }
/* 744 */               if (r == null)
/*     */                 return; 
/*     */             } 
/* 747 */             toNodeSet(r, result);
/*     */           } 
/*     */           return;
/*     */         case 8:
/*     */           return;
/*     */         case 10:
/*     */           return;
/*     */       } 
/* 755 */       result.add(rootNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Object> getStatements() {
/* 763 */     if (this.statementList == null) {
/* 764 */       this.statementList = new ArrayList();
/*     */     } else {
/* 766 */       return this.statementList;
/*     */     } 
/* 768 */     List<Object> list = getStatementOrAuthnStatementOrAuthzDecisionStatement();
/* 769 */     Iterator ite = list.iterator();
/*     */     
/* 771 */     while (ite.hasNext()) {
/* 772 */       Object object = ite.next();
/* 773 */       if (object instanceof AttributeStatementType) {
/* 774 */         AttributeStatement attStmt = new AttributeStatement((AttributeStatementType)object);
/* 775 */         this.statementList.add(attStmt); continue;
/* 776 */       }  if (object instanceof AuthnStatementType) {
/* 777 */         AuthnStatement authStmt = new AuthnStatement((AuthnStatementType)object);
/* 778 */         this.statementList.add(authStmt); continue;
/* 779 */       }  if (object instanceof AuthzDecisionStatementType) {
/* 780 */         AuthzDecisionStatement authDesStmt = new AuthzDecisionStatement((AuthzDecisionStatementType)object);
/* 781 */         this.statementList.add(authDesStmt); continue;
/* 782 */       }  if (object instanceof AttributeStatement || object instanceof AuthnStatement || object instanceof AuthzDecisionStatement) {
/*     */ 
/*     */         
/* 785 */         this.statementList = list;
/* 786 */         return this.statementList;
/*     */       } 
/*     */     } 
/* 789 */     return this.statementList;
/*     */   }
/*     */   
/*     */   public boolean verifySignature(PublicKey pubKey) throws SAMLException {
/*     */     try {
/* 794 */       Document doc = XMLUtil.newDocument();
/* 795 */       Element samlAssertion = toElement(doc);
/* 796 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 797 */       map.put(getID(), samlAssertion);
/* 798 */       NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*     */       
/* 800 */       if (nl.getLength() == 0) {
/* 801 */         throw new SAMLException("Unsigned SAML Assertion encountered while verifying the SAML signature");
/*     */       }
/* 803 */       Element signElement = (Element)nl.item(0);
/* 804 */       DOMValidateContext validationContext = new DOMValidateContext(pubKey, signElement);
/* 805 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*     */       
/* 807 */       XMLSignature xmlSignature = signatureFactory.unmarshalXMLSignature(validationContext);
/* 808 */       validationContext.setURIDereferencer(new DSigResolver(map, samlAssertion));
/* 809 */       boolean coreValidity = xmlSignature.validate(validationContext);
/* 810 */       return coreValidity;
/* 811 */     } catch (Exception ex) {
/* 812 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml20\jaxb20\Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */