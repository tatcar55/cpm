package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.PersonName;

public class PersonNameImpl implements PersonName, Serializable {
  private String fullName = new String();
  
  public PersonNameImpl() {}
  
  public PersonNameImpl(String paramString) {}
  
  public String getFullName() throws JAXRException {
    return this.fullName;
  }
  
  public void setFullName(String paramString) throws JAXRException {
    this.fullName = paramString;
  }
  
  public String getFirstName() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getLastName() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getMiddleName() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setFirstName(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setLastName(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setMiddleName(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\PersonNameImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */