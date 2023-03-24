/*     */ package com.sun.xml.ws.security.trust.impl.elements;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.policy.impl.bindings.PolicyReference;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.ActAs;
/*     */ import com.sun.xml.ws.security.trust.elements.AllowPostdating;
/*     */ import com.sun.xml.ws.security.trust.elements.BinaryExchange;
/*     */ import com.sun.xml.ws.security.trust.elements.CancelTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.DelegateTo;
/*     */ import com.sun.xml.ws.security.trust.elements.Encryption;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Issuer;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.Participants;
/*     */ import com.sun.xml.ws.security.trust.elements.ProofEncryption;
/*     */ import com.sun.xml.ws.security.trust.elements.RenewTarget;
/*     */ import com.sun.xml.ws.security.trust.elements.Renewing;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.SignChallenge;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.elements.ValidateTarget;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.AllowPostdatingType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.CancelTargetType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ClaimsType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.DelegateToType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.OnBehalfOfType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ParticipantsType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RenewTargetType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RenewingType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.RequestSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.SignChallengeType;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.UseKeyType;
/*     */ import com.sun.xml.ws.security.trust.logging.LogStringsMessages;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ArrayList;
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
/*     */ public class RequestSecurityTokenImpl
/*     */   extends RequestSecurityTokenType
/*     */   implements RequestSecurityToken
/*     */ {
/*  98 */   private static final Logger log = Logger.getLogger("com.sun.xml.ws.security.trust", "com.sun.xml.ws.security.trust.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private Claims claims = null;
/* 104 */   private Participants participants = null;
/* 105 */   private URI tokenType = null;
/*     */   
/* 107 */   private URI requestType = null;
/*     */   
/* 109 */   private long keySize = 0L;
/* 110 */   private URI keyType = null;
/* 111 */   private URI computedKeyAlgorithm = null;
/*     */   
/* 113 */   private URI signWith = null;
/* 114 */   private URI encryptWith = null;
/* 115 */   private URI authenticationType = null;
/* 116 */   private URI signatureAlgorithm = null;
/* 117 */   private URI encryptionAlgorithm = null;
/* 118 */   private URI canonAlgorithm = null;
/*     */   
/* 120 */   private Lifetime lifetime = null;
/* 121 */   private Entropy entropy = null;
/* 122 */   private AppliesTo appliesTo = null;
/* 123 */   private OnBehalfOf obo = null;
/* 124 */   private SignChallenge signChallenge = null;
/* 125 */   private Encryption encryption = null;
/* 126 */   private UseKey useKey = null;
/* 127 */   private DelegateTo delegateTo = null;
/* 128 */   private RenewTarget renewTarget = null;
/* 129 */   private CancelTarget cancelTarget = null;
/*     */   
/* 131 */   private AllowPostdating apd = null;
/* 132 */   private BinaryExchange binaryExchange = null;
/* 133 */   private Issuer issuer = null;
/* 134 */   private Renewing renewable = null;
/* 135 */   private ProofEncryption proofEncryption = null;
/*     */   
/*     */   private boolean forwardable = true;
/*     */   
/*     */   private boolean delegatable = false;
/* 140 */   private Policy policy = null;
/* 141 */   private PolicyReference policyRef = null;
/*     */   
/* 143 */   List<Object> extendedElements = new ArrayList();
/*     */   
/*     */   public RequestSecurityTokenImpl() {
/* 146 */     setRequestType(URI.create(WSTrustVersion.WS_TRUST_10.getIssueRequestTypeURI()));
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType) {
/* 150 */     setTokenType(tokenType);
/* 151 */     setRequestType(requestType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, URI context, AppliesTo scopes, Claims claims, Entropy entropy, Lifetime lifetime, URI algorithm) {
/* 158 */     setTokenType(tokenType);
/* 159 */     setRequestType(requestType);
/* 160 */     if (context != null) {
/* 161 */       setContext(context.toString());
/*     */     }
/* 163 */     if (scopes != null) {
/* 164 */       setAppliesTo(scopes);
/*     */     }
/* 166 */     if (claims != null) {
/* 167 */       setClaims(claims);
/*     */     }
/* 169 */     if (entropy != null) {
/* 170 */       setEntropy(entropy);
/*     */     }
/* 172 */     if (lifetime != null) {
/* 173 */       setLifetime(lifetime);
/*     */     }
/* 175 */     if (algorithm != null) {
/* 176 */       setComputedKeyAlgorithm(algorithm);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, URI context, RenewTarget target, AllowPostdating apd, Renewing renewingInfo) {
/* 182 */     setTokenType(tokenType);
/* 183 */     setRequestType(requestType);
/* 184 */     if (context != null) {
/* 185 */       setContext(context.toString());
/*     */     }
/* 187 */     if (context != null) {
/* 188 */       setContext(context.toString());
/*     */     }
/* 190 */     if (target != null) {
/* 191 */       setRenewTarget(target);
/*     */     }
/* 193 */     if (apd != null) {
/* 194 */       setAllowPostdating(apd);
/*     */     }
/* 196 */     if (renewingInfo != null) {
/* 197 */       setRenewable(renewingInfo);
/*     */     }
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, CancelTarget cancel) {
/* 202 */     setTokenType(tokenType);
/* 203 */     setRequestType(requestType);
/* 204 */     setCancelTarget(cancel);
/*     */   }
/*     */   
/*     */   public final void setClaims(Claims claims) {
/* 208 */     this.claims = claims;
/* 209 */     JAXBElement<ClaimsType> cElement = (new ObjectFactory()).createClaims((ClaimsType)claims);
/*     */     
/* 211 */     getAny().add(cElement);
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 215 */     return this.claims;
/*     */   }
/*     */   
/*     */   public final void setCancelTarget(CancelTarget cTarget) {
/* 219 */     this.cancelTarget = cTarget;
/* 220 */     JAXBElement<CancelTargetType> ctElement = (new ObjectFactory()).createCancelTarget((CancelTargetType)cTarget);
/*     */     
/* 222 */     getAny().add(ctElement);
/*     */   }
/*     */   
/*     */   public CancelTarget getCancelTarget() {
/* 226 */     return this.cancelTarget;
/*     */   }
/*     */   
/*     */   public final void setRenewTarget(RenewTarget target) {
/* 230 */     this.renewTarget = target;
/* 231 */     JAXBElement<RenewTargetType> rElement = (new ObjectFactory()).createRenewTarget((RenewTargetType)target);
/*     */     
/* 233 */     getAny().add(rElement);
/*     */   }
/*     */   
/*     */   public RenewTarget getRenewTarget() {
/* 237 */     return this.renewTarget;
/*     */   }
/*     */   
/*     */   public final void setValidateTarget(ValidateTarget target) {
/* 241 */     throw new UnsupportedOperationException("Unsupported operation: setValidateTarget");
/*     */   }
/*     */   
/*     */   public ValidateTarget getValidateTarget() {
/* 245 */     throw new UnsupportedOperationException("Unsupported operation: getValidateTarget");
/*     */   }
/*     */   
/*     */   public final void setParticipants(Participants participants) {
/* 249 */     this.participants = participants;
/* 250 */     JAXBElement<ParticipantsType> rElement = (new ObjectFactory()).createParticipants((ParticipantsType)participants);
/*     */     
/* 252 */     getAny().add(rElement);
/*     */   }
/*     */   
/*     */   public Participants getParticipants() {
/* 256 */     return this.participants;
/*     */   }
/*     */   
/*     */   public URI getTokenType() {
/* 260 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public final void setTokenType(URI tokenType) {
/* 264 */     if (tokenType != null) {
/* 265 */       this.tokenType = tokenType;
/* 266 */       JAXBElement<String> ttElement = (new ObjectFactory()).createTokenType(tokenType.toString());
/*     */       
/* 268 */       getAny().add(ttElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSecondaryParameters(SecondaryParameters sp) {
/* 273 */     throw new UnsupportedOperationException("Unsupported operations!");
/*     */   }
/*     */   
/*     */   public SecondaryParameters getSecondaryParameters() {
/* 277 */     throw new UnsupportedOperationException("Unsupported operations!");
/*     */   }
/*     */   
/*     */   public URI getRequestType() {
/* 281 */     return this.requestType;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setRequestType(@NotNull URI requestType) {
/* 286 */     String rtString = requestType.toString();
/* 287 */     if (!rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getIssueRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCancelRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getKeyExchangeRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getRenewRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getValidateRequestTypeURI())) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 292 */       log.log(Level.SEVERE, LogStringsMessages.WST_0024_INVALID_REQUEST_TYPE(rtString));
/*     */       
/* 294 */       throw new RuntimeException(LogStringsMessages.WST_0024_INVALID_REQUEST_TYPE(rtString));
/*     */     } 
/* 296 */     this.requestType = requestType;
/* 297 */     JAXBElement<String> rtElement = (new ObjectFactory()).createRequestType(rtString);
/*     */     
/* 299 */     getAny().add(rtElement);
/*     */   }
/*     */   
/*     */   public Lifetime getLifetime() {
/* 303 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public final void setLifetime(Lifetime lifetime) {
/* 307 */     this.lifetime = lifetime;
/* 308 */     JAXBElement<LifetimeType> ltElement = (new ObjectFactory()).createLifetime((LifetimeType)lifetime);
/*     */     
/* 310 */     getAny().add(ltElement);
/*     */   }
/*     */   
/*     */   public Entropy getEntropy() {
/* 314 */     return this.entropy;
/*     */   }
/*     */   
/*     */   public final void setEntropy(Entropy entropy) {
/* 318 */     this.entropy = entropy;
/* 319 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 321 */     getAny().add(etElement);
/*     */   }
/*     */   
/*     */   public final void setAppliesTo(AppliesTo appliesTo) {
/* 325 */     getAny().add(appliesTo);
/* 326 */     this.appliesTo = appliesTo;
/*     */   }
/*     */   
/*     */   public AppliesTo getAppliesTo() {
/* 330 */     return this.appliesTo;
/*     */   }
/*     */   
/*     */   public final void setOnBehalfOf(OnBehalfOf onBehalfOf) {
/* 334 */     this.obo = onBehalfOf;
/*     */     
/* 336 */     JAXBElement<OnBehalfOfType> oboElement = (new ObjectFactory()).createOnBehalfOf((OnBehalfOfType)onBehalfOf);
/*     */     
/* 338 */     getAny().add(oboElement);
/*     */   }
/*     */   
/*     */   public OnBehalfOf getOnBehalfOf() {
/* 342 */     return this.obo;
/*     */   }
/*     */   
/*     */   public void setActAs(ActAs actAs) {
/* 346 */     throw new UnsupportedOperationException("Unsupported operation: setActAs");
/*     */   }
/*     */   
/*     */   public ActAs getActAs() {
/* 350 */     return null;
/*     */   }
/*     */   
/*     */   public final void setIssuer(Issuer issuer) {
/* 354 */     this.issuer = issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/* 361 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public final void setRenewable(Renewing renew) {
/* 365 */     this.renewable = renew;
/* 366 */     JAXBElement<RenewingType> renewType = (new ObjectFactory()).createRenewing((RenewingType)renew);
/*     */     
/* 368 */     getAny().add(renewType);
/*     */   }
/*     */   
/*     */   public Renewing getRenewable() {
/* 372 */     return this.renewable;
/*     */   }
/*     */   
/*     */   public final void setSignChallenge(SignChallenge challenge) {
/* 376 */     this.signChallenge = challenge;
/* 377 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallenge((SignChallengeType)challenge);
/*     */     
/* 379 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallenge getSignChallenge() {
/* 383 */     return this.signChallenge;
/*     */   }
/*     */   
/*     */   public final void setBinaryExchange(BinaryExchange exchange) {
/* 387 */     this.binaryExchange = exchange;
/* 388 */     JAXBElement<BinaryExchangeType> exchangeType = (new ObjectFactory()).createBinaryExchange((BinaryExchangeType)exchange);
/*     */     
/* 390 */     getAny().add(exchangeType);
/*     */   }
/*     */   
/*     */   public BinaryExchange getBinaryExchange() {
/* 394 */     return this.binaryExchange;
/*     */   }
/*     */   
/*     */   public final void setAuthenticationType(URI uri) {
/* 398 */     this.authenticationType = uri;
/* 399 */     JAXBElement<String> atElement = (new ObjectFactory()).createAuthenticationType(uri.toString());
/*     */     
/* 401 */     getAny().add(atElement);
/*     */   }
/*     */   
/*     */   public URI getAuthenticationType() {
/* 405 */     return this.authenticationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setKeyType(@NotNull URI keytype) throws WSTrustException {
/* 416 */     this.keyType = keytype;
/* 417 */     JAXBElement<String> ktElement = (new ObjectFactory()).createKeyType(this.keyType.toString());
/*     */     
/* 419 */     getAny().add(ktElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getKeyType() {
/* 424 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public final void setKeySize(long size) {
/* 428 */     this.keySize = size;
/* 429 */     JAXBElement<Long> ksElement = (new ObjectFactory()).createKeySize(Long.valueOf(size));
/* 430 */     getAny().add(ksElement);
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 434 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public final void setSignatureAlgorithm(URI algorithm) {
/* 438 */     this.signatureAlgorithm = algorithm;
/* 439 */     JAXBElement<String> signElement = (new ObjectFactory()).createSignatureAlgorithm(algorithm.toString());
/*     */     
/* 441 */     getAny().add(signElement);
/*     */   }
/*     */   
/*     */   public URI getSignatureAlgorithm() {
/* 445 */     return this.signatureAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setEncryptionAlgorithm(URI algorithm) {
/* 449 */     this.encryptionAlgorithm = algorithm;
/* 450 */     JAXBElement<String> encElement = (new ObjectFactory()).createEncryptionAlgorithm(algorithm.toString());
/*     */     
/* 452 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptionAlgorithm() {
/* 456 */     return this.encryptionAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setCanonicalizationAlgorithm(URI algorithm) {
/* 460 */     this.canonAlgorithm = algorithm;
/* 461 */     JAXBElement<String> canonElement = (new ObjectFactory()).createCanonicalizationAlgorithm(algorithm.toString());
/*     */     
/* 463 */     getAny().add(canonElement);
/*     */   }
/*     */   
/*     */   public URI getCanonicalizationAlgorithm() {
/* 467 */     return this.canonAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setUseKey(UseKey useKey) {
/* 471 */     this.useKey = useKey;
/* 472 */     JAXBElement<UseKeyType> ukElement = (new ObjectFactory()).createUseKey((UseKeyType)useKey);
/*     */     
/* 474 */     getAny().add(ukElement);
/*     */   }
/*     */   
/*     */   public UseKey getUseKey() {
/* 478 */     return this.useKey;
/*     */   }
/*     */   
/*     */   public final void setProofEncryption(ProofEncryption proofEncryption) {
/* 482 */     this.proofEncryption = proofEncryption;
/* 483 */     JAXBElement<ProofEncryptionType> proofElement = (new ObjectFactory()).createProofEncryption((ProofEncryptionType)proofEncryption);
/*     */     
/* 485 */     getAny().add(proofElement);
/*     */   }
/*     */   
/*     */   public ProofEncryption getProofEncryption() {
/* 489 */     return this.proofEncryption;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void setComputedKeyAlgorithm(@NotNull URI algorithm) {
/* 494 */     if (algorithm != null) {
/* 495 */       String ckaString = algorithm.toString();
/* 496 */       if (!ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKHASHalgorithmURI()) && !ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKPSHA1algorithmURI())) {
/*     */         
/* 498 */         log.log(Level.SEVERE, LogStringsMessages.WST_0026_INVALID_CK_ALGORITHM(ckaString));
/*     */         
/* 500 */         throw new RuntimeException(LogStringsMessages.WST_0026_INVALID_CK_ALGORITHM(ckaString));
/*     */       } 
/* 502 */       this.computedKeyAlgorithm = algorithm;
/* 503 */       JAXBElement<String> ckaElement = (new ObjectFactory()).createComputedKeyAlgorithm(ckaString);
/*     */       
/* 505 */       getAny().add(ckaElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getComputedKeyAlgorithm() {
/* 510 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public final void setEncryption(Encryption enc) {
/* 514 */     this.encryption = enc;
/* 515 */     JAXBElement<EncryptionType> encElement = (new ObjectFactory()).createEncryption((EncryptionType)enc);
/*     */     
/* 517 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public Encryption getEncryption() {
/* 521 */     return this.encryption;
/*     */   }
/*     */   
/*     */   public final void setSignWith(URI algorithm) {
/* 525 */     this.signWith = algorithm;
/* 526 */     JAXBElement<String> sElement = (new ObjectFactory()).createSignWith(algorithm.toString());
/* 527 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getSignWith() {
/* 531 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public final void setEncryptWith(URI algorithm) {
/* 535 */     this.encryptWith = algorithm;
/* 536 */     JAXBElement<String> sElement = (new ObjectFactory()).createEncryptWith(algorithm.toString());
/* 537 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptWith() {
/* 541 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(URI algorithm) {
/* 545 */     throw new UnsupportedOperationException("KeyWrapAlgorithm element in WS-Trust Standard version(1.0) is not supported");
/*     */   }
/*     */   
/*     */   public URI getKeyWrapAlgorithm() {
/* 549 */     throw new UnsupportedOperationException("KeyWrapAlgorithm element in WS-Trust Standard version(1.0) is not supported");
/*     */   }
/*     */   
/*     */   public final void setDelegateTo(DelegateTo delegateTo) {
/* 553 */     this.delegateTo = delegateTo;
/* 554 */     JAXBElement<DelegateToType> dtElement = (new ObjectFactory()).createDelegateTo((DelegateToType)delegateTo);
/*     */     
/* 556 */     getAny().add(dtElement);
/*     */   }
/*     */   
/*     */   public DelegateTo getDelegateTo() {
/* 560 */     return this.delegateTo;
/*     */   }
/*     */   
/*     */   public final void setForwardable(boolean flag) {
/* 564 */     this.forwardable = flag;
/* 565 */     JAXBElement<Boolean> forward = (new ObjectFactory()).createForwardable(Boolean.valueOf(flag));
/*     */     
/* 567 */     getAny().add(forward);
/*     */   }
/*     */   
/*     */   public boolean getForwardable() {
/* 571 */     return this.forwardable;
/*     */   }
/*     */   
/*     */   public final void setDelegatable(boolean flag) {
/* 575 */     this.delegatable = flag;
/* 576 */     JAXBElement<Boolean> del = (new ObjectFactory()).createDelegatable(Boolean.valueOf(flag));
/*     */     
/* 578 */     getAny().add(del);
/*     */   }
/*     */   
/*     */   public boolean getDelegatable() {
/* 582 */     return this.delegatable;
/*     */   }
/*     */   
/*     */   public final void setPolicy(Policy policy) {
/* 586 */     this.policy = policy;
/* 587 */     getAny().add(policy);
/*     */   }
/*     */   
/*     */   public Policy getPolicy() {
/* 591 */     return this.policy;
/*     */   }
/*     */   
/*     */   public final void setPolicyReference(PolicyReference policyRef) {
/* 595 */     this.policyRef = policyRef;
/* 596 */     getAny().add(policyRef);
/*     */   }
/*     */   
/*     */   public PolicyReference getPolicyReference() {
/* 600 */     return this.policyRef;
/*     */   }
/*     */   
/*     */   public AllowPostdating getAllowPostdating() {
/* 604 */     return this.apd;
/*     */   }
/*     */   
/*     */   public final void setAllowPostdating(AllowPostdating allowPostdating) {
/* 608 */     this.apd = allowPostdating;
/* 609 */     JAXBElement<AllowPostdatingType> allowPd = (new ObjectFactory()).createAllowPostdating((AllowPostdatingType)this.apd);
/*     */     
/* 611 */     getAny().add(allowPd);
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenImpl(RequestSecurityTokenType rstType) throws URISyntaxException, WSTrustException {
/* 616 */     this.context = rstType.getContext();
/* 617 */     List<Object> list = rstType.getAny();
/* 618 */     for (int i = 0; i < list.size(); i++) {
/* 619 */       if (list.get(i) instanceof AppliesTo)
/* 620 */       { setAppliesTo((AppliesTo)list.get(i));
/*     */          }
/*     */       
/* 623 */       else if (list.get(i) instanceof JAXBElement)
/* 624 */       { JAXBElement<String> obj = (JAXBElement)list.get(i);
/*     */         
/* 626 */         String local = obj.getName().getLocalPart();
/* 627 */         if (local.equalsIgnoreCase("RequestType")) {
/* 628 */           setRequestType(new URI(obj.getValue()));
/* 629 */         } else if (local.equalsIgnoreCase("KeySize")) {
/* 630 */           setKeySize(((Long)obj.getValue()).longValue());
/* 631 */         } else if (local.equalsIgnoreCase("KeyType")) {
/* 632 */           setKeyType(new URI(obj.getValue()));
/* 633 */         } else if (local.equalsIgnoreCase("ComputedKeyAlgorithm")) {
/* 634 */           setComputedKeyAlgorithm(new URI(obj.getValue()));
/* 635 */         } else if (local.equalsIgnoreCase("TokenType")) {
/* 636 */           setTokenType(new URI(obj.getValue()));
/* 637 */         } else if (local.equalsIgnoreCase("AuthenticationType")) {
/* 638 */           setAuthenticationType(new URI(obj.getValue()));
/* 639 */         } else if (local.equalsIgnoreCase("Lifetime")) {
/* 640 */           LifetimeType ltType = (LifetimeType)obj.getValue();
/* 641 */           setLifetime(new LifetimeImpl(ltType.getCreated(), ltType.getExpires()));
/* 642 */         } else if (local.equalsIgnoreCase("Entropy")) {
/* 643 */           EntropyType eType = (EntropyType)obj.getValue();
/* 644 */           setEntropy(new EntropyImpl(eType));
/* 645 */         } else if (local.equalsIgnoreCase("Forwardable")) {
/* 646 */           setForwardable(((Boolean)obj.getValue()).booleanValue());
/* 647 */         } else if (local.equalsIgnoreCase("Delegatable")) {
/* 648 */           setDelegatable(((Boolean)obj.getValue()).booleanValue());
/* 649 */         } else if (local.equalsIgnoreCase("SignWith")) {
/* 650 */           setSignWith(new URI(obj.getValue()));
/* 651 */         } else if (local.equalsIgnoreCase("EncryptWith")) {
/* 652 */           setEncryptWith(new URI(obj.getValue()));
/* 653 */         } else if (local.equalsIgnoreCase("SignatureAlgorithm")) {
/* 654 */           setSignatureAlgorithm(new URI(obj.getValue()));
/* 655 */         } else if (local.equalsIgnoreCase("EncryptionAlgorithm")) {
/* 656 */           setEncryptionAlgorithm(new URI(obj.getValue()));
/* 657 */         } else if (local.equalsIgnoreCase("CanonicalizationAlgorithm")) {
/* 658 */           setCanonicalizationAlgorithm(new URI(obj.getValue()));
/* 659 */         } else if (local.equalsIgnoreCase("AllowPostdating")) {
/* 660 */           setAllowPostdating(new AllowPostdatingImpl());
/* 661 */         } else if (local.equalsIgnoreCase("SignChallenge")) {
/* 662 */           setSignChallenge(new SignChallengeImpl());
/* 663 */         } else if (local.equalsIgnoreCase("BinaryExchange")) {
/* 664 */           BinaryExchangeType bcType = (BinaryExchangeType)obj.getValue();
/* 665 */           setBinaryExchange(new BinaryExchangeImpl(bcType));
/* 666 */         } else if (!local.equalsIgnoreCase("Issuer")) {
/*     */ 
/*     */           
/* 669 */           if (local.equalsIgnoreCase("Claims")) {
/* 670 */             ClaimsType cType = (ClaimsType)obj.getValue();
/* 671 */             setClaims(new ClaimsImpl(cType));
/* 672 */           } else if (local.equalsIgnoreCase("Participants")) {
/* 673 */             ParticipantsType psType = (ParticipantsType)obj.getValue();
/* 674 */             setParticipants(new ParticipantsImpl(psType));
/* 675 */           } else if (local.equalsIgnoreCase("Renewing")) {
/* 676 */             setRenewable(new RenewingImpl());
/* 677 */           } else if (local.equalsIgnoreCase("ProofEncryption")) {
/* 678 */             ProofEncryptionType peType = (ProofEncryptionType)obj.getValue();
/* 679 */             setProofEncryption(new ProofEncryptionImpl(peType));
/* 680 */           } else if (local.equalsIgnoreCase("Policy")) {
/* 681 */             setPolicy((Policy)obj.getValue());
/* 682 */           } else if (local.equalsIgnoreCase("PolicyReference")) {
/* 683 */             setPolicyReference((PolicyReference)obj.getValue());
/* 684 */           } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 685 */             setAppliesTo((AppliesTo)obj.getValue());
/* 686 */           } else if (local.equalsIgnoreCase("OnBehalfOf")) {
/* 687 */             OnBehalfOfType oboType = (OnBehalfOfType)obj.getValue();
/* 688 */             setOnBehalfOf(new OnBehalfOfImpl(oboType));
/* 689 */           } else if (local.equalsIgnoreCase("Encryption")) {
/* 690 */             EncryptionType encType = (EncryptionType)obj.getValue();
/* 691 */             setEncryption(new EncryptionImpl(encType));
/* 692 */           } else if (local.equalsIgnoreCase("UseKey")) {
/* 693 */             UseKeyType ukType = (UseKeyType)obj.getValue();
/* 694 */             setUseKey(new UseKeyImpl(ukType));
/* 695 */           } else if (local.equalsIgnoreCase("DelegateTo")) {
/* 696 */             DelegateToType dtType = (DelegateToType)obj.getValue();
/* 697 */             setDelegateTo(new DelegateToImpl(dtType));
/* 698 */           } else if (local.equalsIgnoreCase("RenewTarget")) {
/* 699 */             RenewTargetType rtType = (RenewTargetType)obj.getValue();
/* 700 */             setRenewTarget(new RenewTargetImpl(rtType));
/* 701 */           } else if (local.equalsIgnoreCase("CancelTarget")) {
/* 702 */             CancelTargetType ctType = (CancelTargetType)obj.getValue();
/* 703 */             setCancelTarget(new CancelTargetImpl(ctType));
/* 704 */           } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 705 */             setAppliesTo((AppliesTo)obj.getValue());
/*     */           } else {
/* 707 */             getAny().add(list.get(i));
/* 708 */             this.extendedElements.add(list.get(i));
/*     */           } 
/*     */         }  }
/* 711 */       else { getAny().add(list.get(i));
/* 712 */         this.extendedElements.add(list.get(i)); }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Object> getExtensionElements() {
/* 718 */     return this.extendedElements;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\elements\RequestSecurityTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */