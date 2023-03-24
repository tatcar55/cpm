package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Versionable;

public class VersionableImpl implements Versionable, Serializable {
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\VersionableImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */