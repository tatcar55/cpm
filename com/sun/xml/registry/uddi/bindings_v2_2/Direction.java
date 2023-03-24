package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Direction {
  FROM_KEY("fromKey"),
  TO_KEY("toKey");
  
  private final String value;
  
  Direction(String paramString1) {
    this.value = paramString1;
  }
  
  public String value() {
    return this.value;
  }
  
  public static Direction fromValue(String paramString) {
    for (Direction direction : values()) {
      if (direction.value.equals(paramString))
        return direction; 
    } 
    throw new IllegalArgumentException(paramString.toString());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\Direction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */