package com.sun.xml.ws.security.policy;

public interface AsymmetricBinding extends Binding {
  Token getRecipientToken();
  
  Token getRecipientSignatureToken();
  
  Token getRecipientEncryptionToken();
  
  Token getInitiatorToken();
  
  Token getInitiatorSignatureToken();
  
  Token getInitiatorEncryptionToken();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\policy\AsymmetricBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */