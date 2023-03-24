package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("attribute")
public interface LocalAttribute extends Annotated, AttributeType, FixedOrDefault, TypedXmlWriter {
  @XmlAttribute
  LocalAttribute form(String paramString);
  
  @XmlAttribute
  LocalAttribute name(String paramString);
  
  @XmlAttribute
  LocalAttribute ref(QName paramQName);
  
  @XmlAttribute
  LocalAttribute use(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\LocalAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */