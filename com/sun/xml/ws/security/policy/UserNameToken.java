package com.sun.xml.ws.security.policy;

import java.util.Set;

public interface UserNameToken extends Token {
  String getType();
  
  boolean useNonce();
  
  boolean useCreated();
  
  boolean hasPassword();
  
  boolean useHashPassword();
  
  Issuer getIssuer();
  
  IssuerName getIssuerName();
  
  Claims getClaims();
  
  boolean isRequireDerivedKeys();
  
  Set getTokenRefernceType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\UserNameToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */