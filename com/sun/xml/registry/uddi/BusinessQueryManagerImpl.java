package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.Utility;
import java.util.Collection;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.Key;

public class BusinessQueryManagerImpl extends QueryManagerImpl implements BusinessQueryManager {
  public BusinessQueryManagerImpl() {}
  
  public BusinessQueryManagerImpl(RegistryServiceImpl paramRegistryServiceImpl) {
    super(paramRegistryServiceImpl);
  }
  
  public BulkResponse findOrganizations(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4, Collection paramCollection5, Collection paramCollection6) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindOrganizationsCommand(this.service, bulkResponseImpl, paramCollection1, paramCollection2, paramCollection3, paramCollection4, paramCollection5, paramCollection6));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findOrganizations(paramCollection1, paramCollection2, paramCollection3, paramCollection4, paramCollection5, paramCollection6);
  }
  
  public BulkResponse findServices(Key paramKey, Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindServicesCommand(this.service, bulkResponseImpl, paramKey, paramCollection1, paramCollection2, paramCollection3, paramCollection4));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findServices(paramKey, paramCollection1, paramCollection2, paramCollection3, paramCollection4);
  }
  
  public BulkResponse findServiceBindings(Key paramKey, Collection paramCollection1, Collection paramCollection2, Collection paramCollection3) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindServiceBindingsCommand(this.service, bulkResponseImpl, paramKey, paramCollection1, paramCollection2, paramCollection3));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findServiceBindings(paramKey, paramCollection1, paramCollection2, paramCollection3);
  }
  
  public BulkResponse findClassificationSchemes(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindClassificationSchemesCommand(this.service, bulkResponseImpl, paramCollection1, paramCollection2, paramCollection3, paramCollection4));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findClassificationSchemes(paramCollection1, paramCollection2, paramCollection3, paramCollection4);
  }
  
  public ClassificationScheme findClassificationSchemeByName(Collection paramCollection, String paramString) throws JAXRException {
    return (paramString == null || paramString.length() == 0) ? null : this.uddi.findClassificationSchemeByName(paramCollection, paramString);
  }
  
  public BulkResponse findConcepts(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4, Collection paramCollection5) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindConceptsCommand(this.service, bulkResponseImpl, paramCollection1, paramCollection2, paramCollection3, paramCollection4, paramCollection5));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findConcepts(paramCollection1, paramCollection2, paramCollection3, paramCollection4, paramCollection5);
  }
  
  public Concept findConceptByPath(String paramString) throws JAXRException {
    return this.uddi.findConceptByPath(paramString);
  }
  
  public BulkResponse findRegistryPackages(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse findAssociations(Collection paramCollection1, String paramString1, String paramString2, Collection paramCollection2) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindAssociationsCommand(this.service, bulkResponseImpl, paramCollection1, paramString1, paramString2, paramCollection2));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findAssociations(paramCollection1, paramString1, paramString2, paramCollection2);
  }
  
  public BulkResponse findCallerAssociations(Collection paramCollection1, Boolean paramBoolean1, Boolean paramBoolean2, Collection paramCollection2) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.FindCallerAssociationsCommand(this.service, bulkResponseImpl, paramCollection1, paramBoolean1, paramBoolean2, paramCollection2));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.findCallerAssociations(paramCollection1, paramBoolean1, paramBoolean2, paramCollection2);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\BusinessQueryManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */