package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.EncryptedKey;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public interface Entropy {
  public static final String BINARY_SECRET_TYPE = "BinarySecret";
  
  public static final String ENCRYPTED_KEY_TYPE = "EncryptedKey";
  
  public static final String CUSTOM_TYPE = "Custom";
  
  String getEntropyType();
  
  void setEntropyType(String paramString);
  
  List<Object> getAny();
  
  Map<QName, String> getOtherAttributes();
  
  BinarySecret getBinarySecret();
  
  void setBinarySecret(BinarySecret paramBinarySecret);
  
  EncryptedKey getEncryptedKey();
  
  void setEncryptedKey(EncryptedKey paramEncryptedKey);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\Entropy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */