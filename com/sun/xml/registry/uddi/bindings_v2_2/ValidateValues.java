package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "validate_values")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validate_values", propOrder = {"businessEntity", "businessService", "tModel"})
public class ValidateValues {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessEntity> businessEntity;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessService> businessService;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<TModel> tModel;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  public List<BusinessEntity> getBusinessEntity() {
    if (this.businessEntity == null)
      this.businessEntity = new ArrayList<BusinessEntity>(); 
    return this.businessEntity;
  }
  
  public List<BusinessService> getBusinessService() {
    if (this.businessService == null)
      this.businessService = new ArrayList<BusinessService>(); 
    return this.businessService;
  }
  
  public List<TModel> getTModel() {
    if (this.tModel == null)
      this.tModel = new ArrayList<TModel>(); 
    return this.tModel;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\ValidateValues.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */