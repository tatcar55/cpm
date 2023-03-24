package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.Utility;
import java.util.Collection;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessLifeCycleManager;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Association;

public class BusinessLifeCycleManagerImpl extends LifeCycleManagerImpl implements BusinessLifeCycleManager {
  public BusinessLifeCycleManagerImpl() {}
  
  public BusinessLifeCycleManagerImpl(RegistryServiceImpl paramRegistryServiceImpl) {
    super(paramRegistryServiceImpl);
  }
  
  public BulkResponse saveOrganizations(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveOrganizationsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveOrganizations(paramCollection);
  }
  
  public BulkResponse saveServices(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveServicesCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveServices(paramCollection);
  }
  
  public BulkResponse saveServiceBindings(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveServiceBindingsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveServiceBindings(paramCollection);
  }
  
  public BulkResponse saveConcepts(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveConceptsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveConcepts(paramCollection);
  }
  
  public BulkResponse saveClassificationSchemes(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveClassificationSchemesCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveClassificationSchemes(paramCollection);
  }
  
  public BulkResponse deleteOrganizations(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteOrganizationsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteOrganizations(paramCollection);
  }
  
  public BulkResponse deleteServices(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteServicesCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteServices(paramCollection);
  }
  
  public BulkResponse deleteServiceBindings(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteServiceBindingsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteServiceBindings(paramCollection);
  }
  
  public BulkResponse deleteConcepts(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteConceptsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteConcepts(paramCollection);
  }
  
  public BulkResponse deleteClassificationSchemes(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteClassificationSchemesCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteConcepts(paramCollection);
  }
  
  public BulkResponse saveAssociations(Collection paramCollection, boolean paramBoolean) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.SaveAssociationsCommand(this.service, bulkResponseImpl, paramCollection, paramBoolean));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.saveAssociations(paramCollection, paramBoolean);
  }
  
  public BulkResponse deleteAssociations(Collection paramCollection) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.DeleteAssociationsCommand(this.service, bulkResponseImpl, paramCollection));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.deleteAssociations(paramCollection);
  }
  
  public void confirmAssociation(Association paramAssociation) throws JAXRException {
    this.uddi.confirmAssociation(paramAssociation);
  }
  
  public void unConfirmAssociation(Association paramAssociation) throws JAXRException {
    this.uddi.unConfirmAssociation(paramAssociation);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\BusinessLifeCycleManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */