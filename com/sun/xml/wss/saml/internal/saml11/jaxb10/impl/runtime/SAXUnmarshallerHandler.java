package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import javax.xml.bind.UnmarshallerHandler;
import javax.xml.bind.ValidationEvent;
import org.xml.sax.SAXException;

public interface SAXUnmarshallerHandler extends UnmarshallerHandler {
  void handleEvent(ValidationEvent paramValidationEvent, boolean paramBoolean) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\SAXUnmarshallerHandler.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */