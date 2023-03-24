package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.Date;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryEntry;
import javax.xml.registry.infomodel.Versionable;

public class RegistryEntryImpl extends RegistryObjectImpl implements RegistryEntry, Versionable, Serializable {
  public RegistryEntryImpl() {}
  
  public RegistryEntryImpl(Key paramKey, String paramString1, String paramString2) {
    super(paramKey, paramString1, paramString2);
  }
  
  public RegistryEntryImpl(Key paramKey) {
    super(paramKey);
  }
  
  public int getStatus() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public int getStability() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setStability(int paramInt) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Date getExpiration() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setExpiration(Date paramDate) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public int getMajorVersion() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setMajorVersion(int paramInt) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public int getMinorVersion() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setMinorVersion(int paramInt) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getUserVersion() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setUserVersion(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\RegistryEntryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */