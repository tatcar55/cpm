package com.sun.xml.ws.security.opt.api;

import java.io.OutputStream;
import java.util.HashMap;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface SecurityElementWriter {
  void writeTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
  
  void writeTo(XMLStreamWriter paramXMLStreamWriter, HashMap paramHashMap) throws XMLStreamException;
  
  void writeTo(OutputStream paramOutputStream);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\api\SecurityElementWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */