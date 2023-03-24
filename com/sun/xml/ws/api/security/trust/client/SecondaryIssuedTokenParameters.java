package com.sun.xml.ws.api.security.trust.client;

import com.sun.xml.ws.api.security.trust.Claims;

public interface SecondaryIssuedTokenParameters {
  String getTokenType();
  
  String getKeyType();
  
  long getKeySize();
  
  String getSignatureAlgorithm();
  
  String getEncryptionAlgorithm();
  
  String getCanonicalizationAlgorithm();
  
  String getKeyWrapAlgorithm();
  
  String getSignWith();
  
  String getEncryptWith();
  
  Claims getClaims();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\client\SecondaryIssuedTokenParameters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */