/*     */ package com.sun.xml.ws.security.trust.impl.wssx.elements;
/*     */ 
/*     */ import com.sun.xml.ws.api.security.trust.Claims;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.policy.impl.bindings.PolicyReference;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.AllowPostdating;
/*     */ import com.sun.xml.ws.security.trust.elements.BinaryExchange;
/*     */ import com.sun.xml.ws.security.trust.elements.DelegateTo;
/*     */ import com.sun.xml.ws.security.trust.elements.Encryption;
/*     */ import com.sun.xml.ws.security.trust.elements.Entropy;
/*     */ import com.sun.xml.ws.security.trust.elements.Issuer;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.OnBehalfOf;
/*     */ import com.sun.xml.ws.security.trust.elements.ProofEncryption;
/*     */ import com.sun.xml.ws.security.trust.elements.Renewing;
/*     */ import com.sun.xml.ws.security.trust.elements.SecondaryParameters;
/*     */ import com.sun.xml.ws.security.trust.elements.SignChallenge;
/*     */ import com.sun.xml.ws.security.trust.elements.UseKey;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.AllowPostdatingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.BinaryExchangeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ClaimsType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.DelegateToType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.EntropyType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.LifetimeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.OnBehalfOfType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.ProofEncryptionType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.RenewingType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.SecondaryParametersType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.SignChallengeType;
/*     */ import com.sun.xml.ws.security.trust.impl.wssx.bindings.UseKeyType;
/*     */ import java.net.URI;
/*     */ import java.util.List;
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
/*     */ public class SecondaryParametersImpl
/*     */   extends SecondaryParametersType
/*     */   implements SecondaryParameters
/*     */ {
/*  79 */   private Claims claims = null;
/*     */   
/*  81 */   private URI tokenType = null;
/*     */ 
/*     */ 
/*     */   
/*  85 */   private long keySize = 0L;
/*  86 */   private URI keyType = null;
/*  87 */   private URI computedKeyAlgorithm = null;
/*     */   
/*  89 */   private URI signWith = null;
/*  90 */   private URI encryptWith = null;
/*  91 */   private URI authenticationType = null;
/*  92 */   private URI signatureAlgorithm = null;
/*  93 */   private URI encryptionAlgorithm = null;
/*  94 */   private URI canonAlgorithm = null;
/*  95 */   private URI keyWrapAlgorithm = null;
/*     */   
/*  97 */   private Lifetime lifetime = null;
/*  98 */   private Entropy entropy = null;
/*  99 */   private AppliesTo appliesTo = null;
/* 100 */   private OnBehalfOf obo = null;
/* 101 */   private SignChallenge signChallenge = null;
/* 102 */   private Encryption encryption = null;
/* 103 */   private UseKey useKey = null;
/* 104 */   private DelegateTo delegateTo = null;
/*     */ 
/*     */ 
/*     */   
/* 108 */   private AllowPostdating apd = null;
/* 109 */   private BinaryExchange binaryExchange = null;
/* 110 */   private Issuer issuer = null;
/* 111 */   private Renewing renewable = null;
/* 112 */   private ProofEncryption proofEncryption = null;
/*     */   
/*     */   private boolean forwardable = true;
/*     */   
/*     */   private boolean delegatable = false;
/* 117 */   private Policy policy = null;
/* 118 */   private PolicyReference policyRef = null;
/*     */ 
/*     */   
/*     */   public SecondaryParametersImpl() {}
/*     */ 
/*     */   
/*     */   public void setClaims(Claims claims) {
/* 125 */     this.claims = claims;
/* 126 */     JAXBElement<ClaimsType> cElement = (new ObjectFactory()).createClaims((ClaimsType)claims);
/*     */     
/* 128 */     getAny().add(cElement);
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 132 */     return this.claims;
/*     */   }
/*     */   
/*     */   public URI getTokenType() {
/* 136 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setTokenType(URI tokenType) {
/* 140 */     if (tokenType != null) {
/* 141 */       this.tokenType = tokenType;
/* 142 */       JAXBElement<String> ttElement = (new ObjectFactory()).createTokenType(tokenType.toString());
/*     */       
/* 144 */       getAny().add(ttElement);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Lifetime getLifetime() {
/* 150 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public void setLifetime(Lifetime lifetime) {
/* 154 */     this.lifetime = lifetime;
/* 155 */     JAXBElement<LifetimeType> ltElement = (new ObjectFactory()).createLifetime((LifetimeType)lifetime);
/*     */     
/* 157 */     getAny().add(ltElement);
/*     */   }
/*     */   
/*     */   public Entropy getEntropy() {
/* 161 */     return this.entropy;
/*     */   }
/*     */   
/*     */   public void setEntropy(Entropy entropy) {
/* 165 */     this.entropy = entropy;
/* 166 */     JAXBElement<EntropyType> etElement = (new ObjectFactory()).createEntropy((EntropyType)entropy);
/*     */     
/* 168 */     getAny().add(etElement);
/*     */   }
/*     */   
/*     */   public void setAppliesTo(AppliesTo appliesTo) {
/* 172 */     getAny().add(appliesTo);
/* 173 */     this.appliesTo = appliesTo;
/*     */   }
/*     */   
/*     */   public AppliesTo getAppliesTo() {
/* 177 */     return this.appliesTo;
/*     */   }
/*     */   
/*     */   public void setOnBehalfOf(OnBehalfOf onBehalfOf) {
/* 181 */     this.obo = onBehalfOf;
/*     */   }
/*     */   
/*     */   public OnBehalfOf getOnBehalfOf() {
/* 185 */     return this.obo;
/*     */   }
/*     */   
/*     */   public void setIssuer(Issuer issuer) {
/* 189 */     this.issuer = issuer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/* 196 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public void setRenewable(Renewing renew) {
/* 200 */     this.renewable = renew;
/* 201 */     JAXBElement<RenewingType> renewType = (new ObjectFactory()).createRenewing((RenewingType)renew);
/*     */     
/* 203 */     getAny().add(renewType);
/*     */   }
/*     */   
/*     */   public Renewing getRenewable() {
/* 207 */     return this.renewable;
/*     */   }
/*     */   
/*     */   public void setSignChallenge(SignChallenge challenge) {
/* 211 */     this.signChallenge = challenge;
/* 212 */     JAXBElement<SignChallengeType> challengeType = (new ObjectFactory()).createSignChallenge((SignChallengeType)challenge);
/*     */     
/* 214 */     getAny().add(challengeType);
/*     */   }
/*     */   
/*     */   public SignChallenge getSignChallenge() {
/* 218 */     return this.signChallenge;
/*     */   }
/*     */   
/*     */   public void setBinaryExchange(BinaryExchange exchange) {
/* 222 */     this.binaryExchange = exchange;
/* 223 */     JAXBElement<BinaryExchangeType> exchangeType = (new ObjectFactory()).createBinaryExchange((BinaryExchangeType)exchange);
/*     */     
/* 225 */     getAny().add(exchangeType);
/*     */   }
/*     */   
/*     */   public BinaryExchange getBinaryExchange() {
/* 229 */     return this.binaryExchange;
/*     */   }
/*     */   
/*     */   public void setAuthenticationType(URI uri) {
/* 233 */     this.authenticationType = uri;
/* 234 */     JAXBElement<String> atElement = (new ObjectFactory()).createAuthenticationType(uri.toString());
/*     */     
/* 236 */     getAny().add(atElement);
/*     */   }
/*     */   
/*     */   public URI getAuthenticationType() {
/* 240 */     return this.authenticationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyType(URI keytype) throws WSTrustException {
/* 250 */     this.keyType = keytype;
/* 251 */     JAXBElement<String> ktElement = (new ObjectFactory()).createKeyType(this.keyType.toString());
/*     */     
/* 253 */     getAny().add(ktElement);
/*     */   }
/*     */ 
/*     */   
/*     */   public URI getKeyType() {
/* 258 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public void setKeySize(long size) {
/* 262 */     this.keySize = size;
/* 263 */     JAXBElement<Long> ksElement = (new ObjectFactory()).createKeySize(Long.valueOf(size));
/* 264 */     getAny().add(ksElement);
/*     */   }
/*     */   
/*     */   public long getKeySize() {
/* 268 */     return this.keySize;
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(URI algorithm) {
/* 272 */     this.signatureAlgorithm = algorithm;
/* 273 */     JAXBElement<String> signElement = (new ObjectFactory()).createSignatureAlgorithm(algorithm.toString());
/*     */     
/* 275 */     getAny().add(signElement);
/*     */   }
/*     */   
/*     */   public URI getSignatureAlgorithm() {
/* 279 */     return this.signatureAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryptionAlgorithm(URI algorithm) {
/* 283 */     this.encryptionAlgorithm = algorithm;
/* 284 */     JAXBElement<String> encElement = (new ObjectFactory()).createEncryptionAlgorithm(algorithm.toString());
/*     */     
/* 286 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptionAlgorithm() {
/* 290 */     return this.encryptionAlgorithm;
/*     */   }
/*     */   
/*     */   public void setCanonicalizationAlgorithm(URI algorithm) {
/* 294 */     this.canonAlgorithm = algorithm;
/* 295 */     JAXBElement<String> canonElement = (new ObjectFactory()).createCanonicalizationAlgorithm(algorithm.toString());
/*     */     
/* 297 */     getAny().add(canonElement);
/*     */   }
/*     */   
/*     */   public URI getCanonicalizationAlgorithm() {
/* 301 */     return this.canonAlgorithm;
/*     */   }
/*     */   
/*     */   public void setUseKey(UseKey useKey) {
/* 305 */     this.useKey = useKey;
/* 306 */     JAXBElement<UseKeyType> ukElement = (new ObjectFactory()).createUseKey((UseKeyType)useKey);
/*     */     
/* 308 */     getAny().add(ukElement);
/*     */   }
/*     */   
/*     */   public UseKey getUseKey() {
/* 312 */     return this.useKey;
/*     */   }
/*     */   
/*     */   public void setProofEncryption(ProofEncryption proofEncryption) {
/* 316 */     this.proofEncryption = proofEncryption;
/* 317 */     JAXBElement<ProofEncryptionType> proofElement = (new ObjectFactory()).createProofEncryption((ProofEncryptionType)proofEncryption);
/*     */     
/* 319 */     getAny().add(proofElement);
/*     */   }
/*     */   
/*     */   public ProofEncryption getProofEncryption() {
/* 323 */     return this.proofEncryption;
/*     */   }
/*     */   
/*     */   public void setComputedKeyAlgorithm(URI algorithm) {
/* 327 */     if (algorithm != null) {
/* 328 */       String ckaString = algorithm.toString();
/* 329 */       if (!ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKHASHalgorithmURI()) && !ckaString.equalsIgnoreCase(WSTrustVersion.WS_TRUST_10.getCKPSHA1algorithmURI()))
/*     */       {
/* 331 */         throw new RuntimeException("Invalid Computed Key Algorithm specified");
/*     */       }
/* 333 */       this.computedKeyAlgorithm = algorithm;
/* 334 */       JAXBElement<String> ckaElement = (new ObjectFactory()).createComputedKeyAlgorithm(ckaString);
/*     */       
/* 336 */       getAny().add(ckaElement);
/*     */     } 
/*     */   }
/*     */   
/*     */   public URI getComputedKeyAlgorithm() {
/* 341 */     return this.computedKeyAlgorithm;
/*     */   }
/*     */   
/*     */   public void setEncryption(Encryption enc) {
/* 345 */     this.encryption = enc;
/* 346 */     JAXBElement<EncryptionType> encElement = (new ObjectFactory()).createEncryption((EncryptionType)enc);
/*     */     
/* 348 */     getAny().add(encElement);
/*     */   }
/*     */   
/*     */   public Encryption getEncryption() {
/* 352 */     return this.encryption;
/*     */   }
/*     */   
/*     */   public void setSignWith(URI algorithm) {
/* 356 */     this.signWith = algorithm;
/* 357 */     JAXBElement<String> sElement = (new ObjectFactory()).createSignWith(algorithm.toString());
/* 358 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getSignWith() {
/* 362 */     return this.signWith;
/*     */   }
/*     */   
/*     */   public void setEncryptWith(URI algorithm) {
/* 366 */     this.encryptWith = algorithm;
/* 367 */     JAXBElement<String> sElement = (new ObjectFactory()).createEncryptWith(algorithm.toString());
/* 368 */     getAny().add(sElement);
/*     */   }
/*     */   
/*     */   public URI getEncryptWith() {
/* 372 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public void setKeyWrapAlgorithm(URI algorithm) {
/* 376 */     this.keyWrapAlgorithm = algorithm;
/* 377 */     JAXBElement<String> keyWrapElement = (new ObjectFactory()).createKeyWrapAlgorithm(algorithm.toString());
/*     */     
/* 379 */     getAny().add(keyWrapElement);
/*     */   }
/*     */   
/*     */   public URI getKeyWrapAlgorithm() {
/* 383 */     return this.keyWrapAlgorithm;
/*     */   }
/*     */   
/*     */   public void setDelegateTo(DelegateTo to) {
/* 387 */     this.delegateTo = to;
/* 388 */     JAXBElement<DelegateToType> dtElement = (new ObjectFactory()).createDelegateTo((DelegateToType)to);
/*     */     
/* 390 */     getAny().add(dtElement);
/*     */   }
/*     */   
/*     */   public DelegateTo getDelegateTo() {
/* 394 */     return this.delegateTo;
/*     */   }
/*     */   
/*     */   public void setForwardable(boolean flag) {
/* 398 */     this.forwardable = flag;
/* 399 */     JAXBElement<Boolean> forward = (new ObjectFactory()).createForwardable(Boolean.valueOf(flag));
/*     */     
/* 401 */     getAny().add(forward);
/*     */   }
/*     */   
/*     */   public boolean getForwardable() {
/* 405 */     return this.forwardable;
/*     */   }
/*     */   
/*     */   public void setDelegatable(boolean flag) {
/* 409 */     this.delegatable = flag;
/* 410 */     JAXBElement<Boolean> del = (new ObjectFactory()).createDelegatable(Boolean.valueOf(flag));
/*     */     
/* 412 */     getAny().add(del);
/*     */   }
/*     */   
/*     */   public boolean getDelegatable() {
/* 416 */     return this.delegatable;
/*     */   }
/*     */   
/*     */   public void setPolicy(Policy policy) {
/* 420 */     this.policy = policy;
/* 421 */     getAny().add(policy);
/*     */   }
/*     */   
/*     */   public Policy getPolicy() {
/* 425 */     return this.policy;
/*     */   }
/*     */   
/*     */   public void setPolicyReference(PolicyReference policyRef) {
/* 429 */     this.policyRef = policyRef;
/* 430 */     getAny().add(policyRef);
/*     */   }
/*     */   
/*     */   public PolicyReference getPolicyReference() {
/* 434 */     return this.policyRef;
/*     */   }
/*     */   
/*     */   public AllowPostdating getAllowPostdating() {
/* 438 */     return this.apd;
/*     */   }
/*     */   
/*     */   public void setAllowPostdating(AllowPostdating allowPostdating) {
/* 442 */     this.apd = allowPostdating;
/* 443 */     JAXBElement<AllowPostdatingType> allowPd = (new ObjectFactory()).createAllowPostdating((AllowPostdatingType)this.apd);
/*     */     
/* 445 */     getAny().add(allowPd);
/*     */   }
/*     */ 
/*     */   
/*     */   public SecondaryParametersImpl(SecondaryParametersType spType) throws Exception {
/* 450 */     List<Object> list = spType.getAny();
/* 451 */     for (int i = 0; i < list.size(); i++) {
/* 452 */       if (list.get(i) instanceof AppliesTo) {
/* 453 */         setAppliesTo((AppliesTo)list.get(i));
/*     */       
/*     */       }
/* 456 */       else if (list.get(i) instanceof JAXBElement) {
/* 457 */         JAXBElement<Long> obj = (JAXBElement)list.get(i);
/*     */         
/* 459 */         String local = obj.getName().getLocalPart();
/* 460 */         if (local.equalsIgnoreCase("KeySize")) {
/* 461 */           setKeySize(((Long)obj.getValue()).longValue());
/* 462 */         } else if (local.equalsIgnoreCase("KeyType")) {
/* 463 */           setKeyType(new URI((String)obj.getValue()));
/* 464 */         } else if (local.equalsIgnoreCase("ComputedKeyAlgorithm")) {
/* 465 */           setComputedKeyAlgorithm(new URI((String)obj.getValue()));
/* 466 */         } else if (local.equalsIgnoreCase("TokenType")) {
/* 467 */           setTokenType(new URI((String)obj.getValue()));
/* 468 */         } else if (local.equalsIgnoreCase("AuthenticationType")) {
/* 469 */           setAuthenticationType(new URI((String)obj.getValue()));
/* 470 */         } else if (local.equalsIgnoreCase("Lifetime")) {
/* 471 */           LifetimeType ltType = (LifetimeType)obj.getValue();
/* 472 */           setLifetime(new LifetimeImpl(ltType.getCreated(), ltType.getExpires()));
/* 473 */         } else if (local.equalsIgnoreCase("Entropy")) {
/* 474 */           EntropyType eType = (EntropyType)obj.getValue();
/* 475 */           setEntropy(new EntropyImpl(eType));
/* 476 */         } else if (local.equalsIgnoreCase("Forwardable")) {
/* 477 */           setForwardable(((Boolean)obj.getValue()).booleanValue());
/* 478 */         } else if (local.equalsIgnoreCase("Delegatable")) {
/* 479 */           setDelegatable(((Boolean)obj.getValue()).booleanValue());
/* 480 */         } else if (local.equalsIgnoreCase("SignWith")) {
/* 481 */           setSignWith(new URI((String)obj.getValue()));
/* 482 */         } else if (local.equalsIgnoreCase("EncryptWith")) {
/* 483 */           setEncryptWith(new URI((String)obj.getValue()));
/* 484 */         } else if (local.equalsIgnoreCase("SignatureAlgorithm")) {
/* 485 */           setSignatureAlgorithm(new URI((String)obj.getValue()));
/* 486 */         } else if (local.equalsIgnoreCase("EncryptionAlgorithm")) {
/* 487 */           setEncryptionAlgorithm(new URI((String)obj.getValue()));
/* 488 */         } else if (local.equalsIgnoreCase("CanonicalizationAlgorithm")) {
/* 489 */           setCanonicalizationAlgorithm(new URI((String)obj.getValue()));
/* 490 */         } else if (local.equalsIgnoreCase("KeyWrapAlgorithm")) {
/* 491 */           setKeyWrapAlgorithm(new URI((String)obj.getValue()));
/* 492 */         } else if (local.equalsIgnoreCase("AllowPostdating")) {
/* 493 */           setAllowPostdating(new AllowPostdatingImpl());
/* 494 */         } else if (local.equalsIgnoreCase("SignChallenge")) {
/* 495 */           setSignChallenge(new SignChallengeImpl());
/* 496 */         } else if (local.equalsIgnoreCase("BinaryExchange")) {
/* 497 */           BinaryExchangeType bcType = (BinaryExchangeType)obj.getValue();
/* 498 */           setBinaryExchange(new BinaryExchangeImpl(bcType));
/* 499 */         } else if (local.equalsIgnoreCase("Claims")) {
/* 500 */           ClaimsType cType = (ClaimsType)obj.getValue();
/* 501 */           setClaims(new ClaimsImpl(cType));
/* 502 */         } else if (local.equalsIgnoreCase("Renewing")) {
/* 503 */           setRenewable(new RenewingImpl());
/* 504 */         } else if (local.equalsIgnoreCase("ProofEncryption")) {
/* 505 */           ProofEncryptionType peType = (ProofEncryptionType)obj.getValue();
/* 506 */           setProofEncryption(new ProofEncryptionImpl(peType));
/* 507 */         } else if (local.equalsIgnoreCase("Policy")) {
/* 508 */           setPolicy((Policy)obj.getValue());
/* 509 */         } else if (local.equalsIgnoreCase("PolicyReference")) {
/* 510 */           setPolicyReference((PolicyReference)obj.getValue());
/* 511 */         } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 512 */           setAppliesTo((AppliesTo)obj.getValue());
/* 513 */         } else if (local.equalsIgnoreCase("OnBehalfOf")) {
/* 514 */           OnBehalfOfType oboType = (OnBehalfOfType)obj.getValue();
/* 515 */           setOnBehalfOf(new OnBehalfOfImpl(oboType));
/* 516 */         } else if (local.equalsIgnoreCase("Encryption")) {
/* 517 */           EncryptionType encType = (EncryptionType)obj.getValue();
/* 518 */           setEncryption(new EncryptionImpl(encType));
/* 519 */         } else if (local.equalsIgnoreCase("UseKey")) {
/* 520 */           UseKeyType ukType = (UseKeyType)obj.getValue();
/* 521 */           setUseKey(new UseKeyImpl(ukType));
/* 522 */         } else if (local.equalsIgnoreCase("DelegateTo")) {
/* 523 */           DelegateToType dtType = (DelegateToType)obj.getValue();
/* 524 */           setDelegateTo(new DelegateToImpl(dtType));
/* 525 */         } else if (local.equalsIgnoreCase("AppliesTo")) {
/* 526 */           setAppliesTo((AppliesTo)obj.getValue());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\elements\SecondaryParametersImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */