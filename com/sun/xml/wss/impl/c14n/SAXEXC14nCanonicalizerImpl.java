package com.sun.xml.wss.impl.c14n;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class SAXEXC14nCanonicalizerImpl implements ContentHandler {
  public void setDocumentLocator(Locator locator) {}
  
  public void startDocument() throws SAXException {}
  
  public void endDocument() throws SAXException {}
  
  public void startPrefixMapping(String prefix, String uri) throws SAXException {}
  
  public void endPrefixMapping(String prefix) throws SAXException {}
  
  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {}
  
  public void endElement(String uri, String localName, String qName) throws SAXException {}
  
  public void characters(char[] ch, int start, int length) throws SAXException {}
  
  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
  
  public void processingInstruction(String target, String data) throws SAXException {}
  
  public void skippedEntity(String name) throws SAXException {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\SAXEXC14nCanonicalizerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */