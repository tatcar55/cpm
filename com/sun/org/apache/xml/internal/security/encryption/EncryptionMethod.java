package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface EncryptionMethod {
  String getAlgorithm();
  
  int getKeySize();
  
  void setKeySize(int paramInt);
  
  byte[] getOAEPparams();
  
  void setOAEPparams(byte[] paramArrayOfbyte);
  
  Iterator getEncryptionMethodInformation();
  
  void addEncryptionMethodInformation(Element paramElement);
  
  void removeEncryptionMethodInformation(Element paramElement);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptionMethod.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */