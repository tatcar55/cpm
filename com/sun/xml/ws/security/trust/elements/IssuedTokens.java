package com.sun.xml.ws.security.trust.elements;

import java.util.List;

public interface IssuedTokens {
  RequestSecurityTokenResponseCollection getIssuedTokens();
  
  void setIssuedTokens(RequestSecurityTokenResponseCollection paramRequestSecurityTokenResponseCollection);
  
  List<Object> getAny();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\IssuedTokens.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */