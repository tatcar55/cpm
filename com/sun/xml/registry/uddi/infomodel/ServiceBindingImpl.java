package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.SpecificationLink;

public class ServiceBindingImpl extends RegistryObjectImpl implements ServiceBinding, Serializable {
  private String accessURI;
  
  private ServiceBinding targetBinding;
  
  private Service parentService;
  
  private ArrayList specificationLinks = new ArrayList();
  
  private URIValidatorImpl validator = new URIValidatorImpl();
  
  public String getAccessURI() throws JAXRException {
    if (this.accessURI == null)
      getObject(); 
    return this.accessURI;
  }
  
  public void setAccessURI(String paramString) throws JAXRException {
    if (this.targetBinding != null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:TargetBinding_already_set")); 
    getObject();
    this.validator.validate(paramString);
    this.accessURI = paramString;
    setIsModified(true);
  }
  
  public ServiceBinding getTargetBinding() throws JAXRException {
    if (this.targetBinding == null)
      getObject(); 
    return this.targetBinding;
  }
  
  public void setTargetBinding(ServiceBinding paramServiceBinding) throws JAXRException {
    if (this.accessURI != null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:AccessURI_already_set")); 
    getObject();
    this.targetBinding = paramServiceBinding;
    setIsModified(true);
  }
  
  public Service getService() throws JAXRException {
    if (this.parentService == null)
      getObject(); 
    return this.parentService;
  }
  
  public void setService(Service paramService) {
    this.parentService = paramService;
  }
  
  public Collection getSpecificationLinks() throws JAXRException {
    if (this.specificationLinks.size() == 0)
      getObject(); 
    return (Collection)this.specificationLinks.clone();
  }
  
  public void addSpecificationLink(SpecificationLink paramSpecificationLink) throws JAXRException {
    if (paramSpecificationLink != null) {
      getObject();
      ((SpecificationLinkImpl)paramSpecificationLink).setServiceBinding(this);
      this.specificationLinks.add(paramSpecificationLink);
      setIsModified(true);
    } 
  }
  
  public void addSpecificationLinks(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    getObject();
    Iterator<SpecificationLinkImpl> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        ((SpecificationLinkImpl)iterator.next()).setServiceBinding(this); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ServiceBindingImpl:Objects_in_collection_must_be_SpecificationLinks"), classCastException);
    } 
    this.specificationLinks.addAll(paramCollection);
    setIsModified(true);
  }
  
  public void removeSpecificationLink(SpecificationLink paramSpecificationLink) throws JAXRException {
    if (paramSpecificationLink != null) {
      getObject();
      this.specificationLinks.remove(paramSpecificationLink);
      setIsModified(true);
    } 
  }
  
  public void removeSpecificationLinks(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.specificationLinks.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public void setSpecificationLinks(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection == null) {
      this.specificationLinks = new ArrayList();
    } else {
      getObject();
      this.specificationLinks = new ArrayList(paramCollection);
    } 
  }
  
  public void setValidateURI(boolean paramBoolean) {
    this.validator.setValidateURI(paramBoolean);
  }
  
  public boolean getValidateURI() {
    return this.validator.getValidateURI();
  }
  
  int getSequenceId(SpecificationLink paramSpecificationLink) {
    return this.specificationLinks.indexOf(paramSpecificationLink);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\ServiceBindingImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */