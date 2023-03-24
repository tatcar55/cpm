package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.ws.wsdl.writer.document.xsd.Schema;

@XmlElement("types")
public interface Types extends TypedXmlWriter, Documented {
  @XmlElement(value = "schema", ns = "http://www.w3.org/2001/XMLSchema")
  Schema schema();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\Types.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */