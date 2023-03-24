package com.sun.xml.ws.api.security.trust.config;

import java.util.Map;
import javax.security.auth.callback.CallbackHandler;

public interface STSConfiguration {
  String getType();
  
  String getIssuer();
  
  boolean getEncryptIssuedToken();
  
  boolean getEncryptIssuedKey();
  
  long getIssuedTokenTimeout();
  
  void setCallbackHandler(CallbackHandler paramCallbackHandler);
  
  Map<String, Object> getOtherOptions();
  
  CallbackHandler getCallbackHandler();
  
  void addTrustSPMetadata(TrustSPMetadata paramTrustSPMetadata, String paramString);
  
  TrustSPMetadata getTrustSPMetadata(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\config\STSConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */