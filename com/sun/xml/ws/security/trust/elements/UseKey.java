package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.Token;
import java.net.URI;

public interface UseKey {
  void setToken(Token paramToken);
  
  Token getToken();
  
  void setSignatureID(URI paramURI);
  
  URI getSignatureID();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\UseKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */