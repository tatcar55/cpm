package com.sun.xml.ws.security.trust;

import com.sun.xml.ws.api.security.trust.WSTrustException;
import com.sun.xml.ws.security.IssuedTokenContext;

public interface TrustPlugin {
  void process(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void processValidate(IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\TrustPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */