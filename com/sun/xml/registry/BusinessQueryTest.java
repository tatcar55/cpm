package com.sun.xml.registry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import javax.xml.registry.BulkResponse;
import javax.xml.registry.BusinessQueryManager;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.JAXRException;
import javax.xml.registry.RegistryService;
import javax.xml.registry.infomodel.Organization;
import javax.xml.registry.infomodel.RegistryObject;
import javax.xml.registry.infomodel.Service;

public class BusinessQueryTest {
  String httpProxyHost = "";
  
  String httpProxyPort = "";
  
  String regUrli = "";
  
  String regUrlp = "";
  
  Properties connProps = new Properties();
  
  private static final String QUERY_URL = "query.url";
  
  private static final String PUBLISH_URL = "publish.url";
  
  private static final String PROXY_HOST = "http.proxy.host";
  
  private static final String PROXY_PORT = "http.proxy.port";
  
  public static void main(String[] paramArrayOfString) {
    String str = "%USA%";
    try {
      Properties properties = new Properties();
      properties.load(BusinessQueryTest.class.getResourceAsStream("query.properties"));
      BusinessQueryTest businessQueryTest = new BusinessQueryTest();
      businessQueryTest.executeQueryTest(properties, str);
    } catch (JAXRException jAXRException) {
      System.err.println("Error during the test: " + jAXRException);
    } catch (IOException iOException) {
      System.err.println("Can not open properties file");
    } 
  }
  
  public void executeQueryTest(Properties paramProperties, String paramString) throws JAXRException {
    try {
      assignUserProperties(paramProperties);
      setConnectionProperties();
      ConnectionFactory connectionFactory = ConnectionFactory.newInstance();
      connectionFactory.setProperties(this.connProps);
      Connection connection = connectionFactory.createConnection();
      RegistryService registryService = connection.getRegistryService();
      BusinessQueryManager businessQueryManager = registryService.getBusinessQueryManager();
      ArrayList<String> arrayList1 = new ArrayList();
      arrayList1.add(paramString);
      ArrayList<String> arrayList2 = new ArrayList();
      arrayList2.add("sortByNameDesc");
      BulkResponse bulkResponse = businessQueryManager.findOrganizations(arrayList2, arrayList1, null, null, null, null);
      if (bulkResponse.getStatus() == 0) {
        System.out.println("Successfully queried the registry for organization matching the name pattern: \"" + paramString + "\"");
        Collection collection = bulkResponse.getCollection();
        System.out.println("Results found: " + collection.size() + "\n");
        for (Organization organization : collection) {
          System.out.println("Organization Name: " + getName((RegistryObject)organization));
          System.out.println("Organization Key: " + organization.getKey().getId());
          System.out.println("Organization Description: " + getDescription((RegistryObject)organization));
          Collection collection1 = organization.getServices();
          for (Service service : collection1) {
            System.out.println("\tService Name: " + getName((RegistryObject)service));
            System.out.println("\tService Key: " + service.getKey().getId());
            System.out.println("\tService Description: " + getDescription((RegistryObject)service));
          } 
        } 
      } else {
        System.err.println("One or more JAXRExceptions occurred during the query operation:");
        Collection collection = bulkResponse.getExceptions();
        for (Exception exception : collection)
          System.err.println(exception.toString()); 
      } 
    } catch (JAXRException jAXRException) {
      jAXRException.printStackTrace();
    } 
  }
  
  private void assignUserProperties(Properties paramProperties) {
    String str = ((String)paramProperties.get("query.url")).trim();
    if (str != null)
      this.regUrli = str; 
    str = ((String)paramProperties.get("publish.url")).trim();
    if (str != null)
      this.regUrlp = str; 
    str = ((String)paramProperties.get("http.proxy.host")).trim();
    if (str != null)
      this.httpProxyHost = str; 
    str = ((String)paramProperties.get("http.proxy.port")).trim();
    if (str != null)
      this.httpProxyPort = str; 
  }
  
  private void setConnectionProperties() {
    this.connProps.setProperty("javax.xml.registry.queryManagerURL", this.regUrli);
    this.connProps.setProperty("javax.xml.registry.lifeCycleManagerURL", this.regUrlp);
    this.connProps.setProperty("javax.xml.registry.factoryClass", "com.sun.xml.registry.uddi.ConnectionFactoryImpl");
    this.connProps.setProperty("com.sun.xml.registry.http.proxyHost", this.httpProxyHost);
    this.connProps.setProperty("com.sun.xml.registry.http.proxyPort", this.httpProxyPort);
  }
  
  private String getName(RegistryObject paramRegistryObject) throws JAXRException {
    try {
      return paramRegistryObject.getName().getValue();
    } catch (NullPointerException nullPointerException) {
      return "";
    } 
  }
  
  private String getDescription(RegistryObject paramRegistryObject) throws JAXRException {
    try {
      return paramRegistryObject.getDescription().getValue();
    } catch (NullPointerException nullPointerException) {
      return "";
    } 
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\BusinessQueryTest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */