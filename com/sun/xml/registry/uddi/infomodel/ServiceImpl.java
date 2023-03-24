package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;

public class ServiceImpl extends RegistryEntryImpl implements Service, Serializable {
  private ArrayList serviceBindings = new ArrayList();
  
  private Organization providingOrganization;
  
  public ServiceImpl() {}
  
  public ServiceImpl(String paramString) {
    this();
    this.name = new InternationalStringImpl(paramString);
  }
  
  public Organization getProvidingOrganization() throws JAXRException {
    return this.providingOrganization;
  }
  
  public void setProvidingOrganization(Organization paramOrganization) throws JAXRException {
    this.providingOrganization = paramOrganization;
    setIsModified(true);
  }
  
  public void addServiceBinding(ServiceBinding paramServiceBinding) throws JAXRException {
    if (paramServiceBinding != null) {
      getObject();
      ((ServiceBindingImpl)paramServiceBinding).setService(this);
      this.serviceBindings.add(paramServiceBinding);
      setIsModified(true);
    } 
  }
  
  public void addServiceBindings(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    getObject();
    Iterator<ServiceBinding> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addServiceBinding(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceImpl:Objects_in_collection_must_be_ServiceBindings"), classCastException);
    } 
    setIsModified(true);
  }
  
  public void removeServiceBinding(ServiceBinding paramServiceBinding) throws JAXRException {
    if (paramServiceBinding != null) {
      getObject();
      this.serviceBindings.remove(paramServiceBinding);
      setIsModified(true);
    } 
  }
  
  public void removeServiceBindings(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.serviceBindings.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getServiceBindings() throws JAXRException {
    if (this.serviceBindings.size() == 0)
      getObject(); 
    return (Collection)this.serviceBindings.clone();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ServiceImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */