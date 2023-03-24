package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import com.sun.msv.verifier.DocumentDeclaration;

public interface ValidatableObject extends XMLSerializable {
  DocumentDeclaration createRawValidator();
  
  Class getPrimaryInterface();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ValidatableObject.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */