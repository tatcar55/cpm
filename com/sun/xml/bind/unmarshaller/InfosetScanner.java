package com.sun.xml.bind.unmarshaller;

import com.sun.xml.bind.v2.runtime.unmarshaller.LocatorEx;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public interface InfosetScanner<XmlNode> {
  void scan(XmlNode paramXmlNode) throws SAXException;
  
  void setContentHandler(ContentHandler paramContentHandler);
  
  ContentHandler getContentHandler();
  
  XmlNode getCurrentElement();
  
  LocatorEx getLocator();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bin\\unmarshaller\InfosetScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */