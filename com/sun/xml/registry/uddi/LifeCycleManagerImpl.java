package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.Utility;
import com.sun.xml.registry.uddi.infomodel.AssociationImpl;
import com.sun.xml.registry.uddi.infomodel.ClassificationImpl;
import com.sun.xml.registry.uddi.infomodel.ClassificationSchemeImpl;
import com.sun.xml.registry.uddi.infomodel.ConceptImpl;
import com.sun.xml.registry.uddi.infomodel.EmailAddressImpl;
import com.sun.xml.registry.uddi.infomodel.ExternalIdentifierImpl;
import com.sun.xml.registry.uddi.infomodel.ExternalLinkImpl;
import com.sun.xml.registry.uddi.infomodel.InternationalStringImpl;
import com.sun.xml.registry.uddi.infomodel.KeyImpl;
import com.sun.xml.registry.uddi.infomodel.LocalizedStringImpl;
import com.sun.xml.registry.uddi.infomodel.OrganizationImpl;
import com.sun.xml.registry.uddi.infomodel.PersonNameImpl;
import com.sun.xml.registry.uddi.infomodel.PostalAddressImpl;
import com.sun.xml.registry.uddi.infomodel.RegistryObjectImpl;
import com.sun.xml.registry.uddi.infomodel.ServiceBindingImpl;
import com.sun.xml.registry.uddi.infomodel.ServiceImpl;
import com.sun.xml.registry.uddi.infomodel.SlotImpl;
import com.sun.xml.registry.uddi.infomodel.SpecificationLinkImpl;
import com.sun.xml.registry.uddi.infomodel.TelephoneNumberImpl;
import com.sun.xml.registry.uddi.infomodel.UserImpl;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.activation.DataHandler;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.LifeCycleManager;
import javax.xml.registry.RegistryService;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.EmailAddress;
import javax.xml.registry.infomodel.ExternalIdentifier;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.ExtrinsicObject;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.LocalizedString;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PersonName;
import javax.xml.registry.infomodel.PostalAddress;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.RegistryPackage;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.Slot;
import javax.xml.registry.infomodel.SpecificationLink;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;

public class LifeCycleManagerImpl implements LifeCycleManager {
  RegistryServiceImpl service;
  
  UDDIMapper uddi;
  
  String[] names = new String[] { 
      "Association", "Classification", "ClassificationScheme", "Concept", "EmailAddress", "ExternalIdentifier", "ExternalLink", "InternationalString", "Key", "LocalizedString", 
      "Organization", "PersonName", "PostalAddress", "Service", "ServiceBinding", "Slot", "SpecificationLink", "TelephoneNumber", "User" };
  
  Class[] infoModelClass = new Class[] { 
      AssociationImpl.class, ClassificationImpl.class, ClassificationSchemeImpl.class, ConceptImpl.class, EmailAddressImpl.class, ExternalIdentifierImpl.class, ExternalLinkImpl.class, InternationalStringImpl.class, KeyImpl.class, LocalizedStringImpl.class, 
      OrganizationImpl.class, PersonNameImpl.class, PostalAddressImpl.class, ServiceImpl.class, ServiceBindingImpl.class, SlotImpl.class, SpecificationLinkImpl.class, TelephoneNumberImpl.class, UserImpl.class };
  
  public LifeCycleManagerImpl() {
    String str = Locale.getDefault().getCountry();
    if (str == null || str == "")
      Locale.setDefault(Locale.US); 
    System.out.println("Default locale: " + Locale.getDefault().toString());
  }
  
  public LifeCycleManagerImpl(RegistryServiceImpl paramRegistryServiceImpl) {
    this();
    this.service = paramRegistryServiceImpl;
    this.uddi = paramRegistryServiceImpl.getUDDIMapper();
  }
  
  public RegistryService getRegistryService() {
    return this.service;
  }
  
  public Object createObject(String paramString) throws JAXRException {
    try {
      for (byte b = 0; b < this.names.length; b++) {
        if (paramString.equals(this.names[b])) {
          Class<Object> clazz = this.infoModelClass[b];
          RegistryObjectImpl registryObjectImpl = (RegistryObjectImpl)clazz.newInstance();
          if (registryObjectImpl instanceof RegistryObjectImpl) {
            RegistryObjectImpl registryObjectImpl1 = registryObjectImpl;
            registryObjectImpl1.setLifeCycleManager(this);
            registryObjectImpl1.setRegistryService(this.service);
            registryObjectImpl1.setIsModified(true);
          } 
          return registryObjectImpl;
        } 
      } 
      if (paramString.equals("AuditableEvent") || paramString.equals("ExtrinsicObject") || paramString.equals("RegistryEntry") || paramString.equals("Versionable") || paramString.equals("RegistryPackage"))
        throw new UnsupportedCapabilityException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:Can_not_create_object_of_type_") + paramString + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:_at_Capability_Level_0")); 
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:Class_Name_is_not_an_interface_in_the_javax.xml.registry.infomodel_package"));
    } catch (InstantiationException instantiationException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:InstantiationException_in_createObject()_"), instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:IllegalAccessException_in_createObject()_"), illegalAccessException);
    } 
  }
  
  public Association createAssociation(RegistryObject paramRegistryObject, Concept paramConcept) throws JAXRException {
    AssociationImpl associationImpl = new AssociationImpl(paramRegistryObject, paramConcept);
    associationImpl.setLifeCycleManager(this);
    associationImpl.setIsModified(true);
    return (Association)associationImpl;
  }
  
  public Classification createClassification(ClassificationScheme paramClassificationScheme, String paramString1, String paramString2) throws JAXRException {
    ClassificationImpl classificationImpl = new ClassificationImpl(paramClassificationScheme, paramString1, paramString2);
    classificationImpl.setLifeCycleManager(this);
    classificationImpl.setIsModified(true);
    return (Classification)classificationImpl;
  }
  
  public Classification createClassification(Concept paramConcept) throws JAXRException, InvalidRequestException {
    if (paramConcept.getClassificationScheme() == null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:Concept_has_no_ClassificationScheme")); 
    ClassificationImpl classificationImpl = new ClassificationImpl(paramConcept);
    classificationImpl.setLifeCycleManager(this);
    classificationImpl.setIsModified(true);
    return (Classification)classificationImpl;
  }
  
  public ClassificationScheme createClassificationScheme(Concept paramConcept) throws JAXRException, InvalidRequestException {
    if (paramConcept.getClassificationScheme() == null && paramConcept.getParentConcept() == null) {
      ClassificationSchemeImpl classificationSchemeImpl = new ClassificationSchemeImpl(paramConcept);
      classificationSchemeImpl.setLifeCycleManager(this);
      classificationSchemeImpl.setIsModified(true);
      return (ClassificationScheme)classificationSchemeImpl;
    } 
    throw new InvalidRequestException();
  }
  
  public ClassificationScheme createClassificationScheme(String paramString1, String paramString2) throws JAXRException, InvalidRequestException {
    ClassificationSchemeImpl classificationSchemeImpl = new ClassificationSchemeImpl(paramString1, paramString2);
    classificationSchemeImpl.setLifeCycleManager(this);
    classificationSchemeImpl.setIsModified(true);
    return (ClassificationScheme)classificationSchemeImpl;
  }
  
  public Concept createConcept(RegistryObject paramRegistryObject, String paramString1, String paramString2) throws JAXRException {
    ConceptImpl conceptImpl = new ConceptImpl(paramRegistryObject, paramString1, paramString2);
    conceptImpl.setLifeCycleManager(this);
    conceptImpl.setIsModified(true);
    return (Concept)conceptImpl;
  }
  
  public Concept createConcept(RegistryObject paramRegistryObject, InternationalString paramInternationalString, String paramString) throws JAXRException {
    ConceptImpl conceptImpl = new ConceptImpl(paramRegistryObject, "", paramString);
    conceptImpl.setName(paramInternationalString);
    conceptImpl.setLifeCycleManager(this);
    conceptImpl.setIsModified(true);
    return (Concept)conceptImpl;
  }
  
  public EmailAddress createEmailAddress(String paramString) throws JAXRException {
    return (EmailAddress)new EmailAddressImpl(paramString);
  }
  
  public EmailAddress createEmailAddress(String paramString1, String paramString2) throws JAXRException {
    return (EmailAddress)new EmailAddressImpl(paramString1, paramString2);
  }
  
  public ExternalIdentifier createExternalIdentifier(ClassificationScheme paramClassificationScheme, String paramString1, String paramString2) throws JAXRException {
    ExternalIdentifierImpl externalIdentifierImpl = new ExternalIdentifierImpl(paramClassificationScheme, paramString1, paramString2);
    externalIdentifierImpl.setLifeCycleManager(this);
    externalIdentifierImpl.setIsModified(true);
    return (ExternalIdentifier)externalIdentifierImpl;
  }
  
  public ExternalLink createExternalLink(String paramString1, String paramString2) throws JAXRException {
    ExternalLinkImpl externalLinkImpl = new ExternalLinkImpl(paramString1, paramString2);
    externalLinkImpl.setLifeCycleManager(this);
    externalLinkImpl.setIsModified(true);
    return (ExternalLink)externalLinkImpl;
  }
  
  public Key createKey(String paramString) throws JAXRException {
    return (Key)new KeyImpl(paramString);
  }
  
  public PersonName createPersonName(String paramString) throws JAXRException {
    return (PersonName)new PersonNameImpl(paramString);
  }
  
  public PostalAddress createPostalAddress(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) throws JAXRException {
    PostalAddressImpl postalAddressImpl = new PostalAddressImpl(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7);
    if (this.service != null)
      postalAddressImpl.setPostalScheme(this.service.getDefaultPostalScheme()); 
    return (PostalAddress)postalAddressImpl;
  }
  
  public Service createService(String paramString) throws JAXRException {
    ServiceImpl serviceImpl = new ServiceImpl(paramString);
    serviceImpl.setLifeCycleManager(this);
    serviceImpl.setIsModified(true);
    return (Service)serviceImpl;
  }
  
  public ServiceBinding createServiceBinding() throws JAXRException {
    ServiceBindingImpl serviceBindingImpl = new ServiceBindingImpl();
    serviceBindingImpl.setLifeCycleManager(this);
    serviceBindingImpl.setIsModified(true);
    return (ServiceBinding)serviceBindingImpl;
  }
  
  public Slot createSlot(String paramString1, String paramString2, String paramString3) throws JAXRException {
    return (Slot)new SlotImpl(paramString1, paramString2, paramString3);
  }
  
  public Slot createSlot(String paramString1, Collection paramCollection, String paramString2) throws JAXRException {
    return (Slot)new SlotImpl(paramString1, paramCollection, paramString2);
  }
  
  public SpecificationLink createSpecificationLink() throws JAXRException {
    SpecificationLinkImpl specificationLinkImpl = new SpecificationLinkImpl();
    specificationLinkImpl.setLifeCycleManager(this);
    specificationLinkImpl.setIsModified(true);
    return (SpecificationLink)specificationLinkImpl;
  }
  
  public TelephoneNumber createTelephoneNumber() throws JAXRException {
    return (TelephoneNumber)new TelephoneNumberImpl();
  }
  
  public User createUser() throws JAXRException {
    UserImpl userImpl = new UserImpl();
    userImpl.setLifeCycleManager(this);
    userImpl.setIsModified(true);
    return (User)userImpl;
  }
  
  public BulkResponse saveObjects(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveObjectsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveObjects(paramCollection);
  }
  
  public BulkResponse deleteObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse deleteObjects(Collection paramCollection, String paramString) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteObjectsCommand(this.service, bulkResponseImpl, paramCollection, paramString));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteObjects(paramCollection, paramString);
  }
  
  public void createConceptEquivalence(Concept paramConcept1, Concept paramConcept2) throws JAXRException {
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:UDDI_V2_functionality_is_not_supported_in_this_release"));
  }
  
  public void deleteConceptEquivalence(Concept paramConcept1, Concept paramConcept2) throws JAXRException {
    throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("LifeCycleManagerImpl:UDDI_V2_functionality_is_not_supported_in_this_release"));
  }
  
  public LocalizedString createLocalizedString(Locale paramLocale, String paramString) throws JAXRException {
    return (LocalizedString)new LocalizedStringImpl(paramLocale, paramString);
  }
  
  public InternationalString createInternationalString(Locale paramLocale, String paramString) throws JAXRException {
    return (InternationalString)new InternationalStringImpl(paramLocale, paramString);
  }
  
  public InternationalString createInternationalString() throws JAXRException {
    return (InternationalString)new InternationalStringImpl();
  }
  
  public InternationalString createInternationalString(String paramString) throws JAXRException {
    return (InternationalString)new InternationalStringImpl(paramString);
  }
  
  public Classification createClassification(ClassificationScheme paramClassificationScheme, InternationalString paramInternationalString, String paramString) throws JAXRException {
    ClassificationImpl classificationImpl = new ClassificationImpl(paramClassificationScheme, "", paramString);
    classificationImpl.setName(paramInternationalString);
    classificationImpl.setLifeCycleManager(this);
    classificationImpl.setIsModified(true);
    return (Classification)classificationImpl;
  }
  
  public ClassificationScheme createClassificationScheme(InternationalString paramInternationalString1, InternationalString paramInternationalString2) throws JAXRException, InvalidRequestException {
    ClassificationSchemeImpl classificationSchemeImpl = new ClassificationSchemeImpl();
    classificationSchemeImpl.setName(paramInternationalString1);
    classificationSchemeImpl.setDescription(paramInternationalString2);
    classificationSchemeImpl.setLifeCycleManager(this);
    classificationSchemeImpl.setIsModified(true);
    return (ClassificationScheme)classificationSchemeImpl;
  }
  
  public LocalizedString createLocalizedString(Locale paramLocale, String paramString1, String paramString2) throws JAXRException {
    LocalizedStringImpl localizedStringImpl = new LocalizedStringImpl(paramLocale, paramString1);
    localizedStringImpl.setCharsetName(paramString2);
    return (LocalizedString)localizedStringImpl;
  }
  
  public Organization createOrganization(InternationalString paramInternationalString) throws JAXRException {
    OrganizationImpl organizationImpl = new OrganizationImpl();
    organizationImpl.setName(paramInternationalString);
    organizationImpl.setLifeCycleManager(this);
    organizationImpl.setIsModified(true);
    return (Organization)organizationImpl;
  }
  
  public Organization createOrganization(String paramString) throws JAXRException {
    return createOrganization(createInternationalString(paramString));
  }
  
  public ExternalLink createExternalLink(String paramString, InternationalString paramInternationalString) throws JAXRException {
    ExternalLinkImpl externalLinkImpl = new ExternalLinkImpl(paramString);
    externalLinkImpl.setDescription(paramInternationalString);
    externalLinkImpl.setLifeCycleManager(this);
    externalLinkImpl.setIsModified(true);
    return (ExternalLink)externalLinkImpl;
  }
  
  public ExternalIdentifier createExternalIdentifier(ClassificationScheme paramClassificationScheme, InternationalString paramInternationalString, String paramString) throws JAXRException {
    ExternalIdentifierImpl externalIdentifierImpl = new ExternalIdentifierImpl(paramClassificationScheme, "", paramString);
    externalIdentifierImpl.setName(paramInternationalString);
    externalIdentifierImpl.setLifeCycleManager(this);
    externalIdentifierImpl.setIsModified(true);
    return (ExternalIdentifier)externalIdentifierImpl;
  }
  
  public Service createService(InternationalString paramInternationalString) throws JAXRException {
    ServiceImpl serviceImpl = new ServiceImpl();
    serviceImpl.setName(paramInternationalString);
    serviceImpl.setLifeCycleManager(this);
    serviceImpl.setIsModified(true);
    return (Service)serviceImpl;
  }
  
  public RegistryPackage createRegistryPackage(InternationalString paramInternationalString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public ExtrinsicObject createExtrinsicObject() throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public PersonName createPersonName(String paramString1, String paramString2, String paramString3) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public RegistryPackage createRegistryPackage(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse deprecateObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse unDeprecateObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public ExtrinsicObject createExtrinsicObject(DataHandler paramDataHandler) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\LifeCycleManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */