package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;

public interface ProofEncryption {
  String getTargetType();
  
  void setTargetType(String paramString);
  
  Object getAny();
  
  void setAny(Object paramObject);
  
  void setSecurityTokenReference(SecurityTokenReference paramSecurityTokenReference);
  
  SecurityTokenReference getSecurityTokenReference();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\ProofEncryption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */