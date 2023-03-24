package com.sun.xml.bind.v2.schemagen.xmlschema;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;

public interface Wildcard extends Annotated, TypedXmlWriter {
  @XmlAttribute
  Wildcard processContents(String paramString);
  
  @XmlAttribute
  Wildcard namespace(String[] paramArrayOfString);
  
  @XmlAttribute
  Wildcard namespace(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\xmlschema\Wildcard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */