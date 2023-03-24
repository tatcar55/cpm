package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.soap.SOAPOperation;
import com.sun.xml.ws.wsdl.writer.document.soap12.SOAPOperation;

public interface BindingOperationType extends TypedXmlWriter, StartWithExtensionsType {
  @XmlAttribute
  BindingOperationType name(String paramString);
  
  @XmlElement(value = "operation", ns = "http://schemas.xmlsoap.org/wsdl/soap/")
  SOAPOperation soapOperation();
  
  @XmlElement(value = "operation", ns = "http://schemas.xmlsoap.org/wsdl/soap12/")
  SOAPOperation soap12Operation();
  
  @XmlElement
  Fault fault();
  
  @XmlElement
  StartWithExtensionsType output();
  
  @XmlElement
  StartWithExtensionsType input();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\BindingOperationType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */