package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "find_tModel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "find_tModel", propOrder = {"findQualifiers", "name", "identifierBag", "categoryBag"})
public class FindTModel {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected FindQualifiers findQualifiers;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected Name name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected IdentifierBag identifierBag;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected CategoryBag categoryBag;
  
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
  
  public Name getName() {
    return this.name;
  }
  
  public void setName(Name paramName) {
    this.name = paramName;
  }
  
  public IdentifierBag getIdentifierBag() {
    return this.identifierBag;
  }
  
  public void setIdentifierBag(IdentifierBag paramIdentifierBag) {
    this.identifierBag = paramIdentifierBag;
  }
  
  public CategoryBag getCategoryBag() {
    return this.categoryBag;
  }
  
  public void setCategoryBag(CategoryBag paramCategoryBag) {
    this.categoryBag = paramCategoryBag;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\FindTModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */