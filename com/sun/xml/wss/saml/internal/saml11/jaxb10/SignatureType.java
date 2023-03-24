package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;

public interface SignatureType {
  SignedInfoType getSignedInfo();
  
  void setSignedInfo(SignedInfoType paramSignedInfoType);
  
  KeyInfoType getKeyInfo();
  
  void setKeyInfo(KeyInfoType paramKeyInfoType);
  
  SignatureValueType getSignatureValue();
  
  void setSignatureValue(SignatureValueType paramSignatureValueType);
  
  List getObject();
  
  String getId();
  
  void setId(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\SignatureType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */