package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errInfo", propOrder = {"value"})
public class ErrInfo {
  @XmlValue
  protected String value;
  
  @XmlAttribute(required = true)
  protected String errCode;
  
  public String getValue() {
    return this.value;
  }
  
  public void setValue(String paramString) {
    this.value = paramString;
  }
  
  public String getErrCode() {
    return this.errCode;
  }
  
  public void setErrCode(String paramString) {
    this.errCode = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\ErrInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */