/*     */ package com.sun.xml.wss.saml.assertion.saml11.jaxb10;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/*     */ import com.sun.xml.bind.util.ListImpl;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.dsig.WSSPolicyConsumerImpl;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AdviceType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AttributeStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthenticationStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.AuthorizationDecisionStatementType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.ConditionsType;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl;
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
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBContext;
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
/*     */ 
/*     */ public class Assertion
/*     */   extends AssertionImpl
/*     */   implements Assertion
/*     */ {
/* 124 */   private Element signedAssertion = null;
/* 125 */   private String version = null;
/*     */   
/* 127 */   private List<Object> statementList = null;
/*     */   private JAXBContext jc;
/* 129 */   private String canonicalizationMethod = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*     */   
/*     */   public Assertion(AssertionImpl assertion) {
/* 132 */     setAdvice(assertion.getAdvice());
/* 133 */     setAssertionID(assertion.getAssertionID());
/* 134 */     setConditions(assertion.getConditions());
/* 135 */     setIssueInstant(assertion.getIssueInstant());
/* 136 */     setIssuer(assertion.getIssuer());
/* 137 */     setMajorVersion(assertion.getMajorVersion());
/* 138 */     setMinorVersion(assertion.getMinorVersion());
/* 139 */     setSignature(assertion.getSignature());
/* 140 */     setStatement(assertion.getStatementOrSubjectStatementOrAuthenticationStatement());
/*     */   }
/*     */   
/* 143 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getVersion() {
/* 149 */     return this.version;
/*     */   }
/*     */   
/*     */   public void setVersion(String version) {
/* 153 */     this.version = version;
/*     */   }
/*     */   
/*     */   public String getID() {
/* 157 */     return null;
/*     */   }
/*     */   
/*     */   public String getSamlIssuer() {
/* 161 */     return getIssuer();
/*     */   }
/*     */   
/*     */   public String getIssueInstance() {
/* 165 */     if (getIssueInstant() != null) {
/* 166 */       return getIssueInstant().toString();
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Conditions getConditions() {
/* 173 */     Conditions cond = new Conditions(super.getConditions());
/* 174 */     return cond;
/*     */   }
/*     */ 
/*     */   
/*     */   public Advice getAdvice() {
/* 179 */     Advice advice = new Advice(super.getAdvice());
/* 180 */     return advice;
/*     */   }
/*     */   
/*     */   public Subject getSubject() {
/* 184 */     throw new UnsupportedOperationException("Direct call of getSubject() method on SAML1.1 assertion is not supported.So, first get the Statements of the SAML assertion and then call the getSubject() on each statement");
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
/* 199 */     if (this.signedAssertion != null) {
/* 200 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 206 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 207 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", pubKey, privKey);
/*     */     }
/* 209 */     catch (Exception ex) {
/*     */       
/* 211 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert) throws SAMLException {
/* 217 */     if (this.signedAssertion != null) {
/* 218 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 224 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 225 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey, alwaysIncludeCert);
/*     */     }
/* 227 */     catch (Exception ex) {
/*     */       
/* 229 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert, String sigAlgorithm, String canonicalizationAlgorithm) throws SAMLException {
/* 235 */     if (this.signedAssertion != null) {
/* 236 */       return this.signedAssertion;
/*     */     }
/*     */     
/* 239 */     if (sigAlgorithm == null) {
/* 240 */       sigAlgorithm = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */     }
/*     */     
/* 243 */     if (canonicalizationAlgorithm != null) {
/* 244 */       this.canonicalizationMethod = canonicalizationAlgorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 250 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 251 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), sigAlgorithm, cert, privKey, alwaysIncludeCert);
/*     */     }
/* 253 */     catch (Exception ex) {
/*     */       
/* 255 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Element sign(X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 261 */     if (this.signedAssertion != null) {
/* 262 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 268 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 269 */       return sign(fac.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null), "http://www.w3.org/2000/09/xmldsig#rsa-sha1", cert, privKey);
/*     */     }
/* 271 */     catch (Exception ex) {
/*     */       
/* 273 */       throw new SAMLException(ex);
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
/* 290 */     if (this.signedAssertion != null) {
/* 291 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 297 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 298 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 300 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 301 */       Transform tr2 = fac.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec)null);
/* 302 */       transformList.add(tr1);
/* 303 */       transformList.add(tr2);
/*     */       
/* 305 */       String uri = "#" + getAssertionID();
/* 306 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 309 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 317 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 318 */       KeyValue kv = kif.newKeyValue(pubKey);
/*     */ 
/*     */       
/* 321 */       KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
/*     */ 
/*     */       
/* 324 */       Document doc = XMLUtil.newDocument();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 330 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 345 */       DOMSignContext dsc = new DOMSignContext(privKey, assertionElement);
/* 346 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 347 */       map.put(getAssertionID(), assertionElement);
/*     */       
/* 349 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 350 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 351 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 354 */       signature.sign(dsc);
/*     */       
/* 356 */       this.signedAssertion = assertionElement;
/* 357 */       return assertionElement;
/* 358 */     } catch (Exception ex) {
/* 359 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey, boolean alwaysIncludeCert) throws SAMLException {
/* 366 */     if (this.signedAssertion != null) {
/* 367 */       return this.signedAssertion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 373 */       XMLSignatureFactory fac = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/* 374 */       ArrayList<Transform> transformList = new ArrayList();
/*     */       
/* 376 */       Transform tr1 = fac.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/* 377 */       Transform tr2 = fac.newTransform(this.canonicalizationMethod, (TransformParameterSpec)null);
/* 378 */       transformList.add(tr1);
/* 379 */       transformList.add(tr2);
/*     */       
/* 381 */       String uri = "#" + getID();
/* 382 */       Reference ref = fac.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 385 */       SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(this.canonicalizationMethod, (C14NMethodParameterSpec)null), fac.newSignatureMethod(signatureMethod, null), Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       Document doc = MessageFactory.newInstance().createMessage().getSOAPPart();
/* 394 */       KeyInfoFactory kif = fac.getKeyInfoFactory();
/* 395 */       KeyInfo ki = null;
/* 396 */       if (!alwaysIncludeCert) {
/* 397 */         byte[] skid = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 398 */         if (skid != null) {
/* 399 */           X509SubjectKeyIdentifier keyIdentifier = new X509SubjectKeyIdentifier(doc);
/* 400 */           keyIdentifier.setCertificate(cert);
/* 401 */           keyIdentifier.setReferenceValue(Base64.encode(skid));
/* 402 */           SecurityTokenReference str = new SecurityTokenReference();
/* 403 */           str.setReference((ReferenceElement)keyIdentifier);
/* 404 */           DOMStructure domKeyInfo = new DOMStructure(str.getAsSoapElement());
/* 405 */           ki = kif.newKeyInfo(Collections.singletonList(domKeyInfo));
/*     */         } 
/*     */       } 
/*     */       
/* 409 */       if (ki == null) {
/* 410 */         X509Data x509Data = kif.newX509Data(Collections.singletonList(cert));
/* 411 */         ki = kif.newKeyInfo(Collections.singletonList(x509Data));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 416 */       Element assertionElement = toElement(doc);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       DOMSignContext dsc = new DOMSignContext(privKey, assertionElement);
/* 432 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 433 */       map.put(getID(), assertionElement);
/*     */       
/* 435 */       dsc.setURIDereferencer(new DSigResolver(map, assertionElement));
/* 436 */       XMLSignature signature = fac.newXMLSignature(si, ki);
/* 437 */       dsc.putNamespacePrefix("http://www.w3.org/2000/09/xmldsig#", "ds");
/*     */ 
/*     */       
/* 440 */       signature.sign(dsc);
/*     */       
/* 442 */       this.signedAssertion = assertionElement;
/* 443 */       return assertionElement;
/* 444 */     } catch (XWSSecurityException ex) {
/* 445 */       throw new SAMLException(ex);
/* 446 */     } catch (MarshalException ex) {
/* 447 */       throw new SAMLException(ex);
/* 448 */     } catch (NoSuchAlgorithmException ex) {
/* 449 */       throw new SAMLException(ex);
/* 450 */     } catch (SOAPException ex) {
/* 451 */       throw new SAMLException(ex);
/* 452 */     } catch (XMLSignatureException ex) {
/* 453 */       throw new SAMLException(ex);
/* 454 */     } catch (InvalidAlgorithmParameterException ex) {
/* 455 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Element sign(DigestMethod digestMethod, String signatureMethod, X509Certificate cert, PrivateKey privKey) throws SAMLException {
/* 460 */     return sign(digestMethod, signatureMethod, cert, privKey, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public Element toElement(Node doc) throws XWSSecurityException {
/* 465 */     if (this.signedAssertion == null)
/*     */     {
/* 467 */       this.signedAssertion = SAMLUtil.toElement(doc, this, this.jc);
/*     */     }
/*     */ 
/*     */     
/* 471 */     return this.signedAssertion;
/*     */   }
/*     */   
/*     */   public boolean isSigned() {
/* 475 */     return (this._Signature != null);
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
/* 491 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 493 */       Unmarshaller u = jc.createUnmarshaller();
/* 494 */       return new Assertion((AssertionImpl)u.unmarshal(element));
/* 495 */     } catch (Exception ex) {
/* 496 */       throw new SAMLException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getType() {
/* 501 */     return "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Object> getStatements() {
/* 506 */     if (this.statementList == null) {
/* 507 */       this.statementList = new ArrayList();
/*     */     } else {
/* 509 */       return this.statementList;
/*     */     } 
/* 511 */     ListImpl listImpl = _getStatementOrSubjectStatementOrAuthenticationStatement();
/* 512 */     Iterator ite = listImpl.iterator();
/*     */     
/* 514 */     while (ite.hasNext()) {
/* 515 */       Object object = ite.next();
/* 516 */       if (object instanceof AttributeStatementType) {
/* 517 */         AttributeStatement attStmt = new AttributeStatement((AttributeStatementType)object);
/* 518 */         this.statementList.add(attStmt); continue;
/* 519 */       }  if (object instanceof AuthenticationStatementType) {
/* 520 */         AuthenticationStatement authStmt = new AuthenticationStatement((AuthenticationStatementType)object);
/* 521 */         this.statementList.add(authStmt); continue;
/* 522 */       }  if (object instanceof AuthorizationDecisionStatementType) {
/* 523 */         AuthorizationDecisionStatement authDesStmt = new AuthorizationDecisionStatement((AuthorizationDecisionStatementType)object);
/* 524 */         this.statementList.add(authDesStmt); continue;
/* 525 */       }  if (object instanceof AttributeStatement || object instanceof AuthenticationStatement || object instanceof AuthorizationDecisionStatement) {
/*     */ 
/*     */         
/* 528 */         this.statementList = (List<Object>)listImpl;
/* 529 */         return this.statementList;
/*     */       } 
/*     */     } 
/* 532 */     return this.statementList;
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/*     */     
/* 537 */     try { Document doc = XMLUtil.newDocument();
/* 538 */       return toElement(doc); }
/* 539 */     catch (ParserConfigurationException ex) {  }
/* 540 */     catch (XWSSecurityException ex) {}
/*     */     
/* 542 */     return null;
/*     */   }
/*     */   
/*     */   private void setStatement(List statement) {
/* 546 */     this._StatementOrSubjectStatementOrAuthenticationStatement = new ListImpl(statement);
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
/*     */   public Assertion(String assertionID, String issuer, Calendar issueInstant, Conditions conditions, Advice advice, List statements) throws SAMLException {
/* 578 */     if (assertionID != null) {
/* 579 */       setAssertionID(assertionID);
/*     */     }
/* 581 */     if (issuer != null) {
/* 582 */       setIssuer(issuer);
/*     */     }
/* 584 */     if (issueInstant != null) {
/* 585 */       setIssueInstant(issueInstant);
/*     */     }
/* 587 */     if (conditions != null) {
/* 588 */       setConditions((ConditionsType)conditions);
/*     */     }
/* 590 */     if (advice != null) {
/* 591 */       setAdvice((AdviceType)advice);
/*     */     }
/* 593 */     if (statements != null)
/* 594 */       setStatement(statements); 
/* 595 */     setMajorVersion(BigInteger.ONE);
/* 596 */     setMinorVersion(BigInteger.ONE);
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
/*     */   public Assertion(String assertionID, String issuer, Calendar issueInstant, Conditions conditions, Advice advice, List statements, JAXBContext jcc) throws SAMLException {
/* 629 */     this(assertionID, issuer, issueInstant, conditions, advice, statements);
/*     */     
/* 631 */     this.jc = jcc;
/*     */   }
/*     */   
/*     */   private static class DSigResolver
/*     */     implements URIDereferencer {
/* 636 */     Element elem = null;
/* 637 */     Map map = null;
/* 638 */     Class _nodeSetClass = null;
/* 639 */     String optNSClassName = "org.jcp.xml.dsig.internal.dom.DOMSubTreeData";
/* 640 */     Constructor _constructor = null;
/* 641 */     Boolean _false = Boolean.valueOf(false);
/*     */     DSigResolver(Map map, Element elem) {
/* 643 */       this.elem = elem;
/* 644 */       this.map = map;
/* 645 */       init();
/*     */     }
/*     */     
/*     */     void init() {
/*     */       try {
/* 650 */         this._nodeSetClass = Class.forName(this.optNSClassName);
/* 651 */         this._constructor = this._nodeSetClass.getConstructor(new Class[] { Node.class, boolean.class });
/* 652 */       } catch (LinkageError le) {
/*     */       
/* 654 */       } catch (ClassNotFoundException cne) {
/*     */       
/* 656 */       } catch (NoSuchMethodException ne) {}
/*     */     }
/*     */ 
/*     */     
/*     */     public Data dereference(URIReference uriRef, XMLCryptoContext context) throws URIReferenceException {
/*     */       try {
/* 662 */         String uri = null;
/* 663 */         uri = uriRef.getURI();
/* 664 */         return dereferenceURI(uri, context);
/* 665 */       } catch (Exception ex) {
/* 666 */         throw new URIReferenceException(ex);
/*     */       } 
/*     */     }
/*     */     Data dereferenceURI(String uri, XMLCryptoContext context) throws URIReferenceException {
/* 670 */       if (uri.charAt(0) == '#') {
/* 671 */         uri = uri.substring(1, uri.length());
/* 672 */         Element el = this.elem.getOwnerDocument().getElementById(uri);
/* 673 */         if (el == null) {
/* 674 */           el = (Element)this.map.get(uri);
/*     */         }
/*     */         
/* 677 */         if (this._constructor != null) {
/*     */           try {
/* 679 */             return this._constructor.newInstance(new Object[] { el, this._false });
/* 680 */           } catch (Exception ex) {
/*     */             
/* 682 */             ex.printStackTrace();
/*     */           } 
/*     */         } else {
/* 685 */           final HashSet nodeSet = new HashSet();
/* 686 */           toNodeSet(el, nodeSet);
/* 687 */           return new NodeSetData() {
/*     */               public Iterator iterator() {
/* 689 */                 return nodeSet.iterator();
/*     */               }
/*     */             };
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 696 */       return null;
/*     */     }
/*     */     void toNodeSet(Node rootNode, Set<Node> result) {
/*     */       Element el;
/*     */       Node r;
/* 701 */       switch (rootNode.getNodeType()) {
/*     */         case 1:
/* 703 */           result.add(rootNode);
/* 704 */           el = (Element)rootNode;
/* 705 */           if (el.hasAttributes()) {
/* 706 */             NamedNodeMap nl = ((Element)rootNode).getAttributes();
/* 707 */             for (int i = 0; i < nl.getLength(); i++) {
/* 708 */               result.add(nl.item(i));
/*     */             }
/*     */           } 
/*     */         
/*     */         case 9:
/* 713 */           for (r = rootNode.getFirstChild(); r != null; r = r.getNextSibling()) {
/* 714 */             if (r.getNodeType() == 3) {
/* 715 */               result.add(r);
/* 716 */               while (r != null && r.getNodeType() == 3) {
/* 717 */                 r = r.getNextSibling();
/*     */               }
/* 719 */               if (r == null)
/*     */                 return; 
/*     */             } 
/* 722 */             toNodeSet(r, result);
/*     */           } 
/*     */           return;
/*     */         case 8:
/*     */           return;
/*     */         case 10:
/*     */           return;
/*     */       } 
/* 730 */       result.add(rootNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verifySignature(PublicKey pubKey) throws SAMLException {
/*     */     try {
/* 740 */       Document doc = XMLUtil.newDocument();
/* 741 */       Element samlAssertion = toElement(doc);
/* 742 */       HashMap<Object, Object> map = new HashMap<Object, Object>();
/* 743 */       map.put(getAssertionID(), samlAssertion);
/* 744 */       NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*     */       
/* 746 */       if (nl.getLength() == 0) {
/* 747 */         throw new SAMLException("Unsigned SAML Assertion encountered while verifying the SAML signature");
/*     */       }
/* 749 */       Element signElement = (Element)nl.item(0);
/* 750 */       DOMValidateContext validationContext = new DOMValidateContext(pubKey, signElement);
/* 751 */       XMLSignatureFactory signatureFactory = WSSPolicyConsumerImpl.getInstance().getSignatureFactory();
/*     */       
/* 753 */       XMLSignature xmlSignature = signatureFactory.unmarshalXMLSignature(validationContext);
/* 754 */       validationContext.setURIDereferencer(new DSigResolver(map, samlAssertion));
/* 755 */       boolean coreValidity = xmlSignature.validate(validationContext);
/* 756 */       return coreValidity;
/* 757 */     } catch (Exception ex) {
/* 758 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\assertion\saml11\jaxb10\Assertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */