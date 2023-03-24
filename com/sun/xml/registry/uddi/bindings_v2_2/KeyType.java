package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum KeyType {
  BINDING_KEY("bindingKey"),
  BUSINESS_KEY("businessKey"),
  SERVICE_KEY("serviceKey"),
  T_MODEL_KEY("tModelKey");
  
  private final String value;
  
  KeyType(String paramString1) {
    this.value = paramString1;
  }
  
  public String value() {
    return this.value;
  }
  
  public static KeyType fromValue(String paramString) {
    for (KeyType keyType : values()) {
      if (keyType.value.equals(paramString))
        return keyType; 
    } 
    throw new IllegalArgumentException(paramString.toString());
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\KeyType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */