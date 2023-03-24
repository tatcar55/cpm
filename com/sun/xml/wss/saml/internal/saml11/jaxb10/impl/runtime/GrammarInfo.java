package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import com.sun.msv.grammar.Grammar;
import javax.xml.bind.JAXBException;

public interface GrammarInfo {
  UnmarshallingEventHandler createUnmarshaller(String paramString1, String paramString2, UnmarshallingContext paramUnmarshallingContext);
  
  Class getRootElement(String paramString1, String paramString2);
  
  String[] getProbePoints();
  
  boolean recognize(String paramString1, String paramString2);
  
  Class getDefaultImplementation(Class paramClass);
  
  Grammar getGrammar() throws JAXBException;
  
  XMLSerializable castToXMLSerializable(Object paramObject);
  
  ValidatableObject castToValidatableObject(Object paramObject);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\GrammarInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */