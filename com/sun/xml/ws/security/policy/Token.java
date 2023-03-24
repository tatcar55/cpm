package com.sun.xml.ws.security.policy;

public interface Token {
  public static final String WSS11 = "WSS11";
  
  public static final String WSS10 = "WSS10";
  
  public static final String REQUIRE_KEY_IDENTIFIER_REFERENCE = "RequireKeyIdentifierReference";
  
  public static final String REQUIRE_ISSUER_SERIAL_REFERENCE = "RequireIssuerSerialReference";
  
  public static final String REQUIRE_EMBEDDED_TOKEN_REFERENCE = "RequireEmbeddedTokenReference";
  
  public static final String REQUIRE_THUMBPRINT_REFERENCE = "RequireThumbprintReference";
  
  public static final String REQUIRE_EXTERNAL_URI_REFERENCE = "RequireExternalUriReference";
  
  public static final String REQUIRE_EXTERNAL_REFERENCE = "RequireExternalReference";
  
  public static final String REQUIRE_INTERNAL_REFERENCE = "RequireInternalReference";
  
  public static final String WSSX509V1TOKEN10 = "WssX509V1Token10";
  
  public static final String WSSX509V3TOKEN10 = "WssX509V3Token10";
  
  public static final String WSSX509PKCS7TOKEN10 = "WssX509Pkcs7Token10";
  
  public static final String WSSX509PKIPATHV1TOKEN10 = "WssX509PkiPathV1Token10";
  
  public static final String WSSX509V1TOKEN11 = "WssX509V1Token11";
  
  public static final String WSSX509V3TOKEN11 = "WssX509V3Token11";
  
  public static final String WSSX509PKCS7TOKEN11 = "WssX509Pkcs7Token11";
  
  public static final String WSSX509PKIPATHV1TOKEN11 = "WssX509PkiPathV1Token11";
  
  public static final String WSSKERBEROS_V5_AP_REQ_TOKEN11 = "WssKerberosV5ApReqToken11";
  
  public static final String WSSKERBEROS_GSS_V5_AP_REQ_TOKEN11 = "WssGssKerberosV5ApReqToken11";
  
  public static final String REQUIRE_DERIVED_KEYS = "RequireDerivedKeys";
  
  public static final String SC10_SECURITYCONTEXT_TOKEN = "SC10SecurityContextToken";
  
  public static final String WSS_SAML_V10_TOKEN10 = "WssSamlV10Token10";
  
  public static final String WSS_SAML_V11_TOKEN10 = "WssSamlV11Token10";
  
  public static final String WSS_SAML_V10_TOKEN11 = "WssSamlV10Token11";
  
  public static final String WSS_SAML_V11_TOKEN11 = "WssSamlV11Token11";
  
  public static final String WSS_SAML_V20_TOKEN11 = "WssSamlV20Token11";
  
  public static final String WSS_REL_V10_TOKEN10 = "WssRelV10Token10";
  
  public static final String WSS_REL_V20_TOKEN10 = "WssRelV20Token10";
  
  public static final String WSS_REL_V10_TOKEN11 = "WssRelV10Token11";
  
  public static final String WSS_REL_V20_TOKEN11 = "WssRelV20Token11";
  
  public static final String WSS_USERNAME_TOKEN_10 = "WssUsernameToken10";
  
  public static final String WSS_USERNAME_TOKEN_11 = "WssUsernameToken11";
  
  public static final String RSA_KEYVALUE_TOKEN = "RsaKeyValue";
  
  String getIncludeToken();
  
  String getTokenId();
  
  SecurityPolicyVersion getSecurityPolicyVersion();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */