package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "keysOwned", propOrder = {"fromKey", "toKey"})
public class KeysOwned {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String fromKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String toKey;
  
  public String getFromKey() {
    return this.fromKey;
  }
  
  public void setFromKey(String paramString) {
    this.fromKey = paramString;
  }
  
  public String getToKey() {
    return this.toKey;
  }
  
  public void setToKey(String paramString) {
    this.toKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\KeysOwned.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */