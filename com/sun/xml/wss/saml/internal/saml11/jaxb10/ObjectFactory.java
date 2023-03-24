/*      */ package com.sun.xml.wss.saml.internal.saml11.jaxb10;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeDesignatorImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConfirmationMethodImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DSAKeyValueImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestValueImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyNameImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyValueTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.MgmtDataImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectStatementAbstractTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl;
/*      */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime.GrammarInfo;
/*      */ import java.math.BigInteger;
/*      */ import java.util.HashMap;
/*      */ import javax.xml.bind.JAXBException;
/*      */ import javax.xml.bind.PropertyException;
/*      */ import javax.xml.namespace.QName;
/*      */ 
/*      */ public class ObjectFactory extends DefaultJAXBContextImpl {
/*   30 */   private static HashMap defaultImplementations = new HashMap(135, 0.75F);
/*   31 */   private static HashMap rootTagMap = new HashMap();
/*   32 */   public static final GrammarInfo grammarInfo = (GrammarInfo)new GrammarInfoImpl(rootTagMap, defaultImplementations, ObjectFactory.class);
/*   33 */   public static final Class version = JAXBVersion.class;
/*      */   
/*      */   static {
/*   36 */     defaultImplementations.put(X509DataType.X509IssuerSerial.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl.X509IssuerSerialImpl");
/*   37 */     defaultImplementations.put(PGPDataType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.PGPDataTypeImpl");
/*   38 */     defaultImplementations.put(AuthenticationStatementType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementTypeImpl");
/*   39 */     defaultImplementations.put(X509DataType.X509SubjectName.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl.X509SubjectNameImpl");
/*   40 */     defaultImplementations.put(AuthorizationDecisionStatementType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementTypeImpl");
/*   41 */     defaultImplementations.put(AnyType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AnyTypeImpl");
/*   42 */     defaultImplementations.put(SignatureMethodType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodTypeImpl");
/*   43 */     defaultImplementations.put(Advice.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceImpl");
/*   44 */     defaultImplementations.put(StatementAbstractType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.StatementAbstractTypeImpl");
/*   45 */     defaultImplementations.put(RetrievalMethodType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RetrievalMethodTypeImpl");
/*   46 */     defaultImplementations.put(X509IssuerSerialType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509IssuerSerialTypeImpl");
/*   47 */     defaultImplementations.put(SignatureType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureTypeImpl");
/*   48 */     defaultImplementations.put(ReferenceType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceTypeImpl");
/*   49 */     defaultImplementations.put(MgmtData.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.MgmtDataImpl");
/*   50 */     defaultImplementations.put(TransformType.XPath.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformTypeImpl.XPathImpl");
/*   51 */     defaultImplementations.put(ManifestType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ManifestTypeImpl");
/*   52 */     defaultImplementations.put(ActionType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionTypeImpl");
/*   53 */     defaultImplementations.put(KeyInfoType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoTypeImpl");
/*   54 */     defaultImplementations.put(Evidence.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceImpl");
/*   55 */     defaultImplementations.put(AssertionType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionTypeImpl");
/*   56 */     defaultImplementations.put(AttributeStatement.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementImpl");
/*   57 */     defaultImplementations.put(DigestMethodType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestMethodTypeImpl");
/*   58 */     defaultImplementations.put(Subject.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectImpl");
/*   59 */     defaultImplementations.put(SPKIDataType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataTypeImpl");
/*   60 */     defaultImplementations.put(DigestMethod.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestMethodImpl");
/*   61 */     defaultImplementations.put(RSAKeyValueType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RSAKeyValueTypeImpl");
/*   62 */     defaultImplementations.put(Action.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ActionImpl");
/*   63 */     defaultImplementations.put(SignedInfoType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoTypeImpl");
/*   64 */     defaultImplementations.put(KeyInfo.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyInfoImpl");
/*   65 */     defaultImplementations.put(SubjectConfirmationType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationTypeImpl");
/*   66 */     defaultImplementations.put(SPKIDataType.SPKISexp.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataTypeImpl.SPKISexpImpl");
/*   67 */     defaultImplementations.put(SubjectStatementAbstractType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectStatementAbstractTypeImpl");
/*   68 */     defaultImplementations.put(Audience.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceImpl");
/*   69 */     defaultImplementations.put(X509DataType.X509Certificate.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl.X509CertificateImpl");
/*   70 */     defaultImplementations.put(SignatureMethod.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodImpl");
/*   71 */     defaultImplementations.put(DigestValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DigestValueImpl");
/*   72 */     defaultImplementations.put(Reference.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ReferenceImpl");
/*   73 */     defaultImplementations.put(TransformsType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsTypeImpl");
/*   74 */     defaultImplementations.put(AttributeType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeTypeImpl");
/*   75 */     defaultImplementations.put(AuthenticationStatement.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthenticationStatementImpl");
/*   76 */     defaultImplementations.put(KeyName.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyNameImpl");
/*   77 */     defaultImplementations.put(TransformType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformTypeImpl");
/*   78 */     defaultImplementations.put(DSAKeyValueType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DSAKeyValueTypeImpl");
/*   79 */     defaultImplementations.put(SPKIData.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SPKIDataImpl");
/*   80 */     defaultImplementations.put(AudienceRestrictionCondition.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionImpl");
/*   81 */     defaultImplementations.put(X509DataType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl");
/*   82 */     defaultImplementations.put(Assertion.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl");
/*   83 */     defaultImplementations.put(Signature.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureImpl");
/*   84 */     defaultImplementations.put(RetrievalMethod.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RetrievalMethodImpl");
/*   85 */     defaultImplementations.put(SubjectConfirmationData.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationDataImpl");
/*   86 */     defaultImplementations.put(AuthorityBindingType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingTypeImpl");
/*   87 */     defaultImplementations.put(CanonicalizationMethod.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.CanonicalizationMethodImpl");
/*   88 */     defaultImplementations.put(Transform.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformImpl");
/*   89 */     defaultImplementations.put(ObjectType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectTypeImpl");
/*   90 */     defaultImplementations.put(X509Data.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataImpl");
/*   91 */     defaultImplementations.put(Condition.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionImpl");
/*   92 */     defaultImplementations.put(SignatureProperties.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertiesImpl");
/*   93 */     defaultImplementations.put(AttributeDesignator.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeDesignatorImpl");
/*   94 */     defaultImplementations.put(AssertionIDReference.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionIDReferenceImpl");
/*   95 */     defaultImplementations.put(RSAKeyValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.RSAKeyValueImpl");
/*   96 */     defaultImplementations.put(AdviceType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AdviceTypeImpl");
/*   97 */     defaultImplementations.put(Conditions.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsImpl");
/*   98 */     defaultImplementations.put(KeyValueType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyValueTypeImpl");
/*   99 */     defaultImplementations.put(SignatureMethodType.HMACOutputLength.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureMethodTypeImpl.HMACOutputLengthImpl");
/*  100 */     defaultImplementations.put(SubjectLocality.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityImpl");
/*  101 */     defaultImplementations.put(DoNotCacheCondition.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionImpl");
/*  102 */     defaultImplementations.put(X509DataType.X509SKI.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl.X509SKIImpl");
/*  103 */     defaultImplementations.put(ConditionAbstractType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionAbstractTypeImpl");
/*  104 */     defaultImplementations.put(AttributeStatementType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeStatementTypeImpl");
/*  105 */     defaultImplementations.put(NameIdentifierType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierTypeImpl");
/*  106 */     defaultImplementations.put(SignaturePropertiesType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertiesTypeImpl");
/*  107 */     defaultImplementations.put(AttributeDesignatorType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeDesignatorTypeImpl");
/*  108 */     defaultImplementations.put(SignatureValueType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueTypeImpl");
/*  109 */     defaultImplementations.put(Object.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ObjectImpl");
/*  110 */     defaultImplementations.put(ConditionsType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConditionsTypeImpl");
/*  111 */     defaultImplementations.put(SubjectStatement.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectStatementImpl");
/*  112 */     defaultImplementations.put(EvidenceType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.EvidenceTypeImpl");
/*  113 */     defaultImplementations.put(SubjectType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectTypeImpl");
/*  114 */     defaultImplementations.put(DoNotCacheConditionType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DoNotCacheConditionTypeImpl");
/*  115 */     defaultImplementations.put(SignedInfo.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignedInfoImpl");
/*  116 */     defaultImplementations.put(SubjectConfirmation.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectConfirmationImpl");
/*  117 */     defaultImplementations.put(Transforms.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.TransformsImpl");
/*  118 */     defaultImplementations.put(X509DataType.X509CRL.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.X509DataTypeImpl.X509CRLImpl");
/*  119 */     defaultImplementations.put(SignatureProperty.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyImpl");
/*  120 */     defaultImplementations.put(SignaturePropertyType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignaturePropertyTypeImpl");
/*  121 */     defaultImplementations.put(CanonicalizationMethodType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.CanonicalizationMethodTypeImpl");
/*  122 */     defaultImplementations.put(ConfirmationMethod.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ConfirmationMethodImpl");
/*  123 */     defaultImplementations.put(KeyValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.KeyValueImpl");
/*  124 */     defaultImplementations.put(DSAKeyValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.DSAKeyValueImpl");
/*  125 */     defaultImplementations.put(SignatureValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SignatureValueImpl");
/*  126 */     defaultImplementations.put(NameIdentifier.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.NameIdentifierImpl");
/*  127 */     defaultImplementations.put(Attribute.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeImpl");
/*  128 */     defaultImplementations.put(SubjectLocalityType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.SubjectLocalityTypeImpl");
/*  129 */     defaultImplementations.put(PGPData.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.PGPDataImpl");
/*  130 */     defaultImplementations.put(Statement.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.StatementImpl");
/*  131 */     defaultImplementations.put(AttributeValue.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AttributeValueImpl");
/*  132 */     defaultImplementations.put(AuthorizationDecisionStatement.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorizationDecisionStatementImpl");
/*  133 */     defaultImplementations.put(AuthorityBinding.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AuthorityBindingImpl");
/*  134 */     defaultImplementations.put(AudienceRestrictionConditionType.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AudienceRestrictionConditionTypeImpl");
/*  135 */     defaultImplementations.put(Manifest.class, "com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.ManifestImpl");
/*  136 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod"), SignatureMethod.class);
/*  137 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "DoNotCacheCondition"), DoNotCacheCondition.class);
/*  138 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectStatement"), SubjectStatement.class);
/*  139 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeDesignator"), AttributeDesignator.class);
/*  140 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName"), KeyName.class);
/*  141 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms"), Transforms.class);
/*  142 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Subject"), Subject.class);
/*  143 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data"), X509Data.class);
/*  144 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Manifest"), Manifest.class);
/*  145 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo"), KeyInfo.class);
/*  146 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Reference"), Reference.class);
/*  147 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorizationDecisionStatement"), AuthorizationDecisionStatement.class);
/*  148 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Evidence"), Evidence.class);
/*  149 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmationData"), SubjectConfirmationData.class);
/*  150 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AssertionIDReference"), AssertionIDReference.class);
/*  151 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod"), RetrievalMethod.class);
/*  152 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureValue"), SignatureValue.class);
/*  153 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue"), KeyValue.class);
/*  154 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Signature"), Signature.class);
/*  155 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo"), SignedInfo.class);
/*  156 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty"), SignatureProperty.class);
/*  157 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperties"), SignatureProperties.class);
/*  158 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute"), Attribute.class);
/*  159 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Audience"), Audience.class);
/*  160 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AudienceRestrictionCondition"), AudienceRestrictionCondition.class);
/*  161 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectLocality"), SubjectLocality.class);
/*  162 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Object"), Object.class);
/*  163 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue"), RSAKeyValue.class);
/*  164 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData"), SPKIData.class);
/*  165 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod"), ConfirmationMethod.class);
/*  166 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod"), DigestMethod.class);
/*  167 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue"), AttributeValue.class);
/*  168 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData"), PGPData.class);
/*  169 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Condition"), Condition.class);
/*  170 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Advice"), Advice.class);
/*  171 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Statement"), Statement.class);
/*  172 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "Transform"), Transform.class);
/*  173 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "MgmtData"), MgmtData.class);
/*  174 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Conditions"), Conditions.class);
/*  175 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement"), AuthenticationStatement.class);
/*  176 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation"), SubjectConfirmation.class);
/*  177 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding"), AuthorityBinding.class);
/*  178 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue"), DSAKeyValue.class);
/*  179 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "NameIdentifier"), NameIdentifier.class);
/*  180 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeStatement"), AttributeStatement.class);
/*  181 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion"), Assertion.class);
/*  182 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue"), DigestValue.class);
/*  183 */     rootTagMap.put(new QName("urn:oasis:names:tc:SAML:1.0:assertion", "Action"), Action.class);
/*  184 */     rootTagMap.put(new QName("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod"), CanonicalizationMethod.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectFactory() {
/*  192 */     super(grammarInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public java.lang.Object newInstance(Class javaContentInterface) throws JAXBException {
/*  208 */     return super.newInstance(javaContentInterface);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public java.lang.Object getProperty(String name) throws PropertyException {
/*  227 */     return super.getProperty(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProperty(String name, java.lang.Object value) throws PropertyException {
/*  246 */     super.setProperty(name, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509IssuerSerial createX509DataTypeX509IssuerSerial() throws JAXBException {
/*  258 */     return (X509DataType.X509IssuerSerial)new X509DataTypeImpl.X509IssuerSerialImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PGPDataType createPGPDataType() throws JAXBException {
/*  270 */     return (PGPDataType)new PGPDataTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthenticationStatementType createAuthenticationStatementType() throws JAXBException {
/*  282 */     return (AuthenticationStatementType)new AuthenticationStatementTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509SubjectName createX509DataTypeX509SubjectName() throws JAXBException {
/*  294 */     return (X509DataType.X509SubjectName)new X509DataTypeImpl.X509SubjectNameImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509SubjectName createX509DataTypeX509SubjectName(String value) throws JAXBException {
/*  306 */     return (X509DataType.X509SubjectName)new X509DataTypeImpl.X509SubjectNameImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthorizationDecisionStatementType createAuthorizationDecisionStatementType() throws JAXBException {
/*  318 */     return (AuthorizationDecisionStatementType)new AuthorizationDecisionStatementTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnyType createAnyType() throws JAXBException {
/*  330 */     return (AnyType)new AnyTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureMethodType createSignatureMethodType() throws JAXBException {
/*  342 */     return (SignatureMethodType)new SignatureMethodTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Advice createAdvice() throws JAXBException {
/*  354 */     return (Advice)new AdviceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatementAbstractType createStatementAbstractType() throws JAXBException {
/*  366 */     return (StatementAbstractType)new StatementAbstractTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RetrievalMethodType createRetrievalMethodType() throws JAXBException {
/*  378 */     return (RetrievalMethodType)new RetrievalMethodTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509IssuerSerialType createX509IssuerSerialType() throws JAXBException {
/*  390 */     return (X509IssuerSerialType)new X509IssuerSerialTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureType createSignatureType() throws JAXBException {
/*  402 */     return (SignatureType)new SignatureTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ReferenceType createReferenceType() throws JAXBException {
/*  414 */     return (ReferenceType)new ReferenceTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MgmtData createMgmtData() throws JAXBException {
/*  426 */     return (MgmtData)new MgmtDataImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MgmtData createMgmtData(String value) throws JAXBException {
/*  438 */     return (MgmtData)new MgmtDataImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformType.XPath createTransformTypeXPath() throws JAXBException {
/*  450 */     return (TransformType.XPath)new TransformTypeImpl.XPathImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformType.XPath createTransformTypeXPath(String value) throws JAXBException {
/*  462 */     return (TransformType.XPath)new TransformTypeImpl.XPathImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ManifestType createManifestType() throws JAXBException {
/*  474 */     return (ManifestType)new ManifestTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ActionType createActionType() throws JAXBException {
/*  486 */     return (ActionType)new ActionTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoType createKeyInfoType() throws JAXBException {
/*  498 */     return (KeyInfoType)new KeyInfoTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Evidence createEvidence() throws JAXBException {
/*  510 */     return (Evidence)new EvidenceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AssertionType createAssertionType() throws JAXBException {
/*  522 */     return (AssertionType)new AssertionTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeStatement createAttributeStatement() throws JAXBException {
/*  534 */     return (AttributeStatement)new AttributeStatementImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigestMethodType createDigestMethodType() throws JAXBException {
/*  546 */     return (DigestMethodType)new DigestMethodTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Subject createSubject() throws JAXBException {
/*  558 */     return (Subject)new SubjectImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPKIDataType createSPKIDataType() throws JAXBException {
/*  570 */     return (SPKIDataType)new SPKIDataTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigestMethod createDigestMethod() throws JAXBException {
/*  582 */     return (DigestMethod)new DigestMethodImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKeyValueType createRSAKeyValueType() throws JAXBException {
/*  594 */     return (RSAKeyValueType)new RSAKeyValueTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Action createAction() throws JAXBException {
/*  606 */     return (Action)new ActionImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignedInfoType createSignedInfoType() throws JAXBException {
/*  618 */     return (SignedInfoType)new SignedInfoTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfo createKeyInfo() throws JAXBException {
/*  630 */     return (KeyInfo)new KeyInfoImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectConfirmationType createSubjectConfirmationType() throws JAXBException {
/*  642 */     return (SubjectConfirmationType)new SubjectConfirmationTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPKIDataType.SPKISexp createSPKIDataTypeSPKISexp() throws JAXBException {
/*  654 */     return (SPKIDataType.SPKISexp)new SPKIDataTypeImpl.SPKISexpImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPKIDataType.SPKISexp createSPKIDataTypeSPKISexp(byte[] value) throws JAXBException {
/*  666 */     return (SPKIDataType.SPKISexp)new SPKIDataTypeImpl.SPKISexpImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectStatementAbstractType createSubjectStatementAbstractType() throws JAXBException {
/*  678 */     return (SubjectStatementAbstractType)new SubjectStatementAbstractTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Audience createAudience() throws JAXBException {
/*  690 */     return (Audience)new AudienceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Audience createAudience(String value) throws JAXBException {
/*  702 */     return (Audience)new AudienceImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509Certificate createX509DataTypeX509Certificate() throws JAXBException {
/*  714 */     return (X509DataType.X509Certificate)new X509DataTypeImpl.X509CertificateImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509Certificate createX509DataTypeX509Certificate(byte[] value) throws JAXBException {
/*  726 */     return (X509DataType.X509Certificate)new X509DataTypeImpl.X509CertificateImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureMethod createSignatureMethod() throws JAXBException {
/*  738 */     return (SignatureMethod)new SignatureMethodImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigestValue createDigestValue() throws JAXBException {
/*  750 */     return (DigestValue)new DigestValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigestValue createDigestValue(byte[] value) throws JAXBException {
/*  762 */     return (DigestValue)new DigestValueImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reference createReference() throws JAXBException {
/*  774 */     return (Reference)new ReferenceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformsType createTransformsType() throws JAXBException {
/*  786 */     return (TransformsType)new TransformsTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeType createAttributeType() throws JAXBException {
/*  798 */     return (AttributeType)new AttributeTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthenticationStatement createAuthenticationStatement() throws JAXBException {
/*  810 */     return (AuthenticationStatement)new AuthenticationStatementImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyName createKeyName() throws JAXBException {
/*  822 */     return (KeyName)new KeyNameImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyName createKeyName(String value) throws JAXBException {
/*  834 */     return (KeyName)new KeyNameImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TransformType createTransformType() throws JAXBException {
/*  846 */     return (TransformType)new TransformTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DSAKeyValueType createDSAKeyValueType() throws JAXBException {
/*  858 */     return (DSAKeyValueType)new DSAKeyValueTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SPKIData createSPKIData() throws JAXBException {
/*  870 */     return (SPKIData)new SPKIDataImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AudienceRestrictionCondition createAudienceRestrictionCondition() throws JAXBException {
/*  882 */     return (AudienceRestrictionCondition)new AudienceRestrictionConditionImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType createX509DataType() throws JAXBException {
/*  894 */     return (X509DataType)new X509DataTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Assertion createAssertion() throws JAXBException {
/*  906 */     return (Assertion)new AssertionImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Signature createSignature() throws JAXBException {
/*  918 */     return (Signature)new SignatureImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RetrievalMethod createRetrievalMethod() throws JAXBException {
/*  930 */     return (RetrievalMethod)new RetrievalMethodImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectConfirmationData createSubjectConfirmationData() throws JAXBException {
/*  942 */     return (SubjectConfirmationData)new SubjectConfirmationDataImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthorityBindingType createAuthorityBindingType() throws JAXBException {
/*  954 */     return (AuthorityBindingType)new AuthorityBindingTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CanonicalizationMethod createCanonicalizationMethod() throws JAXBException {
/*  966 */     return (CanonicalizationMethod)new CanonicalizationMethodImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transform createTransform() throws JAXBException {
/*  978 */     return (Transform)new TransformImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectType createObjectType() throws JAXBException {
/*  990 */     return (ObjectType)new ObjectTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509Data createX509Data() throws JAXBException {
/* 1002 */     return (X509Data)new X509DataImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Condition createCondition() throws JAXBException {
/* 1014 */     return (Condition)new ConditionImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureProperties createSignatureProperties() throws JAXBException {
/* 1026 */     return (SignatureProperties)new SignaturePropertiesImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeDesignator createAttributeDesignator() throws JAXBException {
/* 1038 */     return (AttributeDesignator)new AttributeDesignatorImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AssertionIDReference createAssertionIDReference() throws JAXBException {
/* 1050 */     return (AssertionIDReference)new AssertionIDReferenceImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AssertionIDReference createAssertionIDReference(String value) throws JAXBException {
/* 1062 */     return (AssertionIDReference)new AssertionIDReferenceImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RSAKeyValue createRSAKeyValue() throws JAXBException {
/* 1074 */     return (RSAKeyValue)new RSAKeyValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AdviceType createAdviceType() throws JAXBException {
/* 1086 */     return (AdviceType)new AdviceTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Conditions createConditions() throws JAXBException {
/* 1098 */     return (Conditions)new ConditionsImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyValueType createKeyValueType() throws JAXBException {
/* 1110 */     return (KeyValueType)new KeyValueTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureMethodType.HMACOutputLength createSignatureMethodTypeHMACOutputLength() throws JAXBException {
/* 1122 */     return (SignatureMethodType.HMACOutputLength)new SignatureMethodTypeImpl.HMACOutputLengthImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureMethodType.HMACOutputLength createSignatureMethodTypeHMACOutputLength(BigInteger value) throws JAXBException {
/* 1134 */     return (SignatureMethodType.HMACOutputLength)new SignatureMethodTypeImpl.HMACOutputLengthImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectLocality createSubjectLocality() throws JAXBException {
/* 1146 */     return (SubjectLocality)new SubjectLocalityImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoNotCacheCondition createDoNotCacheCondition() throws JAXBException {
/* 1158 */     return (DoNotCacheCondition)new DoNotCacheConditionImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509SKI createX509DataTypeX509SKI() throws JAXBException {
/* 1170 */     return (X509DataType.X509SKI)new X509DataTypeImpl.X509SKIImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509SKI createX509DataTypeX509SKI(byte[] value) throws JAXBException {
/* 1182 */     return (X509DataType.X509SKI)new X509DataTypeImpl.X509SKIImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConditionAbstractType createConditionAbstractType() throws JAXBException {
/* 1194 */     return (ConditionAbstractType)new ConditionAbstractTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeStatementType createAttributeStatementType() throws JAXBException {
/* 1206 */     return (AttributeStatementType)new AttributeStatementTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameIdentifierType createNameIdentifierType() throws JAXBException {
/* 1218 */     return (NameIdentifierType)new NameIdentifierTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignaturePropertiesType createSignaturePropertiesType() throws JAXBException {
/* 1230 */     return (SignaturePropertiesType)new SignaturePropertiesTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeDesignatorType createAttributeDesignatorType() throws JAXBException {
/* 1242 */     return (AttributeDesignatorType)new AttributeDesignatorTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureValueType createSignatureValueType() throws JAXBException {
/* 1254 */     return (SignatureValueType)new SignatureValueTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object createObject() throws JAXBException {
/* 1266 */     return (Object)new ObjectImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConditionsType createConditionsType() throws JAXBException {
/* 1278 */     return (ConditionsType)new ConditionsTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectStatement createSubjectStatement() throws JAXBException {
/* 1290 */     return (SubjectStatement)new SubjectStatementImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EvidenceType createEvidenceType() throws JAXBException {
/* 1302 */     return (EvidenceType)new EvidenceTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectType createSubjectType() throws JAXBException {
/* 1314 */     return (SubjectType)new SubjectTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DoNotCacheConditionType createDoNotCacheConditionType() throws JAXBException {
/* 1326 */     return (DoNotCacheConditionType)new DoNotCacheConditionTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignedInfo createSignedInfo() throws JAXBException {
/* 1338 */     return (SignedInfo)new SignedInfoImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectConfirmation createSubjectConfirmation() throws JAXBException {
/* 1350 */     return (SubjectConfirmation)new SubjectConfirmationImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Transforms createTransforms() throws JAXBException {
/* 1362 */     return (Transforms)new TransformsImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509CRL createX509DataTypeX509CRL() throws JAXBException {
/* 1374 */     return (X509DataType.X509CRL)new X509DataTypeImpl.X509CRLImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public X509DataType.X509CRL createX509DataTypeX509CRL(byte[] value) throws JAXBException {
/* 1386 */     return (X509DataType.X509CRL)new X509DataTypeImpl.X509CRLImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureProperty createSignatureProperty() throws JAXBException {
/* 1398 */     return (SignatureProperty)new SignaturePropertyImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignaturePropertyType createSignaturePropertyType() throws JAXBException {
/* 1410 */     return (SignaturePropertyType)new SignaturePropertyTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CanonicalizationMethodType createCanonicalizationMethodType() throws JAXBException {
/* 1422 */     return (CanonicalizationMethodType)new CanonicalizationMethodTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConfirmationMethod createConfirmationMethod() throws JAXBException {
/* 1434 */     return (ConfirmationMethod)new ConfirmationMethodImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConfirmationMethod createConfirmationMethod(String value) throws JAXBException {
/* 1446 */     return (ConfirmationMethod)new ConfirmationMethodImpl(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyValue createKeyValue() throws JAXBException {
/* 1458 */     return (KeyValue)new KeyValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DSAKeyValue createDSAKeyValue() throws JAXBException {
/* 1470 */     return (DSAKeyValue)new DSAKeyValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureValue createSignatureValue() throws JAXBException {
/* 1482 */     return (SignatureValue)new SignatureValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NameIdentifier createNameIdentifier() throws JAXBException {
/* 1494 */     return (NameIdentifier)new NameIdentifierImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Attribute createAttribute() throws JAXBException {
/* 1506 */     return (Attribute)new AttributeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SubjectLocalityType createSubjectLocalityType() throws JAXBException {
/* 1518 */     return (SubjectLocalityType)new SubjectLocalityTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PGPData createPGPData() throws JAXBException {
/* 1530 */     return (PGPData)new PGPDataImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Statement createStatement() throws JAXBException {
/* 1542 */     return (Statement)new StatementImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeValue createAttributeValue() throws JAXBException {
/* 1554 */     return (AttributeValue)new AttributeValueImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthorizationDecisionStatement createAuthorizationDecisionStatement() throws JAXBException {
/* 1566 */     return (AuthorizationDecisionStatement)new AuthorizationDecisionStatementImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthorityBinding createAuthorityBinding() throws JAXBException {
/* 1578 */     return (AuthorityBinding)new AuthorityBindingImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AudienceRestrictionConditionType createAudienceRestrictionConditionType() throws JAXBException {
/* 1590 */     return (AudienceRestrictionConditionType)new AudienceRestrictionConditionTypeImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Manifest createManifest() throws JAXBException {
/* 1602 */     return (Manifest)new ManifestImpl();
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\ObjectFactory.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */