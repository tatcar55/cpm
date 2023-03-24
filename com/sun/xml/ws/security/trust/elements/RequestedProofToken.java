package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
import java.net.URI;

public interface RequestedProofToken {
  public static final String COMPUTED_KEY_TYPE = "ComputedKey";
  
  public static final String TOKEN_REF_TYPE = "SecurityTokenReference";
  
  public static final String ENCRYPTED_KEY_TYPE = "EncryptedKey";
  
  public static final String BINARY_SECRET_TYPE = "BinarySecret";
  
  public static final String CUSTOM_TYPE = "Custom";
  
  String getProofTokenType();
  
  void setProofTokenType(String paramString);
  
  Object getAny();
  
  void setAny(Object paramObject);
  
  void setSecurityTokenReference(SecurityTokenReference paramSecurityTokenReference);
  
  SecurityTokenReference getSecurityTokenReference();
  
  void setComputedKey(URI paramURI);
  
  URI getComputedKey();
  
  void setBinarySecret(BinarySecret paramBinarySecret);
  
  BinarySecret getBinarySecret();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\RequestedProofToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */