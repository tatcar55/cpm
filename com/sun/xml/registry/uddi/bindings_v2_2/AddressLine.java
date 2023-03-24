package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addressLine", propOrder = {"value"})
public class AddressLine {
  @XmlValue
  protected String value;
  
  @XmlAttribute
  protected String keyName;
  
  @XmlAttribute
  protected String keyValue;
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
  
  public String getKeyName() {
    return this.keyName;
  }
  
  public void setKeyName(String paramString) {
    this.keyName = paramString;
  }
  
  public String getKeyValue() {
    return this.keyValue;
  }
  
  public void setKeyValue(String paramString) {
    this.keyValue = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\AddressLine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */