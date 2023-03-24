/*     */ package com.sun.xml.wss.impl.config;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ConfigurationConstants
/*     */ {
/*     */   public static final String CONFIGURATION_URL = "http://java.sun.com/xml/ns/xwss/config";
/*     */   public static final String DEFAULT_CONFIGURATION_PREFIX = "xwss";
/*     */   public static final String JAXRPC_SECURITY_ELEMENT_NAME = "JAXRPCSecurity";
/*     */   public static final String SECURITY_ENVIRONMENT_HANDLER_ELEMENT_NAME = "SecurityEnvironmentHandler";
/*     */   public static final String SERVICE_ELEMENT_NAME = "Service";
/*     */   public static final String PORT_ELEMENT_NAME = "Port";
/*     */   public static final String OPERATION_ELEMENT_NAME = "Operation";
/*     */   public static final String NAME_ATTRIBUTE_NAME = "name";
/*     */   public static final String OPTIMIZE_ATTRIBUTE_NAME = "optimize";
/*     */   public static final String ID_ATTRIBUTE_NAME = "id";
/*     */   public static final String CONFORMANCE_ATTRIBUTE_NAME = "conformance";
/*     */   public static final String USECACHE_ATTRIBUTE_NAME = "useCache";
/*     */   public static final String BSP_CONFORMANCE = "bsp";
/*     */   public static final String RETAIN_SEC_HEADER = "retainSecurityHeader";
/*     */   public static final String RESET_MUST_UNDERSTAND = "resetMustUnderstand";
/*     */   public static final String DECLARATIVE_CONFIGURATION_ELEMENT_NAME = "SecurityConfiguration";
/*     */   public static final String DUMP_MESSAGES_ATTRIBUTE_NAME = "dumpMessages";
/*     */   public static final String ENABLE_DYNAMIC_POLICY_ATTRIBUTE_NAME = "enableDynamicPolicy";
/*     */   public static final String ENABLE_WSS11_POLICY_ATTRIBUTE_NAME = "enableWSS11Policy";
/*     */   public static final String SIGNED_TOKEN_REQUIRED_ATTRIBUTE_NAME = "signedTokenRequired";
/*     */   public static final String OPTIONAL_TARGETS_ELEMENT_NAME = "OptionalTargets";
/*     */   public static final String SIGNATURE_REQUIREMENT_ELEMENT_NAME = "RequireSignature";
/*     */   public static final String TIMESTAMP_REQUIRED_ATTRIBUTE_NAME = "requireTimestamp";
/*     */   public static final String ENCRYPTION_REQUIREMENT_ELEMENT_NAME = "RequireEncryption";
/*     */   public static final String USERNAMETOKEN_REQUIREMENT_ELEMENT_NAME = "RequireUsernameToken";
/*     */   public static final String NONCE_REQUIRED_ATTRIBUTE_NAME = "nonceRequired";
/*     */   public static final String PASSWORD_DIGEST_REQUIRED_ATTRIBUTE_NAME = "passwordDigestRequired";
/*     */   public static final String TIMESTAMP_REQUIREMENT_ELEMENT_NAME = "RequireTimestamp";
/*     */   public static final String TIMESTAMP_ELEMENT_NAME = "Timestamp";
/*     */   public static final String TIMEOUT_ATTRIBUTE_NAME = "timeout";
/*     */   public static final String SIGN_OPERATION_ELEMENT_NAME = "Sign";
/*     */   public static final String INCLUDE_TIMESTAMP_ATTRIBUTE_NAME = "includeTimestamp";
/*     */   public static final String ENCRYPT_OPERATION_ELEMENT_NAME = "Encrypt";
/*     */   public static final String SAML_ASSERTION_ELEMENT_NAME = "SAMLAssertion";
/*     */   public static final String SAML_ASSERTION_TYPE_ATTRIBUTE_NAME = "type";
/*     */   public static final String SAML_AUTHORITY_ID_ATTRIBUTE_NAME = "authorityId";
/*     */   public static final String SAML_KEYIDENTIFIER_ATTRIBUTE_NAME = "keyIdentifier";
/*     */   public static final String SV_SAML_TYPE = "SV";
/*     */   public static final String HOK_SAML_TYPE = "HOK";
/*     */   public static final String REQUIRE_SAML_ASSERTION_ELEMENT_NAME = "RequireSAMLAssertion";
/*     */   public static final String X509TOKEN_ELEMENT_NAME = "X509Token";
/*     */   public static final String KEY_REFERENCE_TYPE_ATTRIBUTE_NAME = "keyReferenceType";
/*     */   public static final String CERTIFICATE_ALIAS_ATTRIBUTE_NAME = "certificateAlias";
/*     */   public static final String ENCODING_TYPE_ATTRIBUTE_NAME = "EncodingType";
/*     */   public static final String VALUE_TYPE_ATTRIBUTE_NAME = "ValueType";
/*     */   public static final String SYMMETRIC_KEY_ELEMENT_NAME = "SymmetricKey";
/*     */   public static final String SYMMETRIC_KEY_ALIAS_ATTRIBUTE_NAME = "keyAlias";
/*     */   public static final String TARGET_ELEMENT_NAME = "Target";
/*     */   public static final String TARGET_TYPE_ATTRIBUTE_NAME = "type";
/*     */   public static final String CONTENT_ONLY_ATTRIBUTE_NAME = "contentOnly";
/*     */   public static final String ENFORCE_ATTRIBUTE_NAME = "enforce";
/*     */   public static final String TARGET_VALUE_SOAP_BODY = "SOAP-BODY";
/*     */   public static final String URI_TARGET = "uri";
/*     */   public static final String QNAME_TARGET = "qname";
/*     */   public static final String XPATH_TARGET = "xpath";
/*     */   public static final String ENCRYPTION_TARGET_ELEMENT_NAME = "EncryptionTarget";
/*     */   public static final String SIGNATURE_TARGET_ELEMENT_NAME = "SignatureTarget";
/*     */   public static final String DIGEST_METHOD_ELEMENT_NAME = "DigestMethod";
/*     */   public static final String CANONICALIZATION_METHOD_ELEMENT_NAME = "CanonicalizationMethod";
/*     */   public static final String SIGNATURE_METHOD_ELEMENT_NAME = "SignatureMethod";
/*     */   public static final String KEY_ENCRYPTION_METHOD_ELEMENT_NAME = "KeyEncryptionMethod";
/*     */   public static final String DATA_ENCRYPTION_METHOD_ELEMENT_NAME = "DataEncryptionMethod";
/*     */   public static final String TRANSFORM_ELEMENT_NAME = "Transform";
/*     */   public static final String ALGORITHM_PARAMETER_ELEMENT_NAME = "AlgorithmParameter";
/*     */   public static final String ALGORITHM_ATTRIBUTE_NAME = "algorithm";
/*     */   public static final String VALUE_ATTRIBUTE_NAME = "value";
/*     */   public static final String DISABLE_INCLUSIVE_PREFIX = "disableInclusivePrefix";
/*     */   public static final String DIRECT_KEY_REFERENCE_TYPE = "Direct";
/*     */   public static final String IDENTIFIER_KEY_REFERENCE_TYPE = "Identifier";
/*     */   public static final String SERIAL_KEY_REFERENCE_TYPE = "IssuerSerialNumber";
/*     */   public static final String EMBEDDED_KEY_REFERENCE_TYPE = "Embedded";
/*     */   public static final String USERNAME_PASSWORD_AUTHENTICATION_ELEMENT_NAME = "UsernameToken";
/*     */   public static final String USERNAME_ATTRIBUTE_NAME = "name";
/*     */   public static final String PASSWORD_ATTRIBUTE_NAME = "password";
/*     */   public static final String USE_NONCE_ATTRIBUTE_NAME = "useNonce";
/*     */   public static final String DIGEST_PASSWORD_ATTRIBUTE_NAME = "digestPassword";
/* 196 */   public static final QName DECLARATIVE_CONFIGURATION_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SecurityConfiguration");
/*     */ 
/*     */   
/* 199 */   public static final QName SIGN_OPERATION_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Sign");
/*     */ 
/*     */   
/* 202 */   public static final QName ENCRYPT_OPERATION_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Encrypt");
/*     */ 
/*     */   
/* 205 */   public static final QName TARGET_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Target");
/*     */ 
/*     */   
/* 208 */   public static final QName TIMESTAMP_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Timestamp");
/*     */ 
/*     */   
/* 211 */   public static final QName X509TOKEN_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "X509Token");
/*     */ 
/*     */   
/* 214 */   public static final QName SYMMETRIC_KEY_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SymmetricKey");
/*     */ 
/*     */   
/* 217 */   public static final QName USERNAME_PASSWORD_AUTHENTICATION_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "UsernameToken");
/*     */ 
/*     */   
/* 220 */   public static final QName TIMESTAMP_REQUIREMENT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "RequireTimestamp");
/*     */ 
/*     */   
/* 223 */   public static final QName SIGNATURE_REQUIREMENT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "RequireSignature");
/*     */ 
/*     */   
/* 226 */   public static final QName ENCRYPTION_REQUIREMENT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "RequireEncryption");
/*     */ 
/*     */   
/* 229 */   public static final QName USERNAMETOKEN_REQUIREMENT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "RequireUsernameToken");
/*     */ 
/*     */   
/* 232 */   public static final QName OPTIONAL_TARGETS_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "OptionalTargets");
/*     */ 
/*     */   
/* 235 */   public static final QName JAXRPC_SECURITY_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "JAXRPCSecurity");
/*     */ 
/*     */   
/* 238 */   public static final QName SERVICE_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Service");
/*     */ 
/*     */   
/* 241 */   public static final QName SECURITY_ENVIRONMENT_HANDLER_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SecurityEnvironmentHandler");
/*     */ 
/*     */   
/* 244 */   public static final QName PORT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Port");
/*     */ 
/*     */   
/* 247 */   public static final QName OPERATION_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Operation");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public static final QName SAML_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SAMLAssertion");
/*     */ 
/*     */   
/* 255 */   public static final QName SAML_REQUIREMENT_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "RequireSAMLAssertion");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public static final QName ENCRYPTION_TARGET_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "EncryptionTarget");
/*     */ 
/*     */ 
/*     */   
/* 264 */   public static final QName SIGNATURE_TARGET_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SignatureTarget");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 269 */   public static final QName DIGEST_METHOD_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "DigestMethod");
/*     */ 
/*     */ 
/*     */   
/* 273 */   public static final QName CANONICALIZATION_METHOD_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "CanonicalizationMethod");
/*     */ 
/*     */ 
/*     */   
/* 277 */   public static final QName SIGNATURE_METHOD_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "SignatureMethod");
/*     */ 
/*     */ 
/*     */   
/* 281 */   public static final QName KEY_ENCRYPTION_METHOD_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "KeyEncryptionMethod");
/*     */ 
/*     */ 
/*     */   
/* 285 */   public static final QName DATA_ENCRYPTION_METHOD_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "DataEncryptionMethod");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 290 */   public static final QName TRANSFORM_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "Transform");
/*     */ 
/*     */ 
/*     */   
/* 294 */   public static final QName ALGORITHM_PARAMETER_ELEMENT_QNAME = new QName("http://java.sun.com/xml/ns/xwss/config", "AlgorithmParameter");
/*     */   public static final String DEFAULT_DATA_ENC_ALGO = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*     */   public static final String DEFAULT_KEY_ENC_ALGO = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
/*     */   public static final String MAX_NONCE_AGE = "maxNonceAge";
/*     */   public static final String MAX_CLOCK_SKEW = "maxClockSkew";
/*     */   public static final String TIMESTAMP_FRESHNESS_LIMIT = "timestampFreshnessLimit";
/*     */   public static final String STRID = "strId";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\config\ConfigurationConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */