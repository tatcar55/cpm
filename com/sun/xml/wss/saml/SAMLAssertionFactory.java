/*     */ package com.sun.xml.wss.saml;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.saml.impl.SAMLAssertion1_1FactoryImpl;
/*     */ import com.sun.xml.wss.saml.impl.SAMLAssertion2_1FactoryImpl;
/*     */ import com.sun.xml.wss.saml.impl.SAMLAssertion2_2FactoryImpl;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SAMLAssertionFactory
/*     */ {
/*     */   public static final String SAML1_1 = "Saml1.1";
/*     */   public static final String SAML2_0 = "Saml2.0";
/*  76 */   public static String SAML_VER_CHECK = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SAMLAssertionFactory newInstance(String samlVersion) throws XWSSecurityException {
/*  91 */     if (samlVersion.intern() == "Saml1.1") {
/*  92 */       SAML_VER_CHECK = "Saml1.1";
/*  93 */       if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") != null)
/*  94 */         return (SAMLAssertionFactory)new SAMLAssertion1_1FactoryImpl(); 
/*  95 */       return (SAMLAssertionFactory)new SAMLAssertion2_1FactoryImpl();
/*  96 */     }  if (samlVersion.intern() == "Saml2.0" && System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/*  97 */       SAML_VER_CHECK = "Saml2.0";
/*  98 */       return (SAMLAssertionFactory)new SAMLAssertion2_2FactoryImpl();
/*     */     } 
/* 100 */     throw new XWSSecurityException("Unsupported SAML Version");
/*     */   }
/*     */   
/*     */   public abstract Action createAction(String paramString1, String paramString2) throws SAMLException;
/*     */   
/*     */   public abstract Advice createAdvice(List paramList1, List paramList2, List paramList3) throws SAMLException;
/*     */   
/*     */   public abstract AnyType createAnyType() throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(String paramString1, String paramString2, GregorianCalendar paramGregorianCalendar, Conditions paramConditions, Advice paramAdvice, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(String paramString1, String paramString2, GregorianCalendar paramGregorianCalendar, Conditions paramConditions, Advice paramAdvice, List paramList, JAXBContext paramJAXBContext) throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(String paramString, NameID paramNameID, GregorianCalendar paramGregorianCalendar, Conditions paramConditions, Advice paramAdvice, Subject paramSubject, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(String paramString, NameID paramNameID, GregorianCalendar paramGregorianCalendar, Conditions paramConditions, Advice paramAdvice, Subject paramSubject, List paramList, JAXBContext paramJAXBContext) throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(Element paramElement) throws SAMLException;
/*     */   
/*     */   public abstract Assertion createAssertion(XMLStreamReader paramXMLStreamReader) throws SAMLException;
/*     */   
/*     */   public abstract AssertionIDReference createAssertionIDReference() throws SAMLException;
/*     */   
/*     */   public abstract AssertionIDRef createAssertionIDRef() throws SAMLException;
/*     */   
/*     */   public abstract AssertionIDReference createAssertionIDReference(String paramString) throws SAMLException;
/*     */   
/*     */   public abstract AssertionIDRef createAssertionIDRef(String paramString) throws SAMLException;
/*     */   
/*     */   public abstract Attribute createAttribute(String paramString1, String paramString2, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract Attribute createAttribute(String paramString, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AttributeDesignator createAttributeDesignator(String paramString1, String paramString2) throws SAMLException;
/*     */   
/*     */   public abstract AttributeStatement createAttributeStatement(Subject paramSubject, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AttributeStatement createAttributeStatement(List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AudienceRestrictionCondition createAudienceRestrictionCondition(List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AudienceRestriction createAudienceRestriction(List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AuthenticationStatement createAuthenticationStatement(String paramString, GregorianCalendar paramGregorianCalendar, Subject paramSubject, SubjectLocality paramSubjectLocality, List paramList) throws SAMLException;
/*     */   
/*     */   public abstract AuthnStatement createAuthnStatement(GregorianCalendar paramGregorianCalendar1, SubjectLocality paramSubjectLocality, AuthnContext paramAuthnContext, String paramString, GregorianCalendar paramGregorianCalendar2) throws SAMLException;
/*     */   
/*     */   public abstract AuthorityBinding createAuthorityBinding(QName paramQName, String paramString1, String paramString2) throws SAMLException;
/*     */   
/*     */   public abstract AuthnContext createAuthnContext() throws SAMLException;
/*     */   
/*     */   public abstract AuthnContext createAuthnContext(String paramString1, String paramString2) throws SAMLException;
/*     */   
/*     */   public abstract AuthorizationDecisionStatement createAuthorizationDecisionStatement(Subject paramSubject, String paramString1, String paramString2, List paramList, Evidence paramEvidence) throws SAMLException;
/*     */   
/*     */   public abstract AuthnDecisionStatement createAuthnDecisionStatement(String paramString1, String paramString2, List paramList, Evidence paramEvidence) throws SAMLException;
/*     */   
/*     */   public abstract Conditions createConditions() throws SAMLException;
/*     */   
/*     */   public abstract Conditions createConditions(GregorianCalendar paramGregorianCalendar1, GregorianCalendar paramGregorianCalendar2, List paramList1, List paramList2, List paramList3) throws SAMLException;
/*     */   
/*     */   public abstract Conditions createConditions(GregorianCalendar paramGregorianCalendar1, GregorianCalendar paramGregorianCalendar2, List paramList1, List paramList2, List paramList3, List paramList4) throws SAMLException;
/*     */   
/*     */   public abstract DoNotCacheCondition createDoNotCacheCondition() throws SAMLException;
/*     */   
/*     */   public abstract OneTimeUse createOneTimeUse() throws SAMLException;
/*     */   
/*     */   public abstract Evidence createEvidence(List paramList1, List paramList2) throws SAMLException;
/*     */   
/*     */   public abstract NameIdentifier createNameIdentifier(String paramString1, String paramString2, String paramString3) throws SAMLException;
/*     */   
/*     */   public abstract NameID createNameID(String paramString1, String paramString2, String paramString3) throws SAMLException;
/*     */   
/*     */   public abstract Subject createSubject(NameIdentifier paramNameIdentifier, SubjectConfirmation paramSubjectConfirmation) throws SAMLException;
/*     */   
/*     */   public abstract Subject createSubject(NameID paramNameID, SubjectConfirmation paramSubjectConfirmation) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(String paramString) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(NameID paramNameID, String paramString) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(List paramList, SubjectConfirmationData paramSubjectConfirmationData, KeyInfo paramKeyInfo) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(List paramList, Element paramElement1, Element paramElement2) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(NameID paramNameID, SubjectConfirmationData paramSubjectConfirmationData, String paramString) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmation createSubjectConfirmation(NameID paramNameID, KeyInfoConfirmationData paramKeyInfoConfirmationData, String paramString) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmationData createSubjectConfirmationData(String paramString1, String paramString2, GregorianCalendar paramGregorianCalendar1, GregorianCalendar paramGregorianCalendar2, String paramString3, Element paramElement) throws SAMLException;
/*     */   
/*     */   public abstract SubjectConfirmationData createSubjectConfirmationData(String paramString1, String paramString2, GregorianCalendar paramGregorianCalendar1, GregorianCalendar paramGregorianCalendar2, String paramString3, KeyInfo paramKeyInfo) throws SAMLException;
/*     */   
/*     */   public abstract KeyInfoConfirmationData createKeyInfoConfirmationData(Element paramElement) throws SAMLException;
/*     */   
/*     */   public abstract SubjectLocality createSubjectLocality() throws SAMLException;
/*     */   
/*     */   public abstract SubjectLocality createSubjectLocality(String paramString1, String paramString2) throws SAMLException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\SAMLAssertionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */