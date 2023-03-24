package com.sun.xml.ws.security.policy;

import java.util.Set;

public interface X509Token extends Token {
  String getTokenType();
  
  Set getTokenRefernceType();
  
  boolean isRequireDerivedKeys();
  
  Issuer getIssuer();
  
  IssuerName getIssuerName();
  
  Claims getClaims();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\X509Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */