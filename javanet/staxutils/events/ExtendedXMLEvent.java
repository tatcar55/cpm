package javanet.staxutils.events;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

public interface ExtendedXMLEvent extends XMLEvent {
  boolean matches(XMLEvent paramXMLEvent);
  
  void writeEvent(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\events\ExtendedXMLEvent.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */