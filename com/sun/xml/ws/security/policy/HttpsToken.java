package com.sun.xml.ws.security.policy;

public interface HttpsToken extends Token {
  boolean isRequireClientCertificate();
  
  boolean isHttpBasicAuthentication();
  
  boolean isHttpDigestAuthentication();
  
  Issuer getIssuer();
  
  IssuerName getIssuerName();
  
  Claims getClaims();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\HttpsToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */