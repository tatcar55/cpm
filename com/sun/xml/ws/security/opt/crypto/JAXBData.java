package com.sun.xml.ws.security.opt.crypto;

import com.sun.xml.wss.XWSSecurityException;
import java.io.OutputStream;
import javax.xml.bind.JAXBElement;
import javax.xml.crypto.Data;
import javax.xml.stream.XMLStreamWriter;
import org.jvnet.staxex.NamespaceContextEx;

public interface JAXBData extends Data {
  NamespaceContextEx getNamespaceContext();
  
  void writeTo(XMLStreamWriter paramXMLStreamWriter) throws XWSSecurityException;
  
  void writeTo(OutputStream paramOutputStream) throws XWSSecurityException;
  
  JAXBElement getJAXBElement();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\crypto\JAXBData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */