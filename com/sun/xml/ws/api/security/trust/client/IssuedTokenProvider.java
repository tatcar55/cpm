package com.sun.xml.ws.api.security.trust.client;

import com.sun.xml.ws.api.security.trust.WSTrustException;
import com.sun.xml.ws.security.IssuedTokenContext;

public interface IssuedTokenProvider {
  void issue(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void cancel(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void renew(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void validate(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\client\IssuedTokenProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */