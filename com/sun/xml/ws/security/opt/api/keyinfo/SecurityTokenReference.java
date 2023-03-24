package com.sun.xml.ws.security.opt.api.keyinfo;

import com.sun.xml.ws.security.opt.api.reference.Reference;

public interface SecurityTokenReference extends Token {
  public static final String KEYIDENTIFIER = "Identifier";
  
  public static final String REFERENCE = "Direct";
  
  public static final String X509DATA_ISSUERSERIAL = "X509Data";
  
  public static final String DIRECT_REFERENCE = "Reference";
  
  void setReference(Reference paramReference);
  
  Reference getReference();
  
  void setTokenType(String paramString);
  
  String getTokenType();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\keyinfo\SecurityTokenReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */