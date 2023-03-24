package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import com.sun.xml.bind.JAXBObject;
import com.sun.xml.bind.marshaller.IdentifiableObject;
import com.sun.xml.bind.serializer.AbortSerializationException;
import javax.xml.bind.ValidationEvent;
import org.xml.sax.SAXException;

public interface XMLSerializer {
  void reportError(ValidationEvent paramValidationEvent) throws AbortSerializationException;
  
  void startElement(String paramString1, String paramString2) throws SAXException;
  
  void endNamespaceDecls() throws SAXException;
  
  void endAttributes() throws SAXException;
  
  void endElement() throws SAXException;
  
  void text(String paramString1, String paramString2) throws SAXException;
  
  void startAttribute(String paramString1, String paramString2) throws SAXException;
  
  void endAttribute() throws SAXException;
  
  NamespaceContext2 getNamespaceContext();
  
  String onID(IdentifiableObject paramIdentifiableObject, String paramString) throws SAXException;
  
  String onIDREF(IdentifiableObject paramIdentifiableObject) throws SAXException;
  
  void childAsBody(JAXBObject paramJAXBObject, String paramString) throws SAXException;
  
  void childAsAttributes(JAXBObject paramJAXBObject, String paramString) throws SAXException;
  
  void childAsURIs(JAXBObject paramJAXBObject, String paramString) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\XMLSerializer.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */