package com.sun.xml.registry.common;

import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FederatedConnection;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;

public class ConnectionFactoryImpl extends ConnectionFactory implements Referenceable {
  private Properties properties;
  
  public void setProperties(Properties paramProperties) throws JAXRException {
    this.properties = paramProperties;
  }
  
  public Properties getProperties() throws JAXRException {
    return this.properties;
  }
  
  public Connection createConnection() throws JAXRException {
    ClassLoader classLoader;
    String str1 = (String)this.properties.get("javax.xml.registry.ConnectionFactoryClass");
    String str2 = (String)this.properties.get("javax.xml.registry.queryManagerURL");
    String str3 = (String)this.properties.get("javax.xml.registry.lifeCycleManagerURL");
    if (str2 == null)
      throw new InvalidRequestException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("ConnectionFactoryImpl:Missing_connection_property_javax.xml.registry.queryManagerURL")); 
    if (str1 == null)
      str1 = "com.sun.xml.registry.uddi.ConnectionFactoryImpl"; 
    try {
      classLoader = getClass().getClassLoader();
    } catch (Exception exception) {
      throw new JAXRException(exception.toString(), exception);
    } 
    try {
      Class<?> clazz = null;
      if (classLoader == null) {
        clazz = Class.forName(str1);
      } else {
        clazz = classLoader.loadClass(str1);
      } 
      ConnectionFactory connectionFactory = (ConnectionFactory)clazz.newInstance();
      connectionFactory.setProperties(this.properties);
      return connectionFactory.createConnection();
    } catch (ClassNotFoundException classNotFoundException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("ConnectionFactoryImpl:Unable_to_create_connection"), classNotFoundException);
    } catch (InstantiationException instantiationException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("ConnectionFactoryImpl:Unable_to_create_connection"), instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new JAXRException(ResourceBundle.getBundle("com/sun/xml/registry/common/LocalStrings").getString("ConnectionFactoryImpl:Unable_to_create_connection"), illegalAccessException);
    } 
  }
  
  public FederatedConnection createFederatedConnection(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
  
  public Reference getReference() throws NamingException {
    return new Reference(ConnectionFactoryImpl.class.getName(), ConnectionFactoryFactory.class.getName(), null);
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registry\common\ConnectionFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */