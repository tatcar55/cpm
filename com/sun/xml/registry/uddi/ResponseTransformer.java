package com.sun.xml.registry.uddi;

import com.sun.xml.registry.common.BulkResponseImpl;
import com.sun.xml.registry.uddi.bindings_v2_2.AssertionStatusReport;
import com.sun.xml.registry.uddi.bindings_v2_2.AuthToken;
import com.sun.xml.registry.uddi.bindings_v2_2.BindingDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.BindingTemplate;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessEntity;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessInfos;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessList;
import com.sun.xml.registry.uddi.bindings_v2_2.BusinessService;
import com.sun.xml.registry.uddi.bindings_v2_2.DispositionReport;
import com.sun.xml.registry.uddi.bindings_v2_2.PublisherAssertions;
import com.sun.xml.registry.uddi.bindings_v2_2.RegisteredInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.RelatedBusinessInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.RelatedBusinessInfos;
import com.sun.xml.registry.uddi.bindings_v2_2.RelatedBusinessesList;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceInfos;
import com.sun.xml.registry.uddi.bindings_v2_2.ServiceList;
import com.sun.xml.registry.uddi.bindings_v2_2.TModel;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelDetail;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelInfo;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelInfos;
import com.sun.xml.registry.uddi.bindings_v2_2.TModelList;
import com.sun.xml.registry.uddi.bindings_v2_2.Truncated;
import com.sun.xml.registry.uddi.infomodel.ConceptImpl;
import com.sun.xml.registry.uddi.infomodel.KeyImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.JAXRException;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Service;
import javax.xml.registry.infomodel.ServiceBinding;

public class ResponseTransformer {
  private UDDIMapper mapper;
  
  public ResponseTransformer(UDDIMapper paramUDDIMapper) {
    this.mapper = paramUDDIMapper;
  }
  
  BulkResponse transformResponse(Object paramObject, Collection paramCollection, String paramString) throws JAXRException {
    paramObject = ((JAXBElement)paramObject).getValue();
    return (paramObject instanceof BusinessList) ? transformResponse((BusinessList)paramObject, paramCollection, paramString) : ((paramObject instanceof AuthToken) ? transformResponse((AuthToken)paramObject, paramCollection, paramString) : ((paramObject instanceof AssertionStatusReport) ? transformResponse((AssertionStatusReport)paramObject, paramCollection, paramString) : ((paramObject instanceof RelatedBusinessesList) ? transformResponse((RelatedBusinessesList)paramObject, paramCollection, paramString) : ((paramObject instanceof ServiceList) ? transformResponse((ServiceList)paramObject, paramCollection, paramString) : ((paramObject instanceof TModelList) ? transformResponse((TModelList)paramObject, paramCollection, paramString) : ((paramObject instanceof BusinessDetail) ? transformResponse((BusinessDetail)paramObject, paramCollection, paramString) : ((paramObject instanceof ServiceDetail) ? transformResponse((ServiceDetail)paramObject, paramCollection, paramString) : ((paramObject instanceof BindingDetail) ? transformResponse((BindingDetail)paramObject, paramCollection, paramString) : ((paramObject instanceof TModelDetail) ? transformResponse((TModelDetail)paramObject, paramCollection, paramString) : ((paramObject instanceof RegisteredInfo) ? transformResponse((RegisteredInfo)paramObject, paramCollection, paramString) : ((paramObject instanceof DispositionReport) ? transformResponse((DispositionReport)paramObject, paramCollection, paramString) : ((paramObject instanceof PublisherAssertions) ? transformResponse((PublisherAssertions)paramObject, paramCollection, paramString) : null))))))))))));
  }
  
  BulkResponse transformResponse(AuthToken paramAuthToken, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    ArrayList<String> arrayList = new ArrayList();
    String str = paramAuthToken.getAuthInfo();
    arrayList.add(str);
    bulkResponseImpl.setCollection(arrayList);
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(BusinessList paramBusinessList, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramBusinessList != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<Organization> arrayList = new ArrayList();
      BusinessInfos businessInfos = paramBusinessList.getBusinessInfos();
      Organization organization = null;
      if (businessInfos != null) {
        List list = businessInfos.getBusinessInfo();
        Iterator<BusinessInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
          organization = this.mapper.businessInfo2Organization(iterator.next());
          arrayList.add(organization);
        } 
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(AssertionStatusReport paramAssertionStatusReport, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    if (paramAssertionStatusReport != null) {
      String str1 = paramAssertionStatusReport.getGeneric();
      String str2 = paramAssertionStatusReport.getOperator();
      List list = paramAssertionStatusReport.getAssertionStatusItem();
      Collection collection = this.mapper.assertionStatusItems2Associations(list);
      if (collection != null)
        bulkResponseImpl.setCollection(collection); 
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(RelatedBusinessesList paramRelatedBusinessesList, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    ArrayList arrayList = new ArrayList();
    if (paramRelatedBusinessesList != null) {
      bulkResponseImpl = new BulkResponseImpl();
      RelatedBusinessInfos relatedBusinessInfos = paramRelatedBusinessesList.getRelatedBusinessInfos();
      Collection collection = null;
      List list = relatedBusinessInfos.getRelatedBusinessInfo();
      Iterator<RelatedBusinessInfo> iterator = list.iterator();
      while (iterator.hasNext()) {
        collection = this.mapper.relatedBusinessInfo2Associations(iterator.next(), paramCollection);
        if (collection != null)
          arrayList.addAll(collection); 
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(ServiceList paramServiceList, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramServiceList != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<Service> arrayList = new ArrayList();
      ServiceInfos serviceInfos = paramServiceList.getServiceInfos();
      List list = serviceInfos.getServiceInfo();
      Iterator<ServiceInfo> iterator = list.iterator();
      Service service = null;
      while (iterator.hasNext()) {
        service = this.mapper.serviceInfo2Service(iterator.next());
        arrayList.add(service);
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(TModelList paramTModelList, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = new BulkResponseImpl();
    if (paramTModelList != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList arrayList = new ArrayList();
      TModelInfos tModelInfos = paramTModelList.getTModelInfos();
      Object object = null;
      ArrayList<KeyImpl> arrayList1 = new ArrayList();
      List list = tModelInfos.getTModelInfo();
      Iterator<TModelInfo> iterator = list.iterator();
      while (iterator.hasNext()) {
        String str = ((TModelInfo)iterator.next()).getTModelKey();
        KeyImpl keyImpl = new KeyImpl(str);
        arrayList1.add(keyImpl);
      } 
      ArrayList<RegistryObject> arrayList2 = new ArrayList();
      if (arrayList1.size() > 0) {
        BulkResponse bulkResponse = this.mapper.getConcepts(arrayList1);
        if (bulkResponse.getExceptions() == null) {
          Collection collection = bulkResponse.getCollection();
          for (RegistryObject registryObject : collection)
            arrayList2.add(registryObject); 
        } 
      } 
      bulkResponseImpl.setCollection(arrayList2);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(BusinessDetail paramBusinessDetail, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramBusinessDetail != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<Organization> arrayList = new ArrayList();
      List list = paramBusinessDetail.getBusinessEntity();
      if (list != null) {
        Iterator<BusinessEntity> iterator = list.iterator();
        while (iterator.hasNext()) {
          Organization organization = this.mapper.businessEntity2Organization(iterator.next());
          if (paramString.equalsIgnoreCase("find")) {
            arrayList.add(organization);
            continue;
          } 
          arrayList.add(organization.getKey());
        } 
        bulkResponseImpl.setCollection(arrayList);
      } 
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(ServiceDetail paramServiceDetail, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramServiceDetail != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<Service> arrayList = new ArrayList();
      List list = paramServiceDetail.getBusinessService();
      if (list != null) {
        Iterator<BusinessService> iterator = list.iterator();
        while (iterator.hasNext()) {
          Service service = this.mapper.businessService2Service(iterator.next());
          if (paramString.equals("find")) {
            arrayList.add(service);
            continue;
          } 
          arrayList.add(service.getKey());
        } 
        bulkResponseImpl.setCollection(arrayList);
      } 
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(BindingDetail paramBindingDetail, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramBindingDetail != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<ServiceBinding> arrayList = new ArrayList();
      List list = paramBindingDetail.getBindingTemplate();
      Iterator<BindingTemplate> iterator = list.iterator();
      while (iterator.hasNext()) {
        ServiceBinding serviceBinding = this.mapper.bindingTemplate2ServiceBinding(iterator.next());
        if (paramString.equals("find")) {
          arrayList.add(serviceBinding);
          continue;
        } 
        arrayList.add(serviceBinding.getKey());
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(TModelDetail paramTModelDetail, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramTModelDetail != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<RegistryObject> arrayList = new ArrayList();
      List list = paramTModelDetail.getTModel();
      Iterator<TModel> iterator = list.iterator();
      while (iterator.hasNext()) {
        RegistryObject registryObject = this.mapper.tModel2ConceptOrClassificationScheme(iterator.next());
        if (paramString.equals("find")) {
          arrayList.add(registryObject);
          continue;
        } 
        arrayList.add(registryObject.getKey());
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(RegisteredInfo paramRegisteredInfo, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramRegisteredInfo != null) {
      bulkResponseImpl = new BulkResponseImpl();
      ArrayList<Organization> arrayList = new ArrayList();
      BusinessInfos businessInfos = paramRegisteredInfo.getBusinessInfos();
      List list1 = businessInfos.getBusinessInfo();
      Organization organization = null;
      Iterator<BusinessInfo> iterator = list1.iterator();
      while (iterator.hasNext()) {
        organization = this.mapper.businessInfo2Organization(iterator.next());
        arrayList.add(organization);
      } 
      TModelInfos tModelInfos = paramRegisteredInfo.getTModelInfos();
      List list2 = tModelInfos.getTModelInfo();
      ConceptImpl conceptImpl = null;
      Iterator<TModelInfo> iterator1 = list2.iterator();
      while (iterator1.hasNext()) {
        conceptImpl = (ConceptImpl)this.mapper.tModel2Concept(iterator1.next());
        arrayList.add(conceptImpl);
      } 
      bulkResponseImpl.setCollection(arrayList);
    } 
    return (BulkResponse)bulkResponseImpl;
  }
  
  BulkResponse transformResponse(DispositionReport paramDispositionReport, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponse bulkResponse = null;
    if (paramDispositionReport != null) {
      String str1 = paramDispositionReport.getGeneric();
      String str2 = paramDispositionReport.getOperator();
      Truncated truncated = paramDispositionReport.getTruncated();
      List list = paramDispositionReport.getResult();
      bulkResponse = this.mapper.results2BulkResponse(list, paramCollection, paramString);
    } 
    return bulkResponse;
  }
  
  BulkResponse transformResponse(PublisherAssertions paramPublisherAssertions, Collection paramCollection, String paramString) throws JAXRException {
    BulkResponseImpl bulkResponseImpl = null;
    if (paramPublisherAssertions != null) {
      bulkResponseImpl = new BulkResponseImpl();
      String str1 = paramPublisherAssertions.getGeneric();
      String str2 = paramPublisherAssertions.getOperator();
      String str3 = paramPublisherAssertions.getAuthorizedName();
      List list = paramPublisherAssertions.getPublisherAssertion();
      Collection collection = this.mapper.publisherAssertions2Associations(list);
      if (paramString.equalsIgnoreCase("save")) {
        bulkResponseImpl.setCollection(paramCollection);
      } else {
        bulkResponseImpl.setCollection(collection);
      } 
    } 
    return (BulkResponse)bulkResponseImpl;
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\ResponseTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */