package com.sun.xml.ws.security.policy;

public interface RequestSecurityTokenTemplate {
  String getTrustVersion();
  
  String getTokenType();
  
  String getRequestType();
  
  Lifetime getLifetime();
  
  String getAuthenticationType();
  
  String getKeyType();
  
  int getKeySize();
  
  String getSignatureAlgorithm();
  
  String getEncryptionAlgorithm();
  
  String getCanonicalizationAlgorithm();
  
  boolean getProofEncryptionRequired();
  
  String getComputedKeyAlgorithm();
  
  boolean getEncryptionRequired();
  
  String getSignWith();
  
  String getEncryptWith();
  
  String getKeyWrapAlgorithm();
  
  Claims getClaims();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\RequestSecurityTokenTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */