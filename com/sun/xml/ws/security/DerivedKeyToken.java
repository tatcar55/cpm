package com.sun.xml.ws.security;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.SecretKey;

public interface DerivedKeyToken extends Token {
  public static final String DERIVED_KEY_TOKEN_TYPE = "http://schemas.xmlsoap.org/ws/2005/02/sc/dk";
  
  public static final String DEFAULT_DERIVED_KEY_TOKEN_ALGORITHM = "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
  
  public static final String DEFAULT_DERIVEDKEYTOKEN_LABEL = "WS-SecureConversationWS-SecureConversation";
  
  URI getAlgorithm();
  
  byte[] getNonce();
  
  long getLength();
  
  long getOffset();
  
  long getGeneration();
  
  String getLabel();
  
  SecretKey generateSymmetricKey(String paramString) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\DerivedKeyToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */