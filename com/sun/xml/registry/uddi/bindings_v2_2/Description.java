package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "description", propOrder = {"value"})
public class Description {
  @XmlValue
  protected String value;
  
  @XmlAttribute(namespace = "http://www.w3.org/XML/1998/namespace")
  protected String lang;
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
  
  public String getLang() {
    return this.lang;
  }
  
  public void setLang(String paramString) {
    this.lang = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Description.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */