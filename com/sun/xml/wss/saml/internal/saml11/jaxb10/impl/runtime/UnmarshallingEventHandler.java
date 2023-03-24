package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public interface UnmarshallingEventHandler {
  Object owner();
  
  void enterElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes) throws SAXException;
  
  void leaveElement(String paramString1, String paramString2, String paramString3) throws SAXException;
  
  void text(String paramString) throws SAXException;
  
  void enterAttribute(String paramString1, String paramString2, String paramString3) throws SAXException;
  
  void leaveAttribute(String paramString1, String paramString2, String paramString3) throws SAXException;
  
  void leaveChild(int paramInt) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\UnmarshallingEventHandler.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */