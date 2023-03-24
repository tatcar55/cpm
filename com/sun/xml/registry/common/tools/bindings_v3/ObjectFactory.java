package com.sun.xml.registry.common.tools.bindings_v3;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
  public Result createResult() {
    return new Result();
  }
  
  public PredefinedConcepts createPredefinedConcepts() {
    return new PredefinedConcepts();
  }
  
  public Command createCommand() {
    return new Command();
  }
  
  public JAXRConcept createJAXRConcept() {
    return new JAXRConcept();
  }
  
  public Namepattern createNamepattern() {
    return new Namepattern();
  }
  
  public JAXRClassificationScheme createJAXRClassificationScheme() {
    return new JAXRClassificationScheme();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\tools\bindings_v3\ObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */