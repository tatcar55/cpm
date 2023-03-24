package com.sun.xml.ws.security.trust.elements;

import java.util.List;

public interface Authenticator {
  List<Object> getAny();
  
  byte[] getRawCombinedHash();
  
  void setRawCombinedHash(byte[] paramArrayOfbyte);
  
  String getTextCombinedHash();
  
  void setTextCombinedHash(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\Authenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */