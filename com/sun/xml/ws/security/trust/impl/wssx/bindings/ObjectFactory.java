/*     */ package com.sun.xml.ws.security.trust.impl.wssx.bindings;
/*     */ 
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EndpointReference;
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
/*     */ @XmlRegistry
/*     */ public class ObjectFactory
/*     */ {
/*  78 */   private static final QName _RequestSecurityTokenResponseCollection_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponseCollection");
/*  79 */   private static final QName _Lifetime_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Lifetime");
/*  80 */   private static final QName _KeyType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyType");
/*  81 */   private static final QName _DelegateTo_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "DelegateTo");
/*  82 */   private static final QName _ValidateTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ValidateTarget");
/*  83 */   private static final QName _Issuer_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Issuer");
/*  84 */   private static final QName _SignChallenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallenge");
/*  85 */   private static final QName _Forwardable_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Forwardable");
/*  86 */   private static final QName _IssuedTokens_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "IssuedTokens");
/*  87 */   private static final QName _RequestSecurityTokenCollection_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenCollection");
/*  88 */   private static final QName _SignatureAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignatureAlgorithm");
/*  89 */   private static final QName _RequestType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestType");
/*  90 */   private static final QName _RequestedProofToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedProofToken");
/*  91 */   private static final QName _KeyExchangeToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyExchangeToken");
/*  92 */   private static final QName _ComputedKey_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKey");
/*  93 */   private static final QName _RequestedSecurityToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedSecurityToken");
/*  94 */   private static final QName _KeySize_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeySize");
/*  95 */   private static final QName _Participants_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Participants");
/*  96 */   private static final QName _Claims_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Claims");
/*  97 */   private static final QName _CancelTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CancelTarget");
/*  98 */   private static final QName _EncryptionAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptionAlgorithm");
/*  99 */   private static final QName _CombinedHash_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CombinedHash");
/* 100 */   private static final QName _Challenge_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Challenge");
/* 101 */   private static final QName _Status_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Status");
/* 102 */   private static final QName _CanonicalizationAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "CanonicalizationAlgorithm");
/* 103 */   private static final QName _RenewTarget_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RenewTarget");
/* 104 */   private static final QName _RequestSecurityToken_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityToken");
/* 105 */   private static final QName _SecondaryParameters_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SecondaryParameters");
/* 106 */   private static final QName _KeyWrapAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "KeyWrapAlgorithm");
/* 107 */   private static final QName _ProofEncryption_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ProofEncryption");
/* 108 */   private static final QName _BinaryExchange_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinaryExchange");
/* 109 */   private static final QName _Delegatable_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Delegatable");
/* 110 */   private static final QName _Entropy_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Entropy");
/* 111 */   private static final QName _RequestSecurityTokenResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestSecurityTokenResponse");
/* 112 */   private static final QName _Authenticator_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Authenticator");
/* 113 */   private static final QName _RequestKET_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestKET");
/* 114 */   private static final QName _Renewing_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Renewing");
/* 115 */   private static final QName _BinarySecret_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "BinarySecret");
/* 116 */   private static final QName _RequestedAttachedReference_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedAttachedReference");
/* 117 */   private static final QName _ComputedKeyAlgorithm_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "ComputedKeyAlgorithm");
/* 118 */   private static final QName _SignWith_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignWith");
/* 119 */   private static final QName _AuthenticationType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AuthenticationType");
/* 120 */   private static final QName _Encryption_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "Encryption");
/* 121 */   private static final QName _RequestedUnattachedReference_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedUnattachedReference");
/* 122 */   private static final QName _EncryptWith_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "EncryptWith");
/* 123 */   private static final QName _SignChallengeResponse_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "SignChallengeResponse");
/* 124 */   private static final QName _AllowPostdating_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "AllowPostdating");
/* 125 */   private static final QName _OnBehalfOf_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "OnBehalfOf");
/* 126 */   private static final QName _RequestedTokenCancelled_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "RequestedTokenCancelled");
/* 127 */   private static final QName _TokenType_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "TokenType");
/* 128 */   private static final QName _UseKey_QNAME = new QName("http://docs.oasis-open.org/ws-sx/ws-trust/200512", "UseKey");
/*     */   
/* 130 */   private static final QName _EndpointReference_QNAME = new QName("http://www.w3.org/2005/08/addressing", "EndpointReference");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UseKeyType createUseKeyType() {
/* 144 */     return new UseKeyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseCollectionType createRequestSecurityTokenResponseCollectionType() {
/* 152 */     return new RequestSecurityTokenResponseCollectionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticipantType createParticipantType() {
/* 160 */     return new ParticipantType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestKETType createRequestKETType() {
/* 168 */     return new RequestKETType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DelegateToType createDelegateToType() {
/* 176 */     return new DelegateToType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenCollectionType createRequestSecurityTokenCollectionType() {
/* 184 */     return new RequestSecurityTokenCollectionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinarySecretType createBinarySecretType() {
/* 192 */     return new BinarySecretType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenewTargetType createRenewTargetType() {
/* 200 */     return new RenewTargetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CancelTargetType createCancelTargetType() {
/* 208 */     return new CancelTargetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionType createEncryptionType() {
/* 216 */     return new EncryptionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ValidateTargetType createValidateTargetType() {
/* 224 */     return new ValidateTargetType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProofEncryptionType createProofEncryptionType() {
/* 232 */     return new ProofEncryptionType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedSecurityTokenType createRequestedSecurityTokenType() {
/* 240 */     return new RequestedSecurityTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedReferenceType createRequestedReferenceType() {
/* 248 */     return new RequestedReferenceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LifetimeType createLifetimeType() {
/* 256 */     return new LifetimeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RenewingType createRenewingType() {
/* 264 */     return new RenewingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenType createRequestSecurityTokenType() {
/* 272 */     return new RequestSecurityTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecondaryParametersType createSecondaryParametersType() {
/* 280 */     return new SecondaryParametersType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyExchangeTokenType createKeyExchangeTokenType() {
/* 288 */     return new KeyExchangeTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticipantsType createParticipantsType() {
/* 296 */     return new ParticipantsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AllowPostdatingType createAllowPostdatingType() {
/* 304 */     return new AllowPostdatingType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClaimsType createClaimsType() {
/* 312 */     return new ClaimsType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthenticatorType createAuthenticatorType() {
/* 320 */     return new AuthenticatorType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedTokenCancelledType createRequestedTokenCancelledType() {
/* 328 */     return new RequestedTokenCancelledType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenResponseType createRequestSecurityTokenResponseType() {
/* 336 */     return new RequestSecurityTokenResponseType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BinaryExchangeType createBinaryExchangeType() {
/* 344 */     return new BinaryExchangeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignChallengeType createSignChallengeType() {
/* 352 */     return new SignChallengeType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnBehalfOfType createOnBehalfOfType() {
/* 360 */     return new OnBehalfOfType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatusType createStatusType() {
/* 368 */     return new StatusType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestedProofTokenType createRequestedProofTokenType() {
/* 376 */     return new RequestedProofTokenType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntropyType createEntropyType() {
/* 384 */     return new EntropyType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestSecurityTokenResponseCollection")
/*     */   public JAXBElement<RequestSecurityTokenResponseCollectionType> createRequestSecurityTokenResponseCollection(RequestSecurityTokenResponseCollectionType value) {
/* 393 */     return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_RequestSecurityTokenResponseCollection_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Lifetime")
/*     */   public JAXBElement<LifetimeType> createLifetime(LifetimeType value) {
/* 402 */     return new JAXBElement<LifetimeType>(_Lifetime_QNAME, LifetimeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "KeyType")
/*     */   public JAXBElement<String> createKeyType(String value) {
/* 411 */     return new JAXBElement<String>(_KeyType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "DelegateTo")
/*     */   public JAXBElement<DelegateToType> createDelegateTo(DelegateToType value) {
/* 420 */     return new JAXBElement<DelegateToType>(_DelegateTo_QNAME, DelegateToType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "ValidateTarget")
/*     */   public JAXBElement<ValidateTargetType> createValidateTarget(ValidateTargetType value) {
/* 429 */     return new JAXBElement<ValidateTargetType>(_ValidateTarget_QNAME, ValidateTargetType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Issuer")
/*     */   public JAXBElement createIssuer(EndpointReference value) {
/* 439 */     return new JAXBElement<EndpointReference>(_Issuer_QNAME, EndpointReference.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "SignChallenge")
/*     */   public JAXBElement<SignChallengeType> createSignChallenge(SignChallengeType value) {
/* 448 */     return new JAXBElement<SignChallengeType>(_SignChallenge_QNAME, SignChallengeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Forwardable")
/*     */   public JAXBElement<Boolean> createForwardable(Boolean value) {
/* 457 */     return new JAXBElement<Boolean>(_Forwardable_QNAME, Boolean.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "IssuedTokens")
/*     */   public JAXBElement<RequestSecurityTokenResponseCollectionType> createIssuedTokens(RequestSecurityTokenResponseCollectionType value) {
/* 466 */     return new JAXBElement<RequestSecurityTokenResponseCollectionType>(_IssuedTokens_QNAME, RequestSecurityTokenResponseCollectionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestSecurityTokenCollection")
/*     */   public JAXBElement<RequestSecurityTokenCollectionType> createRequestSecurityTokenCollection(RequestSecurityTokenCollectionType value) {
/* 475 */     return new JAXBElement<RequestSecurityTokenCollectionType>(_RequestSecurityTokenCollection_QNAME, RequestSecurityTokenCollectionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "SignatureAlgorithm")
/*     */   public JAXBElement<String> createSignatureAlgorithm(String value) {
/* 484 */     return new JAXBElement<String>(_SignatureAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestType")
/*     */   public JAXBElement<String> createRequestType(String value) {
/* 493 */     return new JAXBElement<String>(_RequestType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestedProofToken")
/*     */   public JAXBElement<RequestedProofTokenType> createRequestedProofToken(RequestedProofTokenType value) {
/* 502 */     return new JAXBElement<RequestedProofTokenType>(_RequestedProofToken_QNAME, RequestedProofTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "KeyExchangeToken")
/*     */   public JAXBElement<KeyExchangeTokenType> createKeyExchangeToken(KeyExchangeTokenType value) {
/* 511 */     return new JAXBElement<KeyExchangeTokenType>(_KeyExchangeToken_QNAME, KeyExchangeTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "ComputedKey")
/*     */   public JAXBElement<String> createComputedKey(String value) {
/* 520 */     return new JAXBElement<String>(_ComputedKey_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestedSecurityToken")
/*     */   public JAXBElement<RequestedSecurityTokenType> createRequestedSecurityToken(RequestedSecurityTokenType value) {
/* 529 */     return new JAXBElement<RequestedSecurityTokenType>(_RequestedSecurityToken_QNAME, RequestedSecurityTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "KeySize")
/*     */   public JAXBElement<Long> createKeySize(Long value) {
/* 538 */     return new JAXBElement<Long>(_KeySize_QNAME, Long.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Participants")
/*     */   public JAXBElement<ParticipantsType> createParticipants(ParticipantsType value) {
/* 547 */     return new JAXBElement<ParticipantsType>(_Participants_QNAME, ParticipantsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Claims")
/*     */   public JAXBElement<ClaimsType> createClaims(ClaimsType value) {
/* 556 */     return new JAXBElement<ClaimsType>(_Claims_QNAME, ClaimsType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "CancelTarget")
/*     */   public JAXBElement<CancelTargetType> createCancelTarget(CancelTargetType value) {
/* 565 */     return new JAXBElement<CancelTargetType>(_CancelTarget_QNAME, CancelTargetType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "EncryptionAlgorithm")
/*     */   public JAXBElement<String> createEncryptionAlgorithm(String value) {
/* 574 */     return new JAXBElement<String>(_EncryptionAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "CombinedHash")
/*     */   public JAXBElement<byte[]> createCombinedHash(byte[] value) {
/* 583 */     return (JAXBElement)new JAXBElement<byte>(_CombinedHash_QNAME, (Class)byte[].class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Challenge")
/*     */   public JAXBElement<String> createChallenge(String value) {
/* 592 */     return new JAXBElement<String>(_Challenge_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Status")
/*     */   public JAXBElement<StatusType> createStatus(StatusType value) {
/* 601 */     return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "CanonicalizationAlgorithm")
/*     */   public JAXBElement<String> createCanonicalizationAlgorithm(String value) {
/* 610 */     return new JAXBElement<String>(_CanonicalizationAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RenewTarget")
/*     */   public JAXBElement<RenewTargetType> createRenewTarget(RenewTargetType value) {
/* 619 */     return new JAXBElement<RenewTargetType>(_RenewTarget_QNAME, RenewTargetType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestSecurityToken")
/*     */   public JAXBElement<RequestSecurityTokenType> createRequestSecurityToken(RequestSecurityTokenType value) {
/* 628 */     return new JAXBElement<RequestSecurityTokenType>(_RequestSecurityToken_QNAME, RequestSecurityTokenType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "SecondaryParameters")
/*     */   public JAXBElement<SecondaryParametersType> createSecondaryParameters(SecondaryParametersType value) {
/* 637 */     return new JAXBElement<SecondaryParametersType>(_SecondaryParameters_QNAME, SecondaryParametersType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "KeyWrapAlgorithm")
/*     */   public JAXBElement<String> createKeyWrapAlgorithm(String value) {
/* 646 */     return new JAXBElement<String>(_KeyWrapAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "ProofEncryption")
/*     */   public JAXBElement<ProofEncryptionType> createProofEncryption(ProofEncryptionType value) {
/* 655 */     return new JAXBElement<ProofEncryptionType>(_ProofEncryption_QNAME, ProofEncryptionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "BinaryExchange")
/*     */   public JAXBElement<BinaryExchangeType> createBinaryExchange(BinaryExchangeType value) {
/* 664 */     return new JAXBElement<BinaryExchangeType>(_BinaryExchange_QNAME, BinaryExchangeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Delegatable")
/*     */   public JAXBElement<Boolean> createDelegatable(Boolean value) {
/* 673 */     return new JAXBElement<Boolean>(_Delegatable_QNAME, Boolean.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Entropy")
/*     */   public JAXBElement<EntropyType> createEntropy(EntropyType value) {
/* 682 */     return new JAXBElement<EntropyType>(_Entropy_QNAME, EntropyType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestSecurityTokenResponse")
/*     */   public JAXBElement<RequestSecurityTokenResponseType> createRequestSecurityTokenResponse(RequestSecurityTokenResponseType value) {
/* 691 */     return new JAXBElement<RequestSecurityTokenResponseType>(_RequestSecurityTokenResponse_QNAME, RequestSecurityTokenResponseType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Authenticator")
/*     */   public JAXBElement<AuthenticatorType> createAuthenticator(AuthenticatorType value) {
/* 700 */     return new JAXBElement<AuthenticatorType>(_Authenticator_QNAME, AuthenticatorType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestKET")
/*     */   public JAXBElement<RequestKETType> createRequestKET(RequestKETType value) {
/* 709 */     return new JAXBElement<RequestKETType>(_RequestKET_QNAME, RequestKETType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Renewing")
/*     */   public JAXBElement<RenewingType> createRenewing(RenewingType value) {
/* 718 */     return new JAXBElement<RenewingType>(_Renewing_QNAME, RenewingType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "BinarySecret")
/*     */   public JAXBElement<BinarySecretType> createBinarySecret(BinarySecretType value) {
/* 727 */     return new JAXBElement<BinarySecretType>(_BinarySecret_QNAME, BinarySecretType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestedAttachedReference")
/*     */   public JAXBElement<RequestedReferenceType> createRequestedAttachedReference(RequestedReferenceType value) {
/* 736 */     return new JAXBElement<RequestedReferenceType>(_RequestedAttachedReference_QNAME, RequestedReferenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "ComputedKeyAlgorithm")
/*     */   public JAXBElement<String> createComputedKeyAlgorithm(String value) {
/* 745 */     return new JAXBElement<String>(_ComputedKeyAlgorithm_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "SignWith")
/*     */   public JAXBElement<String> createSignWith(String value) {
/* 754 */     return new JAXBElement<String>(_SignWith_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "AuthenticationType")
/*     */   public JAXBElement<String> createAuthenticationType(String value) {
/* 763 */     return new JAXBElement<String>(_AuthenticationType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "Encryption")
/*     */   public JAXBElement<EncryptionType> createEncryption(EncryptionType value) {
/* 772 */     return new JAXBElement<EncryptionType>(_Encryption_QNAME, EncryptionType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestedUnattachedReference")
/*     */   public JAXBElement<RequestedReferenceType> createRequestedUnattachedReference(RequestedReferenceType value) {
/* 781 */     return new JAXBElement<RequestedReferenceType>(_RequestedUnattachedReference_QNAME, RequestedReferenceType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "EncryptWith")
/*     */   public JAXBElement<String> createEncryptWith(String value) {
/* 790 */     return new JAXBElement<String>(_EncryptWith_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "SignChallengeResponse")
/*     */   public JAXBElement<SignChallengeType> createSignChallengeResponse(SignChallengeType value) {
/* 799 */     return new JAXBElement<SignChallengeType>(_SignChallengeResponse_QNAME, SignChallengeType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "AllowPostdating")
/*     */   public JAXBElement<AllowPostdatingType> createAllowPostdating(AllowPostdatingType value) {
/* 808 */     return new JAXBElement<AllowPostdatingType>(_AllowPostdating_QNAME, AllowPostdatingType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "OnBehalfOf")
/*     */   public JAXBElement<OnBehalfOfType> createOnBehalfOf(OnBehalfOfType value) {
/* 817 */     return new JAXBElement<OnBehalfOfType>(_OnBehalfOf_QNAME, OnBehalfOfType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "RequestedTokenCancelled")
/*     */   public JAXBElement<RequestedTokenCancelledType> createRequestedTokenCancelled(RequestedTokenCancelledType value) {
/* 826 */     return new JAXBElement<RequestedTokenCancelledType>(_RequestedTokenCancelled_QNAME, RequestedTokenCancelledType.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "TokenType")
/*     */   public JAXBElement<String> createTokenType(String value) {
/* 835 */     return new JAXBElement<String>(_TokenType_QNAME, String.class, null, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @XmlElementDecl(namespace = "http://docs.oasis-open.org/ws-sx/ws-trust/200512", name = "UseKey")
/*     */   public JAXBElement<UseKeyType> createUseKey(UseKeyType value) {
/* 844 */     return new JAXBElement<UseKeyType>(_UseKey_QNAME, UseKeyType.class, null, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\impl\wssx\bindings\ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */