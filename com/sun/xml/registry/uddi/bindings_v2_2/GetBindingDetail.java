package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "get_bindingDetail")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "get_bindingDetail", propOrder = {"bindingKey"})
public class GetBindingDetail {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<String> bindingKey;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  public List<String> getBindingKey() {
    if (this.bindingKey == null)
      this.bindingKey = new ArrayList<String>(); 
    return this.bindingKey;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\GetBindingDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */