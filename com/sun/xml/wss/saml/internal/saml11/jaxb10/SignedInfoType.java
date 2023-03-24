package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;

public interface SignedInfoType {
  SignatureMethodType getSignatureMethod();
  
  void setSignatureMethod(SignatureMethodType paramSignatureMethodType);
  
  CanonicalizationMethodType getCanonicalizationMethod();
  
  void setCanonicalizationMethod(CanonicalizationMethodType paramCanonicalizationMethodType);
  
  List getReference();
  
  String getId();
  
  void setId(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\SignedInfoType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */