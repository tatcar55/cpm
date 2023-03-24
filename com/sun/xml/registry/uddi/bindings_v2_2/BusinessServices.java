package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessServices", propOrder = {"businessService"})
public class BusinessServices {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessService> businessService;
  
  public List<BusinessService> getBusinessService() {
    if (this.businessService == null)
      this.businessService = new ArrayList<BusinessService>(); 
    return this.businessService;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BusinessServices.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */