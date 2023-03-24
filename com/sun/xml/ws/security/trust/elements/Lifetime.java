package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.wsu10.AttributedDateTime;

public interface Lifetime {
  AttributedDateTime getCreated();
  
  AttributedDateTime getExpires();
  
  void setCreated(AttributedDateTime paramAttributedDateTime);
  
  void setExpires(AttributedDateTime paramAttributedDateTime);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\Lifetime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */