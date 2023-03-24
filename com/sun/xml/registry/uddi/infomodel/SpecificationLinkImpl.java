package com.sun.xml.registry.uddi.infomodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.SpecificationLink;

public class SpecificationLinkImpl extends RegistryObjectImpl implements SpecificationLink, Serializable {
  private Concept registryObject;
  
  private InternationalString usageDescription = new InternationalStringImpl();
  
  private ArrayList usageParameters = new ArrayList();
  
  private ServiceBinding serviceBinding;
  
  public RegistryObject getSpecificationObject() {
    return (RegistryObject)this.registryObject;
  }
  
  public void setSpecificationObject(RegistryObject paramRegistryObject) throws JAXRException {
    if (paramRegistryObject instanceof Concept) {
      this.registryObject = (Concept)paramRegistryObject;
      setIsModified(true);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:For_UDDI_provider,_object_must_be_a_Concept"));
    } 
  }
  
  public InternationalString getUsageDescription() {
    return this.usageDescription;
  }
  
  public void setUsageDescription(InternationalString paramInternationalString) {
    this.usageDescription = paramInternationalString;
    setIsModified(true);
  }
  
  public Collection getUsageParameters() {
    return (Collection)this.usageParameters.clone();
  }
  
  public void setUsageParameters(Collection paramCollection) throws JAXRException {
    if (paramCollection == null) {
      this.usageParameters.clear();
    } else {
      if (paramCollection.size() > 1)
        throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:single_usage_parameter_must_be_specified")); 
      for (Object object : paramCollection) {
        if (!(object instanceof String))
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:usage_parameters_must_be_strings")); 
        if (((String)object).length() > 255)
          throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:usage_parameters_size_must_be_less_than_255")); 
        this.usageParameters.add(object);
      } 
    } 
    setIsModified(true);
  }
  
  public ServiceBinding getServiceBinding() {
    return this.serviceBinding;
  }
  
  public void setServiceBinding(ServiceBinding paramServiceBinding) {
    this.serviceBinding = paramServiceBinding;
  }
  
  public void addExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramExternalLink != null) {
      ExternalLinkImpl externalLinkImpl = (ExternalLinkImpl)paramExternalLink;
      externalLinkImpl.addLinkedObject(this);
      this.externalLinks.add(externalLinkImpl);
      setIsModified(true);
    } 
  }
  
  public void addExternalLinks(Collection paramCollection) throws JAXRException {
    if (this.externalLinks.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:ExternalLink_already_exists,_cannot_add_more.")); 
    if (paramCollection != null) {
      if (paramCollection.size() > 1)
        throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:Cannot_add_more_than_one_ExternalLink")); 
      Iterator<ExternalLink> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext())
          addExternalLink(iterator.next()); 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:Objects_in_collection_must_be_ExternalLinks"), classCastException);
      } 
    } 
  }
  
  public void setExternalLinks(Collection paramCollection) throws JAXRException {
    if (paramCollection == null) {
      this.externalLinks.clear();
      return;
    } 
    if (paramCollection.size() > 0)
      throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("SpecificationLinkImpl:Cannot_set_more_than_one_link.")); 
    this.externalLinks.clear();
    addExternalLinks(paramCollection);
  }
  
  public Key getKey() throws JAXRException {
    if (this.serviceBinding == null || this.serviceBinding.getKey() == null)
      return null; 
    Service service = this.serviceBinding.getService();
    if (service == null || service.getKey() == null)
      return null; 
    if (this.registryObject == null || this.registryObject.getKey() == null)
      return null; 
    int i = ((ServiceBindingImpl)this.serviceBinding).getSequenceId(this);
    String str = service.getKey().getId() + ":" + this.serviceBinding.getAccessURI() + ":" + this.serviceBinding.getKey().getId() + ":" + i + ":" + this.registryObject.getKey().getId();
    return new KeyImpl(str);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\SpecificationLinkImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */