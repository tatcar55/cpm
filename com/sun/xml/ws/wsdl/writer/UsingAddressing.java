package com.sun.xml.ws.wsdl.writer;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.StartWithExtensionsType;

@XmlElement(value = "http://www.w3.org/2006/05/addressing/wsdl", ns = "UsingAddressing")
public interface UsingAddressing extends TypedXmlWriter, StartWithExtensionsType {
  @XmlAttribute(value = "required", ns = "http://schemas.xmlsoap.org/wsdl/")
  void required(boolean paramBoolean);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\UsingAddressing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */