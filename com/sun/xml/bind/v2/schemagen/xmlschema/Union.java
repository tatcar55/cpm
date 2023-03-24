package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("union")
public interface Union extends Annotated, SimpleTypeHost, TypedXmlWriter {
  @XmlAttribute
  Union memberTypes(QName[] paramArrayOfQName);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\Union.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */