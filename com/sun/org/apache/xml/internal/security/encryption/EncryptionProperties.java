package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;

public interface EncryptionProperties {
  String getId();
  
  void setId(String paramString);
  
  Iterator getEncryptionProperties();
  
  void addEncryptionProperty(EncryptionProperty paramEncryptionProperty);
  
  void removeEncryptionProperty(EncryptionProperty paramEncryptionProperty);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptionProperties.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */