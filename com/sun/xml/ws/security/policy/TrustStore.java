package com.sun.xml.ws.security.policy;

public interface TrustStore extends KeyStore {
  String getPeerAlias();
  
  String getSTSAlias();
  
  String getServiceAlias();
  
  String getCertSelectorClassName();
  
  String getTrustStoreCallbackHandler();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\TrustStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */