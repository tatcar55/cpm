package com.sun.xml.bind;

import java.util.concurrent.Callable;
import javax.xml.bind.ValidationEventHandler;
import org.xml.sax.SAXException;

public abstract class IDResolver {
  public void startDocument(ValidationEventHandler eventHandler) throws SAXException {}
  
  public void endDocument() throws SAXException {}
  
  public abstract void bind(String paramString, Object paramObject) throws SAXException;
  
  public abstract Callable<?> resolve(String paramString, Class paramClass) throws SAXException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\IDResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */