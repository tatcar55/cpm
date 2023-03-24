package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.TelephoneNumber;

public class TelephoneNumberImpl implements TelephoneNumber, Serializable {
  String number = new String();
  
  String type = new String();
  
  public String getNumber() throws JAXRException {
    return this.number;
  }
  
  public void setNumber(String paramString) throws JAXRException {
    this.number = paramString;
  }
  
  public String getType() throws JAXRException {
    return this.type;
  }
  
  public void setType(String paramString) throws JAXRException {
    this.type = paramString;
  }
  
  public String getCountryCode() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setCountryCode(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getAreaCode() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setAreaCode(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getExtension() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setExtension(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public String getUrl() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setUrl(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\TelephoneNumberImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */