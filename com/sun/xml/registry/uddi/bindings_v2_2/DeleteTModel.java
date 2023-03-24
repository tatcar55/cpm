package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "delete_tModel")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "delete_tModel", propOrder = {"authInfo", "tModelKey"})
public class DeleteTModel {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected String authInfo;
  
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<String> tModelKey;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  public String getAuthInfo() {
    return this.authInfo;
  }
  
  public void setAuthInfo(String paramString) {
    this.authInfo = paramString;
  }
  
  public List<String> getTModelKey() {
    if (this.tModelKey == null)
      this.tModelKey = new ArrayList<String>(); 
    return this.tModelKey;
  }
  
  public String getGeneric() {
    return this.generic;
  }
  
  public void setGeneric(String paramString) {
    this.generic = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\DeleteTModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */