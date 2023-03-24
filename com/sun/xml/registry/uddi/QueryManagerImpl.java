package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.Utility;
import java.util.Collection;
import java.util.Locale;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.registry.QueryManager;
import javax.xml.registry.RegistryService;
import javax.xml.registry.UnsupportedCapabilityException;
import javax.xml.registry.infomodel.RegistryObject;

public class QueryManagerImpl implements QueryManager {
  RegistryServiceImpl service;
  
  UDDIMapper uddi;
  
  public QueryManagerImpl() {
    String str = Locale.getDefault().getCountry();
    if (str == null || str == "")
      Locale.setDefault(Locale.US); 
    System.out.println("Default locale: " + Locale.getDefault().toString());
  }
  
  public QueryManagerImpl(RegistryServiceImpl paramRegistryServiceImpl) {
    this();
    this.service = paramRegistryServiceImpl;
    this.uddi = paramRegistryServiceImpl.getUDDIMapper();
  }
  
  public BulkResponse getRegistryObjects(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse getRegistryObjects() throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.GetRegistryObjectsCommand(this.service, bulkResponseImpl));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.getRegistryObjects();
  }
  
  public RegistryObject getRegistryObject(String paramString) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public BulkResponse getRegistryObjects(String paramString) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.GetRegistryObjectsByTypeCommand(this.service, bulkResponseImpl, paramString));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.getRegistryObjects(paramString);
  }
  
  public RegistryObject getRegistryObject(String paramString1, String paramString2) throws JAXRException {
    return this.uddi.getRegistryObject(paramString1, paramString2);
  }
  
  public BulkResponse getRegistryObjects(Collection paramCollection, String paramString) throws JAXRException {
    if (!this.service.getConnection().isSynchronous()) {
      BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
      bulkResponseImpl.setStatus(3);
      Utility.getInstance();
      bulkResponseImpl.setRequestId(Utility.generateUUID());
      this.service.storeBulkResponse((BulkResponse)bulkResponseImpl);
      FuturesRequestManager.invokeCommand(new JAXRCommand.GetRegistryObjectsByKeysCommand(this.service, bulkResponseImpl, paramCollection, paramString));
      return (BulkResponse)bulkResponseImpl;
    } 
    return this.uddi.getRegistryObjects(paramCollection, paramString);
  }
  
  public RegistryService getRegistryService() {
    return this.service;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\QueryManagerImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */