package com.sun.xml.ws.wsdl.writer.document.soap12;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("header")
public interface Header extends TypedXmlWriter, BodyType {
  @XmlAttribute
  Header message(QName paramQName);
  
  @XmlElement
  HeaderFault headerFault();
  
  @XmlAttribute
  BodyType part(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\soap12\Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */