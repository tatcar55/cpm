package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.api.security.trust.WSTrustException;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
import com.sun.xml.ws.policy.impl.bindings.PolicyReference;
import java.net.URI;

public interface WSTrustElementBase {
  URI getTokenType();
  
  void setTokenType(URI paramURI);
  
  Lifetime getLifetime();
  
  void setLifetime(Lifetime paramLifetime);
  
  Entropy getEntropy();
  
  void setEntropy(Entropy paramEntropy);
  
  void setAppliesTo(AppliesTo paramAppliesTo);
  
  AppliesTo getAppliesTo();
  
  void setOnBehalfOf(OnBehalfOf paramOnBehalfOf);
  
  OnBehalfOf getOnBehalfOf();
  
  void setIssuer(Issuer paramIssuer);
  
  Issuer getIssuer();
  
  void setRenewable(Renewing paramRenewing);
  
  Renewing getRenewable();
  
  void setSignChallenge(SignChallenge paramSignChallenge);
  
  SignChallenge getSignChallenge();
  
  void setBinaryExchange(BinaryExchange paramBinaryExchange);
  
  BinaryExchange getBinaryExchange();
  
  void setAuthenticationType(URI paramURI);
  
  URI getAuthenticationType();
  
  void setKeyType(URI paramURI) throws WSTrustException;
  
  URI getKeyType();
  
  void setKeySize(long paramLong);
  
  long getKeySize();
  
  void setSignatureAlgorithm(URI paramURI);
  
  URI getSignatureAlgorithm();
  
  void setEncryptionAlgorithm(URI paramURI);
  
  URI getEncryptionAlgorithm();
  
  void setCanonicalizationAlgorithm(URI paramURI);
  
  URI getCanonicalizationAlgorithm();
  
  void setUseKey(UseKey paramUseKey);
  
  UseKey getUseKey();
  
  void setProofEncryption(ProofEncryption paramProofEncryption);
  
  ProofEncryption getProofEncryption();
  
  void setComputedKeyAlgorithm(URI paramURI);
  
  URI getComputedKeyAlgorithm();
  
  void setEncryption(Encryption paramEncryption);
  
  Encryption getEncryption();
  
  void setSignWith(URI paramURI);
  
  URI getSignWith();
  
  void setEncryptWith(URI paramURI);
  
  URI getEncryptWith();
  
  void setKeyWrapAlgorithm(URI paramURI);
  
  URI getKeyWrapAlgorithm();
  
  void setDelegateTo(DelegateTo paramDelegateTo);
  
  DelegateTo getDelegateTo();
  
  void setForwardable(boolean paramBoolean);
  
  boolean getForwardable();
  
  void setDelegatable(boolean paramBoolean);
  
  boolean getDelegatable();
  
  void setPolicy(Policy paramPolicy);
  
  Policy getPolicy();
  
  void setPolicyReference(PolicyReference paramPolicyReference);
  
  PolicyReference getPolicyReference();
  
  AllowPostdating getAllowPostdating();
  
  void setAllowPostdating(AllowPostdating paramAllowPostdating);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\WSTrustElementBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */