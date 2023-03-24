package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.Token;

public interface RequestedSecurityToken {
  Object getAny();
  
  void setAny(Object paramObject);
  
  Token getToken();
  
  void setToken(Token paramToken);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\RequestedSecurityToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */