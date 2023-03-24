package com.sun.xml.registry.uddi.infomodel;

import com.sun.xml.registry.uddi.RegistryServiceImpl;
import com.sun.xml.registry.uddi.UDDIObjectCache;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import javax.xml.registry.Connection;
import javax.xml.registry.JAXRException;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.RegistryService;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.ExternalIdentifier;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Slot;

public abstract class RegistryObjectImpl extends ExtensibleObjectImpl implements RegistryObject, Serializable {
  ArrayList associations = new ArrayList();
  
  ArrayList classifications = new ArrayList();
  
  ArrayList externalIdentifiers = new ArrayList();
  
  ArrayList externalLinks = new ArrayList();
  
  InternationalString description = new InternationalStringImpl();
  
  InternationalString name = new InternationalStringImpl();
  
  Key key;
  
  Organization submittingOrganization;
  
  LifeCycleManager lifeCycleManager;
  
  transient boolean isRetrieved = false;
  
  transient boolean isLoaded = false;
  
  transient boolean isNew = true;
  
  transient boolean isModified = false;
  
  transient boolean isDeleted = false;
  
  transient String serviceId;
  
  RegistryService registryService;
  
  transient boolean areAssociationsLoaded = false;
  
  UDDIObjectCache objectManager;
  
  public RegistryObjectImpl() {}
  
  public RegistryObjectImpl(Key paramKey) {
    this();
    this.key = paramKey;
  }
  
  public RegistryObjectImpl(Key paramKey, String paramString1, String paramString2) {
    this(paramKey);
    this.description = new InternationalStringImpl(paramString1);
    this.name = new InternationalStringImpl(paramString2);
  }
  
  public void addSlot(Slot paramSlot) throws JAXRException {
    super.addSlot(paramSlot);
    setIsModified(true);
  }
  
  public void addSlots(Collection paramCollection) throws JAXRException {
    super.addSlots(paramCollection);
    setIsModified(true);
  }
  
  public void removeSlot(String paramString) throws JAXRException {
    super.removeSlot(paramString);
    setIsModified(true);
  }
  
  public void removeSlots(Collection paramCollection) throws JAXRException {
    super.removeSlots(paramCollection);
    setIsModified(true);
  }
  
  public void addAssociation(Association paramAssociation) throws JAXRException {
    if (paramAssociation == null)
      return; 
    paramAssociation.setSourceObject(this);
    this.associations.add(paramAssociation);
    setIsModified(true);
  }
  
  public void addAssociations(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<Association> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addAssociation(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:Objects_in_collection_must_be_Associations"), classCastException);
    } 
    setIsModified(true);
  }
  
  public void removeAssociation(Association paramAssociation) throws JAXRException {
    if (paramAssociation != null) {
      this.associations.remove(paramAssociation);
      setIsModified(true);
    } 
  }
  
  public void removeAssociations(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      this.associations.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getAssociations() throws JAXRException {
    if (this.associations.isEmpty())
      if (this instanceof Organization) {
        getOrganizationAssociations();
      } else {
        getObject();
      }  
    return (Collection)this.associations.clone();
  }
  
  public void setAssociations(Collection paramCollection) throws JAXRException {
    this.associations.clear();
    addAssociations(paramCollection);
    setIsModified(true);
  }
  
  public void setAssociationsLoaded(boolean paramBoolean) {
    this.areAssociationsLoaded = paramBoolean;
  }
  
  public boolean areAssociationsLoaded() {
    return this.areAssociationsLoaded;
  }
  
  public void addClassification(Classification paramClassification) throws JAXRException {
    if (paramClassification == null)
      return; 
    getObject();
    paramClassification.setClassifiedObject(this);
    this.classifications.add(paramClassification);
    setIsModified(true);
  }
  
  public void addClassifications(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<Classification> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addClassification(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:Objects_in_collection_must_be_Classifiations"), classCastException);
    } 
    setIsModified(true);
  }
  
  public void removeClassification(Classification paramClassification) throws JAXRException {
    if (paramClassification != null) {
      getObject();
      this.classifications.remove(paramClassification);
      setIsModified(true);
    } 
  }
  
  public void removeClassifications(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.classifications.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getClassifications() throws JAXRException {
    if (this.classifications.isEmpty())
      getObject(); 
    return (Collection)this.classifications.clone();
  }
  
  public void setClassifications(Collection paramCollection) throws JAXRException {
    getObject();
    this.classifications.clear();
    addClassifications(paramCollection);
    setIsModified(true);
  }
  
  public void addExternalIdentifier(ExternalIdentifier paramExternalIdentifier) throws JAXRException {
    if (paramExternalIdentifier == null)
      return; 
    getObject();
    ((ExternalIdentifierImpl)paramExternalIdentifier).setRegistryObject(this);
    this.externalIdentifiers.add(paramExternalIdentifier);
    setIsModified(true);
  }
  
  public void addExternalIdentifiers(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<ExternalIdentifier> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext())
        addExternalIdentifier(iterator.next()); 
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:Objects_in_collection_must_be_ExternalIdentifers"), classCastException);
    } 
    setIsModified(true);
  }
  
  public void removeExternalIdentifier(ExternalIdentifier paramExternalIdentifier) throws JAXRException {
    if (paramExternalIdentifier != null) {
      getObject();
      this.externalIdentifiers.remove(paramExternalIdentifier);
      setIsModified(true);
    } 
  }
  
  public void removeExternalIdentifiers(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.externalIdentifiers.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getExternalIdentifiers() throws JAXRException {
    if (this.externalIdentifiers.isEmpty())
      getObject(); 
    return (Collection)this.externalIdentifiers.clone();
  }
  
  public void setExternalIdentifiers(Collection paramCollection) throws JAXRException {
    getObject();
    this.externalIdentifiers.clear();
    addExternalIdentifiers(paramCollection);
    setIsModified(true);
  }
  
  public void addExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:ExternalLinks_may_be_added_only_to_Organization,_Concept,_ClassificationScheme,_and_SpecificationLink"));
  }
  
  public void addExternalLinks(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:ExternalLinks_may_be_added_only_to_Organization,_Concept,_ClassificationScheme,_and_SpecificationLink"));
  }
  
  public void removeExternalLink(ExternalLink paramExternalLink) throws JAXRException {
    if (paramExternalLink != null) {
      getObject();
      this.externalLinks.remove(paramExternalLink);
      setIsModified(true);
    } 
  }
  
  public void removeExternalLinks(Collection<?> paramCollection) throws JAXRException {
    if (paramCollection != null) {
      getObject();
      this.externalLinks.removeAll(paramCollection);
      setIsModified(true);
    } 
  }
  
  public Collection getExternalLinks() throws JAXRException {
    if (this.externalLinks.isEmpty())
      getObject(); 
    return (Collection)this.externalLinks.clone();
  }
  
  public void setExternalLinks(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:ExternalLinks_may_be_added_only_to_Organization,_Concept,_ClassificationScheme,_and_SpecificationLink"));
  }
  
  public InternationalString getDescription() throws JAXRException {
    String str = this.description.getValue();
    if (str == null || str.equals(""))
      getObject(); 
    return this.description;
  }
  
  public void setDescription(InternationalString paramInternationalString) throws JAXRException {
    if (paramInternationalString != null) {
      getObject();
      this.description = paramInternationalString;
    } else {
      this.description = new InternationalStringImpl();
    } 
    setIsModified(true);
  }
  
  public InternationalString getName() throws JAXRException {
    String str = this.name.getValue();
    if (str == null || str.equals(""))
      getObject(); 
    return this.name;
  }
  
  public void setName(InternationalString paramInternationalString) throws JAXRException {
    if (paramInternationalString != null) {
      getObject();
      this.name = paramInternationalString;
    } else {
      this.name = new InternationalStringImpl();
    } 
    setIsModified(true);
  }
  
  public Key getKey() throws JAXRException {
    return this.key;
  }
  
  public void setKey(Key paramKey) throws JAXRException {
    this.key = paramKey;
    setIsModified(true);
  }
  
  public Organization getSubmittingOrganization() throws JAXRException {
    if (this.submittingOrganization == null)
      getObject(); 
    return this.submittingOrganization;
  }
  
  public void setSubmittingOrganization(Organization paramOrganization) throws JAXRException {
    this.submittingOrganization = paramOrganization;
  }
  
  public Connection getConnection() throws JAXRException {
    if (this.lifeCycleManager != null) {
      RegistryServiceImpl registryServiceImpl = (RegistryServiceImpl)this.lifeCycleManager.getRegistryService();
      return (registryServiceImpl != null) ? (Connection)registryServiceImpl.getConnection() : null;
    } 
    return null;
  }
  
  public LifeCycleManager getLifeCycleManager() {
    return this.lifeCycleManager;
  }
  
  public void setLifeCycleManager(LifeCycleManager paramLifeCycleManager) {
    this.lifeCycleManager = paramLifeCycleManager;
  }
  
  public void setServiceId(String paramString) {
    this.serviceId = paramString;
  }
  
  public String getServiceId() {
    return this.serviceId;
  }
  
  public void setRegistryService(RegistryService paramRegistryService) {
    this.registryService = paramRegistryService;
  }
  
  public RegistryService getRegistryService() {
    return this.registryService;
  }
  
  public synchronized void setStatusFlags(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    this.isRetrieved = paramBoolean1;
    this.isLoaded = paramBoolean2;
    this.isNew = paramBoolean3;
  }
  
  public synchronized boolean isLoaded() {
    return this.isLoaded;
  }
  
  public synchronized void setIsLoaded(boolean paramBoolean) {
    this.isLoaded = paramBoolean;
  }
  
  public synchronized boolean isRetrieved() {
    return this.isRetrieved;
  }
  
  public synchronized void setIsRetrieved(boolean paramBoolean) {
    this.isDeleted = false;
    this.isRetrieved = paramBoolean;
  }
  
  public synchronized boolean isNew() {
    return this.isNew;
  }
  
  public synchronized void setIsNew(boolean paramBoolean) {
    this.isNew = paramBoolean;
  }
  
  public boolean isModified() {
    return this.isModified;
  }
  
  public void setIsModified(boolean paramBoolean) {
    this.isModified = paramBoolean;
  }
  
  public synchronized boolean isDeleted() {
    return this.isDeleted;
  }
  
  public synchronized void setIsDeleted(boolean paramBoolean) {
    this.isDeleted = paramBoolean;
  }
  
  void getObject() throws JAXRException {
    if (isDeleted())
      return; 
    if (isRetrieved() && !isLoaded()) {
      if (this.objectManager == null)
        this.objectManager = getObjectManager(); 
      if (this.objectManager == null)
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("RegistryObjectImpl:Can_not_obtain_Object_detail")); 
      this.objectManager.fetchObject(this, this.serviceId);
    } 
  }
  
  UDDIObjectCache getObjectManager() throws JAXRException {
    if (this.registryService == null && this.lifeCycleManager != null)
      this.registryService = this.lifeCycleManager.getRegistryService(); 
    if (this.registryService != null)
      this.objectManager = ((RegistryServiceImpl)this.registryService).getObjectManager(); 
    return this.objectManager;
  }
  
  void getOrganizationAssociations() throws JAXRException {
    if (isDeleted() || isNew())
      return; 
    if (isRetrieved() && !areAssociationsLoaded()) {
      if (this.objectManager == null)
        this.objectManager = getObjectManager(); 
      if (this.objectManager != null)
        this.objectManager.fetchAssociations(this, this.serviceId); 
    } 
  }
  
  int getSequenceId(ExternalLink paramExternalLink) {
    return this.externalLinks.indexOf(paramExternalLink);
  }
  
  public String toXML() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Collection getAssociatedObjects() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Concept getObjectType() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public void setObjectType(Concept paramConcept) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Collection getAuditTrail() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Collection getRegistryPackages() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\infomodel\RegistryObjectImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */