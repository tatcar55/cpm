package com.sun.xml.ws.wsdl.writer.document.http;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("operation")
public interface Operation extends TypedXmlWriter {
  @XmlAttribute
  Operation location(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\http\Operation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */