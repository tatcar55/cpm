package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessService", propOrder = {"name", "description", "bindingTemplates", "categoryBag"})
public class BusinessService {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Name> name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected BindingTemplates bindingTemplates;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected CategoryBag categoryBag;
  
  @XmlAttribute
  protected String businessKey;
  
  @XmlAttribute(required = true)
  protected String serviceKey;
  
  public List<Name> getName() {
    if (this.name == null)
      this.name = new ArrayList<Name>(); 
    return this.name;
  }
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public BindingTemplates getBindingTemplates() {
    return this.bindingTemplates;
  }
  
  public void setBindingTemplates(BindingTemplates paramBindingTemplates) {
    this.bindingTemplates = paramBindingTemplates;
  }
  
  public CategoryBag getCategoryBag() {
    return this.categoryBag;
  }
  
  public void setCategoryBag(CategoryBag paramCategoryBag) {
    this.categoryBag = paramCategoryBag;
  }
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
  }
  
  public String getServiceKey() {
    return this.serviceKey;
  }
  
  public void setServiceKey(String paramString) {
    this.serviceKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BusinessService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */