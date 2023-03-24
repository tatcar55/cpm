package org.jvnet.fastinfoset.stax;

import javax.xml.stream.XMLStreamException;

public interface FastInfosetStreamReader {
  int peekNext() throws XMLStreamException;
  
  int accessNamespaceCount();
  
  String accessLocalName();
  
  String accessNamespaceURI();
  
  String accessPrefix();
  
  char[] accessTextCharacters();
  
  int accessTextStart();
  
  int accessTextLength();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\fastinfoset\stax\FastInfosetStreamReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */