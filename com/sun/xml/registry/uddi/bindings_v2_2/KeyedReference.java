package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "keyedReference")
public class KeyedReference {
  @XmlAttribute
  protected String keyName;
  
  @XmlAttribute(required = true)
  protected String keyValue;
  
  @XmlAttribute
  protected String tModelKey;
  
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
  
  public String getTModelKey() {
    return this.tModelKey;
  }
  
  public void setTModelKey(String paramString) {
    this.tModelKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\KeyedReference.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */