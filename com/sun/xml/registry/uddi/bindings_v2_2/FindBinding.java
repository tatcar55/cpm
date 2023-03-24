package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "find_binding")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "find_binding", propOrder = {"findQualifiers", "tModelBag"})
public class FindBinding {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected FindQualifiers findQualifiers;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected TModelBag tModelBag;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute
  protected Integer maxRows;
  
  @XmlAttribute(required = true)
  protected String serviceKey;
  
  public FindQualifiers getFindQualifiers() {
    return this.findQualifiers;
  }
  
  public void setFindQualifiers(FindQualifiers paramFindQualifiers) {
    this.findQualifiers = paramFindQualifiers;
  }
  
  public TModelBag getTModelBag() {
    return this.tModelBag;
  }
  
  public void setTModelBag(TModelBag paramTModelBag) {
    this.tModelBag = paramTModelBag;
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
  
  public String getServiceKey() {
    return this.serviceKey;
  }
  
  public void setServiceKey(String paramString) {
    this.serviceKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\FindBinding.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */