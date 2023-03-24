package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tModelInfo", propOrder = {"name"})
public class TModelInfo {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected Name name;
  
  @XmlAttribute(required = true)
  protected String tModelKey;
  
  public Name getName() {
    return this.name;
  }
  
  public void setName(Name paramName) {
    this.name = paramName;
  }
  
  public String getTModelKey() {
    return this.tModelKey;
  }
  
  public void setTModelKey(String paramString) {
    this.tModelKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\TModelInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */