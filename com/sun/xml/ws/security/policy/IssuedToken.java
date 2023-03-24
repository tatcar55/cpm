package com.sun.xml.ws.security.policy;

import java.util.Iterator;

public interface IssuedToken extends Token {
  Issuer getIssuer();
  
  IssuerName getIssuerName();
  
  Claims getClaims();
  
  RequestSecurityTokenTemplate getRequestSecurityTokenTemplate();
  
  Iterator getTokenRefernceType();
  
  boolean isRequireDerivedKeys();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\IssuedToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */