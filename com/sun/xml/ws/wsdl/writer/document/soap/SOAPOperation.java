package com.sun.xml.ws.wsdl.writer.document.soap;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("operation")
public interface SOAPOperation extends TypedXmlWriter {
  @XmlAttribute
  SOAPOperation soapAction(String paramString);
  
  @XmlAttribute
  SOAPOperation style(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\soap\SOAPOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */