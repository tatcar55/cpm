/*     */ package com.sun.xml.ws.security.trust.impl.bindings;
/*     */ 
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.annotation.XmlElementDecl;
/*     */ import javax.xml.bind.annotation.XmlRegistry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  94 */   private static final QName _Issuer_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Issuer");
/*  95 */   private static final QName _Claims_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Claims");
/*  96 */   private static final QName _SignWith_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignWith");
/*  97 */   private static final QName _CanonicalizationAlgorithm_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "CanonicalizationAlgorithm");
/*  98 */   private static final QName _Participants_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Participants");
/*  99 */   private static final QName _IssuedTokens_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "IssuedTokens");
/* 100 */   private static final QName _Lifetime_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Lifetime");
/* 101 */   private static final QName _KeyType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeyType");
/* 102 */   private static final QName _SignChallenge_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignChallenge");
/* 103 */   private static final QName _DelegateTo_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "DelegateTo");
/* 104 */   private static final QName _Renewing_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Renewing");
/* 105 */   private static final QName _RenewTarget_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RenewTarget");
/* 106 */   private static final QName _UseKey_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "UseKey");
/* 107 */   private static final QName _AllowPostdating_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "AllowPostdating");
/* 108 */   private static final QName _Authenticator_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Authenticator");
/* 109 */   private static final QName _TokenType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "TokenType");
/* 110 */   private static final QName _Challenge_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Challenge");
/* 111 */   private static final QName _RequestedAttachedReference_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedAttachedReference");
/* 112 */   private static final QName _AuthenticationType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "AuthenticationType");
/* 113 */   private static final QName _Forwardable_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Forwardable");
/* 114 */   private static final QName _KeySize_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeySize");
/* 115 */   private static final QName _SignChallengeResponse_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignChallengeResponse");
/* 116 */   private static final QName _RequestSecurityTokenResponseCollection_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponseCollection");
/* 117 */   private static final QName _Encryption_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Encryption");
/* 118 */   private static final QName _ProofEncryption_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ProofEncryption");
/* 119 */   private static final QName _RequestedTokenCancelled_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedTokenCancelled");
/* 120 */   private static final QName _EncryptionAlgorithm_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptionAlgorithm");
/* 121 */   private static final QName _RequestedSecurityToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedSecurityToken");
/* 122 */   private static final QName _Entropy_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Entropy");
/* 123 */   private static final QName _RequestedProofToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedProofToken");
/* 124 */   private static final QName _RequestSecurityTokenResponse_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityTokenResponse");
/* 125 */   private static final QName _RequestType_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestType");
/* 126 */   private static final QName _ComputedKeyAlgorithm_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ComputedKeyAlgorithm");
/* 127 */   private static final QName _OnBehalfOf_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "OnBehalfOf");
/* 128 */   private static final QName _CombinedHash_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "CombinedHash");
/* 129 */   private static final QName _KeyExchangeToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "KeyExchangeToken");
/* 130 */   private static final QName _EncryptWith_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "EncryptWith");
/* 131 */   private static final QName _RequestSecurityToken_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestSecurityToken");
/* 132 */   private static final QName _BinarySecret_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "BinarySecret");
/* 133 */   private static final QName _Status_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Status");
/* 134 */   private static final QName _ComputedKey_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "ComputedKey");
/* 135 */   private static final QName _BinaryExchange_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "BinaryExchange");
/* 136 */   private static final QName _RequestedUnattachedReference_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestedUnattachedReference");
/* 137 */   private static final QName _RequestKET_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "RequestKET");
/* 138 */   private static final QName _CancelTarget_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "CancelTarget");
/* 139 */   private static final QName _Delegatable_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "Delegatable");
/* 140 */   private static final QName _SignatureAlgorithm_QNAME = new QName("http://schemas.xmlsoap.org/ws/2005/02/trust", "SignatureAlgorithm");
/* 141 */   private static final QName _EndpointReference_QNAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProofEncryptionType createProofEncryptionType() {
/* 155 */     return new ProofEncryptionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionType createRequestSecurityTokenResponseCollectionType() {
/* 163 */     return new RequestSecurityTokenResponseCollectionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseType createRequestSecurityTokenResponseType() {
/* 171 */     return new RequestSecurityTokenResponseType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnBehalfOfType createOnBehalfOfType() {
/* 179 */     return new OnBehalfOfType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticipantType createParticipantType() {
/* 187 */     return new ParticipantType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryExchangeType createBinaryExchangeType() {
/* 195 */     return new BinaryExchangeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyExchangeTokenType createKeyExchangeTokenType() {
/* 203 */     return new KeyExchangeTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CancelTargetType createCancelTargetType() {
/* 211 */     return new CancelTargetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenType createRequestedSecurityTokenType() {
/* 219 */     return new RequestedSecurityTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedReferenceType createRequestedReferenceType() {
/* 227 */     return new RequestedReferenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignChallengeType createSignChallengeType() {
/* 235 */     return new SignChallengeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LifetimeType createLifetimeType() {
/* 243 */     return new LifetimeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedProofTokenType createRequestedProofTokenType() {
/* 251 */     return new RequestedProofTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenewingType createRenewingType() {
/* 259 */     return new RenewingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenewTargetType createRenewTargetType() {
/* 267 */     return new RenewTargetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClaimsType createClaimsType() {
/* 275 */     return new ClaimsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusType createStatusType() {
/* 283 */     return new StatusType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntropyType createEntropyType() {
/* 291 */     return new EntropyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DelegateToType createDelegateToType() {
/* 299 */     return new DelegateToType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecretType createBinarySecretType() {
/* 307 */     return new BinarySecretType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedTokenCancelledType createRequestedTokenCancelledType() {
/* 315 */     return new RequestedTokenCancelledType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionType createEncryptionType() {
/* 323 */     return new EncryptionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticatorType createAuthenticatorType() {
/* 331 */     return new AuthenticatorType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestKETType createRequestKETType() {
/* 339 */     return new RequestKETType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenType createRequestSecurityTokenType() {
/* 347 */     return new RequestSecurityTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticipantsType createParticipantsType() {
/* 355 */     return new ParticipantsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UseKeyType createUseKeyType() {
/* 363 */     return new UseKeyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AllowPostdatingType createAllowPostdatingType() {
/* 371 */     return new AllowPostdatingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Issuer")
/*     */   public JAXBElement createIssuer(EndpointReference value) {
/* 382 */     return new JAXBElement<EndpointReference>(_Issuer_QNAME, EndpointReference.class, null, value);
/*     */   }
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://www.w3.org/2005/08/addressing", name = "EndpointReference")
/*     */   public JAXBElement createEndpointReference(EndpointReference value) {
/* 388 */     return new JAXBElement<EndpointReference>(_EndpointReference_QNAME, EndpointReference.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Claims")
/*     */   public JAXBElement<ClaimsType> createClaims(ClaimsType value) {
/* 399 */     return new JAXBElement<ClaimsType>(_Claims_QNAME, ClaimsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "SignWith")
/*     */   public JAXBElement<String> createSignWith(String value) {
/* 408 */     return new JAXBElement<String>(_SignWith_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "CanonicalizationAlgorithm")
/*     */   public JAXBElement<String> createCanonicalizationAlgorithm(String value) {
/* 417 */     return new JAXBElement<String>(_CanonicalizationAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Participants")
/*     */   public JAXBElement<ParticipantsType> createParticipants(ParticipantsType value) {
/* 426 */     return new JAXBElement<ParticipantsType>(_Participants_QNAME, ParticipantsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "IssuedTokens")
/*     */   public JAXBElement<RequestSecurityTokenResponseCollectionType> createIssuedTokens(RequestSecurityTokenResponseCollectionType value) {
/* 436 */     return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_IssuedTokens_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Lifetime")
/*     */   public JAXBElement<LifetimeType> createLifetime(LifetimeType value) {
/* 445 */     return new JAXBElement<LifetimeType>(_Lifetime_QNAME, LifetimeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "KeyType")
/*     */   public JAXBElement<String> createKeyType(String value) {
/* 454 */     return new JAXBElement<String>(_KeyType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "SignChallenge")
/*     */   public JAXBElement<SignChallengeType> createSignChallenge(SignChallengeType value) {
/* 463 */     return new JAXBElement<SignChallengeType>(_SignChallenge_QNAME, SignChallengeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "DelegateTo")
/*     */   public JAXBElement<DelegateToType> createDelegateTo(DelegateToType value) {
/* 472 */     return new JAXBElement<DelegateToType>(_DelegateTo_QNAME, DelegateToType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Renewing")
/*     */   public JAXBElement<RenewingType> createRenewing(RenewingType value) {
/* 481 */     return new JAXBElement<RenewingType>(_Renewing_QNAME, RenewingType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RenewTarget")
/*     */   public JAXBElement<RenewTargetType> createRenewTarget(RenewTargetType value) {
/* 490 */     return new JAXBElement<RenewTargetType>(_RenewTarget_QNAME, RenewTargetType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "UseKey")
/*     */   public JAXBElement<UseKeyType> createUseKey(UseKeyType value) {
/* 499 */     return new JAXBElement<UseKeyType>(_UseKey_QNAME, UseKeyType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "AllowPostdating")
/*     */   public JAXBElement<AllowPostdatingType> createAllowPostdating(AllowPostdatingType value) {
/* 508 */     return new JAXBElement<AllowPostdatingType>(_AllowPostdating_QNAME, AllowPostdatingType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Authenticator")
/*     */   public JAXBElement<AuthenticatorType> createAuthenticator(AuthenticatorType value) {
/* 517 */     return new JAXBElement<AuthenticatorType>(_Authenticator_QNAME, AuthenticatorType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "TokenType")
/*     */   public JAXBElement<String> createTokenType(String value) {
/* 526 */     return new JAXBElement<String>(_TokenType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Challenge")
/*     */   public JAXBElement<String> createChallenge(String value) {
/* 535 */     return new JAXBElement<String>(_Challenge_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestedAttachedReference")
/*     */   public JAXBElement<RequestedReferenceType> createRequestedAttachedReference(RequestedReferenceType value) {
/* 544 */     return new JAXBElement<RequestedReferenceType>(_RequestedAttachedReference_QNAME, RequestedReferenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "AuthenticationType")
/*     */   public JAXBElement<String> createAuthenticationType(String value) {
/* 553 */     return new JAXBElement<String>(_AuthenticationType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Forwardable")
/*     */   public JAXBElement<Boolean> createForwardable(Boolean value) {
/* 562 */     return new JAXBElement<Boolean>(_Forwardable_QNAME, Boolean.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "KeySize")
/*     */   public JAXBElement<Long> createKeySize(Long value) {
/* 571 */     return new JAXBElement<Long>(_KeySize_QNAME, Long.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "SignChallengeResponse")
/*     */   public JAXBElement<SignChallengeType> createSignChallengeResponse(SignChallengeType value) {
/* 580 */     return new JAXBElement<SignChallengeType>(_SignChallengeResponse_QNAME, SignChallengeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestSecurityTokenResponseCollection")
/*     */   public JAXBElement<RequestSecurityTokenResponseCollectionType> createRequestSecurityTokenResponseCollection(RequestSecurityTokenResponseCollectionType value) {
/* 589 */     return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_RequestSecurityTokenResponseCollection_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Encryption")
/*     */   public JAXBElement<EncryptionType> createEncryption(EncryptionType value) {
/* 598 */     return new JAXBElement<EncryptionType>(_Encryption_QNAME, EncryptionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "ProofEncryption")
/*     */   public JAXBElement<ProofEncryptionType> createProofEncryption(ProofEncryptionType value) {
/* 607 */     return new JAXBElement<ProofEncryptionType>(_ProofEncryption_QNAME, ProofEncryptionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestedTokenCancelled")
/*     */   public JAXBElement<RequestedTokenCancelledType> createRequestedTokenCancelled(RequestedTokenCancelledType value) {
/* 616 */     return new JAXBElement<RequestedTokenCancelledType>(_RequestedTokenCancelled_QNAME, RequestedTokenCancelledType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "EncryptionAlgorithm")
/*     */   public JAXBElement<String> createEncryptionAlgorithm(String value) {
/* 625 */     return new JAXBElement<String>(_EncryptionAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestedSecurityToken")
/*     */   public JAXBElement<RequestedSecurityTokenType> createRequestedSecurityToken(RequestedSecurityTokenType value) {
/* 634 */     return new JAXBElement<RequestedSecurityTokenType>(_RequestedSecurityToken_QNAME, RequestedSecurityTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Entropy")
/*     */   public JAXBElement<EntropyType> createEntropy(EntropyType value) {
/* 643 */     return new JAXBElement<EntropyType>(_Entropy_QNAME, EntropyType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestedProofToken")
/*     */   public JAXBElement<RequestedProofTokenType> createRequestedProofToken(RequestedProofTokenType value) {
/* 652 */     return new JAXBElement<RequestedProofTokenType>(_RequestedProofToken_QNAME, RequestedProofTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestSecurityTokenResponse")
/*     */   public JAXBElement<RequestSecurityTokenResponseType> createRequestSecurityTokenResponse(RequestSecurityTokenResponseType value) {
/* 661 */     return new JAXBElement<RequestSecurityTokenResponseType>(_RequestSecurityTokenResponse_QNAME, RequestSecurityTokenResponseType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestType")
/*     */   public JAXBElement<String> createRequestType(String value) {
/* 670 */     return new JAXBElement<String>(_RequestType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "ComputedKeyAlgorithm")
/*     */   public JAXBElement<String> createComputedKeyAlgorithm(String value) {
/* 679 */     return new JAXBElement<String>(_ComputedKeyAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "OnBehalfOf")
/*     */   public JAXBElement<OnBehalfOfType> createOnBehalfOf(OnBehalfOfType value) {
/* 688 */     return new JAXBElement<OnBehalfOfType>(_OnBehalfOf_QNAME, OnBehalfOfType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "CombinedHash")
/*     */   public JAXBElement<byte[]> createCombinedHash(byte[] value) {
/* 697 */     return (JAXBElement)new JAXBElement<byte>(_CombinedHash_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "KeyExchangeToken")
/*     */   public JAXBElement<KeyExchangeTokenType> createKeyExchangeToken(KeyExchangeTokenType value) {
/* 706 */     return new JAXBElement<KeyExchangeTokenType>(_KeyExchangeToken_QNAME, KeyExchangeTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "EncryptWith")
/*     */   public JAXBElement<String> createEncryptWith(String value) {
/* 715 */     return new JAXBElement<String>(_EncryptWith_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestSecurityToken")
/*     */   public JAXBElement<RequestSecurityTokenType> createRequestSecurityToken(RequestSecurityTokenType value) {
/* 724 */     return new JAXBElement<RequestSecurityTokenType>(_RequestSecurityToken_QNAME, RequestSecurityTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "BinarySecret")
/*     */   public JAXBElement<BinarySecretType> createBinarySecret(BinarySecretType value) {
/* 733 */     return new JAXBElement<BinarySecretType>(_BinarySecret_QNAME, BinarySecretType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Status")
/*     */   public JAXBElement<StatusType> createStatus(StatusType value) {
/* 742 */     return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "ComputedKey")
/*     */   public JAXBElement<String> createComputedKey(String value) {
/* 751 */     return new JAXBElement<String>(_ComputedKey_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "BinaryExchange")
/*     */   public JAXBElement<BinaryExchangeType> createBinaryExchange(BinaryExchangeType value) {
/* 760 */     return new JAXBElement<BinaryExchangeType>(_BinaryExchange_QNAME, BinaryExchangeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestedUnattachedReference")
/*     */   public JAXBElement<RequestedReferenceType> createRequestedUnattachedReference(RequestedReferenceType value) {
/* 769 */     return new JAXBElement<RequestedReferenceType>(_RequestedUnattachedReference_QNAME, RequestedReferenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "RequestKET")
/*     */   public JAXBElement<RequestKETType> createRequestKET(RequestKETType value) {
/* 778 */     return new JAXBElement<RequestKETType>(_RequestKET_QNAME, RequestKETType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "CancelTarget")
/*     */   public JAXBElement<CancelTargetType> createCancelTarget(CancelTargetType value) {
/* 787 */     return new JAXBElement<CancelTargetType>(_CancelTarget_QNAME, CancelTargetType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "Delegatable")
/*     */   public JAXBElement<Boolean> createDelegatable(Boolean value) {
/* 796 */     return new JAXBElement<Boolean>(_Delegatable_QNAME, Boolean.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://schemas.xmlsoap.org/ws/2005/02/trust", name = "SignatureAlgorithm")
/*     */   public JAXBElement<String> createSignatureAlgorithm(String value) {
/* 805 */     return new JAXBElement<String>(_SignatureAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\bindings\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */