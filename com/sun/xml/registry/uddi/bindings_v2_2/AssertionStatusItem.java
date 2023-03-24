package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "assertionStatusItem", propOrder = {"fromKey", "toKey", "keyedReference", "keysOwned"})
public class AssertionStatusItem {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String fromKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String toKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected KeyedReference keyedReference;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected KeysOwned keysOwned;
  
  @XmlAttribute(required = true)
  protected String completionStatus;
  
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
  
  public KeyedReference getKeyedReference() {
    return this.keyedReference;
  }
  
  public void setKeyedReference(KeyedReference paramKeyedReference) {
    this.keyedReference = paramKeyedReference;
  }
  
  public KeysOwned getKeysOwned() {
    return this.keysOwned;
  }
  
  public void setKeysOwned(KeysOwned paramKeysOwned) {
    this.keysOwned = paramKeysOwned;
  }
  
  public String getCompletionStatus() {
    return this.completionStatus;
  }
  
  public void setCompletionStatus(String paramString) {
    this.completionStatus = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\AssertionStatusItem.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */