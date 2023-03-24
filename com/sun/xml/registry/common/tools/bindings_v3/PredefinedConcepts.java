package com.sun.xml.registry.common.tools.bindings_v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"jaxrClassificationScheme"})
@XmlRootElement(name = "PredefinedConcepts")
public class PredefinedConcepts {
  @XmlElement(name = "JAXRClassificationScheme")
  protected List<JAXRClassificationScheme> jaxrClassificationScheme;
  
  public List<JAXRClassificationScheme> getJAXRClassificationScheme() {
    if (this.jaxrClassificationScheme == null)
      this.jaxrClassificationScheme = new ArrayList<JAXRClassificationScheme>(); 
    return this.jaxrClassificationScheme;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\PredefinedConcepts.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */