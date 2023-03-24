/*     */ package com.sun.xml.security.core.xenc;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import java.math.BigInteger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
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
/*  76 */   private static final QName _EncryptionMethodTypeOAEPparams_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "OAEPparams");
/*  77 */   private static final QName _EncryptionMethodTypeKeySize_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeySize");
/*  78 */   private static final QName _AgreementMethod_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod");
/*  79 */   private static final QName _EncryptionProperties_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties");
/*  80 */   private static final QName _CipherReference_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherReference");
/*  81 */   private static final QName _EncryptionProperty_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty");
/*  82 */   private static final QName _EncryptedKey_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
/*  83 */   private static final QName _CipherData_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData");
/*  84 */   private static final QName _EncryptedData_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
/*  85 */   private static final QName _ReferenceListDataReference_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "DataReference");
/*  86 */   private static final QName _ReferenceListKeyReference_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KeyReference");
/*  87 */   private static final QName _AgreementMethodTypeKANonce_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "KA-Nonce");
/*  88 */   private static final QName _AgreementMethodTypeOriginatorKeyInfo_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo");
/*  89 */   private static final QName _AgreementMethodTypeRecipientKeyInfo_QNAME = new QName("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionPropertiesType createEncryptionPropertiesType() {
/* 103 */     return new EncryptionPropertiesType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionPropertyType createEncryptionPropertyType() {
/* 111 */     return new EncryptionPropertyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedDataType createEncryptedDataType() {
/* 119 */     return new EncryptedDataType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CipherReferenceType createCipherReferenceType() {
/* 127 */     return new CipherReferenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionMethodType createEncryptionMethodType() {
/* 135 */     return new EncryptionMethodType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TransformsType createTransformsType() {
/* 143 */     return new TransformsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptedKeyType createEncryptedKeyType() {
/* 151 */     return new EncryptedKeyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceType createReferenceType() {
/* 159 */     return new ReferenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AgreementMethodType createAgreementMethodType() {
/* 167 */     return new AgreementMethodType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CipherDataType createCipherDataType() {
/* 175 */     return new CipherDataType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReferenceList createReferenceList() {
/* 183 */     return new ReferenceList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "OAEPparams", scope = EncryptionMethodType.class)
/*     */   public JAXBElement<byte[]> createEncryptionMethodTypeOAEPparams(byte[] value) {
/* 192 */     return (JAXBElement)new JAXBElement<byte>(_EncryptionMethodTypeOAEPparams_QNAME, (Class)byte[].class, EncryptionMethodType.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "KeySize", scope = EncryptionMethodType.class)
/*     */   public JAXBElement<BigInteger> createEncryptionMethodTypeKeySize(BigInteger value) {
/* 201 */     return new JAXBElement<BigInteger>(_EncryptionMethodTypeKeySize_QNAME, BigInteger.class, EncryptionMethodType.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "AgreementMethod")
/*     */   public JAXBElement<AgreementMethodType> createAgreementMethod(AgreementMethodType value) {
/* 210 */     return new JAXBElement<AgreementMethodType>(_AgreementMethod_QNAME, AgreementMethodType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "EncryptionProperties")
/*     */   public JAXBElement<EncryptionPropertiesType> createEncryptionProperties(EncryptionPropertiesType value) {
/* 219 */     return new JAXBElement<EncryptionPropertiesType>(_EncryptionProperties_QNAME, EncryptionPropertiesType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "CipherReference")
/*     */   public JAXBElement<CipherReferenceType> createCipherReference(CipherReferenceType value) {
/* 228 */     return new JAXBElement<CipherReferenceType>(_CipherReference_QNAME, CipherReferenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "EncryptionProperty")
/*     */   public JAXBElement<EncryptionPropertyType> createEncryptionProperty(EncryptionPropertyType value) {
/* 237 */     return new JAXBElement<EncryptionPropertyType>(_EncryptionProperty_QNAME, EncryptionPropertyType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "EncryptedKey")
/*     */   public JAXBElement<EncryptedKeyType> createEncryptedKey(EncryptedKeyType value) {
/* 246 */     return new JAXBElement<EncryptedKeyType>(_EncryptedKey_QNAME, EncryptedKeyType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "CipherData")
/*     */   public JAXBElement<CipherDataType> createCipherData(CipherDataType value) {
/* 255 */     return new JAXBElement<CipherDataType>(_CipherData_QNAME, CipherDataType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "EncryptedData")
/*     */   public JAXBElement<EncryptedDataType> createEncryptedData(EncryptedDataType value) {
/* 264 */     return new JAXBElement<EncryptedDataType>(_EncryptedData_QNAME, EncryptedDataType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "DataReference", scope = ReferenceList.class)
/*     */   public JAXBElement<ReferenceType> createReferenceListDataReference(ReferenceType value) {
/* 273 */     return new JAXBElement<ReferenceType>(_ReferenceListDataReference_QNAME, ReferenceType.class, ReferenceList.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "KeyReference", scope = ReferenceList.class)
/*     */   public JAXBElement<ReferenceType> createReferenceListKeyReference(ReferenceType value) {
/* 282 */     return new JAXBElement<ReferenceType>(_ReferenceListKeyReference_QNAME, ReferenceType.class, ReferenceList.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "KA-Nonce", scope = AgreementMethodType.class)
/*     */   public JAXBElement<byte[]> createAgreementMethodTypeKANonce(byte[] value) {
/* 291 */     return (JAXBElement)new JAXBElement<byte>(_AgreementMethodTypeKANonce_QNAME, (Class)byte[].class, AgreementMethodType.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "OriginatorKeyInfo", scope = AgreementMethodType.class)
/*     */   public JAXBElement<KeyInfoType> createAgreementMethodTypeOriginatorKeyInfo(KeyInfoType value) {
/* 300 */     return new JAXBElement<KeyInfoType>(_AgreementMethodTypeOriginatorKeyInfo_QNAME, KeyInfoType.class, AgreementMethodType.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/04/xmlenc#", name = "RecipientKeyInfo", scope = AgreementMethodType.class)
/*     */   public JAXBElement<KeyInfoType> createAgreementMethodTypeRecipientKeyInfo(KeyInfoType value) {
/* 309 */     return new JAXBElement<KeyInfoType>(_AgreementMethodTypeRecipientKeyInfo_QNAME, KeyInfoType.class, AgreementMethodType.class, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\xenc\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */