package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import com.sun.xml.bind.JAXBObject;
import org.xml.sax.SAXException;

public interface XMLSerializable extends JAXBObject {
  void serializeBody(XMLSerializer paramXMLSerializer) throws SAXException;
  
  void serializeAttributes(XMLSerializer paramXMLSerializer) throws SAXException;
  
  void serializeURIs(XMLSerializer paramXMLSerializer) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\XMLSerializable.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */