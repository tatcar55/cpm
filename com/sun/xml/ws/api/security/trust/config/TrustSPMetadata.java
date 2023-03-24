package com.sun.xml.ws.api.security.trust.config;

import java.util.Map;

public interface TrustSPMetadata {
  String getCertAlias();
  
  String getTokenType();
  
  String getKeyType();
  
  Map<String, Object> getOtherOptions();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\security\trust\config\TrustSPMetadata.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */