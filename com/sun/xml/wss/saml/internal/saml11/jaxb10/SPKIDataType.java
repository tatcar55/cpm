package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;
import javax.xml.bind.Element;

public interface SPKIDataType {
  List getSPKISexpAndAny();
  
  public static interface SPKISexp extends Element {
    byte[] getValue();
    
    void setValue(byte[] param1ArrayOfbyte);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\SPKIDataType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */