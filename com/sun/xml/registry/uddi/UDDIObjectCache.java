package com.sun.xml.registry.uddi;

import com.sun.xml.registry.uddi.infomodel.RegistryObjectImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Key;
import javax.xml.registry.infomodel.RegistryObject;

public class UDDIObjectCache {
  private Hashtable cache = new Hashtable<Object, Object>();
  
  private LinkedList list = new LinkedList();
  
  Collection registryServices;
  
  private static final int MAX_CACHE_SIZE = 150;
  
  public UDDIObjectCache(RegistryServiceImpl paramRegistryServiceImpl) {
    if (this.registryServices == null)
      this.registryServices = new ArrayList(); 
    addToRegistryServices(paramRegistryServiceImpl);
  }
  
  void addObjectToCache(RegistryObjectImpl paramRegistryObjectImpl, String paramString) throws JAXRException {
    if (paramRegistryObjectImpl.getServiceId() == null)
      paramRegistryObjectImpl.setServiceId(paramString); 
    add(paramRegistryObjectImpl);
  }
  
  void addObjectsToCache(Collection paramCollection, String paramString) throws JAXRException {
    for (RegistryObjectImpl registryObjectImpl : paramCollection) {
      if (registryObjectImpl instanceof RegistryObjectImpl) {
        RegistryObjectImpl registryObjectImpl1 = registryObjectImpl;
        if (registryObjectImpl1.getServiceId() == null)
          registryObjectImpl1.setServiceId(paramString); 
        if (registryObjectImpl1.getServiceId() == null)
          registryObjectImpl1.setServiceId(paramString); 
        add(registryObjectImpl1);
      } 
    } 
  }
  
  RegistryObject fetchObjectFromCache(String paramString) throws JAXRException {
    return (RegistryObject)this.cache.get(paramString);
  }
  
  public void fetchObject(RegistryObjectImpl paramRegistryObjectImpl, String paramString) throws JAXRException {
    Object object1 = null;
    Object object2 = null;
    if (paramRegistryObjectImpl != null) {
      RegistryServiceImpl registryServiceImpl = (RegistryServiceImpl)paramRegistryObjectImpl.getRegistryService();
      if (registryServiceImpl.getConnection().useCache() && paramRegistryObjectImpl.isLoaded() == true)
        return; 
      registryServiceImpl.getUDDIMapper().getRegistryObject(paramRegistryObjectImpl);
      if (registryServiceImpl.getConnection().useCache())
        add(paramRegistryObjectImpl); 
      paramRegistryObjectImpl.setStatusFlags(true, true, false);
    } 
    if (paramRegistryObjectImpl != null)
      paramRegistryObjectImpl.setStatusFlags(true, true, false); 
  }
  
  void removeObjectFromCache(String paramString) {
    if (this.list.size() > 0)
      this.list.remove(paramString); 
    RegistryObjectImpl registryObjectImpl = (RegistryObjectImpl)this.cache.remove(paramString);
    if (registryObjectImpl != null)
      registryObjectImpl.setIsDeleted(true); 
  }
  
  void removeObjectsFromCache(Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return; 
    Iterator<Key> iterator = paramCollection.iterator();
    try {
      while (iterator.hasNext()) {
        String str = ((Key)iterator.next()).getId();
        removeObjectFromCache(str);
      } 
    } catch (JAXRException jAXRException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/uddi/LocalStrings").getString("UDDIObjectManager:Error_deleting_objects_from_Cache"), jAXRException);
    } 
  }
  
  private void add(RegistryObjectImpl paramRegistryObjectImpl) throws JAXRException {
    String str = paramRegistryObjectImpl.getKey().getId();
    synchronized (this.list) {
      this.list.remove(str);
      this.list.addFirst(str);
    } 
    if (this.cache.size() >= 150) {
      String str1 = null;
      synchronized (this.list) {
        str1 = this.list.removeLast();
      } 
      RegistryObjectImpl registryObjectImpl = (RegistryObjectImpl)this.cache.remove(str1);
    } 
    synchronized (this) {
      this.cache.put(str, paramRegistryObjectImpl);
    } 
  }
  
  public void fetchAssociations(RegistryObjectImpl paramRegistryObjectImpl, String paramString) throws JAXRException {
    BulkResponse bulkResponse = null;
    if (paramRegistryObjectImpl != null) {
      String str1 = paramRegistryObjectImpl.getKey().getId();
      if (str1 == null)
        return; 
      if (paramRegistryObjectImpl.areAssociationsLoaded() == true)
        return; 
      String str2 = paramRegistryObjectImpl.getServiceId();
      RegistryServiceImpl registryServiceImpl = getService(str2);
      bulkResponse = registryServiceImpl.getUDDIMapper().findAssociations(null, str1, null, null);
    } 
    if (bulkResponse.getExceptions() == null) {
      Collection collection = bulkResponse.getCollection();
      for (RegistryObjectImpl registryObjectImpl : collection) {
        add(registryObjectImpl);
        registryObjectImpl.setAssociationsLoaded(true);
      } 
    } 
  }
  
  void flushCache() {
    synchronized (this) {
      this.cache.clear();
    } 
  }
  
  void addToRegistryServices(RegistryServiceImpl paramRegistryServiceImpl) {
    synchronized (this.registryServices) {
      if (!this.registryServices.contains(paramRegistryServiceImpl))
        this.registryServices.add(paramRegistryServiceImpl); 
    } 
  }
  
  public RegistryServiceImpl getService(String paramString) {
    synchronized (this.registryServices) {
      for (RegistryServiceImpl registryServiceImpl : this.registryServices) {
        if (registryServiceImpl.getServiceId().equals(paramString))
          return registryServiceImpl; 
      } 
    } 
    return null;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\UDDIObjectCache.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */