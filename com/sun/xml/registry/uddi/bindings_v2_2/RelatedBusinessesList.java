package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relatedBusinessesList", propOrder = {"businessKey", "relatedBusinessInfos"})
public class RelatedBusinessesList {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String businessKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected RelatedBusinessInfos relatedBusinessInfos;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  @XmlAttribute
  protected Truncated truncated;
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
  }
  
  public RelatedBusinessInfos getRelatedBusinessInfos() {
    return this.relatedBusinessInfos;
  }
  
  public void setRelatedBusinessInfos(RelatedBusinessInfos paramRelatedBusinessInfos) {
    this.relatedBusinessInfos = paramRelatedBusinessInfos;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
  
  public String getOperator() {
    return this.operator;
  }
  
  public void setOperator(String paramString) {
    this.operator = paramString;
  }
  
  public Truncated getTruncated() {
    return this.truncated;
  }
  
  public void setTruncated(Truncated paramTruncated) {
    this.truncated = paramTruncated;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\RelatedBusinessesList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */