/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.KeyInfoType;
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.security.core.xenc.CipherDataType;
/*     */ import com.sun.xml.security.core.xenc.EncryptedDataType;
/*     */ import com.sun.xml.security.core.xenc.EncryptionMethodType;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Signature;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.DSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.X509Data;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBSignContext;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBSignatureFactory;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.EnvelopedSignedMessageHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.dsig.JAXBSignatureHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedData;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SAMLToken;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
/*     */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.Attribute;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLAssertionFactory;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmationData;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.io.IOException;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.Key;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.security.interfaces.DSAPublicKey;
/*     */ import java.security.interfaces.RSAPublicKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLSignContext;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SBIssuedSamlTokenContractImpl
/*     */   extends IssueSamlTokenContract
/*     */ {
/*     */   private static final String SAML_HOLDER_OF_KEY = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
/*     */   protected static final String PRINCIPAL = "principal";
/* 158 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/* 159 */   WSSElementFactory wef = new WSSElementFactory(SOAPVersion.SOAP_11);
/*     */   
/* 161 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SBIssuedSamlTokenContractImpl(SOAPVersion soapVersion) {
/* 168 */     this.soapVersion = soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public SBIssuedSamlTokenContractImpl() {}
/*     */   
/*     */   public Token createSAMLAssertion(String appliesTo, String tokenType, String keyType, String assertionId, String issuer, Map<QName, List<String>> claimedAttrs, IssuedTokenContext context) throws WSTrustException {
/*     */     GenericToken genericToken;
/* 176 */     Token token = null;
/*     */     
/* 178 */     CallbackHandler callbackHandler = this.stsConfig.getCallbackHandler();
/*     */     
/*     */     try {
/* 181 */       NamespaceContextEx nsContext = null;
/* 182 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 183 */         nsContext = new NamespaceContextEx();
/*     */       } else {
/* 185 */         nsContext = new NamespaceContextEx(true);
/*     */       } 
/* 187 */       nsContext.addEncryptionNS();
/* 188 */       nsContext.addExc14NS();
/* 189 */       nsContext.addSAMLNS();
/* 190 */       nsContext.addSignatureNS();
/* 191 */       nsContext.addWSSNS();
/*     */       
/* 193 */       X509Certificate serCert = getServiceCertificate(callbackHandler, this.stsConfig.getTrustSPMetadata(appliesTo), appliesTo);
/*     */ 
/*     */       
/* 196 */       KeyInfo keyInfo = createKeyInfo(keyType, serCert, context);
/*     */ 
/*     */       
/* 199 */       Assertion assertion = null;
/* 200 */       SAMLToken samlToken = null;
/* 201 */       if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
/*     */         
/* 203 */         assertion = createSAML11Assertion(assertionId, issuer, appliesTo, keyInfo, claimedAttrs);
/* 204 */         samlToken = new SAMLToken(assertion, SAMLJAXBUtil.getJAXBContext(), this.soapVersion);
/*     */       }
/* 206 */       else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
/* 207 */         assertion = createSAML20Assertion(assertionId, issuer, appliesTo, keyInfo, claimedAttrs);
/* 208 */         samlToken = new SAMLToken(assertion, SAMLJAXBUtil.getJAXBContext(), this.soapVersion);
/*     */       } else {
/* 210 */         log.log(Level.SEVERE, LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/*     */         
/* 212 */         throw new WSTrustException(LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/*     */       } 
/*     */ 
/*     */       
/* 216 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*     */       
/* 218 */       SignatureKeyCallback signatureKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)request);
/* 219 */       Callback[] callbacks = { (Callback)signatureKeyCallback };
/* 220 */       callbackHandler.handle(callbacks);
/* 221 */       PrivateKey stsPrivKey = request.getPrivateKey();
/*     */ 
/*     */ 
/*     */       
/* 225 */       SecurityHeaderElement signedAssertion = createSignature(request.getX509Certificate().getPublicKey(), stsPrivKey, samlToken, nsContext);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 230 */       genericToken = new GenericToken(signedAssertion);
/*     */       
/* 232 */       if (this.stsConfig.getEncryptIssuedToken()) {
/* 233 */         String id = "uuid-" + UUID.randomUUID().toString();
/* 234 */         int keysizeInBytes = 32;
/* 235 */         byte[] skey = WSTrustUtil.generateRandomSecret(32);
/* 236 */         Key key = new SecretKeySpec(skey, "AES");
/*     */         
/* 238 */         KeyInfo encKeyInfo = new KeyInfo();
/* 239 */         EncryptedKey encKey = encryptKey(key, serCert);
/* 240 */         encKeyInfo.getContent().add(encKey);
/* 241 */         EncryptedDataType edt = createEncryptedData(id, "http://www.w3.org/2001/04/xmlenc#aes256-cbc", encKeyInfo, false);
/*     */ 
/*     */         
/* 244 */         JAXBEncryptedData jed = new JAXBEncryptedData(edt, (Data)new SSEData((SecurityElement)signedAssertion, false, (NamespaceContextEx)nsContext), this.soapVersion);
/* 245 */         genericToken = new GenericToken((SecurityHeaderElement)jed);
/*     */       } else {
/* 247 */         genericToken = new GenericToken(signedAssertion);
/*     */       } 
/* 249 */     } catch (XWSSecurityException ex) {
/* 250 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 252 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/* 253 */     } catch (Exception ex) {
/* 254 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */       
/* 256 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/* 258 */     return (Token)genericToken;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Assertion createSAML11Assertion(String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs) throws WSTrustException {
/* 264 */     Assertion assertion = null;
/*     */     try {
/* 266 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml1.1");
/*     */       
/* 268 */       GregorianCalendar issuerInst = new GregorianCalendar();
/* 269 */       GregorianCalendar notOnOrAfter = new GregorianCalendar();
/* 270 */       notOnOrAfter.add(14, (int)this.stsConfig.getIssuedTokenTimeout());
/*     */       
/* 272 */       Conditions conditions = samlFac.createConditions(issuerInst, notOnOrAfter, null, null, null);
/*     */       
/* 274 */       Advice advice = samlFac.createAdvice(null, null, null);
/*     */       
/* 276 */       List<String> confirmMethods = new ArrayList<String>();
/* 277 */       confirmMethods.add("urn:oasis:names:tc:SAML:1.0:cm:holder-of-key");
/*     */       
/* 279 */       SubjectConfirmation subjectConfirm = samlFac.createSubjectConfirmation(confirmMethods, null, keyInfo);
/*     */       
/* 281 */       Subject subj = null;
/* 282 */       List<Attribute> attrs = new ArrayList<Attribute>();
/* 283 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 284 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 285 */         QName attrKey = entry.getKey();
/* 286 */         List<String> values = entry.getValue();
/* 287 */         if (values != null && values.size() > 0) {
/* 288 */           if ("NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 289 */             NameIdentifier nameId = samlFac.createNameIdentifier(values.get(0), attrKey.getNamespaceURI(), null);
/* 290 */             subj = samlFac.createSubject(nameId, subjectConfirm);
/*     */             continue;
/*     */           } 
/* 293 */           Attribute attr = samlFac.createAttribute(attrKey.getLocalPart(), attrKey.getNamespaceURI(), values);
/* 294 */           attrs.add(attr);
/*     */         } 
/*     */       } 
/*     */       
/* 298 */       AttributeStatement statement = samlFac.createAttributeStatement(subj, attrs);
/* 299 */       List<AttributeStatement> statements = new ArrayList<AttributeStatement>();
/* 300 */       statements.add(statement);
/* 301 */       assertion = samlFac.createAssertion(assertionId, issuer, issuerInst, conditions, advice, statements);
/*     */     }
/* 303 */     catch (SAMLException ex) {
/* 304 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 306 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/* 307 */     } catch (XWSSecurityException ex) {
/* 308 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 310 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */     
/* 313 */     return assertion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Assertion createSAML20Assertion(String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs) throws WSTrustException {
/* 319 */     Assertion assertion = null;
/*     */     try {
/* 321 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml2.0");
/*     */ 
/*     */       
/* 324 */       GregorianCalendar issueInst = new GregorianCalendar();
/* 325 */       GregorianCalendar notOnOrAfter = new GregorianCalendar();
/* 326 */       notOnOrAfter.add(14, (int)this.stsConfig.getIssuedTokenTimeout());
/*     */       
/* 328 */       Conditions conditions = samlFac.createConditions(issueInst, notOnOrAfter, null, null, null, null);
/*     */ 
/*     */ 
/*     */       
/* 332 */       SubjectConfirmationData subjComfData = samlFac.createSubjectConfirmationData(null, null, issueInst, notOnOrAfter, appliesTo, keyInfo);
/*     */ 
/*     */       
/* 335 */       SubjectConfirmation subConfirmation = samlFac.createSubjectConfirmation(null, subjComfData, "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key");
/*     */ 
/*     */       
/* 338 */       Subject subj = null;
/* 339 */       List<Attribute> attrs = new ArrayList<Attribute>();
/* 340 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 341 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 342 */         QName attrKey = entry.getKey();
/* 343 */         List<String> values = entry.getValue();
/* 344 */         if (values != null && values.size() > 0) {
/* 345 */           if ("NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 346 */             NameIdentifier nameId = samlFac.createNameIdentifier(values.get(0), attrKey.getNamespaceURI(), null);
/* 347 */             subj = samlFac.createSubject(nameId, subConfirmation);
/*     */             continue;
/*     */           } 
/* 350 */           Attribute attr = samlFac.createAttribute(attrKey.getLocalPart(), values);
/* 351 */           attrs.add(attr);
/*     */         } 
/*     */       } 
/*     */       
/* 355 */       AttributeStatement statement = samlFac.createAttributeStatement(attrs);
/* 356 */       List<AttributeStatement> statements = new ArrayList<AttributeStatement>();
/* 357 */       statements.add(statement);
/*     */       
/* 359 */       NameID issuerID = samlFac.createNameID(issuer, null, null);
/*     */ 
/*     */       
/* 362 */       assertion = samlFac.createAssertion(assertionId, issuerID, issueInst, conditions, null, subj, statements);
/*     */     }
/* 364 */     catch (SAMLException ex) {
/* 365 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 367 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/* 368 */     } catch (XWSSecurityException ex) {
/* 369 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 371 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */     
/* 374 */     return assertion;
/*     */   }
/*     */   
/*     */   private KeyInfo createKeyInfo(String keyType, X509Certificate serCert, IssuedTokenContext ctx) throws WSTrustException {
/* 378 */     KeyInfo keyInfo = new KeyInfo();
/* 379 */     if ("http://schemas.xmlsoap.org/ws/2005/02/trust/SymmetricKey".equals(keyType)) {
/* 380 */       byte[] key = ctx.getProofKey();
/* 381 */       if (!this.stsConfig.getEncryptIssuedToken() && this.stsConfig.getEncryptIssuedKey()) {
/*     */         try {
/* 383 */           Key secKey = new SecretKeySpec(key, "AES");
/* 384 */           EncryptedKey encKey = encryptKey(secKey, serCert);
/* 385 */           keyInfo.getContent().add(encKey);
/* 386 */         } catch (Exception ex) {
/* 387 */           throw new WSTrustException(ex.getMessage(), ex);
/*     */         } 
/*     */       } else {
/* 390 */         BinarySecret secret = this.eleFac.createBinarySecret(key, this.wstVer.getSymmetricKeyTypeURI());
/* 391 */         keyInfo.getContent().add(secret);
/*     */       } 
/* 393 */     } else if ("http://schemas.xmlsoap.org/ws/2005/02/trust/PublicKey".equals(keyType)) {
/*     */       
/* 395 */       X509Data x509Data = new X509Data();
/* 396 */       Set<Object> certs = ctx.getRequestorSubject().getPublicCredentials();
/* 397 */       if (certs == null) {
/* 398 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */         
/* 400 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */       } 
/* 402 */       boolean addedClientCert = false;
/* 403 */       ObjectFactory dsigOF = new ObjectFactory();
/* 404 */       for (Object o : certs) {
/* 405 */         if (o instanceof X509Certificate) {
/* 406 */           JAXBElement<byte[]> certElement; X509Certificate clientCert = (X509Certificate)o;
/*     */ 
/*     */           
/*     */           try {
/* 410 */             certElement = dsigOF.createX509DataTypeX509Certificate(clientCert.getEncoded());
/* 411 */           } catch (CertificateEncodingException ex) {
/*     */             
/* 413 */             throw new WSTrustException("Unable to create KeyInfo", ex);
/*     */           } 
/* 415 */           List<Object> x509DataContent = x509Data.getContent();
/* 416 */           x509DataContent.add(certElement);
/* 417 */           addedClientCert = true;
/*     */         } 
/*     */       } 
/*     */       
/* 421 */       if (!addedClientCert) {
/* 422 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */         
/* 424 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */       } 
/* 426 */       keyInfo.getContent().add(x509Data);
/*     */     } 
/*     */     
/* 429 */     return keyInfo;
/*     */   }
/*     */   
/*     */   private EncryptedKey encryptKey(Key key, X509Certificate cert) throws XWSSecurityException {
/* 433 */     KeyInfo keyInfo = null;
/* 434 */     KeyIdentifier keyIdentifier = this.wef.createKeyIdentifier();
/* 435 */     keyIdentifier.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/* 436 */     keyIdentifier.updateReferenceValue(cert);
/* 437 */     keyIdentifier.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/* 438 */     SecurityTokenReference securityTokenReference = this.wef.createSecurityTokenReference((Reference)keyIdentifier);
/* 439 */     keyInfo = this.wef.createKeyInfo(securityTokenReference);
/*     */     
/* 441 */     return this.wef.createEncryptedKey(null, "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", keyInfo, cert.getPublicKey(), key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private X509Certificate getServiceCertificate(CallbackHandler callbackHandler, TrustSPMetadata spMd, String appliesTo) throws WSTrustException {
/* 448 */     EncryptionKeyCallback.AliasX509CertificateRequest req = new EncryptionKeyCallback.AliasX509CertificateRequest(spMd.getCertAlias());
/* 449 */     EncryptionKeyCallback callback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)req);
/* 450 */     Callback[] callbacks = { (Callback)callback };
/*     */     try {
/* 452 */       callbackHandler.handle(callbacks);
/* 453 */     } catch (IOException ex) {
/* 454 */       log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       
/* 456 */       throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/* 457 */     } catch (UnsupportedCallbackException ex) {
/* 458 */       log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       
/* 460 */       throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */     } 
/*     */     
/* 463 */     return req.getX509Certificate();
/*     */   }
/*     */ 
/*     */   
/*     */   public EncryptedDataType createEncryptedData(String id, String dataEncAlgo, KeyInfo keyInfo, boolean contentOnly) {
/* 468 */     EncryptedDataType edt = new EncryptedDataType();
/* 469 */     if (contentOnly) {
/* 470 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Content");
/*     */     } else {
/* 472 */       edt.setType("http://www.w3.org/2001/04/xmlenc#Element");
/*     */     } 
/* 474 */     EncryptionMethodType emt = new EncryptionMethodType();
/* 475 */     emt.setAlgorithm(dataEncAlgo);
/* 476 */     edt.setEncryptionMethod(emt);
/* 477 */     CipherDataType cipherType = new CipherDataType();
/* 478 */     cipherType.setCipherValue("ed".getBytes());
/* 479 */     edt.setCipherData(cipherType);
/* 480 */     edt.setId(id);
/* 481 */     if (keyInfo != null) {
/* 482 */       edt.setKeyInfo((KeyInfoType)keyInfo);
/*     */     }
/* 484 */     return edt;
/*     */   }
/*     */   private SecurityHeaderElement createSignature(PublicKey pubKey, Key signingKey, SAMLToken samlToken, NamespaceContextEx nsContext) throws WSTrustException {
/*     */     try {
/*     */       KeyValue keyValue;
/* 489 */       JAXBSignatureFactory signatureFactory = JAXBSignatureFactory.newInstance();
/* 490 */       C14NMethodParameterSpec spec = null;
/* 491 */       CanonicalizationMethod canonicalMethod = signatureFactory.newCanonicalizationMethod("http://www.w3.org/2001/10/xml-exc-c14n#", spec);
/*     */ 
/*     */       
/* 494 */       DigestMethod digestMethod = signatureFactory.newDigestMethod("http://www.w3.org/2000/09/xmldsig#sha1", null);
/*     */       
/* 496 */       SignatureMethod signatureMethod = signatureFactory.newSignatureMethod("http://www.w3.org/2000/09/xmldsig#rsa-sha1", null);
/*     */ 
/*     */ 
/*     */       
/* 500 */       ArrayList<Transform> transformList = new ArrayList<Transform>();
/*     */ 
/*     */       
/* 503 */       Transform tr1 = signatureFactory.newTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature", (TransformParameterSpec)null);
/*     */ 
/*     */ 
/*     */       
/* 507 */       Transform tr2 = signatureFactory.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", (TransformParameterSpec)null);
/*     */       
/* 509 */       transformList.add(tr1);
/* 510 */       transformList.add(tr2);
/*     */       
/* 512 */       String uri = "#uuid-" + UUID.randomUUID().toString();
/* 513 */       Reference ref = signatureFactory.newReference(uri, digestMethod, transformList, null, null);
/*     */ 
/*     */       
/* 516 */       SignedInfo signedInfo = signatureFactory.newSignedInfo(canonicalMethod, signatureMethod, Collections.singletonList(ref));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 521 */       if (pubKey instanceof DSAPublicKey) {
/* 522 */         DSAKeyValue dsa = null;
/* 523 */         DSAPublicKey key = (DSAPublicKey)pubKey;
/*     */         
/* 525 */         byte[] paramP = key.getParams().getP().toByteArray();
/* 526 */         byte[] paramQ = key.getParams().getQ().toByteArray();
/* 527 */         byte[] paramG = key.getParams().getG().toByteArray();
/* 528 */         byte[] paramY = key.getY().toByteArray();
/* 529 */         dsa = signatureFactory.newDSAKeyValue(paramP, paramQ, paramG, paramY, null, null, null);
/* 530 */         keyValue = signatureFactory.newKeyValue(Collections.singletonList(dsa));
/*     */       }
/* 532 */       else if (pubKey instanceof RSAPublicKey) {
/* 533 */         RSAKeyValue rsa = null;
/* 534 */         RSAPublicKey key = (RSAPublicKey)pubKey;
/* 535 */         rsa = signatureFactory.newRSAKeyValue(key.getModulus().toByteArray(), key.getPublicExponent().toByteArray());
/* 536 */         keyValue = signatureFactory.newKeyValue(Collections.singletonList(rsa));
/*     */       } else {
/* 538 */         throw new WSTrustException("Unsupported PublicKey");
/*     */       } 
/*     */ 
/*     */       
/* 542 */       KeyInfo keyInfo = signatureFactory.newKeyInfo(Collections.singletonList(keyValue));
/* 543 */       JAXBSignContext signContext = new JAXBSignContext(signingKey);
/*     */       
/* 545 */       SSEData data = null;
/* 546 */       signContext.setURIDereferencer(new DSigResolver((Data)data));
/* 547 */       Signature signature = (Signature)signatureFactory.newXMLSignature(signedInfo, keyInfo);
/* 548 */       JAXBSignatureHeaderElement jhe = new JAXBSignatureHeaderElement(signature, this.soapVersion, (XMLSignContext)signContext);
/* 549 */       return (SecurityHeaderElement)new EnvelopedSignedMessageHeader((SecurityHeaderElement)samlToken, (Reference)ref, jhe, nsContext);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 554 */     catch (NoSuchAlgorithmException ex) {
/* 555 */       log.log(Level.SEVERE, LogStringsMessages.WST_0035_UNABLE_CREATE_SIGN_SAML_ASSERTION(), ex);
/*     */       
/* 557 */       throw new WSTrustException(LogStringsMessages.WST_0035_UNABLE_CREATE_SIGN_SAML_ASSERTION(), ex);
/* 558 */     } catch (InvalidAlgorithmParameterException ex) {
/* 559 */       log.log(Level.SEVERE, LogStringsMessages.WST_0035_UNABLE_CREATE_SIGN_SAML_ASSERTION(), ex);
/*     */       
/* 561 */       throw new WSTrustException(LogStringsMessages.WST_0035_UNABLE_CREATE_SIGN_SAML_ASSERTION(), ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DSigResolver implements URIDereferencer {
/* 566 */     public Data data = null;
/*     */     DSigResolver(Data data) {
/* 568 */       this.data = data;
/*     */     }
/*     */     public Data dereference(URIReference uRIReference, XMLCryptoContext xMLCryptoContext) throws URIReferenceException {
/* 571 */       return this.data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\SBIssuedSamlTokenContractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */