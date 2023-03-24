package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tModelInstanceInfo", propOrder = {"description", "instanceDetails"})
public class TModelInstanceInfo {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected InstanceDetails instanceDetails;
  
  @XmlAttribute(required = true)
  protected String tModelKey;
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public InstanceDetails getInstanceDetails() {
    return this.instanceDetails;
  }
  
  public void setInstanceDetails(InstanceDetails paramInstanceDetails) {
    this.instanceDetails = paramInstanceDetails;
  }
  
  public String getTModelKey() {
    return this.tModelKey;
  }
  
  public void setTModelKey(String paramString) {
    this.tModelKey = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\TModelInstanceInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */