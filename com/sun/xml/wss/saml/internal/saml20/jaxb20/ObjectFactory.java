/*     */ package com.sun.xml.wss.saml.internal.saml20.jaxb20;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
/*     */ import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
/*     */ import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  76 */   private static final QName _EncryptedAttribute_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAttribute");
/*  77 */   private static final QName _AuthzDecisionStatement_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthzDecisionStatement");
/*  78 */   private static final QName _Condition_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Condition");
/*  79 */   private static final QName _AuthenticatingAuthority_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthenticatingAuthority");
/*  80 */   private static final QName _AuthnContextClassRef_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef");
/*  81 */   private static final QName _AuthnContext_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContext");
/*  82 */   private static final QName _SubjectConfirmation_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation");
/*  83 */   private static final QName _AudienceRestriction_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AudienceRestriction");
/*  84 */   private static final QName _Advice_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Advice");
/*  85 */   private static final QName _Statement_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Statement");
/*  86 */   private static final QName _AttributeStatement_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeStatement");
/*  87 */   private static final QName _Assertion_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
/*  88 */   private static final QName _EncryptedID_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedID");
/*  89 */   private static final QName _AuthnContextDeclRef_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDeclRef");
/*  90 */   private static final QName _Attribute_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Attribute");
/*  91 */   private static final QName _OneTimeUse_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "OneTimeUse");
/*  92 */   private static final QName _SubjectLocality_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectLocality");
/*  93 */   private static final QName _AssertionURIRef_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionURIRef");
/*  94 */   private static final QName _EncryptedAssertion_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion");
/*  95 */   private static final QName _BaseID_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "BaseID");
/*  96 */   private static final QName _AssertionIDRef_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionIDRef");
/*  97 */   private static final QName _ProxyRestriction_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction");
/*  98 */   private static final QName _Evidence_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Evidence");
/*  99 */   private static final QName _SubjectConfirmationData_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData");
/* 100 */   private static final QName _Conditions_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Conditions");
/* 101 */   private static final QName _NameID_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "NameID");
/* 102 */   private static final QName _AttributeValue_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeValue");
/* 103 */   private static final QName _Audience_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Audience");
/* 104 */   private static final QName _AuthnContextDecl_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDecl");
/* 105 */   private static final QName _AuthnStatement_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement");
/* 106 */   private static final QName _Subject_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Subject");
/* 107 */   private static final QName _Action_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Action");
/* 108 */   private static final QName _Issuer_QNAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectType createSubjectType() {
/* 122 */     return new SubjectType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdviceType createAdviceType() {
/* 130 */     return new AdviceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthzDecisionStatementType createAuthzDecisionStatementType() {
/* 138 */     return new AuthzDecisionStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedElementType createEncryptedElementType() {
/* 146 */     return new EncryptedElementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionType createAssertionType() {
/* 154 */     return new AssertionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NameIDType createNameIDType() {
/* 162 */     return new NameIDType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectLocalityType createSubjectLocalityType() {
/* 170 */     return new SubjectLocalityType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationType createSubjectConfirmationType() {
/* 178 */     return new SubjectConfirmationType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnContextType createAuthnContextType() {
/* 186 */     return new AuthnContextType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EvidenceType createEvidenceType() {
/* 194 */     return new EvidenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationDataType createSubjectConfirmationDataType() {
/* 202 */     return new SubjectConfirmationDataType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionType createActionType() {
/* 210 */     return new ActionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeStatementType createAttributeStatementType() {
/* 218 */     return new AttributeStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProxyRestrictionType createProxyRestrictionType() {
/* 226 */     return new ProxyRestrictionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfoConfirmationDataType createKeyInfoConfirmationDataType() {
/* 234 */     return new KeyInfoConfirmationDataType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeType createAttributeType() {
/* 242 */     return new AttributeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudienceRestrictionType createAudienceRestrictionType() {
/* 250 */     return new AudienceRestrictionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthnStatementType createAuthnStatementType() {
/* 258 */     return new AuthnStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OneTimeUseType createOneTimeUseType() {
/* 266 */     return new OneTimeUseType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConditionsType createConditionsType() {
/* 274 */     return new ConditionsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "EncryptedAttribute")
/*     */   public JAXBElement<EncryptedElementType> createEncryptedAttribute(EncryptedElementType value) {
/* 283 */     return new JAXBElement<EncryptedElementType>(_EncryptedAttribute_QNAME, EncryptedElementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthzDecisionStatement")
/*     */   public JAXBElement<AuthzDecisionStatementType> createAuthzDecisionStatement(AuthzDecisionStatementType value) {
/* 292 */     return new JAXBElement<AuthzDecisionStatementType>(_AuthzDecisionStatement_QNAME, AuthzDecisionStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Condition")
/*     */   public JAXBElement<ConditionAbstractType> createCondition(ConditionAbstractType value) {
/* 301 */     return new JAXBElement<ConditionAbstractType>(_Condition_QNAME, ConditionAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthenticatingAuthority")
/*     */   public JAXBElement<String> createAuthenticatingAuthority(String value) {
/* 310 */     return new JAXBElement<String>(_AuthenticatingAuthority_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthnContextClassRef")
/*     */   public JAXBElement<String> createAuthnContextClassRef(String value) {
/* 319 */     return new JAXBElement<String>(_AuthnContextClassRef_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthnContext")
/*     */   public JAXBElement<AuthnContextType> createAuthnContext(AuthnContextType value) {
/* 328 */     return new JAXBElement<AuthnContextType>(_AuthnContext_QNAME, AuthnContextType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "SubjectConfirmation")
/*     */   public JAXBElement<SubjectConfirmationType> createSubjectConfirmation(SubjectConfirmationType value) {
/* 337 */     return new JAXBElement<SubjectConfirmationType>(_SubjectConfirmation_QNAME, SubjectConfirmationType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AudienceRestriction")
/*     */   public JAXBElement<AudienceRestrictionType> createAudienceRestriction(AudienceRestrictionType value) {
/* 346 */     return new JAXBElement<AudienceRestrictionType>(_AudienceRestriction_QNAME, AudienceRestrictionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Advice")
/*     */   public JAXBElement<AdviceType> createAdvice(AdviceType value) {
/* 355 */     return new JAXBElement<AdviceType>(_Advice_QNAME, AdviceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Statement")
/*     */   public JAXBElement<StatementAbstractType> createStatement(StatementAbstractType value) {
/* 364 */     return new JAXBElement<StatementAbstractType>(_Statement_QNAME, StatementAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AttributeStatement")
/*     */   public JAXBElement<AttributeStatementType> createAttributeStatement(AttributeStatementType value) {
/* 373 */     return new JAXBElement<AttributeStatementType>(_AttributeStatement_QNAME, AttributeStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Assertion")
/*     */   public JAXBElement<AssertionType> createAssertion(AssertionType value) {
/* 382 */     return new JAXBElement<AssertionType>(_Assertion_QNAME, AssertionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "EncryptedID")
/*     */   public JAXBElement<EncryptedElementType> createEncryptedID(EncryptedElementType value) {
/* 391 */     return new JAXBElement<EncryptedElementType>(_EncryptedID_QNAME, EncryptedElementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthnContextDeclRef")
/*     */   public JAXBElement<String> createAuthnContextDeclRef(String value) {
/* 400 */     return new JAXBElement<String>(_AuthnContextDeclRef_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Attribute")
/*     */   public JAXBElement<AttributeType> createAttribute(AttributeType value) {
/* 409 */     return new JAXBElement<AttributeType>(_Attribute_QNAME, AttributeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "OneTimeUse")
/*     */   public JAXBElement<OneTimeUseType> createOneTimeUse(OneTimeUseType value) {
/* 418 */     return new JAXBElement<OneTimeUseType>(_OneTimeUse_QNAME, OneTimeUseType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "SubjectLocality")
/*     */   public JAXBElement<SubjectLocalityType> createSubjectLocality(SubjectLocalityType value) {
/* 427 */     return new JAXBElement<SubjectLocalityType>(_SubjectLocality_QNAME, SubjectLocalityType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AssertionURIRef")
/*     */   public JAXBElement<String> createAssertionURIRef(String value) {
/* 436 */     return new JAXBElement<String>(_AssertionURIRef_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "EncryptedAssertion")
/*     */   public JAXBElement<EncryptedElementType> createEncryptedAssertion(EncryptedElementType value) {
/* 445 */     return new JAXBElement<EncryptedElementType>(_EncryptedAssertion_QNAME, EncryptedElementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "BaseID")
/*     */   public JAXBElement<BaseIDAbstractType> createBaseID(BaseIDAbstractType value) {
/* 454 */     return new JAXBElement<BaseIDAbstractType>(_BaseID_QNAME, BaseIDAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AssertionIDRef")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   public JAXBElement<String> createAssertionIDRef(String value) {
/* 464 */     return new JAXBElement<String>(_AssertionIDRef_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "ProxyRestriction")
/*     */   public JAXBElement<ProxyRestrictionType> createProxyRestriction(ProxyRestrictionType value) {
/* 473 */     return new JAXBElement<ProxyRestrictionType>(_ProxyRestriction_QNAME, ProxyRestrictionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Evidence")
/*     */   public JAXBElement<EvidenceType> createEvidence(EvidenceType value) {
/* 482 */     return new JAXBElement<EvidenceType>(_Evidence_QNAME, EvidenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "SubjectConfirmationData")
/*     */   public JAXBElement<SubjectConfirmationDataType> createSubjectConfirmationData(SubjectConfirmationDataType value) {
/* 491 */     return new JAXBElement<SubjectConfirmationDataType>(_SubjectConfirmationData_QNAME, SubjectConfirmationDataType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Conditions")
/*     */   public JAXBElement<ConditionsType> createConditions(ConditionsType value) {
/* 500 */     return new JAXBElement<ConditionsType>(_Conditions_QNAME, ConditionsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "NameID")
/*     */   public JAXBElement<NameIDType> createNameID(NameIDType value) {
/* 509 */     return new JAXBElement<NameIDType>(_NameID_QNAME, NameIDType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AttributeValue")
/*     */   public JAXBElement<Object> createAttributeValue(Object value) {
/* 518 */     return new JAXBElement(_AttributeValue_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Audience")
/*     */   public JAXBElement<String> createAudience(String value) {
/* 527 */     return new JAXBElement<String>(_Audience_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthnContextDecl")
/*     */   public JAXBElement<Object> createAuthnContextDecl(Object value) {
/* 536 */     return new JAXBElement(_AuthnContextDecl_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "AuthnStatement")
/*     */   public JAXBElement<AuthnStatementType> createAuthnStatement(AuthnStatementType value) {
/* 545 */     return new JAXBElement<AuthnStatementType>(_AuthnStatement_QNAME, AuthnStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Subject")
/*     */   public JAXBElement<SubjectType> createSubject(SubjectType value) {
/* 554 */     return new JAXBElement<SubjectType>(_Subject_QNAME, SubjectType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Action")
/*     */   public JAXBElement<ActionType> createAction(ActionType value) {
/* 563 */     return new JAXBElement<ActionType>(_Action_QNAME, ActionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:2.0:assertion", name = "Issuer")
/*     */   public JAXBElement<NameIDType> createIssuer(NameIDType value) {
/* 572 */     return new JAXBElement<NameIDType>(_Issuer_QNAME, NameIDType.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml20\jaxb20\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */