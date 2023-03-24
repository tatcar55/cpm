package com.sun.org.apache.xml.internal.security.encryption;

import com.sun.org.apache.xml.internal.security.keys.KeyInfo;

public interface EncryptedType {
  String getId();
  
  void setId(String paramString);
  
  String getType();
  
  void setType(String paramString);
  
  String getMimeType();
  
  void setMimeType(String paramString);
  
  String getEncoding();
  
  void setEncoding(String paramString);
  
  EncryptionMethod getEncryptionMethod();
  
  void setEncryptionMethod(EncryptionMethod paramEncryptionMethod);
  
  KeyInfo getKeyInfo();
  
  void setKeyInfo(KeyInfo paramKeyInfo);
  
  CipherData getCipherData();
  
  EncryptionProperties getEncryptionProperties();
  
  void setEncryptionProperties(EncryptionProperties paramEncryptionProperties);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptedType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */