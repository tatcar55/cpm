package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("port")
public interface Port extends TypedXmlWriter, Documented {
  @XmlAttribute
  Port name(String paramString);
  
  @XmlAttribute
  Port arrayType(String paramString);
  
  @XmlAttribute
  Port binding(QName paramQName);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\Port.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */