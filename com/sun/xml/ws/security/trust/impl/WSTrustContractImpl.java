/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedData;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.STSAttributeProvider;
/*     */ import com.sun.xml.ws.api.security.trust.STSAuthorizationProvider;
/*     */ import com.sun.xml.ws.api.security.trust.STSTokenProvider;
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustContract;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponseCollection;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.callback.EncryptionKeyCallback;
/*     */ import com.sun.xml.wss.impl.callback.SignatureKeyCallback;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class WSTrustContractImpl
/*     */   implements WSTrustContract<BaseSTSRequest, BaseSTSResponse>
/*     */ {
/* 124 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */   
/*     */   protected static final String SAML_SENDER_VOUCHES_1_0 = "urn:oasis:names:tc:SAML:1.0:cm::sender-vouches";
/*     */   
/*     */   protected static final String SAML_SENDER_VOUCHES_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
/*     */   
/*     */   protected STSConfiguration stsConfig;
/*     */   
/*     */   protected WSTrustVersion wstVer;
/*     */   
/*     */   protected WSTrustElementFactory eleFac;
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 256;
/*     */ 
/*     */   
/*     */   public void init(STSConfiguration stsConfig) {
/* 140 */     this.stsConfig = stsConfig;
/* 141 */     this.wstVer = (WSTrustVersion)stsConfig.getOtherOptions().get("WSTrustVersion");
/* 142 */     this.eleFac = WSTrustElementFactory.newInstance(this.wstVer);
/*     */   }
/*     */   public BaseSTSResponse issue(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/*     */     GenericToken genericToken;
/* 146 */     RequestSecurityToken rst = (RequestSecurityToken)request;
/* 147 */     SecondaryParameters secParas = null;
/* 148 */     context.getOtherProperties().put("wstVersion", this.wstVer);
/* 149 */     if (this.wstVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-trust/200512")) {
/* 150 */       secParas = rst.getSecondaryParameters();
/*     */     }
/*     */ 
/*     */     
/* 154 */     AppliesTo applies = rst.getAppliesTo();
/* 155 */     String appliesTo = null;
/* 156 */     X509Certificate serCert = null;
/* 157 */     List<Object> at = null;
/* 158 */     if (applies != null) {
/* 159 */       at = WSTrustUtil.parseAppliesTo(applies);
/* 160 */       for (int i = 0; i < at.size(); i++) {
/* 161 */         Object obj = at.get(i);
/* 162 */         if (obj instanceof String) {
/* 163 */           appliesTo = (String)obj;
/* 164 */         } else if (obj instanceof X509Certificate) {
/* 165 */           serCert = (X509Certificate)obj;
/*     */         } 
/*     */       } 
/*     */     } 
/* 169 */     context.setAppliesTo(appliesTo);
/*     */ 
/*     */     
/* 172 */     String issuer = this.stsConfig.getIssuer();
/* 173 */     context.setTokenIssuer(issuer);
/*     */ 
/*     */     
/* 176 */     TrustSPMetadata spMd = this.stsConfig.getTrustSPMetadata(appliesTo);
/* 177 */     if (spMd == null)
/*     */     {
/* 179 */       spMd = this.stsConfig.getTrustSPMetadata("default");
/*     */     }
/* 181 */     if (spMd == null) {
/* 182 */       log.log(Level.SEVERE, LogStringsMessages.WST_0004_UNKNOWN_SERVICEPROVIDER(appliesTo));
/*     */       
/* 184 */       throw new WSTrustException(LogStringsMessages.WST_0004_UNKNOWN_SERVICEPROVIDER(appliesTo));
/*     */     } 
/*     */ 
/*     */     
/* 188 */     if (serCert == null) {
/* 189 */       serCert = getServiceCertificate(spMd, appliesTo);
/*     */     }
/* 191 */     if (serCert != null) {
/* 192 */       context.getOtherProperties().put("tagetedServiceCertificate", serCert);
/*     */     }
/*     */ 
/*     */     
/* 196 */     Object[] certAndKey = getSTSCertAndPrivateKey();
/* 197 */     context.getOtherProperties().put("stsCertificate", (X509Certificate)certAndKey[0]);
/* 198 */     context.getOtherProperties().put("stsPrivateKey", (PrivateKey)certAndKey[1]);
/*     */ 
/*     */     
/* 201 */     String tokenType = null;
/* 202 */     URI tokenTypeURI = rst.getTokenType();
/* 203 */     if (tokenTypeURI == null && secParas != null) {
/* 204 */       tokenTypeURI = secParas.getTokenType();
/*     */     }
/* 206 */     if (tokenTypeURI != null) {
/* 207 */       tokenType = tokenTypeURI.toString();
/*     */     } else {
/* 209 */       tokenType = spMd.getTokenType();
/*     */     } 
/* 211 */     if (tokenType == null) {
/* 212 */       tokenType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1";
/*     */     }
/* 214 */     context.setTokenType(tokenType);
/*     */ 
/*     */     
/* 217 */     String keyType = null;
/* 218 */     URI keyTypeURI = rst.getKeyType();
/* 219 */     if (keyTypeURI == null && secParas != null) {
/* 220 */       keyTypeURI = secParas.getKeyType();
/*     */     }
/* 222 */     if (keyTypeURI != null) {
/* 223 */       keyType = keyTypeURI.toString();
/*     */     } else {
/* 225 */       keyType = spMd.getKeyType();
/*     */     } 
/* 227 */     if (keyType == null) {
/* 228 */       keyType = this.wstVer.getSymmetricKeyTypeURI();
/*     */     }
/* 230 */     context.setKeyType(keyType);
/*     */ 
/*     */     
/* 233 */     String encryptionAlgorithm = null;
/* 234 */     URI encryptionAlgorithmURI = rst.getEncryptionAlgorithm();
/* 235 */     if (encryptionAlgorithmURI == null && secParas != null) {
/* 236 */       encryptionAlgorithmURI = secParas.getEncryptionAlgorithm();
/*     */     }
/* 238 */     if (encryptionAlgorithmURI != null) {
/* 239 */       encryptionAlgorithm = encryptionAlgorithmURI.toString();
/*     */     }
/* 241 */     context.setEncryptionAlgorithm(encryptionAlgorithm);
/*     */     
/* 243 */     String signatureAlgorithm = null;
/* 244 */     URI signatureAlgorithmURI = rst.getSignatureAlgorithm();
/* 245 */     if (signatureAlgorithmURI == null && secParas != null) {
/* 246 */       signatureAlgorithmURI = secParas.getSignatureAlgorithm();
/*     */     }
/* 248 */     if (signatureAlgorithmURI != null) {
/* 249 */       signatureAlgorithm = signatureAlgorithmURI.toString();
/*     */     }
/* 251 */     context.setSignatureAlgorithm(signatureAlgorithm);
/*     */     
/* 253 */     String canonicalizationAlgorithm = null;
/* 254 */     URI canonicalizationAlgorithmURI = rst.getCanonicalizationAlgorithm();
/* 255 */     if (canonicalizationAlgorithmURI == null && secParas != null) {
/* 256 */       canonicalizationAlgorithmURI = secParas.getCanonicalizationAlgorithm();
/*     */     }
/* 258 */     if (canonicalizationAlgorithmURI != null) {
/* 259 */       canonicalizationAlgorithm = canonicalizationAlgorithmURI.toString();
/*     */     }
/* 261 */     context.setCanonicalizationAlgorithm(canonicalizationAlgorithm);
/*     */ 
/*     */     
/* 264 */     URI keyWrapAlgorithmURI = null;
/* 265 */     if (secParas != null) {
/* 266 */       keyWrapAlgorithmURI = secParas.getKeyWrapAlgorithm();
/*     */     }
/* 268 */     if (keyWrapAlgorithmURI != null) {
/* 269 */       context.getOtherProperties().put("keyWrapAlgorithm", keyWrapAlgorithmURI.toString());
/*     */     }
/*     */ 
/*     */     
/* 273 */     Subject subject = context.getRequestorSubject();
/* 274 */     if (subject == null) {
/* 275 */       AccessControlContext acc = AccessController.getContext();
/* 276 */       subject = Subject.getSubject(acc);
/* 277 */       context.setRequestorSubject(subject);
/*     */     } 
/* 279 */     if (subject == null) {
/* 280 */       log.log(Level.SEVERE, LogStringsMessages.WST_0030_REQUESTOR_NULL());
/*     */       
/* 282 */       throw new WSTrustException(LogStringsMessages.WST_0030_REQUESTOR_NULL());
/*     */     } 
/*     */ 
/*     */     
/* 286 */     String authnCtx = (String)this.stsConfig.getOtherOptions().get("AuthnContextClass");
/* 287 */     if (authnCtx != null) {
/* 288 */       context.getOtherProperties().put("authnContext", authnCtx);
/*     */     }
/*     */ 
/*     */     
/* 292 */     Claims claims = rst.getClaims();
/* 293 */     if (claims == null && secParas != null) {
/* 294 */       claims = secParas.getClaims();
/*     */     }
/* 296 */     if (claims != null) {
/*     */       
/* 298 */       List<Object> si = rst.getExtensionElements();
/* 299 */       claims.getSupportingProperties().addAll(si);
/* 300 */       if (at != null) {
/* 301 */         claims.getSupportingProperties().addAll(at);
/*     */       }
/*     */     } else {
/* 304 */       claims = this.eleFac.createClaims();
/*     */     } 
/*     */     
/* 307 */     String confirMethod = null;
/*     */     
/* 309 */     Element assertionInRST = (Element)this.stsConfig.getOtherOptions().get("SamlAssertionElementInRST");
/*     */     
/* 311 */     OnBehalfOf obo = rst.getOnBehalfOf();
/* 312 */     if (obo != null) {
/* 313 */       Object oboToken = obo.getAny();
/* 314 */       if (assertionInRST != null) {
/* 315 */         oboToken = assertionInRST;
/*     */       }
/* 317 */       if (oboToken != null) {
/* 318 */         subject.getPublicCredentials().add(this.eleFac.toElement(oboToken));
/*     */ 
/*     */         
/* 321 */         claims.getOtherAttributes().put(new QName("OnBehalfOf"), "true");
/* 322 */         context.getOtherProperties().put("OnBehalfOf", "true");
/*     */ 
/*     */         
/* 325 */         Subject oboSubj = new Subject();
/* 326 */         oboSubj.getPublicCredentials().add(this.eleFac.toElement(oboToken));
/* 327 */         claims.getSupportingProperties().add(oboSubj);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 332 */     ActAs actAs = rst.getActAs();
/* 333 */     if (actAs != null) {
/* 334 */       Object actAsToken = actAs.getAny();
/* 335 */       if (assertionInRST != null) {
/* 336 */         actAsToken = assertionInRST;
/*     */       }
/* 338 */       if (actAsToken != null) {
/*     */         
/* 340 */         claims.getOtherAttributes().put(new QName("ActAs"), "true");
/* 341 */         context.getOtherProperties().put("ActAs", "true");
/*     */ 
/*     */         
/* 344 */         Subject actAsSubj = new Subject();
/* 345 */         actAsSubj.getPublicCredentials().add(this.eleFac.toElement(actAsToken));
/* 346 */         claims.getSupportingProperties().add(actAsSubj);
/*     */       } 
/*     */     } 
/*     */     
/* 350 */     if (confirMethod != null) {
/* 351 */       context.getOtherProperties().put("samlConfirmationMethod", confirMethod);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 356 */     STSAuthorizationProvider authzProvider = WSTrustFactory.getSTSAuthorizationProvider();
/* 357 */     if (!authzProvider.isAuthorized(subject, appliesTo, tokenType, keyType)) {
/* 358 */       String user = ((Principal)subject.getPrincipals().iterator().next()).getName();
/* 359 */       log.log(Level.SEVERE, LogStringsMessages.WST_0015_CLIENT_NOT_AUTHORIZED(user, tokenType, appliesTo));
/*     */ 
/*     */       
/* 362 */       throw new WSTrustException(LogStringsMessages.WST_0015_CLIENT_NOT_AUTHORIZED(user, tokenType, appliesTo));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 367 */     STSAttributeProvider attrProvider = WSTrustFactory.getSTSAttributeProvider();
/* 368 */     Map<QName, List<String>> claimedAttrs = attrProvider.getClaimedAttributes(subject, appliesTo, tokenType, claims);
/* 369 */     context.getOtherProperties().put("cliamedAttributes", claimedAttrs);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     RequestedProofToken proofToken = null;
/* 376 */     Entropy serverEntropy = null;
/* 377 */     int keySize = 0;
/* 378 */     if (this.wstVer.getSymmetricKeyTypeURI().equals(keyType)) {
/* 379 */       proofToken = this.eleFac.createRequestedProofToken();
/*     */       
/* 381 */       byte[] clientEntr = null;
/* 382 */       Entropy clientEntropy = rst.getEntropy();
/* 383 */       if (clientEntropy != null) {
/* 384 */         BinarySecret clientBS = clientEntropy.getBinarySecret();
/* 385 */         if (clientBS == null) {
/* 386 */           if (log.isLoggable(Level.FINE)) {
/* 387 */             log.log(Level.FINE, LogStringsMessages.WST_1009_NULL_BINARY_SECRET());
/*     */           }
/*     */         } else {
/*     */           
/* 391 */           clientEntr = clientBS.getRawValue();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 396 */       keySize = (int)rst.getKeySize();
/* 397 */       if (keySize < 1 && secParas != null) {
/* 398 */         keySize = (int)secParas.getKeySize();
/*     */       }
/* 400 */       if (keySize < 1) {
/* 401 */         keySize = 256;
/*     */       }
/* 403 */       if (log.isLoggable(Level.FINE)) {
/* 404 */         log.log(Level.FINE, LogStringsMessages.WST_1010_KEY_SIZE(Integer.valueOf(keySize), Integer.valueOf(256)));
/*     */       }
/*     */ 
/*     */       
/* 408 */       byte[] key = WSTrustUtil.generateRandomSecret(keySize / 8);
/* 409 */       BinarySecret serverBS = this.eleFac.createBinarySecret(key, this.wstVer.getNonceBinarySecretTypeURI());
/* 410 */       serverEntropy = this.eleFac.createEntropy(serverBS);
/*     */ 
/*     */       
/*     */       try {
/* 414 */         if (clientEntr != null && clientEntr.length > 0) {
/* 415 */           proofToken.setComputedKey(URI.create(this.wstVer.getCKPSHA1algorithmURI()));
/* 416 */           proofToken.setProofTokenType("ComputedKey");
/* 417 */           key = SecurityUtil.P_SHA1(clientEntr, key, keySize / 8);
/*     */         } else {
/* 419 */           proofToken.setProofTokenType("BinarySecret");
/* 420 */           proofToken.setBinarySecret(serverBS);
/*     */         } 
/* 422 */       } catch (Exception ex) {
/* 423 */         log.log(Level.SEVERE, LogStringsMessages.WST_0013_ERROR_SECRET_KEY(this.wstVer.getCKPSHA1algorithmURI(), Integer.valueOf(keySize), appliesTo), ex);
/*     */         
/* 425 */         throw new WSTrustException(LogStringsMessages.WST_0013_ERROR_SECRET_KEY(this.wstVer.getCKPSHA1algorithmURI(), Integer.valueOf(keySize), appliesTo), ex);
/*     */       } 
/*     */ 
/*     */       
/* 429 */       context.setProofKey(key);
/* 430 */     } else if (this.wstVer.getPublicKeyTypeURI().equals(keyType)) {
/*     */       
/* 432 */       UseKey useKey = rst.getUseKey();
/* 433 */       if (useKey != null) {
/* 434 */         Element uk = this.eleFac.toElement(useKey.getToken().getTokenValue());
/* 435 */         context.getOtherProperties().put("ConfirmationKeyInfo", uk);
/*     */       } 
/* 437 */       Set<Object> certs = subject.getPublicCredentials();
/* 438 */       boolean addedClientCert = false;
/* 439 */       for (Object o : certs) {
/* 440 */         if (o instanceof X509Certificate) {
/* 441 */           X509Certificate clientCert = (X509Certificate)o;
/* 442 */           context.setRequestorCertificate(clientCert);
/* 443 */           addedClientCert = true;
/*     */         } 
/*     */       } 
/* 446 */       if (!addedClientCert && useKey == null) {
/* 447 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */         
/* 449 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */       } 
/* 451 */     } else if (!this.wstVer.getBearerKeyTypeURI().equals(keyType)) {
/*     */ 
/*     */       
/* 454 */       log.log(Level.SEVERE, LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keyType, appliesTo));
/*     */       
/* 456 */       throw new WSTrustException(LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keyType, appliesTo));
/*     */     } 
/*     */ 
/*     */     
/* 460 */     Lifetime lifetime = rst.getLifetime();
/* 461 */     long currentTime = WSTrustUtil.getCurrentTimeWithOffset();
/* 462 */     long lifespan = -1L;
/* 463 */     if (lifetime == null) {
/* 464 */       lifespan = this.stsConfig.getIssuedTokenTimeout();
/* 465 */       lifetime = WSTrustUtil.createLifetime(currentTime, lifespan, this.wstVer);
/*     */     } else {
/*     */       
/* 468 */       lifespan = WSTrustUtil.getLifeSpan(lifetime);
/*     */     } 
/* 470 */     context.setCreationTime(new Date(currentTime));
/* 471 */     context.setExpirationTime(new Date(currentTime + lifespan));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 478 */     STSTokenProvider tokenProvider = WSTrustFactory.getSTSTokenProvider();
/* 479 */     tokenProvider.generateToken(context);
/*     */ 
/*     */     
/* 482 */     RequestedSecurityToken reqSecTok = this.eleFac.createRequestedSecurityToken();
/* 483 */     Token issuedToken = context.getSecurityToken();
/*     */ 
/*     */     
/* 486 */     if (this.stsConfig.getEncryptIssuedToken() && serCert != null) {
/* 487 */       String keyWrapAlgo = (String)context.getOtherProperties().get("keyWrapAlgorithm");
/* 488 */       Element encTokenEle = encryptToken((Element)issuedToken.getTokenValue(), serCert, appliesTo, encryptionAlgorithm, keyWrapAlgo);
/* 489 */       genericToken = new GenericToken(encTokenEle);
/*     */     } 
/* 491 */     reqSecTok.setToken((Token)genericToken);
/*     */ 
/*     */     
/* 494 */     SecurityTokenReference raSTR = (SecurityTokenReference)context.getAttachedSecurityTokenReference();
/* 495 */     SecurityTokenReference ruSTR = (SecurityTokenReference)context.getUnAttachedSecurityTokenReference();
/* 496 */     RequestedAttachedReference raRef = null;
/* 497 */     if (raSTR != null) {
/* 498 */       raRef = this.eleFac.createRequestedAttachedReference(raSTR);
/*     */     }
/* 500 */     RequestedUnattachedReference ruRef = null;
/* 501 */     if (ruSTR != null) {
/* 502 */       ruRef = this.eleFac.createRequestedUnattachedReference(ruSTR);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 510 */     URI ctx = null;
/* 511 */     String rstCtx = rst.getContext();
/* 512 */     if (rstCtx != null) {
/* 513 */       ctx = URI.create(rstCtx);
/*     */     }
/*     */ 
/*     */     
/* 517 */     RequestSecurityTokenResponse rstr = this.eleFac.createRSTRForIssue(URI.create(tokenType), ctx, reqSecTok, applies, raRef, ruRef, proofToken, serverEntropy, lifetime);
/*     */ 
/*     */     
/* 520 */     if (keySize > 0) {
/* 521 */       rstr.setKeySize(keySize);
/*     */     }
/*     */     
/* 524 */     handleExtension((BaseSTSRequest)rst, (BaseSTSResponse)rstr, context);
/*     */     
/* 526 */     if (log.isLoggable(Level.FINE)) {
/* 527 */       log.log(Level.FINE, LogStringsMessages.WST_1006_CREATED_RST_ISSUE(WSTrustUtil.elemToString((BaseSTSRequest)rst, this.wstVer)));
/*     */       
/* 529 */       log.log(Level.FINE, LogStringsMessages.WST_1007_CREATED_RSTR_ISSUE(WSTrustUtil.elemToString((BaseSTSResponse)rstr, this.wstVer)));
/*     */     } 
/*     */ 
/*     */     
/* 533 */     if (this.wstVer.getNamespaceURI().equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 534 */       List<RequestSecurityTokenResponse> list = new ArrayList<RequestSecurityTokenResponse>();
/* 535 */       list.add(rstr);
/* 536 */       RequestSecurityTokenResponseCollection rstrc = this.eleFac.createRSTRC(list);
/*     */       
/* 538 */       return (BaseSTSResponse)rstrc;
/*     */     } 
/*     */     
/* 541 */     return (BaseSTSResponse)rstr;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleExtension(BaseSTSRequest request, BaseSTSResponse response, IssuedTokenContext context) throws WSTrustException {}
/*     */ 
/*     */   
/*     */   public BaseSTSResponse renew(BaseSTSRequest rst, IssuedTokenContext context) throws WSTrustException {
/* 549 */     throw new UnsupportedOperationException("Unsupported operation: renew");
/*     */   }
/*     */   
/*     */   public BaseSTSResponse cancel(BaseSTSRequest rst, IssuedTokenContext context, Map map) throws WSTrustException {
/* 553 */     throw new UnsupportedOperationException("Unsupported operation: cancel");
/*     */   }
/*     */   
/*     */   public BaseSTSResponse validate(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/* 557 */     RequestSecurityToken rst = (RequestSecurityToken)request;
/*     */ 
/*     */     
/* 560 */     Object[] certAndKey = getSTSCertAndPrivateKey();
/* 561 */     context.getOtherProperties().put("stsCertificate", (X509Certificate)certAndKey[0]);
/* 562 */     context.getOtherProperties().put("stsPrivateKey", (PrivateKey)certAndKey[1]);
/* 563 */     context.getOtherProperties().put("wstVersion", this.wstVer);
/*     */ 
/*     */     
/* 566 */     URI tokenType = rst.getTokenType();
/* 567 */     context.setTokenType(tokenType.toString());
/*     */ 
/*     */     
/* 570 */     Element token = null;
/* 571 */     Element assertionInRST = (Element)this.stsConfig.getOtherOptions().get("SamlAssertionElementInRST");
/* 572 */     if (assertionInRST != null) {
/* 573 */       token = assertionInRST;
/* 574 */     } else if (this.wstVer.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2005/02/trust")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 579 */       List<Object> exts = rst.getExtensionElements();
/* 580 */       if (exts.size() > 0) {
/* 581 */         token = (Element)exts.get(0);
/*     */       }
/*     */     } else {
/* 584 */       ValidateTarget vt = rst.getValidateTarget();
/* 585 */       token = (Element)vt.getAny();
/*     */     } 
/* 587 */     context.setTarget((Token)new GenericToken(token));
/*     */ 
/*     */     
/* 590 */     STSTokenProvider tokenProvider = WSTrustFactory.getSTSTokenProvider();
/* 591 */     tokenProvider.isValideToken(context);
/*     */ 
/*     */     
/* 594 */     RequestedSecurityToken reqSecTok = null;
/* 595 */     if (!this.wstVer.getValidateStatuesTokenType().equals(tokenType.toString())) {
/* 596 */       reqSecTok = this.eleFac.createRequestedSecurityToken();
/* 597 */       Token issuedToken = context.getSecurityToken();
/* 598 */       reqSecTok.setToken(issuedToken);
/*     */     } 
/*     */ 
/*     */     
/* 602 */     RequestSecurityTokenResponse rstr = this.eleFac.createRSTRForValidate(tokenType, reqSecTok, (Status)context.getOtherProperties().get("status"));
/*     */     
/* 604 */     if (this.wstVer.getNamespaceURI().equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 605 */       List<RequestSecurityTokenResponse> list = new ArrayList<RequestSecurityTokenResponse>();
/* 606 */       list.add(rstr);
/* 607 */       RequestSecurityTokenResponseCollection rstrc = this.eleFac.createRSTRC(list);
/*     */       
/* 609 */       return (BaseSTSResponse)rstrc;
/*     */     } 
/*     */     
/* 612 */     return (BaseSTSResponse)rstr;
/*     */   }
/*     */   
/*     */   public void handleUnsolicited(BaseSTSResponse rstr, IssuedTokenContext context) throws WSTrustException {
/* 616 */     throw new UnsupportedOperationException("Unsupported operation: handleUnsolicited");
/*     */   }
/*     */   
/*     */   private X509Certificate getServiceCertificate(TrustSPMetadata spMd, String appliesTo) throws WSTrustException {
/* 620 */     String certAlias = spMd.getCertAlias();
/* 621 */     X509Certificate cert = null;
/* 622 */     CallbackHandler callbackHandler = this.stsConfig.getCallbackHandler();
/* 623 */     if (callbackHandler != null) {
/*     */       
/* 625 */       EncryptionKeyCallback.AliasX509CertificateRequest req = new EncryptionKeyCallback.AliasX509CertificateRequest(spMd.getCertAlias());
/* 626 */       EncryptionKeyCallback callback = new EncryptionKeyCallback((EncryptionKeyCallback.Request)req);
/* 627 */       Callback[] callbacks = { (Callback)callback };
/*     */       try {
/* 629 */         callbackHandler.handle(callbacks);
/* 630 */       } catch (IOException ex) {
/* 631 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */         
/* 633 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       }
/* 635 */       catch (UnsupportedCallbackException ex) {
/* 636 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */         
/* 638 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       } 
/*     */ 
/*     */       
/* 642 */       cert = req.getX509Certificate();
/*     */     } else {
/* 644 */       SecurityEnvironment secEnv = (SecurityEnvironment)this.stsConfig.getOtherOptions().get("SecurityEnvironment");
/*     */       try {
/* 646 */         cert = secEnv.getCertificate(this.stsConfig.getOtherOptions(), certAlias, false);
/* 647 */       } catch (XWSSecurityException ex) {
/* 648 */         log.log(Level.SEVERE, LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), (Throwable)ex);
/*     */         
/* 650 */         throw new WSTrustException(LogStringsMessages.WST_0033_UNABLE_GET_SERVICE_CERT(appliesTo), ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 655 */     return cert;
/*     */   }
/*     */   
/*     */   private Object[] getSTSCertAndPrivateKey() throws WSTrustException {
/* 659 */     X509Certificate stsCert = null;
/* 660 */     PrivateKey stsPrivKey = null;
/* 661 */     CallbackHandler callbackHandler = this.stsConfig.getCallbackHandler();
/* 662 */     if (callbackHandler != null) {
/* 663 */       SignatureKeyCallback.DefaultPrivKeyCertRequest request = new SignatureKeyCallback.DefaultPrivKeyCertRequest();
/*     */       
/* 665 */       SignatureKeyCallback signatureKeyCallback = new SignatureKeyCallback((SignatureKeyCallback.Request)request);
/* 666 */       Callback[] callbacks = { (Callback)signatureKeyCallback };
/*     */       try {
/* 668 */         callbackHandler.handle(callbacks);
/* 669 */       } catch (IOException ex) {
/* 670 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */         
/* 672 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       }
/* 674 */       catch (UnsupportedCallbackException ex) {
/* 675 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */         
/* 677 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       } 
/*     */ 
/*     */       
/* 681 */       stsPrivKey = request.getPrivateKey();
/* 682 */       stsCert = request.getX509Certificate();
/*     */     } else {
/* 684 */       SecurityEnvironment secEnv = (SecurityEnvironment)this.stsConfig.getOtherOptions().get("SecurityEnvironment");
/*     */       try {
/* 686 */         stsCert = secEnv.getDefaultCertificate(this.stsConfig.getOtherOptions());
/* 687 */         stsPrivKey = secEnv.getPrivateKey(this.stsConfig.getOtherOptions(), stsCert);
/* 688 */       } catch (XWSSecurityException ex) {
/* 689 */         log.log(Level.SEVERE, LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), (Throwable)ex);
/*     */         
/* 691 */         throw new WSTrustException(LogStringsMessages.WST_0043_UNABLE_GET_STS_KEY(), ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 696 */     Object[] results = new Object[2];
/* 697 */     results[0] = stsCert;
/* 698 */     results[1] = stsPrivKey;
/* 699 */     return results;
/*     */   }
/*     */   
/*     */   private Element encryptToken(Element assertion, X509Certificate serCert, String appliesTo, String encryptionAlgorithm, String keyWrapAlgorithm) throws WSTrustException {
/* 703 */     Element encDataEle = null;
/*     */     
/*     */     try {
/*     */       XMLCipher cipher;
/* 707 */       if (encryptionAlgorithm != null) {
/* 708 */         cipher = XMLCipher.getInstance(encryptionAlgorithm);
/*     */       } else {
/* 710 */         cipher = XMLCipher.getInstance("http://www.w3.org/2001/04/xmlenc#aes256-cbc");
/*     */       } 
/* 712 */       int keysizeInBytes = 32;
/* 713 */       byte[] skey = WSTrustUtil.generateRandomSecret(32);
/* 714 */       cipher.init(1, new SecretKeySpec(skey, "AES"));
/*     */ 
/*     */       
/* 717 */       Document owner = assertion.getOwnerDocument();
/* 718 */       EncryptedData encData = cipher.encryptData(owner, assertion);
/* 719 */       String id = "uuid-" + UUID.randomUUID().toString();
/* 720 */       encData.setId(id);
/*     */       
/* 722 */       KeyInfo encKeyInfo = new KeyInfo(owner);
/* 723 */       EncryptedKey encKey = WSTrustUtil.encryptKey(owner, skey, serCert, keyWrapAlgorithm);
/* 724 */       encKeyInfo.add(encKey);
/* 725 */       encData.setKeyInfo(encKeyInfo);
/*     */       
/* 727 */       encDataEle = cipher.martial(encData);
/* 728 */     } catch (XMLEncryptionException ex) {
/* 729 */       log.log(Level.SEVERE, LogStringsMessages.WST_0044_ERROR_ENCRYPT_ISSUED_TOKEN(appliesTo), (Throwable)ex);
/*     */       
/* 731 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/* 732 */     } catch (Exception ex) {
/* 733 */       log.log(Level.SEVERE, LogStringsMessages.WST_0044_ERROR_ENCRYPT_ISSUED_TOKEN(appliesTo), ex);
/*     */       
/* 735 */       throw new WSTrustException(LogStringsMessages.WST_0040_ERROR_ENCRYPT_PROOFKEY(appliesTo), ex);
/*     */     } 
/*     */     
/* 738 */     return encDataEle;
/*     */   }
/*     */   
/*     */   private String getSenderVouchesMethod(String tokenType) {
/* 742 */     if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType))
/*     */     {
/* 744 */       return "urn:oasis:names:tc:SAML:1.0:cm::sender-vouches";
/*     */     }
/*     */     
/* 747 */     if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
/* 748 */       return "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
/*     */     }
/*     */     
/* 751 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\WSTrustContractImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */