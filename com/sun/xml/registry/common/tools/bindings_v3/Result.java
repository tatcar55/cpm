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
@XmlType(name = "", propOrder = {"jaxrClassificationScheme", "jaxrConcept"})
@XmlRootElement(name = "Result")
public class Result {
  @XmlElement(name = "JAXRClassificationScheme")
  protected List<JAXRClassificationScheme> jaxrClassificationScheme;
  
  @XmlElement(name = "JAXRConcept")
  protected List<JAXRConcept> jaxrConcept;
  
  @XmlAttribute(required = true)
  protected String commandname;
  
  @XmlAttribute
  protected String error;
  
  @XmlAttribute(required = true)
  protected String status;
  
  public List<JAXRClassificationScheme> getJAXRClassificationScheme() {
    if (this.jaxrClassificationScheme == null)
      this.jaxrClassificationScheme = new ArrayList<JAXRClassificationScheme>(); 
    return this.jaxrClassificationScheme;
  }
  
  public List<JAXRConcept> getJAXRConcept() {
    if (this.jaxrConcept == null)
      this.jaxrConcept = new ArrayList<JAXRConcept>(); 
    return this.jaxrConcept;
  }
  
  public String getCommandname() {
    return this.commandname;
  }
  
  public void setCommandname(String paramString) {
    this.commandname = paramString;
  }
  
  public String getError() {
    return this.error;
  }
  
  public void setError(String paramString) {
    this.error = paramString;
  }
  
  public String getStatus() {
    return this.status;
  }
  
  public void setStatus(String paramString) {
    this.status = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\Result.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */