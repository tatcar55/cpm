/*     */ package com.sun.xml.ws.security.trust.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.IssueSamlTokenContract;
/*     */ import com.sun.xml.ws.api.security.trust.STSAttributeProvider;
/*     */ import com.sun.xml.ws.api.security.trust.STSAuthorizationProvider;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.config.TrustSPMetadata;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
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
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.Principal;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public abstract class IssueSamlTokenContract
/*     */   implements IssueSamlTokenContract<BaseSTSRequest, BaseSTSResponse>
/*     */ {
/* 103 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */   
/*     */   protected static final String SAML_HOLDER_OF_KEY_1_0 = "urn:oasis:names:tc:SAML:1.0:cm:holder-of-key";
/*     */   
/*     */   protected static final String SAML_HOLDER_OF_KEY_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:holder-of-key";
/*     */   
/*     */   protected static final String SAML_BEARER_1_0 = "urn:oasis:names:tc:SAML:1.0:cm:bearer";
/*     */   
/*     */   protected static final String SAML_BEARER_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:bearer";
/*     */   
/*     */   protected static final String SAML_SENDER_VOUCHES_1_0 = "urn:oasis:names:tc:SAML:1.0:cm::sender-vouches";
/*     */   protected static final String SAML_SENDER_VOUCHES_2_0 = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
/*     */   protected STSConfiguration stsConfig;
/*     */   protected WSTrustVersion wstVer;
/*     */   protected String authnCtxClass;
/* 118 */   protected WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance(WSTrustVersion.WS_TRUST_10);
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_KEY_SIZE = 256;
/*     */ 
/*     */   
/*     */   public void init(STSConfiguration stsConfig) {
/* 125 */     this.stsConfig = stsConfig;
/* 126 */     this.wstVer = (WSTrustVersion)stsConfig.getOtherOptions().get("WSTrustVersion");
/* 127 */     this.authnCtxClass = (String)stsConfig.getOtherOptions().get("AuthnContextClass");
/* 128 */     this.eleFac = WSTrustElementFactory.newInstance(this.wstVer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse issue(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/* 134 */     RequestSecurityToken rst = (RequestSecurityToken)request;
/* 135 */     SecondaryParameters secParas = null;
/* 136 */     if (this.wstVer.getNamespaceURI().equals("http://docs.oasis-open.org/ws-sx/ws-trust/200512")) {
/* 137 */       secParas = rst.getSecondaryParameters();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 142 */     AppliesTo applies = rst.getAppliesTo();
/* 143 */     String appliesTo = null;
/* 144 */     X509Certificate serCert = null;
/* 145 */     if (applies != null) {
/* 146 */       List<Object> at = WSTrustUtil.parseAppliesTo(applies);
/* 147 */       for (int i = 0; i < at.size(); i++) {
/* 148 */         Object obj = at.get(i);
/* 149 */         if (obj instanceof String) {
/* 150 */           appliesTo = (String)obj;
/* 151 */         } else if (obj instanceof X509Certificate) {
/* 152 */           serCert = (X509Certificate)obj;
/*     */         } 
/*     */       } 
/*     */     } 
/* 156 */     if (serCert != null) {
/* 157 */       context.getOtherProperties().put("tagetedServiceCertificate", serCert);
/*     */     }
/*     */     
/* 160 */     TrustSPMetadata spMd = this.stsConfig.getTrustSPMetadata(appliesTo);
/* 161 */     if (spMd == null)
/*     */     {
/* 163 */       spMd = this.stsConfig.getTrustSPMetadata("default");
/*     */     }
/* 165 */     if (spMd == null) {
/* 166 */       log.log(Level.SEVERE, LogStringsMessages.WST_0004_UNKNOWN_SERVICEPROVIDER(appliesTo));
/*     */       
/* 168 */       throw new WSTrustException(LogStringsMessages.WST_0004_UNKNOWN_SERVICEPROVIDER(appliesTo));
/*     */     } 
/*     */ 
/*     */     
/* 172 */     String tokenType = null;
/* 173 */     URI tokenTypeURI = rst.getTokenType();
/* 174 */     if (tokenTypeURI == null && secParas != null) {
/* 175 */       tokenTypeURI = secParas.getTokenType();
/*     */     }
/* 177 */     if (tokenTypeURI != null) {
/* 178 */       tokenType = tokenTypeURI.toString();
/*     */     } else {
/* 180 */       tokenType = spMd.getTokenType();
/*     */     } 
/* 182 */     if (tokenType == null) {
/* 183 */       tokenType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1";
/*     */     }
/*     */ 
/*     */     
/* 187 */     String keyType = null;
/* 188 */     URI keyTypeURI = rst.getKeyType();
/* 189 */     if (keyTypeURI == null && secParas != null) {
/* 190 */       keyTypeURI = secParas.getKeyType();
/*     */     }
/* 192 */     if (keyTypeURI != null) {
/* 193 */       keyType = keyTypeURI.toString();
/*     */     } else {
/* 195 */       keyType = spMd.getKeyType();
/*     */     } 
/* 197 */     if (keyType == null) {
/* 198 */       keyType = this.wstVer.getSymmetricKeyTypeURI();
/*     */     }
/*     */     
/* 201 */     String encryptionAlgorithm = null;
/* 202 */     URI encryptionAlgorithmURI = rst.getEncryptionAlgorithm();
/* 203 */     if (encryptionAlgorithmURI == null && secParas != null) {
/* 204 */       encryptionAlgorithmURI = secParas.getEncryptionAlgorithm();
/*     */     }
/* 206 */     if (encryptionAlgorithmURI != null) {
/* 207 */       encryptionAlgorithm = encryptionAlgorithmURI.toString();
/*     */     }
/* 209 */     context.setEncryptionAlgorithm(encryptionAlgorithm);
/*     */     
/* 211 */     String signatureAlgorithm = null;
/* 212 */     URI signatureAlgorithmURI = rst.getSignatureAlgorithm();
/* 213 */     if (signatureAlgorithmURI == null && secParas != null) {
/* 214 */       signatureAlgorithmURI = secParas.getSignatureAlgorithm();
/*     */     }
/* 216 */     if (signatureAlgorithmURI != null) {
/* 217 */       signatureAlgorithm = signatureAlgorithmURI.toString();
/*     */     }
/* 219 */     context.setSignatureAlgorithm(signatureAlgorithm);
/*     */     
/* 221 */     String canonicalizationAlgorithm = null;
/* 222 */     URI canonicalizationAlgorithmURI = rst.getCanonicalizationAlgorithm();
/* 223 */     if (canonicalizationAlgorithmURI == null && secParas != null) {
/* 224 */       canonicalizationAlgorithmURI = secParas.getCanonicalizationAlgorithm();
/*     */     }
/* 226 */     if (canonicalizationAlgorithmURI != null) {
/* 227 */       canonicalizationAlgorithm = canonicalizationAlgorithmURI.toString();
/*     */     }
/* 229 */     context.setCanonicalizationAlgorithm(canonicalizationAlgorithm);
/*     */ 
/*     */     
/* 232 */     URI keyWrapAlgorithmURI = null;
/* 233 */     if (secParas != null) {
/* 234 */       keyWrapAlgorithmURI = secParas.getKeyWrapAlgorithm();
/*     */     }
/* 236 */     if (keyWrapAlgorithmURI != null) {
/* 237 */       context.getOtherProperties().put("keyWrapAlgorithm", keyWrapAlgorithmURI.toString());
/*     */     }
/*     */ 
/*     */     
/* 241 */     Subject subject = context.getRequestorSubject();
/* 242 */     if (subject == null) {
/* 243 */       AccessControlContext acc = AccessController.getContext();
/* 244 */       subject = Subject.getSubject(acc);
/*     */     } 
/* 246 */     if (subject == null) {
/* 247 */       log.log(Level.SEVERE, LogStringsMessages.WST_0030_REQUESTOR_NULL());
/*     */       
/* 249 */       throw new WSTrustException(LogStringsMessages.WST_0030_REQUESTOR_NULL());
/*     */     } 
/*     */     
/* 252 */     OnBehalfOf obo = rst.getOnBehalfOf();
/* 253 */     if (obo != null) {
/* 254 */       Object oboToken = obo.getAny();
/* 255 */       if (oboToken != null) {
/* 256 */         subject.getPublicCredentials().add(this.eleFac.toElement(oboToken));
/* 257 */         String confirMethod = null;
/* 258 */         if (tokenType.equals("urn:oasis:names:tc:SAML:1.0:assertion") || tokenType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1")) {
/*     */           
/* 260 */           confirMethod = "urn:oasis:names:tc:SAML:1.0:cm::sender-vouches";
/* 261 */         } else if (tokenType.equals("urn:oasis:names:tc:SAML:2.0:assertion") || tokenType.equals("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0")) {
/*     */           
/* 263 */           confirMethod = "urn:oasis:names:tc:SAML:2.0:cm:sender-vouches";
/*     */         } 
/* 265 */         if (confirMethod != null) {
/* 266 */           this.stsConfig.getOtherOptions().put("Saml-Confirmation-Method", confirMethod);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 272 */     STSAuthorizationProvider authzProvider = WSTrustFactory.getSTSAuthorizationProvider();
/* 273 */     if (!authzProvider.isAuthorized(subject, appliesTo, tokenType, keyType)) {
/* 274 */       String user = ((Principal)subject.getPrincipals().iterator().next()).getName();
/* 275 */       log.log(Level.SEVERE, LogStringsMessages.WST_0015_CLIENT_NOT_AUTHORIZED(user, tokenType, appliesTo));
/*     */ 
/*     */       
/* 278 */       throw new WSTrustException(LogStringsMessages.WST_0015_CLIENT_NOT_AUTHORIZED(user, tokenType, appliesTo));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 283 */     Claims claims = rst.getClaims();
/* 284 */     if (claims == null && secParas != null) {
/* 285 */       claims = secParas.getClaims();
/*     */     }
/* 287 */     if (claims == null) {
/* 288 */       claims = this.eleFac.createClaims();
/*     */     }
/* 290 */     STSAttributeProvider attrProvider = WSTrustFactory.getSTSAttributeProvider();
/* 291 */     Map<QName, List<String>> claimedAttrs = attrProvider.getClaimedAttributes(subject, appliesTo, tokenType, claims);
/*     */     
/* 293 */     RequestedProofToken proofToken = null;
/* 294 */     Entropy serverEntropy = null;
/* 295 */     int keySize = 0;
/* 296 */     if (this.wstVer.getSymmetricKeyTypeURI().equals(keyType)) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 301 */       proofToken = this.eleFac.createRequestedProofToken();
/*     */ 
/*     */       
/* 304 */       byte[] clientEntr = null;
/* 305 */       Entropy clientEntropy = rst.getEntropy();
/* 306 */       if (clientEntropy != null) {
/* 307 */         BinarySecret clientBS = clientEntropy.getBinarySecret();
/* 308 */         if (clientBS == null) {
/* 309 */           if (log.isLoggable(Level.FINE)) {
/* 310 */             log.log(Level.FINE, LogStringsMessages.WST_1009_NULL_BINARY_SECRET());
/*     */           }
/*     */         } else {
/*     */           
/* 314 */           clientEntr = clientBS.getRawValue();
/*     */         } 
/*     */       } 
/*     */       
/* 318 */       keySize = (int)rst.getKeySize();
/* 319 */       if (keySize < 1 && secParas != null) {
/* 320 */         keySize = (int)secParas.getKeySize();
/*     */       }
/* 322 */       if (keySize < 1) {
/* 323 */         keySize = 256;
/*     */       }
/* 325 */       if (log.isLoggable(Level.FINE)) {
/* 326 */         log.log(Level.FINE, LogStringsMessages.WST_1010_KEY_SIZE(Integer.valueOf(keySize), Integer.valueOf(256)));
/*     */       }
/*     */ 
/*     */       
/* 330 */       byte[] key = WSTrustUtil.generateRandomSecret(keySize / 8);
/* 331 */       BinarySecret serverBS = this.eleFac.createBinarySecret(key, this.wstVer.getNonceBinarySecretTypeURI());
/* 332 */       serverEntropy = this.eleFac.createEntropy(serverBS);
/*     */ 
/*     */       
/*     */       try {
/* 336 */         if (clientEntr != null && clientEntr.length > 0) {
/* 337 */           proofToken.setComputedKey(URI.create(this.wstVer.getCKPSHA1algorithmURI()));
/* 338 */           proofToken.setProofTokenType("ComputedKey");
/* 339 */           key = SecurityUtil.P_SHA1(clientEntr, key, keySize / 8);
/*     */         } else {
/* 341 */           proofToken.setProofTokenType("BinarySecret");
/* 342 */           proofToken.setBinarySecret(serverBS);
/*     */         } 
/* 344 */       } catch (Exception ex) {
/* 345 */         log.log(Level.SEVERE, LogStringsMessages.WST_0013_ERROR_SECRET_KEY(this.wstVer.getCKPSHA1algorithmURI(), Integer.valueOf(keySize), appliesTo), ex);
/*     */         
/* 347 */         throw new WSTrustException(LogStringsMessages.WST_0013_ERROR_SECRET_KEY(this.wstVer.getCKPSHA1algorithmURI(), Integer.valueOf(keySize), appliesTo), ex);
/*     */       } 
/*     */       
/* 350 */       context.setProofKey(key);
/* 351 */     } else if (this.wstVer.getPublicKeyTypeURI().equals(keyType)) {
/*     */       
/* 353 */       UseKey useKey = rst.getUseKey();
/* 354 */       if (useKey != null) {
/* 355 */         Element keyInfo = this.eleFac.toElement(useKey.getToken().getTokenValue());
/* 356 */         this.stsConfig.getOtherOptions().put("ConfirmationKeyInfo", keyInfo);
/*     */       } 
/* 358 */       Set<Object> certs = subject.getPublicCredentials();
/* 359 */       boolean addedClientCert = false;
/* 360 */       for (Object o : certs) {
/* 361 */         if (o instanceof X509Certificate) {
/* 362 */           X509Certificate clientCert = (X509Certificate)o;
/* 363 */           context.setRequestorCertificate(clientCert);
/* 364 */           addedClientCert = true;
/*     */         } 
/*     */       } 
/* 367 */       if (!addedClientCert && useKey == null) {
/* 368 */         log.log(Level.SEVERE, LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */         
/* 370 */         throw new WSTrustException(LogStringsMessages.WST_0034_UNABLE_GET_CLIENT_CERT());
/*     */       } 
/* 372 */     } else if (!this.wstVer.getBearerKeyTypeURI().equals(keyType)) {
/*     */ 
/*     */       
/* 375 */       log.log(Level.SEVERE, LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keyType, appliesTo));
/*     */       
/* 377 */       throw new WSTrustException(LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keyType, appliesTo));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 385 */     String assertionId = "uuid-" + UUID.randomUUID().toString();
/* 386 */     RequestedSecurityToken reqSecTok = this.eleFac.createRequestedSecurityToken();
/* 387 */     Token samlToken = createSAMLAssertion(appliesTo, tokenType, keyType, assertionId, this.stsConfig.getIssuer(), claimedAttrs, context);
/* 388 */     reqSecTok.setToken(samlToken);
/*     */ 
/*     */     
/* 391 */     String valueType = null;
/* 392 */     if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(tokenType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(tokenType)) {
/*     */       
/* 394 */       valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID";
/* 395 */     } else if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(tokenType)) {
/* 396 */       valueType = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID";
/*     */     } 
/* 398 */     SecurityTokenReference samlReference = WSTrustUtil.createSecurityTokenReference(assertionId, valueType);
/* 399 */     RequestedAttachedReference raRef = this.eleFac.createRequestedAttachedReference(samlReference);
/* 400 */     RequestedUnattachedReference ruRef = this.eleFac.createRequestedUnattachedReference(samlReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 407 */     URI ctx = null;
/*     */     try {
/* 409 */       String rstCtx = rst.getContext();
/* 410 */       if (rstCtx != null) {
/* 411 */         ctx = new URI(rst.getContext());
/*     */       }
/* 413 */     } catch (URISyntaxException ex) {
/* 414 */       log.log(Level.SEVERE, LogStringsMessages.WST_0014_URI_SYNTAX(), ex);
/*     */       
/* 416 */       throw new WSTrustException(LogStringsMessages.WST_0014_URI_SYNTAX(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 421 */     long currentTime = WSTrustUtil.getCurrentTimeWithOffset();
/* 422 */     Lifetime lifetime = WSTrustUtil.createLifetime(currentTime, this.stsConfig.getIssuedTokenTimeout(), this.wstVer);
/*     */     
/* 424 */     RequestSecurityTokenResponse rstr = this.eleFac.createRSTRForIssue(rst.getTokenType(), ctx, reqSecTok, applies, raRef, ruRef, proofToken, serverEntropy, lifetime);
/*     */ 
/*     */     
/* 427 */     if (keySize > 0) {
/* 428 */       rstr.setKeySize(keySize);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 437 */     context.setSecurityToken(samlToken);
/* 438 */     context.setAttachedSecurityTokenReference((Token)samlReference);
/* 439 */     context.setUnAttachedSecurityTokenReference((Token)samlReference);
/* 440 */     context.setCreationTime(new Date(currentTime));
/* 441 */     context.setExpirationTime(new Date(currentTime + this.stsConfig.getIssuedTokenTimeout()));
/*     */     
/* 443 */     if (this.wstVer.getNamespaceURI().equals(WSTrustVersion.WS_TRUST_13.getNamespaceURI())) {
/* 444 */       List<RequestSecurityTokenResponse> list = new ArrayList<RequestSecurityTokenResponse>();
/* 445 */       list.add(rstr);
/* 446 */       RequestSecurityTokenResponseCollection rstrc = this.eleFac.createRSTRC(list);
/*     */       
/* 448 */       return (BaseSTSResponse)rstrc;
/*     */     } 
/* 450 */     return (BaseSTSResponse)rstr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse issueMultiple(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/* 457 */     throw new UnsupportedOperationException("Unsupported operation: issueMultiple");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse renew(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/* 464 */     throw new UnsupportedOperationException("Unsupported operation: renew");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse cancel(BaseSTSRequest request, IssuedTokenContext context, Map issuedTokenCtxMap) throws WSTrustException {
/* 471 */     throw new UnsupportedOperationException("Unsupported operation: cancel");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseSTSResponse validate(BaseSTSRequest request, IssuedTokenContext context) throws WSTrustException {
/* 478 */     throw new UnsupportedOperationException("Unsupported operation: validate");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleUnsolicited(BaseSTSResponse rstr, IssuedTokenContext context) throws WSTrustException {
/* 488 */     throw new UnsupportedOperationException("Unsupported operation: handleUnsolicited");
/*     */   }
/*     */   
/*     */   public abstract Token createSAMLAssertion(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Map<QName, List<String>> paramMap, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\IssueSamlTokenContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */