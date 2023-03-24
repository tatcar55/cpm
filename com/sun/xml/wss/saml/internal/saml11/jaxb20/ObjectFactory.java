/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb20;
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
/*  76 */   private static final QName _DoNotCacheCondition_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition");
/*  77 */   private static final QName _SubjectStatement_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement");
/*  78 */   private static final QName _AttributeDesignator_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator");
/*  79 */   private static final QName _Subject_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Subject");
/*  80 */   private static final QName _AuthorizationDecisionStatement_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement");
/*  81 */   private static final QName _Evidence_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence");
/*  82 */   private static final QName _SubjectConfirmationData_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData");
/*  83 */   private static final QName _AssertionIDReference_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference");
/*  84 */   private static final QName _Attribute_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute");
/*  85 */   private static final QName _Audience_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Audience");
/*  86 */   private static final QName _SubjectLocality_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality");
/*  87 */   private static final QName _AudienceRestrictionCondition_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition");
/*  88 */   private static final QName _ConfirmationMethod_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
/*  89 */   private static final QName _AttributeValue_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue");
/*  90 */   private static final QName _Condition_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Condition");
/*  91 */   private static final QName _Advice_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Advice");
/*  92 */   private static final QName _Statement_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Statement");
/*  93 */   private static final QName _Conditions_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions");
/*  94 */   private static final QName _SubjectConfirmation_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
/*  95 */   private static final QName _AuthenticationStatement_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement");
/*  96 */   private static final QName _AuthorityBinding_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding");
/*  97 */   private static final QName _NameIdentifier_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier");
/*  98 */   private static final QName _AttributeStatement_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement");
/*  99 */   private static final QName _Assertion_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/* 100 */   private static final QName _Action_QNAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Action");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NameIdentifierType createNameIdentifierType() {
/* 114 */     return new NameIdentifierType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeDesignatorType createAttributeDesignatorType() {
/* 122 */     return new AttributeDesignatorType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticationStatementType createAuthenticationStatementType() {
/* 130 */     return new AuthenticationStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AssertionType createAssertionType() {
/* 138 */     return new AssertionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorizationDecisionStatementType createAuthorizationDecisionStatementType() {
/* 146 */     return new AuthorizationDecisionStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectConfirmationType createSubjectConfirmationType() {
/* 154 */     return new SubjectConfirmationType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DoNotCacheConditionType createDoNotCacheConditionType() {
/* 162 */     return new DoNotCacheConditionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudienceRestrictionConditionType createAudienceRestrictionConditionType() {
/* 170 */     return new AudienceRestrictionConditionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionType createActionType() {
/* 178 */     return new ActionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectLocalityType createSubjectLocalityType() {
/* 186 */     return new SubjectLocalityType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConditionsType createConditionsType() {
/* 194 */     return new ConditionsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubjectType createSubjectType() {
/* 202 */     return new SubjectType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeStatementType createAttributeStatementType() {
/* 210 */     return new AttributeStatementType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AdviceType createAdviceType() {
/* 218 */     return new AdviceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthorityBindingType createAuthorityBindingType() {
/* 226 */     return new AuthorityBindingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeType createAttributeType() {
/* 234 */     return new AttributeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EvidenceType createEvidenceType() {
/* 242 */     return new EvidenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "DoNotCacheCondition")
/*     */   public JAXBElement<DoNotCacheConditionType> createDoNotCacheCondition(DoNotCacheConditionType value) {
/* 251 */     return new JAXBElement<DoNotCacheConditionType>(_DoNotCacheCondition_QNAME, DoNotCacheConditionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "SubjectStatement")
/*     */   public JAXBElement<SubjectStatementAbstractType> createSubjectStatement(SubjectStatementAbstractType value) {
/* 260 */     return new JAXBElement<SubjectStatementAbstractType>(_SubjectStatement_QNAME, SubjectStatementAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AttributeDesignator")
/*     */   public JAXBElement<AttributeDesignatorType> createAttributeDesignator(AttributeDesignatorType value) {
/* 269 */     return new JAXBElement<AttributeDesignatorType>(_AttributeDesignator_QNAME, AttributeDesignatorType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Subject")
/*     */   public JAXBElement<SubjectType> createSubject(SubjectType value) {
/* 278 */     return new JAXBElement<SubjectType>(_Subject_QNAME, SubjectType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AuthorizationDecisionStatement")
/*     */   public JAXBElement<AuthorizationDecisionStatementType> createAuthorizationDecisionStatement(AuthorizationDecisionStatementType value) {
/* 287 */     return new JAXBElement<AuthorizationDecisionStatementType>(_AuthorizationDecisionStatement_QNAME, AuthorizationDecisionStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Evidence")
/*     */   public JAXBElement<EvidenceType> createEvidence(EvidenceType value) {
/* 296 */     return new JAXBElement<EvidenceType>(_Evidence_QNAME, EvidenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "SubjectConfirmationData")
/*     */   public JAXBElement<Object> createSubjectConfirmationData(Object value) {
/* 305 */     return new JAXBElement(_SubjectConfirmationData_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AssertionIDReference")
/*     */   @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
/*     */   public JAXBElement<String> createAssertionIDReference(String value) {
/* 315 */     return new JAXBElement<String>(_AssertionIDReference_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Attribute")
/*     */   public JAXBElement<AttributeType> createAttribute(AttributeType value) {
/* 324 */     return new JAXBElement<AttributeType>(_Attribute_QNAME, AttributeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Audience")
/*     */   public JAXBElement<String> createAudience(String value) {
/* 333 */     return new JAXBElement<String>(_Audience_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "SubjectLocality")
/*     */   public JAXBElement<SubjectLocalityType> createSubjectLocality(SubjectLocalityType value) {
/* 342 */     return new JAXBElement<SubjectLocalityType>(_SubjectLocality_QNAME, SubjectLocalityType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AudienceRestrictionCondition")
/*     */   public JAXBElement<AudienceRestrictionConditionType> createAudienceRestrictionCondition(AudienceRestrictionConditionType value) {
/* 351 */     return new JAXBElement<AudienceRestrictionConditionType>(_AudienceRestrictionCondition_QNAME, AudienceRestrictionConditionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "ConfirmationMethod")
/*     */   public JAXBElement<String> createConfirmationMethod(String value) {
/* 360 */     return new JAXBElement<String>(_ConfirmationMethod_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AttributeValue")
/*     */   public JAXBElement<Object> createAttributeValue(Object value) {
/* 369 */     return new JAXBElement(_AttributeValue_QNAME, Object.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Condition")
/*     */   public JAXBElement<ConditionAbstractType> createCondition(ConditionAbstractType value) {
/* 378 */     return new JAXBElement<ConditionAbstractType>(_Condition_QNAME, ConditionAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Advice")
/*     */   public JAXBElement<AdviceType> createAdvice(AdviceType value) {
/* 387 */     return new JAXBElement<AdviceType>(_Advice_QNAME, AdviceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Statement")
/*     */   public JAXBElement<StatementAbstractType> createStatement(StatementAbstractType value) {
/* 396 */     return new JAXBElement<StatementAbstractType>(_Statement_QNAME, StatementAbstractType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Conditions")
/*     */   public JAXBElement<ConditionsType> createConditions(ConditionsType value) {
/* 405 */     return new JAXBElement<ConditionsType>(_Conditions_QNAME, ConditionsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "SubjectConfirmation")
/*     */   public JAXBElement<SubjectConfirmationType> createSubjectConfirmation(SubjectConfirmationType value) {
/* 414 */     return new JAXBElement<SubjectConfirmationType>(_SubjectConfirmation_QNAME, SubjectConfirmationType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AuthenticationStatement")
/*     */   public JAXBElement<AuthenticationStatementType> createAuthenticationStatement(AuthenticationStatementType value) {
/* 423 */     return new JAXBElement<AuthenticationStatementType>(_AuthenticationStatement_QNAME, AuthenticationStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AuthorityBinding")
/*     */   public JAXBElement<AuthorityBindingType> createAuthorityBinding(AuthorityBindingType value) {
/* 432 */     return new JAXBElement<AuthorityBindingType>(_AuthorityBinding_QNAME, AuthorityBindingType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "NameIdentifier")
/*     */   public JAXBElement<NameIdentifierType> createNameIdentifier(NameIdentifierType value) {
/* 441 */     return new JAXBElement<NameIdentifierType>(_NameIdentifier_QNAME, NameIdentifierType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "AttributeStatement")
/*     */   public JAXBElement<AttributeStatementType> createAttributeStatement(AttributeStatementType value) {
/* 450 */     return new JAXBElement<AttributeStatementType>(_AttributeStatement_QNAME, AttributeStatementType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Assertion")
/*     */   public JAXBElement<AssertionType> createAssertion(AssertionType value) {
/* 459 */     return new JAXBElement<AssertionType>(_Assertion_QNAME, AssertionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "urn:oasis:names:tc:SAML:1.0:assertion", name = "Action")
/*     */   public JAXBElement<ActionType> createAction(ActionType value) {
/* 468 */     return new JAXBElement<ActionType>(_Action_QNAME, ActionType.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb20\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */