package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("documentation")
public interface Documentation extends TypedXmlWriter {
  @XmlAttribute
  Documentation source(String paramString);
  
  @XmlAttribute(ns = "http://www.w3.org/XML/1998/namespace")
  Documentation lang(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\Documentation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */