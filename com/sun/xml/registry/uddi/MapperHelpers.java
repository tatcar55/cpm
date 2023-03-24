package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.common.util.MarshallerUtil;
import com.sun.xml.registry.common.util.XMLUtil;
import com.sun.xml.registry.uddi.bindings_v2_2.URLType;
import com.sun.xml.registry.uddi.infomodel.OrganizationImpl;
import com.sun.xml.registry.uddi.infomodel.ServiceImpl;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Association;
import javax.xml.registry.infomodel.ClassificationScheme;
import javax.xml.registry.infomodel.Concept;
import javax.xml.registry.infomodel.RegistryObject;

public class MapperHelpers extends JAXRConstants {
  Logger logger = AccessController.<Logger>doPrivileged(new PrivilegedAction<Logger>() {
        public Object run() {
          return Logger.getLogger("javax.enterprise.resource.webservices.registry.uddi");
        }
      });
  
  private static XMLUtil xmlUtil;
  
  private static MarshallerUtil marshallerUtil;
  
  Collection getCallerIsTargetByState(Collection<? extends Association> paramCollection, Boolean paramBoolean1, Boolean paramBoolean2) throws JAXRException {
    ArrayList<Association> arrayList = new ArrayList();
    if (paramBoolean1 != null && paramBoolean2 != null) {
      boolean bool1 = paramBoolean1.booleanValue();
      boolean bool2 = paramBoolean2.booleanValue();
      for (Association association : paramCollection) {
        boolean bool3 = association.isConfirmedBySourceOwner();
        boolean bool4 = association.isConfirmedByTargetOwner();
        if (bool4 == bool1 && bool3 == bool2)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 != null && paramBoolean2 == null) {
      boolean bool = paramBoolean1.booleanValue();
      for (Association association : paramCollection) {
        boolean bool1 = association.isConfirmedByTargetOwner();
        if (bool1 == bool)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 == null && paramBoolean2 != null) {
      boolean bool = paramBoolean2.booleanValue();
      for (Association association : paramCollection) {
        boolean bool1 = association.isConfirmedBySourceOwner();
        if (bool1 == bool)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 == null && paramBoolean2 == null)
      arrayList.addAll(paramCollection); 
    return arrayList;
  }
  
  Collection getCallerIsSourceByState(Collection<? extends Association> paramCollection, Boolean paramBoolean1, Boolean paramBoolean2) throws JAXRException {
    ArrayList<Association> arrayList = new ArrayList();
    if (paramBoolean1 != null && paramBoolean2 != null) {
      boolean bool1 = paramBoolean1.booleanValue();
      boolean bool2 = paramBoolean2.booleanValue();
      for (Association association : paramCollection) {
        boolean bool3 = association.isConfirmedBySourceOwner();
        boolean bool4 = association.isConfirmedByTargetOwner();
        if (bool3 == bool1 && bool4 == bool2)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 != null && paramBoolean2 == null) {
      boolean bool = paramBoolean1.booleanValue();
      for (Association association : paramCollection) {
        boolean bool1 = association.isConfirmedBySourceOwner();
        if (bool1 == bool)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 == null && paramBoolean2 != null) {
      boolean bool = paramBoolean2.booleanValue();
      for (Association association : paramCollection) {
        boolean bool1 = association.isConfirmedByTargetOwner();
        if (bool1 == bool)
          arrayList.add(association); 
      } 
    } 
    if (paramBoolean1 == null && paramBoolean2 == null)
      arrayList.addAll(paramCollection); 
    return arrayList;
  }
  
  Collection getCallerTargetAssociations(Collection paramCollection1, Collection paramCollection2) throws JAXRException {
    ArrayList<Association> arrayList = new ArrayList();
    for (Association association : paramCollection1) {
      RegistryObject registryObject = association.getTargetObject();
      if (registryObject != null) {
        String str = registryObject.getKey().getId();
        for (String str1 : paramCollection2) {
          if (str1.equalsIgnoreCase(str))
            arrayList.add(association); 
        } 
      } 
    } 
    return arrayList;
  }
  
  Collection getCallerSourceAssociations(Collection paramCollection1, Collection paramCollection2, Collection paramCollection3, Collection paramCollection4) throws JAXRException {
    ArrayList<Association> arrayList = new ArrayList();
    for (Association association : paramCollection1) {
      RegistryObject registryObject = association.getSourceObject();
      if (registryObject != null) {
        String str = registryObject.getKey().getId();
        for (String str1 : paramCollection2) {
          if (str1.equalsIgnoreCase(str))
            arrayList.add(association); 
        } 
      } 
    } 
    return arrayList;
  }
  
  BulkResponse filterAssociationsByConfirmationState(BulkResponse paramBulkResponse, Boolean paramBoolean1, Boolean paramBoolean2, Collection paramCollection1, Collection paramCollection2) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList arrayList = new ArrayList();
    Collection collection3 = paramBulkResponse.getCollection();
    Iterator iterator = collection3.iterator();
    Collection collection4 = paramCollection1;
    Collection collection5 = paramCollection2;
    Collection collection1 = getCallerSourceAssociations(collection3, collection4);
    Collection collection2 = getCallerTargetAssociations(collection3, collection5);
    Collection collection6 = getCallerIsSourceByState(collection1, paramBoolean1, paramBoolean2);
    Collection collection7 = getCallerIsTargetByState(collection2, paramBoolean1, paramBoolean2);
    if (collection6 != null && collection6.size() != 0)
      arrayList.addAll(collection6); 
    if (collection7 != null && collection7.size() != 0)
      arrayList.addAll(collection7); 
    bulkResponseImpl.setPartialResponse(paramBulkResponse.isPartialResponse());
    bulkResponseImpl.setStatus(paramBulkResponse.getStatus());
    bulkResponseImpl.addCollection(arrayList);
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse filterByAssociationTypes(BulkResponse paramBulkResponse, Collection paramCollection) throws JAXRException {
    if (paramCollection == null)
      return null; 
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList<Association> arrayList = new ArrayList();
    Collection collection = paramBulkResponse.getCollection();
    Iterator<Association> iterator = collection.iterator();
    for (Concept concept : paramCollection) {
      while (iterator.hasNext()) {
        Association association = iterator.next();
        Concept concept1 = association.getAssociationType();
        if (concept1 != null && concept1.getValue().equalsIgnoreCase(concept.getValue()))
          arrayList.add(association); 
      } 
    } 
    bulkResponseImpl.setPartialResponse(paramBulkResponse.isPartialResponse());
    bulkResponseImpl.setStatus(paramBulkResponse.getStatus());
    bulkResponseImpl.addCollection(arrayList);
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse filterAssociations(BulkResponse paramBulkResponse, int paramInt, String paramString1, String paramString2) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList<Association> arrayList = new ArrayList();
    Collection collection = paramBulkResponse.getCollection();
    for (Association association : collection) {
      RegistryObject registryObject1 = association.getSourceObject();
      RegistryObject registryObject2 = association.getTargetObject();
      String str1 = registryObject1.getKey().getId();
      String str2 = registryObject2.getKey().getId();
      switch (paramInt) {
        case 100:
          if (str1.equals(paramString1))
            arrayList.add(association); 
        case 200:
          if (str2.equals(paramString2))
            arrayList.add(association); 
        case 300:
          if (str1.equals(paramString1) && str2.equals(paramString2))
            arrayList.add(association); 
      } 
    } 
    bulkResponseImpl.setPartialResponse(paramBulkResponse.isPartialResponse());
    bulkResponseImpl.setStatus(paramBulkResponse.getStatus());
    bulkResponseImpl.addCollection(arrayList);
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse cullDuplicates(BulkResponse paramBulkResponse) throws JAXRException {
    Collection collection1 = paramBulkResponse.getCollection();
    if (collection1.isEmpty())
      return paramBulkResponse; 
    Collection collection2 = cullDuplicates(collection1);
    ((BulkResponseImpl)paramBulkResponse).setCollection(collection2);
    return paramBulkResponse;
  }
  
  Collection cullDuplicates(Collection paramCollection) throws JAXRException {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>(paramCollection.size() * 2 - 1);
    for (RegistryObject registryObject1 : paramCollection) {
      String str = registryObject1.getKey().getId().toUpperCase();
      if (!hashMap.containsKey(str)) {
        hashMap.put(str, registryObject1);
        continue;
      } 
      this.logger.finest("Found a dup of " + str);
      RegistryObject registryObject2 = (RegistryObject)hashMap.get(str);
      if (registryObject1 instanceof ClassificationScheme && registryObject2 instanceof ClassificationScheme) {
        this.logger.finest("Both RO's are classificationSchemes");
        if (((ClassificationScheme)registryObject1).isExternal()) {
          this.logger.finest("registryObject is external - don't keep it");
          continue;
        } 
        this.logger.finest("RegistryObject is internal - remove dup - keep internal ro");
        hashMap.remove(str);
        hashMap.put(str, registryObject1);
      } 
    } 
    return hashMap.values();
  }
  
  BulkResponse extractRegistryObjectByClass(BulkResponse paramBulkResponse, String paramString) throws JAXRException {
    ArrayList<RegistryObject> arrayList = new ArrayList();
    if (paramBulkResponse.getExceptions() == null) {
      Collection collection = paramBulkResponse.getCollection();
      for (RegistryObject registryObject : collection) {
        if (paramString.equals("Concept")) {
          if (registryObject instanceof Concept)
            arrayList.add(registryObject); 
          continue;
        } 
        if (paramString.equals("ClassificationScheme") && registryObject instanceof ClassificationScheme)
          arrayList.add(registryObject); 
      } 
      ((BulkResponseImpl)paramBulkResponse).setCollection(arrayList);
    } 
    return paramBulkResponse;
  }
  
  Collection getAllServiceBindingsFromOrganizations(Collection paramCollection) throws JAXRException {
    ArrayList arrayList = new ArrayList();
    Collection collection = getAllServicesFromOrganizations(paramCollection);
    for (ServiceImpl serviceImpl : collection) {
      Collection collection1 = serviceImpl.getServiceBindings();
      arrayList.addAll(collection1);
    } 
    return arrayList;
  }
  
  Collection getAllServicesFromOrganizations(Collection paramCollection) throws JAXRException {
    ArrayList arrayList = new ArrayList();
    for (OrganizationImpl organizationImpl : paramCollection) {
      Collection collection = organizationImpl.getServices();
      arrayList.addAll(collection);
    } 
    return arrayList;
  }
  
  URLType parseUrlForUrlType(String paramString) {
    URLType uRLType = null;
    if (paramString.indexOf("https") != -1 || paramString.indexOf("HTTPS") != -1) {
      uRLType = URLType.HTTPS;
    } else if (paramString.indexOf("http") != -1 || paramString.indexOf("HTTP") != -1) {
      uRLType = URLType.HTTP;
    } else if (paramString.indexOf("ftp") != -1 || paramString.indexOf("FTP") != -1) {
      uRLType = URLType.FTP;
    } else if (paramString.indexOf("phone") != -1 || paramString.indexOf("PHONE") != -1) {
      uRLType = URLType.PHONE;
    } else if (paramString.indexOf("mailto") != -1 || paramString.indexOf("MAILTO") != -1) {
      uRLType = URLType.MAILTO;
    } else {
      uRLType = URLType.OTHER;
    } 
    return uRLType;
  }
  
  Collection getCallerSourceAssociations(Collection paramCollection1, Collection paramCollection2) throws JAXRException {
    ArrayList<Association> arrayList = new ArrayList();
    for (Association association : paramCollection1) {
      RegistryObject registryObject = association.getSourceObject();
      if (registryObject != null) {
        String str = registryObject.getKey().getId();
        for (String str1 : paramCollection2) {
          if (str1.equalsIgnoreCase(str))
            arrayList.add(association); 
        } 
      } 
    } 
    return arrayList;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\MapperHelpers.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */