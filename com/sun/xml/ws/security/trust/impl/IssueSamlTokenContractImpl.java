/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.KeyIdentifierImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
/*     */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.saml.Advice;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.AttributeStatement;
/*     */ import com.sun.xml.wss.saml.AudienceRestriction;
/*     */ import com.sun.xml.wss.saml.AudienceRestrictionCondition;
/*     */ import com.sun.xml.wss.saml.AuthenticationStatement;
/*     */ import com.sun.xml.wss.saml.AuthnContext;
/*     */ import com.sun.xml.wss.saml.AuthnStatement;
/*     */ import com.sun.xml.wss.saml.Conditions;
/*     */ import com.sun.xml.wss.saml.KeyInfoConfirmationData;
/*     */ import com.sun.xml.wss.saml.NameID;
/*     */ import com.sun.xml.wss.saml.NameIdentifier;
/*     */ import com.sun.xml.wss.saml.SAMLAssertionFactory;
/*     */ import com.sun.xml.wss.saml.SAMLException;
/*     */ import com.sun.xml.wss.saml.Subject;
/*     */ import com.sun.xml.wss.saml.SubjectConfirmation;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.SubjectType;
/*     */ import java.io.IOException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
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
/*     */ public class IssueSamlTokenContractImpl
/*     */   extends IssueSamlTokenContract
/*     */ {
/* 127 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*     */   public Token createSAMLAssertion(String appliesTo, String tokenType, String keyType, String assertionId, String issuer, Map<QName, List<String>> claimedAttrs, IssuedTokenContext context) throws WSTrustException {
/*     */     GenericToken genericToken;
/* 133 */     Token token = null;
/*     */ 
/*     */     
/* 136 */     TrustSPMetadata spMd = this.stsConfig.getTrustSPMetadata(appliesTo);
/* 137 */     if (spMd == null) {
/* 138 */       spMd = this.stsConfig.getTrustSPMetadata("default");
/*     */     }
/* 140 */     X509Certificate serCert = (X509Certificate)context.getOtherProperties().get("stsCertificate");
/* 141 */     if (serCert == null) {
/* 142 */       serCert = getServiceCertificate(spMd, appliesTo);
/*     */     }
/*     */ 
/*     */     
/* 146 */     KeyInfo keyInfo = createKeyInfo(keyType, serCert, context, appliesTo);
/*     */ 
/*     */     
/* 149 */     Assertion assertion = null;
/* 150 */     if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
/*     */       
/* 152 */       assertion = createSAML11Assertion(assertionId, issuer, appliesTo, keyInfo, claimedAttrs, keyType);
/* 153 */     } else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
/* 154 */       assertion = createSAML20Assertion(assertionId, issuer, appliesTo, keyInfo, claimedAttrs, keyType);
/*     */     } else {
/* 156 */       log.log(Level.SEVERE, LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/* 157 */       throw new WSTrustException(LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/*     */     } 
/*     */ 
/*     */     
/* 161 */     Object[] stsCertsAndPrikey = getSTSCertAndPrivateKey();
/* 162 */     X509Certificate stsCert = (X509Certificate)stsCertsAndPrikey[0];
/* 163 */     PrivateKey stsPrivKey = (PrivateKey)stsCertsAndPrikey[1];
/*     */ 
/*     */     
/* 166 */     Element signedAssertion = null;
/*     */     try {
/* 168 */       signedAssertion = assertion.sign(stsCert, stsPrivKey, true, context.getSignatureAlgorithm(), context.getCanonicalizationAlgorithm());
/*     */     
/*     */     }
/* 171 */     catch (SAMLException ex) {
/* 172 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 174 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 183 */     if (this.stsConfig.getEncryptIssuedToken()) {
/* 184 */       String keyWrapAlgo = (String)context.getOtherProperties().get("keyWrapAlgorithm");
/* 185 */       Element encData = encryptToken(signedAssertion, serCert, appliesTo, context.getEncryptionAlgorithm(), keyWrapAlgo);
/* 186 */       genericToken = new GenericToken(encData);
/*     */     }
/*     */     else {
/*     */       
/* 190 */       genericToken = new GenericToken(signedAssertion);
/*     */     } 
/*     */     
/* 193 */     return (Token)genericToken;
/*     */   }
/*     */   
/*     */   private EncryptedKey encryptKey(Document doc, byte[] encryptedKey, X509Certificate cert, String appliesTo, String keyWrapAlgorithm) throws WSTrustException {
/* 197 */     EncryptedKey encKey = null; try {
/*     */       XMLCipher cipher;
/* 199 */       PublicKey pubKey = cert.getPublicKey();
/*     */       
/* 201 */       if (keyWrapAlgorithm != null) {
/* 202 */         cipher = XMLCipher.getInstance(keyWrapAlgorithm);
/*     */       } else {
/* 204 */         cipher = XMLCipher.getInstance("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p");
/*     */       } 
/* 206 */       cipher.init(3, pubKey);
/*     */       
/* 208 */       encKey = cipher.encryptKey(doc, new SecretKeySpec(encryptedKey, "AES"));
/* 209 */       KeyInfo keyinfo = new KeyInfo(doc);
/*     */ 
/*     */       
/* 212 */       byte[] skid = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 213 */       if (skid != null && skid.length > 0) {
/* 214 */         KeyIdentifierImpl keyIdentifierImpl = new KeyIdentifierImpl("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier", null);
/* 215 */         keyIdentifierImpl.setValue(Base64.encode(skid));
/* 216 */         SecurityTokenReferenceImpl securityTokenReferenceImpl = new SecurityTokenReferenceImpl((Reference)keyIdentifierImpl);
/* 217 */         keyinfo.addUnknownElement((Element)doc.importNode(WSTrustElementFactory.newInstance().toElement((SecurityTokenReference)securityTokenReferenceImpl, null), true));
/*     */       } else {
/* 219 */         X509Data x509data = new X509Data(doc);
/* 220 */         x509data.addCertificate(cert);
/* 221 */         keyinfo.add(x509data);
/*     */       } 
/* 223 */       encKey.setKeyInfo(keyinfo);
/* 224 */     } catch (XWSSecurityException ex) {
/* 225 */       log.log(Level.SEVERE, LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), (Throwable)ex);
/*     */       
/* 227 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/* 228 */     } catch (XMLEncryptionException ex) {
/* 229 */       log.log(Level.SEVERE, LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), (Throwable)ex);
/*     */       
/* 231 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/* 232 */     } catch (XMLSecurityException ex) {
/* 233 */       log.log(Level.SEVERE, LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */       
/* 235 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */     } 
/*     */     
/* 238 */     return encKey;
/*     */   }
/*     */   
/*     */   private Element encryptToken(Element assertion, X509Certificate serCert, String appliesTo, String encryptionAlgorithm, String keyWrapAlgorithm) throws WSTrustException {
/* 242 */     Element encDataEle = null;
/*     */     
/*     */     try {
/*     */       XMLCipher cipher;
/* 246 */       if (encryptionAlgorithm != null) {
/* 247 */         cipher = XMLCipher.getInstance(encryptionAlgorithm);
/*     */       } else {
/* 249 */         cipher = XMLCipher.getInstance("http://www.w3.org/2001/04/xmlenc#aes256-cbc");
/*     */       } 
/* 251 */       int keysizeInBytes = 32;
/* 252 */       byte[] skey = WSTrustUtil.generateRandomSecret(32);
/* 253 */       cipher.init(1, new SecretKeySpec(skey, "AES"));
/*     */ 
/*     */       
/* 256 */       Document owner = assertion.getOwnerDocument();
/* 257 */       EncryptedData encData = cipher.encryptData(owner, assertion);
/* 258 */       String id = "uuid-" + UUID.randomUUID().toString();
/* 259 */       encData.setId(id);
/*     */       
/* 261 */       KeyInfo encKeyInfo = new KeyInfo(owner);
/* 262 */       EncryptedKey encKey = encryptKey(owner, skey, serCert, appliesTo, keyWrapAlgorithm);
/* 263 */       encKeyInfo.add(encKey);
/* 264 */       encData.setKeyInfo(encKeyInfo);
/*     */       
/* 266 */       encDataEle = cipher.martial(encData);
/* 267 */     } catch (XMLEncryptionException ex) {
/* 268 */       log.log(Level.SEVERE, LogStringsMessages.WST_0044_ERROR_ENCRYPT_ISSUED_TOKEN(appliesTo), (Throwable)ex);
/*     */       
/* 270 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/* 271 */     } catch (Exception ex) {
/* 272 */       log.log(Level.SEVERE, LogStringsMessages.WST_0044_ERROR_ENCRYPT_ISSUED_TOKEN(appliesTo), ex);
/*     */       
/* 274 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */     } 
/*     */ 
/*     */     
/* 278 */     return encDataEle;
/*     */   }
/*     */   
/*     */   private X509Certificate getServiceCertificate(TrustSPMetadata spMd, String appliesTo) throws WSTrustException {
/* 282 */     String certAlias = spMd.getCertAlias();
/* 283 */     X509Certificate cert = null;
/* 284 */     CallbackHandler callbackHandler = this.stsConfig.getCallbackHandler();
/* 285 */     if (callbackHandler != null) {
/*     */       
/* 287 */       EncryptionKeyCallback.AliasX509CertificateRequest req = new EncryptionKeyCallback.AliasX509CertificateRequest(spMd.getCertAlias());
/* 288 */       EncryptionKeyCallback callback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)req);
/* 289 */       Callback[] callbacks = { (Callback)callback };
/*     */       try {
/* 291 */         callbackHandler.handle(callbacks);
/* 292 */       } catch (IOException ex) {
/* 293 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */         
/* 295 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       }
/* 297 */       catch (UnsupportedCallbackException ex) {
/* 298 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */         
/* 300 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       } 
/*     */ 
/*     */       
/* 304 */       cert = req.getX509Certificate();
/*     */     } else {
/* 306 */       SecurityEnvironment secEnv = (SecurityEnvironment)this.stsConfig.getOtherOptions().get("SecurityEnvironment");
/*     */       try {
/* 308 */         cert = secEnv.getCertificate(this.stsConfig.getOtherOptions(), certAlias, false);
/* 309 */       } catch (XWSSecurityException ex) {
/* 310 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), (Throwable)ex);
/*     */         
/* 312 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 317 */     return cert;
/*     */   }
/*     */ 
/*     */   
/*     */   private Object[] getSTSCertAndPrivateKey() throws WSTrustException {
/* 322 */     X509Certificate stsCert = null;
/* 323 */     PrivateKey stsPrivKey = null;
/* 324 */     CallbackHandler callbackHandler = this.stsConfig.getCallbackHandler();
/* 325 */     if (callbackHandler != null) {
/* 326 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*     */       
/* 328 */       SignatureKeyCallback signatureKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)request);
/* 329 */       Callback[] callbacks = { (Callback)signatureKeyCallback };
/*     */       try {
/* 331 */         callbackHandler.handle(callbacks);
/* 332 */       } catch (IOException ex) {
/* 333 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */         
/* 335 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       }
/* 337 */       catch (UnsupportedCallbackException ex) {
/* 338 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */         
/* 340 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       } 
/*     */ 
/*     */       
/* 344 */       stsPrivKey = request.getPrivateKey();
/* 345 */       stsCert = request.getX509Certificate();
/*     */     } else {
/* 347 */       SecurityEnvironment secEnv = (SecurityEnvironment)this.stsConfig.getOtherOptions().get("SecurityEnvironment");
/*     */       try {
/* 349 */         stsCert = secEnv.getDefaultCertificate(this.stsConfig.getOtherOptions());
/* 350 */         stsPrivKey = secEnv.getPrivateKey(this.stsConfig.getOtherOptions(), stsCert);
/* 351 */       } catch (XWSSecurityException ex) {
/* 352 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), (Throwable)ex);
/*     */         
/* 354 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 359 */     Object[] results = new Object[2];
/* 360 */     results[0] = stsCert;
/* 361 */     results[1] = stsPrivKey;
/* 362 */     return results;
/*     */   }
/*     */   
/*     */   private KeyInfo createKeyInfo(String keyType, X509Certificate serCert, IssuedTokenContext ctx, String appliesTo) throws WSTrustException {
/* 366 */     Element kiEle = (Element)this.stsConfig.getOtherOptions().get("ConfirmationKeyInfo");
/* 367 */     if (kiEle != null) {
/*     */       try {
/* 369 */         return new KeyInfo(kiEle, null);
/* 370 */       } catch (XMLSecurityException ex) {
/* 371 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/* 372 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/*     */       } 
/*     */     }
/* 375 */     DocumentBuilderFactory docFactory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 376 */     Document doc = null;
/*     */     try {
/* 378 */       doc = docFactory.newDocumentBuilder().newDocument();
/* 379 */     } catch (ParserConfigurationException ex) {
/* 380 */       log.log(Level.SEVERE, LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */       
/* 382 */       throw new WSTrustException(LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */     } 
/*     */     
/* 385 */     KeyInfo keyInfo = new KeyInfo(doc);
/* 386 */     if (this.wstVer.getSymmetricKeyTypeURI().equals(keyType)) {
/* 387 */       byte[] key = ctx.getProofKey();
/* 388 */       if (this.stsConfig.getEncryptIssuedKey()) {
/* 389 */         EncryptedKey encKey = encryptKey(doc, key, serCert, appliesTo, (String)null);
/*     */         try {
/* 391 */           keyInfo.add(encKey);
/* 392 */         } catch (XMLEncryptionException ex) {
/* 393 */           log.log(Level.SEVERE, LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), (Throwable)ex);
/*     */           
/* 395 */           throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */         } 
/*     */       } else {
/* 398 */         BinarySecret secret = this.eleFac.createBinarySecret(key, this.wstVer.getSymmetricKeyTypeURI());
/* 399 */         Element bsEle = this.eleFac.toElement(secret, doc);
/* 400 */         keyInfo.addUnknownElement(bsEle);
/*     */       } 
/* 402 */     } else if (this.wstVer.getPublicKeyTypeURI().equals(keyType)) {
/* 403 */       X509Data x509data = new X509Data(doc);
/*     */       try {
/* 405 */         x509data.addCertificate(ctx.getRequestorCertificate());
/* 406 */       } catch (XMLSecurityException ex) {
/* 407 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/* 408 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/*     */       } 
/* 410 */       keyInfo.add(x509data);
/*     */     } 
/*     */     
/* 413 */     return keyInfo;
/*     */   }
/*     */   
/*     */   protected Assertion createSAML11Assertion(String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs, String keyType) throws WSTrustException {
/* 417 */     Assertion assertion = null;
/*     */     try {
/* 419 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml1.1");
/*     */       
/* 421 */       TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
/* 422 */       GregorianCalendar issuerInst = new GregorianCalendar(utcTimeZone);
/* 423 */       GregorianCalendar notOnOrAfter = new GregorianCalendar(utcTimeZone);
/* 424 */       notOnOrAfter.add(14, (int)this.stsConfig.getIssuedTokenTimeout());
/*     */       
/* 426 */       List<AudienceRestrictionCondition> arc = null;
/* 427 */       if (appliesTo != null) {
/* 428 */         arc = new ArrayList<AudienceRestrictionCondition>();
/* 429 */         List<String> au = new ArrayList<String>();
/* 430 */         au.add(appliesTo);
/* 431 */         arc.add(samlFac.createAudienceRestrictionCondition(au));
/*     */       } 
/* 433 */       List<String> confirmMethods = new ArrayList<String>();
/* 434 */       String confirMethod = (String)this.stsConfig.getOtherOptions().get("Saml-Confirmation-Method");
/* 435 */       if (confirMethod == null) {
/* 436 */         if (keyType.equals(this.wstVer.getBearerKeyTypeURI())) {
/* 437 */           confirMethod = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
/*     */         } else {
/* 439 */           confirMethod = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
/*     */         } 
/*     */       }
/*     */       
/* 443 */       Element keyInfoEle = null;
/* 444 */       if (keyInfo != null && !this.wstVer.getBearerKeyTypeURI().equals(keyType)) {
/* 445 */         keyInfoEle = keyInfo.getElement();
/*     */       }
/* 447 */       confirmMethods.add(confirMethod);
/*     */       
/* 449 */       SubjectConfirmation subjectConfirm = samlFac.createSubjectConfirmation(confirmMethods, null, keyInfoEle);
/*     */       
/* 451 */       Conditions conditions = samlFac.createConditions(issuerInst, notOnOrAfter, null, arc, null);
/*     */       
/* 453 */       Advice advice = samlFac.createAdvice(null, null, null);
/*     */       
/* 455 */       Subject subj = null;
/*     */       
/* 457 */       QName idName = null;
/* 458 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 459 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 460 */         QName attrKey = entry.getKey();
/* 461 */         List<String> values = entry.getValue();
/* 462 */         if (values != null && values.size() > 0 && 
/* 463 */           "NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 464 */           NameIdentifier nameId = samlFac.createNameIdentifier(values.get(0), attrKey.getNamespaceURI(), null);
/* 465 */           subj = samlFac.createSubject(nameId, subjectConfirm);
/* 466 */           idName = attrKey;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 474 */       if (idName != null) {
/* 475 */         claimedAttrs.remove(idName);
/*     */       }
/*     */       
/* 478 */       List<Object> statements = new ArrayList();
/*     */       
/* 480 */       if (claimedAttrs.isEmpty()) {
/* 481 */         AuthenticationStatement statement = samlFac.createAuthenticationStatement(null, issuerInst, subj, null, null);
/* 482 */         statements.add(statement);
/*     */       } else {
/* 484 */         AttributeStatement statement = samlFac.createAttributeStatement(subj, null);
/* 485 */         statements.add(statement);
/*     */       } 
/* 487 */       assertion = samlFac.createAssertion(assertionId, issuer, issuerInst, conditions, advice, statements);
/*     */       
/* 489 */       if (!claimedAttrs.isEmpty()) {
/* 490 */         return WSTrustUtil.addSamlAttributes(assertion, claimedAttrs);
/*     */       }
/* 492 */     } catch (SAMLException ex) {
/* 493 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 495 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     }
/* 497 */     catch (XWSSecurityException ex) {
/* 498 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 500 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 504 */     return assertion;
/*     */   }
/*     */   
/*     */   protected Assertion createSAML20Assertion(String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs, String keyType) throws WSTrustException {
/* 508 */     Assertion assertion = null;
/*     */     try {
/* 510 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml2.0");
/*     */ 
/*     */       
/* 513 */       TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
/* 514 */       GregorianCalendar issueInst = new GregorianCalendar(utcTimeZone);
/* 515 */       GregorianCalendar notOnOrAfter = new GregorianCalendar(utcTimeZone);
/* 516 */       notOnOrAfter.add(14, (int)this.stsConfig.getIssuedTokenTimeout());
/*     */       
/* 518 */       List<AudienceRestriction> arc = null;
/* 519 */       if (appliesTo != null) {
/* 520 */         arc = new ArrayList<AudienceRestriction>();
/* 521 */         List<String> au = new ArrayList<String>();
/* 522 */         au.add(appliesTo);
/* 523 */         arc.add(samlFac.createAudienceRestriction(au));
/*     */       } 
/*     */       
/* 526 */       KeyInfoConfirmationData keyInfoConfData = null;
/* 527 */       String confirMethod = (String)this.stsConfig.getOtherOptions().get("Saml-Confirmation-Method");
/* 528 */       if (confirMethod == null) {
/* 529 */         if (keyType.equals(this.wstVer.getBearerKeyTypeURI())) {
/* 530 */           confirMethod = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
/*     */         } else {
/* 532 */           confirMethod = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
/* 533 */           if (keyInfo != null) {
/* 534 */             keyInfoConfData = samlFac.createKeyInfoConfirmationData(keyInfo.getElement());
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 539 */       Conditions conditions = samlFac.createConditions(issueInst, notOnOrAfter, null, arc, null, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 547 */       SubjectConfirmation subjectConfirm = samlFac.createSubjectConfirmation(null, keyInfoConfData, confirMethod);
/*     */ 
/*     */       
/* 550 */       Subject subj = null;
/*     */       
/* 552 */       QName idName = null;
/* 553 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 554 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 555 */         QName attrKey = entry.getKey();
/* 556 */         List<String> values = entry.getValue();
/* 557 */         if (values != null && values.size() > 0 && 
/* 558 */           "NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 559 */           NameID nameId = samlFac.createNameID(values.get(0), attrKey.getNamespaceURI(), null);
/* 560 */           subj = samlFac.createSubject(nameId, subjectConfirm);
/* 561 */           idName = attrKey;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 570 */       if (idName != null) {
/* 571 */         claimedAttrs.remove(idName);
/*     */       }
/*     */       
/* 574 */       List<Object> statements = new ArrayList();
/*     */       
/* 576 */       if (claimedAttrs.isEmpty()) {
/* 577 */         AuthnContext ctx = samlFac.createAuthnContext(this.authnCtxClass, null);
/* 578 */         AuthnStatement statement = samlFac.createAuthnStatement(issueInst, null, ctx, null, null);
/* 579 */         statements.add(statement);
/*     */       } else {
/* 581 */         AttributeStatement statement = samlFac.createAttributeStatement(null);
/* 582 */         statements.add(statement);
/*     */       } 
/*     */       
/* 585 */       NameID issuerID = samlFac.createNameID(issuer, null, null);
/*     */ 
/*     */       
/* 588 */       assertion = samlFac.createAssertion(assertionId, issuerID, issueInst, conditions, null, null, statements);
/*     */       
/* 590 */       if (!claimedAttrs.isEmpty()) {
/* 591 */         assertion = WSTrustUtil.addSamlAttributes(assertion, claimedAttrs);
/*     */       }
/* 593 */       ((Assertion)assertion).setSubject((SubjectType)subj);
/*     */     }
/* 595 */     catch (SAMLException ex) {
/* 596 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 598 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     }
/* 600 */     catch (XWSSecurityException ex) {
/* 601 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 603 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 607 */     return assertion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\IssueSamlTokenContractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */