package com.sun.xml.ws.security;

import java.util.List;

public interface SecurityTokenReference extends Token {
  List getAny();
  
  String getId();
  
  void setId(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\SecurityTokenReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */