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
@XmlType(name = "", propOrder = {"jaxrClassificationScheme", "jaxrConcept", "namepattern"})
@XmlRootElement(name = "Command")
public class Command {
  @XmlElement(name = "JAXRClassificationScheme")
  protected List<JAXRClassificationScheme> jaxrClassificationScheme;
  
  @XmlElement(name = "JAXRConcept")
  protected List<JAXRConcept> jaxrConcept;
  
  protected List<Namepattern> namepattern;
  
  @XmlAttribute(required = true)
  protected String commandname;
  
  @XmlAttribute
  protected String path;
  
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
  
  public List<Namepattern> getNamepattern() {
    if (this.namepattern == null)
      this.namepattern = new ArrayList<Namepattern>(); 
    return this.namepattern;
  }
  
  public String getCommandname() {
    return this.commandname;
  }
  
  public void setCommandname(String paramString) {
    this.commandname = paramString;
  }
  
  public String getPath() {
    return this.path;
  }
  
  public void setPath(String paramString) {
    this.path = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\Command.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */