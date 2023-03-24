package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum URLType {
  FAX("fax"),
  FTP("ftp"),
  HTTP("http"),
  HTTPS("https"),
  MAILTO("mailto"),
  OTHER("other"),
  PHONE("phone");
  
  private final String value;
  
  URLType(String paramString1) {
    this.value = paramString1;
  }
  
  public String value() {
    return this.value;
  }
  
  public static URLType fromValue(String paramString) {
    for (URLType uRLType : values()) {
      if (uRLType.value.equals(paramString))
        return uRLType; 
    } 
    throw new IllegalArgumentException(paramString.toString());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\URLType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */