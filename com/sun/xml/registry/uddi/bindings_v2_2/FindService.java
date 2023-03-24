package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "find_service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "find_service", propOrder = {"findQualifiers", "name", "categoryBag", "tModelBag"})
public class FindService {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected FindQualifiers findQualifiers;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Name> name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected CategoryBag categoryBag;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected TModelBag tModelBag;
  
  @XmlAttribute
  protected String businessKey;
  
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
  
  public List<Name> getName() {
    if (this.name == null)
      this.name = new ArrayList<Name>(); 
    return this.name;
  }
  
  public CategoryBag getCategoryBag() {
    return this.categoryBag;
  }
  
  public void setCategoryBag(CategoryBag paramCategoryBag) {
    this.categoryBag = paramCategoryBag;
  }
  
  public TModelBag getTModelBag() {
    return this.tModelBag;
  }
  
  public void setTModelBag(TModelBag paramTModelBag) {
    this.tModelBag = paramTModelBag;
  }
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\FindService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */