package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;
import javax.xml.bind.Element;

public interface X509DataType {
  List getX509IssuerSerialOrX509SKIOrX509SubjectName();
  
  public static interface X509SubjectName extends Element {
    String getValue();
    
    void setValue(String param1String);
  }
  
  public static interface X509SKI extends Element {
    byte[] getValue();
    
    void setValue(byte[] param1ArrayOfbyte);
  }
  
  public static interface X509IssuerSerial extends Element, X509IssuerSerialType {}
  
  public static interface X509Certificate extends Element {
    byte[] getValue();
    
    void setValue(byte[] param1ArrayOfbyte);
  }
  
  public static interface X509CRL extends Element {
    byte[] getValue();
    
    void setValue(byte[] param1ArrayOfbyte);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\X509DataType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */