package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;

import com.sun.xml.bind.unmarshaller.Tracer;
import javax.xml.bind.ValidationEvent;
import javax.xml.namespace.NamespaceContext;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public interface UnmarshallingContext extends NamespaceContext {
  GrammarInfo getGrammarInfo();
  
  void pushContentHandler(UnmarshallingEventHandler paramUnmarshallingEventHandler, int paramInt);
  
  void popContentHandler() throws SAXException;
  
  UnmarshallingEventHandler getCurrentHandler();
  
  String[] getNewlyDeclaredPrefixes();
  
  String[] getAllDeclaredPrefixes();
  
  void pushAttributes(Attributes paramAttributes, boolean paramBoolean);
  
  void popAttributes();
  
  int getAttribute(String paramString1, String paramString2);
  
  Attributes getUnconsumedAttributes();
  
  void consumeAttribute(int paramInt) throws SAXException;
  
  String eatAttribute(int paramInt) throws SAXException;
  
  void addPatcher(Runnable paramRunnable);
  
  String addToIdTable(String paramString);
  
  Object getObjectFromId(String paramString);
  
  Locator getLocator();
  
  void handleEvent(ValidationEvent paramValidationEvent, boolean paramBoolean) throws SAXException;
  
  String resolveNamespacePrefix(String paramString);
  
  String getBaseUri();
  
  boolean isUnparsedEntity(String paramString);
  
  boolean isNotation(String paramString);
  
  Tracer getTracer();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\UnmarshallingContext.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */