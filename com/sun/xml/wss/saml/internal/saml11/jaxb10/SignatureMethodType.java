package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.Element;

public interface SignatureMethodType {
  String getAlgorithm();
  
  void setAlgorithm(String paramString);
  
  List getContent();
  
  public static interface HMACOutputLength extends Element {
    BigInteger getValue();
    
    void setValue(BigInteger param1BigInteger);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\SignatureMethodType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */