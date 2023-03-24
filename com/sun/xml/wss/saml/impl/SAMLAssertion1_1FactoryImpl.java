/*     */ package com.sun.xml.wss.saml.impl;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
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
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Action;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Advice;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AnyType;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AssertionIDReference;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Attribute;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AttributeDesignator;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AudienceRestrictionCondition;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.AuthorizationDecisionStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Conditions;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.DoNotCacheCondition;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Evidence;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Subject;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.SubjectLocality;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAMLAssertion1_1FactoryImpl
/*     */   extends SAMLAssertionFactory
/*     */ {
/*     */   public Action createAction(Element actionElement) throws SAMLException {
/*  77 */     return (Action)new Action(actionElement);
/*     */   }
/*     */   
/*     */   public Action createAction(String action, String namespace) throws SAMLException {
/*  81 */     return (Action)new Action(action, namespace);
/*     */   }
/*     */   
/*     */   public Advice createAdvice(List assertionidreference, List assertion, List otherelement) throws SAMLException {
/*  85 */     return (Advice)new Advice(assertionidreference, assertion, otherelement);
/*     */   }
/*     */   
/*     */   public AnyType createAnyType() throws SAMLException {
/*  89 */     return (AnyType)new AnyType();
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(Element element) throws SAMLException {
/*  93 */     return (Assertion)Assertion.fromElement(element);
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
/* 104 */     return (Assertion)new Assertion(assertionID, issuer, issueInstant, (Conditions)conditions, (Advice)advice, statements);
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
/*     */   public Assertion createAssertion(String assertionID, String issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, List statements, JAXBContext jcc) throws SAMLException {
/* 118 */     return (Assertion)new Assertion(assertionID, issuer, issueInstant, (Conditions)conditions, (Advice)advice, statements, jcc);
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
/*     */   public Assertion createAssertion(String ID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements) throws SAMLException {
/* 134 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Assertion createAssertion(String ID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements, JAXBContext jcc) throws SAMLException {
/* 145 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AssertionIDReference createAssertionIDReference() throws SAMLException {
/* 149 */     return (AssertionIDReference)new AssertionIDReference();
/*     */   }
/*     */   
/*     */   public AssertionIDRef createAssertionIDRef() throws SAMLException {
/* 153 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AssertionIDReference createAssertionIDReference(String id) throws SAMLException {
/* 157 */     return (AssertionIDReference)new AssertionIDReference(id);
/*     */   }
/*     */   
/*     */   public AssertionIDRef createAssertionIDRef(String id) throws SAMLException {
/* 161 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, String nameSpace, List values) throws SAMLException {
/* 165 */     return (Attribute)new Attribute(name, nameSpace, values);
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, List values) throws SAMLException {
/* 169 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AttributeDesignator createAttributeDesignator(String name, String nameSpace) throws SAMLException {
/* 173 */     return (AttributeDesignator)new AttributeDesignator(name, nameSpace);
/*     */   }
/*     */   
/*     */   public AttributeStatement createAttributeStatement(Subject subj, List attr) throws SAMLException {
/* 177 */     return (AttributeStatement)new AttributeStatement((Subject)subj, attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeStatement createAttributeStatement(List attr) throws SAMLException {
/* 182 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AudienceRestrictionCondition createAudienceRestrictionCondition(List audience) throws SAMLException {
/* 186 */     return (AudienceRestrictionCondition)new AudienceRestrictionCondition(audience);
/*     */   }
/*     */   
/*     */   public AudienceRestriction createAudienceRestriction(List audience) throws SAMLException {
/* 190 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationStatement createAuthenticationStatement(String authMethod, GregorianCalendar authInstant, Subject subject, SubjectLocality subjectLocality, List authorityBinding) throws SAMLException {
/* 197 */     return (AuthenticationStatement)new AuthenticationStatement(authMethod, authInstant, (Subject)subject, (SubjectLocality)subjectLocality, authorityBinding);
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
/*     */   public AuthnStatement createAuthnStatement(GregorianCalendar authInstant, SubjectLocality subjectLocality, AuthnContext authnContext, String sessionIndex, GregorianCalendar sessionNotOnOrAfter) throws SAMLException {
/* 209 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AuthorityBinding createAuthorityBinding(QName authKind, String location, String binding) throws SAMLException {
/* 213 */     return (AuthorityBinding)new AuthorityBinding(authKind, location, binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthnContext createAuthnContext() throws SAMLException {
/* 218 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public AuthnContext createAuthnContext(String authContextClassref, String authenticatingAuthority) throws SAMLException {
/* 222 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationDecisionStatement createAuthorizationDecisionStatement(Subject subject, String resource, String decision, List action, Evidence evidence) throws SAMLException {
/* 228 */     return (AuthorizationDecisionStatement)new AuthorizationDecisionStatement((Subject)subject, resource, decision, action, (Evidence)evidence);
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
/*     */   public AuthnDecisionStatement createAuthnDecisionStatement(String resource, String decision, List action, Evidence evidence) throws SAMLException {
/* 240 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public Conditions createConditions() throws SAMLException {
/* 244 */     return (Conditions)new Conditions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions createConditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List arc, List doNotCacheCnd) throws SAMLException {
/* 254 */     return (Conditions)new Conditions(notBefore, notOnOrAfter, condition, arc, doNotCacheCnd);
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
/*     */   public Conditions createConditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List ar, List oneTimeUse, List proxyRestriction) throws SAMLException {
/* 266 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public DoNotCacheCondition createDoNotCacheCondition() throws SAMLException {
/* 270 */     return (DoNotCacheCondition)new DoNotCacheCondition();
/*     */   }
/*     */   
/*     */   public OneTimeUse createOneTimeUse() throws SAMLException {
/* 274 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public Evidence createEvidence(List assertionIDRef, List assertion) throws SAMLException {
/* 278 */     return (Evidence)new Evidence(assertionIDRef, assertion);
/*     */   }
/*     */   
/*     */   public NameIdentifier createNameIdentifier(String name, String nameQualifier, String format) throws SAMLException {
/* 282 */     return (NameIdentifier)new NameIdentifier(name, nameQualifier, format);
/*     */   }
/*     */   
/*     */   public NameID createNameID(String name, String nameQualifier, String format) throws SAMLException {
/* 286 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public Subject createSubject(NameIdentifier nameIdentifier, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 290 */     return (Subject)new Subject((NameIdentifier)nameIdentifier, (SubjectConfirmation)subjectConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject createSubject(NameID nameID, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 296 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(String confirmationMethod) throws SAMLException {
/* 300 */     return (SubjectConfirmation)new SubjectConfirmation(confirmationMethod);
/*     */   }
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, String method) throws SAMLException {
/* 304 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, Element subjectConfirmationData, Element keyInfo) throws SAMLException {
/* 311 */     return (SubjectConfirmation)new SubjectConfirmation(confirmationMethods, subjectConfirmationData, keyInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, SubjectConfirmationData scd, KeyInfo keyInfo) throws SAMLException {
/* 316 */     SubjectConfirmation sc = new SubjectConfirmation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     return (SubjectConfirmation)sc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, SubjectConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 336 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, KeyInfoConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 343 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, Element keyInfo) throws SAMLException {
/* 350 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, KeyInfo keyInfo) throws SAMLException {
/* 356 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfoConfirmationData createKeyInfoConfirmationData(Element keyInfo) throws SAMLException {
/* 361 */     throw new UnsupportedOperationException("Not Supported for SAML1.0");
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality() throws SAMLException {
/* 365 */     return (SubjectLocality)new SubjectLocality();
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality(String ipAddress, String dnsAddress) throws SAMLException {
/* 369 */     return (SubjectLocality)new SubjectLocality(ipAddress, dnsAddress);
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(XMLStreamReader reader) throws SAMLException {
/* 373 */     throw new UnsupportedOperationException("Not Yet Supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\impl\SAMLAssertion1_1FactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */