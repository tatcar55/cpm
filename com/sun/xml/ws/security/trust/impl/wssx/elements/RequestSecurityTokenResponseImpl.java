/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
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
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.WSTrustVersion13;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.AllowPostdatingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.AuthenticatorType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.DelegateToType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RenewingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenResponseType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedProofTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedReferenceType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestedTokenCancelledType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.SignChallengeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.StatusType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.UseKeyType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ public class RequestSecurityTokenResponseImpl
/*     */   extends RequestSecurityTokenResponseType
/*     */   implements RequestSecurityTokenResponse
/*     */ {
/*  97 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   private URI tokenType = null;
/*     */   
/* 104 */   private long keySize = 0L;
/*     */   
/* 106 */   private URI keyType = null;
/* 107 */   private URI computedKeyAlgorithm = null;
/* 108 */   private URI signatureAlgorithm = null;
/* 109 */   private URI encryptionAlgorithm = null;
/* 110 */   private URI canonAlgorithm = null;
/*     */   
/* 112 */   private Lifetime lifetime = null;
/* 113 */   private Entropy entropy = null;
/* 114 */   private AppliesTo appliesTo = null;
/* 115 */   private Authenticator authenticator = null;
/* 116 */   private UseKey useKey = null;
/* 117 */   private ProofEncryption proofEncryption = null;
/* 118 */   private Encryption encryption = null;
/* 119 */   private DelegateTo delegateTo = null;
/*     */   
/* 121 */   private OnBehalfOf obo = null;
/* 122 */   private RequestedSecurityToken requestedSecToken = null;
/* 123 */   private RequestedProofToken requestedProofToken = null;
/* 124 */   private RequestedAttachedReference requestedAttachedReference = null;
/* 125 */   private RequestedUnattachedReference requestedUnattachedReference = null;
/*     */   
/* 127 */   private URI signWith = null;
/* 128 */   private URI encryptWith = null;
/* 129 */   private URI keyWrapAlgorithm = null;
/* 130 */   private URI authenticationType = null;
/*     */   
/* 132 */   private SignChallenge signChallenge = null;
/* 133 */   private SignChallengeResponse signChallengeRes = null;
/*     */   
/*     */   private boolean forwardable = true;
/*     */   
/*     */   private boolean delegatable = false;
/* 138 */   private Issuer issuer = null;
/* 139 */   private Renewing renewable = null;
/*     */   
/* 141 */   private BinaryExchange binaryExchange = null;
/* 142 */   private AllowPostdating apd = null;
/* 143 */   private Status status = null;
/*     */   
/* 145 */   private Policy policy = null;
/* 146 */   private PolicyReference policyRef = null;
/*     */   
/* 148 */   private RequestedTokenCancelled rtc = null;
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
/* 165 */     setTokenType(tokenType);
/* 166 */     if (context != null) setContext(context.toString()); 
/* 167 */     if (token != null) setRequestedSecurityToken(token); 
/* 168 */     if (attached != null) setRequestedAttachedReference(attached); 
/* 169 */     if (unattached != null) setRequestedUnattachedReference(unattached); 
/* 170 */     if (scopes != null) setAppliesTo(scopes); 
/* 171 */     if (proofToken != null) setRequestedProofToken(proofToken); 
/* 172 */     if (entropy != null) setEntropy(entropy); 
/* 173 */     if (lifetime != null) setLifetime(lifetime); 
/* 174 */     if (status != null) setStatus(status); 
/*     */   }
/*     */   
/*     */   public URI getTokenType() {
/* 178 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setTokenType(URI tokenType) {
/* 182 */     if (tokenType != null) {
/* 183 */       this.tokenType = tokenType;
/* 184 */       JAXBElement<String> ttElement = (new ObjectFactory()).createTokenType(tokenType.toString());
/*     */       
/* 186 */       getAny().add(ttElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<QName, String> getOtherAttributes() {
/* 191 */     return super.getOtherAttributes();
/*     */   }
/*     */   
/*     */   public Lifetime getLifetime() {
/* 195 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public void setLifetime(Lifetime lifetime) {
/* 199 */     this.lifetime = lifetime;
/* 200 */     JAXBElement<LifetimeType> ltElement = (new ObjectFactory()).createLifetime((LifetimeType)lifetime);
/*     */     
/* 202 */     getAny().add(ltElement);
/*     */   }
/*     */   
/*     */   public RequestedTokenCancelled getRequestedTokenCancelled() {
/* 206 */     return this.rtc;
/*     */   }
/*     */   
/*     */   public void setRequestedTokenCancelled(RequestedTokenCancelled rtc) {
/* 210 */     this.rtc = rtc;
/* 211 */     JAXBElement<RequestedTokenCancelledType> rtcElement = (new ObjectFactory()).createRequestedTokenCancelled((RequestedTokenCancelledType)rtc);
/*     */     
/* 213 */     getAny().add(rtcElement);
/*     */   }
/*     */   
/*     */   public Status getStatus() {
/* 217 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(Status status) {
/* 221 */     this.status = status;
/* 222 */     JAXBElement<StatusType> sElement = (new ObjectFactory()).createStatus((StatusType)status);
/*     */     
/* 224 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public Entropy getEntropy() {
/* 228 */     return this.entropy;
/*     */   }
/*     */   
/*     */   public void setEntropy(Entropy entropy) {
/* 232 */     this.entropy = entropy;
/* 233 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 235 */     getAny().add(etElement);
/*     */   }
/*     */   
/*     */   public List<Object> getAny() {
/* 239 */     return super.getAny();
/*     */   }
/*     */   
/*     */   public String getContext() {
/* 243 */     return super.getContext();
/*     */   }
/*     */   
/*     */   public void setAppliesTo(AppliesTo appliesTo) {
/* 247 */     getAny().add(appliesTo);
/* 248 */     this.appliesTo = appliesTo;
/*     */   }
/*     */   
/*     */   public AppliesTo getAppliesTo() {
/* 252 */     return this.appliesTo;
/*     */   }
/*     */   
/*     */   public void setOnBehalfOf(OnBehalfOf onBehalfOf) {
/* 256 */     this.obo = onBehalfOf;
/*     */   }
/*     */   
/*     */   public OnBehalfOf getOnBehalfOf() {
/* 260 */     return this.obo;
/*     */   }
/*     */   
/*     */   public void setIssuer(Issuer issuer) {
/* 264 */     this.issuer = issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/* 271 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public void setRenewable(Renewing renew) {
/* 275 */     this.renewable = renew;
/* 276 */     JAXBElement<RenewingType> renewType = (new ObjectFactory()).createRenewing((RenewingType)renew);
/*     */     
/* 278 */     getAny().add(renewType);
/*     */   }
/*     */   
/*     */   public Renewing getRenewable() {
/* 282 */     return this.renewable;
/*     */   }
/*     */   
/*     */   public void setSignChallenge(SignChallenge challenge) {
/* 286 */     this.signChallenge = challenge;
/* 287 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallenge((SignChallengeType)challenge);
/*     */     
/* 289 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallenge getSignChallenge() {
/* 293 */     return this.signChallenge;
/*     */   }
/*     */   
/*     */   public void setBinaryExchange(BinaryExchange exchange) {
/* 297 */     this.binaryExchange = exchange;
/* 298 */     JAXBElement<BinaryExchangeType> exchangeType = (new ObjectFactory()).createBinaryExchange((BinaryExchangeType)exchange);
/*     */     
/* 300 */     getAny().add(exchangeType);
/*     */   }
/*     */   
/*     */   public BinaryExchange getBinaryExchange() {
/* 304 */     return this.binaryExchange;
/*     */   }
/*     */   
/*     */   public void setAuthenticationType(URI uri) {
/* 308 */     this.authenticationType = uri;
/* 309 */     JAXBElement<String> atElement = (new ObjectFactory()).createAuthenticationType(uri.toString());
/*     */     
/* 311 */     getAny().add(atElement);
/*     */   }
/*     */   
/*     */   public URI getAuthenticationType() {
/* 315 */     return this.authenticationType;
/*     */   }
/*     */   
/*     */   public void setKeyType(URI keytype) throws WSTrustException {
/* 319 */     WSTrustVersion13 wSTrustVersion13 = new WSTrustVersion13();
/* 320 */     if (!keytype.toString().equalsIgnoreCase(wSTrustVersion13.getSymmetricKeyTypeURI()) && !keytype.toString().equalsIgnoreCase(wSTrustVersion13.getPublicKeyTypeURI()) && !keytype.toString().equalsIgnoreCase(wSTrustVersion13.getBearerKeyTypeURI())) {
/*     */ 
/*     */       
/* 323 */       log.log(Level.SEVERE, LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keytype.toString(), null));
/*     */       
/* 325 */       throw new WSTrustException(LogStringsMessages.WST_0025_INVALID_KEY_TYPE(keytype.toString(), null));
/*     */     } 
/* 327 */     this.keyType = keytype;
/* 328 */     JAXBElement<String> ktElement = (new ObjectFactory()).createKeyType(this.keyType.toString());
/*     */     
/* 330 */     getAny().add(ktElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getKeyType() {
/* 335 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public void setKeySize(long size) {
/* 339 */     this.keySize = size;
/* 340 */     JAXBElement<Long> ksElement = (new ObjectFactory()).createKeySize(Long.valueOf(size));
/* 341 */     getAny().add(ksElement);
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 345 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(URI algorithm) {
/* 349 */     this.signatureAlgorithm = algorithm;
/* 350 */     JAXBElement<String> signElement = (new ObjectFactory()).createSignatureAlgorithm(algorithm.toString());
/*     */     
/* 352 */     getAny().add(signElement);
/*     */   }
/*     */   
/*     */   public URI getSignatureAlgorithm() {
/* 356 */     return this.signatureAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(URI algorithm) {
/* 360 */     this.encryptionAlgorithm = algorithm;
/* 361 */     JAXBElement<String> encElement = (new ObjectFactory()).createEncryptionAlgorithm(algorithm.toString());
/*     */     
/* 363 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptionAlgorithm() {
/* 367 */     return this.encryptionAlgorithm;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(URI algorithm) {
/* 371 */     this.canonAlgorithm = algorithm;
/* 372 */     JAXBElement<String> canonElement = (new ObjectFactory()).createCanonicalizationAlgorithm(algorithm.toString());
/*     */     
/* 374 */     getAny().add(canonElement);
/*     */   }
/*     */   
/*     */   public URI getCanonicalizationAlgorithm() {
/* 378 */     return this.canonAlgorithm;
/*     */   }
/*     */   
/*     */   public void setUseKey(UseKey useKey) {
/* 382 */     this.useKey = useKey;
/* 383 */     JAXBElement<UseKeyType> ukElement = (new ObjectFactory()).createUseKey((UseKeyType)useKey);
/*     */     
/* 385 */     getAny().add(ukElement);
/*     */   }
/*     */   
/*     */   public UseKey getUseKey() {
/* 389 */     return this.useKey;
/*     */   }
/*     */   
/*     */   public void setProofEncryption(ProofEncryption proofEncryption) {
/* 393 */     this.proofEncryption = proofEncryption;
/* 394 */     JAXBElement<ProofEncryptionType> proofElement = (new ObjectFactory()).createProofEncryption((ProofEncryptionType)proofEncryption);
/*     */     
/* 396 */     getAny().add(proofElement);
/*     */   }
/*     */   
/*     */   public ProofEncryption getProofEncryption() {
/* 400 */     return this.proofEncryption;
/*     */   }
/*     */   
/*     */   public void setComputedKeyAlgorithm(URI algorithm) {
/* 404 */     if (algorithm != null) {
/* 405 */       String ckaString = algorithm.toString();
/* 406 */       if (!ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKHASHalgorithmURI()) && !ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKPSHA1algorithmURI()))
/*     */       {
/* 408 */         throw new RuntimeException("Invalid Computed Key Algorithm specified");
/*     */       }
/* 410 */       this.computedKeyAlgorithm = algorithm;
/* 411 */       JAXBElement<String> ckaElement = (new ObjectFactory()).createComputedKeyAlgorithm(ckaString);
/*     */       
/* 413 */       getAny().add(ckaElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getComputedKeyAlgorithm() {
/* 418 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryption(Encryption enc) {
/* 422 */     this.encryption = enc;
/* 423 */     JAXBElement<EncryptionType> encElement = (new ObjectFactory()).createEncryption((EncryptionType)enc);
/*     */     
/* 425 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public Encryption getEncryption() {
/* 429 */     return this.encryption;
/*     */   }
/*     */   
/*     */   public void setSignWith(URI algorithm) {
/* 433 */     this.signWith = algorithm;
/* 434 */     JAXBElement<String> sElement = (new ObjectFactory()).createSignWith(algorithm.toString());
/* 435 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getSignWith() {
/* 439 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(URI algorithm) {
/* 443 */     this.encryptWith = algorithm;
/* 444 */     JAXBElement<String> sElement = (new ObjectFactory()).createEncryptWith(algorithm.toString());
/* 445 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptWith() {
/* 449 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(URI algorithm) {
/* 453 */     this.keyWrapAlgorithm = algorithm;
/* 454 */     JAXBElement<String> keyWrapElement = (new ObjectFactory()).createKeyWrapAlgorithm(algorithm.toString());
/*     */     
/* 456 */     getAny().add(keyWrapElement);
/*     */   }
/*     */   
/*     */   public URI getKeyWrapAlgorithm() {
/* 460 */     return this.keyWrapAlgorithm;
/*     */   }
/*     */   
/*     */   public void setDelegateTo(DelegateTo to) {
/* 464 */     this.delegateTo = to;
/* 465 */     JAXBElement<DelegateToType> dtElement = (new ObjectFactory()).createDelegateTo((DelegateToType)to);
/*     */     
/* 467 */     getAny().add(dtElement);
/*     */   }
/*     */   
/*     */   public DelegateTo getDelegateTo() {
/* 471 */     return this.delegateTo;
/*     */   }
/*     */   
/*     */   public void setForwardable(boolean flag) {
/* 475 */     this.forwardable = flag;
/* 476 */     JAXBElement<Boolean> forward = (new ObjectFactory()).createForwardable(Boolean.valueOf(flag));
/*     */     
/* 478 */     getAny().add(forward);
/*     */   }
/*     */   
/*     */   public boolean getForwardable() {
/* 482 */     return this.forwardable;
/*     */   }
/*     */   
/*     */   public void setDelegatable(boolean flag) {
/* 486 */     this.delegatable = flag;
/* 487 */     JAXBElement<Boolean> del = (new ObjectFactory()).createDelegatable(Boolean.valueOf(flag));
/*     */     
/* 489 */     getAny().add(del);
/*     */   }
/*     */   
/*     */   public boolean getDelegatable() {
/* 493 */     return this.delegatable;
/*     */   }
/*     */   
/*     */   public void setPolicy(Policy policy) {
/* 497 */     this.policy = policy;
/* 498 */     getAny().add(policy);
/*     */   }
/*     */   
/*     */   public Policy getPolicy() {
/* 502 */     return this.policy;
/*     */   }
/*     */   
/*     */   public void setPolicyReference(PolicyReference policyRef) {
/* 506 */     this.policyRef = policyRef;
/* 507 */     getAny().add(policyRef);
/*     */   }
/*     */   
/*     */   public PolicyReference getPolicyReference() {
/* 511 */     return this.policyRef;
/*     */   }
/*     */   
/*     */   public AllowPostdating getAllowPostdating() {
/* 515 */     return this.apd;
/*     */   }
/*     */   
/*     */   public void setAllowPostdating(AllowPostdating allowPostdating) {
/* 519 */     this.apd = allowPostdating;
/* 520 */     JAXBElement<AllowPostdatingType> allowPd = (new ObjectFactory()).createAllowPostdating((AllowPostdatingType)this.apd);
/*     */     
/* 522 */     getAny().add(allowPd);
/*     */   }
/*     */   
/*     */   public void setSignChallengeResponse(SignChallengeResponse challenge) {
/* 526 */     this.signChallengeRes = challenge;
/* 527 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallengeResponse((SignChallengeType)challenge);
/*     */     
/* 529 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallengeResponse getSignChallengeResponse() {
/* 533 */     return this.signChallengeRes;
/*     */   }
/*     */   
/*     */   public void setAuthenticator(Authenticator authenticator) {
/* 537 */     this.authenticator = authenticator;
/* 538 */     JAXBElement<AuthenticatorType> authType = (new ObjectFactory()).createAuthenticator((AuthenticatorType)authenticator);
/*     */     
/* 540 */     getAny().add(authType);
/*     */   }
/*     */   
/*     */   public Authenticator getAuthenticator() {
/* 544 */     return this.authenticator;
/*     */   }
/*     */   
/*     */   public void setRequestedProofToken(RequestedProofToken proofToken) {
/* 548 */     this.requestedProofToken = proofToken;
/* 549 */     JAXBElement<RequestedProofTokenType> pElement = (new ObjectFactory()).createRequestedProofToken((RequestedProofTokenType)proofToken);
/*     */     
/* 551 */     getAny().add(pElement);
/*     */   }
/*     */   
/*     */   public RequestedProofToken getRequestedProofToken() {
/* 555 */     return this.requestedProofToken;
/*     */   }
/*     */   
/*     */   public void setRequestedSecurityToken(RequestedSecurityToken securityToken) {
/* 559 */     this.requestedSecToken = securityToken;
/* 560 */     JAXBElement<RequestedSecurityTokenType> rstElement = (new ObjectFactory()).createRequestedSecurityToken((RequestedSecurityTokenType)securityToken);
/*     */ 
/*     */     
/* 563 */     getAny().add(rstElement);
/*     */   }
/*     */   
/*     */   public RequestedSecurityToken getRequestedSecurityToken() {
/* 567 */     return this.requestedSecToken;
/*     */   }
/*     */   
/*     */   public void setRequestedAttachedReference(RequestedAttachedReference reference) {
/* 571 */     this.requestedAttachedReference = reference;
/* 572 */     JAXBElement<RequestedReferenceType> raElement = (new ObjectFactory()).createRequestedAttachedReference((RequestedReferenceType)reference);
/*     */     
/* 574 */     getAny().add(raElement);
/*     */   }
/*     */   
/*     */   public RequestedAttachedReference getRequestedAttachedReference() {
/* 578 */     return this.requestedAttachedReference;
/*     */   }
/*     */   
/*     */   public void setRequestedUnattachedReference(RequestedUnattachedReference reference) {
/* 582 */     this.requestedUnattachedReference = reference;
/* 583 */     JAXBElement<RequestedReferenceType> raElement = (new ObjectFactory()).createRequestedUnattachedReference((RequestedReferenceType)reference);
/*     */     
/* 585 */     getAny().add(raElement);
/*     */   }
/*     */   
/*     */   public RequestedUnattachedReference getRequestedUnattachedReference() {
/* 589 */     return this.requestedUnattachedReference;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseImpl(RequestSecurityTokenResponseType rstrType) throws Exception {
/* 595 */     this.context = rstrType.getContext();
/* 596 */     List<Object> list = rstrType.getAny();
/* 597 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 599 */       if (list.get(i) instanceof AppliesTo)
/* 600 */       { setAppliesTo((AppliesTo)list.get(i));
/*     */         
/*     */          }
/*     */       
/* 604 */       else if (list.get(i) instanceof JAXBElement)
/*     */       
/* 606 */       { JAXBElement<Long> obj = (JAXBElement)list.get(i);
/*     */         
/* 608 */         String local = obj.getName().getLocalPart();
/* 609 */         if (local.equalsIgnoreCase("KeySize")) {
/* 610 */           setKeySize(((Long)obj.getValue()).longValue());
/* 611 */         } else if (local.equalsIgnoreCase("KeyType")) {
/* 612 */           setKeyType(new URI((String)obj.getValue()));
/* 613 */         } else if (local.equalsIgnoreCase("ComputedKeyAlgorithm")) {
/* 614 */           setComputedKeyAlgorithm(new URI((String)obj.getValue()));
/* 615 */         } else if (local.equalsIgnoreCase("TokenType")) {
/* 616 */           setTokenType(new URI((String)obj.getValue()));
/* 617 */         } else if (local.equalsIgnoreCase("AuthenticationType")) {
/* 618 */           setAuthenticationType(new URI((String)obj.getValue()));
/* 619 */         } else if (local.equalsIgnoreCase("Lifetime")) {
/* 620 */           LifetimeType ltType = (LifetimeType)obj.getValue();
/* 621 */           setLifetime(new LifetimeImpl(ltType));
/* 622 */         } else if (local.equalsIgnoreCase("Entropy")) {
/* 623 */           EntropyType eType = (EntropyType)obj.getValue();
/* 624 */           setEntropy(new EntropyImpl(eType));
/* 625 */         } else if (local.equalsIgnoreCase("Forwardable")) {
/* 626 */           setForwardable(((Boolean)obj.getValue()).booleanValue());
/* 627 */         } else if (local.equalsIgnoreCase("Delegatable")) {
/* 628 */           setDelegatable(((Boolean)obj.getValue()).booleanValue());
/* 629 */         } else if (local.equalsIgnoreCase("SignWith")) {
/* 630 */           setSignWith(new URI((String)obj.getValue()));
/* 631 */         } else if (local.equalsIgnoreCase("EncryptWith")) {
/* 632 */           setEncryptWith(new URI((String)obj.getValue()));
/* 633 */         } else if (local.equalsIgnoreCase("SignatureAlgorithm")) {
/* 634 */           setSignatureAlgorithm(new URI((String)obj.getValue()));
/* 635 */         } else if (local.equalsIgnoreCase("EncryptionAlgorithm")) {
/* 636 */           setEncryptionAlgorithm(new URI((String)obj.getValue()));
/* 637 */         } else if (local.equalsIgnoreCase("CanonicalizationAlgorithm")) {
/* 638 */           setCanonicalizationAlgorithm(new URI((String)obj.getValue()));
/* 639 */         } else if (local.equalsIgnoreCase("AllowPostdating")) {
/* 640 */           setAllowPostdating(new AllowPostdatingImpl());
/* 641 */         } else if (local.equalsIgnoreCase("SignChallenge")) {
/* 642 */           setSignChallenge(new SignChallengeImpl());
/* 643 */         } else if (local.equalsIgnoreCase("SignChallengeResponse")) {
/* 644 */           setSignChallengeResponse(new SignChallengeResponseImpl());
/* 645 */         } else if (local.equalsIgnoreCase("BinaryExchange")) {
/* 646 */           BinaryExchangeType bcType = (BinaryExchangeType)obj.getValue();
/* 647 */           setBinaryExchange(new BinaryExchangeImpl(bcType));
/* 648 */         } else if (!local.equalsIgnoreCase("Issuer")) {
/*     */ 
/*     */           
/* 651 */           if (local.equalsIgnoreCase("Authenticator")) {
/* 652 */             AuthenticatorType aType = (AuthenticatorType)obj.getValue();
/* 653 */             setAuthenticator(new AuthenticatorImpl(aType));
/* 654 */           } else if (local.equalsIgnoreCase("Renewing")) {
/* 655 */             setRenewable(new RenewingImpl());
/* 656 */           } else if (local.equalsIgnoreCase("ProofEncryption")) {
/* 657 */             ProofEncryptionType peType = (ProofEncryptionType)obj.getValue();
/* 658 */             setProofEncryption(new ProofEncryptionImpl(peType));
/* 659 */           } else if (local.equalsIgnoreCase("Policy")) {
/* 660 */             setPolicy((Policy)obj.getValue());
/* 661 */           } else if (local.equalsIgnoreCase("PolicyReference")) {
/* 662 */             setPolicyReference((PolicyReference)obj.getValue());
/* 663 */           } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 664 */             setAppliesTo((AppliesTo)obj.getValue());
/* 665 */           } else if (local.equalsIgnoreCase("OnBehalfOf")) {
/* 666 */             this.obo = (OnBehalfOf)obj.getValue();
/* 667 */           } else if (local.equalsIgnoreCase("Encryption")) {
/* 668 */             EncryptionType encType = (EncryptionType)obj.getValue();
/* 669 */             setEncryption(new EncryptionImpl(encType));
/* 670 */           } else if (local.equalsIgnoreCase("UseKey")) {
/* 671 */             UseKeyType ukType = (UseKeyType)obj.getValue();
/* 672 */             setUseKey(new UseKeyImpl(ukType));
/* 673 */           } else if (local.equalsIgnoreCase("Status")) {
/* 674 */             StatusType sType = (StatusType)obj.getValue();
/* 675 */             setStatus(new StatusImpl(sType));
/* 676 */           } else if (local.equalsIgnoreCase("DelegateTo")) {
/* 677 */             DelegateToType dtType = (DelegateToType)obj.getValue();
/* 678 */             setDelegateTo(new DelegateToImpl(dtType));
/* 679 */           } else if (local.equalsIgnoreCase("RequestedProofToken")) {
/* 680 */             RequestedProofTokenType rptType = (RequestedProofTokenType)obj.getValue();
/* 681 */             setRequestedProofToken(new RequestedProofTokenImpl(rptType));
/* 682 */           } else if (local.equalsIgnoreCase("RequestedSecurityToken")) {
/* 683 */             RequestedSecurityTokenType rdstType = (RequestedSecurityTokenType)obj.getValue();
/* 684 */             setRequestedSecurityToken(new RequestedSecurityTokenImpl(rdstType));
/* 685 */           } else if (local.equalsIgnoreCase("RequestedAttachedReference")) {
/* 686 */             RequestedReferenceType rarType = (RequestedReferenceType)obj.getValue();
/* 687 */             setRequestedAttachedReference(new RequestedAttachedReferenceImpl(rarType));
/* 688 */           } else if (local.equalsIgnoreCase("RequestedUnattachedReference")) {
/* 689 */             RequestedReferenceType rarType = (RequestedReferenceType)obj.getValue();
/* 690 */             setRequestedUnattachedReference(new RequestedUnattachedReferenceImpl(rarType));
/* 691 */           } else if (local.equalsIgnoreCase("RequestedTokenCancelled")) {
/* 692 */             setRequestedTokenCancelled(new RequestedTokenCancelledImpl());
/*     */           } 
/*     */         }  }
/* 695 */       else { getAny().add(list.get(i)); }
/*     */     
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\RequestSecurityTokenResponseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */