package com.sun.xml.ws.api.security.trust;

import com.sun.xml.ws.security.IssuedTokenContext;

public interface STSTokenProvider {
  void generateToken(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void isValideToken(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void renewToken(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void invalidateToken(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\STSTokenProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */