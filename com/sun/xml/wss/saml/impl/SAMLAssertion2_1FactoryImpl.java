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
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Action;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Advice;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Attribute;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AttributeDesignator;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AudienceRestrictionCondition;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AuthorityBinding;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.AuthorizationDecisionStatement;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Conditions;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.DoNotCacheCondition;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Evidence;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Subject;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.SubjectLocality;
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ 
/*     */ public class SAMLAssertion2_1FactoryImpl
/*     */   extends SAMLAssertionFactory
/*     */ {
/*     */   public Action createAction(Element actionElement) throws SAMLException {
/*  80 */     return (Action)new Action(actionElement);
/*     */   }
/*     */   
/*     */   public Action createAction(String action, String namespace) throws SAMLException {
/*  84 */     return (Action)new Action(action, namespace);
/*     */   }
/*     */   
/*     */   public Advice createAdvice(List assertionidreference, List assertion, List otherelement) throws SAMLException {
/*  88 */     return (Advice)new Advice(assertionidreference, assertion, otherelement);
/*     */   }
/*     */   
/*     */   public AnyType createAnyType() throws SAMLException {
/*  92 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(Element element) throws SAMLException {
/*  96 */     return (Assertion)Assertion.fromElement(element);
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
/* 107 */     return (Assertion)new Assertion(assertionID, issuer, issueInstant, (Conditions)conditions, (Advice)advice, statements);
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
/* 121 */     return (Assertion)new Assertion(assertionID, issuer, issueInstant, (Conditions)conditions, (Advice)advice, statements, jcc);
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
/*     */   public Assertion createAssertion(String ID, NameID issuer, GregorianCalendar issueInstant, Conditions conditions, Advice advice, Subject subject, List statements) throws SAMLException {
/* 138 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
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
/* 149 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AssertionIDReference createAssertionIDReference() throws SAMLException {
/* 153 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AssertionIDRef createAssertionIDRef() throws SAMLException {
/* 157 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AssertionIDReference createAssertionIDReference(String id) throws SAMLException {
/* 161 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AssertionIDRef createAssertionIDRef(String id) throws SAMLException {
/* 165 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, String nameSpace, List values) throws SAMLException {
/* 169 */     return (Attribute)new Attribute(name, nameSpace, values);
/*     */   }
/*     */   
/*     */   public Attribute createAttribute(String name, List values) throws SAMLException {
/* 173 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AttributeDesignator createAttributeDesignator(String name, String nameSpace) throws SAMLException {
/* 177 */     return (AttributeDesignator)new AttributeDesignator(name, nameSpace);
/*     */   }
/*     */   
/*     */   public AttributeStatement createAttributeStatement(Subject subj, List attr) throws SAMLException {
/* 181 */     return (AttributeStatement)new AttributeStatement((Subject)subj, attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public AttributeStatement createAttributeStatement(List attr) throws SAMLException {
/* 186 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AudienceRestrictionCondition createAudienceRestrictionCondition(List audience) throws SAMLException {
/* 190 */     return (AudienceRestrictionCondition)new AudienceRestrictionCondition(audience);
/*     */   }
/*     */   
/*     */   public AudienceRestriction createAudienceRestriction(List audience) throws SAMLException {
/* 194 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationStatement createAuthenticationStatement(String authMethod, GregorianCalendar authInstant, Subject subject, SubjectLocality subjectLocality, List authorityBinding) throws SAMLException {
/* 201 */     return (AuthenticationStatement)new AuthenticationStatement(authMethod, authInstant, (Subject)subject, (SubjectLocality)subjectLocality, authorityBinding);
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
/* 213 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AuthorityBinding createAuthorityBinding(QName authKind, String location, String binding) throws SAMLException {
/* 217 */     return (AuthorityBinding)new AuthorityBinding(authKind, location, binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public AuthnContext createAuthnContext() throws SAMLException {
/* 222 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public AuthnContext createAuthnContext(String authContextClassref, String authenticatingAuthority) throws SAMLException {
/* 226 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationDecisionStatement createAuthorizationDecisionStatement(Subject subject, String resource, String decision, List action, Evidence evidence) throws SAMLException {
/* 232 */     return (AuthorizationDecisionStatement)new AuthorizationDecisionStatement((Subject)subject, resource, decision, action, (Evidence)evidence);
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
/* 244 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   public Conditions createConditions() throws SAMLException {
/* 247 */     return (Conditions)new Conditions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions createConditions(GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, List condition, List arc, List doNotCacheCnd) throws SAMLException {
/* 257 */     return (Conditions)new Conditions(notBefore, notOnOrAfter, condition, arc, doNotCacheCnd);
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
/* 269 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public DoNotCacheCondition createDoNotCacheCondition() throws SAMLException {
/* 273 */     return (DoNotCacheCondition)new DoNotCacheCondition();
/*     */   }
/*     */   
/*     */   public OneTimeUse createOneTimeUse() throws SAMLException {
/* 277 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public Evidence createEvidence(List assertionIDRef, List assertion) throws SAMLException {
/* 281 */     return (Evidence)new Evidence(assertionIDRef, assertion);
/*     */   }
/*     */   
/*     */   public NameIdentifier createNameIdentifier(String name, String nameQualifier, String format) throws SAMLException {
/* 285 */     return (NameIdentifier)new NameIdentifier(name, nameQualifier, format);
/*     */   }
/*     */   
/*     */   public NameID createNameID(String name, String nameQualifier, String format) throws SAMLException {
/* 289 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public Subject createSubject(NameIdentifier nameIdentifier, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 293 */     return (Subject)new Subject((NameIdentifier)nameIdentifier, (SubjectConfirmation)subjectConfirmation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Subject createSubject(NameID nameID, SubjectConfirmation subjectConfirmation) throws SAMLException {
/* 299 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(String confirmationMethod) throws SAMLException {
/* 303 */     return (SubjectConfirmation)new SubjectConfirmation(confirmationMethod);
/*     */   }
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, String method) throws SAMLException {
/* 307 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, Element subjectConfirmationData, Element keyInfo) throws SAMLException {
/* 314 */     return (SubjectConfirmation)new SubjectConfirmation(confirmationMethods, subjectConfirmationData, keyInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(List confirmationMethods, SubjectConfirmationData scd, KeyInfo keyInfo) throws SAMLException {
/* 319 */     SubjectConfirmation sc = new SubjectConfirmation();
/*     */     
/*     */     try {
/* 322 */       if (keyInfo != null) {
/* 323 */         sc.setKeyInfo((KeyInfoType)keyInfo);
/*     */       }
/* 325 */       if (scd != null) {
/* 326 */         sc.setSubjectConfirmationData(scd);
/*     */       }
/* 328 */     } catch (Exception ex) {
/*     */       
/* 330 */       throw new SAMLException(ex);
/*     */     } 
/* 332 */     sc.setConfirmationMethod(confirmationMethods);
/* 333 */     return (SubjectConfirmation)sc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, SubjectConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 341 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmation createSubjectConfirmation(NameID nameID, KeyInfoConfirmationData subjectConfirmationData, String confirmationMethod) throws SAMLException {
/* 348 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, Element keyInfo) throws SAMLException {
/* 355 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationData createSubjectConfirmationData(String address, String inResponseTo, GregorianCalendar notBefore, GregorianCalendar notOnOrAfter, String recipient, KeyInfo keyInfo) throws SAMLException {
/* 362 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfoConfirmationData createKeyInfoConfirmationData(Element keyInfo) throws SAMLException {
/* 367 */     throw new UnsupportedOperationException("Not Supported for SAML1.1");
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality() throws SAMLException {
/* 371 */     return (SubjectLocality)new SubjectLocality();
/*     */   }
/*     */   
/*     */   public SubjectLocality createSubjectLocality(String ipAddress, String dnsAddress) throws SAMLException {
/* 375 */     return (SubjectLocality)new SubjectLocality(ipAddress, dnsAddress);
/*     */   }
/*     */   
/*     */   public Assertion createAssertion(XMLStreamReader reader) throws SAMLException {
/*     */     try {
/* 380 */       Element samlElement = SAMLUtil.createSAMLAssertion(reader);
/* 381 */       return (Assertion)Assertion.fromElement(samlElement);
/*     */     
/*     */     }
/* 384 */     catch (XWSSecurityException ex) {
/* 385 */       throw new SAMLException(ex);
/* 386 */     } catch (XMLStreamException ex) {
/* 387 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\impl\SAMLAssertion2_1FactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */