package com.sun.xml.registry.uddi;

import javax.xml.registry.CapabilityProfile;
import javax.xml.registry.JAXRException;

public class CapabilityProfileImpl implements CapabilityProfile {
  private static CapabilityProfileImpl instance = null;
  
  public static CapabilityProfileImpl getInstance() {
    if (instance == null)
      instance = new CapabilityProfileImpl(); 
    return instance;
  }
  
  public int getCapabilityLevel() throws JAXRException {
    return 0;
  }
  
  public String getVersion() throws JAXRException {
    return "JAXR Version 1.0";
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\CapabilityProfileImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */