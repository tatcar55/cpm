package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.EmailAddress;

public class EmailAddressImpl implements EmailAddress, Serializable {
  String address;
  
  String type;
  
  public EmailAddressImpl() {}
  
  public EmailAddressImpl(String paramString) {
    this.address = paramString;
  }
  
  public EmailAddressImpl(String paramString1, String paramString2) {
    this(paramString1);
    this.type = paramString2;
  }
  
  public String getAddress() throws JAXRException {
    return this.address;
  }
  
  public void setAddress(String paramString) throws JAXRException {
    this.address = paramString;
  }
  
  public String getType() throws JAXRException {
    return this.type;
  }
  
  public void setType(String paramString) throws JAXRException {
    this.type = paramString;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\EmailAddressImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */