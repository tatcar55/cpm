package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceDetail", propOrder = {"businessService"})
public class ServiceDetail {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessService> businessService;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  @XmlAttribute
  protected Truncated truncated;
  
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\ServiceDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */