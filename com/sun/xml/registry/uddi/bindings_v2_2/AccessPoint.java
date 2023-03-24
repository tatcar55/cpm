package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "accessPoint", propOrder = {"value"})
public class AccessPoint {
  @XmlValue
  protected String value;
  
  @XmlAttribute(name = "URLType", required = true)
  protected URLType urlType;
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
  
  public URLType getURLType() {
    return this.urlType;
  }
  
  public void setURLType(URLType paramURLType) {
    this.urlType = paramURLType;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\AccessPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */