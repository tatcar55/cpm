package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("definitions")
public interface Definitions extends TypedXmlWriter, Documented {
  @XmlAttribute
  Definitions name(String paramString);
  
  @XmlAttribute
  Definitions targetNamespace(String paramString);
  
  @XmlElement
  Service service();
  
  @XmlElement
  Binding binding();
  
  @XmlElement
  PortType portType();
  
  @XmlElement
  Message message();
  
  @XmlElement
  Types types();
  
  @XmlElement("import")
  Import _import();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\Definitions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */