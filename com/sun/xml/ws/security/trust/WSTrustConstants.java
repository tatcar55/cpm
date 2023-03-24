/*     */ package com.sun.xml.ws.security.trust;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSTrustConstants
/*     */ {
/*     */   public static final String SAML_CONFIRMATION_METHOD = "Saml-Confirmation-Method";
/*     */   public static final String USE_KEY_RSA_KEY_PAIR = "UseKey-RSAKeyPair";
/*     */   public static final String USE_KEY_SIGNATURE_ID = "UseKey-SignatureID";
/*     */   public static final String STS_CALL_BACK_HANDLER = "stsCallbackHandler";
/*     */   public static final String SAML_ASSERTION_ELEMENT_IN_RST = "SamlAssertionElementInRST";
/*     */   public static final String WST_VERSION = "WSTrustVersion";
/*     */   public static final String AUTHN_CONTEXT_CLASS = "AuthnContextClass";
/*     */   public static final String SECURITY_ENVIRONMENT = "SecurityEnvironment";
/*     */   public static final String SAML10_ASSERTION_TOKEN_TYPE = "urn:oasis:names:tc:SAML:1.0:assertion";
/*     */   public static final String SAML11_ASSERTION_TOKEN_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1";
/*     */   public static final String SAML20_ASSERTION_TOKEN_TYPE = "urn:oasis:names:tc:SAML:2.0:assertion";
/*     */   public static final String SAML20_WSS_TOKEN_TYPE = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0";
/*     */   public static final String OPAQUE_TYPE = "opaque";
/*     */   public static final String SAML11_TYPE = "urn:oasis:names:tc:SAML:1.1:assertion";
/*     */   public static final String WST_NAMESPACE = "http://schemas.xmlsoap.org/ws/2005/02/trust";
/*     */   public static final String WST_PREFIX = "wst";
/*     */   public static final String ISSUE_REQUEST = "http://schemas.xmlsoap.org/ws/2005/02/trust/Issue";
/*     */   public static final String RENEW_REQUEST = "http://schemas.xmlsoap.org/ws/2005/02/trust/Renew";
/*     */   public static final String CANCEL_REQUEST = "http://schemas.xmlsoap.org/ws/2005/02/trust/Cancel";
/*     */   public static final String VALIDATE_REQUEST = "http://schemas.xmlsoap.org/ws/2005/02/trust/Validate";
/*     */   public static final String KEY_EXCHANGE_REQUEST = "http://schemas.xmlsoap.org/ws/2005/02/trust/KET";
/*     */   public static final String PUBLIC_KEY = "http://schemas.xmlsoap.org/ws/2005/02/trust/PublicKey";
/*     */   public static final String SYMMETRIC_KEY = "http://schemas.xmlsoap.org/ws/2005/02/trust/SymmetricKey";
/*     */   public static final String NO_PROOF_KEY = "http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey";
/*     */   public static final String STR_TYPE = "SecurityTokenReference";
/*     */   public static final String TOKEN_TYPE = "Token";
/*     */   public static final String REQUEST_SECURITY_TOKEN_ISSUE_ACTION = "http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue";
/*     */   public static final String REQUEST_SECURITY_TOKEN_RESPONSE_ISSUE_ACTION = "http://schemas.xmlsoap.org/ws/2005/02/trust/RSTR/Issue";
/*     */   public static final String CK_PSHA1 = "http://schemas.xmlsoap.org/ws/2005/02/trust/CK/PSHA1";
/*     */   public static final String CK_HASH = "http://schemas.xmlsoap.org/ws/2005/02/trust/CK/HASH";
/*     */   public static final String DEFAULT_APPLIESTO = "default";
/*     */   public static final String PROPERTY_URL = "WSTRUST_PROPERTY_URL";
/*     */   public static final String PROPERTY_PORT_NAME = "WSTRUST_PROPERTY_PORT_NAME";
/*     */   public static final String PROPERTY_SERVICE_NAME = "WSTRUST_PROPERTY_SERVICE_NAME";
/*     */   public static final String PROPERTY_SERVICE_END_POINT = "STS_END_POINT";
/*     */   public static final String IS_TRUST_MESSAGE = "isTrustMessage";
/*     */   public static final String TRUST_ACTION = "trustAction";
/*     */   
/*     */   public enum STS_PROPERTIES
/*     */   {
/* 140 */     PROPERTY_URL, PROPERTY_PORT_NAME, PROPERTY_SERVICE_NAME, PROPERTY_SERVICE_END_POINT;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\WSTrustConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */