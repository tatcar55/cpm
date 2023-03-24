package com.sun.xml.ws.security.policy;

public interface KeyStore {
  String getLocation();
  
  String getType();
  
  char[] getPassword();
  
  String getAlias();
  
  String getKeyPassword();
  
  String getAliasSelectorClassName();
  
  String getKeyStoreCallbackHandler();
  
  String getKeyStoreLoginModuleConfigName();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\KeyStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */