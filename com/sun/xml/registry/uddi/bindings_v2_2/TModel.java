package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tModel", propOrder = {"name", "description", "overviewDoc", "identifierBag", "categoryBag"})
public class TModel {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected Name name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected OverviewDoc overviewDoc;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected IdentifierBag identifierBag;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected CategoryBag categoryBag;
  
  @XmlAttribute
  protected String authorizedName;
  
  @XmlAttribute
  protected String operator;
  
  @XmlAttribute(required = true)
  protected String tModelKey;
  
  public Name getName() {
    return this.name;
  }
  
  public void setName(Name paramName) {
    this.name = paramName;
  }
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public OverviewDoc getOverviewDoc() {
    return this.overviewDoc;
  }
  
  public void setOverviewDoc(OverviewDoc paramOverviewDoc) {
    this.overviewDoc = paramOverviewDoc;
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
  
  public String getAuthorizedName() {
    return this.authorizedName;
  }
  
  public void setAuthorizedName(String paramString) {
    this.authorizedName = paramString;
  }
  
  public String getOperator() {
    return this.operator;
  }
  
  public void setOperator(String paramString) {
    this.operator = paramString;
  }
  
  public String getTModelKey() {
    return this.tModelKey;
  }
  
  public void setTModelKey(String paramString) {
    this.tModelKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\TModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */