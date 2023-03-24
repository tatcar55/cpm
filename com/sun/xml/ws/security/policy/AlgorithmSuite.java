package com.sun.xml.ws.security.policy;

import java.util.Set;

public interface AlgorithmSuite {
  public static final String INCLUSIVE14N = "InclusiveC14N";
  
  public static final String SOAP_NORMALIZATION10 = "SOAPNormalization10";
  
  public static final String STR_TRANSFORM10 = "STRTransform10";
  
  public static final String XPATH10 = "XPath10";
  
  public static final String XPATH_FILTER20 = "XPathFilter20";
  
  public static final int MAX_SKL = 256;
  
  public static final int MAX_AKL = 4096;
  
  public static final int MIN_AKL = 1024;
  
  AlgorithmSuiteValue getType();
  
  Set getAdditionalProps();
  
  String getDigestAlgorithm();
  
  String getEncryptionAlgorithm();
  
  String getSymmetricKeySignatureAlgorithm();
  
  String getAsymmetricKeySignatureAlgorithm();
  
  String getSymmetricKeyAlgorithm();
  
  String getAsymmetricKeyAlgorithm();
  
  String getSignatureKDAlogrithm();
  
  String getEncryptionKDAlogrithm();
  
  int getMinSKLAlgorithm();
  
  String getComputedKeyAlgorithm();
  
  int getMaxSymmetricKeyLength();
  
  int getMinAsymmetricKeyLength();
  
  int getMaxAsymmetricKeyLength();
  
  void setSignatureAlgorithm(String paramString);
  
  String getSignatureAlgorithm();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\AlgorithmSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */