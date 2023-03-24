/*     */ package com.sun.xml.wss.saml.impl;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.saml.Action;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.AnyType;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.AssertionIDRef;
/*     */ import com.sun.xml.wss.saml.AssertionIDReference;
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeDesignator;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.AudienceRestriction;
/*     */ import com.sun.xml.wss.saml.AudienceRestrictionCondition;
/*     */ import com.sun.xml.wss.saml.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.AuthnContext;
/*     */ import com.sun.xml.wss.saml.AuthnDecisionStatement;
/*     */ import com.sun.xml.wss.saml.AuthnStatement;
/*     */ import com.sun.xml.wss.saml.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.AuthorizationDecisionStatement;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.DoNotCacheCondition;
/*     */ import com.sun.xml.wss.saml.Evidence;
/*     */ import com.sun.xml.wss.saml.KeyInfoConfirmationData;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.OneTimeUse;
/*     */ import com.sun.xml.wss.saml.SAMLAssertionFactory;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.SubjectLocality;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Action;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Advice;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Attribute;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.AudienceRestriction;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.AuthnContext;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.AuthnStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.AuthzDecisionStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Conditions;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Evidence;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.KeyInfoConfirmationData;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.NameID;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.OneTimeUse;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Subject;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.SubjectLocality;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.datatype.DatatypeConfigurationException;
/*     */ import javax.xml.datatype.DatatypeFactory;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAMLAssertion2_2FactoryImpl
/*     */   extends SAMLAssertionFactory
/*     */ {
/*  73 */   DatatypeFactory dataTypeFac = null;
/*     */ 
/*     */   
/*     */   public SAMLAssertion2_2FactoryImpl() {
/*     */     try {
/*  78 */       this.dataTypeFac = DatatypeFactory.newInstance();
/*  79 */     } catch (DatatypeConfigurationException ex) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action createAction(Element actionElement) {
/*  86 */     return (Action)new Action(actionElement);
/*     */   }
/*     */   
/*     */   public Action createAction(String action, String namespace) {
/*  90 */     return (Action)new Action(action, namespace);
/*     */   }
/*     */   
/*     */   public Advice createAdvice(List assertionidreference, List assertion, List otherelement) {
/*  94 */     return (Advice)new Advice(assertionidreference, assertion, otherelement);
/*     */   }
/*     */   
/*     */   public AnyType createAnyType() {
/*  98 */     return null;
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(Element element) throws SAMLException {
/* 102 */     return (Assertion)Assertion.fromElement(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Assertion createAssertion(String assertionID, String issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, List statements) throws SAMLException {
/* 113 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Assertion createAssertion(String assertionID, String issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, List statements, JAXBContext jcc) throws SAMLException {
/* 123 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
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
/*     */   public Assertion createAssertion(String ID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements) throws SAMLException {
/* 135 */     return (Assertion)new Assertion(ID, (NameID)issuer, issueInstant, (Conditions)conditions, (Advice)advice, (Subject)subject, statements);
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
/*     */   public Assertion createAssertion(String ID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements, JAXBContext jcc) throws SAMLException {
/* 153 */     return (Assertion)new Assertion(ID, (NameID)issuer, issueInstant, (Conditions)conditions, (Advice)advice, (Subject)subject, statements, jcc);
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
/*     */   public AssertionIDReference createAssertionIDReference() throws SAMLException {
/* 165 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   public AssertionIDRef createAssertionIDRef() throws SAMLException {
/* 168 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AssertionIDReference createAssertionIDReference(String id) throws SAMLException {
/* 172 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AssertionIDRef createAssertionIDRef(String id) throws SAMLException {
/* 176 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, String nameSpace, List values) throws SAMLException {
/* 180 */     return (Attribute)new Attribute(name, nameSpace, values);
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, List values) throws SAMLException {
/* 184 */     return (Attribute)new Attribute(name, values);
/*     */   }
/*     */   
/*     */   public AttributeDesignator createAttributeDesignator(String name, String nameSpace) throws SAMLException {
/* 188 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AttributeStatement createAttributeStatement(Subject subj, List attr) throws SAMLException {
/* 192 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AttributeStatement createAttributeStatement(List attr) throws SAMLException {
/* 196 */     return (AttributeStatement)new AttributeStatement(attr);
/*     */   }
/*     */   
/*     */   public AudienceRestrictionCondition createAudienceRestrictionCondition(List audience) throws SAMLException {
/* 200 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AudienceRestriction createAudienceRestriction(List audience) throws SAMLException {
/* 204 */     return (AudienceRestriction)new AudienceRestriction(audience);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationStatement createAuthenticationStatement(String authMethod, GregorianCalendar authInstant, Subject subject, SubjectLocality subjectLocality, List authorityBinding) throws SAMLException {
/* 211 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnStatement createAuthnStatement(GregorianCalendar authInstant, SubjectLocality subjectLocality, AuthnContext authnContext, String sessionIndex, GregorianCalendar sessionNotOnOrAfter) throws SAMLException {
/* 218 */     return (AuthnStatement)new AuthnStatement(authInstant, (SubjectLocality)subjectLocality, (AuthnContext)authnContext, sessionIndex, sessionNotOnOrAfter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorityBinding createAuthorityBinding(QName authKind, String location, String binding) throws SAMLException {
/* 227 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public AuthnContext createAuthnContext() throws SAMLException {
/* 231 */     return (AuthnContext)new AuthnContext();
/*     */   }
/*     */   
/*     */   public AuthnContext createAuthnContext(String authContextClassref, String authenticatingAuthority) throws SAMLException {
/* 235 */     return (AuthnContext)new AuthnContext(authContextClassref, authenticatingAuthority);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationDecisionStatement createAuthorizationDecisionStatement(Subject subject, String resource, String decision, List action, Evidence evidence) throws SAMLException {
/* 241 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnDecisionStatement createAuthnDecisionStatement(String resource, String decision, List action, Evidence evidence) throws SAMLException {
/* 247 */     return (AuthnDecisionStatement)new AuthzDecisionStatement(resource, decision, action, (Evidence)evidence);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions createConditions() throws SAMLException {
/* 256 */     return (Conditions)new Conditions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions createConditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List arc, List doNotCacheCnd) throws SAMLException {
/* 266 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions createConditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List ar, List oneTimeUse, List proxyRestriction) throws SAMLException {
/* 277 */     return (Conditions)new Conditions(notBefore, notOnOrAfter, condition, ar, oneTimeUse, proxyRestriction);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DoNotCacheCondition createDoNotCacheCondition() throws SAMLException {
/* 283 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public OneTimeUse createOneTimeUse() throws SAMLException {
/* 287 */     return (OneTimeUse)new OneTimeUse();
/*     */   }
/*     */   
/*     */   public Evidence createEvidence(List assertionIDRef, List assertion) throws SAMLException {
/* 291 */     return (Evidence)new Evidence(assertionIDRef, assertion);
/*     */   }
/*     */   
/*     */   public NameIdentifier createNameIdentifier(String name, String nameQualifier, String format) throws SAMLException {
/* 295 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public NameID createNameID(String name, String nameQualifier, String format) throws SAMLException {
/* 299 */     return (NameID)new NameID(name, nameQualifier, format);
/*     */   }
/*     */   
/*     */   public Subject createSubject(NameIdentifier nameIdentifier, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 303 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public Subject createSubject(NameID nameID, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 307 */     return (Subject)new Subject((NameID)nameID, (SubjectConfirmation)subjectConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(String confirmationMethod) throws SAMLException {
/* 313 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, String method) throws SAMLException {
/* 317 */     return (SubjectConfirmation)new SubjectConfirmation((NameID)nameID, method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, SubjectConfirmationData scd, KeyInfo keyInfo) throws SAMLException {
/* 324 */     SubjectConfirmation sc = new SubjectConfirmation();
/*     */     
/*     */     try {
/* 327 */       if (keyInfo != null) {
/* 328 */         sc.setKeyInfo((KeyInfoType)keyInfo);
/*     */       }
/* 330 */       if (scd != null) {
/* 331 */         sc.setSubjectConfirmationData(scd);
/*     */       }
/* 333 */     } catch (Exception ex) {
/*     */       
/* 335 */       throw new SAMLException(ex);
/*     */     } 
/* 337 */     sc.setConfirmationMethod(confirmationMethods);
/* 338 */     return (SubjectConfirmation)sc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, Element subjectConfirmationData, Element keyInfo) throws SAMLException {
/* 346 */     throw new UnsupportedOperationException("Not Supported for SAML2.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, SubjectConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 353 */     return (SubjectConfirmation)new SubjectConfirmation((NameID)nameID, (SubjectConfirmationData)subjectConfirmationData, confirmationMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, KeyInfoConfirmationData keyInfoConfirmationData, String confirmationMethod) throws SAMLException {
/* 363 */     return (SubjectConfirmation)new SubjectConfirmation((NameID)nameID, (KeyInfoConfirmationData)keyInfoConfirmationData, confirmationMethod);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, Element keyInfo) throws SAMLException {
/* 373 */     return (SubjectConfirmationData)new SubjectConfirmationData(address, inResponseTo, notBefore, notOnOrAfter, recipient, keyInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, KeyInfo keyInfo) throws SAMLException {
/* 381 */     SubjectConfirmationData subjectConfirmationData = new SubjectConfirmationData();
/* 382 */     subjectConfirmationData.setAddress(address);
/* 383 */     subjectConfirmationData.setInResponseTo(inResponseTo);
/* 384 */     if (notBefore != null) {
/* 385 */       subjectConfirmationData.setNotBefore(this.dataTypeFac.newXMLGregorianCalendar(notBefore));
/*     */     }
/*     */     
/* 388 */     if (notOnOrAfter != null) {
/* 389 */       subjectConfirmationData.setNotOnOrAfter(this.dataTypeFac.newXMLGregorianCalendar(notOnOrAfter));
/*     */     }
/*     */     
/* 392 */     subjectConfirmationData.setRecipient(recipient);
/*     */     
/* 394 */     if (keyInfo != null) {
/* 395 */       subjectConfirmationData.getContent().add(keyInfo);
/*     */     }
/* 397 */     return (SubjectConfirmationData)subjectConfirmationData;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfoConfirmationData createKeyInfoConfirmationData(Element keyInfo) throws SAMLException {
/* 402 */     return (KeyInfoConfirmationData)new KeyInfoConfirmationData(keyInfo);
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality() throws SAMLException {
/* 406 */     return (SubjectLocality)new SubjectLocality();
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality(String ipAddress, String dnsAddress) throws SAMLException {
/* 410 */     return (SubjectLocality)new SubjectLocality(ipAddress, dnsAddress);
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(XMLStreamReader reader) throws SAMLException {
/*     */     try {
/* 415 */       Element samlElement = SAMLUtil.createSAMLAssertion(reader);
/* 416 */       return (Assertion)Assertion.fromElement(samlElement);
/*     */     
/*     */     }
/* 419 */     catch (XWSSecurityException ex) {
/* 420 */       throw new SAMLException(ex);
/* 421 */     } catch (XMLStreamException ex) {
/* 422 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\impl\SAMLAssertion2_2FactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */