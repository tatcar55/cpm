package com.sun.xml.ws.security.policy;

public interface SymmetricBinding extends Binding {
  Token getEncryptionToken();
  
  Token getSignatureToken();
  
  Token getProtectionToken();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\SymmetricBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */