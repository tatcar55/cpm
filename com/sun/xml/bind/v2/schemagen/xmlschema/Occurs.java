package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;

public interface Occurs extends TypedXmlWriter {
  @XmlAttribute
  Occurs minOccurs(int paramInt);
  
  @XmlAttribute
  Occurs maxOccurs(String paramString);
  
  @XmlAttribute
  Occurs maxOccurs(int paramInt);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\Occurs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */