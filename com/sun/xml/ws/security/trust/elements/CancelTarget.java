package com.sun.xml.ws.security.trust.elements;

import com.sun.xml.ws.security.Token;
import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;

public interface CancelTarget {
  public static final String STR_TARGET_TYPE = "SecurityTokenReference";
  
  public static final String CUSTOM_TARGET_TYPE = "Custom";
  
  String getTargetType();
  
  Object getAny();
  
  void setAny(Object paramObject);
  
  void setSecurityTokenReference(SecurityTokenReference paramSecurityTokenReference);
  
  SecurityTokenReference getSecurityTokenReference();
  
  void setToken(Token paramToken);
  
  Token getToken();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trust\elements\CancelTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */