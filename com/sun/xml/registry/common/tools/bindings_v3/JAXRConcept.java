package com.sun.xml.registry.common.tools.bindings_v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"jaxrConcept"})
@XmlRootElement(name = "JAXRConcept")
public class JAXRConcept {
  @XmlElement(name = "JAXRConcept")
  protected List<JAXRConcept> jaxrConcept;
  
  @XmlAttribute
  protected String code;
  
  @XmlAttribute(required = true)
  protected String id;
  
  @XmlAttribute(required = true)
  protected String name;
  
  @XmlAttribute(required = true)
  protected String parent;
  
  public List<JAXRConcept> getJAXRConcept() {
    if (this.jaxrConcept == null)
      this.jaxrConcept = new ArrayList<JAXRConcept>(); 
    return this.jaxrConcept;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setCode(String paramString) {
    this.code = paramString;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String paramString) {
    this.id = paramString;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String paramString) {
    this.name = paramString;
  }
  
  public String getParent() {
    return this.parent;
  }
  
  public void setParent(String paramString) {
    this.parent = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\JAXRConcept.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */