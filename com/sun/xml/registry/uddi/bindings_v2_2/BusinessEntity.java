package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessEntity", propOrder = {"discoveryURLs", "name", "description", "contacts", "businessServices", "identifierBag", "categoryBag"})
public class BusinessEntity {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected DiscoveryURLs discoveryURLs;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Name> name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected Contacts contacts;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected BusinessServices businessServices;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected IdentifierBag identifierBag;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected CategoryBag categoryBag;
  
  @XmlAttribute
  protected String authorizedName;
  
  @XmlAttribute(required = true)
  protected String businessKey;
  
  @XmlAttribute
  protected String operator;
  
  public DiscoveryURLs getDiscoveryURLs() {
    return this.discoveryURLs;
  }
  
  public void setDiscoveryURLs(DiscoveryURLs paramDiscoveryURLs) {
    this.discoveryURLs = paramDiscoveryURLs;
  }
  
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
  
  public Contacts getContacts() {
    return this.contacts;
  }
  
  public void setContacts(Contacts paramContacts) {
    this.contacts = paramContacts;
  }
  
  public BusinessServices getBusinessServices() {
    return this.businessServices;
  }
  
  public void setBusinessServices(BusinessServices paramBusinessServices) {
    this.businessServices = paramBusinessServices;
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
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
  }
  
  public String getOperator() {
    return this.operator;
  }
  
  public void setOperator(String paramString) {
    this.operator = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BusinessEntity.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */