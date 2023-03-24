package com.sun.xml.bind.v2.schemagen.episode;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;

public interface SchemaBindings extends TypedXmlWriter {
  @XmlAttribute
  void map(boolean paramBoolean);
  
  @XmlElement("package")
  Package _package();
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\episode\SchemaBindings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */