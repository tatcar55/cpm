package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

@XmlElement("import")
public interface Import extends Annotated, TypedXmlWriter {
  @XmlAttribute
  Import namespace(String paramString);
  
  @XmlAttribute
  Import schemaLocation(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\Import.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */