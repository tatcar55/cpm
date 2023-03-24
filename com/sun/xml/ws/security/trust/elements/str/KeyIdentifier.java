package com.sun.xml.ws.security.trust.elements.str;

import java.net.URI;

public interface KeyIdentifier extends Reference {
  URI getValueTypeURI();
  
  URI getEncodingTypeURI();
  
  String getValue();
  
  void setValue(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\str\KeyIdentifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */