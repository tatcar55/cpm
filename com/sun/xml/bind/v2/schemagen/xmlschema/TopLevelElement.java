package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import javax.xml.namespace.QName;

@XmlElement("element")
public interface TopLevelElement extends Element, TypedXmlWriter {
  @XmlAttribute("final")
  TopLevelElement _final(String[] paramArrayOfString);
  
  @XmlAttribute("final")
  TopLevelElement _final(String paramString);
  
  @XmlAttribute("abstract")
  TopLevelElement _abstract(boolean paramBoolean);
  
  @XmlAttribute
  TopLevelElement substitutionGroup(QName paramQName);
  
  @XmlAttribute
  TopLevelElement name(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\TopLevelElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */