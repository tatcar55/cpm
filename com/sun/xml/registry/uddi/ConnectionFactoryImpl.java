package com.sun.xml.registry.uddi;

import java.util.Collection;
import java.util.Properties;
import javax.xml.registry.Connection;
import javax.xml.registry.ConnectionFactory;
import javax.xml.registry.FederatedConnection;
import javax.xml.registry.InvalidRequestException;
import javax.xml.registry.JAXRException;
import javax.xml.registry.UnsupportedCapabilityException;

public class ConnectionFactoryImpl extends ConnectionFactory {
  private Properties properties;
  
  public void setProperties(Properties paramProperties) throws JAXRException {
    this.properties = paramProperties;
  }
  
  public Properties getProperties() throws JAXRException {
    return this.properties;
  }
  
  public Connection createConnection() throws JAXRException, InvalidRequestException {
    return new ConnectionImpl(this.properties);
  }
  
  public FederatedConnection createFederatedConnection(Collection paramCollection) throws JAXRException {
    throw new UnsupportedCapabilityException();
  }
}


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\registr\\uddi\ConnectionFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */