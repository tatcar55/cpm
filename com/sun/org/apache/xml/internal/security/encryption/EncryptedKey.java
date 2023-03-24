package com.sun.org.apache.xml.internal.security.encryption;

public interface EncryptedKey extends EncryptedType {
  String getRecipient();
  
  void setRecipient(String paramString);
  
  ReferenceList getReferenceList();
  
  void setReferenceList(ReferenceList paramReferenceList);
  
  String getCarriedName();
  
  void setCarriedName(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptedKey.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */