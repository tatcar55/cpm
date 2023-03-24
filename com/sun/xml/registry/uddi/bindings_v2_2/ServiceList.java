package com.sun.xml.registry.uddi.bindings_v2_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceList", propOrder = {"serviceInfos"})
public class ServiceList {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected ServiceInfos serviceInfos;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  @XmlAttribute
  protected Truncated truncated;
  
  public ServiceInfos getServiceInfos() {
    return this.serviceInfos;
  }
  
  public void setServiceInfos(ServiceInfos paramServiceInfos) {
    this.serviceInfos = paramServiceInfos;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\ServiceList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */