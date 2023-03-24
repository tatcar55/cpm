package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface EncryptionProperty {
  String getTarget();
  
  void setTarget(String paramString);
  
  String getId();
  
  void setId(String paramString);
  
  String getAttribute(String paramString);
  
  void setAttribute(String paramString1, String paramString2);
  
  Iterator getEncryptionInformation();
  
  void addEncryptionInformation(Element paramElement);
  
  void removeEncryptionInformation(Element paramElement);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\EncryptionProperty.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */