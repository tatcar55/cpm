/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.Status;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.policy.impl.bindings.PolicyReference;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.AllowPostdating;
/*     */ import com.sun.xml.ws.security.trust.elements.Authenticator;
/*     */ import com.sun.xml.ws.security.trust.elements.BinaryExchange;
/*     */ import com.sun.xml.ws.security.trust.elements.DelegateTo;
/*     */ import com.sun.xml.ws.security.trust.elements.Encryption;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Issuer;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.ProofEncryption;
/*     */ import com.sun.xml.ws.security.trust.elements.Renewing;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityTokenResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedAttachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedProofToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedTokenCancelled;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestedUnattachedReference;
/*     */ import com.sun.xml.ws.security.trust.elements.SignChallenge;
/*     */ import com.sun.xml.ws.security.trust.elements.SignChallengeResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.impl.WSTrustVersion10;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.AllowPostdatingType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.AuthenticatorType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.DelegateToType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RenewingType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenResponseType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedProofTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedReferenceType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestedTokenCancelledType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.SignChallengeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.StatusType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.UseKeyType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RequestSecurityTokenResponseImpl
/*     */   extends RequestSecurityTokenResponseType
/*     */   implements RequestSecurityTokenResponse
/*     */ {
/* 100 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   private URI tokenType = null;
/*     */   
/* 107 */   private long keySize = 0L;
/*     */   
/* 109 */   private URI keyType = null;
/* 110 */   private URI computedKeyAlgorithm = null;
/* 111 */   private URI signatureAlgorithm = null;
/* 112 */   private URI encryptionAlgorithm = null;
/* 113 */   private URI canonAlgorithm = null;
/*     */   
/* 115 */   private Lifetime lifetime = null;
/* 116 */   private Entropy entropy = null;
/* 117 */   private AppliesTo appliesTo = null;
/* 118 */   private Authenticator authenticator = null;
/* 119 */   private UseKey useKey = null;
/* 120 */   private ProofEncryption proofEncryption = null;
/* 121 */   private Encryption encryption = null;
/* 122 */   private DelegateTo delegateTo = null;
/*     */   
/* 124 */   private OnBehalfOf obo = null;
/* 125 */   private RequestedSecurityToken requestedSecToken = null;
/* 126 */   private RequestedProofToken requestedProofToken = null;
/* 127 */   private RequestedAttachedReference requestedAttachedReference = null;
/* 128 */   private RequestedUnattachedReference requestedUnattachedReference = null;
/*     */   
/* 130 */   private URI signWith = null;
/* 131 */   private URI encryptWith = null;
/* 132 */   private URI authenticationType = null;
/*     */   
/* 134 */   private SignChallenge signChallenge = null;
/* 135 */   private SignChallengeResponse signChallengeRes = null;
/*     */   
/*     */   private boolean forwardable = true;
/*     */   
/*     */   private boolean delegatable = false;
/* 140 */   private Issuer issuer = null;
/* 141 */   private Renewing renewable = null;
/*     */   
/* 143 */   private BinaryExchange binaryExchange = null;
/* 144 */   private AllowPostdating apd = null;
/* 145 */   private Status status = null;
/*     */   
/* 147 */   private Policy policy = null;
/* 148 */   private PolicyReference policyRef = null;
/*     */   
/* 150 */   private RequestedTokenCancelled rtc = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseImpl() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseImpl(URI tokenType, URI context, RequestedSecurityToken token, AppliesTo scopes, RequestedAttachedReference attached, RequestedUnattachedReference unattached, RequestedProofToken proofToken, Entropy entropy, Lifetime lifetime, Status status) {
/* 167 */     setTokenType(tokenType);
/* 168 */     if (context != null) setContext(context.toString()); 
/* 169 */     if (token != null) setRequestedSecurityToken(token); 
/* 170 */     if (attached != null) setRequestedAttachedReference(attached); 
/* 171 */     if (unattached != null) setRequestedUnattachedReference(unattached); 
/* 172 */     if (scopes != null) setAppliesTo(scopes); 
/* 173 */     if (proofToken != null) setRequestedProofToken(proofToken); 
/* 174 */     if (entropy != null) setEntropy(entropy); 
/* 175 */     if (lifetime != null) setLifetime(lifetime); 
/* 176 */     if (status != null) setStatus(status); 
/*     */   }
/*     */   
/*     */   public URI getTokenType() {
/* 180 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public final void setTokenType(URI tokenType) {
/* 184 */     if (tokenType != null) {
/* 185 */       this.tokenType = tokenType;
/* 186 */       JAXBElement<String> ttElement = (new ObjectFactory()).createTokenType(tokenType.toString());
/*     */       
/* 188 */       getAny().add(ttElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Lifetime getLifetime() {
/* 193 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public final void setLifetime(Lifetime lifetime) {
/* 197 */     this.lifetime = lifetime;
/* 198 */     JAXBElement<LifetimeType> ltElement = (new ObjectFactory()).createLifetime((LifetimeType)lifetime);
/*     */     
/* 200 */     getAny().add(ltElement);
/*     */   }
/*     */   
/*     */   public RequestedTokenCancelled getRequestedTokenCancelled() {
/* 204 */     return this.rtc;
/*     */   }
/*     */   
/*     */   public final void setRequestedTokenCancelled(RequestedTokenCancelled rtc) {
/* 208 */     this.rtc = rtc;
/* 209 */     JAXBElement<RequestedTokenCancelledType> rtcElement = (new ObjectFactory()).createRequestedTokenCancelled((RequestedTokenCancelledType)rtc);
/*     */     
/* 211 */     getAny().add(rtcElement);
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/* 215 */     return this.status;
/*     */   }
/*     */   
/*     */   public final void setStatus(Status status) {
/* 219 */     this.status = status;
/* 220 */     JAXBElement<StatusType> sElement = (new ObjectFactory()).createStatus((StatusType)status);
/*     */     
/* 222 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public Entropy getEntropy() {
/* 226 */     return this.entropy;
/*     */   }
/*     */   
/*     */   public final void setEntropy(Entropy entropy) {
/* 230 */     this.entropy = entropy;
/* 231 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 233 */     getAny().add(etElement);
/*     */   }
/*     */   
/*     */   public final void setAppliesTo(AppliesTo appliesTo) {
/* 237 */     getAny().add(appliesTo);
/* 238 */     this.appliesTo = appliesTo;
/*     */   }
/*     */   
/*     */   public AppliesTo getAppliesTo() {
/* 242 */     return this.appliesTo;
/*     */   }
/*     */   
/*     */   public final void setOnBehalfOf(OnBehalfOf onBehalfOf) {
/* 246 */     this.obo = onBehalfOf;
/*     */   }
/*     */   
/*     */   public OnBehalfOf getOnBehalfOf() {
/* 250 */     return this.obo;
/*     */   }
/*     */   
/*     */   public final void setIssuer(Issuer issuer) {
/* 254 */     this.issuer = issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/* 261 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public final void setRenewable(Renewing renew) {
/* 265 */     this.renewable = renew;
/* 266 */     JAXBElement<RenewingType> renewType = (new ObjectFactory()).createRenewing((RenewingType)renew);
/*     */     
/* 268 */     getAny().add(renewType);
/*     */   }
/*     */   
/*     */   public Renewing getRenewable() {
/* 272 */     return this.renewable;
/*     */   }
/*     */   
/*     */   public final void setSignChallenge(SignChallenge challenge) {
/* 276 */     this.signChallenge = challenge;
/* 277 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallenge((SignChallengeType)challenge);
/*     */     
/* 279 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallenge getSignChallenge() {
/* 283 */     return this.signChallenge;
/*     */   }
/*     */   
/*     */   public final void setBinaryExchange(BinaryExchange exchange) {
/* 287 */     this.binaryExchange = exchange;
/* 288 */     JAXBElement<BinaryExchangeType> exchangeType = (new ObjectFactory()).createBinaryExchange((BinaryExchangeType)exchange);
/*     */     
/* 290 */     getAny().add(exchangeType);
/*     */   }
/*     */   
/*     */   public BinaryExchange getBinaryExchange() {
/* 294 */     return this.binaryExchange;
/*     */   }
/*     */   
/*     */   public final void setAuthenticationType(URI uri) {
/* 298 */     this.authenticationType = uri;
/* 299 */     JAXBElement<String> atElement = (new ObjectFactory()).createAuthenticationType(uri.toString());
/*     */     
/* 301 */     getAny().add(atElement);
/*     */   }
/*     */   
/*     */   public URI getAuthenticationType() {
/* 305 */     return this.authenticationType;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setKeyType(@NotNull URI keytype) throws WSTrustException {
/* 310 */     WSTrustVersion10 wSTrustVersion10 = new WSTrustVersion10();
/* 311 */     if (!keytype.toString().equalsIgnoreCase(wSTrustVersion10.getSymmetricKeyTypeURI()) && !keytype.toString().equalsIgnoreCase(wSTrustVersion10.getPublicKeyTypeURI()) && !keytype.toString().equalsIgnoreCase(wSTrustVersion10.getBearerKeyTypeURI())) {
/*     */ 
/*     */       
/* 314 */       log.log(Level.SEVERE, LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keytype.toString(), null));
/*     */       
/* 316 */       throw new WSTrustException(LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keytype.toString(), null));
/*     */     } 
/* 318 */     this.keyType = keytype;
/* 319 */     JAXBElement<String> ktElement = (new ObjectFactory()).createKeyType(this.keyType.toString());
/*     */     
/* 321 */     getAny().add(ktElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getKeyType() {
/* 326 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public final void setKeySize(@NotNull long size) {
/* 330 */     this.keySize = size;
/* 331 */     JAXBElement<Long> ksElement = (new ObjectFactory()).createKeySize(Long.valueOf(size));
/* 332 */     getAny().add(ksElement);
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 336 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public final void setSignatureAlgorithm(@NotNull URI algorithm) {
/* 340 */     this.signatureAlgorithm = algorithm;
/* 341 */     JAXBElement<String> signElement = (new ObjectFactory()).createSignatureAlgorithm(algorithm.toString());
/*     */     
/* 343 */     getAny().add(signElement);
/*     */   }
/*     */   
/*     */   public URI getSignatureAlgorithm() {
/* 347 */     return this.signatureAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setEncryptionAlgorithm(@NotNull URI algorithm) {
/* 351 */     this.encryptionAlgorithm = algorithm;
/* 352 */     JAXBElement<String> encElement = (new ObjectFactory()).createEncryptionAlgorithm(algorithm.toString());
/*     */     
/* 354 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptionAlgorithm() {
/* 358 */     return this.encryptionAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setCanonicalizationAlgorithm(@NotNull URI algorithm) {
/* 362 */     this.canonAlgorithm = algorithm;
/* 363 */     JAXBElement<String> canonElement = (new ObjectFactory()).createCanonicalizationAlgorithm(algorithm.toString());
/*     */     
/* 365 */     getAny().add(canonElement);
/*     */   }
/*     */   
/*     */   public URI getCanonicalizationAlgorithm() {
/* 369 */     return this.canonAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setUseKey(UseKey useKey) {
/* 373 */     this.useKey = useKey;
/* 374 */     JAXBElement<UseKeyType> ukElement = (new ObjectFactory()).createUseKey((UseKeyType)useKey);
/*     */     
/* 376 */     getAny().add(ukElement);
/*     */   }
/*     */   
/*     */   public UseKey getUseKey() {
/* 380 */     return this.useKey;
/*     */   }
/*     */   
/*     */   public final void setProofEncryption(ProofEncryption proofEncryption) {
/* 384 */     this.proofEncryption = proofEncryption;
/* 385 */     JAXBElement<ProofEncryptionType> proofElement = (new ObjectFactory()).createProofEncryption((ProofEncryptionType)proofEncryption);
/*     */     
/* 387 */     getAny().add(proofElement);
/*     */   }
/*     */   
/*     */   public ProofEncryption getProofEncryption() {
/* 391 */     return this.proofEncryption;
/*     */   }
/*     */   
/*     */   public final void setComputedKeyAlgorithm(@NotNull URI algorithm) {
/* 395 */     if (algorithm != null) {
/* 396 */       String ckaString = algorithm.toString();
/* 397 */       if (!ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKHASHalgorithmURI()) && !ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKPSHA1algorithmURI()))
/*     */       {
/* 399 */         throw new RuntimeException("Invalid Computed Key Algorithm specified");
/*     */       }
/* 401 */       this.computedKeyAlgorithm = algorithm;
/* 402 */       JAXBElement<String> ckaElement = (new ObjectFactory()).createComputedKeyAlgorithm(ckaString);
/*     */       
/* 404 */       getAny().add(ckaElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getComputedKeyAlgorithm() {
/* 409 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setEncryption(Encryption enc) {
/* 413 */     this.encryption = enc;
/* 414 */     JAXBElement<EncryptionType> encElement = (new ObjectFactory()).createEncryption((EncryptionType)enc);
/*     */     
/* 416 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public Encryption getEncryption() {
/* 420 */     return this.encryption;
/*     */   }
/*     */   
/*     */   public final void setSignWith(URI algorithm) {
/* 424 */     this.signWith = algorithm;
/* 425 */     JAXBElement<String> sElement = (new ObjectFactory()).createSignWith(algorithm.toString());
/* 426 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getSignWith() {
/* 430 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public final void setEncryptWith(@NotNull URI algorithm) {
/* 434 */     this.encryptWith = algorithm;
/* 435 */     JAXBElement<String> sElement = (new ObjectFactory()).createEncryptWith(algorithm.toString());
/* 436 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptWith() {
/* 440 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(URI algorithm) {
/* 444 */     throw new UnsupportedOperationException("KeyWrapAlgorithm element in WS-Trust Standard version(1.0) is not supported");
/*     */   }
/*     */   
/*     */   public URI getKeyWrapAlgorithm() {
/* 448 */     throw new UnsupportedOperationException("KeyWrapAlgorithm element in WS-Trust Standard version(1.0) is not supported");
/*     */   }
/*     */   
/*     */   public final void setDelegateTo(DelegateTo to) {
/* 452 */     this.delegateTo = to;
/* 453 */     JAXBElement<DelegateToType> dtElement = (new ObjectFactory()).createDelegateTo((DelegateToType)to);
/*     */     
/* 455 */     getAny().add(dtElement);
/*     */   }
/*     */   
/*     */   public DelegateTo getDelegateTo() {
/* 459 */     return this.delegateTo;
/*     */   }
/*     */   
/*     */   public final void setForwardable(boolean flag) {
/* 463 */     this.forwardable = flag;
/* 464 */     JAXBElement<Boolean> forward = (new ObjectFactory()).createForwardable(Boolean.valueOf(flag));
/*     */     
/* 466 */     getAny().add(forward);
/*     */   }
/*     */   
/*     */   public boolean getForwardable() {
/* 470 */     return this.forwardable;
/*     */   }
/*     */   
/*     */   public final void setDelegatable(boolean flag) {
/* 474 */     this.delegatable = flag;
/* 475 */     JAXBElement<Boolean> del = (new ObjectFactory()).createDelegatable(Boolean.valueOf(flag));
/*     */     
/* 477 */     getAny().add(del);
/*     */   }
/*     */   
/*     */   public boolean getDelegatable() {
/* 481 */     return this.delegatable;
/*     */   }
/*     */   
/*     */   public final void setPolicy(Policy policy) {
/* 485 */     this.policy = policy;
/* 486 */     getAny().add(policy);
/*     */   }
/*     */   
/*     */   public Policy getPolicy() {
/* 490 */     return this.policy;
/*     */   }
/*     */   
/*     */   public final void setPolicyReference(PolicyReference policyRef) {
/* 494 */     this.policyRef = policyRef;
/* 495 */     getAny().add(policyRef);
/*     */   }
/*     */   
/*     */   public PolicyReference getPolicyReference() {
/* 499 */     return this.policyRef;
/*     */   }
/*     */   
/*     */   public AllowPostdating getAllowPostdating() {
/* 503 */     return this.apd;
/*     */   }
/*     */   
/*     */   public final void setAllowPostdating(AllowPostdating allowPostdating) {
/* 507 */     this.apd = allowPostdating;
/* 508 */     JAXBElement<AllowPostdatingType> allowPd = (new ObjectFactory()).createAllowPostdating((AllowPostdatingType)this.apd);
/*     */     
/* 510 */     getAny().add(allowPd);
/*     */   }
/*     */   
/*     */   public final void setSignChallengeResponse(SignChallengeResponse challenge) {
/* 514 */     this.signChallengeRes = challenge;
/* 515 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallengeResponse((SignChallengeType)challenge);
/*     */     
/* 517 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallengeResponse getSignChallengeResponse() {
/* 521 */     return this.signChallengeRes;
/*     */   }
/*     */   
/*     */   public final void setAuthenticator(Authenticator authenticator) {
/* 525 */     this.authenticator = authenticator;
/* 526 */     JAXBElement<AuthenticatorType> authType = (new ObjectFactory()).createAuthenticator((AuthenticatorType)authenticator);
/*     */     
/* 528 */     getAny().add(authType);
/*     */   }
/*     */   
/*     */   public Authenticator getAuthenticator() {
/* 532 */     return this.authenticator;
/*     */   }
/*     */   
/*     */   public final void setRequestedProofToken(RequestedProofToken proofToken) {
/* 536 */     this.requestedProofToken = proofToken;
/* 537 */     JAXBElement<RequestedProofTokenType> pElement = (new ObjectFactory()).createRequestedProofToken((RequestedProofTokenType)proofToken);
/*     */     
/* 539 */     getAny().add(pElement);
/*     */   }
/*     */   
/*     */   public RequestedProofToken getRequestedProofToken() {
/* 543 */     return this.requestedProofToken;
/*     */   }
/*     */   
/*     */   public final void setRequestedSecurityToken(RequestedSecurityToken securityToken) {
/* 547 */     this.requestedSecToken = securityToken;
/* 548 */     JAXBElement<RequestedSecurityTokenType> rstElement = (new ObjectFactory()).createRequestedSecurityToken((RequestedSecurityTokenType)securityToken);
/*     */ 
/*     */     
/* 551 */     getAny().add(rstElement);
/*     */   }
/*     */   
/*     */   public RequestedSecurityToken getRequestedSecurityToken() {
/* 555 */     return this.requestedSecToken;
/*     */   }
/*     */   
/*     */   public final void setRequestedAttachedReference(RequestedAttachedReference reference) {
/* 559 */     this.requestedAttachedReference = reference;
/* 560 */     JAXBElement<RequestedReferenceType> raElement = (new ObjectFactory()).createRequestedAttachedReference((RequestedReferenceType)reference);
/*     */     
/* 562 */     getAny().add(raElement);
/*     */   }
/*     */   
/*     */   public RequestedAttachedReference getRequestedAttachedReference() {
/* 566 */     return this.requestedAttachedReference;
/*     */   }
/*     */   
/*     */   public final void setRequestedUnattachedReference(RequestedUnattachedReference reference) {
/* 570 */     this.requestedUnattachedReference = reference;
/* 571 */     JAXBElement<RequestedReferenceType> raElement = (new ObjectFactory()).createRequestedUnattachedReference((RequestedReferenceType)reference);
/*     */     
/* 573 */     getAny().add(raElement);
/*     */   }
/*     */   
/*     */   public RequestedUnattachedReference getRequestedUnattachedReference() {
/* 577 */     return this.requestedUnattachedReference;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseImpl(RequestSecurityTokenResponseType rstrType) throws URISyntaxException, WSTrustException {
/* 583 */     this.context = rstrType.getContext();
/* 584 */     List<Object> list = rstrType.getAny();
/* 585 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 587 */       if (list.get(i) instanceof AppliesTo) {
/* 588 */         setAppliesTo((AppliesTo)list.get(i));
/*     */       }
/*     */       else {
/*     */         
/* 592 */         Object object = list.get(i);
/* 593 */         if (!(object instanceof JAXBElement)) {
/* 594 */           getAny().add(object);
/*     */         } else {
/* 596 */           JAXBElement<Long> obj = (JAXBElement)list.get(i);
/* 597 */           String local = obj.getName().getLocalPart();
/* 598 */           if (local.equalsIgnoreCase("KeySize")) {
/* 599 */             setKeySize(((Long)obj.getValue()).longValue());
/* 600 */           } else if (local.equalsIgnoreCase("KeyType")) {
/* 601 */             setKeyType(new URI((String)obj.getValue()));
/* 602 */           } else if (local.equalsIgnoreCase("ComputedKeyAlgorithm")) {
/* 603 */             setComputedKeyAlgorithm(new URI((String)obj.getValue()));
/* 604 */           } else if (local.equalsIgnoreCase("TokenType")) {
/* 605 */             setTokenType(new URI((String)obj.getValue()));
/* 606 */           } else if (local.equalsIgnoreCase("AuthenticationType")) {
/* 607 */             setAuthenticationType(new URI((String)obj.getValue()));
/* 608 */           } else if (local.equalsIgnoreCase("Lifetime")) {
/* 609 */             LifetimeType ltType = (LifetimeType)obj.getValue();
/* 610 */             setLifetime(new LifetimeImpl(ltType));
/* 611 */           } else if (local.equalsIgnoreCase("Entropy")) {
/* 612 */             EntropyType eType = (EntropyType)obj.getValue();
/* 613 */             setEntropy(new EntropyImpl(eType));
/* 614 */           } else if (local.equalsIgnoreCase("Forwardable")) {
/* 615 */             setForwardable(((Boolean)obj.getValue()).booleanValue());
/* 616 */           } else if (local.equalsIgnoreCase("Delegatable")) {
/* 617 */             setDelegatable(((Boolean)obj.getValue()).booleanValue());
/* 618 */           } else if (local.equalsIgnoreCase("SignWith")) {
/* 619 */             setSignWith(new URI((String)obj.getValue()));
/* 620 */           } else if (local.equalsIgnoreCase("EncryptWith")) {
/* 621 */             setEncryptWith(new URI((String)obj.getValue()));
/* 622 */           } else if (local.equalsIgnoreCase("SignatureAlgorithm")) {
/* 623 */             setSignatureAlgorithm(new URI((String)obj.getValue()));
/* 624 */           } else if (local.equalsIgnoreCase("EncryptionAlgorithm")) {
/* 625 */             setEncryptionAlgorithm(new URI((String)obj.getValue()));
/* 626 */           } else if (local.equalsIgnoreCase("CanonicalizationAlgorithm")) {
/* 627 */             setCanonicalizationAlgorithm(new URI((String)obj.getValue()));
/* 628 */           } else if (local.equalsIgnoreCase("AllowPostdating")) {
/* 629 */             setAllowPostdating(new AllowPostdatingImpl());
/* 630 */           } else if (local.equalsIgnoreCase("SignChallenge")) {
/* 631 */             setSignChallenge(new SignChallengeImpl());
/* 632 */           } else if (local.equalsIgnoreCase("SignChallengeResponse")) {
/* 633 */             setSignChallengeResponse(new SignChallengeResponseImpl());
/* 634 */           } else if (local.equalsIgnoreCase("BinaryExchange")) {
/* 635 */             BinaryExchangeType bcType = (BinaryExchangeType)obj.getValue();
/* 636 */             setBinaryExchange(new BinaryExchangeImpl(bcType));
/* 637 */           } else if (!local.equalsIgnoreCase("Issuer")) {
/*     */ 
/*     */             
/* 640 */             if (local.equalsIgnoreCase("Authenticator")) {
/* 641 */               AuthenticatorType aType = (AuthenticatorType)obj.getValue();
/* 642 */               setAuthenticator(new AuthenticatorImpl(aType));
/* 643 */             } else if (local.equalsIgnoreCase("Renewing")) {
/* 644 */               setRenewable(new RenewingImpl());
/* 645 */             } else if (local.equalsIgnoreCase("ProofEncryption")) {
/* 646 */               ProofEncryptionType peType = (ProofEncryptionType)obj.getValue();
/* 647 */               setProofEncryption(new ProofEncryptionImpl(peType));
/* 648 */             } else if (local.equalsIgnoreCase("Policy")) {
/* 649 */               setPolicy((Policy)obj.getValue());
/* 650 */             } else if (local.equalsIgnoreCase("PolicyReference")) {
/* 651 */               setPolicyReference((PolicyReference)obj.getValue());
/* 652 */             } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 653 */               setAppliesTo((AppliesTo)obj.getValue());
/* 654 */             } else if (local.equalsIgnoreCase("OnBehalfOf")) {
/* 655 */               this.obo = (OnBehalfOf)obj.getValue();
/* 656 */             } else if (local.equalsIgnoreCase("Encryption")) {
/* 657 */               EncryptionType encType = (EncryptionType)obj.getValue();
/* 658 */               setEncryption(new EncryptionImpl(encType));
/* 659 */             } else if (local.equalsIgnoreCase("UseKey")) {
/* 660 */               UseKeyType ukType = (UseKeyType)obj.getValue();
/* 661 */               setUseKey(new UseKeyImpl(ukType));
/* 662 */             } else if (local.equalsIgnoreCase("Status")) {
/* 663 */               StatusType sType = (StatusType)obj.getValue();
/* 664 */               setStatus(new StatusImpl(sType));
/* 665 */             } else if (local.equalsIgnoreCase("DelegateTo")) {
/* 666 */               DelegateToType dtType = (DelegateToType)obj.getValue();
/* 667 */               setDelegateTo(new DelegateToImpl(dtType));
/* 668 */             } else if (local.equalsIgnoreCase("RequestedProofToken")) {
/* 669 */               RequestedProofTokenType rptType = (RequestedProofTokenType)obj.getValue();
/* 670 */               setRequestedProofToken(new RequestedProofTokenImpl(rptType));
/* 671 */             } else if (local.equalsIgnoreCase("RequestedSecurityToken")) {
/* 672 */               RequestedSecurityTokenType rdstType = (RequestedSecurityTokenType)obj.getValue();
/* 673 */               setRequestedSecurityToken(new RequestedSecurityTokenImpl(rdstType));
/* 674 */             } else if (local.equalsIgnoreCase("RequestedAttachedReference")) {
/* 675 */               RequestedReferenceType rarType = (RequestedReferenceType)obj.getValue();
/* 676 */               setRequestedAttachedReference(new RequestedAttachedReferenceImpl(rarType));
/* 677 */             } else if (local.equalsIgnoreCase("RequestedUnattachedReference")) {
/* 678 */               RequestedReferenceType rarType = (RequestedReferenceType)obj.getValue();
/* 679 */               setRequestedUnattachedReference(new RequestedUnattachedReferenceImpl(rarType));
/* 680 */             } else if (local.equalsIgnoreCase("RequestedTokenCancelled")) {
/* 681 */               setRequestedTokenCancelled(new RequestedTokenCancelledImpl());
/*     */             } else {
/* 683 */               getAny().add(obj.getValue());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestSecurityTokenResponseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */