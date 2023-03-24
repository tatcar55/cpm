package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bindingTemplate", propOrder = {"description", "accessPoint", "hostingRedirector", "tModelInstanceDetails"})
public class BindingTemplate {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected AccessPoint accessPoint;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected HostingRedirector hostingRedirector;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected TModelInstanceDetails tModelInstanceDetails;
  
  @XmlAttribute(required = true)
  protected String bindingKey;
  
  @XmlAttribute
  protected String serviceKey;
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public AccessPoint getAccessPoint() {
    return this.accessPoint;
  }
  
  public void setAccessPoint(AccessPoint paramAccessPoint) {
    this.accessPoint = paramAccessPoint;
  }
  
  public HostingRedirector getHostingRedirector() {
    return this.hostingRedirector;
  }
  
  public void setHostingRedirector(HostingRedirector paramHostingRedirector) {
    this.hostingRedirector = paramHostingRedirector;
  }
  
  public TModelInstanceDetails getTModelInstanceDetails() {
    return this.tModelInstanceDetails;
  }
  
  public void setTModelInstanceDetails(TModelInstanceDetails paramTModelInstanceDetails) {
    this.tModelInstanceDetails = paramTModelInstanceDetails;
  }
  
  public String getBindingKey() {
    return this.bindingKey;
  }
  
  public void setBindingKey(String paramString) {
    this.bindingKey = paramString;
  }
  
  public String getServiceKey() {
    return this.serviceKey;
  }
  
  public void setServiceKey(String paramString) {
    this.serviceKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BindingTemplate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */