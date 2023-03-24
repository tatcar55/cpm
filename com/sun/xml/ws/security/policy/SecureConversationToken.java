package com.sun.xml.ws.security.policy;

import com.sun.xml.ws.policy.NestedPolicy;
import java.util.Set;

public interface SecureConversationToken extends Token {
  Set getTokenRefernceTypes();
  
  boolean isRequireDerivedKeys();
  
  boolean isMustNotSendCancel();
  
  boolean isMustNotSendRenew();
  
  String getTokenType();
  
  Issuer getIssuer();
  
  IssuerName getIssuerName();
  
  Claims getClaims();
  
  NestedPolicy getBootstrapPolicy();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\SecureConversationToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */