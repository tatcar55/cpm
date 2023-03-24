package com.sun.xml.ws.security;

import java.net.URI;
import java.util.List;

public interface SecurityContextToken extends Token {
  URI getIdentifier();
  
  String getInstance();
  
  String getWsuId();
  
  List getExtElements();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\SecurityContextToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */