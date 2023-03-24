package com.sun.xml.ws.security.opt.api;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface SecurityElement {
  String getId();
  
  void setId(String paramString);
  
  String getNamespaceURI();
  
  String getLocalPart();
  
  XMLStreamReader readHeader() throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\SecurityElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */