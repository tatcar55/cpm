package com.sun.xml.wss.impl.callback;

import java.util.Map;
import javax.security.auth.Subject;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Element;

public interface SAMLValidator extends SAMLAssertionValidator {
  void validate(Element paramElement, Map paramMap, Subject paramSubject) throws SAMLAssertionValidator.SAMLValidationException;
  
  void validate(XMLStreamReader paramXMLStreamReader, Map paramMap, Subject paramSubject) throws SAMLAssertionValidator.SAMLValidationException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\callback\SAMLValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */