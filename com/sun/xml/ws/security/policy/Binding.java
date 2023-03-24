package com.sun.xml.ws.security.policy;

public interface Binding {
  public static final String ENCRYPT_SIGN = "EncryptBeforeSigning";
  
  public static final String SIGN_ENCRYPT = "SignBeforeEncrypting";
  
  AlgorithmSuite getAlgorithmSuite();
  
  boolean isIncludeTimeStamp();
  
  boolean isDisableTimestampSigning();
  
  MessageLayout getLayout();
  
  boolean isSignContent();
  
  String getProtectionOrder();
  
  boolean getTokenProtection();
  
  boolean getSignatureProtection();
  
  SecurityPolicyVersion getSecurityPolicyVersion();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\Binding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */