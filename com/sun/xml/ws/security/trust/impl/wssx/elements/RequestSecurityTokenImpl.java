/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.policy.impl.bindings.PolicyReference;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
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
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.AllowPostdatingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.CancelTargetType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ClaimsType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.DelegateToType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.OnBehalfOfType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ParticipantsType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RenewTargetType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RenewingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RequestSecurityTokenType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.SecondaryParametersType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.SignChallengeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.UseKeyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ValidateTargetType;
/*     */ import com.sun.xml.ws.security.trust.util.WSTrustUtil;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBElement;
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
/*     */ public class RequestSecurityTokenImpl
/*     */   extends RequestSecurityTokenType
/*     */   implements RequestSecurityToken
/*     */ {
/* 101 */   private Claims claims = null;
/* 102 */   private Participants participants = null;
/* 103 */   private URI tokenType = null;
/*     */   
/* 105 */   private URI requestType = null;
/*     */   
/* 107 */   private long keySize = 0L;
/* 108 */   private URI keyType = null;
/* 109 */   private URI computedKeyAlgorithm = null;
/*     */   
/* 111 */   private URI signWith = null;
/* 112 */   private URI encryptWith = null;
/* 113 */   private URI keyWrapAlgorithm = null;
/* 114 */   private URI authenticationType = null;
/* 115 */   private URI signatureAlgorithm = null;
/* 116 */   private URI encryptionAlgorithm = null;
/* 117 */   private URI canonAlgorithm = null;
/*     */   
/* 119 */   private Lifetime lifetime = null;
/* 120 */   private Entropy entropy = null;
/* 121 */   private AppliesTo appliesTo = null;
/* 122 */   private OnBehalfOf obo = null;
/* 123 */   private ActAs actAs = null;
/* 124 */   private SignChallenge signChallenge = null;
/* 125 */   private Encryption encryption = null;
/* 126 */   private UseKey useKey = null;
/* 127 */   private DelegateTo delegateTo = null;
/* 128 */   private RenewTarget renewTarget = null;
/* 129 */   private CancelTarget cancelTarget = null;
/* 130 */   private ValidateTarget validateTarget = null;
/*     */   
/* 132 */   private AllowPostdating apd = null;
/* 133 */   private BinaryExchange binaryExchange = null;
/* 134 */   private Issuer issuer = null;
/* 135 */   private Renewing renewable = null;
/* 136 */   private ProofEncryption proofEncryption = null;
/*     */   
/*     */   private boolean forwardable = true;
/*     */   
/*     */   private boolean delegatable = false;
/* 141 */   private Policy policy = null;
/* 142 */   private PolicyReference policyRef = null;
/*     */   
/* 144 */   private SecondaryParameters sp = null;
/* 145 */   private List<Object> extendedElements = new ArrayList();
/*     */   
/*     */   public RequestSecurityTokenImpl() {
/* 148 */     setRequestType(URI.create(WSTrustVersion.WS_TRUST_13.getIssueRequestTypeURI()));
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType) {
/* 152 */     setTokenType(tokenType);
/* 153 */     setRequestType(requestType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, URI context, AppliesTo scopes, Claims claims, Entropy entropy, Lifetime lt, URI algorithm) {
/* 160 */     setTokenType(tokenType);
/* 161 */     setRequestType(requestType);
/* 162 */     if (context != null) {
/* 163 */       setContext(context.toString());
/*     */     }
/* 165 */     if (scopes != null) {
/* 166 */       setAppliesTo(scopes);
/*     */     }
/* 168 */     if (claims != null) {
/* 169 */       setClaims(claims);
/*     */     }
/* 171 */     if (entropy != null)
/* 172 */       setEntropy(entropy); 
/* 173 */     if (lt != null)
/* 174 */       setLifetime(lt); 
/* 175 */     if (algorithm != null) {
/* 176 */       setComputedKeyAlgorithm(algorithm);
/*     */     }
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, URI context, RenewTarget target, AllowPostdating apd, Renewing renewingInfo) {
/* 181 */     setTokenType(tokenType);
/* 182 */     setRequestType(requestType);
/* 183 */     if (context != null) {
/* 184 */       setContext(context.toString());
/*     */     }
/* 186 */     if (context != null) {
/* 187 */       setContext(context.toString());
/*     */     }
/* 189 */     if (target != null) {
/* 190 */       setRenewTarget(target);
/*     */     }
/* 192 */     if (apd != null) {
/* 193 */       setAllowPostdating(apd);
/*     */     }
/* 195 */     if (renewingInfo != null) {
/* 196 */       setRenewable(renewingInfo);
/*     */     }
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenImpl(URI tokenType, URI requestType, CancelTarget cancel) {
/* 201 */     setTokenType(tokenType);
/* 202 */     setRequestType(requestType);
/* 203 */     setCancelTarget(cancel);
/*     */   }
/*     */   
/*     */   public String getContext() {
/* 207 */     return super.getContext();
/*     */   }
/*     */   
/*     */   public void setClaims(Claims claims) {
/* 211 */     this.claims = claims;
/* 212 */     JAXBElement<ClaimsType> cElement = (new ObjectFactory()).createClaims((ClaimsType)claims);
/*     */     
/* 214 */     getAny().add(cElement);
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 218 */     return this.claims;
/*     */   }
/*     */   
/*     */   public void setCancelTarget(CancelTarget cTarget) {
/* 222 */     this.cancelTarget = cTarget;
/* 223 */     JAXBElement<CancelTargetType> ctElement = (new ObjectFactory()).createCancelTarget((CancelTargetType)cTarget);
/*     */     
/* 225 */     getAny().add(ctElement);
/*     */   }
/*     */   
/*     */   public CancelTarget getCancelTarget() {
/* 229 */     return this.cancelTarget;
/*     */   }
/*     */   
/*     */   public void setRenewTarget(RenewTarget target) {
/* 233 */     this.renewTarget = target;
/* 234 */     JAXBElement<RenewTargetType> rElement = (new ObjectFactory()).createRenewTarget((RenewTargetType)target);
/*     */     
/* 236 */     getAny().add(rElement);
/*     */   }
/*     */   
/*     */   public RenewTarget getRenewTarget() {
/* 240 */     return this.renewTarget;
/*     */   }
/*     */   
/*     */   public final void setValidateTarget(ValidateTarget target) {
/* 244 */     this.validateTarget = target;
/* 245 */     JAXBElement<ValidateTargetType> vtElement = (new ObjectFactory()).createValidateTarget((ValidateTargetType)target);
/*     */     
/* 247 */     getAny().add(vtElement);
/*     */   }
/*     */   
/*     */   public ValidateTarget getValidateTarget() {
/* 251 */     return this.validateTarget;
/*     */   }
/*     */   
/*     */   public void setParticipants(Participants participants) {
/* 255 */     this.participants = participants;
/* 256 */     JAXBElement<ParticipantsType> rElement = (new ObjectFactory()).createParticipants((ParticipantsType)participants);
/*     */     
/* 258 */     getAny().add(rElement);
/*     */   }
/*     */   
/*     */   public Participants getParticipants() {
/* 262 */     return this.participants;
/*     */   }
/*     */   
/*     */   public URI getTokenType() {
/* 266 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setTokenType(URI tokenType) {
/* 270 */     if (tokenType != null) {
/* 271 */       this.tokenType = tokenType;
/* 272 */       JAXBElement<String> ttElement = (new ObjectFactory()).createTokenType(tokenType.toString());
/*     */       
/* 274 */       getAny().add(ttElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getRequestType() {
/* 279 */     return this.requestType;
/*     */   }
/*     */   
/*     */   public void setRequestType(URI requestType) {
/* 283 */     if (requestType == null) {
/* 284 */       throw new RuntimeException("RequestType cannot be null");
/*     */     }
/* 286 */     String rtString = requestType.toString();
/* 287 */     if (!rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getIssueRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCancelRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getKeyExchangeRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getRenewRequestTypeURI()) && !rtString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getValidateRequestTypeURI()))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 292 */       throw new RuntimeException("Invalid Request Type specified");
/*     */     }
/* 294 */     this.requestType = requestType;
/* 295 */     JAXBElement<String> rtElement = (new ObjectFactory()).createRequestType(rtString);
/*     */     
/* 297 */     getAny().add(rtElement);
/*     */   }
/*     */   
/*     */   public Lifetime getLifetime() {
/* 301 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public void setLifetime(Lifetime lifetime) {
/* 305 */     this.lifetime = lifetime;
/* 306 */     JAXBElement<LifetimeType> ltElement = (new ObjectFactory()).createLifetime((LifetimeType)lifetime);
/*     */     
/* 308 */     getAny().add(ltElement);
/*     */   }
/*     */   
/*     */   public SecondaryParameters getSecondaryParameters() {
/* 312 */     return this.sp;
/*     */   }
/*     */   
/*     */   public void setSecondaryParameters(SecondaryParameters sp) {
/* 316 */     this.sp = sp;
/* 317 */     JAXBElement<SecondaryParametersType> spElement = (new ObjectFactory()).createSecondaryParameters((SecondaryParametersType)sp);
/*     */     
/* 319 */     getAny().add(spElement);
/*     */   }
/*     */   
/*     */   public Entropy getEntropy() {
/* 323 */     return this.entropy;
/*     */   }
/*     */   
/*     */   public void setEntropy(Entropy entropy) {
/* 327 */     this.entropy = entropy;
/* 328 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 330 */     getAny().add(etElement);
/*     */   }
/*     */   
/*     */   public void setAppliesTo(AppliesTo appliesTo) {
/* 334 */     getAny().add(appliesTo);
/* 335 */     this.appliesTo = appliesTo;
/*     */   }
/*     */   
/*     */   public AppliesTo getAppliesTo() {
/* 339 */     return this.appliesTo;
/*     */   }
/*     */   
/*     */   public void setOnBehalfOf(OnBehalfOf onBehalfOf) {
/* 343 */     this.obo = onBehalfOf;
/*     */     
/* 345 */     JAXBElement<OnBehalfOfType> oboElement = (new ObjectFactory()).createOnBehalfOf((OnBehalfOfType)onBehalfOf);
/*     */     
/* 347 */     getAny().add(oboElement);
/*     */   }
/*     */   
/*     */   public OnBehalfOf getOnBehalfOf() {
/* 351 */     return this.obo;
/*     */   }
/*     */   
/*     */   public void setActAs(ActAs actAs) {
/* 355 */     this.actAs = actAs;
/*     */ 
/*     */     
/* 358 */     Document doc = WSTrustUtil.newDocument();
/* 359 */     Element actAsElement = doc.createElementNS("http://docs.oasis-open.org/ws-sx/ws-trust/200802", "wst14:ActAs");
/* 360 */     actAsElement.setAttribute("xmlns:wst14", "http://docs.oasis-open.org/ws-sx/ws-trust/200802");
/* 361 */     doc.appendChild(actAsElement);
/* 362 */     actAsElement.appendChild(doc.importNode(WSTrustElementFactory.newInstance(WSTrustVersion.WS_TRUST_13).toElement(actAs.getAny()), true));
/* 363 */     getAny().add(actAsElement);
/*     */   }
/*     */   
/*     */   public ActAs getActAs() {
/* 367 */     return this.actAs;
/*     */   }
/*     */   
/*     */   public void setIssuer(Issuer issuer) {
/* 371 */     this.issuer = issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/* 378 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public void setRenewable(Renewing renew) {
/* 382 */     this.renewable = renew;
/* 383 */     JAXBElement<RenewingType> renewType = (new ObjectFactory()).createRenewing((RenewingType)renew);
/*     */     
/* 385 */     getAny().add(renewType);
/*     */   }
/*     */   
/*     */   public Renewing getRenewable() {
/* 389 */     return this.renewable;
/*     */   }
/*     */   
/*     */   public void setSignChallenge(SignChallenge challenge) {
/* 393 */     this.signChallenge = challenge;
/* 394 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallenge((SignChallengeType)challenge);
/*     */     
/* 396 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallenge getSignChallenge() {
/* 400 */     return this.signChallenge;
/*     */   }
/*     */   
/*     */   public void setBinaryExchange(BinaryExchange exchange) {
/* 404 */     this.binaryExchange = exchange;
/* 405 */     JAXBElement<BinaryExchangeType> exchangeType = (new ObjectFactory()).createBinaryExchange((BinaryExchangeType)exchange);
/*     */     
/* 407 */     getAny().add(exchangeType);
/*     */   }
/*     */   
/*     */   public BinaryExchange getBinaryExchange() {
/* 411 */     return this.binaryExchange;
/*     */   }
/*     */   
/*     */   public void setAuthenticationType(URI uri) {
/* 415 */     this.authenticationType = uri;
/* 416 */     JAXBElement<String> atElement = (new ObjectFactory()).createAuthenticationType(uri.toString());
/*     */     
/* 418 */     getAny().add(atElement);
/*     */   }
/*     */   
/*     */   public URI getAuthenticationType() {
/* 422 */     return this.authenticationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyType(URI keytype) throws WSTrustException {
/* 432 */     this.keyType = keytype;
/* 433 */     JAXBElement<String> ktElement = (new ObjectFactory()).createKeyType(this.keyType.toString());
/*     */     
/* 435 */     getAny().add(ktElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getKeyType() {
/* 440 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public void setKeySize(long size) {
/* 444 */     this.keySize = size;
/* 445 */     JAXBElement<Long> ksElement = (new ObjectFactory()).createKeySize(Long.valueOf(size));
/* 446 */     getAny().add(ksElement);
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 450 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(URI algorithm) {
/* 454 */     this.signatureAlgorithm = algorithm;
/* 455 */     JAXBElement<String> signElement = (new ObjectFactory()).createSignatureAlgorithm(algorithm.toString());
/*     */     
/* 457 */     getAny().add(signElement);
/*     */   }
/*     */   
/*     */   public URI getSignatureAlgorithm() {
/* 461 */     return this.signatureAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(URI algorithm) {
/* 465 */     this.encryptionAlgorithm = algorithm;
/* 466 */     JAXBElement<String> encElement = (new ObjectFactory()).createEncryptionAlgorithm(algorithm.toString());
/*     */     
/* 468 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptionAlgorithm() {
/* 472 */     return this.encryptionAlgorithm;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(URI algorithm) {
/* 476 */     this.canonAlgorithm = algorithm;
/* 477 */     JAXBElement<String> canonElement = (new ObjectFactory()).createCanonicalizationAlgorithm(algorithm.toString());
/*     */     
/* 479 */     getAny().add(canonElement);
/*     */   }
/*     */   
/*     */   public URI getCanonicalizationAlgorithm() {
/* 483 */     return this.canonAlgorithm;
/*     */   }
/*     */   
/*     */   public void setUseKey(UseKey useKey) {
/* 487 */     this.useKey = useKey;
/* 488 */     JAXBElement<UseKeyType> ukElement = (new ObjectFactory()).createUseKey((UseKeyType)useKey);
/*     */     
/* 490 */     getAny().add(ukElement);
/*     */   }
/*     */   
/*     */   public UseKey getUseKey() {
/* 494 */     return this.useKey;
/*     */   }
/*     */   
/*     */   public void setProofEncryption(ProofEncryption proofEncryption) {
/* 498 */     this.proofEncryption = proofEncryption;
/* 499 */     JAXBElement<ProofEncryptionType> proofElement = (new ObjectFactory()).createProofEncryption((ProofEncryptionType)proofEncryption);
/*     */     
/* 501 */     getAny().add(proofElement);
/*     */   }
/*     */   
/*     */   public ProofEncryption getProofEncryption() {
/* 505 */     return this.proofEncryption;
/*     */   }
/*     */   
/*     */   public void setComputedKeyAlgorithm(URI algorithm) {
/* 509 */     if (algorithm != null) {
/* 510 */       String ckaString = algorithm.toString();
/* 511 */       if (!ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKHASHalgorithmURI()) && !ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_13.getCKPSHA1algorithmURI()))
/*     */       {
/* 513 */         throw new RuntimeException("Invalid Computed Key Algorithm specified");
/*     */       }
/* 515 */       this.computedKeyAlgorithm = algorithm;
/* 516 */       JAXBElement<String> ckaElement = (new ObjectFactory()).createComputedKeyAlgorithm(ckaString);
/*     */       
/* 518 */       getAny().add(ckaElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getComputedKeyAlgorithm() {
/* 523 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryption(Encryption enc) {
/* 527 */     this.encryption = enc;
/* 528 */     JAXBElement<EncryptionType> encElement = (new ObjectFactory()).createEncryption((EncryptionType)enc);
/*     */     
/* 530 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public Encryption getEncryption() {
/* 534 */     return this.encryption;
/*     */   }
/*     */   
/*     */   public void setSignWith(URI algorithm) {
/* 538 */     this.signWith = algorithm;
/* 539 */     JAXBElement<String> sElement = (new ObjectFactory()).createSignWith(algorithm.toString());
/* 540 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getSignWith() {
/* 544 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(URI algorithm) {
/* 548 */     this.encryptWith = algorithm;
/* 549 */     JAXBElement<String> sElement = (new ObjectFactory()).createEncryptWith(algorithm.toString());
/* 550 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptWith() {
/* 554 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(URI algorithm) {
/* 558 */     this.keyWrapAlgorithm = algorithm;
/* 559 */     JAXBElement<String> keyWrapElement = (new ObjectFactory()).createKeyWrapAlgorithm(algorithm.toString());
/*     */     
/* 561 */     getAny().add(keyWrapElement);
/*     */   }
/*     */   
/*     */   public URI getKeyWrapAlgorithm() {
/* 565 */     return this.keyWrapAlgorithm;
/*     */   }
/*     */   
/*     */   public void setDelegateTo(DelegateTo to) {
/* 569 */     this.delegateTo = to;
/* 570 */     JAXBElement<DelegateToType> dtElement = (new ObjectFactory()).createDelegateTo((DelegateToType)to);
/*     */     
/* 572 */     getAny().add(dtElement);
/*     */   }
/*     */   
/*     */   public DelegateTo getDelegateTo() {
/* 576 */     return this.delegateTo;
/*     */   }
/*     */   
/*     */   public void setForwardable(boolean flag) {
/* 580 */     this.forwardable = flag;
/* 581 */     JAXBElement<Boolean> forward = (new ObjectFactory()).createForwardable(Boolean.valueOf(flag));
/*     */     
/* 583 */     getAny().add(forward);
/*     */   }
/*     */   
/*     */   public boolean getForwardable() {
/* 587 */     return this.forwardable;
/*     */   }
/*     */   
/*     */   public void setDelegatable(boolean flag) {
/* 591 */     this.delegatable = flag;
/* 592 */     JAXBElement<Boolean> del = (new ObjectFactory()).createDelegatable(Boolean.valueOf(flag));
/*     */     
/* 594 */     getAny().add(del);
/*     */   }
/*     */   
/*     */   public boolean getDelegatable() {
/* 598 */     return this.delegatable;
/*     */   }
/*     */   
/*     */   public void setPolicy(Policy policy) {
/* 602 */     this.policy = policy;
/* 603 */     getAny().add(policy);
/*     */   }
/*     */   
/*     */   public Policy getPolicy() {
/* 607 */     return this.policy;
/*     */   }
/*     */   
/*     */   public void setPolicyReference(PolicyReference policyRef) {
/* 611 */     this.policyRef = policyRef;
/* 612 */     getAny().add(policyRef);
/*     */   }
/*     */   
/*     */   public PolicyReference getPolicyReference() {
/* 616 */     return this.policyRef;
/*     */   }
/*     */   
/*     */   public AllowPostdating getAllowPostdating() {
/* 620 */     return this.apd;
/*     */   }
/*     */   
/*     */   public void setAllowPostdating(AllowPostdating allowPostdating) {
/* 624 */     this.apd = allowPostdating;
/* 625 */     JAXBElement<AllowPostdatingType> allowPd = (new ObjectFactory()).createAllowPostdating((AllowPostdatingType)this.apd);
/*     */     
/* 627 */     getAny().add(allowPd);
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenImpl(RequestSecurityTokenType rstType) throws Exception {
/* 632 */     this.context = rstType.getContext();
/* 633 */     List<Object> list = rstType.getAny();
/* 634 */     for (int i = 0; i < list.size(); i++) {
/* 635 */       if (list.get(i) instanceof AppliesTo)
/* 636 */       { setAppliesTo((AppliesTo)list.get(i));
/*     */          }
/*     */       
/* 639 */       else if (list.get(i) instanceof JAXBElement)
/* 640 */       { JAXBElement<String> obj = (JAXBElement)list.get(i);
/*     */         
/* 642 */         String local = obj.getName().getLocalPart();
/* 643 */         if (local.equalsIgnoreCase("RequestType")) {
/* 644 */           setRequestType(new URI(obj.getValue()));
/* 645 */         } else if (local.equalsIgnoreCase("KeySize")) {
/* 646 */           setKeySize(((Long)obj.getValue()).longValue());
/* 647 */         } else if (local.equalsIgnoreCase("KeyType")) {
/* 648 */           setKeyType(new URI(obj.getValue()));
/* 649 */         } else if (local.equalsIgnoreCase("ComputedKeyAlgorithm")) {
/* 650 */           setComputedKeyAlgorithm(new URI(obj.getValue()));
/* 651 */         } else if (local.equalsIgnoreCase("TokenType")) {
/* 652 */           setTokenType(new URI(obj.getValue()));
/* 653 */         } else if (local.equalsIgnoreCase("AuthenticationType")) {
/* 654 */           setAuthenticationType(new URI(obj.getValue()));
/* 655 */         } else if (local.equalsIgnoreCase("Lifetime")) {
/* 656 */           LifetimeType ltType = (LifetimeType)obj.getValue();
/* 657 */           setLifetime(new LifetimeImpl(ltType.getCreated(), ltType.getExpires()));
/* 658 */         } else if (local.equalsIgnoreCase("Entropy")) {
/* 659 */           EntropyType eType = (EntropyType)obj.getValue();
/* 660 */           setEntropy(new EntropyImpl(eType));
/* 661 */         } else if (local.equalsIgnoreCase("Forwardable")) {
/* 662 */           setForwardable(((Boolean)obj.getValue()).booleanValue());
/* 663 */         } else if (local.equalsIgnoreCase("Delegatable")) {
/* 664 */           setDelegatable(((Boolean)obj.getValue()).booleanValue());
/* 665 */         } else if (local.equalsIgnoreCase("SignWith")) {
/* 666 */           setSignWith(new URI(obj.getValue()));
/* 667 */         } else if (local.equalsIgnoreCase("EncryptWith")) {
/* 668 */           setEncryptWith(new URI(obj.getValue()));
/* 669 */         } else if (local.equalsIgnoreCase("SignatureAlgorithm")) {
/* 670 */           setSignatureAlgorithm(new URI(obj.getValue()));
/* 671 */         } else if (local.equalsIgnoreCase("EncryptionAlgorithm")) {
/* 672 */           setEncryptionAlgorithm(new URI(obj.getValue()));
/* 673 */         } else if (local.equalsIgnoreCase("CanonicalizationAlgorithm")) {
/* 674 */           setCanonicalizationAlgorithm(new URI(obj.getValue()));
/* 675 */         } else if (local.equalsIgnoreCase("AllowPostdating")) {
/* 676 */           setAllowPostdating(new AllowPostdatingImpl());
/* 677 */         } else if (local.equalsIgnoreCase("SignChallenge")) {
/* 678 */           setSignChallenge(new SignChallengeImpl());
/* 679 */         } else if (local.equalsIgnoreCase("BinaryExchange")) {
/* 680 */           BinaryExchangeType bcType = (BinaryExchangeType)obj.getValue();
/* 681 */           setBinaryExchange(new BinaryExchangeImpl(bcType));
/* 682 */         } else if (!local.equalsIgnoreCase("Issuer")) {
/*     */ 
/*     */           
/* 685 */           if (local.equalsIgnoreCase("Claims")) {
/* 686 */             ClaimsType cType = (ClaimsType)obj.getValue();
/* 687 */             setClaims(new ClaimsImpl(cType));
/* 688 */           } else if (local.equalsIgnoreCase("Participants")) {
/* 689 */             ParticipantsType psType = (ParticipantsType)obj.getValue();
/* 690 */             setParticipants(new ParticipantsImpl(psType));
/* 691 */           } else if (local.equalsIgnoreCase("Renewing")) {
/* 692 */             setRenewable(new RenewingImpl());
/* 693 */           } else if (local.equalsIgnoreCase("ProofEncryption")) {
/* 694 */             ProofEncryptionType peType = (ProofEncryptionType)obj.getValue();
/* 695 */             setProofEncryption(new ProofEncryptionImpl(peType));
/* 696 */           } else if (local.equalsIgnoreCase("Policy")) {
/* 697 */             setPolicy((Policy)obj.getValue());
/* 698 */           } else if (local.equalsIgnoreCase("PolicyReference")) {
/* 699 */             setPolicyReference((PolicyReference)obj.getValue());
/* 700 */           } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 701 */             setAppliesTo((AppliesTo)obj.getValue());
/* 702 */           } else if (local.equalsIgnoreCase("OnBehalfOf")) {
/* 703 */             OnBehalfOfType oboType = (OnBehalfOfType)obj.getValue();
/* 704 */             setOnBehalfOf(new OnBehalfOfImpl(oboType));
/* 705 */           } else if (local.equalsIgnoreCase("Encryption")) {
/* 706 */             EncryptionType encType = (EncryptionType)obj.getValue();
/* 707 */             setEncryption(new EncryptionImpl(encType));
/* 708 */           } else if (local.equalsIgnoreCase("UseKey")) {
/* 709 */             UseKeyType ukType = (UseKeyType)obj.getValue();
/* 710 */             setUseKey(new UseKeyImpl(ukType));
/* 711 */           } else if (local.equalsIgnoreCase("DelegateTo")) {
/* 712 */             DelegateToType dtType = (DelegateToType)obj.getValue();
/* 713 */             setDelegateTo(new DelegateToImpl(dtType));
/* 714 */           } else if (local.equalsIgnoreCase("RenewTarget")) {
/* 715 */             RenewTargetType rtType = (RenewTargetType)obj.getValue();
/* 716 */             setRenewTarget(new RenewTargetImpl(rtType));
/* 717 */           } else if (local.equalsIgnoreCase("CancelTarget")) {
/* 718 */             CancelTargetType ctType = (CancelTargetType)obj.getValue();
/* 719 */             setCancelTarget(new CancelTargetImpl(ctType));
/* 720 */           } else if (local.equalsIgnoreCase("ValidateTarget")) {
/* 721 */             ValidateTargetType vtType = (ValidateTargetType)obj.getValue();
/* 722 */             setValidateTarget(new ValidateTargetImpl(vtType));
/* 723 */           } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 724 */             setAppliesTo((AppliesTo)obj.getValue());
/* 725 */           } else if (local.equalsIgnoreCase("SecondaryParameters")) {
/* 726 */             setSecondaryParameters(new SecondaryParametersImpl((SecondaryParametersType)obj.getValue()));
/*     */           } else {
/* 728 */             getAny().add(list.get(i));
/* 729 */             this.extendedElements.add(list.get(i));
/*     */           } 
/*     */         }  }
/* 732 */       else { Object obj = list.get(i);
/* 733 */         if (obj instanceof Element && ((Element)obj).getLocalName().equals("ActAs")) {
/* 734 */           setActAs(new ActAsImpl((Element)obj));
/*     */         } else {
/* 736 */           getAny().add(list.get(i));
/* 737 */           this.extendedElements.add(list.get(i));
/*     */         }  }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Object> getExtensionElements() {
/* 744 */     return this.extendedElements;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\RequestSecurityTokenImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */