package com.sun.xml.ws.api.message;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.bind.api.Bridge;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.spi.db.XMLBridge;
import java.util.Set;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

public interface Header {
  boolean isIgnorable(@NotNull SOAPVersion paramSOAPVersion, @NotNull Set<String> paramSet);
  
  @NotNull
  String getRole(@NotNull SOAPVersion paramSOAPVersion);
  
  boolean isRelay();
  
  @NotNull
  String getNamespaceURI();
  
  @NotNull
  String getLocalPart();
  
  @Nullable
  String getAttribute(@NotNull String paramString1, @NotNull String paramString2);
  
  @Nullable
  String getAttribute(@NotNull QName paramQName);
  
  XMLStreamReader readHeader() throws XMLStreamException;
  
  <T> T readAsJAXB(Unmarshaller paramUnmarshaller) throws JAXBException;
  
  <T> T readAsJAXB(Bridge<T> paramBridge) throws JAXBException;
  
  <T> T readAsJAXB(XMLBridge<T> paramXMLBridge) throws JAXBException;
  
  @NotNull
  WSEndpointReference readAsEPR(AddressingVersion paramAddressingVersion) throws XMLStreamException;
  
  void writeTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
  
  void writeTo(SOAPMessage paramSOAPMessage) throws SOAPException;
  
  void writeTo(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler) throws SAXException;
  
  @NotNull
  String getStringContent();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */