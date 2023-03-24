/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Constants
/*     */ {
/*     */   public static final String ADDRESSING_NS = "http://schemas.xmlsoap.org/ws/2004/08/addressing";
/*     */   public static final String XPATH_NS = "http://www.w3.org/TR/1999/REC-xpath-19991116";
/*     */   public static final String TRUST_NS = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*     */   public static final String TRUST13_NS = "http://docs.oasis-open.org/ws-sx/ws-trust/200512";
/*     */   public static final String UTILITY_NS = "http://docs.oasis-open.org/wss/2004/01/oasis- 200401-wss-wssecurity-utility-1.0.xsd";
/*     */   public static final String MEX_NS = "http://schemas.xmlsoap.org/ws/2004/09/mex";
/*     */   public static final String SP13_NS = "http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802";
/*     */   public static final String _XPATHVERSION = "XPathVersion";
/*     */   public static final String InclusiveC14N = "InclusiveC14N";
/*     */   public static final String InclusiveC14NWithComments = "InclusiveC14NWithComments";
/*     */   public static final String InclusiveC14NWithCommentsForTransforms = "InclusiveC14NWithCommentsForTransforms";
/*     */   public static final String InclusiveC14NWithCommentsForCm = "InclusiveC14NWithCommentsForCm";
/*     */   public static final String ExclusiveC14NWithComments = "ExclusiveC14NWithComments";
/*     */   public static final String ExclusiveC14NWithCommentsForTransforms = "ExclusiveC14NWithCommentsForTransforms";
/*     */   public static final String ExclusiveC14NWithCommentsForCm = "ExclusiveC14NWithCommentsForCm";
/*     */   public static final String MustSupportServerChallenge = "MustSupportServerChallenge";
/*     */   public static final String Basic192Sha256Rsa15 = "Basic192Sha256Rsa15";
/*     */   public static final String STRTransform10 = "STRTransform10";
/*     */   public static final String WssX509PkiPathV1Token11 = "WssX509PkiPathV1Token11";
/*     */   public static final String WssUsernameToken11 = "WssUsernameToken11";
/*     */   public static final String Basic128 = "Basic128";
/*     */   public static final String IssuedToken = "IssuedToken";
/*     */   public static final String ProtectTokens = "ProtectTokens";
/*     */   public static final String Basic256Sha256Rsa15 = "Basic256Sha256Rsa15";
/*     */   public static final String WssGssKerberosV5ApReqToken11 = "WssGssKerberosV5ApReqToken11";
/*     */   public static final String EncryptBeforeSigning = "EncryptBeforeSigning";
/*     */   public static final String SignBeforeEncrypting = "SignBeforeEncrypting";
/*     */   public static final String WssX509V3Token10 = "WssX509V3Token10";
/*     */   public static final String SpnegoContextToken = "SpnegoContextToken";
/*     */   public static final String EncryptSignature = "EncryptSignature";
/*     */   public static final String SignedParts = "SignedParts";
/*     */   public static final String EndorsingSupportingTokens = "EndorsingSupportingTokens";
/*     */   public static final String MustSupportIssuedTokens = "MustSupportIssuedTokens";
/*     */   public static final String WssX509PkiPathV1Token10 = "WssX509PkiPathV1Token10";
/*     */   public static final String MustSupportRefEncryptedKey = "MustSupportRefEncryptedKey";
/*     */   public static final String RequiredElements = "RequiredElements";
/*     */   public static final String SOAPNormalization10 = "SOAPNormalization10";
/*     */   public static final String WssSamlV11Token11 = "WssSamlV11Token11";
/*     */   public static final String Basic128Sha256Rsa15 = "Basic128Sha256Rsa15";
/*     */   public static final String MustSupportRefKeyIdentifier = "MustSupportRefKeyIdentifier";
/*     */   public static final String RequireExternalUriReference = "RequireExternalUriReference";
/*     */   public static final String SamlToken = "SamlToken";
/*     */   public static final String RelToken = "RelToken";
/*     */   public static final String RequireInternalReference = "RequireInternalReference";
/*     */   public static final String Basic256Rsa15 = "Basic256Rsa15";
/*     */   public static final String SignatureToken = "SignatureToken";
/*     */   public static final String MustSupportClientChallenge = "MustSupportClientChallenge";
/*     */   public static final String SignedEndorsingSupportingTokens = "SignedEndorsingSupportingTokens";
/*     */   public static final String WssKerberosV5ApReqToken11 = "WssKerberosV5ApReqToken11";
/*     */   public static final String Basic192Rsa15 = "Basic192Rsa15";
/*     */   public static final String TripleDesRsa15 = "TripleDesRsa15";
/*     */   public static final String Trust10 = "Trust10";
/*     */   public static final String RequireClientEntropy = "RequireClientEntropy";
/*     */   public static final String RequireDerivedKeys = "RequireDerivedKeys";
/*     */   public static final String Strict = "Strict";
/*     */   public static final String RequireKeyIdentifierReference = "RequireKeyIdentifierReference";
/*     */   public static final String LaxTsFirst = "LaxTsFirst";
/*     */   public static final String SecureConversationToken = "SecureConversationToken";
/*     */   public static final String RequireThumbprintReference = "RequireThumbprintReference";
/*     */   public static final String XPathFilter20 = "XPathFilter20";
/*     */   public static final String HttpsToken = "HttpsToken";
/*     */   public static final String SignedElements = "SignedElements";
/*     */   public static final String WssX509Pkcs7Token10 = "WssX509Pkcs7Token10";
/*     */   public static final String Wss10 = "Wss10";
/*     */   public static final String MustSupportRefExternalURI = "MustSupportRefExternalURI";
/*     */   public static final String TransportToken = "TransportToken";
/*     */   public static final String MustSupportRefEmbeddedToken = "MustSupportRefEmbeddedToken";
/*     */   public static final String Wss11 = "Wss11";
/*     */   public static final String EncryptedElements = "EncryptedElements";
/*     */   public static final String WssSamlV11Token10 = "WssSamlV11Token10";
/*     */   public static final String TripleDesSha256 = "TripleDesSha256";
/*     */   public static final String WssRelV10Token11 = "WssRelV10Token11";
/*     */   public static final String SignedSupportingTokens = "SignedSupportingTokens";
/*     */   public static final String SecurityContextToken = "SecurityContextToken";
/*     */   public static final String Basic256Sha256 = "Basic256Sha256";
/*     */   public static final String UsernameToken = "UsernameToken";
/*     */   public static final String OnlySignEntireHeadersAndBody = "OnlySignEntireHeadersAndBody";
/*     */   public static final String InitiatorToken = "InitiatorToken";
/*     */   public static final String InitiatorSignatureToken = "InitiatorSignatureToken";
/*     */   public static final String InitiatorEncryptionToken = "InitiatorEncryptionToken";
/*     */   public static final String WssSamlV20Token11 = "WssSamlV20Token11";
/*     */   public static final String WssSamlV10Token11 = "WssSamlV10Token11";
/*     */   public static final String Basic256 = "Basic256";
/*     */   public static final String WssRelV10Token10 = "WssRelV10Token10";
/*     */   public static final String ProtectionToken = "ProtectionToken";
/*     */   public static final String BootstrapPolicy = "BootstrapPolicy";
/*     */   public static final String SC10SecurityContextToken = "SC10SecurityContextToken";
/*     */   public static final String KerberosToken = "KerberosToken";
/*     */   public static final String WssRelV20Token10 = "WssRelV20Token10";
/*     */   public static final String LaxTsLast = "LaxTsLast";
/*     */   public static final String RequireServerEntropy = "RequireServerEntropy";
/*     */   public static final String RequireExternalReference = "RequireExternalReference";
/*     */   public static final String RequireSignatureConfirmation = "RequireSignatureConfirmation";
/*     */   public static final String Basic128Rsa15 = "Basic128Rsa15";
/*     */   public static final String AsymmetricBinding = "AsymmetricBinding";
/*     */   public static final String IncludeTimestamp = "IncludeTimestamp";
/*     */   public static final String DisableTimestampSigning = "DisableTimestampSigning";
/*     */   public static final String RequireEmbeddedTokenReference = "RequireEmbeddedTokenReference";
/*     */   public static final String MustSupportRefThumbprint = "MustSupportRefThumbprint";
/*     */   public static final String Basic192 = "Basic192";
/*     */   public static final String WssX509Pkcs7Token11 = "WssX509Pkcs7Token11";
/*     */   public static final String WssSamlV10Token10 = "WssSamlV10Token10";
/*     */   public static final String Basic128Sha256 = "Basic128Sha256";
/*     */   public static final String TripleDesSha256Rsa15 = "TripleDesSha256Rsa15";
/*     */   public static final String WssUsernameToken10 = "WssUsernameToken10";
/*     */   public static final String SymmetricBinding = "SymmetricBinding";
/*     */   public static final String TripleDes = "TripleDes";
/*     */   public static final String MustSupportRefIssuerSerial = "MustSupportRefIssuerSerial";
/*     */   public static final String EncryptedParts = "EncryptedParts";
/*     */   public static final String Basic192Sha256 = "Basic192Sha256";
/*     */   public static final String AlgorithmSuite = "AlgorithmSuite";
/*     */   public static final String WssRelV20Token11 = "WssRelV20Token11";
/*     */   public static final String TransportBinding = "TransportBinding";
/*     */   public static final String SupportingTokens = "SupportingTokens";
/*     */   public static final String X509Token = "X509Token";
/*     */   public static final String WssX509V1Token10 = "WssX509V1Token10";
/*     */   public static final String WssX509V1Token11 = "WssX509V1Token11";
/*     */   public static final String WssX509V3Token11 = "WssX509V3Token11";
/*     */   public static final String RecipientToken = "RecipientToken";
/*     */   public static final String RecipientSignatureToken = "RecipientSignatureToken";
/*     */   public static final String RecipientEncryptionToken = "RecipientEncryptionToken";
/*     */   public static final String EncryptionToken = "EncryptionToken";
/*     */   public static final String Lax = "Lax";
/*     */   public static final String Layout = "Layout";
/*     */   public static final String RequireIssuerSerialReference = "RequireIssuerSerialReference";
/*     */   public static final String RsaToken = "RsaToken";
/*     */   public static final String KeyValueToken = "KeyValueToken";
/*     */   public static final String RsaKeyValue = "RsaKeyValue";
/*     */   public static final String HttpBasicAuthentication = "HttpBasicAuthentication";
/*     */   public static final String HttpDigestAuthentication = "HttpDigestAuthentication";
/*     */   public static final String Trust13 = "Trust13";
/*     */   public static final String RequireExplicitDerivedKeys = "RequireExplicitDerivedKeys";
/*     */   public static final String RequireImpliedDerivedKeys = "RequireImpliedDerivedKeys";
/*     */   public static final String SignedEncryptedSupportingTokens = "SignedEncryptedSupportingTokens";
/*     */   public static final String EncryptedSupportingTokens = "EncryptedSupportingTokens";
/*     */   public static final String EndorsingEncryptedSupportingTokens = "EndorsingEncryptedSupportingTokens";
/*     */   public static final String SignedEndorsingEncryptedSupportingTokens = "SignedEndorsingEncryptedSupportingTokens";
/*     */   public static final String RequireRequestSecurityTokenCollection = "RequireRequestSecurityTokenCollection";
/*     */   public static final String RequireAppliesTo = "RequireAppliesTo";
/*     */   public static final String MustNotSendCancel = "MustNotSendCancel";
/*     */   public static final String MustNotSendRenew = "MustNotSendRenew";
/*     */   public static final String Attachments = "Attachments";
/*     */   public static final String ContentSignatureTransform = "ContentSignatureTransform";
/*     */   public static final String AttachmentCompleteSignatureTransform = "AttachmentCompleteSignatureTransform";
/*     */   public static final String Body = "Body";
/*     */   public static final String HEADER = "Header";
/*     */   public static final String RequestSecurityTokenTemplate = "RequestSecurityTokenTemplate";
/*     */   public static final String EndpointReference = "EndpointReference";
/*     */   public static final String IncludeToken = "IncludeToken";
/*     */   public static final String XPath = "XPath";
/*     */   public static final String RequireClientCertificate = "RequireClientCertificate";
/*     */   public static final String Claims = "Claims";
/*     */   public static final String Entropy = "Entropy";
/*     */   public static final String KeyType = "KeyType";
/*     */   public static final String KeySize = "KeySize";
/*     */   public static final String UseKey = "UseKey";
/*     */   public static final String Encryption = "Encryption";
/*     */   public static final String ProofEncryption = "ProofEncryption";
/*     */   public static final String Lifetime = "Lifetime";
/*     */   public static final String Issuer = "Issuer";
/*     */   public static final String IssuerName = "IssuerName";
/*     */   public static final String Address = "Address";
/*     */   public static final String IDENTITY = "Identity";
/*     */   public static final String Metadata = "Metadata";
/*     */   public static final String MetadataSection = "MetadataSection";
/*     */   public static final String MetadataReference = "MetadataReference";
/*     */   public static final String Created = "Created";
/*     */   public static final String Nonce = "Nonce";
/*     */   public static final String Expires = "Expires";
/*     */   public static final String SignWith = "SignWith";
/*     */   public static final String EncryptWith = "EncryptWith";
/*     */   public static final String TokenType = "TokenType";
/*     */   public static final String RequestType = "RequestType";
/*     */   public static final String RequestSecurityToken = "RequestSecurityToken";
/*     */   public static final String OnBehalfOf = "OnBehalfOf";
/*     */   public static final String AuthenticationType = "AuthenticationType";
/*     */   public static final String CanonicalizationAlgorithm = "CanonicalizationAlgorithm";
/*     */   public static final String SignatureAlgorithm = "SignatureAlgorithm";
/*     */   public static final String EncryptionAlgorithm = "EncryptionAlgorithm";
/*     */   public static final String ComputedKeyAlgorithm = "ComputedKeyAlgorithm";
/*     */   public static final String KeyWrapAlgorithm = "KeyWrapAlgorithm";
/*     */   public static final String WS_SECURITY_POLICY_DOMAIN = "javax.enterprise.resource.xml.webservices.security.policy";
/*     */   public static final String WS_SECURITY_POLICY_PACKAGE_ROOT = "com.sun.xml.ws.security.impl.policy";
/*     */   public static final String WS_SECURITY_POLICY_DOMAIN_BUNDLE = "com.sun.xml.ws.security.impl.policy.LogStrings";
/* 249 */   public static final Logger logger = Logger.getLogger("javax.enterprise.resource.xml.webservices.security.policy", "com.sun.xml.ws.security.impl.policy.LogStrings");
/*     */   
/*     */   public static final String SUN_WSS_SECURITY_CLIENT_POLICY_NS = "http://schemas.sun.com/2006/03/wss/client";
/*     */   
/*     */   public static final String SUN_WSS_SECURITY_SERVER_POLICY_NS = "http://schemas.sun.com/2006/03/wss/server";
/*     */   
/*     */   public static final String SUN_TRUST_CLIENT_SECURITY_POLICY_NS = "http://schemas.sun.com/ws/2006/05/trust/client";
/*     */   public static final String SUN_TRUST_SERVER_SECURITY_POLICY_NS = "http://schemas.sun.com/ws/2006/05/trust/server";
/*     */   public static final String SUN_SECURE_CLIENT_CONVERSATION_POLICY_NS = "http://schemas.sun.com/ws/2006/05/sc/client";
/*     */   public static final String SUN_SECURE_SERVER_CONVERSATION_POLICY_NS = "http://schemas.sun.com/ws/2006/05/sc/server";
/*     */   public static final String MS_SP_NS = "http://schemas.microsoft.com/ws/2005/07/securitypolicy";
/*     */   public static final String KerberosConfig = "KerberosConfig";
/*     */   public static final String KeyStore = "KeyStore";
/*     */   public static final String SessionManagerStore = "SessionManagerStore";
/*     */   public static final String TrustStore = "TrustStore";
/*     */   public static final String CallbackHandler = "CallbackHandler";
/*     */   public static final String CallbackHandlerConfiguration = "CallbackHandlerConfiguration";
/*     */   public static final String Validator = "Validator";
/*     */   public static final String ValidatorConfiguration = "ValidatorConfiguration";
/*     */   public static final String ReferenceParameters = "ReferenceParameters";
/*     */   public static final String ReferenceProperties = "ReferenceProperties";
/*     */   public static final String PortType = "PortType";
/*     */   public static final String ServiceName = "ServiceName";
/*     */   public static final String CertStore = "CertStore";
/*     */   public static final String NoPassword = "NoPassword";
/*     */   public static final String HashPassword = "HashPassword";
/*     */   public static final String BSP10 = "BSP10";
/*     */   public static final String SECURITY_POLICY_PACKAGE_DIR = "com.sun.xml.ws.security.impl.policy";
/*     */   
/*     */   public static void log_invalid_assertion(PolicyAssertion assertion, boolean isServer, String parentAssertion) {
/* 279 */     Level level = Level.SEVERE;
/* 280 */     if (!isServer) {
/* 281 */       level = Level.WARNING;
/*     */     }
/* 283 */     if (logger.isLoggable(level))
/* 284 */       logger.log(level, LogStringsMessages.SP_0100_INVALID_SECURITY_ASSERTION(assertion, parentAssertion)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Constants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */