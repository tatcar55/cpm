/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.Init;
/*     */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.security.core.dsig.TransformType;
/*     */ import com.sun.xml.security.core.xenc.CipherDataType;
/*     */ import com.sun.xml.security.core.xenc.CipherReferenceType;
/*     */ import com.sun.xml.security.core.xenc.EncryptedDataType;
/*     */ import com.sun.xml.security.core.xenc.EncryptedKeyType;
/*     */ import com.sun.xml.security.core.xenc.EncryptionMethodType;
/*     */ import com.sun.xml.security.core.xenc.ObjectFactory;
/*     */ import com.sun.xml.security.core.xenc.ReferenceType;
/*     */ import com.sun.xml.security.core.xenc.TransformsType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedData;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyName;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyValue;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.AttachmentData;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.EncryptedHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedData;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.DerivedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.impl.message.GSHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.X509Data;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.X509IssuerSerial;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.DerivedKeyTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.bindings.SecurityContextTokenType;
/*     */ import com.sun.xml.ws.security.secconv.impl.wssx.bindings.DerivedKeyTokenType;
/*     */ import com.sun.xml.ws.security.secext10.BinarySecurityTokenType;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import com.sun.xml.ws.security.secext11.EncryptedHeaderType;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.security.Key;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.Data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSSElementFactory
/*     */ {
/*     */   static {
/* 107 */     Init.init();
/*     */   }
/*     */   
/* 110 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/*     */   
/* 112 */   public static final ObjectFactory eoFactory = new ObjectFactory();
/*     */   
/*     */   public WSSElementFactory(SOAPVersion soapVersion) {
/* 115 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityHeader createSecurityHeader() {
/* 120 */     return new SecurityHeader();
/*     */   }
/*     */   
/*     */   public SecurityHeader createSecurityHeader(int headerLayout, String soapVersion, boolean mustUnderstandValue) {
/* 124 */     return new SecurityHeader(headerLayout, soapVersion, mustUnderstandValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecurityToken createBinarySecurityToken(String id, String valueType, String encodingType, byte[] token) {
/* 132 */     BinarySecurityTokenType bst = new BinarySecurityTokenType();
/* 133 */     bst.setValueType(valueType);
/* 134 */     bst.setId(id);
/* 135 */     bst.setEncodingType(encodingType);
/*     */     
/* 137 */     bst.setValue(token);
/* 138 */     return new BinarySecurityToken(bst, this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecurityToken createBinarySecurityToken(String id, byte[] cer) {
/* 146 */     return createBinarySecurityToken(id, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary", cer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecurityToken createKerberosBinarySecurityToken(String id, byte[] token) {
/* 154 */     return createBinarySecurityToken(id, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary", token);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference(Reference reference) {
/* 162 */     SecurityTokenReference str = new SecurityTokenReference(this.soapVersion);
/* 163 */     str.setReference(reference);
/* 164 */     return str;
/*     */   }
/*     */   
/*     */   public SecurityTokenReference createSecurityTokenReference() {
/* 168 */     SecurityTokenReference str = new SecurityTokenReference(this.soapVersion);
/* 169 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DirectReference createDirectReference() {
/* 177 */     return new DirectReference(this.soapVersion);
/*     */   }
/*     */   
/*     */   public KeyIdentifier createKeyIdentifier() {
/* 181 */     return new KeyIdentifier(this.soapVersion);
/*     */   }
/*     */   
/*     */   public X509Data createX509DataWithIssuerSerial(X509IssuerSerial xis) {
/* 185 */     X509Data x509Data = new X509Data(this.soapVersion);
/* 186 */     List<Object> list = new ArrayList();
/* 187 */     list.add(xis);
/* 188 */     x509Data.setX509IssuerSerialOrX509SKIOrX509SubjectName(list);
/* 189 */     return x509Data;
/*     */   }
/*     */   
/*     */   public GSHeaderElement createGSHeaderElement(JAXBElement el) {
/* 193 */     return new GSHeaderElement(el, this.soapVersion);
/*     */   }
/*     */   
/*     */   public GSHeaderElement createGSHeaderElement(Object obj) {
/* 197 */     return new GSHeaderElement(obj, this.soapVersion);
/*     */   }
/*     */   
/*     */   public SecurityContextToken createSecurityContextToken(URI identifier, String instance, String wsuId) {
/* 201 */     return new SecurityContextToken(identifier, instance, wsuId, this.soapVersion);
/*     */   }
/*     */   
/*     */   public SecurityContextToken createSecurityContextToken(SecurityContextTokenType sTokenType, String wsuId) {
/* 205 */     return new SecurityContextToken(sTokenType, this.soapVersion);
/*     */   }
/*     */   
/*     */   public X509IssuerSerial createX509IssuerSerial(String issuerName, BigInteger serialNumber) {
/* 209 */     X509IssuerSerial xis = new X509IssuerSerial(this.soapVersion);
/* 210 */     xis.setX509IssuerName(issuerName);
/* 211 */     xis.setX509SerialNumber(serialNumber);
/*     */     
/* 213 */     return xis;
/*     */   }
/*     */   
/*     */   public KeyInfo createKeyInfo(SecurityTokenReference str) {
/* 217 */     KeyInfo keyInfo = new KeyInfo();
/* 218 */     JAXBElement je = (new ObjectFactory()).createSecurityTokenReference((SecurityTokenReferenceType)str);
/* 219 */     List<JAXBElement> strList = Collections.singletonList(je);
/* 220 */     keyInfo.setContent(strList);
/* 221 */     return keyInfo;
/*     */   }
/*     */   
/*     */   public KeyInfo createKeyInfo(KeyValue keyValue) {
/* 225 */     KeyInfo keyInfo = new KeyInfo();
/* 226 */     JAXBElement je = (new ObjectFactory()).createKeyValue(keyValue);
/* 227 */     List<JAXBElement> strList = Collections.singletonList(je);
/* 228 */     keyInfo.setContent(strList);
/* 229 */     return keyInfo;
/*     */   }
/*     */   
/*     */   public KeyInfo createKeyInfo(KeyName name) {
/* 233 */     KeyInfo keyInfo = new KeyInfo();
/* 234 */     List<KeyName> strList = Collections.singletonList(name);
/* 235 */     keyInfo.setContent(strList);
/* 236 */     return keyInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public EncryptedData createEncryptedData(String id, Data data, String dataAlgo, KeyInfoType keyInfo, Key key, boolean contentOnly) {
/* 241 */     EncryptedDataType edt = new EncryptedDataType();
/* 242 */     if (contentOnly) {
/* 243 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Content");
/*     */     } else {
/* 245 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Element");
/*     */     } 
/* 247 */     EncryptionMethodType emt = new EncryptionMethodType();
/* 248 */     emt.setAlgorithm(dataAlgo);
/* 249 */     edt.setEncryptionMethod(emt);
/* 250 */     CipherDataType ct = new CipherDataType();
/* 251 */     ct.setCipherValue("ed".getBytes());
/* 252 */     edt.setCipherData(ct);
/* 253 */     edt.setId(id);
/* 254 */     if (keyInfo != null) {
/* 255 */       edt.setKeyInfo(keyInfo);
/*     */     }
/* 257 */     return (EncryptedData)new JAXBEncryptedData(edt, data, key, this.soapVersion);
/*     */   }
/*     */   
/*     */   public EncryptedData createEncryptedData(String id, Attachment attachment, String dataAlgo, KeyInfoType keyInfo, Key key, EncryptionTarget target) {
/* 261 */     AttachmentData attachData = new AttachmentData(attachment);
/* 262 */     String cid = "cid:" + attachment.getContentId();
/* 263 */     boolean contentOnly = target.getContentOnly();
/* 264 */     EncryptedDataType edt = new EncryptedDataType();
/* 265 */     if (contentOnly) {
/* 266 */       edt.setType("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Only");
/*     */     } else {
/* 268 */       edt.setType("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete");
/*     */     } 
/* 270 */     edt.setMimeType(attachment.getContentType());
/* 271 */     EncryptionMethodType emt = new EncryptionMethodType();
/* 272 */     emt.setAlgorithm(dataAlgo);
/* 273 */     edt.setEncryptionMethod(emt);
/* 274 */     CipherDataType ct = new CipherDataType();
/* 275 */     CipherReferenceType crt = new CipherReferenceType();
/* 276 */     crt.setURI(cid);
/* 277 */     TransformsType tst = new TransformsType();
/* 278 */     ArrayList<TransformType> ttList = new ArrayList<TransformType>();
/* 279 */     ArrayList list = target.getCipherReferenceTransforms();
/* 280 */     for (Object obj : list) {
/* 281 */       EncryptionTarget.Transform tr = (EncryptionTarget.Transform)obj;
/* 282 */       TransformType tt = new TransformType();
/* 283 */       tt.setAlgorithm(tr.getTransform());
/* 284 */       ttList.add(tt);
/*     */     } 
/* 286 */     if (!ttList.isEmpty()) {
/* 287 */       tst.setTransform(ttList);
/*     */     }
/* 289 */     crt.setTransforms(tst);
/* 290 */     ct.setCipherReference(crt);
/* 291 */     edt.setCipherData(ct);
/* 292 */     edt.setId(id);
/* 293 */     if (keyInfo != null) {
/* 294 */       edt.setKeyInfo(keyInfo);
/*     */     }
/* 296 */     return (EncryptedData)new JAXBEncryptedData(edt, (Data)attachData, key, this.soapVersion);
/*     */   }
/*     */   
/*     */   public EncryptedHeader createEncryptedHeader(String ehId, String edId, Data data, String dataAlgo, KeyInfoType keyInfo, Key key, boolean contentOnly) {
/* 300 */     EncryptedHeaderType eht = new EncryptedHeaderType();
/* 301 */     EncryptedDataType edt = new EncryptedDataType();
/* 302 */     if (contentOnly) {
/*     */       
/* 304 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Content");
/*     */     } else {
/* 306 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Element");
/*     */     } 
/* 308 */     EncryptionMethodType emt = new EncryptionMethodType();
/* 309 */     emt.setAlgorithm(dataAlgo);
/* 310 */     edt.setEncryptionMethod(emt);
/* 311 */     CipherDataType ct = new CipherDataType();
/* 312 */     ct.setCipherValue("ed".getBytes());
/* 313 */     edt.setCipherData(ct);
/* 314 */     edt.setId(edId);
/* 315 */     if (keyInfo != null) {
/* 316 */       edt.setKeyInfo(keyInfo);
/*     */     }
/* 318 */     eht.setEncryptedData(edt);
/* 319 */     eht.setId(ehId);
/* 320 */     if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 321 */       eht.setMustUnderstand(Boolean.valueOf(true));
/* 322 */     } else if (this.soapVersion == SOAPVersion.SOAP_12) {
/* 323 */       eht.setMustUnderstand12(Boolean.valueOf(true));
/*     */     } 
/* 325 */     EncryptedHeader eh = new EncryptedHeader(eht, data, key, this.soapVersion);
/* 326 */     return eh;
/*     */   }
/*     */   
/*     */   public EncryptedKey createEncryptedKey(String id, String keyEncAlgo, KeyInfo keyInfo, Key dkEK, Key dataEncKey) throws XWSSecurityException {
/* 330 */     EncryptedKeyType ekt = eoFactory.createEncryptedKeyType();
/* 331 */     EncryptionMethodType emt = eoFactory.createEncryptionMethodType();
/* 332 */     emt.setAlgorithm(keyEncAlgo);
/* 333 */     ekt.setEncryptionMethod(emt);
/* 334 */     ekt.setKeyInfo((KeyInfoType)keyInfo);
/* 335 */     CipherDataType ct = new CipherDataType();
/* 336 */     ct.setCipherValue("ek".getBytes());
/* 337 */     ekt.setCipherData(ct);
/* 338 */     ekt.setId(id);
/* 339 */     return (EncryptedKey)new JAXBEncryptedKey(ekt, dkEK, dataEncKey, this.soapVersion);
/*     */   }
/*     */   
/*     */   public JAXBElement<ReferenceType> createDataReference(SecurityElement se) {
/* 343 */     ReferenceType rt = eoFactory.createReferenceType();
/* 344 */     rt.setURI("#" + se.getId());
/* 345 */     return eoFactory.createReferenceListDataReference(rt);
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyInfoType createKeyInfoType(String keyAlgo, String refType, String refId) {
/* 350 */     return new KeyInfoType();
/*     */   }
/*     */   
/*     */   public DerivedKey createDerivedKey(String id, String algo, byte[] nonce, long offset, long length, String label, SecurityTokenReference str, String spVersion) {
/* 354 */     DerivedKeyTokenType dkt = new DerivedKeyTokenType();
/* 355 */     DerivedKeyTokenType dkt13 = new DerivedKeyTokenType();
/* 356 */     if (spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 357 */       dkt13.setId(id);
/* 358 */       dkt13.setOffset(BigInteger.valueOf(offset));
/* 359 */       dkt13.setNonce(nonce);
/* 360 */       dkt13.setLength(BigInteger.valueOf(length));
/* 361 */       dkt13.setSecurityTokenReference((SecurityTokenReferenceType)str);
/*     */ 
/*     */       
/* 364 */       return new DerivedKey(dkt13, this.soapVersion, spVersion);
/*     */     } 
/* 366 */     dkt.setId(id);
/* 367 */     dkt.setOffset(BigInteger.valueOf(offset));
/* 368 */     dkt.setNonce(nonce);
/* 369 */     dkt.setLength(BigInteger.valueOf(length));
/* 370 */     dkt.setSecurityTokenReference((SecurityTokenReferenceType)str);
/*     */ 
/*     */     
/* 373 */     return new DerivedKey(dkt, this.soapVersion, spVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   public DerivedKey createDerivedKey(String id, String algo, byte[] nonce, long offset, long length, String label, SecurityTokenReferenceType str, String spVersion) {
/* 378 */     DerivedKeyTokenType dkt = new DerivedKeyTokenType();
/* 379 */     DerivedKeyTokenType dkt13 = new DerivedKeyTokenType();
/* 380 */     if (spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 381 */       dkt13.setId(id);
/* 382 */       dkt13.setOffset(BigInteger.valueOf(offset));
/* 383 */       dkt13.setNonce(nonce);
/* 384 */       dkt13.setLength(BigInteger.valueOf(length));
/* 385 */       dkt13.setSecurityTokenReference(str);
/*     */ 
/*     */       
/* 388 */       return new DerivedKey(dkt13, this.soapVersion, spVersion);
/*     */     } 
/* 390 */     dkt.setId(id);
/* 391 */     dkt.setOffset(BigInteger.valueOf(offset));
/* 392 */     dkt.setNonce(nonce);
/* 393 */     dkt.setLength(BigInteger.valueOf(length));
/* 394 */     dkt.setSecurityTokenReference(str);
/*     */ 
/*     */     
/* 397 */     return new DerivedKey(dkt, this.soapVersion, spVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   public DerivedKey createDerivedKey(String id, String algo, byte[] nonce, long offset, long length, String label, SecurityTokenReferenceType str, String refId, String spVersion) {
/* 402 */     DerivedKeyTokenType dkt = new DerivedKeyTokenType();
/* 403 */     DerivedKeyTokenType dkt13 = new DerivedKeyTokenType();
/* 404 */     if (spVersion.equals("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200702")) {
/* 405 */       dkt13.setId(id);
/* 406 */       dkt13.setOffset(BigInteger.valueOf(offset));
/* 407 */       dkt13.setNonce(nonce);
/* 408 */       dkt13.setLength(BigInteger.valueOf(length));
/* 409 */       dkt13.setSecurityTokenReference(str);
/*     */ 
/*     */       
/* 412 */       return new DerivedKey(dkt13, this.soapVersion, refId, spVersion);
/*     */     } 
/* 414 */     dkt.setId(id);
/* 415 */     dkt.setOffset(BigInteger.valueOf(offset));
/* 416 */     dkt.setNonce(nonce);
/* 417 */     dkt.setLength(BigInteger.valueOf(length));
/* 418 */     dkt.setSecurityTokenReference(str);
/*     */ 
/*     */     
/* 421 */     return new DerivedKey(dkt, this.soapVersion, refId, spVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String convertAlgURIToTransformation(String algorithmURI) {
/* 426 */     return JCEMapper.translateURItoJCEID(algorithmURI);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\WSSElementFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */