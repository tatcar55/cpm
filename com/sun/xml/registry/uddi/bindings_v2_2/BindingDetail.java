package com.sun.xml.registry.uddi.bindings_v2_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "bindingDetail", propOrder = {"bindingTemplate"})
public class BindingDetail {
  @XmlElement(namespace = "urn:uddi-org:api_v2")
  protected List<BindingTemplate> bindingTemplate;
  
  @XmlAttribute(required = true)
  protected String generic;
  
  @XmlAttribute(required = true)
  protected String operator;
  
  @XmlAttribute
  protected Truncated truncated;
  
  public List<BindingTemplate> getBindingTemplate() {
    if (this.bindingTemplate == null)
      this.bindingTemplate = new ArrayList<BindingTemplate>(); 
    return this.bindingTemplate;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\bindings_v2_2\BindingDetail.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */