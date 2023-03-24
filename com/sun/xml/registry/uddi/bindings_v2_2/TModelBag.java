package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tModelBag", propOrder = {"tModelKey"})
public class TModelBag {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<String> tModelKey;
  
  public List<String> getTModelKey() {
    if (this.tModelKey == null)
      this.tModelKey = new ArrayList<String>(); 
    return this.tModelKey;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\TModelBag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */