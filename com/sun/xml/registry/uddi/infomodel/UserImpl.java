package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PersonName;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;

public class UserImpl extends RegistryObjectImpl implements User, Serializable {
  Organization organization;
  
  PersonName personName;
  
  String type;
  
  ArrayList emailAddresses = new ArrayList();
  
  ArrayList postalAddresses = new ArrayList();
  
  ArrayList telephoneNumbers = new ArrayList();
  
  public Organization getOrganization() throws JAXRException {
    return this.organization;
  }
  
  public PersonName getPersonName() throws JAXRException {
    return this.personName;
  }
  
  public void setPersonName(PersonName paramPersonName) throws JAXRException {
    this.personName = paramPersonName;
    setIsModified(true);
  }
  
  public Collection getPostalAddresses() throws JAXRException {
    return (Collection)this.postalAddresses.clone();
  }
  
  public void setPostalAddresses(Collection paramCollection) throws JAXRException {
    this.postalAddresses.clear();
    if (paramCollection != null)
      this.postalAddresses.addAll(paramCollection); 
    setIsModified(true);
  }
  
  public Collection getTelephoneNumbers(String paramString) throws JAXRException {
    if (paramString == null)
      return (Collection)this.telephoneNumbers.clone(); 
    ArrayList<TelephoneNumber> arrayList = new ArrayList();
    for (TelephoneNumber telephoneNumber : this.telephoneNumbers) {
      if (telephoneNumber.getType().equals(paramString))
        arrayList.add(telephoneNumber); 
    } 
    return arrayList;
  }
  
  public void setTelephoneNumbers(Collection paramCollection) throws JAXRException {
    this.telephoneNumbers.clear();
    if (paramCollection != null)
      this.telephoneNumbers.addAll(paramCollection); 
    setIsModified(true);
  }
  
  public Collection getEmailAddresses() throws JAXRException {
    return (Collection)this.emailAddresses.clone();
  }
  
  public void setEmailAddresses(Collection paramCollection) throws JAXRException {
    this.emailAddresses.clear();
    if (paramCollection != null)
      this.emailAddresses.addAll(paramCollection); 
    setIsModified(true);
  }
  
  public String getType() throws JAXRException {
    return this.type;
  }
  
  public void setType(String paramString) throws JAXRException {
    this.type = paramString;
    setIsModified(true);
  }
  
  public URL getUrl() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setUrl(URL paramURL) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setOrganization(Organization paramOrganization) throws JAXRException {
    this.organization = paramOrganization;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\UserImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */