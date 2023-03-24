package com.sun.xml.wss.saml.internal.saml11.jaxb10;

import java.util.List;
import javax.xml.bind.Element;

public interface TransformType {
  String getAlgorithm();
  
  void setAlgorithm(String paramString);
  
  List getContent();
  
  public static interface XPath extends Element {
    String getValue();
    
    void setValue(String param1String);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\TransformType.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */