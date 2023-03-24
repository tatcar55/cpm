package com.sun.xml.stream.buffer.sax;

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class DefaultWithLexicalHandler extends DefaultHandler implements LexicalHandler {
  public void comment(char[] ch, int start, int length) throws SAXException {}
  
  public void startDTD(String name, String publicId, String systemId) throws SAXException {}
  
  public void endDTD() throws SAXException {}
  
  public void startEntity(String name) throws SAXException {}
  
  public void endEntity(String name) throws SAXException {}
  
  public void startCDATA() throws SAXException {}
  
  public void endCDATA() throws SAXException {}
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\sax\DefaultWithLexicalHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */