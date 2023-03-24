package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceInfos", propOrder = {"serviceInfo"})
public class ServiceInfos {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<ServiceInfo> serviceInfo;
  
  public List<ServiceInfo> getServiceInfo() {
    if (this.serviceInfo == null)
      this.serviceInfo = new ArrayList<ServiceInfo>(); 
    return this.serviceInfo;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\ServiceInfos.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */