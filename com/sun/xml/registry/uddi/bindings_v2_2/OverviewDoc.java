package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "overviewDoc", propOrder = {"description", "overviewURL"})
public class OverviewDoc {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<Description> description;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String overviewURL;
  
  public List<Description> getDescription() {
    if (this.description == null)
      this.description = new ArrayList<Description>(); 
    return this.description;
  }
  
  public String getOverviewURL() {
    return this.overviewURL;
  }
  
  public void setOverviewURL(String paramString) {
    this.overviewURL = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\OverviewDoc.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */