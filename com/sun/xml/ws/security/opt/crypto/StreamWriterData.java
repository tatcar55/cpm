package com.sun.xml.ws.security.opt.crypto;

import javax.xml.crypto.Data;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jvnet.staxex.NamespaceContextEx;

public interface StreamWriterData extends Data {
  NamespaceContextEx getNamespaceContext();
  
  void write(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\StreamWriterData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */