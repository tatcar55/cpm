package com.sun.xml.ws.xmlfilter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface InvocationProcessorFactory {
  InvocationProcessor createInvocationProcessor(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\xmlfilter\InvocationProcessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */