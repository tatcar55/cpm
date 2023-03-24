package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Truncated {
  FALSE("false"),
  TRUE("true");
  
  private final String value;
  
  Truncated(String paramString1) {
    this.value = paramString1;
  }
  
  public String value() {
    return this.value;
  }
  
  public static Truncated fromValue(String paramString) {
    for (Truncated truncated : values()) {
      if (truncated.value.equals(paramString))
        return truncated; 
    } 
    throw new IllegalArgumentException(paramString.toString());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Truncated.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */