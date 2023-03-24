/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb20;
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
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AdviceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AssertionType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthenticationStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AuthorizationDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.ConditionsType;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
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
/* 124 */   private Element signedAssertion = null;
/* 125 */   private String version = null;
/* 126 */   private String canonicalizationMethod = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   
/* 128 */   private List<Object> statementList = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private JAXBContext jc;
/*     */ 
/*     */   
/* 135 */   public static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/".intern();
/*     */   
/*     */   public Assertion(AssertionType assertion) {
/* 138 */     setAdvice(assertion.getAdvice());
/* 139 */     setAssertionID(assertion.getAssertionID());
/* 140 */     setConditions(assertion.getConditions());
/* 141 */     setIssueInstant(assertion.getIssueInstant());
/* 142 */     setIssuer(assertion.getIssuer());
/* 143 */     setMajorVersion(assertion.getMajorVersion());
/* 144 */     setMinorVersion(assertion.getMinorVersion());
/* 145 */     setSignature(assertion.getSignature());
/* 146 */     setStatement(assertion.getStatementOrSubjectStatementOrAuthenticationStatement());
/*     */   }
/*     */   
/* 149 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 154 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(String version) {
/* 158 */     this.version = version;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   public String getSamlIssuer() {
/* 166 */     return getIssuer();
/*     */   }
/*     */   
/*     */   public String getIssueInstance() {
/* 170 */     if (this.issueInstant != null) {
/* 171 */       return this.issueInstant.toString();
/*     */     }
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Conditions getConditions() {
/* 178 */     Conditions cond = new Conditions(super.getConditions());
/* 179 */     return cond;
/*     */   }
/*     */ 
/*     */   
/*     */   public Advice getAdvice() {
/* 184 */     Advice advice = new Advice(super.getAdvice());
/* 185 */     return advice;
/*     */   }
/*     */   
/*     */   public Subject getSubject() {
/* 189 */     throw new UnsupportedOperationException("Direct call of getSubject() method on SAML1.1 assertion is not supported.So, first get the Statements of the SAML assertion and then call the getSubject() on each statement");
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
/*     */   public Element sign(PublicKey pubKey, PrivateKey privKey) throws SAMLException {
/* 204 */     if (this.signedAssertion != null) {
/* 205 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 211 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 212 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", pubKey, privKey);
/*     */     }
/* 214 */     catch (Exception ex) {
/*     */       
/* 216 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert) throws SAMLException {
/* 222 */     if (this.signedAssertion != null) {
/* 223 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 229 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 230 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey, alwaysIncludeCert);
/*     */     }
/* 232 */     catch (Exception ex) {
/*     */       
/* 234 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert, String sigAlgorithm, String canonicalizationAlgorithm) throws SAMLException {
/* 240 */     if (this.signedAssertion != null) {
/* 241 */       return this.signedAssertion;
/*     */     }
/*     */     
/* 244 */     if (sigAlgorithm == null) {
/* 245 */       sigAlgorithm = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */     }
/* 247 */     if (canonicalizationAlgorithm != null) {
/* 248 */       this.canonicalizationMethod = canonicalizationAlgorithm;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 253 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 254 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), sigAlgorithm, cert, privKey, alwaysIncludeCert);
/*     */     }
/* 256 */     catch (Exception ex) {
/*     */       
/* 258 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 264 */     if (this.signedAssertion != null) {
/* 265 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 271 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 272 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey);
/*     */     }
/* 274 */     catch (Exception ex) {
/*     */       
/* 276 */       throw new SAMLException(ex);
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
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, PublicKey pubKey, PrivateKey privKey) throws SAMLException {
/* 293 */     if (this.signedAssertion != null) {
/* 294 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 300 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 301 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 303 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 304 */       Transform tr2 = fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec)null);
/* 305 */       transformList.add(tr1);
/* 306 */       transformList.add(tr2);
/*     */       
/* 308 */       String uri = "#" + getAssertionID();
/* 309 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 312 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 320 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 321 */       KeyValue kv = kif.newKeyValue(pubKey);
/*     */ 
/*     */       
/* 324 */       KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
/*     */ 
/*     */       
/* 327 */       Document doc = XMLUtil.newDocument();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 333 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 352 */       DOMSignContext dsc = new DOMSignContext(privKey, assertionElement);
/* 353 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 354 */       map.put(getAssertionID(), assertionElement);
/*     */       
/* 356 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 357 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 358 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 361 */       signature.sign(dsc);
/*     */       
/* 363 */       this.signedAssertion = assertionElement;
/* 364 */       return assertionElement;
/* 365 */     } catch (Exception ex) {
/*     */       
/* 367 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert) throws SAMLException {
/* 374 */     if (this.signedAssertion != null) {
/* 375 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 381 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 382 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 384 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 385 */       Transform tr2 = fac.newTransform(this.canonicalizationMethod, (TransformParameterSpec)null);
/* 386 */       transformList.add(tr1);
/* 387 */       transformList.add(tr2);
/*     */       
/* 389 */       String uri = "#" + getAssertionID();
/* 390 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 393 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(this.canonicalizationMethod, (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 399 */       Document doc = MessageFactory.newInstance().createMessage().getSOAPPart();
/* 400 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 401 */       KeyInfo ki = null;
/*     */       
/* 403 */       if (!alwaysIncludeCert) {
/* 404 */         byte[] skid = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 405 */         if (skid != null) {
/* 406 */           X509SubjectKeyIdentifier keyIdentifier = new X509SubjectKeyIdentifier(doc);
/* 407 */           keyIdentifier.setCertificate(cert);
/* 408 */           keyIdentifier.setReferenceValue(Base64.encode(skid));
/* 409 */           SecurityTokenReference str = new SecurityTokenReference();
/* 410 */           str.setReference((ReferenceElement)keyIdentifier);
/* 411 */           DOMStructure domKeyInfo = new DOMStructure(str.getAsSoapElement());
/* 412 */           ki = kif.newKeyInfo(Collections.singletonList(domKeyInfo));
/*     */         } 
/*     */       } 
/*     */       
/* 416 */       if (ki == null) {
/* 417 */         X509Data x509Data = kif.newX509Data(Collections.singletonList(cert));
/* 418 */         ki = kif.newKeyInfo(Collections.singletonList(x509Data));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 424 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       DOMSignContext dsc = new DOMSignContext(privKey, assertionElement);
/* 440 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 441 */       map.put(getAssertionID(), assertionElement);
/*     */       
/* 443 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 444 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 445 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 448 */       signature.sign(dsc);
/*     */       
/* 450 */       this.signedAssertion = assertionElement;
/* 451 */       return assertionElement;
/* 452 */     } catch (XWSSecurityException ex) {
/* 453 */       throw new SAMLException(ex);
/* 454 */     } catch (MarshalException ex) {
/* 455 */       throw new SAMLException(ex);
/* 456 */     } catch (NoSuchAlgorithmException ex) {
/* 457 */       throw new SAMLException(ex);
/* 458 */     } catch (SOAPException ex) {
/* 459 */       throw new SAMLException(ex);
/* 460 */     } catch (XMLSignatureException ex) {
/* 461 */       throw new SAMLException(ex);
/* 462 */     } catch (InvalidAlgorithmParameterException ex) {
/* 463 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 468 */     return sign(digestMethod, signatureMethod, cert, privKey, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Element toElement(Node doc) throws XWSSecurityException {
/* 473 */     if (this.signedAssertion == null) {
/*     */       
/* 475 */       this.signedAssertion = SAMLUtil.toElement(doc, this, this.jc);
/* 476 */       if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/* 477 */         this.signedAssertion.setAttributeNS(XMLNS_URI, "xmlns:xs", "http://www.w3.org/2001/XMLSchema");
/*     */       }
/*     */     } 
/*     */     
/* 481 */     return this.signedAssertion;
/*     */   }
/*     */   
/*     */   public boolean isSigned() {
/* 485 */     return (this.signature != null);
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
/* 501 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 503 */       Unmarshaller u = jc.createUnmarshaller();
/* 504 */       Object el = u.unmarshal(element);
/*     */       
/* 506 */       return new Assertion(((JAXBElement<AssertionType>)el).getValue());
/* 507 */     } catch (Exception ex) {
/*     */       
/* 509 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Object> getStatements() {
/* 514 */     if (this.statementList == null) {
/* 515 */       this.statementList = new ArrayList();
/*     */     } else {
/* 517 */       return this.statementList;
/*     */     } 
/* 519 */     List<Object> list = getStatementOrSubjectStatementOrAuthenticationStatement();
/* 520 */     Iterator ite = list.iterator();
/*     */     
/* 522 */     while (ite.hasNext()) {
/* 523 */       Object object = ite.next();
/* 524 */       if (object instanceof AttributeStatementType) {
/* 525 */         AttributeStatement attStmt = new AttributeStatement((AttributeStatementType)object);
/* 526 */         this.statementList.add(attStmt); continue;
/* 527 */       }  if (object instanceof AuthenticationStatementType) {
/* 528 */         AuthenticationStatement authStmt = new AuthenticationStatement((AuthenticationStatementType)object);
/* 529 */         this.statementList.add(authStmt); continue;
/* 530 */       }  if (object instanceof AuthorizationDecisionStatementType) {
/* 531 */         AuthorizationDecisionStatement authDesStmt = new AuthorizationDecisionStatement((AuthorizationDecisionStatementType)object);
/* 532 */         this.statementList.add(authDesStmt); continue;
/* 533 */       }  if (object instanceof AttributeStatement || object instanceof AuthenticationStatement || object instanceof AuthorizationDecisionStatement) {
/*     */ 
/*     */         
/* 536 */         this.statementList = list;
/* 537 */         return this.statementList;
/*     */       } 
/*     */     } 
/* 540 */     return this.statementList;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setStatement(List statement) {
/* 545 */     this.statementOrSubjectStatementOrAuthenticationStatement = statement;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 549 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     
/* 554 */     try { Document doc = XMLUtil.newDocument();
/* 555 */       return toElement(doc); }
/* 556 */     catch (ParserConfigurationException ex) {  }
/* 557 */     catch (XWSSecurityException ex) {}
/*     */     
/* 559 */     return null;
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
/*     */   public Assertion(String assertionID, String issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, List statements) throws SAMLException {
/* 591 */     if (assertionID != null) {
/* 592 */       setAssertionID(assertionID);
/*     */     }
/* 594 */     if (issuer != null) {
/* 595 */       setIssuer(issuer);
/*     */     }
/* 597 */     if (issueInstant != null) {
/*     */       try {
/* 599 */         DatatypeFactory factory = DatatypeFactory.newInstance();
/* 600 */         setIssueInstant(factory.newXMLGregorianCalendar(issueInstant));
/* 601 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 607 */     if (conditions != null) {
/* 608 */       setConditions(conditions);
/*     */     }
/* 610 */     if (advice != null) {
/* 611 */       setAdvice(advice);
/*     */     }
/* 613 */     if (statements != null) {
/* 614 */       setStatement(statements);
/*     */     }
/* 616 */     setMajorVersion(BigInteger.ONE);
/* 617 */     setMinorVersion(BigInteger.ONE);
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
/*     */   public Assertion(String assertionID, String issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, List statements, JAXBContext jcc) throws SAMLException {
/* 650 */     this(assertionID, issuer, issueInstant, conditions, advice, statements);
/*     */     
/* 652 */     this.jc = jcc;
/*     */   }
/*     */   
/*     */   private static class DSigResolver
/*     */     implements URIDereferencer
/*     */   {
/* 658 */     Element elem = null;
/* 659 */     Map map = null;
/* 660 */     Class _nodeSetClass = null;
/* 661 */     String optNSClassName = "org.jcp.xml.dsig.internal.dom.DOMSubTreeData";
/* 662 */     Constructor _constructor = null;
/* 663 */     Boolean _false = Boolean.valueOf(false);
/*     */     DSigResolver(Map map, Element elem) {
/* 665 */       this.elem = elem;
/* 666 */       this.map = map;
/* 667 */       init();
/*     */     }
/*     */     
/*     */     void init() {
/*     */       try {
/* 672 */         this._nodeSetClass = Class.forName(this.optNSClassName);
/* 673 */         this._constructor = this._nodeSetClass.getConstructor(new Class[] { Node.class, boolean.class });
/* 674 */       } catch (LinkageError le) {
/*     */       
/* 676 */       } catch (ClassNotFoundException cne) {
/*     */       
/* 678 */       } catch (NoSuchMethodException ne) {}
/*     */     }
/*     */ 
/*     */     
/*     */     public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/*     */       try {
/* 684 */         String uri = null;
/* 685 */         uri = uriRef.getURI();
/* 686 */         return dereferenceURI(uri, context);
/* 687 */       } catch (Exception ex) {
/*     */         
/* 689 */         throw new URIReferenceException(ex);
/*     */       } 
/*     */     }
/*     */     Data dereferenceURI(String uri, XMLCryptoContext context) throws URIReferenceException {
/* 693 */       if (uri.charAt(0) == '#') {
/* 694 */         uri = uri.substring(1, uri.length());
/* 695 */         Element el = this.elem.getOwnerDocument().getElementById(uri);
/* 696 */         if (el == null) {
/* 697 */           el = (Element)this.map.get(uri);
/*     */         }
/*     */         
/* 700 */         if (this._constructor != null) {
/*     */           try {
/* 702 */             return this._constructor.newInstance(new Object[] { el, this._false });
/* 703 */           } catch (Exception ex) {
/*     */             
/* 705 */             ex.printStackTrace();
/*     */           } 
/*     */         } else {
/* 708 */           final HashSet nodeSet = new HashSet();
/* 709 */           toNodeSet(el, nodeSet);
/* 710 */           return new NodeSetData() {
/*     */               public Iterator iterator() {
/* 712 */                 return nodeSet.iterator();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 719 */       return null;
/*     */     }
/*     */     void toNodeSet(Node rootNode, Set<Node> result) {
/*     */       Element el;
/*     */       Node r;
/* 724 */       switch (rootNode.getNodeType()) {
/*     */         case 1:
/* 726 */           result.add(rootNode);
/* 727 */           el = (Element)rootNode;
/* 728 */           if (el.hasAttributes()) {
/* 729 */             NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 730 */             for (int i = 0; i < nl.getLength(); i++) {
/* 731 */               result.add(nl.item(i));
/*     */             }
/*     */           } 
/*     */         
/*     */         case 9:
/* 736 */           for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 737 */             if (r.getNodeType() == 3) {
/* 738 */               result.add(r);
/* 739 */               while (r != null && r.getNodeType() == 3) {
/* 740 */                 r = r.getNextSibling();
/*     */               }
/* 742 */               if (r == null)
/*     */                 return; 
/*     */             } 
/* 745 */             toNodeSet(r, result);
/*     */           } 
/*     */           return;
/*     */         case 8:
/*     */           return;
/*     */         case 10:
/*     */           return;
/*     */       } 
/* 753 */       result.add(rootNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifySignature(PublicKey pubKey) throws SAMLException {
/*     */     try {
/* 762 */       Document doc = XMLUtil.newDocument();
/* 763 */       Element samlAssertion = toElement(doc);
/* 764 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 765 */       map.put(getAssertionID(), samlAssertion);
/* 766 */       NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*     */       
/* 768 */       if (nl.getLength() == 0) {
/* 769 */         throw new SAMLException("Unsigned SAML Assertion encountered while verifying the SAML signature");
/*     */       }
/* 771 */       Element signElement = (Element)nl.item(0);
/* 772 */       DOMValidateContext validationContext = new DOMValidateContext(pubKey, signElement);
/* 773 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*     */       
/* 775 */       XMLSignature xmlSignature = signatureFactory.unmarshalXMLSignature(validationContext);
/* 776 */       validationContext.setURIDereferencer(new DSigResolver(map, samlAssertion));
/* 777 */       boolean coreValidity = xmlSignature.validate(validationContext);
/* 778 */       return coreValidity;
/* 779 */     } catch (Exception ex) {
/* 780 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb20\Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */