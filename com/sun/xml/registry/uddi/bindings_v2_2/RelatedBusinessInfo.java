package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "relatedBusinessInfo", propOrder = {"businessKey", "name", "description", "sharedRelationships"})
public class RelatedBusinessInfo {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String businessKey;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Name> name;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<SharedRelationships> sharedRelationships;
  
  public String getBusinessKey() {
    return this.businessKey;
  }
  
  public void setBusinessKey(String paramString) {
    this.businessKey = paramString;
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
  
  public List<SharedRelationships> getSharedRelationships() {
    if (this.sharedRelationships == null)
      this.sharedRelationships = new ArrayList<SharedRelationships>(); 
    return this.sharedRelationships;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\RelatedBusinessInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */