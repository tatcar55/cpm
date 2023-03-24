package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.tools.JAXRConceptsManager;
import com.sun.xml.registry.common.util.MarshallerUtil;
import com.sun.xml.registry.common.util.XMLUtil;
import com.sun.xml.registry.uddi.bindings_v2_2.AccessPoint;
import com.sun.xml.registry.uddi.bindings_v2_2.AddPublisherAssertions;
import com.sun.xml.registry.uddi.bindings_v2_2.Address;
import com.sun.xml.registry.uddi.bindings_v2_2.AddressLine;
import com.sun.xml.registry.uddi.bindings_v2_2.AssertionStatusItem;
import com.sun.xml.registry.uddi.bindings_v2_2.BindingTemplate;
import com.sun.xml.registry.uddi.bindings_v2_2.BindingTemplates;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessEntity;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessService;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessServices;
import com.sun.xml.registry.uddi.bindings_v2_2.CategoryBag;
import com.sun.xml.registry.uddi.bindings_v2_2.Contact;
import com.sun.xml.registry.uddi.bindings_v2_2.Contacts;
import com.sun.xml.registry.uddi.bindings_v2_2.DeleteBinding;
import com.sun.xml.registry.uddi.bindings_v2_2.DeleteBusiness;
import com.sun.xml.registry.uddi.bindings_v2_2.DeletePublisherAssertions;
import com.sun.xml.registry.uddi.bindings_v2_2.DeleteService;
import com.sun.xml.registry.uddi.bindings_v2_2.DeleteTModel;
import com.sun.xml.registry.uddi.bindings_v2_2.Description;
import com.sun.xml.registry.uddi.bindings_v2_2.Direction;
import com.sun.xml.registry.uddi.bindings_v2_2.DiscoveryURL;
import com.sun.xml.registry.uddi.bindings_v2_2.DiscoveryURLs;
import com.sun.xml.registry.uddi.bindings_v2_2.Email;
import com.sun.xml.registry.uddi.bindings_v2_2.ErrInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.FindBinding;
import com.sun.xml.registry.uddi.bindings_v2_2.FindBusiness;
import com.sun.xml.registry.uddi.bindings_v2_2.FindQualifiers;
import com.sun.xml.registry.uddi.bindings_v2_2.FindRelatedBusinesses;
import com.sun.xml.registry.uddi.bindings_v2_2.FindService;
import com.sun.xml.registry.uddi.bindings_v2_2.FindTModel;
import com.sun.xml.registry.uddi.bindings_v2_2.GetAssertionStatusReport;
import com.sun.xml.registry.uddi.bindings_v2_2.GetAuthToken;
import com.sun.xml.registry.uddi.bindings_v2_2.GetBindingDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.GetBusinessDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.GetPublisherAssertions;
import com.sun.xml.registry.uddi.bindings_v2_2.GetRegisteredInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.GetServiceDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.GetTModelDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.HostingRedirector;
import com.sun.xml.registry.uddi.bindings_v2_2.IdentifierBag;
import com.sun.xml.registry.uddi.bindings_v2_2.InstanceDetails;
import com.sun.xml.registry.uddi.bindings_v2_2.KeyType;
import com.sun.xml.registry.uddi.bindings_v2_2.KeyedReference;
import com.sun.xml.registry.uddi.bindings_v2_2.KeysOwned;
import com.sun.xml.registry.uddi.bindings_v2_2.Name;
import com.sun.xml.registry.uddi.bindings_v2_2.ObjectFactory;
import com.sun.xml.registry.uddi.bindings_v2_2.OverviewDoc;
import com.sun.xml.registry.uddi.bindings_v2_2.Phone;
import com.sun.xml.registry.uddi.bindings_v2_2.PublisherAssertion;
import com.sun.xml.registry.uddi.bindings_v2_2.RelatedBusinessInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.Result;
import com.sun.xml.registry.uddi.bindings_v2_2.SaveBinding;
import com.sun.xml.registry.uddi.bindings_v2_2.SaveBusiness;
import com.sun.xml.registry.uddi.bindings_v2_2.SaveService;
import com.sun.xml.registry.uddi.bindings_v2_2.SaveTModel;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceInfos;
import com.sun.xml.registry.uddi.bindings_v2_2.SetPublisherAssertions;
import com.sun.xml.registry.uddi.bindings_v2_2.SharedRelationships;
import com.sun.xml.registry.uddi.bindings_v2_2.TModel;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelBag;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelInstanceDetails;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelInstanceInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.URLType;
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.PasswordAuthentication;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.DeleteException;
import javax.xml.registry.FindException;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.RegistryService;
import javax.xml.registry.SaveException;
import javax.xml.registry.UnexpectedObjectException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.Classification;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.EmailAddress;
import javax.xml.registry.infomodel.ExternalIdentifier;
import javax.xml.registry.infomodel.ExternalLink;
import javax.xml.registry.infomodel.InternationalString;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.LocalizedString;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.PersonName;
import javax.xml.registry.infomodel.PostalAddress;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;
import javax.xml.registry.infomodel.Slot;
import javax.xml.registry.infomodel.SpecificationLink;
import javax.xml.registry.infomodel.TelephoneNumber;
import javax.xml.registry.infomodel.User;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPMessage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UDDIMapper extends JAXRConstants {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  private static XMLUtil xmlUtil;
  
  private static MarshallerUtil marshallerUtil;
  
  private UDDIObjectCache objectManager = null;
  
  private RegistryServiceImpl service = null;
  
  private ConnectionImpl connection = null;
  
  private static JAXRConceptsManager manager = null;
  
  private Collection fromKeysOwned;
  
  private Collection toKeysOwned;
  
  private ClassificationScheme defaultPostalScheme;
  
  private ClassificationScheme jaxrPostalAddressScheme;
  
  private HashMap postalAddressMap;
  
  private HashMap equivalentConcepts;
  
  private HashMap semanticEquivalences;
  
  private long tokenTime;
  
  private Processor processor;
  
  private MapperHelpers helper;
  
  private JAXBContext jc;
  
  private ObjectFactory objFactory;
  
  public UDDIMapper(RegistryService paramRegistryService) {
    this.service = (RegistryServiceImpl)paramRegistryService;
    this.objectManager = new UDDIObjectCache((RegistryServiceImpl)paramRegistryService);
    this;
    xmlUtil = XMLUtil.getInstance();
    try {
      marshallerUtil = MarshallerUtil.getInstance();
    } catch (JAXBException jAXBException) {
      System.out.println("Failure to initialize mapper");
    } 
    this.helper = new MapperHelpers();
    initJAXBObjectFactory();
  }
  
  public UDDIObjectCache getObjectManager() {
    return this.objectManager;
  }
  
  private void initJAXBObjectFactory() {
    try {
      if (this.jc == null)
        this.jc = JAXBContext.newInstance("com.sun.xml.registry.uddi.bindings_v2_2"); 
    } catch (JAXBException jAXBException) {
      this.logger.log(Level.SEVERE, "Exiting unable to initial JAXB context", jAXBException);
      System.exit(1);
    } 
    if (this.objFactory == null)
      this.objFactory = new ObjectFactory(); 
  }
  
  private Processor getProcessor() {
    if (this.processor == null)
      this.processor = new Processor(this.service, this); 
    return this.processor;
  }
  
  private JAXRConceptsManager getConceptsManager() throws JAXRException {
    if (manager == null)
      manager = JAXRConceptsManager.getInstance(this.service.getConnection()); 
    return manager;
  }
  
  private ConnectionImpl getConnection() {
    if (this.service != null && this.connection == null)
      this.connection = this.service.getConnection(); 
    return this.connection;
  }
  
  private void setConnection() {
    if (this.service != null && this.connection == null)
      this.connection = this.service.getConnection(); 
  }
  
  BulkResponse findOrganizations(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4, Collection paramCollection5, Collection paramCollection6) throws JAXRException {
    FindBusiness findBusiness = null;
    boolean bool = false;
    findBusiness = this.objFactory.createFindBusiness();
    findBusiness.setGeneric("2.0");
    String str = getConnection().getMaxRows();
    if (str != null) {
      int i = Integer.parseInt(str);
      findBusiness.setMaxRows(Integer.valueOf(i));
    } 
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findBusiness.setFindQualifiers(findQualifiers); 
    Collection collection = namePatterns2Names(paramCollection2, false);
    IdentifierBag identifierBag = externalIdentifiers2IdentifierBag(paramCollection5);
    CategoryBag categoryBag = classifications2CategoryBag(paramCollection3);
    TModelBag tModelBag = concepts2TModelBag(paramCollection4);
    DiscoveryURLs discoveryURLs = externalLinks2DiscoveryURLs(paramCollection6);
    if (collection != null && !collection.isEmpty()) {
      findBusiness.getName().addAll(collection);
      bool = true;
    } 
    if (identifierBag != null) {
      findBusiness.setIdentifierBag(identifierBag);
      bool = true;
    } 
    if (categoryBag != null) {
      findBusiness.setCategoryBag(categoryBag);
      bool = true;
    } 
    if (tModelBag != null) {
      findBusiness.setTModelBag(tModelBag);
      bool = true;
    } 
    if (discoveryURLs != null) {
      findBusiness.setDiscoveryURLs(discoveryURLs);
      bool = true;
    } 
    if (!bool)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Find_Criteria_Set_for_FindOrganization")); 
    return getProcessor().processRequestJAXB(findBusiness, false, null, "find");
  }
  
  BulkResponse saveOrganizations(Collection paramCollection) throws JAXRException {
    SaveBusiness saveBusiness = null;
    saveBusiness = this.objFactory.createSaveBusiness();
    saveBusiness.setGeneric("2.0");
    String str = null;
    Collection collection = organizations2BusinessEntities(paramCollection, true);
    if (!collection.isEmpty()) {
      saveBusiness.getBusinessEntity().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Organization_information_to_save"));
    } 
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_present")); 
    saveBusiness.setAuthInfo(str);
    return getProcessor().processRequestJAXB(saveBusiness, true, null, "save");
  }
  
  private String getAuthInfo() throws JAXRException {
    String str = getConnection().getAuthToken();
    if (str == null || tokenExpired() == true) {
      String str1 = getAuthorizationToken(getConnection().getAuthCreds());
      if (str1 == null)
        str1 = ""; 
      getConnection().setAuthToken(str1.toCharArray());
      getConnection().setAuthTokenTimestamp(timeStamp());
      return str1;
    } 
    return str;
  }
  
  private long timeStamp() {
    return System.currentTimeMillis();
  }
  
  private boolean tokenExpired() {
    this.logger.finest("Last Token time " + this.tokenTime);
    long l1 = timeStamp() - getConnection().getAuthTokenTimestamp();
    this.logger.finest("Elapsed time between authTokens " + l1);
    long l2 = getConnection().getTokenTimeout();
    this.logger.finest("DefaultTimeout " + l2);
    return (l1 > l2);
  }
  
  BulkResponse saveServices(Collection paramCollection) throws JAXRException {
    SaveService saveService = null;
    saveService = this.objFactory.createSaveService();
    saveService.setGeneric("2.0");
    String str = null;
    Collection collection = services2BusinessServicesCollection(paramCollection, true);
    if (collection != null) {
      saveService.getBusinessService().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Service_Information_to_Save_or_Update"));
    } 
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credential_Infomation")); 
    saveService.setAuthInfo(str);
    return getProcessor().processRequestJAXB(saveService, true, null, "save");
  }
  
  BulkResponse saveServiceBindings(Collection paramCollection) throws JAXRException {
    boolean bool = true;
    SaveBinding saveBinding = null;
    saveBinding = this.objFactory.createSaveBinding();
    saveBinding.setGeneric("2.0");
    String str = null;
    Collection collection = serviceBindings2BindingTemplatesCollection(paramCollection, bool);
    if (collection != null) {
      saveBinding.getBindingTemplate().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Binding_Information_to_be_saved_or_updated"));
    } 
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credential_or_Invalid_Credential_Information")); 
    saveBinding.setAuthInfo(str);
    return getProcessor().processRequestJAXB(saveBinding, true, null, "save");
  }
  
  BulkResponse saveConcepts(Collection paramCollection) throws JAXRException {
    boolean bool = true;
    SaveTModel saveTModel = null;
    saveTModel = this.objFactory.createSaveTModel();
    saveTModel.setGeneric("2.0");
    Collection collection = concepts2TModels(paramCollection, bool);
    if (collection != null && !collection.isEmpty()) {
      saveTModel.getTModel().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Concept_Information_or_Invalid_Concept_Information"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_or_Invalid_Credentials")); 
    saveTModel.setAuthInfo(str);
    return getProcessor().processRequestJAXB(saveTModel, true, null, "save");
  }
  
  BulkResponse saveClassificationSchemes(Collection paramCollection) throws JAXRException {
    boolean bool = true;
    SaveTModel saveTModel = null;
    saveTModel = this.objFactory.createSaveTModel();
    saveTModel.setGeneric("2.0");
    Collection collection = classificationSchemes2TModels(paramCollection, bool);
    if (collection != null && !collection.isEmpty()) {
      saveTModel.getTModel().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Concept_Information_or_Invalid_Concept_Information"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_or_Invalid_Credentials")); 
    saveTModel.setAuthInfo(str);
    return getProcessor().processRequestJAXB(saveTModel, true, null, "save");
  }
  
  BulkResponse deleteOrganizations(Collection paramCollection) throws JAXRException {
    DeleteBusiness deleteBusiness = null;
    deleteBusiness = this.objFactory.createDeleteBusiness();
    deleteBusiness.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      deleteBusiness.getBusinessKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Business_Identified_to_Delete"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_or_Invalid_Credentials")); 
    deleteBusiness.setAuthInfo(str);
    return getProcessor().processRequestJAXB(deleteBusiness, true, paramCollection, "delete");
  }
  
  BulkResponse deleteServices(Collection paramCollection) throws JAXRException {
    DeleteService deleteService = null;
    deleteService = this.objFactory.createDeleteService();
    deleteService.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      deleteService.getServiceKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Service_Keys_supplied_for_deletion"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Invalid_Credential_Information")); 
    deleteService.setAuthInfo(str);
    return getProcessor().processRequestJAXB(deleteService, true, paramCollection, "delete");
  }
  
  BulkResponse deleteServiceBindings(Collection paramCollection) throws JAXRException {
    DeleteBinding deleteBinding = null;
    deleteBinding = this.objFactory.createDeleteBinding();
    deleteBinding.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      deleteBinding.getBindingKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Service_Binding_Keys_supplied_for_deletion"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Invalid_Credential_Information")); 
    deleteBinding.setAuthInfo(str);
    return getProcessor().processRequestJAXB(deleteBinding, true, paramCollection, "delete");
  }
  
  BulkResponse deleteConcepts(Collection paramCollection) throws JAXRException {
    DeleteTModel deleteTModel = null;
    deleteTModel = this.objFactory.createDeleteTModel();
    deleteTModel.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      deleteTModel.getTModelKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Service_Binding_Keys_supplied_for_deletion"));
    } 
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Invalid_Credential_Information")); 
    deleteTModel.setAuthInfo(str);
    return getProcessor().processRequestJAXB(deleteTModel, true, paramCollection, "delete");
  }
  
  BulkResponse deleteAssociations(Collection paramCollection) throws JAXRException {
    DeletePublisherAssertions deletePublisherAssertions = null;
    deletePublisherAssertions = this.objFactory.createDeletePublisherAssertions();
    deletePublisherAssertions.setGeneric("2.0");
    Collection collection = associationKeys2PublisherAssertions(paramCollection);
    deletePublisherAssertions.getPublisherAssertion().addAll(collection);
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Invalid_Credential_Information")); 
    deletePublisherAssertions.setAuthInfo(str);
    return getProcessor().processRequestJAXB(deletePublisherAssertions, true, paramCollection, "delete");
  }
  
  BulkResponse findServices(Key paramKey, Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    FindService findService = null;
    boolean bool = false;
    findService = this.objFactory.createFindService();
    findService.setGeneric("2.0");
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findService.setFindQualifiers(findQualifiers); 
    String str = key2Key(paramKey);
    findService.setBusinessKey(str);
    Collection collection = namePatterns2Names(paramCollection2, false);
    CategoryBag categoryBag = classifications2CategoryBag(paramCollection3);
    TModelBag tModelBag = concepts2TModelBag(paramCollection4);
    if (collection != null && !collection.isEmpty()) {
      findService.getName().addAll(collection);
      bool = true;
    } 
    if (categoryBag != null) {
      findService.setCategoryBag(categoryBag);
      bool = true;
    } 
    if (tModelBag != null) {
      findService.setTModelBag(tModelBag);
      bool = true;
    } 
    if (!bool)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Find_Criteria_Specified")); 
    return getProcessor().processRequestJAXB(findService, false, null, "find");
  }
  
  BulkResponse findServiceBindings(Key paramKey, Collection paramCollection1, Collection paramCollection2, Collection paramCollection3) throws JAXRException {
    FindBinding findBinding = null;
    boolean bool = false;
    findBinding = this.objFactory.createFindBinding();
    findBinding.setGeneric("2.0");
    String str1 = getConnection().getMaxRows();
    if (str1 != null) {
      int i = Integer.parseInt(str1);
      findBinding.setMaxRows(Integer.valueOf(i));
    } 
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findBinding.setFindQualifiers(findQualifiers); 
    String str2 = key2Key(paramKey);
    if (str2 == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Service_Key_must_be_specified")); 
    findBinding.setServiceKey(str2);
    TModelBag tModelBag = concepts2TModelBag(paramCollection3);
    if (tModelBag != null) {
      findBinding.setTModelBag(tModelBag);
      bool = true;
    } 
    if (!bool)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Find_Criteria_Specified")); 
    return getProcessor().processRequestJAXB(findBinding, false, null, "find");
  }
  
  BulkResponse findConcepts(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4, Collection paramCollection5) throws JAXRException {
    FindTModel findTModel = null;
    boolean bool = false;
    findTModel = this.objFactory.createFindTModel();
    findTModel.setGeneric("2.0");
    String str = getConnection().getMaxRows();
    if (str != null) {
      int j = Integer.parseInt(str);
      findTModel.setMaxRows(Integer.valueOf(j));
    } 
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findTModel.setFindQualifiers(findQualifiers); 
    Collection collection = namePatterns2Names(paramCollection2, false);
    CategoryBag categoryBag = classifications2CategoryBag(paramCollection3);
    IdentifierBag identifierBag = externalIdentifiers2IdentifierBag(paramCollection4);
    if (categoryBag != null) {
      findTModel.setCategoryBag(categoryBag);
      bool = true;
    } 
    if (identifierBag != null) {
      findTModel.setIdentifierBag(identifierBag);
      bool = true;
    } 
    ArrayList<BulkResponse> arrayList = new ArrayList();
    int i = collection.size();
    if (collection != null && i > 0) {
      int j;
      for (j = 0; j < i; j++) {
        findTModel.setName(((ArrayList<Name>)collection).get(j));
        BulkResponse bulkResponse2 = getProcessor().processRequestJAXB(findTModel, false, null, "find");
        Collection collection1 = bulkResponse2.getCollection();
        Iterator iterator = collection1.iterator();
        arrayList.add(bulkResponse2);
      } 
      try {
        j = arrayList.size();
        int k;
        for (k = 0; k < j; k += 2) {
          BulkResponse bulkResponse2 = arrayList.get(k);
          BulkResponse bulkResponse3 = null;
          if (k + 1 < j)
            bulkResponse3 = arrayList.get(k + 1); 
          if (bulkResponse2 != null && bulkResponse3 != null) {
            Iterator<ConceptImpl> iterator1 = bulkResponse2.getCollection().iterator();
            Iterator<ConceptImpl> iterator2 = bulkResponse3.getCollection().iterator();
            boolean bool1;
            for (bool1 = true; iterator1.hasNext() && iterator2.hasNext(); bool1 = false) {
              if (((KeyImpl)((ConceptImpl)iterator1.next()).getKey()).getId().equals(((KeyImpl)((ConceptImpl)iterator2.next()).getKey()).getId())) {
                bool1 = true;
                continue;
              } 
            } 
            if (bool1)
              if (!iterator1.hasNext()) {
                arrayList.remove(k);
              } else if (!iterator2.hasNext()) {
                arrayList.remove(k + 1);
              }  
          } 
        } 
      } catch (ClassCastException classCastException) {}
      BulkResponse bulkResponse1 = BulkResponseImpl.combineBulkResponses(arrayList);
      return this.helper.extractRegistryObjectByClass(bulkResponse1, "Concept");
    } 
    if (!bool)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Find_Criteria_Specified_specified")); 
    BulkResponse bulkResponse = getProcessor().processRequestJAXB(findTModel, false, null, "find");
    return this.helper.extractRegistryObjectByClass(bulkResponse, "Concept");
  }
  
  BulkResponse findClassificationSchemes(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    return findClassificationSchemes(paramCollection1, paramCollection2, paramCollection3, paramCollection4, true);
  }
  
  BulkResponse findClassificationSchemes(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4, boolean paramBoolean) throws JAXRException {
    ArrayList<BulkResponseImpl> arrayList = new ArrayList();
    Collection collection1 = null;
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    if (paramCollection3 == null && paramCollection4 == null && paramCollection2 != null) {
      collection1 = getConceptsManager().findClassificationSchemes(paramCollection1, paramCollection2, paramCollection3, paramCollection4);
      if (collection1.size() > 0) {
        Iterator<ClassificationScheme> iterator = collection1.iterator();
        while (iterator.hasNext()) {
          try {
            ClassificationScheme classificationScheme = iterator.next();
            if (classificationScheme != null)
              this.objectManager.addObjectToCache((RegistryObjectImpl)classificationScheme, this.service.getServiceId()); 
          } catch (ClassCastException classCastException) {
            this.logger.log(Level.SEVERE, classCastException.getMessage(), classCastException);
          } 
          bulkResponseImpl.setCollection(collection1);
        } 
        arrayList.add(bulkResponseImpl);
      } 
    } 
    Object object1 = null;
    FindTModel findTModel = null;
    boolean bool = false;
    Object object2 = null;
    findTModel = this.objFactory.createFindTModel();
    findTModel.setGeneric("2.0");
    String str = getConnection().getMaxRows();
    if (str != null) {
      int j = Integer.parseInt(str);
      findTModel.setMaxRows(Integer.valueOf(j));
    } 
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findTModel.setFindQualifiers(findQualifiers); 
    Collection collection2 = namePatterns2Names(paramCollection2, false);
    CategoryBag categoryBag = classifications2CategoryBag(paramCollection3);
    if (categoryBag != null) {
      findTModel.setCategoryBag(categoryBag);
      bool = true;
    } 
    int i = collection2.size();
    if (collection2 != null && i > 0) {
      for (byte b = 0; b < i; b++) {
        findTModel.setName(((ArrayList<Name>)collection2).get(b));
        BulkResponse bulkResponse3 = getProcessor().processRequestJAXB(findTModel, false, null, "find");
        arrayList.add(bulkResponse3);
      } 
      BulkResponse bulkResponse = BulkResponseImpl.combineBulkResponses(arrayList);
      return paramBoolean ? this.helper.cullDuplicates(this.helper.extractRegistryObjectByClass(bulkResponse, "ClassificationScheme")) : this.helper.extractRegistryObjectByClass(bulkResponse, "ClassificationScheme");
    } 
    if (!bool)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Find_Criteria_Specified_specified")); 
    BulkResponse bulkResponse1 = getProcessor().processRequestJAXB(findTModel, false, null, "find");
    arrayList.add(bulkResponse1);
    BulkResponse bulkResponse2 = BulkResponseImpl.combineBulkResponses(arrayList);
    return this.helper.cullDuplicates(this.helper.extractRegistryObjectByClass(bulkResponse2, "ClassificationScheme"));
  }
  
  ClassificationScheme findClassificationSchemeByName(Collection paramCollection, String paramString) throws JAXRException {
    ArrayList<String> arrayList = new ArrayList();
    arrayList.add(paramString);
    BulkResponse bulkResponse = findClassificationSchemes(null, arrayList, null, null, true);
    if (bulkResponse != null && bulkResponse.getExceptions() == null) {
      Collection collection = bulkResponse.getCollection();
      int i = collection.size();
      this.logger.finest("Found schemes " + i);
      if (i > 1)
        throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("Multiple_Schemes_matching_name_pattern")); 
      Iterator<ClassificationScheme> iterator = collection.iterator();
      if (iterator.hasNext())
        try {
          return iterator.next();
        } catch (ClassCastException classCastException) {
          this.logger.log(Level.SEVERE, classCastException.getMessage(), classCastException);
          throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("Expected_ClassificationScheme"), classCastException);
        }  
    } 
    return null;
  }
  
  Concept findConceptByPath(String paramString) throws JAXRException {
    return (paramString != null) ? getConceptsManager().findConceptByPath(paramString) : null;
  }
  
  public Collection getChildConcepts(ClassificationScheme paramClassificationScheme) throws JAXRException {
    Collection collection = null;
    if (((ClassificationSchemeImpl)paramClassificationScheme).isPredefined())
      collection = getConceptsManager().getChildConcepts(paramClassificationScheme); 
    return collection;
  }
  
  KeyedReference associationType2KeyedReference(Object paramObject) throws JAXRException {
    if (paramObject == null)
      return null; 
    String str1 = null;
    String str2 = null;
    if (paramObject instanceof Concept) {
      str1 = ((Concept)paramObject).getName().getValue();
      str2 = ((Concept)paramObject).getValue();
    } else if (paramObject instanceof String) {
      str1 = (String)paramObject;
      str2 = (String)paramObject;
    } 
    String str3 = null;
    String str4 = null;
    if (str2.equalsIgnoreCase("EquivalentTo")) {
      str3 = "identity";
      str4 = "identity";
    } else if (str2.equalsIgnoreCase("RelatedTo")) {
      str3 = "peer-peer";
      str4 = "peer-peer";
    } else if (str2.equalsIgnoreCase("HasChild")) {
      str3 = "parent-child";
      str4 = "parent-child";
    } else {
      str3 = str1;
      str4 = str2;
    } 
    KeyedReference keyedReference = null;
    keyedReference = this.objFactory.createKeyedReference();
    keyedReference.setTModelKey("uuid:807a2c6a-ee22-470d-adc7-e0424a337c03");
    if (str3 != null) {
      keyedReference.setKeyName(str3);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Association_Type_required_to_save_Association_to_Registry"));
    } 
    if (str4 != null) {
      keyedReference.setKeyValue(str4);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Association_Type_required_to_save_Association_to_Registry"));
    } 
    return keyedReference;
  }
  
  Collection associationTypes2KeyedReferences(Collection paramCollection) throws JAXRException {
    ArrayList<KeyedReference> arrayList = new ArrayList();
    if (paramCollection != null) {
      Iterator iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        KeyedReference keyedReference = associationType2KeyedReference(iterator.next());
        if (keyedReference != null)
          arrayList.add(keyedReference); 
      } 
    } 
    return arrayList;
  }
  
  BulkResponse getOrganizations(Collection paramCollection) throws JAXRException {
    GetBusinessDetail getBusinessDetail = null;
    getBusinessDetail = this.objFactory.createGetBusinessDetail();
    getBusinessDetail.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      getBusinessDetail.getBusinessKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Organization_Keys_supplied"));
    } 
    return getProcessor().processRequestJAXB(getBusinessDetail, false, paramCollection, "find");
  }
  
  BulkResponse getServices(Collection paramCollection) throws JAXRException {
    GetServiceDetail getServiceDetail = null;
    getServiceDetail = this.objFactory.createGetServiceDetail();
    getServiceDetail.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      getServiceDetail.getServiceKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_service_Keys_Supplied"));
    } 
    return getProcessor().processRequestJAXB(getServiceDetail, false, null, "find");
  }
  
  BulkResponse getServiceBindings(Collection paramCollection) throws JAXRException {
    GetBindingDetail getBindingDetail = null;
    getBindingDetail = this.objFactory.createGetBindingDetail();
    getBindingDetail.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      getBindingDetail.getBindingKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_ServiceBinding_Keys_supplied"));
    } 
    return getProcessor().processRequestJAXB(getBindingDetail, false, null, "find");
  }
  
  BulkResponse getConcepts(Collection paramCollection) throws JAXRException {
    GetTModelDetail getTModelDetail = null;
    getTModelDetail = this.objFactory.createGetTModelDetail();
    getTModelDetail.setGeneric("2.0");
    Collection collection = keys2Keys(paramCollection);
    if (collection != null && !collection.isEmpty()) {
      getTModelDetail.getTModelKey().addAll(collection);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Concept_keys_supplied"));
    } 
    return getProcessor().processRequestJAXB(getTModelDetail, false, null, "find");
  }
  
  IdentifierBag externalIdentifiers2IdentifierBag(Collection paramCollection) throws UnexpectedObjectException, JAXRException {
    IdentifierBag identifierBag = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      identifierBag = this.objFactory.createIdentifierBag();
      try {
        ArrayList<KeyedReference> arrayList = new ArrayList();
        Iterator<ExternalIdentifier> iterator = paramCollection.iterator();
        while (iterator.hasNext()) {
          KeyedReference keyedReference = externalIdentifier2KeyedReference(iterator.next());
          if (keyedReference != null)
            arrayList.add(keyedReference); 
        } 
        identifierBag.getKeyedReference().addAll(arrayList);
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_ExternalIdentifier"), classCastException);
      } 
    } 
    return identifierBag;
  }
  
  Collection organizations2BusinessEntities(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<BusinessEntity> arrayList = new ArrayList();
    if (paramCollection != null && !paramCollection.isEmpty()) {
      Iterator<OrganizationImpl> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          BusinessEntity businessEntity = organization2BusinessEntity(iterator.next(), paramBoolean);
          if (businessEntity != null)
            arrayList.add(businessEntity); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Organization"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  BusinessEntity organization2BusinessEntity(OrganizationImpl paramOrganizationImpl, boolean paramBoolean) throws JAXRException {
    BusinessEntity businessEntity = null;
    if (paramOrganizationImpl != null) {
      businessEntity = this.objFactory.createBusinessEntity();
      Key key = paramOrganizationImpl.getKey();
      Slot slot1 = paramOrganizationImpl.getSlot("authorizedName");
      Slot slot2 = paramOrganizationImpl.getSlot("operator");
      Collection collection1 = getNames((RegistryObject)paramOrganizationImpl, paramBoolean);
      Collection collection2 = getDescriptions((RegistryObject)paramOrganizationImpl, paramBoolean);
      Collection collection3 = paramOrganizationImpl.getUsers();
      User user = paramOrganizationImpl.getPrimaryContact();
      Collection collection4 = paramOrganizationImpl.getServices();
      Collection collection5 = paramOrganizationImpl.getExternalIdentifiers();
      Collection collection6 = paramOrganizationImpl.getClassifications();
      Collection collection7 = paramOrganizationImpl.getExternalLinks();
      if (key != null) {
        String str = key.getId();
        if (str != null)
          businessEntity.setBusinessKey(str); 
      } else {
        businessEntity.setBusinessKey("");
      } 
      if (collection1 != null) {
        businessEntity.getName().addAll(collection1);
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:The_Organization_name_must_be_specified_to_save_an_Organization"));
      } 
      if (collection2 != null)
        businessEntity.getDescription().addAll(collection2); 
      if (slot1 != null) {
        Collection collection = slot1.getValues();
        if (collection != null && !collection.isEmpty()) {
          String str = (String)collection.toArray()[0];
          if (str != null)
            businessEntity.setAuthorizedName(str); 
        } 
      } 
      if (slot2 != null) {
        Collection collection = slot2.getValues();
        if (collection != null && !collection.isEmpty()) {
          String str = (String)collection.toArray()[0];
          if (str != null)
            businessEntity.setOperator(str); 
        } 
      } 
      if (collection5 != null && !collection5.isEmpty()) {
        IdentifierBag identifierBag = externalIdentifiers2IdentifierBag(collection5);
        if (identifierBag != null)
          businessEntity.setIdentifierBag(identifierBag); 
      } 
      if (collection6 != null && !collection6.isEmpty()) {
        CategoryBag categoryBag = classifications2CategoryBag(collection6);
        if (categoryBag != null)
          businessEntity.setCategoryBag(categoryBag); 
      } 
      if (collection7 != null && !collection7.isEmpty()) {
        DiscoveryURLs discoveryURLs = externalLinks2DiscoveryURLs(collection7);
        if (discoveryURLs != null)
          businessEntity.setDiscoveryURLs(discoveryURLs); 
      } 
      if (collection4 != null && !collection4.isEmpty()) {
        BusinessServices businessServices = services2BusinessServices(collection4, paramBoolean);
        if (businessServices != null)
          businessEntity.setBusinessServices(businessServices); 
      } 
      if (collection3 != null && !collection3.isEmpty()) {
        Contacts contacts = users2Contacts(collection3, paramBoolean);
        if (contacts != null)
          businessEntity.setContacts(contacts); 
      } 
    } 
    return businessEntity;
  }
  
  Collection identifierBag2ExternalIdentifiers(IdentifierBag paramIdentifierBag) throws JAXRException {
    ArrayList<ExternalIdentifier> arrayList = null;
    if (paramIdentifierBag != null) {
      arrayList = new ArrayList();
      List list = paramIdentifierBag.getKeyedReference();
      Iterator<KeyedReference> iterator = list.iterator();
      while (iterator.hasNext()) {
        ExternalIdentifier externalIdentifier = keyedReference2ExternalIdentifier(iterator.next());
        if (externalIdentifier != null)
          arrayList.add(externalIdentifier); 
      } 
    } 
    return arrayList;
  }
  
  BusinessServices services2BusinessServices(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    BusinessServices businessServices = null;
    if (paramCollection != null) {
      businessServices = this.objFactory.createBusinessServices();
      try {
        Iterator<Service> iterator = paramCollection.iterator();
        while (iterator.hasNext()) {
          BusinessService businessService = service2BusinessService(iterator.next(), paramBoolean);
          if (businessService != null)
            businessServices.getBusinessService().add(businessService); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Service"), classCastException);
      } 
    } 
    return businessServices;
  }
  
  Collection services2BusinessServicesCollection(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<BusinessService> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      try {
        Iterator<Service> iterator = paramCollection.iterator();
        while (iterator.hasNext()) {
          BusinessService businessService = service2BusinessService(iterator.next(), paramBoolean);
          if (businessService != null)
            arrayList.add(businessService); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Service"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  BusinessService service2BusinessService(Service paramService, boolean paramBoolean) throws JAXRException {
    BusinessService businessService = null;
    if (paramService != null) {
      businessService = this.objFactory.createBusinessService();
      Organization organization = paramService.getProvidingOrganization();
      if (organization != null) {
        Key key1 = organization.getKey();
        if (key1 != null) {
          String str1 = key1.getId();
          if (str1 != null) {
            businessService.setBusinessKey(str1);
            this.logger.finest("Setting business key");
            this.logger.finest("key" + key1);
          } 
        } else {
          this.logger.finest("Organization key is null");
        } 
      } else {
        this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Org_is_null"));
      } 
      Key key = paramService.getKey();
      String str = null;
      if (key != null)
        str = key.getId(); 
      Collection collection1 = getNames((RegistryObject)paramService, paramBoolean);
      Collection collection2 = getDescriptions((RegistryObject)paramService, paramBoolean);
      Collection collection3 = paramService.getServiceBindings();
      Collection collection4 = paramService.getClassifications();
      if (str != null) {
        businessService.setServiceKey(str);
      } else {
        businessService.setServiceKey("");
      } 
      if (collection1 != null)
        businessService.getName().addAll(collection1); 
      if (collection2 != null)
        businessService.getDescription().addAll(collection2); 
      CategoryBag categoryBag = classifications2CategoryBag(collection4);
      if (categoryBag != null)
        businessService.setCategoryBag(categoryBag); 
      BindingTemplates bindingTemplates = serviceBindings2BindingTemplates(collection3, paramBoolean);
      if (bindingTemplates != null)
        businessService.setBindingTemplates(bindingTemplates); 
    } 
    return businessService;
  }
  
  BindingTemplates serviceBindings2BindingTemplates(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    BindingTemplates bindingTemplates = null;
    if (paramCollection != null) {
      bindingTemplates = this.objFactory.createBindingTemplates();
      Iterator<ServiceBinding> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          BindingTemplate bindingTemplate = serviceBinding2BindingTemplate(iterator.next(), paramBoolean);
          if (bindingTemplate != null)
            bindingTemplates.getBindingTemplate().add(bindingTemplate); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_ServiceBinding"), classCastException);
      } 
    } 
    return bindingTemplates;
  }
  
  Collection serviceBindings2BindingTemplatesCollection(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<BindingTemplate> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      Iterator<ServiceBinding> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          BindingTemplate bindingTemplate = serviceBinding2BindingTemplate(iterator.next(), paramBoolean);
          if (bindingTemplate != null)
            arrayList.add(bindingTemplate); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_ServiceBinding"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  BindingTemplate serviceBinding2BindingTemplate(ServiceBinding paramServiceBinding, boolean paramBoolean) throws JAXRException {
    BindingTemplate bindingTemplate = null;
    if (paramServiceBinding != null) {
      String str1 = null;
      bindingTemplate = this.objFactory.createBindingTemplate();
      Key key = paramServiceBinding.getKey();
      if (key != null)
        str1 = key.getId(); 
      Service service = paramServiceBinding.getService();
      if (service != null) {
        Key key1 = service.getKey();
        if (key1 != null) {
          String str = key1.getId();
          if (str != null) {
            bindingTemplate.setServiceKey(str);
          } else {
            bindingTemplate.setServiceKey("");
          } 
        } 
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:A_ServiceBinding_must_be_associated_with_a_service."));
      } 
      Description description = getDescription((RegistryObject)paramServiceBinding);
      String str2 = paramServiceBinding.getAccessURI();
      if (str1 != null) {
        bindingTemplate.setBindingKey(str1);
      } else {
        bindingTemplate.setBindingKey("");
      } 
      if (description != null)
        bindingTemplate.getDescription().add(description); 
      ServiceBinding serviceBinding = paramServiceBinding.getTargetBinding();
      if (str2 == null && serviceBinding == null)
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:AccessURI_or_targetBinding_needs_to_be_set_on_the_ServiceBinding_-_neither_is_set")); 
      AccessPoint accessPoint = null;
      if (str2 != null) {
        accessPoint = this.objFactory.createAccessPoint();
        accessPoint.setValue(str2);
        Collection collection1 = paramServiceBinding.getClassifications();
        URLType uRLType = null;
        Iterator<Classification> iterator = collection1.iterator();
        boolean bool = false;
        while (iterator.hasNext()) {
          Classification classification = iterator.next();
          Concept concept = classification.getConcept();
          if (concept != null) {
            ClassificationScheme classificationScheme = concept.getClassificationScheme();
            InternationalString internationalString = classificationScheme.getName();
            String str = null;
            if (internationalString != null)
              str = internationalString.getValue(); 
            if (classificationScheme != null && str.equalsIgnoreCase("URLType")) {
              String str3 = concept.getValue();
              if (str3.indexOf("https") != -1) {
                uRLType = URLType.HTTPS;
              } else if (str3.indexOf("http") != -1) {
                uRLType = URLType.HTTP;
              } else if (str3.indexOf("ftp") != -1) {
                uRLType = URLType.FTP;
              } else if (str3.indexOf("phone") != -1) {
                uRLType = URLType.PHONE;
              } else if (str3.indexOf("mailto") != -1) {
                uRLType = URLType.MAILTO;
              } else {
                uRLType = URLType.OTHER;
              } 
              bool = true;
              break;
            } 
          } 
        } 
        if (!bool)
          uRLType = this.helper.parseUrlForUrlType(str2); 
        if (uRLType != null) {
          accessPoint.setURLType(uRLType);
        } else {
          accessPoint.setURLType(URLType.OTHER);
        } 
        bindingTemplate.setAccessPoint(accessPoint);
      } else if (serviceBinding != null) {
        HostingRedirector hostingRedirector = targetBinding2HostingRedirector(serviceBinding);
        if (hostingRedirector != null)
          bindingTemplate.setHostingRedirector(hostingRedirector); 
      } 
      Collection collection = paramServiceBinding.getSpecificationLinks();
      TModelInstanceDetails tModelInstanceDetails = null;
      if (collection != null) {
        Collection collection1 = specificationLinks2TModelInstanceInfos(collection, paramBoolean);
        tModelInstanceDetails = null;
        tModelInstanceDetails = this.objFactory.createTModelInstanceDetails();
        if (collection1 != null)
          tModelInstanceDetails.getTModelInstanceInfo().addAll(collection1); 
        bindingTemplate.setTModelInstanceDetails(tModelInstanceDetails);
      } 
    } 
    return bindingTemplate;
  }
  
  Collection specificationLinks2TModelInstanceInfos(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<TModelInstanceInfo> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      Iterator<SpecificationLink> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        SpecificationLink specificationLink = null;
        try {
          specificationLink = iterator.next();
        } catch (ClassCastException classCastException) {
          this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
          throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_SpecificationLink"), classCastException);
        } 
        RegistryObject registryObject = specificationLink.getSpecificationObject();
        InternationalString internationalString = specificationLink.getUsageDescription();
        Collection collection1 = specificationLink.getUsageParameters();
        Collection collection2 = specificationLink.getExternalLinks();
        TModelInstanceInfo tModelInstanceInfo = null;
        tModelInstanceInfo = this.objFactory.createTModelInstanceInfo();
        if (registryObject != null) {
          Key key = registryObject.getKey();
          if (key != null) {
            String str = key.getId();
            if (str != null)
              tModelInstanceInfo.setTModelKey(str); 
          } else {
            throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:The_Concept_Key_defining_the_Technical_interface_of_this_Service_Binding_must_be_supplied."));
          } 
        } else {
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:The_Concept_defining_the_Technical_interface_of_this_Service_Binding_must_be_supplied."));
        } 
        InstanceDetails instanceDetails = null;
        if (internationalString != null || collection1 != null || collection2 != null) {
          instanceDetails = null;
          instanceDetails = this.objFactory.createInstanceDetails();
          Collection collection = internationalString2Descriptions(internationalString, paramBoolean);
          if (internationalString != null)
            instanceDetails.getDescription().addAll(collection); 
          if (collection1 != null) {
            Iterator<String> iterator1 = collection1.iterator();
            if (iterator1.hasNext())
              instanceDetails.setInstanceParms(iterator1.next()); 
          } 
          if (collection2 != null) {
            Iterator<ExternalLink> iterator1 = collection2.iterator();
            if (iterator1.hasNext()) {
              OverviewDoc overviewDoc = externalLink2OverviewDoc(iterator1.next(), paramBoolean);
              if (overviewDoc != null)
                instanceDetails.setOverviewDoc(overviewDoc); 
            } 
          } 
        } 
        if (instanceDetails != null) {
          tModelInstanceInfo.setInstanceDetails(instanceDetails);
          arrayList.add(tModelInstanceInfo);
        } 
      } 
    } 
    return arrayList;
  }
  
  HostingRedirector targetBinding2HostingRedirector(ServiceBinding paramServiceBinding) throws JAXRException {
    HostingRedirector hostingRedirector = null;
    if (paramServiceBinding != null) {
      hostingRedirector = null;
      hostingRedirector = this.objFactory.createHostingRedirector();
      Key key = paramServiceBinding.getKey();
      if (key != null) {
        String str = key.getId();
        hostingRedirector.setBindingKey(str);
      } else {
        hostingRedirector = null;
      } 
    } 
    return hostingRedirector;
  }
  
  ServiceBinding hostingRedirector2TargetBinding(HostingRedirector paramHostingRedirector) throws JAXRException {
    ServiceBinding serviceBinding = null;
    String str = paramHostingRedirector.getBindingKey();
    if (str != null) {
      serviceBinding = (ServiceBinding)this.objectManager.fetchObjectFromCache(str);
      if (serviceBinding == null) {
        KeyImpl keyImpl = new KeyImpl(str);
        ArrayList<KeyImpl> arrayList = new ArrayList();
        arrayList.add(keyImpl);
        BulkResponse bulkResponse = getServiceBindings(arrayList);
        if (bulkResponse.getExceptions() == null) {
          Collection collection = bulkResponse.getCollection();
          if (collection != null) {
            Iterator<ServiceBinding> iterator = collection.iterator();
            if (iterator.hasNext())
              return iterator.next(); 
          } 
        } 
      } 
      return serviceBinding;
    } 
    return null;
  }
  
  Concept urlType2Concept(URLType paramURLType) throws JAXRException {
    String str = paramURLType.value();
    Collection<ClassificationScheme> collection = getConceptsManager().findClassificationSchemeByName(null, "URLType");
    ClassificationScheme classificationScheme = collection.iterator().next();
    if (classificationScheme != null) {
      Collection collection1 = classificationScheme.getChildrenConcepts();
      if (collection1 != null)
        for (Concept concept : collection1) {
          InternationalString internationalString = concept.getName();
          String str1 = internationalString.getValue();
          String str2 = concept.getValue();
          if (str != null && str2 != null && str1 != null && (str.indexOf(str1) != -1 || str.indexOf(str2) != -1))
            return concept; 
        }  
    } 
    return null;
  }
  
  Contacts users2Contacts(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    Contacts contacts = null;
    contacts = this.objFactory.createContacts();
    if (paramCollection != null) {
      Iterator<User> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          Contact contact = user2Contact(iterator.next(), paramBoolean);
          if (contact != null)
            contacts.getContact().add(contact); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_User"), classCastException);
      } 
    } 
    return contacts;
  }
  
  Contact user2Contact(User paramUser, boolean paramBoolean) throws JAXRException {
    Contact contact = null;
    if (paramUser != null) {
      Collection collection1 = paramUser.getTelephoneNumbers(null);
      PersonNameImpl personNameImpl = (PersonNameImpl)paramUser.getPersonName();
      Collection collection2 = paramUser.getPostalAddresses();
      Collection collection3 = paramUser.getEmailAddresses();
      Collection collection4 = getDescriptions((RegistryObject)paramUser, paramBoolean);
      String str = paramUser.getType();
      contact = null;
      contact = this.objFactory.createContact();
      if (personNameImpl != null) {
        String str1 = personNameImpl.getFullName();
        if (str1 != null)
          contact.setPersonName(str1); 
      } else {
        return null;
      } 
      if (collection4 != null)
        contact.getDescription().addAll(collection4); 
      if (str != null)
        contact.setUseType(str); 
      if (collection1 != null && !collection1.isEmpty()) {
        Iterator<TelephoneNumber> iterator = collection1.iterator();
        try {
          while (iterator.hasNext()) {
            Phone phone = telephoneNumber2Phone(iterator.next());
            if (phone != null);
            contact.getPhone().add(phone);
          } 
        } catch (ClassCastException classCastException) {
          this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
          throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_TelephoneNumber"), classCastException);
        } 
      } 
      if (collection3 != null && !collection3.isEmpty()) {
        Iterator<EmailAddress> iterator = collection3.iterator();
        ArrayList<Email> arrayList = new ArrayList();
        try {
          while (iterator.hasNext()) {
            EmailAddress emailAddress = iterator.next();
            String str1 = emailAddress.getAddress();
            String str2 = emailAddress.getType();
            Email email = null;
            email = this.objFactory.createEmail();
            if (str1 != null)
              email.setValue(str1); 
            if (str2 != null)
              email.setUseType(str2); 
            arrayList.add(email);
          } 
          contact.getEmail().addAll(arrayList);
        } catch (ClassCastException classCastException) {
          this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
          throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_EmailAddress"), classCastException);
        } 
      } 
      if (collection2 != null)
        for (PostalAddress postalAddress : collection2) {
          Address address = postalAddress2Address(postalAddress);
          contact.getAddress().add(address);
        }  
    } 
    return contact;
  }
  
  Phone telephoneNumber2Phone(TelephoneNumber paramTelephoneNumber) throws JAXRException {
    Phone phone = null;
    phone = this.objFactory.createPhone();
    if (paramTelephoneNumber != null) {
      String str1 = paramTelephoneNumber.getType();
      if (str1 != null)
        phone.setUseType(str1); 
      String str2 = paramTelephoneNumber.getNumber();
      if (str2 != null)
        phone.setValue(str2); 
    } 
    return phone;
  }
  
  Collection postalAddresses2Addresses(Collection paramCollection) {
    return null;
  }
  
  Address postalAddress2Address(PostalAddress paramPostalAddress) throws JAXRException {
    if (paramPostalAddress == null)
      return null; 
    initPostalSchemes();
    String str = paramPostalAddress.getType();
    this.logger.finest("Got type");
    Slot slot = paramPostalAddress.getSlot("sortCode");
    this.logger.finest("SortCode");
    Address address = null;
    ClassificationScheme classificationScheme = this.defaultPostalScheme;
    address = postalAddressEquivalence2Address(classificationScheme, paramPostalAddress);
    if (address == null)
      try {
        Slot slot1 = paramPostalAddress.getSlot("addressLines");
        this.logger.finest("addressLines");
        address = this.objFactory.createAddress();
        Collection collection = null;
        collection = postalAddressLines2AddressLines(slot1);
        this.logger.finest("alines");
        if (collection != null)
          address.getAddressLine().addAll(collection); 
      } catch (ClassCastException classCastException) {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_PostalAddress"), classCastException);
      }  
    if (address != null) {
      if (str != null)
        address.setUseType(str); 
      if (slot != null) {
        Collection collection = slot.getValues();
        if (collection != null && !collection.isEmpty()) {
          String str1 = (String)collection.toArray()[0];
          if (str1 != null)
            address.setSortCode(str1); 
        } 
      } else {
        address.setSortCode("");
      } 
    } 
    return address;
  }
  
  private Address postalAddressEquivalence2Address(ClassificationScheme paramClassificationScheme, PostalAddress paramPostalAddress) throws JAXRException {
    boolean bool = false;
    if (paramClassificationScheme == null)
      bool = true; 
    ClassificationScheme classificationScheme = null;
    if (paramPostalAddress != null)
      classificationScheme = paramPostalAddress.getPostalScheme(); 
    Object object = null;
    Address address = null;
    address = this.objFactory.createAddress();
    if (this.postalAddressMap == null) {
      mapPostalAddressAttributes(classificationScheme);
      if (this.postalAddressMap == null)
        this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_PostalAddressMapping")); 
    } 
    ArrayList<String> arrayList = new ArrayList();
    ArrayList<AddressLine> arrayList1 = new ArrayList();
    String str1 = paramPostalAddress.getStreet();
    if (str1 != null)
      arrayList.add(str1); 
    String str2 = paramPostalAddress.getStreetNumber();
    if (str2 != null)
      arrayList.add(str2); 
    String str3 = paramPostalAddress.getCity();
    if (str3 != null)
      arrayList.add(str3); 
    String str4 = paramPostalAddress.getStateOrProvince();
    if (str4 != null)
      arrayList.add(str4); 
    String str5 = paramPostalAddress.getPostalCode();
    if (str5 != null)
      arrayList.add(str5); 
    String str6 = paramPostalAddress.getCountry();
    if (str6 != null)
      arrayList.add(str6); 
    AddressLine addressLine = null;
    if (str2 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str2, "StreetNumber");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (str1 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str1, "Street");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (str3 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str3, "City");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (str4 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str4, "State");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (str5 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str5, "PostalCode");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (str6 != null) {
      addressLine = postalAddressAttribute2AddressLine(this.postalAddressMap, str6, "Country");
      if (addressLine != null)
        arrayList1.add(addressLine); 
      addressLine = null;
    } 
    if (arrayList1 != null && !arrayList1.isEmpty()) {
      address = null;
      address = this.objFactory.createAddress();
      if (paramClassificationScheme != null) {
        String str = paramClassificationScheme.getKey().getId();
        if (str != null)
          address.setTModelKey(str); 
      } 
      address.getAddressLine().addAll(arrayList1);
      return address;
    } 
    return null;
  }
  
  AddressLine postalAddressAttribute2AddressLine(HashMap paramHashMap, String paramString1, String paramString2) throws JAXRException {
    Object object = null;
    String str1 = null;
    String str2 = null;
    AddressLine addressLine = null;
    addressLine = this.objFactory.createAddressLine();
    if (paramString1 == null || paramString1.equals(""))
      return null; 
    Concept concept = null;
    if (paramHashMap != null)
      concept = (Concept)paramHashMap.get(paramString2); 
    if (concept != null) {
      str1 = concept.getName().getValue();
      str2 = concept.getValue();
    } else {
      str1 = paramString2;
      str2 = paramString2;
    } 
    if (paramString1 != null) {
      if (str1 != null)
        addressLine.setKeyName(str1); 
      if (str2 != null)
        addressLine.setKeyValue(str2); 
      addressLine.setValue(paramString1);
      return addressLine;
    } 
    return null;
  }
  
  Collection postalAddressLines2AddressLines(Slot paramSlot) throws JAXRException {
    ArrayList<AddressLine> arrayList = null;
    if (paramSlot != null) {
      arrayList = new ArrayList();
      Collection collection = paramSlot.getValues();
      if (collection != null)
        for (String str : collection) {
          AddressLine addressLine = null;
          addressLine = this.objFactory.createAddressLine();
          if (addressLine != null) {
            addressLine.setValue(str);
            arrayList.add(addressLine);
          } 
        }  
    } 
    return arrayList;
  }
  
  CategoryBag classifications2CategoryBag(Collection paramCollection) throws JAXRException {
    CategoryBag categoryBag = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      categoryBag = null;
      categoryBag = this.objFactory.createCategoryBag();
      Classification classification = null;
      Iterator<Classification> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          classification = iterator.next();
          Concept concept = classification.getConcept();
          if (concept != null) {
            KeyedReference keyedReference = concept2KeyedReference(concept);
            if (keyedReference != null)
              categoryBag.getKeyedReference().add(keyedReference); 
            continue;
          } 
          ClassificationScheme classificationScheme = classification.getClassificationScheme();
          String str1 = classification.getValue();
          Name name = getName((RegistryObject)classification);
          String str2 = null;
          if (name != null)
            str2 = name.getValue(); 
          if (classificationScheme != null) {
            if (str1 != null && !str1.equalsIgnoreCase("")) {
              KeyedReference keyedReference = classification2KeyedReference(classificationScheme, str2, str1);
              if (keyedReference != null)
                categoryBag.getKeyedReference().add(keyedReference); 
              continue;
            } 
            throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Concept_specified_for_this_Classification"));
          } 
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Concept_specified_for_this_Classification"));
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Classification"), classCastException);
      } 
    } 
    return categoryBag;
  }
  
  Collection categoryBag2Classifications(CategoryBag paramCategoryBag) throws JAXRException {
    ArrayList<Classification> arrayList = null;
    if (paramCategoryBag != null) {
      arrayList = new ArrayList();
      List list = paramCategoryBag.getKeyedReference();
      Iterator<KeyedReference> iterator = list.iterator();
      while (iterator.hasNext()) {
        Classification classification = keyedReference2Classification(iterator.next());
        if (classification != null)
          arrayList.add(classification); 
      } 
    } 
    return arrayList;
  }
  
  CategoryBag concepts2CategoryBag(Collection paramCollection) throws JAXRException {
    CategoryBag categoryBag = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      categoryBag = null;
      categoryBag = this.objFactory.createCategoryBag();
      Iterator<Concept> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          KeyedReference keyedReference = concept2KeyedReference(iterator.next());
          if (keyedReference != null)
            categoryBag.getKeyedReference().add(keyedReference); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Concept"), classCastException);
      } 
    } 
    return categoryBag;
  }
  
  Collection categoryBag2Concepts(CategoryBag paramCategoryBag) throws JAXRException {
    ArrayList<Concept> arrayList = null;
    if (paramCategoryBag != null) {
      arrayList = new ArrayList();
      List list = paramCategoryBag.getKeyedReference();
      Iterator<KeyedReference> iterator = list.iterator();
      while (iterator.hasNext()) {
        Concept concept = keyedReference2Concept(iterator.next());
        if (concept != null)
          arrayList.add(concept); 
      } 
    } 
    return arrayList;
  }
  
  TModelBag concepts2TModelBag(Collection paramCollection) throws JAXRException {
    TModelBag tModelBag = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      tModelBag = null;
      tModelBag = this.objFactory.createTModelBag();
      Concept concept = null;
      Iterator<Concept> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          concept = iterator.next();
          ClassificationScheme classificationScheme = concept.getClassificationScheme();
          Concept concept1 = concept.getParentConcept();
          if (classificationScheme == null && concept1 == null) {
            Key key = concept.getKey();
            tModelBag.getTModelKey().add(key.getId());
            continue;
          } 
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:ClassificationScheme_and_Parent_must_be_non-existent_for_specifications"));
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Concept"), classCastException);
      } 
    } 
    return tModelBag;
  }
  
  String[] strings2Names(Collection paramCollection) throws JAXRException {
    String[] arrayOfString = null;
    if (paramCollection != null)
      try {
        arrayOfString = (String[])paramCollection.toArray((Object[])new String[0]);
      } catch (ArrayStoreException arrayStoreException) {
        throw new JAXRException(arrayStoreException);
      }  
    return arrayOfString;
  }
  
  Collection namePatterns2Names(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<Name> arrayList = new ArrayList();
    if (paramCollection != null)
      for (String str : paramCollection) {
        if (str instanceof String) {
          Name name1 = null;
          Name name2 = null;
          name1 = this.objFactory.createName();
          name2 = this.objFactory.createName();
          name1.setValue(str);
          name2.setValue(str);
          Locale locale = Locale.getDefault();
          String str1 = locale.getLanguage();
          if (!paramBoolean) {
            name2.setLang(str1);
            arrayList.add(name2);
          } 
          String str2 = locale.getCountry();
          if (str2 != null && !str2.equals(""))
            str1 = str1 + "-" + str2; 
          name1.setLang(str1);
          arrayList.add(name1);
          continue;
        } 
        if (str instanceof InternationalString) {
          Collection<? extends Name> collection = internationalString2Names((InternationalString)str, paramBoolean);
          if (collection != null)
            arrayList.addAll(collection); 
        } 
      }  
    return arrayList;
  }
  
  String key2Key(Key paramKey) throws JAXRException {
    String str = null;
    if (paramKey != null)
      str = paramKey.getId(); 
    return str;
  }
  
  Collection keys2Keys(Collection paramCollection) throws JAXRException {
    ArrayList<String> arrayList = new ArrayList();
    if (paramCollection != null) {
      Iterator<Key> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext())
          arrayList.add(((Key)iterator.next()).getId()); 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Keys"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  FindQualifiers strings2FindQualifiers(Collection paramCollection) throws JAXRException {
    try {
      FindQualifiers findQualifiers = null;
      if (paramCollection != null && !paramCollection.isEmpty()) {
        findQualifiers = null;
        findQualifiers = this.objFactory.createFindQualifiers();
        Iterator<String> iterator = paramCollection.iterator();
        while (iterator.hasNext())
          findQualifiers.getFindQualifier().add(iterator.next()); 
      } 
      return findQualifiers;
    } catch (ClassCastException classCastException) {
      this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_String"), classCastException);
    } 
  }
  
  DiscoveryURLs externalLinks2DiscoveryURLs(Collection paramCollection) throws JAXRException {
    DiscoveryURLs discoveryURLs = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      discoveryURLs = null;
      discoveryURLs = this.objFactory.createDiscoveryURLs();
      ExternalLink externalLink = null;
      Iterator<ExternalLink> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          DiscoveryURL discoveryURL = null;
          discoveryURL = this.objFactory.createDiscoveryURL();
          externalLink = iterator.next();
          String str = externalLink.getExternalURI();
          Name name = getName((RegistryObject)externalLink);
          if (str != null)
            discoveryURL.setValue(str); 
          if (name == null || name.equals("")) {
            name.setValue("Unknown Name");
            name.setLang("");
          } 
          discoveryURL.setUseType(name.getValue());
          discoveryURLs.getDiscoveryURL().add(discoveryURL);
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_ExternalLink"), classCastException);
      } 
    } 
    return discoveryURLs;
  }
  
  Collection concepts2TModels(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<TModel> arrayList = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      arrayList = new ArrayList();
      Iterator<Concept> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          TModel tModel = concept2TModel(iterator.next(), paramBoolean);
          if (tModel != null)
            arrayList.add(tModel); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Concept"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  Collection classificationSchemes2TModels(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<TModel> arrayList = null;
    if (paramCollection != null && !paramCollection.isEmpty()) {
      arrayList = new ArrayList();
      Iterator<ClassificationScheme> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          TModel tModel = classificationScheme2TModel(iterator.next(), paramBoolean);
          if (tModel != null)
            arrayList.add(tModel); 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_ClassificationScheme"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  TModel concept2TModel(Concept paramConcept, boolean paramBoolean) throws JAXRException {
    TModel tModel = null;
    tModel = this.objFactory.createTModel();
    ConceptImpl conceptImpl = (ConceptImpl)paramConcept;
    Key key = conceptImpl.getKey();
    if (key != null) {
      tModel.setTModelKey(key.getId());
    } else {
      tModel.setTModelKey("");
    } 
    Name name = getName((RegistryObject)paramConcept);
    tModel.setName(name);
    Collection collection1 = getDescriptions((RegistryObject)conceptImpl, paramBoolean);
    tModel.getDescription().addAll(collection1);
    Collection collection2 = conceptImpl.getSlots();
    if (collection2 != null)
      try {
        for (Slot slot : collection2) {
          if (slot.getName().equals("authorizedName")) {
            Collection collection = slot.getValues();
            if (collection != null && !collection.isEmpty()) {
              Object[] arrayOfObject = collection.toArray();
              String str = (String)arrayOfObject[0];
              if (str != null)
                tModel.setAuthorizedName(str); 
            } 
          } 
          if (slot.getName().equals("operator")) {
            Collection collection = slot.getValues();
            if (collection != null && !collection.isEmpty()) {
              Object[] arrayOfObject = collection.toArray();
              String str = (String)arrayOfObject[0];
              if (str != null)
                tModel.setOperator(str); 
            } 
          } 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Slot"), classCastException);
      }  
    Collection collection3 = conceptImpl.getExternalIdentifiers();
    if (collection3 != null) {
      IdentifierBag identifierBag = externalIdentifiers2IdentifierBag(collection3);
      if (identifierBag != null)
        tModel.setIdentifierBag(identifierBag); 
    } 
    Collection collection4 = conceptImpl.getClassifications();
    CategoryBag categoryBag = null;
    if (collection4 != null)
      categoryBag = classifications2CategoryBag(collection4); 
    categoryBag = addCategorization(categoryBag, "Concept");
    if (categoryBag != null)
      tModel.setCategoryBag(categoryBag); 
    Collection collection5 = paramConcept.getExternalLinks();
    if (collection5 != null && !collection5.isEmpty()) {
      OverviewDoc overviewDoc = externalLink2OverviewDoc((ExternalLink)collection5.toArray()[0], paramBoolean);
      tModel.setOverviewDoc(overviewDoc);
    } 
    return tModel;
  }
  
  TModel classificationScheme2TModel(ClassificationScheme paramClassificationScheme, boolean paramBoolean) throws JAXRException {
    TModel tModel = null;
    tModel = this.objFactory.createTModel();
    Key key = paramClassificationScheme.getKey();
    if (key != null) {
      tModel.setTModelKey(key.getId());
    } else {
      tModel.setTModelKey("");
    } 
    Name name = getName((RegistryObject)paramClassificationScheme);
    tModel.setName(name);
    Collection collection1 = getDescriptions((RegistryObject)paramClassificationScheme, paramBoolean);
    tModel.getDescription().addAll(collection1);
    Collection collection2 = paramClassificationScheme.getSlots();
    if (collection2 != null)
      try {
        for (Slot slot : collection2) {
          if (slot.getName().equals("authorizedName")) {
            Collection collection = slot.getValues();
            if (collection != null && !collection.isEmpty()) {
              Object[] arrayOfObject = collection.toArray();
              String str = (String)arrayOfObject[0];
              if (str != null)
                tModel.setAuthorizedName(str); 
            } 
          } 
          if (slot.getName().equals("operator")) {
            Collection collection = slot.getValues();
            if (collection != null && !collection.isEmpty()) {
              Object[] arrayOfObject = collection.toArray();
              String str = (String)arrayOfObject[0];
              if (str != null)
                tModel.setOperator(str); 
            } 
          } 
        } 
      } catch (ClassCastException classCastException) {
        this.logger.log(Level.WARNING, classCastException.getMessage(), classCastException);
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Slot"), classCastException);
      }  
    Collection collection3 = paramClassificationScheme.getExternalIdentifiers();
    if (collection3 != null) {
      IdentifierBag identifierBag = externalIdentifiers2IdentifierBag(collection3);
      if (identifierBag != null)
        tModel.setIdentifierBag(identifierBag); 
    } 
    Collection collection4 = paramClassificationScheme.getClassifications();
    CategoryBag categoryBag = null;
    if (collection4 != null)
      categoryBag = classifications2CategoryBag(collection4); 
    categoryBag = addCategorization(categoryBag, "ClassificationScheme");
    if (categoryBag != null)
      tModel.setCategoryBag(categoryBag); 
    Collection collection5 = paramClassificationScheme.getExternalLinks();
    if (collection5 != null && !collection5.isEmpty()) {
      OverviewDoc overviewDoc = externalLink2OverviewDoc((ExternalLink)collection5.toArray()[0], paramBoolean);
      tModel.setOverviewDoc(overviewDoc);
    } 
    return tModel;
  }
  
  CategoryBag addCategorization(CategoryBag paramCategoryBag, String paramString) throws JAXRException {
    boolean bool = false;
    List list = null;
    if (paramCategoryBag == null) {
      paramCategoryBag = this.objFactory.createCategoryBag();
    } else {
      list = paramCategoryBag.getKeyedReference();
      if (list != null)
        for (KeyedReference keyedReference : list) {
          if (keyedReference.getTModelKey().equalsIgnoreCase("uuid:c1acf26d-9672-4404-9d70-39b756e62ab4")) {
            bool = true;
            break;
          } 
        }  
    } 
    if (!bool) {
      KeyedReference keyedReference = null;
      keyedReference = this.objFactory.createKeyedReference();
      if (paramString.equalsIgnoreCase("ClassificationScheme")) {
        keyedReference.setTModelKey("uuid:c1acf26d-9672-4404-9d70-39b756e62ab4");
        keyedReference.setKeyName("uddi-org:types");
        keyedReference.setKeyValue("categorization");
      } else if (paramString.equalsIgnoreCase("Concept")) {
        keyedReference.setTModelKey("uuid:c1acf26d-9672-4404-9d70-39b756e62ab4");
        keyedReference.setKeyName("uddi-org:types");
        keyedReference.setKeyValue("specification");
      } else {
        keyedReference = null;
      } 
      if (keyedReference != null)
        paramCategoryBag.getKeyedReference().add(keyedReference); 
    } 
    return paramCategoryBag;
  }
  
  Concept tModel2Concept(TModelInfo paramTModelInfo) throws JAXRException {
    ConceptImpl conceptImpl = null;
    String str = paramTModelInfo.getTModelKey();
    Name name = paramTModelInfo.getName();
    if (getConnection().useCache())
      try {
        conceptImpl = (ConceptImpl)this.objectManager.fetchObjectFromCache(str);
      } catch (ClassCastException classCastException) {
        this.logger.finest("ClassCastException in tModelInfo2Concept on fetch, continuing");
      }  
    if (conceptImpl == null) {
      conceptImpl = new ConceptImpl();
      conceptImpl.setServiceId(this.service.getServiceId());
      conceptImpl.setRegistryService(this.service);
    } 
    InternationalString internationalString = name2InternationalString(name);
    conceptImpl.setName(internationalString);
    conceptImpl.setKey((Key)new KeyImpl(str));
    conceptImpl.setStatusFlags(true, false, false);
    this.objectManager.addObjectToCache((RegistryObjectImpl)conceptImpl, this.service.getServiceId());
    return (Concept)conceptImpl;
  }
  
  Concept tModel2Concept(TModel paramTModel) throws JAXRException {
    ConceptImpl conceptImpl = null;
    String str1 = paramTModel.getTModelKey();
    if (getConnection().useCache())
      try {
        conceptImpl = (ConceptImpl)this.objectManager.fetchObjectFromCache(str1);
      } catch (ClassCastException classCastException) {
        this.logger.finest("ClassCastException in tModel2Concept, continuing");
        conceptImpl = null;
      }  
    if (conceptImpl == null) {
      conceptImpl = new ConceptImpl((Key)new KeyImpl(str1));
      conceptImpl.setServiceId(this.service.getServiceId());
      conceptImpl.setRegistryService(this.service);
    } 
    conceptImpl.setIsLoaded(true);
    conceptImpl.setIsRetrieved(true);
    conceptImpl.setIsNew(false);
    Name name = paramTModel.getName();
    String str2 = paramTModel.getAuthorizedName();
    SlotImpl slotImpl1 = new SlotImpl("authorizedName", "authorizedName", str2);
    conceptImpl.addSlot((Slot)slotImpl1);
    String str3 = paramTModel.getOperator();
    SlotImpl slotImpl2 = new SlotImpl("operator", "operator", str3);
    conceptImpl.addSlot((Slot)slotImpl2);
    ExternalLink externalLink = null;
    OverviewDoc overviewDoc = paramTModel.getOverviewDoc();
    if (overviewDoc != null) {
      externalLink = overviewDoc2ExternalLink(overviewDoc);
      if (externalLink != null) {
        ArrayList<ExternalLink> arrayList = new ArrayList();
        arrayList.add(externalLink);
        conceptImpl.setExternalLinks(arrayList);
      } 
    } 
    Collection collection1 = null;
    IdentifierBag identifierBag = paramTModel.getIdentifierBag();
    if (identifierBag != null) {
      collection1 = identifierBag2ExternalIdentifiers(identifierBag);
      if (collection1 != null && !collection1.isEmpty())
        conceptImpl.setExternalIdentifiers(collection1); 
    } 
    Collection collection2 = null;
    CategoryBag categoryBag = paramTModel.getCategoryBag();
    if (categoryBag != null) {
      collection2 = categoryBag2Classifications(categoryBag);
      if (collection2 != null && !collection2.isEmpty())
        conceptImpl.setClassifications(collection2); 
    } 
    KeyImpl keyImpl = new KeyImpl(str1);
    InternationalString internationalString1 = name2InternationalString(name);
    conceptImpl.setName(internationalString1);
    List list = paramTModel.getDescription();
    InternationalString internationalString2 = descriptions2InternationalString(list);
    if (internationalString2 != null)
      conceptImpl.setDescription(internationalString2); 
    if (getConnection().useCache()) {
      this.objectManager.addObjectToCache((RegistryObjectImpl)conceptImpl, this.service.getServiceId());
    } else {
      this.objectManager.removeObjectFromCache(conceptImpl.getKey().getId());
    } 
    return (Concept)conceptImpl;
  }
  
  RegistryObject tModel2ConceptOrClassificationScheme(TModel paramTModel) throws JAXRException {
    RegistryObjectImpl registryObjectImpl2;
    ConceptImpl conceptImpl;
    RegistryObjectImpl registryObjectImpl1 = null;
    String str1 = paramTModel.getTModelKey();
    registryObjectImpl1 = (RegistryObjectImpl)this.objectManager.fetchObjectFromCache(str1);
    if (registryObjectImpl1 != null) {
      registryObjectImpl1.setRegistryService(this.service);
      registryObjectImpl1.setIsLoaded(true);
    } 
    Collection collection1 = null;
    CategoryBag categoryBag = paramTModel.getCategoryBag();
    boolean bool = false;
    if (categoryBag != null) {
      collection1 = categoryBag2Classifications(categoryBag);
      if (collection1 != null && !collection1.isEmpty())
        try {
          Iterator<Classification> iterator = collection1.iterator();
          while (iterator.hasNext()) {
            bool = isClassificationScheme(iterator.next());
            if (bool)
              break; 
          } 
        } catch (ClassCastException classCastException) {
          this.logger.log(Level.SEVERE, classCastException.getMessage(), classCastException);
        }  
    } 
    ClassificationSchemeImpl classificationSchemeImpl = null;
    if (registryObjectImpl1 != null)
      if (bool) {
        if (registryObjectImpl1 instanceof Concept)
          classificationSchemeImpl = new ClassificationSchemeImpl((Concept)registryObjectImpl1); 
      } else {
        registryObjectImpl2 = registryObjectImpl1;
      }  
    if (registryObjectImpl2 == null) {
      if (bool) {
        ClassificationSchemeImpl classificationSchemeImpl1 = new ClassificationSchemeImpl((Key)new KeyImpl(str1));
      } else {
        conceptImpl = new ConceptImpl((Key)new KeyImpl(str1));
      } 
      conceptImpl.setServiceId(this.service.getServiceId());
      conceptImpl.setRegistryService(this.service);
    } 
    conceptImpl.setIsRetrieved(true);
    conceptImpl.setIsNew(false);
    conceptImpl.setIsLoaded(true);
    String str2 = paramTModel.getAuthorizedName();
    SlotImpl slotImpl1 = new SlotImpl("authorizedName", str2, "authorizedName");
    conceptImpl.addSlot((Slot)slotImpl1);
    String str3 = paramTModel.getOperator();
    SlotImpl slotImpl2 = new SlotImpl("operator", "operator", str3);
    conceptImpl.addSlot((Slot)slotImpl2);
    if (collection1 != null)
      conceptImpl.setClassifications(collection1); 
    ExternalLink externalLink = null;
    OverviewDoc overviewDoc = paramTModel.getOverviewDoc();
    if (overviewDoc != null) {
      externalLink = overviewDoc2ExternalLink(overviewDoc);
      if (externalLink != null) {
        ArrayList<ExternalLink> arrayList = new ArrayList();
        arrayList.add(externalLink);
        conceptImpl.setExternalLinks(arrayList);
      } 
    } 
    Collection collection2 = null;
    IdentifierBag identifierBag = paramTModel.getIdentifierBag();
    if (identifierBag != null) {
      collection2 = identifierBag2ExternalIdentifiers(identifierBag);
      if (collection2 != null && !collection2.isEmpty())
        conceptImpl.setExternalIdentifiers(collection2); 
    } 
    Name name = paramTModel.getName();
    InternationalString internationalString1 = name2InternationalString(name);
    conceptImpl.setName(internationalString1);
    List list = paramTModel.getDescription();
    InternationalString internationalString2 = descriptions2InternationalString(list);
    conceptImpl.setDescription(internationalString2);
    this.objectManager.addObjectToCache((RegistryObjectImpl)conceptImpl, this.service.getServiceId());
    return (RegistryObject)conceptImpl;
  }
  
  boolean isClassificationScheme(Classification paramClassification) throws JAXRException {
    boolean bool = false;
    String str = paramClassification.getValue();
    if (str == null)
      return bool; 
    if (str.equalsIgnoreCase("identifier")) {
      bool = true;
    } else if (str.equalsIgnoreCase("namespace")) {
      bool = true;
    } else if (str.equalsIgnoreCase("categorization")) {
      bool = true;
    } else if (str.equalsIgnoreCase("postalAddress")) {
      bool = true;
    } 
    return bool;
  }
  
  KeyedReference externalIdentifier2KeyedReference(ExternalIdentifier paramExternalIdentifier) throws JAXRException {
    KeyedReference keyedReference = null;
    keyedReference = this.objFactory.createKeyedReference();
    ClassificationScheme classificationScheme = paramExternalIdentifier.getIdentificationScheme();
    if (classificationScheme == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:IdentificationScheme_missing_-_this_must_be_supplied")); 
    Key key = classificationScheme.getKey();
    String str1 = null;
    if (key != null)
      str1 = key.getId(); 
    if (key != null) {
      keyedReference.setTModelKey(str1);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_IdentificationScheme_Key_-_this_must_be_supplied"));
    } 
    String str2 = null;
    try {
      str2 = paramExternalIdentifier.getName().getValue();
    } catch (NullPointerException nullPointerException) {}
    if (str2 != null) {
      keyedReference.setKeyName(str2);
    } else {
      keyedReference.setKeyName("");
    } 
    String str3 = paramExternalIdentifier.getValue();
    if (str3 != null) {
      keyedReference.setKeyValue(str3);
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_ExternialIdentifier_Value_supplied_-_this_must_be_supplied"));
    } 
    return keyedReference;
  }
  
  ExternalIdentifier keyedReference2ExternalIdentifier(KeyedReference paramKeyedReference) throws JAXRException {
    ExternalIdentifierImpl externalIdentifierImpl = null;
    if (paramKeyedReference != null) {
      String str1 = paramKeyedReference.getTModelKey();
      String str2 = paramKeyedReference.getKeyName();
      String str3 = paramKeyedReference.getKeyValue();
      ClassificationSchemeImpl classificationSchemeImpl = new ClassificationSchemeImpl();
      classificationSchemeImpl.setKey((Key)new KeyImpl(str1));
      classificationSchemeImpl.setName((InternationalString)new InternationalStringImpl(str2));
      externalIdentifierImpl = new ExternalIdentifierImpl((ClassificationScheme)classificationSchemeImpl, str2, str3);
      externalIdentifierImpl.setRegistryService(this.service);
    } 
    return (ExternalIdentifier)externalIdentifierImpl;
  }
  
  KeyedReference concept2KeyedReference(Concept paramConcept) throws JAXRException {
    ConceptImpl conceptImpl = (ConceptImpl)paramConcept;
    KeyedReference keyedReference = null;
    keyedReference = this.objFactory.createKeyedReference();
    if (paramConcept != null) {
      ClassificationScheme classificationScheme = paramConcept.getClassificationScheme();
      if (classificationScheme != null) {
        Key key = classificationScheme.getKey();
        if (key == null)
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Root_ClassificationScheme_key_supplied")); 
        String str1 = key.getId();
        String str2 = null;
        if (conceptImpl.getName() != null)
          str2 = conceptImpl.getName().getValue(); 
        String str3 = conceptImpl.getValue();
        if (str2 != null) {
          keyedReference.setKeyName(str2);
        } else {
          keyedReference.setKeyName("Unknown");
        } 
        if (str3 != null) {
          keyedReference.setKeyValue(str3);
        } else {
          return null;
        } 
        if (str1 != null) {
          keyedReference.setTModelKey(str1);
        } else {
          return null;
        } 
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Root_Concept_for_this_concept__") + paramConcept.getName());
      } 
    } 
    return keyedReference;
  }
  
  KeyedReference classification2KeyedReference(ClassificationScheme paramClassificationScheme, String paramString1, String paramString2) throws JAXRException {
    KeyedReference keyedReference = null;
    if (paramClassificationScheme != null) {
      keyedReference = null;
      keyedReference = this.objFactory.createKeyedReference();
      Key key = paramClassificationScheme.getKey();
      String str = null;
      if (key != null)
        str = key.getId(); 
      if (paramString1 != null) {
        keyedReference.setKeyName(paramString1);
      } else {
        keyedReference.setKeyName("");
      } 
      if (paramString2 != null) {
        keyedReference.setKeyValue(paramString2);
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Classification_must_have_a_value_"));
      } 
      if (str != null) {
        keyedReference.setTModelKey(str);
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Parent_ClassificationScheme_key_must_have_a_value_"));
      } 
    } 
    return keyedReference;
  }
  
  Concept keyedReference2Concept(KeyedReference paramKeyedReference) throws JAXRException {
    ConceptImpl conceptImpl = null;
    String str1 = paramKeyedReference.getTModelKey();
    String str2 = paramKeyedReference.getKeyName();
    String str3 = paramKeyedReference.getKeyValue();
    conceptImpl = new ConceptImpl((Key)new KeyImpl(str1), str2, str3);
    conceptImpl.setName((InternationalString)new InternationalStringImpl(str2));
    conceptImpl.setIsRetrieved(true);
    conceptImpl.setIsLoaded(false);
    conceptImpl.setIsNew(false);
    this.objectManager.addObjectToCache((RegistryObjectImpl)conceptImpl, this.service.getServiceId());
    return (Concept)conceptImpl;
  }
  
  Classification keyedReference2Classification(KeyedReference paramKeyedReference) throws JAXRException {
    String str1 = paramKeyedReference.getTModelKey();
    String str2 = paramKeyedReference.getKeyName();
    String str3 = paramKeyedReference.getKeyValue();
    ClassificationSchemeImpl classificationSchemeImpl = (ClassificationSchemeImpl)getConceptsManager().getClassificationSchemeById(str1);
    if (classificationSchemeImpl == null)
      try {
        classificationSchemeImpl = (ClassificationSchemeImpl)this.objectManager.fetchObjectFromCache(str1);
      } catch (ClassCastException classCastException) {
        this.logger.finest("ClassCastException in keyedRef2Classification feting classificationScheme");
        classificationSchemeImpl = null;
      }  
    if (classificationSchemeImpl == null) {
      classificationSchemeImpl = new ClassificationSchemeImpl();
      classificationSchemeImpl.setKey((Key)new KeyImpl(str1));
      classificationSchemeImpl.setIsLoaded(false);
      classificationSchemeImpl.setIsNew(false);
      classificationSchemeImpl.setIsRetrieved(true);
      this.objectManager.addObjectToCache((RegistryObjectImpl)classificationSchemeImpl, this.service.getServiceId());
    } 
    ClassificationImpl classificationImpl = new ClassificationImpl();
    classificationImpl.setClassificationScheme((ClassificationScheme)classificationSchemeImpl);
    classificationImpl.setName((InternationalString)new InternationalStringImpl(str2));
    classificationImpl.setValue(str3);
    classificationImpl.setRegistryService(this.service);
    return (Classification)classificationImpl;
  }
  
  KeyedReference key2KeyedReference(Key paramKey) throws JAXRException {
    KeyedReference keyedReference = null;
    keyedReference = this.objFactory.createKeyedReference();
    if (paramKey != null) {
      String str = paramKey.getId();
      keyedReference.setTModelKey(str);
    } 
    return keyedReference;
  }
  
  OverviewDoc externalLink2OverviewDoc(ExternalLink paramExternalLink, boolean paramBoolean) throws JAXRException {
    ExternalLinkImpl externalLinkImpl = (ExternalLinkImpl)paramExternalLink;
    OverviewDoc overviewDoc = null;
    overviewDoc = this.objFactory.createOverviewDoc();
    if (paramExternalLink != null) {
      String str = externalLinkImpl.getExternalURI();
      overviewDoc.setOverviewURL(str);
      Collection collection = getDescriptions((RegistryObject)externalLinkImpl, paramBoolean);
      overviewDoc.getDescription().addAll(collection);
    } 
    return overviewDoc;
  }
  
  ExternalLink overviewDoc2ExternalLink(OverviewDoc paramOverviewDoc) throws JAXRException {
    ExternalLinkImpl externalLinkImpl = null;
    if (paramOverviewDoc != null) {
      String str = paramOverviewDoc.getOverviewURL();
      List list = paramOverviewDoc.getDescription();
      InternationalString internationalString = descriptions2InternationalString(list);
      externalLinkImpl = new ExternalLinkImpl();
      externalLinkImpl.setValidateURI(false);
      externalLinkImpl.setExternalURI(str);
      if (internationalString != null)
        externalLinkImpl.setDescription(internationalString); 
    } 
    return (ExternalLink)externalLinkImpl;
  }
  
  Collection tModelInstanceInfos2SpecificationLinks(Collection paramCollection) throws JAXRException {
    ArrayList<SpecificationLinkImpl> arrayList = new ArrayList();
    Iterator<TModelInstanceInfo> iterator = paramCollection.iterator();
    if (paramCollection != null) {
      while (iterator.hasNext()) {
        SpecificationLinkImpl specificationLinkImpl = new SpecificationLinkImpl();
        specificationLinkImpl.setRegistryService(this.service);
        TModelInstanceInfo tModelInstanceInfo = iterator.next();
        String str = tModelInstanceInfo.getTModelKey();
        List list = tModelInstanceInfo.getDescription();
        InstanceDetails instanceDetails = tModelInstanceInfo.getInstanceDetails();
        ConceptImpl conceptImpl = null;
        if (str != null) {
          try {
            conceptImpl = (ConceptImpl)this.objectManager.fetchObjectFromCache(str);
          } catch (ClassCastException classCastException) {
            this.logger.finest("ClassCastException in tModelInstanceInfos2SpecificationLinks fetch concept, continuing");
            conceptImpl = null;
          } 
          if (conceptImpl == null) {
            conceptImpl = new ConceptImpl();
            conceptImpl.setRegistryService(this.service);
            conceptImpl.setKey((Key)new KeyImpl(str));
            conceptImpl.setStatusFlags(true, false, false);
            conceptImpl.setRegistryService(this.service);
            this.objectManager.addObjectToCache((RegistryObjectImpl)conceptImpl, this.service.getServiceId());
          } 
          if (conceptImpl != null) {
            specificationLinkImpl.setSpecificationObject((RegistryObject)conceptImpl);
            this.logger.finest("setting specification concept");
          } 
        } 
        if (instanceDetails != null) {
          this.logger.finest("have instance Details");
          OverviewDoc overviewDoc = instanceDetails.getOverviewDoc();
          String str1 = instanceDetails.getInstanceParms();
          List list1 = instanceDetails.getDescription();
          InternationalString internationalString = descriptions2InternationalString(list1);
          if (internationalString != null)
            specificationLinkImpl.setUsageDescription(internationalString); 
          if (str1 != null) {
            ArrayList<String> arrayList1 = new ArrayList();
            arrayList1.add(str1);
            specificationLinkImpl.setUsageParameters(arrayList1);
          } 
          ExternalLinkImpl externalLinkImpl = null;
          if (overviewDoc != null) {
            this.logger.finest("oDoc is found");
            externalLinkImpl = (ExternalLinkImpl)overviewDoc2ExternalLink(overviewDoc);
            if (externalLinkImpl == null)
              this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Exlink_is_null")); 
          } 
          if (externalLinkImpl != null) {
            externalLinkImpl.setRegistryService(this.service);
            specificationLinkImpl.addExternalLink((ExternalLink)externalLinkImpl);
          } 
        } 
        if (specificationLinkImpl != null) {
          this.logger.finest("Adding specificationLink");
          arrayList.add(specificationLinkImpl);
        } 
      } 
      return arrayList;
    } 
    return null;
  }
  
  Organization businessInfo2Organization(BusinessInfo paramBusinessInfo) throws JAXRException {
    OrganizationImpl organizationImpl = null;
    String str = paramBusinessInfo.getBusinessKey();
    if (getConnection().useCache())
      try {
        organizationImpl = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str);
      } catch (ClassCastException classCastException) {
        this.logger.finest("CLassCastException businessInfo2Org on fetch from cache, continuing");
        organizationImpl = null;
      }  
    if (organizationImpl == null) {
      organizationImpl = new OrganizationImpl();
      organizationImpl.setServiceId(this.service.getServiceId());
    } 
    organizationImpl.setRegistryService(this.service);
    organizationImpl.setIsRetrieved(false);
    List list1 = paramBusinessInfo.getName();
    InternationalString internationalString1 = names2InternationalString(list1);
    if (internationalString1 != null)
      organizationImpl.setName(internationalString1); 
    if (str != null)
      organizationImpl.setKey((Key)new KeyImpl(str)); 
    List list2 = paramBusinessInfo.getDescription();
    InternationalString internationalString2 = descriptions2InternationalString(list2);
    if (internationalString2 != null)
      organizationImpl.setDescription(internationalString2); 
    ServiceInfos serviceInfos = paramBusinessInfo.getServiceInfos();
    if (serviceInfos != null) {
      ArrayList<ServiceImpl> arrayList = new ArrayList();
      List list = serviceInfos.getServiceInfo();
      Iterator<ServiceInfo> iterator = list.iterator();
      while (iterator.hasNext()) {
        ServiceImpl serviceImpl = (ServiceImpl)serviceInfo2Service(iterator.next());
        if (serviceImpl != null) {
          serviceImpl.setServiceId(this.service.getServiceId());
          serviceImpl.setRegistryService(this.service);
          arrayList.add(serviceImpl);
        } 
      } 
      if (arrayList != null)
        organizationImpl.setServices(arrayList); 
    } 
    organizationImpl.setIsRetrieved(true);
    organizationImpl.setIsNew(false);
    if (!getConnection().useCache())
      organizationImpl.setIsLoaded(false); 
    this.objectManager.addObjectToCache((RegistryObjectImpl)organizationImpl, this.service.getServiceId());
    return (Organization)organizationImpl;
  }
  
  Collection relatedBusinessInfo2Associations(RelatedBusinessInfo paramRelatedBusinessInfo, Collection paramCollection) throws JAXRException {
    OrganizationImpl organizationImpl1 = null;
    OrganizationImpl organizationImpl2 = null;
    if (paramCollection == null)
      this.logger.finest("Keys are null"); 
    if (paramCollection != null && !paramCollection.isEmpty()) {
      Iterator<KeyImpl> iterator = paramCollection.iterator();
      KeyImpl keyImpl = iterator.next();
      String str1 = keyImpl.getId();
      organizationImpl2 = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str1);
      if (organizationImpl2 == null)
        organizationImpl2 = (OrganizationImpl)getRegistryObject(str1, "Organization"); 
    } 
    String str = paramRelatedBusinessInfo.getBusinessKey();
    this.logger.finest("Related Business Key " + str);
    organizationImpl1 = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str);
    if (organizationImpl1 == null) {
      organizationImpl1 = new OrganizationImpl();
      organizationImpl1.setServiceId(this.service.getServiceId());
      organizationImpl1.setRegistryService(this.service);
    } 
    organizationImpl1.setIsRetrieved(true);
    organizationImpl1.setIsNew(false);
    if (str != null)
      organizationImpl1.setKey((Key)new KeyImpl(str)); 
    List list1 = paramRelatedBusinessInfo.getName();
    InternationalString internationalString1 = names2InternationalString(list1);
    if (internationalString1 != null)
      organizationImpl1.setName(internationalString1); 
    if (str != null)
      organizationImpl1.setKey((Key)new KeyImpl(str)); 
    List list2 = paramRelatedBusinessInfo.getDescription();
    InternationalString internationalString2 = descriptions2InternationalString(list2);
    if (internationalString2 != null)
      organizationImpl1.setDescription(internationalString2); 
    List list3 = paramRelatedBusinessInfo.getSharedRelationships();
    Collection collection = sharedRelationships2Associations((Organization)organizationImpl1, (Organization)organizationImpl2, list3);
    if (collection != null)
      this.logger.finest("RelatedBusinessInfo2Associations : associations = " + collection.size()); 
    this.objectManager.addObjectToCache((RegistryObjectImpl)organizationImpl1, this.service.getServiceId());
    return collection;
  }
  
  Collection sharedRelationships2Associations(Organization paramOrganization1, Organization paramOrganization2, Collection paramCollection) throws JAXRException {
    ArrayList<AssociationImpl> arrayList = new ArrayList();
    for (SharedRelationships sharedRelationships : paramCollection) {
      Direction direction = sharedRelationships.getDirection();
      String str1 = direction.value();
      Organization organization1 = null;
      Organization organization2 = null;
      String str2 = null;
      String str3 = null;
      if (str1.equalsIgnoreCase("fromKey")) {
        organization1 = paramOrganization1;
        str2 = paramOrganization1.getKey().getId();
        organization2 = paramOrganization2;
        str3 = paramOrganization2.getKey().getId();
      } else if (str1.equalsIgnoreCase("toKey")) {
        organization1 = paramOrganization2;
        str2 = paramOrganization2.getKey().getId();
        organization2 = paramOrganization1;
        str3 = paramOrganization1.getKey().getId();
      } 
      List list = sharedRelationships.getKeyedReference();
      Collection collection = keyedReferences2AssociationTypes(list);
      for (Concept concept : collection) {
        AssociationImpl associationImpl = new AssociationImpl();
        associationImpl.setAssociationType(concept);
        associationImpl.setSourceObject((RegistryObject)organization1);
        associationImpl.setTargetObject((RegistryObject)organization2);
        associationImpl.setRegistryService(this.service);
        if (!str2.equals(str3))
          associationImpl.setIsExtramural(true); 
        KeyImpl keyImpl = buildAssociationKey(str2, str3, concept.getValue());
        associationImpl.setKey((Key)keyImpl);
        associationImpl.setIsConfirmedBySourceOwner(true);
        associationImpl.setIsConfirmedByTargetOwner(true);
        arrayList.add(associationImpl);
      } 
    } 
    return arrayList;
  }
  
  Collection keyedReferences2AssociationTypes(Collection paramCollection) throws JAXRException {
    ArrayList<ConceptImpl> arrayList = null;
    if (paramCollection != null) {
      Iterator<KeyedReference> iterator = paramCollection.iterator();
      arrayList = new ArrayList();
      while (iterator.hasNext()) {
        KeyedReference keyedReference = iterator.next();
        String str1 = keyedReference.getTModelKey();
        String str2 = keyedReference.getKeyName();
        String str3 = keyedReference.getKeyValue();
        String str4 = null;
        String str5 = null;
        if (str3.equals("peer-peer")) {
          str4 = "RelatedTo";
          str5 = "RelatedTo";
        } else if (str3.equals("identity")) {
          str4 = "EquivalentTo";
          str5 = "EquivalentTo";
        } else if (str3.equals("parent-child")) {
          str4 = "HasChild";
          str5 = "HasChild";
        } else {
          str4 = str2;
          str5 = str3;
        } 
        ConceptImpl conceptImpl = new ConceptImpl();
        if (str2 != null)
          conceptImpl.setName((InternationalString)new InternationalStringImpl(str4)); 
        if (str3 != null)
          conceptImpl.setValue(str5); 
        if (str1 != null)
          conceptImpl.setKey((Key)new KeyImpl(str1)); 
        arrayList.add(conceptImpl);
      } 
    } 
    return arrayList;
  }
  
  Organization businessEntity2Organization(BusinessEntity paramBusinessEntity) throws JAXRException {
    if (paramBusinessEntity == null)
      return null; 
    OrganizationImpl organizationImpl = null;
    String str1 = paramBusinessEntity.getBusinessKey();
    try {
      organizationImpl = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str1);
    } catch (ClassCastException classCastException) {
      this.logger.finest("ClassCastException fetching org in businessEntity2Org, continuing");
    } 
    if (organizationImpl == null) {
      organizationImpl = new OrganizationImpl();
      organizationImpl.setServiceId(this.service.getServiceId());
      organizationImpl.setRegistryService(this.service);
    } 
    organizationImpl.setIsRetrieved(true);
    organizationImpl.setIsNew(false);
    organizationImpl.setIsLoaded(true);
    String str2 = paramBusinessEntity.getOperator();
    SlotImpl slotImpl1 = null;
    if (str2 != null) {
      slotImpl1 = new SlotImpl("operator", str2, null);
      if (slotImpl1 != null)
        organizationImpl.addSlot((Slot)slotImpl1); 
    } 
    String str3 = paramBusinessEntity.getAuthorizedName();
    SlotImpl slotImpl2 = null;
    if (str3 != null) {
      slotImpl2 = new SlotImpl("authorizedName", str3, null);
      if (slotImpl2 != null)
        organizationImpl.addSlot((Slot)slotImpl2); 
    } 
    List list1 = paramBusinessEntity.getName();
    List list2 = paramBusinessEntity.getDescription();
    Contacts contacts = paramBusinessEntity.getContacts();
    BusinessServices businessServices = paramBusinessEntity.getBusinessServices();
    Collection collection1 = null;
    IdentifierBag identifierBag = paramBusinessEntity.getIdentifierBag();
    if (identifierBag != null) {
      collection1 = identifierBag2ExternalIdentifiers(identifierBag);
      if (collection1 != null)
        organizationImpl.setExternalIdentifiers(collection1); 
    } 
    Collection collection2 = null;
    CategoryBag categoryBag = paramBusinessEntity.getCategoryBag();
    if (categoryBag != null) {
      collection2 = categoryBag2Classifications(categoryBag);
      if (collection2 != null)
        organizationImpl.setClassifications(collection2); 
    } 
    Collection collection3 = null;
    DiscoveryURLs discoveryURLs = paramBusinessEntity.getDiscoveryURLs();
    if (discoveryURLs != null) {
      collection3 = discoveryURLs2ExternalLinks(discoveryURLs);
      if (collection3 != null)
        organizationImpl.setExternalLinks(collection3); 
    } 
    if (str1 != null)
      organizationImpl.setKey((Key)new KeyImpl(str1)); 
    if (list1 != null) {
      InternationalString internationalString = names2InternationalString(list1);
      if (internationalString != null)
        organizationImpl.setName(internationalString); 
    } 
    if (list2 != null) {
      InternationalString internationalString = descriptions2InternationalString(list2);
      if (internationalString != null)
        organizationImpl.setDescription(internationalString); 
    } 
    if (businessServices != null) {
      List list = businessServices.getBusinessService();
      ArrayList<Service> arrayList = new ArrayList();
      if (list != null) {
        Iterator<BusinessService> iterator = list.iterator();
        while (iterator.hasNext()) {
          Service service = businessService2Service(iterator.next());
          if (service != null)
            arrayList.add(service); 
        } 
        if (arrayList != null)
          organizationImpl.setServices(arrayList); 
      } 
    } 
    if (contacts != null) {
      List list = contacts.getContact();
      if (list != null) {
        Iterator<Contact> iterator = list.iterator();
        Collection collection = organizationImpl.getUsers();
        organizationImpl.removeUsers(collection);
        byte b = 0;
        while (iterator.hasNext()) {
          UserImpl userImpl = (UserImpl)contact2User(iterator.next());
          userImpl.setIsLoaded(true);
          userImpl.setRegistryService(this.service);
          if (userImpl != null) {
            userImpl.setSubmittingOrganization((Organization)organizationImpl);
            userImpl.setOrganization((Organization)organizationImpl);
          } 
          if (!b) {
            organizationImpl.setPrimaryContact((User)userImpl);
            b++;
            continue;
          } 
          organizationImpl.addUser((User)userImpl);
        } 
      } 
    } 
    this.objectManager.addObjectToCache((RegistryObjectImpl)organizationImpl, this.service.getServiceId());
    return (Organization)organizationImpl;
  }
  
  Collection discoveryURLs2ExternalLinks(DiscoveryURLs paramDiscoveryURLs) throws JAXRException {
    ArrayList<ExternalLinkImpl> arrayList = null;
    List list = paramDiscoveryURLs.getDiscoveryURL();
    if (list != null) {
      arrayList = new ArrayList();
      for (DiscoveryURL discoveryURL : list) {
        String str1 = discoveryURL.getValue();
        String str2 = discoveryURL.getUseType();
        ExternalLinkImpl externalLinkImpl = new ExternalLinkImpl();
        externalLinkImpl.setRegistryService(this.service);
        externalLinkImpl.setValidateURI(false);
        if (str1 != null)
          externalLinkImpl.setExternalURI(str1); 
        if (str2 != null) {
          externalLinkImpl.setName((InternationalString)new InternationalStringImpl(str2));
        } else {
          externalLinkImpl.setName((InternationalString)new InternationalStringImpl("ExternalLink"));
        } 
        arrayList.add(externalLinkImpl);
      } 
    } 
    return arrayList;
  }
  
  User contact2User(Contact paramContact) throws JAXRException {
    if (paramContact != null) {
      String str1 = paramContact.getUseType();
      List list1 = paramContact.getDescription();
      String str2 = paramContact.getPersonName();
      List list2 = paramContact.getPhone();
      List list3 = paramContact.getEmail();
      List list4 = paramContact.getAddress();
      UserImpl userImpl = new UserImpl();
      if (str2 != null)
        userImpl.setPersonName((PersonName)new PersonNameImpl(str2)); 
      if (str1 != null)
        userImpl.setType(str1); 
      if (list1 != null) {
        InternationalString internationalString = descriptions2InternationalString(list1);
        if (internationalString != null)
          userImpl.setDescription(internationalString); 
      } 
      if (list2 != null) {
        Collection collection = phones2TelephoneNumbers(list2);
        userImpl.setTelephoneNumbers(collection);
      } 
      if (list3 != null) {
        Collection collection = emails2EmailAddresses(list3);
        if (collection.size() > 0)
          userImpl.setEmailAddresses(collection); 
      } 
      if (list4 != null) {
        Collection collection = addresses2PostalAddresses(list4);
        if (collection.size() > 0)
          userImpl.setPostalAddresses(collection); 
      } 
      return (User)userImpl;
    } 
    return null;
  }
  
  private PostalAddress address2PostalAddress(Address paramAddress) throws JAXRException {
    PostalAddressImpl postalAddressImpl;
    PostalAddress postalAddress = null;
    if (paramAddress != null) {
      initPostalSchemes();
      List list = paramAddress.getAddressLine();
      String str1 = paramAddress.getTModelKey();
      this.logger.finest("PostalSchemeId retrieved = " + str1);
      if (str1 != null) {
        ClassificationScheme classificationScheme = this.service.getDefaultPostalScheme();
        if (classificationScheme != null && classificationScheme.getKey().getId().equalsIgnoreCase(str1)) {
          if (this.postalAddressMap == null)
            mapPostalAddressAttributes(classificationScheme); 
          if (this.postalAddressMap == null) {
            this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_PostalAddressMapping"));
          } else {
            postalAddress = addressLines2PostalAddressEquivalence(classificationScheme, list);
          } 
        } 
      } 
      if (postalAddress == null && list != null) {
        postalAddressImpl = new PostalAddressImpl();
        SlotImpl slotImpl = new SlotImpl();
        slotImpl.setName("addressLines");
        ArrayList<String> arrayList = new ArrayList();
        Iterator<AddressLine> iterator = list.iterator();
        while (iterator.hasNext())
          arrayList.add(((AddressLine)iterator.next()).getValue()); 
        slotImpl.setValues(arrayList);
        postalAddressImpl.addSlot((Slot)slotImpl);
      } 
      String str2 = paramAddress.getUseType();
      if (str2 != null)
        postalAddressImpl.setType(str2); 
      String str3 = paramAddress.getSortCode();
      if (str3 != null) {
        SlotImpl slotImpl = new SlotImpl();
        slotImpl.setName("sortCode");
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add(str3);
        slotImpl.setValues(arrayList);
        postalAddressImpl.addSlot((Slot)slotImpl);
      } 
    } 
    return (PostalAddress)postalAddressImpl;
  }
  
  PostalAddress addressLines2PostalAddressEquivalence(ClassificationScheme paramClassificationScheme, Collection paramCollection) throws JAXRException {
    SlotImpl slotImpl = null;
    ArrayList<String> arrayList = null;
    PostalAddressImpl postalAddressImpl = null;
    if (this.postalAddressMap == null)
      return null; 
    if (paramClassificationScheme != null && paramCollection != null) {
      PostalAddress postalAddress;
      PostalAddressImpl postalAddressImpl1;
      Collection collection = paramClassificationScheme.getChildrenConcepts();
      for (AddressLine addressLine : paramCollection) {
        String str1 = addressLine.getKeyValue();
        String str2 = addressLine.getKeyName();
        if (str1 != null) {
          for (Concept concept : collection) {
            if (this.postalAddressMap.containsValue(concept)) {
              Set set = this.postalAddressMap.keySet();
              Iterator<String> iterator = set.iterator();
              String str = concept.getValue();
              label47: while (iterator.hasNext()) {
                String str3 = iterator.next();
                Concept concept1 = (Concept)this.postalAddressMap.get(str3);
                if (concept1.getValue().equalsIgnoreCase(str1)) {
                  if (postalAddressImpl == null) {
                    postalAddressImpl = new PostalAddressImpl();
                    break label47;
                  } 
                  postalAddress = mapEquivalentLine2PostalAttribute(str3, addressLine, (PostalAddress)postalAddressImpl);
                } 
              } 
            } 
          } 
          continue;
        } 
        if (str2 != null) {
          ClassificationScheme classificationScheme = findClassificationSchemeByName(null, "PostalAddressAttributes");
          if (classificationScheme != null) {
            Collection collection1 = classificationScheme.getChildrenConcepts();
            for (Concept concept : collection1) {
              String str = concept.getName().getValue();
              if (str.equalsIgnoreCase(str2) && postalAddress == null) {
                postalAddressImpl1 = new PostalAddressImpl();
                postalAddress = mapEquivalentLine2PostalAttribute(str2, addressLine, (PostalAddress)postalAddressImpl1);
              } 
            } 
          } 
          continue;
        } 
        if (arrayList == null)
          arrayList = new ArrayList(); 
        arrayList.add(addressLine.getValue());
      } 
      if (arrayList != null && arrayList.size() > 0) {
        if (postalAddress == null)
          postalAddressImpl1 = new PostalAddressImpl(); 
        slotImpl = new SlotImpl();
        slotImpl.setName("addressLines");
        slotImpl.setValues(arrayList);
        postalAddressImpl1.addSlot((Slot)slotImpl);
      } 
      return (PostalAddress)postalAddressImpl1;
    } 
    return null;
  }
  
  PostalAddress mapEquivalentLine2PostalAttribute(String paramString, AddressLine paramAddressLine, PostalAddress paramPostalAddress) throws JAXRException {
    String str = paramAddressLine.getValue();
    if (str != null)
      if (paramString.equalsIgnoreCase("StreetNumber")) {
        paramPostalAddress.setStreetNumber(str);
      } else if (paramString.equalsIgnoreCase("Street")) {
        paramPostalAddress.setStreet(str);
      } else if (paramString.equalsIgnoreCase("City")) {
        paramPostalAddress.setCity(str);
      } else if (paramString.equalsIgnoreCase("State")) {
        paramPostalAddress.setStateOrProvince(str);
      } else if (paramString.equalsIgnoreCase("PostalCode")) {
        paramPostalAddress.setPostalCode(str);
      } else if (paramString.equalsIgnoreCase("Country")) {
        paramPostalAddress.setCountry(str);
      } else {
        this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_matching_postal_Address_Attribute"));
      }  
    return paramPostalAddress;
  }
  
  Collection addresses2PostalAddresses(Collection paramCollection) throws JAXRException {
    ArrayList<PostalAddress> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      Iterator<Address> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        PostalAddress postalAddress = address2PostalAddress(iterator.next());
        if (postalAddress != null)
          arrayList.add(postalAddress); 
      } 
      return arrayList;
    } 
    return null;
  }
  
  Collection emails2EmailAddresses(Collection paramCollection) throws JAXRException {
    ArrayList<EmailAddressImpl> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      for (Email email : paramCollection) {
        String str1 = email.getValue();
        String str2 = email.getUseType();
        EmailAddressImpl emailAddressImpl = new EmailAddressImpl();
        if (str1 != null)
          emailAddressImpl.setAddress(str1); 
        if (str2 != null)
          emailAddressImpl.setType(str2); 
        arrayList.add(emailAddressImpl);
      } 
    } 
    return arrayList;
  }
  
  Collection phones2TelephoneNumbers(Collection paramCollection) throws JAXRException {
    ArrayList<TelephoneNumberImpl> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList(paramCollection.size());
      Iterator<Phone> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        TelephoneNumberImpl telephoneNumberImpl = new TelephoneNumberImpl();
        Phone phone = iterator.next();
        String str1 = phone.getValue();
        if (str1 != null)
          telephoneNumberImpl.setNumber(str1); 
        String str2 = phone.getUseType();
        if (str2 != null)
          telephoneNumberImpl.setType(str2); 
        arrayList.add(telephoneNumberImpl);
      } 
      return arrayList;
    } 
    return null;
  }
  
  ServiceBinding bindingTemplate2ServiceBinding(BindingTemplate paramBindingTemplate) throws JAXRException {
    ServiceBindingImpl serviceBindingImpl = null;
    ServiceImpl serviceImpl = null;
    String str1 = paramBindingTemplate.getBindingKey();
    String str2 = paramBindingTemplate.getServiceKey();
    try {
      serviceImpl = (ServiceImpl)this.objectManager.fetchObjectFromCache(str2);
    } catch (ClassCastException classCastException) {
      this.logger.finest("ClassCastException in bindingTemplate2ServiceBinding fetch service, continuing");
      serviceImpl = null;
    } 
    try {
      serviceBindingImpl = (ServiceBindingImpl)this.objectManager.fetchObjectFromCache(str1);
    } catch (ClassCastException classCastException) {
      this.logger.finest("ClassCastException in bindingTemplate2ServicBinding fetchin serviceBinding, continuing");
      serviceBindingImpl = null;
    } 
    if (serviceBindingImpl == null) {
      serviceBindingImpl = new ServiceBindingImpl();
      serviceBindingImpl.setServiceId(this.service.getServiceId());
    } 
    serviceBindingImpl.setRegistryService(this.service);
    serviceBindingImpl.setIsLoaded(true);
    serviceBindingImpl.setIsRetrieved(false);
    serviceBindingImpl.setIsNew(false);
    if (serviceImpl != null) {
      serviceImpl.setIsRetrieved(true);
      serviceBindingImpl.setService((Service)serviceImpl);
    } 
    serviceBindingImpl.setKey((Key)new KeyImpl(str1));
    List list = paramBindingTemplate.getDescription();
    InternationalString internationalString = descriptions2InternationalString(list);
    serviceBindingImpl.setDescription(internationalString);
    AccessPoint accessPoint = null;
    HostingRedirector hostingRedirector = null;
    if (paramBindingTemplate != null) {
      accessPoint = paramBindingTemplate.getAccessPoint();
      hostingRedirector = paramBindingTemplate.getHostingRedirector();
    } 
    if (accessPoint != null) {
      URLType uRLType = accessPoint.getURLType();
      if (uRLType != null) {
        Concept concept = urlType2Concept(uRLType);
        ClassificationImpl classificationImpl = new ClassificationImpl(concept);
        classificationImpl.setRegistryService(this.service);
        serviceBindingImpl.addClassification((Classification)classificationImpl);
      } 
      String str = accessPoint.getValue();
      if (str != null) {
        serviceBindingImpl.setValidateURI(false);
        serviceBindingImpl.setAccessURI(str);
      } 
    } 
    if (hostingRedirector != null) {
      ServiceBinding serviceBinding = hostingRedirector2TargetBinding(hostingRedirector);
      if (serviceBinding != null) {
        ((ServiceBindingImpl)serviceBinding).setRegistryService(this.service);
        serviceBindingImpl.setTargetBinding(serviceBinding);
      } 
    } 
    TModelInstanceDetails tModelInstanceDetails = paramBindingTemplate.getTModelInstanceDetails();
    if (tModelInstanceDetails != null) {
      List list1 = tModelInstanceDetails.getTModelInstanceInfo();
      if (list1 != null) {
        this.logger.finest("Got InstanceInfo");
        Collection collection = tModelInstanceInfos2SpecificationLinks(list1);
        if (collection != null && collection.size() > 0)
          serviceBindingImpl.setSpecificationLinks(collection); 
      } 
    } 
    if (getConnection().useCache()) {
      this.objectManager.addObjectToCache((RegistryObjectImpl)serviceBindingImpl, this.service.getServiceId());
    } else {
      this.objectManager.removeObjectFromCache(serviceBindingImpl.getKey().getId());
    } 
    return (ServiceBinding)serviceBindingImpl;
  }
  
  Service serviceInfo2Service(ServiceInfo paramServiceInfo) throws JAXRException {
    ServiceImpl serviceImpl = null;
    String str1 = paramServiceInfo.getServiceKey();
    String str2 = paramServiceInfo.getBusinessKey();
    Object object = null;
    if (getConnection().useCache())
      try {
        serviceImpl = (ServiceImpl)this.objectManager.fetchObjectFromCache(str1);
      } catch (ClassCastException classCastException) {
        this.logger.finest("ClassCastException on fetch of service, serviceInfo2Service, continuing");
        serviceImpl = null;
      }  
    if (serviceImpl == null) {
      serviceImpl = new ServiceImpl();
      serviceImpl.setServiceId(this.service.getServiceId());
    } 
    serviceImpl.setRegistryService(this.service);
    serviceImpl.setIsRetrieved(false);
    serviceImpl.setKey((Key)new KeyImpl(str1));
    OrganizationImpl organizationImpl = null;
    if (str2 != null) {
      try {
        organizationImpl = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str2);
      } catch (ClassCastException classCastException) {
        this.logger.finest("ClassCastException fetching providing Org, serviceInfo2Service, continuing");
        organizationImpl = null;
      } 
      if (organizationImpl == null) {
        organizationImpl = new OrganizationImpl();
        organizationImpl.setKey((Key)new KeyImpl(str2));
        organizationImpl.setIsRetrieved(true);
        organizationImpl.setRegistryService(this.service);
        organizationImpl.setIsNew(false);
        this.objectManager.addObjectToCache((RegistryObjectImpl)organizationImpl, this.service.getServiceId());
      } 
      if (organizationImpl != null)
        serviceImpl.setProvidingOrganization((Organization)organizationImpl); 
    } 
    List list = paramServiceInfo.getName();
    InternationalString internationalString = names2InternationalString(list);
    serviceImpl.setName(internationalString);
    serviceImpl.setIsRetrieved(true);
    serviceImpl.setIsNew(false);
    if (!getConnection().useCache())
      serviceImpl.setIsLoaded(false); 
    this.objectManager.addObjectToCache((RegistryObjectImpl)serviceImpl, this.service.getServiceId());
    return (Service)serviceImpl;
  }
  
  Service businessService2Service(BusinessService paramBusinessService) throws JAXRException {
    ServiceImpl serviceImpl = null;
    String str1 = paramBusinessService.getServiceKey();
    String str2 = paramBusinessService.getBusinessKey();
    try {
      serviceImpl = (ServiceImpl)this.objectManager.fetchObjectFromCache(str1);
    } catch (ClassCastException classCastException) {
      this.logger.finest("ClassCastException in serviceDetail2Service fetching service, continuing");
      serviceImpl = null;
    } 
    if (serviceImpl == null) {
      serviceImpl = new ServiceImpl();
      serviceImpl.setServiceId(this.service.getServiceId());
      serviceImpl.setRegistryService(this.service);
    } 
    serviceImpl.setIsRetrieved(true);
    serviceImpl.setIsNew(false);
    serviceImpl.setIsLoaded(true);
    OrganizationImpl organizationImpl = null;
    try {
      organizationImpl = (OrganizationImpl)this.objectManager.fetchObjectFromCache(str2);
    } catch (ClassCastException classCastException) {
      this.logger.finest("ClassCastException fething org in serviceDetail2Service, continuing");
      organizationImpl = null;
    } 
    if (organizationImpl == null) {
      organizationImpl = new OrganizationImpl();
      organizationImpl.setKey((Key)new KeyImpl(str2));
      organizationImpl.setRegistryService(this.service);
      organizationImpl.setIsRetrieved(true);
      organizationImpl.setIsNew(false);
      organizationImpl.setIsLoaded(false);
      this.objectManager.addObjectToCache((RegistryObjectImpl)organizationImpl, this.service.getServiceId());
    } 
    serviceImpl.setProvidingOrganization((Organization)organizationImpl);
    KeyImpl keyImpl = new KeyImpl(str1);
    serviceImpl.setKey((Key)keyImpl);
    List list1 = paramBusinessService.getDescription();
    InternationalString internationalString1 = descriptions2InternationalString(list1);
    if (internationalString1 != null)
      serviceImpl.setDescription(internationalString1); 
    List list2 = paramBusinessService.getName();
    InternationalString internationalString2 = names2InternationalString(list2);
    if (internationalString2 != null)
      serviceImpl.setName(internationalString2); 
    CategoryBag categoryBag = paramBusinessService.getCategoryBag();
    Collection collection = categoryBag2Classifications(categoryBag);
    if (collection != null && collection.size() > 0)
      serviceImpl.setClassifications(collection); 
    BindingTemplates bindingTemplates = paramBusinessService.getBindingTemplates();
    if (bindingTemplates != null) {
      List list = bindingTemplates.getBindingTemplate();
      ArrayList<ServiceBindingImpl> arrayList = new ArrayList();
      if (list != null) {
        Iterator<BindingTemplate> iterator = list.iterator();
        while (iterator.hasNext()) {
          ServiceBindingImpl serviceBindingImpl = (ServiceBindingImpl)bindingTemplate2ServiceBinding(iterator.next());
          if (serviceBindingImpl != null) {
            this.objectManager.addObjectToCache((RegistryObjectImpl)serviceBindingImpl, this.service.getServiceId());
            serviceBindingImpl.setServiceId(this.service.getServiceId());
            serviceBindingImpl.setRegistryService(this.service);
            serviceBindingImpl.setStatusFlags(true, true, false);
            arrayList.add(serviceBindingImpl);
          } 
        } 
        Collection collection1 = serviceImpl.getServiceBindings();
        serviceImpl.removeServiceBindings(collection1);
        serviceImpl.addServiceBindings(arrayList);
      } 
    } 
    if (getConnection().useCache()) {
      this.objectManager.addObjectToCache((RegistryObjectImpl)serviceImpl, this.service.getServiceId());
    } else {
      this.objectManager.removeObjectFromCache(serviceImpl.getKey().getId());
    } 
    return (Service)serviceImpl;
  }
  
  BulkResponse results2BulkResponse(Collection paramCollection1, Collection paramCollection2, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList arrayList = new ArrayList();
    if (paramCollection2 != null)
      bulkResponseImpl.setCollection(paramCollection2); 
    if (paramCollection1 != null && !paramCollection1.isEmpty())
      for (Result result : paramCollection1) {
        DeleteException deleteException;
        KeyType keyType = result.getKeyType();
        int i = result.getErrno();
        ErrInfo errInfo = result.getErrInfo();
        String str1 = "";
        String str2 = "";
        if (errInfo != null) {
          str1 = errInfo.getErrCode();
          str2 = errInfo.getValue();
          if (str1.equalsIgnoreCase("E_success")) {
            if (paramString.equals("delete"))
              getObjectManager().removeObjectsFromCache(paramCollection2); 
            return (BulkResponse)bulkResponseImpl;
          } 
        } else if (i == 0) {
          if (paramString.equals("delete"))
            getObjectManager().removeObjectsFromCache(paramCollection2); 
          return (BulkResponse)bulkResponseImpl;
        } 
        String str3 = ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:UDDI_DispositionReport:_Error_Code_=_") + str1 + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:;_Error_Message_=_") + str2 + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:;_Error_Number_=_") + i;
        FindException findException = null;
        if (paramString.equals("find")) {
          findException = new FindException(str3);
        } else if (paramString.equals("save")) {
          SaveException saveException = new SaveException(str3);
        } else if (paramString.equals("delete")) {
          deleteException = new DeleteException(str3);
        } 
        bulkResponseImpl.addException((JAXRException)deleteException);
      }  
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse getRegistryObjects(Collection paramCollection) throws JAXRException {
    HashMap hashMap = sortObjectType(paramCollection);
    Collection collection1 = (Collection)hashMap.get("unknown");
    Collection collection2 = null;
    ArrayList<BulkResponse> arrayList = new ArrayList();
    collection2 = (Collection)hashMap.get("orgs");
    if (collection2 != null) {
      collection2.addAll(collection1);
      if (collection2 != null && !collection2.isEmpty())
        arrayList.add(getOrganizations(collection2)); 
    } 
    if (collection2 != null) {
      collection2 = (Collection)hashMap.get("services");
      collection2.addAll(collection1);
      if (collection2 != null && !collection2.isEmpty())
        arrayList.add(getServices(collection2)); 
    } 
    if (collection2 != null) {
      collection2 = (Collection)hashMap.get("bindings");
      collection2.addAll(collection1);
      if (collection2 != null && !collection2.isEmpty())
        arrayList.add(getServiceBindings(collection2)); 
    } 
    if (collection2 != null) {
      collection2 = (Collection)hashMap.get("concepts");
      collection2.addAll(collection1);
      if (collection2 != null && !collection2.isEmpty())
        arrayList.add(getConcepts(collection2)); 
    } 
    if (collection2 != null) {
      collection2 = (Collection)hashMap.get("schemes");
      collection2.addAll(collection1);
      if (collection2 != null && !collection2.isEmpty())
        arrayList.add(getConcepts(collection2)); 
    } 
    return BulkResponseImpl.combineBulkResponses(arrayList);
  }
  
  BulkResponse getRegistryObjects(Collection paramCollection, String paramString) throws JAXRException {
    BulkResponse bulkResponse = null;
    if (paramString.equals("Organization")) {
      bulkResponse = bulkResponse = getOrganizations(paramCollection);
    } else if (paramString.equals("Service")) {
      bulkResponse = getServices(paramCollection);
    } else if (paramString.equals("ServiceBinding")) {
      bulkResponse = getServiceBindings(paramCollection);
    } else if (paramString.equals("Concept") || paramString.equals("ClassificationScheme")) {
      bulkResponse = getConcepts(paramCollection);
    } else {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Unknown_Object_Type_") + paramString + ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:_cannot_retrieve"));
    } 
    return bulkResponse;
  }
  
  BulkResponse getRegistryObjects() throws JAXRException {
    GetRegisteredInfo getRegisteredInfo = null;
    getRegisteredInfo = this.objFactory.createGetRegisteredInfo();
    getRegisteredInfo.setGeneric("2.0");
    String str = null;
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_present")); 
    getRegisteredInfo.setAuthInfo(str);
    return getProcessor().processRequestJAXB(getRegisteredInfo, true, null, "find");
  }
  
  RegistryObject getRegistryObject(RegistryObjectImpl paramRegistryObjectImpl) throws JAXRException {
    BulkResponse bulkResponse = null;
    Object object = null;
    if (paramRegistryObjectImpl == null)
      return null; 
    Key key = paramRegistryObjectImpl.getKey();
    ArrayList<Key> arrayList = new ArrayList();
    arrayList.add(key);
    if (paramRegistryObjectImpl instanceof OrganizationImpl) {
      bulkResponse = getOrganizations(arrayList);
    } else if (paramRegistryObjectImpl instanceof ServiceImpl) {
      bulkResponse = getServices(arrayList);
    } else if (paramRegistryObjectImpl instanceof ServiceBindingImpl) {
      bulkResponse = getServiceBindings(arrayList);
    } else if (paramRegistryObjectImpl instanceof ConceptImpl) {
      bulkResponse = getConcepts(arrayList);
    } else {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Unknown_Object_Type"));
    } 
    if (bulkResponse.getExceptions() == null) {
      Collection collection = bulkResponse.getCollection();
      Iterator<RegistryObject> iterator = collection.iterator();
      if (iterator.hasNext())
        return iterator.next(); 
    } 
    return null;
  }
  
  RegistryObject getRegistryObject(String paramString1, String paramString2) throws JAXRException {
    if (paramString1 == null)
      return null; 
    Object object = null;
    RegistryObject registryObject = this.objectManager.fetchObjectFromCache(paramString1);
    if (registryObject != null)
      return registryObject; 
    KeyImpl keyImpl = new KeyImpl(paramString1);
    ArrayList<KeyImpl> arrayList = new ArrayList();
    arrayList.add(keyImpl);
    BulkResponse bulkResponse = getRegistryObjects(arrayList, paramString2);
    if (bulkResponse.getExceptions() == null) {
      Collection collection = bulkResponse.getCollection();
      RegistryObject registryObject1 = null;
      if (collection.size() == 1)
        return ((ArrayList<RegistryObject>)collection).get(0); 
    } else {
      Collection collection = bulkResponse.getExceptions();
      Iterator iterator = collection.iterator();
      if (iterator.hasNext())
        throw (JAXRException)iterator.next(); 
    } 
    return null;
  }
  
  String getAuthorizationToken(Set paramSet) throws JAXRException {
    String str1 = null;
    String str2 = null;
    char[] arrayOfChar = null;
    if (paramSet != null && paramSet.size() > 0)
      for (PasswordAuthentication passwordAuthentication : paramSet) {
        if (passwordAuthentication instanceof PasswordAuthentication) {
          str1 = ((PasswordAuthentication)passwordAuthentication).getUserName();
          arrayOfChar = passwordAuthentication.getPassword();
          str2 = new String(arrayOfChar);
        } 
      }  
    if (str1 == null || arrayOfChar == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:User_Name_and/or_Password_not_set.")); 
    GetAuthToken getAuthToken = null;
    getAuthToken = this.objFactory.createGetAuthToken();
    getAuthToken.setGeneric("2.0");
    getAuthToken.setUserID(str1);
    getAuthToken.setCred(str2);
    str2 = null;
    BulkResponse bulkResponse = null;
    bulkResponse = getProcessor().processRequestJAXB(getAuthToken, true, null, "save");
    if (bulkResponse.getExceptions() == null) {
      Collection collection = bulkResponse.getCollection();
      if (collection.size() > 0)
        return ((ArrayList<String>)collection).get(0); 
    } else {
      Collection collection = bulkResponse.getExceptions();
      Iterator<JAXRException> iterator = collection.iterator();
      if (iterator.hasNext()) {
        JAXRException jAXRException = iterator.next();
        throw jAXRException;
      } 
    } 
    return null;
  }
  
  HashMap sortObjectType(Collection paramCollection) throws UnexpectedObjectException, JAXRException {
    ArrayList<KeyImpl> arrayList1 = new ArrayList();
    ArrayList<KeyImpl> arrayList2 = new ArrayList();
    ArrayList<KeyImpl> arrayList3 = new ArrayList();
    ArrayList<KeyImpl> arrayList4 = new ArrayList();
    ArrayList<KeyImpl> arrayList5 = new ArrayList();
    ArrayList<KeyImpl> arrayList6 = new ArrayList();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (KeyImpl keyImpl : paramCollection) {
      RegistryObject registryObject = this.objectManager.fetchObjectFromCache(keyImpl.getId());
      if (registryObject != null) {
        if (registryObject instanceof Organization) {
          arrayList1.add(keyImpl);
          continue;
        } 
        if (registryObject instanceof Service) {
          arrayList2.add(keyImpl);
          continue;
        } 
        if (registryObject instanceof ServiceBinding) {
          arrayList3.add(keyImpl);
          continue;
        } 
        if (registryObject instanceof Concept) {
          arrayList4.add(keyImpl);
          continue;
        } 
        if (registryObject instanceof ClassificationScheme) {
          arrayList5.add(keyImpl);
          continue;
        } 
        throw new UnexpectedObjectException();
      } 
      if (keyImpl.getId().indexOf("uuid") != -1 || keyImpl.getId().indexOf("UUID") != -1) {
        arrayList4.add(keyImpl);
        continue;
      } 
      arrayList6.add(keyImpl);
    } 
    hashMap.put("orgs", arrayList1);
    hashMap.put("services", arrayList2);
    hashMap.put("bindings", arrayList3);
    hashMap.put("concepts", arrayList4);
    hashMap.put("schemes", arrayList5);
    hashMap.put("unknown", arrayList6);
    return hashMap;
  }
  
  BulkResponse saveObjects(Collection paramCollection) throws JAXRException {
    ArrayList<BulkResponse> arrayList = new ArrayList();
    ArrayList arrayList1 = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    ArrayList arrayList5 = new ArrayList();
    for (Object object : paramCollection) {
      if (object instanceof Organization) {
        arrayList1.add(object);
        continue;
      } 
      if (object instanceof Service) {
        arrayList2.add(object);
        continue;
      } 
      if (object instanceof ServiceBinding) {
        arrayList3.add(object);
        continue;
      } 
      if (object instanceof Concept) {
        arrayList4.add(object);
        continue;
      } 
      if (object instanceof ClassificationScheme) {
        arrayList5.add(object);
        continue;
      } 
      throw new UnexpectedObjectException();
    } 
    if (arrayList1.size() != 0)
      arrayList.add(saveOrganizations(arrayList1)); 
    if (arrayList2.size() != 0)
      arrayList.add(saveServices(arrayList2)); 
    if (arrayList3.size() != 0)
      arrayList.add(saveServiceBindings(arrayList3)); 
    if (arrayList4.size() != 0)
      arrayList.add(saveConcepts(arrayList4)); 
    if (arrayList5.size() != 0)
      arrayList.add(saveClassificationSchemes(arrayList5)); 
    return BulkResponseImpl.combineBulkResponses(arrayList);
  }
  
  BulkResponse deleteObjects(Collection paramCollection) throws JAXRException {
    HashMap hashMap = sortObjectType(paramCollection);
    Collection collection = null;
    ArrayList<BulkResponse> arrayList = new ArrayList();
    collection = (Collection)hashMap.get("orgs");
    if (collection != null && !collection.isEmpty())
      arrayList.add(deleteOrganizations(collection)); 
    collection = (Collection)hashMap.get("services");
    if (collection != null && !collection.isEmpty())
      arrayList.add(deleteServices(collection)); 
    collection = (Collection)hashMap.get("bindings");
    if (collection != null && !collection.isEmpty())
      arrayList.add(deleteServiceBindings(collection)); 
    collection = (Collection)hashMap.get("concepts");
    if (collection != null && !collection.isEmpty())
      arrayList.add(deleteConcepts(collection)); 
    collection = (Collection)hashMap.get("schemes");
    if (collection != null && !collection.isEmpty())
      arrayList.add(deleteConcepts(collection)); 
    return BulkResponseImpl.combineBulkResponses(arrayList);
  }
  
  BulkResponse deleteObjects(Collection paramCollection, String paramString) throws JAXRException {
    BulkResponse bulkResponse = null;
    if (paramString.equals("Organization"))
      bulkResponse = deleteOrganizations(paramCollection); 
    if (paramString.equals("Service"))
      bulkResponse = deleteServices(paramCollection); 
    if (paramString.equals("ServiceBinding"))
      bulkResponse = deleteServiceBindings(paramCollection); 
    if (paramString.equals("Concept") || paramString.equals("ClassificationScheme"))
      bulkResponse = deleteConcepts(paramCollection); 
    return bulkResponse;
  }
  
  public BulkResponse saveAssociations(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    if (paramCollection.size() == 0)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("ConnectionImpl:UDDIMapper:No_Associations_found_to_save")); 
    if (paramBoolean)
      return saveAllAssociations(paramCollection, paramBoolean); 
    AddPublisherAssertions addPublisherAssertions = null;
    addPublisherAssertions = this.objFactory.createAddPublisherAssertions();
    addPublisherAssertions.setGeneric("2.0");
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_or_Invalid_Credential_Information")); 
    addPublisherAssertions.setAuthInfo(str);
    Collection collection = associations2PublisherAssertions(paramCollection);
    if (collection != null)
      addPublisherAssertions.getPublisherAssertion().addAll(collection); 
    ArrayList<KeyImpl> arrayList = new ArrayList();
    Iterator<Association> iterator = paramCollection.iterator();
    while (iterator.hasNext()) {
      KeyImpl keyImpl = (KeyImpl)((Association)iterator.next()).getKey();
      arrayList.add(keyImpl);
    } 
    return getProcessor().processRequestJAXB(addPublisherAssertions, true, arrayList, "save");
  }
  
  public BulkResponse saveAllAssociations(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    SetPublisherAssertions setPublisherAssertions = null;
    setPublisherAssertions = this.objFactory.createSetPublisherAssertions();
    setPublisherAssertions.setGeneric("2.0");
    String str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credential_or_Invalid_Credential_Information")); 
    setPublisherAssertions.setAuthInfo(str);
    Collection collection = associations2PublisherAssertions(paramCollection);
    setPublisherAssertions.getPublisherAssertion().addAll(collection);
    ArrayList<KeyImpl> arrayList = new ArrayList();
    Iterator<Association> iterator = paramCollection.iterator();
    while (iterator.hasNext()) {
      KeyImpl keyImpl = (KeyImpl)((Association)iterator.next()).getKey();
      arrayList.add(keyImpl);
    } 
    return getProcessor().processRequestJAXB(setPublisherAssertions, true, arrayList, "save");
  }
  
  public BulkResponse findAssociations(Collection paramCollection1, String paramString1, String paramString2, Collection paramCollection2) throws JAXRException {
    short s = -1;
    FindRelatedBusinesses findRelatedBusinesses = null;
    findRelatedBusinesses = this.objFactory.createFindRelatedBusinesses();
    findRelatedBusinesses.setGeneric("2.0");
    String str = getConnection().getMaxRows();
    if (str != null) {
      int i = Integer.parseInt(str);
      findRelatedBusinesses.setMaxRows(Integer.valueOf(i));
    } 
    if (paramString1 == null && paramString2 == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:RegistryObject_required_for_FindAssociatedObjects_method._")); 
    ArrayList<KeyImpl> arrayList = new ArrayList();
    if (paramString1 != null && paramString2 != null) {
      s = 300;
      findRelatedBusinesses.setBusinessKey(paramString1);
      arrayList.add(new KeyImpl(paramString1));
    } else if (paramString1 != null) {
      s = 100;
      findRelatedBusinesses.setBusinessKey(paramString1);
      arrayList.add(new KeyImpl(paramString1));
    } else if (paramString2 != null) {
      s = 200;
      findRelatedBusinesses.setBusinessKey(paramString2);
      arrayList.add(new KeyImpl(paramString2));
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Registry_Object_Key_required_for_findAssociatedObjects._"));
    } 
    FindQualifiers findQualifiers = strings2FindQualifiers(paramCollection1);
    if (findQualifiers != null)
      findRelatedBusinesses.setFindQualifiers(findQualifiers); 
    Collection collection = associationTypes2KeyedReferences(paramCollection2);
    ArrayList<BulkResponse> arrayList1 = new ArrayList();
    BulkResponse bulkResponse = null;
    if (collection != null && !collection.isEmpty()) {
      Iterator<KeyedReference> iterator = collection.iterator();
      while (iterator.hasNext()) {
        findRelatedBusinesses.setKeyedReference(iterator.next());
        arrayList1.add(getProcessor().processRequestJAXB(findRelatedBusinesses, false, arrayList, "find"));
      } 
      bulkResponse = BulkResponseImpl.combineBulkResponses(arrayList1);
    } else {
      bulkResponse = getProcessor().processRequestJAXB(findRelatedBusinesses, false, arrayList, "find");
    } 
    return (bulkResponse.getExceptions() != null) ? bulkResponse : this.helper.filterAssociations(bulkResponse, s, paramString1, paramString2);
  }
  
  public BulkResponse findCallerAssociations(Collection paramCollection1, Boolean paramBoolean1, Boolean paramBoolean2, Collection paramCollection2) throws JAXRException {
    GetAssertionStatusReport getAssertionStatusReport = null;
    getAssertionStatusReport = this.objFactory.createGetAssertionStatusReport();
    String str = null;
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_present")); 
    getAssertionStatusReport.setGeneric("2.0");
    getAssertionStatusReport.setAuthInfo(str);
    ArrayList<BulkResponse> arrayList = new ArrayList();
    Collection collection = confirmationState2CompletionStatus(paramBoolean1, paramBoolean2);
    this.fromKeysOwned = new ArrayList();
    this.toKeysOwned = new ArrayList();
    for (String str1 : collection) {
      getAssertionStatusReport.setCompletionStatus(str1);
      arrayList.add(getProcessor().processRequestJAXB(getAssertionStatusReport, true, null, "find"));
    } 
    BulkResponse bulkResponse = BulkResponseImpl.combineBulkResponses(arrayList);
    if (bulkResponse.getExceptions() == null) {
      BulkResponse bulkResponse1 = this.helper.filterAssociationsByConfirmationState(bulkResponse, paramBoolean1, paramBoolean2, this.fromKeysOwned, this.toKeysOwned);
      return (paramCollection2 != null) ? this.helper.filterByAssociationTypes(bulkResponse1, paramCollection2) : bulkResponse1;
    } 
    return bulkResponse;
  }
  
  void confirmAssociation(Association paramAssociation) throws JAXRException {
    if (paramAssociation != null) {
      ArrayList<Association> arrayList = new ArrayList();
      arrayList.add(paramAssociation);
      BulkResponse bulkResponse = saveAssociations(arrayList, false);
      if (bulkResponse.getExceptions() != null) {
        Collection collection = bulkResponse.getExceptions();
        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
          throw (JAXRException)iterator.next(); 
      } 
    } 
  }
  
  void unConfirmAssociation(Association paramAssociation) throws JAXRException {
    if (paramAssociation != null) {
      ArrayList<String> arrayList = new ArrayList();
      String str = paramAssociation.getKey().getId();
      if (str != null) {
        arrayList.add(str);
        BulkResponse bulkResponse = deleteAssociations(arrayList);
        if (bulkResponse.getExceptions() != null) {
          Collection collection = bulkResponse.getExceptions();
          Iterator iterator = collection.iterator();
          if (iterator.hasNext())
            throw (JAXRException)iterator.next(); 
        } 
      } 
    } 
  }
  
  BulkResponse findSourceAssociations(Collection paramCollection1, Boolean paramBoolean1, Boolean paramBoolean2, Collection paramCollection2) throws JAXRException {
    GetPublisherAssertions getPublisherAssertions = null;
    getPublisherAssertions = this.objFactory.createGetPublisherAssertions();
    String str = null;
    str = getAuthInfo();
    if (str == null)
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_Credentials_present")); 
    getPublisherAssertions.setGeneric("2.0");
    getPublisherAssertions.setAuthInfo(str);
    return getProcessor().processRequestJAXB(getPublisherAssertions, true, null, "find");
  }
  
  String makeRegistrySpecificRequest(String paramString, boolean paramBoolean) throws JAXRException {
    if (paramString == null)
      return null; 
    this.logger.finest(paramString);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramString.getBytes());
    SOAPMessage sOAPMessage = null;
    try {
      Object object = MarshallerUtil.getInstance().jaxbUnmarshalInputStream(byteArrayInputStream);
      sOAPMessage = MarshallerUtil.getInstance().jaxbMarshalObject(object);
    } catch (JAXBException jAXBException) {
      throw new JAXRException(jAXBException);
    } 
    Node node1 = this.service.send(sOAPMessage, paramBoolean);
    Node node2 = null;
    String str = node1.getNodeName();
    if (((SOAPBody)node1).hasFault()) {
      if (node1 instanceof Element) {
        NodeList nodeList = ((Element)node1).getElementsByTagName("dispositionReport");
        if (nodeList != null) {
          int i = nodeList.getLength();
          if (i > 0)
            for (byte b = 0; b < i; b++) {
              Node node = nodeList.item(b);
              if (node != null) {
                node2 = node;
                str = "dispositionReport";
                break;
              } 
            }  
        } 
      } 
    } else {
      node2 = node1.getFirstChild();
    } 
    ByteArrayOutputStream byteArrayOutputStream = null;
    try {
      Object object = MarshallerUtil.getInstance().jaxbUnmarshalObject(node2);
      byteArrayOutputStream = (ByteArrayOutputStream)MarshallerUtil.getInstance().jaxbMarshalOutStream(object);
    } catch (JAXBException jAXBException) {
      throw new JAXRException(jAXBException);
    } 
    return byteArrayOutputStream.toString();
  }
  
  Collection confirmationState2CompletionStatus(Boolean paramBoolean1, Boolean paramBoolean2) {
    ArrayList<String> arrayList = new ArrayList();
    Object object = null;
    if (paramBoolean1 != null && paramBoolean2 != null) {
      if (paramBoolean1.booleanValue() && paramBoolean2.booleanValue()) {
        arrayList.add("status:complete");
      } else {
        arrayList.add("status:toKey_incomplete");
        arrayList.add("status:fromKey_incomplete");
      } 
    } else {
      arrayList.add("status:complete");
      arrayList.add("status:toKey_incomplete");
      arrayList.add("status:fromKey_incomplete");
    } 
    return arrayList;
  }
  
  Collection associationKeys2PublisherAssertions(Collection paramCollection) throws JAXRException {
    ArrayList<PublisherAssertion> arrayList = new ArrayList();
    if (paramCollection != null) {
      Iterator<KeyImpl> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          KeyImpl keyImpl = iterator.next();
          PublisherAssertion publisherAssertion = associationKey2PublisherAssertion(keyImpl);
          if (publisherAssertion != null)
            arrayList.add(publisherAssertion); 
        } 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_javax.xml.infomodel.Key"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  PublisherAssertion associationKey2PublisherAssertion(KeyImpl paramKeyImpl) throws JAXRException {
    PublisherAssertion publisherAssertion = null;
    if (paramKeyImpl != null) {
      String str = paramKeyImpl.getId();
      if (str != null) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ":", false);
        if (stringTokenizer.countTokens() == 3) {
          String str1 = stringTokenizer.nextToken();
          String str2 = stringTokenizer.nextToken();
          String str3 = stringTokenizer.nextToken();
          publisherAssertion = null;
          publisherAssertion = this.objFactory.createPublisherAssertion();
          publisherAssertion.setFromKey(str1);
          publisherAssertion.setToKey(str2);
          KeyedReference keyedReference = associationType2KeyedReference(str3);
          if (keyedReference != null)
            publisherAssertion.setKeyedReference(keyedReference); 
        } else {
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Association_Key_id_is_incorrectly_formated_-_expected_SourceObjectKeyId:TargetObjectKeyId:AssociationTypeString._"));
        } 
      } 
    } 
    return publisherAssertion;
  }
  
  Collection publisherAssertions2Associations(Collection paramCollection) throws JAXRException {
    ArrayList<Association> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList();
      Iterator<PublisherAssertion> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        Association association = publisherAssertion2Association(iterator.next());
        if (association != null)
          arrayList.add(association); 
      } 
    } 
    return arrayList;
  }
  
  Association publisherAssertion2Association(PublisherAssertion paramPublisherAssertion) throws JAXRException {
    AssociationImpl associationImpl = null;
    if (paramPublisherAssertion != null) {
      String str1 = paramPublisherAssertion.getFromKey();
      String str2 = paramPublisherAssertion.getToKey();
      KeyedReference keyedReference = paramPublisherAssertion.getKeyedReference();
      Concept concept = keyedReference2AssociationType(keyedReference);
      String str3 = concept.getValue();
      KeyImpl keyImpl = buildAssociationKey(str1, str2, str3);
      associationImpl = (AssociationImpl)this.objectManager.fetchObjectFromCache(keyImpl.getId());
      if (associationImpl == null) {
        associationImpl = new AssociationImpl();
        associationImpl.setServiceId(this.service.getServiceId());
        associationImpl.setRegistryService(this.service);
      } 
      if (!str1.equalsIgnoreCase(str2))
        associationImpl.setIsExtramural(true); 
      associationImpl.setAssociationType(concept);
      associationImpl.setIsRetrieved(true);
      associationImpl.setIsNew(false);
      if (keyImpl != null)
        associationImpl.setKey((Key)keyImpl); 
      associationImpl.setIsLoaded(false);
      Organization organization1 = null;
      if (str1 != null && associationImpl.getSourceObject() == null) {
        organization1 = (Organization)this.objectManager.fetchObjectFromCache(str1);
        if (organization1 == null)
          organization1 = (Organization)getRegistryObject(str1, "Organization"); 
        if (organization1 != null) {
          associationImpl.setSourceObject((RegistryObject)organization1);
          organization1.addAssociation((Association)associationImpl);
        } 
      } 
      Organization organization2 = null;
      if (str2 != null && associationImpl.getTargetObject() == null) {
        organization2 = (Organization)this.objectManager.fetchObjectFromCache(str2);
        if (organization2 == null)
          organization2 = (Organization)getRegistryObject(str2, "Organization"); 
        if (organization2 != null)
          associationImpl.setTargetObject((RegistryObject)organization2); 
      } 
      if (associationImpl != null)
        this.objectManager.addObjectToCache((RegistryObjectImpl)associationImpl, this.service.getServiceId()); 
      return (Association)associationImpl;
    } 
    return null;
  }
  
  Collection assertionStatusItems2Associations(Collection paramCollection) throws JAXRException {
    ArrayList<Association> arrayList = null;
    if (paramCollection != null) {
      arrayList = new ArrayList(paramCollection.size());
      Iterator<AssertionStatusItem> iterator = paramCollection.iterator();
      while (iterator.hasNext()) {
        Association association = assertionStatusItem2Association(iterator.next());
        if (association != null)
          arrayList.add(association); 
      } 
      return arrayList;
    } 
    return null;
  }
  
  Association assertionStatusItem2Association(AssertionStatusItem paramAssertionStatusItem) throws JAXRException {
    AssociationImpl associationImpl = null;
    if (paramAssertionStatusItem != null) {
      this.logger.finest("Got assertionStatusItem");
      String str1 = paramAssertionStatusItem.getCompletionStatus();
      String str2 = paramAssertionStatusItem.getFromKey();
      String str3 = paramAssertionStatusItem.getToKey();
      KeyedReference keyedReference = paramAssertionStatusItem.getKeyedReference();
      Concept concept = null;
      if (keyedReference != null)
        concept = keyedReference2AssociationType(keyedReference); 
      String str4 = null;
      if (concept != null)
        str4 = concept.getValue(); 
      KeyImpl keyImpl = buildAssociationKey(str2, str3, str4);
      this.logger.finest("AssociationKeyIs " + keyImpl.getId());
      associationImpl = (AssociationImpl)this.objectManager.fetchObjectFromCache(keyImpl.getId());
      if (associationImpl == null) {
        associationImpl = new AssociationImpl();
        associationImpl.setServiceId(this.service.getServiceId());
        associationImpl.setRegistryService(this.service);
      } 
      if (keyImpl != null)
        associationImpl.setKey((Key)keyImpl); 
      if (!str2.equalsIgnoreCase(str3))
        associationImpl.setIsExtramural(true); 
      associationImpl.setIsRetrieved(true);
      associationImpl.setIsNew(false);
      associationImpl.setIsLoaded(true);
      KeysOwned keysOwned = paramAssertionStatusItem.getKeysOwned();
      String str5 = keysOwned.getFromKey();
      String str6 = keysOwned.getToKey();
      if (str5 != null) {
        this.fromKeysOwned.add(str5);
      } else if (str6 != null) {
        this.toKeysOwned.add(str6);
      } 
      if (concept != null)
        associationImpl.setAssociationType(concept); 
      this.logger.finest("Setting confirmationState");
      if (str1.equals("status:complete")) {
        associationImpl.setIsConfirmedBySourceOwner(true);
        associationImpl.setIsConfirmedByTargetOwner(true);
      } else if (str1.equals("status:toKey_incomplete")) {
        associationImpl.setIsConfirmedBySourceOwner(true);
        associationImpl.setIsConfirmedByTargetOwner(false);
      } else if (str1.equals("status:fromKey_incomplete")) {
        associationImpl.setIsConfirmedBySourceOwner(false);
        associationImpl.setIsConfirmedByTargetOwner(true);
      } 
      Organization organization1 = null;
      if (str2 != null && associationImpl.getSourceObject() == null) {
        organization1 = (Organization)this.objectManager.fetchObjectFromCache(str2);
        if (organization1 == null)
          organization1 = (Organization)getRegistryObject(str2, "Organization"); 
        if (organization1 != null) {
          associationImpl.setSourceObject((RegistryObject)organization1);
          organization1.addAssociation((Association)associationImpl);
        } 
      } 
      Organization organization2 = null;
      if (str3 != null && associationImpl.getTargetObject() == null) {
        organization2 = (Organization)this.objectManager.fetchObjectFromCache(str3);
        if (organization2 == null)
          organization2 = (Organization)getRegistryObject(str3, "Organization"); 
        if (organization2 != null)
          associationImpl.setTargetObject((RegistryObject)organization2); 
      } 
      if (associationImpl != null)
        this.objectManager.addObjectToCache((RegistryObjectImpl)associationImpl, this.service.getServiceId()); 
      this.logger.finest("Returning association with a value");
      return (Association)associationImpl;
    } 
    return null;
  }
  
  KeyImpl buildAssociationKey(String paramString1, String paramString2, String paramString3) {
    StringBuffer stringBuffer = new StringBuffer(400);
    stringBuffer.append(paramString1);
    stringBuffer.append(":");
    stringBuffer.append(paramString2);
    stringBuffer.append(":");
    stringBuffer.append(paramString3);
    return new KeyImpl(stringBuffer.toString());
  }
  
  Concept keyedReference2AssociationType(KeyedReference paramKeyedReference) throws JAXRException {
    ConceptImpl conceptImpl = null;
    if (paramKeyedReference != null) {
      String str1 = paramKeyedReference.getTModelKey();
      String str2 = paramKeyedReference.getKeyName();
      String str3 = paramKeyedReference.getKeyValue();
      String str4 = null;
      String str5 = null;
      if (str3.equalsIgnoreCase("peer-peer")) {
        str4 = "RelatedTo";
        str5 = "RelatedTo";
      } else if (str3.equalsIgnoreCase("identity")) {
        str4 = "EquivalentTo";
        str5 = "EquivalentTo";
      } else if (str3.equalsIgnoreCase("parent-child")) {
        str4 = "HasChild";
        str5 = "HasChild";
      } else {
        str4 = str2;
        str5 = str3;
      } 
      conceptImpl = new ConceptImpl();
      if (str1 != null)
        conceptImpl.setKey((Key)new KeyImpl(str1)); 
      if (str2 != null)
        conceptImpl.setName((InternationalString)new InternationalStringImpl(str4)); 
      if (str3 != null)
        conceptImpl.setValue(str5); 
    } 
    return (Concept)conceptImpl;
  }
  
  public BulkResponse getRegistryObjects(String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList arrayList1 = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    ArrayList arrayList4 = new ArrayList();
    ArrayList arrayList5 = new ArrayList();
    if (paramString != null) {
      BulkResponse bulkResponse = getRegistryObjects();
      if (bulkResponse != null && bulkResponse.getExceptions() == null) {
        Collection collection = bulkResponse.getCollection();
        for (Object object : collection) {
          if (object instanceof Organization) {
            arrayList1.add(object);
            continue;
          } 
          if (object instanceof Service) {
            arrayList2.add(object);
            continue;
          } 
          if (object instanceof ServiceBinding) {
            arrayList3.add(object);
            continue;
          } 
          if (object instanceof Concept) {
            arrayList4.add(object);
            continue;
          } 
          if (object instanceof ClassificationScheme) {
            arrayList5.add(object);
            continue;
          } 
          throw new UnexpectedObjectException();
        } 
        if (paramString.equals("Organization")) {
          bulkResponseImpl.setCollection(arrayList1);
        } else if (paramString.equals("Service")) {
          bulkResponseImpl.setCollection(this.helper.getAllServicesFromOrganizations(arrayList1));
        } else if (paramString.equals("ServiceBinding")) {
          bulkResponseImpl.setCollection(this.helper.getAllServiceBindingsFromOrganizations(arrayList1));
        } else if (paramString.equals("Concept")) {
          bulkResponseImpl.setCollection(arrayList4);
        } else if (paramString.equals("ClassificationScheme")) {
          bulkResponseImpl.setCollection(arrayList5);
        } else {
          throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Unexpected_Object_type"));
        } 
      } 
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  Collection associations2PublisherAssertions(Collection paramCollection) throws JAXRException {
    ArrayList<PublisherAssertion> arrayList = new ArrayList();
    if (paramCollection != null) {
      Iterator<Association> iterator = paramCollection.iterator();
      try {
        while (iterator.hasNext()) {
          Association association = iterator.next();
          PublisherAssertion publisherAssertion = association2PublisherAssertion(association);
          if (publisherAssertion != null)
            arrayList.add(publisherAssertion); 
        } 
      } catch (ClassCastException classCastException) {
        throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Expected_Association_Object"), classCastException);
      } 
    } 
    return arrayList;
  }
  
  PublisherAssertion association2PublisherAssertion(Association paramAssociation) throws JAXRException {
    Concept concept = paramAssociation.getAssociationType();
    boolean bool1 = paramAssociation.isConfirmedBySourceOwner();
    boolean bool2 = paramAssociation.isConfirmedByTargetOwner();
    Organization organization1 = null;
    Organization organization2 = null;
    try {
      organization1 = (Organization)paramAssociation.getSourceObject();
      organization2 = (Organization)paramAssociation.getTargetObject();
    } catch (ClassCastException classCastException) {
      throw new UnexpectedObjectException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:The_Source_Object_and_the_Target_Object_of_the_Association_must_be_of_type_Organization."), classCastException);
    } 
    PublisherAssertion publisherAssertion = null;
    publisherAssertion = this.objFactory.createPublisherAssertion();
    KeyImpl keyImpl1 = (KeyImpl)organization1.getKey();
    if (keyImpl1 != null) {
      String str = keyImpl1.getId();
      if (str != null) {
        publisherAssertion.setFromKey(str);
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Source_Object_key_id_needs_to_be_provided._"));
      } 
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Target_Object_Key_needs_to_be_provided._"));
    } 
    KeyImpl keyImpl2 = (KeyImpl)organization2.getKey();
    if (keyImpl2 != null) {
      String str = keyImpl2.getId();
      if (str != null) {
        publisherAssertion.setToKey(str);
      } else {
        throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Source_Object_key_id_needs_to_be_provided._"));
      } 
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Target_Object_Key_needs_to_be_provided._"));
    } 
    if (concept != null) {
      KeyedReference keyedReference = associationType2KeyedReference(concept);
      if (keyedReference != null)
        publisherAssertion.setKeyedReference(keyedReference); 
    } else {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:Association_type_required_to_save_an_Association_in_the_Registry"));
    } 
    return publisherAssertion;
  }
  
  private Description getDescription(RegistryObject paramRegistryObject) throws JAXRException {
    Description description1 = null;
    Description description2 = null;
    String str1 = null;
    Object object = null;
    try {
      InternationalString internationalString = paramRegistryObject.getDescription();
      if (internationalString != null)
        str1 = internationalString.getValue(); 
    } catch (JAXRException jAXRException) {
      this.logger.log(Level.WARNING, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
    if (str1 == null)
      str1 = ""; 
    description1 = null;
    description1 = this.objFactory.createDescription();
    description2 = this.objFactory.createDescription();
    description1.setValue(str1);
    description2.setValue(str1);
    String str2 = Locale.getDefault().getLanguage();
    description2.setLang(str2);
    String str3 = Locale.getDefault().getCountry();
    if (str3 != null && !str3.equals(""))
      str2 = str2 + "-" + str3; 
    description1.setLang(str2);
    return description1;
  }
  
  private Name getName(RegistryObject paramRegistryObject) throws JAXRException {
    Name name = null;
    String str1 = null;
    try {
      InternationalString internationalString = paramRegistryObject.getName();
      str1 = internationalString.getValue();
    } catch (JAXRException jAXRException) {
      this.logger.log(Level.WARNING, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
    if (str1 == null)
      str1 = ""; 
    name = null;
    name = this.objFactory.createName();
    name.setValue(str1);
    String str2 = Locale.getDefault().getLanguage();
    String str3 = Locale.getDefault().getCountry();
    if (str3 != null && !str3.equals(""))
      str2 = str2 + "-" + str3; 
    name.setLang(str2);
    return name;
  }
  
  private Collection getNames(RegistryObject paramRegistryObject, boolean paramBoolean) {
    Collection collection = null;
    try {
      InternationalString internationalString = paramRegistryObject.getName();
      Collection collection1 = internationalString.getLocalizedStrings();
      collection = localizedStrings2Names(collection1, paramBoolean);
    } catch (JAXRException jAXRException) {
      this.logger.log(Level.WARNING, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
    return collection;
  }
  
  private Collection getDescriptions(RegistryObject paramRegistryObject, boolean paramBoolean) {
    Collection collection = null;
    try {
      InternationalString internationalString = paramRegistryObject.getDescription();
      Collection collection1 = internationalString.getLocalizedStrings();
      collection = localizedStrings2Descriptions(collection1, paramBoolean);
    } catch (JAXRException jAXRException) {
      this.logger.log(Level.WARNING, jAXRException.getMessage(), (Throwable)jAXRException);
    } 
    return collection;
  }
  
  Collection internationalString2Names(InternationalString paramInternationalString, boolean paramBoolean) throws JAXRException {
    Collection collection = null;
    try {
      if (paramInternationalString != null) {
        Collection collection1 = paramInternationalString.getLocalizedStrings();
        collection = localizedStrings2Names(collection1, paramBoolean);
      } 
    } catch (JAXRException jAXRException) {
      throw new JAXRException(jAXRException.getMessage(), jAXRException);
    } 
    return collection;
  }
  
  Collection localizedStrings2Names(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<Name> arrayList = new ArrayList();
    if (paramCollection != null)
      for (LocalizedString localizedString : paramCollection) {
        Locale locale = localizedString.getLocale();
        String str1 = localizedString.getValue();
        String str2 = localizedString.getCharsetName();
        Name name1 = null;
        Name name2 = null;
        name1 = this.objFactory.createName();
        name2 = this.objFactory.createName();
        name1.setValue(str1);
        name2.setValue(str1);
        if (locale == null)
          locale = Locale.getDefault(); 
        String str3 = locale.getLanguage().toLowerCase();
        if (!paramBoolean) {
          name2.setLang(str3);
          arrayList.add(name2);
        } 
        String str4 = locale.getCountry().toUpperCase();
        if (str4 != null && !str4.equals(""))
          str3 = str3 + "-" + str4; 
        name1.setLang(str3);
        arrayList.add(name1);
      }  
    return arrayList;
  }
  
  Collection internationalString2Descriptions(InternationalString paramInternationalString, boolean paramBoolean) throws JAXRException {
    Collection collection = null;
    try {
      if (paramInternationalString != null) {
        Collection collection1 = paramInternationalString.getLocalizedStrings();
        collection = localizedStrings2Descriptions(collection1, paramBoolean);
      } 
    } catch (JAXRException jAXRException) {
      throw new JAXRException(jAXRException.getMessage(), jAXRException);
    } 
    return collection;
  }
  
  Collection localizedStrings2Descriptions(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    ArrayList<Description> arrayList = new ArrayList();
    if (paramCollection != null)
      for (LocalizedString localizedString : paramCollection) {
        Locale locale = localizedString.getLocale();
        String str1 = localizedString.getValue();
        String str2 = localizedString.getCharsetName();
        Description description1 = null;
        Description description2 = null;
        description1 = this.objFactory.createDescription();
        description2 = this.objFactory.createDescription();
        description1.setValue(str1);
        description2.setValue(str1);
        if (locale == null)
          locale = Locale.getDefault(); 
        String str3 = locale.getLanguage().toLowerCase();
        if (!paramBoolean) {
          description2.setLang(str3);
          arrayList.add(description2);
        } 
        String str4 = locale.getCountry().toUpperCase();
        if (str4 != null && !str4.equals(""))
          str3 = str3 + "-" + str4; 
        description1.setLang(str3);
        arrayList.add(description1);
      }  
    return arrayList;
  }
  
  Collection descriptions2LocalizedStrings(Collection paramCollection) throws JAXRException {
    ArrayList<LocalizedStringImpl> arrayList = new ArrayList();
    Iterator<Description> iterator = paramCollection.iterator();
    while (iterator.hasNext()) {
      LocalizedStringImpl localizedStringImpl = new LocalizedStringImpl();
      Description description = iterator.next();
      String str1 = description.getValue();
      String str2 = description.getLang();
      if (str1 != null)
        localizedStringImpl.setValue(str1); 
      Locale locale = null;
      if (str2 != null) {
        int i = str2.indexOf('-');
        if (i != -1) {
          String str3 = str2.substring(0, i).toLowerCase();
          String str4 = str2.substring(i + 1).toUpperCase();
          locale = new Locale(str3, str4);
        } else {
          locale = new Locale(str2.toLowerCase(), "");
        } 
      } else {
        locale = Locale.getDefault();
      } 
      localizedStringImpl.setLocale(locale);
      arrayList.add(localizedStringImpl);
    } 
    return arrayList;
  }
  
  Collection names2LocalizedStrings(Collection paramCollection) throws JAXRException {
    ArrayList<LocalizedStringImpl> arrayList = new ArrayList();
    for (Name name : paramCollection) {
      String str1 = name.getValue();
      String str2 = name.getLang();
      LocalizedStringImpl localizedStringImpl = new LocalizedStringImpl();
      if (str1 != null)
        localizedStringImpl.setValue(str1); 
      Locale locale = null;
      if (str2 != null) {
        int i = str2.indexOf('-');
        if (i != -1) {
          String str3 = str2.substring(0, i).toLowerCase();
          String str4 = str2.substring(i + 1).toUpperCase();
          locale = new Locale(str3, str4);
        } else {
          locale = new Locale(str2.toLowerCase(), "");
        } 
      } else {
        locale = Locale.getDefault();
      } 
      localizedStringImpl.setLocale(locale);
      arrayList.add(localizedStringImpl);
    } 
    return arrayList;
  }
  
  LocalizedString name2LocalizedString(Name paramName) throws JAXRException {
    String str1 = paramName.getValue();
    String str2 = paramName.getLang();
    LocalizedStringImpl localizedStringImpl = new LocalizedStringImpl();
    if (str1 != null)
      localizedStringImpl.setValue(str1); 
    Locale locale = null;
    if (str2 != null) {
      int i = str2.indexOf('-');
      if (i != -1) {
        String str3 = str2.substring(0, i).toLowerCase();
        String str4 = str2.substring(i + 1).toUpperCase();
        locale = new Locale(str3, str4);
      } else {
        locale = new Locale(str2.toLowerCase(), "");
      } 
    } else {
      locale = Locale.getDefault();
    } 
    localizedStringImpl.setLocale(locale);
    return (LocalizedString)localizedStringImpl;
  }
  
  InternationalString name2InternationalString(Name paramName) throws JAXRException {
    InternationalStringImpl internationalStringImpl = new InternationalStringImpl();
    if (paramName != null) {
      LocalizedString localizedString = name2LocalizedString(paramName);
      if (localizedString != null) {
        internationalStringImpl = new InternationalStringImpl();
        internationalStringImpl.addLocalizedString(localizedString);
      } 
    } 
    return (InternationalString)internationalStringImpl;
  }
  
  InternationalString names2InternationalString(Collection paramCollection) throws JAXRException {
    InternationalStringImpl internationalStringImpl = new InternationalStringImpl();
    if (paramCollection != null) {
      Collection collection = names2LocalizedStrings(paramCollection);
      if (collection != null) {
        internationalStringImpl = new InternationalStringImpl();
        internationalStringImpl.addLocalizedStrings(collection);
      } 
    } 
    return (InternationalString)internationalStringImpl;
  }
  
  InternationalString descriptions2InternationalString(Collection paramCollection) throws JAXRException {
    InternationalStringImpl internationalStringImpl = new InternationalStringImpl();
    if (paramCollection != null) {
      Collection collection = descriptions2LocalizedStrings(paramCollection);
      if (collection != null) {
        internationalStringImpl = new InternationalStringImpl();
        internationalStringImpl.addLocalizedStrings(collection);
      } 
    } 
    return (InternationalString)internationalStringImpl;
  }
  
  private void mapPostalAddressAttributes(ClassificationScheme paramClassificationScheme) throws JAXRException {
    if (this.equivalentConcepts == null) {
      initSemanticEquivalences();
      if (this.equivalentConcepts == null) {
        this.logger.finest("equivalent concepts are null");
        return;
      } 
    } 
    this.defaultPostalScheme = this.service.getDefaultPostalScheme();
    if (this.postalAddressMap == null)
      this.postalAddressMap = new HashMap<Object, Object>(); 
    Collection collection1 = null;
    Collection collection2 = null;
    if (this.jaxrPostalAddressScheme != null)
      collection1 = this.jaxrPostalAddressScheme.getChildrenConcepts(); 
    Object object = null;
    if (paramClassificationScheme == null) {
      if (this.defaultPostalScheme != null) {
        this.defaultPostalScheme = getClassificationSchemeById(this.defaultPostalScheme.getKey().getId().trim());
        collection2 = this.defaultPostalScheme.getChildrenConcepts();
      } 
    } else if (paramClassificationScheme != null) {
      collection2 = paramClassificationScheme.getChildrenConcepts();
    } 
    Iterator<Concept> iterator = collection1.iterator();
    while (iterator.hasNext()) {
      boolean bool = false;
      this.logger.finest("checking to see if there are equivalent concepts for postalAddressMapping");
      Concept concept1 = iterator.next();
      this.logger.finest("first postal key " + concept1.getKey().getId());
      Concept concept2 = (Concept)this.equivalentConcepts.get(concept1.getValue());
      if (concept2 == null) {
        this.logger.finest("no Equivalent Concept found in equivalentConcepts");
        this.postalAddressMap.put(concept1.getValue(), null);
      } 
      if (concept2 != null) {
        String str = concept2.getValue();
        this.logger.finest("Equivalent Concept id " + str);
        if (collection2 != null) {
          for (Concept concept : collection2) {
            String str1 = concept.getValue();
            this.logger.finest("defaultI value " + str1);
            if (str1.equalsIgnoreCase(str)) {
              this.logger.finest("putting in postalAddressMap");
              this.postalAddressMap.put(concept1.getValue(), concept);
              bool = true;
            } 
          } 
        } else {
          this.postalAddressMap.put(concept1.getValue(), null);
        } 
        if (!bool)
          this.logger.warning(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIMapper:No_match_found_for_JAXR_Postal_Address_Atribute_") + concept1.getValue()); 
      } 
    } 
  }
  
  ClassificationScheme getClassificationSchemeById(String paramString) throws JAXRException {
    return (paramString == null) ? null : getConceptsManager().getClassificationSchemeById(paramString);
  }
  
  void initPostalSchemes() throws JAXRException {
    initSemanticEquivalences();
    Collection collection1 = null;
    this.jaxrPostalAddressScheme = getConceptsManager().getClassificationSchemeById("PostalAddressAttributes");
    if (this.jaxrPostalAddressScheme == null) {
      this.logger.finest("Didn't find JAXR PostalAddressAttributes");
    } else {
      collection1 = this.jaxrPostalAddressScheme.getChildrenConcepts();
      if (collection1 != null)
        this.logger.finest("got jaxr postal children"); 
    } 
    this.defaultPostalScheme = this.service.getDefaultPostalScheme();
    Collection collection2 = null;
    if (this.defaultPostalScheme != null) {
      collection2 = this.defaultPostalScheme.getChildrenConcepts();
      if (collection2 != null)
        this.logger.finest("got default children"); 
    } else {
      this.logger.finest("default Postal Scheme not found");
    } 
  }
  
  private void initSemanticEquivalences() throws JAXRException {
    this.semanticEquivalences = getConnection().getSemanticEquivalences();
    if (this.semanticEquivalences == null) {
      this.logger.finest("SemanticEquivalences are null");
      return;
    } 
    Collection collection1 = null;
    this.jaxrPostalAddressScheme = getConceptsManager().getClassificationSchemeById("PostalAddressAttributes");
    if (this.jaxrPostalAddressScheme == null) {
      this.logger.finest("Didn't find JAXR PostalAddressAttributes");
    } else {
      collection1 = this.jaxrPostalAddressScheme.getChildrenConcepts();
      if (collection1 != null)
        this.logger.finest("got jaxr postal children " + collection1.size()); 
    } 
    ClassificationScheme classificationScheme = this.service.getDefaultPostalScheme();
    Collection collection2 = null;
    if (classificationScheme != null)
      collection2 = classificationScheme.getChildrenConcepts(); 
    Set set = this.semanticEquivalences.keySet();
    Iterator<String> iterator1 = set.iterator();
    Collection collection3 = this.semanticEquivalences.values();
    Iterator<String> iterator2 = collection3.iterator();
    while (iterator1.hasNext()) {
      String str1 = iterator1.next();
      if (iterator2.hasNext())
        String str = iterator2.next(); 
      if (str1 == null)
        this.logger.finest("key is null"); 
      String str2 = (String)this.semanticEquivalences.get(str1);
      if (str2 == null)
        this.logger.finest("Value is null"); 
      Concept concept1 = null;
      Concept concept2 = null;
      if (str1 != null && str2 != null) {
        str1 = str1.trim();
        str2 = str2.trim();
        for (Concept concept : collection1) {
          if (concept.getKey().getId().equalsIgnoreCase(str1)) {
            concept1 = concept;
            break;
          } 
        } 
        if (concept1 == null) {
          this.logger.finest("Did not find jaxr child key Equivalent Concept");
          continue;
        } 
        if (collection2 != null)
          for (Concept concept : collection2) {
            Key key = concept.getKey();
            String str = null;
            if (key != null)
              str = key.getId(); 
            if (str != null && str.equalsIgnoreCase(str2)) {
              concept2 = concept;
              break;
            } 
          }  
        if (concept2 == null)
          this.logger.finest("Did not find value Equivalent Concept"); 
        if (this.equivalentConcepts == null)
          this.equivalentConcepts = new HashMap<Object, Object>(); 
        this.logger.finest("putting keyConcept valueConcept");
        this.equivalentConcepts.put(concept1.getValue(), concept2);
      } 
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\UDDIMapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */