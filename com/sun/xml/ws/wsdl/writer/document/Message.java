package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("message")
public interface Message extends TypedXmlWriter, Documented {
  @XmlAttribute
  Message name(String paramString);
  
  @XmlElement
  Part part();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */