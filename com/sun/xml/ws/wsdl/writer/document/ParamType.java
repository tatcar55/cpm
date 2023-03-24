package com.sun.xml.ws.wsdl.writer.document;

import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import javax.xml.namespace.QName;

public interface ParamType extends TypedXmlWriter, Documented {
  @XmlAttribute
  ParamType message(QName paramQName);
  
  @XmlAttribute
  ParamType name(String paramString);
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\writer\document\ParamType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */