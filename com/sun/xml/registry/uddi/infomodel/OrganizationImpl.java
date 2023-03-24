package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PostalAddress;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;

public class OrganizationImpl extends RegistryObjectImpl implements Organization, Serializable {
  private ArrayList services = new ArrayList();
  
  private ArrayList telephoneNumbers = new ArrayList();
  
  private ArrayList users = new ArrayList();
  
  private User primaryContact;
  
  public OrganizationImpl() {}
  
  public OrganizationImpl(Key paramKey) {
    this();
    this.key = paramKey;
  }
  
  public OrganizationImpl(String paramString) throws JAXRException {
    this();
    this.name = new InternationalStringImpl(paramString);
  }
  
  public OrganizationImpl(Key paramKey, String paramString1, String paramString2) throws JAXRException {
    this(paramKey);
    this.description = new InternationalStringImpl(paramString1);
    this.name = new InternationalStringImpl(paramString2);
  }
  
  public User getPrimaryContact() throws JAXRException {
    getObject();
    return this.primaryContact;
  }
  
  public void setPrimaryContact(User paramUser) throws JAXRException {
    this.primaryContact = paramUser;
    getObject();
    if (!this.users.contains(paramUser))
      this.users.add(paramUser); 
    setIsModified(true);
  }
  
  public void addUser(User paramUser) throws JAXRException {
    if (paramUser != null) {
      getObject();
      this.users.add(paramUser);
      setIsModified(true);
    } 
  }
  
  public void addUsers(Collection paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.users.addAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public void removeUser(User paramUser) throws JAXRException {
    if (paramUser != null) {
      getObject();
      this.users.remove(paramUser);
      setIsModified(true);
    } 
  }
  
  public void removeUsers(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.users.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getUsers() throws JAXRException {
    if (this.users.size() == 0)
      getObject(); 
    return (Collection)this.users.clone();
  }
  
  public Collection getTelephoneNumbers(String paramString) throws JAXRException {
    if (this.telephoneNumbers.size() == 0)
      getObject(); 
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
    getObject();
    this.telephoneNumbers.clear();
    if (paramCollection != null)
      this.telephoneNumbers.addAll(paramCollection); 
    setIsModified(true);
  }
  
  public void addService(Service paramService) throws JAXRException {
    if (paramService != null) {
      getObject();
      ((ServiceImpl)paramService).setProvidingOrganization(this);
      this.services.add(paramService);
      setIsModified(true);
    } 
  }
  
  public void addServices(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    getObject();
    Iterator<ServiceImpl> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext()) {
        ServiceImpl serviceImpl = iterator.next();
        serviceImpl.setProvidingOrganization(this);
        this.services.add(serviceImpl);
      } 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("OrganizationImpl:Objects_in_collection_must_be_services"), classCastException);
    } 
    setIsModified(true);
  }
  
  public void removeService(Service paramService) throws JAXRException {
    if (paramService != null) {
      getObject();
      this.services.remove(paramService);
      setIsModified(true);
    } 
  }
  
  public void removeServices(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.services.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getServices() throws JAXRException {
    getObject();
    return (Collection)this.services.clone();
  }
  
  public void setServices(Collection paramCollection) throws JAXRException {
    getObject();
    this.services.clear();
    addServices(paramCollection);
  }
  
  public void addExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    if (paramExternalLink != null) {
      getObject();
      ExternalLinkImpl externalLinkImpl = (ExternalLinkImpl)paramExternalLink;
      externalLinkImpl.addLinkedObject(this);
      this.externalLinks.add(externalLinkImpl);
      setIsModified(true);
    } 
  }
  
  public void addExternalLinks(Collection paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      Iterator<ExternalLink> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext())
          addExternalLink(iterator.next()); 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("OrganizationImpl:Objects_in_collection_must_be_ExternalLinks"), classCastException);
      } 
    } 
  }
  
  public void setExternalLinks(Collection paramCollection) throws JAXRException {
    getObject();
    this.externalLinks.clear();
    addExternalLinks(paramCollection);
  }
  
  public PostalAddress getPostalAddress() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setPostalAddress(PostalAddress paramPostalAddress) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void addChildOrganization(Organization paramOrganization) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void addChildOrganizations(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void removeChildOrganization(Organization paramOrganization) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void removeChildOrganizations(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public int getChildOrganizationCount() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Collection getChildOrganizations() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Collection getDescendantOrganizations() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Organization getParentOrganization() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Organization getRootOrganization() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\OrganizationImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */