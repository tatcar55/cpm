package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "find_relatedBusinesses")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "find_relatedBusinesses", propOrder = {"findQualifiers", "businessKey", "keyedReference"})
public class FindRelatedBusinesses {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected FindQualifiers findQualifiers;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String businessKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected KeyedReference keyedReference;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute
  protected Integer maxRows;
  
  public FindQualifiers getFindQualifiers() {
    return this.findQualifiers;
  }
  
  public void setFindQualifiers(FindQualifiers paramFindQualifiers) {
    this.findQualifiers = paramFindQualifiers;
  }
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
  }
  
  public KeyedReference getKeyedReference() {
    return this.keyedReference;
  }
  
  public void setKeyedReference(KeyedReference paramKeyedReference) {
    this.keyedReference = paramKeyedReference;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
  
  public Integer getMaxRows() {
    return this.maxRows;
  }
  
  public void setMaxRows(Integer paramInteger) {
    this.maxRows = paramInteger;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\FindRelatedBusinesses.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */