package com.sun.xml.ws.api.security.trust;

import com.sun.xml.ws.api.security.trust.config.STSConfiguration;
import com.sun.xml.ws.security.IssuedTokenContext;
import java.util.Map;

public interface WSTrustContract<K, V> {
  void init(STSConfiguration paramSTSConfiguration);
  
  V issue(K paramK, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  V renew(K paramK, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  V cancel(K paramK, IssuedTokenContext paramIssuedTokenContext, Map paramMap) throws WSTrustException;
  
  V validate(K paramK, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
  
  void handleUnsolicited(V paramV, IssuedTokenContext paramIssuedTokenContext) throws WSTrustException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\WSTrustContract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */