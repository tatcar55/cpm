package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.RegistryPackage;

public class RegistryPackageImpl extends RegistryEntryImpl implements RegistryPackage, Serializable {
  public void addRegistryObject(RegistryObject paramRegistryObject) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void addRegistryObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void removeRegistryObject(RegistryObject paramRegistryObject) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void removeRegistryObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Set getRegistryObjects() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\RegistryPackageImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */