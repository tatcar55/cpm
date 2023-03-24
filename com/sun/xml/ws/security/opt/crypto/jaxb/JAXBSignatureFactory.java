/*     */ package com.sun.xml.ws.security.opt.crypto.jaxb;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.CanonicalizationMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.DigestMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Signature;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignatureMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.SignedInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transform;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transforms;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.XMLObject;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.DSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyName;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.PGPData;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RetrievalMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.SPKIData;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509Data;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509IssuerSerial;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import java.math.BigInteger;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.MarshalException;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Manifest;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignatureProperties;
/*     */ import javax.xml.crypto.dsig.SignatureProperty;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLObject;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.XMLValidateContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.DigestMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.SignatureMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JAXBSignatureFactory
/*     */   extends XMLSignatureFactory
/*     */ {
/* 100 */   private static JAXBSignatureFactory instance = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JAXBSignatureFactory newInstance() {
/* 107 */     if (instance == null) {
/* 108 */       instance = new JAXBSignatureFactory();
/*     */     }
/* 110 */     return instance;
/*     */   }
/*     */   
/*     */   public JAXBContext getJAXBContext() throws JAXBException {
/* 114 */     return JAXBUtil.getJAXBContext();
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
/*     */   public XMLSignature newXMLSignature(SignedInfo signedInfo, KeyInfo keyInfo) {
/* 127 */     if (signedInfo == null) {
/* 128 */       throw new NullPointerException("SignedInfo can not be null");
/*     */     }
/* 130 */     Signature signature = new Signature();
/* 131 */     signature.setKeyInfo((KeyInfo)keyInfo);
/* 132 */     signature.setSignedInfo((SignedInfo)signedInfo);
/* 133 */     return (XMLSignature)signature;
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
/*     */   public XMLSignature newXMLSignature(SignedInfo signedInfo, KeyInfo keyInfo, List objects, String id, String type) {
/* 150 */     Signature signature = (Signature)newXMLSignature(signedInfo, keyInfo);
/* 151 */     signature.setId(id);
/* 152 */     signature.setType(type);
/* 153 */     signature.setObjects(objects);
/* 154 */     return (XMLSignature)signature;
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
/*     */   public Reference newReference(String uri, DigestMethod digestMethod) {
/* 168 */     if (digestMethod == null) {
/* 169 */       throw new NullPointerException("Digest method can not be null");
/*     */     }
/* 171 */     Reference ref = new Reference();
/* 172 */     ref.setURI(uri);
/* 173 */     ref.setDigestMethod((DigestMethod)digestMethod);
/* 174 */     return (Reference)ref;
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
/*     */   public Reference newReference(String uri, DigestMethod digestMethod, List transforms, String type, String id) {
/* 189 */     Reference ref = (Reference)newReference(uri, digestMethod);
/* 190 */     ref.setType(type);
/* 191 */     ref.setId(id);
/*     */     
/* 193 */     Transforms transfrormList = new Transforms();
/* 194 */     transfrormList.setTransform(transforms);
/*     */     
/* 196 */     ref.setTransforms(transfrormList);
/* 197 */     return (Reference)ref;
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
/*     */   public Reference newReference(String uri, DigestMethod digestMethod, List transforms, String type, String id, byte[] digestValue) {
/* 213 */     if (digestMethod == null)
/* 214 */       throw new NullPointerException("DigestMethod can not be null"); 
/* 215 */     if (digestValue == null) {
/* 216 */       throw new NullPointerException("Digest value can not be null");
/*     */     }
/*     */     
/* 219 */     Reference ref = (Reference)newReference(uri, digestMethod, transforms, type, id);
/* 220 */     ref.setDigestValue(digestValue);
/* 221 */     return (Reference)ref;
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
/*     */   public Reference newReference(String string, DigestMethod digestMethod, List list, Data data, List list0, String string0, String string1) {
/* 238 */     throw new UnsupportedOperationException("Not yet suported");
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
/*     */   public SignedInfo newSignedInfo(CanonicalizationMethod canonicalizationMethod, SignatureMethod signatureMethod, List references) {
/* 253 */     if (canonicalizationMethod == null)
/* 254 */       throw new NullPointerException("Canonicalization Method can not be null"); 
/* 255 */     if (signatureMethod == null)
/* 256 */       throw new NullPointerException("Signature Method can not be null"); 
/* 257 */     if (references == null || references.size() == 0) {
/* 258 */       throw new NullPointerException("References can not be null");
/*     */     }
/*     */     
/* 261 */     SignedInfo signedInfo = new SignedInfo();
/* 262 */     signedInfo.setCanonicalizationMethod((CanonicalizationMethod)canonicalizationMethod);
/* 263 */     signedInfo.setSignatureMethod((SignatureMethod)signatureMethod);
/* 264 */     signedInfo.setReference(references);
/*     */     
/* 266 */     return (SignedInfo)signedInfo;
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
/*     */   public SignedInfo newSignedInfo(CanonicalizationMethod canonicalizationMethod, SignatureMethod signatureMethod, List references, String id) {
/* 280 */     SignedInfo signedInfo = (SignedInfo)newSignedInfo(canonicalizationMethod, signatureMethod, references);
/*     */     
/* 282 */     signedInfo.setId(id);
/* 283 */     return (SignedInfo)signedInfo;
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
/*     */   public XMLObject newXMLObject(List content, String id, String mime, String encoding) {
/* 297 */     XMLObject xmlObject = new XMLObject();
/*     */     
/* 299 */     xmlObject.setEncoding(encoding);
/* 300 */     xmlObject.setMimeType(mime);
/* 301 */     xmlObject.setId(id);
/* 302 */     xmlObject.setContent(content);
/* 303 */     return (XMLObject)xmlObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest newManifest(List list) {
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Manifest newManifest(List list, String string) {
/* 324 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperty newSignatureProperty(List list, String string, String string0) {
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureProperties newSignatureProperties(List list, String string) {
/* 345 */     return null;
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
/*     */   public DigestMethod newDigestMethod(String algorithm, DigestMethodParameterSpec digestMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 357 */     if (algorithm == null) {
/* 358 */       throw new NullPointerException("Digest algorithm can not be null");
/*     */     }
/* 360 */     DigestMethod digestMethod = new DigestMethod();
/*     */     
/* 362 */     digestMethod.setParameterSpec(digestMethodParameterSpec);
/* 363 */     digestMethod.setAlgorithm(algorithm);
/* 364 */     return (DigestMethod)digestMethod;
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
/*     */   public SignatureMethod newSignatureMethod(String algorithm, SignatureMethodParameterSpec signatureMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 376 */     if (algorithm == null) {
/* 377 */       throw new NullPointerException("Signature Method algorithm can not be null");
/*     */     }
/*     */     
/* 380 */     SignatureMethod signatureMethod = new SignatureMethod();
/*     */     
/* 382 */     signatureMethod.setAlgorithm(algorithm);
/* 383 */     if (signatureMethodParameterSpec != null)
/* 384 */       signatureMethod.setParameter(signatureMethodParameterSpec); 
/* 385 */     return (SignatureMethod)signatureMethod;
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
/*     */   public Transform newTransform(String algorithm, TransformParameterSpec transformParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 397 */     Transform transform = new Transform();
/*     */     
/* 399 */     transform.setAlgorithm(algorithm);
/* 400 */     transform.setParameterSpec(transformParameterSpec);
/* 401 */     return (Transform)transform;
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
/*     */   public Transform newTransform(String algorithm, XMLStructure xMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 414 */     if (algorithm == null) {
/* 415 */       throw new NullPointerException("Algorithm can not be null");
/*     */     }
/* 417 */     Transform transform = new Transform();
/*     */     
/* 419 */     transform.setAlgorithm(algorithm);
/*     */     
/* 421 */     List<JAXBElement> content = new ArrayList();
/* 422 */     content.add(((JAXBStructure)xMLStructure).getJAXBElement());
/*     */     
/* 424 */     transform.setContent(content);
/* 425 */     return (Transform)transform;
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
/*     */   public CanonicalizationMethod newCanonicalizationMethod(String algorithm, C14NMethodParameterSpec c14NMethodParameterSpec) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 437 */     CanonicalizationMethod canonicalizationMethod = new CanonicalizationMethod();
/*     */     
/* 439 */     canonicalizationMethod.setAlgorithm(algorithm);
/* 440 */     canonicalizationMethod.setParameterSpec(c14NMethodParameterSpec);
/* 441 */     return (CanonicalizationMethod)canonicalizationMethod;
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
/*     */   public CanonicalizationMethod newCanonicalizationMethod(String algorithm, XMLStructure xMLStructure) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
/* 454 */     CanonicalizationMethod canonicalizationMethod = new CanonicalizationMethod();
/*     */     
/* 456 */     canonicalizationMethod.setAlgorithm(algorithm);
/* 457 */     if (xMLStructure != null) {
/* 458 */       List<XMLStructure> content = new ArrayList();
/* 459 */       content.add(xMLStructure);
/* 460 */       canonicalizationMethod.setContent(content);
/*     */     } 
/* 462 */     return (CanonicalizationMethod)canonicalizationMethod;
/*     */   }
/*     */   
/*     */   public KeyInfo newKeyInfo(List content) {
/* 466 */     KeyInfo ki = new KeyInfo();
/* 467 */     ki.setContent(content);
/* 468 */     return (KeyInfo)ki;
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
/*     */   public DSAKeyValue newDSAKeyValue(byte[] p, byte[] q, byte[] g, byte[] y, byte[] j, byte[] seed, byte[] pgenCounter) {
/* 490 */     DSAKeyValue dsaKeyValue = new DSAKeyValue();
/* 491 */     dsaKeyValue.setP(p);
/* 492 */     dsaKeyValue.setQ(q);
/* 493 */     dsaKeyValue.setG(g);
/* 494 */     dsaKeyValue.setY(y);
/* 495 */     dsaKeyValue.setJ(j);
/* 496 */     dsaKeyValue.setSeed(seed);
/* 497 */     dsaKeyValue.setPgenCounter(pgenCounter);
/*     */     
/* 499 */     return dsaKeyValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfo newKeyInfo(String id, List content) {
/* 510 */     KeyInfo keyInfo = new KeyInfo();
/* 511 */     keyInfo.setId(id);
/* 512 */     keyInfo.setContent(content);
/* 513 */     return keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyName newKeyName(String name) {
/* 522 */     KeyName keyName = new KeyName();
/* 523 */     keyName.setKeyName(name);
/* 524 */     return keyName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyValue newKeyValue(List content) {
/* 534 */     KeyValue keyValue = new KeyValue();
/* 535 */     keyValue.setContent(content);
/* 536 */     return keyValue;
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
/*     */   public PGPData newPGPData(List content) {
/* 548 */     PGPData pgpData = new PGPData();
/* 549 */     pgpData.setContent(content);
/* 550 */     return pgpData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RSAKeyValue newRSAKeyValue(byte[] modulas, byte[] exponent) {
/* 560 */     RSAKeyValue rsaKeyValue = new RSAKeyValue();
/* 561 */     rsaKeyValue.setExponent(exponent);
/* 562 */     rsaKeyValue.setModulus(modulas);
/* 563 */     return rsaKeyValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RetrievalMethod newRetrievalMethod(Transforms transforms, String type, String uri) {
/* 574 */     RetrievalMethod rm = new RetrievalMethod();
/* 575 */     rm.setTransforms(transforms);
/* 576 */     rm.setType(type);
/* 577 */     rm.setURI(uri);
/* 578 */     return rm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SPKIData newSPKIData(List spkiSexpAndAny) {
/* 588 */     SPKIData spkiData = new SPKIData();
/* 589 */     spkiData.setSpkiSexpAndAny(spkiSexpAndAny);
/* 590 */     return spkiData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509Data newX509Data(List content) {
/* 600 */     X509Data x509Data = new X509Data();
/* 601 */     x509Data.setX509IssuerSerialOrX509SKIOrX509SubjectName(content);
/* 602 */     return x509Data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public X509IssuerSerial newX509IssuerSerial(String issuer, BigInteger serialno) {
/* 612 */     X509IssuerSerial x509IssuerSerial = new X509IssuerSerial();
/* 613 */     x509IssuerSerial.setX509IssuerName(issuer);
/* 614 */     x509IssuerSerial.setX509SerialNumber(serialno);
/* 615 */     return x509IssuerSerial;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature unmarshalXMLSignature(XMLValidateContext xMLValidateContext) throws MarshalException {
/* 625 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature unmarshalXMLSignature(XMLStructure xMLStructure) throws MarshalException {
/* 635 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFeatureSupported(String string) {
/* 644 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URIDereferencer getURIDereferencer() {
/* 652 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\jaxb\JAXBSignatureFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */