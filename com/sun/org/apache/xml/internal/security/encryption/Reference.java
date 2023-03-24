package com.sun.org.apache.xml.internal.security.encryption;

import java.util.Iterator;
import org.w3c.dom.Element;

public interface Reference {
  String getURI();
  
  void setURI(String paramString);
  
  Iterator getElementRetrievalInformation();
  
  void addElementRetrievalInformation(Element paramElement);
  
  void removeElementRetrievalInformation(Element paramElement);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\org\apache\xml\internal\security\encryption\Reference.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */