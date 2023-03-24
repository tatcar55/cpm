package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instanceDetails", propOrder = {"description", "overviewDoc", "instanceParms"})
public class InstanceDetails {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected OverviewDoc overviewDoc;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String instanceParms;
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public OverviewDoc getOverviewDoc() {
    return this.overviewDoc;
  }
  
  public void setOverviewDoc(OverviewDoc paramOverviewDoc) {
    this.overviewDoc = paramOverviewDoc;
  }
  
  public String getInstanceParms() {
    return this.instanceParms;
  }
  
  public void setInstanceParms(String paramString) {
    this.instanceParms = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\InstanceDetails.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */