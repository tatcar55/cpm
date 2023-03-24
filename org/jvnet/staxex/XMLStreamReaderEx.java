package org.jvnet.staxex;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface XMLStreamReaderEx extends XMLStreamReader {
  CharSequence getPCDATA() throws XMLStreamException;
  
  NamespaceContextEx getNamespaceContext();
  
  String getElementTextTrim() throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\XMLStreamReaderEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */