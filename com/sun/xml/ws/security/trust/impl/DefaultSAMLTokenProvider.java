/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.xml.ws.api.security.trust.STSTokenProvider;
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
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
/*     */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*     */ import java.security.PrivateKey;
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
/*     */ public class DefaultSAMLTokenProvider
/*     */   implements STSTokenProvider
/*     */ {
/* 109 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */   
/*     */   protected static final String SAML_HOLDER_OF_KEY_1_0 = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
/*     */   
/*     */   protected static final String SAML_HOLDER_OF_KEY_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
/*     */   
/*     */   protected static final String SAML_BEARER_1_0 = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
/*     */   
/*     */   protected static final String SAML_BEARER_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
/*     */   
/*     */   protected static final String SAML_SENDER_VOUCHES_1_0 = "urn:oasis:names:tc:SAML:1.0:cm:sender-vouches";
/*     */   protected static final String SAML_SENDER_VOUCHES_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
/*     */   
/*     */   public void generateToken(IssuedTokenContext ctx) throws WSTrustException {
/* 123 */     String issuer = ctx.getTokenIssuer();
/* 124 */     String appliesTo = ctx.getAppliesTo();
/* 125 */     String tokenType = ctx.getTokenType();
/* 126 */     String keyType = ctx.getKeyType();
/* 127 */     int tokenLifeSpan = (int)(ctx.getExpirationTime().getTime() - ctx.getCreationTime().getTime());
/* 128 */     String confirMethod = (String)ctx.getOtherProperties().get("samlConfirmationMethod");
/* 129 */     Map<QName, List<String>> claimedAttrs = (Map<QName, List<String>>)ctx.getOtherProperties().get("cliamedAttributes");
/* 130 */     WSTrustVersion wstVer = (WSTrustVersion)ctx.getOtherProperties().get("wstVersion");
/*     */ 
/*     */ 
/*     */     
/* 134 */     KeyInfo keyInfo = createKeyInfo(ctx);
/*     */ 
/*     */     
/* 137 */     String assertionId = "uuid-" + UUID.randomUUID().toString();
/*     */ 
/*     */     
/* 140 */     Assertion assertion = null;
/* 141 */     SecurityTokenReference samlReference = null;
/* 142 */     if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
/*     */       
/* 144 */       assertion = createSAML11Assertion(wstVer, tokenLifeSpan, confirMethod, assertionId, issuer, appliesTo, keyInfo, claimedAttrs, keyType);
/* 145 */       samlReference = WSTrustUtil.createSecurityTokenReference(assertionId, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID");
/* 146 */     } else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(tokenType)) {
/*     */       
/* 148 */       String authnCtx = (String)ctx.getOtherProperties().get("authnContext");
/* 149 */       assertion = createSAML20Assertion(wstVer, tokenLifeSpan, confirMethod, assertionId, issuer, appliesTo, keyInfo, claimedAttrs, keyType, authnCtx);
/* 150 */       samlReference = WSTrustUtil.createSecurityTokenReference(assertionId, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID");
/*     */ 
/*     */       
/* 153 */       samlReference.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");
/*     */     } else {
/* 155 */       log.log(Level.SEVERE, LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/* 156 */       throw new WSTrustException(LogStringsMessages.WST_0031_UNSUPPORTED_TOKEN_TYPE(tokenType, appliesTo));
/*     */     } 
/*     */ 
/*     */     
/* 160 */     X509Certificate stsCert = (X509Certificate)ctx.getOtherProperties().get("stsCertificate");
/* 161 */     PrivateKey stsPrivKey = (PrivateKey)ctx.getOtherProperties().get("stsPrivateKey");
/*     */ 
/*     */     
/* 164 */     Element signedAssertion = null;
/*     */     try {
/* 166 */       signedAssertion = assertion.sign(stsCert, stsPrivKey, true, ctx.getSignatureAlgorithm(), ctx.getCanonicalizationAlgorithm());
/*     */     
/*     */     }
/* 169 */     catch (SAMLException ex) {
/* 170 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 172 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 177 */     ctx.setSecurityToken((Token)new GenericToken(signedAssertion));
/* 178 */     ctx.setAttachedSecurityTokenReference((Token)samlReference);
/* 179 */     ctx.setUnAttachedSecurityTokenReference((Token)samlReference);
/*     */   }
/*     */   
/*     */   public void isValideToken(IssuedTokenContext ctx) throws WSTrustException {
/* 183 */     WSTrustVersion wstVer = (WSTrustVersion)ctx.getOtherProperties().get("wstVersion");
/* 184 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(wstVer);
/*     */ 
/*     */     
/* 187 */     Token token = ctx.getTarget();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     Element element = eleFac.toElement(token.getTokenValue());
/*     */     
/* 194 */     String code = wstVer.getValidStatusCodeURI();
/* 195 */     String reason = "The Trust service successfully validate the input";
/*     */ 
/*     */     
/* 198 */     if (!isSAMLAssertion(element)) {
/* 199 */       code = wstVer.getInvalidStatusCodeURI();
/* 200 */       reason = "The Trust service did not successfully validate the input";
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     X509Certificate stsCert = (X509Certificate)ctx.getOtherProperties().get("stsCertificate");
/*     */     
/*     */     try {
/* 211 */       boolean isValid = true;
/*     */ 
/*     */       
/* 214 */       isValid = SAMLUtil.verifySignature(element, stsCert.getPublicKey());
/*     */ 
/*     */       
/* 217 */       isValid = SAMLUtil.validateTimeInConditionsStatement(element);
/*     */       
/* 219 */       if (!isValid) {
/* 220 */         code = wstVer.getInvalidStatusCodeURI();
/* 221 */         reason = "The Trust service did not successfully validate the input";
/*     */       } 
/* 223 */     } catch (XWSSecurityException ex) {
/* 224 */       throw new WSTrustException(ex.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 228 */     Status status = eleFac.createStatus(code, reason);
/*     */ 
/*     */     
/* 231 */     String tokenType = ctx.getTokenType();
/* 232 */     if (!wstVer.getValidateStatuesTokenType().equals(tokenType));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     ctx.getOtherProperties().put("status", status);
/*     */   }
/*     */   
/*     */   public void renewToken(IssuedTokenContext ctx) throws WSTrustException {
/* 241 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public void invalidateToken(IssuedTokenContext ctx) throws WSTrustException {
/* 245 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   protected Assertion createSAML11Assertion(WSTrustVersion wstVer, int lifeSpan, String confirMethod, String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs, String keyType) throws WSTrustException {
/* 249 */     Assertion assertion = null;
/*     */     try {
/* 251 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml1.1");
/*     */       
/* 253 */       TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
/* 254 */       GregorianCalendar issuerInst = new GregorianCalendar(utcTimeZone);
/* 255 */       GregorianCalendar notOnOrAfter = new GregorianCalendar(utcTimeZone);
/* 256 */       notOnOrAfter.add(14, lifeSpan);
/*     */       
/* 258 */       List<AudienceRestrictionCondition> arc = null;
/* 259 */       if (appliesTo != null) {
/* 260 */         arc = new ArrayList<AudienceRestrictionCondition>();
/* 261 */         List<String> au = new ArrayList<String>();
/* 262 */         au.add(appliesTo);
/* 263 */         arc.add(samlFac.createAudienceRestrictionCondition(au));
/*     */       } 
/* 265 */       List<String> confirmMethods = new ArrayList<String>();
/* 266 */       Element keyInfoEle = null;
/* 267 */       if (keyType.equals(wstVer.getBearerKeyTypeURI())) {
/* 268 */         confirMethod = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
/*     */       } else {
/* 270 */         if (confirMethod == null) {
/* 271 */           confirMethod = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
/*     */         }
/* 273 */         if (keyInfo != null) {
/* 274 */           keyInfoEle = keyInfo.getElement();
/*     */         }
/*     */       } 
/* 277 */       confirmMethods.add(confirMethod);
/*     */       
/* 279 */       SubjectConfirmation subjectConfirm = samlFac.createSubjectConfirmation(confirmMethods, null, keyInfoEle);
/*     */       
/* 281 */       Conditions conditions = samlFac.createConditions(issuerInst, notOnOrAfter, null, arc, null);
/*     */       
/* 283 */       Advice advice = samlFac.createAdvice(null, null, null);
/*     */       
/* 285 */       Subject subj = null;
/*     */       
/* 287 */       QName idName = null;
/* 288 */       String id = null;
/* 289 */       String idNS = null;
/* 290 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 291 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 292 */         QName attrKey = entry.getKey();
/* 293 */         List<String> values = entry.getValue();
/* 294 */         if (values != null) {
/* 295 */           if ("ActAs".equals(attrKey.getLocalPart())) {
/* 296 */             if (values.size() > 0) {
/* 297 */               id = values.get(0);
/*     */             } else {
/* 299 */               id = null;
/*     */             } 
/* 301 */             idNS = attrKey.getNamespaceURI();
/* 302 */             idName = attrKey;
/*     */             break;
/*     */           } 
/* 305 */           if ("NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 306 */             if (values.size() > 0) {
/* 307 */               id = values.get(0);
/*     */             }
/* 309 */             idNS = attrKey.getNamespaceURI();
/* 310 */             idName = attrKey;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 317 */       NameIdentifier nameId = null;
/* 318 */       if (idName != null && id != null) {
/* 319 */         nameId = samlFac.createNameIdentifier(id, idNS, null);
/* 320 */         claimedAttrs.remove(idName);
/*     */       } 
/* 322 */       subj = samlFac.createSubject(nameId, subjectConfirm);
/* 323 */       List<Object> statements = new ArrayList();
/*     */       
/* 325 */       if (claimedAttrs.isEmpty()) {
/* 326 */         AuthenticationStatement statement = samlFac.createAuthenticationStatement(null, issuerInst, subj, null, null);
/* 327 */         statements.add(statement);
/*     */       } else {
/* 329 */         AttributeStatement statement = samlFac.createAttributeStatement(subj, null);
/* 330 */         statements.add(statement);
/*     */       } 
/* 332 */       assertion = samlFac.createAssertion(assertionId, issuer, issuerInst, conditions, advice, statements);
/*     */       
/* 334 */       if (!claimedAttrs.isEmpty()) {
/* 335 */         return WSTrustUtil.addSamlAttributes(assertion, claimedAttrs);
/*     */       }
/* 337 */     } catch (SAMLException ex) {
/* 338 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 340 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     }
/* 342 */     catch (XWSSecurityException ex) {
/* 343 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 345 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 349 */     return assertion;
/*     */   }
/*     */   
/*     */   protected Assertion createSAML20Assertion(WSTrustVersion wstVer, int lifeSpan, String confirMethod, String assertionId, String issuer, String appliesTo, KeyInfo keyInfo, Map<QName, List<String>> claimedAttrs, String keyType, String authnCtx) throws WSTrustException {
/* 353 */     Assertion assertion = null;
/*     */     try {
/* 355 */       SAMLAssertionFactory samlFac = SAMLAssertionFactory.newInstance("Saml2.0");
/*     */ 
/*     */       
/* 358 */       TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");
/* 359 */       GregorianCalendar issueInst = new GregorianCalendar(utcTimeZone);
/* 360 */       GregorianCalendar notOnOrAfter = new GregorianCalendar(utcTimeZone);
/* 361 */       notOnOrAfter.add(14, lifeSpan);
/*     */       
/* 363 */       List<AudienceRestriction> arc = null;
/* 364 */       if (appliesTo != null) {
/* 365 */         arc = new ArrayList<AudienceRestriction>();
/* 366 */         List<String> au = new ArrayList<String>();
/* 367 */         au.add(appliesTo);
/* 368 */         arc.add(samlFac.createAudienceRestriction(au));
/*     */       } 
/* 370 */       KeyInfoConfirmationData keyInfoConfData = null;
/* 371 */       if (keyType.equals(wstVer.getBearerKeyTypeURI())) {
/* 372 */         confirMethod = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
/*     */       } else {
/* 374 */         if (confirMethod == null) {
/* 375 */           confirMethod = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
/*     */         }
/* 377 */         if (keyInfo != null) {
/* 378 */           keyInfoConfData = samlFac.createKeyInfoConfirmationData(keyInfo.getElement());
/*     */         }
/*     */       } 
/*     */       
/* 382 */       Conditions conditions = samlFac.createConditions(issueInst, notOnOrAfter, null, arc, null, null);
/*     */       
/* 384 */       SubjectConfirmation subjectConfirm = samlFac.createSubjectConfirmation(null, keyInfoConfData, confirMethod);
/*     */ 
/*     */       
/* 387 */       Subject subj = null;
/*     */       
/* 389 */       QName idName = null;
/* 390 */       String id = null;
/* 391 */       String idNS = null;
/* 392 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 393 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 394 */         QName attrKey = entry.getKey();
/* 395 */         List<String> values = entry.getValue();
/* 396 */         if (values != null) {
/* 397 */           if ("ActAs".equals(attrKey.getLocalPart())) {
/* 398 */             if (values.size() > 0) {
/* 399 */               id = values.get(0);
/*     */             } else {
/* 401 */               id = null;
/*     */             } 
/* 403 */             idNS = attrKey.getNamespaceURI();
/* 404 */             idName = attrKey;
/*     */             break;
/*     */           } 
/* 407 */           if ("NameID".equals(attrKey.getLocalPart()) && subj == null) {
/* 408 */             if (values.size() > 0) {
/* 409 */               id = values.get(0);
/*     */             }
/* 411 */             idNS = attrKey.getNamespaceURI();
/* 412 */             idName = attrKey;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       NameID nameId = null;
/* 422 */       if (idName != null && id != null) {
/* 423 */         nameId = samlFac.createNameID(id, idNS, null);
/* 424 */         claimedAttrs.remove(idName);
/*     */       } 
/* 426 */       subj = samlFac.createSubject(nameId, subjectConfirm);
/*     */       
/* 428 */       List<Object> statements = new ArrayList();
/*     */       
/* 430 */       if (claimedAttrs.isEmpty()) {
/* 431 */         AuthnContext ctx = samlFac.createAuthnContext(authnCtx, null);
/* 432 */         AuthnStatement statement = samlFac.createAuthnStatement(issueInst, null, ctx, null, null);
/* 433 */         statements.add(statement);
/*     */       } else {
/* 435 */         AttributeStatement statement = samlFac.createAttributeStatement(null);
/* 436 */         statements.add(statement);
/*     */       } 
/*     */       
/* 439 */       NameID issuerID = samlFac.createNameID(issuer, null, null);
/*     */ 
/*     */       
/* 442 */       assertion = samlFac.createAssertion(assertionId, issuerID, issueInst, conditions, null, null, statements);
/*     */       
/* 444 */       if (!claimedAttrs.isEmpty()) {
/* 445 */         assertion = WSTrustUtil.addSamlAttributes(assertion, claimedAttrs);
/*     */       }
/* 447 */       ((Assertion)assertion).setSubject((SubjectType)subj);
/* 448 */     } catch (SAMLException ex) {
/* 449 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 451 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     }
/* 453 */     catch (XWSSecurityException ex) {
/* 454 */       log.log(Level.SEVERE, LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), (Throwable)ex);
/*     */       
/* 456 */       throw new WSTrustException(LogStringsMessages.WST_0032_ERROR_CREATING_SAML_ASSERTION(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 460 */     return assertion;
/*     */   }
/*     */   
/*     */   private KeyInfo createKeyInfo(IssuedTokenContext ctx) throws WSTrustException {
/* 464 */     Element kiEle = (Element)ctx.getOtherProperties().get("ConfirmationKeyInfo");
/* 465 */     if (kiEle != null && "KeyInfo".equals(kiEle.getLocalName())) {
/*     */       try {
/* 467 */         return new KeyInfo(kiEle, null);
/* 468 */       } catch (XMLSecurityException ex) {
/* 469 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/* 470 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/*     */       } 
/*     */     }
/* 473 */     DocumentBuilderFactory docFactory = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*     */     
/* 475 */     Document doc = null;
/*     */     try {
/* 477 */       doc = docFactory.newDocumentBuilder().newDocument();
/* 478 */     } catch (ParserConfigurationException ex) {
/* 479 */       log.log(Level.SEVERE, LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */       
/* 481 */       throw new WSTrustException(LogStringsMessages.WST_0039_ERROR_CREATING_DOCFACTORY(), ex);
/*     */     } 
/*     */     
/* 484 */     String appliesTo = ctx.getAppliesTo();
/* 485 */     KeyInfo keyInfo = new KeyInfo(doc);
/* 486 */     if (kiEle != null) {
/* 487 */       keyInfo.addUnknownElement(kiEle);
/* 488 */       return keyInfo;
/*     */     } 
/* 490 */     String keyType = ctx.getKeyType();
/* 491 */     WSTrustVersion wstVer = (WSTrustVersion)ctx.getOtherProperties().get("wstVersion");
/* 492 */     if (wstVer.getSymmetricKeyTypeURI().equals(keyType)) {
/* 493 */       byte[] key = ctx.getProofKey();
/*     */       try {
/* 495 */         EncryptedKey encKey = WSTrustUtil.encryptKey(doc, key, (X509Certificate)ctx.getOtherProperties().get("tagetedServiceCertificate"), null);
/* 496 */         keyInfo.add(encKey);
/* 497 */       } catch (Exception ex) {
/* 498 */         log.log(Level.SEVERE, LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */         
/* 500 */         throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */       } 
/* 502 */     } else if (wstVer.getPublicKeyTypeURI().equals(keyType)) {
/* 503 */       X509Data x509data = new X509Data(doc);
/*     */       try {
/* 505 */         x509data.addCertificate(ctx.getRequestorCertificate());
/* 506 */       } catch (XMLSecurityException ex) {
/* 507 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/* 508 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT(), ex);
/*     */       } 
/* 510 */       keyInfo.add(x509data);
/*     */     } 
/*     */     
/* 513 */     return keyInfo;
/*     */   }
/*     */   
/*     */   private boolean isSAMLAssertion(Element token) {
/* 517 */     if (token.getLocalName().equals("Assertion") && (token.getNamespaceURI().equals("urn:oasis:names:tc:SAML:1.0:assertion") || token.getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")))
/*     */     {
/*     */       
/* 520 */       return true;
/*     */     }
/*     */     
/* 523 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\DefaultSAMLTokenProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */