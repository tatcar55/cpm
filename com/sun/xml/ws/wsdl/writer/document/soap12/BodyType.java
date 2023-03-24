package com.sun.xml.ws.wsdl.writer.document.soap12;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;

public interface BodyType extends TypedXmlWriter {
  @XmlAttribute
  BodyType encodingStyle(String paramString);
  
  @XmlAttribute
  BodyType namespace(String paramString);
  
  @XmlAttribute
  BodyType use(String paramString);
  
  @XmlAttribute
  BodyType parts(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\soap12\BodyType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */