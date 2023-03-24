/*     */ package com.sun.xml.security.core.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.DigestMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Manifest;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Signature;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureProperties;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureProperty;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignedInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transform;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transforms;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.DSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.PGPData;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RetrievalMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.SPKIData;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509Data;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509IssuerSerial;
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
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  77 */   private static final QName _Object_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Object");
/*  78 */   private static final QName _SPKIData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKIData");
/*  79 */   private static final QName _SignatureMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureMethod");
/*  80 */   private static final QName _RSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue");
/*  81 */   private static final QName _RetrievalMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "RetrievalMethod");
/*  82 */   private static final QName _SignatureValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureValue");
/*  83 */   private static final QName _KeyName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyName");
/*  84 */   private static final QName _DSAKeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue");
/*  85 */   private static final QName _DigestMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestMethod");
/*  86 */   private static final QName _KeyValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyValue");
/*  87 */   private static final QName _Transforms_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transforms");
/*  88 */   private static final QName _Signature_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*  89 */   private static final QName _X509Data_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Data");
/*  90 */   private static final QName _Manifest_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Manifest");
/*  91 */   private static final QName _SignedInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignedInfo");
/*  92 */   private static final QName _KeyInfo_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
/*  93 */   private static final QName _PGPData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPData");
/*  94 */   private static final QName _SignatureProperty_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperty");
/*  95 */   private static final QName _SignatureProperties_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SignatureProperties");
/*  96 */   private static final QName _Reference_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Reference");
/*  97 */   private static final QName _DigestValue_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "DigestValue");
/*  98 */   private static final QName _Transform_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "Transform");
/*  99 */   private static final QName _MgmtData_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "MgmtData");
/* 100 */   private static final QName _CanonicalizationMethod_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "CanonicalizationMethod");
/* 101 */   private static final QName _SignatureMethodTypeHMACOutputLength_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "HMACOutputLength");
/* 102 */   private static final QName _PGPDataTypePGPKeyPacket_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyPacket");
/* 103 */   private static final QName _PGPDataTypePGPKeyID_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID");
/* 104 */   private static final QName _SPKIDataTypeSPKISexp_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "SPKISexp");
/* 105 */   private static final QName _TransformTypeXPath_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "XPath");
/* 106 */   private static final QName _X509DataTypeX509SKI_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SKI");
/* 107 */   private static final QName _X509DataTypeX509Certificate_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509Certificate");
/* 108 */   private static final QName _X509DataTypeX509IssuerSerial_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509IssuerSerial");
/* 109 */   private static final QName _X509DataTypeX509SubjectName_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509SubjectName");
/* 110 */   private static final QName _X509DataTypeX509CRL_QNAME = new QName("http://www.w3.org/2000/09/xmldsig#", "X509CRL");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DSAKeyValue createDSAKeyValue() {
/* 124 */     return new DSAKeyValue();
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
/*     */   public PGPData createPGPData() {
/* 140 */     return new PGPData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RetrievalMethod createRetrievalMethod() {
/* 148 */     return new RetrievalMethod();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperty createSignatureProperty() {
/* 156 */     return new SignatureProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestMethod createDigestMethod() {
/* 164 */     return new DigestMethod();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignedInfo createSignedInfo() {
/* 172 */     return new SignedInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Signature createSignature() {
/* 180 */     return new Signature();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperties createSignatureProperties() {
/* 188 */     return new SignatureProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transforms createTransforms() {
/* 196 */     return new Transforms();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureMethod createSignatureMethod() {
/* 204 */     return new SignatureMethod();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest createManifest() {
/* 212 */     return new Manifest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectType createObjectType() {
/* 220 */     return new ObjectType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPKIData createSPKIData() {
/* 228 */     return new SPKIData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAKeyValue createRSAKeyValue() {
/* 236 */     return new RSAKeyValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial createX509IssuerSerial() {
/* 244 */     return new X509IssuerSerial();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reference createReference() {
/* 252 */     return new Reference();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transform createTransform() {
/* 260 */     return new Transform();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfo createKeyInfo() {
/* 268 */     return new KeyInfo();
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
/*     */   public X509Data createX509Data() {
/* 284 */     return new X509Data();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue createKeyValue() {
/* 292 */     return new KeyValue();
/*     */   }
/*     */   
/*     */   public InclusiveNamespacesType createInclusiveNamespaces() {
/* 296 */     return new InclusiveNamespacesType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Object")
/*     */   public JAXBElement<ObjectType> createObject(ObjectType value) {
/* 305 */     return new JAXBElement<ObjectType>(_Object_QNAME, ObjectType.class, null, value);
/*     */   }
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2001/10/xml-exc-c14n#", name = "InclusiveNamespaces")
/*     */   public JAXBElement<InclusiveNamespacesType> createInclusiveNamespaces(InclusiveNamespacesType value) {
/* 310 */     QName qname = new QName("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces");
/* 311 */     return new JAXBElement<InclusiveNamespacesType>(qname, InclusiveNamespacesType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKIData")
/*     */   public JAXBElement<SPKIData> createSPKIData(SPKIData value) {
/* 320 */     return new JAXBElement<SPKIData>(_SPKIData_QNAME, SPKIData.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureMethod")
/*     */   public JAXBElement<SignatureMethod> createSignatureMethod(SignatureMethod value) {
/* 329 */     return new JAXBElement<SignatureMethod>(_SignatureMethod_QNAME, SignatureMethod.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RSAKeyValue")
/*     */   public JAXBElement<RSAKeyValue> createRSAKeyValue(RSAKeyValue value) {
/* 338 */     return new JAXBElement<RSAKeyValue>(_RSAKeyValue_QNAME, RSAKeyValue.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "RetrievalMethod")
/*     */   public JAXBElement<RetrievalMethod> createRetrievalMethod(RetrievalMethod value) {
/* 347 */     return new JAXBElement<RetrievalMethod>(_RetrievalMethod_QNAME, RetrievalMethod.class, null, value);
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
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyName")
/*     */   public JAXBElement<String> createKeyName(String value) {
/* 365 */     return new JAXBElement<String>(_KeyName_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DSAKeyValue")
/*     */   public JAXBElement<DSAKeyValue> createDSAKeyValue(DSAKeyValue value) {
/* 374 */     return new JAXBElement<DSAKeyValue>(_DSAKeyValue_QNAME, DSAKeyValue.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestMethod")
/*     */   public JAXBElement<DigestMethod> createDigestMethod(DigestMethod value) {
/* 383 */     return new JAXBElement<DigestMethod>(_DigestMethod_QNAME, DigestMethod.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyValue")
/*     */   public JAXBElement<KeyValue> createKeyValue(KeyValue value) {
/* 392 */     return new JAXBElement<KeyValue>(_KeyValue_QNAME, KeyValue.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transforms")
/*     */   public JAXBElement<Transforms> createTransforms(Transforms value) {
/* 401 */     return new JAXBElement<Transforms>(_Transforms_QNAME, Transforms.class, null, value);
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
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Data")
/*     */   public JAXBElement<X509Data> createX509Data(X509Data value) {
/* 419 */     return new JAXBElement<X509Data>(_X509Data_QNAME, X509Data.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Manifest")
/*     */   public JAXBElement<Manifest> createManifest(Manifest value) {
/* 428 */     return new JAXBElement<Manifest>(_Manifest_QNAME, Manifest.class, null, value);
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
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "KeyInfo")
/*     */   public JAXBElement<KeyInfo> createKeyInfo(KeyInfo value) {
/* 446 */     return new JAXBElement<KeyInfo>(_KeyInfo_QNAME, KeyInfo.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPData")
/*     */   public JAXBElement<PGPData> createPGPData(PGPData value) {
/* 455 */     return new JAXBElement<PGPData>(_PGPData_QNAME, PGPData.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperty")
/*     */   public JAXBElement<SignatureProperty> createSignatureProperty(SignatureProperty value) {
/* 464 */     return new JAXBElement<SignatureProperty>(_SignatureProperty_QNAME, SignatureProperty.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SignatureProperties")
/*     */   public JAXBElement<SignatureProperties> createSignatureProperties(SignatureProperties value) {
/* 473 */     return new JAXBElement<SignatureProperties>(_SignatureProperties_QNAME, SignatureProperties.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Reference")
/*     */   public JAXBElement<Reference> createReference(Reference value) {
/* 482 */     return new JAXBElement<Reference>(_Reference_QNAME, Reference.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "DigestValue")
/*     */   public JAXBElement<byte[]> createDigestValue(byte[] value) {
/* 491 */     return (JAXBElement)new JAXBElement<byte>(_DigestValue_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "Transform")
/*     */   public JAXBElement<Transform> createTransform(Transform value) {
/* 500 */     return new JAXBElement<Transform>(_Transform_QNAME, Transform.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "MgmtData")
/*     */   public JAXBElement<String> createMgmtData(String value) {
/* 509 */     return new JAXBElement<String>(_MgmtData_QNAME, String.class, null, value);
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
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "HMACOutputLength", scope = SignatureMethodType.class)
/*     */   public JAXBElement<BigInteger> createSignatureMethodTypeHMACOutputLength(BigInteger value) {
/* 527 */     return new JAXBElement<BigInteger>(_SignatureMethodTypeHMACOutputLength_QNAME, BigInteger.class, SignatureMethod.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyPacket", scope = PGPDataType.class)
/*     */   public JAXBElement<byte[]> createPGPDataTypePGPKeyPacket(byte[] value) {
/* 536 */     return (JAXBElement)new JAXBElement<byte>(_PGPDataTypePGPKeyPacket_QNAME, (Class)byte[].class, PGPData.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "PGPKeyID", scope = PGPDataType.class)
/*     */   public JAXBElement<byte[]> createPGPDataTypePGPKeyID(byte[] value) {
/* 545 */     return (JAXBElement)new JAXBElement<byte>(_PGPDataTypePGPKeyID_QNAME, (Class)byte[].class, PGPData.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "SPKISexp", scope = SPKIDataType.class)
/*     */   public JAXBElement<byte[]> createSPKIDataTypeSPKISexp(byte[] value) {
/* 554 */     return (JAXBElement)new JAXBElement<byte>(_SPKIDataTypeSPKISexp_QNAME, (Class)byte[].class, SPKIData.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "XPath", scope = TransformType.class)
/*     */   public JAXBElement<String> createTransformTypeXPath(String value) {
/* 563 */     return new JAXBElement<String>(_TransformTypeXPath_QNAME, String.class, Transform.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SKI", scope = X509DataType.class)
/*     */   public JAXBElement<byte[]> createX509DataTypeX509SKI(byte[] value) {
/* 572 */     return (JAXBElement)new JAXBElement<byte>(_X509DataTypeX509SKI_QNAME, (Class)byte[].class, X509Data.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509Certificate", scope = X509DataType.class)
/*     */   public JAXBElement<byte[]> createX509DataTypeX509Certificate(byte[] value) {
/* 581 */     return (JAXBElement)new JAXBElement<byte>(_X509DataTypeX509Certificate_QNAME, (Class)byte[].class, X509Data.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509IssuerSerial", scope = X509Data.class)
/*     */   public JAXBElement<X509IssuerSerial> createX509DataTypeX509IssuerSerial(X509IssuerSerial value) {
/* 590 */     return new JAXBElement<X509IssuerSerial>(_X509DataTypeX509IssuerSerial_QNAME, X509IssuerSerial.class, X509Data.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509SubjectName", scope = X509DataType.class)
/*     */   public JAXBElement<String> createX509DataTypeX509SubjectName(String value) {
/* 599 */     return new JAXBElement<String>(_X509DataTypeX509SubjectName_QNAME, String.class, X509Data.class, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2000/09/xmldsig#", name = "X509CRL", scope = X509DataType.class)
/*     */   public JAXBElement<byte[]> createX509DataTypeX509CRL(byte[] value) {
/* 608 */     return (JAXBElement)new JAXBElement<byte>(_X509DataTypeX509CRL_QNAME, (Class)byte[].class, X509Data.class, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\security\core\dsig\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */