package com.sun.xml.ws.security.policy;

import java.util.Set;

public interface WSSAssertion {
  public static final String MUSTSUPPORT_REF_THUMBPRINT = "MustSupportRefThumbprint";
  
  public static final String MUSTSUPPORT_REF_ENCRYPTED_KEY = "MustSupportRefEncryptedKey";
  
  public static final String REQUIRE_SIGNATURE_CONFIRMATION = "RequireSignatureConfirmation";
  
  public static final String MUST_SUPPORT_CLIENT_CHALLENGE = "MustSupportClientChallenge";
  
  public static final String MUST_SUPPORT_SERVER_CHALLENGE = "MustSupportServerChallenge";
  
  public static final String REQUIRE_CLIENT_ENTROPY = "RequireClientEntropy";
  
  public static final String REQUIRE_SERVER_ENTROPY = "RequireServerEntropy";
  
  public static final String MUST_SUPPORT_ISSUED_TOKENS = "MustSupportIssuedTokens";
  
  public static final String MUSTSUPPORT_REF_ISSUER_SERIAL = "MustSupportRefIssuerSerial";
  
  public static final String REQUIRE_EXTERNAL_URI_REFERENCE = "RequireExternalUriReference";
  
  public static final String REQUIRE_EMBEDDED_TOKEN_REF = "RequireEmbeddedTokenReference";
  
  public static final String MUST_SUPPORT_REF_KEYIDENTIFIER = "MustSupportRefKeyIdentifier";
  
  Set<String> getRequiredProperties();
  
  String getType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\WSSAssertion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */