package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "businessInfos", propOrder = {"businessInfo"})
public class BusinessInfos {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BusinessInfo> businessInfo;
  
  public List<BusinessInfo> getBusinessInfo() {
    if (this.businessInfo == null)
      this.businessInfo = new ArrayList<BusinessInfo>(); 
    return this.businessInfo;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BusinessInfos.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */