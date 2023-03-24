package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "save_service")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "save_service", propOrder = {"authInfo", "businessService"})
public class SaveService {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String authInfo;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessService> businessService;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  public String getAuthInfo() {
    return this.authInfo;
  }
  
  public void setAuthInfo(String paramString) {
    this.authInfo = paramString;
  }
  
  public List<BusinessService> getBusinessService() {
    if (this.businessService == null)
      this.businessService = new ArrayList<BusinessService>(); 
    return this.businessService;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\SaveService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */